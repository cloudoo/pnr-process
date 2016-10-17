package com.csair.loong.pnr;

import com.csair.loong.pnr.processor.PnrElderTagProcessor;
import com.csair.loong.pnr.processor.PnrNorTagProcessor;
import com.csair.loong.pnr.processor.Processor;
import com.csair.loong.pnr.processor.UnGzProcessor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cloudoo on 2016/9/23.
 */
public class PnrElderTagApp {
	protected static final Logger log = LoggerFactory.getLogger(PnrElderTagApp.class);

	
	public void nor2Elder(String dir){
		File tDir = new File(dir);
		if(tDir.isDirectory()){
			
			for(File file:tDir.listFiles()){
				
				
			}
			
		}else{
			log.error(dir+"不是路径");
		}
		
	}
	
	
	public static void main(String[] args) {

		String orgFileName = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\2015\\eldprocessor\\CZ_DFP_20151019_1.txt";
		orgFileName = "F:\\FullPNR";
		
//		orgFileName = "F:\\FullPNR_update20161006";
		String unzipFileDir = "S:\\test";

		Processor<File, File> testP = new PnrNorTagProcessor();

		BufferedWriter bw = null;
		try {

			bw = new BufferedWriter(new FileWriter(new File("S:\\error1.log")));

			Processor<File, File> pnrProcessor = new PnrElderTagProcessor();

			List<File> srcFile = findZipFile(orgFileName);

			Processor unzipP = new UnGzProcessor(unzipFileDir);
			Date startDt,endDt = new Date();
			for (File zipFile : srcFile) {
				startDt = new Date();
				List<File> files = (List<File>) unzipP.doit(zipFile.getAbsolutePath());

				for (File orgFile : files) {
					try {
						File eldTagFile = pnrProcessor.doit(testP.doit(orgFile));
						
					} catch (Exception e) {
						log.error("处理失败" + zipFile.getName());
						bw.append(zipFile.getName() + ":processor error");
					}finally{
						if (!orgFile.delete()) {
							log.error("删除失败" + orgFile.getName());
						}
					}
					endDt = new Date();
					log.info("elapse:"+(endDt.getTime()-startDt.getTime())/1000l);

				}

			}

		} catch (IOException e1) {
			log.error("其他错误" + e1.getMessage());
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// orgFileName =
		// "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\2015\\eldprocessor\\CZ_DFP_20151019_1.txt.eldertag";

	}

	public static List<File> findZipFile(String src) {
		List<File> rsList = new ArrayList<File>();
		File file = new File(src);
		if (file.isDirectory()) {

			File[] tempFile = file.listFiles();
			
			for (File temp : tempFile) {
				String fileName = temp.getName();
				String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
				if (temp.isFile() && prefix.equalsIgnoreCase("gz")) {
					rsList.add(temp);
				}
			}

		}
		return rsList;
	}
}
