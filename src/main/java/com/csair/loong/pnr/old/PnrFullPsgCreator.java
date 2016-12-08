package com.csair.loong.pnr.old;

import com.csair.loong.domain.FullPassengerInfo;
import com.csair.loong.domain.PnrInfo;
import com.csair.loong.domain.PnrSegInfo;
import com.csair.loong.pnr.processor.FullPsgInfoBuilder;
import com.csair.loong.pnr.processor.PnrSegInfoBuilder;
import com.csair.loong.pnr.processor.Processor;
import com.csair.loong.service.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cloudoo on 2016/9/20.
 */
 
public class PnrFullPsgCreator {
    private static final Logger log = LoggerFactory.getLogger(PnrFullPsgCreator.class);
//    Queue<PnrSegInfo> pnrSegInfos = new ConcurrentLinkedQueue<>();
    Queue<FullPassengerInfo> fullPassengerInfos = new ConcurrentLinkedQueue<>();
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private boolean isOver = false;
    private FileWriter pnrFileWriter;

    public PnrFullPsgCreator(String fileName){
        pnrFileWriter = new FileWriter(fileName);
    }

    public void singleRegPnrInfos(List<PnrInfo> pnrInfoList){

        Processor processor = new PnrSegInfoBuilder();
        Processor processor1 = new FullPsgInfoBuilder();
        PnrSegInfo pnrSegInfo =  (PnrSegInfo) processor.doit(pnrInfoList);
        if(pnrSegInfo!=null&&pnrSegInfo.getHeader()!=null){
            List<FullPassengerInfo> fullPassengerInfoList = (List<FullPassengerInfo>)processor1.doit(pnrSegInfo);
            if(fullPassengerInfoList!=null)
                for(FullPassengerInfo fullPassengerInfo:fullPassengerInfoList){
                    pnrFileWriter.writer(fullPassengerInfo.toString());
                }
        }

    }

    public void closeFile(){

        pnrFileWriter.close();

    }


    public void regPnrInfos(final List<PnrInfo> pnrInfoList){

        cachedThreadPool.execute( new Thread() {
            @Override
            public void run() {
                log.info("转化线程"+this.hashCode()+"启动");
                Processor processor = new PnrSegInfoBuilder();
                Processor processor1 = new FullPsgInfoBuilder();
                PnrSegInfo pnrSegInfo =  (PnrSegInfo) processor.doit(pnrInfoList);
                fullPassengerInfos.addAll((List<FullPassengerInfo>) processor1.doit(pnrSegInfo));


                log.info("转化线程"+this.hashCode()+"结束");

            }
        });

    }

    public void writePnrInfo(final String fileName){

        cachedThreadPool.execute( new Thread()  {
            @Override
            public void run() {
                log.info("写数据线程"+this.hashCode()+"启动");
                //
                FileWriter pnrFileWriter = new FileWriter(fileName);
                int i =0;
                while(1==1){
                    try {
                        this.sleep(10);
                        if(!isOver){
                            FullPassengerInfo fullPassengerInfo = fullPassengerInfos.poll();
                            if(fullPassengerInfo!=null){
                                log.info("写数据线程"+this.hashCode()+"获取元素"+(++i));
                                pnrFileWriter.writer(fullPassengerInfo.toString());
                            }else{
                                log.info("写数据线程"+this.hashCode()+"没有获取到");
                            }
                        }else{
                            pnrFileWriter.close();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public static void main(String[] args){
        PnrFullPsgCreator psgCreator = new PnrFullPsgCreator("\"D:\\\\03_工作文件\\\\02_研究院\\\\01_项目\\\\05_PNR数据\\\\02_数据\\\\fullpnrtest.txt.fulltest\"");
        psgCreator.regPnrInfos(null);
        psgCreator.writePnrInfo("D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\leisy.txt");

    }
}
