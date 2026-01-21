package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testRollDie() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int result = App.rollDie(random);
            assertTrue("Die roll should be between 1 and 6", result >= 1 && result <= 6);
        }
    }

    @Test
    public void testRollDieWithFixedSeed() {
        Random random = new Random(42); // Fixed seed for predictable results
        // With seed 42, the first few rolls are predictable
        int roll1 = App.rollDie(random);
        int roll2 = App.rollDie(random);
        int roll3 = App.rollDie(random);
        // Just check they are in range, since exact values depend on seed
        assertTrue(roll1 >= 1 && roll1 <= 6);
        assertTrue(roll2 >= 1 && roll2 <= 6);
        assertTrue(roll3 >= 1 && roll3 <= 6);
    }
}
