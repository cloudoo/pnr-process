package com.csair.loong.pnr;

import com.csair.loong.pnr.processor.PnrDivProcessor;
import com.csair.loong.pnr.processor.PnrElderTagProcessor;
import com.csair.loong.pnr.processor.PnrNorTagProcessor;
import com.csair.loong.pnr.processor.Processor;
import com.csair.loong.pnr.processor.UnGzProcessor;
import com.csair.loong.pnr.processor.UnzipProcessor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cloudoo on 2016/9/23.
 */
public class PnrElderTagApp {
	 protected static final Logger log = LoggerFactory.getLogger(PnrElderTagApp.class);
	    
    public static void main(String[] args){

        String orgFileName = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\2015\\eldprocessor\\CZ_DFP_20151019_1.txt";
        orgFileName = "F:\\FullPNR";
        String unzipFileDir = "S:\\orgFile";
        
        Processor<File,File> testP = new PnrNorTagProcessor();
//        File orgFile = new File(orgFileName);
//        System.out.print(orgFile.getAbsolutePath());
//        File resultFile = testP.doit(new File(orgFileName));
        
        try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("S:\\error.log")));
		
        
        Processor<File,File> pnrProcessor = new PnrElderTagProcessor();
        
        
        List<File> srcFile = findZipFile(orgFileName);
         
        Processor unzipP = new UnGzProcessor(unzipFileDir);
        
        	
       
        for(File zipFile:srcFile){
        	try{
        	List<File> files = (List<File>)unzipP.doit(zipFile.getAbsolutePath());
        
        		for(File orgFile:files){
            		File eldTagFile = pnrProcessor.doit(
                            testP.doit(orgFile)
                    );
            		if(!orgFile.delete()){
            			log.error("删除失败"+orgFile.getName());
            		}
            	}
        	}catch(Exception e){
            	bw.append(zipFile.getName()+":processor error");
            }
        	
        	
        }
        
        
        } catch (IOException e1) {
        	
		}
//        orgFileName = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\2015\\eldprocessor\\CZ_DFP_20151019_1.txt.eldertag";
        
        
        
    }
    
    
    
    public static List<File> findZipFile(String src){
    	List<File> rsList = new ArrayList<File>();
    	File file = new File(src);
    	if(file.isDirectory()){
    		
    		File[] tempFile = file.listFiles();
    		for(File temp:tempFile){
    			String fileName=temp.getName();
    		      String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
    			if(temp.isFile()&&prefix.equalsIgnoreCase("gz")){
    				rsList.add(temp);
    			}
    		}

    	}
    	
    	
    	return rsList;
    	
    }
}
