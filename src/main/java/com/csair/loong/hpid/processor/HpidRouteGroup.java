package com.csair.loong.hpid.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.loong.hpid.domain.FltRoute;
import com.csair.loong.pnr.old.PnrFileReader;
import com.csair.loong.pnr.processor.ProcessorChain;
import com.csair.loong.service.FileReader;
import com.csair.loong.service.FileWriter;
import com.csair.loong.service.Writer;

public class HpidRouteGroup {

	private static final Logger log = LoggerFactory
			.getLogger(HpidRouteGroup.class);
	private ProcessorChain chain;
	private List<FltRoute> fltRouteList = new ArrayList<FltRoute>();
	private Writer routeFileWriter;
	private File file;

	public void init() {

		routeFileWriter = new FileWriter(file.getAbsolutePath() + ".group");

		chain = new ProcessorChain().addProcessor(new NbrPaxFilter());
		// .addProcessor(new NbrPaxFilter());

	}

	public HpidRouteGroup(File file) {
		this.file = file;
		init();
	}

	public HpidRouteGroup(String fileName) {
		this.file = new File(fileName);
		init();
	}

	public void process() {

		List<FltRoute> fltRouteList = new ArrayList<FltRoute>();
		Map<String, FltRoute> fltRouteMap = new HashMap<String, FltRoute>();
		
		BufferedReader br = null;
		BufferedWriter wr = null;
		String line = null;
		List<Object> objects = null;

		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "GBK"));
			wr = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(file.getAbsolutePath()
							+ ".error")), "UTF-8"));
			int i=0;
			while ((line = br.readLine()) != null) {
				// 解析为响应的类
				try {
					// 处理每行记录
					String[] lines = line.split(",");
					FltRoute fltRoute = new FltRoute();
					fltRoute.init(lines);
					
					if(isValidFlightTrip(fltRoute)){
						
						String key = fltRoute.getRoute() + fltRoute.getOrigCity()
								+ fltRoute.getDestCity() + fltRoute.getDep()
								+ fltRoute.getArv() + fltRoute.getTripDepTime()
								+ fltRoute.getTripArvTime() + fltRoute.getDeptq()
								+ fltRoute.getArvtq() + fltRoute.getDistance()
								+ fltRoute.getTripElapTime() + fltRoute.getTripSegCount();

						if (!fltRouteMap.containsKey(key)) {
							fltRouteMap.put(key, fltRoute);
							fltRouteList.add(fltRoute);
						} else {
							FltRoute tempRoute = fltRouteMap.get(key);
							tempRoute.setTripFreq(tempRoute.getTripFreq() + 1);
							tempRoute.setNbrPax(tempRoute.getNbrPax()
									+ fltRoute.getNbrPax());
						}
					}
					
					i++;
					if(i%200000==0){
						System.out.println("第"+i+"行");
						i = 0;
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("[FileReader]解析异常！" + line + "\n\r"
							+ e.getStackTrace());
					wr.write("fail," + line + "\b\n");
				}

			}

		} catch (FileNotFoundException e) {
			log.error("[FileReader][fileName=" + file.getAbsolutePath()
					+ "]没有找到", e);
		} catch (IOException e) {
			log.error("[FileReader][fileName=" + file.getAbsolutePath()
					+ "] io错误", e);
		} finally {

			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("[FileReader][fileName=" + file.getAbsolutePath()
							+ "] br关闭错误", e);
				}
			}
			if (wr != null) {
				try {
					wr.close();
				} catch (IOException e) {
					log.error("[FileReader][fileName=" + file.getAbsolutePath()
							+ "] wr关闭错误", e);
				}
			}
		}

		group(fltRouteList);

	}

	/**
	 * 飞行的速度不能大于 1500公里/小时
	 * @param fltRoute
	 * @return
	 */
	public boolean isValidFlightTrip(FltRoute fltRoute){
		double ratio = fltRoute.getDistance() * 60 / Double.parseDouble(fltRoute.getTripElapTime()) ;
		
		return ratio > 1500? false:true;
	}
	
	public void group(List<FltRoute> list) {

		Iterator<FltRoute> iter = list.iterator();
		while (iter.hasNext()) {
			FltRoute fltRoute = iter.next();
			routeFileWriter.writer(fltRoute.toString());
		}

	}
	
	public void close() {

		if (routeFileWriter != null) {
			routeFileWriter.close();
		}
	}
	
	public static void main(String[] args) {

		String dir = "S:\\hpid\\HPID.txt.hpid.route";

		HpidRouteGroup hpidRouteGroup = new HpidRouteGroup(dir);

		hpidRouteGroup.process();
		
	}

}
