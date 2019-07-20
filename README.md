# goodBets
trawls odd-makers and prediction sites to find high yield sports bets

# Design Document

Team Members:

•	Meredith Latasa
•	Eric Rice

Proposal:

There are a number of computer-based systems for predicting the outcome of sporting events freely available online.   (See, e.g., ESPN’s FPI for college football).    And at least some of those models are known to be slightly better than the Vegas odds-makers. 

We want to build a program that alerts users when it finds a discrepancy between the predicted outcome of a sporting event and the odds given by online bookies.  To do that, we will scrape prediction sites and odds-maker sites, perform calculations, and send users an email alert about discrepancies above a certain threshold.  The user can then decide if they would like to make a bet based on the reported discrepancy. 

We will start with just a single sport (baseball), a single prediction site (538), and single odds-provider’s site (Vegas Insider).  We may use a third-party authentication service like Facebook or Google to authenticate users and get basic information like email address.  In general, we are thinking of this project as the core of a system that could be built upon in future projects to be more comprehensive and customizable – though, if we have time, we may add some features now (such as additional prediction sites to refine the advice, or multiple sports, a database to save sports or email preferences, or a feature to keep track of how much money a user would have made if they had actually bet on the games indicated over some period of time).  Please note that we do not intend to back test any models before starting, so users will have to come to their own conclusions about whether and how much the trust the model(s) that we are scraping.  

Design:

1.	Game Class
  a.	Instance Variables
    i.	Home Team
    ii.	Away Team
    iii.	Date and Time
    iv.	Home Team Odds (money-line)
      1.	Will be taken from Vegas Insider
    v.	Away Team Odds (money-line)
      1.	Will be taken from Vegas Insider
    vi.	Home Team Implied Probability
      1.	Represents the probability of the Home Team winning that is implied by the Home Team Odds.
      2.	Will be calculated from Home Team Odds by Calculate Implied Probability method.
    vii.	Away Team Implied Probability
      1.	Represents the probability of the Away Team winning that is implied by the Away Team Odds.
      2.	Will be calculated from Away Team Odds by Calculate Implied Probability method.
    viii.	Home Team Actual Probability
      1.	Represents the “actual” probability that the Home Team wins, as calculated by the predictive model.
      2.	Will be taken from FiveThirtyEight.
    ix.	Away Team Actual Probability
      1.	Represents the “actual” probability that the Away Team wins, as calculated by the predictive model.
      2.	Will be taken from FiveThirtyEight.
    x.	Home Team Edge:
      1.	Represents how good of a bet the Home Team is at the Home Team Odds.
      2.	Represents the difference of the Home Team Actual Probability minus the Home Team Implied Probability
    xi.	Away Team Edge:
      1.	Represents how good of a bet the Away Team is at the Away Team Odds.
      2.	Will be the difference of the Away Team Actual Probability minus the Away Team Implied Probability 

  b.	Methods
    i.	Constructor:
      1.	Will populate the following Instance Variables with data scraped from Vegas Insider: 
        a.	Home Team
        b.	Away Team
        c.	Date and Time
        d.	Home Team Odds (money-line)
        e.	Away Team Odds (money-line)
    ii.	Set Actual Probability:
      1.	Will populate the following Instance Variables with data scraped from FiveThirtyEight:
        a.	Home Team Actual Probability
        b.	Away Team Actual Probability
    iii.	Calculate Implied Probability:
      1.	Calculates implied probability for a team in a game from money-line odds.
        a.	Positive Money-Line
          i.	Implied probability =	100/(odds + 100) 
        b.	Negative Money-Line
          i.	(-1 * odds ) / (( -1* odds) + 100)
    iv.	Calculate Edge:
      1.	This method will calculate the “Edge” for each team based on the implied and actual probabilities that that team wins and populate the following Instance Variables:
        a.	Away Team Edge
        b.	Home Team Edge
    v.	Get Biggest Edge: Compares the Away Team Edge and Home Team Edge:
      1.	Returns the larger of Home Team Edge or Away Team Edge
      2.	This will be used by the program when determining if a game provides an edge at least equal to the threshold edge supplied by the user.
    vi.	Display Suggested Bet:
      1.	Compares the Away Team Edge and Home Team Edge and, if either is greater than zero, chooses the greater one and returns a String setting forth: 
        a.	Which team to bet on (Home Team or Away Team)
        b.	The odds for that team
        c.	The edge those odds provide
      2.	Note that the program returns only a single team.  In theory, both the Home Team and Away Team could have positive edges, in which case the user would obviously want to be on both teams.  However, that scenario really shouldn’t occur because it would represent an arbitrage opportunity, so we have ignored that case here.  

2.	Vegas Scraper Class:
  a.	Instance Variables:
    i.	Odds Info: 
      1.	A file containing odds information.
      2.	Will be a downloaded version of Vegas Insider.
    ii.	Current Games:
      1.	An array list of Game Objects without actual probabilities.
      2.	Will be created by the Read Odds Method.
  b.	Methods:
    i.	Crawl Vegas site:
      1.	Will crawl the site and create Game Objects for each game listed
      2.	Will create game objects with contain home and away team names (standardized using the BaseballTeams class), date/time of game, and odds for home and away teams
    ii.	getInteger:
      1.	To turn the odds strings into signed integers
3.	FiveThirtyEight Scraper Class:
  a.	Instance Variables:
    i.	Predictive Info: 
      1.	A file containing predictions for games
      2.	Will be a downloaded version of FiveThirtyEight.
    ii.	Current Games:
      1.	An array list of Game Objects without actual probabilities.
      2.	Will be passed to the Five Thirty Eight Scraper Class.
  b.	Methods:
    i.	Find Data:
      1.	Given a Game object, will find the data in Predictive Info corresponding to the same game (same home and away teams and same date and time).
      2.	Will return the actual probabilities for both teams.
    ii.	Update Current Games:
      1.	This method will iterate through the game objects in Current Games, and for each Game.
      2.	For each Game it will use Find Date find the corresponding data in Predictive Info and update the Home Team Actual Probability and Away Team Actual Probability.
      3.	The Method will then return the updated ArrayList of Games.
      4.	BaseballTeams Class
  a.	Instance Variables:
    i.	HashMap with Vegas site name as key and standardized name and 538 site names in an array as the value
  b.	Methods:
    i.	Constructor:  reads in a .csv file of team names (standardized, Vegas site, and 538) and creates a HashMap
    ii.	getTeamName:  uses Vegas name to get standardized name

5.	Runner Class
  a.	Instance Variables:
    i.	Threshold Edge
      1.	The User Set threshold for what they consider a valuable bet (e.g. 5 percentage point difference between actual and implied probability).
    ii.	Output File
      1.	This is the file to which we will write our suggested bets.
    iii.	Current Games
      1.	ArrayList of Game objects for which full information is available.
      2.	This will be created by the Vegas Scraper Class and finalized by the FiveThrityEight Scraper Class.
  b.	Methods
    i.	Get Suggested Bets:
      1.	Will iterate through Current Games and check whether, for each Game, either team has an Edge at least equal to the Threshold Edge.  
      2.	If one team does have a sufficient Edge, it will print the display string for the relevant team to the Output File.

