package com.csair.loong.pnr.processor;

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

import com.csair.loong.dao.FullPnrDao;
import com.csair.loong.domain.FullPassengerInfo;

public class FullPsgInfoFile2HbaseProcessor implements Processor<Boolean, File> {
	private static final Logger log = LoggerFactory
			.getLogger(FullPsgInfoFile2HbaseProcessor.class);

	FullPnrDao dao;

	public FullPsgInfoFile2HbaseProcessor(FullPnrDao dao) {
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
					datas.add(strAddQuotes(line));
				}
			}
		} catch (FileNotFoundException e) {
			log.error("FullPsgInfoFile2HbaseProcessor:file readed error["
					+ e.getLocalizedMessage() + "]");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("FullPsgInfoFile2HbaseProcessor:data readed error["
					+ e.getLocalizedMessage() + "]");
			e.printStackTrace();
		}
		
		if(dao!=null){
			
			
			List<String> tempList = null;
			int fromIndex=0,toIndex=10000;
			while(toIndex<=datas.size()){
				tempList = datas.subList(fromIndex, toIndex);
				log.info("准备批量插入："+tempList.size());
				if(dao.insertStr(tempList)){
					log.info("successed!:insert from"+fromIndex+" to"+toIndex+"");
				}else{
					log.info("falied!:insert from"+fromIndex+" to"+toIndex+"");
				}
				fromIndex = toIndex;
				if((toIndex+10000)<datas.size()){
					toIndex = toIndex+10000;
				}else{
					toIndex = datas.size();
				}
			}
		   
			 
			return true;
		
		}
		
		return false;
	}
	
	public String strAddQuotes(String str){
		str = str.replaceAll(",", "','");
		return "'"+str+"'";
	}
	
	public static void main(String[] args){
		FullPsgInfoFile2HbaseProcessor pp = new FullPsgInfoFile2HbaseProcessor(new FullPnrDao());
		
		File file = new File("S:\\temp1\\CZ_DFP_20161008_1.txt.gz.processor");
//		
		pp.doit(file);
//		String str = "ML7SKD,20141031,,,,1,CHIA/LILENG,,,,";
//		System.out.println(pp.strAddQuotes(str));
		
	}

}
