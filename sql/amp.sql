/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : amp

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 08/10/2022 10:32:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for amp_account_extend
-- ----------------------------
DROP TABLE IF EXISTS `amp_account_extend`;
CREATE TABLE `amp_account_extend` (
  `id` varchar(32)   NOT NULL,
  `account_id` varchar(32) NOT NULL COMMENT '用户帐号id',
  `use_count` bigint(20) NOT NULL DEFAULT 0 COMMENT '帐号使用次数',
  `total_income` decimal(20, 2) NOT NULL DEFAULT 0.00 COMMENT '帐号收益',
  `created_by` varchar(32)   NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(32)   NOT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_account_id` (`account_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin
  COMMENT '用户帐号扩展表';

-- ----------------------------
-- Table structure for amp_account_tran_log
-- ----------------------------
DROP TABLE IF EXISTS `amp_account_tran_log`;
CREATE TABLE `amp_account_tran_log` (
  `id` varchar(32)   NOT NULL,
  `account_id` varchar(32)   NOT NULL COMMENT '用户帐号',
  `category_name` varchar(200)   NOT NULL COMMENT '类目名称',
  `user_name` varchar(200)   NOT NULL COMMENT '帐号名称',
  `platform_name` varchar(200)   NOT NULL COMMENT '平台名称',
  `user_id` varchar(32)   NOT NULL COMMENT '用户id',
  `channel` varchar(20)   NOT NULL COMMENT '交易渠道 advs-广告',
  `income` decimal(20,2) NOT NULL COMMENT '收益金额',
  `type` varchar(20)   NOT NULL COMMENT 'profit-盈利 loss-亏损',
  `created_by` varchar(32)   NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_account_id` (`account_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin
  COMMENT '用户交易日志表';

-- ----------------------------
-- Table structure for amp_category_info
-- ----------------------------
DROP TABLE IF EXISTS `amp_category_info`;
CREATE TABLE `amp_category_info` (
  `id` varchar(32)   NOT NULL,
  `name` varchar(200)   NOT NULL COMMENT '类目名称',
  `description` varchar(255)   DEFAULT NULL COMMENT '类目描述',
  `created_by` varchar(32)   NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(32)   NOT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin
  COMMENT '类目表';

-- ----------------------------
-- Table structure for amp_platform_account_info
-- ----------------------------
DROP TABLE IF EXISTS `amp_platform_account_info`;
CREATE TABLE `amp_platform_account_info` (
  `id` varchar(32)   NOT NULL,
  `user_name` varchar(200)   NOT NULL COMMENT '用户帐号',
  `password` varchar(128)   NOT NULL COMMENT '密码',
  `platform_id` varchar(32)   NOT NULL COMMENT '平台id',
  `status` varchar(10)   NOT NULL DEFAULT 'draft'  COMMENT 'draft-草稿 approve-审批 active-激活 invalid-失效',
  `price` decimal(20,2) NOT NULL COMMENT '原价',
  `user_id` varchar(32)   NOT NULL COMMENT '用户id',
  `end_time` datetime DEFAULT NULL COMMENT '截止时间',
  `description` varchar(128)   DEFAULT NULL COMMENT '帐号描述信息',
  `created_by` varchar(32)   NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(32)   NOT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_platform_id` (`platform_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin
  COMMENT '平台帐号表';

-- ----------------------------
-- Table structure for amp_platform_info
-- ----------------------------
DROP TABLE IF EXISTS `amp_platform_info`;
CREATE TABLE `amp_platform_info` (
  `id` varchar(32)   NOT NULL,
  `name` varchar(200)   NOT NULL COMMENT '平台/软件名称',
  `icon` longblob NULL COMMENT '图标',
  `category_id` varchar(32)   NOT NULL COMMENT '类目id',
  `description` varchar(255)   DEFAULT NULL COMMENT '平台描述信息',
  `status` varchar(10)   NOT NULL DEFAULT 'draft' COMMENT 'draft-草稿 approve-审核 active-激活 invalid-失效',
  `created_by` varchar(32)   NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(32)   NOT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin
  COMMENT '平台信息表';
-- ----------------------------
-- Table structure for amp_user_income_info
-- ----------------------------
DROP TABLE IF EXISTS `amp_user_income_info`;
CREATE TABLE `amp_user_income_info` (
  `id` varchar(32)   NOT NULL,
  `user_id` varchar(32)   NOT NULL COMMENT '用户id',
  `total_income` decimal(20,2) NOT NULL COMMENT '用户收益',
  `created_by` varchar(32)   NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(32)   NOT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin
  COMMENT '用户收益表';
-- ----------------------------
-- Table structure for amp_user_info
-- ----------------------------
DROP TABLE IF EXISTS `amp_user_info`;
CREATE TABLE `amp_user_info` (
  `id` varchar(32)   NOT NULL,
  `nickname` varchar(200)   NOT NULL COMMENT '用户昵称',
  `phone` varchar(11)   NOT NULL COMMENT '用户手机号',
  `openid` varchar(32)   DEFAULT NULL COMMENT '微信openid',
  `status` varchar(20)   NOT NULL DEFAULT 'enable' COMMENT '账户状态,正常:enable,禁用:disable',
  `description` varchar(255)   DEFAULT NULL COMMENT '用户描述信息',
  `password` varchar(128)   NOT NULL COMMENT '用户密码',
  `avatar_image` longblob COMMENT '用户头像',
  `created_by` varchar(32)   NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(32)   NOT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_phone` (`phone`) USING BTREE,
  UNIQUE KEY `idx_openid` (`openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin
  COMMENT '用户信息表';


SET FOREIGN_KEY_CHECKS = 1;
