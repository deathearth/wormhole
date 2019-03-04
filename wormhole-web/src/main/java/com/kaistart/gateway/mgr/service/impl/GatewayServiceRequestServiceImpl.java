package  com.kaistart.gateway.mgr.service.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaistart.gateway.api.service.GatewayServiceRequestService;
import com.kaistart.gateway.common.tool.ZkService;
import com.kaistart.gateway.domain.GatewayServiceRequestDO;
import com.kaistart.gateway.exception.DaoException;
import com.kaistart.gateway.mgr.mapper.CommonMapper;
import com.kaistart.gateway.mgr.mapper.GatewayServiceRequestMapper;
/**
 * 
 * API请求参数表ServiceImpl
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



@Service("gatewayServiceRequestService")
public class GatewayServiceRequestServiceImpl extends AbstractCommonServiceImpl<GatewayServiceRequestDO> implements GatewayServiceRequestService {

  private static final Logger logger = LoggerFactory.getLogger(GatewayServiceRequestServiceImpl.class);

    @Resource
    private GatewayServiceRequestMapper gatewayservicerequestMapper;
    
    @Autowired
    private ZkService  zkService;
    
    @Override
    protected CommonMapper<GatewayServiceRequestDO> getMapper() {
      return gatewayservicerequestMapper;
    }
    
    @Override
    public void delete(Long id) throws Exception {
      GatewayServiceRequestDO target = gatewayservicerequestMapper.selectByPrimaryKey(id);
      if(target == null) {
        return ;
      }
      int result = gatewayservicerequestMapper.deleteByPrimaryKey(id );
      if (result <= 0){
          logger.info("update ServiceRequest which id is {}, update nums is 0!",id);
          throw DaoException.DB_DELETE_RESULT_0;
      }
      
      
      //查询输入参数
      Map<String, Object> map = new HashMap<String, Object>(2);
      map.put("apiId", target.getApiId());
      //已经按照index升序排序
      List<GatewayServiceRequestDO> paramList = gatewayservicerequestMapper.selectParamsList(map); 
      if(paramList.size() > 0) {
        for(int i = 0;i < paramList.size();i++) {
          GatewayServiceRequestDO param = paramList.get(i);
          //如果不是按照升序索引对应,则需要修改
          if(param.getIndex() != i) { 
            param.setIndex(i);
            gatewayservicerequestMapper.updateByPrimaryKey(param);
          }
        }
      }
      
      // 全局通知
      zkService.updateApi(target.getApiId());
      
    }

    @Override
    public void insert(GatewayServiceRequestDO t) throws Exception {
      int result = gatewayservicerequestMapper.insertSelective(t);
      if (result <= 0){
          throw DaoException.DB_UPDATE_RESULT_0;
      }

      // 全局通知
      zkService.updateApi(t.getApiId());
    }

    @Override
    public void batchInsert(List<GatewayServiceRequestDO> list) throws Exception {
      
    }

    @Override
    public void update(GatewayServiceRequestDO t) throws Exception {
      int result = gatewayservicerequestMapper.updateByPrimaryKeySelective(t);
      if (result <= 0){
          logger.info("update ServiceRequest which id is {}, update nums is 0!",t.getId());
          throw DaoException.DB_DELETE_RESULT_0;
      }

      // 全局通知
      zkService.updateApi(t.getApiId());
      
    }

}




