package edu.brandeis.cosi103a.ip1;

import java.util.Scanner;
import java.util.Random;

/**
 * Dice Rolling Game
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to the Dice Rolling Game!");
        
        String[] players = getPlayerNames(scanner);
        int[] scores = {0, 0};

        playGame(scanner, random, players, scores);

        displayFinalResults(players, scores);

        scanner.close();
    }

    private static String[] getPlayerNames(Scanner scanner) {
        String[] players = new String[2];
        System.out.print("Enter name for Player 1: ");
        players[0] = scanner.nextLine();
        System.out.print("Enter name for Player 2: ");
        players[1] = scanner.nextLine();
        return players;
    }

    private static void playGame(Scanner scanner, Random random, String[] players, int[] scores) {
        for (int turn = 1; turn <= 10; turn++) {
            System.out.println("\n--- Turn " + turn + " ---");
            for (int i = 0; i < 2; i++) {
                scores[i] += playTurn(scanner, random, players[i]);
                System.out.println(players[i] + "'s total score: " + scores[i]);
            }
        }
    }

    private static int playTurn(Scanner scanner, Random random, String playerName) {
        System.out.println(playerName + "'s turn:");
        int die = rollDie(random);
        System.out.println("Rolled: " + die);
        die = handleReRolls(scanner, random, die, playerName);
        return die;
    }

    private static void displayFinalResults(String[] players, int[] scores) {
        System.out.println("\n--- Final Scores ---");
        System.out.println(players[0] + ": " + scores[0]);
        System.out.println(players[1] + ": " + scores[1]);

        String winner = determineWinner(players, scores);
        System.out.println(winner);
    }

    private static String determineWinner(String[] players, int[] scores) {
        if (scores[0] > scores[1]) {
            return players[0] + " wins!";
        } else if (scores[1] > scores[0]) {
            return players[1] + " wins!";
        } else {
            return "It's a tie!";
        }
    }

    public static int rollDie(Random random) {
        return random.nextInt(6) + 1;
    }

    private static int handleReRolls(Scanner scanner, Random random, int currentDie, String playerName) {
        int reRolls = 0;
        while (reRolls < 2) {
            System.out.print("Do you want to re-roll? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("y") || choice.equals("yes")) {
                currentDie = rollDie(random);
                System.out.println("Re-rolled: " + currentDie);
                reRolls++;
            } else {
                break;
            }
        }
        return currentDie;
    }
}
