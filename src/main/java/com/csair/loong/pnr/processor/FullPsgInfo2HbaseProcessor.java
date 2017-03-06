package com.csair.loong.pnr.processor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.loong.dao.FullPnrDao;
import com.csair.loong.domain.FullPassengerInfo;

public class FullPsgInfo2HbaseProcessor implements
		Processor< List<FullPassengerInfo>,String> {
	private static final Logger log = LoggerFactory
			.getLogger(FullPsgInfo2HbaseProcessor.class);

	FullPnrDao dao;

	public FullPsgInfo2HbaseProcessor(FullPnrDao dao) {
		this.dao = dao;
	}

	public void init() {
		dao.init();
	}

	@Override
	public String doit(List<FullPassengerInfo> fullPsgInfos) {

		dao.insert(fullPsgInfos);

		return "";
	}

}
