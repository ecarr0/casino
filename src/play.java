import java.util.Scanner;

public class play{
	private Deck deck;
	
	private User user;
	private Dealer dealer;
	
	private Scanner in;
	
	private static final String[] RANKS =
		{"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

	private static final String[] SUITS =
		{"spades", "hearts", "diamonds", "clubs"};

	private static final int[] POINT_VALUES =
		{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
	 
	
	public play(User user) throws InterruptedException {
		deck = new Deck(RANKS, SUITS, POINT_VALUES);
		this.user = user;
		this.in = new Scanner(System.in);
		
		boolean done = false;
		do {
			deck = new Deck(RANKS, SUITS, POINT_VALUES);
			user.deleteHand();
		
			user.addCard(deck.deal());
			user.addCard(deck.deal());
			this.dealer = new Dealer(deck.deal(), deck.deal());
			if(user.getChips() > 0) {
				done = runGame(true);
			}
			else {
				System.out.println("Whoah there! You have no more money to bet! Good bye!");
				done = true;
			}
		}while(!done);
	}
	
	public boolean runGame(boolean notOver) throws InterruptedException {
		int userPoints = user.getPoints();
		boolean validType = false;
		int bet = 0;
		String stringBet = "";
		do {
			System.out.println("How many chips would you like to bet this game?");
			try {
				stringBet = in.nextLine();
				bet = Integer.parseInt(stringBet);
				validType = true;
				if(bet > user.getChips() || bet <= 0) {
					System.out.println("Invalid amount. Try again.");
					validType = false;
				}
			}catch(NumberFormatException e) {
				System.out.println("Invalid amount. Try again.");
				validType = false;
			}
		}while(!validType);
		user.setBet(bet);
		String word = "chip";
		if(user.getBet() > 1) {
			word += "s";
		}
		System.out.println(user.getName() + "'s Current Bet: " + user.getBet() + " " + word + ".");
		
		try {
			delay("Dealing");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean doubled = false;
		
		while(userPoints <= 21 && notOver) {
			//print hands
			System.out.println("Dealer's hand: " + dealer.getCard(0));
			System.out.println("Your hand: " + user.printHand());
			
			//checks if user input is valid
			boolean valid = true;
			//options to select
			String options = "[1] Sort hand\n[2] Hit\n[3] Stand\n[4] Double Down\n";
			//asks user what he or she will do
			do {	
				//prompt user
				System.out.println("What do you want to do?");
				System.out.println(options);
				
				int userOption = 0;
				//checks if user input is valid
				try {
					
					userOption = in.nextInt();
				}catch (NumberFormatException e){
					valid = false;
					System.out.println("Invalid response. Try again.");
				}
				//checks through options
				switch (userOption) {
				case 1:
					user.sortHand();
					valid = true;
					break;
				case 2:
					Card newCard = deck.deal();
					delay("Dealing");
					System.out.println("Card dealt: " + newCard.toString());
					user.addCard(newCard);
					valid = true;
					if(user.getPoints() > 21) {
						notOver = false;
					}
					break;
				case 3:
					System.out.println("Your final hand: " + user.printHand());
					notOver = false;
					valid = true;
					break;
				case 4:
					if(!doubled) {
						if(user.getBet()*2 <= user.getChips()) {
							user.setBet(user.getBet() *2);;
						}
						else {
							System.out.println("Insufficient chips.");
						}
						if(user.getBet() > 1) {
							word = "chips";
						}
						System.out.println(user.getName() + "'s Current Bet: " + user.getBet() + " " + word + ".");
						valid = true;
						doubled = true;
						break;
					}
					else {
						System.out.println("Invalid move.");
						valid = false;
					}
				default:
					System.out.println("Invalid selection. Try again.");
					valid = false;
					break;
				}
			}while(!valid);	
			if(user.getPoints() > 21) {
				notOver = false;
			}
		}
		userPoints = user.getPoints();
		if(userPoints > 21) {
			System.out.println("You busted!");
			System.out.println("Your final hand: {" + user.printHand());
			if(user.getBet() < 2) {
				word = "chip";
			}
			else {
				word = "chips";
			}
			System.out.println("You lost " + user.getBet() + " " + word + ".");
			user.setChips(user.getChips() - user.getBet());
			user.setBet(0);
			if(user.getChips() < 2) {
				word = "chip";
			}
			else {
				word = "chips";
			}
			System.out.println("You have " + user.getChips() + " " + word + " remaining.");
			return endgame();
		}
		else {
			while(dealer.calcPoints() < 17) {
				System.out.println("The dealer hits!");
				dealer.addCard(deck.deal());
			}
			if(dealer.calcPoints() > 21) {
				System.out.println("The dealer busted!");
				System.out.println("The dealer's hand: " + dealer.printHand());
				System.out.println("You win!");
				if(user.getBet() < 2) {
					word = "chip";
				}
				else {
					word = "chips";
				}
				System.out.println("You won " + user.getBet() + " " + word + ".");
				user.setChips(user.getChips() + user.getBet());
				user.setBet(0);
				if(user.getChips() < 2) {
					word = "chip";
				}
				else {
					word = "chips";
				}
				System.out.println("You current balance is: " + user.getChips() + " " + word + ".");
				return endgame();
			}
			else if(dealer.calcPoints() > userPoints) {
				System.out.println("The dealer stands!");
				System.out.println("The dealer's hand: " + dealer.printHand());
				System.out.println("The dealer wins!");
				if(user.getBet() < 2) {
					word = "chip";
				}
				else {
					word = "chips";
				}
				System.out.println("You lost " + user.getBet() + " " + word + ".");
				user.setChips(user.getChips() - user.getBet());
				user.setBet(0);
				if(user.getChips() < 2) {
					word = "chip";
				}
				else {
					word = "chips";
				}
				System.out.println("You have " + user.getChips() + " " + word + " remaining.");
				return endgame();
			}
			else if(dealer.calcPoints() == userPoints) {
				System.out.println("The dealer stands!");
				System.out.println("The dealer's hand: " + dealer.printHand());
				System.out.println("Push!");
				System.out.println("You lost 0 chips.");
				user.setBet(0);
				if(user.getChips() < 2) {
					word = "chip";
				}
				else {
					word = "chips";
				}
				System.out.println("You have " + user.getChips() + " " + word + " remaining.");
				return endgame();
			}
			else if(dealer.calcPoints() < userPoints) {
				System.out.println("The dealer's hand: " + dealer.printHand());
				System.out.println("You win!");
				if(user.getBet() < 2) {
					word = "chip";
				}
				else {
					word = "chips";
				}
				System.out.println("You won " + user.getBet() + " " + word + ".");
				user.setChips(user.getChips() + user.getBet());
				user.setBet(0);
				if(user.getChips() < 2) {
					word = "chip";
				}
				else {
					word = "chips";
				}
				System.out.println("You current balance is: " + user.getChips() + " " + word + ".");
				return endgame();
			}
		}
		return endgame();
	}
	
	public void delay(String print) throws InterruptedException {
		System.out.print(print + ".");
		Thread.sleep(1000);
		System.out.print(".");
		Thread.sleep(1000);
		System.out.print(".\n");
	}
	
	public boolean endgame() {
		in.nextLine();
		boolean valid = true;
		do {
			System.out.println("Play again? (Y/N)");
			String option = in.nextLine();
			if(option.equals("Y") || option.equals("y")) {
				valid = true;
				return false;
			}
			else if(option.equals("N") || option.equals("n")){
				System.out.println("Ok! Bye!");
				valid = true;
				return true;
			}
			else {
				System.out.println("Invalid response.");
				valid = false;
			}
		}while(!valid);
		return true;
	}
}