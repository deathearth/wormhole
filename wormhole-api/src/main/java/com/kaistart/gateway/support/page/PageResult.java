package com.kaistart.gateway.support.page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页组件 Filename: PageResult.java Description: Copyright: Copyright (c) 2015-2018 All Rights
 * Reserved. Company: kaistart.cn Inc.
 * 
 * @author: wyz
 * @version: 1.0 Create at: 2017年11月21日 上午10:50:39
 *
 */
public class PageResult<T> implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 8470697978259453214L;
  
  /**
   * 默认每页大小为10
   */
  private static final Integer PAGE_SIZE = 10;

  /** 当前页*/
  private Integer currentPage;
  /** 每页显示多少条*/
  private Integer numPerPage; 
  /** 总记录数*/
  private Integer totalCount; 
  /** 本页的数据列表*/
  private List<T> recordList; 
  /** 总页数*/
  private Integer pageCount; 
  /** 页码列表的开始索引（包含）*/
  private Integer beginPageIndex; 
  /** 页码列表的结束索引（包含）*/
  private Integer endPageIndex; 
  /** 当前分页条件下的统计结果*/
  private Map<String, Object> countResultMap; 


  public PageResult() {}

  /**
   * 只接受前4个必要的属性，会自动的计算出其他3个属生的值
   * 
   * @param currentPage
   * @param pageSize
   * @param totalCount
   * @param recordList
   */
  public PageResult(Integer currentPage, Integer numPerPage, Integer totalCount, List<T> recordList) {
    this.currentPage = currentPage;
    this.numPerPage = numPerPage;
    this.totalCount = totalCount;
    this.recordList = recordList;

    // 计算总页码
    pageCount = (totalCount + numPerPage - 1) / numPerPage;

    // 计算 beginPageIndex 和 endPageIndex
    // >> 总页数不多于10页，则全部显示
    if (pageCount <= PAGE_SIZE) {
      beginPageIndex = 1;
      endPageIndex = pageCount;
    }
    // >> 总页数多于10页，则显示当前页附近的共10个页码
    else {
      // 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
      beginPageIndex = currentPage - 4;
      endPageIndex = currentPage + 5;
      // 当前面的页码不足4个时，则显示前10个页码
      if (beginPageIndex < 1) {
        beginPageIndex = 1;
        endPageIndex = 10;
      }
      // 当后面的页码不足5个时，则显示后10个页码
      if (endPageIndex > pageCount) {
        endPageIndex = pageCount;
        beginPageIndex = pageCount - 10 + 1;
      }
    }
  }

  /**
   * 只接受前5个必要的属性，会自动的计算出其他3个属生的值
   * 
   * @param currentPage
   * @param pageSize
   * @param totalCount
   * @param recordList
   */
  public PageResult(Integer currentPage, Integer numPerPage, Integer totalCount, List<T> recordList, Map<String, Object> countResultMap) {
    this.currentPage = currentPage;
    this.numPerPage = numPerPage;
    this.totalCount = totalCount;
    this.recordList = recordList;
    this.countResultMap = countResultMap;

    // 计算总页码
    pageCount = (totalCount + numPerPage - 1) / numPerPage;

    // 计算 beginPageIndex 和 endPageIndex
    // >> 总页数不多于10页，则全部显示
    if (pageCount <= PAGE_SIZE) {
      beginPageIndex = 1;
      endPageIndex = pageCount;
    }
    // >> 总页数多于10页，则显示当前页附近的共10个页码
    else {
      // 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
      beginPageIndex = currentPage - 4;
      endPageIndex = currentPage + 5;
      // 当前面的页码不足4个时，则显示前10个页码
      if (beginPageIndex < 1) {
        beginPageIndex = 1;
        endPageIndex = 10;
      }
      // 当后面的页码不足5个时，则显示后10个页码
      if (endPageIndex > pageCount) {
        endPageIndex = pageCount;
        beginPageIndex = pageCount - 10 + 1;
      }
    }
  }

  public List<T> getRecordList() {
    return recordList;
  }

  public void setRecordList(List<T> recordList) {
    this.recordList = recordList;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public int getPageCount() {
    return pageCount;
  }

  public void setPageCount(Integer pageCount) {
    this.pageCount = pageCount;
  }

  public int getNumPerPage() {
    return numPerPage;
  }

  public void setNumPerPage(Integer numPerPage) {
    this.numPerPage = numPerPage;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public int getBeginPageIndex() {
    return beginPageIndex;
  }

  public void setBeginPageIndex(Integer beginPageIndex) {
    this.beginPageIndex = beginPageIndex;
  }

  public int getEndPageIndex() {
    return endPageIndex;
  }

  public void setEndPageIndex(Integer endPageIndex) {
    this.endPageIndex = endPageIndex;
  }

  public Map<String, Object> getCountResultMap() {
    return countResultMap;
  }

  public void setCountResultMap(Map<String, Object> countResultMap) {
    this.countResultMap = countResultMap;
  }

}
