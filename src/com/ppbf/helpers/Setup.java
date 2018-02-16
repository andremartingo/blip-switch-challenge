package com.ppbf.helpers;

import static com.ppbf.helpers.File.readFromFile;
import static com.ppbf.helpers.Menu.printMenu;
import static com.ppbf.sandbox.Sandbox.parse;

import com.ppbf.sandbox.Sandbox;
import com.ppbf.solutions.models.Bet;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class Setup {

	private static Scanner in = new Scanner(System.in);

	private static final String FORMAT_PROJECT_TABLE =
			"%-10s%-40s%-30s%-30s%-20s%n";

	private static final String TABLE_SPACER_95 = "-----------------------------------"
			+ "-----------------------------------------------------------------------------------------------";

	public static void printTable(List<Bet> bet){
		for(Bet b : bet){
			System.out.format(FORMAT_PROJECT_TABLE, "Competition", "Name", "Market",
					"Id", "Odd");
			System.out.format(FORMAT_PROJECT_TABLE,b.competitionName,b.event.name,b.event.market.name,b.event.market.id,b.event.market.odds);
			System.out.println(TABLE_SPACER_95);
		}
	}

	public static void startSandbox() throws FileNotFoundException {
		System.out.println("Welcome to the Switch Programme Exercise @Blip");

		boolean quit = false;
		long marketId;

		do {
			printMenu();

			int menuItem = in.nextInt();

			switch (menuItem) {
				case 1:
					// readFromFile returns a List with each entry representing a line of the file.
					List<String> lines = readFromFile("resources/eventsWithDuplicates.csv");
					List<Bet> allBets = parse(lines);
					printTable(allBets);
					boolean flag = false;
					do {
						System.out.print("Choose marketId:");
						marketId = in.nextLong();
						if (Sandbox.validateMarket(lines, marketId)) {
							flag = true;
						}
					} while (flag == false);

					List<Bet> selectedBet = Sandbox.getBetById(lines, marketId);
					printTable(selectedBet);

					break;
				case 2:
					// readFromFile returns a List with each entry representing a line of the file.
					lines = readFromFile("resources/eventsWithDuplicates.csv");

					List<Bet>  bet = Sandbox.sortByOdd(lines);
					printTable(bet);

					break;
				case 3:
					// readFromFile returns a List with each entry representing a line of the file.
					lines = readFromFile("resources/eventsWithoutDuplicates.csv");

					BigDecimal TOTAL_MONEY = new BigDecimal("30.1");
					BigDecimal updatedMoney = new BigDecimal("0");
					List<Long> removedMarkets = new ArrayList<>();
					Map<Long, BigDecimal> bets = new HashMap<>();
					List<Bet> listBet = new ArrayList<>();
					listBet = parse(lines);

					do {
						System.out.println("Total Money:" + TOTAL_MONEY);

						//System.out.println(lines);
						List<Bet> betsToPrint = Sandbox.parse(lines);
						printTable(betsToPrint);
						flag = false;
						do {
							System.out.print("Choose marketId:");
							marketId = in.nextLong();
							if (Sandbox.validateMarket(lines, marketId)) {
								flag = true;
							}
						} while (flag == false);
						System.out.print("Choose stake:");
						BigDecimal stake = in.nextBigDecimal();
						if (stake.compareTo(TOTAL_MONEY) < 1) {
							updatedMoney = Sandbox.validateAndUpdateTotalMoney(lines, TOTAL_MONEY, marketId, stake);
							if (updatedMoney != null) {
								TOTAL_MONEY = updatedMoney;
								Sandbox.addMarketAndStateToMap(bets, marketId, stake);
							} else {
								System.out.println("Error...");
							}
						} else {
							System.out.println("You dont have that money");
						}

					} while (TOTAL_MONEY.compareTo(BigDecimal.ZERO) > 0);
					Random rand = new Random();
					long winner = rand.nextInt(3) + 1;
					BigDecimal profit = new BigDecimal("0");
					winner = 1;
					if (bets.containsKey(winner)) {
						for (Bet b : listBet) {
							if (b.event.market.id == winner) {
								profit = profit.add(bets.get(winner).multiply(b.event.market.odds).subtract(bets.get(winner)));
							}
						}
					}

					System.out.println("Profit:");
					System.out.println(profit);

					break;
				case 0:
					quit = true;
					break;
				default:
					System.out.println("ERROR: Invalid choice.");
			}
		} while (!quit);

		System.out.println("Bye-bye!");
	}
}
