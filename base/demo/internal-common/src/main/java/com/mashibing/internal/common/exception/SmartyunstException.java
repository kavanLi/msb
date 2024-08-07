package com.mashibing.internal.common.exception;

import com.mashibing.internal.common.constant.ErrorCodeEnum;
import com.mashibing.internal.common.constant.ServerRespCode;
import lombok.Data;

/**
 * @author Joyce Huang
 */
@Data
public class SmartyunstException extends RuntimeException{

    private ServerRespCode serverRespCode;

    private ErrorCodeEnum errorCodeEnum;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public SmartyunstException(final ServerRespCode serverRespCode, final String message) {
        super(message);
        this.serverRespCode = serverRespCode;
    }

    public SmartyunstException(final ErrorCodeEnum errorCodeEnum, final String message) {
        super(message);
        this.errorCodeEnum = errorCodeEnum;
    }


    public SmartyunstException(final ServerRespCode errorCode) {
        this(errorCode, errorCode.getMsg());
    }

    public SmartyunstException(final ErrorCodeEnum errorCode) {
        this(errorCode, errorCode.getValue());
    }

}
