package com.cloud.gds.cleaning.api.vo;

import com.baomidou.mybatisplus.plugins.Page;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

/**
 * 分页查询传入参数
 *
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2018-12-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageParam<T> extends Page<T> {

	private List<String> eq;
	private List<String> like;

}
