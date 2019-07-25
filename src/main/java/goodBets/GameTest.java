package goodBets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

class GameTest {

	private static Game testGame = new Game("Boston", "Baltimore", "07/20 7:05 PM", 190, -220);

	@BeforeClass
	public static void setUpBeforeClass() {
		testGame.setHomeActualProbability(37);
		testGame.setAwayActualProbability(63);
	}

	@Test
	void testCalculateImpliedProbabilityNegative() {
		double expected = 68.8;
		assertEquals(expected, testGame.calculateImpliedProbability(-220), 0.1);
	}

	@Test
	void testCalculateImpliedProbabilityPositive() {
		double expected = 34.5;
		assertEquals(expected, testGame.calculateImpliedProbability(190), 0.1);

	}

	@Test
	void testHomeImpliedProbability() {
		double expected = 34.5;
		assertEquals(expected, testGame.getHomeImpliedProbability(), 0.1);
	}

	@Test
	void testAwayImpliedProbability() {
		double expected = 68.8;
		assertEquals(expected, testGame.getAwayImpliedProbability(), 0.1);
	}

	@Test
	void testCalculateDeltaNegative() {
		double expected = -5.8;
		assertEquals(expected, testGame.calculateDelta(63.0, 68.8), 0.1);
	}

	@Test
	void testCalculateDeltaPositive() {
		double expected = 2.5;
		assertEquals(expected, testGame.calculateDelta(37.0, 34.48), 0.1);
	}

}
