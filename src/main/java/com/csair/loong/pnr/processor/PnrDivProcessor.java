package com.csair.loong.pnr.processor;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.bi.commons.FieldSegment;




public class PnrDivProcessor implements Processor<String,List<File>> {
    protected static final Logger log = LoggerFactory.getLogger(PnrDivProcessor.class);
    
//    private String fileName;
    private FieldSegment segment;
//    private RedisClient redisClient =  new RedisSingleClient();
    private Map<String,BufferedWriter> writerMap = new HashMap<String,BufferedWriter>();
    private Map<String,String> titleMap = new HashMap<String,String>();
    
    public PnrDivProcessor() {
        initTitle();
//        this.fileName = fileName;
    }
    public PnrDivProcessor(FieldSegment segment) {
        initTitle();
//        this.fileName = fileName;
        this.segment = segment;
    }

    public List<File> doit(String fileName) {
        BufferedReader br = null;
        Pattern pattern = Pattern.compile("\\s{2,}");
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<File> fileList = new ArrayList<>();
        log.info("process start");
        try {
              br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    fileName), "GBK"));
            String line = "";
            int index=1;
            String tempLine = "";
            String strDt="";
            String endDt="";
            String key = "";
            
            while((line=br.readLine())!=null){
                String cacheKey = "";
                tempLine = line;
                if(index==1){
                    
                    String[] test = pattern.split(line);
                    if(test.length>=2){
                          strDt = test[1].substring(0, 16);
                          //endDt = test[1].substring(16);
                          try {
                              
                            Date str = DateUtils.parseDate(strDt, new String[]{"yyyyMMddHH:mm:ss"});
                            //Date end = DateUtils.parseDate(endDt, new String[]{"yyyyMMddHH:mm:ss"});
                            strDt = simpleFormat.format(str);
                            //endDt = simpleFormat.format(end);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                         
                    }
                    index ++;
                    continue;
                    
                }else{
                
                    tempLine = tempLine.replace(";^;","");//去除无意义的字符
                    
                    String[] tempLines = tempLine.split(",");
                    String type = tempLines[0].trim();
                    if(tempLines!=null&&tempLines.length>2&&type.equals("001")){
                            key = strDt+","+endDt+","+tempLines[1]+","+tempLines[2]+","+tempLines[1]+tempLines[2]+",";
                    } 
                    
                    if(type.equals("001")){
                        tempLine = tempLine.substring(0, 4) + strDt+","+endDt+"," + tempLine.substring(4, 20)+tempLines[1]+tempLines[2]+","+tempLine.substring(20, tempLine.length());
                    }else{
                        tempLine = tempLine.substring(0, 4) + key + tempLine.substring(4, tempLine.length());
                      }

                    if(!writerMap.containsKey(type)){
                        File resultFile = new File(fileName+"."+type);
                        fileList.add(resultFile);
                        BufferedWriter  temp = new BufferedWriter(new OutputStreamWriter(
                                new FileOutputStream(resultFile),"UTF-8"));
                        writerMap.put(type,temp);
                        //添加title
                        BufferedWriter writer = writerMap.get(type);
                        writer.write(titleMap.get(type)+"\n");
                    }
                    BufferedWriter writer = writerMap.get(type);
                    writer.write(tempLine+"\n");
                }
                index ++;
                if(index%1000000==0){
                    log.info("----process:"+index+" lines");
                }

                //redisClient.push(cacheKey,tempLine);
            }
            log.info("process is over");
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            
            log.error("File:"+fileName+",not found",e.getLocalizedMessage());
            
        } catch (IOException e) {
            
            log.error("IO错误", e);
          
        }finally {
            assert br != null;
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                Iterator iter = writerMap.keySet().iterator();
                while(iter.hasNext()){
                    BufferedWriter writer =   writerMap.get(iter.next());
                    writer.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileList;
    }
    
    public void initTitle(){
        
        titleMap.put("001", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Pnr_Grp_Ind,Pnr_Cncl_Ind,Pnr_Head_Txt,Pnr_Own_Gds,Pnr_Orig_Ref,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6,Filler7,Filler8,Filler9,Filler10");
        titleMap.put("002", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Pax_Id,Pax_Rltv_Posn,Txn_Itm_Nbr_Add,Txn_Itm_Nbr_Cncl,Pax_Mv_Ind,Pax_Ful_Nm,Pax_Lst_Nm,Pax_Fst_Nm,Pax_Cn_Nm,Vip_Ind,Pax_Age_Ctg_Cd,Pax_Sltn,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6,Filler7,Filler8,Filler9,Filler10");
        titleMap.put("003", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Pax_Id,Pax_Hist_Itm_Nbr,Txn_Itm_Nbr_Add,Txn_Itm_Nbr_Cncl,Pax_Sltn,Pax_Ful_Nm,Pax_Lst_Nm,Pax_Fst_Nm,Pax_Cn_Nm,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6");
        titleMap.put("004", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Seg_Id,Pax_Id,Seg_Rltv_Posn,Txn_Itm_Nbr_Add,Txn_Itm_Nbr_Cncl,Carr_Cd,Flt_Nbr,Flt_Nbr_Sfx,Dpt_Airpt_Cd,Arrv_Airpt_Cd,Dpt_Dt_Lcl,Dpt_Dow,Dpt_Tm_Lcl,Arrv_Dt_Lcl,Arrv_Tm_Lcl,Air_Seg_Flt_Typ,Sub_Cls_Cd,Opr_Stat_Cd,Mktg_Carr_Cd,Mktg_Flt_Nbr,Mktg_Flt_Nbr_Sfx,Prm_Carr_Cd,Prm_Flt_Nbr,Prm_Flt_Nbr_Sfx,Cdshr_Typ_Cd,Prm_Sub_Cls_Cd,Ovb_Qty,Marr_Ind,Flt_Inf_Ind,Oth_Txt,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6,Filler7,Filler8,Filler9,Filler10,Filler11,Filler12");
        titleMap.put("005", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Seg_Id,Stat_Id,Txn_Itm_Nbr_Add,Txn_Itm_Nbr_Cncl,Opr_Stat_Cd,Prev_Stat_Cd,Curr_Pax_Nbr,Seg_Pax_Nbr,Filller1,Filller2,Filller3,Filller4,Filller5,Filller6");
        titleMap.put("006", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Seg_Id,Seg_Rltv_Posn,Grnd_Seg_Tpy,Prod_Supp,Txn_Itm_Nbr_Add,Txn_Itm_Nbr_Cncl,Dep_Pnt_Cd,Arrv_Pnt_Cd,Opr_Stat_Cd,Grnd_Dpt_Dt_Lcl,Grnd_Arrv_Dt_Lcl,Grnd_Addt_Txt,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6");
        titleMap.put("007", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Txn_Id,Txn_Dt,Txn_Tm,Pnr_Curr_Offc_Cd,Pnr_Curr_Agc_Cd,Pnr_Orig_Bkg_Offc,Txn_Rcvd_Txt,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6");
        titleMap.put("008", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Pax_Dat_Id,Pax_Dat_Typ_Cd,Carr_Cd,Txn_Itm_Nbr_Add,Txn_Itm_Nbr_Cncl,Pax_Dat_Rltv_Posn,Pax_Dat_Txt,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6,Filler7,Filler8,Filler9,Filler10,Filler11,Filler12");
        titleMap.put("009", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Pax_Dat_Id,Pax_Id,Card_Issr_Cd,Card_Lvl_Cd,Card_Nbr,Carr_Cd,Flt_Nbr,Flt_Nbr_Sfx,Opr_Stat_Cd,Pax_Qty,Dpt_Airpt_Cd,Arrv_Airpt_Cd,Sub_Cls_Cd,Dpt_Dt_Lcl,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6");
        titleMap.put("010", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Pax_Dat_Id,Pax_Id,Indvl_Id_Typ_Cd,Indvl_Id_Nbr,Cntry_Cd,Indvl_Bth_Day,Gnd_Cd,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6,Filler7,Filler8");
        titleMap.put("011", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Pax_dat_Id,Pax_Id,Rltv_Posn,Txn_Itm_Nbr_Add,Txn_Itm_Nbr_Cncl,Carr_Cd,Flt_Nbr,Dpt_Airpt_Cd,Arrv_Airpt_Cd,Dpt_Dt_Lcl,Dpt_Cls_Cd,Tkt_Nbr_Pri,Opr_Stat_Cd,Ful_Txt,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6");
        titleMap.put("012", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Pnr_Spt_Itm_Nbr,Pnr_Ref_sub,Pnr_Crt_Dt_sub,Txn_Itm_Nbr_Add,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6");
        titleMap.put("013", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Grp_Nm,Pnr_Stat_Cd,Chg_Seq_Nbr,Grp_Orig_St_Qty,Txn_Itm_Nbr_Sp,Txn_Itm_Nbr_Cncl,Grp_Curr_St_Qty,Grp_St_Sp_Qty,Grp_St_Cncl_Qty,Grp_Curr_Pax_Qty,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6");
        titleMap.put("014", "Part_Typ,Sta_Dt,update_dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Txn_Id,Bkg_Cty_GDS,Pnr_Ref_opp,Bkg_Offc_Cd,Iata_Nbr,Cty_Cd,Airln_Cd,Usr_Typ,ISO_Cntry_Cd,ISO_Crncy,Dty_Cd,ERSP_Cd,Fst_Dpt_Pnt,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6");
   		titleMap.put("015","Part_Typ,Sta_Dt,Updata_Dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Pax_dat_Id,Rltv_Posn,Txn_Itm_Nbr_Add,Txn_Itm_Nbr_Cncl,Carr_Cd,Opr_Stat_Cd,Pax_Qty,Vico_Card,Tkt_Typ,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6");
		 titleMap.put("016","Part_Typ,Sta_Dt,Updata_Dt,Pnr_Ref,Pnr_Crt_Dt,Pnr_Ref_Crt_Dt,Pax_dat_Id,Rltv_Posn,Txn_Itm_Nbr_Add,Txn_Itm_Nbr_Cncl,Carr_Cd,Ct_Typ,Ctc_Nbr,Pax_Id,Filler1,Filler2,Filler3,Filler4,Filler5,Filler6");
	}
    
    
    public static void main(String[] args){
//        Pattern pattern = Pattern.compile("\\s{2,}");
//        String[] test = pattern.split("PNR FILE ICS   2013042924:00:002013043024:00:00  272811");
//        String strDt = test[1].substring(0, 16);
//        String endDt = test[1].substring(16);
//        System.out.println(test.length);
        String fileName = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\2016\\org\\CZ_DFP_20161206_1.txt";
        Processor<String,List<File>> testP = new PnrDivProcessor();
        List<File> fileList = testP.doit(fileName);

    }

}
