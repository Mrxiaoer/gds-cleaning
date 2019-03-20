package com.cloud.gds.cleaning.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分析线程池
 *
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2019-01-22
 */
@Configuration
public class AnalysisThreadFactory {

	@Bean("analysisThreadPool")
	public ExecutorService createSingleThreadPool() {
		// public SimpleAsyncTaskExecutor createSingleThreadPool() {
		ThreadFactory analysisThreadFactory = new ThreadFactoryBuilder().setNameFormat("analysis-pool-%d").build();

		// return new SimpleAsyncTaskExecutor(analysisThreadFactory);
		return new ThreadPoolExecutor(16, 16, 100L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(Integer.MAX_VALUE),
			analysisThreadFactory, new ThreadPoolExecutor.AbortPolicy());

	}

}
