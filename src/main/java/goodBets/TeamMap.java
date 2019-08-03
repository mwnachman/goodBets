package goodBets;

import java.util.*;
import java.io.*;

/**
 * This class creates a HashMap, based on a stored CSV file
 * (MLB_Team_Names.csv), mapping the three digit code representing a team,
 * returns the full name of that team, which is used in the Game class
 * 
 * @author ericrice
 *
 */

public class TeamMap {

	private HashMap<String, String> teamMap = new HashMap<String, String>();
	private HashMap<String, String> teamsVegasKeyMap = new HashMap<String, String>();
	private File teams = new File("MLB_Team_Names.csv");
	private Scanner teamIn;

	public TeamMap() {

		try {
			teamIn = new Scanner(teams);
			teamIn.nextLine();
			while (teamIn.hasNextLine()) {
				String row = teamIn.nextLine();
				String[] info = row.split(",");
				teamMap.put(info[3], info[0]);
				String vegasName = info[1];
				String fullName = info[0];
				teamsVegasKeyMap.put(vegasName, fullName);
			}
			teamIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Give a three digit code representing a team, returns the full name of that
	 * team, which is used in the Game class
	 * 
	 * @param name
	 * @return
	 */

	public String getFullName(String name) {
		return teamsVegasKeyMap.get(name);
	}

	/**
	 * Returns a HasMap, mapping the three digit code representing a team, returns
	 * the full name of that team, which is used in the Game class
	 * 
	 * @return
	 */
	public HashMap<String, String> getTeamMap() {
		return teamMap;
	}

}
