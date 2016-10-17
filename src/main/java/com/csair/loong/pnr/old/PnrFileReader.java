package com.csair.loong.pnr.old;

import com.csair.loong.domain.PnrInfo;
import com.csair.loong.pnr.processor.Processor;
import com.csair.loong.pnr.processor.UnGzProcessor;
import com.csair.loong.service.PnrInfoFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cloudoo on 2016/9/14.
 */
public class PnrFileReader {
    private static final Logger log = LoggerFactory.getLogger(PnrFileReader.class);
    private String pnrFileName;
    private PnrFullPsgCreator pnrFullPsgCreator;

    public PnrFileReader(String pnrFileName, PnrFullPsgCreator pnrFullPsgCreator) {
        this.pnrFileName = pnrFileName;
        this.pnrFullPsgCreator = pnrFullPsgCreator;
    }

    public static void main(String[] args) {
    	Date startDt = new Date();
    	log.info("-------------------处理开始-------------------");
        String fileName ;
        fileName = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\2015\\CZ_DFP_20151103_1.txt";
        String descDir = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\2015\\";
//        fileName = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\fullpnrtest.txt";
//        fileName = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\2015\\test.txt";
        
        Processor unzipP = new UnGzProcessor(descDir);
        List<File> files = (List<File>)unzipP.doit("D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\2015\\CZ_DFP_20151103_1.txt.gz");
         
        PnrFullPsgCreator pnrFullPsgCreator = new PnrFullPsgCreator(fileName+".process");
        PnrFileReader pnrFileReader = new PnrFileReader(files.get(0).getAbsolutePath(), pnrFullPsgCreator);
        pnrFileReader.reg();
        
        Date endDt = new Date();
        log.info("xxxxxxxxxxxxxxxxxxxxx处理完毕xxxxxxxxxxxxxxxxxxxxxxxxxx");
        log.info("xxxxxxxxxxxxxxxxxxxxx耗费："+(endDt.getTime()-startDt.getTime())/1000+"xxxxxxxxxxxxxxxxxxxxxxxxx");
        try {
            pnrFileReader.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void reg() {

        BufferedReader br = null;
        BufferedWriter wr = null;
        String line = null;
        List<PnrInfo> pnrInfos = null;
        PnrInfoFactory pnrInfoFactory = PnrInfoFactory.getInstance();
//        PnrFileWriter pnrFileWriter = new PnrFileWriter(pnrFileName+".fullpsgInfo");
        try {

            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pnrFileName)),"GBK"));
            wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(pnrFileName + ".error")),"UTF-8"));
//            pnrFullPsgCreator.writePnrInfo(pnrFileName+".fullpsgInfo");
            while ((line = br.readLine()) != null) {
                //解析为响应的类
                try {
                    //根据每行转化为对象

                    PnrInfo pnrInfo = pnrInfoFactory.doit(line);

                    if (pnrInfo == null) {
                        if(line.indexOf("END PNR FILE")>=0){
                            pnrFullPsgCreator.singleRegPnrInfos(pnrInfos);
                        }else{
//                            log.info("[PnrFileReader]解析失败！" + line);
                           // wr.write("fail," + line + "\b\n");
                        }

                    } else {
                        if (pnrInfo.getPartTyp().equalsIgnoreCase(PnrInfo.PNR_HEAD_TYPE)) {
                            //如果是001则处理一个pnr信息
                            if (pnrInfos != null && pnrInfos.size() > 0) {
//                                pnrFullPsgCreator.regPnrInfos(pnrInfos);
                                pnrFullPsgCreator.singleRegPnrInfos(pnrInfos);
                            }
                            pnrInfos = new ArrayList<>();
                            pnrInfos.add(pnrInfo);

                        } else {
                            pnrInfos.add(pnrInfo);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("[PnrFileReader]解析异常！" + line+"\n\r"+e.getStackTrace());
                    wr.write("fail," + line + "\b\n");
                }

            }
            pnrFullPsgCreator.setOver(true);
            pnrFullPsgCreator.closeFile();
        } catch (FileNotFoundException e) {
            log.error("[PnrFileReader][fileName=" + pnrFileName + "]没有找到", e);
        } catch (IOException e) {
            log.error("[PnrFileReader][fileName=" + pnrFileName + "] io错误", e);
        } finally {

            pnrFullPsgCreator.setOver(true);
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("[PnrFileReader][fileName=" + pnrFileName + "] br关闭错误", e);
                }
            }
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException e) {
                    log.error("[PnrFileReader][fileName=" + pnrFileName + "] wr关闭错误", e);
                }
            }
        }

    }


}
