package com.csair.loong.hpid.domain;

public class FltSegment {
	
	private String depDate;
	private String arvDate;
	private String depDateU;
	private String arvDateU;
	private String tripDepDate;
	private String tripArvDate;
	private String bookDate;
	private String depTime;
	private String arvTime;
	private String depTimeU;
	private String arvTimeU;
	private String segconTime;
	private String tripElapTime;
	private String dep;
	private String arv;
	private String depCity;
	private String arvCity;
	private String depArea;
	private String arvArea;
	private String origCity;
	private String destCity;
	private String depCountry;
	private String arvCountry;
	private String opaln;
	private String mktaln;
	private String domainmktaln;
	private String domainopaln;
	private String opflt;
	private String mktflt;
	private String cabinClass;
	private String clazz;
	private String tripClass;
	private String tripId;
	private String nbrStops;
	private String distance;
	private String nbrpax;
	private String actype;
	private String tripSegCount;
	private String tripStops;
	private String bookDateAhead;
	private String tripAllMktaln;
	private String pnr;
	private String agent;
	private String depDateT;
	private String bookDateT;
	private String arvDateT;
	private String arvDateUT;
	private String depDateUT;
	private String tripDepDateT;
	private String tripArvDateT;
	private String segpos;
	private String onflag;
	private String onlineflag;

	public FltSegment(String[] lines) {

		depDate = lines[0];
		arvDate = lines[1];
		depDateU = lines[2];
		arvDateU = lines[3];
		tripDepDate = lines[4];
		tripArvDate = lines[5];
		bookDate = lines[6];
		depTime = lines[7];
		arvTime = lines[8];
		depTimeU = lines[9];
		arvTimeU = lines[10];
		segconTime = lines[11];
		tripElapTime = lines[12];
		dep = lines[13];
		arv = lines[14];
		depCity = lines[15];
		arvCity = lines[16];
		depArea = lines[17];
		arvArea = lines[18];
		origCity = lines[19];
		destCity = lines[20];
		depCountry = lines[21];
		arvCountry = lines[22];
		opaln = lines[23];
		mktaln = lines[24];
		domainmktaln = lines[25];
		domainopaln = lines[25];
		opflt = lines[27];
		mktflt = lines[28];
		cabinClass = lines[29];
		clazz = lines[30];
		tripClass = lines[31];
		tripId = lines[32];
		nbrStops = lines[33];
		distance = lines[34];
		nbrpax = lines[35];
		actype = lines[36];
		tripSegCount = lines[37];
		tripStops = lines[38];
		bookDateAhead = lines[39];
		tripAllMktaln = lines[40];
		pnr = lines[41];
		agent = lines[42];
		depDateT = lines[43];
		bookDateT = lines[44];
		arvDateT = lines[45];
		arvDateUT = lines[46];
		depDateUT = lines[47];
		tripDepDateT = lines[48];
		tripArvDateT = lines[49];
		segpos = lines[50];
		onflag = lines[51];
		onlineflag = lines[52];

	}

	public String getDepDate() {
		return depDate;
	}

	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

	public String getArvDate() {
		return arvDate;
	}

	public void setArvDate(String arvDate) {
		this.arvDate = arvDate;
	}

	public String getDepDateU() {
		return depDateU;
	}

	public void setDepDateU(String depDateU) {
		this.depDateU = depDateU;
	}

	public String getArvDateU() {
		return arvDateU;
	}

	public void setArvDateU(String arvDateU) {
		this.arvDateU = arvDateU;
	}

	public String getTripDepDate() {
		return tripDepDate;
	}

	public void setTripDepDate(String tripDepDate) {
		this.tripDepDate = tripDepDate;
	}

	public String getTripArvDate() {
		return tripArvDate;
	}

	public void setTripArvDate(String tripArvDate) {
		this.tripArvDate = tripArvDate;
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public String getDepTime() {
		return depTime;
	}

	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}

	public String getArvTime() {
		return arvTime;
	}

	public void setArvTime(String arvTime) {
		this.arvTime = arvTime;
	}

	public String getDepTimeU() {
		return depTimeU;
	}

	public void setDepTimeU(String depTimeU) {
		this.depTimeU = depTimeU;
	}

	public String getArvTimeU() {
		return arvTimeU;
	}

	public void setArvTimeU(String arvTimeU) {
		this.arvTimeU = arvTimeU;
	}

	public String getSegconTime() {
		return segconTime;
	}

	public void setSegconTime(String segconTime) {
		this.segconTime = segconTime;
	}

	public String getTripElapTime() {
		return tripElapTime;
	}

