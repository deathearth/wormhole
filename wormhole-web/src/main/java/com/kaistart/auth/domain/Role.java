package com.kaistart.auth.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.kaistart.gateway.support.proto.ProtoBean;

/**
 * @author fendyguo
 * @date 2019年2月21日 下午7:40:44
 */
public class Role extends ProtoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String group;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date cdt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date udt;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCdt() {
		return this.cdt;
	}

	public void setCdt(Date cdt) {
		this.cdt = cdt;
	}

	public Date getUdt() {
		return this.udt;
	}

	public void setUdt(Date udt) {
		this.udt = udt;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "Role{id=" + this.id + ", name='" + this.name + '\'' + ", cdt=" + this.cdt + ", udt=" + this.udt + '}';
	}
}
