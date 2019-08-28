/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2019-08-28 17:53:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for xc_tbl
-- ----------------------------
DROP TABLE IF EXISTS `xc_tbl`;
CREATE TABLE `xc_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `money` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xc_tbl
-- ----------------------------
INSERT INTO `xc_tbl` VALUES ('1', '1', '10000');
INSERT INTO `xc_tbl` VALUES ('2', '2', '10000');
INSERT INTO `xc_tbl` VALUES ('3', '1', '100');
INSERT INTO `xc_tbl` VALUES ('7', '1', '100');
INSERT INTO `xc_tbl` VALUES ('13', '1', '100');
INSERT INTO `xc_tbl` VALUES ('14', '1', '100');
INSERT INTO `xc_tbl` VALUES ('15', '1', '100');
INSERT INTO `xc_tbl` VALUES ('17', '1', '100');
INSERT INTO `xc_tbl` VALUES ('19', '1', '100');
INSERT INTO `xc_tbl` VALUES ('20', '1', '100');
INSERT INTO `xc_tbl` VALUES ('27', '1', '100');
INSERT INTO `xc_tbl` VALUES ('28', '1', '100');
INSERT INTO `xc_tbl` VALUES ('33', '1', '100');
