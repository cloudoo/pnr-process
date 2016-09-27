package com.csair.loong.domain;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by cloudoo on 2016/9/18.
 */
public class FullPsgInfo {
    private PnrHeader pnrHeader;
    private PnrPsgInfo pnrPsgInfo;
    private List<PsgChgNameInfo> psgChgNameInfos;

    private List<FltSegInfo> fltSegInfos;
    private  Map<String,FltSegInfo> fltSegInfoMap;
    private List<PsgIdInfo> psgIdInfos;
    private Map<String,PsgIdInfo> psgIdInfoMap=new HashMap<>();
    private List<BigClientInfo> bigClientInfos = new ArrayList<>();
    private List<ContractInfo> contractInfos = new ArrayList<>();


    public void addPsgChgNameInfo(PsgChgNameInfo psgChgNameInfo){
        if(psgChgNameInfos == null){
            psgChgNameInfos = new ArrayList<>();
        }
        psgChgNameInfos.add(psgChgNameInfo);
    }
    public void addFltSegInfo(FltSegInfo fltSegInfo){
        //TODO:是否需要判断航段？
        if(fltSegInfos==null){
            fltSegInfos = new ArrayList<>();
        }
        fltSegInfos.add(fltSegInfo);
        if(fltSegInfoMap==null){
            fltSegInfoMap = new HashMap<String,FltSegInfo>();
        }
        fltSegInfoMap.put(String.valueOf(fltSegInfo.getSegId()),fltSegInfo);
    }

    public FltSegInfo getFltSegInfo(String segId){
           return fltSegInfoMap.get(segId);
    }

    public void removeFltSegInfo(String segId){
        FltSegInfo fltSegInfo = fltSegInfoMap.get(segId);
        if(fltSegInfo!=null){
            fltSegInfos.remove(fltSegInfo);
            fltSegInfoMap.remove(segId);
        }
    }

    public void addPsgIdInfo(PsgIdInfo psgIdInfo){
        if(psgIdInfos==null){
            psgIdInfos = new ArrayList<>();
        }
        psgIdInfos.add(psgIdInfo);

        if(psgIdInfoMap==null){
            psgIdInfoMap = new HashMap<>();
        }
        psgIdInfoMap.put(psgIdInfo.getPaxDatId(),psgIdInfo);
    }

    public PsgIdInfo getPsgIdInfo(String paxDatId){
        return psgIdInfoMap.get(paxDatId);
    }

    public void addBigInfo(BigClientInfo bigClientInfo){
        bigClientInfos.add(bigClientInfo);
    }

    public void addContractInfo(ContractInfo contractInfo){
        contractInfos.add(contractInfo);
    }

    /**********************************get && set ********************************************************************/
    public PnrHeader getPnrHeader() {
        return pnrHeader;
    }

    public void setPnrHeader(PnrHeader pnrHeader) {
        this.pnrHeader = pnrHeader;
    }

    public PnrPsgInfo getPnrPsgInfo() {
        return pnrPsgInfo;
    }

    public void setPnrPsgInfo(PnrPsgInfo pnrPsgInfo) {
        this.pnrPsgInfo = pnrPsgInfo;
    }

    public List<PsgChgNameInfo> getPsgChgNameInfos() {
        return psgChgNameInfos;
    }

    public void setPsgChgNameInfos(List<PsgChgNameInfo> psgChgNameInfos) {
        this.psgChgNameInfos = psgChgNameInfos;
    }


    public List<FltSegInfo> getFltSegInfos() {
        return fltSegInfos;
    }

    public void setFltSegInfos(List<FltSegInfo> fltSegInfos) {
        this.fltSegInfos = fltSegInfos;
    }

    public List<PsgIdInfo> getPsgIdInfos() {
        return psgIdInfos;
    }

    public void setPsgIdInfos(List<PsgIdInfo> psgIdInfos) {
        this.psgIdInfos = psgIdInfos;
    }

    public List<BigClientInfo> getBigClientInfos() {
        return bigClientInfos;
    }

    public void setBigClientInfos(List<BigClientInfo> bigClientInfos) {
        this.bigClientInfos = bigClientInfos;
    }

    public List<ContractInfo> getContractInfos() {
        return contractInfos;
    }

    public void setContractInfos(List<ContractInfo> contractInfos) {
        this.contractInfos = contractInfos;
    }
}
