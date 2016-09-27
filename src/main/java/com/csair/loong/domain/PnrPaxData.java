package com.csair.loong.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by cloudoo on 2016/9/19.
 */
public class PnrPaxData extends PnrInfo {
    private String partTyp = "008";
    private String paxDatId;//Pax_Data序号,主键
    private String paxDatTypCd;//段组类型代码,详细说明参考：Pax_Dat_Typ_Cd类别
    private String carrCd;//航空公司代码
    private int txnItmNbrAdd;//创建交易号,创建本信息的交易序号；与交易组信息中的Txn_Id一致
    private int txnItmNbrCncl;//取消交易号,0-未被取消,存在现行部分;非0-在本次交 易被取消;与交易组信息中的Txn_Id一致
    private int paxDatRltvPosn;//段组相关位置,0-不存在于现行部分;非0-在现行部分的相关位置
    private String paxDatTxt;//数据内容,详细文本
    private String privacyInfo;//隐私信息标识字段,空——非已知隐私信息；航空公司代码——包含该航空公司隐私信息
    private String filler11;//
    private String filler12;//

    public PnrPaxData(String[] lines) {
        if (lines.length > 8) {
            paxDatId = lines[1];
            paxDatTypCd = lines[2];
            carrCd = lines[3];
            if (StringUtils.isNotBlank(lines[4])) {
                txnItmNbrAdd = Integer.parseInt(lines[4]);
            }
            if (StringUtils.isNotBlank(lines[5])) {
                txnItmNbrCncl = Integer.parseInt(lines[5]);
            }
            if (StringUtils.isNotBlank(lines[6])) {
                paxDatRltvPosn = Integer.parseInt(lines[6]);
            }
            paxDatTxt = lines[7];
            privacyInfo = lines[8];
        }
    }


    /*************************************************************************************************/
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

    public String getPaxDatTypCd() {
        return paxDatTypCd;
    }

    public void setPaxDatTypCd(String paxDatTypCd) {
        this.paxDatTypCd = paxDatTypCd;
    }

    public String getCarrCd() {
        return carrCd;
    }

    public void setCarrCd(String carrCd) {
        this.carrCd = carrCd;
    }

    public int getTxnItmNbrCncl() {
        return txnItmNbrCncl;
    }

    public void setTxnItmNbrCncl(int txnItmNbrCncl) {
        this.txnItmNbrCncl = txnItmNbrCncl;
    }

    public int getTxnItmNbrAdd() {
        return txnItmNbrAdd;
    }

    public void setTxnItmNbrAdd(int txnItmNbrAdd) {
        this.txnItmNbrAdd = txnItmNbrAdd;
    }

    public int getPaxDatRltvPosn() {
        return paxDatRltvPosn;
    }

    public void setPaxDatRltvPosn(int paxDatRltvPosn) {
        this.paxDatRltvPosn = paxDatRltvPosn;
    }

    public String getPaxDatTxt() {
        return paxDatTxt;
    }

    public void setPaxDatTxt(String paxDatTxt) {
        this.paxDatTxt = paxDatTxt;
    }

    public String getPrivacyInfo() {
        return privacyInfo;
    }

    public void setPrivacyInfo(String privacyInfo) {
        this.privacyInfo = privacyInfo;
    }

    public String getFiller11() {
        return filler11;
    }

    public void setFiller11(String filler11) {
        this.filler11 = filler11;
    }

    public String getFiller12() {
        return filler12;
    }

    public void setFiller12(String filler12) {
        this.filler12 = filler12;
    }
}
