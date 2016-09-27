package com.csair.loong.pnr;

import com.csair.loong.pnr.processor.PnrElderTagProcessor;
import com.csair.loong.pnr.processor.PnrNorTagProcessor;
import com.csair.loong.pnr.processor.Processor;

import java.io.File;

/**
 * Created by cloudoo on 2016/9/23.
 */
public class PnrElderTagApp {

    public static void main(String[] args){

        String orgFileName = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\2015\\eldprocessor\\CZ_DFP_20151019_1.txt";
        Processor<File,File> testP = new PnrNorTagProcessor();
//        File orgFile = new File(orgFileName);
//        System.out.print(orgFile.getAbsolutePath());
//        File resultFile = testP.doit(new File(orgFileName));

        Processor<File,File> pnrProcessor = new PnrElderTagProcessor();
//        orgFileName = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\2015\\eldprocessor\\CZ_DFP_20151019_1.txt.eldertag";
        File eldTagFile = pnrProcessor.doit(
                testP.doit(new File(orgFileName))
        );
    }
}
