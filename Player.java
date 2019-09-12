import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Player extends JFrame
{
	private JLabel resultLabel; // label displays the type of hand and the payout
	private JLabel[] cardLabel; // an array of 5 labels that display card images
	private JButton[] holdButton; // click to keep a particular card
	private JButton add1Button; // add 1 coin
	private JButton add5Button; // clicking adds 5 coins;
	private JLabel bankrollLabel; // label that displays the current number of coins
	private JButton quitButton; // exit the application
	private JButton dealButton; // click to display the updated hand
	private JButton[] betAndPlayButton; // clicking makes a bet and begins play

	private Bankroll bankroll;
	private PokerGame pokerGame;
	private Bet bet;
	private Hand hand;

	public Player() // constructor
	{
		super("Video Poker");
		bet = new Bet();
		bankroll = new Bankroll();
		setBounds(100,100,800,600);

		// the label places at NORTH area of the frame
		resultLabel = new JLabel();
		resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
		resultLabel.setText("Video Poker");

		// Display five card images, the initial image is "Back.gif" - a dummy card
		cardLabel = new JLabel[5];
		for (int i = 0; i < 5; i++)
		{
			ImageIcon imageIcon = new ImageIcon("Cards/Back.png"); // load the image to a imageIcon
			Image image = imageIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageIcon = new ImageIcon(newimg);  // transform it back
			cardLabel[i] = new JLabel(imageIcon);
		}

		// the five hold/discard buttons
		holdButton = new JButton[5];
		for (int  i = 0; i < 5; i++)
		{
			holdButton[i] = new JButton("" + (i + 1)); // initially display numbers 1 - 5
			holdButton[i].setFont(new Font("Arial", Font.BOLD, 18));
			holdButton[i].setEnabled(false);
		}

		// the five "bet and play" buttons
		betAndPlayButton = new JButton[5];
		for (int i = 0; i < 5; i++)
		{
			betAndPlayButton[i] = new JButton("Bet " + (i + 1)); // display Bet 1, Bet 2,..Bet5
			betAndPlayButton[i].setEnabled(false); // initially turned off
			betAndPlayButton[i].setFont(new Font("Arial", Font.BOLD, 15));
		}

		// the deal initially turned off
		dealButton = new JButton("Deal");
		dealButton.setFont(new Font("Arial", Font.BOLD, 18));
		dealButton.setEnabled(false);

		// the quit button
		quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Arial", Font.BOLD, 15));

		// label that displays current number of coins, i.e., the "bankroll"
		bankrollLabel = new JLabel();
		bankrollLabel.setFont(new Font("Arial", Font.BOLD, 24));
		bankrollLabel.setText("Coins remaining: " + 0); // initially no coins

		// two buttons that either add 1 or 5 coins to the machine
		add1Button = new JButton("Add 1");
		add5Button = new JButton("Add 5");
		add1Button.setFont(new Font("Arial", Font.BOLD, 15));
		add5Button.setFont(new Font("Arial", Font.BOLD, 15));

		// panel holds bet buttons, card labels, hold buttons, deposit buttons, deal and quit
		JPanel centerPanel = new JPanel(new GridLayout(4,5));

		// add the five bet buttons
		for (int i = 0; i < 5; i++)
			centerPanel.add(betAndPlayButton[i]);

		// add the five labels that display the card images
		for (int i = 0; i < 5; i++)
			centerPanel.add(cardLabel[i]);

		// add the five hold buttons
		for (int i = 0; i < 5; i++)
			centerPanel.add(holdButton[i]);

		// add the two deposit buttons, a blank button, the deal and quit buttons
		centerPanel.add(add1Button);
		centerPanel.add(add5Button);
		JButton sp = new JButton();
		sp.setEnabled(false);
		centerPanel.add(sp); // a blank button as a separator
		centerPanel.add(dealButton);
		centerPanel.add(quitButton);

		// add the label that displays the game results to the NORTH section of the frame
		add(resultLabel, BorderLayout.NORTH);
		// add the label that displays the coin to count to the south section of the frame
		add(bankrollLabel, BorderLayout.SOUTH);

		// add the panel that holds the button and cards labels to the CENTER section of the frame
		add(centerPanel, BorderLayout.CENTER);

		// register listeners, one inner class does all listening
		add1Button.addActionListener(new ButtonListener());
		add5Button.addActionListener(new ButtonListener());
		dealButton.addActionListener(new ButtonListener());
		quitButton.addActionListener(new ButtonListener());

		for (int i = 0; i < 5; i++)
			betAndPlayButton[i].addActionListener(new ButtonListener());
		for (int i = 0; i < 5; i++)
			holdButton[i].addActionListener(new ButtonListener());
		setResizable(false);
		setVisible(true);
	}

	public void displayHand(Hand hand) // displays images of five cards
	{
		String[] handString = hand.getHand();
		for (int i = 0; i < 5; i++)
		{
			String name = "Cards/" + handString[i] + ".png"; // name is a file name.
			ImageIcon imageIcon = new ImageIcon(name); // load the image to a imageIcon
			Image image = imageIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageIcon = new ImageIcon(newimg);  // transform it back
			cardLabel[i].setIcon(imageIcon);
		}
	}

	public void getDiscard(boolean[] holdCards) // maintains hold/discard information
	{
		for (int i = 0; i < 5; i++)
		{
			if (holdButton[i].isEnabled())
				holdCards[i] = false; // card is discarded
			else
				holdCards[i] = true; // card is retained
		}
	}

	public void displayResults(int payoff, int winnings) // displays the final outcome on a label
	{
		String nameOfHand = "Lose";
		if (payoff == 250)
			nameOfHand = "Royal Flush";
		else if (payoff == 50)
			nameOfHand = "Straight Flush";
		else if (payoff == 25)
			nameOfHand = "Four of a kind";
		else if (payoff == 9)
			nameOfHand = "Full House";
		else if (payoff == 6)
			nameOfHand = "Flush";
		else if (payoff == 4)
			nameOfHand = "Straight";
		else if (payoff == 3)
			nameOfHand = "Three of a kind";
		else if (payoff == 2)
			nameOfHand = "Two Pair";
		else if (payoff == 1)
			nameOfHand = "Pair of Jacks or Better";

		if (winnings > 0) // display outcome on resultLabel
			resultLabel.setText("Winner: " + nameOfHand + " - pays " + winnings);
		else
			resultLabel.setText("You lost your bet of " + bet.getBet());

		bankrollLabel.setText("Coins remaining: " + bankroll.getBankroll());
	}

	private class ButtonListener implements ActionListener // respond to button events
	{
		public void actionPerformed(ActionEvent e)
		{
			if((e.getSource() == add1Button) || (e.getSource() == add5Button)) // click Add 1/Add 5
			{
				if (e.getSource() == add1Button)
					bankroll.alterBankroll(1);
				else
					bankroll.alterBankroll(5);

				int br = bankroll.getBankroll();
				bankrollLabel.setText("Coins remaining: " + br);
				for (int i = 0; i < 5; i++)
					if (br >= (i + 1))
						betAndPlayButton[i].setEnabled(true);
				return;
			}
			if (e.getSource() == quitButton) // click the Quit button
				System.exit(0);

			for (int i = 0; i < 5; i++) // click one of the five bet buttons
				if (e.getSource() == betAndPlayButton[i])
				{
					bet = new Bet();
					bet.setBet(i + 1);
					resultLabel.setText("Bet is " + (i + 1));
					pokerGame = new PokerGame(bet, bankroll, Player.this);
					pokerGame.viewInitialHand();

					for (int j = 0; j < 5; j++)
					{
						holdButton[j].setText("" + (j + 1));
						holdButton[j].setEnabled(true);
					}

					// enable and disable other buttons
					add1Button.setEnabled(false);
					add5Button.setEnabled(false);
					quitButton.setEnabled(false);
					dealButton.setEnabled(true);
					for (int j = 0; j < 5; j++)
						betAndPlayButton[j].setEnabled(false);
					return;
				}

			for (int i = 0; i < 5; i++) // respond to a hold button event
				if (e.getSource() == holdButton[i])
				{
					holdButton[i].setText("Hold");
					holdButton[i].setEnabled(false);
					return;
				}

			if (e.getSource() == dealButton) // respond to a Deal button event
			{
				pokerGame.discardOrHoldCards();
				dealButton.setEnabled(false);
				for (int j = 0; j < 5; j++)
					holdButton[j].setEnabled(false);

				for (int i = 0; i < 5; i++)
					if (bankroll.getBankroll() >= (i + 1)) // enough coins ?
						betAndPlayButton[i].setEnabled(true);

				add1Button.setEnabled(true);
				add5Button.setEnabled(true);
				quitButton.setEnabled(true);
			}
		}
	}

	public static void main(String[] args)
	{
		Player pm = new Player();
	}
}