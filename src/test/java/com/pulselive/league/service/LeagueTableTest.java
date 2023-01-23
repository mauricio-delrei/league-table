package com.pulselive.league.service;

import com.google.common.collect.ImmutableList;
import com.pulselive.league.model.LeagueTableEntry;
import com.pulselive.league.model.Match;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LeagueTableTest {

    private List<Match> matches;


    private static Stream<Arguments> invalidResults() {
        return Stream.of(
                Arguments.of(ImmutableList.of(new Match("Team 1", "Team 2", 2, -1))), // null strings should be considered blank
                Arguments.of(ImmutableList.of(new Match("Team 1", "Team 2", -2, -1))),
                Arguments.of(ImmutableList.of(new Match("Team 1", "Team 2", -2, 1)))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidResults")
    public void invalidResults(List<Match> matches){
        final LeagueTable table = new LeagueTable(matches);
        final List<LeagueTableEntry> tableEntries = table.getTableEntries();

        assertThat(tableEntries).isEmpty();
    }

    @Test
    public void testCorrectMatchIsAddedProperly(){
        matches = ImmutableList.of(
                new Match("Team 1", "Team 2", 2, 1)
        );

        final LeagueTable table = new LeagueTable(matches);
        final List<LeagueTableEntry> tableEntries = table.getTableEntries();

        assertThat(tableEntries).isNotEmpty();
    }

    @Test
    public void testCorrectMatchCalculateCorrectTeamStats(){
        final Match match1 = new Match("Team 1", "Team 2", 2, 1);
        final Match match2 = new Match("Team 2", "TeaM 1", 2, 2);

        matches = ImmutableList.of(match1, match2);

        final LeagueTable table = new LeagueTable(matches);
        final List<LeagueTableEntry> tableEntries = table.getTableEntries();

        assertThat(tableEntries).hasSize(2);
        checkExpectedTeamStats(tableEntries.get(0), "Team 1", 2,1, 1, 0, 4, 3, 4);
        checkExpectedTeamStats(tableEntries.get(1), "Team 2", 2, 0, 1, 1, 3, 4, 1);
    }

    @Test
    public void testSameTeamNumberInCapitalLettersIsNotAddedTwice(){
        matches = ImmutableList.of(
                new Match("Team 1", "Team 2", 5, 2),
                new Match("TEAM 1", "Team 2", 5, 1)
        );

        final LeagueTable table = new LeagueTable(matches);
        final List<LeagueTableEntry> tableEntries = table.getTableEntries();

        assertThat(tableEntries).hasSize(2);
        checkExpectedTeamStats(tableEntries.get(0), "Team 1", 2,2, 0, 0, 10, 3, 6);
        checkExpectedTeamStats(tableEntries.get(1), "Team 2", 2,0, 0, 2, 3, 10, 0);
    }

    @Test
    public void testTableIsOrderedByPointsThenByGoalDifferenceThenByGoalsForAndThenByNameAfterBuildTheList(){
        matches = ImmutableList.of(
                new Match("Team 1", "Team 2", 5, 2),
                new Match("Team 1", "Team 3", 5, 1),
                new Match("Team 2", "Team 3", 0, 0),
                new Match("Team 1", "Team 6", 3, 2),
                new Match("Team 2", "Team 6", 3, 1),
                new Match("Team 3", "Team 6", 3, 1),
                new Match("Team 1", "Team 4", 2, 1),
                new Match("Team 1", "Team 5", 2, 1)
        );

        final LeagueTable table = new LeagueTable(matches);
        final List<LeagueTableEntry> tableEntries = table.getTableEntries();

        assertThat(tableEntries).hasSize(6);
        checkExpectedTeamStats(tableEntries.get(0), "Team 1", 5,5, 0, 0, 17, 7, 15);
        checkExpectedTeamStats(tableEntries.get(1), "Team 2", 3,1, 1, 1, 5, 6, 4);
        checkExpectedTeamStats(tableEntries.get(2), "Team 3", 3,1, 1, 1, 4, 6, 4);
        checkExpectedTeamStats(tableEntries.get(3), "Team 4", 1,0, 0, 1, 1, 2, 0);
        checkExpectedTeamStats(tableEntries.get(4), "Team 5", 1,0, 0, 1, 1, 2, 0);
        checkExpectedTeamStats(tableEntries.get(5), "Team 6", 3,0, 0, 3, 4, 9, 0);
    }

    private void checkExpectedTeamStats(final LeagueTableEntry entry, final String expectedName, final int played,
                                        final int won, final int drawn, final int lost, final int goalsFor,
                                        final int goalsAgainst, final int points) {
        assertThat(entry.getTeamName()).isEqualToIgnoringCase(expectedName);
        assertThat(entry.getPlayed()).isEqualTo(played);
        assertThat(entry.getWon()).isEqualTo(won);
        assertThat(entry.getDrawn()).isEqualTo(drawn);
        assertThat(entry.getLost()).isEqualTo(lost);
        assertThat(entry.getGoalsFor()).isEqualTo(goalsFor);
        assertThat(entry.getGoalsAgainst()).isEqualTo(goalsAgainst);
        assertThat(entry.getGoalDifference()).isEqualTo(goalsFor - goalsAgainst);
        assertThat(entry.getPoints()).isEqualTo(points);
    }
}