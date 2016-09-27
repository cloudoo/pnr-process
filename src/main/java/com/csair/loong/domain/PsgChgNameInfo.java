package com.csair.loong.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by cloudoo on 2016/9/13.
 */
public class PsgChgNameInfo extends PnrInfo {
    private String partType = "003";
    private String paxId; //旅客序号,pax_id 指的是在改名前这个旅客的序号
    private int paxHistItmNbr;//主键，多次改名的记录顺序
    private int txnItmNbrAdd;//创建交易号,旅客被创建的交易序号；与交易组信息中的Txn_Id 一致
    private int txnItmNbrCncl;//取消交易号，旅客被改名的交易序号；与交易组信息中的Txn_Id 一致
    private String paxSltn;//旅客称呼，DR/MISS/MR/MRS/MS/MSTR
    //旅客全名，中文姓名旅客，该字段为中文姓名的全部拼音；英 文 姓 名旅客，
    // 包含Pax_Fst_Nm 和Pax_Lst_Nm；如果PNR 姓名段组输入了CHD 等附加信息，也在此字段中表现
    private String paxFulNm;
    private String paxLstNm;//英文名，具有中文姓名的旅客此字段为中文姓名的全部拼音
    private String paxFstNm;//英文姓，具有中文姓名的旅客此字段为空值
    private String paxCnNm;//中文姓,此字段限输入了中文姓名的旅客

    public PsgChgNameInfo(String[] lines) {
        if (lines.length > 9) {
            paxId = lines[1];
            if (StringUtils.isNotBlank(lines[2])) {
                paxHistItmNbr = Integer.parseInt(lines[2]);
            }
            if (StringUtils.isNotBlank(lines[3])) {
                txnItmNbrAdd = Integer.parseInt(lines[3]);
            }
            if (StringUtils.isNotBlank(lines[4])) {
                txnItmNbrCncl = Integer.parseInt(lines[4]);
            }
            paxSltn = lines[5];
            paxFulNm = lines[6];
            paxLstNm = lines[7];
            paxFstNm = lines[8];
            paxCnNm = lines[9];


        }

    }

    public String getPartTyp() {
        return partType;
    }

    public void setPartTyp(String partType) {
        this.partType = partType;
    }

    public String getPaxId() {
        return paxId;
    }

    public void setPaxId(String paxId) {
        this.paxId = paxId;
    }

    public int getPaxHistItmNbr() {
        return paxHistItmNbr;
    }

    public void setPaxHistItmNbr(int paxHistItmNbr) {
        this.paxHistItmNbr = paxHistItmNbr;
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

    public String getPaxSltn() {
        return paxSltn;
    }

    public void setPaxSltn(String paxSltn) {
        this.paxSltn = paxSltn;
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
}
