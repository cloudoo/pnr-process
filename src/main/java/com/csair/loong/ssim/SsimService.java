package com.csair.loong.ssim;

import com.csair.loong.service.FileReader;
import com.csair.loong.service.FileWriter;
import com.csair.loong.service.TxtFormaterService;
import com.csair.loong.service.Writer;

/**
 * Created by cloudoo on 2016/9/22.
 */
public class SsimService {

    private FileReader<SsimInfo> fileReader;
    private Writer writer;

    public void setFileReader(FileReader<SsimInfo> fileReader){
        this.fileReader = fileReader;
    }

    public void setWriter(Writer writer){
        this.writer = writer;
    }

    public SsimService(String fileName){
        fileReader = new SsimFileReader(fileName);
        TxtFormaterService<SsimInfo> txtFormaterService = new SsimFormaterService();

    }

    public void test(){

        fileReader.reg();

    }
}
