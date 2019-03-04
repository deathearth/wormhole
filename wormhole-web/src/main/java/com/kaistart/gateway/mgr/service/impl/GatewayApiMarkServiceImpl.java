package  com.kaistart.gateway.mgr.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.kaistart.gateway.api.service.GatewayApiMarkService;
import com.kaistart.gateway.domain.GatewayApiMarkDO;
import com.kaistart.gateway.domain.GatewayMarkDO;
import com.kaistart.gateway.mgr.mapper.CommonMapper;
import com.kaistart.gateway.mgr.mapper.GatewayApiMarkMapper;
/**
 * 
 * 接口标签关系表ServiceImpl
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



@Service("gatewayApiMarkService")
public class GatewayApiMarkServiceImpl extends AbstractCommonServiceImpl<GatewayApiMarkDO> implements GatewayApiMarkService {

  private static final Logger logger = LoggerFactory.getLogger(GatewayApiMarkServiceImpl.class);

    @Resource
    private GatewayApiMarkMapper gatewayapimarkMapper;

    @Override
    protected CommonMapper<GatewayApiMarkDO> getMapper() {
        return gatewayapimarkMapper;
    }

    @Override
    public void insert(GatewayApiMarkDO t) throws Exception {
      Map< String, Object > map = Maps.newHashMap();
      map.put("markId", t.getMarkId());
      map.put("apiId", t.getApiId());
      if (gatewayapimarkMapper.selectCount(map) > 0)
      {
        logger.info("apiId【{}】and markId-【{}】- already exists relationship!",t.getMarkId(),t.getApiId());
          //throw new GatewayMgrException(ResultCode.APP_NAME_EXIST);
      }else {
        int result = gatewayapimarkMapper.insert( t );
        if (result <= 0){
           logger.info("insert api_mark  update nums is 0!",t.getMarkId(),t.getApiId());
            //throw DaoException.DB_INSERT_RESULT_0;
        }
      }
    }

    @Override
    public void batchInsert(List<GatewayApiMarkDO> list) throws Exception {
      
    }

    @Override
    public void delete(Long id) throws Exception {
      
    }

    @Override
    public void update(GatewayApiMarkDO t) throws Exception {
      
    }

    @Override
    public int deleteDB(GatewayApiMarkDO t) {
      int i = 0;
      try {
        Map< String, Object > map = Maps.newHashMap();
        map.put("markId", t.getMarkId());
        map.put("apiId", t.getApiId());
        if (gatewayapimarkMapper.selectCount(map) <= 0)
        {
          logger.info("apiId【{}】and markId-【{}】- record not found!",t.getMarkId(),t.getApiId());
            //throw new GatewayMgrException(ResultCode.MARK_API_RELATIION_EXIST);
        }else {
          i = gatewayapimarkMapper.deleteDB(t);
          if(i <= 0) {
            logger.info("delete api_mark  update nums is 0!",t.getMarkId(),t.getApiId());
            //throw DaoException.DB_INSERT_RESULT_0;
          }
        }
      }catch(Exception e ) {
        logger.info("delete api_mark has some error: {}",e);
      }
      return i;
    }

    @Override
    public List<GatewayMarkDO> selectMarks(Long apiId) {
      return gatewayapimarkMapper.selectMarks(apiId);
    }
    
 
}




