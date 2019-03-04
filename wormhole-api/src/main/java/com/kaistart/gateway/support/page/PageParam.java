package com.kaistart.gateway.support.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页参数传递工具类 Filename: PageParam.java Description: Copyright: Copyright (c) 2015-2018 All Rights
 * Reserved. Company: kaistart.cn Inc.
 * 
 * @author: wyz
 * @version: 1.0 Create at: 2017年11月21日 上午10:50:24
 *
 */
public class PageParam implements Serializable {
  private static final long serialVersionUID = 6297178964005032338L;

  private static final Integer PAGE_NUM = 1;
  private static final Integer NUM_PER_PAGE = 20;
  /**
   * 当前页数
   */
  private Integer pageNum = PAGE_NUM; 
  /**
   * 每页记录数
   */
  private Integer numPerPage = NUM_PER_PAGE; 

  /**
   * 条件参数
   */
  private Map<String, Object> paramMap = new HashMap<String, Object>(); 

  public PageParam() {}

  public PageParam(int pageNum, int numPerPage) {
    super();
    this.pageNum = pageNum;
    this.numPerPage = numPerPage;
  }

  /** 当前页数 */
  public int getPageNum() {
    if(pageNum == null) {
      return PAGE_NUM;
    }
    return pageNum;
  }

  /** 当前页数 */
  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  /** 每页记录数 */
  public int getNumPerPage() {
    if(numPerPage == null) {
      return NUM_PER_PAGE;
    }
    return numPerPage;
  }

  /** 每页记录数 */
  public void setNumPerPage(int numPerPage) {
    this.numPerPage = numPerPage;
  }

  public Map<String, Object> getParamMap() {
    return paramMap;
  }

  public void setParamMap(Map<String, Object> paramMap) {
    this.paramMap = paramMap;
  }

  public void addParamMap(String key, Object value) {
    if (null != key && !"".equals(key)) {
      paramMap.put(key, value);
    }
  }
}
