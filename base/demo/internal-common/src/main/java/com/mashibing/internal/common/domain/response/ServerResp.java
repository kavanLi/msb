package com.mashibing.internal.common.domain.response;

import java.io.Serializable;

import com.mashibing.internal.common.constant.ErrorCodeEnum;
import com.mashibing.internal.common.constant.ServerRespCode;
import lombok.Data;

/**
 * @author Joyce Huang
 * @param <T>
 */
@Data
public class ServerResp<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;

	private String msg;

	private T data;

	public ServerResp() {
		this(null);
	}

	public ServerResp(final T data) {
		this(ServerRespCode.SUCCESS, data);
	}

	public ServerResp(final ServerRespCode respCode, final T data) {
		this(respCode.getCode(), respCode.getMsg(), data);
	}

	public ServerResp(final ErrorCodeEnum errorCodeEnum, final T data) {
		this(errorCodeEnum.getCode(), errorCodeEnum.getValue(), data);
	}

	public ServerResp(final String code, final String msg, final T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
