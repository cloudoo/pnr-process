package com.csair.loong.ssim;

import com.csair.loong.service.FileReader;
import com.csair.loong.service.TxtFormaterService;

import java.util.List;

/**
 * Created by cloudoo on 2016/9/22.
 */
public class SsimFileReader extends FileReader{

    private TxtFormaterService<SsimInfo> txtFormaterService;

    public SsimFileReader(String fileName) {
        super(fileName);
        txtFormaterService = new SsimFileTxtFormat();
    }

    @Override
    public void process(String line) {
         txtFormaterService.process(line);
    }

    class SsimFileTxtFormat extends TxtFormaterService<SsimInfo>{

        @Override
        public boolean isTitle(String str) {
            String type = str.substring(0,1);
            if(type.equalsIgnoreCase(SsimInfo.HEADER))
              return true;
            else return false;
        }

        @Override
        public boolean isTail(String str) {

            return false;
        }

        @Override
        public boolean isSegStart(String str) {
            String type = str.substring(0,1);
            if(type.equalsIgnoreCase(SsimInfo.FLIGHT_LEG_RECORD))
                return true;
            else return false;
        }
    }


}
