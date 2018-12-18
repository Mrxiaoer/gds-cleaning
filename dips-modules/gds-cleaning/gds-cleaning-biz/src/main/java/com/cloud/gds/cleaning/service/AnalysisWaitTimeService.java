package com.cloud.gds.cleaning.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.gds.cleaning.api.entity.DataField;

/**
 * 分析等待时间接口
 *
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2018-12-12
 */
public interface AnalysisWaitTimeService extends IService<DataField> {

    /**
     * 进行分析，等待开始
     *
     * @param dimension 维度（字段数）
     * @param number    数据数量
     * @return [预计等待时间（ms）,下次询问时间(ms)]
     */
    double[] analysisWaitStart(Integer dimension, Integer number);

    /**
     * 判断是否仍需等待
     *
     * @param id
     * @param lastTime 剩下预计等待时间
     * @return 下次询问时间(ms)
     */
    double isNeedWait(Long id, double lastTime);

}