	public void setTripElapTime(String tripElapTime) {
		this.tripElapTime = tripElapTime;
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

	public String getDepCity() {
		return depCity;
	}

	public void setDepCity(String depCity) {
		this.depCity = depCity;
	}

	public String getArvCity() {
		return arvCity;
	}

	public void setArvCity(String arvCity) {
		this.arvCity = arvCity;
	}

	public String getDepArea() {
		return depArea;
	}

	public void setDepArea(String depArea) {
		this.depArea = depArea;
	}

	public String getArvArea() {
		return arvArea;
	}

	public void setArvArea(String arvArea) {
		this.arvArea = arvArea;
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

	public String getDepCountry() {
		return depCountry;
	}

	public void setDepCountry(String depCountry) {
		this.depCountry = depCountry;
	}

	public String getArvCountry() {
		return arvCountry;
	}

	public void setArvCountry(String arvCountry) {
		this.arvCountry = arvCountry;
	}

	public String getOpaln() {
		return opaln;
	}

	public void setOpaln(String opaln) {
		this.opaln = opaln;
	}

	public String getMktaln() {
		return mktaln;
	}

	public void setMktaln(String mktaln) {
		this.mktaln = mktaln;
	}

	public String getDomainmktaln() {
		return domainmktaln;
	}

	public void setDomainmktaln(String domainmktaln) {
		this.domainmktaln = domainmktaln;
	}

	public String getDomainopaln() {
		return domainopaln;
	}

	public void setDomainopaln(String domainopaln) {
		this.domainopaln = domainopaln;
	}

	public String getOpflt() {
		return opflt;
	}

	public void setOpflt(String opflt) {
		this.opflt = opflt;
	}

	public String getMktflt() {
		return mktflt;
	}

	public void setMktflt(String mktflt) {
		this.mktflt = mktflt;
	}

	public String getCabinClass() {
		return cabinClass;
	}

	public void setCabinClass(String cabinClass) {
		this.cabinClass = cabinClass;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getTripClass() {
		return tripClass;
	}

	public void setTripClass(String tripClass) {
		this.tripClass = tripClass;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getNbrStops() {
		return nbrStops;
	}

	public void setNbrStops(String nbrStops) {
		this.nbrStops = nbrStops;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getNbrpax() {
		return nbrpax;
	}

	public void setNbrpax(String nbrpax) {
		this.nbrpax = nbrpax;
	}

	public String getActype() {
		return actype;
	}

	public void setActype(String actype) {
		this.actype = actype;
	}

	public String getTripSegCount() {
		return tripSegCount;
	}

	public void setTripSegCount(String tripSegCount) {
		this.tripSegCount = tripSegCount;
	}

	public String getTripStops() {
		return tripStops;
	}

	public void setTripStops(String tripStops) {
		this.tripStops = tripStops;
	}

	public String getBookDateAhead() {
		return bookDateAhead;
	}

	public void setBookDateAhead(String bookDateAhead) {
		this.bookDateAhead = bookDateAhead;
	}

	public String getTripAllMktaln() {
		return tripAllMktaln;
	}

	public void setTripAllMktaln(String tripAllMktaln) {
		this.tripAllMktaln = tripAllMktaln;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getDepDateT() {
		return depDateT;
	}

	public void setDepDateT(String depDateT) {
		this.depDateT = depDateT;
	}

	public String getBookDateT() {
		return bookDateT;
	}

	public void setBookDateT(String bookDateT) {
		this.bookDateT = bookDateT;
	}

	public String getArvDateT() {
		return arvDateT;
	}

	public void setArvDateT(String arvDateT) {
		this.arvDateT = arvDateT;
	}

	public String getArvDateUT() {
		return arvDateUT;
	}

	public void setArvDateUT(String arvDateUT) {
		this.arvDateUT = arvDateUT;
	}

	public String getDepDateUT() {
		return depDateUT;
	}

	public void setDepDateUT(String depDateUT) {
		this.depDateUT = depDateUT;
	}

	public String getTripDepDateT() {
		return tripDepDateT;
	}

	public void setTripDepDateT(String tripDepDateT) {
		this.tripDepDateT = tripDepDateT;
	}

	public String getTripArvDateT() {
		return tripArvDateT;
	}

	public void setTripArvDateT(String tripArvDateT) {
		this.tripArvDateT = tripArvDateT;
	}

	public String getSegpos() {
		return segpos;
	}

	public void setSegpos(String segpos) {
		this.segpos = segpos;
	}

	public String getOnflag() {
		return onflag;
	}

	public void setOnflag(String onflag) {
		this.onflag = onflag;
	}

	public String getOnlineflag() {
		return onlineflag;
	}

	public void setOnlineflag(String onlineflag) {
		this.onlineflag = onlineflag;
	}

}
