package com.kaistart.gateway.domain.pojo;

import java.util.Date;

/**
 * 用户token实体对象
 * @author chenhailong
 * @date 2019年2月14日 上午10:59:09
 */
public class UserToken {
	private String userid;
	private String token;
	private Date createTime;
	private Date expireTime;
	private long numId;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public void setNumId(long numId) {
		this.numId = numId;
	}

	public long getNumId(){
	  return numId;
	}
}

