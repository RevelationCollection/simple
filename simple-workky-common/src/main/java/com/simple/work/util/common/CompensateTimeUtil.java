package com.simple.work.util.common;

import java.util.Date;

/**
 * @author wy
 * @version 创建时间：2019年12月22日 上午6:14:08
 */
public class CompensateTimeUtil {
    private CompensateTimeUtil(){}

    /**  间隔时间5s*/
    private static final long INTERVAL_TIME = 5000L;

    public static long getNextTime(long optimistic){
        if(optimistic<0){
            optimistic = 0;
        }
        optimistic = optimistic+1;
        return (optimistic << optimistic) *INTERVAL_TIME;
    }

    public static Date getNextTime(Date startTime,long optimistic){
        if(startTime==null){
            startTime = new Date();
        }
        long nextTime = getNextTime(optimistic);
        return new Date(startTime.getTime()+nextTime);
    }

//    public static void main(String[] args) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//        long time = date.getTime();
//        System.out.println("now:"+format.format(date));
//        for (int i = 0; i <10; i++) {
//            long nextTime = getNextTime(i);
//            System.out.println("i:"+i+",nextTime:"+nextTime+",time:"+format.format(new Date(time+nextTime)));
//        }
//    }
}