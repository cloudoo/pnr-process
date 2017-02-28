package com.csair.loong.pnr;

import com.csair.loong.dao.FullPnrDao;
import com.csair.loong.pnr.processor.FullPsgInfoBuilder;
import com.csair.loong.pnr.processor.PnrSegInfoBuilder;
import com.csair.loong.pnr.processor.ProcessorChain;
import com.csair.loong.processor.CSVFile2HbaseProcessor;

/**
 * Created by cloudoo on 2016/11/23.
 */
public class PnrDivApp {
    private ProcessorChain chain;

    public PnrDivApp() {

        chain = new ProcessorChain().addProcessor(new PnrSegInfoBuilder())
                .addProcessor(new FullPsgInfoBuilder());

    }
}
