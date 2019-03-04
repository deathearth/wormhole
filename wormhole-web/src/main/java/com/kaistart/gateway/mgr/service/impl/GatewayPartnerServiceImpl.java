package  com.kaistart.gateway.mgr.service.impl;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.kaistart.gateway.api.service.GatewayPartnerService;
import com.kaistart.gateway.common.tool.GatewayUtil;
import com.kaistart.gateway.domain.GatewayPartnerDO;
import com.kaistart.gateway.exception.DaoException;
import com.kaistart.gateway.exception.GatewayMgrException;
import com.kaistart.gateway.exception.ResultCode;
import com.kaistart.gateway.mgr.mapper.CommonMapper;
import com.kaistart.gateway.mgr.mapper.GatewayAppMapper;
import com.kaistart.gateway.mgr.mapper.GatewayPartnerMapper;
/**
 * 
 * 网关用户表ServiceImpl
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

@Service("gatewayPartnerService")
public class GatewayPartnerServiceImpl extends AbstractCommonServiceImpl<GatewayPartnerDO> implements GatewayPartnerService {

  private static final Logger logger = LoggerFactory.getLogger(GatewayPartnerServiceImpl.class);

    @Resource
    private GatewayPartnerMapper gatewaypartnerMapper;
    
    @Resource
    private GatewayAppMapper gatewayAppMapper;
    
    @Override
    protected CommonMapper<GatewayPartnerDO> getMapper() {
      return gatewaypartnerMapper;
    } 

    @Override
    public void delete(Long id) throws Exception {
      
      Map< String, Object > map = Maps.newHashMap();
      map.put("partnerId", id);
      map.put("status", 1);
      if (gatewayAppMapper.selectCount(map) > 0)
      {
          logger.info("delete partner not match the condition which id is{},because app[{}] exists!",id);
    	  throw new GatewayMgrException(ResultCode.PARTNER_APP_EXIST);
      }
      
      GatewayPartnerDO partner = new GatewayPartnerDO();
      partner.setId(id);
      partner.setStatus(0);
      int result = gatewaypartnerMapper.updateByPrimaryKeySelective(partner);
      if (result <= 0){
          logger.info("删除ID为{}的partner时,更新数量为0!",id);
          throw DaoException.DB_DELETE_RESULT_0;
      }
    }
    
    /**
     * 记录新增
     *
     * @param bean 需持久化的数据对象
     * @throws Exception 数据库访问异常    
     */
    @Override
    public void insert( GatewayPartnerDO partner ) throws Exception {
    	Map< String, Object > map = Maps.newHashMap();
    	map.put("name", partner.getName());
    	if (gatewaypartnerMapper.selectCount(map) > 0)
    	{
    	  logger.info("partner name-【{}】- already exists!",partner.getName());
    		throw new GatewayMgrException(ResultCode.PARTNER_NAME_EXIST);
    	}
    	
    	String partnerKey = GatewayUtil.randomHexString(32);
    	partner.setPartnerKey(partnerKey);
        int result = gatewaypartnerMapper.insert( partner );
        if (result <= 0){
            logger.info("update partner which id is {}, update nums is 0!");
            throw DaoException.DB_INSERT_RESULT_0;
        }
    }

    /**
     * 记录更新
     *
     * @param bean 需持久化的数据对象
     * @throws Exception 数据库访问异常
     */
    @Override
    public void update( GatewayPartnerDO partner ) throws Exception {
    	Map< String, Object > map = Maps.newHashMap();
    	map.put("name", partner.getName());
    	map.put("status", 1);
    	List< GatewayPartnerDO > partnerList = gatewaypartnerMapper.selectList(map);
    	if (!CollectionUtils.isEmpty(partnerList))
    	{
    		for (GatewayPartnerDO partnerTmp : partnerList)
    		{
    			if (!partnerTmp.getId().equals(partner.getId()))
    			{
    			  logger.info("partner name-【{}】- already exists!",partner.getName());
    				throw new GatewayMgrException(ResultCode.PARTNER_NAME_EXIST);
    			}
    		}
    	}
        int result = gatewaypartnerMapper.updateByPrimaryKeySelective( partner );
        if (result <= 0){
            logger.info("update partner which id is {}, update nums is 0!",partner.getId());
            throw DaoException.DB_UPDATE_RESULT_0;
        }
    }

    @Override
    public void batchInsert(List<GatewayPartnerDO> list) throws Exception {
      
    }

//    @Override
//    public void deleteLogic(Long id) throws Exception {
//      
//    }
//
//    @Override
//    public GatewayPartnerDO selectById(Long id) throws Exception {
//      return gatewaypartnerMapper.selectByPrimaryKey(id);
//    }
//
//    @Override
//    public Integer selectCount(Map<String, Object> map) throws Exception {
//      return gatewaypartnerMapper.selectCount(map);
//    }
//
//    @Override
//    public List<GatewayPartnerDO> selectList(Map<String, Object> map) throws Exception {
//      return gatewaypartnerMapper.selectList(map);
//    }

   
 
}




