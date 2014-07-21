package net.thegenesismc.searchanddestroy.listeners;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (SND.gm.isPlaying(p)) {
            e.getDrops().clear();
            if (p.getKiller() instanceof Player) {
                Player killer = p.getKiller();
                SND.gm.broadcastMessageInGame(SND.TAG_BLUE + SND.tm.getPlayerNameInTeamColor(p) + " §9was killed by " + SND.tm.getPlayerNameInTeamColor(killer) + "§9.", true);
            } else {
                SND.gm.broadcastMessageInGame(SND.TAG_BLUE + SND.tm.getPlayerNameInTeamColor(p) + " §9died.");
            }
            e.setDeathMessage("");
            p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 1, 1);
            p.setHealth(20.0);
            p.setFoodLevel(20);
            SND.gm.removePlayerFromGame(p);
            SND.sm.setSpectator(p);
            SND.gm.updateJoinSign();
        }
    }

}
