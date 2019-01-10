package com.cloud.gds.cleaning.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分析结果
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dataclean_analysis_result")
public class AnalysisResult extends Model<AnalysisResult> {

	private static final long serialVersionUID = 4674961419049560149L;
	/**
	 * 主表id
	 */
	private Long fieldId;
	/**
	 * 基础数据id
	 */
	private Long baseId;
	/**
	 * 对比数据id
	 */
	private Long compareId;
	/**
	 * 相似度
	 */
	private Double similarity;
	/**
	 * 是否人工操作(0、自动 1、人工)
	 */
	private Integer isManual;

	/**
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.fieldId + "_" + this.baseId + "_" + this.compareId;
	}

}
