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
  `SALEABLE` varchar(255) DEFAULT NULL COMMENT '可售楼栋',
  `CREATE_TIME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbl_presale
-- ----------------------------
INSERT INTO `tbl_presale` VALUES ('1', '2019096', '万达﹒西安one项目8地块', '西安市高新区科技七路以南，木塔一路以西', '陕西锦世达置业有限公司', '5幢,7幢', '2019-03-26');
