package net.thegenesismc.searchanddestroy.listeners;

import net.thegenesismc.searchanddestroy.SND;
import net.thegenesismc.searchanddestroy.utils.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * ASSAULT,
 JUGGERNAUT,
 SPY,
 SNIPER,
 SCOUT,
 EXPLOSIVE,
 WIZARD;
 */

public class InventoryListener implements Listener {

    @EventHandler
    public void onTeamSelect(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            try {
                Player p = (Player) e.getWhoClicked();
                ItemStack kit = e.getCurrentItem();
                String name = kit.getItemMeta().getDisplayName();
                Material m = kit.getType();
                if (name.equals("§bAssault Kit")&&m==Material.IRON_SWORD) {
                    e.setCancelled(true);
                    p.closeInventory();
                    SND.km.setKit(p, Kit.ASSAULT, SND.tm.getTeam(p));
                } else if (name.equals("§bJuggernaut Kit")&&m==Material.STONE_SWORD) {
                    e.setCancelled(true);
                    p.closeInventory();
                    SND.km.setKit(p, Kit.JUGGERNAUT, SND.tm.getTeam(p));
                } else if (name.equals("§bSpy Kit")&&m==Material.ENDER_PEARL) {
                    e.setCancelled(true);
                    p.closeInventory();
                    SND.km.setKit(p, Kit.SPY, SND.tm.getTeam(p));
                } else if (name.equals("§bSniper Kit")&&m==Material.BOW) {
                    e.setCancelled(true);
                    p.closeInventory();
                    SND.km.setKit(p, Kit.SNIPER, SND.tm.getTeam(p));
                } else if (name.equals("§bScout Kit")&&m==Material.DIAMOND_AXE) {
                    e.setCancelled(true);
                    p.closeInventory();
                    SND.km.setKit(p, Kit.SCOUT, SND.tm.getTeam(p));
                } else if (name.equals("§aExplosive Kit")&&m==Material.TNT) {
                    e.setCancelled(true);
                    p.closeInventory();
                    SND.km.setKit(p, Kit.EXPLOSIVE, SND.tm.getTeam(p));
            } else if (name.equals("§aWizard Kit")&&m==Material.BLAZE_ROD) {
                    e.setCancelled(true);
                    p.closeInventory();
                    SND.km.setKit(p, Kit.WIZARD, SND.tm.getTeam(p));
                }
            } catch (NullPointerException npe) {}
        }
    }
}
