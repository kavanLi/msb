package com.mashibing.internal.common.domain.response;

import com.mashibing.internal.common.constant.ErrorCodeEnum;
import com.mashibing.internal.common.constant.ServerRespCode;

/**
 * @author Joyce Huang
 */
public class ServerRespEntity {

    public static <T> ServerResp<T> success() {
        return new ServerResp("");
    }

    public static <T> ServerResp<T> success(final T data) {
        return new ServerResp<>(data);
    }

    public static <T> ServerResp<T> fail(final T data) {
        return new ServerResp<>(ServerRespCode.FAIL, data);
    }

    public static <T> ServerResp<T> fail(final ServerRespCode respCode, final T data) {
        return new ServerResp<>(respCode, data);
    }

    public static <T> ServerResp<T> fail(final ErrorCodeEnum errorCodeEnum, final T data) {
        return new ServerResp<>(errorCodeEnum, data);
    }


    public static <T> ServerResp<T> fail(final String respCode, final String msg, final T data) {
        return new ServerResp<>(respCode, msg, data);
    }

    private ServerRespEntity() {
    }
}
