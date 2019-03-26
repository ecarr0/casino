import java.util.ArrayList;

public class User{
	
	private String name;
	private int chips;
	private int bet;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private int points;
	
	public User(String name, int chips) {
		this.name = name;
		this.chips = chips;
		
		points = getPoints();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setBet(int bet) {
		this.bet = bet;
	}
	
	public int getBet() {
		return this.bet;
	}
	
	public int getChips() {
		return this.chips;
	}
	
	public void setChips(int chips) {
		this.chips = chips;
	}
	
	public int getPoints() {
		int sum = 0;
		for(int i = 0; i < this.hand.size(); i++) {
			sum += this.hand.get(i).pointValue();
		}
		for(int i = 0; i < this.hand.size(); i++) {
			if(this.hand.get(i).rank().equalsIgnoreCase("ace")) {
				sum -= this.hand.get(i).pointValue();
				this.hand.get(i).changeValue(sum - 1);
				sum += this.hand.get(i).pointValue();
			}
		}
		return sum;
	}
	
	public void addCard(Card card) {
		this.hand.add(card);
	}
	
	public void setCard(Card card, int i) {
		this.hand.set(i, card);
	}
	
	public Card getCard(int i) {
		return this.hand.get(i);
	}

	public void sortHand() {
		boolean sorted = false;
		do {
			sorted = true;
			for(int i = 1; i < this.hand.size(); i++) {
				if(hand.get(i).compareTo(hand.get(i - 1)) < 0) {
					sorted = false;
					hand.set(i, hand.set(i - 1, hand.get(i)));
				}
			}
		}while(!sorted);
	}
	
	public void deleteHand() {
		hand = new ArrayList<Card>();
	}
	
	public String printHand() {
		String printHand = "{ ";
		for(int i = 0; i < hand.size() - 1; i++) {
			printHand += this.hand.get(i).toString() + ", ";
		}
		printHand += this.hand.get(hand.size() - 1).toString() + "}";
		return printHand;
	}
}