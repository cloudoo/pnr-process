package com.csair.loong.qar.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.loong.dao.AbstractHBaseJdbcDAO;
import com.csair.loong.processor.CSVFile2HbaseProcessor;
import com.csair.loong.qar.domain.QarEngDomain;

public class CSVFile2PhoneixHbaseProcessor extends CSVFile2HbaseProcessor{
	private static final Logger log = LoggerFactory
			.getLogger(CSVFile2PhoneixHbaseProcessor.class);
	
	public CSVFile2PhoneixHbaseProcessor(AbstractHBaseJdbcDAO dao) {
		super(dao);
	}

	public String strAddQuotes(String str){
		QarEngDomain domain = new QarEngDomain();
//		log.info(str);
		
		domain.initByStrings(str);
		
		return domain.toSql();
	}
	
}
