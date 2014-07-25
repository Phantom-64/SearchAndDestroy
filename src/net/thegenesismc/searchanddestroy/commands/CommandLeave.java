package net.thegenesismc.searchanddestroy.commands;

import net.thegenesismc.searchanddestroy.SND;
import net.thegenesismc.searchanddestroy.utils.GameState;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class CommandLeave {

    public static void execute(Player p, String[] a) {
        if (a.length!=1) {
            p.sendMessage(SND.TAG_BLUE + "Usage: /snd leave");
        } else {
            if (SND.gm.isPlaying(p)) {
                SND.gm.broadcastMessageInGame(SND.TAG_RED + SND.tm.getPlayerNameInTeamColor(p) + " §cleft the game.");
                for (PotionEffect effect : p.getActivePotionEffects()) {
                    p.removePotionEffect(effect.getType());
                }
                SND.gm.removePlayerFromGame(p);
                if (SND.gm.getPlaying().size()==0) {
                    SND.gm.setGameState(GameState.LOBBY);
                }
                SND.gm.updateJoinSign();
            } else if (SND.lm.isInLobby(p)) {
                SND.lm.broadcastMessageInLobby(SND.TAG_RED + p.getName() + " left the lobby.");
                for (PotionEffect effect : p.getActivePotionEffects()) {
                    p.removePotionEffect(effect.getType());
                }
                SND.lm.removePlayerFromLobby(p);
                p.teleport(SND.lh.getExitSpawn());
                SND.gm.updateJoinSign();
            } else if (SND.sm.isSpectator(p)) {
                SND.gm.broadcastMessageInGame(SND.TAG_RED + p.getName() + " stopped spectating.", true);
                for (PotionEffect effect : p.getActivePotionEffects()) {
                    p.removePotionEffect(effect.getType());
                }
                SND.sm.removeSpectator(p);
                p.teleport(SND.lh.getExitSpawn());
                if (SND.gm.getPlaying().size()==0) {
                    SND.gm.setGameState(GameState.LOBBY);
                }
                SND.gm.updateJoinSign();
            } else {
                p.sendMessage(SND.TAG_BLUE + "You are not in a game!");
            }
        }
    }

}
