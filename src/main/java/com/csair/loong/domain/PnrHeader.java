package com.csair.loong.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cloudoo on 2016/8/29.
 */
public class PnrHeader extends PnrInfo{
    private String partTyp="001";//类型代码
    public static char GROUP_TAG='G';
    public static char BLOCK_TAG='B';
    private String pnrRef;//pnr记录编码
    private String pnrCrtDt;//pnr创建日期
    private String pnrGrpInd;//团队标识,G 团队，B 占座，否则为null。
    private String pnrCnclInd;//取消标识，D 整个取消，否则为null。
    private String pnrHeadTxt;//PNR 辅助标记,‘E’-电子票PNR;‘C’-CODESHARE MARKETING PNR； 否则为 null。
    private String ownGds;//
    private String origRes;//

    public PnrHeader(String[] lines){
        if(lines.length>10){

            pnrRef = lines[1];
            pnrCrtDt = lines[2];
            pnrGrpInd = lines[3];
            pnrCnclInd = lines[4];
            pnrHeadTxt = lines[5];
            ownGds = lines[6];
            origRes = lines[7];

        }
    }

    @Override
    public String getPartTyp() {
        return partTyp;
    }

    @Override
    public void setPartTyp(String partTyp) {
        this.partTyp = partTyp;
    }

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

    public String getPnrCnclInd() {
        return pnrCnclInd;
    }

    public void setPnrCnclInd(String pnrCnclInd) {
        this.pnrCnclInd = pnrCnclInd;
    }

    public String getPnrHeadTxt() {
        return pnrHeadTxt;
    }

    public void setPnrHeadTxt(String pnrHeadTxt) {
        this.pnrHeadTxt = pnrHeadTxt;
    }

    public String getOwnGds() {
        return ownGds;
    }

    public void setOwnGds(String ownGds) {
        this.ownGds = ownGds;
    }

    public String getOrigRes() {
        return origRes;
    }

    public void setOrigRes(String origRes) {
        this.origRes = origRes;
    }


}
