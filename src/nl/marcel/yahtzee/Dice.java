package nl.marcel.yahtzee;

public class Dice {

	private int rolledDice;
	private int[] allDice = new int[] { 0, 0, 0, 0, 0 };

	// Roll a new dice and switch number in right position
	public int roll(boolean[] checks) {
		for (int i = 0; i < 5; i++) {
			if (checks[i] == false) {
				rolledDice = ((int) (Math.random() * 6 + 1));
				allDice[i] = rolledDice;
			}
		}
		return rolledDice;
	}

	// Return all dice in array
	public int[] getAllDice() {
		return allDice;
	}
}
