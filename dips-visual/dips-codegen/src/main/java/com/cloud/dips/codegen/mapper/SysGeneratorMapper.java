package com.cloud.dips.codegen.mapper;

import com.cloud.dips.common.core.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author BigPan
 */
public interface SysGeneratorMapper {

	/**
	 * 分页查询表格
	 *
	 * @param params
	 * @param tableName
	 * @return
	 */
	List<Map<String, Object>> queryList(Query params, @Param("tableName") Object tableName);

	/**
	 * 查询表信息
	 *
	 * @param tableName 表名称
	 * @return
	 */
	Map<String, String> queryTable(String tableName);

	/**
	 * 查询表列信息
	 *
	 * @param tableName 表名称
	 * @return
	 */
	List<Map<String, String>> queryColumns(String tableName);
}
