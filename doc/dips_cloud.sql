/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : dips_cloud

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-10-24 11:53:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `gov_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `gov_attachment`;
CREATE TABLE `gov_attachment` (
  `g_file_id` int(11) NOT NULL AUTO_INCREMENT,
  `g_user_id` int(11) NOT NULL COMMENT '上传人',
  `g_url` varchar(150) CHARACTER SET utf8 NOT NULL COMMENT '文件名',
  `g_length` bigint(20) DEFAULT NULL COMMENT '文件长度',
  `g_ip` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'IP',
  `g_time` datetime NOT NULL COMMENT '时间',
  PRIMARY KEY (`g_file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='附件表';

-- ----------------------------
-- Records of gov_attachment
-- ----------------------------
INSERT INTO `gov_attachment` VALUES ('1', '1', 'http://192.168.1.188:4000/upload/imgs/20181024/1540350899700_251.jpg', '39229', '192.168.1.188', '2018-10-24 11:15:00');
INSERT INTO `gov_attachment` VALUES ('2', '1', 'http://192.168.1.188:4000/upload/imgs/20181024/1540350908037_45.jpg', '19145', '192.168.1.188', '2018-10-24 11:15:08');
INSERT INTO `gov_attachment` VALUES ('3', '1', 'http://192.168.1.188:4000/upload/imgs/20181024/1540350939555_262.jpg', '38282', '192.168.1.188', '2018-10-24 11:15:40');
INSERT INTO `gov_attachment` VALUES ('4', '1', 'http://192.168.1.188:4000/upload/imgs/20181024/1540352461154_406.jpg', '39229', '192.168.1.188', '2018-10-24 11:41:01');

