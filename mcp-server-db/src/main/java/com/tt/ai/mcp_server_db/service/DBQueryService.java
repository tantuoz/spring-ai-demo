package com.tt.ai.mcp_server_db.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DBQueryService {

    private final JdbcTemplate jdbcTemplate;

    public DBQueryService(JdbcTemplate jdbcTemplate) {
        // log.info("连接数据源信息：{}", jdbcTemplate.getDataSource());
        this.jdbcTemplate = jdbcTemplate;
        // log.info("test query, res={}", jdbcTemplate.queryForList(
        // "SELECT TABLE_NAME , TABLE_COMMENT FROM information_schema.tables WHERE TABLE_SCHEMA = DATABASE()"));
    }

    @Tool(name = "queryAllTables", description = "查询数据库中所有的表")
    public List<Map<String, Object>> queryAllTables() {
        String sql = "SELECT TABLE_NAME , TABLE_COMMENT FROM information_schema.tables WHERE TABLE_SCHEMA = DATABASE()";
        return jdbcTemplate.queryForList(sql);
    }

    @Tool(name = "queryTable", description = "查询数据库中的表的数据")
    public List<Map<String, Object>> queryTable(@ToolParam(description = "表名") String tableName) {
        String sql = "SELECT * FROM " + tableName + "limit 100";
        return jdbcTemplate.queryForList(sql);
    }

    @Tool(name = "queryTableById", description = "查询主键ID查询表中数据")
    public Map<String, Object> queryTableById(@ToolParam(description = "表名") String tableName,
        @ToolParam(description = "主键ID") Long id) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = " + id;
        return jdbcTemplate.queryForMap(sql);
    }

}
