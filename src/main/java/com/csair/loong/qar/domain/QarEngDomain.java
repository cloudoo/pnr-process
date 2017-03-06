package com.csair.loong.qar.domain;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.loong.qar.dao.QarEngDao;

public class QarEngDomain {
	public static final Logger log = LoggerFactory
			.getLogger(QarEngDomain.class);
	// 所有的字段名
	// Time,IAS,CITY_FROM_R,CITY_TO_R,DATE_R,EGT1,EGT2,EPR_ENG1_ACTUA,EPR_ENG2_ACTUA,EPR_ENG1,EPR_ENG2,FF1,FF2,FLIGHT_PHASE,FLIGHT_NO1,FLIGHT_NO2,GS,HEAD,TAS,MACH,VRTG,LATG,LONG,LONPC,LATPC,RALTC,ALT_STDC,FQTY_TON,TAT,GW,HEAD_TRUE,DRIFT,ROLL,VREF,PITCH,FLT_PATH_ANGL,WIN_SPD,WIN_DIR,APU_ON,V2,V1,VR,VREF,PITCH,AC_TYPE,MACH_BUFF,PITCH_TRM,PITCH_RATE,HEAD_LIN,FLAPC,CTL_CL_FC_A_L,CTL_CL_FC_B_L,GLIDE_DEVC,LOC_DEVC,LDG_SELDW,AP_EGD1,AP_EGD2,AP_OFF,ATS_EGD,IVV,TLA1,TLA2,GPWS_MODE,MAS_CAU,IASC,GSC,EGT1C,EGT2C,OIP1,OIP2,AIL_QAD_POS_L,OIL_PRS1,OIL_PRS2,RAW_OIP1_A,RAW_OIP1_B,RAW_OIP2_A,RAW_OIP2_B,LDGC,LDGL,LDGNOS,LDGR,N11,N12,VIB_N11,VIB_N12,N21,N22,VIB_N21,VIB_N22,N31,N32,VIB_N31,VIB_N32,OIL_QTY1,OIL_QTY2,OIL_TMP1,OIL_TMP2,SATR,DFC,ALT_STD_ISIS

	public long id;
	public String tailnr;
	public String fltdt;
	public String engfilename;
	public String time;// 1
	public int ias;
	public String cityFromR;
	public String cityToR;
	public String dateR;
	public float egt1;
	public float egt2;
	public float eprEng1Actua;
	public float eprEng2Actua;
	public float eprEng1;// 10
	public float eprEng2;
	public float ff1;
	public float ff2;
	public String flightPhase;
	public String flightNo1;
	public String flightNo2;
	public int gs;
	public float head;
	public float tas;
	public float mach;
	public float vrtg;
	public float latg;
	public float longit;
	public float lonpc;
	public float latpc;
	public float raltc;// 28
	public float altStdc;// 29
	public float fqtyTon;// 30
	public float tat;
	public int gw;
	public float headTrue;
	public float drift;
	public float roll;
	public int vref_1;
	public float pitch_1;
	public float fltPathAngl;
	public float winSpd;
	public float winDir;
	public String apuOn;
	public int v2;
	public int v1;
	public int vr;
	public int vref_2;
	public float pitch_2;
	public int acType;
	public float machBuff;
	public float pitchTrm;
	public float pitchRate;
	public float headLin;
	public int flapc;
	public float ctlClFcAL;
	public float ctlClFcBL;
	public float glideDevc;
	public float locDevc;
	public String ldgSeldw;
	public int apEgd1;
	public int apEgd2;
	public String apOff;
	public String atsEgd;
	public int ivv;
	public float tla1;
	public float tla2;
	public String gpwsMode;
	public String masCau;
	public int iasc;
	public int gsc;
	public int egt1c;
	public int egt2c;
	public float oip1;
	public float oip2;
	public float ailQadPosL;
	public float oilPrs1;
	public float oilPrs2;
	public float rawOip1A;
	public float rawOip1B;
	public float rawOip2A;
	public float rawOip2B;
	public String ldgc;
	public String ldgl;
	public String ldgnos;
	public String ldgr;
	public float n11;
	public float n12;
	public float vibN11;
	public float vibN12;
	public float n21;
	public float n22;
	public float vibN21;
	public float vibN22;
	public float n31;
	public float n32;
	public float vibN31;
	public float vibN32;
	public float oilQty1;
	public float oilQty2;
	public float oilTmp1;
	public float oilTmp2;
	public float satr;
	public int dfc;
	public String altStdIsis = "";

