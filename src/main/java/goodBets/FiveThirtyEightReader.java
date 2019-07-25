import java.io.*;
import java.util.*;
import java.net.*;

public class FiveThirtyEightReader {

	private ArrayList<Game> games;
	private URL fiveThirtyEight;
	private Scanner in;
	private TeamMap tm = new TeamMap();
	private HashMap<String, String> nameMap;

	public FiveThirtyEightReader(ArrayList<Game> games) {
		this.games = games;
		try {
			fiveThirtyEight = new URL("https://projects.fivethirtyeight.com/mlb-api/mlb_elo_latest.csv");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			in = new Scanner(fiveThirtyEight.openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nameMap = tm.getTeamMap();
	}
	
	public boolean sameGame(String[] data, Game game) {
		if (sameDate(game.getGameDay(), data[0]) && sameTeam(game.getAwayTeam(), data[4])
				&& sameTeam(game.getHomeTeam(), data[5])) {
			return true;
		} else
			return false;
	}

	public boolean sameTeam(String gameTeam, String dataTeam) {
		if (nameMap.get(dataTeam).equals(gameTeam)) {
			return true;
		} else
			return false;
	}

	public boolean sameDate(String gameDate, String dataDate) {
		String[] gameInfo = gameDate.split(" ")[0].split("/");
		String[] dataInfo = dataDate.split("-");
		if (gameInfo[0].equals(dataInfo[1]) && gameInfo[1].equals(dataInfo[2])) {
			return true;
		} else
			return false;
	}

	public ArrayList<Game> updateGames() {
		while (in.hasNext()) {
			String[] gameData = in.nextLine().split(",");
			for(Game g:games) {
				if(sameGame(gameData,g)) {
					g.setAwayActualProbability(Double.parseDouble(gameData[8]));
					g.setHomeActualProbability(Double.parseDouble(gameData[9]));
					break;
				}
			}
		}
		return games;
	}

}
