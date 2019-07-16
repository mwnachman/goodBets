package goodBets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class VegasCrawler {

	public static void main(String[] args) {
		String address = "http://www.vegasinsider.com/mlb/odds/las-vegas/?s=538";
		Document doc = null;
		try {
			doc = Jsoup.connect(address).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements oddsTable = doc.select("table.frodds-data-tbl");
		for (Element element : oddsTable.select("tr")) {
			List<Element> tds = element.children();
			if (tds.size() > 4) {
				Element teams = tds.get(0);
				List<Element> children = teams.children();
				if (children.size() > 4) {
					Element dateTime = children.get(0);
					System.out.println(dateTime.text());
					Element awayTeam = children.get(2);
					System.out.println(awayTeam.text());
					Element homeTeam = children.get(4);
					System.out.println(homeTeam.text());
				}
				Element odds = tds.get(2);
				String[] oddsArray = odds.text().split(" ");
				if (oddsArray.length > 2) {
					String awayOdds = oddsArray[1];
					System.out.println(awayOdds);
					String homeOdds = oddsArray[2];
					System.out.println(homeOdds);
				}
				System.out.println("");

			}
		}
	}

}
