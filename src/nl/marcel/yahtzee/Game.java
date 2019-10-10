package nl.marcel.yahtzee;

abstract class Game {
	protected String gameType;
	protected String gameName;

	Game(String gameType, String gameName) {
		this.gameType = gameType;
		this.gameName = gameName;
	}
}
