package com.simple.work.util.drools.service;

import com.simple.work.util.drools.bean.RuleObj;
import com.simple.work.util.drools.core.RunRule;
import com.simple.work.util.drools.service.entity.CardRuleModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.List;

/**
 * 规则文件执行业务
 * Created by yong.wang on 19-11-4.
 */
public class DroolsRuleService{

    @Value("${rule.local.path:-1}")
    private String localRulePath;

    @Value("${lfs.rule.path:-1}")
    private String lfsRulePath;


    public void executeRule(RuleObj ruleObj, String modelCode) {
        if(ruleObj == null || StringUtils.isEmpty(modelCode)){
            throw  new RuntimeException("param is null");
        }
        //查询缓存或查库
        CardRuleModel ruleModel = getCardRuleModel(modelCode);
        if(ruleModel==null){
            throw  new RuntimeException("mode is not exists");
        }
        String modelVersion = ruleModel.getModelVersion();
        String ruleFileName = modelCode+"_"+modelVersion+".drl";
        //是否刷新规则文件
        boolean isFlushFile = !ruleModel.getCurrentVersion().equals(modelVersion);
        if(isFlushFile){
            refreshRuleFile(ruleModel,ruleFileName);
        }
        String loaclFile = localRulePath + File.separator+ruleFileName;
        File file = new File(loaclFile);
        //不存在文件则去下载
        if(!file.exists()){
            refreshRuleFile(ruleModel,ruleFileName);
        }
        //执行规则文件
        RunRule.exeRuleEngine(modelCode,modelVersion,loaclFile,ruleObj);
    }

    private CardRuleModel getCardRuleModel(String modelCode){
//        List<CardRuleModel> list = cardRuleModelMapper.findByModelCode(modelCode);
        List<CardRuleModel> list = null;
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        for (CardRuleModel cardRuleModel : list) {
            if (cardRuleModel != null) {
                return cardRuleModel;
            }
        }
        return null;
    }

    /**
     * 刷新规则文件
     * @param ruleMode 规则模型
     * @param ruleFileName 最新当前规则文件名称
     */
    private void refreshRuleFile(CardRuleModel ruleMode,String ruleFileName){
        OutputStream outputStream = null;
        try{
            synchronized (DroolsRuleService.class){
                CardRuleModel newRuleMode = getCardRuleModel(ruleMode.getModelCode());
                String lfsPath = lfsRulePath + ruleFileName;
                String localPath = localRulePath + File.separator+ ruleFileName;
                outputStream = new BufferedOutputStream(new FileOutputStream(localPath));
//                if(!lfsFileUtils.downloadDroolsFileFromLFS(lfsPath, outputStream)){
//                    throw  new RuntimeException("down lfs file error");
//                }
//                if(!newRuleMode.getCurrentVersion().equals(newRuleMode.getModelVersion())){
//                    newRuleMode.setCurrentVersion(newRuleMode.getModelVersion());
//                    cardRuleModelMapper.updateById(newRuleMode);
//                    return ;
//                }

            }
        }catch (Exception e){
            System.out.println("error:"+e);
            throw new RuntimeException("fuls lfs error",e);
        }finally{
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.out.println("error"+e);
                }
            }
        }
    }
}