package com.csair.loong.pnr;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.loong.domain.PnrInfo;
import com.csair.loong.pnr.processor.FullPsgInfo2FileProcessor;
import com.csair.loong.pnr.processor.FullPsgInfoBuilder;
import com.csair.loong.pnr.processor.PnrSegInfoBuilder;
import com.csair.loong.pnr.processor.Processor;
import com.csair.loong.pnr.processor.ProcessorChain;
import com.csair.loong.pnr.processor.UnGzProcessor;
import com.csair.loong.service.FileReader;
import com.csair.loong.service.FileWriter;
import com.csair.loong.service.PnrInfoFactory;
import com.csair.loong.service.Writer;

public class PnrFile2PnrFullPsgTransfer extends FileReader {
	private static final Logger log = LoggerFactory
			.getLogger(PnrFile2PnrFullPsgTransfer.class);
	private ProcessorChain chain;
	private List<PnrInfo> pnrInfos;
	private Writer pnrFileWriter;

	public PnrFile2PnrFullPsgTransfer(String fileName) {
		super(fileName);
	}

	public PnrFile2PnrFullPsgTransfer(File file) {
		super(file);
	}

	public void init() {

		pnrFileWriter = new FileWriter(super.file.getAbsolutePath()
				+ ".processor");
		chain = new ProcessorChain().addProcessor(new PnrSegInfoBuilder())
				.addProcessor(new FullPsgInfoBuilder())
				.addProcessor(new FullPsgInfo2FileProcessor(pnrFileWriter));

	}

	public void closeRes() {
		if (pnrFileWriter != null) {
			pnrFileWriter.close();
		}
	}

	@Override
	public void process(int index,String line) {
		PnrInfoFactory pnrInfoFactory = PnrInfoFactory.getInstance();
		PnrInfo pnrInfo = pnrInfoFactory.doit(line);

		if (pnrInfo == null) {
			if (line.indexOf("END PNR FILE") >= 0) {
				chain.doit(pnrInfos);
			} else {
				// log.info("[PnrFileReader]解析失败！" + line);
				// wr.write("fail," + line + "\b\n");
			}

		} else {
			if (pnrInfo.getPartTyp().equalsIgnoreCase(PnrInfo.PNR_HEAD_TYPE)) {
				// 如果是001则处理一个pnr信息
				if (pnrInfos != null && pnrInfos.size() > 0) {
					chain.doit(pnrInfos);
				}
				pnrInfos = new ArrayList<>();
				pnrInfos.add(pnrInfo);
			} else {
				pnrInfos.add(pnrInfo);
			}
		}
	}

	public static List<File> findZipFile(String src) {
		List<File> rsList = new ArrayList<File>();
		File file = new File(src);
		if (file.isDirectory()) {

			File[] tempFile = file.listFiles();
			for (File temp : tempFile) {
				String fileName = temp.getName();
				String prefix = fileName
						.substring(fileName.lastIndexOf(".") + 1);
				if (temp.isFile() && prefix.equalsIgnoreCase("gz")) {
					rsList.add(temp);
				}
			}

		}
		return rsList;
	}

	public static void main(String[] args) {
		String srcDir = "S:\\test";
		String descDir = "S:\\temp1";
		
		Processor unzipP = new UnGzProcessor(descDir);
		List<File> orgZipFiles = findZipFile(srcDir);
		Date startDt, endDt = new Date();
		for (File tempFile : orgZipFiles) {
			startDt = new Date();
			List<File> files = (List<File>) unzipP.doit(tempFile
					.getAbsolutePath());

			for (File tFile : files) {

				PnrFile2PnrFullPsgTransfer pnrTransfer = new PnrFile2PnrFullPsgTransfer(
						tFile);
				pnrTransfer.init();
				pnrTransfer.reg();
				pnrTransfer.closeRes();
				if (!tFile.delete()) {
					log.error("[" + tFile.getAbsolutePath() + "]delet fail!");
				}
				endDt = new Date();
				log.info("elapse:" + (endDt.getTime() - startDt.getTime())
						/ 1000l);
			}

		}

	}

//	 public static void main(String[] args) {
	 // CZ_DFP_20151030_1.txt.gz
	
//	 PnrFile2PnrFullPsgTransfer pnrTransfer = new PnrFile2PnrFullPsgTransfer(
//	 new File("S:\\test\\temptest.txt"));
//	 pnrTransfer.init();
//	 pnrTransfer.reg();
//	 pnrTransfer.closeRes();
	 
//	 }

}
