package com.centryOf22th.demo.lock;

import com.centryOf22th.framework.utils.SleepUtil;
import com.sun.javafx.scene.control.behavior.TableRowBehavior;

/**
 * Created by louis on 16-11-18.
 */
public class SyncInterrupt {
    private int i;

    public synchronized void add(){
        ++i;
        System.out.println(i);
        SleepUtil.second(2);
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().isInterrupted());
    }


    public static void main(String[] args) {


        SyncInterrupt syncInterrupt =new SyncInterrupt();
        syncInterrupt.add();

    }


}
