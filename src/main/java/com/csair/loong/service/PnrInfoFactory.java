package com.csair.loong.service;

import com.csair.loong.domain.*;
import com.csair.loong.pnr.processor.Processor;

/**
 * Created by cloudoo on 2016/9/14.
 */
public class PnrInfoFactory implements Processor<String,PnrInfo>{
	
	private static PnrInfoFactory instance;

	private PnrInfoFactory() {

	}

	public synchronized static PnrInfoFactory getInstance() {
		if (instance == null)
			instance = new PnrInfoFactory();
		return instance;
	}

	public PnrInfo doit(String pnrLines) {
		if (pnrLines.indexOf(PnrInfo.FILE_HEADER) >= 0) {
			return null;
		}
		if (pnrLines.indexOf(PnrInfo.FILE_ENDER) >= 0) {
			return null;
		}
		String[] lines = pnrLines.split(",");
		PnrInfo pnrInfo = null;
		if (lines.length > 0) {
			int type = Integer.parseInt(lines[0]);
			switch (type) {
			case 1: {
				pnrInfo = new PnrHeader(lines);
				break;
			}
			case 2:
				pnrInfo = new PnrPsgInfo(lines);
				break;
			case 3:
				pnrInfo = new PsgChgNameInfo(lines);
				break;
			case 4:
				pnrInfo = new FltSegInfo(lines);
				break;
			case 5:
				pnrInfo = new FltSegActCdInfo(lines);
				break;
			case 6:
				// pnrInfo = new PnrHeader(lines);
				break;
			case 7:
				// pnrInfo = new PnrHeader(lines);
				break;
			case 8:
				pnrInfo = new PnrPaxData(lines);
				break;
			case 9:
				// pnrInfo = new PnrHeader(lines);
				break;
			case 10:
				pnrInfo = new PsgIdInfo(lines);
				break;
			case 11:
				// pnrInfo = new PnrHeader(lines);
				break;
			case 12:
				pnrInfo = new PnrHeader(lines);
				break;
			case 13:
				// pnrInfo = new PnrHeader(lines);
				break;
			case 14:
				// pnrInfo = new PnrHeader(lines);
				break;
			case 15:
				pnrInfo = new BigClientInfo(lines);
				break;
			case 16:
				pnrInfo = new ContractInfo(lines);
				break;

			default:
				break;

			}

		}
		return pnrInfo;
	}


}
