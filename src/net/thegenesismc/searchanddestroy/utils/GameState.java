package net.thegenesismc.searchanddestroy.utils;

/**
 * First line: &9[Join]
 Second line: &aSND1
 Thrid line: &5&l● Lobby ●
 Forth line: &a0/24

 When the game has started:
 First line: &9[Join]
 Second line: &aSND1
 Thrid line: &8● In Game ●
 Forth line: &a0/24

 When the game is restarting:
 First line: &9[Join]
 Second line: &aSND1
 Thrid line: &4&l● Restarting ●
 Forth line: &a0/24
 */

public enum GameState {

    LOBBY("§5§l● Lobby ●"),
    INGAME("§8● In Game ●"),
    RESTARTING("§4&§l● Restarting ●");

    private GameState(String label) {

    }

    public String getName() {
        return
                "";
    }
}
