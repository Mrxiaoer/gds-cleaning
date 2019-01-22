package com.cloud.gds.cleaning.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;

/**
 * 分析线程池
 *
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2019-01-22
 */
public class AnalysisThreadFactory {

	@Bean("analysisThreadPool")
	public ExecutorService createSingleThreadPool() {
		ThreadFactory analysisThreadFactory = new ThreadFactoryBuilder().setNameFormat("analysis-pool-%d").build();

		return new ThreadPoolExecutor(1, 8, 500L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(1024), analysisThreadFactory, new ThreadPoolExecutor.AbortPolicy());
	}

}
