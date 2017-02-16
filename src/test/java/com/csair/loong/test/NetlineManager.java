package com.csair.loong.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NetlineManager {
	protected static final Logger log = LoggerFactory.getLogger(NetlineManager.class);
	
	public void merge(String fileName, String outputFileName)   {
		BufferedReader br = null;
		BufferedWriter writer = null;
		Map<String,List<String>> segMap = new HashMap();
		try {
			String line = "";
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					fileName)));
			log.info("开始处理文本。。。。");
			while((line= br.readLine())!=null){
				String[] arps = line.split(",");
				String depArp = arps[0];
				String arvArp = arps[1];
				String id = arps[2];
				if(!segMap.containsKey(id)){
					List<String> tempArp =  new ArrayList<String>();
					tempArp.add(depArp);
					tempArp.add(arvArp);
					segMap.put(id, tempArp);
					
				}else{
					List<String> segList = segMap.get(id);
					if(segList.size()==0){
						segList.add(depArp);
						segList.add(arvArp);
					}else{
						if(segList.get(segList.size()-1).equals(depArp)){
							segList.add(arvArp);
						}
					}
				}
			}
			log.info("处理文本结束。。。。");
			log.info("开始输出结果。。。。");
			writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outputFileName), "UTF-8"));
			
			Iterator<String> iter = segMap.keySet().iterator();
			while(iter.hasNext()){
				String id = iter.next();
				List<String> arpSegs = segMap.get(id);
				String arpSegStr = arpSegs.toString();
				arpSegStr = arpSegStr.replaceAll(",", "-").replace("[", "").replace("]", "").replace(" ", "") ;
				String lines = id+","+ arpSegStr;
				
                writer.write(lines+"\n");
			}
			log.info("is over。。。。");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) {
		NetlineManager nlm = new NetlineManager();
		nlm.merge("S:\\test\\route(1).txt", "S:\\test\\result.txt");

	}

}
