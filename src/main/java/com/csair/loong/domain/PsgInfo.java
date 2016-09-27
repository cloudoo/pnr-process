package com.csair.loong.domain;

import java.io.Serializable;

/**
 * Created by cloudpj on 16/8/14.
 */

public class PsgInfo implements Serializable {

    private String pnrKey;
    private String paxId;
    private String paxFulName;
    private String paxCnName;
    private String indalIdTypeCd="";
    private String indalIdNum="";
    private String cntryCd="";
    private String bthDay="";
    private String gndCd="";//性别



    public String getPnrKey() {
        return pnrKey;
    }

    public void setPnrKey(String pnrKey) {
        this.pnrKey = pnrKey;
    }

    public String getPaxId() {
        return paxId;
    }

    public void setPaxId(String paxId) {
        this.paxId = paxId;
    }

    public String getPaxFulName() {
        return paxFulName;
    }

    public void setPaxFulName(String paxFulName) {
        this.paxFulName = paxFulName;
    }

    public String getPaxCnName() {
        return paxCnName;
    }

    public void setPaxCnName(String paxCnName) {
        this.paxCnName = paxCnName;
    }

    public String getIndalIdTypeCd() {
        return indalIdTypeCd;
    }

    public void setIndalIdTypeCd(String indalIdTypeCd) {
        this.indalIdTypeCd = indalIdTypeCd;
    }

    public String getIndalIdNum() {
        return indalIdNum;
    }

    public void setIndalIdNum(String indalIdNum) {
        this.indalIdNum = indalIdNum;
    }

    public String getCntryCd() {
        return cntryCd;
    }

    public void setCntryCd(String cntryCd) {
        this.cntryCd = cntryCd;
    }

    public String getBthDay() {
        return bthDay;
    }

    public void setBthDay(String bthDay) {
        this.bthDay = bthDay;
    }

    public String getGndCd() {
        return gndCd;
    }

    public void setGndCd(String gndCd) {
        this.gndCd = gndCd;
    }

    public String toString(){
        return paxId+","+paxFulName+","+getPaxCnName()+","+indalIdTypeCd+","+indalIdNum+","+cntryCd+","+bthDay+","+gndCd;
    }
}
