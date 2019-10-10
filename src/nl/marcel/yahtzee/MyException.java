package nl.marcel.yahtzee;

import java.util.ArrayList;

class MyException {

	static boolean isAllTrue(boolean[] checks) {
		for (boolean check : checks) {
			if (!check) {
				return false;
			}
		}
		return true;
	}

	static boolean isAllFalse(boolean[] checks) {
		for (boolean check : checks) {
			if (check) {
				return true;
			}
		}
		return false;
	}
	
	static boolean isPresentInArrayListString(ArrayList<String> checks, String toBeChecked) {
		if (checks.contains(toBeChecked)) {
			return true;
		}
		return false;
	}
}
