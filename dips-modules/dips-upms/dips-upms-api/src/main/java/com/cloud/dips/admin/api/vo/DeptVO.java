package com.cloud.dips.admin.api.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableLogic;

import lombok.Data;

/**
 * @author Dingbin
 *
 */
@Data
public class DeptVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 部门ID
	 */
	private Integer id;
	/**
	 * 部门名称
	 */
	@NotBlank(message = "部门名称不能为空")
	private String name;
	/**
	 * 部门编码
	 */
	@NotBlank(message = "部门编码不能为空")
	private String number;
	/**
	 * 排序
	 */
	private Integer orderNum;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime modifiedTime;
	/**
	 * 是否删除  -1：已删除  0：正常
	 */
	@TableLogic
	private String isDeleted;

	/**
	 * 父级ID
	 */
	private Integer parentId;
	
	/**
	 * 所属城市ID
	 */
	private Integer cityId;
	
	/**
	 * 所属城市名称
	 */
	private String cityName;

}
