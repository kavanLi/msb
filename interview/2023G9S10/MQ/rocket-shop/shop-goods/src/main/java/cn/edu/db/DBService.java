package cn.edu.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * 类说明：手写的方式：模拟数据库服务
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
    // 模拟数据库服务,如果200ms内无法获取到，将会返回null
    public String useDb(String sql)throws Exception {
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
            throw new Exception("无法获取到数据库连接");
        }
        return sql+"被正确处理";
    }
}
