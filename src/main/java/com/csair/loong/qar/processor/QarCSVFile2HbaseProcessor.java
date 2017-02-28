package com.csair.loong.qar.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.loong.dao.AbstractHbaseDao;
import com.csair.loong.processor.CSVFile2HbaseProcessor;
import com.csair.loong.qar.domain.QarEngDomain;

public class QarCSVFile2HbaseProcessor extends CSVFile2HbaseProcessor{
	private static final Logger log = LoggerFactory
			.getLogger(QarCSVFile2HbaseProcessor.class);
	
	public QarCSVFile2HbaseProcessor(AbstractHbaseDao dao) {
		super(dao);
	}

	public String strAddQuotes(String str){
		QarEngDomain domain = new QarEngDomain();
		log.info(str);
		
		domain.initByStrings(str);
//		str = str.replaceAll(",", "','");
		
		return domain.toSql();
	}
	
}
