package com.mj_tech.marlon.bass.utility;

/**
 * Created by Marlon on 6/12/2015.
 */
public abstract class Utility {

    public static String GetTime(long duration){


        int seconds = (int) (duration / 1000) % 60 ;
        int minutes = (int) ((duration / (1000*60)) % 60);
        int hours   = (int) ((duration / (1000*60*60)) % 24);

        String time= String.format("%02d:%02d",minutes,seconds);

        return time;
    }
}
