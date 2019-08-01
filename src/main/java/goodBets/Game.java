package goodBets;

public class Game {
	private final String LEAGUE = "MLB"; // in the future this may be a variable
	private String homeTeam; // Name of home team
	private String awayTeam; // name of away team
	private String gameDay; // date and time of game
	private int homeTeamOdds; // Vegas moneyline odds that home team wins
	private int awayTeamOdds; // Vegas moneyline odds that away team wins
	private double homeImpliedProbability; // Probability of a win implied by home team odds
	private double awayImpliedProbability; // Probability of a win implied by away team odds
	private double homeActualProbability; // Probability of a home team win calculated by external model
	private double awayActualProbability; // Probability of an away team win calculated by external model
	private double homeDelta; // Difference between the actual and implied probabilities for the home team
	private double awayDelta; // Difference between the actual and implied probabilities for the home team

	/**
	 * Initializes the game object with the teams, day, and odds variablaes, which
	 * will have been taken from Vegas Insiders by the Vegas Crawler class
	 * 
	 * @param homeTeam
	 * @param awayTeam
	 * @param gameDay
	 * @param homeTeamOdds
	 * @param awayTeamOdds
	 */

	public Game(String awayTeam, String homeTeam, String gameDay, int homeTeamOdds, int awayTeamOdds) {
		super();
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.gameDay = gameDay;
		this.homeTeamOdds = homeTeamOdds;
		this.awayTeamOdds = awayTeamOdds;
		this.homeImpliedProbability = calculateImpliedProbability(homeTeamOdds);
		this.awayImpliedProbability = calculateImpliedProbability(awayTeamOdds);
	}

	/**
	 * uses the money line odds on one team to calculate the implied probability
	 * that that team wins.
	 * 
	 * @param odds a team wins
	 * @return The implied probability that team wins
	 */

	public double calculateImpliedProbability(int odds) {
		if (odds < 0) {
			double inverse = odds * -1;
			return (inverse / (inverse + 100.0)) * 100;
		} else
			return (100.0 / (odds + 100.0)) * 100;
	}

	/**
	 * calculates the difference between the implied probability a team wins and the
	 * "actual" probability supplied by the relevant model
	 * 
	 * @param actual probability a team wins
	 * @param implied probability a team wins
	 * @return the difference between actual and implied probability
	 */
	public double calculateDelta(Double actual, Double implied) {
		return actual - implied;
	}

	/**
	 * Calculates the home team Delta
	 * 
	 * @return The home team delta
	 */
	public double getHomeDelta() {
		return homeDelta;
	}

	/**
	 * Calculates the away team Delta
	 * 
	 * @return Away team delta
	 */
	public double getAwayDelta() {
		return awayDelta;
	}

	/**
	 * Sets the home team "actual" probability. This is handled outside of the
	 * constructor, because the info will come from a different source.
	 * 
	 * Also calculates and sets Home Team Delta.
	 * 
	 * @param home team's actual probability of winning
	 */
	public void setHomeActualProbability(double homeActualProbability) {
		this.homeActualProbability = homeActualProbability;
		this.homeDelta = calculateDelta(homeActualProbability, homeImpliedProbability);
	}

	/**
	 * Sets the away team "actual" probability. This is handled outside of the
	 * constructor, because the info will come from a different source Also
	 * calculates and sets Away Team Delta.
	 * 
	 * @param away team's actual probability of winning
	 */
	public void setAwayActualProbability(double awayActualProbability) {
		this.awayActualProbability = awayActualProbability;
		this.awayDelta = calculateDelta(awayActualProbability, awayImpliedProbability);

	}

	/**
	 * @return the homeImpliedProbability
	 */
	public double getHomeImpliedProbability() {
		return homeImpliedProbability;
	}

	/**
	 * @param homeImpliedProbability the homeImpliedProbability to set
	 */
	public void setHomeImpliedProbability(double homeImpliedProbability) {
		this.homeImpliedProbability = homeImpliedProbability;
	}

	/**
	 * @return the awayImpliedProbability
	 */
	public double getAwayImpliedProbability() {
		return awayImpliedProbability;
	}

	/**
	 * @param awayImpliedProbability the awayImpliedProbability to set
	 */
	public void setAwayImpliedProbability(double awayImpliedProbability) {
		this.awayImpliedProbability = awayImpliedProbability;
	}

	/**
	 * @return the homeActualProbability
	 */
	public double getHomeActualProbability() {
		return homeActualProbability;
	}

	/**
	 * @return the awayActualProbability
	 */
	public double getAwayActualProbability() {
		return awayActualProbability;
	}

	/**
	 * returns the larger of the two deltas. This method will be used by the runner
	 * method when it looks for games with an edge of greater than n.
	 * 
	 * @return the larger of the home team delta and away team delta
	 */

	public double largerDelta() {
		return Math.max(awayDelta, homeDelta);
	}

	/**
	 * @return a string of the relevant game info..
	 */

	@Override
	public String toString() {
		StringBuilder description = new StringBuilder(
				"League: " + LEAGUE + " Date:  " + gameDay + "\n" + awayTeam + " vs. " + homeTeam + "\n");
		description.append(String.format(
				"Away Team Prob: %f, Away Team Implied Prob: %f, Away Team Edge: %f\nHome Team Prob: %f Home Team Implied Prob: %f, Home Team Edge: %f\n",
				awayActualProbability, awayImpliedProbability, awayDelta, homeActualProbability, homeImpliedProbability,
				homeDelta));
		if (awayDelta > 0 && awayDelta > homeDelta) {
			description.append(
					String.format("Suggested Bet: %s at %d with a %f edge.\n", awayTeam, awayTeamOdds, awayDelta));
		} else if (homeDelta > 0 && homeDelta >= awayDelta) {
			description.append(
					String.format("Suggested Bet: %s at %d with a %f edge.\n", homeTeam, homeTeamOdds, homeDelta));
		} else
			description.append("Sorry, no value to be found here!\n");
		return description.toString();
	}

	/**
	 * return away team
	 * 
	 * @return away team
	 */

	public String getAwayTeam() {
		return awayTeam;
	}

	/**
	 * return home team
	 * 
	 * @return home team
	 */

	public String getHomeTeam() {
		return homeTeam;
	}

	/**
	 * return game day
	 * 
	 * @return game date
	 */

	public String getGameDay() {
		return gameDay;
	}

}
