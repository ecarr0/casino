import java.util.ArrayList;

public class Dealer{

	private ArrayList<Card> hand = new ArrayList<Card>();
	
	public Dealer(Card card1, Card card2) {
		this.hand.add(card1);
		this.hand.add(card2);
		sortHand();
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
	
	public String printHand() {
		String printHand = "{ ";
		for(int i = 0; i < hand.size() - 1; i++) {
			printHand += this.hand.get(i).toString() + ", ";
		}
		printHand += this.hand.get(hand.size() - 1).toString() + "}";
		return printHand;
	}
	
	public int calcPoints() {
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
}