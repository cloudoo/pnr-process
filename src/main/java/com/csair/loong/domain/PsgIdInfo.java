package com.csair.loong.domain;

/**
 * Created by cloudoo on 2016/9/13.
 */
public class PsgIdInfo extends PnrInfo{

    private String partType = "010";
    private String paxDatId;//Pax_dat 序号 ，主键
    private String paxId;//旅客序号，与旅客信息部分中的pax_id一致
    /**
     * I-关键字为 FOID 且含NI；
     D- 关键字为FOID 且含ID；
     O- 关键字为FOID 的其他情况；
     S- 关键字为DOCS；
     P- 关键字为FOID 且含PP或者关键字不属于FOID 、DOCS 的情况
     */
    private String indvlIdTypCd;//旅客证件类型，
    private String indvlIdNbr;//旅客证件号
    private String cntryCd;//旅客国别代码 ，KR？
    private String indvlBthDay;//旅客出生日期 yyyyMMdd
    private String gndCd;//旅客性别，M-Male；F-Female
    // 到filler8

    public PsgIdInfo(String[] lines){
        if(lines.length>7){
            paxDatId =  lines[1] ;
            paxId =  lines[2] ;
            indvlIdTypCd = lines[3];
            indvlIdNbr = lines[4];
            cntryCd = lines[5];
            indvlBthDay = lines[6];
            gndCd = lines[7];
        }

    }

    public String getPartTyp() {
        return partType;
    }

    public void setPartTyp(String partType) {
        this.partType = partType;
    }

    public String getPaxDatId() {
        return paxDatId;
    }

    public void setPaxDatId(String paxDatId) {
        this.paxDatId = paxDatId;
    }

    public String getPaxId() {
        return paxId;
    }

    public void setPaxId(String paxId) {
        this.paxId = paxId;
    }

    public String getIndvlIdTypCd() {
        return indvlIdTypCd;
    }

    public void setIndvlIdTypCd(String indvlIdTypCd) {
        this.indvlIdTypCd = indvlIdTypCd;
    }

    public String getIndvlIdNbr() {
        return indvlIdNbr;
    }

    public void setIndvlIdNbr(String indvlIdNbr) {
        this.indvlIdNbr = indvlIdNbr;
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
}
