package com.csair.loong.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by cloudoo on 2016/9/14.
 */
public class BigClientInfo extends PnrInfo{
    private String partTyp = "015";
    private String paxDatId;//Pax_dat序号,主键
    private int rltvPosn;//段组相关位置 0-不存在于现行部分; 非0-在现行部分的相关位置
    private int txnItmNbrAdd;//交易项序号增加,本信息创建的交易序号, 与交易组信息中的Txn_Id一致
    private int txnItmNbrCncl;//交易项序号取消,0-未被取消,存在现行部分;非0-在本次交易被取消，与交易组信息中的Txn_Id一致
    private String carrCd;//航空公司,旅客订座航段的航空公司代码
    private String oprStatCd;//行动代码,出票操作行动代码
    private int paxQty;//旅客人数，行动代码之后紧跟的旅客数量
    private String vicoCard;//大客户卡号,5位或者6位的大客户编码
    /**
     *
     * 大客户出票类型,
     * ‘C’——来自FC项；‘
     * S’——来自SSR CKIN项；
     * ‘V’——来自SSR VICO项；‘
     * T’——来自于TC项；
     * ‘P’——来自于FP项；
     * ‘E’——来自于EI项。
     *
     */
    private String tktTyp;//
    //到filler6
    public BigClientInfo(String[] lines){
         if(lines.length>7){
             paxDatId =  lines[1] ;
             if(StringUtils.isNotBlank(lines[2])){
             rltvPosn = Integer.parseInt(lines[2]);}
             if(StringUtils.isNotBlank(lines[2])){
             txnItmNbrAdd = Integer.parseInt(lines[3]);}
             if(StringUtils.isNotBlank(lines[2])){
             txnItmNbrCncl = Integer.parseInt(lines[4]);}
             carrCd = lines[5];
             oprStatCd = lines[6];
             paxQty = Integer.parseInt(lines[7]);
             oprStatCd = lines[8];
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

    public String getPaxDatId() {
        return paxDatId;
    }

    public void setPaxDatId(String paxDatId) {
        this.paxDatId = paxDatId;
    }

    public int getRltvPosn() {
        return rltvPosn;
    }

    public void setRltvPosn(int rltvPosn) {
        this.rltvPosn = rltvPosn;
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

    public String getCarrCd() {
        return carrCd;
    }

    public void setCarrCd(String carrCd) {
        this.carrCd = carrCd;
    }

    public String getOprStatCd() {
        return oprStatCd;
    }

    public void setOprStatCd(String oprStatCd) {
        this.oprStatCd = oprStatCd;
    }

    public int getPaxQty() {
        return paxQty;
    }

    public void setPaxQty(int paxQty) {
        this.paxQty = paxQty;
    }

    public String getVicoCard() {
        return vicoCard;
    }

    public void setVicoCard(String vicoCard) {
        this.vicoCard = vicoCard;
    }

    public String getTktTyp() {
        return tktTyp;
    }

    public void setTktTyp(String tktTyp) {
        this.tktTyp = tktTyp;
    }
}
