package net.thegenesismc.searchanddestroy.listeners;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onSpecDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (SND.sm.isSpectator(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSpecDamageByBlock(EntityDamageByBlockEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (SND.sm.isSpectator(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEndDamage(EntityDamageEvent e) {
        if (SND.gm.isEnded()) {
            e.setCancelled(true);
        }
    }

}
