package com.pulselive.league.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * League table entry contains fields for league table
 *
 * @author pulselive
 *
 */

public class LeagueTableEntry implements Serializable {


    private static final long serialVersionUID = -1958875412087509969L;
    private String teamName;
    private int played;
    private int won;
    private int drawn;
    private int lost;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;
    private int points;


    public LeagueTableEntry(){}

    public LeagueTableEntry( final String teamName, final int played, final int won, final int drawn, final int lost,
        final int goalsFor, final int goalsAgainst, final int goalDifference, final int points )
    {
        this.teamName = teamName;
        this.played = played;
        this.won = won;
        this.drawn = drawn;
        this.lost = lost;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.goalDifference = goalDifference;
        this.points = points;
    }

    public String getTeamName()
    {
        return teamName;
    }

    public int getPlayed()
    {
        return played;
    }

    public int getWon()
    {
        return won;
    }

    public int getDrawn()
    {
        return drawn;
    }

    public int getLost()
    {
        return lost;
    }

    public int getGoalsFor()
    {
        return goalsFor;
    }

    public int getGoalsAgainst()
    {
        return goalsAgainst;
    }

    public int getGoalDifference()
    {
        return goalDifference;
    }

    public int getPoints()
    {
        return points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeagueTableEntry that = (LeagueTableEntry) o;
        return Objects.equals(teamName, that.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName);
    }
}
