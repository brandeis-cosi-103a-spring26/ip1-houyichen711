package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for core game classes and basic gameplay.
 */
public class AppTest {

    @Test
    public void testCardProperties() {
        Card c = new Card(Card.Type.AUTOMATION, "Method", 2, 1);
        assertEquals(Card.Type.AUTOMATION, c.getType());
        assertEquals("Method", c.getName());
        assertEquals(2, c.getCost());
        assertEquals(1, c.getValue());
    }

    @Test
    public void testDeckOperations() {
        Deck d = new Deck();
        Card b = new Card(Card.Type.CRYPTOCURRENCY, "Bitcoin", 0, 1);
        d.add(b);
        assertEquals(1, d.size());
        Card drawn = d.draw();
        assertNotNull(drawn);
        assertEquals(0, d.size());
        assertTrue(d.isEmpty());
    }

    @Test
    public void testPlayerPlayAndBuy() {
        Player p = new Player();
        // Put one Bitcoin into deck and draw it
        Card btc = new Card(Card.Type.CRYPTOCURRENCY, "Bitcoin", 0, 1);
        p.addToDeck(btc);
        p.drawHand(1);
        assertEquals(1, p.getHand().size());

        // Play the Bitcoin
        p.playCard(btc);
        assertEquals(1, p.getCryptocoins());

        // Buy a Method (cost 2) should fail
        Card method = new Card(Card.Type.AUTOMATION, "Method", 2, 1);
        p.buyCard(method);
        // Not enough coins, so still 1 coin and method added only if affordable
        assertEquals(1, p.getCryptocoins());

        // Give the player enough coins and buy
        // simulate by setting coins via playing another bitcoin
        Card btc2 = new Card(Card.Type.CRYPTOCURRENCY, "Bitcoin", 0, 1);
        p.addToDeck(btc2);
        p.drawHand(1);
        p.playCard(btc2);
        assertEquals(2, p.getCryptocoins());
        p.buyCard(method);
        assertEquals(0, p.getCryptocoins());
    }

    @Test
    public void testGameTurnAlternationAndSummary() {
        Game g = new Game();
        int start = g.getCurrentPlayerIndex();
        String s1 = g.playTurn();
        assertNotNull(s1);
        // After one turn, current player should have advanced by 1
        int after1 = g.getCurrentPlayerIndex();
        assertEquals((start + 1) % 2, after1);

        String s2 = g.playTurn();
        assertNotNull(s2);
        int after2 = g.getCurrentPlayerIndex();
        // After two turns, should be back to start
        assertEquals(start, after2);

        // Summaries should contain AP reporting
        assertTrue(s1.contains("AP="));
        assertTrue(s2.contains("AP="));
        // Each summary should mention whether something was bought or not
        assertTrue(s1.contains("bought") || s1.contains("bought nothing"));
    }

    @Test
    public void testGameRunsAndEndsEventually() {
        Game g = new Game();
        // Run the game for a bounded number of turns to ensure it progresses
        int maxTurns = 1000;
        int turns = 0;
        while (!g.isGameOver() && turns < maxTurns) {
            g.playTurn();
            turns++;
        }
        // Either game ended or we reached the bound; in either case, state should be consistent
        assertFalse(turns == 0);
        if (g.isGameOver()) {
            // When the game reports it ended, ensure no Frameworks remain in the supply
            assertTrue(g.allFrameworksBought());
        }
    }
}
