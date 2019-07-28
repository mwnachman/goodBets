package goodBets;

import java.io.*;
import java.util.*;

public class Runner {
	
	private static ArrayList<Game> games;
	private static FiveThirtyEightReader fter = new FiveThirtyEightReader();
	private static double requiredEdge = 3.0; // tbd what this should be and whether it should be hardcoded, or the user shhould be prompted for an input
	
	public static ArrayList<Game> getGames() {
		VegasCrawler vc = new VegasCrawler();
		ArrayList<Game> games = vc.crawlVegasSite();
		return games;
	}
	
	public static void main(String[] args) {
		games = getGames();
		fter.updateGames(games);
		File out = new File("suggestions.txt");
		try(PrintWriter pw = new PrintWriter(out) ) {
			for(Game g : games) {
				if(g.largerDelta() >= requiredEdge){
					pw.println(g);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not write the File out.  Check permissions, or contact course staff for help");
		}
	}
}
