package com.csair.loong.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class FullPnrDao {

    private JdbcTemplate jdbcTemplate;
    
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public void batchSave(){
        
        
        
    }
    
    
}
