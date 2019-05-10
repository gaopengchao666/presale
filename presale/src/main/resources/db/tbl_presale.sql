/*
Navicat MySQL Data Transfer
Date: 2019-03-27 18:28:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_presale
-- ----------------------------
DROP TABLE IF EXISTS `tbl_presale`;
CREATE TABLE `tbl_presale` (
  `ID` int(255) NOT NULL AUTO_INCREMENT,
  `CERTIFICATE` varchar(255) DEFAULT NULL COMMENT '预售证号',
  `PROJECT_NAME` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `LOCATION` varchar(255) DEFAULT NULL COMMENT '项目所在位置',
  `DEVELOPER` varchar(255) DEFAULT NULL COMMENT '开发商',
  `SALEABLE` varchar(1024) DEFAULT NULL COMMENT '可售楼栋',
  `CREATE_TIME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tbl_price
-- ----------------------------
DROP TABLE IF EXISTS `tbl_price`;
CREATE TABLE `tbl_price` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PROJECT_ID` int(11) DEFAULT NULL COMMENT '项目ID',
  `SORT` int(11) DEFAULT NULL COMMENT '序号',
  `ROOM_NO` varchar(50) DEFAULT NULL COMMENT '房间号',
  `AREA` double(10,4) DEFAULT NULL COMMENT '面积',
  `UNIT_PRICE` decimal(10,4) DEFAULT NULL COMMENT '房屋单价',
  `TOTAL_PRICE` decimal(10,4) DEFAULT NULL COMMENT '房屋总价',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tbl_project
-- ----------------------------
DROP TABLE IF EXISTS `tbl_project`;
CREATE TABLE `tbl_project` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `BATCH` varchar(255) DEFAULT NULL COMMENT '所属批次',
  `PROJECT_NAME` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `SALEABLE` varchar(255) DEFAULT NULL COMMENT '申报楼幢',
  `FLOORS` varchar(50) DEFAULT NULL COMMENT '总层数',
  `ADDRESS` varchar(255) DEFAULT NULL COMMENT '地址',
  `DECORATION` varchar(20) DEFAULT NULL COMMENT '装修标准',
  `PRICE_URL` varchar(255) DEFAULT NULL COMMENT '项目价格URL',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
