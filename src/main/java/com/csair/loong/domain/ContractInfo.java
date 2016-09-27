package com.csair.loong.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by cloudoo on 2016/9/14.
 */
public class ContractInfo extends PnrInfo{
    private String partTyp = "016";
    private String paxDatId;//Pax_dat序号,主键
    private int rltvPosn;//段组相关位置,0-不存在于现行部分;非0-在现行部分的相关位置
    private int txnItmNbrAdd;//交易项序号增加,本信息创建的交易序号, 与交易组信息中的Txn_Id一致
    private int txnItmNbrCncl;//交易项序号取消,0-未被取消,存在现行部分; 非0-在本次交易被取消，与交易组信息中的Txn_Id一致
    private String carrCd;//航空公司，航空公司代码
    /**
     * CTCT/CTC—来源于OSI XX CTCT或者OSI XX CTC；
     * CT—来源于PNR中联系组自由文本
     */
    private String ctTyp;//手机号码来源类型,
    private String ctcNbr;//手机号码
    private int paxId;//旅客序号,联系方式对应的旅客序号

    public ContractInfo(String[] lines){
        if(lines.length>4){
            paxDatId =  lines[1];
            if(StringUtils.isNotBlank(lines[2])){
            rltvPosn = Integer.parseInt(lines[2]);}
            if(StringUtils.isNotBlank(lines[3])){
            txnItmNbrAdd = Integer.parseInt(lines[3]);}
            if(StringUtils.isNotBlank(lines[4])){
            txnItmNbrCncl = Integer.parseInt(lines[4]);}
            carrCd = lines[5];
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

    public String getCtTyp() {
        return ctTyp;
    }

    public void setCtTyp(String ctTyp) {
        this.ctTyp = ctTyp;
    }

    public String getCtcNbr() {
        return ctcNbr;
    }

    public void setCtcNbr(String ctcNbr) {
        this.ctcNbr = ctcNbr;
    }

    public int getPaxId() {
        return paxId;
    }

    public void setPaxId(int paxId) {
        this.paxId = paxId;
    }
}
