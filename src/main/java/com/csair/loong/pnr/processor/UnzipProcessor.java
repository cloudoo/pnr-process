package com.csair.loong.pnr.processor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnzipProcessor implements Processor<String,List<File>> {
	protected static final Logger log = LoggerFactory.getLogger(UnzipProcessor.class);
	private static final int buffer = 2048;
	
	private String destPath;
	public UnzipProcessor(String destPath){
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
			ZipEntry entry = null;
			FileInputStream fis = new FileInputStream(fileName);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));

			while ((entry = zis.getNextEntry()) != null) {
				byte data[] = new byte[buffer];

				String temp = entry.getName();
				index = temp.lastIndexOf("/");
				if (index > -1)
					temp = temp.substring(index + 1);
				temp = destPath + "//" + temp;

				File f = new File(temp);
				f.createNewFile();

				FileOutputStream fos = new FileOutputStream(f);
				bos = new BufferedOutputStream(fos, buffer);

				while ((count = zis.read(data, 0, buffer)) != -1) {
					bos.write(data, 0, count);
				}

				bos.flush();
				bos.close();
				fileList.add(f);
			}

			zis.close();

		} catch (Exception e) {
			log.error("文件解压错误！", e);
		}

		return fileList;
	}

}
