package com.simple.work.util.drools.mvel;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TestMvel {
	public static void main(String[] args) {
		String respInfo = "{\"encrypt\":{\"data\":{\"date\":\"2019-10-01\",\"CP5111\":\"11760000.00\",\"CP5109\": \"0.00\",\"CP5113\": \"0.00\"}}}";
		JSONObject upayDataJson = JSONObject.parseObject(respInfo).getJSONObject("encrypt").getJSONObject("data");
		System.out.println(mvelProcess("$J.get(\"CP5111\")/($J.get(\"CP5111\")+$J.get(\"CP5109\")+$J.get(\"CP5113\"))","CP5111,CP5109,CP5113",upayDataJson));;
		//System.out.println(mvelProcess("$J.get(\"date\")","date",upayDataJson));;
	}
	private static String mvelProcess(String idxExpression, String idxParams,JSONObject upayDataJson) {
		String[] idxParamsArr = idxParams.split(",");
		Map<String, Object> judge = new HashMap<>();
		for (int i = 0; i < idxParamsArr.length; i++) {
			String data = upayDataJson.getString(idxParamsArr[i])==null?"":upayDataJson.getString(idxParamsArr[i]);
			if(isNumber(data)){
				judge.put(String.valueOf(idxParamsArr[i]), Double.valueOf(data));
			}else{
				judge.put(String.valueOf(idxParamsArr[i]), String.valueOf(data));
			}
		}
		Map<String, Object> params = new HashMap<>();
		params.put("$J", judge);
		Object er = MvelUtil.doMvel(idxExpression, params);
		String result = null;
		if(null == er){
		}else if (er instanceof String){
			System.out.println("ss");
			result = er.toString();
		}else if (er instanceof Double){
			System.out.println("dd");
			result = BigDecimal.valueOf(((Double) er).doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}
		return result;
	}

	private static boolean isNumber(String str) {
		boolean rs = str.matches("-?[0-9]+.?[0-9]*");
		return rs;
	}
}
