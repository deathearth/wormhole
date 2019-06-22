package com.kaistart.gateway.servlet;

import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.AsyncContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kaistart.gateway.common.exception.GatewayException;
import com.kaistart.gateway.config.RunConfig;
import com.kaistart.gateway.domain.response.BaseResult;
import com.kaistart.gateway.domain.response.ResultCode;
import com.kaistart.gateway.processor.Context;
import com.kaistart.gateway.processor.ProcessorChain;
import com.kaistart.init.AsyListener;

/**
 * @author wuyuan.lfk
 * @date 2019年1月21日 上午11:30:50
 * 
 * 必须在一个请求涉及的所有Servlet及Filter中都声明asyncSupported=true，该请求才会支持异步
 */
@WebServlet(urlPatterns = "/api", loadOnStartup = 1 , asyncSupported = true)
public class KaistartGatewayServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(KaistartGatewayServlet.class);

	private ProcessorChain processorChain;
	private RunConfig runConfig;

	private ThreadPoolExecutor pool;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// 第一种方式，实现Autowired功能可用
		// SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
		// config.getServletContext());

		// 第二种方式
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		processorChain = (ProcessorChain) context.getBean("processorChain");
		runConfig = (RunConfig) context.getBean("runConfig");
		pool = (ThreadPoolExecutor) context.getServletContext().getAttribute("pool");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) {
		Context context = new Context();
		
		/**
		 * TODO 
		 * 支持异步,按理在servlet或者filter中增加了asyncSupported=true属性，应该支持异步请求
		 * 但是这里无法成功，只能强制增加下面的设置
		*/
		request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
		
		if (request.isAsyncSupported()) {
			AsyncContext async = request.startAsync();
			async.setTimeout(30 * 1000l);
			async.addListener(new AsyListener());
			pool.execute(() -> {
				try {
					Object result = processorChain.run(request, context);
					response(response, result);
				} catch (GatewayException e) {
					error(e, response);
				} catch (Throwable e) {
					logger.error("eid=" + e.hashCode() + " " + e.getMessage(), e);
					error(response, ResultCode.SYSTEM_ERROR, e.getMessage());
				}finally {
					async.complete();
				}
			});
		}
	}

	@SuppressWarnings("rawtypes")
	private void error(HttpServletResponse response, ResultCode code, String msg) {
		BaseResult result = new BaseResult();
		result.setResultCode(code);
		// 测试环境返回详细异常信息
		if (!runConfig.isOnline() && msg != null) {
			result.setMessage(msg);
		}
		response(response, result);
	}

	@SuppressWarnings("rawtypes")
	private void error(GatewayException e, HttpServletResponse response) {
		BaseResult result = new BaseResult();
		result.setCode(e.getCode());
		result.setMessage(e.getMessage());
		response(response, result);
	}

	private void response(HttpServletResponse response, Object result) {
		if (result == null) {
		}
		try {
			response.setContentType("application/json;charset=UTF-8");
			PropertyFilter profilter = new PropertyFilter() {
				@Override
				public boolean apply(Object object, String name, Object value) {
					// 排除class属性
					String ext = "class";
					if (name.equalsIgnoreCase(ext)) {
						// false表示last字段将被排除在外
						return false;
					}
					return true;
				}
			};
			// 时间的默认处理格式
			JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
			response.getWriter().write(JSON.toJSONString(result, profilter, SerializerFeature.WriteDateUseDateFormat,
					SerializerFeature.WriteMapNullValue));
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
	}

}
