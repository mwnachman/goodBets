package goodBets;

import java.util.ArrayList;

public class Runner {
	private ArrayList<Game> games;
	
	public Runner() {
		games = getGames();
	}
	
	public ArrayList<Game> getGames() {
		VegasCrawler vc = new VegasCrawler();
		ArrayList<Game> games = vc.crawlVegasSite();
		return games;
	}
	
	public static void main(String[] args) {
		Runner r = new Runner();
	}
}
