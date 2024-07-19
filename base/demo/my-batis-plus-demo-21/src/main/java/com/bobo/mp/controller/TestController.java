package com.bobo.mp.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.bobo.mp.dataSource.annotation.ReportDB;
import com.bobo.mp.service.DynaAmsOrganizationService;
import com.mashibing.internal.common.domain.pojo.DynaAmsOrganization;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-13 09:43
 * To change this template use File | Settings | File and Code Templates.
 */
@RestController
@RequestMapping("/test")
@ResponseBody
public class TestController {

    //@Autowired
    //private DynaAmsUserOptLogService dynaAmsUserOptLogService;

    @Autowired
    private DynaAmsOrganizationService dynaAmsOrganizationService;

    @Autowired
    ApplicationContext ctx;


    @GetMapping("/test2")
    public JSONArray test2() {
        JSONObject result = new JSONObject();
        JSONArray resultArray = new JSONArray();
        result.put("code", "fail");
        result.put("msg", "exception.getMessage()");
        return resultArray.put(result);
    }

    //@Value("${spring.datasource.url}")
    //private String yourProperty;

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
    @GetMapping(value = "")
    @ReportDB
    public Map <String, String> test() {
        //DataSource ds = ctx.getBean(DataSource.class);
        //new Thread(() -> {
        //    DynaAmsOrganization dynaAmsOrganization = dynaAmsOrganizationService.getById(1764L);
        //    while (true) {
        //        System.out.println(dynaAmsOrganization.getName());
        //        try {
        //            Thread.sleep(1000);
        //        } catch (InterruptedException e) {
        //            throw new RuntimeException(e);
        //        }
        //    }
        //}).start();
        //DynaAmsUserOptLog byId = dynaAmsUserOptLogService.getById(360930L);
        //System.out.println(yourProperty);
        Map <String, String> errors = new HashMap <>();
        errors.put("123", "test");
        return errors;
    }


    @GetMapping("/test1")
    public JSONArray test1() {
        //DynaAmsOrganization dynaAmsOrganization = dynaAmsOrganizationService.getById(1764L);
        JSONObject result = new JSONObject();
        JSONArray resultArray = new JSONArray();
        result.put("code", "fail");
        result.put("msg", "exception.getMessage()");
        return resultArray.put(result);
    }

    @RequestMapping(value = "/test2", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity <JSONObject> test2(@RequestParam("reqJson") String reqJson) {
        JSONObject jsonObject = new JSONObject(reqJson);
        HttpServletRequest request = (HttpServletRequest) jsonObject.get("$request");
        JSONObject result = new JSONObject();
        result.put("code", "fail");
        result.put("msg", "exception.getMessage()");
        return ResponseEntity.ok(result);
    }
}
