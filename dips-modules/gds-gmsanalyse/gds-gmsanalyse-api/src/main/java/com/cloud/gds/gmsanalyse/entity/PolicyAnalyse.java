package com.cloud.gds.gmsanalyse.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 政策分析表
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("policy_analyse")
public class PolicyAnalyse extends Model<PolicyAnalyse> implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private Long id;
	/**
	 * 分析名称
	 */
	private String analyseName;
	/**
	 * 分析结果慨要
	 */
	private String analyseSummary;
	/**
	 * 特征数
	 */
	private Integer featureNum;
	/**
	 * 分析政策数
	 */
	private Integer policyNum;
	/**
	 * 分析状态（1.正在分析 2.完成快速分析 3.分析出错）
	 */
	private Integer analyseState;
	/**
	 * 创建用户
	 */
	@JsonIgnore
	private Integer createUser;
	/**
	 * 更新用户
	 */
	@JsonIgnore
	private Integer modifiedUser;
	/**
	 * 创建时间
	 */
	@JsonIgnore
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	@JsonIgnore
	private LocalDateTime modifiedTime;
	/**
	 * 更新时间
	 */
	@JsonIgnore
	private LocalDateTime isDeleted;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


}
