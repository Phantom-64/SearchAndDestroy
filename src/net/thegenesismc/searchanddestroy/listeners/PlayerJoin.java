package net.thegenesismc.searchanddestroy.listeners;

import net.thegenesismc.searchanddestroy.SND;
import net.thegenesismc.searchanddestroy.utils.LocationHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        LocationHandler lh = SND.lh;
        if (e.getPlayer().isOp()) {
            if (lh.getRedBombSpawn()==null||lh.getBlueBombSpawn()==null||lh.getRedSpawn()==null||lh.getBlueSpawn()==null||lh.getExitSpawn()==null) {
                e.getPlayer().sendMessage(SND.TAG_BLUE + "All the spawns are not set.");
            }
        }
    }

}
