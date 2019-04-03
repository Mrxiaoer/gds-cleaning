package com.cloud.gds.gmsanalyse.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
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
	/**
	 * id
	 */
	private Long id;
	/**
	 * 分析名称
	 */
	private String analyseName;
	/**
	 * 分析政策的id集
	 */
	private String originalList;
	/**
	 * 分析结果集
	 */
	private String analyseResult;
	/**
	 * 特征数
	 */
	private Integer featureNum;
	/**
	 * 创建用户
	 */
	private Long createUser;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新用户
	 */
	private Long modifiedUser;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
