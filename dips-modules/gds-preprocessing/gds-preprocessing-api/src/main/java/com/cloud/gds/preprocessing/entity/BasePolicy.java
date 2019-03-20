package com.cloud.gds.preprocessing.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;


import java.io.Serializable;

/**
 * 基础policy实体类
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-19
 */
@Data
public class BasePolicy extends Model<BasePolicy> {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 标题
	 */
	private String title;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
