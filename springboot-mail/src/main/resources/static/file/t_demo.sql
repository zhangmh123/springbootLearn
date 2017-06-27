/*
Navicat MySQL Data Transfer

Source Server         : MYSQL
Source Server Version : 50512
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50512
File Encoding         : 65001

Date: 2017-05-26 14:40:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_demo`
-- ----------------------------
DROP TABLE IF EXISTS `t_demo`;
CREATE TABLE `t_demo` (
  `id` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  UNIQUE KEY `pk_demo_id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_demo
-- ----------------------------
