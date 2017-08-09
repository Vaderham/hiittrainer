package com.example.reaganharper.hiittrainer02;

import android.os.CountDownTimer;


public class PausableTimer{

    private CountDownTimer timer;
    private OnTickListener tickListener;
    private boolean isPaused;
    private long mCurrentTime;

    public PausableTimer(long millisUntilFinished, long interval, OnTickListener listener){

        tickListener = listener;

        this.timer = new CountDownTimer(millisUntilFinished, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                tickListener.OnTick(millisUntilFinished);
                mCurrentTime = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                tickListener.OnTick(0);
                tickListener.OnFinish();
                isPaused = false;
            }
        };
    }

    public void start(){
        this.timer.start();
        isPaused = false;
    }

    public void stop(){
        this.timer.cancel();
        tickListener.OnTick(0);
    }

    public void pause(){
        this.timer.cancel();
        tickListener.OnTick(mCurrentTime);
        isPaused = true;
    }

    public void resume(final long currentTime){
        this.timer = new CountDownTimer(currentTime, 1000) {
            @Override
            public void onTick(long l) {
                tickListener.OnTick(l);
                mCurrentTime = l;
            }
            @Override
            public void onFinish() {
                tickListener.OnTick(0);
            }
        };
        this.timer.start();
        isPaused = false;
    }

    public long getCurrentTime(){
        return mCurrentTime;
    }

    public boolean getIsPaused(){
        return isPaused;
    }

}

