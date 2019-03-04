package  com.kaistart.gateway.mgr.service.impl;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.kaistart.gateway.api.service.GatewayApiGroupService;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayApiGroupDO;
import com.kaistart.gateway.exception.DaoException;
import com.kaistart.gateway.exception.GatewayMgrException;
import com.kaistart.gateway.exception.ResultCode;
import com.kaistart.gateway.mgr.mapper.CommonMapper;
import com.kaistart.gateway.mgr.mapper.GatewayApiGroupMapper;
import com.kaistart.gateway.mgr.mapper.GatewayApiMapper;
import com.kaistart.gateway.mgr.mapper.GatewayAppAuthMapper;
/**
 * 
 * API分组表ServiceImpl
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



@Service("gatewayApiGroupService")
public class GatewayApiGroupServiceImpl extends AbstractCommonServiceImpl<GatewayApiGroupDO> implements GatewayApiGroupService {

  private static final Logger logger = LoggerFactory.getLogger(GatewayApiGroupServiceImpl.class);
  
    @Resource
    private GatewayApiGroupMapper gatewayapigroupMapper;
    
    @Resource
    private GatewayApiMapper gatewayApiMapper;
    
    @Resource
    private GatewayAppAuthMapper gatewayAppAuthMapper;

    @Override
    protected CommonMapper<GatewayApiGroupDO> getMapper() {
      return gatewayapigroupMapper;
    }

    @Override
    public void delete(Long id) throws Exception {
      Map< String, Object > map = Maps.newHashMap();
      map.put("groupId", id);
      List<GatewayApiDO> groupList = gatewayApiMapper.selectList(map);
      if (!CollectionUtils.isEmpty(groupList))
      {
    	  for(GatewayApiDO api : groupList)
    	  {
    		  if (api.getStatus() != 3)
    		  {
    		      logger.info("delete apigroup not match the condition which id is{},because api[{}] exists!",id,api.getId());
    			  throw new GatewayMgrException(ResultCode.GROUP_API_LIST_EXIST);
    		  }
    	  }
      }
      
      map.clear();
      map.put("authId", id);
      map.put("authIdType", 1);
      if (gatewayAppAuthMapper.selectCount(map) > 0)
      {
          logger.info("delete apigroup not match the condition which id is{},because group has be granted!",id);
    	  throw new GatewayMgrException(ResultCode.APP_GROUP_AUTH_EXIST);
      }
      
      
      GatewayApiGroupDO group = new GatewayApiGroupDO();
      group.setId(id);
      group.setStatus(0);
    	
      int result = gatewayapigroupMapper.updateByPrimaryKeySelective(group );
      if (result <= 0){
          logger.info("update apigourp which id is {}, update nums is 0!",id);
          throw DaoException.DB_UPDATE_RESULT_0;
      }
    }
    
    /**
     * 记录新增
     *
     * @param bean 需持久化的数据对象
     * @throws Exception 数据库访问异常    
     */
    @Override
    public void insert( GatewayApiGroupDO group ) throws Exception {
    	Map< String, Object > map = Maps.newHashMap();
    	map.put("name", group.getName());
    	map.put("status", 1);
    	if (gatewayapigroupMapper.selectCount(map) > 0)
    	{
    	    logger.info("The apiGroup name-【{}】-already exists!",group.getName());
    		throw new GatewayMgrException(ResultCode.APIGROUP_NAME_EXIST);
    	}
    	
        int result = gatewayapigroupMapper.insert( group );
        if (result <= 0){
            throw DaoException.DB_INSERT_RESULT_0;
        }
    }
    
    /**
     * 记录新增
     *
     * @param bean 需持久化的数据对象
     * @throws Exception 数据库访问异常    
     */
    @Override
    public void update( GatewayApiGroupDO group) throws Exception {
    	Map< String, Object > map = Maps.newHashMap();
    	map.put("name", group.getName());
    	map.put("status", 1);
    	List<GatewayApiGroupDO> groupList = gatewayapigroupMapper.selectList(map);
    	if (!CollectionUtils.isEmpty(groupList))
    	{
    		for(GatewayApiGroupDO groupTmp : groupList)
    		{
    			if (!groupTmp.getId().equals(group.getId()))
    			{
    			    logger.info("The apiGroup name-【{}】-already exists!",group.getName());
    				throw new GatewayMgrException(ResultCode.APIGROUP_NAME_EXIST);
    			}
    		}
    	}
    	
        int result = gatewayapigroupMapper.updateByPrimaryKeySelective( group );
        if (result <= 0){
            logger.info("update apigourp which id is {}, update nums is 0!",group.getId());
            throw DaoException.DB_UPDATE_RESULT_0;
        }
    }

    @Override
    public void batchInsert(List<GatewayApiGroupDO> list) throws Exception {
      
    }

//    @Override
//    public GatewayApiGroupDO selectById(Long id) throws Exception {
//      return gatewayapigroupMapper.selectByPrimaryKey(id);
//    }


    

    
    
 
}




