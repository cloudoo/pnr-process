package com.csair.loong.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractHbaseDao<T> {
	
	private static final Logger log = LoggerFactory
			.getLogger(AbstractHbaseDao.class);
	protected Connection conn;
	
	static final String JDBC_DRIVER_CLASS = "org.apache.phoenix.jdbc.PhoenixDriver";
	// static final String JDBC_URL = "jdbc:phoenix:10.95.68.71:2181:/hbase";
	static final String JDBC_URL = "jdbc:phoenix:10.92.1.129:2181:/hbase";
	
	public void init() {
		if (conn == null) {
			try {
				Class.forName(JDBC_DRIVER_CLASS);
				conn = DriverManager.getConnection(JDBC_URL, "", "");

			} catch (ClassNotFoundException | SQLException e) {
				log.error("连接初始化错误！！" + e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
	}

	public boolean insert(String sql) {
		if (conn == null)
			init();
		Statement statement = null;
		int size = -1;
		try {
			statement = conn.createStatement();

			size = statement.executeUpdate(sql);

			conn.commit();

		} catch (SQLException e) {
			log.error("sql执行错误：" + e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("statement关闭错误：" + e.getLocalizedMessage());
					e.printStackTrace();
				}
			}
		}

		return size > 0;
	}

	public List<T> execute(String sql) {
		List<T> objects = new ArrayList<T>();

		if (conn == null)
			init();
		Statement statement = null;
		ResultSet rs = null;
		boolean isOk = false;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				objects.add(toObject(rs));
			}
		} catch (SQLException e) {
			log.error("sql执行错误：" + e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("statement关闭错误：" + e.getLocalizedMessage());
					e.printStackTrace();
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error("rs关闭错误：" + e.getLocalizedMessage());
					e.printStackTrace();
				}
			}
		}

		return objects;
	}

	public abstract T toObject(ResultSet rs);

	public abstract String getInsertSql();
	public boolean insertStr(List<String> qarEngDomains) {
		
		List<String> sqls = new ArrayList<String>();
		for (String str : qarEngDomains) {
			if (StringUtils.isNotBlank(str)) {
				String sql = getInsertSql() + " (" + str + ")";
				sqls.add(sql);
			}
		}
		int[] size = batchInsert(sqls);
		log.info("插入记录数：" + size);
		return true;
	
	}
	
	
	public int[] batchInsert(List<String> sqls) {
		if (conn == null)
			init();
		Statement statement = null;
		int[] res = null;
		try {
			statement = conn.createStatement();
			for (String sql : sqls) {
				statement.addBatch(sql);
			}
			res = statement.executeBatch();
			conn.commit();

		} catch (SQLException e) {
			log.error("sql执行错误：" + e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("statement关闭错误：" + e.getLocalizedMessage());
					e.printStackTrace();
				}
			}
		}

		return res;

	}

	public void close() {
		if (conn != null) {

			try {
				conn.close();

			} catch (SQLException e) {
				log.error("conn关闭错误：" + e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
	}

	public void testQuery(String sql) {

		Connection conn = null;
		 Statement ps = null;
		ResultSet rs = null;



		try {
			Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
			conn = DriverManager.getConnection(
					"jdbc:phoenix:10.92.1.129:2181:/hbase", "", "");
			
//			ps = conn.prepareStatement();
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery(sql);

			while (rs.next()) {
				System.out.println("test");
				System.out.println("1:" + rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public void testUpdate(String sql) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		
		try {
			Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
			
			conn = DriverManager.getConnection(
					"jdbc:phoenix:10.92.1.129:2181:/hbase", "", "");

			ps = conn.prepareStatement(sql);

			int size = -1;
			size = ps.executeUpdate();

			conn.commit();

			System.out.println("更新了" + size + "个");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

	}


	

	public static void main(String[] args) {
		
		AbstractHbaseDao dao = new FullPnrDao();
		// dao.init();
		// dao.insert("UPSERT INTO leisy values('1','leisy')");
		// dao.insert("UPSERT INTO MY_TABLE(org_id,entity_id,payload) values('1','2','3') ");

		String sql = "select date,plane_no,id from qardata where date between   '20150402' and '20150407' group by date,plane_no,id";

		sql = "SELECT * FROM  fullpnr.full_pnr_passenger_info  LIMIT 1000";

		sql = "SELECT * FROM  leisy1  LIMIT 1000";

		sql = "SELECT count(*) FROM  fullpnr.full_passenger_info ";
		
		String tests = "CREATE TABLE IF NOT EXISTS  leisy1 ("
				+ " org_id  char(50) not null , entity_id  char(50) not null , payload binary(1000) "
				+ " CONSTRAINT pk  PRIMARY KEY (org_id, entity_id) )  TTL=86400 ";

		tests = "create table if not exists fullpnr.full_pnr_passenger_info"
				+ "(pnr_ref  char(50) not null,pnr_crt_dt  char(50)  not null,pnr_grp_ind  char(5),pax_id  char(5) not null,pax_ful_nm  varchar(350),pax_cn_nm  varchar(200),vip_ind  char(1),pax_age_ctg_cd  char(1),seg_id  char(10),carr_cd  char(5),"
				+ "flt_nbr  char(4),flt_nbr_sfx  char(1),dpt_airpt_cd  char(3),arrv_airpt_cd  char(3),dpt_dt_lcl  char(8),dpt_dow char(1),dpt_tm_lcl char(8),arrv_dt_lcl char(8),arrv_tm_lcl char(8),air_seg_flt_typ char(1),sub_cls_cd char(2),opr_stat_cd char(2),stat_id char(10),indvl_id_typ_cd char(10),indvl_nbr char(60),cntry_cd char(15),indvl_bth_day char(50),gnd_cd char(10),vico_card char(6),tkt_type char(5),ctc_nbr char(11) CONSTRAINT pk  PRIMARY KEY (pnr_ref, pnr_crt_dt,pax_id))";

		tests = "UPSERT INTO fullpnr.full_pnr_passenger_info("
				+ "pnr_ref,pnr_crt_dt,pnr_grp_ind,pax_id,pax_ful_nm ,pax_cn_nm,vip_ind,pax_age_ctg_cd,seg_id,carr_cd,flt_nbr,flt_nbr_sfx,dpt_airpt_cd,arrv_airpt_cd,dpt_dt_lcl,dpt_dow,dpt_tm_lcl,arrv_dt_lcl,arrv_tm_lcl,air_seg_flt_typ,sub_cls_cd,opr_stat_cd,stat_id,indvl_id_typ_cd,indvl_nbr,cntry_cd,indvl_bth_day,gnd_cd,vico_card,tkt_type,ctc_nbr) "
				+ "values("
				+ "'MJT8DV','20151014','','2','LINJIERU','林洁茹a','','A','3','CZ','6589','','CAN','WUH','20161001','6','08:40:00','20161001','10:40:00','N','X','TK','0','I','440107197709030025','','','','','','')";

		tests = "select count(*) from fullpnr.full_pnr_passenger_info";
		
		// tests =
		// "ML0H0B,20140624,,1,ROSENBAUM/BERNARDO GABRIEL MR,BERNARDO GABRIEL,,,1,AF,4402,,CDG,CAN,20141010,5,12:25:00,20141011,05:55:00,N,D,HK,0,HK,S,AAB849433,AR,19630826,M,,,";
		// tests =
		// "ALTER TABLE fullpnr.full_pnr_passenger_info drop column CNTRY_CD ";
		// tests =
		// "ALTER TABLE fullpnr.full_pnr_passenger_info ADD   CNTRY_CD char(5)";
		//
		// tests = "CREATE TABLE IF NOT EXISTS  test.leisy2 ("+
		// " org_id  char(50) not null , entity_id  varchar(250) not null , payload binary(1000) "+
		// " CONSTRAINT pk  PRIMARY KEY (org_id, entity_id) )  TTL=86400 ";

		// tests =
		// "UPSERT INTO test.leisy3(org_id,entity_id) values('1','雷松云')";
		// tests = "DROP TABLE  fullpnr.full_pnr_passenger_info";
		// System.out.println(dao.insert(tests));
		// boolean isOK = dao.insert(tests);

		// System.out.println(isOK);
		// dao.test();
		// dao.testUpdate();
		// List list = dao.execute(tests);

		  sql = "UPSERT INTO fullpnr.full_pnr_passenger_info("
				+ "pnr_ref,pnr_crt_dt,pnr_grp_ind,pax_id,pax_ful_nm ,pax_cn_nm,vip_ind,pax_age_ctg_cd,seg_id,carr_cd,flt_nbr,flt_nbr_sfx,dpt_airpt_cd,arrv_airpt_cd,dpt_dt_lcl,dpt_dow,dpt_tm_lcl,arrv_dt_lcl,arrv_tm_lcl,air_seg_flt_typ,sub_cls_cd,opr_stat_cd,stat_id,indvl_id_typ_cd,indvl_nbr,cntry_cd,indvl_bth_day,gnd_cd,vico_card,tkt_type,ctc_nbr) "
				+ "values("
				+ "'MJT8DV','20151014','','2','LINJIERU','林洁茹a','','A','3','CZ','6589','','CAN','WUH','20161001','6','08:40:00','20161001','10:40:00','N','X','TK','0','I','440107197709030025','','','','','','')";
		sql = " DROP TABLE IF EXISTS fullpnr.full_pnr_passenger_info";

		sql = "CREATE TABLE IF NOT EXISTS  leisy ("
				+ " org_id  char(50) not null , entity_id  char(50) not null ,test1  char(50),test2  char(50), payload binary(1000) "
				+ " CONSTRAINT pk  PRIMARY KEY (org_id, entity_id) )  TTL=86400 ";

//		sql = " DROP TABLE IF EXISTS leisy";
		
		sql = "UPSERT INTO leisy(org_id,entity_id,test1,test2) values('2','leisy','sefw','ss')";
//
//		sql = " DROP TABLE IF EXISTS fullpnr.full_passenger_info";
//		
//		sql = "create table if not exists fullpnr.full_passenger_info"
//				+ "(pnr_ref  char(50) not null,pnr_crt_dt  char(50)  not null,pnr_grp_ind  char(5),pax_id  char(5) not null,pax_ful_nm  varchar(350),pax_cn_nm  varchar(200),vip_ind  char(1),pax_age_ctg_cd  char(1),seg_id  char(10),carr_cd  char(5),"
//				+ "flt_nbr  char(4),flt_nbr_sfx  char(1),dpt_airpt_cd  char(3),arrv_airpt_cd  char(3),dpt_dt_lcl  char(8),dpt_dow char(1),dpt_tm_lcl char(8),arrv_dt_lcl char(8),arrv_tm_lcl char(8),air_seg_flt_typ char(1),sub_cls_cd char(2),opr_stat_cd char(2),stat_id char(10),indvl_id_typ_cd char(10),indvl_nbr char(60),cntry_cd char(15),indvl_bth_day char(50),gnd_cd char(10),vico_card char(6),tkt_type char(5),ctc_nbr char(11) CONSTRAINT pk  PRIMARY KEY (pnr_ref, pnr_crt_dt,pax_id))";
//
//		sql = " DROP TABLE IF EXISTS fullpnr.full_pnr_passenger_info";
//		
//		sql = "UPSERT INTO fullpnr.full_passenger_info("
//				+ "pnr_ref,pnr_crt_dt,pnr_grp_ind,pax_id,pax_ful_nm ,pax_cn_nm,vip_ind,pax_age_ctg_cd,seg_id,carr_cd,flt_nbr,flt_nbr_sfx,dpt_airpt_cd,arrv_airpt_cd,dpt_dt_lcl,dpt_dow,dpt_tm_lcl,arrv_dt_lcl,arrv_tm_lcl,air_seg_flt_typ,sub_cls_cd,opr_stat_cd,stat_id,indvl_id_typ_cd,indvl_nbr,cntry_cd,indvl_bth_day,gnd_cd,vico_card,tkt_type,ctc_nbr) "
//				+ "values("
//				+ "'MJT8DV','20151014','','2','LINJIERU','林洁茹a','','A','3','CZ','6589','','CAN','WUH','20161001','6','08:40:00','20161001','10:40:00','N','X','TK','0','I','440107197709030025','','','','','','')";
//		
		sql = "create table if not EXISTS qar.qar_eng_file_y"
				+"(id INTEGER not null,tailNr char(10) not null,fltDt char(10) not null,engFileName char(50)"
				+",Time  char(20),IAS  INTEGER,CITY_FROM_R char(20),CITY_TO_R char(20),DATE_R char(10),EGT1 FLOAT,EGT2 FLOAT,EPR_ENG1_ACTUA FLOAT,EPR_ENG2_ACTUA FLOAT,EPR_ENG1 FLOAT,EPR_ENG2 FLOAT,FF1 FLOAT,FF2 FLOAT,FLIGHT_PHASE char(50),FLIGHT_NO1 char(20),FLIGHT_NO2 char(20),GS INTEGER,HEAD FLOAT,TAS FLOAT,MACH FLOAT,VRTG FLOAT,LATG FLOAT,LONG FLOAT,LONPC FLOAT,LATPC FLOAT,RALTC INTEGER ,ALT_STDC INTEGER,FQTY_TON FLOAT,TAT FLOAT,GW INTEGER,HEAD_TRUE FLOAT,DRIFT FLOAT ,ROLL FLOAT ,VREF_1 INTEGER,PITCH_1 FLOAT,FLT_PATH_ANGL FLOAT,WIN_SPD FLOAT,WIN_DIR FLOAT,APU_ON char(50),V2 INTEGER,V1 INTEGER,VR INTEGER,VREF_2 INTEGER,PITCH_2  FLOAT,AC_TYPE INTEGER,MACH_BUFF  FLOAT,PITCH_TRM FLOAT,PITCH_RATE FLOAT,HEAD_LIN FLOAT,FLAPC INTEGER,CTL_CL_FC_A_L FLOAT,CTL_CL_FC_B_L FLOAT,GLIDE_DEVC FLOAT,LOC_DEVC FLOAT,LDG_SELDW char(20),AP_EGD1 TINYINT,AP_EGD2 TINYINT,AP_OFF char(50),ATS_EGD char(50),IVV INTEGER,TLA1 FLOAT,TLA2 FLOAT,GPWS_MODE char(50),MAS_CAU char(50),IASC INTEGER,GSC INTEGER,EGT1C INTEGER,EGT2C INTEGER,OIP1 FLOAT,OIP2 FLOAT,AIL_QAD_POS_L FLOAT ,OIL_PRS1 FLOAT,OIL_PRS2 FLOAT,RAW_OIP1_A FLOAT,RAW_OIP1_B FLOAT,RAW_OIP2_A FLOAT,RAW_OIP2_B FLOAT,LDGC char(50),LDGL char(50),LDGNOS char(50),LDGR char(50),N11 FLOAT,N12 FLOAT,VIB_N11 FLOAT,VIB_N12 FLOAT,N21 FLOAT,N22 FLOAT,VIB_N21 FLOAT,VIB_N22 FLOAT,N31 FLOAT,N32 FLOAT,VIB_N31 FLOAT,VIB_N32 FLOAT,OIL_QTY1 FLOAT,OIL_QTY2 FLOAT,OIL_TMP1 FLOAT,OIL_TMP2 FLOAT,SATR FLOAT,DFC  INTEGER, ALT_STD_ISIS char(50) "
//				+",Time  char(20),IAS  UNSIGNED_SMALLINT,CITY_FROM_R char(20),CITY_TO_R char(20),DATE_R char(10),EGT1 FLOAT,EGT2 FLOAT,EPR_ENG1_ACTUA FLOAT,EPR_ENG2_ACTUA FLOAT,EPR_ENG1 FLOAT,EPR_ENG2 FLOAT,FF1 FLOAT,FF2 FLOAT,FLIGHT_PHASE char(50),FLIGHT_NO1 char(20),FLIGHT_NO2 char(20),GS INTEGER,HEAD FLOAT,TAS FLOAT,MACH FLOAT,VRTG FLOAT,LATG FLOAT,LONG FLOAT,LONPC FLOAT,LATPC FLOAT,RALTC UNSIGNED_INT ,ALT_STDC UNSIGNED_INT,FQTY_TON FLOAT,TAT FLOAT,GW UNSIGNED_INT,HEAD_TRUE FLOAT,DRIFT FLOAT ,ROLL FLOAT ,VREF_1 UNSIGNED_INT,PITCH_1 FLOAT,FLT_PATH_ANGL FLOAT,WIN_SPD FLOAT,WIN_DIR FLOAT,APU_ON char(50),V2 UNSIGNED_INT,V1 UNSIGNED_INT,VR UNSIGNED_INT,VREF_2 UNSIGNED_INT,PITCH_2  FLOAT,AC_TYPE UNSIGNED_INT,MACH_BUFF  FLOAT,PITCH_TRM FLOAT,PITCH_RATE FLOAT,HEAD_LIN FLOAT,FLAPC INTEGER,CTL_CL_FC_A_L FLOAT,CTL_CL_FC_B_L FLOAT,GLIDE_DEVC FLOAT,LOC_DEVC FLOAT,LDG_SELDW TINYINT,AP_EGD1 TINYINT,AP_EGD2 TINYINT,AP_OFF char(50),ATS_EGD char(50),IVV INTEGER,TLA1 FLOAT,TLA2 FLOAT,GPWS_MODE char(50),MAS_CAU char(50),IASC INTEGER,GSC INTEGER,EGT1C INTEGER,EGT2C INTEGER,OIP1 FLOAT,OIP2 FLOAT,AIL_QAD_POS_L FLOAT ,OIL_PRS1 FLOAT,OIL_PRS2 FLOAT,RAW_OIP1_A FLOAT,RAW_OIP1_B FLOAT,RAW_OIP2_A FLOAT,RAW_OIP2_B FLOAT,LDGC char(50),LDGL char(50),LDGNOS char(50),LDGR char(50),N11 FLOAT,N12 FLOAT,VIB_N11 FLOAT,VIB_N12 FLOAT,N21 FLOAT,N22 FLOAT,VIB_N21 FLOAT,VIB_N22 FLOAT,N31 FLOAT,N32 FLOAT,VIB_N31 FLOAT,VIB_N32 FLOAT,OIL_QTY1 FLOAT,OIL_QTY2 FLOAT,OIL_TMP1 FLOAT,OIL_TMP2 FLOAT,SATR FLOAT,DFC  INTEGER, ALT_STD_ISIS char(50),TIME_R char(20)"
				+"  CONSTRAINT pk  PRIMARY KEY (id, tailNr,fltDt) "
				+ ")";
//		sql = " UPSERT INTO  qar.qar_eng_file_y values(169311,B6625,2015-01-15,169311-B-6625-20150115,04:06:28,0,ZA,,00-00-00,1023.00,0.00,,,,,0,0,ENG. STOP,C,1,2,239.41,0.0,0.000,0.9885,0.008,0.018,0.0000,40.0709,0,-280,,,,231.86,0.00,-0.7,0,0.7,0.00,0.00,0.00,ON,0,0,0,0,0.70   0.70   0.70   0.70,0.0,0.000,,0.7025,239.41,0,,,0.0000,0.0000,,-,-,NOT VALID,-,0,-2.81,-2.81,NO WARNING,-,0,2,0,0,,,0,0.0,27.0,,,,,,GROUND,GROUND,GROUND,3.4,3.4,,,0.0,0.0,,,,,,,,,,,,3828)";
		
//		sql = "ALTER TABLE qar.qar_eng_file_y SET   ALT_STDC INTEGER";
//		
//		sql = "ALTER TABLE qar.qar_eng_file_y SET   ALT_STDC INTEGER";
		
//		sql = "create table if not EXISTS qar.qartempeng"
//				+ "(id UNSIGNED_INT not null,tailNr char(10) not null,fltDt char(10) not null "
//				+ "CONSTRAINT pk  PRIMARY KEY (id, tailNr,fltDt)"
//				+ ")";
		
//		sql = " DROP TABLE IF EXISTS qar.qar_eng_file_y";
		
		sql = "UPSERT INTO qar.qar_eng_file_y(id,tailNr,fltDt,engFileName,Time,IAS,CITY_FROM_R,CITY_TO_R,DATE_R,EGT1,EGT2,EPR_ENG1_ACTUA,EPR_ENG2_ACTUA,EPR_ENG1,EPR_ENG2,FF1,FF2,FLIGHT_PHASE,FLIGHT_NO1,FLIGHT_NO2,GS,HEAD,TAS,MACH,VRTG,LATG,LONG,LONPC,LATPC,RALTC,ALT_STDC,FQTY_TON,TAT,GW,HEAD_TRUE,DRIFT,ROLL,VREF_1,PITCH_1,FLT_PATH_ANGL,WIN_SPD,WIN_DIR,APU_ON,V2,V1,VR,VREF_2,PITCH_2,AC_TYPE,MACH_BUFF,PITCH_TRM,PITCH_RATE,HEAD_LIN,FLAPC,CTL_CL_FC_A_L,CTL_CL_FC_B_L,GLIDE_DEVC,LOC_DEVC,LDG_SELDW,AP_EGD1,AP_EGD2,AP_OFF,ATS_EGD,IVV,TLA1,TLA2,GPWS_MODE,MAS_CAU,IASC,GSC,EGT1C,EGT2C,OIP1,OIP2,AIL_QAD_POS_L,OIL_PRS1,OIL_PRS2,RAW_OIP1_A,RAW_OIP1_B,RAW_OIP2_A,RAW_OIP2_B,LDGC,LDGL,LDGNOS,LDGR,N11,N12,VIB_N11,VIB_N12,N21,N22,VIB_N21,VIB_N22,N31,N32,VIB_N31,VIB_N32,OIL_QTY1,OIL_QTY2,OIL_TMP1,OIL_TMP2,SATR,DFC,ALT_STD_ISIS) values  "
				 +"(169311,'B6625','2015-01-15','169311-B-6625-20150115','04:10:54',0,'ZBAA','ZGHA','15-01-15',425.0,443.0,0.0,0.0,0.0,0.0,434.0,464.0,'TAXI OUT','CSN3','14X',22,359.3,0.0,0.0,0.99074996,-0.01075,0.0,116.5993,40.0738,0,-276,0.0,0.0,0,352.79,0.0,0.35,0,-0.35,0.0,0.0,0.0,'CLOSED',159,0,159,0,-0.35,0,0.0,0.0,0.0,-0.7,0,0.0,0.0,0.0,0.0,'DOWN',0,0,'NOT VALID','-',-38,-2.81,-2.81,'NO WARNING','-',0,22,425,443,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,'','GROUND','GROUND','GROUND',21.6,22.4,0.0,0.0,57.8,58.9,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,66,'')";
		
		sql = "select count(*) from qar.qar_eng_file_y";
		
				 dao.testQuery(sql);
//		dao.testUpdate(sql);

		dao.close();

	}

}
