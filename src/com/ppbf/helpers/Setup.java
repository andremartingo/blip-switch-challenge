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

    public static void startSandbox() throws FileNotFoundException {
        System.out.println("Welcome to the Switch Programme Exercise @Blip");

        boolean quit = false;

        do {
            printMenu();

            int menuItem = in.nextInt();

            switch (menuItem) {
                case 1:
                    // readFromFile returns a List with each entry representing a line of the file.
                    List<String> lines = readFromFile("resources/eventsWithDuplicates.csv");

                    System.out.print("Choose marketId:");
                    long marketId = in.nextLong();

                    System.out.println(Sandbox.ex1(lines, marketId));

                    break;
                case 2:
                    // readFromFile returns a List with each entry representing a line of the file.
                    lines = readFromFile("resources/eventsWithDuplicates.csv");

                    System.out.println(Sandbox.ex2(lines));

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

                        System.out.println(lines);

                        System.out.print("Choose marketId:");
                        marketId = in.nextLong();
                        System.out.print("Choose stake:");
                        BigDecimal stake = in.nextBigDecimal();
                        if(stake.compareTo(TOTAL_MONEY)< 1) {
                            updatedMoney = Sandbox.validateAndUpdateTotalMoney(lines, TOTAL_MONEY, marketId, stake);
                            if (updatedMoney != null) {
                                TOTAL_MONEY = updatedMoney;
                                bets = Sandbox.addMarketAndStateToMap(bets, marketId, stake);
                            } else {
                                System.out.println("Error...");
                            }
                        }else{
                            System.out.println("You dont have that money");
                        }

                    } while (TOTAL_MONEY.compareTo(BigDecimal.ZERO) > 0);
                    Random rand = new Random();
                    int  winner = rand.nextInt(3) + 1;
                    BigDecimal profit = new BigDecimal("0");
                    winner = 1;

                    for (Map.Entry<Long, BigDecimal> entry : bets.entrySet())
                    {
                       if(entry.getKey() == 1){
                           for(Bet b : listBet){
                               if(b.event.market.id == entry.getKey()){
                                   profit = profit.add(entry.getValue().multiply(b.event.market.odds).subtract(entry.getValue()));
                               }
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
