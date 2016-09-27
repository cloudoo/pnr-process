package com.csair.loong.pnr.processor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by cloudpj on 16/8/14.
 */
public class PnrElderTagProcessor extends Processor<File,File> {
    protected static final Logger log = LoggerFactory.getLogger(PnrElderTagProcessor.class);
//    private String fileName;
    SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMdd");
    Calendar now =null;


    @Override
    public File doit(File file) {
        File resultFile = null;
        BufferedReader br = null;
        BufferedWriter writer = null;
        Pattern pattern = Pattern.compile("\\s{2,}");
        now = Calendar.getInstance();

        log.info("process start");
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    file), "UTF-8"));
            String line = "";
            int index = 1;
            String tempLine = "";
            String strDt = "";
            String endDt = "";
            String key = "";
            List<String[]> tempPnrGroup = new ArrayList<String[]>();
            //写入数据
            resultFile = new File(file.getAbsolutePath() + ".eldertag.process");
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(resultFile), "UTF-8"));


            while ((line = br.readLine()) != null) {
//                String tempLine = "";
                String[] tempLines = line.split(",");
                if(tempLines[7]!=null){
                    int age =   getAge(tempLines[7]);
                    if(age>=65){
                        writer.write(line+"\n");
                    }
                }


            }
        }catch (UnsupportedEncodingException | FileNotFoundException e) {

            log.error("File:" + file.getName() + ",not found", e.getLocalizedMessage());

        } catch (IOException e) {

            log.error("IO错误", e);

        }   finally {
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

    public int getAge(String yyyyMMdd)  {
        int age = 0;


        Date dateOfBirth = null;
        try {

            dateOfBirth = simpleFormat.parse(filterAlphbater(yyyyMMdd));

        } catch (ParseException e) {

            log.error("年龄解析错误:" +e);

        }

        Calendar born = Calendar.getInstance();


            if (dateOfBirth != null) {
                now.setTime(new Date());
                born.setTime(dateOfBirth);
                if (born.after(now)) {
                    log.error("出生在未来时间。。。。");
                }
                age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
                    age -= 1;
                }
            }

        return age;

    }


    /**
     * 去除数字字符中的字母
     * @param brtdh
     * @return
     */
    public String filterAlphbater(String brtdh){
        return brtdh.replaceAll("O","0").replaceAll("I","1");
    }

    public static void main(String[] args){

//        Processor testP = new PnrElderTagProcessor3("/Users/cloudpj/develop/IdeaProjects/PnrProcess/CZ_DFP_20160111_1.txt.eldertag");
        String fileName = "D:\\03_工作文件\\02_研究院\\01_项目\\05_PNR数据\\CZ_DFP_20151103_1.txt.eldertag";
        Processor<File,File> testP = new PnrElderTagProcessor();
        File file = testP.doit(new File(fileName));

    }
}
