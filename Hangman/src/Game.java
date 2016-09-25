import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JFrame implements ActionListener {

	Font bigFont = new Font("Sans-Serif", Font.ROMAN_BASELINE, 80);
	Font titleFont = new Font("Serif", Font.BOLD, 90);

	Container c = getContentPane();

	JLabel lblTitle = new JLabel("HANGMAN", JLabel.CENTER);

	JPanel pnlLetters = new JPanel(new GridLayout(3, 0, 10, 10));
	JButton[] btnsLetters = new JButton[26];

	JPanel pnlMain = new JPanel(new GridBagLayout());
	GridBagConstraints gc = new GridBagConstraints();
	JLabel lblHiddenWord = new JLabel("");
	JLabel lblLives = new JLabel("Lives left: " + Player.lives);

	JMenuBar menuBar = new JMenuBar();
	JMenu mnOptions = new JMenu("Options");
	JMenu mnDifficulty = new JMenu("Change Difficulty");
	JMenuItem[] mntmDiff = new JMenuItem[5];

	JMenuItem mntmNewGame = new JMenuItem("New Game");

	GPanel pnlDraw = new GPanel();

	String sDashes = "";
	Word mystery = new Word();
	int iDifficulty = 1;

	Game() {
		super("Hangman Game");
		setSize(750, 1000);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		c.add("South", pnlLetters);
		for (int i = 0; i < btnsLetters.length; i++) {
			btnsLetters[i] = new JButton(Character.toString((char) (i + 65)));
			btnsLetters[i].addActionListener(this);
			pnlLetters.add(btnsLetters[i]);
		}

		lblTitle.setFont(titleFont);
		c.add("North", lblTitle);
		c.add("Center", pnlMain);

		gc.weightx = 10;
		gc.weighty = 10;

		gc.gridy = 0;
		pnlMain.add(lblLives, gc);

		gc.gridy = 1;
		pnlMain.add(pnlDraw, gc);

		for (int i = 0; i < mystery.iWordLength; i++) {
			mystery.cGuesses[i] = '-';
		}

		writeGuess();
		lblHiddenWord.setFont(bigFont);

		gc.gridy = 2;
		pnlMain.add(lblHiddenWord, gc);

		setJMenuBar(menuBar);

		menuBar.add(mnOptions);

		mnOptions.add(mnDifficulty);

		for (int i = 0; i < mntmDiff.length; i++) {
			String s = "";
			switch (i) {
			case 0:
				s = "Kiddie";
				break;
			case 1:
				s = "Easy";
				break;
			case 2:
				s = "Medium";
				break;
			case 3:
				s = "Hard";
				break;
			case 4:
				s = "Elite";
				break;
			}

			mntmDiff[i] = new JMenuItem(s);
			mntmDiff[i].addActionListener(this);
			mnDifficulty.add(mntmDiff[i]);

		}

		mnOptions.add(mntmNewGame);
		mntmNewGame.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < btnsLetters.length; i++) {
			if (e.getSource() == btnsLetters[i]) {

				btnsLetters[i].setEnabled(false);
				boolean correct = false;
				for (int j = 0; j < mystery.iWordLength; j++) {
					if (mystery.sWord.charAt(j) == ((char) (i + 65))
							|| mystery.sWord.charAt(j) == ((char) (i + 97))) {

						mystery.cGuesses[j] = ((char) (i + 65));
						writeGuess();

						correct = true;
						check();
					}

				}
				if (!correct) {
					Player.lives--;
					updateLives();
					pnlDraw.repaint();
					if (Player.lives == 0) {

						JOptionPane.showInternalMessageDialog(c,
								"You have ran out of lives and thus lost. The word was: "
										+ mystery.sWord);
						enableAllButtons(false);
						if ((JOptionPane.showConfirmDialog(c,
								"Would you like to guess another word?") == JOptionPane.YES_OPTION)) {

							newGame();

						}
					}
				}

			}
		}

		for (int i = 0; i < mntmDiff.length; i++) {
			if (e.getSource() == mntmDiff[i]) {

				boolean noLetters = true;

				for (int j = 0; j < mystery.iWordLength; j++) {
					if (mystery.cGuesses[j] != '-') {
						noLetters = false;
					}
				}

				if (noLetters) {
					if ((JOptionPane.showConfirmDialog(c, "Are you sure?") == JOptionPane.YES_OPTION)) {
						iDifficulty = i;
						setDifficulty();
						updateLives();
						pnlDraw.repaint();

					}
				} else {
					if ((JOptionPane
							.showConfirmDialog(c,
									"Are you sure? Changes will take effect from next game.") == JOptionPane.YES_OPTION)) {
						iDifficulty = i;
					}
				}
			}
		}

		if (e.getSource() == mntmNewGame) {

			if ((JOptionPane.showConfirmDialog(c, "Are you sure?") == JOptionPane.YES_OPTION)) {
				newGame();
			}

		}
	}

	void writeGuess() {
		String s = "";

		for (int i = 0; i < mystery.iWordLength; i++) {
			s += mystery.cGuesses[i];
		}

		lblHiddenWord.setText(s);

	}

	void check() {
		int counter = 0;

		for (int i = 0; i < mystery.iWordLength; i++) {
			if (mystery.cGuesses[i] != '-') {
				counter++;
			}
		}

		if (counter == mystery.iWordLength) {
			JOptionPane.showInternalMessageDialog(c,
					"You won! Congratulations! The word was: " + mystery.sWord);
			enableAllButtons(false);
			if ((JOptionPane.showConfirmDialog(c,
					"Would you like to guess another word?") == JOptionPane.YES_OPTION)) {

				newGame();

			}

		}
	}

	void enableAllButtons(boolean b) {
		for (int i = 0; i < btnsLetters.length; i++) {
			btnsLetters[i].setEnabled(b);
		}
	}

	void newGame() {
		setDifficulty();
		Random r = new Random();
		mystery.sWord = Word.sWordBank[r.nextInt(Word.sWordBank.length)];
		mystery.iWordLength = mystery.sWord.length();
		mystery.cGuesses = new char[mystery.iWordLength];
		for (int i = 0; i < mystery.iWordLength; i++) {
			mystery.cGuesses[i] = '-';
		}
		writeGuess();
		enableAllButtons(true);
		updateLives();
		pnlDraw.repaint();
	}

	void setDifficulty() {
		switch (iDifficulty) {
		case 0:
			Player.lives = 20;
			break;
		case 1:
			Player.lives = 11;
			break;
		case 2:
			Player.lives = 6;
			break;
		case 3:
			Player.lives = 3;
			break;
		case 4:
			Player.lives = 1;
			break;
		}

	}

	void updateLives() {
		lblLives.setText("Lives left: " + Player.lives);
	}

	public static void main(String[] args){

		new Game();

	}

}
