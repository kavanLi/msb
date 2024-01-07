package com.bobo.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bobo.mp.dataSource.annotation.ReportDB;
import com.bobo.mp.domain.DynaAmsSoaservice;
import com.bobo.mp.domain.DynaAmsSubprodApi;
import com.bobo.mp.domain.pojo.DynaAmsOrganization;
import com.bobo.mp.domain.pojo.DynaAmsUserOptLog;
import com.bobo.mp.domain.pojo.DynaApaTlOrganization;
import com.bobo.mp.mapper.UserMapper;
import com.bobo.mp.domain.pojo.User;
import com.bobo.mp.service.DynaAmsSoaserviceService;
import com.bobo.mp.service.DynaAmsSubprodApiService;
import com.bobo.mp.service.DynaAmsUserOptLogService;
import com.bobo.mp.service.DynaApaTlOrganizationService;
import com.bobo.mp.service.IUserService;
import com.bobo.mp.service.DynaAmsOrganizationService;
import lombok.SneakyThrows;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sql.DataSource;

@SpringBootTest
class MyBatisPlusDemo21ApplicationTests {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private DynaAmsOrganizationService dynaAmsOrganizationService;

    @Autowired
    private DynaApaTlOrganizationService dynaApaTlOrganizationService;

    @Autowired
    private DynaAmsUserOptLogService dynaAmsUserOptLogService;

    @Autowired
    private DynaAmsSubprodApiService dynaAmsSubprodApiService;

    @Autowired
    private DynaAmsSoaserviceService dynaAmsSoaserviceService;

    @Autowired
    private DataSource dataSource;

