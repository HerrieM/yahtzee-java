package nl.marcel.yahtzee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Player {
	private String loginName, screenName;
	private int playerHighScore, gamesPlayed;
	private ArrayList<String> nameList;
	private java.sql.Date date_scored;
	static public int[] highScore;
	static public String[] names;
	static public java.sql.Date[] dates;
	ResultSet playerRes, highScores;
	DbConnections dbCon = new DbConnections();

	public void loadPlayer(String username) {
		playerRes = dbCon.getData(username, "*");
		try {
			playerRes.next();
			playerHighScore = playerRes.getInt("high_score");
			gamesPlayed = playerRes.getInt("games_played");
			date_scored = playerRes.getDate("date_scored");
			loginName = playerRes.getString("login_name");
			screenName = playerRes.getString("screen_name");
			dbCon.closeCon();
		} catch (SQLException e) {
			System.out.println("error in loadPlayer " + e);
		}
	}

	public String getName(String type) {
		if (type == "login") {
			return loginName;
		} else {
			return screenName;
		}
	}

	public int getPlayerHighscore() {
		return playerHighScore;
	}
	
	public java.sql.Date getHighScoreDate() {
		return date_scored;
	}

	public void setPlayer(String username, int highscore) {
		boolean new_date = true;
		if (highscore < playerHighScore) {
			highscore = playerHighScore;
			new_date = false;
		}
		dbCon.updatePlayer(username, highscore, (gamesPlayed + 1), new_date);
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void newPlayer(String username, String password) {
		dbCon.newPlayer(username, password);
	}

	public void loadHighScore() {
		highScores = dbCon.getHighScoreArray();
		highScore = new int[5];
		names = new String[5];
		dates = new java.sql.Date[5];
		try {
			int i = 0;
			while (highScores.next()) {
				highScore[i] = highScores.getInt("high_score");
				names[i] = (highScores.getString("login_name"));
				dates[i] = highScores.getDate("date_scored");
				i++;
			}
		} catch (SQLException e) {
			System.out.println("error in loadHighScore ");
		}
		dbCon.closeCon();
	}

	public ArrayList<String> getNameList() {
		nameList = dbCon.getNameList();
		dbCon.closeCon();
		return nameList;
	}
	
	public void setGuest (int highscore, String guestName) {
		dbCon.setGuest(highscore, guestName);
	}
}
