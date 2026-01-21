package edu.brandeis.cosi103a.ip1;

import java.util.Random;
import java.util.Scanner;

/**
 * Simple two-player dice rolling game.
 */
public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rnd = new Random();

        final int TURNS_PER_PLAYER = 10;
        final int MAX_REROLLS = 2;
        int[] scores = new int[2];

        System.out.println("Two-player Dice Game");
        System.out.println("Each player gets " + TURNS_PER_PLAYER + " turns.");
        System.out.println("On each turn you roll one 6-sided die and may reroll up to " + MAX_REROLLS + " times.");
        System.out.println("Enter 'r' to reroll or 'h' to hold when prompted. Press Enter after your choice.");
        System.out.println();

        for (int turn = 1; turn <= TURNS_PER_PLAYER; turn++) {
            for (int player = 0; player < 2; player++) {
                System.out.println("--- Turn " + turn + " for Player " + (player + 1) + " ---");

                int roll = rnd.nextInt(6) + 1;
                int rerolls = 0;

                while (true) {
                    System.out.println("Player " + (player + 1) + " rolled: " + roll);
                    if (rerolls >= MAX_REROLLS) {
                        System.out.println("No rerolls left.");
                        break;
                    }

                    System.out.print("Enter 'r' to reroll (" + (MAX_REROLLS - rerolls) + " left) or 'h' to hold: ");
                    String line = sc.nextLine();
                    if (line == null) {
                        break;
                    }
                    line = line.trim().toLowerCase();
                    if (line.equals("r")) {
                        rerolls++;
                        roll = rnd.nextInt(6) + 1;
                        continue;
                    } else if (line.equals("h") || line.isEmpty()) {
                        break;
                    } else {
                        System.out.println("Unrecognized input. Treating as hold.");
                        break;
                    }
                }

                scores[player] += roll;
                System.out.println("Player " + (player + 1) + " ends turn with " + roll + " (total: " + scores[player] + ")");
                System.out.println();
            }
        }

        System.out.println("=== Final Scores ===");
        System.out.println("Player 1: " + scores[0]);
        System.out.println("Player 2: " + scores[1]);
        if (scores[0] > scores[1]) {
            System.out.println("Player 1 wins!");
        } else if (scores[1] > scores[0]) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a tie!");
        }

        sc.close();
    }
}
