package com.mashibing.internal.common.domain.response;


import com.mashibing.internal.common.constant.ErrorCodeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseResult<T> {

    private String code;
    private String message;
    private T data;

    /**
     * 成功响应的方法
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success() {
        return new ResponseResult().setCode(ErrorCodeEnum.SUCCESS.getCode()).setMessage(ErrorCodeEnum.SUCCESS.getValue());
    }

    /**
     * 成功响应的方法
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success(T data) {
        return new ResponseResult().setCode(ErrorCodeEnum.SUCCESS.getCode()).setMessage(ErrorCodeEnum.SUCCESS.getValue()).setData(data);
    }

    /**
     * 失败：统一的失败
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult fail(T data) {
        return new ResponseResult().setData(data);
    }

    /**
     * 失败：自定义失败 错误码和提示信息
     *
     * @param code
     * @param message
     * @return
     */
    public static ResponseResult fail(String code, String message) {
        return new ResponseResult().setCode(code).setMessage(message);
    }


    /**
     * 失败：自定义失败 错误码、提示信息、具体错误
     *
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static ResponseResult fail(String code, String message, String data) {
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }

}
