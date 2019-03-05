package  com.kaistart.gateway.mgr.service.impl;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.kaistart.gateway.api.service.GatewayApiService;
import com.kaistart.gateway.common.tool.ZkService;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayApiGroupDO;
import com.kaistart.gateway.domain.GatewayApiRequestDO;
import com.kaistart.gateway.domain.GatewayApiResultDO;
import com.kaistart.gateway.domain.GatewayServiceRequestDO;
import com.kaistart.gateway.exception.DaoException;
import com.kaistart.gateway.exception.GatewayMgrException;
import com.kaistart.gateway.exception.ResultCode;
import com.kaistart.gateway.mgr.mapper.CommonMapper;
import com.kaistart.gateway.mgr.mapper.GatewayApiGroupMapper;
import com.kaistart.gateway.mgr.mapper.GatewayApiMapper;
import com.kaistart.gateway.mgr.mapper.GatewayApiResultMapper;
import com.kaistart.gateway.mgr.mapper.GatewayAppAuthMapper;
import com.kaistart.gateway.mgr.mapper.GatewayServiceRequestMapper;
/**
 * 
 * API接口表ServiceImpl
 * 
 * @version 
 * @author chenhailong
 * <pre>
 * Author	Version		Date		Changes
 * chenhailong 	1.0  		2018年06月22日 	Created
 *
 * </pre>
 * @since 1.
 */



@Service("gatewayApiService")
public class GatewayApiServiceImpl extends AbstractCommonServiceImpl<GatewayApiDO> implements GatewayApiService {
    private static final Logger logger = LoggerFactory.getLogger(GatewayApiServiceImpl.class);
    
    @Resource
    private GatewayApiMapper gatewayApiMapper;
    
    @Resource
    private GatewayAppAuthMapper gatewayAppAuthMapper;
    
    @Resource
    private GatewayApiResultMapper gatewayApiResultMapper;
    
    @Resource
    private GatewayServiceRequestMapper gatewayServiceRequestMapper;
    
    @Resource
    private GatewayApiGroupMapper gatewayapigroupMapper;
    
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    
    @Autowired
    private ZkService  zkService;
    
    @Override
    protected CommonMapper<GatewayApiDO> getMapper() {
      return gatewayApiMapper;
    }
    
    @Override
    public void delete(Long id) throws Exception {
      Map< String, Object > map = Maps.newHashMap();
      map.put("authId", id);
      map.put("authIdType", 2);
      if (gatewayAppAuthMapper.selectCount(map) > 0)
      {
        logger.info("delete api not match the condition which id is{},because api has be granted!",id);
    	  throw new GatewayMgrException(ResultCode.APP_API_AUTH_EXIST);
      }
    
      int result = gatewayApiMapper.deleteByPrimaryKey(id);
      if (result <= 0){
          logger.info("update api which id is {}, update nums is 0!",id);
          throw DaoException.DB_DELETE_RESULT_0;
      }
      // 全局通知
      zkService.updateApi(id);
    }

    @Override
    public void insert(GatewayApiDO t) throws Exception {
      int result = gatewayApiMapper.insert( t );
      if (result <= 0){
          throw DaoException.DB_INSERT_RESULT_0;
      }
      // 全局通知
      zkService.updateApi(t.getId());
      
    }

    @Override
    public void batchInsert(List<GatewayApiDO> list) throws Exception {
      
    }

//    @Override
//    public void deleteLogic(Long id) throws Exception {
//      
//    }

    @Override
    public void update(GatewayApiDO t) throws Exception {
      int result = gatewayApiMapper.updateByPrimaryKeySelective(t);
      if (result <= 0){
          logger.info("update api which id is {}, update nums is 0!",t.getId());
          throw DaoException.DB_DELETE_RESULT_0;
      }
      // 全局通知
      zkService.updateApi(t.getId());
    }

    @Override
    public Integer selectSearchCount(Map<String, Object> map) {
      return gatewayApiMapper.selectSearchCount(map);
    }

