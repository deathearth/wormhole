/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart.gateway.common.http;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

/**
 * get发送body
 * 
 * @author chenhailong
 * @date 2019年1月9日 下午6:07:03 
 */
public class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {

  private final static String METHOD_NAME = "GET";

  @Override
  public String getMethod() {
      return METHOD_NAME;
  }

  public HttpGetWithEntity() {
      super();
  }

  public HttpGetWithEntity(final URI uri) {
      super();
      setURI(uri);
  }

  HttpGetWithEntity(final String uri) {
      super();
      setURI(URI.create(uri));
  }
}
