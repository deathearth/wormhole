/*
 Navicat Premium Data Transfer

 Source Server         : 47.99.141.0-新数据库
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : 47.99.141.0
 Source Database       : gateway

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : utf-8

 Date: 02/22/2019 16:58:12 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `auth_authorization`
-- ----------------------------
DROP TABLE IF EXISTS `auth_authorization`;
CREATE TABLE `auth_authorization` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(10) NOT NULL COMMENT '用户id',
  `ROLE_ID` int(10) NOT NULL COMMENT '角色id',
  `CDT` datetime NOT NULL,
  `UDT` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_AUTH_AUTHORIZATION` (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联关系表';

-- ----------------------------
--  Records of `auth_authorization`
-- ----------------------------
BEGIN;
INSERT INTO `auth_authorization` VALUES ('1', '20', '1', '2019-02-22 16:07:59', '2019-02-22 16:07:59');
COMMIT;

-- ----------------------------
--  Table structure for `auth_permission`
-- ----------------------------
DROP TABLE IF EXISTS `auth_permission`;
CREATE TABLE `auth_permission` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `RES_ID` int(10) NOT NULL COMMENT '资源id',
  `ROLE_ID` int(10) NOT NULL COMMENT '角色id',
  `CDT` datetime NOT NULL,
  `UDT` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_AUTH_PERMISSION` (`RES_ID`,`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3891 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色权限表';

-- ----------------------------
--  Records of `auth_permission`
-- ----------------------------
BEGIN;
INSERT INTO `auth_permission` VALUES ('3792', '1', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3793', '2', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3794', '48', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3795', '49', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3796', '52', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3797', '55', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3798', '58', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3799', '61', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3800', '63', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3801', '64', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3802', '66', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3803', '67', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3804', '3', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3805', '46', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3806', '50', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3807', '53', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3808', '56', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3809', '59', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3810', '62', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3811', '4', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3812', '47', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3813', '51', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3814', '54', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3815', '57', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3816', '60', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3817', '99', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3818', '111', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3819', '128', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3820', '153', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3821', '155', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3822', '72', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3823', '73', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3824', '75', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3825', '76', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3826', '77', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3827', '78', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3828', '74', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3829', '79', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3830', '83', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3831', '81', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3832', '82', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3833', '84', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3834', '85', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3835', '86', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3836', '87', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3837', '88', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3838', '89', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3839', '90', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3840', '91', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3841', '92', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3842', '95', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3843', '94', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3844', '96', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3845', '139', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3846', '100', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3847', '101', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3848', '102', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3849', '103', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3850', '104', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3851', '105', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3852', '112', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3853', '135', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3854', '119', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3855', '114', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3856', '115', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3857', '116', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3858', '205', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3859', '120', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3860', '123', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3861', '129', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3862', '141', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3863', '142', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3864', '149', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3865', '150', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3866', '151', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3867', '152', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3868', '143', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3869', '144', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3870', '145', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3871', '146', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3872', '201', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3873', '204', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3874', '219', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3875', '220', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3876', '221', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3877', '222', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3878', '117', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3879', '158', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3880', '118', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3881', '148', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3882', '126', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3883', '137', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3884', '156', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3885', '157', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3886', '127', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3887', '159', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3888', '140', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3889', '154', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04'), ('3890', '124', '1', '2019-01-25 10:17:04', '2019-01-25 10:17:04');
COMMIT;

-- ----------------------------
--  Table structure for `auth_resource`
-- ----------------------------
DROP TABLE IF EXISTS `auth_resource`;
CREATE TABLE `auth_resource` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `PID` int(10) DEFAULT NULL,
  `NAME` varchar(30) NOT NULL,
  `CATEGORY` char(1) NOT NULL,
  `SEQ` tinyint(4) DEFAULT NULL,
  `URI` varchar(100) DEFAULT NULL,
  `CDT` datetime NOT NULL,
  `UDT` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=utf8mb4 COMMENT='访问资源表';

-- ----------------------------
--  Records of `auth_resource`
-- ----------------------------
BEGIN;
INSERT INTO `auth_resource` VALUES ('1', '0', '系统管理', 'd', '1', '', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('2', '1', '用户管理', 'm', '1', '/auth/user/list', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('3', '1', '角色管理', 'm', '2', '/auth/role/list', '2018-03-26 17:08:46', '2018-03-26 17:08:50'), ('4', '1', '资源管理', 'm', '3', '/auth/resource/list', '2018-03-26 17:10:01', '2018-03-26 17:10:04'), ('46', '3', '角色查询', 'a', null, '/auth/role/proto/index', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('47', '4', '资源查询', 'a', null, '/auth/resource/proto/index', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('48', '2', '用户查询', 'a', null, '/auth/user/proto/index', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('49', '2', '用户添加', 'a', null, '/auth/user/proto/post', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('50', '3', '角色添加', 'a', null, '/auth/role/proto/post', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('51', '4', '资源添加', 'a', null, '/auth/resource/proto/post', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('52', '2', '用户查看', 'a', null, '/auth/user/proto/get', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('53', '3', '角色查看', 'a', null, '/auth/role/proto/get', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('54', '4', '资源查看', 'a', null, '/auth/resource/proto/get', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('55', '2', '用户修改', 'a', null, '/auth/user/proto/put', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('56', '3', '角色修改', 'a', null, '/auth/role/proto/put', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('57', '4', '资源修改', 'a', null, '/auth/resource/proto/put', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('58', '2', '用户删除', 'a', null, '/auth/user/proto/delete', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('59', '3', '角色删除', 'a', null, '/auth/role/proto/delete', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('60', '4', '资源删除', 'a', null, '/auth/resource/proto/delete', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('61', '2', '用户批量删除', 'a', null, '/auth/user/proto/deleteList', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('62', '3', '角色批量删除', 'a', null, '/auth/role/proto/deleteList', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('63', '2', '分配角色查询', 'a', null, '/auth/authorization/get', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('64', '2', '分配资源查询', 'a', null, '/auth/permission/get', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('66', '2', '分配资源', 'a', null, '/auth/authorization/authz', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('67', '2', '分配资源', 'a', null, '/auth/permission/authz', '2018-03-23 20:13:51', '2018-03-23 20:13:51'), ('112', '0', '网关接口', 'd', '2', '/top/gateway', '2018-06-25 10:44:00', '2019-02-22 16:03:55'), ('114', '112', '应用管理', 'm', '1', '/gateway/app/list', '2018-06-25 10:50:19', '2018-06-25 10:50:19'), ('115', '112', '分组管理', 'm', '2', '/gateway/apigroup/list', '2018-06-25 10:51:19', '2018-06-25 19:46:55'), ('116', '112', '接口管理', 'm', '3', '/gateway/api/list', '2018-06-26 13:52:00', '2018-06-26 13:52:00'), ('119', '112', '用户管理', 'm', '0', '/gateway/partner/list', '2018-06-28 15:19:25', '2018-06-28 16:34:28'), ('160', '119', '打开新增页面', 'a', '1', '/gateway/partner/add', '2018-12-18 13:35:46', '2018-12-24 16:49:11'), ('161', '119', '打开编辑页面', 'a', '3', '/gateway/partner/edit', '2018-12-18 13:36:31', '2018-12-24 17:24:24'), ('162', '119', '删除用户操作', 'a', '5', '/gateway/partner/delete', '2018-12-18 13:36:51', '2018-12-24 17:24:41'), ('164', '114', '打开新增页面', 'a', '1', '/gateway/app/add', '2018-12-19 19:06:43', '2018-12-24 16:52:42'), ('165', '114', '打开编辑页面', 'a', '3', '/gateway/app/edit', '2018-12-19 19:07:11', '2018-12-24 17:21:07'), ('166', '114', '删除APP操作', 'a', '5', '/gateway/app/delete', '2018-12-19 19:07:43', '2018-12-24 17:21:28'), ('167', '114', '增加分组/api授权', 'a', '8', '/gateway/mgr/grant', '2018-12-19 19:12:32', '2018-12-24 17:21:51'), ('168', '114', '解除分组/API授权', 'a', '9', '/gateway/mgr/ungrant', '2018-12-19 19:12:59', '2018-12-24 17:23:43'), ('169', '115', '新增分组操作', 'a', '1', '/gateway/apigroup/proto/post', '2018-12-19 19:14:11', '2018-12-24 17:01:12'), ('170', '115', '打开编辑页面', 'a', '2', '/gateway/apigroup/edit', '2018-12-19 19:14:32', '2018-12-24 17:05:00'), ('171', '115', '删除分组操作', 'a', '4', '/gateway/apigroup/detail', '2018-12-19 19:14:47', '2018-12-24 17:05:28'), ('172', '116', '打开新增页面', 'a', '1', '/gateway/api/add', '2018-12-19 19:16:06', '2018-12-24 17:06:41'), ('173', '116', '打开编辑页面', 'a', '3', '/gateway/api/edit', '2018-12-19 19:16:33', '2018-12-24 17:10:15'), ('174', '116', '删除接口操作', 'a', '12', '/gateway/api/delete', '2018-12-19 19:17:16', '2018-12-24 17:17:05'), ('175', '116', '打开详情页面(需授予查看参数列表权限)', 'a', '13', '/gateway/api/detail', '2018-12-19 19:17:48', '2018-12-24 17:17:50'), ('176', '116', '打开测试页面', 'a', '14', '/gateway/api/testinfo', '2018-12-19 19:18:13', '2018-12-25 17:22:44'), ('177', '116', '同步接口操作', 'a', '16', '/gateway/api/send', '2018-12-19 19:18:32', '2018-12-25 17:23:02'), ('178', '119', '查看用户列表', 'a', '0', '/gateway/partner/proto/index', '2018-12-20 11:05:38', '2018-12-24 16:48:54'), ('179', '114', '查看APP列表', 'a', '0', '/gateway/app/proto/index', '2018-12-20 11:06:25', '2018-12-24 16:52:32'), ('180', '115', '查看分组列表', 'a', '0', '/gateway/apigroup/proto/index', '2018-12-20 11:09:24', '2018-12-24 17:00:33'), ('181', '116', '查看接口列表', 'a', '0', '/gateway/api/proto/search', '2018-12-20 11:10:49', '2018-12-24 17:06:29'), ('182', '116', '查看参数列表', 'a', '7', '/gateway/servicerequest/proto/index', '2018-12-20 13:39:23', '2018-12-24 17:11:10'), ('183', '116', '保存新增参数', 'a', '8', '/gateway/servicerequest/proto/post', '2018-12-20 13:41:12', '2018-12-24 17:13:00'), ('184', '116', '删除参数操作', 'a', '11', '/gateway/servicerequest/delete', '2018-12-20 13:41:54', '2018-12-24 17:15:16'), ('185', '114', '查看授权列表', 'a', '7', '/gateway/mgr/auth/grant/index', '2018-12-21 15:01:07', '2018-12-24 16:59:00'), ('186', '116', '保存响应信息', 'a', '5', '/gateway/apiresult/proto/put', '2018-12-24 11:12:54', '2018-12-24 17:16:32'), ('187', '116', '新增基本信息', 'a', '2', '/gateway/api/proto/post', '2018-12-24 11:27:26', '2018-12-24 17:07:26'), ('188', '115', '打开新增页面', 'a', '1', '/gateway/apigroup/add', '2018-12-24 13:27:37', '2018-12-24 17:00:53'), ('189', '119', '新增用户操作', 'a', '2', '/gateway/partner/proto/post', '2018-12-24 13:28:37', '2018-12-24 17:24:15'), ('190', '114', '新增APP操作', 'a', '2', '/gateway/app/proto/post', '2018-12-24 13:29:27', '2018-12-24 17:20:59'), ('191', '114', '保存编辑操作', 'a', '4', '/gateway/app/proto/put', '2018-12-24 13:30:41', '2018-12-24 17:21:16'), ('192', '116', '保存基本信息', 'a', '4', '/gateway/api/proto/put', '2018-12-24 13:39:10', '2018-12-24 17:16:10'), ('193', '116', '打开编辑页面(api参数)', 'a', '9', '/gateway/servicerequest/edit', '2018-12-24 13:43:47', '2018-12-24 17:25:04'), ('194', '116', '保存编辑信息(api参数)', 'a', '10', '/gateway/servicerequest/proto/put', '2018-12-24 13:44:19', '2018-12-24 17:14:26'), ('195', '119', '保存编辑操作', 'a', '4', '/gateway/partner/proto/put', '2018-12-24 16:51:49', '2018-12-24 16:51:49'), ('196', '114', '打开授权页面', 'a', '6', '/gateway/app/authorize', '2018-12-24 16:58:47', '2018-12-24 17:21:35'), ('197', '115', '保存编辑操作', 'a', '3', '/gateway/apigroup/proto/put', '2018-12-24 17:04:36', '2018-12-24 17:05:51'), ('198', '115', '打开分组内页', 'a', '5', '/gateway/apigroup/detail', '2018-12-25 11:31:27', '2018-12-25 11:31:27'), ('199', '119', '打开应用内页', 'a', '10', '/gateway/partner/detail', '2018-12-25 11:34:10', '2018-12-25 11:34:10'), ('200', '116', '测试接口操作', 'a', '15', '/gateway/api/tryTest', '2018-12-25 17:23:16', '2018-12-25 17:23:16'), ('205', '112', '标签管理', 'm', '4', '/gateway/mark/list', '2019-01-14 14:33:15', '2019-01-14 14:33:15'), ('206', '205', '查看标签列表', 'a', '1', '/gateway/mark/proto/index', '2019-01-15 19:07:21', '2019-01-15 19:07:21'), ('207', '205', '打开新增页面', 'a', '2', '/gateway/mark/add', '2019-01-15 19:07:44', '2019-01-15 19:08:20'), ('208', '205', '打开编辑页面', 'a', '3', '/gateway/mark/edit', '2019-01-15 19:08:42', '2019-01-15 19:08:42'), ('209', '205', '删除标签操作', 'a', '4', '/gateway/mark/delete', '2019-01-15 19:09:06', '2019-01-15 19:09:06'), ('210', '205', '查询一级标签', 'a', '5', '/gateway/mark/proto/select/level/one', '2019-01-15 19:09:33', '2019-01-15 19:09:33'), ('211', '205', '查询二级标签', 'a', '6', '/gateway/mark/proto/select/level/two', '2019-01-15 19:09:50', '2019-01-15 19:09:50'), ('212', '205', '查询三级标签', 'a', '7', '/gateway/mark/proto/select/level/three', '2019-01-15 19:10:10', '2019-01-15 19:10:10'), ('213', '205', '新增标签操作', 'a', '8', '/gateway/mark/proto/pos', '2019-01-15 19:10:51', '2019-01-15 19:12:13'), ('214', '205', '保存编辑信息', 'a', '9', '/gateway/mark/proto/post', '2019-01-15 19:11:39', '2019-01-15 19:12:05'), ('215', '205', '打开关联页面', 'a', '10', '/gateway/mark/check', '2019-01-15 19:13:19', '2019-01-15 19:13:19'), ('216', '205', '查看关联列表', 'a', '11', '/gateway/mark/proto/check/list', '2019-01-15 19:13:48', '2019-01-15 19:13:48'), ('217', '205', '保存关联关系', 'a', '12', '/gateway/mark/relationship', '2019-01-15 19:14:16', '2019-01-15 19:14:16'), ('218', '116', '复制接口操作', 'a', '17', '/gateway/api/copy', '2019-01-15 19:15:35', '2019-01-15 19:15:35');
COMMIT;

-- ----------------------------
--  Table structure for `auth_role`
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(30) NOT NULL,
  `CDT` datetime NOT NULL,
  `UDT` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_AUTH_ROLE` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `auth_role`
-- ----------------------------
BEGIN;
INSERT INTO `auth_role` VALUES ('1', '系统管理员', '2018-03-23 20:14:23', '2018-03-23 20:14:23');
COMMIT;

-- ----------------------------
--  Table structure for `auth_user`
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(30) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(100) NOT NULL,
  `CDT` datetime NOT NULL,
  `UDT` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_AUTH_USER` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `auth_user`
-- ----------------------------
BEGIN;
INSERT INTO `auth_user` VALUES ('20', 'root', '$shiro1$SHA-256$500000$Wx58eFr3FNgAuz+SoorCvQ==$5eKP4z8cUfQAUXDpcFAE76VcLdGyLl8kPVGQ2caR81M=', '2018-03-23 20:12:56', '2019-02-22 16:05:09');
COMMIT;

-- ----------------------------
--  Table structure for `gateway_api`
-- ----------------------------
DROP TABLE IF EXISTS `gateway_api`;
CREATE TABLE `gateway_api` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `GROUP_ID` bigint(20) unsigned NOT NULL COMMENT '分组id',
  `NAME` varchar(64) NOT NULL COMMENT 'api名称，英文字母和点，例如user.login',
  `DESCRIPTION` varchar(256) NOT NULL COMMENT '描述',
  `STATUS` int(11) NOT NULL COMMENT '状态 0删除 1上线2下线3未上线',
  `HTTP_METHOD` int(11) NOT NULL COMMENT '1=get 2=post',
  `IS_AUTH` int(11) NOT NULL COMMENT '是否鉴权 1=是 0=否',
  `IS_LOGIN` int(11) NOT NULL COMMENT '是否登录1=是 0=否',
  `AUTH_VERSION` int(11) DEFAULT NULL COMMENT '鉴权协议版本，如md5等',
  `SERVICE_NAME` varchar(256) DEFAULT NULL COMMENT '后端服务接口全路径',
  `SERVICE_METHOD` varchar(256) DEFAULT NULL COMMENT '后端服务方法名称',
  `SERVICE_VERSION` varchar(32) DEFAULT NULL COMMENT '服务版本号，必须与提供者服务版本号一致',
  `TIME_OUT` int(11) DEFAULT '2000' COMMENT '服务版本号，必须与提供者服务版本号一致',
  `CREATE_BY` varchar(128) DEFAULT NULL COMMENT '创建者',
  `UPDATE_BY` varchar(128) DEFAULT NULL COMMENT '修改者',
  `CDT` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `UDT` timestamp(6) NULL DEFAULT NULL COMMENT '修改时间',
  `VERSION` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `SPECIAL` int(1) DEFAULT '0',
  `RESPONSE_HEADER` varchar(300) DEFAULT NULL COMMENT '项目编号',
  PRIMARY KEY (`ID`),
  KEY `FK_APIGROUP_ID` (`GROUP_ID`),
  CONSTRAINT `FK_APIGROUP_ID` FOREIGN KEY (`GROUP_ID`) REFERENCES `gateway_api_group` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `gateway_api`
-- ----------------------------
BEGIN;
INSERT INTO `gateway_api` VALUES ('163', '37', 'user.login', '用户登录接口', '1', '1', '1', '0', '1', 'com.test.userService', 'login', '1.0.0.local', '3000', 'root', '未知', '2019-02-22 16:29:04.000000', '2019-02-22 16:50:02.000000', '1', '0', null);
COMMIT;

-- ----------------------------
--  Table structure for `gateway_api_group`
-- ----------------------------
DROP TABLE IF EXISTS `gateway_api_group`;
CREATE TABLE `gateway_api_group` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` varchar(64) NOT NULL COMMENT 'api group名称，支持汉字，英文，数字，下划线，且只能以英文和汉字开头',
  `DESCRIPTION` varchar(256) DEFAULT NULL COMMENT '描述',
  `STATUS` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1正常 0删除',
  `CREATE_BY` varchar(128) DEFAULT NULL COMMENT '创建者',
  `UPDATE_BY` varchar(128) DEFAULT NULL COMMENT '修改者',
  `CDT` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `UDT` timestamp(6) NULL DEFAULT NULL COMMENT '修改时间',
  `VERSION` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `gateway_api_group`
-- ----------------------------
BEGIN;
INSERT INTO `gateway_api_group` VALUES ('37', 'example', '示例分组数据', '1', 'root', null, '2019-02-22 16:28:03.000000', '2019-02-22 16:28:03.000000', '1');
COMMIT;

-- ----------------------------
--  Table structure for `gateway_api_mark`
-- ----------------------------
DROP TABLE IF EXISTS `gateway_api_mark`;
CREATE TABLE `gateway_api_mark` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mark_id` bigint(20) DEFAULT NULL COMMENT '标签id',
  `api_id` bigint(20) DEFAULT NULL COMMENT 'api接口ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `gateway_api_mark`
-- ----------------------------
BEGIN;
INSERT INTO `gateway_api_mark` VALUES ('1', '1', '163');
COMMIT;

-- ----------------------------
--  Table structure for `gateway_api_result`
-- ----------------------------
DROP TABLE IF EXISTS `gateway_api_result`;
CREATE TABLE `gateway_api_result` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `API_ID` bigint(20) unsigned NOT NULL COMMENT 'api id',
  `EXAMPLE` text COMMENT '结果示例',
  `RESULT_DESC` text COMMENT '响应参数说明',
  `CODE_DESC` text COMMENT '响应码说明',
  `CDT` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `UDT` timestamp(6) NULL DEFAULT NULL COMMENT '修改时间',
  `VERSION` int(11) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `FK_API_ID` (`API_ID`),
  CONSTRAINT `FK_API_ID` FOREIGN KEY (`API_ID`) REFERENCES `gateway_api` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `gateway_api_result`
-- ----------------------------
BEGIN;
INSERT INTO `gateway_api_result` VALUES ('1', '163', '{\n    \"code\":200,\n    \"message\":\"登录成功\",\n    \"data\":{\n        \"userId\":\"11111111\",\n        \"token\":\"22222222\"\n    }\n}', 'code//响应编码\nmessage//响应信息\ndata//响应数据\n {\n  userId//用户id\n  token //token信息\n}', '200 //成功\n500 //失败', '2019-02-22 16:33:17.000000', '2019-02-22 16:33:17.000000', '0');
COMMIT;

-- ----------------------------
--  Table structure for `gateway_app`
-- ----------------------------
DROP TABLE IF EXISTS `gateway_app`;
CREATE TABLE `gateway_app` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` varchar(64) NOT NULL COMMENT 'app名称',
  `DESCRIPTION` varchar(256) DEFAULT NULL COMMENT '描述',
  `PARTNER_ID` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `APP_KEY` varchar(64) NOT NULL COMMENT 'app_key',
  `APP_SECRET` varchar(64) NOT NULL COMMENT 'app secret',
  `AUTH_TYPE` int(11) NOT NULL DEFAULT '2' COMMENT '授权类型1=全部授权2=分级授权',
  `STATUS` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1正常 0删除',
  `CREATE_BY` varchar(128) DEFAULT NULL COMMENT '创建者',
  `UPDATE_BY` varchar(128) DEFAULT NULL COMMENT '修改者',
  `CDT` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `UDT` timestamp(6) NULL DEFAULT NULL COMMENT '修改时间',
  `VERSION` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `APP_KEY_UNIQUE` (`APP_KEY`),
  UNIQUE KEY `APP_SECRET_UNIQUE` (`APP_SECRET`),
  KEY `FK_PARTNER_ID` (`PARTNER_ID`),
  CONSTRAINT `FK_PARTNER_ID` FOREIGN KEY (`PARTNER_ID`) REFERENCES `gateway_partner` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `gateway_app`
-- ----------------------------
BEGIN;
INSERT INTO `gateway_app` VALUES ('39', '示例_APP', '示例APP数据', '25', '2274414A2A450DF30C8574BF0B9A81C2', '313D93A770AB64561425F98F99D9A936', '1', '1', 'root', null, '2019-02-22 16:27:46.000000', '2019-02-22 16:27:46.000000', '1');
COMMIT;

-- ----------------------------
--  Table structure for `gateway_app_auth`
-- ----------------------------
DROP TABLE IF EXISTS `gateway_app_auth`;
CREATE TABLE `gateway_app_auth` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `APP_ID` bigint(20) unsigned NOT NULL COMMENT 'app id',
  `AUTH_ID` bigint(20) unsigned NOT NULL COMMENT '被授权id',
  `AUTH_ID_TYPE` int(11) NOT NULL COMMENT '被授权id类型1=服务分组id2=服务id',
  `AUTH_NAME` varchar(64) NOT NULL COMMENT '被授权对象名称',
  `CDT` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `UDT` timestamp(6) NULL DEFAULT NULL COMMENT '修改时间',
  `VERSION` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `FK_REQUEST_APP_ID` (`APP_ID`),
  CONSTRAINT `FK_REQUEST_APP_ID` FOREIGN KEY (`APP_ID`) REFERENCES `gateway_app` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `gateway_app_auth`
-- ----------------------------
BEGIN;
INSERT INTO `gateway_app_auth` VALUES ('1', '39', '37', '1', 'example', '2019-02-22 16:50:55.000000', '2019-02-22 16:50:55.000000', '1');
COMMIT;

-- ----------------------------
--  Table structure for `gateway_ipset`
-- ----------------------------
DROP TABLE IF EXISTS `gateway_ipset`;
CREATE TABLE `gateway_ipset` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `type` tinyint(10) DEFAULT NULL COMMENT '类型  默认是1',
  `desc` varchar(500) DEFAULT NULL COMMENT '描述信息',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改者',
  `status` tinyint(10) DEFAULT NULL COMMENT '状态 1正常 0 删除',
  `cdt` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `udt` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `gateway_mark`
-- ----------------------------
DROP TABLE IF EXISTS `gateway_mark`;
CREATE TABLE `gateway_mark` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `level` tinyint(4) DEFAULT '0' COMMENT '层级关系,有且只有1，2，3层',
  `root_id` bigint(20) DEFAULT NULL COMMENT '根节点ID',
  `branch_id` bigint(20) DEFAULT NULL COMMENT '枝节点ID',
  `leaf_id` bigint(20) DEFAULT NULL COMMENT '叶节点ID',
  `name` varchar(50) DEFAULT NULL COMMENT '标签名称',
  `desc` varchar(500) DEFAULT NULL COMMENT '标签描述',
  `status` tinyint(4) DEFAULT NULL COMMENT '1正常0删除',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改者',
  `cdt` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `udt` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `gateway_mark`
-- ----------------------------
BEGIN;
INSERT INTO `gateway_mark` VALUES ('1', '1', '1', '0', '0', '测试标签', '这是一个测试标签', '1', 'root', null, '2019-02-22 16:35:40', '2019-02-22 16:35:40', '0');
COMMIT;

-- ----------------------------
--  Table structure for `gateway_partner`
-- ----------------------------
DROP TABLE IF EXISTS `gateway_partner`;
CREATE TABLE `gateway_partner` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` varchar(64) NOT NULL COMMENT '用户名称',
  `PARTNER_KEY` varchar(64) NOT NULL COMMENT 'key',
  `DESCRIPTION` varchar(256) DEFAULT NULL COMMENT '描述',
  `STATUS` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1正常 0删除',
  `CREATE_BY` varchar(128) DEFAULT NULL COMMENT '创建者',
  `UPDATE_BY` varchar(128) DEFAULT NULL COMMENT '修改者',
  `CDT` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `UDT` timestamp(6) NULL DEFAULT NULL COMMENT '修改时间',
  `VERSION` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME_UNIQUE` (`NAME`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `gateway_partner`
-- ----------------------------
BEGIN;
INSERT INTO `gateway_partner` VALUES ('25', '示例用户', '1351CE975CBFD28AAA5FBB7882A491FC', '示例用户数据', '1', 'root', null, '2019-02-22 16:27:07.000000', '2019-02-22 16:27:07.000000', '1');
COMMIT;

-- ----------------------------
--  Table structure for `gateway_service_request`
-- ----------------------------
DROP TABLE IF EXISTS `gateway_service_request`;
CREATE TABLE `gateway_service_request` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `API_ID` bigint(20) unsigned NOT NULL COMMENT 'api id',
  `INDEX` int(11) NOT NULL COMMENT '参数顺序，从0开始',
  `NAME` varchar(64) NOT NULL COMMENT '参数名称',
  `TYPE` int(11) NOT NULL COMMENT '参数类型 1=String,2=int,3=long,4=boolean,5=float,6=dubble,7=自定义类型',
  `TYPE_NAME` varchar(256) NOT NULL COMMENT '自定义类型类全名称 和type对应 1=String,2=int,3=long,4=boolean,5=float,6=dubble,7=com.kaishiba.domain.XX,',
  `IS_REQUIRED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否必传（是否可空）',
  `EXAMPLE` text COMMENT '参数示例',
  `DESCRIPTION` text COMMENT '参数说明',
  `CDT` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `UDT` timestamp(6) NULL DEFAULT NULL COMMENT '修改时间',
  `VERSION` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `FK_REQUEST_API_ID` (`API_ID`),
  CONSTRAINT `FK_REQUEST_API_ID` FOREIGN KEY (`API_ID`) REFERENCES `gateway_api` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `gateway_service_request`
-- ----------------------------
BEGIN;
INSERT INTO `gateway_service_request` VALUES ('1', '163', '0', 'username', '1', 'java.lang.String', '1', 'admin', '登录名', '2019-02-22 16:34:23.000000', '2019-02-22 16:35:03.000000', '0'), ('2', '163', '1', 'password', '1', 'java.lang.String', '1', '123456', '登录密码', '2019-02-22 16:34:53.000000', '2019-02-22 16:34:53.000000', '0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
