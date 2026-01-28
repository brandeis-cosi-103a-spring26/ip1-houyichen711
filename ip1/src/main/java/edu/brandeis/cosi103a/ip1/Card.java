package edu.brandeis.cosi103a.ip1;

/**
 * Represents a card in Automation: The Game.
 */
public class Card {
    public enum Type {
        AUTOMATION, CRYPTOCURRENCY
    }

    private final Type type;
    private final String name;
    private final int cost;
    private final int value;

    public Card(Type type, String name, int cost, int value) {
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + " (cost: " + cost + ", value: " + value + ")";
    }
}