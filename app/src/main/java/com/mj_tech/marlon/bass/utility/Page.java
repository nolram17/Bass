package com.mj_tech.marlon.bass.utility;

/**
 * Created by Marlon on 6/12/2015.
 */
public enum Page {

    Selection(0),
    SubSelection(1),
    Player(2);

    private int value;

    private Page(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
