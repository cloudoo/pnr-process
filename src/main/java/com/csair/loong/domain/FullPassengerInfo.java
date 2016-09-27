package com.csair.loong.domain;

import java.io.Serializable;

/**
 * Created by cloudoo on 2016/9/18.
 */
public class FullPassengerInfo implements Serializable{

    //001
    private String pnrRef;//
    private String pnrCrtDt;//
    private String pnrGrpInd;
    //003
    private String paxId;//
    private String paxFulNm;
    private String paxCnNm;
    private String vipInd;//
    private String paxAgeCtgCd;//
    //004
    private String segId;//
    private String carrCd;//
    private String fltNbr;//
    private String fltNbrSfx;//
    private String dptAirptCd;//
    private String arrvAirptCd;//
    private String dptDtLcl;//
    private String dptDow;//
    private String dptTmLcl;//
    private String arrvDtLcl;//
    private String arrvTmLcl;//
    private String airSegFltTyp;//
    private String subClsCd;//
    private String oprStatCd;//
    //005
    private int statId;//
//    private String oprStatCd;//
    //010
    private String indvlIdTypCd;//
    private String indvlNbr;//
    private String cntryCd;//
    private String indvlBthDay;//
    private String gndCd;//
    //015
    private String vicoCard;//
    private String tktType;//
    //016
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

    public String toString(){
        return pnrRef+","+pnrCrtDt+","+pnrGrpInd+","+paxId+","+paxFulNm +","+paxCnNm+","+vipInd+","+paxAgeCtgCd+","+segId+","+carrCd+","+fltNbr+","+fltNbrSfx+","+dptAirptCd+","+arrvAirptCd+","+dptDtLcl+","+dptDow+","+dptTmLcl+","+arrvDtLcl+","+arrvTmLcl+","+airSegFltTyp+","+subClsCd+","+oprStatCd+","+statId+","+oprStatCd+","+indvlIdTypCd+","+indvlNbr+","+cntryCd+","+indvlBthDay+","+gndCd+","+vicoCard+","+tktType+","+ctcNbr+"\n\r";
    }
}
