/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart.gateway.processor;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午5:14:39 
 */
public interface Processor {

  /**
   * 组件公共入口方法
   * @param request 请求对象
   * @param context 上下文信息
   * @return
   * @throws Exception
   */
  Object run(HttpServletRequest request, Context context) throws Exception;

  /**
   * 获取组件的名称,用于执行调用链时确定顺序
   * @return
   */
  String getProcessorName();
}
