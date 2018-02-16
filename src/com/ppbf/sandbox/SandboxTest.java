package com.ppbf.sandbox;

import com.ppbf.solutions.models.Bet;
import com.ppbf.solutions.models.Event;
import com.ppbf.solutions.models.Market;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SandboxTest {

    private final List<String> lines = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        lines.add("Champions League;Liverpool vs Porto;Liverpool Wins;1;3.0");
        lines.add("Champions League;Liverpool vs Porto;Liverpool Wins;2;1.0");
        lines.add("Champions League;Liverpool vs Porto;Draw;2;2.0");
    }

    @Test
    public void ensureGetAllMarketsWithOneID() throws Exception {
        Bet ex1 = new Bet("Champions League",
            new Event("Liverpool vs Porto",
                new Market("Liverpool Wins", 2, new BigDecimal("1.0"))));

        Bet ex2 = new Bet("Champions League",
            new Event("Liverpool vs Porto",
                new Market("Draw", 2, new BigDecimal("2.0"))));

        List<Bet> expected = new ArrayList<>();
        expected.add(ex1);
        expected.add(ex2);

        List<Bet> actual = Sandbox.ex1(lines, 2);
        assertEquals(actual, expected);
    }

    @Test
    public void ensureSortAscending() throws Exception {
        Bet ex1 = new Bet("Champions League",
            new Event("Liverpool vs Porto",
                new Market("Liverpool Wins", 2, new BigDecimal("1.0"))));

        Bet ex2 = new Bet("Champions League",
            new Event("Liverpool vs Porto",
                new Market("Draw", 2, new BigDecimal("2.0"))));

        Bet ex3 = new Bet("Champions League",
            new Event("Liverpool vs Porto",
                new Market("Liverpool Wins", 1, new BigDecimal("3.0"))));

        List<Bet> expected = new ArrayList<>();
        expected.add(ex1);
        expected.add(ex2);
        expected.add(ex3);

        List<Bet> actual = Sandbox.ex2(lines);
        assertEquals(actual, expected);
    }

    @Test
    public void ensureExpectNullWhenPassInvalidMarketId() throws Exception {
        BigDecimal totalCash = Sandbox
                .validateAndUpdateTotalMoney(lines, new BigDecimal("20.0"), 4, new BigDecimal("20.0"));
        assertNull(totalCash);

        totalCash = Sandbox
                .validateAndUpdateTotalMoney(lines, new BigDecimal("20.0"), 1, new BigDecimal("10.0"));
        BigDecimal expectedTotalCash = new BigDecimal("10.0");
        assertEquals(totalCash, expectedTotalCash);
    }

    @Test
    public void ensureUpdateTotalMoney() throws Exception {

        BigDecimal totalCash = Sandbox
                .validateAndUpdateTotalMoney(lines, new BigDecimal("20.0"), 1, new BigDecimal("10.0"));
        BigDecimal expectedTotalCash = new BigDecimal("10.0");
        assertEquals(totalCash, expectedTotalCash);
    }

    @Test
    public void ensureReturnTrueWhenAValidMarket() throws Exception {
        boolean result;
        result = Sandbox.validateMarket(lines,1);
        assertTrue(result);
    }

    @Test
    public void ensureReturnFalseForAInvalidValidMarket() throws Exception {
        boolean result;
        result = Sandbox.validateMarket(lines,10);
        assertFalse(result);
    }

    @Test
    public void ensureCanAddValidMarketIdToBetsMap() throws Exception {
        Map<Long, BigDecimal> bets = new HashMap<>();
        bets = Sandbox.addMarketAndStateToMap(bets,1,new BigDecimal("2.0"));
        Map<Long, BigDecimal> expectedBets = new HashMap<>();
        expectedBets.put(new Long("1"),new BigDecimal("2.0"));
        assertEquals(expectedBets,bets);
    }

    @Test
    public void ensureUpdateBetsMapWhenMarketAlreadyExists() throws Exception {
        Map<Long, BigDecimal> bets = new HashMap<>();
        bets = Sandbox.addMarketAndStateToMap(bets,1,new BigDecimal("2.0"));
        bets = Sandbox.addMarketAndStateToMap(bets,1,new BigDecimal("2.0"));
        Map<Long, BigDecimal> expectedBets = new HashMap<>();
        expectedBets.put(new Long("1"),new BigDecimal("4.0"));
        assertEquals(expectedBets,bets);
        assertTrue(bets.size() == 1);
    }

}
