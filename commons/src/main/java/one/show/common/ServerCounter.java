package one.show.common;

/*
 * Copyright (c) 2012 Sohu TV
 * All rights reserved.
 */

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于sid的请求计数器（用来限制一段时间内打到某个server上的请求量，以此控制瞬间请求带来的调度不平衡）
 * 
 * @author jiqingzhao (jiqingzhao@sohu-inc.com)
 * @date 2012-9-12
 *
 */
public class ServerCounter {

    /**
     * 单位时间
     */
    public static int INTERVAL = 3000;
    /**
     * 最大请求量
     */
    public static int MAX_COUNT = 15;
    
    private int sid;
    private long lastTime;
    private AtomicInteger count;
    
    public ServerCounter(int sid) {
        this.sid = sid;
        count = new AtomicInteger(0);
        lastTime = System.currentTimeMillis();
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public AtomicInteger getCount() {
        long time = System.currentTimeMillis();
        if (time - lastTime > INTERVAL) {
            count.set(0);
            lastTime = time;
        }
        if (count.get() < MAX_COUNT) {
            return count;
        } else {
            return null;
        }
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }
    
    public static void setCounterInterval(int interval) {
        INTERVAL = interval;
    }
    public static void setCounterMaxCount(int maxCount){
        MAX_COUNT = maxCount;
    }
}

