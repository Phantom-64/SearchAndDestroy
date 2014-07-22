package net.thegenesismc.searchanddestroy.listeners;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class NoDropListener implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (SND.gm.isPlaying(p)||SND.lm.isInLobby(p)||SND.sm.isSpectator(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if (SND.gm.isPlaying(p)||SND.lm.isInLobby(p)||SND.sm.isSpectator(p)) {
                if (p.getInventory().contains(e.getCurrentItem())) {
                    e.setCancelled(true);
                    p.closeInventory();
                }
            }
        }
    }

}
