package com.kaistart.init;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * 升级servlet 代替web.xml文件中的配置
 * @author chenhailong
 *
 */
public class Init implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("init AebApplication");
		//配置文件
	    servletContext.setInitParameter("contextConfigLocation","classpath*:/spring/spring.xml");
	    //日志文件
	    servletContext.setInitParameter("log4jConfigLocation", "classpath:log4j.xml");
		
	    //编码过滤器CharacterEncodingFilter
	    CharacterEncodingFilter filter = new CharacterEncodingFilter();
	    filter.setEncoding("UTF-8");
	    Dynamic filterRegistration  = servletContext.addFilter("SpringCharacterEncodingFilter",filter);
	    filterRegistration.setAsyncSupported(true);
	    filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
	    
	    
	    //创建DispatcherServlet
	    DispatcherServlet disServlet = new DispatcherServlet();
	    disServlet.setContextConfigLocation("classpath*:/spring/dispatcher.xml");
	    ServletRegistration.Dynamic ds = servletContext.addServlet("spring", disServlet);
	    ds.setAsyncSupported(true);  
	    ds.setLoadOnStartup(1);
	    ds.addMapping("/"); //这里要排除静态资源的映射，在web.xml中配置
	    
		//增加异步请求监听
	    servletContext.addListener(new ContextLoaderListener());
	}

}
