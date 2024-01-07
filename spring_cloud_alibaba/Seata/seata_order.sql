/*
 Navicat Premium Data Transfer

 Source Server         : 本地库
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : seata_order

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 23/02/2023 15:22:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL,
  `commodity_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `count` int(0) NULL DEFAULT NULL,
  `money` int(0) NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (5, 1, '1', 1, 1, 1);
INSERT INTO `order` VALUES (8, 1001, '2001', 1, 2, 1);
INSERT INTO `order` VALUES (11, 1001, '2001', 1, 88, 0);
INSERT INTO `order` VALUES (12, 1001, '2001', 1, 88, 0);
INSERT INTO `order` VALUES (13, 1001, '2001', 1, 88, 0);
INSERT INTO `order` VALUES (16, 1001, '2001', 1, 10, 1);
INSERT INTO `order` VALUES (26, 1001, '2001', 1, 1, 1);
INSERT INTO `order` VALUES (27, 1001, '2001', 1, 1, 1);
INSERT INTO `order` VALUES (28, 1001, '2001', 1, 1, 1);
INSERT INTO `order` VALUES (29, 1001, '2001', 1, 50, 0);
INSERT INTO `order` VALUES (31, 1001, '2001', 1, 50, 0);
INSERT INTO `order` VALUES (32, 1001, '2001', 1, 50, 0);
INSERT INTO `order` VALUES (33, 1001, '2001', 1, 50, 0);
INSERT INTO `order` VALUES (34, 1001, '2001', 1, 50, 0);
INSERT INTO `order` VALUES (35, 1001, '2001', 1, 50, 0);
INSERT INTO `order` VALUES (36, 1001, '2001', 1, 50, 0);
INSERT INTO `order` VALUES (37, 1001, '2001', 1, 50, 0);
INSERT INTO `order` VALUES (45, 1001, '2001', 1, 5, 0);
INSERT INTO `order` VALUES (46, 1001, '2001', 1, 5, 0);
INSERT INTO `order` VALUES (49, 1001, '2001', 1, 1, 0);
INSERT INTO `order` VALUES (58, 1001, '2001', 1, 1, 0);
INSERT INTO `order` VALUES (59, 1001, '2001', 1, 1, 0);
INSERT INTO `order` VALUES (60, 1001, '2001', 1, 1, 0);
INSERT INTO `order` VALUES (61, 1001, '2001', 1, 1, 0);

-- ----------------------------
-- Table structure for order_tcc
-- ----------------------------
DROP TABLE IF EXISTS `order_tcc`;
CREATE TABLE `order_tcc`  (
  `id` bigint(0) NOT NULL,
  `user_id` int(0) NULL DEFAULT NULL,
  `commodity_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `count` int(0) NULL DEFAULT NULL,
  `money` int(0) NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_tcc
-- ----------------------------
INSERT INTO `order_tcc` VALUES (416353154574196736, 1001, '2001', 1, 1, 1);
INSERT INTO `order_tcc` VALUES (416353459760144384, 1001, '2001', 1, 1, -1);
INSERT INTO `order_tcc` VALUES (416353603075317760, 1001, '2001', 1, 1, 1);
INSERT INTO `order_tcc` VALUES (416355484254216192, 1001, '2001', 1, 11, -1);
INSERT INTO `order_tcc` VALUES (416355581444628480, 1001, '2001', 1, 11, -1);

-- ----------------------------
-- Table structure for tcc_fence_log
-- ----------------------------
DROP TABLE IF EXISTS `tcc_fence_log`;
CREATE TABLE `tcc_fence_log`  (
  `xid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'global id',
  `branch_id` bigint(0) NOT NULL COMMENT 'branch id',
  `action_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'action name',
  `status` tinyint(0) NOT NULL COMMENT 'status(tried:1;committed:2;rollbacked:3;suspended:4)',
  `gmt_create` datetime(3) NOT NULL COMMENT 'create time',
  `gmt_modified` datetime(3) NOT NULL COMMENT 'update time',
  PRIMARY KEY (`xid`, `branch_id`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tcc_fence_log
-- ----------------------------
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497699', 5413689721568497700, 'prepareSaveOrder', 2, '2023-02-22 21:47:55.625', '2023-02-22 21:49:04.269');
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497705', 5413689721568497706, 'prepareSaveOrder', 2, '2023-02-22 21:49:18.150', '2023-02-22 21:49:18.724');
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497711', 5413689721568497712, 'prepareSaveOrder', 2, '2023-02-22 21:52:38.125', '2023-02-22 21:52:38.833');
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497717', 5413689721568497718, 'prepareSaveOrder', 2, '2023-02-22 21:53:52.759', '2023-02-22 21:53:53.036');
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497723', 5413689721568497724, 'prepareSaveOrder', 2, '2023-02-22 21:55:03.194', '2023-02-22 21:55:03.451');
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497729', 5413689721568497730, 'prepareSaveOrder', 2, '2023-02-22 21:55:41.388', '2023-02-22 21:55:41.428');
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497735', 5413689721568497736, 'prepareSaveOrder', 2, '2023-02-22 21:56:24.833', '2023-02-22 21:56:25.068');
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497741', 5413689721568497742, 'prepareSaveOrder', 4, '2023-02-22 21:57:35.122', '2023-02-22 21:57:35.122');
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497743', 5413689721568497744, 'prepareSaveOrder', 2, '2023-02-22 21:58:15.572', '2023-02-22 21:58:15.823');
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497749', 5413689721568497750, 'prepareSaveOrder', 2, '2023-02-22 21:58:47.522', '2023-02-22 21:58:47.563');
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497755', 5413689721568497756, 'prepareSaveOrder', 3, '2023-02-22 22:00:00.301', '2023-02-22 22:00:00.314');
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497757', 5413689721568497758, 'prepareSaveOrder', 2, '2023-02-22 22:00:34.453', '2023-02-22 22:00:34.714');
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497763', 5413689721568497764, 'prepareSaveOrder', 3, '2023-02-22 22:08:02.978', '2023-02-22 22:08:03.065');
INSERT INTO `tcc_fence_log` VALUES ('192.168.0.11:8091:5413689721568497767', 5413689721568497768, 'prepareSaveOrder', 3, '2023-02-22 22:08:26.133', '2023-02-22 22:08:26.169');

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(0) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(0) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 99 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
