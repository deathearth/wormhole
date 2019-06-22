package com.kaistart.init;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 容器启动时创建线程池
 * @author chenhailong
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

	private int coreThread = 100; // 核心线程数
	private int maxThread = 200; // 最大线程数
	private long liveTime = 30000L;// 超过核心线程数的超时时间设置
	private int queueSize = 500; // 队列长度

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ThreadPoolExecutor pool = new ThreadPoolExecutor(coreThread,maxThread,liveTime,TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(queueSize),new ThreadPoolExecutor.DiscardOldestPolicy());
		sce.getServletContext().setAttribute("pool", pool);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ThreadPoolExecutor pool = (ThreadPoolExecutor)sce.getServletContext().getAttribute("pool");
		pool.shutdown();
	}

}
