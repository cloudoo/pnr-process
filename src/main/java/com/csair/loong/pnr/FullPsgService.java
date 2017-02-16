package com.csair.loong.pnr;

import java.io.File;
import java.util.List;

import com.csair.loong.dao.FullPnrDao;
import com.csair.loong.domain.FullPassengerInfo;
import com.csair.loong.pnr.processor.FullPsgInfo2FileProcessor;
import com.csair.loong.pnr.processor.FullPsgInfoBuilder;
import com.csair.loong.pnr.processor.FullPsgInfoFile2HbaseProcessor;
import com.csair.loong.pnr.processor.PnrSegInfoBuilder;
import com.csair.loong.pnr.processor.ProcessorChain;

public class FullPsgService {

	private ProcessorChain chain;

	public FullPsgService() {

		chain = new ProcessorChain()
				.addProcessor(new FullPsgInfoFile2HbaseProcessor(
						new FullPnrDao()));

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
	     if(prefix.equalsIgnoreCase("process")){
	    	 
	    	 return false;
	    	 
	     }
	     
		 Object object = chain.doit(file);

		return (Boolean) object;
	}

	public static void main(String[] args) {
		FullPsgService fps = new FullPsgService();
	    String fileDir = "S:\\pnrFullPsgOrgFile";
		if(args.length>1){
			fileDir = args[0];
		}
		File files = new File(fileDir);
		if(files.isDirectory()){
			File[] tFiles = files.listFiles();
			for(File file:tFiles){
				fps.save(file);
			}
		}
	}

}
