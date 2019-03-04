package com.kaistart.gateway.exception;

/**
 * 网关自定义异常信息
 * @author chenhailong
 * @date 2019年2月14日 上午10:45:22
 */
public class ResultCode {

	public static ResultCode SUCCESS = new ResultCode("200", "success");
	
	public static ResultCode PARTNER_NAME_EXIST = new ResultCode("1001", "合作用户名已经存在！");
	
	public static ResultCode PARTNER_APP_EXIST = new ResultCode("1002", "该合作用户下已经有创建的应用！");
	
	public static ResultCode APP_NAME_EXIST = new ResultCode("1003", "同一个合作用户下的app名称必须唯一！");
	
	public static ResultCode APP_AUTH_EXIST = new ResultCode("1004", "不能删除已经授权的APP！");
	
	public static ResultCode APIGROUP_NAME_EXIST = new ResultCode("1005", "API组名称已经存在！");
	
	public static ResultCode GROUP_API_LIST_EXIST = new ResultCode("1006", "组下有已经创建的api！");
	
	public static ResultCode APP_GROUP_AUTH_EXIST = new ResultCode("1007", "不能删除已经授权的GROUP！");
	
	public static ResultCode APP_API_AUTH_EXIST = new ResultCode("1008", "不能删除已经授权的API！");
	
	public static ResultCode APP_API_HAS_EXIST = new ResultCode("1009", "apiName和 serviceMethod 已经存在相同记录！");
	
	public static ResultCode GATEWAY_IP_HAS_EXIST = new ResultCode("1010", "该IP地址已经存在！");
	
	public static ResultCode GATEWAY_MARK_HAS_EXIST = new ResultCode("1011", "标签名称已经存在！");
	
	public static ResultCode GATEWAY_MARK_NOT_EXIST = new ResultCode("1012", "标签不存在！");

	public static ResultCode MARK_API_RELATIION_EXIST = new ResultCode("1013", "不能删除已经有绑定关系的标签！");

    public static ResultCode MARK_API_EXIST = new ResultCode("1014", "已经存在绑定关系！");

	private String code;
	private String message;

	public ResultCode() {
	}

	public ResultCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "{code=" + code + ", message=" + message + "}";
	}

}
