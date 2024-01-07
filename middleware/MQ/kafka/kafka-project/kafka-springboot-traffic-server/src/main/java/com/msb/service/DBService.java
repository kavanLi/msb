package com.msb.service;

import com.msb.db.DBPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * 类说明：模拟数据库服务
 */
@Component
public class DBService {
    @Autowired
    private DBPool dbPool;

    private static class SQLResult{
        private final String sql;
        private String result;

        public SQLResult(String sql) {
            this.sql = sql;
        }
        public String getSql() {
            return sql;
        }
        public String getResult() {
            return result;
        }
        public void setResult(String result) {
            this.result = result;
        }
    }

    public String useDb(String sql) {
        try {
            // 从线程池中获取连接，如果1000ms内无法获取到，将会返回null
            Connection connection = dbPool.fetchConn(1000);
            if (connection != null) {
                try {
                    connection.createStatement();
                    connection.commit();
                } finally {
                    dbPool.releaseConn(connection);
                }
            } else {
                return sql+"无法执行，超过数据池连接数限制！";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sql+"被正确处理";
    }
}
