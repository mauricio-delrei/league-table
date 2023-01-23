# Pulselive test
+ [Introduction](#introduction)
+ [Task](#Task)
+ [Assumptions](#Assumptions)
+ [Approach](#approach)
+ [Tests](#Tests)

# Introduction
The purpose of this exercise is to demonstrate your ability to use 
Java to build a dynamic football league table generator.

# Task
Consider a league table for football. Each team plays a number of matches and the results
of each match build the table. Given the code attached as a starting point build
the LeagueTable class that can take a list of completed matches and produce a sorted 
list of LeagueTableEntry objects.

The sorting rules for entries in the league table should be
* Sort by total points (descending)
* Then by goal difference (descending)
* Then by goals scored (descending)
* Then by team name (in alphabetical order)
A win is worth three points to the winning team. A draw is worth one point to each team.

Your code will be run through a series of JUnit tests to validate the implementation so it is important 
that method signatures are not changed. You will also be assessed on code quality and clarity.

In undertaking this task, please consider the following:
* You should be submitting production quality code
* Future reuse and extension of code
* Any documentation / notes on build


# Assumptions
I have considered the next assumptions to make the program:
- If the list of matches is empty or null, the result is going to be an empty list.

- Any incorrect match won't be added to the list of matches. E.g. if the score of a match is (1, -1), this match
will be ignored and no one of the scores/teams will be considered.

- The teams names are compared using equalsIgnoreCase. It is avoiding that same team name in capital letters
will not be considered twice.

- I changed LeagueTableEntry to make it an immutable class. Just removing the set methods, and letting assigning
 values just using the constructor.

- Any time that a team is checked, a ScoreType is created. A ScoreType is an enum that helps in the treatment
of any team. It contains if the team won, if it drawn or lost. And the proper points earned in each situation. 

- There is a function in LeagueTableUtils that given a list of LeagueTableEntry, prints the League table. I checked 
the format but I didn't add any call to this method in the current code. If it is needed, the result of
LeagueTable.getTableEntries can be used as the input parameter to print it. 


# Approach
I have been thinking about two different approaches.
 - I thought about getting all the different teams and then filter by each of them getting all the results
  and setting them in a list. We could iterate over this small list getting all the points, scored goals, etc. 
  
 - As the normal amount of teams in each league doesn't seem too big, I decided instead, to consider match 
 by match and updating the set of matches with the proper team info (2 for each match).
 
# Tests
Open a command line. Navigate to project root folder and execute "mvn clean install".
