package  com.kaistart.gateway.mgr.service.impl;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.kaistart.gateway.api.service.GatewayIpsetService;
import com.kaistart.gateway.domain.GatewayIpsetDO;
import com.kaistart.gateway.exception.DaoException;
import com.kaistart.gateway.exception.GatewayMgrException;
import com.kaistart.gateway.exception.ResultCode;
import com.kaistart.gateway.mgr.mapper.CommonMapper;
import com.kaistart.gateway.mgr.mapper.GatewayIpsetMapper;
import com.kaistart.gateway.tool.DateUtil;
/**
 * 
 * 网关IP名单表ServiceImpl
 * 
 * @version 
 * @author chenhailong
 * <pre>
 * Author	Version		Date		Changes
 * chenhailong 	1.0  		2019年01月14日 	Created
 *
 * </pre>
 * @since 1.
 */



@Service("gatewayIpsetService")
public class GatewayIpsetServiceImpl extends AbstractCommonServiceImpl<GatewayIpsetDO> implements GatewayIpsetService {

    private static final Logger logger = LoggerFactory.getLogger(GatewayIpsetServiceImpl.class);

    @Resource
    private GatewayIpsetMapper gatewayipsetMapper;

    @Override
    protected CommonMapper<GatewayIpsetDO> getMapper() {
        return gatewayipsetMapper;
    }

    @Override
    public void insert(GatewayIpsetDO t) throws Exception {
      Map< String, Object > map = Maps.newHashMap();
      map.put("ip", t.getIp());
      map.put("status", t.getStatus());
      if (gatewayipsetMapper.selectCount(map) > 0) {
        logger.info("ip【{}】 already exists !",t.getIp());
        throw new GatewayMgrException(ResultCode.APP_NAME_EXIST);
      }
      t.setCdt(DateUtil.getFormatTime());
      t.setUdt(DateUtil.getFormatTime());
      int result = gatewayipsetMapper.insert( t );
      if (result <= 0){
        logger.info("insert ip which id is {}, update nums is 0!",t.getId());
          throw DaoException.DB_INSERT_RESULT_0;
      }
    }

    @Override
    public void batchInsert(List<GatewayIpsetDO> list) throws Exception {
      
    }

    @Override
    public void delete(Long id) throws Exception {
      GatewayIpsetDO t = gatewayipsetMapper.selectByPrimaryKey(id);
      t.setStatus(GatewayIpsetDO.STATUS_0);
      t.setUdt(DateUtil.getFormatTime());
      int result = gatewayipsetMapper.updateByPrimaryKeySelective(t);
      if (result <= 0){
          logger.info("delete api which id is {}, update nums is 0!",t.getId());
          throw DaoException.DB_DELETE_RESULT_0;
      }
    }

    @Override
    public void update(GatewayIpsetDO t) throws Exception {
      t.setUdt(DateUtil.getFormatTime());
      int result = gatewayipsetMapper.updateByPrimaryKeySelective(t);
      if (result <= 0){
          logger.info("update ip which id is {}, update nums is 0!",t.getId());
          throw DaoException.DB_DELETE_RESULT_0;
      }
    }

}




