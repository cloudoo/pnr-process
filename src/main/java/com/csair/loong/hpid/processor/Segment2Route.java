package com.csair.loong.hpid.processor;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.loong.hpid.domain.FltRoute;
import com.csair.loong.pnr.processor.Processor;

public class Segment2Route implements Processor<FltRoute, List<FltRoute>> {

	private static final Logger log = LoggerFactory
			.getLogger(Segment2Route.class);

	@Override
	public FltRoute doit(List<FltRoute> fltRoutes) {
		FltRoute fltRoute = new FltRoute();

		Iterator<FltRoute> iter = fltRoutes.iterator();
		while (iter.hasNext()) {
			FltRoute tempRoute = iter.next();
			if (StringUtils.isBlank(fltRoute.getTripId())) {
				fltRoute.copy(tempRoute);
			} else if (fltRoute.getArv().equals(tempRoute.getDep())) {
				fltRoute.setArv(tempRoute.getArv());
				fltRoute.setDestCity(tempRoute.getDestCity());
				fltRoute.setTripArvTime(tempRoute.getTripArvTime());
				fltRoute.addRoute(tempRoute.getArv());
				fltRoute.addDistance(tempRoute.getDistance());
				
			}
		}
		log.info("segment2Route finished");

		return filter(fltRoute);
	}

	public FltRoute filter(FltRoute fltRoute) {

		// 1. tripelaptime 转化为分钟数
		int elapMinutes = getMinutes(fltRoute.getTripElapTime());
		fltRoute.setTripElapTime(String.valueOf(elapMinutes));

		// 2.
		int tripDepTime = getMinutes(fltRoute.getTripDepTime());
		int tripArvTime = getMinutes(fltRoute.getTripArvTime());

		fltRoute.setDeptq(String.valueOf(getDepQ(tripDepTime)));

		fltRoute.setArvtq(String.valueOf(getArvQ(tripArvTime)));
		
		if (elapMinutes > 0)
			return fltRoute;
		return null;
	}

	public int getMinutes(String hourMinform) {

		String[] elaps = hourMinform.split(":");
		if (elaps.length == 2) {
			int hour = Integer.parseInt(elaps[0]);
			int minuter = Integer.parseInt(elaps[1]);
			return hour * 60 + minuter;
		}
		return 0;
	}

	public int getDepQ(int depTime) {

		if (depTime >= 0 && depTime < 420) {
			return 1;
		} else if (depTime >= 420 && depTime < 780) {
			return 2;
		} else if (depTime >= 780 && depTime < 1200) {
			return 3;
		} else if (depTime >= 1200 && depTime < 1440) {
			return 4;
		} else {
			return 5;
		}
	}

	public int getArvQ(int arvTime) {

		if (arvTime >= 0 && arvTime < 360) {
			return 1;
		} else if (arvTime >= 360 && arvTime < 660) {
			return 2;
		} else if (arvTime >= 660 && arvTime < 960) {
			return 3;
		} else if (arvTime >= 960 && arvTime < 1440) {
			return 4;
		} else {
			return 5;
		}
	}

	public static void main(String[] args) {

		String a = "10:11";
		String b = "8:11";

		System.out.println(a.compareTo(b));

	}

}
