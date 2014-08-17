package net.thegenesismc.searchanddestroy.utils;

public enum Kit {

    ASSAULT(0),
    JUGGERNAUT(400),
    SPY(0),
    SNIPER(0),
    SCOUT(250),
    EXPLOSIVE(600),
    WIZARD(600),

    BANTER(50),
    GHOST(200),
    ASSASSIN(400);

    private int cost;

    private Kit(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
