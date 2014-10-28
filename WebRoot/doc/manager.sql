--客户信息.
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `custId` varchar(20) DEFAULT NULL  COMMENT '客户ID(程序分配一个)',
  `custName` varchar(20) DEFAULT NULL COMMENT '客户名字',
  `engName` varchar(20) DEFAULT NULL COMMENT '英文名字',
  `custSex` int DEFAULT 1  COMMENT '客户性别,0:女，1:男',
  `country` varchar(20) DEFAULT '中国' COMMENT '所在国家',
  `province` varchar(20) DEFAULT NULL COMMENT '所在省份',
  `city` varchar(20) DEFAULT NULL COMMENT '所在市',
  `otherPlace` varchar(20) DEFAULT NULL COMMENT '其他地区', 
  `mobilePhone` varchar(20) DEFAULT NULL  COMMENT '客户手机',
  `linePhone` varchar(20) DEFAULT NULL  COMMENT '总机电话',
  `custPhone` varchar(20) DEFAULT NULL  COMMENT '客户分机',
  `personalMail` varchar(30) DEFAULT NULL  COMMENT '个人邮箱',
  `custMail` varchar(30) DEFAULT NULL  COMMENT '公司邮箱',
  `custQQ` varchar(30) DEFAULT NULL  COMMENT '客户QQ',
  `companyName` varchar(10) DEFAULT NULL COMMENT '所在公司名称',
  `department` varchar(40) DEFAULT NULL  COMMENT '公司部门(多级用"|"分割)',
  `companyMark` varchar(10) DEFAULT NULL COMMENT '公司头衔',
  `zipCode` varchar(10) DEFAULT NULL COMMENT '公司邮编',
  `companyPhone` varchar(20) DEFAULT NULL COMMENT '公司电话',
  `tempVal0` varchar(100) DEFAULT NULL COMMENT '预留字段0',
  `tempVal1` varchar(100) DEFAULT NULL COMMENT '预留字段1',
  `tempVal2` varchar(100) DEFAULT NULL COMMENT '预留字段2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;




