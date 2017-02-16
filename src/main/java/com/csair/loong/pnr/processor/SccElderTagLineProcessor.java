package com.csair.loong.pnr.processor;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SccElderTagLineProcessor implements Processor<String, String> {

	protected static final Logger log = LoggerFactory
			.getLogger(SccElderTagLineProcessor.class);

	private String fltDt = "";

	private SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMdd");

	public SccElderTagLineProcessor() {
		fltDt = simpleFormat.format(new Date());
	}

	public SccElderTagLineProcessor(String fltDt) {
		if(StringUtils.isBlank(fltDt)){
			this.fltDt = simpleFormat.format(new Date());
		}
		else this.fltDt = fltDt;
	}

	@Override
	public String doit(String tempPsgLine) {

		return filterElderPsg(tempPsgLine, fltDt);

	}

	public boolean is65old(String bth, String fltDt) {

		if (getAge(bth, fltDt) >= 65)
			return true;
		return false;
	}

	public boolean isMore3old(String bth) {

		if (getAge(bth, fltDt) >= 3)
			return true;
		return false;
	}

	public String getBrtById(String id) {
		if (id.length() == 18) {
			return id.substring(6, 14);
		} else if (id.length() == 15) {
			String temp = "19" + id.substring(7, 13);
			return temp;
		}
		return "";
	}

	// public boolean idIsLess3old(String id) {
	// // 410423198302152616
	// if (id.length() == 18) {
	// return getAge(id.substring(6, 14),fltDt)<3;
	// } else if (id.length() == 15) {
	// String temp = "19" + id.substring(7, 13);
	// return getAge(temp,fltDt)<3;
	// }
	// return false;
	// }

	public boolean isValidId(String id) {
		if (id.indexOf("XXXXXX") >= 0) {
			return false;
		} else
			return true;
	}

	public int getAge(String brtDt, String nowDt) {
		int age = 0;

		Date dateOfBirth = null;
		Date now = null;
		try {
			dateOfBirth = simpleFormat.parse(filterAlphbater(brtDt));
			if (nowDt != null) {
				now = simpleFormat.parse(nowDt);
			}
		} catch (ParseException e) {
			log.error("年龄解析错误:" + e);
		}

		Calendar born = Calendar.getInstance();
		Calendar nowCal = Calendar.getInstance();

		if (dateOfBirth != null) {
			nowCal.setTime(now);
			born.setTime(dateOfBirth);
			if (born.after(now)) {
				log.error("出生在未来时间。。。。");
			}

			age = nowCal.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			if (nowCal.get(Calendar.DAY_OF_YEAR) < born
					.get(Calendar.DAY_OF_YEAR)) {
				age -= 1;
			}
		}

		return age;

	}

	/**
	 * 去除数字字符中的字母
	 * 
	 * @param brtdh
	 * @return
	 */
	public String filterAlphbater(String brtdh) {
		return brtdh.replaceAll("O", "0").replaceAll("I", "1");
	}


	/**
	 * 1、处理成宽表后，判断证件号缺失的，不发送给SCC 2、处理成宽表后，证件号中有6个连续“XXXXXX”的无效，不发送给SCC
	 * 
	 * @param ids
	 * @param fltDt
	 * @return
	 */
	public String filterId(String ids, String fltDt) {
		String tempId = "";
		if (StringUtils.isNotBlank(ids)) {
			String id[] = ids.split("/");

			for (String tid : id) {
				if (tid.indexOf("XXXXXX") < 0) {

					// 1.如果是身份证号，就处理生日，如果不是身份证，就返回改id
					String brth = getBrtById(tid);
					if (brth.length() == 0) {
						tempId += tid + "/";
					} else if (is65old(brth, fltDt)) {
						tempId += tid + "/";
					}

				}
			}

		}
		return tempId.length() != 0 ? tempId.substring(0, tempId.length() - 1)
				: tempId;
	}

	public String filterNorId(String ids, String fltDt) {
		String tempId = "";
		if (StringUtils.isNotBlank(ids)) {
			String id[] = ids.split("/");

			for (String tid : id) {
				if (tid.indexOf("XXXXXX") < 0) {

					// 1.如果是身份证号，就处理生日，如果不是身份证，就返回改id
					String brth = getBrtById(tid);

					if (brth.length() > 0 && is65old(brth, fltDt)) {
						tempId += tid + "/";
					}

				}
			}

		}
		return tempId.length() != 0 ? tempId.substring(0, tempId.length() - 1)
				: tempId;
	}

	/**
	 * 3、处理成宽表后，婴儿的证件号与成人的证件号一同出现的，可以据生日将（文件日期 -- 生日< 3岁）的信息去掉，只保留成人的信息发送根给SCC婴儿
	 * 
	 * @param brt
	 * @param fltDt
	 * @return
	 */
	public String filterBrt(String brt, String fltDt) {
		String tempBrt = "";
		if (StringUtils.isNotBlank(brt)) {
			String brts[] = brt.split("/");

			for (String tbrt : brts) {

				if (tbrt.length() != 0 && is65old(tbrt, fltDt)) {
					tempBrt += tbrt + "/";
				}

			}

		}

		if (tempBrt.length() == 0)
			return tempBrt;
		else
			return tempBrt.substring(0, tempBrt.length() - 1);
	}

	/**
	 * 1、处理成宽表后，判断证件号缺失的，不发送给SCC 2、处理成宽表后，证件号中有6个连续“XXXXXX”的无效，不发送给SCC
	 * 3、处理成宽表后，婴儿的证件号与成人的证件号一同出现的，可以据生日将（文件日期 -- 生日< 3岁）的信息去掉，只保留成人的信息发送根给SCC婴儿
	 * 
	 * @param line
	 * @return
	 */
	public String filterElderPsg(String line, String fltDt) {
		String[] elderTag = new String[4];

		String[] lines = line.split(",");
		elderTag[0] = lines[4];
		elderTag[1] = lines[5];
		if (lines.length > 23) {
			elderTag[2] = lines[23];
		} else
			elderTag[2] = "";

		String id = "", brt = "";

		// 1.某天的航班
		if (lines.length > 14 && lines[14].equalsIgnoreCase(fltDt)) {
			// lines[24]证件号
			// lines[26]生日

			if (lines.length > 24) {

				id = filterId(lines[24], fltDt);

				if (StringUtils.isNotEmpty(id)) {
					elderTag[3] = id;
				}

			}

			if (lines.length > 26) {
				brt = filterBrt(lines[26], fltDt);
			}
		}

		if (brt.length() > 0 && id.length() > 0) {
			return StringUtils.join(elderTag, ",");
		} else if (lines.length > 24) {

			id = filterNorId(lines[24], fltDt);

			if (StringUtils.isNotBlank(id)) {
				elderTag[3] = id;
			} else {
				return "";
			}

		}
		return "";

	}

	/**
	 * 1、处理成宽表后，判断证件号缺失的，不发送给SCC 2、处理成宽表后，证件号中有6个连续“XXXXXX”的无效，不发送给SCC
	 * 3、处理成宽表后，婴儿的证件号与成人的证件号一同出现的，可以据生日将（文件日期 -- 生日< 3岁）的信息去掉，只保留成人的信息发送根给SCC婴儿
	 * 
	 * @param line
	 * @return
	 */
	public String filterElderPsg2(String line, String fltDt) {

		String[] elderTag = new String[4];

		String[] lines = line.split(",");
		elderTag[0] = lines[4];
		elderTag[1] = lines[5];

		if (lines.length > 23) {
			elderTag[2] = lines[23];
		} else
			elderTag[2] = "";

		String id = "", brt = "";

		// 1.某天的航班
		if (lines.length > 14 && lines[14].equalsIgnoreCase(fltDt)) {
			// lines[24]证件号
			// lines[26]生日

			if (lines.length > 26) {

				// brt = filterBrt(lines[26],fltDt);

				String tempBrt = "";
				if (StringUtils.isNotBlank(brt)) {
					String brts[] = brt.split("/");

					for (String tbrt : brts) {

						if (tbrt.length() != 0 && is65old(tbrt, fltDt)) {
							tempBrt += tbrt + "/";
						}
					}
				}

			}

			if (lines.length > 24) {

				id = filterId(lines[24], fltDt);

				if (StringUtils.isNotEmpty(id)) {
					elderTag[3] = id;
				}

			}

		}

		if (brt.length() > 0 && id.length() > 0) {
			return StringUtils.join(elderTag, ",");
		} else if (lines.length > 24) {

			id = filterNorId(lines[24], fltDt);

			if (StringUtils.isNotBlank(id)) {
				elderTag[3] = id;
			} else {
				return "";
			}

		}
		return "";

	}

}
