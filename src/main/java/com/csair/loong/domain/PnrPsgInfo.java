package com.csair.loong.domain;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cloudoo on 2016/8/29.
 */
public class PnrPsgInfo extends PnrInfo {
    private String pnrRef;//pnr记录编码，从pnr来的联合主键
    private String pnrCrtDt;//pnr创建日期，从pnr来的联合主键

    private String partTyp="002";//类型代码
    private String paxId;//旅客序号
    private int paxRltvPosn;//旅客相关位置 0-此旅客不存在现行部分; 非0-此旅客在现行部分的位置
    private int txnItmNbrAdd;//创建交易号 旅客被创建的交易序号；与交易组信息中的Txn_Id 一致
    private int txnItmNbrCncl;//取消交易号，0-此旅客没有被取消或分离;非0-此旅客在本次交易被取消或分离；与交易组信息中的Txn_Id 一致
    private String paxMvInd;//旅客分离取消标识 S-被分离;X-被取消;否则未被分离或取消
    private String paxFulNm;//旅客全名，中文姓名旅客，该字段为中文姓名的全部拼音；英 文 姓 名旅客， 包含Pax_Fst_Nm 和Pax_Lst_Nm；如果PNR 姓名段组输入了CHD 等附加信息，也在此字段中表现。
    private String paxLstNm;//英文名
    private String paxFstNm;//英文姓
    private String paxCnNm;//中文姓名
    private String vipInd;//vip 标识
    private String paxAgeCtgCd;//旅客类型，A-成人;C-儿童;I-婴儿
    private String paxSltn;//旅客称呼 ,DR/MISS/MR/MRS/MS/MSTR

    public PnrPsgInfo(String[] lines){

        if(lines.length>=13){
            paxId = lines[1];
//            pnrRef = lines[2];
//            pnrCrtDt = lines[3];
            if(StringUtils.isNoneBlank(lines[2])){
                paxRltvPosn = Integer.parseInt(lines[2]);
            }
            if(StringUtils.isNoneBlank(lines[3])) {
                txnItmNbrAdd = Integer.parseInt(lines[3]);
            }
            if(StringUtils.isNoneBlank(lines[4])) {
                txnItmNbrCncl = Integer.parseInt(lines[4]);
            }
            paxMvInd = lines[5];
            paxFulNm = lines[6];
            paxLstNm = lines[7];
            paxFstNm = lines[8];
            paxCnNm = lines[9];
            vipInd = lines[10];
            paxAgeCtgCd = lines[11];
            paxSltn = lines[12];
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

    public String getPaxId() {
        return paxId;
    }

    public void setPaxId(String paxId) {
        this.paxId = paxId;
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

    public int getPaxRltvPosn() {
        return paxRltvPosn;
    }

    public void setPaxRltvPosn(int paxRltvPosn) {
        this.paxRltvPosn = paxRltvPosn;
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

    public String getPaxMvInd() {
        return paxMvInd;
    }

    public void setPaxMvInd(String paxMvInd) {
        this.paxMvInd = paxMvInd;
    }

    public String getPaxFulNm() {
        return paxFulNm;
    }

    public void setPaxFulNm(String paxFulNm) {
        this.paxFulNm = paxFulNm;
    }

    public String getPaxLstNm() {
        return paxLstNm;
    }

    public void setPaxLstNm(String paxLstNm) {
        this.paxLstNm = paxLstNm;
    }

    public String getPaxFstNm() {
        return paxFstNm;
    }

    public void setPaxFstNm(String paxFstNm) {
        this.paxFstNm = paxFstNm;
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

    public String getPaxSltn() {
        return paxSltn;
    }

    public void setPaxSltn(String paxSltn) {
        this.paxSltn = paxSltn;
    }
}