	public void initByStrings(String lines) {

//		String[] tempLine = lines.split(",");
//
//		this.id = Long.parseLong(tempLine[0]);
//		this.tailnr = tempLine[1];
//		this.fltdt = tempLine[2];
//		this.engfilename = tempLine[3];
//		this.time = tempLine[4];
//		this.ias = parseInt(tempLine[5]);
//		this.cityFromR = tempLine[6];
//		this.cityToR = tempLine[7];
//		this.dateR = tempLine[8];
//		this.egt1 = parseFloat(tempLine[9]);
//		this.egt2 = parseFloat(tempLine[10]);
//		this.eprEng1Actua = parseFloat(tempLine[11]);
//		this.eprEng2Actua = parseFloat(tempLine[12]);
//		this.eprEng1 = parseFloat(tempLine[13]);
//		this.eprEng2 = parseFloat(tempLine[14]);
//		this.ff1 = parseFloat(tempLine[15]);
//		this.ff2 = parseFloat(tempLine[16]);
//		this.flightPhase = tempLine[17];
//		this.flightNo1 = tempLine[18];
//		this.flightNo2 = tempLine[19];
//		this.gs = parseInt(tempLine[20]);
//		this.head = parseFloat(tempLine[21]);
//		this.tas = parseFloat(tempLine[22]);
//		this.mach = parseFloat(tempLine[23]);
//		this.vrtg = parseFloat(tempLine[24]);
//		this.latg = parseFloat(tempLine[25]);
//		this.longit = parseFloat(tempLine[26]);
//		this.lonpc = parseFloat(tempLine[27]);
//		this.latpc = parseFloat(tempLine[28]);
//		this.raltc = parseInt(tempLine[29]);
//		this.altStdc = parseInt(tempLine[30]);
//		this.fqtyTon = parseFloat(tempLine[31]);
//		this.tat = parseFloat(tempLine[32]);
//		this.gw = parseInt(tempLine[33]);
//		this.headTrue = parseFloat(tempLine[34]);
//		this.drift = parseFloat(tempLine[35]);
//		this.roll = parseFloat(tempLine[36]);
//		this.vref_1 = parseInt(tempLine[37]);
//		this.pitch_1 = parseFloat(tempLine[38]);
//		this.fltPathAngl = parseFloat(tempLine[39]);
//		this.winSpd = parseFloat(tempLine[40]);
//		this.winDir = parseFloat(tempLine[41]);
//		this.apuOn = tempLine[42];
//		this.v2 = parseInt(tempLine[43]);
//		this.v1 = parseInt(tempLine[44]);
//		this.vr = parseInt(tempLine[45]);
//		this.vref_2 = parseInt(tempLine[46]);
//		this.pitch_2 = parseFloat(tempLine[47]);
//		this.acType = parseInt(tempLine[48]);
//		this.machBuff = parseFloat(tempLine[49]);
//		this.pitchTrm = parseFloat(tempLine[50]);
//		this.pitchRate = parseFloat(tempLine[51]);
//		this.headLin = parseFloat(tempLine[52]);
//		this.flapc = parseInt(tempLine[53]);
//		this.ctlClFcAL = parseFloat(tempLine[54]);
//		this.ctlClFcBL = parseFloat(tempLine[55]);
//		this.glideDevc = parseFloat(tempLine[56]);
//		this.locDevc = parseFloat(tempLine[57]);
//		this.ldgSeldw = tempLine[58];
//		this.apEgd1 = parseInt(tempLine[59]);
//		this.apEgd2 = parseInt(tempLine[60]);
//		this.apOff = (tempLine[61]);
//		this.atsEgd = (tempLine[62]);
//		this.ivv = parseInt(tempLine[63]);
//		this.tla1 = parseFloat(tempLine[64]);
//		this.tla2 = parseFloat(tempLine[65]);
//		this.gpwsMode = (tempLine[66]);
//		this.masCau = (tempLine[67]);
//		this.iasc = parseInt(tempLine[68]);
//		this.gsc = parseInt(tempLine[69]);
//		this.egt1c = parseInt(tempLine[70]);
//		this.egt2c = parseInt(tempLine[71]);
//		this.oip1 = parseFloat(tempLine[72]);
//		this.oip2 = parseFloat(tempLine[73]);
//		this.ailQadPosL = parseFloat(tempLine[74]);
//		this.oilPrs1 = parseFloat(tempLine[75]);
//		this.oilPrs2 = parseFloat(tempLine[76]);
//		this.rawOip1A = parseFloat(tempLine[77]);
//		this.rawOip1B = parseFloat(tempLine[78]);
//		this.rawOip2A = parseFloat(tempLine[79]);
//		this.rawOip2B = parseFloat(tempLine[80]);
//		this.ldgc = (tempLine[81]);
//		this.ldgl = (tempLine[82]);
//		this.ldgnos = (tempLine[83]);
//		this.ldgr = (tempLine[84]);
//		this.n11 = parseFloat(tempLine[85]);
//		this.n12 = parseFloat(tempLine[86]);
//		this.vibN11 = parseFloat(tempLine[87]);
//		this.vibN12 = parseFloat(tempLine[88]);
//		this.n21 = parseFloat(tempLine[89]);
//		this.n22 = parseFloat(tempLine[90]);
//		this.vibN21 = parseFloat(tempLine[91]);
//		this.vibN22 = parseFloat(tempLine[92]);
//		this.n31 = parseFloat(tempLine[93]);
//		this.n32 = parseFloat(tempLine[94]);
//		this.vibN31 = parseFloat(tempLine[95]);
//		this.vibN32 = parseFloat(tempLine[96]);
//		this.oilQty1 = parseFloat(tempLine[97]);
//		this.oilQty2 = parseFloat(tempLine[98]);
//		this.oilTmp1 = parseFloat(tempLine[99]);
//		this.oilTmp2 = parseFloat(tempLine[100]);
//		this.satr = parseFloat(tempLine[101]);
//		this.dfc = parseInt(tempLine[102]);
//		if (tempLine.length >= 104)
//			this.altStdIsis = tempLine[103];

	}



