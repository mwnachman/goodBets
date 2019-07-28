package goodBets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VegasCrawler {

	/**
	 * Helper function to get integer from odds string
	 * 
	 * @param s String of signed integer
	 * @return Signed integer
	 */
	private int getInteger(String s) {
		int integerToReturn = 0;

		String[] nums = s.split("");
		String[] unsignedNum = Arrays.copyOfRange(nums, 1, nums.length);
		String number = String.join("", unsignedNum);

		if (nums[0].equals("+")) {
			integerToReturn = Integer.parseInt(number);
		} else if (nums[0].equals("-")) {
			integerToReturn = Integer.parseInt(number) * -1;
		}

		return integerToReturn;
	}

	/**
	 * Creates Games for each game on Vegas Insider website
	 * 
	 * @return ArrayList of the day's games
	 */
	public ArrayList<Game> crawlVegasSite() {
		String address = "http://www.vegasinsider.com/mlb/odds/las-vegas/?s=538";
		Document doc = null;

		try {
			doc = Jsoup.connect(address).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Grab the table with the odds in it
		Elements oddsTable = doc.select("table.frodds-data-tbl");
		ArrayList<Game> games = new ArrayList<Game>();

		// Instantiate a new BaseballTeams object to compare team names
		TeamMap bt = new TeamMap();

		// We only want the larger elements in the table - the smaller
		// rows are used as dividers and for other purposes
		for (Element element : oddsTable.select("tr")) {
			List<Element> tds = element.children();

			if (tds.size() > 4) {
				Element teams = tds.get(0);
				List<Element> children = teams.children();

				String dateTime = null;
				String awayTeam = null;
				String homeTeam = null;

				// Get the home and away team names, and grab their
				// full names from the BaseballTeams class list
				if (children.size() > 4) {
					dateTime = children.get(0).text();
					awayTeam = bt.getFullName(children.get(2).text());
					homeTeam = bt.getFullName(children.get(4).text());
				}

				// Get the home and away odds and convert to signed integers
				Element odds = tds.get(2);
				String[] oddsArray = odds.text().split(" ");
				int awayOdds = 0;
				int homeOdds = 0;
				if (oddsArray.length > 2) {
					awayOdds = getInteger(oddsArray[1]);
					homeOdds = getInteger(oddsArray[2]);
				}

				// Check for missing info and add the game to the day's games array
				if (awayTeam != null && homeTeam != null && dateTime != null && awayOdds != 0 && homeOdds != 0) {
					Game game = new Game(awayTeam, homeTeam, dateTime, homeOdds, awayOdds);
					games.add(game);
				}
			}
		}

		return games;
	}

}
