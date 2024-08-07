package com.bobo.mp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashibing.internal.common.constant.ItemManagementConstants;
import com.mashibing.internal.common.domain.model.YchItemManagermentResp;
import com.mashibing.internal.common.domain.pojo.SaasyunstItemManagerment;
import com.mashibing.internal.common.domain.request.ItemManagermentQueryReq;
import com.mashibing.internal.common.domain.response.PageResponse;
import com.bobo.mp.mapper.SaasyunstItemManagermentMapper;
import com.bobo.mp.service.SaasyunstItemManagermentService;
import com.mashibing.internal.common.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author kavanLi-R7000
 * @description 针对表【saasyunst_item_managerment】的数据库操作Service实现
 * @createDate 2024-02-22 16:18:54
 */
@Service
@Lazy
@Slf4j
public class SaasyunstItemManagermentServiceImpl extends ServiceImpl <SaasyunstItemManagermentMapper, SaasyunstItemManagerment>
        implements SaasyunstItemManagermentService {


    @Autowired
    private SaasyunstItemManagermentService saasyunstItemManagermentService;


    /**
     * 门店端-科目管理-查询
     *
     * @return *
     */
    @Override
    public PageResponse <YchItemManagermentResp> itemList(ItemManagermentQueryReq req) {
        LambdaQueryWrapper <SaasyunstItemManagerment> queryWrapper = new LambdaQueryWrapper <>();

        //id不为空，添加限定条件
        if (!Objects.isNull(req.getId()) && StringUtils.isNotEmpty(String.valueOf(req.getId()))) {
            queryWrapper.eq(SaasyunstItemManagerment::getId, req.getId());
        }
        //商户用户编号不为空，添加限定条件
        if (StringUtils.isNotEmpty(req.getBizUserId())) {
            queryWrapper.eq(SaasyunstItemManagerment::getBizUserId, req.getBizUserId());
        }
        //科目编号不为空，添加限定条件
        if (StringUtils.isNotEmpty(req.getItemNo())) {
            queryWrapper.eq(SaasyunstItemManagerment::getItemNo, req.getItemNo());
        }
        //科目名称不为空，添加限定条件
        if (StringUtils.isNotEmpty(req.getItemName())) {
            queryWrapper.eq(SaasyunstItemManagerment::getItemName, req.getItemName());
        }
        //科目描述不为空，添加限定条件
        if (StringUtils.isNotEmpty(req.getItemDesc())) {
            queryWrapper.eq(SaasyunstItemManagerment::getItemDesc, req.getItemDesc());
        }
        //科目层级不为空，添加限定条件
        if (!Objects.isNull(req.getItemTier()) && ItemManagementConstants.ItemTier.exists(req.getItemTier())) {
            queryWrapper.eq(SaasyunstItemManagerment::getItemTier, ItemManagementConstants.ItemTier.of(req.getItemTier()).getCode());
        }
        //应用场景不为空，添加限定条件
        if (!Objects.isNull(req.getItemScene()) && ItemManagementConstants.ItemScene.exists(req.getItemScene())) {
            queryWrapper.eq(SaasyunstItemManagerment::getItemScene, ItemManagementConstants.ItemScene.of(req.getItemScene()).getCode());
        }
        //顺序号不为空，添加限定条件
        if (!Objects.isNull(req.getSeqNo())) {
            if (req.getSeqNo() > 0) {
                queryWrapper.eq(SaasyunstItemManagerment::getSeqNo, req.getSeqNo());
            } else {
                //throw new SaasYunstException(ServerRespCode.ERROR_PARAM, "顺序号必须时是大于0的正整数！");
            }
        }

        //价格不为空，添加限定条件
        if (!Objects.isNull(req.getItemPrice())) {
            queryWrapper.eq(SaasyunstItemManagerment::getItemPrice, req.getItemPrice());
        }
        //实名标识不为空，添加限定条件
        if (!Objects.isNull(req.getIsRealName())) {
            queryWrapper.eq(SaasyunstItemManagerment::getIsRealName, req.getIsRealName());
        }
        //收款方编号不为空，添加限定条件
        if (StringUtils.isNotEmpty(req.getReceiverId())) {
            queryWrapper.eq(SaasyunstItemManagerment::getReceiverId, req.getReceiverId());
        }
        //收款方名称不为空，添加限定条件
        if (StringUtils.isNotEmpty(req.getReceiverName())) {
            queryWrapper.eq(SaasyunstItemManagerment::getReceiverName, req.getReceiverName());
        }
        //启用状态不为空，添加限定条件
        if (!Objects.isNull(req.getStatus()) && ItemManagementConstants.ItemStatus.exists(req.getStatus())) {
            queryWrapper.eq(SaasyunstItemManagerment::getStatus,
                    ItemManagementConstants.ItemStatus.of(req.getStatus()).getCode());
        }

        if (StringUtils.isNotEmpty(req.getCreateUserName())) {
            queryWrapper.eq(SaasyunstItemManagerment::getCreateUserName, req.getCreateUserName());
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotEmpty(req.getStartTime())) {
                Date orderGenerateTimeBegin = sdf.parse(req.getStartTime() + " 00:00:00");
                queryWrapper.ge(SaasyunstItemManagerment::getGmtCreate, orderGenerateTimeBegin);
            }
            if (StringUtils.isNotEmpty(req.getEndTime())) {
                Date orderGenerateTimeEndStr = sdf.parse(req.getEndTime() + " 23:59:59");
                queryWrapper.le(SaasyunstItemManagerment::getGmtCreate, orderGenerateTimeEndStr);
            }
        } catch (Exception e) {
            //throw new SaasYunstException(ServerRespCode.ERROR_PARAM, "创建时间参数格式错误转换异常!");
        }

        //按创建时间进行排序
        queryWrapper.orderByDesc(SaasyunstItemManagerment::getGmtCreate);
        IPage <SaasyunstItemManagerment> iPage = new Page <>(req.getPageNo(), req.getPageSize());
        iPage = saasyunstItemManagermentService.page(iPage, queryWrapper);
        PageResponse <YchItemManagermentResp> pageResponse = null;
        if (iPage != null) {
            log.info("current:{},size:{},total:{},pages:{}", iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getPages());
            List <SaasyunstItemManagerment> list = iPage.getRecords();
            List <YchItemManagermentResp> respList = new ArrayList <>();
            for (SaasyunstItemManagerment item : list) {
                YchItemManagermentResp resp = new YchItemManagermentResp();
                BeanUtils.mergeObjects(item, resp);
                respList.add(resp);
            }
            pageResponse = new PageResponse <>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), respList);
        }

        return pageResponse;
    }


}