	public String toString(){
		
		return id + "," + tailnr + "," + fltdt + "," + engfilename + ","
				+ time + "," + ias + "," + cityFromR + "," + cityToR
				+ "," + dateR + "," + egt1 + "," + egt2 + "," + eprEng1Actua
				+ "," + eprEng2Actua + "," + eprEng1 + "," + eprEng2 + ","
				+ ff1 + "," + ff2 + "," + flightPhase + "," + flightNo1
				+ "," + flightNo2 + "," + gs + "," + head + "," + tas + ","
				+ mach + "," + vrtg + "," + latg + "," + longit + "," + lonpc
				+ "," + latpc + "," + raltc + "," + altStdc + "," + fqtyTon
				+ "," + tat + "," + gw + "," + headTrue + "," + drift + ","
				+ roll + "," + vref_1 + "," + pitch_1 + "," + fltPathAngl + ","
				+ winSpd + "," + winDir + "," + apuOn + "," + v2 + "," + v1
				+ "," + vr + "," + vref_2 + "," + pitch_2 + "," + acType + ","
				+ machBuff + "," + pitchTrm + "," + pitchRate + "," + headLin
				+ "," + flapc + "," + ctlClFcAL + "," + ctlClFcBL + ","
				+ glideDevc + "," + locDevc + "," + ldgSeldw + "," + apEgd1
				+ "," + apEgd2 + "," + apOff + "," + atsEgd + "," + ivv
				+ "," + tla1 + "," + tla2 + "," + gpwsMode + "," + masCau
				+ "," + iasc + "," + gsc + "," + egt1c + "," + egt2c + ","
				+ oip1 + "," + oip2 + "," + ailQadPosL + "," + oilPrs1 + ","
				+ oilPrs2 + "," + rawOip1A + "," + rawOip1B + "," + rawOip2A
				+ "," + rawOip2B + "," + ldgc + "," + ldgl + "," + ldgnos
				+ "," + ldgr + "," + n11 + "," + n12 + "," + vibN11 + ","
				+ vibN12 + "," + n21 + "," + n22 + "," + vibN21 + "," + vibN22
				+ "," + n31 + "," + n32 + "," + vibN31 + "," + vibN32 + ","
				+ oilQty1 + "," + oilQty2 + "," + oilTmp1 + "," + oilTmp2 + ","
				+ satr + "," + dfc + "," + altStdIsis;
	}
	
