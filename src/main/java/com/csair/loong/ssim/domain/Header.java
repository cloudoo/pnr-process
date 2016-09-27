package com.csair.loong.ssim.domain;

import com.csair.loong.ssim.SsimInfo;

/**
 * Created by cloudoo on 2016/9/22.
 */
public class Header extends SsimInfo{
    private String type;
    private String title;
    private String seasonsNumber;
    private String dtSerialNum;
    private String recordSerialNum;

    public Header(String str){
        type = str.substring(0,1);
        title = str.substring(1,35);
        seasonsNumber = str.substring(40,41);
        dtSerialNum = str.substring(191,194);
        recordSerialNum = str.substring(194,200);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeasonsNumber() {
        return seasonsNumber;
    }

    public void setSeasonsNumber(String seasonsNumber) {
        this.seasonsNumber = seasonsNumber;
    }

    public String getDtSerialNum() {
        return dtSerialNum;
    }

    public void setDtSerialNum(String dtSerialNum) {
        this.dtSerialNum = dtSerialNum;
    }

    public String getRecordSerialNum() {
        return recordSerialNum;
    }

    public void setRecordSerialNum(String recordSerialNum) {
        this.recordSerialNum = recordSerialNum;
    }

    public String toString(){
        return type+","+title+","+seasonsNumber+","+dtSerialNum+","+recordSerialNum;
    }



}
