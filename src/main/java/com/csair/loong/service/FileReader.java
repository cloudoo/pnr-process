package com.csair.loong.service;

import com.csair.loong.pnr.PnrFileReader;
import com.csair.loong.pnr.processor.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloudoo on 2016/9/21.
 */
public abstract class FileReader<T> implements  Reader{
    private static final Logger log = LoggerFactory.getLogger(PnrFileReader.class);
    private String fileName;

    public FileReader(String fileName) {
        this.fileName = fileName;
    }

    public abstract void process(String line);

    public void reg() {

        BufferedReader br = null;
        BufferedWriter wr = null;
        String line = null;
        List<Object> objects = null;


        try {

            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "GBK"));
            wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName + ".error")), "UTF-8"));

            while ((line = br.readLine()) != null) {
                //解析为响应的类
                try {
                    //处理每行记录
                    process(line);

                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("[FileReader]解析异常！" + line + "\n\r" + e.getStackTrace());
                    wr.write("fail," + line + "\b\n");
                }

            }

        } catch (FileNotFoundException e) {
            log.error("[FileReader][fileName=" + fileName + "]没有找到", e);
        } catch (IOException e) {
            log.error("[FileReader][fileName=" + fileName + "] io错误", e);
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("[FileReader][fileName=" + fileName + "] br关闭错误", e);
                }
            }
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException e) {
                    log.error("[FileReader][fileName=" + fileName + "] wr关闭错误", e);
                }
            }
        }
        //可能需要关闭资源

    }

}
