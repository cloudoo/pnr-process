package com.csair.loong.test;

import java.io.File;

import com.csair.datatrs.common.Processor;
import com.csair.loong.pnr.PnrFile2PnrFullPsgTransfer;
import com.csair.loong.pnr.processor.PnrSaveDbProcessor;

public class PnrTest {

	public static void main(String[] args) {

		// Processor prossor1 = new
		// PnrProcessor("D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\CZ_DFP_20130501_1.txt");
		// prossor1.doit();
		// Processor prossor2 = new PnrSaveDbProcessor();
		// prossor2.doit();

		testFullPsgTrans();
	}

	public static void testFullPsgTrans() {
		
		PnrFile2PnrFullPsgTransfer pnrTransfer = new PnrFile2PnrFullPsgTransfer(
				new File("S:\\FullPnr\\temp\\orgin.txt"));
		pnrTransfer.init();
		pnrTransfer.reg();
		pnrTransfer.closeRes();
		
//		[A-Z][A-Z]bidt_\d{6}.txt.Z
		
	}
	
}
