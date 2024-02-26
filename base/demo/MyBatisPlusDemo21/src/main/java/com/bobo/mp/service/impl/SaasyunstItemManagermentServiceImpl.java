package com.bobo.mp.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bobo.mp.domain.pojo.SaasyunstItemManagerment;
import com.bobo.mp.mapper.SaasyunstItemManagermentMapper;
import com.bobo.mp.service.SaasyunstItemManagermentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author kavanLi-R7000
 * @description 针对表【saasyunst_item_managerment】的数据库操作Service实现
 * @createDate 2024-02-22 16:18:54
 */
@Service
@Slf4j
public class SaasyunstItemManagermentServiceImpl extends ServiceImpl <SaasyunstItemManagermentMapper, SaasyunstItemManagerment>
        implements SaasyunstItemManagermentService {


    @Resource
    private SaasyunstItemManagermentService saasyunstItemManagermentService;

}




