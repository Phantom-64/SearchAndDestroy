package net.thegenesismc.searchanddestroy.listeners;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelListener implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            if (SND.gm.isPlaying((Player)e.getEntity())||SND.lm.isInLobby((Player)e.getEntity())||SND.sm.isSpectator((Player)e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

}
