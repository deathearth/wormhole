/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart.gateway.domain;


/**
 *
 * 
 * @author chenhailong
 * @date 2018年8月6日 下午2:19:34 
 */
public class SyncDataDo extends CommonDO {

  /**
   * 
   */
  private static final long serialVersionUID = 1263653314820653510L;
  
  private String syncData;

  public String getSyncData() {
    return syncData;
  }

  public void setSyncData(String syncData) {
    this.syncData = syncData;
  }
  

}
