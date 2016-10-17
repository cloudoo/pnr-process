package com.csair.loong.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by cloudoo on 2016/9/19.
 */
public class FileWriter implements Writer{
    private static final Logger log = LoggerFactory.getLogger(FileWriter.class);
    private File file = null;
    private BufferedWriter wr = null;
    public FileWriter(String fileName)  {

        file = new File(fileName);
        try {
            wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));

        } catch (FileNotFoundException e) {
            log.error("[FileWriter]:找不到文件，fileName="+fileName);
        } catch (UnsupportedEncodingException e) {
            log.error("[FileWriter]:UnsupportedEncodingException，fileName="+fileName);
        }

    }
    
    public void writer(Object someStr){

        if(wr!=null){
            try {

                wr.write(someStr.toString()+"\n");
//                log.info(someStr.toString());
            } catch (IOException e) {
                log.error("[FileWriter]:写入失败，data=["+someStr+"]");
            }
        }
    }

    public void close(){
            if(wr!=null){
                try {
                    wr.close();
                } catch (IOException e) {
                    log.error("[FileWriter]:流关闭错误");
                }
            }
    }

}
