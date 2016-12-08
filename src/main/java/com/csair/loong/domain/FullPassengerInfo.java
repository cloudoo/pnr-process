package com.csair.loong.domain;

import java.io.Serializable;

/**
 * 
 * 针对文档<FULL PNR 数据导入规则v1.2.doc>处理规则 Created by cloudoo on 2016/9/18.
 * 
 */
public class FullPassengerInfo implements Serializable {

	// 001
	private String pnrRef;//
	private String pnrCrtDt;//
	private String pnrGrpInd;
	// 003
	private String paxId;//
	private String paxFulNm;
	private String paxCnNm;
	private String vipInd;//
	private String paxAgeCtgCd;//
	// 004
	private String segId;//
	private String carrCd;//
	private String fltNbr;//--10
	private String fltNbrSfx;//
	private String dptAirptCd;//
	private String arrvAirptCd;//
	private String dptDtLcl;//
	private String dptDow;//
	private String dptTmLcl;//
	private String arrvDtLcl;//
	private String arrvTmLcl;//
	private String airSegFltTyp;//
	private String subClsCd;//--20
	private String oprStatCd;//
	// 005
	private int statId;//
	// private String oprStatCd;//
	// 010
	private String indvlIdTypCd;//--23
	private String indvlNbr;//
	private String cntryCd;//
	private String indvlBthDay;//
	private String gndCd;//
	// 015
	private String vicoCard;//
	private String tktType;//
	// 016
	private String ctcNbr;//

	public String getPnrRef() {
		return pnrRef;
	}

	public void setPnrRef(String pnrRef) {
		this.pnrRef = pnrRef;
	}

	public String getPnrCrtDt() {
		return pnrCrtDt;
	}

	public void setPnrCrtDt(String pnrCrtDt) {
		this.pnrCrtDt = pnrCrtDt;
	}

	public String getPnrGrpInd() {
		return pnrGrpInd;
	}

	public void setPnrGrpInd(String pnrGrpInd) {
		this.pnrGrpInd = pnrGrpInd;
	}

	public String getPaxId() {
		return paxId;
	}

	public void setPaxId(String paxId) {
		this.paxId = paxId;
	}

	public String getPaxFulNm() {
		return paxFulNm;
	}

	public void setPaxFulNm(String paxFulNm) {
		this.paxFulNm = paxFulNm;
	}

	public String getPaxCnNm() {
		return paxCnNm;
	}

	public void setPaxCnNm(String paxCnNm) {
		this.paxCnNm = paxCnNm;
	}

	public String getVipInd() {
		return vipInd;
	}

	public void setVipInd(String vipInd) {
		this.vipInd = vipInd;
	}

	public String getPaxAgeCtgCd() {
		return paxAgeCtgCd;
	}

	public void setPaxAgeCtgCd(String paxAgeCtgCd) {
		this.paxAgeCtgCd = paxAgeCtgCd;
	}

	public String getSegId() {
		return segId;
	}

	public void setSegId(String segId) {
		this.segId = segId;
	}

	public String getCarrCd() {
		return carrCd;
	}

	public void setCarrCd(String carrCd) {
		this.carrCd = carrCd;
	}

	public String getFltNbr() {
		return fltNbr;
	}

	public void setFltNbr(String fltNbr) {
		this.fltNbr = fltNbr;
	}

	public String getFltNbrSfx() {
		return fltNbrSfx;
	}

	public void setFltNbrSfx(String fltNbrSfx) {
		this.fltNbrSfx = fltNbrSfx;
	}

	public String getDptAirptCd() {
		return dptAirptCd;
	}

	public void setDptAirptCd(String dptAirptCd) {
		this.dptAirptCd = dptAirptCd;
	}

	public String getArrvAirptCd() {
		return arrvAirptCd;
	}

	public void setArrvAirptCd(String arrvAirptCd) {
		this.arrvAirptCd = arrvAirptCd;
	}

	public String getDptDtLcl() {
		return dptDtLcl;
	}

	public void setDptDtLcl(String dptDtLcl) {
		this.dptDtLcl = dptDtLcl;
	}

	public String getDptDow() {
		return dptDow;
	}

	public void setDptDow(String dptDow) {
		this.dptDow = dptDow;
	}

	public String getDptTmLcl() {
		return dptTmLcl;
	}

	public void setDptTmLcl(String dptTmLcl) {
		this.dptTmLcl = dptTmLcl;
	}

	public String getArrvDtLcl() {
		return arrvDtLcl;
	}

	public void setArrvDtLcl(String arrvDtLcl) {
		this.arrvDtLcl = arrvDtLcl;
	}

	public String getArrvTmLcl() {
		return arrvTmLcl;
	}

	public void setArrvTmLcl(String arrvTmLcl) {
		this.arrvTmLcl = arrvTmLcl;
	}

	public String getAirSegFltTyp() {
		return airSegFltTyp;
	}

	public void setAirSegFltTyp(String airSegFltTyp) {
		this.airSegFltTyp = airSegFltTyp;
	}

	public String getSubClsCd() {
		return subClsCd;
	}

	public void setSubClsCd(String subClsCd) {
		this.subClsCd = subClsCd;
	}

	public String getOprStatCd() {
		return oprStatCd;
	}

	public void setOprStatCd(String oprStatCd) {
		this.oprStatCd = oprStatCd;
	}

	public int getStatId() {
		return statId;
	}

	public void setStatId(int statId) {
		this.statId = statId;
	}

	public String getIndvlIdTypCd() {
		return indvlIdTypCd;
	}

