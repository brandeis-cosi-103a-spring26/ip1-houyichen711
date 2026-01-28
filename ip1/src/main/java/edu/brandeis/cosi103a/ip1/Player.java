package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 */
public class Player {
    private final Deck deck;
    private final Deck discardPile;
    private final List<Card> hand;
    private int cryptocoins;

    public Player() {
        this.deck = new Deck();
        this.discardPile = new Deck();
        this.hand = new ArrayList<>();
        this.cryptocoins = 0;
    }

    public void addToDeck(Card card) {
        deck.add(card);
    }

    public void addToDiscard(Card card) {
        discardPile.add(card);
    }

    public void drawHand(int numCards) {
        for (int i = 0; i < numCards; i++) {
            Card card = drawCard();
            if (card != null) {
                hand.add(card);
            }
        }
    }

    private Card drawCard() {
        if (deck.isEmpty()) {
            // Shuffle discard into deck
            deck.addAll(discardPile.getCards());
            discardPile.getCards().clear();
            deck.shuffle();
        }
        return deck.draw();
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }

    public void playCard(Card card) {
        if (hand.contains(card)) {
            hand.remove(card);
            if (card.getType() == Card.Type.CRYPTOCURRENCY) {
                cryptocoins += card.getValue();
            }
            addToDiscard(card);
        }
    }

    public void buyCard(Card card) {
        if (cryptocoins >= card.getCost()) {
            cryptocoins -= card.getCost();
            addToDiscard(card);
        }
    }

    public void discardHand() {
        for (Card card : hand) {
            addToDiscard(card);
        }
        hand.clear();
    }

    public int getCryptocoins() {
        return cryptocoins;
    }

    public void shuffleDeck() {
        deck.shuffle();
    }

    public int getAutomationPoints() {
        List<Card> allCards = getAllCards();
        int points = 0;
        for (Card card : allCards) {
            if (card.getType() == Card.Type.AUTOMATION) {
                points += card.getValue();
            }
        }
        return points;
    }

    private List<Card> getAllCards() {
        List<Card> all = new ArrayList<>();
        all.addAll(deck.getCards());
        all.addAll(discardPile.getCards());
        all.addAll(hand);
        return all;
    }
}