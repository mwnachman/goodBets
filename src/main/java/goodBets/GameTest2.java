
public class GameTest2 {
	
	private static Game testGame = new Game("Boston", "Baltimore", "07/20 7:05 PM", 190, -220);
	
	public static void main(String[] args) {
		testGame.setHomeActualProbability(37);
		testGame.setAwayActualProbability(63);
		testGame.displayGame();
	}

}
