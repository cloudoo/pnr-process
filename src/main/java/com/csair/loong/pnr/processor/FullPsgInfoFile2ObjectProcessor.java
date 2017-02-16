package com.csair.loong.pnr.processor;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.csair.loong.domain.FullPassengerInfo;
import com.csair.loong.domain.PnrSegInfo;
import com.csair.loong.service.Writer;

public class FullPsgInfoFile2ObjectProcessor implements
		Processor<List<FullPassengerInfo>, String> {

	@Override
	public List<FullPassengerInfo> doit(String dir) {
		List<FullPassengerInfo> fullPsgInfos = new ArrayList<FullPassengerInfo>();
		BufferedReader br = null;
		//TODO:读文件，转化为对象
		
		
		return fullPsgInfos;
	}

}
