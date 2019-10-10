package nl.marcel.yahtzee;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
class MyWindow extends JFrame {

	JButton b1, b2, b3, guestButton;
	JCheckBox cb1, cb2, cb3, cb4, cb5;
	JFrame f1;
	JLabel namel1, gamesPlayedLabel, l1, l2, l3, exceptionLabel, guestLabel;
	JLabel title1, title2;
	JLabel scoreName1, scoreName2, scoreName3, scoreName4, scoreName5;
	JLabel score1, score2, score3, score4, score5;
	JLabel date1, date2, date3, date4, date5;
	JPanel p1, p2, p3, p4, pyesno, bp1, scorecard, hs1, loginPanel, guestPanel, playerPanel;
	JRadioButton sr1, sr2, sr3, sr4, sr5, sr6, sr7, sr8, sr9, sr10, sr11, sr12, sr13;
	JRadioButton colorb1, colorb2, yes, no;
	JTextField tf1, tf2, tfGuest;
	JTextArea ta1, ta2, ta3;
	JSeparator scoreSep1, scoreSep2, scoreSep3, scoreSep4, scoreSep5, scoreSep6;
	JSeparator[] scoreSepArray;
	static JCheckBox[] checkBoxes;
	static JRadioButton[] radioButtons, colorOptions;
	static JLabel[] actualScores, scoreNames, highScores, scoreDates;
	String[] possibleScores;
	ButtonGroup bg1, bgcolor, bgyesno;

	// Build main frame with option of start, game and highscore (highscore still
	// needs to be build)
	MyWindow(String type, String name, int gamesPlayed) {
		this.setTitle("Yahtzee");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		if (type.equals("Start")) {
			start();
		} else if (type.equals("Game")) {
			game(name, gamesPlayed);
		} else if (type.equals("HighScore")) {
			highScore();
		}

		this.add(p1);
		this.setSize(600, 600);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	// Build frame with name field and start button
	private JPanel start() {
		p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		
		l1 = new JLabel("Welcome to Yahtzee!");

		loginPanel = new JPanel(); // Panel for buttons
		loginPanel.setLayout(new GridBagLayout());
		l2 = new JLabel("Username: ");
		l3 = new JLabel("Password");
		tf1 = new JTextField("Please Give Name");
		tf2 = new JTextField("Please Give Password");
		b1 = new JButton("Login");
		b2 = new JButton("Create New User and Login");
		addItem(loginPanel, l2, 0, 1, 1, 1, GridBagConstraints.EAST);
		addItem(loginPanel, tf1, 1, 1, 2, 1, GridBagConstraints.WEST);
		addItem(loginPanel, l3, 0, 2, 1, 1, GridBagConstraints.EAST);
		addItem(loginPanel, tf2, 1, 2, 2, 1, GridBagConstraints.WEST);
		addItem(loginPanel, b1, 0, 3, 1, 1, GridBagConstraints.EAST);
		addItem(loginPanel, b2, 1, 3, 1, 1, GridBagConstraints.WEST);
		
		guestPanel = new JPanel(); // Panel to play as guest
		guestPanel.setLayout(new BoxLayout(guestPanel, BoxLayout.PAGE_AXIS));
		guestLabel = new JLabel("Welcome to Yahtzee, click here to play as guest:");
		guestButton = new JButton("Play!");
		guestLabel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		guestButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		guestPanel.add(guestLabel);
		guestPanel.add(guestButton);
		
		p1.add(l1, BorderLayout.NORTH);
		p1.add(guestPanel, BorderLayout.CENTER);
		p1.add(loginPanel, BorderLayout.SOUTH);
		hs1 = new JPanel();
		hs1 = highScorePanel();
		p1.add(hs1, BorderLayout.EAST);
		return p1;
	}

	// Build frame with game layout
	private JPanel game(String name, int gamesPlayed) {
		yes = new JRadioButton("Yes");
		yes.setSelected(true);
		p1 = new JPanel();
		p1.setLayout(new BorderLayout(5, 5));
		playerPanel = new JPanel();
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));
		p2 = new JPanel();
		p2.setLayout(new FlowLayout());

		// Create checkboxes for the dice
		checkBoxes = new JCheckBox[5];
		for (int i = 0; i < checkBoxes.length; i++) {
			checkBoxes[i] = new JCheckBox();
		}