    @Override
    public List<GatewayApiDO> selectSearchPage(Map<String, Object> map) {
      return gatewayApiMapper.selectSearchPage(map);
    }

    @Override
    public boolean transferData(GatewayApiRequestDO bean) {
      GatewayApiDO api = new GatewayApiDO();
      boolean bool =true;
      long groupId = 0;
      
      try { //api方法名 + dubbo方法名 同时只能存在一条记录
        HashMap<String,Object> map = new HashMap<String,Object>(4);
        map.put("name", bean.getName());
        map.put("serviceMethod", bean.getServiceMethod());
        //查询上线状态
        map.put("status","1");  
        List<GatewayApiDO> existList = gatewayApiMapper.selectList(map);
        if(existList.size() > 0) {
          logger.info("sync data has exist ！！！ apiName为{},serviceMethod为{}",bean.getName(),bean.getServiceMethod());
          throw new GatewayMgrException(ResultCode.APP_API_HAS_EXIST);
        }
        
        map = new HashMap<String,Object>(2);
        map.put("name", bean.getGroupName());
        map.put("status", "1");
        List<GatewayApiGroupDO> listGroup = gatewayapigroupMapper.selectList(map);
        //如果有,则查询
        if(listGroup.size() > 0) { 
          GatewayApiGroupDO groupInfo = listGroup.get(0);
          groupId = groupInfo.getId();
        }else { //如果没有则创建
          GatewayApiGroupDO record = new GatewayApiGroupDO();
          record.setName(bean.getGroupName());
          record.setCreateBy("auto");
          record.setUpdateBy("auto");
          record.setCdt(new Date());
          record.setUdt(new Date());
          record.setDescription(bean.getGroupName()+"简介");
          record.setStatus(1); 
          record.setVersion(0);
          int count = gatewayapigroupMapper.insertSelective(record);
          if(count > 0) {
            map = new HashMap<String,Object>(2);
            map.put("name", bean.getGroupName());
            listGroup = gatewayapigroupMapper.selectList(map);
            if(listGroup.size()>0) {
              GatewayApiGroupDO groupInfo = listGroup.get(0);
              groupId = groupInfo.getId();
            }else {
              logger.info("sync apiGroup info has failed ！！！ apiName为{},groupName为{}",bean.getName(),bean.getGroupName());
            }
          }
        }
        
        
        //API基本信息
        api.setGroupId(groupId);
        api.setName(bean.getName());
        api.setDescription(bean.getDescription());
        api.setSpecial(bean.getSpecial());
        api.setStatus(bean.getStatus());
        api.setHttpMethod(bean.getHttpMethod());
        api.setIsAuth(bean.getIsAuth());
        api.setIsLogin(bean.getIsLogin());
        api.setAuthVersion(bean.getAuthVersion());
        api.setServiceName(bean.getServiceName());
        api.setServiceMethod(bean.getServiceMethod());
        api.setServiceVersion(GatewayApiDO.SERVER_VERSION_ONLINE);
        api.setTimeOut(bean.getTimeOut());
        api.setCreateBy(bean.getCreateBy());
        api.setUpdateBy(bean.getUpdateBy());
        api.setCdt(new Date());
        api.setUdt(new Date());
        api.setVersion(bean.getVersion());
        int count = gatewayApiMapper.insert(api);
        if(count < 1) {
          logger.info("sync base info has failed ！！！ apiName为{}",bean.getName());
          bool = false;
        }
        Long apiId = api.getId();
        //API响应信息
        GatewayApiResultDO result = bean.getGatewayApiResultDo();
        result.setId(null);
        result.setApiId(apiId);
        count = gatewayApiResultMapper.insert(result);
        if(count < 1) {
          logger.info("sync response info has failed ！！！ apiName为{}",bean.getName());
          bool = false;
        }
        
        //API参数信息
        List<GatewayServiceRequestDO> list = bean.getParamList();
        for(GatewayServiceRequestDO param :list) {
          param.setId(null);
          param.setApiId(apiId);
          count = gatewayServiceRequestMapper.insert(param);
          if(count < 1) {
            logger.info("sync param info has failed ！！！ apiName为{},paramName为{}",bean.getName(),param.getName());
            bool = false;
          }
        }
        //同步zk数据
        zkService.updateApi(apiId);
      }catch(Exception e) {
        e.printStackTrace();
        logger.info("transfer data has failed ！！！ apiName为{}",bean.getName());
      }
      return bool;
    }
    
    
    
    
    @Override
    public boolean copyData(GatewayApiRequestDO bean) {
      GatewayApiDO api = new GatewayApiDO();
      boolean bool =true;
      long groupId = 0;
      try { //api方法名 + dubbo方法名 同时只能存在一条记录
        HashMap<String,Object> map = new HashMap<String,Object>(2);
        map.put("name", bean.getName());
        map.put("serviceMethod", bean.getServiceMethod());
        List<GatewayApiDO> existList = gatewayApiMapper.selectList(map);
        if(existList.size() > 0) {
          logger.info("sync data has exist ！！！ apiName为{},serviceMethod为{}",bean.getName(),bean.getServiceMethod());
          throw new GatewayMgrException(ResultCode.APP_API_HAS_EXIST);
        }
        
        map = new HashMap<String,Object>(2);
        map.put("name", bean.getGroupName());
        map.put("status", "1");
        List<GatewayApiGroupDO> listGroup = gatewayapigroupMapper.selectList(map);
        //如果有,则查询
        if(listGroup.size() > 0) { 
          GatewayApiGroupDO groupInfo = listGroup.get(0);
          groupId = groupInfo.getId();
        }
        //API基本信息
        api.setGroupId(groupId);
        api.setName(bean.getName());
        api.setDescription(bean.getDescription());
        api.setSpecial(bean.getSpecial());
        api.setStatus(bean.getStatus());
        api.setHttpMethod(bean.getHttpMethod());
        api.setIsAuth(bean.getIsAuth());
        api.setIsLogin(bean.getIsLogin());
        api.setAuthVersion(bean.getAuthVersion());
        api.setServiceName(bean.getServiceName());
        api.setServiceMethod(bean.getServiceMethod());
        api.setServiceVersion(GatewayApiDO.SERVER_VERSION_DEV);
        api.setTimeOut(bean.getTimeOut());
        api.setCreateBy(bean.getCreateBy());
        api.setUpdateBy(bean.getUpdateBy());
        api.setCdt(new Date());
        api.setUdt(new Date());
        api.setVersion(bean.getVersion());
        int count = gatewayApiMapper.insert(api);
        if(count < 1) {
          logger.info("copy base info has failed ！！！ apiName为{}",bean.getName());
          bool = false;
        }
        
        Long apiId = api.getId();
        
        //API响应信息
        GatewayApiResultDO result = bean.getGatewayApiResultDo();
        result.setId(null);
        result.setApiId(apiId);
        count = gatewayApiResultMapper.insert(result);
        if(count < 1) {
          logger.info("copy response info has failed ！！！ apiName为{}",bean.getName());
          bool = false;
        }
        
        //API参数信息
        List<GatewayServiceRequestDO> list = bean.getParamList();
        for(GatewayServiceRequestDO param :list) {
          param.setId(null);
          param.setApiId(apiId);
          count = gatewayServiceRequestMapper.insert(param);
          if(count < 1) {
            logger.info("copy param info has failed ！！！ apiName为{},paramName为{}",bean.getName(),param.getName());
            bool = false;
          }
        }
        
        //同步zk数据
        zkService.updateApi(apiId);
      }catch(Exception e) {
        e.printStackTrace();
        logger.info("copy data has failed ！！！ apiName为{}",bean.getName());
      }
      return bool;
    }

}




