package com.simple.work.util.drools.core;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/** 
 * @author wy
 */
public class DroolUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(DroolUtil.class);
    
    public static KieContainer initKieContainer(List<String> path){
        if(path==null || path.isEmpty()){
            throw new RuntimeException("rule file path is null");
        }
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        for (String string : path) {
            File file = new File(string);
            if(!file.exists()){
                throw new RuntimeException("file path "+string+" is not exists");
            }
            kieFileSystem.write(ResourceFactory.newFileResource(file));
        }
        final KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(new KieModule() {
            @Override
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            logger.error(" error : " + results.getMessages());
            throw new IllegalStateException("build drools rule file errors ");
        }
        kieBuilder.buildAll();
        KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
        return kieContainer;
    }
    
    public static KieSession initKieSession(List<String> path){
        if(path==null || path.isEmpty()){
            throw new RuntimeException("rule file path is null");
        }
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        for (String string : path) {
            File file = new File(string);
            if(!file.exists()){
                throw new RuntimeException("file path "+string+" is not exists");
            }
            kieFileSystem.write(ResourceFactory.newFileResource(file));
        }
        final KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(new KieModule() {
            @Override
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            logger.error(" error : " + results.getMessages());
            throw new IllegalStateException("build drools rule file errors ");
        }
        kieBuilder.buildAll();
        KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
        return kieContainer.newKieSession();
    }
    
    /**
     * 执行规则文件
     * @param obj
     */
    public static void executeRuleEngine(KieContainer kieContainer, Object obj) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(obj);
        kieSession.fireAllRules();
        kieSession.dispose();
    }
    
    /**
     * 执行规则文件
     * @param obj
     */
    public static void executeRuleEngine(KieSession kieSession, Object obj) {
        kieSession.insert(obj);
        kieSession.fireAllRules();
    }
}