package edu.brandeis.cosi103a.ip1;

import java.util.Random;
import java.util.Scanner;

/**
 * Dice Rolling Game
 *
 */
public class App 
{
    private static final int NUM_PLAYERS = 2;
    private static final int TURNS_PER_PLAYER = 10;
    private static final int MAX_REROLLS = 2;
    
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        int[] scores = new int[NUM_PLAYERS];
        
        for(int turn = 0; turn < NUM_PLAYERS * TURNS_PER_PLAYER; turn++) {
            int player = turn % NUM_PLAYERS;
            int turnScore = playTurn(sc, rand, player);
            scores[player] += turnScore;
            System.out.println("Turn score: " + turnScore + ", Total score: " + scores[player]);
            System.out.println();
        }
        
        announceWinner(scores);
        sc.close();
    }
    
    private static int playTurn(Scanner sc, Random rand, int player) {
        System.out.println("Player " + (player + 1) + "'s turn");
        
        int die = rollDie(rand);
        System.out.println("Rolled: " + die);
        
        for(int reroll = 0; reroll < MAX_REROLLS; reroll++) {
            if(!getYesNo(sc, "Re-roll? (y/n): ")) {
                break;
            }
            die = rollDie(rand);
            System.out.println("Rolled: " + die);
        }
        
        return die;
    }
    
    private static boolean getYesNo(Scanner sc, String prompt) {
        System.out.print(prompt);
        String choice = sc.next().toLowerCase();
        return choice.equals("y");
    }
    
    private static void announceWinner(int[] scores) {
        System.out.println("Final Scores:");
        for(int i = 0; i < scores.length; i++) {
            System.out.println("Player " + (i + 1) + ": " + scores[i]);
        }
        
        if(scores[0] > scores[1]) {
            System.out.println("Player 1 wins!");
        } else if(scores[1] > scores[0]) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }
    
    static int rollDie(Random rand) {
        return rand.nextInt(6) + 1;
    }
}
