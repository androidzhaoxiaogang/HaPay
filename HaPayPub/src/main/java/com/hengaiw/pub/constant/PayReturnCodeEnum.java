package com.hengaiw.pub.constant;

/**
 * 支付返回码定义
 * 
 * @author jianhuizhang
 *
 */
public enum PayReturnCodeEnum {
	PAY_SUCCESS("000000","请求成功"),
	PAY_ERR_000001("000001", "系统错误，未知错误"), PAY_ERR_000002("000002", "参数错误"), PAY_ERR_000003("000003", "查询信息不在在"),
	MCH_ERR_000001("000001","未查找到相关商户信息"),MCH_ERR_000002("000002","商户状态无效"),MCH_ERR_000003("000003","商户密钥无效");
	private String code;
	private String message;

	PayReturnCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
