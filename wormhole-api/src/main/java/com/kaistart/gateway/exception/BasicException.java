package com.kaistart.gateway.exception;

/**
 * 一般异常，非事务异常。
 * Filename:    BasicException.java  
 * Description:   
 * Copyright:   Copyright (c) 2015-2018 All Rights Reserved.
 * Company:     kaistart.cn Inc.
 * @author:     hewei 
 * @version:    1.0  
 * Create at:   2017年9月26日 下午5:00:42  
 *
 */
public class BasicException extends RuntimeException {

	private static final long serialVersionUID = -4971709041482821303L;

	public BasicException() {
		super();
	}

	public BasicException(String message, Throwable cause) {
		super(message, cause);
	}

	public BasicException(String message) {
		super(message);
	}

	public BasicException(Throwable cause) {
		super(cause);
	}
	
}
