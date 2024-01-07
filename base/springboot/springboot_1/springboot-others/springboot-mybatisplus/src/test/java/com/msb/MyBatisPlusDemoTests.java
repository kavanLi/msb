package com.msb;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyBatisPlusDemoTests {


    @SneakyThrows
    @Test
    void test1() {
        System.out.println(123);
        //List <DynaAmsOrganization> dynaAmsOrganizations = dynaAmsOrganizationService.list();
        //System.out.println(123);

        // 获取分公司信息
        //DynaApaTlOrganization dynaApaTlOrganization = dynaApaTlOrganizationService.getById(630381L);
        //// 要更新的数据
        //DynaAmsOrganization dynaAmsOrganization = DynaAmsOrganization.builder()
        //        .name("云商通应用-test1")
        //        .companyId(dynaApaTlOrganization.getId())
        //        .companyLabel(dynaApaTlOrganization.getOrgName())
        //        .sybOrganizationId(1007L)
        //        .serviceProvider("999")
        //        .merchantTag(90001L)
        //        .riskLevel(1L)
        //        .riskTypes("100,2,3")
        //        .fmCreateprinname("liyz4")
        //        .fmUpdateprinname("liyz4")
        //        .build();
        //
        //// 更新的条件
        //QueryWrapper <DynaAmsOrganization> queryWrapper = new QueryWrapper <>();
        //queryWrapper.eq("codeno", appid);
        //boolean update = dynaAmsOrganizationService.update(dynaAmsOrganization, queryWrapper);
        //System.out.println(update);
    }


}
