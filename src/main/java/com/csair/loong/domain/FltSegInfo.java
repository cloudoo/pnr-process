package com.csair.loong.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 航班航段信息
 * Created by cloudoo on 2016/9/13.
 */
public class FltSegInfo extends PnrInfo {
    private static String normal_opr_stat_cd = "'DK','DR','FS','GK','HK','HS','IS','KK','KL','LK','PK','PL','PU','RR','SS','TK'";
    private String partTyp = "004";//类型代码
    private String segId;//主键，航段序号
    private String paxId;//主键，旅客序号,与旅客信息部分中的pax_id一致
    private int segRltvPosn;//航段相关位置,0-不存在于现行部分;非0-在现行部分的相关位置
    private int txnItmNbrAdd;//创建交易号,此航段被创建的交易序号；与 交 易 组 信 息 中 的Txn_Id 一致
    private int txnItmNbrCncl;//取消交易号,0-此航段没有被取消且相关旅客没有被取消或分离;非0-此航段在本次交易被取消,
    //或者相关旅客在本次交易被取消或分离；与交易组信息中的Txn_Id 一致
    private String carrCd;//航空公司代码，如果是共享航班，与订座中航段所显示的航空公司一致
    private String fltNbr;//航班号，open-open 航段
    private String fltNbrSfx;//航班后缀；基本为null
    private String dptAirptCd;//起飞机场
    private String arrvAirptCd;//落地机场
    private String dptDtLcl;//当地起飞时间  yyyy-MM-dd 是否string好点？
    private String dptDow;//起飞星期；0-星期天
    private String dptTmLcl;// 当地起飞时间，hh:mi:ss
    private String arrvDtLcl;//当地到达日期，
    private String arrvTmLcl;//当地到达时间
    private String airSegFltType;// N -夜航 ，否则为null？
    private String subClsCd;//销售子舱位，M，订座子仓位代码
    private String oprStatCd;//航段行动代码；RR，当前行动代码
    private String mktgCarrCd;//代码共享航空公司代码，预留扩展用
    private String mktgFltNbr;//代码共享航班号，
    private String mktgFltNbrSfx;//代码共享航班号后缀
    private String prmCarrCd;//
    private String prmFltNbr;//
    private String prmFltNbrSfx;//
    /**
     * 航班代码共享类型，
     * 0-非codeshare;
     * 1-国内的freesale;
     * 2-block space;
     * 3-国际freesale;
     * 7- 国际的codeshare,-CS
     * PAX 形式。
     */
    private String cdshrTypeCd;
    /**
     * 航班共享承运子仓位
     * 在PNR 中，某些以OP-的形式进行航班共享显示的，此字段有值
     */
    private String prmSubClsCd;//
    /**
     * 超售旅客数量
     * 0-非overbook；
     * 非 0-overbook 旅客数量
     */
    private int ovbQty;//超售旅客数量 0-非overbook；非 0-overbook 旅客数量
    private String marrInd;//MARRIED航段标识 ，1，married航段，0 非married航段
    private String fltInfInd;//航段信息标识，Y-信息航段，N-非信息航段
    private String othTxt;//航段组中有待细致解析的部分
    //航段当前行动代码信息
    private FltSegActCdInfo currentFltSegActCdInfo;
    //航段所有行动代码信息
    private List<FltSegActCdInfo> fltSegActCdInfos;

    private String filler11;//
    private String filler12;//;^;

    public FltSegInfo(String[] lines) {
        if (lines.length > 23) {
            segId = lines[1];
            paxId = lines[2];
            if (StringUtils.isNotBlank(lines[3])) {
                segRltvPosn = Integer.parseInt(lines[3]);
            }
            if (StringUtils.isNotBlank(lines[4])) {
                txnItmNbrAdd = Integer.parseInt(lines[4]);
            }
            if (StringUtils.isNotBlank(lines[5])) {
                txnItmNbrCncl = Integer.parseInt(lines[5]);
            }
            carrCd = lines[6];
            fltNbr = lines[7];
            fltNbrSfx = lines[8];
            dptAirptCd = lines[9];
            arrvAirptCd = lines[10];
            dptDtLcl = lines[11];
            dptDow = lines[12];
            dptTmLcl = lines[13];
            arrvDtLcl = lines[14];
            arrvTmLcl = lines[15];
            airSegFltType = lines[16];
            subClsCd = lines[17];
            oprStatCd = lines[18];
            mktgCarrCd = lines[19];
            mktgFltNbr = lines[20];
            mktgFltNbrSfx = lines[21];
            prmCarrCd = lines[22];
            prmFltNbr = lines[23];
            prmFltNbrSfx = lines[24];
        }
    }

