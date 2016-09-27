package com.csair.loong.service;

import com.csair.loong.domain.FullPassengerInfo;
import com.csair.loong.domain.PnrInfo;
import com.csair.loong.domain.PnrSegInfo;
import com.csair.loong.pnr.FullPsgInfoBuilder;
import com.csair.loong.pnr.processor.FullPsgInfoProcessor;
import com.csair.loong.pnr.processor.Processor;

import java.util.List;

/**
 * Created by cloudoo on 2016/9/21.
 */
public interface ObjectCreator<T,K> {
    public List<K> create(List<T> list) ;
}
