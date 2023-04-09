package com.test.batis.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TMapper {

	@Select("select * from t")
	List<Map<String, Object>> queryFroList();

	//执行数据库查询
	@Select("select * from t where id =${id}")
	Map<String, Object> queryFroMap(Integer id);
}
