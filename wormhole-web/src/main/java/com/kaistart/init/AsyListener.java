package com.kaistart.init;

import java.io.IOException;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.annotation.WebListener;

/**
 * 用来监听异步操作不同事件的后续处理，这里在暂不处理
 * @author chenhailong
 */
@WebListener
public class AsyListener implements AsyncListener {

	@Override
	public void onComplete(AsyncEvent event) throws IOException {

	}

	@Override
	public void onTimeout(AsyncEvent event) throws IOException {

	}

	@Override
	public void onError(AsyncEvent event) throws IOException {

	}

	@Override
	public void onStartAsync(AsyncEvent event) throws IOException {

	}

}
