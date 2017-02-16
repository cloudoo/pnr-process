package com.csair.loong.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractHbaseDao<T> {
	private static final Logger log = LoggerFactory
			.getLogger(AbstractHbaseDao.class);
	protected Connection conn;
	static final String JDBC_DRIVER_CLASS = "org.apache.phoenix.jdbc.PhoenixDriver";
	// static final String JDBC_URL = "jdbc:phoenix:10.95.68.71:2181:/hbase";
	static final String JDBC_URL = "jdbc:phoenix:Y-BI-Cn-T1:2181:/hbase";

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

	abstract T toObject(ResultSet rs);

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

	public void testQuery() {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "select date,plane_no,id from qardata where date between   '20150402' and '20150407' group by date,plane_no,id";

		sql = "SELECT * FROM  fullpnr.full_pnr_passenger_info  LIMIT 1000";

		sql = "SELECT * FROM  leisy1  LIMIT 1000";

		sql = "SELECT count(*) FROM  fullpnr.full_passenger_info ";

		try {
			Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
			conn = DriverManager.getConnection(
					"jdbc:phoenix:10.92.1.129:2181:/hbase", "", "");
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

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

	public void testUpdate() {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "UPSERT INTO fullpnr.full_pnr_passenger_info("
				+ "pnr_ref,pnr_crt_dt,pnr_grp_ind,pax_id,pax_ful_nm ,pax_cn_nm,vip_ind,pax_age_ctg_cd,seg_id,carr_cd,flt_nbr,flt_nbr_sfx,dpt_airpt_cd,arrv_airpt_cd,dpt_dt_lcl,dpt_dow,dpt_tm_lcl,arrv_dt_lcl,arrv_tm_lcl,air_seg_flt_typ,sub_cls_cd,opr_stat_cd,stat_id,indvl_id_typ_cd,indvl_nbr,cntry_cd,indvl_bth_day,gnd_cd,vico_card,tkt_type,ctc_nbr) "
				+ "values("
				+ "'MJT8DV','20151014','','2','LINJIERU','林洁茹a','','A','3','CZ','6589','','CAN','WUH','20161001','6','08:40:00','20161001','10:40:00','N','X','TK','0','I','440107197709030025','','','','','','')";
		sql = " DROP TABLE IF EXISTS fullpnr.full_pnr_passenger_info";

		sql = "CREATE TABLE IF NOT EXISTS  leisy1 ("
				+ " org_id  char(50) not null , entity_id  char(50) not null , payload binary(1000) "
				+ " CONSTRAINT pk  PRIMARY KEY (org_id, entity_id) )  TTL=86400 ";

		sql = "UPSERT INTO leisy1(org_id,entity_id) values('1','leisy')";

		sql = " DROP TABLE IF EXISTS fullpnr.full_passenger_info";
		
		sql = "create table if not exists fullpnr.full_passenger_info"
				+ "(pnr_ref  char(50) not null,pnr_crt_dt  char(50)  not null,pnr_grp_ind  char(5),pax_id  char(5) not null,pax_ful_nm  varchar(350),pax_cn_nm  varchar(200),vip_ind  char(1),pax_age_ctg_cd  char(1),seg_id  char(10),carr_cd  char(5),"
				+ "flt_nbr  char(4),flt_nbr_sfx  char(1),dpt_airpt_cd  char(3),arrv_airpt_cd  char(3),dpt_dt_lcl  char(8),dpt_dow char(1),dpt_tm_lcl char(8),arrv_dt_lcl char(8),arrv_tm_lcl char(8),air_seg_flt_typ char(1),sub_cls_cd char(2),opr_stat_cd char(2),stat_id char(10),indvl_id_typ_cd char(10),indvl_nbr char(60),cntry_cd char(15),indvl_bth_day char(50),gnd_cd char(10),vico_card char(6),tkt_type char(5),ctc_nbr char(11) CONSTRAINT pk  PRIMARY KEY (pnr_ref, pnr_crt_dt,pax_id))";

		sql = " DROP TABLE IF EXISTS fullpnr.full_pnr_passenger_info";
		
		sql = "UPSERT INTO fullpnr.full_passenger_info("
				+ "pnr_ref,pnr_crt_dt,pnr_grp_ind,pax_id,pax_ful_nm ,pax_cn_nm,vip_ind,pax_age_ctg_cd,seg_id,carr_cd,flt_nbr,flt_nbr_sfx,dpt_airpt_cd,arrv_airpt_cd,dpt_dt_lcl,dpt_dow,dpt_tm_lcl,arrv_dt_lcl,arrv_tm_lcl,air_seg_flt_typ,sub_cls_cd,opr_stat_cd,stat_id,indvl_id_typ_cd,indvl_nbr,cntry_cd,indvl_bth_day,gnd_cd,vico_card,tkt_type,ctc_nbr) "
				+ "values("
				+ "'MJT8DV','20151014','','2','LINJIERU','林洁茹a','','A','3','CZ','6589','','CAN','WUH','20161001','6','08:40:00','20161001','10:40:00','N','X','TK','0','I','440107197709030025','','','','','','')";

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

		 dao.testQuery();
//		dao.testUpdate();

		dao.close();

	}

}
