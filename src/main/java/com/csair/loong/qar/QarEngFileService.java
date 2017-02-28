package com.csair.loong.qar;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.loong.dao.FullPnrDao;
import com.csair.loong.pnr.FullPsgService;
import com.csair.loong.pnr.processor.ProcessorChain;
import com.csair.loong.processor.CSVFile2HbaseProcessor;
import com.csair.loong.qar.dao.QarEngDao;
import com.csair.loong.qar.processor.QarCSVFile2HbaseProcessor;

public class QarEngFileService {
	private static final Logger log = LoggerFactory
			.getLogger(QarEngFileService.class);

	private ProcessorChain chain;

	public QarEngFileService() {

		chain = new ProcessorChain()
				.addProcessor(new QarCSVFile2HbaseProcessor(
						new QarEngDao()));

	}

	/**
	 * 从 csv文件保存到数据库中
	 * 
	 * @param file
	 * @return
	 */
	public boolean save(File file) {
		
		 String fileName=file.getName();
	     String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
	     if(!prefix.equalsIgnoreCase("ENG")){
	    	 
	    	 return false;
	    	 
	     }
	     
		 Object object = chain.doit(file);

		return (Boolean) object;
	}

	public static void main(String[] args) {
		QarEngFileService fps = new QarEngFileService();
	    String fileDir = "S:\\qar\\process";
		if(args.length>1){
			fileDir = args[0];
		}
		File files = new File(fileDir);
		if(files.isDirectory()){
			File[] tFiles = files.listFiles();
			for(File file:tFiles){
				log.info(file.getName());
				fps.save(file);
			}
		}
	}
}
