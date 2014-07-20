package net.thegenesismc.searchanddestroy.commands;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.entity.Player;

public class CommandLeave {

    public static void execute(Player p, String[] a) {
        if (a.length!=1) {
            p.sendMessage(SND.TAG_BLUE + "Usage: /snd leave");
        } else {
            if (SND.gm.isPlaying(p)) {
                SND.gm.broadcastMessageInGame(SND.TAG_RED + SND.tm.getPlayerNameInTeamColor(p) + " §cleft the game!");
                SND.gm.removePlayerFromGame(p);
            } else {
                p.sendMessage(SND.TAG_BLUE + "You are not in a game!");
            }
        }
    }

}
