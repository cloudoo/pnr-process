package com.csair.loong.pnr.processor;

import com.csair.datatrs.common.FieldSegment;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 *
 *
 * 性能不太理想，
 *
 */
@Deprecated
public class PnrNormalTagProcessor implements Processor<File,File> {
    protected static final Logger log = LoggerFactory.getLogger(PnrNormalTagProcessor.class);
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//    private String fileName;
    private FieldSegment segment;
//    private RedisClient redisClient =  new RedisSingleClient();
    private Map<String,BufferedWriter> writerMap = new HashMap<String,BufferedWriter>();
    private Map<String,String> titleMap = new HashMap<String,String>();

    public PnrNormalTagProcessor() {
        initTitle();
    }
    public PnrNormalTagProcessor(String fileName, FieldSegment segment) {
        initTitle();
//        this.fileName = fileName;
        this.segment = segment;
    }

    public File doit(File file) {
        BufferedReader br = null;
        BufferedWriter  writer = null;
        Pattern pattern = Pattern.compile("\\s{2,}");
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File resultFile = null;
        log.info("process start");
        try {
              br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
            String line = "";
            int index=1;
            String tempLine = "";
            String strDt="";
            String endDt="";
            String key = "";
            List<String[]> tempPnrGroup = new ArrayList<String[]>();
            //写入数据
            resultFile = new File(file.getName()+".eldertag");
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(resultFile),"GBK"));
            boolean isEld= false;
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

                    if(type.equalsIgnoreCase("001")){

                        if(tempPnrGroup.size()>0){

                                //处理tempPnrGroup信息
                           String[] elderTags =  getElderTag(tempPnrGroup);

                            if(elderTags!=null){
                                Joiner joiner =  Joiner.on(",").useForNull("");
                                String etStr = joiner.join(elderTags);

                                log.info("写入标签:"+etStr);

                                writer.write(etStr+"\n");
                                tempPnrGroup.clear();
                            }
                        }
                        //002中，Pax_Rltv_Posn字段不为“0”，如果为0 ，则不取这个旅客的相关内容
                    }

                    tempPnrGroup.add(tempLines);




                }
                index ++;
                log.info("----process:"+index);

                //redisClient.push(cacheKey,tempLine);
            }
            log.info("process is over");
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            
            log.error("File:"+file.getName()+",not found",e.getLocalizedMessage());
            
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
                assert writer != null;
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultFile;
    }

    public String[] getElderTag(List<String[]> pnrGroup){
        String[] temp = new String[9];

        for(String[] line:pnrGroup){
            //001中，Pnr_Cncl_Ind字段不为“D”，如果为D，则不取这个PNR相关的内容
            if(line[0].equals("001")){
                if(StringUtils.trimToEmpty(line[4]).equals("D")){
                    return null;
                }
            }
              if(line[0].equals("002")){
//                  if(StringUtils.trimToEmpty(line[4]).equals("0")){
//                      return null;
//                  }
                  temp[0] = line[1];//Pax_Id	旅客序号
                  temp[1] = line[6];//Pax_Ful_Nm	旅客全名
                  temp[2] = line[9];//Pax_Cn_Nm	中文姓名
              }else if(line[0].equals("010")){
                  temp[3] = line[3];//Indvl_Id_Typ_Cd	旅客证件类型
                  temp[4] = line[4];//Indvl_Id_Nbr	旅客证件号
                  temp[5] = line[5];//Cntry_Cd	旅客国别代码
                  temp[6] = line[6];//Indvl_Bth_Day	旅客出生日期
                  temp[7] = line[7];//Gnd_Cd	旅客性别
              }
        }
//        8、如果旅客生日为空，旅客证件类型Indvl_Id_Typ_Cd=“I”，
//        （1）且Indvl_Id_Nbr长度等于18，取出7-14位，作为生日日期，补充到旅客生日字段。
//        （2）且Indvl_Id_Nbr长度等于20，Indvl_Id_Nbr前两个字符=“NI”,其他情况不管，取出9-16位，作为生日日期，补充到旅客生日字段。

        if(StringUtils.trimToNull(temp[6])==null&&StringUtils.trimToEmpty(temp[3]).equals("I")){
             //
            String indvlIdNbr = StringUtils.trimToEmpty(temp[4]);
            if(indvlIdNbr.length()>0 && indvlIdNbr.length()==18){
                temp[6] = indvlIdNbr.substring(7,14);
            }

            if(indvlIdNbr.length()>0 && indvlIdNbr.indexOf("NI")>=0&& indvlIdNbr.length()==20){
                temp[6] = indvlIdNbr.substring(9,16);
            }
        }

//        try {
//
//            Date bir = sdf.parse(temp[6]);
//            Date now = new Date();
//            long age = (now.getTime()- bir.getTime())/(365*24*60*60*1000);
//
//            if(age<65){
//                return null;
//            }else{
//                return temp;
//            }
//        } catch (ParseException e) {
//
//            e.printStackTrace();
//        }

        return temp;

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
    }
    
    
    public static void main(String[] args){
//        Pattern pattern = Pattern.compile("\\s{2,}");
//        String[] test = pattern.split("PNR FILE ICS   2013042924:00:002013043024:00:00  272811");
//        String strDt = test[1].substring(0, 16);
//        String endDt = test[1].substring(16);
//        System.out.println(test.length);
        String fileName = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\test.txt";

        Processor<File,File> testP = new PnrNormalTagProcessor();
        File resultFile = testP.doit(new File(fileName));

    }

}
