public class Card
{
	private int suit; // 1 = Hearts, 2 = Diamonds, 3 = Clubs, 4 = Spades
	private int value; // 1 = Ace...11 = Jack, 12 = Queen, 13 = King

	public Card() // Ace of Hearts, by default
	{
		suit = 1;
		value = 1;
	}

	public Card(int s, int v)
	{
		suit = s;
		value = v;
	}

	public int getSuit()
	{
		return suit;
	}

	public int getValue()
	{
		return value;
	}

	public void setSuit(int s)
	{
		suit = s;
	}

	public void setValue(int v)
	{
		value = v;
	}

	public String getName() // returns string, e.g., "Ace of Hearts"
	{
		String name = "";
		if (value == 1)
			name = "ace_of_";
		else if (value == 11)
			name = "jack_of_";
		else if (value == 12)
			name = "queen_of_";
		else if (value == 13)
			name = "king_of_";
		else // use the numerical value
			name = value + "_of_";

		// Add on the suit

		if (suit == 1)
			name += "hearts";
		else if (suit == 2)
			name += "diamonds";
		else if (suit == 3)
			name += "clubs";
		else 
			name += "spades";
		return name;
	}
}