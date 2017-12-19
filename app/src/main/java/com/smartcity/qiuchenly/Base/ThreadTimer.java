package com.smartcity.qiuchenly.Base;

import android.os.Handler;

/**
 * Author: qiuchenly
 * Date   : 24/11/2017
 * Usage :
 * Lasted:2017 11 24
 * ProjectName:SmartRoadSystem
 * Create: 2017 11 24 , on 13:34
 */

public abstract class ThreadTimer implements Runnable {

  public abstract void TimeCallBack(long totalTime);

  private Handler mHandler;

  long loopTime;
  long totalTime;
  boolean onUIThread;

  Thread mThread;

  public ThreadTimer(Handler mainLooper, long delayTime, long totalTime, boolean nowStart, boolean
          onMainUIThread) {
    mThread = new Thread(this);
    this.totalTime = totalTime;
    onUIThread = onMainUIThread;
    this.loopTime = delayTime;
    mHandler = mainLooper;

    if (nowStart) {
      mThread.start();
    }
  }

  public void Start() {
    mThread.start();
  }

  public void Stop() {
    //totalTime = 0;
    exitThread = false;
    //TimeCallBack(totalTime);
  }

  boolean exitThread = true;

  public void run() {
    while (exitThread) {

      synchronized (this) {
        try {
          wait(loopTime);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      if (onUIThread) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            TimeCallBack(totalTime);
          }
        });
      }
      totalTime -= loopTime;
    }
  }
}
