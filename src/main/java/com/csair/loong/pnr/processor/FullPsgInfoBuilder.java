package com.csair.loong.pnr.processor;

import com.csair.loong.domain.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cloudoo on 2016/9/19.
 */
public class FullPsgInfoBuilder implements
		Processor<List<FullPassengerInfo>, PnrSegInfo> {
	private static final Logger log = LoggerFactory
			.getLogger(FullPsgInfoBuilder.class);

	public static void main(String[] args) {
		FullPassengerInfo fullPassengerInfo = new FullPassengerInfo();
		FullPassengerInfo fullPassengerInfo1 = new FullPassengerInfo();
		fullPassengerInfo.setOprStatCd("test");
		fullPassengerInfo1.setSubClsCd("leisy");
		BeanUtils.copyProperties(fullPassengerInfo, fullPassengerInfo1);
		BeanUtils.copyProperties(fullPassengerInfo1, fullPassengerInfo);
		
		log.debug(fullPassengerInfo.getOprStatCd() + ","
				+ fullPassengerInfo1.getOprStatCd());
		log.debug(fullPassengerInfo.getSubClsCd() + ","
				+ fullPassengerInfo1.getSubClsCd());
	}

	@Override
	public List<FullPassengerInfo> doit(PnrSegInfo pnrSegInfo) {
//		Map<String, List<FullPassengerInfo>> fullPasMap = new HashMap<>();

		if (pnrSegInfo == null || pnrSegInfo.getHeader() == null) {
			return null;
		}
		List<FullPassengerInfo> fullPassengerInfos = new ArrayList<>();

		for (FullPsgInfo fullPsgInfo : pnrSegInfo.getPsgInfos()) {
			fullPassengerInfos.addAll(create(fullPsgInfo));
		}
		if(fullPassengerInfos==null||fullPassengerInfos.size()==0){
			log.warn("warning fullpassengerInfo size is 0");
		}
		return fullPassengerInfos;
	}

	public List<FullPassengerInfo> create(FullPsgInfo fullPsgInfo) {
		List<FullPassengerInfo> fullPassengerInfoList = new ArrayList<>();
		if (fullPsgInfo.getFltSegInfos() != null
				&& fullPsgInfo.getFltSegInfos().size() != 0) {

			PnrHeader header = fullPsgInfo.getPnrHeader();
			FullPassengerInfo fullPassengerInfo = new FullPassengerInfo();

			fullPassengerInfo.setPnrRef(header.getPnrRef());
			fullPassengerInfo.setPnrCrtDt(header.getPnrCrtDt());
			fullPassengerInfo.setPnrGrpInd(header.getPnrGrpInd());

			PnrPsgInfo pnrPsgInfo = fullPsgInfo.getPnrPsgInfo();
			fullPassengerInfo.setPaxId(pnrPsgInfo.getPaxId());
			fullPassengerInfo.setPaxFulNm(pnrPsgInfo.getPaxFulNm());
			fullPassengerInfo.setPaxCnNm(pnrPsgInfo.getPaxCnNm());
			fullPassengerInfo.setVipInd(pnrPsgInfo.getVipInd());
			fullPassengerInfo.setPaxAgeCtgCd(pnrPsgInfo.getPaxAgeCtgCd());

			// 改名信息

			// 004 005
			fullPassengerInfoList.add(fullPassengerInfo);

			int paxHistItmNbr = 0;
			PsgChgNameInfo tempChgNameInfo = null;
			if (fullPsgInfo.getPsgChgNameInfos() != null) {
				for (PsgChgNameInfo chgNameInfo : fullPsgInfo
						.getPsgChgNameInfos()) {
					if (chgNameInfo.getPaxHistItmNbr() > paxHistItmNbr) {
						paxHistItmNbr = chgNameInfo.getPaxHistItmNbr();
						tempChgNameInfo = chgNameInfo;
					}
				}
			}
			if (tempChgNameInfo != null) {
				fullPassengerInfo.setPaxFulNm(tempChgNameInfo.getPaxFulNm());
				fullPassengerInfo.setPaxCnNm(tempChgNameInfo.getPaxCnNm());
			}

			// 008 航段信息
			if (fullPsgInfo.getFltSegInfos() != null) {
				for (int i = 0; i < fullPsgInfo.getFltSegInfos().size(); i++) {
					FltSegInfo fltSegInfo = fullPsgInfo.getFltSegInfos().get(i);
					FullPassengerInfo tempFullPsg = new FullPassengerInfo();
					BeanUtils.copyProperties(fullPassengerInfo, tempFullPsg);

					tempFullPsg.setSegId(fltSegInfo.getSegId());
					tempFullPsg.setCarrCd(fltSegInfo.getCarrCd());
					tempFullPsg.setFltNbr(fltSegInfo.getFltNbr());
					tempFullPsg.setFltNbrSfx(fltSegInfo.getFltNbrSfx());
					tempFullPsg.setDptAirptCd(fltSegInfo.getDptAirptCd());
					tempFullPsg.setArrvAirptCd(fltSegInfo.getArrvAirptCd());
					tempFullPsg.setDptDtLcl(fltSegInfo.getDptDtLcl());
					tempFullPsg.setDptDow(fltSegInfo.getDptDow());
					tempFullPsg.setDptTmLcl(fltSegInfo.getDptTmLcl());
					tempFullPsg.setArrvDtLcl(fltSegInfo.getArrvDtLcl());
					tempFullPsg.setArrvTmLcl(fltSegInfo.getArrvTmLcl());
					tempFullPsg.setAirSegFltTyp(fltSegInfo.getAirSegFltType());
					tempFullPsg.setSubClsCd(fltSegInfo.getSubClsCd());
					tempFullPsg.setOprStatCd(fltSegInfo.getOprStatCd());
					// TODO:行动代码没有拷贝
					if (i == 0) {
						BeanUtils
								.copyProperties(tempFullPsg, fullPassengerInfo);
					} else {
						fullPassengerInfoList.add(tempFullPsg);

					}
				}// 航段信息
			}
			// 010 旅客id
			String indvlIdTypeCd = "", indvlIdNbr = "", cntryCd = "", indvlBthDay = "", gndCd = "";
			if (fullPsgInfo.getPsgIdInfos() != null) {
				for (PsgIdInfo psgIdInfo : fullPsgInfo.getPsgIdInfos()) {
					String tempIndvlIdTypeCd = StringUtils
							.trimToEmpty(psgIdInfo.getIndvlIdTypCd());
					String tempindvlIdNbr = StringUtils.trimToEmpty(psgIdInfo
							.getIndvlIdNbr());
					String tempcntryCd = StringUtils.trimToEmpty(psgIdInfo
							.getCntryCd());
					String tempindvlBthDay = StringUtils.trimToEmpty(psgIdInfo
							.getIndvlBthDay());
					String tempgndCd = StringUtils.trimToEmpty(psgIdInfo
							.getGndCd());

					if (indvlIdTypeCd.length() == 0) {
						indvlIdTypeCd = tempIndvlIdTypeCd;
					} else if (tempIndvlIdTypeCd.length() > 0
							&& indvlIdTypeCd
									.indexOf(tempIndvlIdTypeCd)<0) {
						indvlIdTypeCd += "/" + tempIndvlIdTypeCd;
					}

					if (indvlIdNbr.length() == 0) {
						indvlIdNbr = tempindvlIdNbr;
					} else if (tempindvlIdNbr.length()>0&&indvlIdNbr.indexOf(tempindvlIdNbr)<0) {
						indvlIdNbr += "/" + tempindvlIdNbr;
					}

					if (cntryCd.length() == 0) {
						cntryCd = tempcntryCd;
					} else if (tempcntryCd.length()>0&& cntryCd.indexOf(tempcntryCd)<0) {
						cntryCd += "/" + tempcntryCd;
					}

					if (indvlBthDay.length() == 0) {
						indvlBthDay = tempindvlBthDay;
					} else if (tempindvlBthDay.length()>0&& indvlBthDay.indexOf(tempindvlBthDay)<0) {
						indvlBthDay += "/" + tempindvlBthDay;
					}

					if (gndCd.length() == 0) {
						gndCd = tempgndCd;
					} else if (tempgndCd.length()>0&&gndCd.indexOf(tempgndCd)<0) {
						gndCd += "/" + tempgndCd;
					}

					// indvlIdNbr +=
					// "/"+StringUtils.trimToEmpty(psgIdInfo.getIndvlIdNbr());
					// cntryCd +=
					// "/"+StringUtils.trimToEmpty(psgIdInfo.getCntryCd());
					// indvlBthDay +=
					// "/"+StringUtils.trimToEmpty(psgIdInfo.getIndvlBthDay());
					// gndCd +=
					// "/"+StringUtils.trimToEmpty(psgIdInfo.getGndCd());
				}
			}

			// 015
			String vicoCard = "", tktType = "";
			if (fullPsgInfo.getBigClientInfos() != null) {
				for (BigClientInfo bigClientInfo : fullPsgInfo
						.getBigClientInfos()) {
					vicoCard += bigClientInfo.getVicoCard() + "/";
					tktType += bigClientInfo.getTktTyp() + "/";
				}
			}
			
			// 016
			String ctcNbr = "";
			if (fullPsgInfo.getContractInfos() != null) {
				for (ContractInfo contractInfo : fullPsgInfo.getContractInfos()) {
					ctcNbr += contractInfo.getCtcNbr() + "/";
				}
			}
			if(StringUtils.isBlank(indvlBthDay)){
				if(indvlIdNbr.length()==18){
					indvlBthDay = indvlIdNbr.substring(6,14);//422422198008107918
				}else if(indvlIdNbr.length()==15){
					indvlBthDay = "19"+indvlIdNbr.substring(6,12);//422422198008107918
				}
			}
			for (FullPassengerInfo tempFullPsg : fullPassengerInfoList) {
				tempFullPsg.setIndvlIdTypCd(indvlIdTypeCd);
				tempFullPsg.setIndvlNbr(indvlIdNbr);
				tempFullPsg.setCntryCd(cntryCd);
				tempFullPsg.setIndvlBthDay(indvlBthDay);
				tempFullPsg.setGndCd(gndCd);
				tempFullPsg.setVicoCard(vicoCard);
				tempFullPsg.setTktType(tktType);
				tempFullPsg.setCtcNbr(ctcNbr);
			}
		}
		return fullPassengerInfoList;
	}
}
