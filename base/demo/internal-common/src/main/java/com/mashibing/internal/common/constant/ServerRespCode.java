package com.mashibing.internal.common.constant;

/**
 * @author Joyce Huang
 */
public enum ServerRespCode {

	SUCCESS("000000", "成功"),
	FAIL("000001", "失败"),
	//登录异常
	NOT_LOGIN("000002","未登录"),
	INCOMPLETE_LOGIN_INFO("000003","登录信息不全"),
	// 参数ERROR
	ERROR_PARAM("100000", "参数异常"),
	// 数据ERROR
	ERROR_NOT_FOUND("200000", "数据未找到"),
	ERROR_DATA("200001", "数据异常"),
	ERROR_PERMISSION("200002", "权限不足"),
	// 云商通响应ERROR信息
	YUNST_RESPONSE_ERROR("300000", "云商通响应异常"),
	YUNST_FAIL("300001","云商通服务调用失败"),
	//企业会员H5注册流程已完成，请勿重复操作对应响应码

	BUSINESS_UNIFIED_CUST_ERROR("300002", "查询统一客户失败"),
	BUSINESS_WORK_PICTURE_EMPTY("300003", "文件为空，请上传文件"),
	BOSS_UPLOAD_FILE_FAIL("300004", "上传文件失败"),
	BUSINESS_WORK_PICTURE_NOT_FOUND("300005", "图片不存在"),
	BUSINESS_YUNST_INFOMODIFY_ERROR("300006", "企业、法人比对一致：不能重新设置"),
	BUSINESS_YUNST_COMCNL_ERROR("300007", "企业对比成功，图片不能修改"),
	BUSINESS_YUNST_LEGCNL_ERROR("300008", "法人对比成功，图片不能修改"),

	BUSINESS_OCRSERVICE_ERROR("300009", "OCR服务异常"),
	BUSINESS_OCRTYPE_ERROR("300010", "OCR类型错误"),
	BUSINESS_OCRGETPICTURE_ERROR("300012", "获取图片失败"),
	BUSINESS_UAS_ENC_ERROR("300014", "加密失败"),
	BUSINESS_UAS_SIGN_ERROR("300015", "加签失败"),

	//小程序相关的错误返回，需要明确弹出对应提示内容
	MINI_ERROR("600000", ""),

	COMPANY_H5_REGISTERED("910000","企业会员H5注册流程已完成，请勿重复操作"),
	COMPANY_H5_SMSPARAM("910001", "授权人信息不能为空"),
	COMPANY_H5_SMSSENDED("910002", "授权协议上传成功，请等待审核完成");
	private String code;

	private String msg;

	ServerRespCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
