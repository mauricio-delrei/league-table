package com.pulselive.league.util;
/**
 * @author mauricio del rei
 * */
public enum ScoreType {
    WON(3),
    DRAWN(1),
    LOST(0);

    private int points;

    ScoreType(final int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