    public void printDatabaseInfo() {
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

    private final static List <String> yunstIndustryApiName = Stream.of("signalAgentPay",
            "queryBalance",
            "getOrderStatus",
            "withdrawApply",
            "consumeProtocolApply",
            "getCheckAccountFile",
            "applyBindBankCard",
            "applyBindBankCardAndCreateMember",
            "bindBankCardAndCreateMember",
            "bindPhone",
            "createMember",
            "sendVerificationCode",
            "quickpayAgreeApply",
            "setRealName",
            "bindPhoneWithoutConfirm",
            "getMemberInfo",
            "unbindBankCard",
            "getOrderDetail",
            "unfreezeMoney",
            "agentCollectProtocolApply",
            "queryBankCardFourElements",
            "bankCardSyncInfo",
            "agentCollectApply",
            "freezeMoney",
            "queryMerchantBalance",
            "refund",
            "orderSplitRefund").collect(Collectors.toList());


    @SneakyThrows
    @Test
    @ReportDB
    void initData4DynaAmsSubprodApi() {
        List <DynaAmsSoaservice> dynaAmsSoaservices = dynaAmsSoaserviceService.list();
        List <DynaAmsSubprodApi> subProdApiList = new ArrayList <>();
        Random random = new Random();
        for (DynaAmsSoaservice soaService : dynaAmsSoaservices) {
            DynaAmsSubprodApi subProdApi = new DynaAmsSubprodApi();
            subProdApi.setApiId(soaService.getId());
            subProdApi.setAvailable((short) random.nextInt(2));
            subProdApi.setNameEn(soaService.getNameEn());
            subProdApi.setNameSoa(soaService.getNameSoa());
            subProdApi.setPVersion(soaService.getPVersion());
            subProdApi.setProdId(String.valueOf(random.nextInt(3)));
            subProdApi.setCreateTime(new Date());
            subProdApi.setUpdateTime(new Date());
            if (yunstIndustryApiName.contains(soaService.getNameEn())) {
                subProdApi.setApplicationId(1272802971645644802L);
                subProdApi.setCreateUserName("慧营销接口");
                subProdApi.setUpdateUserName("慧营销接口");
            } else {
                subProdApi.setApplicationId(1811011059060727510L);
                subProdApi.setCreateUserName("云商通二代接口");
                subProdApi.setUpdateUserName("云商通二代接口");
            }
            subProdApiList.add(subProdApi);
        }
        dynaAmsSubprodApiService.saveBatch(subProdApiList);
    }

    @SneakyThrows
    @Test
    @ReportDB
    void test1() {
        System.out.println(123);

        //printDatabaseInfo();
        //DynaAmsUserOptLog dynaAmsUserOptLog = DynaAmsUserOptLog.builder()
        //        .createSource(3L)
        //        .loginName("createUser" + "/" + "updateUser")
        //        .ipAddress("reqip" + "/" + "request.getRemoteAddr()")
        //        .requestUrl("request.getRequestURI()")
        //        .optName("optName")
        //        .requestTime(LocalDateTime.now())
        //        .requestParam("String.valueOf(args)").build();
        //DynaAmsUserOptLog byId = dynaAmsUserOptLogService.getById(360930L);
        //Map <Long, String> allEnumList = EnumCacheUtil.getAllEnumList();
        ////throw new IllegalArgumentException();
        //Thread.sleep(1000);
        //Map <Long, String> allEnumList1 = EnumCacheUtil.getAllEnumList();
        System.out.println(123);
        //List <DynaAmsOrganization> dynaAmsOrganizations = dynaAmsOrganizationService.list();
        //System.out.println(123);

        // 获取分公司信息
        //DynaAmsOrganization dynaAmsOrganization = dynaAmsOrganizationService.getById(1764L);
        System.out.println(123);

        // 要更新的数据
        //DynaAmsUserOptLog dynaAmsUserOptLog1 = DynaAmsUserOptLog.builder()
        //        .loginName("litg1")
        //        .build();
        //dynaAmsUserOptLogService.save(dynaAmsUserOptLog1);
        //dynaAmsUserOptLogService.getById(dynaAmsUserOptLog1);
        // 更新的条件
        //QueryWrapper <DynaAmsOrganization> queryWrapper = new QueryWrapper <>();
        //queryWrapper.eq("codeno", appid);
        //boolean update = dynaAmsOrganizationService.update(dynaAmsOrganization, queryWrapper);
        //System.out.println(update);
    }


    /**
     * 姓名包含o 年龄大于20 且邮箱不为空的用户
     * select * from t_user where name like '%o%' and age > 20 and email is not null
     */
    @Test
    void queryAllUser() {
        QueryWrapper <User> wrapper = new QueryWrapper <>();
        wrapper.like("name", "o")
                .gt("age", 20)
                .isNull("email");
        List <User> users = mapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 排序：首先根据年龄升序，年龄相同的情况下根据id降序
     */
    @Test
    void queryAllUserByOrder() {
        QueryWrapper <User> wrapper = new QueryWrapper <>();
        wrapper.orderByAsc("age")
                .orderByDesc("uid");
        List <User> users = mapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 删除年龄小18的记录
     */
    @Test
    void deleteUser() {
        QueryWrapper <User> wrapper = new QueryWrapper <>();
        wrapper.lt("age", 18);
        int i = mapper.delete(wrapper);
        System.out.println(i);
    }

    /**
     * 查询出姓名中包含o 且年龄大于23 或者 邮箱地址为空的记录
     * select * from t_user where (name like '%o%' and age > 18 ) or ( email is  null )
     * SELECT uid,name AS userName,age,email,is_deleted
     * FROM t_user
     * WHERE is_deleted=0 AND (name LIKE ? AND age > ? OR email IS NULL)
     */
    @Test
    void queryAllUser1() {
        QueryWrapper <User> wrapper = new QueryWrapper <>();
        /*wrapper.like("name","o")
                .gt("age",23)
                .or().isNull("email");*/
        wrapper.and((item) -> {
            item.like("name", "o").gt("age", 23);
        }).or((item) -> {
            item.isNull("email");
        });
        List <User> users = mapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 查询出特定的字段列表
     */
    @Test
    void queryAllUser2() {
        QueryWrapper <User> wrapper = new QueryWrapper <>();
        /*wrapper.like("name","o")
                .gt("age",23)
                .or().isNull("email");*/
        wrapper.and((item) -> {
            item.like("name", "o").gt("age", 23);
        }).or((item) -> {
            item.isNull("email");
        }).select("uid", "name", "age");
        //List<User> users = mapper.selectList(wrapper);
        List <Map <String, Object>> maps = mapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    /**
     * 子查询
     * select * from t_user where id in (select id from t_user where id < 6 )
     */
    @Test
    void queryAllUser3() {
        QueryWrapper <User> wrapper = new QueryWrapper <>();
        wrapper.inSql("uid", "select uid from t_user where uid < 6 ");
        //List<User> users = mapper.selectList(wrapper);
        List <Map <String, Object>> maps = mapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    @Test
    void queryAllUser4() {
        String name = "Tom";
        Integer age = null;
        String email = null;
        QueryWrapper <User> wrapper = new QueryWrapper <>();
        if (StringUtils.isNotBlank(name)) {
            wrapper.eq("name", name);
        }
        if (age != null && age > 0) {
            wrapper.eq("age", age);
        }
        if (StringUtils.isNotBlank(email)) {
            wrapper.eq("email", email);
        }
        List <User> users = mapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void queryAllUser5() {
        String name = "Tom";
        Integer age = null;
        String email = null;
        QueryWrapper <User> wrapper = new QueryWrapper <>();
        wrapper.eq(StringUtils.isNotBlank(name), "name", name)
                .eq(age != null && age > 0, "age", age)
                .eq(StringUtils.isNotBlank(email), "email", email);
        List <User> users = mapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void queryAllUserPage() {
        Page <User> page = new Page <>(1, 5);
        Page <User> userPage = mapper.selectPage(page, null);
        List <User> list = userPage.getRecords();
        list.forEach(System.out::println);
        System.out.println("userPage.getPages() = " + userPage.getPages());
        System.out.println("userPage.getTotal() = " + userPage.getTotal());
        System.out.println("userPage.getSize() = " + userPage.getSize());
        System.out.println("userPage.getCurrent() = " + userPage.getCurrent());
        System.out.println("userPage.hasNext() = " + userPage.hasNext());
        System.out.println("userPage.hasPrevious() = " + userPage.hasPrevious());

    }


    @Test
    void updateUser() {
        UpdateWrapper <User> wrapper = new UpdateWrapper <>();
        wrapper.set("age", 33)
                .set("email", "xxx@qq.com") // set 是update中的 set 部分 后面的 eq是 where 条件
                .eq("name", "Tom");
        mapper.update(null, wrapper);
    }
}
