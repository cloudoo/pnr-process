package com.csair.loong.domain;

import java.io.Serializable;

/**
 * Created by cloudoo on 2016/9/12.
 */
public abstract class PnrInfo implements Serializable {
	public static String FILE_HEADER="PNR FILE ICS";
    public static String PNR_HEAD_TYPE = "001";
    public static String PNR_PSG_INFO_TYPE = "002";
    public static String PNR_PSG_CHGNAME_INFO_TYPE = "003";
    public static String PNR_FLT_SEG_TYPE = "004";
    public static String PNR_FLT_SEG_ACT_CD_TYPE = "005";
    public static String PNR_PAX_DATA_TYPE = "008";
    public static String PNR_PSG_ID_INFO_TYPE = "010";
    public static String PNR_BIG_CLIENT = "015";
    public static String PNR_CONTRACT_TYPE = "016";
    public static String FILE_ENDER="END PNR FILE";
    

    private String filler1;
    private String filler2;
    private String filler3;
    private String filler4;
    private String filler5;
    private String filler6;
    private String filler7;
    private String filler8;
    private String filler9;
    private String filler10;//预留扩展用，当所有filler 预留字段使用完毕后，启用该字段，所以该字段可能含有“,”字符，目前此字段含行结束符“;^;”。

    public abstract String getPartTyp();
    public abstract void setPartTyp(String partTyp);
    public String getFiller1() {
        return filler1;
    }

    public void setFiller1(String filler1) {
        this.filler1 = filler1;
    }

    public String getFiller2() {
        return filler2;
    }

    public void setFiller2(String filler2) {
        this.filler2 = filler2;
    }

    public String getFiller3() {
        return filler3;
    }

    public void setFiller3(String filler3) {
        this.filler3 = filler3;
    }

    public String getFiller4() {
        return filler4;
    }

    public void setFiller4(String filler4) {
        this.filler4 = filler4;
    }

    public String getFiller5() {
        return filler5;
    }

    public void setFiller5(String filler5) {
        this.filler5 = filler5;
    }

    public String getFiller6() {
        return filler6;
    }

    public void setFiller6(String filler6) {
        this.filler6 = filler6;
    }

    public String getFiller7() {
        return filler7;
    }

    public void setFiller7(String filler7) {
        this.filler7 = filler7;
    }

    public String getFiller8() {
        return filler8;
    }

    public void setFiller8(String filler8) {
        this.filler8 = filler8;
    }

    public String getFiller9() {
        return filler9;
    }

    public void setFiller9(String filler9) {
        this.filler9 = filler9;
    }

    public String getFiller10() {
        return filler10;
    }

    public void setFiller10(String filler10) {
        this.filler10 = filler10;
    }



}
