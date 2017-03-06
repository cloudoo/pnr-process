package com.csair.loong.qar.processor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.bi.commons.FieldSegment;
import com.csair.loong.pnr.processor.Processor;
import com.csair.loong.qar.domain.QarEngDomain;

public class TextParse implements Processor<String, QarEngDomain> {

	protected static final Logger log = LoggerFactory
			.getLogger(TextParse.class);

	public static Map<String, String> map = new HashMap<String, String>();

	@Override
	public QarEngDomain doit(String t) {

		QarEngDomain qarEngDomain = new QarEngDomain();

		String[] tempValues = t.split(FieldSegment.separator);

		log.debug("解析文本" + t);
		log.debug("tempValues.length=" + tempValues.length);

		for (int i = 0; i < tempValues.length; i++) {
			tempValues[i] = StringUtils.trimToEmpty(tempValues[i]);
		}

		qarEngDomain.id = Long.parseLong(tempValues[0]);
		
		qarEngDomain.tailnr = tempValues[1];
		qarEngDomain.fltdt = tempValues[2];
		qarEngDomain.engfilename = tempValues[3];
		qarEngDomain.time = tempValues[4];
		qarEngDomain.ias = (int) mean(tempValues[5].trim());
		qarEngDomain.cityFromR = tempValues[6];
		qarEngDomain.cityToR = tempValues[7];
		qarEngDomain.dateR = tempValues[8].replaceAll("/", "-");// tempValues[4]
																// =
																// tempValues[4].replaceAll("/",
																// "-");
		qarEngDomain.egt1 = parseFloat(tempValues[9]);
		qarEngDomain.egt2 = parseFloat(tempValues[10]);
		qarEngDomain.eprEng1Actua = parseFloat(tempValues[11]);
		qarEngDomain.eprEng2Actua = parseFloat(tempValues[12]);
		qarEngDomain.eprEng1 = parseFloat(tempValues[13]);
		qarEngDomain.eprEng2 = parseFloat(tempValues[14]);
		qarEngDomain.ff1 = parseFloat(tempValues[15]);
		qarEngDomain.ff2 = parseFloat(tempValues[16]);
		qarEngDomain.flightPhase = tempValues[17];
		qarEngDomain.flightNo1 = tempValues[18];
		qarEngDomain.flightNo2 = tempValues[19];
		qarEngDomain.gs = parseInt(tempValues[20]);
		qarEngDomain.head = parseFloat(tempValues[21]);
		qarEngDomain.tas = mean(tempValues[22].trim());// tempValues[18] =  String.valueOf(mean(tempValues[18].trim()));
		qarEngDomain.mach = parseFloat(tempValues[23]);
		qarEngDomain.vrtg = mean(tempValues[24].trim());// tempValues[20] =  String.valueOf(mean(tempValues[20].trim()));
		qarEngDomain.latg = mean(tempValues[25].trim()); // tempValues[21] =  String.valueOf(mean(tempValues[21].trim()));
		qarEngDomain.longit = mean(tempValues[26].trim()); // tempValues[22] =  String.valueOf(mean(tempValues[22].trim()));
		qarEngDomain.lonpc = parseFloat(tempValues[27]);
		qarEngDomain.latpc = parseFloat(tempValues[28]);
		qarEngDomain.raltc = mean(tempValues[29].trim()); // tempValues[25] =  String.valueOf(mean(tempValues[25].trim()));
		qarEngDomain.altStdc = mean(tempValues[30].trim()); // tempValues[26] =  String.valueOf(mean(tempValues[26].trim()));
		qarEngDomain.fqtyTon = parseFloat(tempValues[31]);
		qarEngDomain.tat = parseFloat(tempValues[32]);
		qarEngDomain.gw = parseInt(tempValues[33]);
		qarEngDomain.headTrue = parseFloat(tempValues[34]);
		qarEngDomain.drift = parseFloat(tempValues[35]);// lastValue("drift",  31, tempValues);
		qarEngDomain.roll = mean(tempValues[36].trim()); // tempValues[32] =
															// String.valueOf(mean(tempValues[32].trim()));
		qarEngDomain.vref_1 = parseInt(tempValues[37]);
		qarEngDomain.pitch_1 = mean(tempValues[38].trim()); // tempValues[34] =
															// String.valueOf(mean(tempValues[34].trim()));//
		qarEngDomain.fltPathAngl = parseFloat(tempValues[39]);
		qarEngDomain.winSpd = mean(tempValues[40].trim()); // tempValues[36] =
															// String.valueOf(mean(tempValues[36].trim()));
		qarEngDomain.winDir = parseFloat(tempValues[41]);
		qarEngDomain.apuOn = lastValue("apuOn", 42, tempValues); // lastValue("apuOn",
																	// 38,
																	// tempValues);
		qarEngDomain.v2 = parseInt(tempValues[43]);
		qarEngDomain.v1 = parseInt(tempValues[44]);
		qarEngDomain.vr = parseInt(tempValues[45]);
		qarEngDomain.vref_2 = parseInt(tempValues[46]);
		qarEngDomain.pitch_2 = mean(tempValues[47].trim());//		tempValues[43] = String.valueOf(mean(tempValues[43].trim()));
		qarEngDomain.acType = parseInt(tempValues[48]);
		qarEngDomain.machBuff = mean(tempValues[49].trim());//tempValues[45] = String.valueOf(mean(tempValues[45].trim()));
		qarEngDomain.pitchTrm = mean(tempValues[50].trim());//tempValues[46] = String.valueOf(mean(tempValues[46].trim()));
		qarEngDomain.pitchRate = mean(tempValues[51].trim()); //tempValues[47] = String.valueOf(mean(tempValues[47].trim()));
		qarEngDomain.headLin = parseFloat(tempValues[52]);
		qarEngDomain.flapc = parseInt(tempValues[53]);
		qarEngDomain.ctlClFcAL = mean(tempValues[54].trim()); //tempValues[50] = String.valueOf(mean(tempValues[50].trim()));
		qarEngDomain.ctlClFcBL = mean(tempValues[55].trim()); //tempValues[51] = String.valueOf(mean(tempValues[51].trim()));
		qarEngDomain.glideDevc = parseFloat(tempValues[56]);
		qarEngDomain.locDevc = parseFloat(tempValues[57]);
		qarEngDomain.ldgSeldw = tempValues[58];
		qarEngDomain.apEgd1 = parseInt(tempValues[59]);
		qarEngDomain.apEgd2 = parseInt(tempValues[60]);
		qarEngDomain.apOff = (tempValues[61]);
		qarEngDomain.atsEgd = (tempValues[62]);
		qarEngDomain.ivv = (int)mean(tempValues[63].trim());//tempValues[59] = String.valueOf(mean(tempValues[59].trim()));
		qarEngDomain.tla1 = parseFloat(tempValues[64]);
		qarEngDomain.tla2 = parseFloat(tempValues[65]);
		qarEngDomain.gpwsMode = (tempValues[66]);
		qarEngDomain.masCau = (tempValues[67]);
		qarEngDomain.iasc = (int)mean(tempValues[68].trim());//tempValues[64] = String.valueOf(mean(tempValues[64].trim()));
		qarEngDomain.gsc = (int)mean(tempValues[69].trim()); //tempValues[65] = String.valueOf(mean(tempValues[65].trim()));
		qarEngDomain.egt1c = parseInt(tempValues[70]);
		qarEngDomain.egt2c = parseInt(tempValues[71]);
		qarEngDomain.oip1 = parseFloat(tempValues[72]);
		qarEngDomain.oip2 = parseFloat(tempValues[73]);
		qarEngDomain.ailQadPosL = mean(tempValues[74].trim()); //tempValues[70] = String.valueOf(mean(tempValues[70].trim()));
		qarEngDomain.oilPrs1 = parseFloat(tempValues[75]); //lastValue("OIL_PRS1", 71, tempValues);
		qarEngDomain.oilPrs2 = parseFloat(tempValues[76]); //lastValue("OIL_PRS2", 72, tempValues);
		qarEngDomain.rawOip1A = parseFloat(tempValues[77]);
		qarEngDomain.rawOip1B = parseFloat(tempValues[78]);
		qarEngDomain.rawOip2A = parseFloat(tempValues[79]);
		qarEngDomain.rawOip2B = parseFloat(tempValues[80]);
		qarEngDomain.ldgc = (tempValues[81]);
		qarEngDomain.ldgl = (tempValues[82]);
		qarEngDomain.ldgnos = (tempValues[83]);
		qarEngDomain.ldgr = (tempValues[84]);
		qarEngDomain.n11 = parseFloat(tempValues[85]);
		qarEngDomain.n12 = parseFloat(tempValues[86]);
		qarEngDomain.vibN11 = parseFloat(tempValues[87]);
		qarEngDomain.vibN12 = parseFloat(tempValues[88]);
		qarEngDomain.n21 = parseFloat(tempValues[89]);
		qarEngDomain.n22 = parseFloat(tempValues[90]);
		qarEngDomain.vibN21 = parseFloat(tempValues[91]);
		qarEngDomain.vibN22 = parseFloat(tempValues[92]);
		qarEngDomain.n31 = parseFloat(tempValues[93]);
		qarEngDomain.n32 = parseFloat(tempValues[94]);
		qarEngDomain.vibN31 = parseFloat(tempValues[95]);
		qarEngDomain.vibN32 = parseFloat(tempValues[96]);
		qarEngDomain.oilQty1 = parseFloat(tempValues[97]);
		qarEngDomain.oilQty2 = parseFloat(tempValues[98]);
		qarEngDomain.oilTmp1 = parseFloat(tempValues[99]);
		qarEngDomain.oilTmp2 = parseFloat(tempValues[100]);
		qarEngDomain.satr = parseFloat(tempValues[101]);
		qarEngDomain.dfc = parseInt(tempValues[102]);
		lastValue("DFC", 98, tempValues);
		if (tempValues.length >= 104)
			qarEngDomain.altStdIsis = tempValues[103];

		return qarEngDomain;

	}

