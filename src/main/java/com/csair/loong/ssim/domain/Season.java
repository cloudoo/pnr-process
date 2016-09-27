package com.csair.loong.ssim.domain;

import com.csair.loong.ssim.SsimInfo;

/**
 * Created by cloudoo on 2016/9/22.
 */
public class Season extends SsimInfo{
    private String type = SsimInfo.SEASON_RECORD;
    private String timeMode;// U = UTC L = Local Time
    private String airlineDesignator;
    private String compSchStdVerNum;
    private String season;//
//    from 15	14位长度	航班计划有效期
    private String schValStartDt;//
    private String schValEndDt;//
    private String createDt;
    private String dataTitle;
    private String releaseDt;//
    private String schStatus;//Always C (Complete)

    private String etType;//ET = default for Carrier is that flight legs are Electronic Ticketing Candidates
                             // EN = default for Carrier is that flight legs are Not Electronic Ticketing Candidates
    private String createTm;//hhmm
    private String recordSerialNum;

    public Season(String str){
        timeMode=str.substring(1,2);
        airlineDesignator = str.substring(2,5);
        compSchStdVerNum = str.substring(6,10);
        season = str.substring(10,13);
        schValStartDt = str.substring(14,20);
        schValEndDt = str.substring(20,26);
        createDt = str.substring(28,35);
        dataTitle = str.substring(35,64);
        releaseDt = str.substring(64,71);
        schStatus = str.substring(71,72);

    }


}
