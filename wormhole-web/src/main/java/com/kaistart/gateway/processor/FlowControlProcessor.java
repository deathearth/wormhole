/*
 * Copyright 2016 kaistart.com All right reserved. This software is the confidential and proprietary
 * information of kaistart.com ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with kaistart.com.
 */
package com.kaistart.gateway.processor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.kaistart.gateway.common.exception.GatewayException;
import com.kaistart.gateway.domain.response.ResultCode;

/**
 * 负责流控降级
 * 
 * 结构 全局定义 qps 2000 单个接口 threadNum 5
 * 
 * 测验成功之后，将配置调整为动态配置。配合后台界面达到方便使用的目的。【资源入库持久化、界面可视化】
 * 
 * 本地测试100个线程，每个50次调用网关接口，期间没有时间间隔
 * 流量控制 20/s, 结果如下， 说明控制是有效的，速率稳定在20左右, 误差1-2个以内
 毫秒数据     | 时间   | 资源名 |pass通过数|拒绝数|成功数|异常数|平均响应时长
1546922651000|2019-01-08 12:44:11|gateway|21|79|21|0|29
1546922656000|2019-01-08 12:44:16|gateway|21|72|21|0|0
1546922657000|2019-01-08 12:44:17|gateway|20|77|20|0|0
 * 
 * 本地测试100个线程,每个50次调用网关接口，期间没有时间间隔
 * 流量控制在2000/s 结果如下，
 毫秒数据     | 时间   | 资源名 |pass通过数|拒绝数|成功数|异常数|平均响应时长
1546922307000|2019-01-08 12:38:27|gateway|113|0|113|0|0
1546922308000|2019-01-08 12:38:28|gateway|191|0|191|0|0
1546922309000|2019-01-08 12:38:29|gateway|120|0|120|0|0
 * 
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午5:46:57
 */
@Component("flowControlProcessor")
public class FlowControlProcessor implements Processor {

  /**
   * 全局流控resource
   */
  private static final String GLOBAL_CONTROL = "gateway";

  /**
   * api流控规则集合
   */
  private static List<FlowRule> Rules = new ArrayList<>();

//  /**
//   * api熔断规则集合
//   */
//  private static List<DegradeRule> Degraderules = new ArrayList<>();
//
//  @Autowired
//  private CacheService cacheService;
  /**
   * 启动初始化全局qps控制
   */
  static {
    initFlowRules();
  }

  @Override
  public Object run(HttpServletRequest request, Context context) throws Exception {
//    String apiName = request.getParameter(GatewayConstants.HTTP_PARAM_API_NAME);    
//    addNewFlowRules(apiName); // 线程控制
//    addDegradeRule(apiName);  // 熔断控制 
//    Entry apiLimit = null;
    Entry golbal = null;
    
    try {
      golbal = SphU.entry(GLOBAL_CONTROL);
      // 资源中的逻辑.
    } catch (BlockException e1) { 
      //如果超限,则返回‘系统异常，请稍后再试’
      throw new GatewayException(ResultCode.SYSTEM_FLOW_ERROR);
    } finally {
//      if (apiLimit != null) {
//        apiLimit.exit();
//      }
      if (golbal != null) {
        golbal.exit();
      }
    }
    return null;
  }


  /**，
   * 初始化全局的QPS控制 2000 qps设置
   */
  private static void initFlowRules() {
    FlowRule rule = new FlowRule();
    rule.setResource(GLOBAL_CONTROL);
    rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
    rule.setCount(2000);
    Rules.add(rule);
    FlowRuleManager.loadRules(Rules);
  }

//  /**
//   * 逐步增加api的限流规则
//   * 
//   * @param apiname
//   */
//  private void addNewFlowRules(String apiname) {
//    /**
//     * 如果已经配置了规则,不在创建，要防止并发,创建重复规则
//     */
//    String limitName = "GATEWAY_LIMIT_FLOW_"+apiname;
//    if(!cacheService.exists(limitName)) {
//      if (!FlowRuleManager.hasConfig(apiname)) {
//        FlowRule rule = new FlowRule();
//        rule.setResource(apiname);
//        // 根据线程数进行流控
//        rule.setGrade(RuleConstant.FLOW_GRADE_THREAD); 
//        rule.setCount(3);
//        Rules.add(rule);
//        FlowRuleManager.loadRules(Rules);
//      }
//      cacheService.lock(limitName, 3600);
//    }
//  }
  
//  private static void initFlowRuleTest() {
//    List<FlowRule> rules = new ArrayList<FlowRule>();
//    FlowRule rule1 = new FlowRule();
//    rule1.setResource("bbc.bbc");
//    rule1.setCount(3);
//    rule1.setGrade(RuleConstant.FLOW_GRADE_THREAD);
//    rule1.setLimitApp("default");
//    rules.add(rule1);
//    FlowRuleManager.loadRules(rules);
//}

//  /**
//   * 逐步增加API的熔断规则
//   * 
//   * 待进行有效测试....
//   */
//  private void addDegradeRule(String apiname) {
//    if (!DegradeRuleManager.hasConfig(apiname)) {
//      DegradeRule rule = new DegradeRule();
//      rule.setResource(apiname);
//
//      // 异常降级仅针对业务异常,对sentinel本身异常不做统计,
//      // 10% 异常比率的阈值范围是 [0.0, 1.0]，代表 0% - 100%
//      // 根据异常比例进行熔断
//      rule.setCount(0.1); 
//      rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO); 
//
//      // rule.setCount(10); //当资源近 1 分钟的异常数目超过阈值之后会进行熔断。
//      // rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT); //根据异常数进行熔断
//
//      // rule.setCount(10); //10 ms
//      // rule.setGrade(RuleConstant.DEGRADE_GRADE_RT); //根据响应时间来处理
//      // 降级时间
//      rule.setTimeWindow(10); 
//      Degraderules.add(rule);
//      DegradeRuleManager.loadRules(Degraderules);
//    }
//  }

@Override
  public String getProcessorName() {
    return "flowControl";
  }

}
