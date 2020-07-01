package com.simple.work.util.drools.core;

import com.simple.work.util.drools.bean.RuleObj;
import org.kie.api.runtime.KieSession;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** @author wy */
public class RunRuleSession {

	private static Map<String, KieSession> ruleMap = new ConcurrentHashMap<>();

	private static final Logger logger = LoggerFactory.getLogger(RunRuleSession.class);

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

	/**
	 * 执行规则
	 * @time: 2019年11月16日 上午6:11:05
	 */
	public static void frieRule(String key, RuleObj data) {
	    KieSession kieSession = ruleMap.get(key);
		if (kieSession != null) {
			DroolUtil.executeRuleEngine(kieSession, data);
		}
	}

	/**
	 * 系统启动时加载 -- 重载方法
	 * @time: 2019年11月16日 上午6:11:17
	 */
    public static synchronized void initRuleEngine(String key, List<String> path) {
        KieSession kieSession = ruleMap.get(key);
        if(kieSession!=null){
            logger.info("文件已存在:{},path:{}",key,path);
            return ;
        }
        logger.info("初始化流程key:{},path:{}",key,path);
        kieSession = null;
        try {
            kieSession = DroolUtil.initKieSession(path);
        } catch (Exception e) {
            logger.error("初始化流程 ： " + key + "失败 加载规则文件失败。",e);
        }
        if(kieSession==null){
            throw new RuntimeException(" rule is null");
        }
        ruleMap.put(key, kieSession);
        logger.info("初始化流程 成功！！： " + key);
    }

	public static String exeRuleEngine(Map<String, String> param, RuleObj data) {
		String verStr = param.get("version");
		String ruleName = param.get("ruleName");
		String key = ruleName+"_"+verStr;
		KieSession kieSession = ruleMap.get(key);
		List<String> list = transPath2List(param);
		String res = "N";
		if (kieSession == null) {
			logger.info("初始化流程");
			initRuleEngine(key, list);
		}
		kieSession = ruleMap.get(key);
		if(kieSession!=null){
		    res = "Y";
		    frieRule(key, data);
		}
		return res;
	}

	/** 刷新规则 -- 重载 */
	public synchronized static void refRuleEngine(String key, List<String> path) {
		logger.info("刷新流程 ：name:{} " ,key);
		logger.info("刷新流程 path ： " + path);
		KieSession kieSession = ruleMap.get(key);
		if (kieSession != null) {
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