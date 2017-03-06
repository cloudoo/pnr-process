package com.csair.loong.qar.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.csair.loong.dao.AbstractHbaseDao;
import com.csair.loong.domain.FullPassengerInfo;
import com.csair.loong.qar.domain.QarEngDomain;

public class QarEngDao extends AbstractHbaseDao<QarEngDomain> {

	
	public static final Logger log = LoggerFactory.getLogger(QarEngDao.class);
	
	private JdbcTemplate jdbcTemplate;
	
	
	public QarEngDao(){
		
	}
	public QarEngDao(String jdbcUrl,String driverClass){
			this.JDBC_DRIVER_CLASS = driverClass;
			this.JDBC_URL = jdbcUrl;
	}
	/**
	 * 
	 * 	sql = "create table if not EXISTS qar.qar_eng_file_y"
			+"(id UNSIGNED_INT not null,tailNr char(10) not null,fltDt char(10) not null,engFileName char(50)"
			+",Time  char(20),IAS  UNSIGNED_SMALLINT,CITY_FROM_R char(20),CITY_TO_R char(20),DATE_R char(10),EGT1 FLOAT,EGT2 FLOAT,EPR_ENG1_ACTUA FLOAT,EPR_ENG2_ACTUA FLOAT,EPR_ENG1 FLOAT,EPR_ENG2 FLOAT,FF1 FLOAT,FF2 FLOAT,FLIGHT_PHASE char(50),FLIGHT_NO1 char(20),FLIGHT_NO2 char(20),GS INTEGER,HEAD FLOAT,TAS FLOAT,MACH FLOAT,VRTG FLOAT,LATG FLOAT,LONG FLOAT,LONPC FLOAT,LATPC FLOAT,RALTC UNSIGNED_INT ,ALT_STDC UNSIGNED_INT,FQTY_TON FLOAT,TAT FLOAT,GW UNSIGNED_INT,HEAD_TRUE FLOAT,DRIFT FLOAT ,ROLL FLOAT ,VREF_1 UNSIGNED_INT,PITCH_1 FLOAT,FLT_PATH_ANGL FLOAT,WIN_SPD FLOAT,WIN_DIR FLOAT,APU_ON char(50),V2 UNSIGNED_INT,V1 UNSIGNED_INT,VR UNSIGNED_INT,VREF_2 UNSIGNED_INT,PITCH_2  FLOAT,AC_TYPE UNSIGNED_INT,MACH_BUFF  FLOAT,PITCH_TRM FLOAT,PITCH_RATE FLOAT,HEAD_LIN FLOAT,FLAPC INTEGER,CTL_CL_FC_A_L FLOAT,CTL_CL_FC_B_L FLOAT,GLIDE_DEVC FLOAT,LOC_DEVC FLOAT,LDG_SELDW TINYINT,AP_EGD1 TINYINT,AP_EGD2 TINYINT,AP_OFF char(50),ATS_EGD char(50),IVV INTEGER,TLA1 FLOAT,TLA2 FLOAT,GPWS_MODE char(50),MAS_CAU char(50),IASC INTEGER,GSC INTEGER,EGT1C INTEGER,EGT2C INTEGER,OIP1 FLOAT,OIP2 FLOAT,AIL_QAD_POS_L FLOAT ,OIL_PRS1 FLOAT,OIL_PRS2 FLOAT,RAW_OIP1_A FLOAT,RAW_OIP1_B FLOAT,RAW_OIP2_A FLOAT,RAW_OIP2_B FLOAT,LDGC char(50),LDGL char(50),LDGNOS char(50),LDGR char(50),N11 FLOAT,N12 FLOAT,VIB_N11 FLOAT,VIB_N12 FLOAT,N21 FLOAT,N22 FLOAT,VIB_N21 FLOAT,VIB_N22 FLOAT,N31 FLOAT,N32 FLOAT,VIB_N31 FLOAT,VIB_N32 FLOAT,OIL_QTY1 FLOAT,OIL_QTY2 FLOAT,OIL_TMP1 FLOAT,OIL_TMP2 FLOAT,SATR FLOAT,DFC  INTEGER, ALT_STD_ISIS char(50),TIME_R char(20) "
			+"  CONSTRAINT pk  PRIMARY KEY (id, tailNr,fltDt) "
			+ ")";
	 * 
	 * 
	 */
	
	public String insert_sql = "UPSERT INTO qar.qar_eng_file_y("
			+ "id,tailNr,fltDt,engFileName,Time,IAS,CITY_FROM_R,CITY_TO_R,DATE_R,EGT1,EGT2,EPR_ENG1_ACTUA,EPR_ENG2_ACTUA,EPR_ENG1,EPR_ENG2,FF1,FF2,FLIGHT_PHASE,FLIGHT_NO1,FLIGHT_NO2,GS,HEAD,TAS,MACH,VRTG,LATG,LONG,LONPC,LATPC,RALTC,ALT_STDC,FQTY_TON,TAT,GW,HEAD_TRUE,DRIFT,ROLL,VREF_1,PITCH_1,FLT_PATH_ANGL,WIN_SPD,WIN_DIR,APU_ON,V2,V1,VR,VREF_2,PITCH_2,AC_TYPE,MACH_BUFF,PITCH_TRM,PITCH_RATE,HEAD_LIN,FLAPC,CTL_CL_FC_A_L,CTL_CL_FC_B_L,GLIDE_DEVC,LOC_DEVC,LDG_SELDW,AP_EGD1,AP_EGD2,AP_OFF,ATS_EGD,IVV,TLA1,TLA2,GPWS_MODE,MAS_CAU,IASC,GSC,EGT1C,EGT2C,OIP1,OIP2,AIL_QAD_POS_L,OIL_PRS1,OIL_PRS2,RAW_OIP1_A,RAW_OIP1_B,RAW_OIP2_A,RAW_OIP2_B,LDGC,LDGL,LDGNOS,LDGR,N11,N12,VIB_N11,VIB_N12,N21,N22,VIB_N21,VIB_N22,N31,N32,VIB_N31,VIB_N32,OIL_QTY1,OIL_QTY2,OIL_TMP1,OIL_TMP2,SATR,DFC,ALT_STD_ISIS"
			+ ") values ";
	

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public boolean has(QarEngDomain qarEngDomain) {

		String sql = insert_sql + " (" + qarEngDomain.toSql() + ")";

		return insert(sql);
	}

	public boolean insert(List<QarEngDomain> qarEngs) {

		List<String> sqls = new ArrayList<String>();
		for (QarEngDomain qarEng : qarEngs) {
			String sql = insert_sql + " (" + qarEng.toSql() + ")";
			sqls.add(sql);
		}

		int[] size = batchInsert(sqls);

		log.info("插入记录数：" + size.toString());

		return true;
	}

	public boolean insert(QarEngDomain fullPsgInfo) {

		String sql = insert_sql + " (" + fullPsgInfo.toSql() + ")";

		return insert(sql);
		
	}


	@Override
	public QarEngDomain toObject(ResultSet rs) {
		
		return null;
	}


	@Override
	public String getInsertSql() {
		return this.insert_sql;
	}

 

	
	
}
