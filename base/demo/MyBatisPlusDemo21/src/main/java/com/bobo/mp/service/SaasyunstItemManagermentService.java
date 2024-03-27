package com.bobo.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashibing.internalcommon.domain.model.YchItemManagermentResp;
import com.mashibing.internalcommon.domain.pojo.SaasyunstItemManagerment;
import com.mashibing.internalcommon.domain.request.ItemManagermentQueryReq;
import com.mashibing.internalcommon.domain.response.PageResponse;

/**
* @author kavanLi-R7000
* @description 针对表【saasyunst_item_managerment】的数据库操作Service
* @createDate 2024-02-22 16:18:54
*/
public interface SaasyunstItemManagermentService extends IService<SaasyunstItemManagerment> {

    PageResponse <YchItemManagermentResp> itemList(ItemManagermentQueryReq req);
}
