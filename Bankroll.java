public class Bankroll
{
	private int bankroll;

	public Bankroll() // default constructor
	{
		bankroll = 0;
	}

	public Bankroll(int n) // one-argument construtor
	{
		bankroll = n;
	}

	public int getBankroll()
	{
		return bankroll;
	}

	public void alterBankroll(int n) // n can be negative
	{
		bankroll += n;
	}
}