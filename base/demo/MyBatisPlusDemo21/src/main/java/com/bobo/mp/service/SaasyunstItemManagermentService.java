package com.bobo.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bobo.mp.domain.model.YchItemManagermentResp;
import com.bobo.mp.domain.pojo.SaasyunstItemManagerment;
import com.bobo.mp.domain.request.ItemManagermentQueryReq;
import com.bobo.mp.domain.response.PageResponse;

/**
* @author kavanLi-R7000
* @description 针对表【saasyunst_item_managerment】的数据库操作Service
* @createDate 2024-02-22 16:18:54
*/
public interface SaasyunstItemManagermentService extends IService<SaasyunstItemManagerment> {

    PageResponse <YchItemManagermentResp> itemList(ItemManagermentQueryReq req);
}
