package  com.kaistart.gateway.mgr.service.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaistart.gateway.api.service.GatewayAppAuthService;
import com.kaistart.gateway.common.tool.ZkService;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayAppAuthDO;
import com.kaistart.gateway.dto.RequestAuthorize;
import com.kaistart.gateway.exception.DaoException;
import com.kaistart.gateway.mgr.mapper.CommonMapper;
import com.kaistart.gateway.mgr.mapper.GatewayApiMapper;
import com.kaistart.gateway.mgr.mapper.GatewayAppAuthMapper;
/**
 * 
 * APP鉴权表ServiceImpl
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



@Service("gatewayAppAuthService")
public class GatewayAppAuthServiceImpl extends AbstractCommonServiceImpl<GatewayAppAuthDO> implements GatewayAppAuthService {
  private static final Logger logger = LoggerFactory.getLogger(GatewayAppAuthServiceImpl.class);
    @Resource
    private GatewayAppAuthMapper gatewayappauthMapper;

    @Resource
    private GatewayApiMapper gatewayApiMapper;
    
    @Autowired
    private ZkService  zkService;
    
    
    @Override
    protected CommonMapper<GatewayAppAuthDO> getMapper() {
      return gatewayappauthMapper;
    }
    
    @Override
    public void delete(Long id) throws Exception {
      int result = gatewayappauthMapper.deleteByPrimaryKey(id);
      if (result <= 0){
          logger.info("update app_auth which id is {}, update nums is 0!",id);
          throw DaoException.DB_DELETE_RESULT_0;
      }
      // 全局通知
      zkService.updateAppAuth(id);
    }

    @Override
    public List<RequestAuthorize> selectPageAuth(Map<String, Object> map) throws Exception {
      String type = map.get("authIdType")==null?"1":map.get("authIdType").toString();
      return "1".equals(type)?gatewayappauthMapper.selectPageAuthGroup(map):gatewayappauthMapper.selectPageAuthApi(map);
    }

    @Override
    public List<RequestAuthorize> selectListAuth(Map<String, Object> map) throws Exception {
      String type = map.get("authIdType")==null?"1":map.get("authIdType").toString();
      return "1".equals(type)?gatewayappauthMapper.selectListAuthGroup(map):gatewayappauthMapper.selectListAuthApi(map);
    }

    @Override
    public Integer selectCountAuth(Map<String, Object> map) throws Exception {
      String type = map.get("authIdType")==null?"1":map.get("authIdType").toString();
      return "1".equals(type)?gatewayappauthMapper.selectCountGroup(map):gatewayappauthMapper.selectCountApi(map);
    }

    @Override
    public void grant(GatewayAppAuthDO gatewayAppAuth) throws DaoException,Exception {
      Map<String,Object> map = new HashMap<String,Object>(8);
      map.put("appId", gatewayAppAuth.getAppId());
      map.put("authId", gatewayAppAuth.getAuthId());
      map.put("authIdType", gatewayAppAuth.getAuthIdType());
      
      String desc = gatewayAppAuth.getAuthIdType() ==1 ?"GROUP":"API";
      List<GatewayAppAuthDO> list = gatewayappauthMapper.selectList(map);
      if(list.size()>0){
        logger.info("the APP id={} has been granted to the "+desc+"id is{}!",gatewayAppAuth.getAppId(),gatewayAppAuth.getAuthId());
        throw new DaoException(500,"该"+desc +"已经授权给APP,请勿重复授权!");
      }
      int result = gatewayappauthMapper.insert(gatewayAppAuth);
      if (result <= 0){
          logger.info("update appid which id is {}, update nums is 0!",gatewayAppAuth.getAppId());
          throw DaoException.DB_DELETE_RESULT_0;
      }
      // 全局通知
      zkService.updateAppAuth(gatewayAppAuth.getId());
      
      
    }

    @Override
    public void ungrant(GatewayAppAuthDO gatewayAppAuth) throws DaoException,Exception {
      int type = gatewayAppAuth.getAuthIdType();
      
      Map<String,Object> map = new HashMap<String,Object>(8);
      map.put("appId", gatewayAppAuth.getAppId());
      map.put("authId", gatewayAppAuth.getAuthId());
      map.put("authIdType", type);
      List<GatewayAppAuthDO> list = gatewayappauthMapper.selectList(map);
      if(list.size()<=0){
        logger.info("not founded the "+(type==1?"GROUP":"API")+" grant records!");
        throw new DaoException(500,"没有找到对应的"+(type==1?"GROUP":"API")+"授权记录!");
      }
      //API解除授权
      if(type==GatewayAppAuthDO.AUTH_TYPE_API){
        int result = gatewayappauthMapper.deleteByPrimaryKey(gatewayAppAuth.getId());
        if (result <= 0){
          logger.info("ungrant app which id is {}, update nums is 0!",gatewayAppAuth.getAppId());
          throw DaoException.DB_DELETE_RESULT_0;
        }
      }else{ //GROUP解除授权
        //查询组下面的所有API
        map = new HashMap<String,Object>(8); 
        map.put("groupId", gatewayAppAuth.getAuthId());
        List<GatewayApiDO> lsApi = gatewayApiMapper.selectList(map);
        for(GatewayApiDO api : lsApi){
          map = new HashMap<String,Object>(8);
          map.put("appId", gatewayAppAuth.getAppId());
          map.put("authId", gatewayAppAuth.getAuthId());
          map.put("authIdType", GatewayAppAuthDO.AUTH_TYPE_API);
          list = gatewayappauthMapper.selectList(map); 
          //如果组里的API已经单独授权，则无法解除该组
          if(list.size()>0){ 
            logger.info("interface named "+api.getName()+", Already authorized separately，please try again after ungrant!");
            throw new DaoException(500, "GROUP中的接口"+api.getName()+"已经单独授权，请解除后再试!");
          }
        }
        int result = gatewayappauthMapper.deleteByPrimaryKey(gatewayAppAuth.getId());
        if (result <= 0){
          logger.info("ungrant app which id is {}, update nums is 0!",gatewayAppAuth.getAppId());
          throw DaoException.DB_DELETE_RESULT_0;
        }
      }
      // 全局通知
      zkService.updateAppAuth(gatewayAppAuth.getId());
    }

    @Override
    public void insert(GatewayAppAuthDO t) throws Exception {
      int result = gatewayappauthMapper.insert( t );
      if (result <= 0){
          throw DaoException.DB_INSERT_RESULT_0;
      }
      // 全局通知
      zkService.updateAppAuth(t.getId());
      
    }

    @Override
    public void batchInsert(List<GatewayAppAuthDO> list) throws Exception {
      
    }

    @Override
    public void update(GatewayAppAuthDO t) throws Exception {
      int result = gatewayappauthMapper.updateByPrimaryKeySelective(t);
      if (result <= 0){
          logger.info("update app_auth which id is {}, update nums is 0!",t.getId());
          throw DaoException.DB_DELETE_RESULT_0;
      }
      // 全局通知
      zkService.updateAppAuth(t.getId());    
    }

//    @Override
//    public void deleteLogic(Long id) throws Exception {
//      
//    }
//
//    @Override
//    public GatewayAppAuthDO selectById(Long id) throws Exception {
//      return gatewayappauthMapper.selectByPrimaryKey(id);
//    }
//
//    @Override
//    public Integer selectCount(Map<String, Object> map) throws Exception {
//      return gatewayappauthMapper.selectCount(map);
//    }
//
//    @Override
//    public List<GatewayAppAuthDO> selectList(Map<String, Object> map) throws Exception {
//      return gatewayappauthMapper.selectAuthList(map);
//    }

    /**
     * 根据查询条件分页查询。
     *
     * @param map 查询条件映射
     * @throws Exception 数据库访问异常
     */
    
//    public PageResult< RequestAuthorize > selectPage( PageParam pageParam,String type ) throws Exception {
//        Map<String, Object> paramMap = pageParam.getParamMap();
//        Integer count = this.getMapper().selectCount(paramMap);
//        List<RequestAuthorize> list = new ArrayList<RequestAuthorize>() ;
//        if (count != null && count.intValue() > 0) {
//            int startRecord = (pageParam.getPageNum() - 1) * pageParam.getNumPerPage();
//            int endRecord = pageParam.getNumPerPage();
//            paramMap.put("skip", startRecord);
//            paramMap.put("size", endRecord);
//            list = type.equals("1")?gatewayappauthMapper.selectPageAuthGroup(map):gatewayappauthMapper.selectPageAuthApi(map);
//        }
//        return new PageResult<RequestAuthorize>(pageParam.getPageNum(), pageParam.getNumPerPage(), count.intValue(), list);
//    }
}




