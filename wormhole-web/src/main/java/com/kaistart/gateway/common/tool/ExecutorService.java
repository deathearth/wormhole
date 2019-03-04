package com.kaistart.gateway.common.tool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 线程池自定义类
 * @author xxx
 * @date 2019年2月20日 下午5:28:24
 */
@Service
public class ExecutorService implements InitializingBean {
  private static final Logger logger = LoggerFactory.getLogger(ExecutorService.class);
  /**
   * 线程池
   */
  private ThreadPoolExecutor executor;
  /**
   * 核心线程数
   */
  private int coreThread = 4;
  /**
   * 最大线程数
   */
  private int maxThread = 16;
  /**
   * 存活时间
   */
  private long liveTime = 60;
  /**
   * 队列大小
   */
  private int queueSize = 2000;

  public void execute(Runnable task) {
    try{
      executor.execute(task);
    }catch(Throwable t){
      logger.error(t.getMessage(), t);
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    executor =
        new ThreadPoolExecutor(coreThread, maxThread, liveTime, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(queueSize), new ThreadPoolExecutor.DiscardPolicy());
    Runtime.getRuntime().addShutdownHook(new Thread(){
      @Override
      public void run(){
        try {
          logger.warn("shutdown common threadPoolExecutor");
          executor.shutdown();
        } catch (Throwable e) {
          logger.error(e.getMessage(), e);
        }
      }
    });
  }

  public int getCoreThread() {
    return coreThread;
  }

  public void setCoreThread(int coreThread) {
    this.coreThread = coreThread;
  }

  public int getMaxThread() {
    return maxThread;
  }

  public void setMaxThread(int maxThread) {
    this.maxThread = maxThread;
  }

  public long getLiveTime() {
    return liveTime;
  }

  public void setLiveTime(long liveTime) {
    this.liveTime = liveTime;
  }

  public int getQueueSize() {
    return queueSize;
  }

  public void setQueueSize(int queueSize) {
    this.queueSize = queueSize;
  }

}
