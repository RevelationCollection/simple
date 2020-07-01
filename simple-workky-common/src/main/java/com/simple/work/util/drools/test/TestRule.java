package com.simple.work.util.drools.test;

import com.simple.work.util.drools.bean.RuleObj;
import com.simple.work.util.drools.core.RunRule;
import org.drools.core.io.impl.ClassPathResource;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wy
 */
public class TestRule {

    private static volatile Set<Integer> set = new HashSet<>(7000);

    private static ArrayBlockingQueue<Runnable> arrayBlockingQueue =  new ArrayBlockingQueue<Runnable>(5000);

    private static ThreadPoolExecutor POOL = new ThreadPoolExecutor(50, 50, 30, TimeUnit.SECONDS,
            arrayBlockingQueue,
            new NamedThreadFactory("my_threadPool"),new AbortPolicy());

    public static void main(String[] args) throws Throwable {
        Map<String, String> param = new HashMap<>();
        param.put("ruleName", "M0008");
        param.put("version", "01");
//        param.put("path", "H:"+File.separator+"myTest.drl");
        ClassPathResource pathResource = new ClassPathResource("myTest.drl");
        param.put("path", pathResource.getURL().getPath());
        long start = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
           final int j = i;
           Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    long begin = System.currentTimeMillis();
                    RuleObj data = new RuleObj();
                    Map<String,Object> map = new HashMap<>();
                    data.setRuleDetail(map);
                    map.put("first", j);
                    RunRule.exeRuleEngine(param, data);
//                    RunRuleSession.exeRuleEngine(param, data);
                    if(set.contains(j)){
                        throw new RuntimeException("11111 :"+j);
                    }
                    set.add(j);
                    System.out.println(j+"--- >  time:"+(System.currentTimeMillis()-start)+
                            " handeTime:"+ (System.currentTimeMillis()-begin )+"  _:" +
                            " size:"+arrayBlockingQueue.size()+"  _:" +
                            data );
                }
           };
           POOL.execute(runnable);
        }
        System.out.println(" time :" + (System.currentTimeMillis()-start));
        while(true){
            if(arrayBlockingQueue.size()==0 && POOL.getActiveCount()==0){
                POOL.shutdown();
                return ;
            }
            Thread.sleep(2000L);
        }
    }


    public static void test(){
        String fileName = "M008t0_1.drl";
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(fileName,"UTF-8"));
        final KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(new KieModule() {
            @Override
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
        KieSession kieSession = kieContainer.newKieSession();
        RuleObj rule = new RuleObj();
        Map<String,Object> map = new HashMap<>();
        rule.setRuleDetail(map);
        kieSession.insert(rule);
        kieSession.fireAllRules();
        System.out.println("1 --- >" + rule);

        rule = new RuleObj();
        map = new HashMap<>();
        map.put("first", 22);
        rule.setRuleDetail(map);
        Object obj = rule;
        kieSession.insert(obj);
        kieSession.fireAllRules();
        System.out.println("2 --- >" + rule);
    }

}


/**
 * InternalThreadFactory.
 *
 * @author qian.lei
 */

 class NamedThreadFactory implements ThreadFactory {
    private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);

    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    private final String mPrefix;

    private final boolean mDaemo;

    private final ThreadGroup mGroup;

    public NamedThreadFactory() {
        this("pool-" + POOL_SEQ.getAndIncrement(), false);
    }

    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public NamedThreadFactory(String prefix, boolean daemo) {
        mPrefix = prefix + "-thread-";
        mDaemo = daemo;
        SecurityManager s = System.getSecurityManager();
        mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    public Thread newThread(Runnable runnable) {
        String name = mPrefix + mThreadNum.getAndIncrement();
        Thread ret = new Thread(mGroup, runnable, name, 0);
        ret.setDaemon(mDaemo);
        return ret;
    }

    public ThreadGroup getThreadGroup() {
        return mGroup;
    }
}