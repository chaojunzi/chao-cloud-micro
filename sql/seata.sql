/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : seata

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2019-08-28 17:51:36
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for xc_order
-- ----------------------------
DROP TABLE IF EXISTS `xc_order`;
CREATE TABLE `xc_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_sn` varchar(64) DEFAULT '' COMMENT '订单号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户Id',
  `version` int(11) DEFAULT 0 COMMENT '乐观锁',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='用户帐号';

-- ----------------------------
-- Records of xc_order
-- ----------------------------
INSERT INTO `xc_order` VALUES ('6', '1165901473955971072', '1', '0', '2019-08-26 16:19:07');
INSERT INTO `xc_order` VALUES ('7', '1165901877095694336', '1', '0', '2019-08-26 16:20:43');
INSERT INTO `xc_order` VALUES ('8', '1166189981845159936', '1', '0', '2019-08-27 11:25:33');
INSERT INTO `xc_order` VALUES ('9', '1166192152284233728', '1', '0', '2019-08-27 11:34:10');
INSERT INTO `xc_order` VALUES ('10', '1166202038791438336', '1', '0', '2019-08-27 12:13:27');
INSERT INTO `xc_order` VALUES ('11', '1166220995007086592', '1', '0', '2019-08-27 13:28:51');
INSERT INTO `xc_order` VALUES ('12', '1166226256677568512', '1', '0', '2019-08-27 13:49:41');
INSERT INTO `xc_order` VALUES ('13', '1166229220838342656', '1', '0', '2019-08-27 14:01:28');
INSERT INTO `xc_order` VALUES ('14', '1166234273150140416', '1', '0', '2019-08-27 14:21:32');
INSERT INTO `xc_order` VALUES ('15', '1166234638184611840', '1', '0', '2019-08-27 14:22:59');
INSERT INTO `xc_order` VALUES ('16', '1166236444402909184', '1', '0', '2019-08-27 14:30:10');
INSERT INTO `xc_order` VALUES ('17', '1166237945682722816', '1', '0', '2019-08-27 14:36:08');
INSERT INTO `xc_order` VALUES ('18', '1166253865226469376', '1', '0', '2019-08-27 15:39:24');
INSERT INTO `xc_order` VALUES ('19', '1166268438868393984', '1', '0', '2019-08-27 16:37:18');
INSERT INTO `xc_order` VALUES ('21', '1166268864837713920', '1', '0', '2019-08-27 16:39:00');
INSERT INTO `xc_order` VALUES ('27', '1166606797620903936', '1', '0', '2019-08-28 15:01:49');
