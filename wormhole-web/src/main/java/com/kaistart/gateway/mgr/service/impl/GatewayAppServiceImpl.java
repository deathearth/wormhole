package  com.kaistart.gateway.mgr.service.impl;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.kaistart.gateway.api.service.GatewayAppService;
import com.kaistart.gateway.common.tool.GatewayUtil;
import com.kaistart.gateway.common.tool.ZkService;
import com.kaistart.gateway.domain.GatewayAppDO;
import com.kaistart.gateway.exception.DaoException;
import com.kaistart.gateway.exception.GatewayMgrException;
import com.kaistart.gateway.exception.ResultCode;
import com.kaistart.gateway.mgr.mapper.CommonMapper;
import com.kaistart.gateway.mgr.mapper.GatewayAppAuthMapper;
import com.kaistart.gateway.mgr.mapper.GatewayAppMapper;
/**
 * 
 * APP表ServiceImpl
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



@Service("gatewayAppService")
public class GatewayAppServiceImpl extends AbstractCommonServiceImpl<GatewayAppDO> implements GatewayAppService {

  private static final Logger logger = LoggerFactory.getLogger(GatewayAppServiceImpl.class);

    @Resource
    private GatewayAppMapper gatewayappMapper;
    
    @Resource
    private GatewayAppAuthMapper gatewayAppAuthMapper;
    @Autowired
    private ZkService  zkService;

    @Override
    protected CommonMapper<GatewayAppDO> getMapper() {
      return gatewayappMapper;
    }
    
    @Override
    public void delete(Long id) throws Exception {
      Map< String, Object > map = Maps.newHashMap();
      map.put("appId", id);
      if (gatewayAppAuthMapper.selectCount(map) > 0)
      {
          logger.info("delete app not match the condition which id is{},because app has be granted!!",id);
    	  throw new GatewayMgrException(ResultCode.APP_AUTH_EXIST);
      }
      
      GatewayAppDO app = new GatewayAppDO();
      app.setId(id);
      app.setStatus(0);
    	
      int result = gatewayappMapper.updateByPrimaryKeySelective(app );
      if (result <= 0){
          logger.info("update app which id is {}, update nums is 0!",id);
          throw DaoException.DB_UPDATE_RESULT_0;
      }
      // 全局通知
      zkService.updateApp(id);
      
    }
    
    /**
     * 记录新增
     *
     * @param bean 需持久化的数据对象
     * @throws Exception 数据库访问异常    
     */
    @Override
    public void insert( GatewayAppDO app ) throws Exception {
    	Map< String, Object > map = Maps.newHashMap();
    	map.put("name", app.getName());
    	map.put("partnerId", app.getPartnerId());
    	map.put("status", 1);
    	if (gatewayappMapper.selectCount(map) > 0)
    	{
    	  logger.info("APP name-【{}】- already exists!",app.getName());
    		throw new GatewayMgrException(ResultCode.APP_NAME_EXIST);
    	}
    	String appKey = GatewayUtil.randomHexString(32);
		String appSecret = GatewayUtil.randomHexString(32);
		app.setAppKey(appKey);
		app.setAppSecret(appSecret);
        int result = gatewayappMapper.insert( app );
        if (result <= 0){
           logger.info("delete app which id is {}, update nums is 0!",app.getId());
            throw DaoException.DB_INSERT_RESULT_0;
        }
        // 全局通知
        zkService.updateApp(app.getId());
    }
    
    /**
     * 记录新增
     *
     * @param bean 需持久化的数据对象
     * @throws Exception 数据库访问异常    
     */
    @Override
    public void update( GatewayAppDO app) throws Exception {
    	Map< String, Object > map = Maps.newHashMap();
    	map.put("name", app.getName());
    	map.put("partnerId", app.getPartnerId());
    	map.put("status", 1);
    	List<GatewayAppDO> appList = gatewayappMapper.selectList(map);
    	if (!CollectionUtils.isEmpty(appList))
    	{
    		for(GatewayAppDO appInfo : appList)
    		{
    			if (!appInfo.getId().equals(app.getId()))
    			{
    			  logger.info("APP name-【{}】- already exists!",app.getName());
    				throw new GatewayMgrException(ResultCode.APP_NAME_EXIST);
    			}
    		}
    	}
    	
        int result = gatewayappMapper.updateByPrimaryKeySelective( app );
        if (result <= 0){
          logger.info("update app which id is {}, update nums is 0!",app.getId());
            throw DaoException.DB_UPDATE_RESULT_0;
        }
        // 全局通知
        zkService.updateApp(app.getId());
    }

    @Override
    public void batchInsert(List<GatewayAppDO> list) throws Exception {
      
    }

//    @Override
//    public void deleteLogic(Long id) throws Exception {
//      
//    }
//
//    @Override
//    public GatewayAppDO selectById(Long id) throws Exception {
//      return gatewayappMapper.selectByPrimaryKey(id);
//    }
//
//    @Override
//    public Integer selectCount(Map<String, Object> map) throws Exception {
//      return gatewayappMapper.selectCount(map);
//    }
//
//    @Override
//    public List<GatewayAppDO> selectList(Map<String, Object> map) throws Exception {
//      return gatewayappMapper.selectList(map);
//    }

    
    
}




