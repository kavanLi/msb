package com.mashibing.internalcommon.domain.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-18 15:52
 * To change this template use File | Settings | File and Code Templates.
 */

@Data
public class CommonResponse<T> implements Serializable {
    /* fields -------------------------------------------------------------- */
    private String retcode;
    private String retmsg;
    private String reqsn;
    private String appid;
    private String sign;
    private List <T> data;

    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */


    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
