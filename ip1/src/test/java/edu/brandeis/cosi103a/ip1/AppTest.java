package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.Test;

/**
 * Unit test for App.
 */
public class AppTest 
{
    /**
     * Test the rollDie method
     */
    @Test
    public void testRollDie()
    {
        Random rand = new Random();
        for(int i = 0; i < 100; i++) {
            int die = App.rollDie(rand);
            assertTrue("Die roll should be between 1 and 6", die >= 1 && die <= 6);
        }
    }
    
    /**
     * Test the full game with simulated inputs
     */
    @Test
    public void testGameSimulation()
    {
        // Simulate inputs: for each of 20 turns, roll once, no reroll (n)
        StringBuilder inputBuilder = new StringBuilder();
        for(int i = 0; i < 20; i++) {
            inputBuilder.append("n\n");
        }
        String input = inputBuilder.toString();
        
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        System.setIn(in);
        System.setOut(new PrintStream(out));
        
        try {
            App.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }
        
        String output = out.toString();
        assertTrue("Output should contain 'Final Scores'", output.contains("Final Scores"));
        assertTrue("Output should contain 'Player 1:'", output.contains("Player 1:"));
        assertTrue("Output should contain 'Player 2:'", output.contains("Player 2:"));
    }
}
