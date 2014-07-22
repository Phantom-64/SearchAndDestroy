package net.thegenesismc.searchanddestroy.listeners;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitAndJoin implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (SND.gm.isPlaying(p)) {
            SND.gm.getPlaying().remove(p);
            SND.tm.removeFromTeam(p);
            SND.km.clearInventory(p);
            SND.km.removeKit(p);
            SND.gm.broadcastMessageInGame(SND.TAG_RED + SND.tm.getPlayerNameInTeamColor(p) + " §cleft the game!", true);
        } else if (SND.sm.isSpectator(p)) {
            SND.sm.removeSpectator(p);
            SND.gm.broadcastMessageInGame(SND.TAG_RED + SND.tm.getPlayerNameInTeamColor(p) + " §cstopped spectating.", true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.teleport(SND.lh.getExitSpawn());
    }

}
