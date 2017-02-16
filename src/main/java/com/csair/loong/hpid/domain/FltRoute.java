package com.csair.loong.hpid.domain;

public class FltRoute {

	private String tripId;
	private String depDate;
	private String dep;
	private String arv;
	private String origCity;
	private String destCity;
	private String tripDepTime;
	private String tripArvTime;
	private String tripElapTime;
	private String tripSegCount;
	private long distance;
	private String tripStops;
	private int nbrPax;
	private int tripFreq = 1;
	private String route;
	private String deptq;
	private String arvtq;

	public void hpidStringInit(String[] lines) {
		this.tripId = lines[32];
		this.depDate = lines[0];
		this.dep = lines[13];
		this.arv = lines[14];
		this.origCity = lines[19];
		this.destCity = lines[20];
		this.tripDepTime = lines[7];
		this.tripArvTime = lines[8];
		this.tripElapTime = lines[12];
		this.tripSegCount = lines[37];
		this.distance = Long.parseLong(lines[34]);
		this.tripStops = lines[38];
		this.nbrPax = Integer.parseInt(lines[35]);
	}

	public void init(String[] lines) {
		tripId = lines[0];
		depDate = lines[1];
		dep = lines[2];
		arv = lines[3];
		origCity = lines[4];
		destCity = lines[5];
		tripDepTime = lines[6];
		tripArvTime = lines[7];
		tripElapTime = lines[8];
		tripSegCount = lines[9];
		distance = Long.parseLong(lines[10]);
		tripStops = lines[11];
		nbrPax = Integer.parseInt(lines[12]);
		tripFreq = Integer.parseInt(lines[13]);
		route = lines[14];
		deptq = lines[15];
		arvtq = lines[16];
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getDepDate() {
		return depDate;
	}

	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getArv() {
		return arv;
	}

	public void setArv(String arv) {
		this.arv = arv;
	}

	public String getOrigCity() {
		return origCity;
	}

	public void setOrigCity(String origCity) {
		this.origCity = origCity;
	}

	public String getDestCity() {
		return destCity;
	}

	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}

	public String getTripDepTime() {
		return tripDepTime;
	}

	public void setTripDepTime(String tripDepTime) {
		this.tripDepTime = tripDepTime;
	}

	public String getTripArvTime() {
		return tripArvTime;
	}

	public void setTripArvTime(String tripArvTime) {
		this.tripArvTime = tripArvTime;
	}

	public String getTripElapTime() {
		return tripElapTime;
	}

	public void setTripElapTime(String tripElapTime) {
		this.tripElapTime = tripElapTime;
	}

	public String getTripSegCount() {
		return tripSegCount;
	}

	public void setTripSegCount(String tripSegCount) {
		this.tripSegCount = tripSegCount;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	public void addDistance(long distance) {
		this.distance += distance;
	}

	public String getTripStops() {
		return tripStops;
	}

	public void setTripStops(String tripStops) {
		this.tripStops = tripStops;
	}

	public int getNbrPax() {
		return nbrPax;
	}

	public void setNbrPax(int nbrPax) {
		this.nbrPax = nbrPax;
	}

	public int getTripFreq() {
		return tripFreq;
	}

	public void setTripFreq(int tripFreq) {
		this.tripFreq = tripFreq;
	}

	public void addTripFreq() {
		this.tripFreq = this.tripFreq + 1;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public void addRoute(String arpCd) {
		this.route += "-" + arpCd;
	}

	public String getDeptq() {
		return deptq;
	}

	public void setDeptq(String deptq) {
		this.deptq = deptq;
	}

	public String getArvtq() {
		return arvtq;
	}

	public void setArvtq(String arvtq) {
		this.arvtq = arvtq;
	}

	public void copy(FltRoute fltRoute) {

		setTripId(fltRoute.getTripId());
		setDepDate(fltRoute.getDepDate());
		setDep(fltRoute.getDep());
		setArv(fltRoute.getArv());
		setOrigCity(fltRoute.getOrigCity());
		setDestCity(fltRoute.getDestCity());
		setTripDepTime(fltRoute.getTripDepTime());
		setTripArvTime(fltRoute.getTripArvTime());
		setTripElapTime(fltRoute.getTripElapTime());
		setTripSegCount(fltRoute.getTripSegCount());
		setDistance(fltRoute.getDistance());
		setTripStops(fltRoute.getTripStops());
		setNbrPax(fltRoute.getNbrPax());
		setRoute(fltRoute.getDep() + "-" + fltRoute.getArv());
		setDeptq(fltRoute.getDeptq());
		setArvtq(fltRoute.getArvtq());

	}

	public String toString() {
		// tripId,depDate,dep,arv,origCity,destCity,tripDepTime,tripArvTime,tripElapTime,tripSegCount,privatelongdistance,tripStops,privateintnbrPax,tripFreq,route,deptq,arvtq

		return this.getTripId() + "," + this.getDepDate() + "," + this.getDep()
				+ "," + this.getArv() + "," + this.getOrigCity() + ","
				+ this.getDestCity() + "," + this.getTripDepTime() + ","
				+ this.getTripArvTime() + "," + this.getTripSegCount() + ","
				+ this.getDistance() + "," + this.getTripElapTime() + ","
				+ this.getTripStops() + "," + this.getNbrPax() + ","
				+ this.getTripFreq() + "," + this.getRoute() + ","
				+ this.getDeptq() + "," + this.getArvtq();

	}

}
