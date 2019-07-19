package goodBets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BaseballTeams {
	Map<String, String[]> teamsVegasKeyMap;

	public BaseballTeams() {
		File f = new File("MLB_teams.csv");
		Scanner in = null;
		teamsVegasKeyMap = new HashMap<String, String[]>();
		
		try {
			in = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		in.nextLine();
		while (in.hasNextLine()) {
			String[] teamInfo = in.nextLine().split(",");
			String name = teamInfo[1];
			String[] alternativeNames = new String[2];
			alternativeNames[0] = teamInfo[0];
			alternativeNames[1] = teamInfo[2];
			teamsVegasKeyMap.put(name, alternativeNames);
		}
	}
	
	public String getFullName(String name) {
		return teamsVegasKeyMap.get(name)[0];
	}
}
