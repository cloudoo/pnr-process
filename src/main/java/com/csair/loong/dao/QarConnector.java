package com.csair.loong.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;

public class QarConnector {

	private String jdbcString = "jdbc:phoenix:10.95.68.71:2181:/hbase";

	public List<Map<String, Object>> fetch(String key, Param param) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		String selectPart = "*";
		String columns = param.getColumns();
		if (columns != null && !"".equals(columns.trim())) {
			selectPart = columns;
		}
		
		String wherePart = "";

		String startTime = param.getStartTime();
		
		if (startTime != null && !"".equals(startTime.trim())) {
			wherePart += " and to_timestamp(date_r||time,'dd/MM/yyHH:mm:ss','GMT+8') >= to_timestamp('" + startTime + "','','GMT+8')";
		}
		
		String endTime = param.getEndTime();
		if( endTime != null && !"".equals(endTime.trim())){
			wherePart += " and to_timestamp(date_r||time,'dd/MM/yyHH:mm:ss','GMT+8') <= to_timestamp('" + endTime + "','','GMT+8')";
		}

		String sql = "select " + selectPart
				+ " from qardata where date = ? and plane_no = ? and id = ? "+wherePart;
		if (param.getLimit() != 0) {
			sql = sql + " limit " + param.getLimit();
		}
		
		
		System.out.println(sql);
		result = executeQuery(sql,key.split("_"));

		return result;
	}

	public List<Map<String, Object>> search(Param param) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		String wherePart = "";

		String startDate = param.getStartDate();
		String endDate = param.getEndDate();
		if (startDate != null && !"".equals(startDate.trim())
				&& endDate != null && !"".equals(endDate.trim())) {
			wherePart += " and date between '" + startDate + "' and '"
					+ endDate + "'";
		}

		String planeNo = param.getPlaneNo();
		if (planeNo != null && !"".equals(planeNo.trim())) {

			wherePart += " and plane_no in ('"
					+ Joiner.on("','").join(planeNo.split(",")) + "') ";
		}

		String flightNo = param.getFlightNo();
		if (flightNo != null && !"".equals(flightNo.trim())) {
			wherePart += " and trim(flight_no2) in ('"
					+ Joiner.on("','").join(flightNo.split(",")) + "') ";
		}

		String sql = "select date,plane_no,id from qardata where 1=1 "
				+ wherePart + "group by date,plane_no,id ";
		if (param.getLimit() != 0) {
			sql = sql + " limit " + param.getLimit();
		}

		result = executeQuery(sql);

		return result;
	}

	public List<Map<String, Object>> getFuel(Param param) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		String fuelDetail = "";
		
		if(param.isFuelByFlightPhase()) {
			fuelDetail = ",flight_phase";
		}
		
		String wherePart = "";

		String startDate = param.getStartDate();
		String endDate = param.getEndDate();
		if (startDate != null && !"".equals(startDate.trim())
				&& endDate != null && !"".equals(endDate.trim())) {
			wherePart += " and date between '" + startDate + "' and '"
					+ endDate + "'";
		}

		String planeNo = param.getPlaneNo();
		if (planeNo != null && !"".equals(planeNo.trim())) {

			wherePart += " and plane_no in ('"
					+ Joiner.on("','").join(planeNo.split(",")) + "') ";
		}

		String flightNo = param.getFlightNo();
		if (flightNo != null && !"".equals(flightNo.trim())) {
			wherePart += " and trim(flight_no2) in ('"
					+ Joiner.on("','").join(flightNo.split(",")) + "') ";
		}
		
		String flightPhase = param.getFlightPhase();
		if (flightPhase != null && !"".equals(flightPhase.trim())) {
			wherePart += " and trim(flight_phase) in ('"
					+ Joiner.on("','").join(flightPhase.split(",")) + "') ";
		}

		String sql = "select date,plane_no,id"+fuelDetail+",sum(to_number(FF1)+to_number(FF2)) as fuel from qardata where 1=1 "
				+ wherePart + "group by date,plane_no,id "+fuelDetail;
		if (param.getLimit() != 0) {
			sql = sql + " limit " + param.getLimit();
		}
//		System.out.println(sql);
		result = executeQuery(sql);

		return result;
	}

	private void test() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			conn = DriverManager.getConnection(
					"jdbc:phoenix:10.95.68.71:2181:/hbase", "", "");
			String sql = "select date,plane_no,id from qardata where date between   '20150402' and '20150407' group by date,plane_no,id";
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			while (rs.next()) {

				for (int i = 0; i < meta.getColumnCount(); i++) {
					System.out.print(meta.getColumnLabel(i + 1) + ": "
							+ rs.getObject(i + 1) + "  |");
				}
				System.out.println("");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conn = null;
			}
		}
	}

	private List<Map<String, Object>> executeQuery(String sql) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(jdbcString, "", "");

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < meta.getColumnCount(); i++) {
					map.put(meta.getColumnLabel(i + 1), rs.getObject(i + 1));
				}
				// System.out.println("row");
				result.add(map);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conn = null;
			}
		}

		return result;
	}
	
	private List<Map<String, Object>> executeQuery(String sql,String[] params) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(jdbcString, "", "");

			ps = conn.prepareStatement(sql);
			for(int i = 0;i<params.length;i++){
				ps.setObject(i+1, params[i]);
			}

			rs = ps.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < meta.getColumnCount(); i++) {
					map.put(meta.getColumnLabel(i + 1), rs.getObject(i + 1));
				}
				// System.out.println("row");
				result.add(map);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conn = null;
			}
		}

		return result;
	}

	public static void main(String args[]) {
		String version = System.getProperty("java.version");
		System.out.println(version);
		String planeNo = "a";
		String result = Joiner.on("','").join(planeNo.split(","));
		System.out.println(result);
		 QarConnector connector = new QarConnector();
		 connector.test();
	}

}
