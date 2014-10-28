/*
MySQL Data Transfer
Source Host: localhost
Source Database: ssi_spring_security
Target Host: localhost
Target Database: ssi_spring_security
Date: 2013-12-7 23:25:59
*/

drop database if exists model;

create database if not exists model; 

use model;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `syslog`;
CREATE TABLE `syslog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `module` varchar(30) DEFAULT NULL,
  `action` varchar(30) DEFAULT NULL,
  `actionTime` varchar(30) DEFAULT NULL,
  `userIP` varchar(30) DEFAULT NULL,
  `operTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resources
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `resKey` varchar(50) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `resUrl` varchar(200) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resources_role
-- ----------------------------
DROP TABLE IF EXISTS `resources_role`;
CREATE TABLE `resources_role` (
  `resc_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`resc_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `sysrole`;
CREATE TABLE `sysrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `roleKey` varchar(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for serverinfo
-- ----------------------------
DROP TABLE IF EXISTS `serverinfo`;
CREATE TABLE `serverinfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `cpuUsage` varchar(255) DEFAULT NULL,
  `setCpuUsage` varchar(255) DEFAULT NULL,
  `jvmUsage` varchar(255) DEFAULT NULL,
  `setJvmUsage` varchar(255) DEFAULT NULL,
  `ramUsage` varchar(255) DEFAULT NULL,
  `setRamUsage` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `operTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `mark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `sysuser`;
CREATE TABLE `sysuser` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `userPassword` varchar(40) DEFAULT NULL,
  `userNickname` varchar(20) DEFAULT NULL,
  `userRealname` varchar(20) DEFAULT NULL,
  `userAge` int(11) DEFAULT NULL,
  `userSex` varchar(10) DEFAULT NULL,
  `userAddress` varchar(100) DEFAULT NULL,
  `userPhone` varchar(30) DEFAULT NULL,
  `userMail` varchar(45) DEFAULT NULL,
  `userQQ` varchar(30) DEFAULT NULL,
  `regTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastLogintime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `level` int(11) DEFAULT NULL,
  `province` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `bankName` varchar(100) DEFAULT NULL,
  `branchBank` varchar(100) DEFAULT NULL,
  `subbranchBank` varchar(100) DEFAULT NULL,
  `openBankName` varchar(100) DEFAULT NULL,
  `bankAccountName` varchar(100) DEFAULT NULL,
  `bankAccount` varchar(100) DEFAULT NULL,
  `accountType` varchar(20) DEFAULT NULL,
  `pay` varchar(20) DEFAULT NULL,
  `mark` varchar(200) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `parentNumber` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  `role_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userLoginLog
-- ----------------------------
DROP TABLE IF EXISTS `userloginlog`;
CREATE TABLE `userloginlog` (
  `loginId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `loginTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `loginIP` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`loginId`),
  KEY `FK_userloginlog` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=292 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------

INSERT INTO `resources` VALUES ('1', '用户管理', '1010', 'sys_user', '0', 'sysuser', '1', '用户管理');
INSERT INTO `resources` VALUES ('2', '用户列表', '1', 'sys_user_find', '1', '/background/sysuser/find.html', '2', '用户列表');
INSERT INTO `resources` VALUES ('3', '新增用户', '1', 'sys_user_addUI', '1', '/background/sysuser/addUI.html', '3', '新增用户');
INSERT INTO `resources` VALUES ('4', '编辑用户', '2', 'sys_user_edit', '2', '/background/sysuser/getById.html', '4', '编辑用户');
INSERT INTO `resources` VALUES ('5', '删除用户', '2', 'sys_user_delete', '2', '/background/sysuser/deleteById.html', '5', '删除用户');
INSERT INTO `resources` VALUES ('6', '分配角色', '2', 'sys_user_fenpeirole', '2', 'sys_user_fenpeirole', '6', '分配角色');
INSERT INTO `resources` VALUES ('7', '详细信息', '2', 'sys_user_info', '2', 'sys_user_info', '7', '显示详细信息');

INSERT INTO `resources` VALUES ('8', '角色管理', '1010', 'sys_role', '0', 'sysrole', '8', '角色管理');
INSERT INTO `resources` VALUES ('9', '角色列表', '8', 'sys_role_find', '1', '/background/sysrole/find.html', '9', '角色列表');
INSERT INTO `resources` VALUES ('10', '新增角色', '8', 'sys_role_add', '1', '/background/sysrole/addUI.html', '10', '新增角色');
INSERT INTO `resources` VALUES ('11', '编辑角色', '9', 'sys_role_edit', '2', '/background/sysrole/getById.html', '11', '编辑角色');
INSERT INTO `resources` VALUES ('12', '删除角色', '9', 'sys_role_delete', '2', '/background/sysrole/deleteById.html', '12', '删除角色');
INSERT INTO `resources` VALUES ('13', '分配权限', '9', 'sys_role_permissions', '2', 'sys_role_permissions', '13', '分配权限');

INSERT INTO `resources` VALUES ('14', '资源管理', '1010', 'sys_resources', '0', 'resources', '14', '资源管理');
INSERT INTO `resources` VALUES ('15', '资源列表', '14', 'sys_resc_find', '1', '/background/resources/find.html', '15', '资源列表');
INSERT INTO `resources` VALUES ('16', '添加资源', '14', 'sys_resc_addUI', '1', '/background/resources/addUI.html', '16', '添加资源');
INSERT INTO `resources` VALUES ('17', '编辑资源', '15', 'sys_res_edit', '2', '/background/resources/getById.html', '17', '编辑权限资源');
INSERT INTO `resources` VALUES ('18', '删除资源', '15', 'sys_res_delete', '2', '/background/resources/deleteById.html', '18', '删除权限资源');

INSERT INTO `resources` VALUES ('19', '服务管理', '1010', 'server', '0', 'server', '19', '服务器配置管理');
INSERT INTO `resources` VALUES ('20', '预警设置', '19', 'ser_warn', '1', '/background/serverInfo/forecast.html', '20', '预警设置');
INSERT INTO `resources` VALUES ('21', '状态列表', '19', 'ser_list', '1', '/background/serverInfo/find.html', '21', '状态列表');
INSERT INTO `resources` VALUES ('22', '服务器状态', '19', 'ser_statu', '1', '/background/serverInfo/show.html', '22', '服务器状态');

INSERT INTO `resources` VALUES ('23', '日志管理', '1010', 'sys_log', '0', 'syslog', '23', '日志管理');
INSERT INTO `resources` VALUES ('24', '操作日志', '23', 'syslog_find', '1', '/background/syslog/find.html', '24', '操作日志');
INSERT INTO `resources` VALUES ('25', '登录日志', '23', 'login_list', '1', '/background/syslog/findlogin.html', '25', '登录日志');

INSERT INTO `resources` VALUES ('26', '详细信息', '9', 'sys_role_info', '2', 'sys_role_info', '26', '显示详细信息');
INSERT INTO `resources` VALUES ('27', '详细信息', '15', 'sys_res_info', '2', 'sys_res_info', '27', '显示详细信息');

INSERT INTO `resources_role` VALUES ('1', '1');
INSERT INTO `resources_role` VALUES ('2', '1');
INSERT INTO `resources_role` VALUES ('3', '1');
INSERT INTO `resources_role` VALUES ('4', '1');
INSERT INTO `resources_role` VALUES ('5', '1');
INSERT INTO `resources_role` VALUES ('6', '1');
INSERT INTO `resources_role` VALUES ('7', '1');
INSERT INTO `resources_role` VALUES ('8', '1');
INSERT INTO `resources_role` VALUES ('9', '1');
INSERT INTO `resources_role` VALUES ('10', '1');

INSERT INTO `resources_role` VALUES ('11', '1');
INSERT INTO `resources_role` VALUES ('12', '1');
INSERT INTO `resources_role` VALUES ('13', '1');
INSERT INTO `resources_role` VALUES ('14', '1');
INSERT INTO `resources_role` VALUES ('15', '1');
INSERT INTO `resources_role` VALUES ('16', '1');
INSERT INTO `resources_role` VALUES ('17', '1');
INSERT INTO `resources_role` VALUES ('18', '1');
INSERT INTO `resources_role` VALUES ('19', '1');
INSERT INTO `resources_role` VALUES ('20', '1');

INSERT INTO `resources_role` VALUES ('21', '1');
INSERT INTO `resources_role` VALUES ('22', '1');
INSERT INTO `resources_role` VALUES ('23', '1');
INSERT INTO `resources_role` VALUES ('24', '1');
INSERT INTO `resources_role` VALUES ('25', '1');
INSERT INTO `resources_role` VALUES ('26', '1');
INSERT INTO `resources_role` VALUES ('27', '1');


INSERT INTO `sysrole` VALUES ('1', 'admin', 'ROLE_USER', '管理员', '1');

INSERT INTO `serverinfo` VALUES ('1', '9.3', '20', '64.0', '80', '75.0', '80', '1577620678@qq.com', '2014-10-12 11:31:04', '<font color=\'red\'>内存当前：75.0%,超出预设值  60%</font>');
INSERT INTO `serverinfo` VALUES ('2', '0.8', '20', '60.0', '80', '75.0', '80', '1577620678@qq.com', '2014-10-12 11:32:02', '<font color=\'red\'>内存当前：75.0%,超出预设值  60%</font>');
INSERT INTO `serverinfo` VALUES ('3', '1.5', '20', '59.0', '80', '75.0', '80', '1577620678@qq.com', '2014-10-12 11:33:03', '<font color=\'red\'>内存当前：75.0%,超出预设值  60%</font>');
INSERT INTO `serverinfo` VALUES ('4', '0.7', '20', '57.0', '80', '75.0', '80', '1577620678@qq.com', '2014-10-12 11:34:02', '<font color=\'red\'>内存当前：75.0%,超出预设值  60%</font>');
INSERT INTO `serverinfo` VALUES ('5', '2.3', '20', '57.0', '80', '75.0', '80', '1577620678@qq.com', '2014-10-12 11:35:02', '<font color=\'red\'>内存当前：75.0%,超出预设值  60%</font>');
INSERT INTO `serverinfo` VALUES ('6', '17.9', '20', '57.0', '80', '77.0', '80', '1577620678@qq.com', '2014-10-12 11:36:02', '<font color=\'red\'>内存当前：77.0%,超出预设值  60%</font>');

INSERT INTO `sysuser` VALUES ('1', 'admin', 'admin', 'admin', 'admin', '10', '男', '枯霜下要孤', '0253526', 'jnfjfjj@163.com', '32432', '2013-06-18 10:37:11', '0000-00-00 00:00:00', '1', null, null, null, null, null, null, null, null, null, null, null, null, null);

INSERT INTO `user_role` VALUES ('1', '1');
