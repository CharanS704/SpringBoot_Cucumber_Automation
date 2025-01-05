package com.ecommerce.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.Data;

@Repository
@Data
public class StoreRepository {

	private String SELECT_QUERY_FOR_COUNT;
	private static final String SELECT_QUERY_FOR_RECORDS = "select itemId,itemName,itemQuantity,isItemBooked from ItemDetails where isItemBooked =?";
	private static final String INSERT_RECORD = "insert into ItemDetails(itemName,itemQuantity,isItemBooked) values(?,?,?)";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Integer totalRecordsCountByNameAndStatus(String name, Boolean status) {
		return jdbcTemplate.queryForObject(SELECT_QUERY_FOR_COUNT, Integer.class, name, status);
	}

	public List<Map<String, Object>> getRecordsByStatus(Boolean status) {
		return jdbcTemplate.queryForList(SELECT_QUERY_FOR_RECORDS, status);
	}

}
