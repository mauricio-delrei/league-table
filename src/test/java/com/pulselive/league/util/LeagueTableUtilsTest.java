package com.pulselive.league.util;

import com.google.common.collect.ImmutableList;
import com.pulselive.league.model.LeagueTableEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LeagueTableUtilsTest {

    private static Stream<Arguments> createScoreTypeValues() {
        return Stream.of(
                Arguments.of(3, ScoreType.WON), // null strings should be considered blank
                Arguments.of(2, ScoreType.WON),
                Arguments.of(0, ScoreType.DRAWN),
                Arguments.of(-1, ScoreType.LOST),
                Arguments.of(-5, ScoreType.LOST)
        );
    }

    @ParameterizedTest
    @MethodSource("createScoreTypeValues")
    public void checkCreateScoreType(final int goalDifference, final ScoreType expectedScoreType) {
        assertThat(LeagueTableUtils.createScoreType(goalDifference)).isEqualTo(expectedScoreType);
    }

    private static Stream<Arguments> checkScoreTypeValues() {
        return Stream.of(
                Arguments.of(ScoreType.WON, ScoreType.WON, 1), // null strings should be considered blank
                Arguments.of(ScoreType.WON, ScoreType.DRAWN, 0),
                Arguments.of(ScoreType.WON, ScoreType.LOST, 0),
                Arguments.of(ScoreType.DRAWN, ScoreType.DRAWN, 1),
                Arguments.of(ScoreType.DRAWN, ScoreType.WON, 0),
                Arguments.of(ScoreType.DRAWN, ScoreType.LOST, 0),
                Arguments.of(ScoreType.LOST, ScoreType.LOST, 1),
                Arguments.of(ScoreType.LOST, ScoreType.WON, 0),
                Arguments.of(ScoreType.LOST, ScoreType.DRAWN, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("checkScoreTypeValues")
    public void testCheckScoreType(final ScoreType currentScoreType, final ScoreType expectedScoreType, final int expectedValue) {
        assertThat(LeagueTableUtils.checkScoreType(currentScoreType, expectedScoreType)).isEqualTo(expectedValue);
    }

    @Test
    public void testOrderTableEntries() {
        final LeagueTableEntry team5 = new LeagueTableEntry("Team 5", 4, 3, 1, 1,
                5, 1, 4, 10);
        final LeagueTableEntry team4 = new LeagueTableEntry("Team 4", 4, 3, 1, 1,
                5, 0, 5, 10);
        final LeagueTableEntry team3 = new LeagueTableEntry("Team 3", 4, 3, 1, 1,
                10, 5, 5, 10);
        final LeagueTableEntry team1 = new LeagueTableEntry("Team 1", 4, 4, 1, 0,
                10, 8, 2, 13);
        final LeagueTableEntry team2 = new LeagueTableEntry("Team 2", 4, 4, 1, 0,
                10, 8, 2, 13);

        final List<LeagueTableEntry> tableEntries = new ArrayList<LeagueTableEntry>() {{
            add(team1);
            add(team2);
            add(team3);
            add(team4);
            add(team5);
        }};

        final List<LeagueTableEntry> sortedList = LeagueTableUtils.orderTableEntries(tableEntries);
        assertThat(sortedList).isEqualTo(ImmutableList.of(team1, team2, team3, team4, team5));
    }
}