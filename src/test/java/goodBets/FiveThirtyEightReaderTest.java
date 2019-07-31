package goodBets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FiveThirtyEightReaderTest {
	
	private static Game testGame;
	private static FiveThirtyEightReader fte;
	private static String[] trueGame;
	private static String[] falseGame;

	@BeforeAll
	public static void setUp() {
		testGame = new Game("Boston Red Sox", "Baltimore Orioles", "07/20 7:05 PM", 190, -220);
		fte = new FiveThirtyEightReader();
		trueGame = "2019-07-20,2019,0,,BAL,BOS,1423.49850770114,1547.61899839909,0.35977522286323477,0.6402247771367653,1420.42322722643,1550.6942788738,1407.97578495173,1543.04532020815,Thomas Eshelman,Rick Porcello,47.6183018617218,49.2396132899981,3.108495192381708,-16.861973685814917,0.3718219860073634,0.6281780139926366,1405.5591487938,1545.46195636608,6,17".split(",");
		falseGame = "2019-07-21,2019,0,,NYY,COL,1582.09946472755,1493.63239138863,0.6564271424813375,0.3435728575186625,,,1588.35704179565,1498.51335397097,James Paxton,German Marquez,52.878260324597,53.6081781175184,4.171820954223392,16.913678910901247,0.6415249273211301,0.3584750726788699,,,,".split(",");
	}

	// Checks whether the "sameGame" method works to match two games
	@Test
	void testSameGameTrue() {
		fte = new FiveThirtyEightReader();
		assertTrue(fte.sameGame(trueGame, testGame));
	}
	
	// Checks whether "sameGame" method works to distinguish two games
	@Test
	void testSameGameFalse() {
		assertFalse(fte.sameGame(falseGame, testGame));
	}

	// Checks whether "sameTeam" method works to compare teams
	@Test
	void testSameTeamTrue() {
		assertTrue(fte.sameTeam(testGame.getAwayTeam(), trueGame[5]));	
	}
	
	// Checks whether "sameTeam" method works to compare teams
	@Test
	void testSameTeamFalse() {
		assertFalse(fte.sameTeam(testGame.getAwayTeam(), falseGame[5]));	
	}

	// Checks whether "sameDate" method works to compare dates
	@Test
	void testSameDateTrue() {
		assertTrue(fte.sameDate(testGame.getGameDay(), trueGame[0]));	
	}
	
	// Checks whether "sameDate" method works to compare dates
	@Test
	void testSameDateFalse() {
		assertFalse(fte.sameDate(testGame.getGameDay(), falseGame[0]));	
	}

}
