CREATE TABLE `cd_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序列主键',
  `model_code` varchar(10) NOT NULL COMMENT '模型编号',
  `rule_code` varchar(10) NOT NULL COMMENT '规则编号（维度）',
  `rule_name` varchar(200) DEFAULT NULL COMMENT '规则名称',
  `rule_dim` varchar(10) NOT NULL COMMENT '规则维度 主维度:P 子维度:C',
  `p_rule_code` varchar(10) DEFAULT NULL COMMENT '主维度编码',
  `rule_weight` decimal(10,2) DEFAULT NULL COMMENT '权重值',
  `rule_score` decimal(10,2) DEFAULT NULL COMMENT '标准分数',
  `hit_score` decimal(10,2) DEFAULT NULL COMMENT '命中后得分',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) NOT NULL COMMENT '状态 TRUE:可用 FALSE:禁用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(30) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `rule` (`rule_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COMMENT='规则表';


CREATE TABLE `cd_rule_func_base` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序列主键',
  `func_base_code` varchar(10) DEFAULT NULL COMMENT '基础函数编号',
  `func_base_name` varchar(200) DEFAULT NULL COMMENT '基础函数名称',
  `cn_express` varchar(1000) DEFAULT NULL COMMENT '中文表达式',
  `en_express` varchar(200) DEFAULT NULL COMMENT '英文表达式',
  `express_form_params` varchar(200) DEFAULT NULL COMMENT '表达式形式参数',
  `func_info` varchar(2000) DEFAULT NULL COMMENT '函数内容',
  `status` varchar(10) DEFAULT NULL COMMENT '状态 TRUE:可用 FALSE:禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(30) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`func_base_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='规则基础函数表';



CREATE TABLE `cd_rule_func_ref` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序列主键',
  `model_code` varchar(10) NOT NULL COMMENT '模型编号',
  `func_ref_code` varchar(10) NOT NULL COMMENT '关联函数编号',
  `rule_code` varchar(10) NOT NULL COMMENT '规则编号',
  `func_base_code` varchar(10) DEFAULT NULL COMMENT '基础函数编号',
  `express_real_params` varchar(200) DEFAULT NULL COMMENT '表达式实际参数',
  `status` varchar(10) NOT NULL COMMENT '状态 TRUE:可用 FALSE:禁用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(30) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `refCode` (`func_ref_code`) USING BTREE,
  KEY `ruleCode` (`rule_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COMMENT='规则引用函数表';


CREATE TABLE `cd_rule_hit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序列主键',
  `diag_date` varchar(30) NOT NULL COMMENT '诊断日期',
  `pan` varchar(30) NOT NULL COMMENT '卡号',
  `model_code` varchar(10) NOT NULL COMMENT '模型编号',
  `rule_code` varchar(10) NOT NULL COMMENT '规则编号（维度）',
  `p_rule_code` varchar(10) NOT NULL COMMENT '主维度编码',
  `hit_score` decimal(10,2) DEFAULT NULL COMMENT '命中后得分',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) NOT NULL COMMENT '状态 TRUE:可用 FALSE:禁用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(30) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `panDate` (`pan`,`diag_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8mb4 COMMENT='规则命中表';


CREATE TABLE `cd_rule_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序列主键',
  `model_code` varchar(10) NOT NULL COMMENT '模型编号',
  `model_desc` varchar(200) DEFAULT NULL COMMENT '模型描述',
  `model_version` varchar(10) NOT NULL COMMENT '模型版本',
  `current_version` varchar(10) NOT NULL COMMENT '缓存当前版本',
  `status` varchar(10) NOT NULL COMMENT '状态 TRUE:可用 FALSE:禁用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(30) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`model_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='规则模型表';



