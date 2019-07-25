import java.util.*;
import java.io.*;

public class TeamMap {
	
	private HashMap<String, String> teamMap = new HashMap<String, String>();
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
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, String> getTeamMap() {
		return teamMap;
	}

}
