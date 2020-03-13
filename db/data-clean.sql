/*
 Navicat Premium Data Transfer

 Source Server         : 创新13308
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : 115.233.227.46:13308
 Source Schema         : data-clean

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 13/03/2020 14:45:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base
-- ----------------------------
DROP TABLE IF EXISTS `base`;
CREATE TABLE `base` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '修改用户',
  `modified_user` bigint(20) NOT NULL COMMENT '更新用户',
  `is_deleted` tinyint(1) unsigned zerofill NOT NULL COMMENT '是否删除（0，未删除；1，已删除）',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dataclean_analysis_result
-- ----------------------------
DROP TABLE IF EXISTS `dataclean_analysis_result`;
CREATE TABLE `dataclean_analysis_result` (
  `field_id` bigint(20) NOT NULL DEFAULT '0',
  `base_id` bigint(20) NOT NULL DEFAULT '0',
  `compare_id` bigint(20) NOT NULL DEFAULT '0',
  `similarity` decimal(5,4) NOT NULL DEFAULT '0.0000',
  `is_manual` tinyint(1) DEFAULT '0' COMMENT '操作方式(1、自动 2、人工)',
  PRIMARY KEY (`field_id`,`base_id`,`compare_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dataclean_attribute_match
-- ----------------------------
DROP TABLE IF EXISTS `dataclean_attribute_match`;
CREATE TABLE `dataclean_attribute_match` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '数据id（关联data_wait_clean表）',
  `attribute_key` varchar(255) NOT NULL COMMENT '属性key',
  `attribute_value` varchar(255) NOT NULL COMMENT '属性value',
  `parent_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父id（本表）',
  `similarity` decimal(5,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '相似度',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '创建用户',
  `modified_user` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '更新用户',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0，未删除；1，已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性';

-- ----------------------------
-- Table structure for dataclean_data_field
-- ----------------------------
DROP TABLE IF EXISTS `dataclean_data_field`;
CREATE TABLE `dataclean_data_field` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL,
  `rule_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '规则id',
  `dept_id` bigint(20) NOT NULL COMMENT '数据来源部门',
  `dept_name` varchar(255) NOT NULL DEFAULT '' COMMENT '来源部门名称',
  `method_id` tinyint(1) NOT NULL DEFAULT '1' COMMENT '来源方式(1.API导入 2.手动添加 3.多池合一)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户',
  `modified_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0.未删除；1.已删除）',
  `analyse_state` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '分析状态（0.未分析 1.正在分析 2.完成快速分析 3.分析出错4.完成深度分析）',
  `matrix_file` varchar(255) NOT NULL DEFAULT '' COMMENT '矩阵文件名称',
  `threshold` decimal(5,4) NOT NULL DEFAULT '0.0000' COMMENT '阀值',
  `need_reanalysis` tinyint(1) unsigned DEFAULT '1' COMMENT '是否需要重新分析',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 COMMENT='清洗池表';

-- ----------------------------
-- Table structure for dataclean_data_field_value
-- ----------------------------
DROP TABLE IF EXISTS `dataclean_data_field_value`;
CREATE TABLE `dataclean_data_field_value` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `field_id` bigint(20) unsigned NOT NULL COMMENT '与filed关联的id',
  `field_value` text NOT NULL COMMENT '导入数据的字段内容（json格式）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '创建用户',
  `modified_user` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '更新用户',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0，未删除；1，已删除）',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '分析状态(0、未分析 1、已分析）',
  `be_cleaned_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '被某条数据清洗掉的id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`) USING BTREE,
  KEY `idx_field_key_id` (`field_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1017469 DEFAULT CHARSET=utf8 COMMENT='数据池表';

-- ----------------------------
-- Table structure for dataclean_data_rule
-- ----------------------------
DROP TABLE IF EXISTS `dataclean_data_rule`;
CREATE TABLE `dataclean_data_rule` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '规则名称',
  `params` text COMMENT '字段集合',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '创建用户',
  `modified_user` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '更新用户',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0.未删除1.已删除）',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态（0，未清洗；1，已清洗）',
  `dept_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '来源部门',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='规则表';

SET FOREIGN_KEY_CHECKS = 1;
