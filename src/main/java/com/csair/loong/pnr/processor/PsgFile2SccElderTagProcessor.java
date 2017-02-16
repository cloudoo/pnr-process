package com.csair.loong.pnr.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 从FullPassengerInfo中普通旅客记录中找出有旅客身份证和生日信息的旅客
 * 
 * @author cloudoo
 *
 */
public class PsgFile2SccElderTagProcessor implements Processor<File, File> {
	protected static final Logger log = LoggerFactory
			.getLogger(PsgFile2SccElderTagProcessor.class);
	private File resultFile = null;
	private String fltDt = "";
	SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMdd");
	private Processor<String, String> processor = null;

	/**
	 *
	 * @param resultFile
	 *            结果集文件名
	 * @param fltDt
	 *            某天的航班
	 */
	public PsgFile2SccElderTagProcessor(File resultFile, String fltDt) {
		this.resultFile = resultFile;
		this.fltDt = fltDt;
		if (fltDt.length() > 0)
			processor = new SccElderTagLineProcessor(fltDt);
		else
			processor = new SccElderTagLineProcessor();
	}

	@Override
	public File doit(File dir) {
		BufferedWriter bw = null;
		BufferedReader br = null;

		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(resultFile, true), "UTF-8"));

			if (dir.isDirectory()) {
				for (File file : dir.listFiles()) {

					if (checkFile(file)) {
						br = new BufferedReader(new InputStreamReader(
								new FileInputStream(file), "UTF-8"));
						String line = "";
						while ((line = br.readLine()) != null) {

							// String temp = filterElderPsg(line,fltDt);
							String temp = (String) processor.doit(line);

							if (StringUtils.isNotEmpty(temp)) {

								bw.append(temp + "\n");

							}

						}
					}

				}
			} else if (checkFile(dir)) {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(dir), "UTF-8"));
				String line = "";
				while ((line = br.readLine()) != null) {
					if (!StringUtils.isBlank(line)) {

//						String temp = filterElderPsg(line, fltDt);
						String temp = (String) processor.doit(line);
						
						if (StringUtils.isNotEmpty(temp)) {
							bw.append(temp + "\n");
						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("noknow error:" + e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultFile;

	}

	public boolean checkFile(File tempFile) {
		String fileName = tempFile.getName();
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (prefix.equalsIgnoreCase("processor")) {
			return true;
		} else
			return false;

	}

	public static void main(String[] args) {

		PsgFile2SccElderTagProcessor set = new PsgFile2SccElderTagProcessor(
				new File("S:\\test\\scc_20161208.tag"), "20151020");

		String dir = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\2016\\test\\";
		
		dir = "S:\\test";
		
		File file = new File(dir);
		
		if (file.isDirectory()) {
			for (File tempFile : file.listFiles()) {

				if (set.checkFile(tempFile)) {
					log.info(tempFile.getName() + " is process....");
					set.doit(tempFile);
				}
			}
		}

		// String test = "fwefwef/";
		// String test2[] = test.split("/");
		// System.out.print(test2.length);
	}

}
