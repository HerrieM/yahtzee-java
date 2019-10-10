package nl.marcel.yahtzee;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;

public class Scorecard {
	private String[] scorecard;
	private String threeOfKind = "1{3}..|2{3}..|(.+2{3}.+)|3{3}.+|.3{3}.|..3{3}|..4{3}|.4{3}.|4{3}..|..5{3}|.5{3}.|5{3}..|..6{3}";
	private String fourOfKind = "1{4}.|.2{4}|2{4}.|.3{4}|3{4}.|.4{4}|4{4}.|.5{4}|5{4}.|.6{4}";
	private String fullHouse = "(1{2}2{3})|(1{2}3{3})|(1{2}4{3})|(1{2}5{3})|(1{2}6{3})|(1{3}2{2})|(1{3}3{2})|(1{3}4{2})|(1{3}5{2})|(1{3}6{2})|(2{2}3{3})|(2{2}4{3})|(2{2}5{3})|(2{2}6{3})|(2{3}3{2})|(2{3}4{2})|(2{3}5{2})|(2{3}6{2})|(3{2}4{3})|(3{2}5{3})|(3{2}6{3})|(3{3}4{2})|(3{3}5{2})|(3{3}6{2})|(4{2}5{3})|(4{2}6{3})|(4{3}5{2})|(4{3}6{2})|(5{2}6{3})|(5{3}6{2})";
	private String smallStreet = "11234|1234.|.2345|2345.|.3456|34566|12.34|23.45|34.56";
	private String bigStreet = "12345|23456";
	private String yahtzee = "1{5}|2{5}|3{5}|4{5}|5{5}|6{5}";
	private String chance = "[1-6]{5}";
	private String[] scores2 = new String[] { threeOfKind, fourOfKind, fullHouse, smallStreet, bigStreet, yahtzee,
			chance };

	// The scorecard calculations and checks are performed here
	Scorecard() {
		scorecard = new String[13];
		for (int i = 0; i < scorecard.length; i++) {
			scorecard[i] = "0";
		}
	}

	// Get input as dice array + the selected choice of points
	public int calculateScore(boolean[] selectedScore, int[] tempArray) {
		int score = 0;
		// Set totals to String for regEx
		int[]  totalCheck = new int[5];
		for (int i = 0; i < 5; i++) {
			totalCheck[i] = tempArray[i];
		}
		Arrays.sort(totalCheck);
		String totalString = Arrays.toString(totalCheck);
		totalString = totalString.replaceAll("\\p{P}", "");
		totalString = totalString.replaceAll(" ", "");
		// Start by checking for 1-6 as option
		for (int i = 0; i < scorecard.length; i++) {
			if (i < 6) {
				if (selectedScore[i] == true) {
					int valueSelected = i + 1;
					for (int e : totalCheck) {
						if (e == valueSelected) {
							score = (score + e);
						}
					}
				}
				// Next check for the rest of the options
			} else if (i >= 6 && i < 13) {
				if (selectedScore[i] == true) {
					int j = i - 6;
					if (regEx(totalString, j) == false) {
						score = 0;
						break;
					}
					switch (j) {
					case 0: // Three of a kind
					case 1: // Four of a kind
					case 6: // Chance
						for (int e : totalCheck) {
							score = score + e;
						}
						break;
					case 2: // Full House
						score = 25;
						break;
					case 3: // Small Street
						score = 30;
						break;
					case 4: // Big Street
						score = 35;
						break;
					case 5: // Yahtzee
						score = 50;
						break;
					}
				}

			}
		}
		// Return the actual score
		return score;
	}

	// Method for regEx
	public final boolean regEx(String toCheck, int j) {
		Pattern p = Pattern.compile(scores2[j]);
		Matcher m = p.matcher(toCheck);
		return m.matches();
	}

	// Set score based on actual scores
	public void setScore(JLabel[] actualScores) {
		for (int i = 0; i < scorecard.length; i++) {
			scorecard[i] = actualScores[i].getText();
			;
		}
	}

	public String[] getScore() {
		return scorecard;
	}

}
