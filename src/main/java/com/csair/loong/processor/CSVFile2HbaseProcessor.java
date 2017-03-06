package com.csair.loong.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.loong.dao.AbstractHbaseDao;
import com.csair.loong.dao.FullPnrDao;
import com.csair.loong.domain.FullPassengerInfo;
import com.csair.loong.pnr.processor.Processor;

public class CSVFile2HbaseProcessor implements Processor<File,Boolean> {
	
	private static final Logger log = LoggerFactory
			.getLogger(CSVFile2HbaseProcessor.class);

	private AbstractHbaseDao dao;

	public CSVFile2HbaseProcessor(AbstractHbaseDao dao) {
		this.dao = dao;
	}

	public void init() {
		dao.init();
	}

	@Override
	public Boolean doit(File file) {

		BufferedReader br = null;
		List<String> datas = new ArrayList<String>();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
			String line = null;
			while((line = br.readLine())!=null){
				if(StringUtils.isNotBlank(line)){
					String someStr = "";
					try{
						someStr = strAddQuotes(line);
					}catch(Exception e){
						log.debug("["+file.getName()+"]["+line+"]");
					}
					datas.add(someStr);
				}
			}
		} catch (FileNotFoundException e) {
			log.error("CSVFile2HbaseProcessor readed error["
					+ e.getLocalizedMessage() + "]");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("CSVFile2HbaseProcessor readed error["
					+ e.getLocalizedMessage() + "]");
			e.printStackTrace();
		}
		
		if(dao!=null){
			
			
			List<String> tempList = null;
			
			int fromIndex=0,toIndex=1,step = 10000;
			
			for(;toIndex<datas.size();toIndex+=step){
				
			
				tempList = datas.subList(fromIndex, toIndex);
				log.info("准备批量插入："+tempList.size());
				
				if(dao.insertStr(tempList)){
					log.info("successed!:insert from"+fromIndex+" to"+toIndex+"");
				}else{
					log.info("falied!:insert from"+fromIndex+" to"+toIndex+"");
				}
				fromIndex = toIndex;
			}
			
			if(fromIndex<datas.size()){
				
				toIndex = datas.size();
				tempList = datas.subList(fromIndex, toIndex);
				log.info("准备批量插入："+tempList.size());
				if(dao.insertStr(tempList)){
					log.info("successed!:insert from"+fromIndex+" to"+toIndex+"");
				}else{
					log.info("falied!:insert from"+fromIndex+" to"+toIndex+"");
				}
			}
			
			
			
//			while(toIndex<datas.size()){
//				//FIXME:如果小于10000行则有问题
//				tempList = datas.subList(fromIndex, toIndex);
//				
//				log.info("准备批量插入："+tempList.size());
//				
//				if(dao.insertStr(tempList)){
//					log.info("successed!:insert from"+fromIndex+" to"+toIndex+"");
//				}else{
//					log.info("falied!:insert from"+fromIndex+" to"+toIndex+"");
//				}
//				
//				fromIndex = toIndex;
//				if((toIndex+step)<datas.size()){
//					toIndex = toIndex+step;
//				}else{
//					toIndex=datas.size();
//				}
//				
//			}
		   
			dao.close();
			
			return true;
		
		}
		
		return false;
	}
	
	public String strAddQuotes(String str){
		str = str.replaceAll(",", "','");
		return "'"+str+"'";
	}
	
	public static void main(String[] args){
//		CSVFile2HbaseProcessor pp = new CSVFile2HbaseProcessor(new FullPnrDao());
//		
//		File file = new File("S:\\temp1\\CZ_DFP_20161008_1.txt.gz.processor");
//		
//		pp.doit(file);
		
		String test = "a,b,w,d,d,e,re,e,r,e,re,er,er,e,r,er";
		
		 
		
//		String str = "ML7SKD,20141031,,,,1,CHIA/LILENG,,,,";
//		System.out.println(pp.strAddQuotes(str));
		
	}

}