		p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.PAGE_AXIS));

		// Create radiobuttons for colors
		bgcolor = new ButtonGroup();
		colorb1 = new JRadioButton("Red");
		colorb2 = new JRadioButton("Black");
		colorOptions = new JRadioButton[] { colorb1, colorb2 };
		bgcolor.add(colorb1);
		bgcolor.add(colorb2);

		// Create the labels for the frame
		l1 = new JLabel("Yahtzee!");
		l2 = new JLabel("Select the once you want to keep");
		l3 = new JLabel("Roll the dice!");
		namel1 = new JLabel(name);
		gamesPlayedLabel = new JLabel("Played: " + gamesPlayed);
		playerPanel.add(namel1);
		playerPanel.add(gamesPlayedLabel);

		// Create the scorecard
		scorecard();

		// Add the buttons
		bp1 = new JPanel();
		b1 = new JButton("Roll Dice");
		b2 = new JButton("Save Score");
		b2.setEnabled(false);
		bp1.add(b1);
		bp1.add(b2);

		p1.add(l1, BorderLayout.NORTH);
		p1.add(playerPanel, BorderLayout.WEST);
		p1.add(scorecard, BorderLayout.EAST);
		p1.add(bp1, BorderLayout.SOUTH);
		return p1;
	}

	// Score card is build and added to main panel
	private JPanel scorecard() {
		scorecard = new JPanel();
		scorecard.setLayout(new GridBagLayout());

		// All buttons and scorefields are created
		radioButtons = new JRadioButton[] { sr1, sr2, sr3, sr4, sr5, sr6, sr7, sr8, sr9, sr10, sr11, sr12, sr13 };
		possibleScores = new String[] { "1", "2", "3", "4", "5", "6", "3 of a kind", "4 of a kind", "Full House",
				"Kleine straat", "Grote Straat", "Yahtzee", "Chance" };
		actualScores = new JLabel[possibleScores.length];

		title1 = new JLabel("Possible");
		addItem(scorecard, title1, 0, 0, 1, 1, GridBagConstraints.WEST);

		title2 = new JLabel("Actual");
		addItem(scorecard, title2, 1, 0, 1, 1, GridBagConstraints.WEST);

		bg1 = new ButtonGroup();

		// Buttons and scorefields are added
		for (int i = 0; i < radioButtons.length; i++) {
			radioButtons[i] = new JRadioButton(possibleScores[i]);
			bg1.add(radioButtons[i]);
			addItem(scorecard, radioButtons[i], 0, i + 1, 1, 1, GridBagConstraints.WEST);
			actualScores[i] = new JLabel("-");
			addItem(scorecard, actualScores[i], 1, i + 1, 1, 1, GridBagConstraints.EAST);
		}

		return p1;
	}

	private void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = x;
		gc.gridy = y;
		gc.gridwidth = width;
		gc.gridheight = height;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = align;
		gc.fill = GridBagConstraints.NONE;
		p.add(c, gc);
	}

	public JFrame createExceptionBox(String exception) {
		f1 = new JFrame();
		f1.setTitle("Exception");
		f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		tfGuest = new JTextField();

		p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.PAGE_AXIS));
		exceptionLabel = new JLabel(exception);
		b3 = new JButton("New Game");

		pyesno = new JPanel();
		pyesno.setLayout(new BoxLayout(pyesno, BoxLayout.LINE_AXIS));
		bgyesno = new ButtonGroup();
		no = new JRadioButton("No");
		yes = new JRadioButton("Yes");
		bgyesno.add(yes);
		bgyesno.add(no);
		pyesno.add(yes);
		pyesno.add(no);

		p4.add(exceptionLabel);
		f1.add(p4);
		f1.setSize(500, 100);
		f1.setVisible(true);
		f1.setLocationRelativeTo(null);
		return f1;
	}

	public JPanel highScorePanel() {
		Player hsp = new Player();
		hsp.loadHighScore();
		hs1 = new JPanel();
		hs1.setLayout(new BoxLayout(hs1, BoxLayout.PAGE_AXIS));
		hs1.add(new JLabel("Highscores:"));
		hs1.add(scoreSep6 = new JSeparator());
		scoreSepArray = new JSeparator[] { scoreSep1, scoreSep2, scoreSep3, scoreSep4, scoreSep5 };
		scoreNames = new JLabel[] { scoreName1, scoreName2, scoreName3, scoreName4, scoreName5 };
		highScores = new JLabel[] { score1, score2, score3, score4, score5 };
		scoreDates = new JLabel[] { date1, date2, date3, date4, date5 };
		for (int i = 0; i < 5; i++) {
			scoreNames[i] = new JLabel(Player.names[i]);
			highScores[i] = new JLabel(String.valueOf(Player.highScore[i]));
			scoreDates[i] = new JLabel(String.valueOf(Player.dates[i]));
			hs1.add(scoreNames[i]);
			hs1.add(highScores[i]);
			hs1.add(scoreDates[i]);
			hs1.add(scoreSepArray[i] = new JSeparator ());
		}
		return hs1;
	}
	
	private JFrame highScore() {
		return f1;		
	}

}
