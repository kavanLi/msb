package com.mashibing.internal.common.domain.request;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BusinessOrderListRequest implements Serializable {

    private Long applicationId;
    private String orderNo;
    private String bizOrderNo;
}