INSERT INTO `card_diag`.`cd_rule_func_base` (`id`, `func_base_code`, `func_base_name`, `cn_express`, `en_express`, `express_form_params`, `func_info`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('5', 'FB000005', '范围比较', '{key}的值在[{startAmount},{endAmount}]范围内', 'checkBetweenAnd($c,startAmount,endAmount,key)', '{\"startAmount\":\"String\",\"endAmount\":\"String\",\"key\":\"String\"}', 'function boolean checkBetweenAnd(RuleObj ruleObj, String startAmount, String endAmount,String key) {\r\n		Object amountObject = ruleObj.getRuleDetail().get(key);\r\n		if (StringUtils.isEmpty(amountObject)) {\r\n			System.out.println(key+\",checkBetweenAnd is  null \");\r\n			return false;\r\n		}\r\n		BigDecimal transAmount = new BigDecimal(String.valueOf(amountObject));\r\n		BigDecimal startBigDecimal = new BigDecimal(startAmount);\r\n		BigDecimal endBigDecimal = new BigDecimal(endAmount);\r\n		if (transAmount.compareTo(startBigDecimal) >= 0 && transAmount.compareTo(endBigDecimal) <= 0) {\r\n			return true;\r\n		}\r\n		return false;\r\n}', 'TRUE', '2019-11-07 11:37:10', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_base` (`id`, `func_base_code`, `func_base_name`, `cn_express`, `en_express`, `express_form_params`, `func_info`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('6', 'FB000006', '大于等于', '{key}的值≥{limit}', 'checkGreaterThanAnd($c,limit,key)', '{\"limit\":\"String\",\"key\":\"String\"}', 'function boolean checkGreaterThanAnd(RuleObj ruleObj, String limit,String key) {\r\n		Object amountObject = ruleObj.getRuleDetail().get(key);\r\n		if (StringUtils.isEmpty(amountObject)) {\r\n			System.out.println(key+\",checkGreaterThanAnd is  null \");\r\n			return false;\r\n		}\r\n		BigDecimal transAmount = new BigDecimal(String.valueOf(amountObject));\r\n		BigDecimal start = new BigDecimal(limit);\r\n		if (transAmount.compareTo(start) >= 0) {\r\n			return true;\r\n		}\r\n		return false;\r\n}', 'TRUE', '2019-11-07 13:44:09', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_base` (`id`, `func_base_code`, `func_base_name`, `cn_express`, `en_express`, `express_form_params`, `func_info`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('7', 'FB000007', '小于等于', '{key}的值≤{limit}', 'checkLessThanAnd($c,limit,key)', '{\"limit\":\"String\",\"key\":\"String\"}', 'function boolean checkLessThanAnd(RuleObj ruleObj, String limit,String key) {\r\n		Object amountObject = ruleObj.getRuleDetail().get(key);\r\n		if (StringUtils.isEmpty(amountObject)) {\r\n			System.out.println(key+\",checkLessThanAnd is  null \");\r\n			return false;\r\n		}\r\n		BigDecimal transAmount = new BigDecimal(String.valueOf(amountObject));\r\n		BigDecimal start = new BigDecimal(limit);\r\n		if (transAmount.compareTo(start) <= 0) {\r\n			return true;\r\n		}\r\n		return false;\r\n}', 'TRUE', '2019-11-07 14:04:56', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_base` (`id`, `func_base_code`, `func_base_name`, `cn_express`, `en_express`, `express_form_params`, `func_info`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('8', 'FB000008', '大于', '{key}的值＞{limit}', 'checkGreaterThan($c,limit,key)', '{\"limit\":\"String\",\"key\":\"String\"}', 'function boolean checkGreaterThan(RuleObj ruleObj, String limit,String key) {\r\n		Object amountObject = ruleObj.getRuleDetail().get(key);\r\n		if (StringUtils.isEmpty(amountObject)) {\r\n			System.out.println(key+\",checkGreaterThan is  null \");\r\n			return false;\r\n		}\r\n		BigDecimal transAmount = new BigDecimal(String.valueOf(amountObject));\r\n		BigDecimal start = new BigDecimal(limit);\r\n		if (transAmount.compareTo(start) > 0) {\r\n			return true;\r\n		}\r\n		return false;\r\n}', 'TRUE', '2019-11-07 14:05:01', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_base` (`id`, `func_base_code`, `func_base_name`, `cn_express`, `en_express`, `express_form_params`, `func_info`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('9', 'FB000009', '小于', '{key}的值＜{limit}', 'checkLessThan($c,limit,key)', '{\"limit\":\"String\",\"key\":\"String\"}', 'function boolean checkLessThan(RuleObj ruleObj, String limit,String key) {\r\n		Object amountObject = ruleObj.getRuleDetail().get(key);\r\n		if (StringUtils.isEmpty(amountObject)) {\r\n			System.out.println(key+\",checkLessThan is  null \");\r\n			return false;\r\n		}\r\n		BigDecimal transAmount = new BigDecimal(String.valueOf(amountObject));\r\n		BigDecimal start = new BigDecimal(limit);\r\n		if (transAmount.compareTo(start) < 0) {\r\n			return true;\r\n		}\r\n		return false;\r\n}', 'TRUE', '2019-11-07 14:06:16', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_base` (`id`, `func_base_code`, `func_base_name`, `cn_express`, `en_express`, `express_form_params`, `func_info`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('10', 'FB000010', '范围比较', '{key}的值在[{startAmount},{endAmount})范围内', 'checkBetweenLessMax($c,startAmount,endAmount,key)', '{\"startAmount\":\"String\",\"endAmount\":\"String\",\"key\":\"String\"}', 'function boolean checkBetweenLessMax(RuleObj ruleObj, String startAmount, String endAmount,String key) {\r\n		Object amountObject = ruleObj.getRuleDetail().get(key);\r\n		if (StringUtils.isEmpty(amountObject)) {\r\n			System.out.println(key+\",checkBetweenLessMax is  null \");\r\n			return false;\r\n		}\r\n		BigDecimal transAmount = new BigDecimal(String.valueOf(amountObject));\r\n		BigDecimal startBigDecimal = new BigDecimal(startAmount);\r\n		BigDecimal endBigDecimal = new BigDecimal(endAmount);\r\n		if (transAmount.compareTo(startBigDecimal) >= 0 && transAmount.compareTo(endBigDecimal) < 0) {\r\n			return true;\r\n		}\r\n		return false;\r\n}', 'TRUE', '2019-11-07 14:13:58', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_base` (`id`, `func_base_code`, `func_base_name`, `cn_express`, `en_express`, `express_form_params`, `func_info`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('11', 'FB000011', '范围比较', '{key}的值在({startAmount},{endAmount}]范围内', 'checkBetweenGreaterMin($c,startAmount,endAmount,key)', '{\"startAmount\":\"String\",\"endAmount\":\"String\",\"key\":\"String\"}', 'function boolean checkBetweenGreaterMin(RuleObj ruleObj, String startAmount, String endAmount,String key) {\r\n		Object amountObject = ruleObj.getRuleDetail().get(key);\r\n		if (StringUtils.isEmpty(amountObject)) {\r\n			System.out.println(key+\",checkBetweenGreaterMin is  null \");\r\n			return false;\r\n		}\r\n		BigDecimal transAmount = new BigDecimal(String.valueOf(amountObject));\r\n		BigDecimal startBigDecimal = new BigDecimal(startAmount);\r\n		BigDecimal endBigDecimal = new BigDecimal(endAmount);\r\n		if (transAmount.compareTo(startBigDecimal) > 0 && transAmount.compareTo(endBigDecimal) <= 0) {\r\n			return true;\r\n		}\r\n		return false;\r\n}', 'TRUE', '2019-11-07 14:15:50', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_base` (`id`, `func_base_code`, `func_base_name`, `cn_express`, `en_express`, `express_form_params`, `func_info`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('12', 'FB000012', '获取笔均', '获取总数{total}/笔数{count}的笔均{key}', 'saveAvgKey($c,total,count,key)', '{\"total\":\"String\",\"count\":\"String\",\"key\":\"String\"}', 'function boolean saveAvgKey(RuleObj ruleObj, String total, String count,String key) {\r\n    Map<String,Object> map = ruleObj.getRuleDetail();\r\n    Object totalObject = map.get(total);\r\n    if (StringUtils.isEmpty(totalObject)) {\r\n        System.out.println(key+\",totalObject is  null \");\r\n        return false;\r\n    }\r\n    Object countObject = ruleObj.getRuleDetail().get(count);\r\n    if (StringUtils.isEmpty(countObject)) {\r\n        System.out.println(key+\",countObject is  null \");\r\n        return false;\r\n    }\r\n    BigDecimal totalAmount = new BigDecimal(String.valueOf(totalObject));\r\n    BigDecimal countAmount = new BigDecimal(String.valueOf(countObject));\r\n    BigDecimal divide = totalAmount.divide(countAmount);\r\n    map.put(key, divide.toString());\r\n    return true;\r\n}', 'TRUE', '2019-11-07 14:15:50', NULL, NULL, NULL);



INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('4', 'RISK_LEVEL', 'RF00004', 'R00005', 'FB000012', '{\"total\":\"CP3016\",\"count\":\"CP3017\",\"key\":\"threeAvgAmount\"}', 'FALSE', '2019-11-07 14:34:52', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('5', 'RISK_LEVEL', 'RF00005', 'R00005', 'FB000010', '{\"startAmount\":\"0\",\"endAmount\":\"4000\",\"key\":\"CP3020\"}', 'TRUE', '2019-11-07 14:53:36', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('6', 'RISK_LEVEL', 'RF00006', 'R00006', 'FB000012', '{\"total\":\"CP3016\",\"count\":\"CP3017\",\"key\":\"threeAvgAmount\"}', 'FALSE', '2019-11-07 15:06:46', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('7', 'RISK_LEVEL', 'RF00007', 'R00006', 'FB000010', '{\"startAmount\":\"4000\",\"endAmount\":\"10000\",\"key\":\"CP3020\"}', 'TRUE', '2019-11-07 15:07:21', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('8', 'RISK_LEVEL', 'RF00008', 'R00007', 'FB000012', '{\"total\":\"CP3016\",\"count\":\"CP3017\",\"key\":\"threeAvgAmount\"}', 'FALSE', '2019-11-07 15:09:43', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('9', 'RISK_LEVEL', 'RF00009', 'R00007', 'FB000006', '{\"limit\":\"10000\",\"key\":\"CP3020\"}', 'TRUE', '2019-11-07 15:10:47', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('10', 'RISK_LEVEL', 'RF00010', 'R00008', 'FB000006', '{\"limit\":\"10000\",\"key\":\"CP2375\"}', 'TRUE', '2019-11-07 15:15:47', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('11', 'RISK_LEVEL', 'RF00011', 'R00009', 'FB000006', '{\"limit\":\"10000\",\"key\":\"CP3375\"}', 'TRUE', '2019-11-07 15:15:50', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('12', 'RISK_LEVEL', 'RF00012', 'R00010', 'FB000006', '{\"limit\":\"20000\",\"key\":\"CP4375\"}', 'TRUE', '2019-11-07 15:16:15', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('13', 'RISK_LEVEL', 'RF00013', 'R00011', 'FB000006', '{\"limit\":\"20000\",\"key\":\"CP5375\"}', 'TRUE', '2019-11-07 15:16:37', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('14', 'RISK_LEVEL', 'RF00014', 'R00015', 'FB000009', '{\"limit\":\"2\",\"key\":\"CP2384\"}', 'TRUE', '2019-11-07 17:15:16', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('15', 'RISK_LEVEL', 'RF00015', 'R00016', 'FB000009', '{\"limit\":\"3\",\"key\":\"CP3384\"}', 'TRUE', '2019-11-07 17:15:20', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('16', 'RISK_LEVEL', 'RF00016', 'R00017', 'FB000009', '{\"limit\":\"3\",\"key\":\"CP4384\"}', 'TRUE', '2019-11-07 17:16:11', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('17', 'RISK_LEVEL', 'RF00017', 'R00018', 'FB000009', '{\"limit\":\"4\",\"key\":\"CP5384\"}', 'TRUE', '2019-11-07 17:17:15', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('18', 'RISK_LEVEL', 'RF00018', 'R00020', 'FB000008', '{\"limit\":\"1\",\"key\":\"CP2219\"}', 'TRUE', '2019-11-07 17:24:00', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('19', 'RISK_LEVEL', 'RF00019', 'R00022', 'FB000008', '{\"limit\":\"10000\",\"key\":\"CP2226\"}', 'TRUE', '2019-11-07 17:43:48', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('20', 'RISK_LEVEL', 'RF00020', 'R00023', 'FB000008', '{\"limit\":\"10000\",\"key\":\"CP3226\"}', 'TRUE', '2019-11-07 17:44:38', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('21', 'RISK_LEVEL', 'RF00021', 'R00024', 'FB000008', '{\"limit\":\"10000\",\"key\":\"CP4226\"}', 'TRUE', '2019-11-07 17:45:13', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('22', 'RISK_LEVEL', 'RF00022', 'R00025', 'FB000008', '{\"limit\":\"10000\",\"key\":\"CP5226\"}', 'TRUE', '2019-11-07 17:47:10', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('23', 'RISK_LEVEL', 'RF00023', 'R00027', 'FB000008', '{\"limit\":\"1000\",\"key\":\"CP2242\"}', 'TRUE', '2019-11-07 18:27:35', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('24', 'RISK_LEVEL', 'RF00024', 'R00028', 'FB000008', '{\"limit\":\"3000\",\"key\":\"CP3242\"}', 'TRUE', '2019-11-07 18:16:34', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('25', 'RISK_LEVEL', 'RF00025', 'R00029', 'FB000008', '{\"limit\":\"10000\",\"key\":\"CP4242\"}', 'TRUE', '2019-11-07 18:16:34', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('26', 'RISK_LEVEL', 'RF00026', 'R00030', 'FB000008', '{\"limit\":\"10000\",\"key\":\"CP5242\"}', 'TRUE', '2019-11-07 18:16:34', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('27', 'RISK_LEVEL', 'RF00027', 'R00032', 'FB000008', '{\"limit\":\"2000\",\"key\":\"CP3299\"}', 'TRUE', '2019-11-13 17:46:50', '', NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('28', 'RISK_LEVEL', 'RF00028', 'R00033', 'FB000008', '{\"limit\":\"5000\",\"key\":\"CP4299\"}', 'TRUE', '2019-11-13 17:46:50', '', NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('29', 'RISK_LEVEL', 'RF00029', 'R00034', 'FB000008', '{\"limit\":\"8000\",\"key\":\"CP5299\"}', 'TRUE', '2019-11-14 14:39:14', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('30', 'RISK_LEVEL', 'RF00030', 'R00036', 'FB000008', '{\"limit\":\"0.2\",\"key\":\"Z00078\"}', 'TRUE', '2019-11-14 14:41:29', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('31', 'RISK_LEVEL', 'RF00031', 'R00037', 'FB000008', '{\"limit\":\"0.2\",\"key\":\"Z00079\"}', 'TRUE', '2019-11-14 14:41:29', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('32', 'RISK_LEVEL', 'RF00032', 'R00038', 'FB000008', '{\"limit\":\"0.3\",\"key\":\"Z00080\"}', 'TRUE', '2019-11-14 14:41:29', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('33', 'RISK_LEVEL', 'RF00033', 'R00039', 'FB000008', '{\"limit\":\"0.3\",\"key\":\"Z00081\"}', 'TRUE', '2019-11-14 14:41:29', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('34', 'RISK_LEVEL', 'RF00034', 'R00041', 'FB000008', '{\"limit\":\"0.5\",\"key\":\"Z00086\"}', 'TRUE', '2019-11-14 14:43:49', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('35', 'RISK_LEVEL', 'RF00035', 'R00042', 'FB000008', '{\"limit\":\"0.5\",\"key\":\"Z00087\"}', 'TRUE', '2019-11-14 14:43:49', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule_func_ref` (`id`, `model_code`, `func_ref_code`, `rule_code`, `func_base_code`, `express_real_params`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('36', 'RISK_LEVEL', 'RF00036', 'R00043', 'FB000008', '{\"limit\":\"0.5\",\"key\":\"Z00088\"}', 'TRUE', '2019-11-14 14:43:49', NULL, NULL, NULL);


INSERT INTO `card_diag`.`cd_rule_model` (`id`, `model_code`, `model_desc`, `model_version`, `current_version`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('3', 'SEIZE_CASH', '套现分数', '3', '3', 'TRUE', '2019-11-07 11:19:21', 'test', '2019-11-15 09:47:20', NULL);
INSERT INTO `card_diag`.`cd_rule_model` (`id`, `model_code`, `model_desc`, `model_version`, `current_version`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('4', 'RISK_LEVEL', '风险级别', '9', '9', 'TRUE', '2019-11-07 11:20:01', 'test', '2019-12-20 13:59:54', NULL);



INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('5', 'RISK_LEVEL', 'R00005', '近3月笔均消费在[0,4000)', 'C', 'R00012', '1.00', '0.00', '0.00', '笔均消费', 'TRUE', '2019-11-07 14:10:24', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('6', 'RISK_LEVEL', 'R00006', '近3月笔均消费在[4000,10000)', 'C', 'R00012', '1.00', '200.00', '200.00', '笔均消费', 'TRUE', '2019-11-07 14:11:31', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('7', 'RISK_LEVEL', 'R00007', '近3月笔均消费≥10000', 'C', 'R00012', '1.00', '500.00', '500.00', '笔均消费', 'TRUE', '2019-11-07 14:17:55', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('8', 'RISK_LEVEL', 'R00008', '近1月整额消费金额≥1w', 'C', 'R00013', '0.40', '500.00', '200.00', '整额消费', 'TRUE', '2019-11-07 14:20:16', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('9', 'RISK_LEVEL', 'R00009', '近3月整额消费金额≥1w', 'C', 'R00013', '0.30', '500.00', '150.00', '整额消费', 'TRUE', '2019-11-07 14:22:45', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('10', 'RISK_LEVEL', 'R00010', '近6月整额消费金额≥2w', 'C', 'R00013', '0.20', '500.00', '100.00', '整额消费', 'TRUE', '2019-11-07 14:23:35', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('11', 'RISK_LEVEL', 'R00011', '近12月整额消费金额≥2w', 'C', 'R00013', '0.10', '500.00', '50.00', '整额消费', 'TRUE', '2019-11-07 14:24:14', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('12', 'RISK_LEVEL', 'R00012', '近3月笔均消费金额', 'P', NULL, '0.00', '500.00', '0.00', '笔均消费', 'TRUE', '2019-11-07 16:34:51', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('13', 'RISK_LEVEL', 'R00013', '整额消费金额', 'P', NULL, '0.00', '500.00', '0.00', '整额消费', 'TRUE', '2019-11-07 16:35:41', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('14', 'RISK_LEVEL', 'R00014', '发生消费的MCC种类数', 'P', NULL, '0.00', '500.00', '0.00', 'MCC种类', 'TRUE', '2019-11-07 16:42:37', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('15', 'RISK_LEVEL', 'R00015', '近1月发生消费的MCC种类数<2', 'C', 'R00014', '0.40', '500.00', '200.00', 'MCC种类', 'TRUE', '2019-11-07 16:49:52', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('16', 'RISK_LEVEL', 'R00016', '近2月发生消费的MCC种类数<3', 'C', 'R00014', '0.30', '500.00', '150.00', 'MCC种类', 'TRUE', '2019-11-07 17:08:47', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('17', 'RISK_LEVEL', 'R00017', '近6月发生消费的MCC种类数<3', 'C', 'R00014', '0.20', '500.00', '100.00', 'MCC种类', 'TRUE', '2019-11-07 17:09:43', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('18', 'RISK_LEVEL', 'R00018', '近12月发生消费的MCC种类数<4', 'C', 'R00014', '0.10', '500.00', '50.00', 'MCC种类', 'TRUE', '2019-11-07 17:12:28', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('19', 'RISK_LEVEL', 'R00019', '银行关闭交易笔数', 'P', NULL, '0.00', '1000.00', '0.00', NULL, 'TRUE', '2019-11-07 17:19:36', NULL, NULL, NULL);
INSERT INTO `card_diag`.`cd_rule` (`id`, `model_code`, `rule_code`, `rule_name`, `rule_dim`, `p_rule_code`, `rule_weight`, `rule_score`, `hit_score`, `memo`, `status`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('20', 'RISK_LEVEL', 'R00020', '近3月银行关闭交易笔数', 'C', 'R00019', '1.00', '1000.00', '1000.00', NULL, 'TRUE', '2019-11-07 17:22:42', NULL, NULL, NULL);
