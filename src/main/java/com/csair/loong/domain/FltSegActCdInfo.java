package com.csair.loong.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by cloudoo on 2016/9/13.
 */
public class FltSegActCdInfo extends PnrInfo{

    private static String CANCEL_CD= "'DL','HX','NO','UC','UN','XX'";
    private String partType = "005";
    private int segId;//航段序号，主键
    private int statId;//记录序号，主键
    private int txnItmNbrAdd;//创建交易号，//此行动代码建立的交易序号；与交易组信息中的Txn_Id 一致
    private int txnItmNbrCncl;//取消交易号，行动代码被更新的交易序号；与交易组信息中的Txn_Id 一致
    private String oprStatCd;//行动代码，如果同一次交易具有多个行动代码，按照两个行动代码进行分组，此字段为该分组的第二个行动代码
    private String prevStatCd;//前次行动代码，如果同一次交易具有多个行动代码，按照两个行动代码进行分组，此字段为该分组的第一个行动代码
    private int currPaxNbr;//当前旅客人数,本PNR 的旅客人数
    private int segPaxNbr;//最终旅客人数,本PNR 中该航段的证实人数
    //只到filller6

    public FltSegActCdInfo(String[] lines){
         if(lines.length>8){
             if(StringUtils.isNotBlank(lines[1])){
             segId = Integer.parseInt(lines[1]);}
             if(StringUtils.isNotBlank(lines[2])){
             statId = Integer.parseInt(lines[2]);}
             if(StringUtils.isNotBlank(lines[3])){
             txnItmNbrAdd = Integer.parseInt(lines[3]);}
             if(StringUtils.isNotBlank(lines[4])){
             txnItmNbrCncl = Integer.parseInt(lines[4]);}
             oprStatCd = lines[5];
             prevStatCd = lines[6];
             if(StringUtils.isNotBlank(lines[7])){
             currPaxNbr = Integer.parseInt(lines[7]);}
             if(StringUtils.isNotBlank(lines[8])){
             segPaxNbr = Integer.parseInt(lines[8]);}
         }
    }


    public boolean isCancelStat(){
        return CANCEL_CD.indexOf(oprStatCd)>=0;
    }


    @Override
    public String getPartTyp() {
        return partType;
    }
    @Override
    public void setPartTyp(String partType) {
        this.partType = partType;
    }

    public int getSegId() {
        return segId;
    }

    public void setSegId(int segId) {
        this.segId = segId;
    }

    public int getStatId() {
        return statId;
    }

    public void setStatId(int statId) {
        this.statId = statId;
    }

    public int getTxnItmNbrAdd() {
        return txnItmNbrAdd;
    }

    public void setTxnItmNbrAdd(int txnItmNbrAdd) {
        this.txnItmNbrAdd = txnItmNbrAdd;
    }

    public int getTxnItmNbrCncl() {
        return txnItmNbrCncl;
    }

    public void setTxnItmNbrCncl(int txnItmNbrCncl) {
        this.txnItmNbrCncl = txnItmNbrCncl;
    }

    public String getOprStatCd() {
        return oprStatCd;
    }

    public void setOprStatCd(String oprStatCd) {
        this.oprStatCd = oprStatCd;
    }

    public String getPrevStatCd() {
        return prevStatCd;
    }

    public void setPrevStatCd(String prevStatCd) {
        this.prevStatCd = prevStatCd;
    }

    public int getCurrPaxNbr() {
        return currPaxNbr;
    }

    public void setCurrPaxNbr(int currPaxNbr) {
        this.currPaxNbr = currPaxNbr;
    }

    public int getSegPaxNbr() {
        return segPaxNbr;
    }

    public void setSegPaxNbr(int segPaxNbr) {
        this.segPaxNbr = segPaxNbr;
    }


}
