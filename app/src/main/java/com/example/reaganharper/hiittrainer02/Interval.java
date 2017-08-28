package com.example.reaganharper.hiittrainer02;

import java.io.Serializable;

public class Interval implements Serializable {

    private String name;
    private long intervalTime;

    public Interval(String name, long intervalTime) {
        this.name = name;
        this.intervalTime = intervalTime;
    }

    public String getName() {
        return name;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

}