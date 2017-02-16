package com.csair.loong.dao;



public class Param {
	
	private int limit;
	
	private String columns;
	
	private String startDate;
	
	private String endDate;
	
	private String startTime;
	
	private String endTime;
	
	private String planeNo;
	
	private String flightNo;
	
	private String flightPhase;
	
	private boolean fuelByFlightPhase = true;
	
	
	
	
	
	

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		
		this.limit = limit;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
			this.columns = columns;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
			this.startDate = startDate;
		
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
			this.endDate = endDate;
		
	}

	
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
			this.startTime = startTime;
		
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
			this.endTime = endTime;
		
	}

	public String getPlaneNo() {
		return planeNo;
	}

	public void setPlaneNo(String planeNo) {
			this.planeNo = planeNo;
		
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
			this.flightNo = flightNo;
		
	}
	

	public String getFlightPhase() {
		return flightPhase;
	}

	public void setFlightPhase(String flightPhase) {
			this.flightPhase = flightPhase;
		
	}

	public boolean isFuelByFlightPhase() {
		return fuelByFlightPhase;
	}

	public void setFuelByFlightPhase(boolean fuelByFlightPhase) {
		this.fuelByFlightPhase = fuelByFlightPhase;
	}

	
	
	
	
	

}
