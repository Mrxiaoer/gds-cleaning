package com.cloud.gds.cleaning.api.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 清洗数据树
 *
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2018-12-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataFieldValueTree extends TreeNode {

    String name;

}
