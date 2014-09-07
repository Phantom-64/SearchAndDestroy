package net.thegenesismc.searchanddestroy.listeners;

import net.thegenesismc.searchanddestroy.SND;
import net.thegenesismc.searchanddestroy.utils.LocationHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLogin implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        LocationHandler lh = SND.lh;
        if (!e.getPlayer().isOp()) {
            if (lh.getRedBombSpawn()==null||lh.getBlueBombSpawn()==null||lh.getRedSpawn()==null||lh.getBlueSpawn()==null||lh.getExitSpawn()==null) {
                e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                e.setKickMessage("   " + SND.TAG_RED + "\n" + "§cThe game has not been set up!");
            }
        }
    }

}
