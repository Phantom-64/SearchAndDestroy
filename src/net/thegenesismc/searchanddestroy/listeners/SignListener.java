package net.thegenesismc.searchanddestroy.listeners;

import net.thegenesismc.searchanddestroy.SND;
import net.thegenesismc.searchanddestroy.utils.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * When game is waiting to start:
 * First line: &9[Join]
 * Second line: &aSND1
 * Thrid line: &5&l● Lobby ●
 * Forth line: &a0/24
 * <p/>
 * When the game has started:
 * First line: &9[Join]
 * Second line: &aSND1
 * Thrid line: &8● In Game ●
 * Forth line: &a0/24
 * <p/>
 * When the game is restarting:
 * First line: &9[Join]
 * Second line: &aSND1
 * Thrid line: &4&l● Restarting ●
 * Forth line: &a0/24
 * <p/>
 * (Make sure the forth line updates when a player has joined)
 */

public class SignListener implements Listener {

    @EventHandler
    public void onJoinSignCreate(SignChangeEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) {
            if (e.getLine(0).equalsIgnoreCase("[snd]") && e.getLine(1).equalsIgnoreCase("join")) {
                SND.gm.setJoinSignLocation(e.getBlock().getLocation());
                if (SND.gm.getGameState() == GameState.LOBBY) {
                    e.setLine(0, "§9[Join]");
                    e.setLine(1, "§aSND");
                    e.setLine(2, "§5§l● Lobby ●");
                    e.setLine(3, "§a" + SND.gm.getPlaying().size() + "/" + SND.gm.getPlayerLimit());
                } else if (SND.gm.getGameState() == GameState.INGAME) {
                    e.setLine(0, "§9[Join]");
                    e.setLine(1, "§aSND");
                    e.setLine(2, "§8● In Game ●");
                    e.setLine(3, "§a" + SND.gm.getPlaying().size() + "/" + SND.gm.getPlayerLimit());
                } else if (SND.gm.getGameState() == GameState.RESTARTING) {
                    e.setLine(0, "§9[Join]");
                    e.setLine(1, "§aSND");
                    e.setLine(2, "§4§l● Restarting ●");
                    e.setLine(3, "§a" + SND.gm.getPlaying().size() + "/" + SND.gm.getPlayerLimit());
                }
                p.sendMessage(SND.TAG_GREEN + "Join sign created!");
            }
        }
    }

}
