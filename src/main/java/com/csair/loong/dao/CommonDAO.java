package com.csair.loong.dao;

import java.sql.ResultSet;
import java.util.List;

public interface CommonDAO<T> {
	public boolean insert(String sql) ;
	public List<T> execute(String sql);
	public abstract T toObject(ResultSet rs);
	public abstract String getInsertSql();
	public boolean insertStr(List<String> qarEngDomains);
	public int[] batchInsert(List<String> sqls);
	public void close();
}
