package com.pulselive.league.service;

import com.google.common.collect.Lists;
import com.pulselive.league.model.LeagueTableEntry;
import com.pulselive.league.model.Match;
import com.pulselive.league.util.LeagueTableUtils;
import com.pulselive.league.util.ScoreType;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * League table for football.
 * Each team plays a number of matches and the results of each match build the table.
 * {@code LeagueTable} can take a list of completed matches and produce a sorted
 * {@code List} of {@code LeagueTableEntry} objects.
 *
 * @author mauricio del rei
 *
 */
public class LeagueTable {
    private List<Match> matches;
    private Set<LeagueTableEntry> tableEntries;

    public LeagueTable( final List<Match> matches ) {
        this.matches = Objects.isNull(matches) ? new ArrayList<>() : matches;
        tableEntries = new HashSet<>();
    }

    /**
     * Get the ordered list of league table entries for this league table.
     *
     * @return
     */
    public List<LeagueTableEntry> getTableEntries() {
        matches.forEach(match -> {
            if(match.isValidMatch()) {
                processTableEntryInfo(match);
            }
        });

        return LeagueTableUtils.orderTableEntries(Lists.newArrayList(tableEntries));
    }

    private void processTableEntryInfo(final Match match) {
        processTeamTableEntry(match.getHomeTeam(), match.getHomeScore(), match.getAwayScore());
        processTeamTableEntry(match.getAwayTeam(), match.getAwayScore(), match.getHomeScore());
    }

    private void processTeamTableEntry(final String teamName, final int teamScore, final int rivalScore) {
        final int goalDifference = teamScore - rivalScore;
        final ScoreType scoreType = LeagueTableUtils.createScoreType(goalDifference);
        final Optional<LeagueTableEntry> currentEntry;
        final LeagueTableEntry updatedEntry;

        currentEntry = tableEntries.stream()
                .filter(entry -> StringUtils.equalsIgnoreCase(entry.getTeamName(), teamName))
                .findAny();

        if (!currentEntry.isPresent()) {
            updatedEntry = new LeagueTableEntry(teamName, 1,
                    LeagueTableUtils.checkScoreType(scoreType, ScoreType.WON),
                    LeagueTableUtils.checkScoreType(scoreType, ScoreType.DRAWN),
                    LeagueTableUtils.checkScoreType(scoreType, ScoreType.LOST),
                    teamScore,
                    rivalScore,
                    goalDifference,
                    scoreType.getPoints());
        } else {
            tableEntries.remove(currentEntry.get());

            updatedEntry = new LeagueTableEntry(teamName, currentEntry.get().getPlayed() + 1,
                    currentEntry.get().getWon() + LeagueTableUtils.checkScoreType(scoreType, ScoreType.WON),
                    currentEntry.get().getDrawn() + LeagueTableUtils.checkScoreType(scoreType, ScoreType.DRAWN),
                    currentEntry.get().getLost() + LeagueTableUtils.checkScoreType(scoreType, ScoreType.LOST),
                    currentEntry.get().getGoalsFor() + teamScore,
                    currentEntry.get().getGoalsAgainst() + rivalScore,
                    currentEntry.get().getGoalDifference() + goalDifference,
                    currentEntry.get().getPoints() + scoreType.getPoints());
        }

        tableEntries.add(updatedEntry);
    }
}
