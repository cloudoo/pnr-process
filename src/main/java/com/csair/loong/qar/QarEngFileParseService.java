package com.csair.loong.qar;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.bi.utils.DateUtils;
import com.csair.loong.pnr.processor.ProcessorChain;
import com.csair.loong.processor.Object2FileProcessor;
import com.csair.loong.qar.domain.QarEngDomain;
import com.csair.loong.qar.processor.TextParse;
import com.csair.loong.service.FileReader;
import com.csair.loong.service.FileWriter;
import com.csair.loong.service.Writer;

public class QarEngFileParseService extends FileReader {
	private static final Logger log = LoggerFactory
			.getLogger(QarEngFileParseService.class);
	
	public QarEngFileParseService(String file) {
		super(file);
	}

	public QarEngFileParseService(File file) {
		super(file);
	}

//	private ProcessorChain chain;
	private Writer pnrFileWriter;
	private String qarKeyStr= "";

	public void init() {

		
		pnrFileWriter = new FileWriter(super.file.getAbsolutePath()
				+ ".processor");
//		chain = new ProcessorChain().addProcessor(new TextParse())
//				.addProcessor(
//						new Object2FileProcessor<QarEngDomain>(pnrFileWriter));

		this.setEncode("UTF-8");
		
		qarKeyStr = acquireTitle(file.getName());

	}

	@Override
	public void process(int index, String line) {
		TextParse parse = new TextParse();
		if (index > 2) {
			QarEngDomain qd = parse.doit(qarKeyStr+","+line);
			
			pnrFileWriter.writer(qd.toString());
			
		}

	}
	
    public String acquireTitle(String fileName){
        int index = fileName.indexOf(".");
        String tempTitle = fileName.substring(0, index);
        String name = tempTitle;
        String titles = tempTitle.replaceAll("-", ",");

        int first = titles.indexOf(',');
        int second = titles.indexOf(',', first + 1);
        titles = titles.substring(0, second) + titles.substring(second+1, titles.length());
        String[] latestTitles = titles.split(",");
        latestTitles[2] = DateUtils.string2HiveDateFormat(latestTitles[2]);
        String tempR = "";
        for(String temp:latestTitles){
            tempR+=temp+",";
        }
        return tempR+name;
    }
    
	
	public static void main(String[] args){
		
		
		String str = "S:\\qar\\org\\org";
		File file = new File(str);
		if(file.isDirectory()){
			
			File[] files = file.listFiles();
			for(File tFile:files){
				QarEngFileParseService qarParseService = new QarEngFileParseService(tFile);
				qarParseService.init();
				qarParseService.reg();
				log.info("处理文件"+tFile.getName());
			}
		}
		
		
	}
}
