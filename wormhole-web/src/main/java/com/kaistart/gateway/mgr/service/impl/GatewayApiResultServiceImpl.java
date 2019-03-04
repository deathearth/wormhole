package  com.kaistart.gateway.mgr.service.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaistart.gateway.api.service.GatewayApiResultService;
import com.kaistart.gateway.domain.GatewayApiResultDO;
import com.kaistart.gateway.exception.DaoException;
import com.kaistart.gateway.mgr.mapper.CommonMapper;
import com.kaistart.gateway.mgr.mapper.GatewayApiResultMapper;
/**
 * 
 * API结果表ServiceImpl
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



@Service("gatewayApiResultService")
public class GatewayApiResultServiceImpl extends AbstractCommonServiceImpl<GatewayApiResultDO> implements GatewayApiResultService {

//  private static final Logger logger = LoggerFactory.getLogger(GatewayApiResultServiceImpl.class);

    @Resource
    private GatewayApiResultMapper gatewayapiresultMapper;

    @Override
    protected CommonMapper<GatewayApiResultDO> getMapper() {
      return gatewayapiresultMapper;
    }
    
    @Override
    public void delete(Long id) throws Exception {
      int result = gatewayapiresultMapper.deleteByPrimaryKey(id);
      if (result <= 0){
          throw DaoException.DB_DELETE_RESULT_0;
      }
    }
    
    @Override
    public void update( GatewayApiResultDO bean ) throws Exception {
      Map<String,Object> map = new HashMap<String,Object>(1);
      map.put("apiId", bean.getApiId());
      List<GatewayApiResultDO> list = gatewayapiresultMapper.selectList(map);
      if(list.size()<1){
        int result = gatewayapiresultMapper.insert( bean );
        if (result <= 0){
            throw DaoException.DB_INSERT_RESULT_0;
        }
      }else{
        bean.setId(list.get(0).getId());
        int result = gatewayapiresultMapper.updateByPrimaryKeySelective(bean);
        if (result <= 0){
            throw DaoException.DB_INSERT_RESULT_0;
        }
      }
  }


    @Override
    public void insert(GatewayApiResultDO t) throws Exception {
      int result = gatewayapiresultMapper.insert( t );
      if (result <= 0){
          throw DaoException.DB_INSERT_RESULT_0;
      }
      
    }


    @Override
    public void batchInsert(List<GatewayApiResultDO> list) throws Exception {
      
    }


//    @Override
//    public void deleteLogic(Long id) throws Exception {
//      
//    }


//    @Override
//    public GatewayApiResultDO selectById(Long id) throws Exception {
//      return gatewayapiresultMapper.selectByPrimaryKey(id);
//    }
//
//
//    @Override
//    public Integer selectCount(Map<String, Object> map) throws Exception {
//      return gatewayapiresultMapper.selectCount(map);
//    }
//
//
//    @Override
//    public List<GatewayApiResultDO> selectList(Map<String, Object> map) throws Exception {
//      return gatewayapiresultMapper.selectList(map);
//    }


    
    
}