	public String toSql() {

		return id + ",'" + tailnr + "','" + fltdt + "','" + engfilename + "','"
				+ time + "'," + ias + ",'" + cityFromR + "','" + cityToR
				+ "','" + dateR + "'," + egt1 + "," + egt2 + "," + eprEng1Actua
				+ "," + eprEng2Actua + "," + eprEng1 + "," + eprEng2 + ","
				+ ff1 + "," + ff2 + ",'" + flightPhase + "','" + flightNo1
				+ "','" + flightNo2 + "'," + gs + "," + head + "," + tas + ","
				+ mach + "," + vrtg + "," + latg + "," + longit + "," + lonpc
				+ "," + latpc + "," + raltc + "," + altStdc + "," + fqtyTon
				+ "," + tat + "," + gw + "," + headTrue + "," + drift + ","
				+ roll + "," + vref_1 + "," + pitch_1 + "," + fltPathAngl + ","
				+ winSpd + "," + winDir + ",'" + apuOn + "'," + v2 + "," + v1
				+ "," + vr + "," + vref_2 + "," + pitch_2 + "," + acType + ","
				+ machBuff + "," + pitchTrm + "," + pitchRate + "," + headLin
				+ "," + flapc + "," + ctlClFcAL + "," + ctlClFcBL + ","
				+ glideDevc + "," + locDevc + ",'" + ldgSeldw + "'," + apEgd1
				+ "," + apEgd2 + ",'" + apOff + "','" + atsEgd + "'," + ivv
				+ "," + tla1 + "," + tla2 + ",'" + gpwsMode + "','" + masCau
				+ "'," + iasc + "," + gsc + "," + egt1c + "," + egt2c + ","
				+ oip1 + "," + oip2 + "," + ailQadPosL + "," + oilPrs1 + ","
				+ oilPrs2 + "," + rawOip1A + "," + rawOip1B + "," + rawOip2A
				+ "," + rawOip2B + ",'" + ldgc + "','" + ldgl + "','" + ldgnos
				+ "','" + ldgr + "'," + n11 + "," + n12 + "," + vibN11 + ","
				+ vibN12 + "," + n21 + "," + n22 + "," + vibN21 + "," + vibN22
				+ "," + n31 + "," + n32 + "," + vibN31 + "," + vibN32 + ","
				+ oilQty1 + "," + oilQty2 + "," + oilTmp1 + "," + oilTmp2 + ","
				+ satr + "," + dfc + ",'" + altStdIsis + "'";
	}
}
