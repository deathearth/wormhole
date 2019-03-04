package com.kaistart.gateway.support.proto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kaistart.gateway.exception.DaoException;
import com.kaistart.gateway.support.page.PageParam;
import com.kaistart.gateway.support.page.PageResult;

/**
 * 公共数据操作层
 * @author xxx
 * @date 2018年8月21日 下午6:48:25
 */
public abstract class ProtoServiceImpl<T extends ProtoBean> implements ProtoService<T> {

  /**
   * 获取公共mapper对象
   * @return
   */
  protected abstract ProtoMapper<T> getMapper();

  /**
   * 记录新增
   *
   * @param bean 需持久化的数据对象
   * @throws Exception 数据库访问异常
   */
  @Override
  public void insert(T bean) throws Exception {
    int result = getMapper().insert(bean);
    if (result <= 0) {
      throw DaoException.DB_INSERT_RESULT_0;
    }
  }

  /**
   * 批量新增记录
   * 
   * @param list
   * @throws Exception
   */
  @Override
  public void batchInsert(List<T> list) throws Exception {
    int result = getMapper().batchInsert(list);
    if (result <= 0) {
      throw DaoException.DB_INSERT_RESULT_0;
    }
  }

  /**
   * 记录删除
   *
   * @param id 要删除记录的主键
   * @throws Exception 数据库访问异常
   */
  @Override
  public void delete(Object id) throws Exception {
    int result = getMapper().delete(id);
    if (result <= 0) {
      throw DaoException.DB_DELETE_RESULT_0;
    }
  }

  @Override
  public void deleteDB(Object id) throws Exception {
    int result = getMapper().deleteDB(id);
    if (result <= 0) {
      throw DaoException.DB_DELETE_RESULT_0;
    }
  }

  /**
   * 记录更新
   *
   * @param bean 需持久化的数据对象
   * @throws Exception 数据库访问异常
   */
  @Override
  public void update(T bean) throws Exception {
    int result = getMapper().update(bean);
    if (result <= 0) {
      throw DaoException.DB_UPDATE_RESULT_0;
    }
  }

  /**
   * 根据版本号更新记录
   * 
   * @param bean
   * @throws Exception
   */
  @Override
  public void updateByVersion(T bean) throws Exception {
    int result = getMapper().updateByVersion(bean);
    if (result <= 0) {
      throw DaoException.DB_UPDATE_RESULT_0;
    }
  }

  /**
   * 根据主键查询记录
   *
   * @param id 主键
   * @throws Exception 数据库访问异常
   */
  @Override
  public T selectById(Object id) throws Exception {
    return (T) getMapper().selectById(id);
  }

  /**
   * 根据查询条件返回查询结果记录数。一般用于分页查询。
   *
   * @param map 查询条件映射
   * @throws Exception 数据库访问异常
   */
  @Override
  public Integer selectCount(Map<String, Object> map) throws Exception {
    return getMapper().selectCount(map);
  }

  /**
   * 根据查询条件返回查询结果。
   *
   * @param map 查询条件映射
   * @throws Exception 数据库访问异常
   */
  @Override
  public List<T> selectList(Map<String, Object> map) throws Exception {
    return getMapper().selectList(map);
  }

  /**
   * 根据查询条件分页查询。
   *
   * @param map 查询条件映射
   * @throws Exception 数据库访问异常
   */
  @Override
  public PageResult<T> selectPage(PageParam pageParam) throws Exception {
    Map<String, Object> paramMap = pageParam.getParamMap();
    Integer count = this.getMapper().selectCount(paramMap);
    List<T> list = new ArrayList<T>();
    if (count != null && count.intValue() > 0) {
      int startRecord = (pageParam.getPageNum() - 1) * pageParam.getNumPerPage();
      int endRecord = pageParam.getNumPerPage();
      paramMap.put("skip", startRecord);
      paramMap.put("size", endRecord);
      list = this.getMapper().selectPage(paramMap);
    }
    return new PageResult<T>(pageParam.getPageNum(), pageParam.getNumPerPage(), count.intValue(), list);
  }

  @Override
  public List<T> selectPage(Map<String, Object> map) throws Exception {
    return getMapper().selectPage(map);
  }
}
