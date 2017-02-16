package com.csair.loong.pnr.old;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.loong.pnr.processor.Processor;
import com.csair.loong.pnr.processor.UnGzProcessor;

/**
 * 从原始PNR文件中获取有效旅客信息
 * 针对文档<FULL PNR 数据导入规则v1.2.doc>处理规则
 * 服务类
 * 
 * @author cloudoo
 *
 */
public class PnrFullPsgService {
	private static final Logger log = LoggerFactory
			.getLogger(PnrFullPsgService.class);

	public static void main(String[] args) {
		PnrFullPsgService pnrService = new PnrFullPsgService();
		String dir = "S:\\test";
		
		pnrService.createFormZipDir(dir, "gz");

	}
	
	public void createFormZipDir(String zipDir, String type) {

		List<File> files = findZipFile(zipDir, type);
		String unzipFileDir = "S:\\temp2";
		Processor unzipP = new UnGzProcessor(unzipFileDir);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File(
					"S:\\newpnrprocesserror.log")));
			Date startDt,endDt = new Date();
			for (File zipFile : files) {
				log.info("start processing...." + zipFile.getAbsolutePath());
				startDt = new Date();
				List<File> temps = (List<File>) unzipP.doit(zipFile
						.getAbsolutePath());
				for (File tFile : temps) {
					try {
						PnrFullPsgCreator pnrFullPsgCreator = new PnrFullPsgCreator(
								tFile.getAbsolutePath() + ".process");
						PnrFileReader pnrFileReader = new PnrFileReader(
								tFile.getAbsolutePath(), pnrFullPsgCreator);
						pnrFileReader.reg();
						log.info("end processing...." + tFile.getAbsolutePath());
						
					} catch (Exception e) {
						log.error("处理失败" + zipFile.getName());
						bw.append(zipFile.getName() + ":processor error");
					} finally {
						if (!tFile.delete()) {
							log.error("删除失败" + tFile.getName());
						}
					}
				}
				endDt = new Date();
				log.info("elapse:"+(endDt.getTime()-startDt.getTime())/1000l);
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
	}
	
	public static List<File> findZipFile(String src, String off) {
		List<File> rsList = new ArrayList<File>();
		File file = new File(src);
		if (file.isDirectory()) {

			File[] tempFile = file.listFiles();
			for (File temp : tempFile) {
				String fileName = temp.getName();
				String prefix = fileName
						.substring(fileName.lastIndexOf(".") + 1);
				if (temp.isFile() && prefix.equalsIgnoreCase(off)) {
					rsList.add(temp);
				}
			}

		}
		return rsList;
	}
}
