package com.csair.loong.pnr;


import com.csair.loong.domain.*;
import com.csair.loong.pnr.processor.Processor;
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
public class FullPsgInfoBuilder extends Processor<List<FullPassengerInfo>,PnrSegInfo> {
    private static final Logger log = LoggerFactory.getLogger(FullPsgInfoBuilder.class);

    public static void main(String[] args) {
        FullPassengerInfo fullPassengerInfo = new FullPassengerInfo();
        FullPassengerInfo fullPassengerInfo1 = new FullPassengerInfo();
        fullPassengerInfo.setOprStatCd("test");
        fullPassengerInfo1.setSubClsCd("leisy");
        BeanUtils.copyProperties(fullPassengerInfo, fullPassengerInfo1);
        BeanUtils.copyProperties(fullPassengerInfo1, fullPassengerInfo);
        System.out.print(fullPassengerInfo.getOprStatCd() + "," + fullPassengerInfo1.getOprStatCd());
        System.out.print(fullPassengerInfo.getSubClsCd() + "," + fullPassengerInfo1.getSubClsCd());
    }

    @Override
    public List<FullPassengerInfo> doit(PnrSegInfo pnrSegInfo) {
        Map<String, List<FullPassengerInfo>> fullPasMap = new HashMap<>();

        if (pnrSegInfo == null) {
            return null;
        }
        List<FullPassengerInfo> fullPassengerInfos = new ArrayList<>();


        for (FullPsgInfo fullPsgInfo : pnrSegInfo.getPsgInfos()) {
            fullPassengerInfos.addAll(create(fullPsgInfo));
        }
        return fullPassengerInfos;
    }

    public List<FullPassengerInfo> create(FullPsgInfo fullPsgInfo) {
        List<FullPassengerInfo> fullPassengerInfoList = new ArrayList<>();
        if (fullPsgInfo.getFltSegInfos() != null && fullPsgInfo.getFltSegInfos().size() != 0) {


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

            //改名信息

            //004 005
            fullPassengerInfoList.add(fullPassengerInfo);

            int paxHistItmNbr = 0;
            PsgChgNameInfo tempChgNameInfo = null;
            if (fullPsgInfo.getPsgChgNameInfos() != null) {
                for (PsgChgNameInfo chgNameInfo : fullPsgInfo.getPsgChgNameInfos()) {
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

            //008 航段信息
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
                    //TODO:行动代码没有拷贝
                    if (i == 0) {
                        BeanUtils.copyProperties(tempFullPsg, fullPassengerInfo);
                    } else {
                        fullPassengerInfoList.add(tempFullPsg);

                    }
                }//航段信息
            }
            //010 旅客id
            String indvlIdTypeCd = "", indvlIdNbr = "", cntryCd = "", indvlBthDay = "", gndCd = "";
            if (fullPsgInfo.getPsgIdInfos() != null) {
                for (PsgIdInfo psgIdInfo : fullPsgInfo.getPsgIdInfos()) {
                    indvlIdTypeCd += StringUtils.trimToEmpty(psgIdInfo.getIndvlIdTypCd());
                    indvlIdNbr += StringUtils.trimToEmpty(psgIdInfo.getIndvlIdNbr());
                    cntryCd += StringUtils.trimToEmpty(psgIdInfo.getCntryCd());
                    indvlBthDay += StringUtils.trimToEmpty(psgIdInfo.getIndvlBthDay());
                    gndCd += StringUtils.trimToEmpty(psgIdInfo.getGndCd());
                }
            }


            //015
            String vicoCard = "", tktType = "";
            if (fullPsgInfo.getBigClientInfos() != null) {
                for (BigClientInfo bigClientInfo : fullPsgInfo.getBigClientInfos()) {
                    vicoCard += bigClientInfo.getVicoCard() + "/";
                    tktType += bigClientInfo.getTktTyp() + "/";
                }
            }


            //016
            String ctcNbr = "";
            if (fullPsgInfo.getContractInfos() != null) {
                for (ContractInfo contractInfo : fullPsgInfo.getContractInfos()) {
                    ctcNbr += contractInfo.getCtcNbr() + "/";
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
