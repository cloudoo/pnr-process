package com.csair.loong.service;

import com.csair.loong.pnr.old.PnrFileReader;
import com.csair.loong.pnr.processor.Processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloudoo on 2016/9/21.
 */
public abstract class FileReader implements  Reader{
    private static final Logger log = LoggerFactory.getLogger(FileReader.class);
    protected File file;

    private String encode = "GBK";
    public FileReader(String fileName) {
        this.file = new File(fileName);
    }
    
    public FileReader(File file) {
        this.file = file;
    }
    public void setEncode(String encode){
    	this.encode = encode;
    }

    public abstract void process(int index,String line);

    public void reg() {

        BufferedReader br = null;
        BufferedWriter wr = null;
        String line = null;
        List<Object> objects = null;
        
        int index = 0;

        try {

            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encode));//默认应该是GBK格式
            wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(file.getAbsolutePath() + ".error")), "UTF-8"));

            while ((line = br.readLine()) != null) {
                //解析为响应的类
                try {
                    //处理每行记录
                    process(index,line);
                    
                } catch (Exception e) {
                    log.error("[FileReader]解析异常！ index:"+index+"line:" + line + "\n\r" + e.getStackTrace());
                    wr.write("fail," + line + "\b\n");
                }
                
                index ++;
            }

        } catch (FileNotFoundException e) {
            log.error("[FileReader][fileName=" + file.getAbsolutePath() + "]没有找到", e);
        } catch (IOException e) {
            log.error("[FileReader][fileName=" + file.getAbsolutePath() + "] io错误", e);
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("[FileReader][fileName=" + file.getAbsolutePath() + "] br关闭错误", e);
                }
            }
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException e) {
                    log.error("[FileReader][fileName=" + file.getAbsolutePath() + "] wr关闭错误", e);
                }
            }
        }
        //可能需要关闭资源
        
    }

}
