package com.csair.bi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * Created by cloudoo on 2015/8/7.
 */
public class FileUtils {

    protected static final Logger log = LoggerFactory.getLogger(FileUtils.class);
    public static final int BUFSIZE = 1024 * 8;


    public static String[] listFileName(String dir,String fuxx)   {

        File dirFile = new File(dir);
        File[] files = dirFile.listFiles();
        StringBuffer sb = new StringBuffer();

        for(int i=0;i<files.length;i++){

            String fileName = files[i].getName();
            String fileabPath = files[i].getAbsolutePath();
            String suxx = fileName.split("\\.")[fileName.split("\\.").length-1];

            if(!suxx.equals(fuxx))
                continue;

            sb.append(fileabPath);
            sb.append("|");
        }

        return  sb.toString().split("\\|");
    }

    public static String[] listDir(String dir,String fuxx)   {

         File dirFile = new File(dir);
         File[] files = dirFile.listFiles();
         StringBuffer sb = new StringBuffer();

         for(int i=0;i<files.length;i++){

             String fileName = files[i].getName();
             String fileabPath = files[i].getAbsolutePath();
             String suxx = fileName.split("\\.")[fileName.split("\\.").length-1];

             if(!suxx.equals(fuxx))
                 continue;

             sb.append(fileabPath.substring(0,fileabPath.length()-fuxx.length()-1));
             sb.append("|");
         }

         return  sb.toString().split("\\|");
     }


    public static void moveFile(String dir, String fuxx, String destDir) {
        File dirFile = new File(dir);

        if(dirFile.isFile()) {
            dirFile.renameTo(new File(destDir + "\\" + dirFile.getName()));
            log.info("移动文件[" + dirFile.getAbsolutePath() + "]到" + destDir + "\\" +dirFile.getName());
        }else{


        File[] files = dirFile.listFiles();
        StringBuffer sb = new StringBuffer();


        for(int i=0;i<files.length;i++){
            if(files[i].isFile()){
                log.info("移动第"+i+"个文件["+files[i].getAbsolutePath()+"]");
                files[i].renameTo(new File(destDir + "\\" + files[i].getName()));
//                copyFile(files[i].getAbsolutePath(), destDir + "\\" + files[i].getName());
                log.info("移动第" + i + "个文件[" + files[i].getAbsolutePath() + "]到" + destDir + "\\" + files[i].getName());
//                delFile(files[i].getAbsolutePath());
//                log.info("删除文件["+files[i].getAbsolutePath()+"] 成功");
            }else if(files[i].isDirectory()){
                moveFile(files[i].getAbsolutePath(), fuxx, destDir);
                delFile(files[i].getAbsolutePath());
                log.info("删除目录[" + files[i].getAbsolutePath()+"] 成功");
            }
        }
        }

    }

    public static void moveFile(String fileName, String destDir) {
        File file = new File(fileName);
            if(file.isFile()) {
                file.renameTo(new File(destDir + "\\" + file.getName()));
                log.info("移动文件[" + file.getAbsolutePath() + "]到" + destDir + "\\" +file.getName());
            }


    }



    public  static void  copyFile(String  oldPath,  String  newPath)  {
        try  {
//           int  bytesum  =  0;
            int  byteread  =  0;
            File  oldfile  =  new  File(oldPath);
            File  newfile  =  new  File(newPath);
            if(!newfile.exists()){
                newfile.createNewFile();
            }
            if  (oldfile.exists())  {  //文件存在时
                InputStream inStream  =  new FileInputStream(oldPath);  //读入原文件
                FileOutputStream fs  =  new  FileOutputStream(newfile);
                byte[]  buffer  =  new  byte[1444];
//               int  length;
                while  (  (byteread  =  inStream.read(buffer))  !=  -1)  {
//                   bytesum  +=  byteread;  //字节数  文件大小
//                   System.out.println(bytesum);
                    fs.write(buffer,  0,  byteread);
                }
                inStream.close();
            }
        }
        catch  (Exception  e)  {
            log.error("复制单个文件操作出错", e);
        }

    }

    public  static void  delFile(String  filePathAndName)  {
        try  {
            String  filePath  =  filePathAndName;
            filePath  =  filePath.toString();
            File  myDelFile  =  new  File(filePath);
            myDelFile.delete();

        }
        catch  (Exception  e)  {

            log.error("删除文件操作出错", e);

        }

    }

    public static void mergeFiles(String outFile, String[] files,int kbSize) {

        FileChannel outChannel = null;
        log.info("Merge " + Arrays.toString(files) + " into " + outFile);
        int fileIndex = 1;
        try {
            outChannel = new FileOutputStream(outFile+"_"+fileIndex).getChannel();
            for(String f : files){

                FileChannel fc = new FileInputStream(f).getChannel();
                ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
                long size = (fc.size()+outChannel.size())/(1024);
                if(size>=kbSize){
                    fileIndex++;
                    outChannel = new FileOutputStream(outFile+"_"+fileIndex).getChannel();
                }
                while(fc.read(bb) != -1){
                    bb.flip();
                    outChannel.write(bb);
                    bb.clear();
                }

                fc.close();
            }
            log.info("Merged!! ");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {if (outChannel != null) {outChannel.close();}} catch (IOException ignore) {}
        }
    }

    public static void main(String[] args){
        FileUtils flp = new FileUtils();
        String[] temp = flp.listDir("E:\\00_temp\\midt\\12月","zip");
        for(String t:temp){
            System.out.printf("i的值为%s,\n", t);
            System.out.flush();

        }


    }
}
