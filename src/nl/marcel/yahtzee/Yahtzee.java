package nl.marcel.yahtzee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

public class Yahtzee extends DiceGame {
	private String playerName, diceColor, checkName;
	private boolean guest;
	private JCheckBox[] checkBoxes;
	private boolean[] checkedDice, selectedScore, radioButtonChecked;
	private JRadioButton[] radioButton;
	private JLabel[] scoreLabel;
	private int clicked, scoreWarning, gamesPlayed;
	private int[] diceNumber;
	private MyWindow startWindow, gameWindow;
	private Player player;

	Yahtzee() {
		super("Yahtzee");
		player = new Player();
		startWindow = new MyWindow("Start", "", 0);

		startWindow.b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				checkName = startWindow.tf1.getText();
				System.out.println("name entered: " + checkName);
				if (!checkName.equals("")) {
					if (MyException.isPresentInArrayListString(player.getNameList(), checkName)) {
						game();
					} else {
						startWindow.createExceptionBox("Username does not exist yet");
					}
				} else {
					startWindow.createExceptionBox("Please give a name");
				}
			}
		});
		startWindow.b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				checkName = startWindow.tf1.getText();
				if (!checkName.equals("")) {
					if (!MyException.isPresentInArrayListString(player.getNameList(), checkName)) {
						player.newPlayer(checkName, startWindow.tf2.getText());
						game();
					} else {
						startWindow.createExceptionBox("Username already exists");
					}
				} else {
					startWindow.createExceptionBox("Please give a name");
				}
			}
		});
		startWindow.guestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				checkName = startWindow.tf1.getText();
				guest = true;
				game();
			}
		});
	}

	public void game() {
		if (guest) {
			playerName = "Guest";
			gamesPlayed = 0;
		} else {
			player.loadPlayer(checkName);
			playerName = player.getName("login");
			gamesPlayed = player.getGamesPlayed();
		}
		gameWindow = new MyWindow("Game", playerName, gamesPlayed);
		Dice rollDice = new Dice();
		Scorecard scorecard = new Scorecard();
		Images diceImage = new Images();
		checkedDice = new boolean[] { false, false, false, false, false };
		scoreLabel = MyWindow.actualScores;
		clicked = 0;
		gameWindow.b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				switch (clicked) {
				case 0:
					gameWindow.l3.setText("First roll:");
					break;
				case 1:
					gameWindow.l3.setText("Second roll:");
					break;
				case 2:
					gameWindow.l3.setText("Third roll:");
					gameWindow.b1.setEnabled(false);
					for (int i = 0; i < checkBoxes.length; i++) {
						checkBoxes[i].setEnabled(false);
					}
					break;
				}
				clicked++;

				checkBoxes = MyWindow.checkBoxes;

				// Turn on Save Score button
				gameWindow.b2.setEnabled(true);

				// See if any dice are checked and assign new numbers to all unchecked
				for (int i = 0; i < checkBoxes.length; i++) {
					if (!checkBoxes[i].isSelected()) {
						rollDice.roll(checkedDice);
						break;
					}
				}

				// Get the selected dice color
				for (int i = 0; i < 2; i++) {
					if (MyWindow.colorOptions[i].isSelected()) {
						diceColor = MyWindow.colorOptions[i].getText();
						break;
					} else {
						diceColor = "black";
					}
				}

				// Fill checkboxes with information from the dice
				diceNumber = rollDice.getAllDice();
				String[] diceNumberString = new String[5];
				for (int i = 0; i < 5; i++) {
					diceNumberString[i] = String.valueOf(diceNumber[i]);
				}

				for (int i = 0; i < checkBoxes.length; i++) {
					checkBoxes[i].setIcon(diceImage.getImage(diceColor, diceNumberString[i]));
					checkBoxes[i].setEnabled(true);
					gameWindow.p2.add(checkBoxes[i]);
				}

				gameWindow.l3.setAlignmentX(Component.CENTER_ALIGNMENT);
				gameWindow.l2.setAlignmentX(Component.CENTER_ALIGNMENT);
				gameWindow.p3.add(gameWindow.l3);
				gameWindow.p3.add(gameWindow.l2);
				gameWindow.p3.add(gameWindow.p2);
				gameWindow.p3.add(gameWindow.colorb1);
				gameWindow.p3.add(gameWindow.colorb2);
				gameWindow.p1.add((gameWindow.p3), (BorderLayout.CENTER));

				// See if checkboxes are clicked or not and remember settings
				for (int i = 0; i < checkBoxes.length; i++) {
					int j = i;
					checkBoxes[i].addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							gameWindow.l2.setText("Dice: " + (e.getStateChange() == 1 ? "checked" : "unchecked"));
							if (checkBoxes[j].isSelected()) {
								checkedDice[j] = true;
								checkBoxes[j].setBackground(Color.YELLOW);
							} else {
								checkedDice[j] = false;
								checkBoxes[j].setBackground(null);
							}
						}

					});
				}
				gameWindow.revalidate();
			}
		});

		radioButton = MyWindow.radioButtons;
		radioButtonChecked = new boolean[radioButton.length];

		// Set warning for 0 score
		scoreWarning = 0;

		gameWindow.b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				selectedScore = new boolean[radioButton.length];

				// Fill array with radiobutton selection
				for (int i = 0; i < radioButton.length; i++) {
					selectedScore[i] = radioButton[i].isSelected();
				}

				int[] total = rollDice.getAllDice();
				for (int i = 0; i < 5; i++) {
				}

				// Application stops when no score is selected
				if (!MyException.isAllFalse(selectedScore)) {
					gameWindow.createExceptionBox("Please select scorefield to be added");
					gameWindow.f1.setSize(300, 80);
					// The else is only applicable when a score is selected
				} else {

					// Turn off Save Score button
					gameWindow.b2.setEnabled(false);

					// Get the selected scoring box
					for (int i = 0; i < radioButton.length; i++) {
						if (selectedScore[i]) {
							radioButton[i].setEnabled(false);
							radioButtonChecked[i] = true;
							gameWindow.bg1.clearSelection();
							radioButton[i].setSelected(false);
						}
					}

					// Calculate the score
					int score = scorecard.calculateScore(selectedScore, total);

					// Warn if score is 0
					if (score != 0 || scoreWarning == 1) {
						gameWindow.b1.setEnabled(true);

						if (gameWindow.yes.isSelected()) {

							for (int i = 0; i < scoreLabel.length; i++) {
								if (selectedScore[i] == true) {
									scoreLabel[i].setText(String.valueOf(score));
								}
							}

							// Set score to correct label and disable radiobutton
							scorecard.setScore(scoreLabel);

							// If all scores filled out, give score
							if (MyException.isAllTrue(radioButtonChecked)) {
								System.out.println("game ended");
								String[] finalScore = scorecard.getScore();
								int[] finalInt = new int[finalScore.length];
								int actualScore = 0;
								for (int i = 0; i < finalScore.length; i++) {
									finalInt[i] = Integer.parseInt(finalScore[i]);
									actualScore = actualScore + finalInt[i];
								}
								gameWindow.createExceptionBox("Winner! Your score is: " + actualScore);
								startWindow.p1.remove(startWindow.hs1);
								if (guest) {
									int actualScore2 = actualScore;
									gameWindow.b3.setText("Submit");
									gameWindow.tfGuest.setText("Please give name");
									gameWindow.p4.add(gameWindow.tfGuest);
									gameWindow.p4.add(gameWindow.b3);
									startWindow.hs1 = startWindow.highScorePanel();
									startWindow.p1.add(startWindow.hs1, BorderLayout.EAST);
									gameWindow.b3.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent event) {
											player.setGuest(actualScore2, gameWindow.tfGuest.getText());
											gameWindow.f1.dispose();
											gameWindow.dispose();
										}
									});
								} else {
									player.setPlayer(playerName, actualScore);
									startWindow.hs1 = startWindow.highScorePanel();
									startWindow.p1.add(startWindow.hs1, BorderLayout.EAST);
									gameWindow.f1.setTitle("Game Ended");
									gameWindow.p4.add(gameWindow.b3);
									gameWindow.b3.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent event) {
											gameWindow.f1.dispose();
											gameWindow.dispose();
										}

									});
								}
								guest = false;
							}

							// Reset checkboxes
							for (int i = 0; i < checkBoxes.length; i++) {
								checkBoxes[i].setEnabled(false);
								checkBoxes[i].setSelected(false);
								checkBoxes[i].setIcon(null);
								gameWindow.p2.add(checkBoxes[i]);
								gameWindow.p2.add(checkBoxes[i]);
							}

							// Reset dice
							for (int i = 0; i < diceNumber.length; i++) {
								diceNumber[i] = 0;
							}

							// Set labels as new
							gameWindow.l3.setText("Roll the dice!");
							gameWindow.l2.setText("Good luck!");

							clicked = 0;
							scoreWarning = 0;
						}
					} else {
						gameWindow.yes.setSelected(false);
						gameWindow.createExceptionBox("This score will be 0, are you sure?");
						gameWindow.exceptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
						gameWindow.pyesno.setAlignmentX(Component.CENTER_ALIGNMENT);
						gameWindow.p4.add(gameWindow.pyesno);
						gameWindow.no.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								gameWindow.b2.setEnabled(true);
								for (int i = 0; i < radioButton.length; i++) {
									if (selectedScore[i]) {
										radioButton[i].setEnabled(true);
									}
								}
								gameWindow.f1.dispose();
							}
						});
						gameWindow.yes.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								scoreWarning = 1;
								gameWindow.b2.setEnabled(true);
								for (int i = 0; i < radioButton.length; i++) {
									if (selectedScore[i]) {
										radioButton[i].setSelected(true);
										radioButton[i].setEnabled(true);
									}
								}
								gameWindow.f1.dispose();
							}
						});
					}
				}
			}
		});
	}
}
