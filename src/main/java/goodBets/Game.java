package goodBets;

import java.util.*;

public class Game {
	private final String LEAGUE = "MLB"; // in the future this may be a variable
	private String homeTeam;
	private String awayTeam;
	private	String gameDay;
	private int homeTeamOdds; // Note that all odds are moneyline
	private int awayTeamOdds; 
	private double homeImpliedProbability;
	private double awayImpliedProbability;
	private double homeActualProbability;
	private double awayActualProbability;
	private double homeDelta;
	private double awayDelta;
	
	public Game(String homeTeam, String awayTeam, String gameDay, int homeTeamOdds, int awayTeamOdds) {
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
	 * @param odds
	 * @return
	 */
	public double calculateImpliedProbability(int odds) {
		if(odds<0) {
			double inverse = odds * -1;
			return(inverse/(inverse + 100.0))*100;
		}
		else return (100.0/(odds + 100.0))*100;
	}
	
	/**
	 * calculates the difference between the implied probability a team wins
	 * and the "actual" probability supplied by the relevant model
	 * @param actual
	 * @param implied
	 * @return
	 */
	public double calculateDelta(Double actual, Double implied) {
		return actual - implied;
	}
	
	/**
	 * Calculates the home team Delta
	 * @return
	 */
	public double getHomeDelta() {
		return homeDelta;
	}
	
	/**
	 * Calculates the away team Delta
	 * @return
	 */
	public double getAwayDelta() {
		return awayDelta;
	}
	
	/**
	 * Sets the home team "actual" probability.
	 * This is handled outside of the constructor, because the info will
	 * come from a different source
	 * @return
	 */
	public void setHomeActualProbability(double homeActualProbability) {
		this.homeActualProbability = homeActualProbability;
		this.homeDelta = calculateDelta(homeActualProbability, homeImpliedProbability);
	}
	
	/**
	 * Sets the away team "actual" probability.
	 * This is handled outside of the constructor, because the info will
	 * come from a different source
	 * @return
	 */
	public void setAwayActualProbability(double awayActualProbability) {
		this.awayActualProbability = awayActualProbability;
		this.awayDelta = calculateDelta(awayActualProbability, awayImpliedProbability);

	}
	
	/**
	 * returns the larger of the two deltas.  This method will be used
	 * by the runner method when it looks for games with an edge oof greater
	 * than n.
	 * @return
	 */
	
	public double largerDelta() {
		return Math.max(awayDelta, homeDelta);
	}
	
	/**
	 * Prints the relevant game info.
	 */
	
	public void displayGame() {
		System.out.printf("League: %s Date: %s \n", LEAGUE, gameDay);
		System.out.printf("%s vs. %s\n", homeTeam, awayTeam);
		if(awayDelta > 0 && awayDelta > homeDelta) {
			System.out.printf("Suggested Bet: %s at %d with a %f edge", awayTeam, awayTeamOdds, awayDelta);
		}
		else if (homeDelta > 0 && homeDelta >= awayDelta) {
			System.out.printf("Suggested Bet: %s at %d with a %f edge\n", homeTeam, homeTeamOdds, homeDelta);
		}
		else System.out.printf("Sorry, no value to be found here!");
	}

	public String getLEAGUE() {
		return LEAGUE;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public String getGameDay() {
		return gameDay;
	}

	public int getHomeTeamOdds() {
		return homeTeamOdds;
	}

	public int getAwayTeamOdds() {
		return awayTeamOdds;
	}

	public double getHomeImpliedProbability() {
		return homeImpliedProbability;
	}

	public double getAwayImpliedProbability() {
		return awayImpliedProbability;
	}

	public double getHomeActualProbability() {
		return homeActualProbability;
	}

	public double getAwayActualProbability() {
		return awayActualProbability;
	}
	
}
