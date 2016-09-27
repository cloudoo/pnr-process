package com.csair.loong.ssim;

import com.csair.loong.pnr.processor.Processor;
import com.csair.loong.service.TxtFormaterService;

import java.util.List;

/**
 * Created by cloudoo on 2016/9/22.
 */
public class SsimFormaterService extends TxtFormaterService<SsimInfo> {

    private List<SsimInfo> ssims;


    @Override
    public boolean isTitle(String str) {
        return false;
    }

    @Override
    public boolean isTail(String str) {
        return false;
    }

    @Override
    public boolean isSegStart(String str) {
        return false;
    }



}
