package com.csair.loong.pnr.processor;

import java.util.List;

import com.csair.loong.domain.FullPassengerInfo;
import com.csair.loong.domain.PnrSegInfo;
import com.csair.loong.service.Writer;


public class FullPsgInfo2FileProcessor implements
		Processor<List<FullPassengerInfo>,Writer> {

	private Writer writer;

	public FullPsgInfo2FileProcessor(Writer writer) {
		this.writer = writer;
	}

	@Override
	public Writer doit(List<FullPassengerInfo> fullPassengerInfoList) {
		if (fullPassengerInfoList != null)
			for (FullPassengerInfo fullPassengerInfo : fullPassengerInfoList) {
				writer.writer(fullPassengerInfo.toString());
			}
		return writer;
	}

}
