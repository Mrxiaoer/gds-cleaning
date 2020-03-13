/*
 Navicat Premium Data Transfer

 Source Server         : 创新13308
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : 115.233.227.46:13308
 Source Schema         : dips_cloud_core

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 13/03/2020 14:45:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gov_attachment
-- ----------------------------
DROP TABLE IF EXISTS `gov_attachment`;
CREATE TABLE `gov_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '上传人',
  `url` varchar(150) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '文件名',
  `file_size` bigint(20) NOT NULL DEFAULT '0' COMMENT '文件长度',
  `ip` varchar(100) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT 'IP',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='附件表';

-- ----------------------------
-- Records of gov_attachment
-- ----------------------------
BEGIN;
INSERT INTO `gov_attachment` VALUES (1, 1, '//dips-cloud-gov.oss-cn-hangzhou.aliyuncs.com/upload/images/201811/cecf97a6-920a-467d-93f8-9d345cf4e562.jpg', 26040, '192.168.12.198', '2018-11-20 16:29:47');
INSERT INTO `gov_attachment` VALUES (2, 1, '//dips-cloud-gov.oss-cn-hangzhou.aliyuncs.com/upload/images/201811/82732aca-9de1-4f68-87ac-4ac49b15159e.jpg', 44325, '192.168.12.198', '2018-11-21 09:23:10');
INSERT INTO `gov_attachment` VALUES (3, 2, '//dips-cloud-gov.oss-cn-hangzhou.aliyuncs.com/upload/images/201811/ec35dc33-1248-4f5a-90e0-bd1bb346ce35.jpg', 26040, '192.168.12.198', '2018-11-21 09:35:59');
INSERT INTO `gov_attachment` VALUES (4, 1, '//dips-cloud-gov.oss-cn-hangzhou.aliyuncs.com/upload/images/201903/080c83a8-64cd-4337-8e4c-7a475afd5ed1.jpg', 49097, '172.18.0.7', '2019-03-07 03:10:25');
INSERT INTO `gov_attachment` VALUES (5, 1, '//dips-cloud-gov.oss-cn-hangzhou.aliyuncs.com/upload/images/201903/76b36125-3f4f-43a9-8c2e-98e4729ecad2.jpg', 49097, '172.18.0.7', '2019-03-07 03:11:15');
INSERT INTO `gov_attachment` VALUES (6, 1, '//dips-cloud-gov.oss-cn-hangzhou.aliyuncs.com/upload/images/201904/d541a520-12f4-4415-aac1-58810a0df0c8.jpg', 569354, '172.18.0.3', '2019-04-24 07:18:14');
COMMIT;

-- ----------------------------
-- Table structure for gov_city
-- ----------------------------
DROP TABLE IF EXISTS `gov_city`;
CREATE TABLE `gov_city` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '城市ID',
  `name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '城市名称',
  `number` varchar(255) NOT NULL COMMENT '城市编号',
  `code` varchar(255) NOT NULL COMMENT '城市代码',
  `city_level` varchar(100) NOT NULL DEFAULT '0' COMMENT '城市级别',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` char(1) CHARACTER SET utf8 NOT NULL DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `parent_id` bigint(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='城市表';

-- ----------------------------
-- Records of gov_city
-- ----------------------------
BEGIN;
INSERT INTO `gov_city` VALUES (1, '北京', '110000', '110000', '5', 1, '2018-11-20 10:36:51', '2018-11-20 16:55:02', '0', 0);
COMMIT;

-- ----------------------------
-- Table structure for gov_city_relation
-- ----------------------------
DROP TABLE IF EXISTS `gov_city_relation`;
CREATE TABLE `gov_city_relation` (
  `ancestor` bigint(20) NOT NULL COMMENT '祖先节点',
  `descendant` bigint(20) NOT NULL COMMENT '后代节点',
  PRIMARY KEY (`ancestor`,`descendant`),
  KEY `idx1` (`ancestor`) USING BTREE,
  KEY `idx2` (`descendant`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='城市父子级关系表';

-- ----------------------------
-- Records of gov_city_relation
-- ----------------------------
BEGIN;
INSERT INTO `gov_city_relation` VALUES (1, 1);
COMMIT;

-- ----------------------------
-- Table structure for gov_dept
-- ----------------------------
DROP TABLE IF EXISTS `gov_dept`;
CREATE TABLE `gov_dept` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '部门名称',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `city_id` bigint(11) DEFAULT NULL COMMENT '所属城市ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
-- Records of gov_dept
-- ----------------------------
BEGIN;
INSERT INTO `gov_dept` VALUES (1, '国脉集团', 1, '2018-01-22 19:00:23', '2018-11-20 14:50:28', '0', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for gov_dept_relation
-- ----------------------------
DROP TABLE IF EXISTS `gov_dept_relation`;
CREATE TABLE `gov_dept_relation` (
  `ancestor` bigint(20) NOT NULL COMMENT '祖先节点',
  `descendant` bigint(20) NOT NULL COMMENT '后代节点',
  PRIMARY KEY (`ancestor`,`descendant`),
  KEY `idx1` (`ancestor`) USING BTREE,
  KEY `idx2` (`descendant`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='部门父子级关系表';

-- ----------------------------
-- Records of gov_dept_relation
-- ----------------------------
BEGIN;
INSERT INTO `gov_dept_relation` VALUES (1, 1);
COMMIT;

-- ----------------------------
-- Table structure for gov_dict
-- ----------------------------
DROP TABLE IF EXISTS `gov_dict`;
CREATE TABLE `gov_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `number` varchar(100) NOT NULL DEFAULT '' COMMENT '字典编码',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '字典名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `system` varchar(100) NOT NULL DEFAULT '' COMMENT '所属系统',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`number`) USING BTREE,
  KEY `sys_dict_label` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of gov_dict
-- ----------------------------
BEGIN;
INSERT INTO `gov_dict` VALUES (1, 'IS_NORMAL', '是否正常', '2018-11-20 13:56:55', '2018-11-20 13:56:55', 'DIPS');
INSERT INTO `gov_dict` VALUES (2, 'IS_NOTT', '是否', '2018-11-20 14:39:21', '2018-11-20 14:39:21', 'DIPS');
INSERT INTO `gov_dict` VALUES (3, 'CITYLEVEL', '城市级别', '2018-11-20 14:44:52', '2018-11-20 14:44:52', 'DIPS');
INSERT INTO `gov_dict` VALUES (4, 'SOCIAL_TYPE', '社交类型', '2018-11-20 13:56:55', '2018-11-20 13:56:55', 'DIPS');
INSERT INTO `gov_dict` VALUES (5, 'GDS_METHOD_NAME', '清洗来源方式', '2019-01-11 13:58:45', '2019-01-11 13:58:50', 'DIPS');
INSERT INTO `gov_dict` VALUES (6, 'GDS_ANALYSE_STATE', '分析状态', '2019-01-11 13:59:01', '2019-01-11 13:59:01', 'DIPS');
COMMIT;

-- ----------------------------
-- Table structure for gov_dict_value
-- ----------------------------
DROP TABLE IF EXISTS `gov_dict_value`;
CREATE TABLE `gov_dict_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(100) NOT NULL DEFAULT '' COMMENT '字典键',
  `value` varchar(100) NOT NULL DEFAULT '' COMMENT '字典值',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `dict_id` bigint(20) NOT NULL COMMENT '所属字典id',
  `parent_id` bigint(20) NOT NULL COMMENT '父类id',
  `sort` int(8) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COMMENT='字段值域表';

-- ----------------------------
-- Records of gov_dict_value
-- ----------------------------
BEGIN;
INSERT INTO `gov_dict_value` VALUES (1, '0', '否', '2018-11-20 15:23:34', '2018-11-20 15:23:34', 2, 0, 0);
INSERT INTO `gov_dict_value` VALUES (2, '9', '是', '2018-11-20 15:23:36', '2018-11-20 15:23:36', 2, 0, 0);
INSERT INTO `gov_dict_value` VALUES (3, '0', '正常', '2018-11-20 15:33:31', '2018-11-20 15:33:31', 1, 0, 0);
INSERT INTO `gov_dict_value` VALUES (4, '9', '异常', '2018-11-20 15:33:33', '2018-11-20 15:33:33', 1, 0, 0);
INSERT INTO `gov_dict_value` VALUES (5, '1', '省级', '2018-11-20 15:23:42', '2018-11-20 15:23:42', 3, 0, 0);
INSERT INTO `gov_dict_value` VALUES (6, '2', '市级', '2018-11-20 15:23:43', '2018-11-20 15:23:43', 3, 0, 0);
INSERT INTO `gov_dict_value` VALUES (7, '3', '县级', '2018-11-20 15:23:44', '2018-11-20 15:23:44', 3, 0, 0);
INSERT INTO `gov_dict_value` VALUES (8, 'WX', '微信', '2018-11-20 15:39:02', '2018-11-20 15:39:02', 4, 0, 0);
INSERT INTO `gov_dict_value` VALUES (9, 'QQ', 'QQ', '2018-11-20 15:39:12', '2018-11-20 15:39:12', 4, 0, 0);
INSERT INTO `gov_dict_value` VALUES (10, '1', 'API导入', '2019-01-11 13:59:45', '2019-01-11 13:59:45', 5, 0, 1);
INSERT INTO `gov_dict_value` VALUES (11, '2', '手动添加', '2019-01-11 13:59:59', '2019-01-11 13:59:59', 5, 0, 2);
INSERT INTO `gov_dict_value` VALUES (12, '0', '未分析', '2019-01-11 14:00:18', '2019-01-11 14:00:18', 6, 0, 0);
INSERT INTO `gov_dict_value` VALUES (13, '1', '正在分析', '2019-01-11 14:00:26', '2019-01-11 14:00:26', 6, 0, 1);
INSERT INTO `gov_dict_value` VALUES (14, '2', '完成快速分析', '2019-01-11 14:00:37', '2019-01-11 14:00:37', 6, 0, 2);
INSERT INTO `gov_dict_value` VALUES (15, '3', '分析出错', '2019-01-11 14:00:49', '2019-01-11 14:00:49', 6, 0, 3);
INSERT INTO `gov_dict_value` VALUES (16, '4', '完成深度分析', '2019-01-11 14:00:59', '2019-01-11 14:00:59', 6, 0, 4);
COMMIT;

-- ----------------------------
-- Table structure for gov_element
-- ----------------------------
DROP TABLE IF EXISTS `gov_element`;
CREATE TABLE `gov_element` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '信息资源项ID',
  `ch_name` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '中文名称',
  `en_name` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '英文名称',
  `data_type` varchar(100) NOT NULL DEFAULT '' COMMENT '数据类型',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '字段描述',
  `data_length` varchar(255) NOT NULL DEFAULT '' COMMENT '数据长度',
  `default_value` varchar(255) NOT NULL DEFAULT '' COMMENT '默认值',
  `object_type` varchar(100) NOT NULL DEFAULT '' COMMENT '对象类型',
  `is_dict` varchar(100) NOT NULL DEFAULT '' COMMENT '是否字典项',
  `is_pk` varchar(100) NOT NULL DEFAULT '' COMMENT '是否主键',
  `is_null` varchar(100) NOT NULL DEFAULT '' COMMENT '是否为空',
  `res_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所在信息资源ID',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `sharing_type
sharing_type
sharing_type` varchar(100) NOT NULL DEFAULT '' COMMENT '共享类型',
  `sharing_cond` varchar(100) NOT NULL DEFAULT '' COMMENT '共享类型',
  `sharing_mode` varchar(100) NOT NULL DEFAULT '' COMMENT '共享类型',
  `is_open` varchar(100) NOT NULL DEFAULT '' COMMENT '是否向社会开放',
  `open_cond` varchar(255) NOT NULL DEFAULT '' COMMENT '开放条件',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `upd_cycle` varchar(100) NOT NULL DEFAULT '' COMMENT '更新周期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='信息项';

-- ----------------------------
-- Records of gov_element
-- ----------------------------
BEGIN;
INSERT INTO `gov_element` VALUES (1, '中文名', '英文名字', '1', '我就是描述', '1', '1', '1', '1', '1', '1', 1, '2018-11-23 05:46:12', '', '', '', '', '', '2018-11-23 05:46:12', '');
COMMIT;

-- ----------------------------
-- Table structure for gov_log
-- ----------------------------
DROP TABLE IF EXISTS `gov_log`;
CREATE TABLE `gov_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) NOT NULL DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '日志标题',
  `service_id` varchar(32) NOT NULL DEFAULT '' COMMENT '服务ID',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remote_addr` varchar(255) NOT NULL DEFAULT '' COMMENT '操作IP地址',
  `user_agent` varchar(1000) NOT NULL DEFAULT '' COMMENT '用户代理',
  `request_uri` varchar(255) NOT NULL DEFAULT '' COMMENT '请求URI',
  `method` varchar(10) NOT NULL DEFAULT '' COMMENT '操作方式',
  `params` text NOT NULL COMMENT '操作提交的数据',
  `time` mediumtext NOT NULL COMMENT '执行时间',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `exception` text NOT NULL COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`) USING BTREE,
  KEY `sys_log_request_uri` (`request_uri`) USING BTREE,
  KEY `sys_log_type` (`type`) USING BTREE,
  KEY `sys_log_create_date` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of gov_log
-- ----------------------------
BEGIN;
INSERT INTO `gov_log` VALUES (1, '0', '添加字典', 'dips', 'admin', '2018-11-07 16:39:12', '2018-11-07 16:39:12', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/dict', 'POST', '', '24', '0', '');
INSERT INTO `gov_log` VALUES (2, '0', '锁定或解锁用户', 'dips', 'admin', '2018-11-08 14:13:00', '2018-11-08 14:13:00', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/user/lock/2', 'PUT', '', '55', '0', '');
INSERT INTO `gov_log` VALUES (3, '0', '锁定或解锁用户', 'dips', 'admin', '2018-11-08 14:13:01', '2018-11-08 14:13:01', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/user/lock/2', 'PUT', '', '15', '0', '');
INSERT INTO `gov_log` VALUES (4, '0', '修改字典', 'dips', 'admin', '2018-11-08 14:36:52', '2018-11-08 14:36:52', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/dict', 'PUT', '', '8', '0', '');
INSERT INTO `gov_log` VALUES (5, '0', '锁定或解锁用户', 'dips', 'admin', '2018-11-08 14:46:33', '2018-11-08 14:46:33', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/user/lock/2', 'PUT', '', '36', '0', '');
INSERT INTO `gov_log` VALUES (6, '0', '锁定或解锁用户', 'dips', 'admin', '2018-11-08 14:47:02', '2018-11-08 14:47:02', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/user/lock/2', 'PUT', '', '7', '0', '');
INSERT INTO `gov_log` VALUES (7, '0', '锁定或解锁用户', 'dips', 'admin', '2018-11-08 14:47:03', '2018-11-08 14:47:03', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/user/lock/2', 'PUT', '', '9', '0', '');
INSERT INTO `gov_log` VALUES (8, '0', '锁定或解锁用户', 'dips', 'admin', '2018-11-08 15:15:13', '2018-11-08 15:15:13', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/user/lock/2', 'PUT', '', '32', '0', '');
INSERT INTO `gov_log` VALUES (9, '0', '锁定或解锁用户', 'dips', 'admin', '2018-11-08 15:15:15', '2018-11-08 15:15:15', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/user/lock/2', 'PUT', '', '7', '0', '');
INSERT INTO `gov_log` VALUES (10, '0', '添加字典', 'dips', 'admin', '2018-11-20 15:38:47', '2018-11-20 15:38:47', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/dict', 'POST', '', '83', '0', '');
INSERT INTO `gov_log` VALUES (11, '0', '添加字典值', 'dips', 'admin', '2018-11-20 15:39:03', '2018-11-20 15:39:03', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/dictValue', 'POST', '', '156', '0', '');
INSERT INTO `gov_log` VALUES (12, '0', '添加字典值', 'dips', 'admin', '2018-11-20 15:39:13', '2018-11-20 15:39:13', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/dictValue', 'POST', '', '695', '0', '');
INSERT INTO `gov_log` VALUES (13, '0', '更新字典', 'dips', 'admin', '2018-11-20 15:39:15', '2018-11-20 15:39:15', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/dict', 'PUT', '', '110', '0', '');
INSERT INTO `gov_log` VALUES (14, '0', '添加角色', 'dips', 'admin', '2018-11-20 16:31:31', '2018-11-20 16:31:31', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role', 'POST', '', '105', '0', '');
INSERT INTO `gov_log` VALUES (15, '0', '添加用户', 'dips', 'admin', '2018-11-20 16:33:25', '2018-11-20 16:33:25', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/user', 'POST', '', '543', '0', '');
INSERT INTO `gov_log` VALUES (16, '0', '更新角色菜单', 'dips', 'admin', '2018-11-20 16:33:55', '2018-11-20 16:33:55', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B2%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C35%2C36%2C37%2C%5D', '1158', '0', '');
INSERT INTO `gov_log` VALUES (17, '0', '修改角色', 'dips', 'admin', '2018-11-20 16:52:48', '2018-11-20 16:52:48', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role', 'PUT', '', '65', '0', '');
INSERT INTO `gov_log` VALUES (18, '0', '修改角色', 'dips', 'admin', '2018-11-20 16:53:06', '2018-11-20 16:53:06', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role', 'PUT', '', '77', '0', '');
INSERT INTO `gov_log` VALUES (19, '0', '修改角色', 'dips', 'admin', '2018-11-20 16:53:11', '2018-11-20 16:53:11', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role', 'PUT', '', '43', '0', '');
INSERT INTO `gov_log` VALUES (20, '0', '更新字典', 'dips', 'admin', '2018-11-20 16:57:09', '2018-11-20 16:57:09', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/dict', 'PUT', '', '65', '0', '');
INSERT INTO `gov_log` VALUES (21, '0', '更新角色菜单', 'dips', 'admin', '2018-11-21 09:14:26', '2018-11-21 09:14:26', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B1%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C35%2C36%2C45%2C46%2C47%2C37%2C48%2C49%2C50%2C51%2C52%2C53%2C%5D', '5407', '0', '');
INSERT INTO `gov_log` VALUES (22, '0', '更新角色菜单', 'dips', 'admin', '2018-11-21 09:19:26', '2018-11-21 09:19:26', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B1%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C36%2C45%2C46%2C47%2C35%5D', '1635', '0', '');
INSERT INTO `gov_log` VALUES (23, '0', '更新角色菜单', 'dips', 'admin', '2018-11-21 09:19:31', '2018-11-21 09:19:31', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B1%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C35%2C36%2C45%2C46%2C47%2C37%2C48%2C49%2C50%2C51%2C52%2C53%2C%5D', '1952', '0', '');
INSERT INTO `gov_log` VALUES (24, '0', '更新角色菜单', 'dips', 'admin', '2018-11-21 09:19:46', '2018-11-21 09:19:46', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B2%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C35%2C36%2C45%2C46%2C47%2C37%2C48%2C49%2C50%2C51%2C52%2C53%2C%5D', '1741', '0', '');
INSERT INTO `gov_log` VALUES (25, '0', '更新角色菜单', 'dips', 'admin', '2018-11-21 09:19:54', '2018-11-21 09:19:54', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B1%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C36%2C45%2C46%2C47%2C35%5D', '1900', '0', '');
INSERT INTO `gov_log` VALUES (26, '0', '更新角色菜单', 'dips', 'admin', '2018-11-21 09:19:59', '2018-11-21 09:19:59', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B2%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C36%2C45%2C46%2C47%2C35%5D', '1670', '0', '');
INSERT INTO `gov_log` VALUES (27, '0', '更新角色菜单', 'dips', 'admin', '2018-11-21 09:20:04', '2018-11-21 09:20:04', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B1%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C35%2C36%2C45%2C46%2C47%2C37%2C48%2C49%2C50%2C51%2C52%2C53%2C%5D', '2202', '0', '');
INSERT INTO `gov_log` VALUES (28, '0', '更新角色菜单', 'dips', 'admin', '2018-11-21 09:20:09', '2018-11-21 09:20:09', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B2%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C35%2C36%2C45%2C46%2C47%2C37%2C48%2C49%2C50%2C51%2C52%2C53%2C%5D', '1961', '0', '');
INSERT INTO `gov_log` VALUES (29, '0', '更新角色菜单', 'dips', 'admin', '2018-11-21 09:21:08', '2018-11-21 09:21:08', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B1%5D&menuIds=%5B11%2C12%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C35%2C36%2C45%2C46%2C47%2C37%2C48%2C49%2C50%2C51%2C52%2C53%2C1%2C2%5D', '1990', '0', '');
INSERT INTO `gov_log` VALUES (30, '0', '更新角色菜单', 'dips', 'admin', '2018-11-21 09:21:46', '2018-11-21 09:21:46', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B1%5D&menuIds=%5B11%2C12%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C35%2C36%2C45%2C46%2C47%2C37%2C48%2C49%2C50%2C51%2C52%2C53%2C1%2C2%5D', '2041', '0', '');
INSERT INTO `gov_log` VALUES (31, '0', '更新角色菜单', 'dips', 'admin', '2018-11-21 09:22:02', '2018-11-21 09:22:02', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B1%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C35%2C36%2C45%2C46%2C47%2C37%2C48%2C49%2C50%2C51%2C52%2C53%2C%5D', '1984', '0', '');
INSERT INTO `gov_log` VALUES (32, '0', '修改用户信息', 'dips', 'admin', '2018-11-21 09:23:12', '2018-11-21 09:23:12', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/user', 'PUT', '', '179', '0', '');
INSERT INTO `gov_log` VALUES (33, '0', '修改用户信息', 'dips', 'admin', '2018-11-21 09:23:17', '2018-11-21 09:23:17', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/user', 'PUT', '', '128', '0', '');
INSERT INTO `gov_log` VALUES (34, '0', '修改用户信息', 'dips', 'admin', '2018-11-21 09:23:19', '2018-11-21 09:23:19', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/user', 'PUT', '', '218', '0', '');
INSERT INTO `gov_log` VALUES (35, '0', '添加用户', 'dips', 'admin', '2018-11-21 09:23:59', '2018-11-21 09:23:59', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/user', 'POST', '', '356', '0', '');
INSERT INTO `gov_log` VALUES (36, '0', '修改个人信息', 'dips', 'admin', '2018-11-21 09:26:18', '2018-11-21 09:26:18', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/user/editInfo', 'PUT', '', '81', '0', '');
INSERT INTO `gov_log` VALUES (37, '0', '修改个人信息', 'dips', 'admin', '2018-11-21 09:27:16', '2018-11-21 09:27:16', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/user/editInfo', 'PUT', '', '207', '0', '');
INSERT INTO `gov_log` VALUES (38, '0', '修改用户信息', 'dips', 'admin', '2018-11-21 09:30:22', '2018-11-21 09:30:22', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/user', 'PUT', '', '533', '0', '');
INSERT INTO `gov_log` VALUES (39, '0', '修改用户信息', 'dips', 'admin', '2018-11-21 09:30:28', '2018-11-21 09:30:28', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/user', 'PUT', '', '168', '0', '');
INSERT INTO `gov_log` VALUES (40, '0', '修改用户信息', 'dips', 'test', '2018-11-21 09:36:10', '2018-11-21 09:36:10', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/user', 'PUT', '', '160', '0', '');
INSERT INTO `gov_log` VALUES (41, '0', '添加标签分类', 'dips', 'test', '2018-11-21 09:36:48', '2018-11-21 09:36:48', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/tagType/saveTagType', 'POST', '', '99', '0', '');
INSERT INTO `gov_log` VALUES (42, '0', '添加标签级别', 'dips', 'test', '2018-11-21 09:36:56', '2018-11-21 09:36:56', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/tagLevel/saveTagLevel', 'POST', '', '52', '0', '');
INSERT INTO `gov_log` VALUES (43, '0', '添加标签', 'dips', 'test', '2018-11-21 09:37:31', '2018-11-21 09:37:31', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/tag/saveTag', 'POST', '', '101', '0', '');
INSERT INTO `gov_log` VALUES (44, '0', '更新标签', 'dips', 'test', '2018-11-21 09:37:34', '2018-11-21 09:37:34', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/tag/updateTag', 'PUT', '', '64', '0', '');
INSERT INTO `gov_log` VALUES (45, '0', '修改用户信息', 'dips', 'test', '2018-11-21 09:38:16', '2018-11-21 09:38:16', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/user', 'PUT', '', '295', '0', '');
INSERT INTO `gov_log` VALUES (46, '0', '修改用户信息', 'dips', 'test', '2018-11-21 09:38:19', '2018-11-21 09:38:19', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/user', 'PUT', '', '118', '0', '');
INSERT INTO `gov_log` VALUES (47, '0', '修改用户信息', 'dips', 'admin', '2018-11-21 15:23:00', '2018-11-21 15:23:00', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/user', 'PUT', '', '253', '0', '');
INSERT INTO `gov_log` VALUES (48, '0', '更新角色菜单', 'dips', 'admin', '2018-11-22 17:31:02', '2018-11-22 17:31:02', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B1%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C35%2C36%2C45%2C46%2C47%2C37%2C48%2C49%2C50%2C51%2C52%2C53%2C57%2C%5D', '1383', '0', '');
INSERT INTO `gov_log` VALUES (49, '0', '添加标签分类', 'dips', 'admin', '2018-11-23 16:30:52', '2018-11-23 16:30:52', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tagType/saveTagType', 'POST', '', '53', '0', '');
INSERT INTO `gov_log` VALUES (50, '0', '添加标签分类', 'dips', 'admin', '2018-11-23 16:30:59', '2018-11-23 16:30:59', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tagType/saveTagType', 'POST', '', '51', '0', '');
INSERT INTO `gov_log` VALUES (51, '0', '更新标签', 'dips', 'admin', '2018-11-23 16:31:13', '2018-11-23 16:31:13', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/updateTag', 'PUT', '', '70', '0', '');
INSERT INTO `gov_log` VALUES (52, '0', '更新标签', 'dips', 'admin', '2018-11-23 16:31:19', '2018-11-23 16:31:19', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/updateTag', 'PUT', '', '28', '0', '');
INSERT INTO `gov_log` VALUES (53, '0', '更新标签', 'dips', 'admin', '2018-11-23 16:31:24', '2018-11-23 16:31:24', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/updateTag', 'PUT', '', '35', '0', '');
INSERT INTO `gov_log` VALUES (54, '0', '更新标签', 'dips', 'admin', '2018-11-23 16:31:28', '2018-11-23 16:31:28', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/updateTag', 'PUT', '', '38', '0', '');
INSERT INTO `gov_log` VALUES (55, '0', '添加用户', 'dips', 'admin', '2018-11-26 12:54:29', '2018-11-26 12:54:29', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/user', 'POST', '', '256', '0', '');
INSERT INTO `gov_log` VALUES (56, '0', '修改用户信息', 'dips', 'admin', '2018-11-26 12:58:57', '2018-11-26 12:58:57', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/user', 'PUT', '', '129', '0', '');
INSERT INTO `gov_log` VALUES (57, '0', '修改用户信息', 'dips', 'admin', '2018-11-26 12:59:02', '2018-11-26 12:59:02', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/user', 'PUT', '', '127', '0', '');
INSERT INTO `gov_log` VALUES (58, '0', '修改用户信息', 'dips', 'admin', '2018-11-26 13:02:36', '2018-11-26 13:02:36', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/user', 'PUT', '', '89', '0', '');
INSERT INTO `gov_log` VALUES (59, '0', '添加用户', 'dips', 'admin', '2018-11-26 13:07:42', '2018-11-26 13:07:42', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/user', 'POST', '', '186', '0', '');
INSERT INTO `gov_log` VALUES (60, '0', '修改用户信息', 'dips', 'admin', '2018-11-26 13:07:53', '2018-11-26 13:07:53', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/user', 'PUT', '', '64', '0', '');
INSERT INTO `gov_log` VALUES (61, '0', '删除用户信息', 'dips', 'admin', '2018-11-26 13:10:25', '2018-11-26 13:10:25', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/user/4', 'DELETE', '', '51', '0', '');
INSERT INTO `gov_log` VALUES (62, '0', '删除用户信息', 'dips', 'admin', '2018-11-26 13:10:29', '2018-11-26 13:10:29', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/user/3', 'DELETE', '', '174', '0', '');
INSERT INTO `gov_log` VALUES (63, '0', '添加用户', 'dips', 'admin', '2018-11-26 13:18:48', '2018-11-26 13:18:48', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/user', 'POST', '', '186', '0', '');
INSERT INTO `gov_log` VALUES (64, '0', '添加用户', 'dips', 'admin', '2018-11-26 13:21:06', '2018-11-26 13:21:06', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/user', 'POST', '', '134', '0', '');
INSERT INTO `gov_log` VALUES (65, '0', '修改用户信息', 'dips', 'admin', '2018-11-26 13:21:19', '2018-11-26 13:21:19', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/user', 'PUT', '', '82', '0', '');
INSERT INTO `gov_log` VALUES (66, '0', '删除角色', 'dips', 'admin', '2018-11-26 13:49:52', '2018-11-26 13:49:52', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/role/1', 'DELETE', '', '51', '0', '');
INSERT INTO `gov_log` VALUES (67, '0', '修改角色', 'dips', 'admin', '2018-11-26 13:52:22', '2018-11-26 13:52:22', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/role', 'PUT', '', '59', '0', '');
INSERT INTO `gov_log` VALUES (68, '0', '更新字典', 'dips', 'admin', '2018-11-26 14:08:25', '2018-11-26 14:08:25', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/dict', 'PUT', '', '46', '0', '');
INSERT INTO `gov_log` VALUES (69, '0', '更新字典', 'dips', 'admin', '2018-11-26 14:08:29', '2018-11-26 14:08:29', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/dict', 'PUT', '', '43', '0', '');
INSERT INTO `gov_log` VALUES (70, '0', '添加标签', 'dips', 'admin', '2018-11-26 15:06:21', '2018-11-26 15:06:21', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/saveTag', 'POST', '', '87', '0', '');
INSERT INTO `gov_log` VALUES (71, '0', '删除用户信息', 'dips', 'admin', '2018-11-26 15:12:25', '2018-11-26 15:12:25', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/user/6', 'DELETE', '', '56', '0', '');
INSERT INTO `gov_log` VALUES (72, '0', '删除用户信息', 'dips', 'admin', '2018-11-26 15:12:28', '2018-11-26 15:12:28', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/user/5', 'DELETE', '', '40', '0', '');
INSERT INTO `gov_log` VALUES (73, '0', '修改用户信息', 'dips', 'admin', '2018-11-26 15:12:34', '2018-11-26 15:12:34', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/user', 'PUT', '', '85', '0', '');
INSERT INTO `gov_log` VALUES (74, '0', '添加标签', 'dips', 'admin', '2018-11-26 15:14:36', '2018-11-26 15:14:36', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/saveTag', 'POST', '', '28', '0', '');
INSERT INTO `gov_log` VALUES (75, '0', '更新角色菜单', 'dips', 'admin', '2018-11-27 11:05:34', '2018-11-27 11:05:34', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B1%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C35%2C36%2C45%2C46%2C47%2C37%2C48%2C49%2C50%2C51%2C52%2C53%2C57%2C58%2C%5D', '1395', '0', '');
INSERT INTO `gov_log` VALUES (76, '0', '添加标签', 'dips', 'admin', '2018-11-27 16:28:02', '2018-11-27 16:28:02', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/tag/create', 'POST', '', '51', '0', '');
INSERT INTO `gov_log` VALUES (77, '0', '更新标签', 'dips', 'admin', '2018-11-27 16:28:15', '2018-11-27 16:28:15', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/tag/update', 'POST', '', '62', '0', '');
INSERT INTO `gov_log` VALUES (78, '0', '删除标签', 'dips', 'admin', '2018-11-27 16:28:21', '2018-11-27 16:28:21', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/tag/2', 'DELETE', '', '261', '0', '');
INSERT INTO `gov_log` VALUES (79, '0', '更新标签分类', 'dips', 'admin', '2018-11-27 16:41:25', '2018-11-27 16:41:25', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/tag-type/update', 'POST', '', '61', '0', '');
INSERT INTO `gov_log` VALUES (80, '0', '更新标签分类', 'dips', 'admin', '2018-11-27 16:41:29', '2018-11-27 16:41:29', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/tag-type/update', 'POST', '', '34', '0', '');
INSERT INTO `gov_log` VALUES (81, '0', '添加标签分类', 'dips', 'admin', '2018-11-27 16:41:33', '2018-11-27 16:41:33', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/tag-type/create', 'POST', '', '44', '0', '');
INSERT INTO `gov_log` VALUES (82, '0', '添加标签级别', 'dips', 'admin', '2018-11-27 16:41:38', '2018-11-27 16:41:38', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/tag-level/create', 'POST', '', '53', '0', '');
INSERT INTO `gov_log` VALUES (83, '0', '删除标签', 'test', 'admin', '2018-11-30 14:24:50', '2018-11-30 14:24:50', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/9', 'DELETE', '', '367', '0', '');
INSERT INTO `gov_log` VALUES (84, '0', '标签合并', 'test', 'admin', '2018-11-30 14:37:37', '2018-11-30 14:37:37', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tagRelation/tagRelationMerge', 'POST', 'tagName1=%5B%E5%A4%A7%E6%95%B0%E6%8D%AE%5D&tagName2=%5B%E5%A5%A5%E6%9C%AF%E5%A4%A7%E5%B8%88%E5%A4%9A%5D', '453', '0', '');
INSERT INTO `gov_log` VALUES (85, '0', '标签合并', 'test', 'admin', '2018-11-30 14:37:51', '2018-11-30 14:37:51', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tagRelation/tagRelationMerge', 'POST', 'tagName1=%5B%E5%A4%A7%E6%95%B0%E6%8D%AE%5D&tagName2=%5B%E5%A5%A5%E6%9C%AF%E5%A4%A7%E5%B8%88%E5%A4%9A%5D', '77', '0', '');
INSERT INTO `gov_log` VALUES (86, '0', '添加标签', 'dips', 'admin', '2018-12-04 11:10:44', '2018-12-04 11:10:44', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/tag/create', 'POST', '', '323', '0', '');
INSERT INTO `gov_log` VALUES (87, '0', '添加标签', 'dips', 'admin', '2018-12-04 11:15:12', '2018-12-04 11:15:12', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/tag/create', 'POST', '', '294', '0', '');
INSERT INTO `gov_log` VALUES (88, '0', '标签合并', 'dips', 'admin', '2018-12-04 15:08:33', '2018-12-04 15:08:33', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/tag/merge', 'POST', '', '822', '0', '');
INSERT INTO `gov_log` VALUES (89, '0', '标签合并', 'dips', 'admin', '2018-12-04 15:09:53', '2018-12-04 15:09:53', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/tag/merge', 'POST', '', '158', '0', '');
INSERT INTO `gov_log` VALUES (90, '0', '标签合并', 'dips', 'admin', '2018-12-04 15:11:48', '2018-12-04 15:11:48', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/tag/merge', 'POST', '', '326', '0', '');
INSERT INTO `gov_log` VALUES (91, '0', '更新标签描述', 'dips', 'admin', '2018-12-04 17:31:01', '2018-12-04 17:31:01', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/description/update', 'POST', '', '51', '0', '');
INSERT INTO `gov_log` VALUES (92, '0', '删除标签描述', 'dips', 'admin', '2018-12-04 17:32:54', '2018-12-04 17:32:54', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/description/delete/2', 'POST', '', '57', '0', '');
INSERT INTO `gov_log` VALUES (93, '0', '删除标签描述', 'dips', 'admin', '2018-12-04 17:33:07', '2018-12-04 17:33:07', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/description/delete/3', 'POST', '', '27', '0', '');
INSERT INTO `gov_log` VALUES (94, '0', '删除标签描述', 'dips', 'admin', '2018-12-04 17:33:17', '2018-12-04 17:33:17', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/description/delete/4', 'POST', '', '50', '0', '');
INSERT INTO `gov_log` VALUES (95, '0', '删除标签描述', 'dips', 'admin', '2018-12-04 17:33:20', '2018-12-04 17:33:20', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/description/delete/5', 'POST', '', '35', '0', '');
INSERT INTO `gov_log` VALUES (96, '0', '删除标签描述', 'dips', 'admin', '2018-12-04 17:33:21', '2018-12-04 17:33:21', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36', '/description/delete/6', 'POST', '', '26', '0', '');
INSERT INTO `gov_log` VALUES (97, '0', '更新标签', 'dips', 'admin', '2018-12-05 09:31:48', '2018-12-05 09:31:48', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/update', 'POST', '', '184', '0', '');
INSERT INTO `gov_log` VALUES (98, '0', '更新标签', 'dips', 'admin', '2018-12-05 09:32:13', '2018-12-05 09:32:13', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/update', 'POST', '', '162', '0', '');
INSERT INTO `gov_log` VALUES (99, '0', '更新标签', 'dips', 'admin', '2018-12-05 09:32:23', '2018-12-05 09:32:23', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/update', 'POST', '', '36', '0', '');
INSERT INTO `gov_log` VALUES (100, '0', '更新标签', 'dips', 'admin', '2018-12-05 09:32:29', '2018-12-05 09:32:29', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/update', 'POST', '', '34', '0', '');
INSERT INTO `gov_log` VALUES (101, '0', '更新标签', 'dips', 'admin', '2018-12-05 09:32:31', '2018-12-05 09:32:31', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/update', 'POST', '', '33', '0', '');
INSERT INTO `gov_log` VALUES (102, '0', '更新标签', 'dips', 'admin', '2018-12-05 09:32:32', '2018-12-05 09:32:32', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/update', 'POST', '', '21', '0', '');
INSERT INTO `gov_log` VALUES (103, '0', '更新标签', 'dips', 'admin', '2018-12-05 09:32:32', '2018-12-05 09:32:32', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/update', 'POST', '', '21', '0', '');
INSERT INTO `gov_log` VALUES (104, '0', '更新标签', 'dips', 'admin', '2018-12-05 09:32:32', '2018-12-05 09:32:32', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/update', 'POST', '', '26', '0', '');
INSERT INTO `gov_log` VALUES (105, '0', '更新标签', 'dips', 'admin', '2018-12-05 10:14:20', '2018-12-05 10:14:20', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag/update', 'POST', '', '213', '0', '');
INSERT INTO `gov_log` VALUES (106, '0', '更新标签', 'dips', 'admin', '2018-12-05 10:14:35', '2018-12-05 10:14:35', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag/update', 'POST', '', '68', '0', '');
INSERT INTO `gov_log` VALUES (107, '0', '更新标签', 'dips', 'admin', '2018-12-05 10:18:13', '2018-12-05 10:18:13', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag/update', 'POST', '', '218', '0', '');
INSERT INTO `gov_log` VALUES (108, '0', '添加标签', 'dips', 'admin', '2018-12-05 10:19:12', '2018-12-05 10:19:12', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag/create', 'POST', '', '318', '0', '');
INSERT INTO `gov_log` VALUES (109, '0', '更新标签', 'dips', 'admin', '2018-12-05 10:19:26', '2018-12-05 10:19:26', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag/update', 'POST', '', '94', '0', '');
INSERT INTO `gov_log` VALUES (110, '0', '更新标签', 'dips', 'admin', '2018-12-05 10:19:35', '2018-12-05 10:19:35', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag/update', 'POST', '', '32', '0', '');
INSERT INTO `gov_log` VALUES (111, '0', '更新标签', 'dips', 'admin', '2018-12-05 10:21:21', '2018-12-05 10:21:21', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36', '/tag/update', 'POST', '', '90', '0', '');
INSERT INTO `gov_log` VALUES (112, '0', '添加标签分类', 'dips', 'admin', '2018-12-05 16:34:35', '2018-12-05 16:34:35', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag_type/create', 'POST', '', '48', '0', '');
INSERT INTO `gov_log` VALUES (113, '0', '添加标签分类', 'dips', 'admin', '2018-12-05 16:34:51', '2018-12-05 16:34:51', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag_type/create', 'POST', '', '41', '0', '');
INSERT INTO `gov_log` VALUES (114, '0', '更新标签分类', 'dips', 'admin', '2018-12-05 16:37:52', '2018-12-05 16:37:52', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag_type/update', 'POST', '', '49', '0', '');
INSERT INTO `gov_log` VALUES (115, '0', '删除标签分类', 'dips', 'admin', '2018-12-05 16:38:19', '2018-12-05 16:38:19', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag_type/10', 'DELETE', '', '109', '0', '');
INSERT INTO `gov_log` VALUES (116, '0', '删除标签分类', 'dips', 'admin', '2018-12-05 16:38:24', '2018-12-05 16:38:24', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag_type/11', 'DELETE', '', '84', '0', '');
INSERT INTO `gov_log` VALUES (117, '0', '更新标签分类', 'dips', 'admin', '2018-12-05 16:38:30', '2018-12-05 16:38:30', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag_type/update', 'POST', '', '36', '0', '');
INSERT INTO `gov_log` VALUES (118, '0', '更新标签分类', 'dips', 'admin', '2018-12-05 16:39:07', '2018-12-05 16:39:07', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag_type/update', 'POST', '', '26', '0', '');
INSERT INTO `gov_log` VALUES (119, '0', '更新标签分类', 'dips', 'admin', '2018-12-05 16:39:11', '2018-12-05 16:39:11', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag_type/update', 'POST', '', '30', '0', '');
INSERT INTO `gov_log` VALUES (120, '0', '添加标签分类', 'dips', 'admin', '2018-12-05 16:45:39', '2018-12-05 16:45:39', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag_type/create', 'POST', '', '22', '0', '');
INSERT INTO `gov_log` VALUES (121, '0', '添加标签分类', 'dips', 'admin', '2018-12-05 16:45:48', '2018-12-05 16:45:48', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag_type/create', 'POST', '', '50', '0', '');
INSERT INTO `gov_log` VALUES (122, '0', '添加标签分类', 'dips', 'admin', '2018-12-05 16:46:13', '2018-12-05 16:46:13', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag_type/create', 'POST', '', '45', '0', '');
INSERT INTO `gov_log` VALUES (123, '0', '删除标签分类', 'dips', 'admin', '2018-12-05 16:47:32', '2018-12-05 16:47:32', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/tag_type/13', 'DELETE', '', '98', '0', '');
INSERT INTO `gov_log` VALUES (124, '0', '更新角色菜单', 'dips', 'admin', '2018-12-05 17:18:26', '2018-12-05 17:18:26', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B1%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C35%2C36%2C45%2C46%2C47%2C37%2C48%2C49%2C50%2C51%2C52%2C53%2C57%2C58%2C59%2C%5D', '1456', '0', '');
INSERT INTO `gov_log` VALUES (125, '0', '更新角色菜单', 'dips', 'admin', '2018-12-06 17:25:09', '2018-12-06 17:25:09', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36', '/role/roleMenuUpd', 'PUT', 'roleId=%5B1%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C35%2C36%2C45%2C46%2C47%2C37%2C48%2C49%2C50%2C51%2C52%2C53%2C57%2C58%2C59%2C61%2C62%2C63%2C64%2C%5D', '160', '0', '');
INSERT INTO `gov_log` VALUES (126, '0', '更新角色菜单', 'dips', 'admin', '2019-01-11 17:03:52', '2019-01-11 17:03:52', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C1%5D', '478', '0', '');
INSERT INTO `gov_log` VALUES (127, '0', '更新角色菜单', 'dips', 'admin', '2019-01-11 17:04:26', '2019-01-11 17:04:26', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B2%5D&menuIds=%5B1%2C2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C38%2C39%2C40%2C41%2C42%2C44%2C54%2C55%2C56%2C8%2C9%2C10%2C36%2C45%2C46%2C47%2C37%2C48%2C49%2C50%2C51%2C52%2C53%2C35%5D', '455', '0', '');
INSERT INTO `gov_log` VALUES (128, '0', '更新角色菜单', 'dips', 'admin', '2019-01-11 17:05:01', '2019-01-11 17:05:01', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B2%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C1%5D', '333', '0', '');
INSERT INTO `gov_log` VALUES (129, '0', '更新角色菜单', 'dips', 'admin', '2019-01-11 17:05:24', '2019-01-11 17:05:24', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C1%5D', '305', '0', '');
INSERT INTO `gov_log` VALUES (130, '0', '更新角色菜单', 'dips', 'admin', '2019-02-23 03:22:12', '2019-02-23 03:22:12', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C65%2C66%2C67%2C1%5D', '80', '0', '');
INSERT INTO `gov_log` VALUES (131, '0', '更新角色菜单', 'dips', 'admin', '2019-02-25 00:37:58', '2019-02-25 00:37:58', '172.18.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C1%5D', '127', '0', '');
INSERT INTO `gov_log` VALUES (132, '0', '更新角色菜单', 'dips', 'admin', '2019-02-25 00:38:22', '2019-02-25 00:38:22', '172.18.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C65%2C66%2C67%2C1%5D', '81', '0', '');
INSERT INTO `gov_log` VALUES (133, '0', '更新角色菜单', 'dips', 'admin', '2019-03-07 14:13:09', '2019-03-07 14:13:09', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C65%2C66%2C67%2C68%2C1%5D', '1816', '0', '');
INSERT INTO `gov_log` VALUES (134, '0', '更新角色菜单', 'dips', 'admin', '2019-03-07 06:23:07', '2019-03-07 06:23:07', '172.18.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C65%2C66%2C67%2C68%2C1%5D', '77', '0', '');
INSERT INTO `gov_log` VALUES (135, '0', '更新角色菜单', 'dips', 'admin', '2019-03-07 14:44:32', '2019-03-07 14:44:32', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C65%2C66%2C67%2C68%2C1%5D', '1023', '0', '');
INSERT INTO `gov_log` VALUES (136, '0', '更新角色菜单', 'dips', 'admin', '2019-03-07 15:18:56', '2019-03-07 15:18:56', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C7%2C25%2C26%2C27%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C65%2C66%2C67%2C68%2C1%5D', '3579', '0', '');
INSERT INTO `gov_log` VALUES (137, '0', '更新角色菜单', 'dips', 'admin', '2019-03-07 15:19:46', '2019-03-07 15:19:46', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C65%2C66%2C67%2C68%2C1%5D', '4444', '0', '');
INSERT INTO `gov_log` VALUES (138, '0', '更新角色菜单', 'dips', 'admin', '2019-03-11 11:08:19', '2019-03-11 11:08:19', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C65%2C66%2C67%2C1%5D', '969', '0', '');
INSERT INTO `gov_log` VALUES (139, '0', '更新角色菜单', 'dips', 'admin', '2019-03-26 08:56:24', '2019-03-26 08:56:24', '172.18.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C65%2C66%2C67%2C68%2C1%5D', '275', '0', '');
INSERT INTO `gov_log` VALUES (140, '0', '更新角色菜单', 'dips', 'admin', '2019-03-26 09:00:33', '2019-03-26 09:00:33', '172.18.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C65%2C66%2C67%2C68%2C69%2C1%5D', '47', '0', '');
INSERT INTO `gov_log` VALUES (141, '0', '更新角色菜单', 'dips', 'admin', '2019-03-26 09:14:47', '2019-03-26 09:14:47', '172.18.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C8%2C9%2C10%2C61%2C62%2C63%2C64%2C65%2C66%2C67%2C68%2C70%2C71%2C72%2C73%2C1%5D', '45', '0', '');
INSERT INTO `gov_log` VALUES (142, '0', '更新角色菜单', 'dips', 'admin', '2019-03-26 09:20:41', '2019-03-26 09:20:41', '172.18.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C61%2C62%2C65%2C66%2C67%2C68%2C70%2C71%2C72%2C73%2C1%5D', '41', '0', '');
INSERT INTO `gov_log` VALUES (143, '0', '更新角色菜单', 'dips', 'admin', '2019-04-02 12:26:33', '2019-04-02 12:26:33', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C61%2C62%2C65%2C66%2C67%2C68%2C74%2C70%2C71%2C72%2C73%2C1%5D', '69', '0', '');
INSERT INTO `gov_log` VALUES (144, '0', '更新角色菜单', 'dips', 'admin', '2019-04-02 12:27:50', '2019-04-02 12:27:50', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C61%2C62%2C65%2C66%2C67%2C70%2C71%2C72%2C73%2C1%5D', '70', '0', '');
INSERT INTO `gov_log` VALUES (145, '0', '更新角色菜单', 'dips', 'admin', '2019-04-02 12:27:54', '2019-04-02 12:27:54', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C61%2C62%2C65%2C66%2C67%2C68%2C74%2C70%2C71%2C72%2C73%2C1%5D', '42', '0', '');
INSERT INTO `gov_log` VALUES (146, '0', '更新角色菜单', 'dips', 'admin', '2019-04-04 02:06:34', '2019-04-04 02:06:34', '172.18.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C61%2C62%2C65%2C66%2C67%2C70%2C71%2C72%2C73%2C1%5D', '226', '0', '');
INSERT INTO `gov_log` VALUES (147, '0', '更新角色菜单', 'dips', 'admin', '2019-04-07 04:35:53', '2019-04-07 04:35:53', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '/role/update/roleMenuUpd', 'POST', 'roleId=%5B1%5D&menuIds=%5B2%2C11%2C12%2C13%2C3%2C14%2C15%2C16%2C4%2C17%2C18%2C19%2C20%2C5%2C21%2C6%2C22%2C23%2C24%2C32%2C33%2C39%2C40%2C41%2C42%2C61%2C62%2C65%2C66%2C67%2C68%2C74%2C70%2C71%2C72%2C73%2C1%5D', '140', '0', '');
COMMIT;

-- ----------------------------
-- Table structure for gov_menu
-- ----------------------------
DROP TABLE IF EXISTS `gov_menu`;
CREATE TABLE `gov_menu` (
  `id` bigint(20) NOT NULL COMMENT '菜单ID',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `permission` varchar(32) NOT NULL DEFAULT '' COMMENT '菜单权限标识',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '前端URL',
  `parent_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '父菜单ID',
  `icon` varchar(32) NOT NULL DEFAULT '' COMMENT '图标',
  `component` varchar(255) NOT NULL DEFAULT '' COMMENT 'VUE页面',
  `sort` int(11) NOT NULL DEFAULT '1' COMMENT '排序值',
  `type` char(1) NOT NULL COMMENT '菜单类型 （0菜单 1按钮）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '0--正常 1--删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- ----------------------------
-- Records of gov_menu
-- ----------------------------
BEGIN;
INSERT INTO `gov_menu` VALUES (1, '系统管理', '', '/admin', -1, 'icon-xitongguanli', 'Layout', 11, '0', '2017-11-07 20:56:00', '2018-11-06 15:38:54', '0');
INSERT INTO `gov_menu` VALUES (2, '用户管理', '', 'user', 1, 'icon-yonghuguanli', 'views/admin/user/index', 2, '0', '2017-11-02 22:24:37', '2018-11-06 15:38:56', '0');
INSERT INTO `gov_menu` VALUES (3, '菜单管理', '', 'menu', 1, 'icon-caidanguanli', 'views/admin/menu/index', 3, '0', '2017-11-08 09:57:27', '2018-11-06 15:38:57', '0');
INSERT INTO `gov_menu` VALUES (4, '角色管理', '', 'role', 1, 'icon-jiaoseguanli', 'views/admin/role/index', 4, '0', '2017-11-08 10:13:37', '2018-11-06 15:38:58', '0');
INSERT INTO `gov_menu` VALUES (5, '日志管理', '', 'log', 1, 'icon-rizhiguanli', 'views/admin/log/index', 5, '0', '2017-11-20 14:06:22', '2018-11-06 15:38:59', '0');
INSERT INTO `gov_menu` VALUES (6, '字典管理', '', 'dict', 1, 'icon-zygl', 'views/admin/dict/index', 6, '0', '2017-11-29 11:30:52', '2018-11-06 15:39:02', '0');
INSERT INTO `gov_menu` VALUES (7, '部门管理', '', 'dept', 1, 'icon-iconbmgl', 'views/admin/dept/index', 7, '0', '2018-01-20 13:17:19', '2018-11-06 15:39:04', '0');
INSERT INTO `gov_menu` VALUES (8, '系统监控', '', '1', -1, 'icon-iconbmgl', '1', 12, '0', '2018-01-22 12:30:41', '2018-11-06 15:39:05', '0');
INSERT INTO `gov_menu` VALUES (9, '服务监控', '', 'http://127.0.0.1:5001', 8, 'icon-jiankong', '', 9, '0', '2018-01-23 10:53:33', '2018-11-06 15:39:06', '0');
INSERT INTO `gov_menu` VALUES (10, '接口文档', '', 'http://127.0.0.1:9999/swagger-ui.html', 8, 'icon-wendangdocument72', '', 14, '0', '2018-01-23 10:56:43', '2018-11-06 15:39:11', '0');
INSERT INTO `gov_menu` VALUES (11, '用户新增', 'sys_user_add', '', 2, '', '', 0, '1', '2017-11-08 09:52:09', '2018-11-06 15:38:21', '0');
INSERT INTO `gov_menu` VALUES (12, '用户修改', 'sys_user_edit', '', 2, '', '', 0, '1', '2017-11-08 09:52:48', '2018-11-06 15:38:21', '0');
INSERT INTO `gov_menu` VALUES (13, '用户删除', 'sys_user_del', '', 2, '', '', 0, '1', '2017-11-08 09:54:01', '2018-11-06 15:38:22', '0');
INSERT INTO `gov_menu` VALUES (14, '菜单新增', 'sys_menu_add', '', 3, '', '', 0, '1', '2017-11-08 10:15:53', '2018-11-06 15:38:22', '0');
INSERT INTO `gov_menu` VALUES (15, '菜单修改', 'sys_menu_edit', '', 3, '', '', 0, '1', '2017-11-08 10:16:23', '2018-11-06 15:38:23', '0');
INSERT INTO `gov_menu` VALUES (16, '菜单删除', 'sys_menu_del', '', 3, '', '', 0, '1', '2017-11-08 10:16:43', '2018-11-06 15:38:23', '0');
INSERT INTO `gov_menu` VALUES (17, '角色新增', 'sys_role_add', '', 4, '', '', 0, '1', '2017-11-08 10:14:18', '2018-11-06 15:38:24', '0');
INSERT INTO `gov_menu` VALUES (18, '角色修改', 'sys_role_edit', '', 4, '', '', 0, '1', '2017-11-08 10:14:41', '2018-11-06 15:38:25', '0');
INSERT INTO `gov_menu` VALUES (19, '角色删除', 'sys_role_del', '', 4, '', '', 0, '1', '2017-11-08 10:14:59', '2018-11-06 15:38:25', '0');
INSERT INTO `gov_menu` VALUES (20, '分配权限', 'sys_role_perm', '', 4, '', '', 0, '1', '2018-04-20 07:22:55', '2018-11-06 15:38:26', '0');
INSERT INTO `gov_menu` VALUES (21, '日志删除', 'sys_log_del', '', 5, '', '', 0, '1', '2017-11-20 20:37:37', '2018-11-06 15:38:26', '0');
INSERT INTO `gov_menu` VALUES (22, '字典删除', 'sys_dict_del', '', 6, '', '', 0, '1', '2017-11-29 11:30:11', '2018-11-06 15:38:27', '0');
INSERT INTO `gov_menu` VALUES (23, '字典新增', 'sys_dict_add', '', 6, '', '', 0, '1', '2018-05-11 22:34:55', '2018-11-06 15:38:27', '0');
INSERT INTO `gov_menu` VALUES (24, '字典修改', 'sys_dict_edit', '', 6, '', '', 0, '1', '2018-05-11 22:36:03', '2018-11-06 15:38:28', '0');
INSERT INTO `gov_menu` VALUES (25, '部门新增', 'sys_dept_add', '', 7, '', '', 0, '1', '2018-01-20 14:56:16', '2018-11-06 15:38:29', '0');
INSERT INTO `gov_menu` VALUES (26, '部门修改', 'sys_dept_edit', '', 7, '', '', 0, '1', '2018-01-20 14:56:59', '2018-11-06 15:38:29', '0');
INSERT INTO `gov_menu` VALUES (27, '部门删除', 'sys_dept_del', '', 7, '', '', 0, '1', '2018-01-20 14:57:28', '2018-11-06 15:38:31', '0');
INSERT INTO `gov_menu` VALUES (28, '客户端管理', '', 'client', 1, 'icon-bangzhushouji', 'views/admin/client/index', 10, '0', '2018-01-20 13:17:19', '2018-10-24 10:15:42', '0');
INSERT INTO `gov_menu` VALUES (29, '客户端新增', 'sys_client_add', '', 28, '', '', 0, '1', '2018-05-15 21:35:18', '2018-11-06 15:38:31', '0');
INSERT INTO `gov_menu` VALUES (30, '客户端修改', 'sys_client_edit', '', 28, '', '', 0, '1', '2018-05-15 21:37:06', '2018-11-06 15:38:32', '0');
INSERT INTO `gov_menu` VALUES (31, '客户端删除', 'sys_client_del', '', 28, '', '', 0, '1', '2018-05-15 21:39:16', '2018-11-06 15:38:33', '0');
INSERT INTO `gov_menu` VALUES (32, '令牌管理', '', 'token', 1, 'icon-key', 'views/admin/token/index', 11, '0', '2018-10-18 14:15:03', '2018-10-24 10:15:48', '0');
INSERT INTO `gov_menu` VALUES (33, '令牌删除', 'sys_token_del', '', 32, '', '', 1, '1', '2018-10-18 14:18:59', '2018-11-06 15:38:34', '0');
INSERT INTO `gov_menu` VALUES (34, '关联管理', '', 'relation', 1, 'icon-weibiaoti46', 'views/admin/relation/index', 8, '0', '2018-09-04 16:17:17', '2018-11-20 14:48:32', '1');
INSERT INTO `gov_menu` VALUES (35, '标签管理', '', '/tag', -1, 'el-icon-tickets', 'Layout', 15, '0', '2018-08-17 08:21:04', '2018-11-06 15:39:31', '0');
INSERT INTO `gov_menu` VALUES (36, '标签列表', '', 'bqgl', 35, 'icon-layers', 'views/tag/tag_manager/index', 1, '0', '2018-08-17 08:25:49', '2018-11-06 15:39:32', '0');
INSERT INTO `gov_menu` VALUES (37, '标签属性', '', 'bqsx', 35, 'el-icon-edit', 'views/tag/tag_prop/index', 2, '0', '2018-08-17 08:27:00', '2018-11-06 15:39:34', '0');
INSERT INTO `gov_menu` VALUES (38, '代码生成', '', 'gen', 1, 'icon-weibiaoti46', 'views/admin/gen/index', 10, '0', '2018-09-01 14:33:52', '2018-11-06 15:40:14', '0');
INSERT INTO `gov_menu` VALUES (39, '密钥管理', '', 'social', 1, 'icon-social', 'views/admin/social/index', 11, '0', '2018-09-01 14:35:07', '2018-11-06 15:40:26', '0');
INSERT INTO `gov_menu` VALUES (40, '密钥新增', '', '', 39, '', '', 0, '1', '2018-09-01 14:36:01', '2018-11-06 15:40:23', '0');
INSERT INTO `gov_menu` VALUES (41, '密钥删除', '', '', 39, '', '', 2, '1', '2018-09-01 14:37:09', '2018-11-06 15:40:20', '0');
INSERT INTO `gov_menu` VALUES (42, '密钥修改', '', '', 39, '', '', 1, '1', '2018-09-01 14:36:42', '2018-11-06 15:38:37', '0');
INSERT INTO `gov_menu` VALUES (44, '城市管理', '', 'city', 1, 'icon-iconbmgl', 'views/admin/city/index', 8, '0', '2018-11-20 08:59:46', '2018-11-20 08:59:46', '0');
INSERT INTO `gov_menu` VALUES (45, '标签删除', 'gov_tag_del', '', 36, '', '', 1, '1', '2018-11-20 17:21:50', '2018-11-21 08:58:10', '0');
INSERT INTO `gov_menu` VALUES (46, '标签新增', 'gov_tag_add', '', 36, '', '', 1, '1', '2018-11-20 17:22:25', '2018-11-21 08:58:12', '0');
INSERT INTO `gov_menu` VALUES (47, '标签更新', 'gov_tag_edit', '', 36, '', '', 1, '1', '2018-11-20 17:23:10', '2018-11-21 08:58:14', '0');
INSERT INTO `gov_menu` VALUES (48, '标签级别删除', 'gov_tagLevel_del', '', 37, '', '', 1, '1', '2018-11-20 17:24:29', '2018-11-21 08:58:16', '0');
INSERT INTO `gov_menu` VALUES (49, '标签级别新增', 'gov_tagLevel_add', '', 37, '', '', 1, '1', '2018-11-20 17:24:54', '2018-11-21 08:58:20', '0');
INSERT INTO `gov_menu` VALUES (50, '标签级别更新', 'gov_tagLevel_edit', '', 37, '', '', 1, '1', '2018-11-20 17:25:19', '2018-11-21 08:58:22', '0');
INSERT INTO `gov_menu` VALUES (51, '标签分类删除', 'gov_tagType_del', '', 37, '', '', 1, '1', '2018-11-20 17:26:00', '2018-11-21 08:58:24', '0');
INSERT INTO `gov_menu` VALUES (52, '标签分类新增', 'gov_tagType_add', '', 37, '', '', 1, '1', '2018-11-20 17:26:33', '2018-11-21 08:58:27', '0');
INSERT INTO `gov_menu` VALUES (53, '标签分类更新', 'gov_tagType_edit', '', 37, '', '', 1, '1', '2018-11-20 17:26:58', '2018-11-21 08:58:29', '0');
INSERT INTO `gov_menu` VALUES (54, '添加城市', 'sys_city_add', '', 44, '', '', 1, '1', '2018-11-21 09:00:44', '2018-11-21 09:00:44', '0');
INSERT INTO `gov_menu` VALUES (55, '编辑城市', 'sys_city_edit', '', 44, '', '', 1, '1', '2018-11-21 09:00:59', '2018-11-21 09:00:59', '0');
INSERT INTO `gov_menu` VALUES (56, '删除城市', 'sys_city_del', '', 44, '', '', 1, '1', '2018-11-21 09:01:10', '2018-11-21 09:01:10', '0');
INSERT INTO `gov_menu` VALUES (57, '标签统计', '', 'bqtj', 35, 'icon-layers', 'views/tag/statistics/index', 3, '0', '2018-11-22 17:30:35', '2018-11-22 17:30:35', '0');
INSERT INTO `gov_menu` VALUES (58, '功能启用', '', 'function', 35, 'icon-layers', 'views/tag/function/index', 4, '0', '2018-11-27 11:03:19', '2018-11-27 11:03:19', '0');
INSERT INTO `gov_menu` VALUES (59, '标签规则', '', 'rule', 35, 'icon-layers', 'views/tag/rule/index', 5, '0', '2018-12-05 17:17:51', '2018-12-05 17:17:51', '0');
INSERT INTO `gov_menu` VALUES (61, '规则设置', '', '/data_cleansing', -1, 'el-icon-edit', 'Layout', 1, '0', '2018-12-06 15:18:23', '2018-12-06 15:18:23', '0');
INSERT INTO `gov_menu` VALUES (62, '规则池', '', 'rule_configuration', 61, 'icon-layers', 'views/data_cleansing/rule_configuration/index', 1, '0', '2018-12-06 15:19:00', '2019-03-26 17:06:06', '0');
INSERT INTO `gov_menu` VALUES (63, '清洗池', '', 'list_tobecleaned', 61, 'icon-layers', 'views/data_cleansing/list_tobecleaned/index', 2, '0', '2018-12-06 15:20:09', '2019-03-26 17:18:28', '1');
INSERT INTO `gov_menu` VALUES (64, '结果池', '', 'list_cleaned', 61, 'icon-layers', 'views/data_cleansing/list_cleaned/index', 3, '0', '2018-12-06 15:20:46', '2019-03-26 17:18:31', '1');
INSERT INTO `gov_menu` VALUES (65, '回收站', '', '/recycle', -1, 'el-icon-delete', 'Layout', 4, '0', '2019-02-23 11:09:00', '2019-02-23 11:09:00', '0');
INSERT INTO `gov_menu` VALUES (66, '规则回收池', '', 'rule_configuration', 65, 'icon-layers', 'views/recycle/rule_configuration/index', 1, '0', '2019-02-23 11:20:46', '2019-02-23 11:20:46', '0');
INSERT INTO `gov_menu` VALUES (67, '清洗回收池', '', 'list_tobecleaned', 65, 'icon-layers', 'views/recycle/list_tobecleaned/index', 2, '0', '2019-02-23 11:21:39', '2019-02-23 11:21:39', '0');
INSERT INTO `gov_menu` VALUES (68, '尚在开发中...', '', '/combine', -1, 'icon-layers', 'Layout', 5, '0', '2019-03-07 14:12:41', '2019-03-07 14:12:41', '0');
INSERT INTO `gov_menu` VALUES (69, '多池合一', '', '', 68, 'icon-layers', '', 1, '1', '2019-03-26 16:59:28', '2019-03-26 17:02:18', '1');
INSERT INTO `gov_menu` VALUES (70, '分析清洗', '', '/data_cleansing', -1, 'el-icon-refresh', 'Layout', 2, '0', '2019-03-26 17:10:54', '2019-03-26 17:10:54', '0');
INSERT INTO `gov_menu` VALUES (71, '清洗池', '', 'list_tobecleaned', 70, 'icon-layers', 'views/data_cleansing/list_tobecleaned/index', 1, '0', '2019-03-26 17:12:23', '2019-03-26 17:12:23', '0');
INSERT INTO `gov_menu` VALUES (72, '结果展示', '', '/data_cleansing', -1, 'el-icon-date', 'Layout', 3, '0', '2019-03-26 17:13:20', '2019-03-26 17:13:20', '0');
INSERT INTO `gov_menu` VALUES (73, '结果池', '', 'list_cleaned', 72, 'icon-layers', 'views/data_cleansing/list_cleaned/index', 1, '0', '2019-03-26 17:14:10', '2019-03-26 17:14:10', '0');
INSERT INTO `gov_menu` VALUES (74, '多池合一', '', 'index', 68, 'icon-layers', 'views/combine/combine/index', 1, '0', '2019-04-02 20:22:24', '2019-04-02 20:22:24', '0');
COMMIT;

-- ----------------------------
-- Table structure for gov_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `gov_oauth_client_details`;
CREATE TABLE `gov_oauth_client_details` (
  `client_id` varchar(32) NOT NULL,
  `resource_ids` varchar(256) NOT NULL DEFAULT '',
  `client_secret` varchar(256) NOT NULL DEFAULT '',
  `scope` varchar(256) NOT NULL DEFAULT '',
  `authorized_grant_types` varchar(256) NOT NULL DEFAULT '',
  `web_server_redirect_uri` varchar(256) NOT NULL DEFAULT '',
  `authorities` varchar(256) NOT NULL DEFAULT '',
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) NOT NULL,
  `autoapprove` varchar(256) NOT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端管理表';

-- ----------------------------
-- Records of gov_oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `gov_oauth_client_details` VALUES ('dips', '', 'dips', 'server', 'password,refresh_token', '', '', NULL, NULL, '', 'true');
INSERT INTO `gov_oauth_client_details` VALUES ('test', '', 'test', 'server', 'password,refresh_token', '', '', NULL, NULL, '', 'true');
COMMIT;

-- ----------------------------
-- Table structure for gov_resrc
-- ----------------------------
DROP TABLE IF EXISTS `gov_resrc`;
CREATE TABLE `gov_resrc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '信息资源ID',
  `name` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '信息资源名称',
  `number` varchar(50) NOT NULL DEFAULT '' COMMENT '信息资源代码',
  `dept_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '资源提供方',
  `summ` varchar(255) NOT NULL DEFAULT '' COMMENT '信息资源摘要',
  `sys_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属系统',
  `fmt` varchar(100) NOT NULL DEFAULT '' COMMENT '信息资源格式',
  `area_type` varchar(100) NOT NULL DEFAULT '' COMMENT '重点领域分类',
  `topic_type` varchar(100) NOT NULL DEFAULT '' COMMENT '主题分类',
  `res_type` varchar(100) NOT NULL DEFAULT '' COMMENT '所属目录资源分类',
  `upd_cycle` varchar(100) NOT NULL DEFAULT '' COMMENT '更新周期',
  `sharing_type
sharing_type
sharing_type` varchar(100) NOT NULL DEFAULT '' COMMENT '共享类型',
  `sharing_cond` varchar(100) NOT NULL DEFAULT '' COMMENT '共享类型',
  `sharing_mode` varchar(100) NOT NULL DEFAULT '' COMMENT '共享类型',
  `is_open` varchar(100) NOT NULL DEFAULT '' COMMENT '是否向社会开放',
  `open_cond` varchar(255) NOT NULL DEFAULT '' COMMENT '开放条件',
  `rescode` varchar(255) NOT NULL DEFAULT '' COMMENT '关联资源代码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` char(1) CHARACTER SET utf8 NOT NULL DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='信息资源';

-- ----------------------------
-- Table structure for gov_role
-- ----------------------------
DROP TABLE IF EXISTS `gov_role`;
CREATE TABLE `gov_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) CHARACTER SET utf8mb4 NOT NULL,
  `role_code` varchar(64) CHARACTER SET utf8mb4 NOT NULL,
  `role_desc` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` char(1) CHARACTER SET utf8mb4 NOT NULL DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_idx1_role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户角色表';

-- ----------------------------
-- Records of gov_role
-- ----------------------------
BEGIN;
INSERT INTO `gov_role` VALUES (1, '超级管理员', 'ROLE_ADMIN', '最大权限', '2017-10-29 15:45:51', '2018-11-26 13:50:53', '0');
INSERT INTO `gov_role` VALUES (2, '普通会员', 'ROLE_USER', '普通权限', '2018-11-20 16:31:28', '2018-11-20 16:31:28', '0');
COMMIT;

-- ----------------------------
-- Table structure for gov_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `gov_role_dept`;
CREATE TABLE `gov_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';

-- ----------------------------
-- Records of gov_role_dept
-- ----------------------------
BEGIN;
INSERT INTO `gov_role_dept` VALUES (2, 2, 1);
INSERT INTO `gov_role_dept` VALUES (3, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for gov_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `gov_role_menu`;
CREATE TABLE `gov_role_menu` (
  `role_id` bigint(11) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单权限表';

-- ----------------------------
-- Records of gov_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `gov_role_menu` VALUES (1, 1);
INSERT INTO `gov_role_menu` VALUES (1, 2);
INSERT INTO `gov_role_menu` VALUES (1, 3);
INSERT INTO `gov_role_menu` VALUES (1, 4);
INSERT INTO `gov_role_menu` VALUES (1, 5);
INSERT INTO `gov_role_menu` VALUES (1, 6);
INSERT INTO `gov_role_menu` VALUES (1, 11);
INSERT INTO `gov_role_menu` VALUES (1, 12);
INSERT INTO `gov_role_menu` VALUES (1, 13);
INSERT INTO `gov_role_menu` VALUES (1, 14);
INSERT INTO `gov_role_menu` VALUES (1, 15);
INSERT INTO `gov_role_menu` VALUES (1, 16);
INSERT INTO `gov_role_menu` VALUES (1, 17);
INSERT INTO `gov_role_menu` VALUES (1, 18);
INSERT INTO `gov_role_menu` VALUES (1, 19);
INSERT INTO `gov_role_menu` VALUES (1, 20);
INSERT INTO `gov_role_menu` VALUES (1, 21);
INSERT INTO `gov_role_menu` VALUES (1, 22);
INSERT INTO `gov_role_menu` VALUES (1, 23);
INSERT INTO `gov_role_menu` VALUES (1, 24);
INSERT INTO `gov_role_menu` VALUES (1, 32);
INSERT INTO `gov_role_menu` VALUES (1, 33);
INSERT INTO `gov_role_menu` VALUES (1, 39);
INSERT INTO `gov_role_menu` VALUES (1, 40);
INSERT INTO `gov_role_menu` VALUES (1, 41);
INSERT INTO `gov_role_menu` VALUES (1, 42);
INSERT INTO `gov_role_menu` VALUES (1, 61);
INSERT INTO `gov_role_menu` VALUES (1, 62);
INSERT INTO `gov_role_menu` VALUES (1, 65);
INSERT INTO `gov_role_menu` VALUES (1, 66);
INSERT INTO `gov_role_menu` VALUES (1, 67);
INSERT INTO `gov_role_menu` VALUES (1, 68);
INSERT INTO `gov_role_menu` VALUES (1, 70);
INSERT INTO `gov_role_menu` VALUES (1, 71);
INSERT INTO `gov_role_menu` VALUES (1, 72);
INSERT INTO `gov_role_menu` VALUES (1, 73);
INSERT INTO `gov_role_menu` VALUES (1, 74);
INSERT INTO `gov_role_menu` VALUES (2, 1);
INSERT INTO `gov_role_menu` VALUES (2, 2);
INSERT INTO `gov_role_menu` VALUES (2, 3);
INSERT INTO `gov_role_menu` VALUES (2, 4);
INSERT INTO `gov_role_menu` VALUES (2, 5);
INSERT INTO `gov_role_menu` VALUES (2, 6);
INSERT INTO `gov_role_menu` VALUES (2, 8);
INSERT INTO `gov_role_menu` VALUES (2, 9);
INSERT INTO `gov_role_menu` VALUES (2, 10);
INSERT INTO `gov_role_menu` VALUES (2, 11);
INSERT INTO `gov_role_menu` VALUES (2, 12);
INSERT INTO `gov_role_menu` VALUES (2, 13);
INSERT INTO `gov_role_menu` VALUES (2, 14);
INSERT INTO `gov_role_menu` VALUES (2, 15);
INSERT INTO `gov_role_menu` VALUES (2, 16);
INSERT INTO `gov_role_menu` VALUES (2, 17);
INSERT INTO `gov_role_menu` VALUES (2, 18);
INSERT INTO `gov_role_menu` VALUES (2, 19);
INSERT INTO `gov_role_menu` VALUES (2, 20);
INSERT INTO `gov_role_menu` VALUES (2, 21);
INSERT INTO `gov_role_menu` VALUES (2, 22);
INSERT INTO `gov_role_menu` VALUES (2, 23);
INSERT INTO `gov_role_menu` VALUES (2, 24);
INSERT INTO `gov_role_menu` VALUES (2, 32);
INSERT INTO `gov_role_menu` VALUES (2, 33);
INSERT INTO `gov_role_menu` VALUES (2, 39);
INSERT INTO `gov_role_menu` VALUES (2, 40);
INSERT INTO `gov_role_menu` VALUES (2, 41);
INSERT INTO `gov_role_menu` VALUES (2, 42);
INSERT INTO `gov_role_menu` VALUES (2, 61);
INSERT INTO `gov_role_menu` VALUES (2, 62);
INSERT INTO `gov_role_menu` VALUES (2, 63);
INSERT INTO `gov_role_menu` VALUES (2, 64);
COMMIT;

-- ----------------------------
-- Table structure for gov_social_details
-- ----------------------------
DROP TABLE IF EXISTS `gov_social_details`;
CREATE TABLE `gov_social_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
  `type` varchar(16) NOT NULL DEFAULT '' COMMENT '类型',
  `remark` varchar(64) NOT NULL DEFAULT '' COMMENT '描述',
  `app_id` varchar(64) NOT NULL DEFAULT '' COMMENT 'appid',
  `app_secret` varchar(64) NOT NULL DEFAULT '' COMMENT 'app_secret',
  `redirect_url` varchar(128) NOT NULL DEFAULT '' COMMENT '回调地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` char(50) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统社交登录账号表';

-- ----------------------------
-- Table structure for gov_tag
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag`;
CREATE TABLE `gov_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL DEFAULT '' COMMENT '标签名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '标签创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `refers` int(11) NOT NULL DEFAULT '0' COMMENT '标签应用次数',
  `order_num` tinyint(4) NOT NULL COMMENT '标签优先级',
  `type_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '标签分类',
  `level_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '标签级别',
  `views` int(11) NOT NULL COMMENT '标签浏览量',
  `description` varchar(255) NOT NULL COMMENT '标签介绍',
  `creator_id` bigint(20) NOT NULL COMMENT '标签创建者',
  `system` varchar(80) NOT NULL COMMENT '所属系统',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '标签状态',
  `enable` int(1) NOT NULL DEFAULT '1' COMMENT '标签启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

-- ----------------------------
-- Records of gov_tag
-- ----------------------------
BEGIN;
INSERT INTO `gov_tag` VALUES (1, '奥术大师多sadas2121', '2018-12-03 16:15:40', '2018-12-05 10:21:21', 3, 1, 5, 2, 0, '阿发', 1, 'DIPS', 0, 1);
INSERT INTO `gov_tag` VALUES (2, 'DSFSD担任法国放松放松', '2018-12-03 16:16:35', '2018-12-03 16:16:35', 3, 1, 6, 1, 0, 'SAFA', 1, 'DIPS', 1, 1);
INSERT INTO `gov_tag` VALUES (10, 'test', '2018-12-04 11:10:43', '2018-12-04 11:10:43', 0, 1, 8, 1, 0, 'eeeeee', 1, 'DIPS', 1, 1);
INSERT INTO `gov_tag` VALUES (11, '快速添加', '2018-12-04 11:15:12', '2018-12-04 11:15:12', 0, 3, 7, 1, 0, '三', 1, 'DIPS', 1, 1);
INSERT INTO `gov_tag` VALUES (12, '萨达', '2018-12-04 11:15:12', '2018-12-04 11:15:12', 1, 1, 0, 0, 0, '', 1, 'DIPS', 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for gov_tag_description
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag_description`;
CREATE TABLE `gov_tag_description` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签描述id',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `tag_id` bigint(20) NOT NULL COMMENT '关联标签id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` bigint(20) NOT NULL COMMENT '创建者',
  PRIMARY KEY (`id`),
  KEY `g_tag_id` (`tag_id`) USING BTREE,
  KEY `g_creator_id` (`creator_id`) USING BTREE,
  CONSTRAINT `gov_tag_description_ibfk_1` FOREIGN KEY (`tag_id`) REFERENCES `gov_tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签描述表';

-- ----------------------------
-- Table structure for gov_tag_function
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag_function`;
CREATE TABLE `gov_tag_function` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '功能名称',
  `number` varchar(255) NOT NULL DEFAULT '' COMMENT '功能编码',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '功能描述',
  `enable` int(1) NOT NULL DEFAULT '1' COMMENT '是否启用 1启用 0关闭',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='标签功能表';

-- ----------------------------
-- Records of gov_tag_function
-- ----------------------------
BEGIN;
INSERT INTO `gov_tag_function` VALUES (1, '标签审核', 'tagReview', '标签审核即对用户所创建的标签进行审核，根据标签标准和规范给予其通过与否的操作。功能禁用状态下，新增的标签默认审核通过状态。功能启用状态下，新增的标签默认待审核，需要管理员给以通过与否的操作。', 1);
INSERT INTO `gov_tag_function` VALUES (2, '标签注释', 'tagNotes', '标签注释即对标签进行定义、描述。功能禁用状态下，无法对标签进行注释。功能启用状态下，管理员可以对标签进行一组注释，并且管理员可对用户的注释进行管理。', 0);
INSERT INTO `gov_tag_function` VALUES (3, '标签关联', 'tagRelation', '标签关联指对相关的标签进行有效联系，挖掘标签与标签间潜在的关系，减少标签间的信息跨度。在对事物打标签的过程中提供更加精准的、更加全面可靠的标签特征。功能禁用状态下，无法进行关联操作。功能启用状态下，可以对多个标签进行关联。', 0);
COMMIT;

-- ----------------------------
-- Table structure for gov_tag_level
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag_level`;
CREATE TABLE `gov_tag_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签级别ID',
  `name` varchar(150) NOT NULL COMMENT '标签级别名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='标签级别表';

-- ----------------------------
-- Records of gov_tag_level
-- ----------------------------
BEGIN;
INSERT INTO `gov_tag_level` VALUES (1, '默认级别', '2018-11-21 09:36:56');
INSERT INTO `gov_tag_level` VALUES (2, '21212', '2018-11-27 16:41:38');
COMMIT;

-- ----------------------------
-- Table structure for gov_tag_merge_record
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag_merge_record`;
CREATE TABLE `gov_tag_merge_record` (
  `tag_id` bigint(20) NOT NULL COMMENT '标签id',
  `merge_id` bigint(20) NOT NULL COMMENT '合并id',
  PRIMARY KEY (`tag_id`,`merge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签合并记录表';

-- ----------------------------
-- Records of gov_tag_merge_record
-- ----------------------------
BEGIN;
INSERT INTO `gov_tag_merge_record` VALUES (1, 2);
INSERT INTO `gov_tag_merge_record` VALUES (2, 1);
COMMIT;

-- ----------------------------
-- Table structure for gov_tag_modification_record
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag_modification_record`;
CREATE TABLE `gov_tag_modification_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '修改描述',
  `creator_id` bigint(20) NOT NULL COMMENT '创建者id',
  `tag_id` bigint(20) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='标签修改记录表';

-- ----------------------------
-- Records of gov_tag_modification_record
-- ----------------------------
BEGIN;
INSERT INTO `gov_tag_modification_record` VALUES (1, '2018-12-04 16:24:26', '创建了标签“test”', 1, 1);
INSERT INTO `gov_tag_modification_record` VALUES (2, '2018-12-04 16:24:27', '创建了标签“快速添加”', 1, 1);
INSERT INTO `gov_tag_modification_record` VALUES (3, '2018-12-04 16:24:28', '“DSFSD”与“奥术大师多”合并', 1, 1);
INSERT INTO `gov_tag_modification_record` VALUES (4, '2018-12-04 16:24:30', '“DSFSD”与“奥术大师多”合并', 1, 1);
INSERT INTO `gov_tag_modification_record` VALUES (5, '2018-12-04 15:11:48', '“奥术大师多”与“DSFSD”合并', 1, 1);
INSERT INTO `gov_tag_modification_record` VALUES (6, '2018-12-04 16:24:35', '大萨达所多', 1, 1);
INSERT INTO `gov_tag_modification_record` VALUES (7, '2018-12-05 09:31:47', '“奥术大师多”更名为“奥术大师多22”', 1, 1);
INSERT INTO `gov_tag_modification_record` VALUES (8, '2018-12-05 09:32:12', '“DSFSD”更名为“DSFSD担任法国放松放松”', 1, 2);
INSERT INTO `gov_tag_modification_record` VALUES (9, '2018-12-05 10:14:19', '“奥术大师多22”更名为“奥术大师多”', 1, 1);
INSERT INTO `gov_tag_modification_record` VALUES (10, '2018-12-05 10:14:35', '“奥术大师多”更名为“奥术大师多66”', 1, 1);
INSERT INTO `gov_tag_modification_record` VALUES (11, '2018-12-05 10:18:12', '“奥术大师多66”更名为“奥术大师多”', 1, 1);
INSERT INTO `gov_tag_modification_record` VALUES (12, '2018-12-05 10:19:12', '创建了标签“sdsadsadsad”', 1, 13);
INSERT INTO `gov_tag_modification_record` VALUES (13, '2018-12-05 10:19:26', '“奥术大师多”更名为“奥术大师多sadas”', 1, 1);
INSERT INTO `gov_tag_modification_record` VALUES (14, '2018-12-05 10:21:20', '“奥术大师多sadas”更名为“奥术大师多sadas2121”', 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for gov_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag_relation`;
CREATE TABLE `gov_tag_relation` (
  `node` varchar(60) NOT NULL COMMENT '栏目编码',
  `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
  `relation_id` bigint(20) NOT NULL COMMENT '标签关联ID',
  `type_id` bigint(20) NOT NULL COMMENT '标签关联类型',
  PRIMARY KEY (`tag_id`,`relation_id`,`type_id`,`node`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签关联表';

-- ----------------------------
-- Records of gov_tag_relation
-- ----------------------------
BEGIN;
INSERT INTO `gov_tag_relation` VALUES ('tag', 1, 10, 4);
INSERT INTO `gov_tag_relation` VALUES ('tag', 1, 13, 4);
INSERT INTO `gov_tag_relation` VALUES ('tag', 2, 13, 4);
INSERT INTO `gov_tag_relation` VALUES ('tag', 12, 11, 4);
COMMIT;

-- ----------------------------
-- Table structure for gov_tag_relation_type
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag_relation_type`;
CREATE TABLE `gov_tag_relation_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签',
  `type_name` varchar(30) NOT NULL COMMENT '标签关联名称',
  `type_number` varchar(30) NOT NULL COMMENT '标签关联编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='标签关联类型表';

-- ----------------------------
-- Records of gov_tag_relation_type
-- ----------------------------
BEGIN;
INSERT INTO `gov_tag_relation_type` VALUES (1, '卓越标签', 'ability');
INSERT INTO `gov_tag_relation_type` VALUES (2, '专业标签', 'project');
INSERT INTO `gov_tag_relation_type` VALUES (3, '进步标签', 'learning');
INSERT INTO `gov_tag_relation_type` VALUES (4, '默认标签', 'def');
COMMIT;

-- ----------------------------
-- Table structure for gov_tag_type
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag_type`;
CREATE TABLE `gov_tag_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签分类ID',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级分类ID',
  `name` varchar(150) NOT NULL COMMENT '标签分类名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='标签分类表';

-- ----------------------------
-- Records of gov_tag_type
-- ----------------------------
BEGIN;
INSERT INTO `gov_tag_type` VALUES (1, -1, '人名类', '2018-11-21 09:36:48');
INSERT INTO `gov_tag_type` VALUES (2, -1, '地域类', '2018-11-23 16:30:52');
INSERT INTO `gov_tag_type` VALUES (3, -1, '机构类', '2018-11-23 16:30:59');
INSERT INTO `gov_tag_type` VALUES (4, -1, '水电费', '2018-11-27 14:55:36');
INSERT INTO `gov_tag_type` VALUES (5, 0, '阿萨德111666', '2018-11-27 14:55:44');
INSERT INTO `gov_tag_type` VALUES (6, 0, '阿萨斯的', '2018-11-27 14:56:03');
INSERT INTO `gov_tag_type` VALUES (7, 0, '奥术大师666999', '2018-11-27 14:56:14');
INSERT INTO `gov_tag_type` VALUES (8, 0, '大作战', '2018-11-27 14:56:28');
INSERT INTO `gov_tag_type` VALUES (9, 5, '32323', '2018-11-27 16:41:33');
INSERT INTO `gov_tag_type` VALUES (12, 5, '根本扽吧', '2018-12-05 16:45:39');
INSERT INTO `gov_tag_type` VALUES (14, 12, 'asdasd', '2018-12-05 16:46:13');
COMMIT;

-- ----------------------------
-- Table structure for gov_user
-- ----------------------------
DROP TABLE IF EXISTS `gov_user`;
CREATE TABLE `gov_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `real_name` varchar(64) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '真实姓名',
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `salt` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '随机盐',
  `phone` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '简介',
  `avatar` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '头像',
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  `dept_name` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '部门名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` char(1) COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '0-正常，1-删除',
  `weixin_openid` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '微信openid',
  `qq_openid` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'QQ openid',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_idx1_username` (`username`) USING BTREE,
  KEY `user_wx_openid` (`weixin_openid`) USING BTREE,
  KEY `user_qq_openid` (`qq_openid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户表';

-- ----------------------------
-- Records of gov_user
-- ----------------------------
BEGIN;
INSERT INTO `gov_user` VALUES (1, 'admin', '超级管理员', '$2a$10$2UGhOnDBAouO09rtateDOO.NYWfOlNc/iIn7nk9G6cQwOdH9uEH4.', '', '13750728507', '//dips-cloud-gov.oss-cn-hangzhou.aliyuncs.com/upload/images/201811/82732aca-9de1-4f68-87ac-4ac49b15159e.jpg', 1, '超级管理部门', '2018-11-16 14:55:51', '2018-12-24 10:21:17', '0', 'o_0FT0uyg_H1vVy2H0JpSwlVGhWQ', 'admin');
INSERT INTO `gov_user` VALUES (2, 'test', '普通用户', '$2a$10$VhzOG7gA0FQyVTyir0PADOIDa7y9jgfeU6XXD5VjDWMpflZEiAia.', '', '13750728507', '//dips-cloud-gov.oss-cn-hangzhou.aliyuncs.com/upload/images/201811/ec35dc33-1248-4f5a-90e0-bd1bb346ce35.jpg', 1, '普通测试部门', '2018-11-21 09:23:59', '2018-12-24 10:21:30', '0', '', '');
INSERT INTO `gov_user` VALUES (3, 'zc', '测试人员', '', '', '', '', 1, '', '2018-11-26 12:54:29', '2019-03-07 14:56:44', '1', '', '');
INSERT INTO `gov_user` VALUES (4, 'cs', '测试人员', '$2a$10$2UGhOnDBAouO09rtateDOO.NYWfOlNc/iIn7nk9G6cQwOdH9uEH4.', '', '', '', 1, '', '2018-11-26 13:07:41', '2019-03-07 14:56:45', '1', '', '');
INSERT INTO `gov_user` VALUES (5, 'sadsada', '测试人员', '$2a$10$0lHag16j72O.tspWcu6pkenhEJ2m7xdpkm1sYhemi.TshKsMs06BO', '', '', '', 1, '', '2018-11-26 13:18:47', '2019-03-07 14:56:47', '1', '', '');
INSERT INTO `gov_user` VALUES (6, 'dsadsa', '测试人员', '$2a$10$j6AORjUsCycKXiT7ToFa4uIW7zK6zHltNCONkIALCn.0PU1ULykoK', '', '', '', 1, '', '2018-11-26 13:21:06', '2019-03-07 14:56:49', '1', '', '');
COMMIT;

-- ----------------------------
-- Table structure for gov_user_role
-- ----------------------------
DROP TABLE IF EXISTS `gov_user_role`;
CREATE TABLE `gov_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of gov_user_role
-- ----------------------------
BEGIN;
INSERT INTO `gov_user_role` VALUES (1, 1);
INSERT INTO `gov_user_role` VALUES (2, 1);
INSERT INTO `gov_user_role` VALUES (2, 2);
COMMIT;

-- ----------------------------
-- Table structure for job_execution_log
-- ----------------------------
DROP TABLE IF EXISTS `job_execution_log`;
CREATE TABLE `job_execution_log` (
  `id` varchar(40) NOT NULL,
  `job_name` varchar(100) NOT NULL,
  `task_id` varchar(255) NOT NULL,
  `hostname` varchar(255) NOT NULL,
  `ip` varchar(50) NOT NULL,
  `sharding_item` int(11) NOT NULL,
  `execution_source` varchar(20) NOT NULL,
  `failure_cause` varchar(4000) DEFAULT NULL,
  `is_success` int(11) NOT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `complete_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='执行工作日志';

-- ----------------------------
-- Table structure for job_status_trace_log
-- ----------------------------
DROP TABLE IF EXISTS `job_status_trace_log`;
CREATE TABLE `job_status_trace_log` (
  `id` varchar(40) NOT NULL,
  `job_name` varchar(100) NOT NULL,
  `original_task_id` varchar(255) NOT NULL,
  `task_id` varchar(255) NOT NULL,
  `slave_id` varchar(50) NOT NULL,
  `source` varchar(50) NOT NULL,
  `execution_type` varchar(20) NOT NULL,
  `sharding_item` varchar(100) NOT NULL,
  `state` varchar(20) NOT NULL,
  `message` varchar(4000) DEFAULT NULL,
  `creation_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `TASK_ID_STATE_INDEX` (`task_id`,`state`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作状态跟踪日志';

-- ----------------------------
-- Records of job_status_trace_log
-- ----------------------------
BEGIN;
INSERT INTO `job_status_trace_log` VALUES ('15e99f6f-6e81-40c8-844f-1c1d1a920e8b', 'spring-dataflow-job', '', 'spring-dataflow-job@-@@-@READY@-@192.168.1.142@-@3560', '192.168.1.142', 'LITE_EXECUTOR', 'READY', '[]', 'TASK_STAGING', 'Job \'spring-dataflow-job\' execute begin.', '2018-09-04 20:07:36');
INSERT INTO `job_status_trace_log` VALUES ('788a8ef2-746b-4e8e-b792-5feb36c32d0c', 'spring-simple-job2', '', 'spring-simple-job2@-@@-@READY@-@192.168.1.142@-@3560', '192.168.1.142', 'LITE_EXECUTOR', 'READY', '[]', 'TASK_FINISHED', 'Sharding item for job \'spring-simple-job2\' is empty.', '2018-09-04 20:07:36');
INSERT INTO `job_status_trace_log` VALUES ('85e9ca50-6207-461c-a213-1108a63df515', 'spring-simple-job', '', 'spring-simple-job@-@@-@READY@-@192.168.1.142@-@3560', '192.168.1.142', 'LITE_EXECUTOR', 'READY', '[]', 'TASK_FINISHED', 'Sharding item for job \'spring-simple-job\' is empty.', '2018-09-04 20:07:36');
INSERT INTO `job_status_trace_log` VALUES ('9179f1be-2f53-4f5d-94cd-e3e0c8f711dc', 'spring-dataflow-job', '', 'spring-dataflow-job@-@@-@READY@-@192.168.1.142@-@3560', '192.168.1.142', 'LITE_EXECUTOR', 'READY', '[]', 'TASK_FINISHED', 'Sharding item for job \'spring-dataflow-job\' is empty.', '2018-09-04 20:07:44');
INSERT INTO `job_status_trace_log` VALUES ('9e4f0deb-c396-4fad-8225-a25dd873268d', 'spring-simple-job2', '', 'spring-simple-job2@-@@-@READY@-@192.168.1.142@-@3560', '192.168.1.142', 'LITE_EXECUTOR', 'READY', '[]', 'TASK_STAGING', 'Job \'spring-simple-job2\' execute begin.', '2018-09-04 20:07:36');
INSERT INTO `job_status_trace_log` VALUES ('a5b96922-810b-4886-9e5f-20ff808ec680', 'spring-simple-job', '', 'spring-simple-job@-@@-@READY@-@192.168.1.142@-@3560', '192.168.1.142', 'LITE_EXECUTOR', 'READY', '[]', 'TASK_STAGING', 'Job \'spring-simple-job\' execute begin.', '2018-09-04 20:07:36');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
