/**
 * 
 */
package com.csair.loong.hpid.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csair.loong.hpid.domain.FltRoute;
import com.csair.loong.pnr.processor.ProcessorChain;
import com.csair.loong.service.FileReader;
import com.csair.loong.service.FileWriter;
import com.csair.loong.service.Writer;

/**
 * @author cloudoo
 *
 */
public class HpidRouteTransfer extends FileReader {
	private static final Logger log = LoggerFactory
			.getLogger(HpidRouteTransfer.class);
	private ProcessorChain chain;
	private List<FltRoute> fltRouteList = new ArrayList<FltRoute>();
	private Writer routeFileWriter;

	public void init() {

		routeFileWriter = new FileWriter(super.file.getAbsolutePath()
				+ ".hpid.route");

		chain = new ProcessorChain().addProcessor(new Segment2Route());
		// .addProcessor(new NbrPaxFilter());
		
		
	}

	public HpidRouteTransfer(String fileName) {
		super(fileName);
		init();
	}

	public HpidRouteTransfer(File file) {
		super(file);
		init();
	}

	@Override
	public void process(int index,String line) {

		if (line.indexOf("DEPDATE") < 0) {
			String[] lines = line.split("\t");

			String tripLapTime = StringUtils.trimToEmpty(lines[12]);
			String distance = StringUtils.trimToEmpty(lines[34]);
			log.info("start processing.......");

			if (tripLapTime.indexOf("99:99") < 0 && !distance.equals("0")) {
				// 1.把每行转化为对象
				FltRoute fltRoute = new FltRoute();

				fltRoute.hpidStringInit(lines);

				if (fltRouteList.size() == 0) {
					fltRouteList.add(fltRoute);
				} else if (fltRouteList.get(fltRouteList.size() - 1)
						.getTripId().equals(fltRoute.getTripId())) {
					fltRouteList.add(fltRoute);
				} else {

					FltRoute tRoute = (FltRoute) chain.doit(fltRouteList);

					log.info("start saveing.......");
					if (tRoute != null)
						writeFile(tRoute);

					fltRouteList = new ArrayList<FltRoute>();
					fltRouteList.add(fltRoute);
				}
			}
		}

	}

	public void writeFile(List<FltRoute> list) {

		Iterator<FltRoute> iter = list.iterator();
		while (iter.hasNext()) {
			FltRoute fltRoute = iter.next();
			routeFileWriter.writer(fltRoute.toString() + "\n");
		}
	}

	public void writeFile(FltRoute fltRoute) {

		routeFileWriter.writer(fltRoute.toString());

	}

	public void close() {

		if (routeFileWriter != null) {
			routeFileWriter.close();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String dir = "S:\\hpid\\HPID.txt";

		HpidRouteTransfer routeTransfer = new HpidRouteTransfer(dir);

		routeTransfer.reg();
		routeTransfer.close();
	}

}
