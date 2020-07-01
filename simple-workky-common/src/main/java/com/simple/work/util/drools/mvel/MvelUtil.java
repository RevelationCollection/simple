package com.simple.work.util.drools.mvel;

import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MvelUtil {
	private static final Logger logger = LoggerFactory.getLogger(MvelUtil.class);

	public static Object doMvel(String expression, Map<String, Object> params) {
		logger.debug("expression:{} , params:{}", expression, params);
		try {
			Serializable er = MVEL.compileExpression(expression);
			return MVEL.executeExpression(er, params);
		} catch (Exception e) {
			logger.error("doMvel error,evl:{},error:",expression, e);
		}
		logger.warn("doMvel return null");
		return null;
	}

	public static void main(String[] args) {
		String mvelString = "$J.get(\"a\")+$J.get(\"b\")+$J.get(\"c\")+$J.get(\"d\")+$J.get(\"e\")";
		Map<String, Object> judge = new HashMap<String, Object>();
		judge.put("a", 1);
		judge.put("b", 1);
		judge.put("c", 1);
		judge.put("d", 1);
		judge.put("e", 1);
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("$J", judge);
		System.out.println(doMvel(mvelString, parm));
	}
}
