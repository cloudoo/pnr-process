package com.csair.loong.processor;

import java.util.List;

import com.csair.loong.pnr.processor.Processor;
import com.csair.loong.service.Writer;

public class Object2FileProcessor<T> implements Processor<List<T>, Writer> {
	private Writer writer;

	public Object2FileProcessor(Writer pnrFileWriter) {
		this.writer = writer;
	}

	@Override
	public Writer doit(List<T> list) {

		if (list != null)
			for (T t : list) {
				writer.writer(t.toString());
			}
		return writer;
	}

}
