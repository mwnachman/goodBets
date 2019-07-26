package goodBets;

import java.util.*;
import java.io.*;

public class TeamMap {
	
	private HashMap<String, String> teamMap = new HashMap<String, String>();
	private HashMap<String, String> teamsVegasKeyMap = new HashMap<String, String>();
	private File teams = new File("MLB_Team_Names.csv");
	private Scanner teamIn;
	
	public TeamMap() {
		
		try {
			teamIn = new Scanner(teams);
			teamIn.nextLine();
			while(teamIn.hasNextLine()) {
				String row = teamIn.nextLine();
				String[] info = row.split(",");
				teamMap.put(info[3], info[1]);
				
				String vegasName = info[1];
				String fullName = info[0];
				teamsVegasKeyMap.put(vegasName, fullName);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String getFullName(String name) {
		return teamsVegasKeyMap.get(name);
	}

	public HashMap<String, String> getTeamMap() {
		return teamMap;
	}

}
