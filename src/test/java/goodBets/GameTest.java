package goodBets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameTest {

	private static Game testGame = new Game("Boston", "Baltimore", "07/20 7:05 PM", 190, -220);

	// Test method to calculate implied probability
	@Test
	void testCalculateImpliedProbabilityNegative() {
		double expected = 68.8;
		assertEquals(expected, testGame.calculateImpliedProbability(-220), 0.1);
	}

	// Test method to calculate implied probability
	@Test
	void testCalculateImpliedProbabilityPositive() {
		double expected = 34.5;
		assertEquals(expected, testGame.calculateImpliedProbability(190), 0.1);

	}

	// Test method to calculate implied probability specifically for home team
	@Test
	void testHomeImpliedProbability() {
		double expected = 34.5;
		assertEquals(expected, testGame.getHomeImpliedProbability(), 0.1);
	}

	// Test method to calculate implied probability specifically for away team
	@Test
	void testAwayImpliedProbability() {
		double expected = 68.8;
		assertEquals(expected, testGame.getAwayImpliedProbability(), 0.1);
	}

	// Test method to calculate delta for a negative delta
	@Test
	void testCalculateDeltaNegative() {
		double expected = -5.8;
		assertEquals(expected, testGame.calculateDelta(63.0, 68.8), 0.1);
	}

	// Test method to calculate delta for a positive delta
	@Test
	void testCalculateDeltaPositive() {
		double expected = 2.5;
		assertEquals(expected, testGame.calculateDelta(37.0, 34.48), 0.1);
	}

}