-- ----------------------------
-- Table structure for `gov_dept`
-- ----------------------------
DROP TABLE IF EXISTS `gov_dept`;
CREATE TABLE `gov_dept` (
  `g_dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `g_dept_number` varchar(150) DEFAULT NULL COMMENT '部门编号',
  `g_dept_title` varchar(150) DEFAULT NULL COMMENT '部门名称',
  `g_creator_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `g_start_time` datetime DEFAULT NULL COMMENT '创建时间',
  `g_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `g_order_num` int(11) DEFAULT NULL COMMENT '排序',
  `g_category` varchar(150) DEFAULT NULL COMMENT '机构分类（自定义层级）',
  `g_isFinancial` int(11) DEFAULT NULL COMMENT '是否财务结算公司',
  `g_isIntranet` int(11) DEFAULT NULL COMMENT '是否内网结算部门',
  `g_image` varchar(255) DEFAULT NULL COMMENT '宣传图路径',
  `g_dept_input` varchar(150) DEFAULT NULL COMMENT '联系方式',
  `g_status` char(1) DEFAULT '0' COMMENT '状态(是否删除  -1：已删除  0：正常)',
  `g_parent_id` int(11) DEFAULT NULL COMMENT '父级ID',
  PRIMARY KEY (`g_dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门管理';

-- ----------------------------
-- Records of gov_dept
-- ----------------------------
INSERT INTO `gov_dept` VALUES ('1', 'FFFFFF', '国脉集团', '8', '2018-08-06 17:02:12', '2018-08-24 15:31:32', '1', '第三方', '0', '0', 'fff', '双方各', '0', '0');

-- ----------------------------
-- Table structure for `gov_dept_clob`
-- ----------------------------
DROP TABLE IF EXISTS `gov_dept_clob`;
CREATE TABLE `gov_dept_clob` (
  `g_dept_id` int(11) NOT NULL COMMENT '部门ID',
  `g_key` varchar(50) NOT NULL COMMENT '键',
  `g_value` mediumtext COMMENT '值',
  KEY `g_dept_id` (`g_dept_id`),
  CONSTRAINT `gov_dept_clob_ibfk_1` FOREIGN KEY (`g_dept_id`) REFERENCES `gov_dept` (`g_dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门简介，组织架构，核心优势';

-- ----------------------------
-- Records of gov_dept_clob
-- ----------------------------

-- ----------------------------
-- Table structure for `gov_dept_relation`
-- ----------------------------
DROP TABLE IF EXISTS `gov_dept_relation`;
CREATE TABLE `gov_dept_relation` (
  `g_ancestor` int(11) NOT NULL COMMENT '祖先节点',
  `g_descendant` int(11) NOT NULL COMMENT '后代节点',
  PRIMARY KEY (`g_ancestor`,`g_descendant`),
  KEY `idx1` (`g_ancestor`),
  KEY `idx2` (`g_descendant`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='部门关系表';

-- ----------------------------
-- Records of gov_dept_relation
-- ----------------------------

-- ----------------------------
-- Table structure for `gov_dict`
-- ----------------------------
DROP TABLE IF EXISTS `gov_dict`;
CREATE TABLE `gov_dict` (
  `g_id` int(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `g_value` varchar(100) NOT NULL COMMENT '数据值',
  `g_label` varchar(100) NOT NULL COMMENT '标签名',
  `g_type` varchar(100) NOT NULL COMMENT '编码',
  `g_description` varchar(100) NOT NULL COMMENT '描述',
  `g_sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `g_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `g_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `g_remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `g_del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`g_id`),
  KEY `sys_dict_value` (`g_value`) USING BTREE,
  KEY `sys_dict_label` (`g_label`) USING BTREE,
  KEY `sys_dict_del_flag` (`g_del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of gov_dict
-- ----------------------------
INSERT INTO `gov_dict` VALUES ('1', '0', '正常', 'log_type', '日志状态', '1', '2018-08-17 14:38:24', '2018-08-17 14:38:24', '日志正常', '0');
INSERT INTO `gov_dict` VALUES ('2', '1', '异常', 'log_type', '日志状态', '1', '2018-08-20 13:28:13', '2018-08-20 13:28:13', '日志异常', '0');
INSERT INTO `gov_dict` VALUES ('3', '1', '通知公告', 'notify_type', '通知类型', '2', '2018-08-20 11:21:09', '2018-08-20 11:21:09', '通知公告', '0');
INSERT INTO `gov_dict` VALUES ('4', '2', '领导指示', 'notify_type', '通知类型', '2', '2018-08-20 11:21:15', '2018-08-20 11:21:15', '领导指示', '0');
INSERT INTO `gov_dict` VALUES ('6', '1', '纪要', 'system_info_type', '系统消息分类', '1', '2018-09-07 15:42:41', '2018-09-07 15:42:41', '纪要', '0');
INSERT INTO `gov_dict` VALUES ('7', '2', '任务', 'system_info_type', '系统消息分类', '2', '2018-09-07 15:42:40', '2018-09-07 15:42:40', '任务', '0');
INSERT INTO `gov_dict` VALUES ('8', '3', '财富', 'system_info_type', '系统消息分类', '3', '2018-09-07 15:42:39', '2018-09-07 15:42:39', '财富', '0');
INSERT INTO `gov_dict` VALUES ('9', '4', '其他', 'system_info_type', '系统消息分类', '4', '2018-09-07 15:42:37', '2018-09-07 15:42:37', '其他', '0');
INSERT INTO `gov_dict` VALUES ('10', '5', '重要', 'system_info_type', '系统消息分类', '5', '2018-09-07 15:42:36', '2018-09-07 15:42:36', '重要', '0');
INSERT INTO `gov_dict` VALUES ('11', '1', '是', 'yes_or_no', '是否', '1', '2018-08-20 17:15:47', '2018-08-20 17:15:47', '是', '0');
INSERT INTO `gov_dict` VALUES ('12', '2', '否', 'yes_or_no', '是否', '2', '2018-08-20 17:15:47', '2018-08-20 17:15:47', '否', '0');
INSERT INTO `gov_dict` VALUES ('13', '1', 'DNA', 'base_frame', '底层框架', '1', '2018-10-18 16:04:18', '2018-10-18 16:04:18', 'DNA', '0');
INSERT INTO `gov_dict` VALUES ('14', '2', '水巢', 'base_frame', '底层框架', '2', '2018-08-20 17:15:47', '2018-08-20 17:15:47', '水巢', '0');
INSERT INTO `gov_dict` VALUES ('15', '3', '慧企', 'base_frame', '底层框架', '3', '2018-08-20 17:15:47', '2018-08-20 17:15:47', '慧企', '0');
INSERT INTO `gov_dict` VALUES ('16', '1', '咨询', 'work_style', '业务类型', '1', '2018-08-20 17:15:47', '2018-08-20 17:15:47', '咨询', '0');
INSERT INTO `gov_dict` VALUES ('17', '2', '产品', 'work_style', '业务类型', '2', '2018-08-20 17:15:47', '2018-08-20 17:15:47', '产品', '0');
INSERT INTO `gov_dict` VALUES ('18', '3', '数据', 'work_style', '业务类型', '3', '2018-08-20 17:15:47', '2018-08-20 17:15:47', '数据', '0');
INSERT INTO `gov_dict` VALUES ('19', '4', '外包', 'work_style', '业务类型', '4', '2018-08-20 17:15:47', '2018-08-20 17:15:47', '外包', '0');
INSERT INTO `gov_dict` VALUES ('20', '5', '会议培训', 'work_style', '业务类型', '5', '2018-08-20 17:30:01', '2018-08-20 17:30:01', '会议培训', '0');
INSERT INTO `gov_dict` VALUES ('21', '6', '平台', 'work_style', '业务类型', '6', '2018-08-20 17:30:04', '2018-08-20 17:30:04', '平台', '0');
INSERT INTO `gov_dict` VALUES ('22', '7', '技术服务', 'work_style', '业务类型', '7', '2018-08-20 17:30:05', '2018-08-20 17:30:05', '技术服务', '0');
INSERT INTO `gov_dict` VALUES ('23', '8', '其它', 'work_style', '业务类型', '8', '2018-08-20 17:30:08', '2018-08-20 17:30:08', '其它', '0');
INSERT INTO `gov_dict` VALUES ('24', '1', '完结', 'project_status', '项目状态', '1', '2018-08-20 17:30:08', '2018-08-20 17:30:08', '完结', '0');
INSERT INTO `gov_dict` VALUES ('25', '2', '筹备', 'project_status', '项目状态', '2', '2018-08-20 17:31:54', '2018-08-20 17:31:54', '筹备', '0');
INSERT INTO `gov_dict` VALUES ('26', '3', '执行', 'project_status', '项目状态', '3', '2018-08-20 17:31:51', '2018-08-20 17:31:51', '执行', '0');
INSERT INTO `gov_dict` VALUES ('27', '1', '核心客户', 'customer_relation', '客户关系', '1', '2018-08-20 17:37:29', '2018-08-20 17:37:29', '核心客户', '0');
INSERT INTO `gov_dict` VALUES ('28', '2', '重要客户', 'customer_relation', '客户关系', '2', '2018-08-20 17:37:29', '2018-08-20 17:37:29', '重要客户', '0');
INSERT INTO `gov_dict` VALUES ('29', '3', '一般客户', 'customer_relation', '客户关系', '3', '2018-08-20 17:37:29', '2018-08-20 17:37:29', '一般客户', '0');
INSERT INTO `gov_dict` VALUES ('30', '4', '潜在客户', 'customer_relation', '客户关系', '4', '2018-08-20 17:37:29', '2018-08-20 17:37:29', '潜在客户', '0');
INSERT INTO `gov_dict` VALUES ('31', '5', '其它客户', 'customer_relation', '客户关系', '5', '2018-08-20 20:31:11', '2018-08-20 20:31:11', '其它客户', '0');
INSERT INTO `gov_dict` VALUES ('32', '1', '方案可研', 'materials_type', '材料类型', '1', '2018-08-22 11:45:39', '2018-08-22 11:45:39', '方案可研', '0');
INSERT INTO `gov_dict` VALUES ('33', '2', '过程可研', 'materials_type', '材料类型', '2', '2018-08-22 11:45:41', '2018-08-22 11:45:41', '过程可研', '0');
INSERT INTO `gov_dict` VALUES ('34', '3', '结果文档', 'materials_type', '材料类型', '3', '2018-08-22 11:45:43', '2018-08-22 11:45:43', '结果文档', '0');
INSERT INTO `gov_dict` VALUES ('35', '4', '制度文件', 'materials_type', '材料类型', '4', '2018-08-22 11:45:45', '2018-08-22 11:45:45', '制度文件', '0');
INSERT INTO `gov_dict` VALUES ('36', '5', '领导讲话', 'materials_type', '材料类型', '5', '2018-08-22 11:45:46', '2018-08-22 11:45:46', '领导讲话', '0');
INSERT INTO `gov_dict` VALUES ('37', '6', '例会纪要', 'materials_type', '材料类型', '6', '2018-08-22 11:45:48', '2018-08-22 11:45:48', '例会纪要', '0');
INSERT INTO `gov_dict` VALUES ('38', '7', '专题会议', 'materials_type', '材料类型', '7', '2018-08-22 11:45:49', '2018-08-22 11:45:49', '专题会议', '0');
INSERT INTO `gov_dict` VALUES ('39', '8', '荣誉资质', 'materials_type', '材料类型', '8', '2018-08-22 11:45:52', '2018-08-22 11:45:52', '荣誉资质', '0');
INSERT INTO `gov_dict` VALUES ('40', '9', '基础材料类', 'materials_type', '材料类型', '9', '2018-08-22 11:45:53', '2018-08-22 11:45:53', '基础材料类', '0');
INSERT INTO `gov_dict` VALUES ('41', '10', '外部资料', 'materials_type', '材料类型', '10', '2018-08-22 11:45:55', '2018-08-22 11:45:55', '外部资料', '0');
INSERT INTO `gov_dict` VALUES ('42', '11', '考题库', 'materials_type', '材料类型', '11', '2018-08-22 11:45:57', '2018-08-22 11:45:57', '考题库', '0');
INSERT INTO `gov_dict` VALUES ('43', '12', '内部培训材料', 'materials_type', '材料类型', '12', '2018-08-22 11:46:00', '2018-08-22 11:46:00', '内部培训材料', '0');
INSERT INTO `gov_dict` VALUES ('44', '13', '数字广东材料', 'materials_type', '材料类型', '13', '2018-08-22 11:46:01', '2018-08-22 11:46:01', '数字广东材料', '0');
INSERT INTO `gov_dict` VALUES ('45', '14', '社保及交税材料', 'materials_type', '材料类型', '14', '2018-08-22 11:46:02', '2018-08-22 11:46:02', '社保及交税材料', '0');
INSERT INTO `gov_dict` VALUES ('46', '15', '新生必读', 'materials_type', '材料类型', '15', '2018-08-22 11:46:06', '2018-08-22 11:46:06', '新生必读', '0');
INSERT INTO `gov_dict` VALUES ('47', '16', '组织材料', 'materials_type', '材料类型', '16', '2018-08-22 11:46:17', '2018-08-22 11:46:17', '组织材料', '0');
INSERT INTO `gov_dict` VALUES ('48', '1', 'A', 'company_level', '单位等级', '1', '2018-08-20 20:31:16', '2018-08-20 20:31:16', 'A', '0');
INSERT INTO `gov_dict` VALUES ('49', '2', 'B', 'company_level', '单位等级', '2', '2018-08-20 20:43:57', '2018-08-20 20:43:57', 'B', '0');
INSERT INTO `gov_dict` VALUES ('50', '3', 'C', 'company_level', '单位等级', '3', '2018-08-20 20:44:03', '2018-08-20 20:44:03', 'C', '0');
INSERT INTO `gov_dict` VALUES ('51', '4', 'D', 'company_level', '单位等级', '4', '2018-08-20 20:44:03', '2018-08-20 20:44:03', 'D', '0');
INSERT INTO `gov_dict` VALUES ('52', '5', 'E', 'company_level', '单位等级', '5', '2018-08-20 20:44:03', '2018-08-20 20:44:03', 'E', '0');
INSERT INTO `gov_dict` VALUES ('53', '1', '部委', 'customer_type', '客户类型', '1', '2018-08-30 14:44:12', '2018-08-30 14:44:12', '部委', '0');
INSERT INTO `gov_dict` VALUES ('54', '2', '省级', 'customer_type', '客户类型', '2', '2018-08-30 14:43:53', '2018-08-30 14:43:55', '省级', '0');
INSERT INTO `gov_dict` VALUES ('55', '3', '地区', 'customer_type', '客户类型', '3', '2018-08-30 14:45:21', '2018-08-30 14:45:24', '地区', '0');
INSERT INTO `gov_dict` VALUES ('56', '4', '县区', 'customer_type', '客户类型', '4', '2018-08-30 14:46:43', '2018-08-30 14:46:47', '县区', '0');
INSERT INTO `gov_dict` VALUES ('57', '5', '园区', 'customer_type', '客户类型', '5', '2018-08-30 14:48:46', '2018-08-30 14:48:49', '园区', '0');
INSERT INTO `gov_dict` VALUES ('58', '6', '企业', 'customer_type', '客户类型', '6', '2018-08-30 14:49:38', '2018-08-30 14:49:41', '企业', '0');
INSERT INTO `gov_dict` VALUES ('59', '1', '已合作', 'maintain_condition', '维护情况', '1', '2018-08-30 14:56:26', '2018-08-30 14:56:29', '已合作', '0');
INSERT INTO `gov_dict` VALUES ('60', '2', '已确定合作意向', 'maintain_condition', '维护情况', '2', '2018-08-30 15:04:18', '2018-08-30 15:04:20', '已确定合作意向', '0');
INSERT INTO `gov_dict` VALUES ('61', '3', '正在引导客户合作', 'maintain_condition', '维护情况', '3', '2018-08-30 15:08:38', '2018-08-30 15:08:42', '正在引导客户合作', '0');
INSERT INTO `gov_dict` VALUES ('62', '4', '暂无需求', 'maintain_condition', '维护情况', '4', '2018-08-30 15:09:49', '2018-08-30 15:09:53', '暂无需求', '0');
INSERT INTO `gov_dict` VALUES ('63', '5', '客户投诉或敌对', 'maintain_condition', '维护情况', '5', '2018-08-30 15:10:43', '2018-08-30 15:10:46', '客户投诉或敌对', '0');
INSERT INTO `gov_dict` VALUES ('64', '1', '内部', 'train_object', '培训对象', '1', '2018-09-04 16:24:08', '2018-09-04 16:24:16', '培训对象', '0');
INSERT INTO `gov_dict` VALUES ('65', '2', '外部', 'train_object', '培训对象', '1', '2018-09-04 16:25:30', '2018-09-04 16:25:33', '培训对象', '0');
INSERT INTO `gov_dict` VALUES ('66', '1', '线上', 'train_form', '培训形式', '1', '2018-09-04 16:27:00', '2018-09-04 16:27:03', '培训形式', '0');
INSERT INTO `gov_dict` VALUES ('67', '2', '线下', 'train_form', '培训形式', '1', '2018-09-15 10:21:06', '2018-09-15 10:21:06', '培训形式', '0');
INSERT INTO `gov_dict` VALUES ('74', '1', '管理会议', 'gov_conference_type', '会议类型', '1', '2018-08-30 14:39:51', '2018-08-30 14:39:51', '管理会议', '0');
INSERT INTO `gov_dict` VALUES ('75', '2', '市场会议', 'gov_conference_type', '会议类型', '2', '2018-08-30 14:42:00', '2018-08-30 14:42:00', '市场会议', '0');
INSERT INTO `gov_dict` VALUES ('76', '3', '项目会议', 'gov_conference_type', '会议类型', '3', '2018-08-30 14:41:41', '2018-08-30 14:41:41', '项目会议', '0');
INSERT INTO `gov_dict` VALUES ('77', '4', '产品会议', 'gov_conference_type', '会议类型', '4', '2018-08-30 14:41:49', '2018-08-30 14:41:49', '产品会议', '0');
INSERT INTO `gov_dict` VALUES ('78', '5', '培训会议', 'gov_conference_type', '会议类型', '5', '2018-08-30 14:41:35', '2018-08-30 14:41:35', '培训会议', '0');
INSERT INTO `gov_dict` VALUES ('79', '6', '其它', 'gov_conference_type', '会议类型', '6', '2018-08-30 14:41:25', '2018-08-30 14:41:25', '其它', '0');

-- ----------------------------
-- Table structure for `gov_log`
-- ----------------------------
DROP TABLE IF EXISTS `gov_log`;
CREATE TABLE `gov_log` (
  `g_id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `g_type` char(1) DEFAULT '1' COMMENT '日志类型',
  `g_title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `g_service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
  `g_create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `g_create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `g_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `g_remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `g_user_agent` varchar(1000) DEFAULT NULL COMMENT '用户代理',
  `g_request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `g_method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `g_params` text COMMENT '操作提交的数据',
  `g_time` mediumtext COMMENT '执行时间',
  `g_del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `g_exception` text COMMENT '异常信息',
  PRIMARY KEY (`g_id`),
  KEY `sys_log_create_by` (`g_create_by`),
  KEY `sys_log_request_uri` (`g_request_uri`),
  KEY `sys_log_type` (`g_type`),
  KEY `sys_log_create_date` (`g_create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='日志表';

-- ----------------------------
-- Records of gov_log
-- ----------------------------
INSERT INTO `gov_log` VALUES ('1', '0', '上传用户头像', 'dips', 'admin', '2018-10-24 11:15:00', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/upload/uploadAvatar', 'POST', 'file=%5B%5D&userId=%5B1%5D', '384', '0', null);
INSERT INTO `gov_log` VALUES ('2', '0', '上传用户头像', 'dips', 'admin', '2018-10-24 11:15:08', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/upload/uploadAvatar', 'POST', 'file=%5B%5D&userId=%5B1%5D', '269', '0', null);
INSERT INTO `gov_log` VALUES ('3', '0', '上传用户头像', 'dips', 'admin', '2018-10-24 11:15:39', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/upload/uploadAvatar', 'POST', 'file=%5B%5D&userId=%5B1%5D', '63', '0', null);
INSERT INTO `gov_log` VALUES ('4', '0', '添加标签关联', 'dips', 'admin', '2018-10-24 11:23:11', null, '192.168.1.188', 'okhttp/3.10.0', '/tagRelation/saveTagRelation', 'POST', 'number=%5Bability%5D&tagKeyWords=%5B%E5%A4%A7%E6%95%B0%E6%8D%AE%5D&gNode=%5Buser%5D&gRelationId=%5B1%5D', '208', '0', null);
INSERT INTO `gov_log` VALUES ('5', '0', '添加标签关联', 'dips', 'admin', '2018-10-24 11:23:11', null, '192.168.1.188', 'okhttp/3.10.0', '/tagRelation/saveTagRelation', 'POST', 'number=%5Bproject%5D&tagKeyWords=%5B%5D&gNode=%5Buser%5D&gRelationId=%5B1%5D', '7', '0', null);
INSERT INTO `gov_log` VALUES ('6', '0', '添加标签关联', 'dips', 'admin', '2018-10-24 11:23:12', null, '192.168.1.188', 'okhttp/3.10.0', '/tagRelation/saveTagRelation', 'POST', 'number=%5Blearning%5D&tagKeyWords=%5B%5D&gNode=%5Buser%5D&gRelationId=%5B1%5D', '29', '0', null);
INSERT INTO `gov_log` VALUES ('7', '0', '添加标签关联', 'dips', 'admin', '2018-10-24 11:27:27', null, '192.168.1.188', 'okhttp/3.10.0', '/tagRelation/saveTagRelation', 'POST', 'number=%5Bability%5D&tagKeyWords=%5B%E5%A4%A7%E6%95%B0%E6%8D%AE%5D&gNode=%5Buser%5D&gRelationId=%5B1%5D', '228', '0', null);
INSERT INTO `gov_log` VALUES ('8', '0', '添加标签关联', 'dips', 'admin', '2018-10-24 11:27:28', null, '192.168.1.188', 'okhttp/3.10.0', '/tagRelation/saveTagRelation', 'POST', 'number=%5Bproject%5D&tagKeyWords=%5B%5D&gNode=%5Buser%5D&gRelationId=%5B1%5D', '7', '0', null);
INSERT INTO `gov_log` VALUES ('9', '0', '添加标签关联', 'dips', 'admin', '2018-10-24 11:27:28', null, '192.168.1.188', 'okhttp/3.10.0', '/tagRelation/saveTagRelation', 'POST', 'number=%5Blearning%5D&tagKeyWords=%5B%5D&gNode=%5Buser%5D&gRelationId=%5B1%5D', '23', '0', null);
INSERT INTO `gov_log` VALUES ('10', '0', '上传用户头像', 'dips', 'admin', '2018-10-24 11:41:01', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36', '/upload/uploadAvatar', 'POST', 'file=%5B%5D&userId=%5B1%5D', '557', '0', null);
INSERT INTO `gov_log` VALUES ('11', '0', '添加标签关联', 'dips', 'admin', '2018-10-24 11:41:15', null, '192.168.1.188', 'okhttp/3.10.0', '/tagRelation/saveTagRelation', 'POST', 'number=%5Bability%5D&tagKeyWords=%5B%E5%A4%A7%E6%95%B0%E6%8D%AE%5D&gNode=%5Buser%5D&gRelationId=%5B1%5D', '702', '0', null);
INSERT INTO `gov_log` VALUES ('12', '0', '添加标签关联', 'dips', 'admin', '2018-10-24 11:41:15', null, '192.168.1.188', 'okhttp/3.10.0', '/tagRelation/saveTagRelation', 'POST', 'number=%5Bproject%5D&tagKeyWords=%5B%E5%A4%A7%E6%95%B0%E6%8D%AE%5D&gNode=%5Buser%5D&gRelationId=%5B1%5D', '85', '0', null);
INSERT INTO `gov_log` VALUES ('13', '0', '添加标签关联', 'dips', 'admin', '2018-10-24 11:41:16', null, '192.168.1.188', 'okhttp/3.10.0', '/tagRelation/saveTagRelation', 'POST', 'number=%5Blearning%5D&tagKeyWords=%5B%5D&gNode=%5Buser%5D&gRelationId=%5B1%5D', '8', '0', null);

-- ----------------------------
-- Table structure for `gov_menu`
-- ----------------------------
DROP TABLE IF EXISTS `gov_menu`;
CREATE TABLE `gov_menu` (
  `g_menu_id` int(11) NOT NULL COMMENT '菜单ID',
  `g_name` varchar(32) NOT NULL COMMENT '菜单名称',
  `g_permission` varchar(32) DEFAULT NULL COMMENT '菜单权限标识',
  `g_path` varchar(255) DEFAULT NULL COMMENT '前端URL',
  `g_parent_id` int(11) DEFAULT NULL COMMENT '父菜单ID',
  `g_icon` varchar(32) DEFAULT NULL COMMENT '图标',
  `g_component` varchar(255) DEFAULT NULL COMMENT 'VUE页面',
  `g_sort` int(11) DEFAULT '1' COMMENT '排序值',
  `g_type` char(1) DEFAULT NULL COMMENT '菜单类型 （0菜单 1按钮）',
  `g_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `g_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `g_del_flag` char(1) DEFAULT '0' COMMENT '0--正常 1--删除',
  PRIMARY KEY (`g_menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';

-- ----------------------------
-- Records of gov_menu
-- ----------------------------
INSERT INTO `gov_menu` VALUES ('1', '系统管理', null, '/admin', '-1', 'icon-xitongguanli', 'Layout', '11', '0', '2017-11-07 20:56:00', '2018-08-02 11:03:27', '0');
INSERT INTO `gov_menu` VALUES ('2', '用户管理', null, 'user', '1', 'icon-yonghuguanli', 'views/admin/user/index', '2', '0', '2017-11-02 22:24:37', '2018-05-14 22:11:35', '0');
INSERT INTO `gov_menu` VALUES ('3', '菜单管理', null, 'menu', '1', 'icon-caidanguanli', 'views/admin/menu/index', '3', '0', '2017-11-08 09:57:27', '2018-05-14 22:11:21', '0');
INSERT INTO `gov_menu` VALUES ('4', '角色管理', null, 'role', '1', 'icon-jiaoseguanli', 'views/admin/role/index', '4', '0', '2017-11-08 10:13:37', '2018-05-14 22:11:19', '0');
INSERT INTO `gov_menu` VALUES ('5', '日志管理', null, 'log', '1', 'icon-rizhiguanli', 'views/admin/log/index', '5', '0', '2017-11-20 14:06:22', '2018-05-14 22:11:18', '0');
INSERT INTO `gov_menu` VALUES ('6', '字典管理', null, 'dict', '1', 'icon-zygl', 'views/admin/dict/index', '6', '0', '2017-11-29 11:30:52', '2018-05-14 22:12:48', '0');
INSERT INTO `gov_menu` VALUES ('7', '部门管理', null, 'dept', '1', 'icon-iconbmgl', 'views/admin/dept/index', '7', '0', '2018-01-20 13:17:19', '2018-05-14 22:11:16', '0');
INSERT INTO `gov_menu` VALUES ('8', '系统监控', null, '', '-1', 'icon-iconbmgl', null, '12', '0', '2018-01-22 12:30:41', '2018-08-02 11:03:29', '0');
INSERT INTO `gov_menu` VALUES ('9', '服务监控', null, 'http://127.0.0.1:5001', '8', 'icon-jiankong', null, '9', '0', '2018-01-23 10:53:33', '2018-08-02 11:08:37', '0');
INSERT INTO `gov_menu` VALUES ('10', '接口文档', null, 'http://127.0.0.1:9999/swagger-ui.html', '8', 'icon-wendangdocument72', null, '14', '0', '2018-01-23 10:56:43', '2018-10-24 10:15:10', '0');
INSERT INTO `gov_menu` VALUES ('11', '用户新增', 'sys_user_add', null, '2', null, null, null, '1', '2017-11-08 09:52:09', '2018-10-24 10:15:12', '0');
INSERT INTO `gov_menu` VALUES ('12', '用户修改', 'sys_user_edit', null, '2', null, null, null, '1', '2017-11-08 09:52:48', '2018-10-24 10:15:13', '0');
INSERT INTO `gov_menu` VALUES ('13', '用户删除', 'sys_user_del', null, '2', null, null, null, '1', '2017-11-08 09:54:01', '2018-10-24 10:15:14', '0');
INSERT INTO `gov_menu` VALUES ('14', '菜单新增', 'sys_menu_add', null, '3', null, null, null, '1', '2017-11-08 10:15:53', '2018-10-24 10:15:15', '0');
INSERT INTO `gov_menu` VALUES ('15', '菜单修改', 'sys_menu_edit', null, '3', null, null, null, '1', '2017-11-08 10:16:23', '2018-10-24 10:15:18', '0');
INSERT INTO `gov_menu` VALUES ('16', '菜单删除', 'sys_menu_del', null, '3', null, null, null, '1', '2017-11-08 10:16:43', '2018-10-24 10:15:20', '0');
INSERT INTO `gov_menu` VALUES ('17', '角色新增', 'sys_role_add', null, '4', null, null, null, '1', '2017-11-08 10:14:18', '2018-10-24 10:15:22', '0');
INSERT INTO `gov_menu` VALUES ('18', '角色修改', 'sys_role_edit', null, '4', null, null, null, '1', '2017-11-08 10:14:41', '2018-10-24 10:15:24', '0');
INSERT INTO `gov_menu` VALUES ('19', '角色删除', 'sys_role_del', null, '4', null, null, null, '1', '2017-11-08 10:14:59', '2018-10-24 10:15:26', '0');
INSERT INTO `gov_menu` VALUES ('20', '分配权限', 'sys_role_perm', null, '4', null, null, null, '1', '2018-04-20 07:22:55', '2018-10-24 10:15:30', '0');
INSERT INTO `gov_menu` VALUES ('21', '日志删除', 'sys_log_del', null, '5', null, null, null, '1', '2017-11-20 20:37:37', '2018-10-24 10:15:32', '0');
INSERT INTO `gov_menu` VALUES ('22', '字典删除', 'sys_dict_del', null, '6', null, null, null, '1', '2017-11-29 11:30:11', '2018-10-24 10:15:33', '0');
INSERT INTO `gov_menu` VALUES ('23', '字典新增', 'sys_dict_add', null, '6', null, null, null, '1', '2018-05-11 22:34:55', '2018-10-24 10:15:35', '0');
INSERT INTO `gov_menu` VALUES ('24', '字典修改', 'sys_dict_edit', null, '6', null, null, null, '1', '2018-05-11 22:36:03', '2018-10-24 10:15:36', '0');
INSERT INTO `gov_menu` VALUES ('25', '部门新增', 'sys_dept_add', null, '7', null, null, null, '1', '2018-01-20 14:56:16', '2018-10-24 10:15:37', '0');
INSERT INTO `gov_menu` VALUES ('26', '部门修改', 'sys_dept_edit', null, '7', null, null, null, '1', '2018-01-20 14:56:59', '2018-10-24 10:15:39', '0');
INSERT INTO `gov_menu` VALUES ('27', '部门删除', 'sys_dept_del', null, '7', null, null, null, '1', '2018-01-20 14:57:28', '2018-10-24 10:15:41', '0');
INSERT INTO `gov_menu` VALUES ('28', '客户端管理', '', 'client', '1', 'icon-bangzhushouji', 'views/admin/client/index', '10', '0', '2018-01-20 13:17:19', '2018-10-24 10:15:42', '0');
INSERT INTO `gov_menu` VALUES ('29', '客户端新增', 'sys_client_add', '', '28', '1', '', null, '1', '2018-05-15 21:35:18', '2018-10-24 11:19:46', '0');
INSERT INTO `gov_menu` VALUES ('30', '客户端修改', 'sys_client_edit', '', '28', '', '', null, '1', '2018-05-15 21:37:06', '2018-10-24 11:19:47', '0');
INSERT INTO `gov_menu` VALUES ('31', '客户端删除', 'sys_client_del', '', '28', '', '', null, '1', '2018-05-15 21:39:16', '2018-10-24 11:19:48', '0');
INSERT INTO `gov_menu` VALUES ('32', '令牌管理', '', 'token', '1', 'icon-key', 'views/admin/token/index', '11', '0', '2018-10-18 14:15:03', '2018-10-24 10:15:48', '0');
INSERT INTO `gov_menu` VALUES ('33', '令牌删除', 'sys_token_del', null, '32', null, null, '1', null, '2018-10-18 14:18:59', '2018-10-24 11:19:53', '0');
INSERT INTO `gov_menu` VALUES ('34', '关联管理', null, 'relation', '1', 'icon-weibiaoti46', 'views/admin/relation/index', '8', '0', '2018-09-04 16:17:17', '2018-10-24 10:15:51', '0');
INSERT INTO `gov_menu` VALUES ('35', '标签管理', null, '/tag', '-1', 'el-icon-tickets', 'Layout', '7', '0', '2018-08-17 08:21:04', '2018-10-24 10:18:05', '0');
INSERT INTO `gov_menu` VALUES ('36', '标签管理', null, 'bqgl', '35', 'icon-layers', 'views/tag/tag_manager/index', '1', '0', '2018-08-17 08:25:49', '2018-10-24 11:19:25', '0');
INSERT INTO `gov_menu` VALUES ('37', '标签属性', null, 'bqsx', '35', 'el-icon-edit', 'views/tag/tag_prop/index', '2', '0', '2018-08-17 08:27:00', '2018-10-24 11:19:27', '0');
INSERT INTO `gov_menu` VALUES ('38', '代码生成', null, 'gen', '1', 'icon-weibiaoti46', 'views/admin/gen/index', '10', '0', '2018-09-01 14:33:52', '2018-10-24 10:18:12', '0');
INSERT INTO `gov_menu` VALUES ('39', '密钥管理', null, 'social', '1', 'icon-social', 'views/admin/social/index', '11', '0', '2018-09-01 14:35:07', '2018-10-24 10:18:14', '0');
INSERT INTO `gov_menu` VALUES ('40', '密钥新增', null, null, '39', '1', null, '0', '1', '2018-09-01 14:36:01', '2018-10-24 11:19:35', '0');
INSERT INTO `gov_menu` VALUES ('41', '密钥删除', null, null, '39', '1', null, '2', '1', '2018-09-01 14:37:09', '2018-10-24 11:19:38', '0');
INSERT INTO `gov_menu` VALUES ('42', '密钥修改', '', null, '39', '1', null, '1', '1', '2018-09-01 14:36:42', '2018-10-24 11:19:40', '0');

-- ----------------------------
-- Table structure for `gov_oauth_client_details`
-- ----------------------------
DROP TABLE IF EXISTS `gov_oauth_client_details`;
CREATE TABLE `gov_oauth_client_details` (
  `g_client_id` varchar(32) NOT NULL,
  `g_resource_ids` varchar(256) DEFAULT NULL,
  `g_client_secret` varchar(256) DEFAULT NULL,
  `g_scope` varchar(256) DEFAULT NULL,
  `g_authorized_grant_types` varchar(256) DEFAULT NULL,
  `g_web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `g_authorities` varchar(256) DEFAULT NULL,
  `g_access_token_validity` int(11) DEFAULT NULL,
  `g_refresh_token_validity` int(11) DEFAULT NULL,
  `g_additional_information` varchar(4096) DEFAULT NULL,
  `g_autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`g_client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='统一认证表';

-- ----------------------------
-- Records of gov_oauth_client_details
-- ----------------------------
INSERT INTO `gov_oauth_client_details` VALUES ('dips', null, 'dips', 'server', 'password,refresh_token', null, null, null, null, null, 'true');
INSERT INTO `gov_oauth_client_details` VALUES ('test', null, 'test', 'server', 'password,refresh_token', null, null, null, null, null, 'true');

-- ----------------------------
-- Table structure for `gov_relation`
-- ----------------------------
DROP TABLE IF EXISTS `gov_relation`;
CREATE TABLE `gov_relation` (
  `g_node` varchar(60) NOT NULL COMMENT '关联编码',
  `g_relation_id` int(11) NOT NULL COMMENT '关联ID',
  `g_correlation_id` int(11) NOT NULL COMMENT '被关联ID',
  `g_type_id` int(11) NOT NULL COMMENT '关联类型ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登记关联表';

-- ----------------------------
-- Records of gov_relation
-- ----------------------------

-- ----------------------------
-- Table structure for `gov_relation_type`
-- ----------------------------
DROP TABLE IF EXISTS `gov_relation_type`;
CREATE TABLE `gov_relation_type` (
  `g_type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关联类型ID',
  `g_type_name` varchar(50) NOT NULL COMMENT '关联类型名称',
  `g_type_number` varchar(50) NOT NULL COMMENT '关联类型编码',
  PRIMARY KEY (`g_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COMMENT='登记关联类型表';

-- ----------------------------
-- Records of gov_relation_type
-- ----------------------------
INSERT INTO `gov_relation_type` VALUES ('1', '相关客户', 'gov_customer_registration');
INSERT INTO `gov_relation_type` VALUES ('2', '项目经理', 'sys_user_manage');
INSERT INTO `gov_relation_type` VALUES ('3', '项目成员', 'sys_user_member');
INSERT INTO `gov_relation_type` VALUES ('4', '管理支撑者', 'sys_user_supper');
INSERT INTO `gov_relation_type` VALUES ('5', '市场经理', 'sys_user_market');
INSERT INTO `gov_relation_type` VALUES ('6', '承接部门', 'sys_dept_under');
INSERT INTO `gov_relation_type` VALUES ('7', '合作部门', 'sys_dept_cooper');
INSERT INTO `gov_relation_type` VALUES ('8', '项目合同', 'gov_bargain');
INSERT INTO `gov_relation_type` VALUES ('9', '相关产品', 'gov_product');
INSERT INTO `gov_relation_type` VALUES ('10', '相关材料', 'gov_materials');
INSERT INTO `gov_relation_type` VALUES ('12', '相关机构', 'gov_organization_related');
INSERT INTO `gov_relation_type` VALUES ('13', '联系人', 'gov_customer_contact');
INSERT INTO `gov_relation_type` VALUES ('14', '合作项目', 'gov_project_cooperation');
INSERT INTO `gov_relation_type` VALUES ('15', '关联部门', 'sys_dept_relation');
INSERT INTO `gov_relation_type` VALUES ('16', '客户标签', 'gov_customer_tag');
INSERT INTO `gov_relation_type` VALUES ('17', '关联标签', 'gov_train_tag');
INSERT INTO `gov_relation_type` VALUES ('18', '讲师', 'gov_teacher');
INSERT INTO `gov_relation_type` VALUES ('19', '培训资料', 'gov_information');
INSERT INTO `gov_relation_type` VALUES ('20', '关联项目', 'gov_relation_project');
INSERT INTO `gov_relation_type` VALUES ('21', '产品经理', 'gov_product_manager');
INSERT INTO `gov_relation_type` VALUES ('22', '产品标签', 'gov_product_tag');
INSERT INTO `gov_relation_type` VALUES ('23', '团队成员', 'gov_team_members');
INSERT INTO `gov_relation_type` VALUES ('24', '产品资质', 'gov_product_qualification');
INSERT INTO `gov_relation_type` VALUES ('27', '部门负责人', 'gov_dept_master');
INSERT INTO `gov_relation_type` VALUES ('28', '部门成员', 'gov_dept_member');
INSERT INTO `gov_relation_type` VALUES ('29', '部门助理', 'gov_dept_writer');
INSERT INTO `gov_relation_type` VALUES ('30', '拜访客户', 'gov_visit_user');
INSERT INTO `gov_relation_type` VALUES ('31', '参会人', 'gov_joinmeeting_user');
INSERT INTO `gov_relation_type` VALUES ('32', '抄送人', 'gov_send_user');
INSERT INTO `gov_relation_type` VALUES ('33', '会议主持人', 'conferenceHost');
INSERT INTO `gov_relation_type` VALUES ('34', '会议参会人', 'conferenCeattendees');
INSERT INTO `gov_relation_type` VALUES ('35', '会议抄送人', 'conferenCarbons');
INSERT INTO `gov_relation_type` VALUES ('36', '拜访日志', 'visitLog');
INSERT INTO `gov_relation_type` VALUES ('37', '产品', 'product');
INSERT INTO `gov_relation_type` VALUES ('38', '项目', 'project');
INSERT INTO `gov_relation_type` VALUES ('46', '签署部门', 'sys_dept_sign');
INSERT INTO `gov_relation_type` VALUES ('47', '业务类型', 'work_style');
INSERT INTO `gov_relation_type` VALUES ('48', '底层框架', 'g_bottom_fram');
INSERT INTO `gov_relation_type` VALUES ('49', '是否带库', 'g_is_library');
INSERT INTO `gov_relation_type` VALUES ('50', '拜访日志', 'visit_log');

-- ----------------------------
-- Table structure for `gov_role`
-- ----------------------------
DROP TABLE IF EXISTS `gov_role`;
CREATE TABLE `gov_role` (
  `g_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `g_role_name` varchar(64) COLLATE utf8mb4_bin NOT NULL,
  `g_role_code` varchar(64) COLLATE utf8mb4_bin NOT NULL,
  `g_role_desc` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `g_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `g_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `g_del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`g_role_id`),
  UNIQUE KEY `role_idx1_role_code` (`g_role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Records of gov_role
-- ----------------------------
INSERT INTO `gov_role` VALUES ('1', 'admin', 'ROLE_ADMIN', '超级管理员', '2017-10-29 15:45:51', '2018-04-22 11:40:29', '0');

-- ----------------------------
-- Table structure for `gov_role_dept`
-- ----------------------------
DROP TABLE IF EXISTS `gov_role_dept`;
CREATE TABLE `gov_role_dept` (
  `g_id` int(20) NOT NULL AUTO_INCREMENT,
  `g_role_id` int(20) DEFAULT NULL COMMENT '角色ID',
  `g_dept_id` int(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`g_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色与部门对应关系';

-- ----------------------------
-- Records of gov_role_dept
-- ----------------------------
INSERT INTO `gov_role_dept` VALUES ('35', '1', '1');

-- ----------------------------
-- Table structure for `gov_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `gov_role_menu`;
CREATE TABLE `gov_role_menu` (
  `g_role_id` int(11) NOT NULL COMMENT '角色ID',
  `g_menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`g_role_id`,`g_menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色菜单表';

-- ----------------------------
-- Records of gov_role_menu
-- ----------------------------
INSERT INTO `gov_role_menu` VALUES ('1', '1');
INSERT INTO `gov_role_menu` VALUES ('1', '2');
INSERT INTO `gov_role_menu` VALUES ('1', '3');
INSERT INTO `gov_role_menu` VALUES ('1', '4');
INSERT INTO `gov_role_menu` VALUES ('1', '5');
INSERT INTO `gov_role_menu` VALUES ('1', '6');
INSERT INTO `gov_role_menu` VALUES ('1', '7');
INSERT INTO `gov_role_menu` VALUES ('1', '8');
INSERT INTO `gov_role_menu` VALUES ('1', '9');
INSERT INTO `gov_role_menu` VALUES ('1', '10');
INSERT INTO `gov_role_menu` VALUES ('1', '11');
INSERT INTO `gov_role_menu` VALUES ('1', '12');
INSERT INTO `gov_role_menu` VALUES ('1', '13');
INSERT INTO `gov_role_menu` VALUES ('1', '14');
INSERT INTO `gov_role_menu` VALUES ('1', '15');
INSERT INTO `gov_role_menu` VALUES ('1', '16');
INSERT INTO `gov_role_menu` VALUES ('1', '17');
INSERT INTO `gov_role_menu` VALUES ('1', '18');
INSERT INTO `gov_role_menu` VALUES ('1', '19');
INSERT INTO `gov_role_menu` VALUES ('1', '20');
INSERT INTO `gov_role_menu` VALUES ('1', '21');
INSERT INTO `gov_role_menu` VALUES ('1', '22');
INSERT INTO `gov_role_menu` VALUES ('1', '23');
INSERT INTO `gov_role_menu` VALUES ('1', '24');
INSERT INTO `gov_role_menu` VALUES ('1', '25');
INSERT INTO `gov_role_menu` VALUES ('1', '26');
INSERT INTO `gov_role_menu` VALUES ('1', '27');
INSERT INTO `gov_role_menu` VALUES ('1', '28');
INSERT INTO `gov_role_menu` VALUES ('1', '29');
INSERT INTO `gov_role_menu` VALUES ('1', '30');
INSERT INTO `gov_role_menu` VALUES ('1', '31');
INSERT INTO `gov_role_menu` VALUES ('1', '32');
INSERT INTO `gov_role_menu` VALUES ('1', '33');
INSERT INTO `gov_role_menu` VALUES ('1', '34');
INSERT INTO `gov_role_menu` VALUES ('1', '35');
INSERT INTO `gov_role_menu` VALUES ('1', '36');
INSERT INTO `gov_role_menu` VALUES ('1', '37');
INSERT INTO `gov_role_menu` VALUES ('1', '38');
INSERT INTO `gov_role_menu` VALUES ('1', '39');
INSERT INTO `gov_role_menu` VALUES ('1', '40');
INSERT INTO `gov_role_menu` VALUES ('1', '41');
INSERT INTO `gov_role_menu` VALUES ('1', '42');

-- ----------------------------
-- Table structure for `gov_social_details`
-- ----------------------------
DROP TABLE IF EXISTS `gov_social_details`;
CREATE TABLE `gov_social_details` (
  `g_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
  `g_type` varchar(16) NOT NULL COMMENT '类型',
  `g_remark` varchar(64) DEFAULT NULL COMMENT '描述',
  `g_app_id` varchar(64) NOT NULL COMMENT 'appid',
  `g_app_secret` varchar(64) NOT NULL COMMENT 'app_secret',
  `g_redirect_url` varchar(128) DEFAULT NULL COMMENT '回调地址',
  `g_create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `g_update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `g_del_flag` char(50) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`g_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统社交登录账号表';

-- ----------------------------
-- Records of gov_social_details
-- ----------------------------

-- ----------------------------
-- Table structure for `gov_tag`
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag`;
CREATE TABLE `gov_tag` (
  `g_tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `g_name` varchar(150) NOT NULL COMMENT '标签名称',
  `g_creation_date` datetime NOT NULL COMMENT '标签创建时间',
  `g_refers` int(11) NOT NULL COMMENT '标签应用次数',
  `g_priority` tinyint(4) NOT NULL COMMENT '标签优先级',
  `g_type_id` int(11) DEFAULT NULL COMMENT '标签分类',
  `g_level_id` int(11) DEFAULT NULL COMMENT '标签级别',
  `g_views` int(11) NOT NULL COMMENT '标签浏览量',
  `g_description` mediumtext COMMENT '标签介绍',
  `g_relation` varchar(80) DEFAULT NULL COMMENT '关联标签',
  `g_creator_id` int(11) NOT NULL COMMENT '标签创建者',
  PRIMARY KEY (`g_tag_id`),
  KEY `g_creator_id` (`g_creator_id`),
  KEY `g_type_id` (`g_type_id`),
  KEY `g_level_id` (`g_level_id`),
  CONSTRAINT `gov_tag_ibfk_1` FOREIGN KEY (`g_type_id`) REFERENCES `gov_tag_type` (`g_type_id`),
  CONSTRAINT `gov_tag_ibfk_2` FOREIGN KEY (`g_level_id`) REFERENCES `gov_tag_level` (`g_level_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

-- ----------------------------
-- Records of gov_tag
-- ----------------------------
INSERT INTO `gov_tag` VALUES ('1', '大数据', '2018-10-24 11:23:11', '2', '1', null, null, '0', null, null, '1');

-- ----------------------------
-- Table structure for `gov_tag_description`
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag_description`;
CREATE TABLE `gov_tag_description` (
  `g_tag_description_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签描述id',
  `g_description` mediumtext NOT NULL COMMENT '描述',
  `g_tag_id` int(11) NOT NULL COMMENT '关联标签id',
  `g_creation_date` datetime NOT NULL COMMENT '创建时间',
  `g_creator_id` int(11) NOT NULL COMMENT '创建者',
  PRIMARY KEY (`g_tag_description_id`),
  KEY `g_tag_id` (`g_tag_id`),
  KEY `g_creator_id` (`g_creator_id`),
  CONSTRAINT `gov_tag_description_ibfk_1` FOREIGN KEY (`g_tag_id`) REFERENCES `gov_tag` (`g_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签描述表';

-- ----------------------------
-- Records of gov_tag_description
-- ----------------------------

-- ----------------------------
-- Table structure for `gov_tag_level`
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag_level`;
CREATE TABLE `gov_tag_level` (
  `g_level_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签级别ID',
  `g_parent_id` int(11) DEFAULT NULL COMMENT '上级级别ID',
  `g_name` varchar(150) NOT NULL COMMENT '标签级别名称',
  `g_creation_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`g_level_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签级别表';

-- ----------------------------
-- Records of gov_tag_level
-- ----------------------------

-- ----------------------------
-- Table structure for `gov_tag_relation`
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag_relation`;
CREATE TABLE `gov_tag_relation` (
  `g_tag_id` int(11) NOT NULL COMMENT '标签ID',
  `g_relation_id` int(11) NOT NULL COMMENT '标签关联ID',
  `g_type_id` int(11) NOT NULL COMMENT '标签关联类型',
  `g_node` varchar(60) NOT NULL COMMENT '栏目编码',
  PRIMARY KEY (`g_tag_id`,`g_relation_id`,`g_type_id`,`g_node`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签关联表';

-- ----------------------------
-- Records of gov_tag_relation
-- ----------------------------
INSERT INTO `gov_tag_relation` VALUES ('1', '1', '1', 'user');
INSERT INTO `gov_tag_relation` VALUES ('1', '1', '2', 'user');

-- ----------------------------
-- Table structure for `gov_tag_relation_type`
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag_relation_type`;
CREATE TABLE `gov_tag_relation_type` (
  `g_type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签',
  `g_type_name` varchar(30) NOT NULL COMMENT '标签关联名称',
  `g_type_number` varchar(30) NOT NULL COMMENT '标签关联编码',
  PRIMARY KEY (`g_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='标签关联类型表';

-- ----------------------------
-- Records of gov_tag_relation_type
-- ----------------------------
INSERT INTO `gov_tag_relation_type` VALUES ('1', '卓越标签', 'ability');
INSERT INTO `gov_tag_relation_type` VALUES ('2', '专业标签', 'project');
INSERT INTO `gov_tag_relation_type` VALUES ('3', '进步标签', 'learning');
INSERT INTO `gov_tag_relation_type` VALUES ('4', '默认标签', 'def');

-- ----------------------------
-- Table structure for `gov_tag_type`
-- ----------------------------
DROP TABLE IF EXISTS `gov_tag_type`;
CREATE TABLE `gov_tag_type` (
  `g_type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签分类ID',
  `g_parent_id` int(11) DEFAULT NULL COMMENT '上级分类ID',
  `g_name` varchar(150) NOT NULL COMMENT '标签分类名称',
  `g_creation_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`g_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签分类表';

-- ----------------------------
-- Records of gov_tag_type
-- ----------------------------

-- ----------------------------
-- Table structure for `gov_user`
-- ----------------------------
DROP TABLE IF EXISTS `gov_user`;
CREATE TABLE `gov_user` (
  `g_user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `g_membergroup_id` int(11) DEFAULT NULL COMMENT '会员组',
  `g_username` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `g_password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `g_safe_password` varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT '二级密码',
  `g_salt` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '随机盐',
  `g_email` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '电子邮箱',
  `g_real_name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '真实姓名',
  `g_phone` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机',
  `g_avatar` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `g_dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  `g_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `g_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `g_del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '0-正常，1-删除',
  `g_status` int(11) DEFAULT '0' COMMENT '状态(0:正常,1:锁定,2:待验证)',
  `g_gender` char(1) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别',
  `g_birth_date` datetime DEFAULT NULL COMMENT '出生年月',
  `g_id_card` varchar(25) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '身份证号',
  `g_rank` int(11) DEFAULT '99999' COMMENT '等级',
  `g_type` int(11) DEFAULT '0' COMMENT '类型(0:会员,1:管理员)',
  `g_qq_openid` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT 'qq openid',
  `g_weibo_uid` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT 'weibo uid',
  `g_weixin_openid` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT 'weixin openid',
  `g_subscribe` int(11) DEFAULT '0' COMMENT '是否订阅 0否 1是',
  `g_staff_id` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户工号',
  `g_integral` int(50) DEFAULT '0' COMMENT '用户积分',
  `g_amount` decimal(10,2) DEFAULT '0.00' COMMENT '帐户金额',
  `g_logintime` int(255) DEFAULT '0' COMMENT '登录时长',
  PRIMARY KEY (`g_user_id`),
  UNIQUE KEY `user_idx1_username` (`g_username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of gov_user
-- ----------------------------
INSERT INTO `gov_user` VALUES ('1', '0', 'admin', '$2a$10$15NYwg6aGbmXHbBJfLHKue86npKgYByD.USf3atd1/3BzkzsOUD0m', null, null, '456456@qq.com', '超级管理员', '17034642888', 'http://192.168.1.188:4000/upload/imgs/20181024/1540352461154_406.jpg', '1', '2018-06-20 07:15:18', '2018-10-24 11:41:14', '0', '0', '男', '1996-01-03 08:00:00', '3330666633336665555', '99999', '0', null, null, null, '0', 'GM312321', '0', '0.00', '0');

-- ----------------------------
-- Table structure for `gov_user_clob`
-- ----------------------------
DROP TABLE IF EXISTS `gov_user_clob`;
CREATE TABLE `gov_user_clob` (
  `g_user_id` int(11) NOT NULL,
  `g_key` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '键',
  `g_value` mediumtext CHARACTER SET utf8 COMMENT '值',
  KEY `g_user_id` (`g_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息大文本字段';

-- ----------------------------
-- Records of gov_user_clob
-- ----------------------------
INSERT INTO `gov_user_clob` VALUES ('1', 'workText', null);
INSERT INTO `gov_user_clob` VALUES ('1', 'education', null);

-- ----------------------------
-- Table structure for `gov_user_detail`
-- ----------------------------
DROP TABLE IF EXISTS `gov_user_detail`;
CREATE TABLE `gov_user_detail` (
  `g_user_id` int(11) NOT NULL,
  `g_validation_date` datetime DEFAULT NULL COMMENT '验证生成时间',
  `g_login_error_date` datetime DEFAULT NULL COMMENT '登录错误时间',
  `g_login_error_count` int(11) DEFAULT '0' COMMENT '登录错误次数',
  `g_prev_login_date` datetime DEFAULT NULL COMMENT '上次登录日期',
  `g_prev_login_ip` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '上次登录IP',
  `g_last_login_date` datetime DEFAULT NULL COMMENT '最后登录日期',
  `g_last_login_ip` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '最后登录IP',
  `g_creation_date` datetime DEFAULT NULL COMMENT '加入日期',
  `g_creation_ip` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '加入IP',
  `g_logins` int(11) DEFAULT '0' COMMENT '登录次数',
  `g_is_with_avatar` char(1) CHARACTER SET utf8 DEFAULT '0' COMMENT '是否有头像（0为没有，1为有头像）',
  `g_bio` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '自我介绍',
  `g_come_from` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '来自',
  `g_qq` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'QQ',
  `g_weixin` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '微信',
  `g_head_img` varchar(150) CHARACTER SET utf8 DEFAULT NULL COMMENT '第三方头像',
  `g_nick_name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '昵称',
  `g_sign_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '个性签名',
  `g_nation` varchar(25) CHARACTER SET utf8 DEFAULT NULL COMMENT '民族',
  `g_marry` varchar(25) CHARACTER SET utf8 DEFAULT NULL COMMENT '婚姻',
  `g_bear` varchar(25) CHARACTER SET utf8 DEFAULT NULL COMMENT '生育状况',
  `g_politics` varchar(25) CHARACTER SET utf8 DEFAULT NULL COMMENT '政治面貌',
  `g_account_category` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '户口类别',
  `g_permanent_address` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '户籍地址',
  `g_present_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '现住地址',
  `g_education` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '学历',
  `g_graduation_time` date DEFAULT NULL COMMENT '毕业时间',
  `g_school` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '毕业学校',
  `g_major` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '专业',
  `g_external_certificate` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '外部证书',
  `g_language_level` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '外语水平',
  `g_start_time` datetime DEFAULT NULL COMMENT '入职时间',
  `g_regular_time` datetime DEFAULT NULL COMMENT '转正时间',
  `g_home_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '个人主页',
  `g_emergency` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '应急联系方式',
  PRIMARY KEY (`g_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户详情表';

-- ----------------------------
-- Records of gov_user_detail
-- ----------------------------
INSERT INTO `gov_user_detail` VALUES ('1', null, null, '0', null, null, null, null, null, null, '0', '1', null, null, null, null, null, null, '我就是我', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `gov_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `gov_user_role`;
CREATE TABLE `gov_user_role` (
  `g_user_id` int(11) NOT NULL COMMENT '用户ID',
  `g_role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`g_user_id`,`g_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

-- ----------------------------
-- Records of gov_user_role
-- ----------------------------
INSERT INTO `gov_user_role` VALUES ('1', '1');
