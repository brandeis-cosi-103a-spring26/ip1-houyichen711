package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;

/**
 * Unit test for App.
 */
public class AppTest {
    @Test
    public void testGameRunsAndPrintsFinalScores() throws Exception {
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            input.append("h\n");
        }

        InputStream sysInOrig = System.in;
        PrintStream sysOutOrig = System.out;

        ByteArrayInputStream in = new ByteArrayInputStream(input.toString().getBytes("UTF-8"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true, "UTF-8");

        try {
            System.setIn(in);
            System.setOut(ps);

            App.main(new String[0]);

            ps.flush();
            String output = baos.toString("UTF-8");

            assertTrue("Output should contain final scores header", output.contains("=== Final Scores ==="));
            assertTrue("Output should mention Player 1", output.contains("Player 1:"));
            assertTrue("Output should mention Player 2", output.contains("Player 2:"));
        } finally {
            System.setIn(sysInOrig);
            System.setOut(sysOutOrig);
        }
    }
}
