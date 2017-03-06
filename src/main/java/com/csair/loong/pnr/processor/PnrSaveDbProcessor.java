package com.csair.loong.pnr.processor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.loong.commons.cache.RedisClient;
@Deprecated
public class PnrSaveDbProcessor implements Processor<String,Boolean>{
    private RedisClient redisClient = RedisClient.getInstance();
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    
    protected static final Logger log = LoggerFactory.getLogger(PnrSaveDbProcessor.class);

    @Override
    public Boolean doit(String pnrKey) {
        
        String value = redisClient.pull(pnrKey);
        
        executorService.submit(new Save2Db(value));
        return false;
    }
    
     class Save2Db implements Runnable{
        
        String value="";
        public Save2Db(String value){
            this.value = value;
        }
        @Override
        public void run() {
            
            String temp = redisClient.pull(value);
            
            log.info(temp);
            
        }
         
     }
     
     public static void main(String[] args){
         Processor testP = new PnrSaveDbProcessor();
//         testP.doit();
     }

 
}
