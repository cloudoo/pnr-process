package com.csair.loong.pnr.processor;


import java.util.ArrayList;
import java.util.List;


public class ProcessorChain  implements Processor{

	private  List<Processor>  processorList = new ArrayList<Processor>();  ;
	
	public ProcessorChain addProcessor(Processor processor){
		processorList.add(processor);
		return this;
	}

	@Override
	public Object doit(Object something) {
		Object result = something;
		 for(Processor processor:processorList){
			 result = processor.doit(result);
		 }
		 return result;
	}

}
