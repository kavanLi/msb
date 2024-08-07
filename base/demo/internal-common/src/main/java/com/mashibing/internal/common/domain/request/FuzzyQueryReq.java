package com.mashibing.internal.common.domain.request;

import lombok.Data;

@Data
public class FuzzyQueryReq {

    /**
     * 平台号
     */
    private String applicationId;

    /**
     * 会员编号
     */
    private String bizUserId;

    private String queryStr;

}
