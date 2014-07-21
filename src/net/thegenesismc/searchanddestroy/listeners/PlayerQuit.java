package net.thegenesismc.searchanddestroy.listeners;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (SND.gm.isPlaying(p)) {
            SND.gm.getPlaying().remove(p);
            SND.tm.removeFromTeam(p);
            SND.km.clearInventory(p);
            SND.km.removeKit(p);
        } else if (SND.sm.isSpectator(p)) {

        }
    }

}
