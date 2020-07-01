package com.simple.work.util.drools.service;

/**
 * @author: wy
 * 规则drl文件修饰符与固定格式常量
 */
public class DrlFileConstants {

	public static final String UNKNOWN = "N/A";
	public static final String NEWLINE = "\r\n";

	/** 包路径 */
    public static final String PACKAGE = "package com.pay.union.portrait.drools;";

	/** 引入java.util */
	public static final String IMPORT_JAVA_UTIL = "import java.util.*;";

	/** 引入String.util */
	public static final String IMPORT_STRING_UTIL = "import org.springframework.util.StringUtils;";

	/** 引入java.math */
	public static final String IMPORT_JAVA_MATH = "import java.math.*;";

	/** 引入alibaba.fastjson */
	public static final String IMPORT_ALIBABA_FASTJSON = "import com.alibaba.fastjson.*;";

	/** 引入自定义bean */
	public static final String IMPORT_CUSTOM_DROOLS = "import com.pay.union.portrait.common.drools.bean.*;";

	/** 引入自定义util */
	public static final String IMPORT_CUSTOM_UTIL = "import com.pay.union.portrait.common.util.*;";

	/** function */
	public static final String FUNCTION = "function boolean ";

	/** 方法参数 */
	public static final String FUNCTION_PARAMETER = "RuleObj ruleObj, double parameter";

	/** 方法元素 */
	public static final String FUNCTION_ELEMENT = "replace_element";

	/** rule */
	public static final String RULE = "rule ";

	/** when */
	public static final String WHEN = "when ";

	/** then */
	public static final String THEN = "then ";

	/** end */
	public static final String END = "end ";

	/** eval */
	public static final String EVAL = "eval";

	/** $c */
	public static final String RULEOBJ = "$c, ";

	/** return */
	public static final String RETURN_TRUE = "return true;";

	public static final String RETURN_FALSE = "return false;";

	/** 条件基础 */
	public static final String BASE_RULE = "$c:RuleObj();";

	/** 返回值基础 */
	public static final String BASE_THEN_RULE_CODE = "rule_code";

	public static final String BASE_THEN = "$c.setRuleRecords(\"" + BASE_THEN_RULE_CODE + "\");";

	/** 方法基础 */
	public static final String BASE_FUNCTION1 = "Map<String, Object> ruleDetail = ruleObj.getRuleDetail();";

	public static final String BASE_FUNCTION2 = "String element_str = String.valueOf(ruleDetail.get(\"" + FUNCTION_ELEMENT + "\"));";

	public static final String BASE_FUNCTION3 = "!StringUtils.isStrEmpty(element_str)";

	public static final String BASE_FUNCTION4 = "Double element_num = Double.valueOf(element_str);";

}