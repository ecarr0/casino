import java.util.Scanner;
 
public class test{
	public static void main(String[]args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter your name: ");
		String name = in.nextLine();
		boolean valid = true;
		int bet = 0;
		String stringBet = "";
		do {
			System.out.print("How many chips would you like to buy?");
			try {
				stringBet = in.nextLine();
				bet = Integer.parseInt(stringBet);
				valid = true;
			}catch (NumberFormatException e){
				System.out.println("Error. Try again.");
				valid = false;
			}
			if(bet <= 0) {
				System.out.println("Invalid amount.");
				valid = false;
			}
		}while(!valid);
		
		User user = new User(name, bet);
		try {
			new play(user);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		in.close();
	}
}