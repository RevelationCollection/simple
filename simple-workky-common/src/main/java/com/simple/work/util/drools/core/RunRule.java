package com.simple.work.util.drools.core;

import com.simple.work.util.drools.bean.RuleObj;
import org.kie.api.runtime.KieContainer;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wy
 */
public class RunRule {

	private static Map<String, KieContainer> ruleMap = new ConcurrentHashMap<>();

	private static final Logger logger = LoggerFactory.getLogger(RunRule.class);

	/**
	 * 用Mvel得出当前数据是否需要后续操作,Y为需要,N为不需要
	 * @param map
	 * @param str
	 * @return
	 */
	public String getResultByMvel(Map<String, String> map, String str) {
		Map<String, Object> inputMap = new HashMap<String, Object>(50);
		inputMap.put("map", map);
		Serializable r = MVEL.compileExpression(str);
		String re = (String) MVEL.executeExpression(r, inputMap);
		return re;
	}

	/** 执行规则 */
	public static void fireRule(String key, RuleObj data) {
	    KieContainer kieContainer = ruleMap.get(key);
		if (kieContainer != null) {
			DroolUtil.executeRuleEngine(kieContainer, data);
		}
	}

	/** 系统启动时加载 -- 重载方法 */
    public static synchronized void initRuleEngine(String key, List<String> path) {
        //key = M0008t0_1
        KieContainer kieContainer = ruleMap.get(key);
        if(kieContainer!=null){
            logger.info("文件已存在:{},path:{}",key,path);
            return ;
        }
        logger.info("初始化流程key:{},path:{}",key,path);
        try {
            kieContainer = DroolUtil.initKieContainer(path);
        } catch (Exception e) {
            logger.error("初始化流程 ： " + key + "失败 加载规则文件失败。",e);
        }
        if(kieContainer==null){
            throw new RuntimeException(" rule is null");
        }
        ruleMap.put(key, kieContainer);
        logger.info("初始化流程 成功！！： " + key + "");
    }

	public static String exeRuleEngine(Map<String, String> param, RuleObj data) {
		String verStr = param.get("version");
		String ruleName = param.get("ruleName");
		String key = ruleName+"_"+verStr;
		KieContainer kieContainer = ruleMap.get(key);
		List<String> list = transPath2List(param);
		String res = "N";
		if (kieContainer == null) {
			logger.info("初始化流程");
			initRuleEngine(key, list);
		}
		kieContainer = ruleMap.get(key);
		if(kieContainer!=null){
		    res = "Y";
		    fireRule(key, data);
		}
		return res;
	}

	/**
	 * 执行规则引擎
	 * @param modelCode 模型编号
	 * @param modelVersion 模型版本
	 * @param ruleFilePath 执行的规则文件本地路径
	 * @param data 执行的数据
	 * @return 执行结果
	 */
	public static String exeRuleEngine(String modelCode,String modelVersion,String ruleFilePath, RuleObj data) {
		Map<String, String> param = new HashMap<>(8);
		param.put("ruleName", modelCode);
		param.put("version", modelVersion);
		param.put("path", ruleFilePath);
		return exeRuleEngine(param,data);
	}

	/** 刷新规则 -- 重载 */
	public synchronized static void refRuleEngine(String key, List<String> path) {
		logger.info("刷新流程 ：name:{},path ：{} " ,key,path);
		KieContainer kieContainer = ruleMap.get(key);
		if (kieContainer != null) {
		    ruleMap.remove(key);
		}
		initRuleEngine(key, path);
	}

	private static List<String> transPath2List(Map<String, String> param) {
		String pathStr = param.get("path");
		String[] paths = pathStr.split(";");
		List<String> list = new ArrayList<String>();
		for (String s : paths) {
			list.add(s);
		}
		return list;
	}

}