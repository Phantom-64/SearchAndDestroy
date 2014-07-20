package net.thegenesismc.searchanddestroy.listeners;

import net.thegenesismc.searchanddestroy.SND;
import net.thegenesismc.searchanddestroy.utils.Kit;
import net.thegenesismc.searchanddestroy.utils.Team;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void onTeamSelect(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            ItemStack kit = e.getCurrentItem();
            if (kit.getItemMeta().getDisplayName().equals("§bAssault Kit")&&kit.getType()==Material.IRON_SWORD) {
                Team team = SND.tm.getValidTeam();
                SND.gm.addPlayerToGame(p, team, Kit.ASSAULT);
                SND.gm.broadcastMessageInGame(SND.TAG_GREEN + SND.tm.getPlayerNameInTeamColor(p) + " §ajoined the lobby. " + SND.gm.getPlaying().size() + "/" + SND.gm.getPlayerLimit());
            }
        }
    }
}
