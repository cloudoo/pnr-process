package com.csair.loong.domain;

import com.sun.javafx.collections.MappingChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cloudoo on 2016/9/18.
 * 
 */
public class PnrSegInfo {

    private PnrHeader header;
    private List<FullPsgInfo>  psgInfos = new ArrayList<>();
    private List<PnrPaxData> pnrPaxDatas = new ArrayList<>();
    //辅助存储信息
    private  Map<String,FullPsgInfo> pnrPsgInfoMap = new HashMap<>();
    private Map<String,PnrPaxData> pnrPaxDataMap = new HashMap<>();
    private Map<String,String> paxDat2IdMap = new HashMap<>();
    public void addFullPsgInfo(FullPsgInfo fullPsgInfo){
        if(psgInfos==null){
            psgInfos = new ArrayList<>();
        }
        psgInfos.add(fullPsgInfo);

        if(pnrPsgInfoMap == null){
            pnrPsgInfoMap = new HashMap<>();
        }
        if(fullPsgInfo.getPnrPsgInfo()!=null){
            if(pnrPsgInfoMap.containsKey(fullPsgInfo.getPnrPsgInfo().getPaxId())){

            }else{
                pnrPsgInfoMap.put(fullPsgInfo.getPnrPsgInfo().getPaxId(),fullPsgInfo);
            }
        }

    }
    public FullPsgInfo getFullPsgInfo(String paxId){
        return pnrPsgInfoMap.get(paxId);
    }
    public void addPnrPaxDatas(PnrPaxData pnrPaxData){
        if(pnrPaxDatas==null){
            pnrPaxDatas = new ArrayList<>();
        }
        pnrPaxDatas.add(pnrPaxData);
        if(pnrPaxDataMap == null){
            pnrPaxDataMap = new HashMap<>();
        }
        String paxDataId = String.valueOf(pnrPaxData.getPaxDatId());
        if(pnrPaxDataMap.containsKey(paxDataId)){

        }else{
            pnrPaxDataMap.put(String.valueOf(pnrPaxData.getPaxDatId()),pnrPaxData);
        }

    }

    public void putPaxDatPaxId(String paxDatId,String paxId){
        if(paxDat2IdMap==null)
            paxDat2IdMap = new HashMap<>();
        paxDat2IdMap.put(paxDatId,paxId);
    }
    public String getPaxIdFromDatId(String paxDatId){
        return paxDat2IdMap.get(paxDatId);
    }
    public PnrPaxData getPnrPaxData(String paxDataId){
        return pnrPaxDataMap.get(paxDataId);
    }

    public PnrHeader getHeader() {
        return header;
    }

    public void setHeader(PnrHeader header) {
        this.header = header;
    }

    public List<FullPsgInfo> getPsgInfos() {
        return psgInfos;
    }

    public void setPsgInfos(List<FullPsgInfo> psgInfos) {
        this.psgInfos = psgInfos;
    }

    public List<PnrPaxData> getPnrPaxDatas() {
        return pnrPaxDatas;
    }

    public void setPnrPaxDatas(List<PnrPaxData> pnrPaxDatas) {
        this.pnrPaxDatas = pnrPaxDatas;
    }
}