	/**
	 * 计算平均值
	 * 
	 * @param floats
	 * @return
	 */
	public float mean(String floats) {
		if (StringUtils.isBlank(floats)) {
			return 0f;
		}
		String[] a = floats.split("\\s+");

		float temp = 0f;

		try {
			for (String t : a) {
				temp += Float.parseFloat(t);
			}

		} catch (NumberFormatException e) {
			log.debug("NumberFormatException:" + floats);
		}

		return temp / a.length;
	}

	public String lastValue(String segmentName, int segmentIndex,
			String[] tempValues) {

		String temp = tempValues[segmentIndex].trim();

		if (temp.length() > 0) {
			map.put(segmentName, temp);
		}

		return map.get(segmentName);

	}

	public float parseFloat(String str) {
		float f = 0.00f;

		try {

			if (str != null && str.length() > 0) {
				f = Float.parseFloat(str);
			}

		} catch (NumberFormatException e) {
			log.debug("NumberFormatException:" + str);
		}

		return f;
	}

	public int parseInt(String str) {
		String temp = StringUtils.trimToEmpty(str);
		int i = 0;
		try {
			if (temp != null && temp.length() > 0) {
				i = Integer.parseInt(temp);
			}
		} catch (NumberFormatException e) {
			log.debug("NumberFormatException:" + str);
		}

		return i;
	}

}
