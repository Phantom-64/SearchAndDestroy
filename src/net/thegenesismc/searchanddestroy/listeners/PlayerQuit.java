package net.thegenesismc.searchanddestroy.listeners;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (SND.gm.isPlaying(p)) {
            SND.gm.broadcastMessageInGame(SND.TAG_BLUE + SND.tm.getPlayerNameInTeamColor(p) + " §9disconnected.", true);
            SND.gm.getPlaying().remove(p);
            SND.tm.removeFromTeam(p);
            SND.km.clearInventory(p);
            SND.km.removeKit(p);
            e.setQuitMessage("");
            if (SND.tm.getBlue().size()==0) {
                SND.gm.broadcastMessageInGame(SND.TAG_GREEN + "Everyone in §9blue team §ais dead!", true);
                SND.gm.broadcastMessageInGame(SND.TAG_GREEN + "§cRed team §awins!", true);
                for (int i=1;i<4;i++) {
                    SND.shootFirework(SND.lh.getRedSpawn());
                }
                SND.gm.endGame();
            } else if (SND.tm.getRed().size()==0) {
                SND.gm.broadcastMessageInGame(SND.TAG_GREEN + "Everyone in §cred team §ais dead!", true);
                SND.gm.broadcastMessageInGame(SND.TAG_GREEN + "§9Blue team §awins!", true);
                for (int i=1;i<4;i++) {
                    SND.shootFirework(SND.lh.getRedSpawn());
                }
                SND.gm.endGame();
            }
        } else if (SND.sm.isSpectator(p)) {
            SND.sm.removeSpectator(p);
            SND.gm.broadcastMessageInGame(SND.TAG_RED + p.getName() + " stopped spectating.", true);
        } else if (SND.lm.isInLobby(p)) {
            SND.lm.removePlayerFromLobby(p);
            SND.lm.broadcastMessageInLobby(SND.TAG_RED + p.getName() + " left the lobby.");
            if (SND.lm.getLobby().size()==0) {
                if (SND.timer!=500) {
                    Bukkit.getScheduler().cancelTask(SND.timer);
                }
            }
        }
    }

}
