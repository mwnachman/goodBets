package goodBets;

import java.io.*;
import java.util.*;

/**
 * This class runs the goodBets program. It initially prompts the user to supply
 * a required edge, between 0 and 100 percentage points. It then checks
 * VegasInsiders.com for games for which odds are currently available and then
 * checks fivethrityeight.com for the "actual" probabilities of each team
 * winning those games. It then iterates through the list of those games and, if
 * any bets represent an edge over the required edge, prints those games to a
 * new file called suggestions.txt.
 *
 */

public class Runner {

	private static ArrayList<Game> games;
	private static ArrayList<Game> suggestedGames;
	private static FiveThirtyEightReader fter = new FiveThirtyEightReader();
	private static double requiredEdge;

	/**
	 * This function prompts user for user's required edge, which will be a double
	 * between zero and 100.
	 * 
	 * @return required edge
	 */
	public static double getRequiredEdge() {
		Scanner user = new Scanner(System.in);
		boolean tryAgain = true;
		while (tryAgain) {
			System.out.println("What is your required edge (please note, must be between zero and 100):");
			if (user.hasNextDouble()) {
				double required = user.nextDouble();
				if (required >= 0 && required <= 100) {
					return required;
				}
			}
			user.nextLine();
		}
		return 0;
	}

	/**
	 * This function initializes the VegasCrawler and uses it to get an ArrayList of
	 * games for which odds are currently available.
	 * 
	 * @return array list of game objects that can be bet on
	 */
	public static ArrayList<Game> getGames() {
		VegasCrawler vc = new VegasCrawler();
		ArrayList<Game> games = vc.crawlVegasSite();
		fter.updateGames(games);
		return games;
	}

	/**
	 * This function takes the ArrayList of Games for which odds are currently
	 * available and iterates through the fivethirtyeight data to update the Games
	 * for the actual probabilities.
	 * 
	 * @param array list of game objects
	 */
	public static ArrayList<Game> getSuggestedGames(ArrayList<Game> games) {
		ArrayList<Game> suggestedGames = new ArrayList<Game>();
		for (Game g : games) {
			if (g.largerDelta() > requiredEdge) {
				suggestedGames.add(g);
			}
		}
		return suggestedGames;
	}

	/**
	 * Requests input from the user, gathers relevant information,
	 * and creates response file
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		requiredEdge = getRequiredEdge();
		games = getGames();
		suggestedGames = getSuggestedGames(games);
		
		if (suggestedGames.isEmpty()) { // if no games are above the required edge, tell the user there is nothing to
										// suggest
			System.out.println("Sorry, there are no games to suggest at this time!");
		} else { // if there are games over the required edge, print those to suggestions.txt
			File out = new File("suggestions.txt");
			try (PrintWriter pw = new PrintWriter(out)) {
				for (Game g : suggestedGames) {
					pw.println(g);
				}
				System.out.println("Please check suggestions.txt for your suggested bets!");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not write the File out.");
			}
		}
	}
}
