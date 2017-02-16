package com.csair.loong.hpid.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.loong.hpid.domain.FltRoute;
import com.csair.loong.pnr.processor.Processor;

public class NbrPaxFilter implements Processor<List<FltRoute>, List<FltRoute>> {
	private static final Logger log = LoggerFactory
			.getLogger(NbrPaxFilter.class);
	@Override
	public List<FltRoute> doit(List<FltRoute> fltRoutes) {

		Map<String, FltRoute> fltRouteMap = new HashMap<String, FltRoute>();

		List<FltRoute> result = new ArrayList<FltRoute>();

		Iterator<FltRoute> iter = fltRoutes.iterator();
		log.info("filter route..");
		while (iter.hasNext()) {
			
			FltRoute fltRoute = iter.next();
			
			if(isValidFlightTrip(fltRoute)){
				
				String key = fltRoute.getRoute() + fltRoute.getOrigCity()
						+ fltRoute.getDestCity() + fltRoute.getDep()
						+ fltRoute.getArv() + fltRoute.getTripDepTime()
						+ fltRoute.getTripArvTime() + fltRoute.getDeptq()
						+ fltRoute.getArvtq() + fltRoute.getDistance()
						+ fltRoute.getTripElapTime() + fltRoute.getTripSegCount();

				if (!fltRouteMap.containsKey(key)) {
					fltRouteMap.put(key, fltRoute);
					result.add(fltRoute);
				} else {
					FltRoute tempRoute = fltRouteMap.get(key);
					tempRoute.setTripFreq(tempRoute.getTripFreq() + 1);
					tempRoute.setNbrPax(tempRoute.getNbrPax()
							+ fltRoute.getNbrPax());
				}
			}
			
		}

		return result;
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

}
