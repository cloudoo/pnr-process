package com.csair.loong.pnr.processor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnGzProcessor implements Processor<List<File>, String> {
	protected static final Logger log = LoggerFactory.getLogger(UnGzProcessor.class);
	private static final int buffer = 2048;
	
	private String destPath;
	public UnGzProcessor(String destPath){
		this.destPath = destPath;
	}

	@Override
	public List<File> doit(String fileName) {
		List<File> fileList = new ArrayList<File>();
		File srcFile = new File(fileName);
		int count = -1;
		int index = -1;

		
		if(destPath==null){
			destPath = srcFile.getParent();
		}
	 
		
		log.info("开始解压文件" + fileName + "到：" + destPath);
		
		try {
			BufferedOutputStream bos = null;
			
			FileInputStream fis = new FileInputStream(fileName);
			GZIPInputStream  zis = new GZIPInputStream (new BufferedInputStream(fis));
			int n=0;
			
			 // 使用GZIPInputStream包装InputStream流，使其具有解压特性
	          BufferedReader in2 = new BufferedReader(new InputStreamReader(zis));
	          String s;
	          
	          fileName = destPath + "//" + srcFile.getName();

				File f = new File(fileName);
				f.createNewFile();
				FileOutputStream fos = new FileOutputStream(f);
				
	          bos = new BufferedOutputStream(fos, buffer);
	          byte data[] = new byte[buffer];
	          
	          while ((count = zis.read(data, 0, buffer)) != -1) {
					bos.write(data, 0, count);
				}

				bos.flush();
				bos.close();
				fileList.add(f);
	          
			

			zis.close();

		} catch (Exception e) {
			log.error("文件解压错误！", e);
		}
		
//		if(this.processor!=null){
//			this.processor.doit(fileList);
//		}
		
		return fileList;
	}

}
