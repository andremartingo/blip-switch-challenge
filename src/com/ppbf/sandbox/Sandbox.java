package com.ppbf.sandbox;

import com.ppbf.solutions.models.Bet;
import com.ppbf.solutions.models.Event;
import com.ppbf.solutions.models.Market;

import java.math.BigDecimal;
import java.util.*;

public class Sandbox {

    // Ex1: Given a List of lines on the file;
    //      Given a marketId;
    //      Write a function that finds the corresponding value of marketId in the list.
    // TIP: print the whole line


    public static List<Bet> parse(List<String> lines) {
        List<Bet> listBet = new ArrayList<>();
        for(String line : lines){
            String[] result = line.split(";");
            Market market = new Market(result[2],Long.parseLong(result[3]),new BigDecimal(result[4]));
            Event event = new Event(result[1],market);
            Bet bet = new Bet(result[0],event);
            listBet.add(bet);
        }
        return listBet;

    }
    public static List<Bet> ex1(List<String> lines, long marketId) {
        List<Bet> result = new ArrayList<>();
        List<Bet> listBet = new ArrayList<>();
        listBet = parse(lines);
        for(Bet bet : listBet){
            if(bet.event.market.id == marketId){
                result.add(bet);
            }
        }
        return result;
    }

    // Ex2: Given a List of lines on the file;
    //      Write a function that sorts the competitions by odd value (ascending).
    // TIP: sort the whole line
    public static List<Bet> ex2(List<String> lines) {
        List<Bet> listBet = new ArrayList<>();
        listBet = parse(lines);
        Collections.sort(listBet, (o1, o2) -> o1.event.market.odds.compareTo(o2.event.market.odds));
        return listBet;
    }

    // Ex3: Given a List of lines on the file;
    //      Given the total money;
    //      While you still have money available:
    //      Write a function that lets you bet on one or more markets.
    //
    // TIP: use validateAndUpdateTotalMoney to validate all data and update totalMoney.
    //      use addMarketAndStateToMap to return a Map with the marketId and the stake.
    public static boolean validateMarket(List<String> lines,long marketId){
        List<Bet> listBet = new ArrayList<>();
        listBet = parse(lines);
        for(Bet bet : listBet){
            if(bet.event.market.id == marketId){
                return true;
            }
        }
        return false;
    }

    public static BigDecimal validateAndUpdateTotalMoney(List<String> lines, BigDecimal totalMoney, long marketId, BigDecimal stake) {
        if(validateMarket(lines,marketId)){
            totalMoney = totalMoney.subtract(stake);
            return totalMoney;
        }else{
            return null;
        }
    }

    public static Map<Long, BigDecimal> addMarketAndStateToMap(Map<Long, BigDecimal> bets, long marketId, BigDecimal stake) {
        if(bets.get(marketId) != null){
            stake = bets.get(marketId).add(stake);
            bets.put(marketId,stake);
            return bets;
        }else {
            bets.put(marketId,stake);
            return bets;
        }
    }

    // Ex3_2: Given a List of lines on the file;
    //        Given a List of bets (List of marketId and the stake of the bet);
    //        Write a function that calculates the possible profit for each bet
    // TIP: return a list of profit values
    public static List<BigDecimal> ex3_2(List<String> lines, Map<Long, BigDecimal> bets) {
        System.out.println("Not implemented yet");
        return null;
    }
}
