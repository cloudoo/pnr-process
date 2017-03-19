package com.csair.loong.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.csair.loong.domain.FullPassengerInfo;

public class FullPnrDao extends AbstractHBaseJdbcDAO<FullPassengerInfo> {
	private static final Logger log = LoggerFactory.getLogger(FullPnrDao.class);
	private JdbcTemplate jdbcTemplate;
	private String insert_sql = "UPSERT INTO fullpnr.full_pnr_passenger_info("
			+ "pnr_ref,pnr_crt_dt,pnr_grp_ind,pax_id,pax_ful_nm ,pax_cn_nm,vip_ind,pax_age_ctg_cd,seg_id,carr_cd,flt_nbr,flt_nbr_sfx,dpt_airpt_cd,arrv_airpt_cd,dpt_dt_lcl,dpt_dow,dpt_tm_lcl,arrv_dt_lcl,arrv_tm_lcl,air_seg_flt_typ,sub_cls_cd,opr_stat_cd,stat_id,indvl_id_typ_cd,indvl_nbr,cntry_cd,indvl_bth_day,gnd_cd,vico_card,tkt_type,ctc_nbr"
			+ ") values ";

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public boolean hasFullPnrPsg(FullPassengerInfo fullPsgInfo) {

		String sql = insert_sql + " (" + fullPsgInfo.toSql() + ")";

		return insert(sql);
	}

	public boolean insert(List<FullPassengerInfo> fullPsgInfos) {

		List<String> sqls = new ArrayList<String>();
		for (FullPassengerInfo fullPsgInfo : fullPsgInfos) {
			String sql = insert_sql + " (" + fullPsgInfo.toSql() + ")";
			sqls.add(sql);
		}

		int[] size = batchInsert(sqls);

		log.info("插入记录数：" + size.toString());

		return true;
	}

	public boolean insert(FullPassengerInfo fullPsgInfo) {

		String sql = insert_sql + " (" + fullPsgInfo.toSql() + ")";

		return insert(sql);
	}

	@Override
	public FullPassengerInfo toObject(ResultSet rs) {
		FullPassengerInfo fpi = new FullPassengerInfo();

		return null;
	}

	@Override
	public String getInsertSql() {
		return this.insert_sql;
	}

}
