package com.pulselive.league.util;

import com.pulselive.league.model.LeagueTableEntry;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LeagueTableUtils {

    /**
     * Creates a scoreType given a goal difference. ScoreType can be WON< DRAWN or LOST depending on goaldifference is
     * positive, 0, or negative
     * @param goalDifference
     * @return
     * @author mauricio del rei
     */
    public static ScoreType createScoreType(final int goalDifference) {
        return goalDifference > 0 ? ScoreType.WON :
                (goalDifference == 0 ? ScoreType.DRAWN : ScoreType.LOST);
    }


    /**
     * Returns 1 if 2 scoreTypes are equals and 0 in other case
     * @param scoreType
     * @param expectedScoreType
     * @return
     */
    public static int checkScoreType(final ScoreType scoreType, final ScoreType expectedScoreType) {
        return scoreType.compareTo(expectedScoreType) == 0 ? 1 : 0;
    }


    /**
     * Given a list of table entries, this method order the list by total points (descending)
     * Then by goal difference (descending)
     * Then by goals scored (descending)
     * Then by team name (in alphabetical order)
     * @param leagueTableEntries
     * @return
     */
    public static List<LeagueTableEntry> orderTableEntries(List<LeagueTableEntry> leagueTableEntries) {
       Collections.sort(leagueTableEntries, new Comparator<LeagueTableEntry>() {
            @Override
            public int compare(final LeagueTableEntry entry1, final LeagueTableEntry entry2) {
                int order;
                order = entry2.getPoints() - entry1.getPoints();
                if (order == 0)
                    order = entry2.getGoalDifference() - entry1.getGoalDifference();
                if (order == 0)
                    order = entry2.getGoalsFor() - entry1.getGoalsFor();
                if (order == 0)
                    order = entry1.getTeamName().compareTo(entry2.getTeamName());

                return order;
            }
        });

        return leagueTableEntries;
    }

    /**
     * Method to print the table entries
     * @param leagueTableEntries
     */
    public static void printTableEntries(List<LeagueTableEntry> leagueTableEntries) {

        System.out.println("\n--------------------------------------------------------------------------------------------------------");
        System.out.println("\tLeague Table");
        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.format("%10s%10s%10s%10s%10s%10s%10s%10s%10s%10s%n", "Position", "Club", "Played", "Won", "Drawn", "Lost", "GF", "GA", "GD", "Points" );

        AtomicInteger runCount = new AtomicInteger(1);
        leagueTableEntries.forEach(leagueTableEntry -> {
                    System.out.format("%10d%10s%10d%10d%10d%10d%10d%10d%10d%10d%n", runCount.getAndIncrement(),
                            leagueTableEntry.getTeamName(), leagueTableEntry.getPlayed(), leagueTableEntry.getWon(),
                            leagueTableEntry.getDrawn(), leagueTableEntry.getLost(), leagueTableEntry.getGoalsFor(),
                            leagueTableEntry.getGoalsAgainst(), leagueTableEntry.getGoalDifference(),
                            leagueTableEntry.getPoints());
        });
        System.out.println("--------------------------------------------------------------------------------------------------------");
    }
}
