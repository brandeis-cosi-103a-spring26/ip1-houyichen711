package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the game state and logic.
 */
public class Game {
    private final List<Player> players;
    private final Deck supply;
    private final Random random;
    private int currentPlayerIndex;
    private boolean gameOver;

    public Game() {
        this.players = new ArrayList<>();
        this.supply = new Deck();
        this.random = new Random();
        this.currentPlayerIndex = 0;
        this.gameOver = false;
        initializeGame();
    }

    private void initializeGame() {
        // Create supply
        for (int i = 0; i < 14; i++) {
            supply.add(new Card(Card.Type.AUTOMATION, "Method", 2, 1));
        }
        for (int i = 0; i < 8; i++) {
            supply.add(new Card(Card.Type.AUTOMATION, "Module", 5, 3));
        }
        for (int i = 0; i < 8; i++) {
            supply.add(new Card(Card.Type.AUTOMATION, "Framework", 8, 6));
        }
        for (int i = 0; i < 60; i++) {
            supply.add(new Card(Card.Type.CRYPTOCURRENCY, "Bitcoin", 0, 1));
        }
        for (int i = 0; i < 40; i++) {
            supply.add(new Card(Card.Type.CRYPTOCURRENCY, "Ethereum", 3, 2));
        }
        for (int i = 0; i < 30; i++) {
            supply.add(new Card(Card.Type.CRYPTOCURRENCY, "Dogecoin", 6, 3));
        }

        // Create players
        for (int i = 0; i < 2; i++) {
            Player player = new Player();
            // Starter deck: 7 Bitcoins and 3 Methods
            for (int j = 0; j < 7; j++) {
                player.addToDeck(new Card(Card.Type.CRYPTOCURRENCY, "Bitcoin", 0, 1));
            }
            for (int j = 0; j < 3; j++) {
                player.addToDeck(new Card(Card.Type.AUTOMATION, "Method", 2, 1));
            }
            player.shuffleDeck();
            player.drawHand(5);
            players.add(player);
        }

        // Random starting player
        currentPlayerIndex = random.nextInt(2);
    }

    public String playTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        StringBuilder sb = new StringBuilder();
        sb.append("Player ").append(currentPlayerIndex + 1).append(": ");

        // Buy phase: play all crypto currency cards in hand
        List<Card> cryptos = new ArrayList<>();
        for (Card card : currentPlayer.getHand()) {
            if (card.getType() == Card.Type.CRYPTOCURRENCY) {
                cryptos.add(card);
            }
        }
        int beforeCoins = currentPlayer.getCryptocoins();
        for (Card crypto : cryptos) {
            currentPlayer.playCard(crypto);
        }
        int afterCoins = currentPlayer.getCryptocoins();
        int gained = afterCoins - beforeCoins;
        sb.append("played ").append(cryptos.size()).append(" crypto cards, gained ").append(gained).append(" coins; ");

        // Buy a card if possible
        List<Card> affordable = new ArrayList<>();
        for (Card card : supply.getCards()) {
            if (card.getCost() <= currentPlayer.getCryptocoins()) {
                affordable.add(card);
            }
        }
        if (!affordable.isEmpty()) {
            Card bought = affordable.get(random.nextInt(affordable.size()));
            supply.remove(bought);
            currentPlayer.buyCard(bought);
            sb.append("bought ").append(bought.getName()).append(" (cost ").append(bought.getCost()).append("); ");
        } else {
            sb.append("bought nothing; ");
        }

        // Cleanup phase
        currentPlayer.discardHand();
        currentPlayer.drawHand(5);

        // Report AP for both players
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            sb.append("P").append(i + 1).append(" AP=").append(p.getAutomationPoints());
            if (i < players.size() - 1) sb.append(", ");
        }

        // Check game end (no Frameworks left)
        boolean frameworksLeft = false;
        for (Card card : supply.getCards()) {
            if (card.getName().equals("Framework")) {
                frameworksLeft = true;
                break;
            }
        }
        if (!frameworksLeft) {
            gameOver = true;
        }

        // Next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return sb.toString();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Player getWinner() {
        if (!gameOver) return null;
        Player p1 = players.get(0);
        Player p2 = players.get(1);
        int ap1 = p1.getAutomationPoints();
        int ap2 = p2.getAutomationPoints();
        if (ap1 > ap2) return p1;
        if (ap2 > ap1) return p2;
        return null; // tie, but for simplicity, return null
    }

    /**
     * Returns true when there are no Framework cards left in the supply.
     */
    public boolean allFrameworksBought() {
        for (Card card : supply.getCards()) {
            if ("Framework".equals(card.getName())) return false;
        }
        return true;
    }

    public List<Player> getPlayers() {
        return players;
    }
}