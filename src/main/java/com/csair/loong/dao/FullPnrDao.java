package com.csair.loong.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.csair.loong.domain.FullPassengerInfo;

public class FullPnrDao extends AbstractHbaseDao {
	private static final Logger log = LoggerFactory.getLogger(FullPnrDao.class);
	private JdbcTemplate jdbcTemplate;
	private String insert_sql = "insert into full_psg_info values ";

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public boolean hasFullPnrPsg(FullPassengerInfo fullPsgInfo) {

		String sql = insert_sql + " (" + fullPsgInfo.toString() + ")";

		return insert(sql);
	}

	public boolean insertStr(List<String> fullPsgStrs) {

		List<String> sqls = new ArrayList<String>();
		for (String str : fullPsgStrs) {
			if (StringUtils.isNotBlank(str)) {
				String sql = insert_sql + " (" + str + ")";
				sqls.add(sql);
			}
		}
		int[] size = batchInsert(sqls);
		log.info("插入记录数：" + size.toString());
		return true;

	}

	public boolean insert(List<FullPassengerInfo> fullPsgInfos) {

		List<String> sqls = new ArrayList<String>();
		for (FullPassengerInfo fullPsgInfo : fullPsgInfos) {
			String sql = insert_sql + " (" + fullPsgInfo.toString() + ")";
			sqls.add(sql);
		}

		int[] size = batchInsert(sqls);

		log.info("插入记录数：" + size.toString());

		return true;
	}

	public boolean insert(FullPassengerInfo fullPsgInfo) {

		String sql = insert_sql + " (" + fullPsgInfo.toString() + ")";

		return insert(sql);
	}

}
