package com.cloud.gds.gmsanalyse.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 政策分析特征实体类
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("policy_analyse_feature")
public class PolicyAnalyseFeature extends Model<PolicyAnalyseFeature> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 政策分析表对应id
	 */
	private Long analyseId;
	/**
	 * 政策特征名称
	 */
	private String featureName;
	/**
	 * 政策特征出现的次数
	 */
	private Integer featureNum;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
