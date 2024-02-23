package com.bobo.mp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bobo.mp.domain.pojo.SaasyunstMchtOrder;
import com.bobo.mp.mapper.SaasyunstMchtOrderMapper;
import com.bobo.mp.service.ISaasyunstMchtOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author lijj
 * @since 2023-5-30
 */
@Service
@Slf4j
public class SaasyunstMchtOrderServiceImpl extends ServiceImpl <SaasyunstMchtOrderMapper, SaasyunstMchtOrder> implements ISaasyunstMchtOrderService {

}
