package com.csair.loong.pnr.old;

import com.csair.loong.pnr.processor.Processor;
import com.csair.loong.service.FileWriter;

public class PnrFileWriter extends FileWriter implements Processor{

	public PnrFileWriter(String fileName) {
		super(fileName);
		 
	}

	@Override
	public Object doit(Object k) {
		super.writer(k);
		return this;
	}

}
