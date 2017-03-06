package com.csair.loong.ssim;

import com.csair.loong.pnr.processor.Processor;

/**
 * Created by cloudoo on 2016/9/22.
 */
public class SsimLineParse implements Processor<String,SsimInfo> {
    @Override
    public SsimInfo doit(String s) {
        String[] lines = s.split(",");
        return null;
    }
}
