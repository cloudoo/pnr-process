package com.csair.loong.pnr.processor;


import com.csair.loong.domain.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by cloudoo on 2016/9/18.
 */
public class PnrSegInfoBuilder implements Processor<List<PnrInfo>,PnrSegInfo> {


    private  PnrSegInfo pnrSegInfo;
    

    public PnrSegInfo doit(List<PnrInfo> list) {

            if(list!=null){
                //list 第一条记录肯定是001，001 在一个pnr段中只有一条
                //
                pnrSegInfo = new PnrSegInfo();

                PnrInfo temp =  list.get(0);
                if(!temp.getPartTyp().equals(PnrInfo.PNR_HEAD_TYPE)){
                     return pnrSegInfo;
                }
                PnrHeader header = (PnrHeader)temp;
                if(header.getPnrCnclInd().equalsIgnoreCase("D")){
                    return pnrSegInfo;
                }else{
                    pnrSegInfo.setHeader(header);
                }

                for(int i=1;i<list.size();i++){
                    PnrInfo tempPnr = list.get(i);

                     if(tempPnr.getPartTyp().equalsIgnoreCase(PnrInfo.PNR_PSG_INFO_TYPE)){
                         PnrPsgInfo pnrPsgInfo = (PnrPsgInfo)tempPnr;
//                         002
//                         规则：	Pax_Rltv_Posn字段不为“0”，通过Pnr_Ref_Crt_Dt与001关联
                         if(pnrPsgInfo.getPaxRltvPosn()!=0){
                             if(pnrSegInfo.getFullPsgInfo(pnrPsgInfo.getPaxId())==null){
                                 FullPsgInfo fullPsgInfo = new FullPsgInfo();
                                 fullPsgInfo.setPnrHeader(header);
                                 fullPsgInfo.setPnrPsgInfo(pnrPsgInfo);
                                 pnrSegInfo.addFullPsgInfo(fullPsgInfo);

                             }else{
                                 FullPsgInfo fullPsgInfo = pnrSegInfo.getFullPsgInfo(pnrPsgInfo.getPaxId());
                                 fullPsgInfo.setPnrPsgInfo(pnrPsgInfo);
                             }

                         }
                     }else if(tempPnr.getPartTyp().equalsIgnoreCase(PnrInfo.PNR_PSG_CHGNAME_INFO_TYPE)){
                         PsgChgNameInfo psgChgNameInfo = (PsgChgNameInfo)tempPnr;
                         FullPsgInfo fullPsgInfo = pnrSegInfo.getFullPsgInfo(String.valueOf(psgChgNameInfo.getPaxId()));
                         if(fullPsgInfo!=null){
                             fullPsgInfo.addPsgChgNameInfo(psgChgNameInfo);
                         }

                     }else if(tempPnr.getPartTyp().equalsIgnoreCase(PnrInfo.PNR_FLT_SEG_TYPE)){
                         FltSegInfo fltSegInfo = (FltSegInfo)tempPnr;
                         //004处理规则
                         //规则：根据Pnr_Ref_Crt_Dt，Pax_Id关联，Seg_Rltv_Posn不为“0”，
                         // 行动代码Opr_Stat_Cd在'DK','DR','FS','GK','HK','HS','IS','KK','KL','LK','PK','PL','PU','RR','SS','TK'中，
                         // （规则同BIDT的判断规则）
                         if(fltSegInfo.getSegRltvPosn()!=0&&fltSegInfo.isNormalStatCd()){
                             FullPsgInfo fullPsgInfo = pnrSegInfo.getFullPsgInfo(fltSegInfo.getPaxId());
                             if(fullPsgInfo!=null){
                                 fullPsgInfo.addFltSegInfo(fltSegInfo);
                             }

                         }

                     }else if(tempPnr.getPartTyp().equalsIgnoreCase(PnrInfo.PNR_FLT_SEG_ACT_CD_TYPE)){
//                         FltSegActCdInfo fltSegActCdInfo = (FltSegActCdInfo)tempPnr;

                         //TODO:没做

                     }else if(tempPnr.getPartTyp().equalsIgnoreCase(PnrInfo.PNR_PAX_DATA_TYPE)){
                         //008 paxdata信息
                         PnrPaxData pnrPaxData = (PnrPaxData)tempPnr;
                         pnrSegInfo.addPnrPaxDatas(pnrPaxData);

                     }else if(tempPnr.getPartTyp().equalsIgnoreCase(PnrInfo.PNR_PSG_ID_INFO_TYPE)){
                        // 010
                         //规则：首先关联008取Pax_Dat_rltv_posn不为0的数据对应的Pax_Dat_Id，
                         // 通过Pnr_Ref_Crt_Dt, Pax_Id关联010表，判断010 中有超过两条的数据（
                         // 有时可以通过取最大的Pax_Dat_Id，有时最大的是同行的婴儿，暂时没有办法解决~）
                            PsgIdInfo psgIdInfo= (PsgIdInfo)tempPnr;
                            FullPsgInfo fullPsgInfo = pnrSegInfo.getFullPsgInfo(psgIdInfo.getPaxId());
                         pnrSegInfo.putPaxDatPaxId(psgIdInfo.getPaxDatId(),psgIdInfo.getPaxId());

                         PnrPaxData pnrPaxData = pnrSegInfo.getPnrPaxData(psgIdInfo.getPaxDatId());

                         if(pnrPaxData!=null&&pnrPaxData.getPaxDatRltvPosn()!=0){
                                if(fullPsgInfo!=null){
                                    fullPsgInfo.addPsgIdInfo(psgIdInfo);
                                }
                         }

                     }else if(tempPnr.getPartTyp().equalsIgnoreCase(PnrInfo.PNR_BIG_CLIENT)){
                        // 规则：Rltv_Posn不为0，通过Pnr_Ref_Crt_Dt, Pax_Dat_Id关联
                       BigClientInfo bigClientInfo = (BigClientInfo)tempPnr;

                        PnrPaxData paxData = pnrSegInfo.getPnrPaxData(bigClientInfo.getPaxDatId());
                         if(paxData!=null&&bigClientInfo.getRltvPosn()!=0){
                             //加入
                             String paxId = pnrSegInfo.getPaxIdFromDatId(bigClientInfo.getPaxDatId());
                             if(paxId!=null){
                                 pnrSegInfo.getFullPsgInfo(paxId).addBigInfo(bigClientInfo);
                             }
                         }
                     }else if(tempPnr.getPartTyp().equalsIgnoreCase(PnrInfo.PNR_CONTRACT_TYPE)){
                            ContractInfo contractInfo = (ContractInfo)tempPnr;
                            if(contractInfo.getRltvPosn()!=0){
                                String paxId = pnrSegInfo.getPaxIdFromDatId(contractInfo.getPaxDatId());
                                if(paxId!=null){
                                    pnrSegInfo.getFullPsgInfo(paxId).addContractInfo(contractInfo);
                                }
                            }
                     }

                }//for循环结束


            }
        return pnrSegInfo;
    }
}
