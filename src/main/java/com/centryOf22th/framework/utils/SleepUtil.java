package com.centryOf22th.framework.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by louis on 16-10-18.
 */
public class SleepUtil {
    public static final void second(long second){
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
