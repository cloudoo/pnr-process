package com.csair.loong.pnr.processor;

import com.csair.datatrs.common.FieldSegment;
import com.csair.loong.domain.PsgInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


public class PnrNorTagProcessor implements Processor<File,File> {

    protected static final Logger log = LoggerFactory.getLogger(PnrNorTagProcessor.class);
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//    private String fileName;
    private FieldSegment segment;
    //    private RedisClient redisClient =  new RedisSingleClient();
    private Map<String, BufferedWriter> writerMap = new HashMap<String, BufferedWriter>();
    private Map<String, String> titleMap = new HashMap<String, String>();

    public PnrNorTagProcessor() {

    }

    public PnrNorTagProcessor(FieldSegment segment) {

        this.segment = segment;
    }

    public File doit(File orgFile) {
        BufferedReader br = null;
        BufferedWriter writer = null;
        Pattern pattern = Pattern.compile("\\s{2,}");
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File resultFile = null;
        log.info("process start");
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    orgFile), "UTF-8"));
            String line = "";
            int index = 1;
            String tempLine = "";
            String strDt = "";
            String endDt = "";
            String key = "";
            List<String[]> tempPnrGroup = new ArrayList<String[]>();
            //写入数据
            resultFile = new File(orgFile.getAbsolutePath()+ ".eldertag");
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(resultFile), "UTF-8"));
            boolean isEld = false;
            boolean isLoop = true;
            String pnrKey = "";
            Map<String,String> psgTag = new HashMap<String,String>();//key:paxId,value:证件号
            Map<String,PsgInfo> tempPsgMap = new HashMap<String,PsgInfo>();//key:paxId,value:旅客信息
            Map<String,PsgInfo> psgMap = new HashMap<String,PsgInfo>();//key:证件号,value:pagInfo
            List<PsgInfo> psgTagList = new ArrayList<PsgInfo>();//旅客信息
            Set<String> paxDatId = new HashSet<String>();//paxDatid

            while ((line = br.readLine()) != null) {
                String cacheKey = "";
                tempLine = line;
                if (index == 1) {

                    String[] test = pattern.split(line);
                    if (test.length >= 2) {
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
                    index++;
                    continue;

                } else {


                    tempLine = tempLine.replace(";^;", "");//去除无意义的字符
//                    String[] tempLines  = StringUtils.split(tempLine,",");
                    String[] tempLines = tempLine.split(",");



                    String type = tempLines[0].trim();

                    if (type.equalsIgnoreCase("001")) {

                        if (isEld) {

                                for(PsgInfo psgInfo:psgTagList){

//                                    log.info("写入标签:" + eldTag);
                                    if(psgInfo.getBthDay()!=null&&psgInfo.getBthDay().length()>0){
                                        writer.write(pnrKey+","+psgInfo.toString() + "\n");
                                    }

                                }

                        }
                        pnrKey = tempLines[1]+tempLines[2];
                        psgTag.clear();
                        psgMap.clear();
                        tempPsgMap.clear();
                        psgTagList.clear();
                        paxDatId.clear();

                        isEld = false;

                        if (StringUtils.trimToEmpty(tempLines[4]).equals("D")) {
                            isEld = false;
                        }else{
                            isEld = true;
                        }

                        //002中，Pax_Rltv_Posn字段不为“0”，如果为0 ，则不取这个旅客的相关内容
                    } else if (type.equals("002")&&isEld) {


                        if (!StringUtils.trimToEmpty(tempLines[2]).equals("0")) {

                            PsgInfo  psgInfo = new PsgInfo();

                            psgInfo.setPaxId(tempLines[1]);//Pax_Id	旅客序号
                            psgInfo.setPaxFulName(tempLines[6]); //Pax_Ful_Nm	旅客全名


                            if(tempLines.length>9){

                                psgInfo.setPaxCnName(tempLines[9]);//Pax_Cn_Nm	中文姓名
                            }
                            psgTagList.add(psgInfo);
                            psgTag.put(tempLines[1],"first");
                            tempPsgMap.put(tempLines[1],psgInfo);
                        }
                        log.info("Pax_Rltv_Posn字段为"+tempLines[2]+",index:" +index);

                    }
                    else if(type.equals("008")&&isEld){

                        if(!StringUtils.trimToEmpty(tempLines[6]).equals("0")){
                            paxDatId.add(tempLines[1]);
                        }

                    }
                    else if (type.equals("010")&&isEld) {

                         String psgIdNbr = psgTag.get(tempLines[2]);
                         PsgInfo oldInfo = tempPsgMap.get(tempLines[2]);

                        if(!paxDatId.contains(tempLines[1])){
                            continue;
                        }

                         PsgInfo psgIdInfo = getPsgIdInfo(tempLines);

                         if(psgIdNbr==null||psgIdInfo==null){
                             continue;
                         }
                         if(psgIdNbr.equals("first")){

                             if(psgIdInfo.getIndalIdNum().length()>0){

                                 oldInfo.setIndalIdTypeCd(psgIdInfo.getIndalIdTypeCd());
                                 oldInfo.setIndalIdNum(psgIdInfo.getIndalIdNum());
                                 oldInfo.setGndCd(psgIdInfo.getGndCd());
                                 oldInfo.setCntryCd(psgIdInfo.getCntryCd());
                                 oldInfo.setBthDay(psgIdInfo.getBthDay());

                                 psgTag.put(tempLines[1],psgIdInfo.getIndalIdNum());
                                 psgMap.put(psgIdInfo.getIndalIdNum(),oldInfo);

                             }

                         }else{

                             if(psgIdNbr.indexOf(psgIdInfo.getIndalIdNum())<0){

                                 psgIdInfo.setPaxId(oldInfo.getPaxId());
                                 psgIdInfo.setPaxFulName(oldInfo.getPaxFulName());
                                 psgIdInfo.setPaxCnName(oldInfo.getPaxCnName());


                                 psgTag.put(tempLines[1],psgIdNbr+","+psgIdInfo.getIndalIdNum());
                                 psgTagList.add(psgIdInfo);
                                 psgMap.put(psgIdInfo.getIndalIdNum(),psgIdInfo);

                             }

                         }

                    }


                }
                index++;
//                log.info("----process:" + index);

                //redisClient.push(cacheKey,tempLine);
            }
            log.info("process is over");
        } catch (UnsupportedEncodingException | FileNotFoundException e) {

            log.error("File:" + orgFile + ",not found", e.getLocalizedMessage());

        } catch (IOException e) {

            log.error("IO错误", e);

        } finally {
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

    public PsgInfo getPsgIdInfo(String[] tempLines) {

        if(tempLines.length>4) {

            PsgInfo ttPsg = new PsgInfo();
            String psgDate = "";
            ttPsg.setIndalIdTypeCd(tempLines[3]);//Indvl_Id_Typ_Cd	旅客证件类型
            ttPsg.setIndalIdNum(tempLines[4]); //Indvl_Id_Nbr	旅客证件号

            if (tempLines.length > 5) {
                ttPsg.setCntryCd(tempLines[5]);//Cntry_Cd	旅客国别代码

            }


            if(StringUtils.trimToEmpty(tempLines[3]).equals("I")){


                    //
                    String indvlIdNbr = StringUtils.trimToEmpty(tempLines[4]);
                    if (indvlIdNbr.length() > 0 && indvlIdNbr.length() == 18) {
                        psgDate = indvlIdNbr.substring(6, 14);
                    }

                    if (indvlIdNbr.length() > 0 && indvlIdNbr.indexOf("NI") >= 0 && indvlIdNbr.length() == 20) {
                        psgDate = indvlIdNbr.substring(8, 16);
                    }


            }else if(tempLines.length>6&&tempLines[6]!=null){
                psgDate = tempLines[6];
            }
              ttPsg.setBthDay(psgDate);//Indvl_Bth_Day	旅客出生日期

            if (tempLines.length > 7) {
                ttPsg.setGndCd(tempLines[7]);//Gnd_Cd	旅客性别
            }
            return ttPsg;
        }
        return null;

    }


    public static void main(String[] args) {
//        Pattern pattern = Pattern.compile("\\s{2,}");
//        String[] test = pattern.split("PNR FILE ICS   2013042924:00:002013043024:00:00  272811");
//        String strDt = test[1].substring(0, 16);
//        String endDt = test[1].substring(16);
//        System.out.println(test.length);

//            String temp = "10,30,2,,,MS,,,,,,,,,,";
//        String[] temps = temp.split(",");
//        String[] temps = StringUtils.split(temp,",");
//
//        System.out.print(temps.length); CZ_DFP_20151103_1

        String orgFileName = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\02_数据\\2015\\CZ_DFP_20151019_1.txt";
        Processor<File,File> testP = new PnrNorTagProcessor();
//        Processor testP = new PnrElderTagProcessor2("/Users/cloudpj/develop/IdeaProjects/PnrProcess/CZ_DFP_20160111_1.txt");
        File resultFile = testP.doit(new File(orgFileName));


    }

}
