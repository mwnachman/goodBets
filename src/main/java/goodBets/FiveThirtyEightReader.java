package goodBets;

import java.io.*;
import java.util.*;
import java.net.*;

/**
 * This class pulls the updated MLB prediction data from Nate Silver's
 * fivethirty eight, formatted as a CSV file and reads it to update the actual
 * probabilities for games.
 * 
 * @author ericrice
 *
 */

public class FiveThirtyEightReader {

	private URL fiveThirtyEight; // this will be the url of the fivethirtyeight csv file
	private Scanner in; // scanner that will be used to read the fivethirtyeight csv file
	private TeamMap tm = new TeamMap();// this object allows the production of the nameMap
	private HashMap<String, String> nameMap; // this is the hashmap used to match the abbreviated team names in the
												// fivethirtyeight csv file to the full team names that the game objects
												// use.

	public FiveThirtyEightReader() {
		try {
			fiveThirtyEight = new URL("https://projects.fivethirtyeight.com/mlb-api/mlb_elo_latest.csv"); // initialize
																											// fivethirtyeight
																											// url,
																											// surrounded
																											// by
																											// try/catch
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			in = new Scanner(fiveThirtyEight.openStream()); // initialize scanner, based on file downloaded from
															// fivethirtyeight, surrounded by try/catch
		} catch (IOException e) {
			e.printStackTrace();
		}
		nameMap = tm.getTeamMap(); // initialize name map
	}

	/**
	 * this function checks whether a line of data from fivethirtyeight and a game
	 * object are referring to the same actual MLB game
	 * 
	 * @param data one line from 538
	 * @param game one game object
	 * @return
	 */
	public boolean sameGame(String[] data, Game game) {
		if (sameDate(game.getGameDay(), data[0]) && sameTeam(game.getAwayTeam(), data[5])
				&& sameTeam(game.getHomeTeam(), data[4])) {
			return true; // checks for the same date, home team and away team
		} else
			return false;
	}

	/**
	 * this function takes a team name, as stored in the five thirty eight csv file
	 * and a team name from a game object and uses the nameMap to check whther they
	 * are referring to the same team
	 * 
	 * @param gameTeam a team name as stored in a game object
	 * @param dataTeam a team name as supplied by 538
	 * @return
	 */

	public boolean sameTeam(String gameTeam, String dataTeam) {
		if (nameMap.get(dataTeam).equals(gameTeam)) {
			return true;
		} else
			return false;
	}

	/**
	 * This function takes a date as stored in the fivethrityeight csv file and a
	 * date as stored in a game object and checks whether they are referring to the
	 * same date. Please note that this assumes the years are the same. For other
	 * sports, such as hockey or basketball where seasons run over the new year,
	 * this would need to be updated.
	 * 
	 * @param gameDate a date as stored in the game object
	 * @param dataDate a date as provided in 538
	 * @return
	 */

	public boolean sameDate(String gameDate, String dataDate) {
		String[] gameInfo = gameDate.split(" ")[0].split("/");
		String[] dataInfo = dataDate.split("-");
		if (gameInfo[0].equals(dataInfo[1]) && gameInfo[1].equals(dataInfo[2])) {
			return true;
		} else
			return false;
	}

	/**
	 * Iterates through the fivethrityeight csv file and a supplied ArrayList of
	 * games, for each line of the fivethirtyeight file, it checks whether that line
	 * corresponds to one of the games in the supplied ArrayList, and, if so,
	 * updates the actual probabilities for that game object.
	 * 
	 * @param games an array list of game objects
	 */

	public void updateGames(ArrayList<Game> games) {
		in.nextLine();
		while (in.hasNext()) {
			String[] gameData = in.nextLine().split(",");
			for (Game g : games) {
				if (sameGame(gameData, g)) {
					g.setAwayActualProbability(Double.parseDouble(gameData[9]) * 100);
					g.setHomeActualProbability(Double.parseDouble(gameData[8]) * 100);
					break;
				}
			}
		}
		in.close();
	}

}
