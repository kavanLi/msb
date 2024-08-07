package com.bobo.mp.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.mashibing.internal.common.config.dataSource.annotation.ReportDB;
import com.bobo.mp.service.AsyncService;
import com.bobo.mp.service.DynaAmsOrganizationService;
import com.mashibing.internal.common.domain.pojo.DynaAmsOrganization;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-13 09:43
 * To change this template use File | Settings | File and Code Templates.
 */
@RestController
@RequestMapping("/testMultiDataSourceController")
@ResponseBody
public class TestMultiDataSourceController {

    //@Autowired
    //private DynaAmsUserOptLogService dynaAmsUserOptLogService;

    @Autowired
    private DynaAmsOrganizationService dynaAmsOrganizationService;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    ApplicationContext ctx;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 结束线程的方法：
     *
     * 1. 自然结束（能自然结束就尽量自然结束）
     * 2. stop() suspend() resume()
     * 3. volatile标志
     *    1. 不适合某些场景（比如还没有同步的时候，线程做了阻塞操作，没有办法循环回去）
     *    2. 打断时间也不是特别精确，比如一个阻塞容器，容量为5的时候结束生产者，
     *       但是，由于volatile同步线程标志位的时间控制不是很精确，有可能生产者还继续生产一段儿时间
     * 4. interrupt() and isInterrupted（比较优雅）
     */
    private static volatile Boolean running = true;

    /**
     * 查看当前的数据源信息
     * Database URL: jdbc:oracle:thin:@192.168.30.52:1521:orcldb
     * Username: YUNSTCRS
     * Database Product: Oracle
     * Database Version: Oracle Database 19c Enterprise Edition Release 19.0.0.0.0 - Production
     */
    @Autowired
    private DataSource dataSource;

    public void printDatabaseInfo(DataSource dataSource) {
        try (Connection connection = DataSourceUtils.getConnection(dataSource)) {
            DatabaseMetaData metaData = connection.getMetaData();

            String databaseUrl = metaData.getURL();
            String databaseUserName = metaData.getUserName();
            String databaseProductName = metaData.getDatabaseProductName();
            String databaseProductVersion = metaData.getDatabaseProductVersion();

            System.out.println("Database URL: " + databaseUrl);
            System.out.println("Username: " + databaseUserName);
            System.out.println("Database Product: " + databaseProductName);
            System.out.println("Database Version: " + databaseProductVersion);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 查看当前的数据源信息
     */

    @SneakyThrows
    @GetMapping(value = "/testReportDB")
    @ReportDB
    public Map <String, String> testSecondary() {
        DataSource ds = ctx.getBean(DataSource.class);
        //printDatabaseInfo(ds);
        taskExecutor.execute(() -> asyncService.test());
        // 下面的方法可以获取数据库数据，而单起的线程中需要重新Set ReportDB
        //DynaAmsUserOptLog dynaAmsUserOptLog1 = dynaAmsUserOptLogService.getById(360930L);
        //new Thread(() -> {
        //    /**
        //     * 注意 注意 注意！！！！ ThreadLocal是线程独有的，如果通过注解实现异步的执行执行数据操作，切面的SetKey会失效。导致使用Primary默认库
        //     */
        //    //DynaAmsOrganization dynaAmsOrganization = dynaAmsOrganizationService.getById(1764L);
        //    DynaAmsUserOptLog dynaAmsUserOptLog = dynaAmsUserOptLogService.getById(360930L);
        //    while (running) {
        //        //System.out.println(dynaAmsOrganization.getName());
        //        System.out.println(dynaAmsUserOptLog.getOptName());
        //        try {
        //            Thread.sleep(1000);
        //        } catch (InterruptedException e) {
        //            throw new RuntimeException(e);
        //        }
        //    }
        //}).start();
        Map <String, String> errors = new HashMap <>();
        errors.put("123", "1231");
        return errors;
    }


    /**
     * 数据源是线程隔离的，上面的接口切换数据源并再新线程中循环不停止。
     * 下面这个接口会使用primary主数据源。
     * @return
     */
    @GetMapping("/testConfigDB")
    public JSONArray testPrimary() {
        DynaAmsOrganization dynaAmsOrganization = dynaAmsOrganizationService.getById(1764L);
        JSONObject result = new JSONObject();
        JSONArray resultArray = new JSONArray();
        result.put("code", "fail");
        result.put("msg", "exception.getMessage()");
        running = false;
        return resultArray.put(result);
    }
}
