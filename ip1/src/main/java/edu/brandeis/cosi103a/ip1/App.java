package edu.brandeis.cosi103a.ip1;

/**
 * Automation: The Game Prototype
 */
public class App {
    public static void main(String[] args) {
        Game game = new Game();
        int turn = 0;
        while (!game.isGameOver()) {
            System.out.println("Turn " + (++turn) + ", Player " + (game.getCurrentPlayerIndex() + 1) + "'s turn");
            String summary = game.playTurn();
            System.out.println(summary);
        }
        Player winner = game.getWinner();
        if (winner != null) {
            System.out.println("Player " + (game.getPlayers().indexOf(winner) + 1) + " wins with " + winner.getAutomationPoints() + " APs!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}
