package com.dariosimonetti.dissertation.agent.test;

import com.dariosimonetti.dissertation.agent.Measured;

public class Logarithmic {
    @Measured
    public void quick(int n) {
        for(long i = 0; i < Math.log(n)*1562l; i++) {

        }
    }

    @Measured
    public void average(int n) {
        for(long i = 0; i < Math.log(n)*6250l; i++) {

        }
    }

    @Measured
    public void slow(int n) {
        for(long i = 0; i < Math.log(n)*25000l; i++) {

        }
    }
}
