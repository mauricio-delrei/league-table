package com.pulselive.league.model;

import java.io.Serializable;

/**
 * Match represents each match with teams scores
 *
 * @author pulselive
 *
 */

public class Match implements Serializable {

    private static final long serialVersionUID = 5049164449561222366L;

    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;


    public Match(){}

    public Match( final String homeTeam, final String awayTeam, final int homeScore, final int awayScore )
    {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public String getHomeTeam()
    {
        return homeTeam;
    }

    public String getAwayTeam()
    {
        return awayTeam;
    }

    public int getHomeScore()
    {
        return homeScore;
    }

    public int getAwayScore()
    {
        return awayScore;
    }

    public boolean isValidMatch() {
        return homeScore >= 0 && awayScore >= 0;
    }
}
