package  com.kaistart.gateway.mgr.service.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kaistart.gateway.api.service.GatewayMarkService;
import com.kaistart.gateway.domain.GatewayApiMarkDO;
import com.kaistart.gateway.domain.GatewayMarkDO;
import com.kaistart.gateway.exception.DaoException;
import com.kaistart.gateway.exception.GatewayMgrException;
import com.kaistart.gateway.exception.ResultCode;
import com.kaistart.gateway.mgr.mapper.CommonMapper;
import com.kaistart.gateway.mgr.mapper.GatewayApiMarkMapper;
import com.kaistart.gateway.mgr.mapper.GatewayMarkMapper;
import com.kaistart.gateway.tool.DateUtil;
/**
 * 
 * 网关接口标签表ServiceImpl
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



@Service("gatewayMarkService")
public class GatewayMarkServiceImpl extends AbstractCommonServiceImpl<GatewayMarkDO> implements GatewayMarkService {
  
    private static final Logger logger = LoggerFactory.getLogger(GatewayMarkServiceImpl.class);

    @Resource
    private GatewayMarkMapper gatewaymarkMapper;
    
    @Resource
    private GatewayApiMarkMapper gatewayapimarkMapper;

    @Override
    protected CommonMapper<GatewayMarkDO> getMapper() {
        return gatewaymarkMapper;
    }

    @Override
    public void insert(GatewayMarkDO t) throws Exception {
      Map<String,Object> map = new HashMap<String,Object>(8);
      map.put("name", t.getName());
      map.put("status", GatewayMarkDO.STATUS_1);
      //这里要做重名验证
      List<GatewayMarkDO> list = gatewaymarkMapper.selectList(map); 
      if(list.size() > 0) {
        logger.info("mark name【{}】 already exists ",t.getName());
        throw new GatewayMgrException(ResultCode.GATEWAY_MARK_HAS_EXIST);
      }
      t.setCdt(DateUtil.getFormatTime());
      t.setUdt(DateUtil.getFormatTime());
      t.setVersion(0);
      int i = gatewaymarkMapper.insert(t);
      if(i <= 0) {
        logger.info("insert mark which id is {}, update nums is 0!",t.getId());
        throw DaoException.DB_INSERT_RESULT_0;
      }
      GatewayMarkDO mark = gatewaymarkMapper.selectByPrimaryKey(t.getId());
      if(mark != null) {
        if(mark.getLevel() == GatewayMarkDO.LEVEL_1) {
          mark.setRootId(t.getId());
        }else if(mark.getLevel() == GatewayMarkDO.LEVEL_2) {
          mark.setBranchId(t.getId());
        }else if(mark.getLevel() == GatewayMarkDO.LEVEL_3) {
          mark.setLeafId(t.getId());
        }
        mark.setUdt(DateUtil.getFormatTime());
        gatewaymarkMapper.updateByPrimaryKeySelective(mark);
      }
    }

    @Override
    public void batchInsert(List<GatewayMarkDO> list) throws Exception {
      
    }

    @Override
    public void delete(Long id) throws Exception {
      
      Map<String,Object> map = new HashMap<String,Object>(1);
      map.put("markId", id);
      List<GatewayApiMarkDO> relation = gatewayapimarkMapper.selectList(map);
      if(relation.size() > 0) {
        logger.info("do not delete this mark {} ,because it has binded some api !",id);
        throw new GatewayMgrException(ResultCode.MARK_API_RELATIION_EXIST);
      }
      
      GatewayMarkDO mark = gatewaymarkMapper.selectByPrimaryKey(id);
      if(mark == null) {
        throw new GatewayMgrException(ResultCode.GATEWAY_MARK_NOT_EXIST);
      }
      mark.setStatus(GatewayMarkDO.STATUS_0);
      int i = gatewaymarkMapper.updateByPrimaryKeySelective(mark);
      if(i <= 0) {
        logger.info("update mark which id is {}, update nums is 0!",id);
        throw DaoException.DB_DELETE_RESULT_0;
      }
    }

    @Override
    public void update(GatewayMarkDO t) throws Exception {
      Map<String,Object> map = new HashMap<String,Object>(2);
      map.put("name", t.getName());
      map.put("status", GatewayMarkDO.STATUS_1);
      List<GatewayMarkDO> list = gatewaymarkMapper.selectList(map); 
      if(list.size() > 0) {
          //取第一条记录,判断id是否和当前对象一致,如果一致则可以修改
          GatewayMarkDO mark= list.get(0); 
          if(mark.getId() != t.getId()) {
            logger.info("mark name【{}】 already exists ",t.getName());
            throw new GatewayMgrException(ResultCode.GATEWAY_MARK_HAS_EXIST);
          }
      }
      t.setUdt(DateUtil.getFormatTime());
      int i = gatewaymarkMapper.insert(t);
      if(i <= 0) {
        throw DaoException.DB_INSERT_RESULT_0;
      }
      
    }

    @Override
    public List<Map<String, Object>> selectCheckPage(Map<String, Object> map) {
      return gatewaymarkMapper.selectCheckPage(map);
    }

    @Override
    public int selectCheckCount(Map<String, Object> map) {
      return gatewaymarkMapper.selectCheckCount(map);
    }
    
 
}