	public void setIndvlIdTypCd(String indvlIdTypCd) {
		this.indvlIdTypCd = indvlIdTypCd;
	}

	public String getIndvlNbr() {
		return indvlNbr;
	}

	public void setIndvlNbr(String indvlNbr) {
		this.indvlNbr = indvlNbr;
	}

	public String getCntryCd() {
		return cntryCd;
	}

	public void setCntryCd(String cntryCd) {
		this.cntryCd = cntryCd;
	}

	public String getIndvlBthDay() {
		return indvlBthDay;
	}

	public void setIndvlBthDay(String indvlBthDay) {
		this.indvlBthDay = indvlBthDay;
	}

	public String getGndCd() {
		return gndCd;
	}

	public void setGndCd(String gndCd) {
		this.gndCd = gndCd;
	}

	public String getVicoCard() {
		return vicoCard;
	}

	public void setVicoCard(String vicoCard) {
		this.vicoCard = vicoCard;
	}

	public String getTktType() {
		return tktType;
	}

	public void setTktType(String tktType) {
		this.tktType = tktType;
	}

	public String getCtcNbr() {
		return ctcNbr;
	}

	public void setCtcNbr(String ctcNbr) {
		this.ctcNbr = ctcNbr;
	}

	public String toString() {
		return pnrRef + "," + pnrCrtDt + "," + pnrGrpInd + "," + paxId + ","
				+ paxFulNm + "," + paxCnNm + "," + vipInd + "," + paxAgeCtgCd
				+ "," + segId + "," + carrCd + "," + fltNbr + "," + fltNbrSfx
				+ "," + dptAirptCd + "," + arrvAirptCd + "," + dptDtLcl + ","
				+ dptDow + "," + dptTmLcl + "," + arrvDtLcl + "," + arrvTmLcl
				+ "," + airSegFltTyp + "," + subClsCd + "," + oprStatCd + ","
				+ statId + "," + indvlIdTypCd + "," + indvlNbr + "," + cntryCd
				+ "," + indvlBthDay + "," + gndCd + "," + vicoCard + ","
				+ tktType + "," + ctcNbr;
		// sql = "create table if not exists fullpnr.full_pnr_passenger_info"
		// +
		// "(pnr_ref  char(50) not null,pnr_crt_dt  char(50)  not null,pnr_grp_ind  char(5),pax_id  char(5) not null,pax_ful_nm  char(350),pax_cn_nm  char(200),vip_ind  char(1),pax_age_ctg_cd  char(1),seg_id  char(10),carr_cd  char(5),"
		// +
		// "flt_nbr  char(4),flt_nbr_sfx  char(1),dpt_airpt_cd  char(3),arrv_airpt_cd  char(3),dpt_dt_lcl  char(8),dpt_dow char(1),dpt_tm_lcl char(8),arrv_dt_lcl char(8),arrv_tm_lcl char(8),air_seg_flt_typ char(1),sub_cls_cd char(2),seg_opr_stat_cd char(2),stat_id char(10),opr_stat_cd char(2),indvl_id_typ_cd char(2),indvl_nbr char(60),cntry_cd char(4),indvl_bth_day char(8),gnd_cd char(1),vico_card char(6),tkt_type char(1),ctc_nbr char(11) CONSTRAINT pk  PRIMARY KEY (pnr_ref, pnr_crt_dt,pax_id))";
	}

	public String toSql() {
		return "'" + pnrRef + "','" + pnrCrtDt + "','" + pnrGrpInd + "','"
				+ paxId + "','" + paxFulNm + "','" + paxCnNm + "','" + vipInd
				+ "','" + paxAgeCtgCd + "','" + segId + "','" + carrCd + "','"
				+ fltNbr + "','" + fltNbrSfx + "','" + dptAirptCd + "','"
				+ arrvAirptCd + "','" + dptDtLcl + "','" + dptDow + "','"
				+ dptTmLcl + "','" + arrvDtLcl + "','" + arrvTmLcl + "','"
				+ airSegFltTyp + "','" + subClsCd + "','" + oprStatCd + "','"
				+ statId + "','" + indvlIdTypCd + "','" + indvlNbr + "','"
				+ cntryCd + "','" + indvlBthDay + "','" + gndCd + "','"
				+ vicoCard + "','" + tktType + "','" + ctcNbr + "'";
	}
	
	/**
	 * 
	 * HADOOP_CLASSPATH=/usr/lib/hbase/hbase-protocol-0.98.6-cdh5.3.3.jar:/usr/lib/hbase/conf hadoop jar  /usr/lib/hbase/lib/phoenix-4.6.0-HBase-0.98-client.jar org.apache.phoenix.mapreduce.CsvBulkLoadTool --table FULLPNR.FULL_PNR_PASSENGER_INFO --input /user/admin/leisongyun/CZ_DFP_20151019_1.txt.gz.process
	 * 
	 * pnr_ref,pnr_crt_dt,pnr_grp_ind,pax_id,pax_ful_nm ,pax_cn_nm,vip_ind,pax_age_ctg_cd,seg_id,carr_cd,flt_nbr,flt_nbr_sfx,dpt_airpt_cd,arrv_airpt_cd,dpt_dt_lcl,dpt_dow,dpt_tm_lcl,arrv_dt_lcl,arrv_tm_lcl,air_seg_flt_typ,sub_cls_cd,opr_stat_cd,stat_id,indvl_id_typ_cd,indvl_nbr,cntry_cd,indvl_bth_day,gnd_cd,vico_card,tkt_type,ctc_nbr

	 * 
	 * 
	 */
}
