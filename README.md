# goodBets
trawls odd-makers and prediction sites to find high yield sports bets

## Authors
Meredith Latasa – mnachman@seas.upenn.edu
Eric Rice – ericrice@seas.upenn.edu 

## Introduction
GoodBets is designed to help gamblers find value in sports betting, by comparing available odds to independent statistical analyses.
 
There are a number of computer-based systems for predicting the outcome of sporting events freely available online.  One such system, run by Nate Silver’s fivethirtyeight.com (“538”) predicts the outcome of Major League Baseball (“MLB”) games, by using a sophisticated statistical model to calculate the probability of each team winning the game. 
 
In sports gambling, odds-makers publish the “odds” for a game, that dictates how bets on either team in the game will pay out.  The most common sort of odds for MLB games are “moneyline” odds.  Moneyline odds are either positive (e.g. +120) or negative (e.g. -180).  In the case of positive moneyline odds, the number represents how much a gambler who bets $100 stands to win, if she bets correctly (i.e. if she correctly bets $100 dollars on a +120 team, she will receive $220 back, her initial money ($100) plus $120 of profit). In the case of negative moneyline odds, the number represents how much a gambler must bet to win $100, if she bets correctly (i.e. if she correctly bets $100 dollars on a -180 team, she will receive $280 back, her initial money ($180) plus $100 of profit).  
 
Moneyline odds can be converted into an implied probability of each team winning.  And the discrepancy between a team’s win probability as calculated statistically versus the win probability implied by the odds offered on that team is potentially a source of value for people interested in betting on sports.  That is where GoodBets comes in.
 
GoodBets version 1.0 focuses solely on MLB games and uses only 538’s statistical analysis.  When run, it initially searches for games with available odds information at Vegas Insider, where it uses the “composite” odds.  It then retrieves the “actual” win probabilities from 538 for those games and reports the user any games where the “actual” probability of a given team winning is greater than the probability of that team winning implied by the odds available on that team by a given threshold.  That threshold (which we call the “required edge”) will be supplied by the user.  Future versions of GoodBets may support additional sports and/or additional model options.  
 
Please note that we do not intend to back test any models before starting, so users will have to come to their own conclusions about whether and how much the trust the model(s) that we are scraping.  
 
##Installation Instructions
 
Clone the repo and from the root directory run:

```
$ gradle build
```
 
##Running Instructions
 
Once GoodBets is installed, it can be run by running Runner.java from the command line or within an IDE.  GoodBets will prompt the user for a “required edge”, which must be a double between 0 and 100.  Per the above, the required edge represents a threshold difference between actual and implied probability of a team winning; only games above that threshold will be reported to the user.  The required edge is the only user input necessary.
 
After GoodBets runs, it will either (a) if there are games where one team is above the required edge, print a description and recommended bet in those games to a file called “suggestions.txt” and prompt the user to check that file, or (b) if no games are available with a team above the required edge, notify the user that it cannot suggest any games at that time.
 