    public void addFltSegActCdInfo(FltSegActCdInfo fltSegActCdInfo) {
        if (fltSegActCdInfos == null) {
            fltSegActCdInfos = new ArrayList<FltSegActCdInfo>();
        }
        if (fltSegActCdInfo.getTxnItmNbrCncl() == 0 && !fltSegActCdInfo.isCancelStat()) {
            this.setCurrentFltSegActCdInfo(fltSegActCdInfo);
        }
        fltSegActCdInfos.add(fltSegActCdInfo);
    }


    public boolean isNormalStatCd() {
        return normal_opr_stat_cd.indexOf(this.oprStatCd) >= 0;
    }

    public boolean isNormalStatCd(String cd) {
        return normal_opr_stat_cd.indexOf(cd) >= 0;
    }

    @Override
    public String getPartTyp() {
        return partTyp;
    }

    @Override
    public void setPartTyp(String partTyp) {
        this.partTyp = partTyp;
    }

    public String getSegId() {
        return segId;
    }

    public void setSegId(String segId) {
        this.segId = segId;
    }

    public String getPaxId() {
        return paxId;
    }

    public void setPaxId(String paxId) {
        this.paxId = paxId;
    }

    public int getSegRltvPosn() {
        return segRltvPosn;
    }

    public void setSegRltvPosn(int segRltvPosn) {
        this.segRltvPosn = segRltvPosn;
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

    public String getAirSegFltType() {
        return airSegFltType;
    }

    public void setAirSegFltType(String airSegFltType) {
        this.airSegFltType = airSegFltType;
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

    public String getMktgCarrCd() {
        return mktgCarrCd;
    }

    public void setMktgCarrCd(String mktgCarrCd) {
        this.mktgCarrCd = mktgCarrCd;
    }

    public String getMktgFltNbr() {
        return mktgFltNbr;
    }

    public void setMktgFltNbr(String mktgFltNbr) {
        this.mktgFltNbr = mktgFltNbr;
    }

    public String getMktgFltNbrSfx() {
        return mktgFltNbrSfx;
    }

    public void setMktgFltNbrSfx(String mktgFltNbrSfx) {
        this.mktgFltNbrSfx = mktgFltNbrSfx;
    }

    public String getPrmCarrCd() {
        return prmCarrCd;
    }

    public void setPrmCarrCd(String prmCarrCd) {
        this.prmCarrCd = prmCarrCd;
    }

    public String getPrmFltNbr() {
        return prmFltNbr;
    }

    public void setPrmFltNbr(String prmFltNbr) {
        this.prmFltNbr = prmFltNbr;
    }

    public String getPrmFltNbrSfx() {
        return prmFltNbrSfx;
    }

    public void setPrmFltNbrSfx(String prmFltNbrSfx) {
        this.prmFltNbrSfx = prmFltNbrSfx;
    }

    public String getCdshrTypeCd() {
        return cdshrTypeCd;
    }

    public void setCdshrTypeCd(String cdshrTypeCd) {
        this.cdshrTypeCd = cdshrTypeCd;
    }

    public String getPrmSubClsCd() {
        return prmSubClsCd;
    }

    public void setPrmSubClsCd(String prmSubClsCd) {
        this.prmSubClsCd = prmSubClsCd;
    }

    public int getOvbQty() {
        return ovbQty;
    }

    public void setOvbQty(int ovbQty) {
        this.ovbQty = ovbQty;
    }

    public String getMarrInd() {
        return marrInd;
    }

    public void setMarrInd(String marrInd) {
        this.marrInd = marrInd;
    }

    public String getFltInfInd() {
        return fltInfInd;
    }

    public void setFltInfInd(String fltInfInd) {
        this.fltInfInd = fltInfInd;
    }

    public String getOthTxt() {
        return othTxt;
    }

    public void setOthTxt(String othTxt) {
        this.othTxt = othTxt;
    }

    public FltSegActCdInfo getCurrentFltSegActCdInfo() {
        return currentFltSegActCdInfo;
    }

    public void setCurrentFltSegActCdInfo(FltSegActCdInfo currentFltSegActCdInfo) {
        this.currentFltSegActCdInfo = currentFltSegActCdInfo;
    }

    public List<FltSegActCdInfo> getFltSegActCdInfos() {
        return fltSegActCdInfos;
    }

    public void setFltSegActCdInfos(List<FltSegActCdInfo> fltSegActCdInfos) {
        this.fltSegActCdInfos = fltSegActCdInfos;
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
