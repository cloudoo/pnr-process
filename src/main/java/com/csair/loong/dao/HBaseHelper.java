package com.csair.loong.dao;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HBaseHelper {
	
	private static final Logger log = LoggerFactory
			.getLogger(HBaseHelper.class);
	
	protected Configuration conf ;
	
	public HBaseHelper(){
		conf = HBaseConfiguration.create();
	}
	public Configuration getConf(){
		return conf;
	}
	
	public void createTable(String tableName, String[] columnFamily)
			throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.tableExists(tableName)) {
			log.error("table:"+tableName+" exits");
		} else {
			HTableDescriptor tableDesc = new HTableDescriptor(
					TableName.valueOf(tableName));
			for (String conumnF : columnFamily) {
				tableDesc.addFamily(new HColumnDescriptor(conumnF));
			}
			
			admin.createTable(tableDesc);

			log.info("create table success!");
			
		}
		admin.close();
	}
	
	public void addTableColumnFamily(String tableName, String columnFamily)
			throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (!admin.tableExists(tableName)) {
			log.error("Table NO exists!");
			createTable(tableName, new String[] { columnFamily });
		} else {
			HTableDescriptor tableDesc = new HTableDescriptor(
					TableName.valueOf(tableName));
			tableDesc.addFamily(new HColumnDescriptor(columnFamily));

			log.info("add columnFamily" + columnFamily + " success!");
		}
		admin.close();
	}
	
	public boolean deleteTable(String tablename) throws IOException {
		
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.tableExists(tablename)) {
			try {
				admin.disableTable(tablename);
				admin.deleteTable(tablename);
			} catch (Exception e) {
				log.error("后台错误："+e.getMessage());
				admin.close();
				return false;
			}
		}
		admin.close();
		return true;
	}
	
	public void putCell(HTable table, String rowKey, String columnFamily,
			String identifier, String data) throws Exception {
		Put p1 = new Put(Bytes.toBytes(rowKey));
		p1.add(Bytes.toBytes(columnFamily), Bytes.toBytes(identifier),
				Bytes.toBytes(data));
		table.put(p1);
		
		log.debug("put '" + rowKey + "', '" + columnFamily + ":"
				+ identifier + "', '" + data + "'");
	}
	
	public void putCells(HTable table, String rowKey, String columnFamily,String[] identifier,Object[] data) throws RetriesExhaustedWithDetailsException, InterruptedIOException{
		
		List<Put> puts = new ArrayList<Put>();
		Integer a;
		for(int i=0;i<identifier.length;i++){
			Put p1 = new Put(Bytes.toBytes(rowKey));
			p1.add(Bytes.toBytes(columnFamily), Bytes.toBytes(identifier[i]),
					Bytes.toBytes(data[i].toString()));
		}
		
		table.put(puts);
		
		log.debug("put '" + rowKey + "', '" + columnFamily + ":"
				+ identifier + "', '" + data + "'");
	}

	/**
	 * get a row identified by rowkey
	 * 
	 * @param HTable
	 *            , create by : HTable table = new HTable(conf, "tablename")
	 * @param rowKey
	 * @throws Exception
	 */
	public Result getRow(HTable table, String rowKey) throws Exception {
		Get get = new Get(Bytes.toBytes(rowKey));
		Result result = table.get(get);
		
		log.debug("Get: " + result);
		
		return result;
	}

	/**
	 * delete a row identified by rowkey
	 * 
	 * @param HTable
	 *            , create by : HTable table = new HTable(conf, "tablename")
	 * @param rowKey
	 * @throws Exception
	 */
	public void deleteRow(HTable table, String rowKey) throws Exception {
		Delete delete = new Delete(Bytes.toBytes(rowKey));
		table.delete(delete);
		
		log.debug("Delete row: " + rowKey);
	}

	/**
	 * return all row from a table
	 * 
	 * @param HTable
	 *            , create by : HTable table = new HTable(conf, "tablename")
	 * @throws Exception
	 */
	public ResultScanner scanAll(HTable table) throws Exception {
		Scan s = new Scan();
		ResultScanner rs = table.getScanner(s);

		return rs;
	}

	/**
	 * return a range of rows specified by startrow and endrow
	 * 
	 * @param HTable
	 *            , create by : HTable table = new HTable(conf, "tablename")
	 * @param startrow
	 * @param endrow
	 * @throws Exception
	 */
	public ResultScanner scanRange(HTable table, String startrow, String endrow)
			throws Exception {
		Scan s = new Scan(Bytes.toBytes(startrow), Bytes.toBytes(endrow));
		ResultScanner rs = table.getScanner(s);
		return rs;
	}

	/**
	 * return a range of rows filtered by specified condition
	 * 
	 * @param HTable
	 *            , create by : HTable table = new HTable(conf, "tablename")
	 * @param startrow
	 * @param filter
	 * @throws Exception
	 */
	public ResultScanner scanFilter(HTable table, String startrow, Filter filter)
			throws Exception {
		Scan s = new Scan(Bytes.toBytes(startrow), filter);
		ResultScanner rs = table.getScanner(s);
		
		return rs;
	}

	
}
