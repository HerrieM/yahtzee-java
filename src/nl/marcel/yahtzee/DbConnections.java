package nl.marcel.yahtzee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbConnections {
	private ResultSet rs, highScores;
	private ArrayList<String> nameList;
	private Connection con;

	public ResultSet getData(String username, String field) {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/yahtzee", "root", "");

			Statement stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT " + field + " FROM player WHERE login_name='" + username + "'");

		} catch (Exception e) {
			System.out.println("error in getData " + e);
		}
		return rs;
	}

	private void setData(String query) {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/yahtzee", "root", "");

			Statement stmt = con.createStatement();

			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("error in setData " + e);
		}
	}

	public void newPlayer(String username, String password) {
		setData("INSERT INTO `player`(`id`, `login_name`, `screen_name`, `password`, `high_score`, `date_scored`, `games_played`) "
				+ "VALUES (null, '" + username + "', '', '" + password + "', 0, CURRENT_TIMESTAMP,  0)");
	}

	public void updatePlayer(String username, int highscore, int games_played, boolean new_date) {
		if (new_date) {
			setData("UPDATE `player` SET `high_score`=" + highscore + ",`games_played`=" + games_played
					+ ", date_scored=CURRENT_TIMESTAMP WHERE login_name = '" + username + "'");
		} else {
			setData("UPDATE `player` SET `high_score`=" + highscore + ",`games_played`=" + games_played
					+ " WHERE login_name = '" + username + "'");
		}
	}

	// Highscore needs to be fixed, there is no output with MAX
	public ResultSet getHighScoreArray() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/yahtzee", "root", "");

			Statement stmt = con.createStatement();

			highScores = stmt.executeQuery("SELECT high_score, login_name, date_scored FROM player\r\n" + "UNION\r\n"
					+ "SELECT high_score, name, date_scored FROM guest\r\n" + "ORDER BY high_score DESC LIMIT 5");
		} catch (Exception e) {
			System.out.println("error in getHighScoreArray ");
		}
		return highScores;
	}

	public ArrayList<String> getNameList() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/yahtzee", "root", "");

			Statement stmt = con.createStatement();

			ResultSet res = stmt.executeQuery("SELECT login_name FROM player");

			nameList = new ArrayList<String>();

			while (res.next()) {
				nameList.add(res.getString(1));
			}
		} catch (Exception e) {
			System.out.println("error in getNameList " + e);
		}
		return nameList;
	}

	public void setGuest(int highscore, String guestName) {
		setData("INSERT INTO `guest`(`name`, `high_score`, `date_scored`) " + " VALUES ('" + guestName + "', '" + highscore
				+ "', CURRENT_TIMESTAMP)");
	}

	public void closeCon() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("error with closeCon" + e);
		}
	}
}
