package com.mashibing.internal.common.exception;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-13 10:38
 * To change this template use File | Settings | File and Code Templates.
 */
public class BaseException extends RuntimeException {
    public BaseException() {
        super();
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }
}