package com.example.reaganharper.hiittrainer02;

import android.os.CountDownTimer;


public class PausableTimer{

    private CountDownTimer timer;
    private OnTickListener tickListener;
    private long currentTime;

    public PausableTimer(long millisUntilFinished, long interval, OnTickListener listener){

        tickListener = listener;

        this.timer = new CountDownTimer(millisUntilFinished, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                tickListener.OnTick(millisUntilFinished);
                currentTime = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                tickListener.OnTick(0);
            }
        };
    }

    public void start(){
        this.timer.start();
    }

    public void stop(){
        this.timer.cancel();
        tickListener.OnTick(0);
    }

    public void pause(){
        this.timer.cancel();
        tickListener.OnTick(currentTime);
    }

    public void resume(){

    }

    public long getCurrentTime(){
        return currentTime;
    }

}

