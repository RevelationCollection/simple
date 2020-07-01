package com.simple.work.util.drools.service;

import com.alibaba.fastjson.JSONObject;
import com.simple.work.util.common.FileUtils;
import com.simple.work.util.drools.bean.RuleObj;
import com.simple.work.util.drools.core.RunRule;
import com.simple.work.util.drools.service.entity.CardRule;
import com.simple.work.util.drools.service.entity.CardRuleFuncBase;
import com.simple.work.util.drools.service.entity.CardRuleFuncRef;
import com.simple.work.util.drools.service.entity.CardRuleModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;

/**
 * 规则模型
 * Created by yong.wang on 19-10-31.
 */
public class RuleModeService{

    @Value("${rule.local.path:-1}")
    private String rulePath;

    @Value("${lfs.rule.path:-1}")
    private String lfsRulePath;

    
    public JSONObject createRuleFile(String modeCode) {
        JSONObject response = new JSONObject();
        response.put("code","01");
        if(StringUtils.isEmpty(modeCode)){
            response.put("msg","参数为空");
            return response;
        }
//        List<CardRuleModel> ruleModes = cardRuleModelMapper.findByModelCode(modeCode);
        List<CardRuleModel> ruleModes = null;
        if(CollectionUtils.isEmpty(ruleModes)){
            response.put("msg","模型编号不正确:"+modeCode);
            return response;
        }
        CardRuleModel ruleModel = null;
        for (CardRuleModel mode : ruleModes) {
            if(mode==null){
                continue;
            }
            ruleModel=mode;
            break;
        }
        if(ruleModel==null){
            response.put("msg","不存在对应的模型实体");
            return response;
        }
        BufferedOutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;
        try {
            // 拼接模型对应的规则文件
            String ruleFileString = buildRuleFile(ruleModel);
            if(StringUtils.isEmpty(ruleFileString)){
                response.put("msg","规则文件组装失败");
                return response;
            }
            // 模型版本+1
            String version = ruleModel.getModelVersion();
            Integer modelVersion = Integer.parseInt(version) + 1;
            String ruleName = modeCode+"_"+modelVersion+".drl";
            String localRuleFilePath = rulePath + File.separator+ruleName;
            System.out.println("local file path:{}"+localRuleFilePath);
            // 本地方式创建文件
            FileUtils.createFile(localRuleFilePath, ruleFileString);
            File file = new File(localRuleFilePath);
            if(!file.exists()){
                response.put("msg","创建文件失败");
                return response;
            }
            RuleObj data = new RuleObj();
            Map<String,Object> map = new HashMap<>();
            map.put("transDateDay","2");
            data.setRuleDetail(map);
            System.out.println("开始执行规则文件");
            //预执行规则文件
            RunRule.exeRuleEngine(modeCode,String.valueOf(modelVersion),localRuleFilePath,data);
            System.out.println("执行规则文件成功:"+data.getRules());
            //规则文件执行成功，上传到lfs文件中
            String lfsRuleFilePath = lfsRulePath+ruleName;
            Object lfsFileUtils = null;
            outputStream = new BufferedOutputStream(new FileOutputStream(localRuleFilePath));
//            if (!lfsFileUtils.uploadDroolsFile2LFS(lfsRuleFilePath, ruleFileString) || !lfsFileUtils.downloadDroolsFileFromLFS(lfsRuleFilePath, outputStream)) {
//                throw new RuntimeException("createDrlFile,从LFS上传或下载文件失败");
//            }
            ruleModel.setModelVersion(String.valueOf(modelVersion));
            ruleModel.setUpdateTime(new Date());
//            cardRuleModelMapper.updateById(ruleModel);
            response.put("code","00");
            response.put("msg","成功");
        }catch (Exception e) {
            System.out.println("创建规则文件发生异常,"+ e);
            throw new RuntimeException("创建规则文件发生异常:" , e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.out.println("createDrlFile,out.close() error"+ e);
                }
            }if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("createDrlFile,out.close() error"+ e);
                }
            }

        }
        return response;
    }

    /**
     * 创建规则文件
     * @param ruleMode 规则模型
     * @return
     */
    private String buildRuleFile(CardRuleModel ruleMode) {
        StringBuffer ruleFileSb = new StringBuffer();
        // 拼接规则文件头部引用
        ruleFileSb.append(DrlFileConstants.PACKAGE).append(DrlFileConstants.NEWLINE).append(DrlFileConstants.IMPORT_JAVA_UTIL).append(DrlFileConstants.NEWLINE)
                .append(DrlFileConstants.IMPORT_JAVA_MATH).append(DrlFileConstants.NEWLINE).append(DrlFileConstants.IMPORT_STRING_UTIL).append(DrlFileConstants.NEWLINE)
                .append(DrlFileConstants.IMPORT_CUSTOM_UTIL).append(DrlFileConstants.NEWLINE).append(DrlFileConstants.IMPORT_CUSTOM_DROOLS)
                .append(DrlFileConstants.NEWLINE).append(DrlFileConstants.IMPORT_ALIBABA_FASTJSON).append(DrlFileConstants.NEWLINE).append(DrlFileConstants.NEWLINE);
        // 拼接规则文件函数定义部分
        // 优化，只查询已经关联的基础函数
        String modelCode = ruleMode.getModelCode();
//        List<CardRuleFuncBase> funcBaseList = cardRuleFuncBaseMapper.findListAllByModelCode(modelCode);
        List<CardRuleFuncBase> funcBaseList =null;
        if(CollectionUtils.isEmpty(funcBaseList)){
            System.out.println("modelCode对应的函数为空。无法组装规则文件");
            return null;
        }
        Map<String,CardRuleFuncBase> funcBaseMap = new HashMap<>(funcBaseList.size());
        for (CardRuleFuncBase ruleFuncBase : funcBaseList) {
            String code = ruleFuncBase.getFuncBaseCode();
            if (funcBaseMap.get(code) != null) {
                continue;
            }
            ruleFileSb.append(ruleFuncBase.getFuncInfo()).append(DrlFileConstants.NEWLINE);
            funcBaseMap.put(code,ruleFuncBase);
        }
        // 根据模型ID,得到模型的对应得规则Code,根据排序号进行排序
//        List<CardRule> cardRuleList = cardRuleMapper.findRuleByModelCode(modelCode);
        List<CardRule> cardRuleList = null;
        if(CollectionUtils.isEmpty(cardRuleList)){
            System.out.println("modelCode对应的规则为空。无法组装规则文件");
            return null;
        }
        //保存规则函数的判断及处理措施
        for (CardRule cardRule : cardRuleList) {
            saveByRule(cardRule,ruleFileSb,funcBaseMap);
        }
        return ruleFileSb.toString();
    }

    private void saveByRule(CardRule cardRule,StringBuffer ruleFileSb, Map<String,CardRuleFuncBase> funcBaseMap){
        if(cardRule==null){
            return ;
        }
        String ruleCode = cardRule.getRuleCode();
        String modelCode = cardRule.getModelCode();
        // 拼接规则文件对应得参数部分
//        List<CardRuleFuncRef> cardRuleFuncRefList = cardRuleFuncRefMapper.findListAllByRuleCode(ruleCode,modelCode);
        List<CardRuleFuncRef> cardRuleFuncRefList =null;
        if (CollectionUtils.isEmpty(cardRuleFuncRefList)) {
            System.out.println("buildRuleFile model.routeId={},ruleCode={},routeRuleFuncRefs is null"+ cardRule.getRuleCode()+ ruleCode);
            return;
        }
        ruleFileSb.append(DrlFileConstants.RULE).append(ruleCode).append(DrlFileConstants.NEWLINE);
        ruleFileSb.append(DrlFileConstants.WHEN).append(DrlFileConstants.NEWLINE);
        ruleFileSb.append(DrlFileConstants.BASE_RULE).append(DrlFileConstants.NEWLINE);


        String rulePress = "";
        int size = cardRuleFuncRefList.size();
        for (int i = 0; i < size; i++) {
            CardRuleFuncRef cardRuleFuncRef = cardRuleFuncRefList.get(i);
            String funcBaseCode = cardRuleFuncRef.getFuncBaseCode();
            CardRuleFuncBase cardRuleFuncBase = funcBaseMap.get(funcBaseCode);
            String enExpress = cardRuleFuncBase.getEnExpress(); // 英文表达式
            String expressDefParams = cardRuleFuncBase.getExpressFormParams();// 参数类型
            String expressRealParams = cardRuleFuncRef.getExpressRealParams(); // 函数参数
            JSONObject expressRealJson = JSONObject.parseObject(expressRealParams);
            JSONObject expressDefJson = JSONObject.parseObject(expressDefParams);
            // 参数替换
            Set<String> keys = expressRealJson.keySet();
            for (String key : keys) {
                if ("String".equals(expressDefJson.get(key))) {
                    enExpress = enExpress.replaceAll(key, "\"" + expressRealJson.get(key) + "\"");
                } else {
                    enExpress = enExpress.replaceAll(key, "" + expressRealJson.get(key));
                }
            }
            rulePress += enExpress;
            if (i + 1 != size) {
                rulePress += " && ";
            }
        }
        // 拼接规则文件规则判定部分
        ruleFileSb.append(DrlFileConstants.EVAL).append("(").append(rulePress).append(")").append(DrlFileConstants.NEWLINE);
        ruleFileSb.append(DrlFileConstants.THEN).append(DrlFileConstants.NEWLINE);
        ruleFileSb.append(DrlFileConstants.BASE_THEN.replace(DrlFileConstants.BASE_THEN_RULE_CODE, ruleCode)).append(DrlFileConstants.NEWLINE);
        ruleFileSb.append(DrlFileConstants.END).append(DrlFileConstants.NEWLINE);

    }
}