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
 * Assault-
 Sniper-
 Banter
 Ghost-
 Scout-
 Juggernaut-
 Assassin
 Explosive-
 Wizard-
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
                if (name.equals("§aAssault Kit")&&m==Material.IRON_SWORD) {
                    p.getInventory().remove(new ItemStack(Material.NETHER_STAR, 1));
                    e.setCancelled(true);
                    p.closeInventory();
                    SND.km.setKit(p, Kit.ASSAULT, SND.tm.getTeam(p));
                } else if (name.equals("§bJuggernaut Kit")&&m==Material.STONE_SWORD) {
                    if (p.hasPermission("snd.donator")) {
                        p.getInventory().remove(new ItemStack(Material.NETHER_STAR, 1));
                        e.setCancelled(true);
                        p.closeInventory();
                        SND.km.setKit(p, Kit.JUGGERNAUT, SND.tm.getTeam(p));
                    } else {
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage(SND.TAG_RED + "You don't have permission for this kit!");
                    }
                } else if (name.equals("§aGhost Kit")&&m==Material.ENDER_PEARL) {
                    p.getInventory().remove(new ItemStack(Material.NETHER_STAR, 1));
                    e.setCancelled(true);
                    p.closeInventory();
                    SND.km.setKit(p, Kit.GHOST, SND.tm.getTeam(p));
                } else if (name.equals("§aSniper Kit")&&m==Material.BOW) {
                    p.getInventory().remove(new ItemStack(Material.NETHER_STAR, 1));
                    e.setCancelled(true);
                    p.closeInventory();
                    SND.km.setKit(p, Kit.SNIPER, SND.tm.getTeam(p));
                } else if (name.equals("§aScout Kit")&&m==Material.DIAMOND_AXE) {
                    p.getInventory().remove(new ItemStack(Material.NETHER_STAR, 1));
                    e.setCancelled(true);
                    p.closeInventory();
                    SND.km.setKit(p, Kit.SCOUT, SND.tm.getTeam(p));
                } else if (name.equals("§bExplosive Kit")&&m==Material.TNT) {
                    if (p.hasPermission("snd.donator")) {
                        p.getInventory().remove(new ItemStack(Material.NETHER_STAR, 1));
                        e.setCancelled(true);
                        p.closeInventory();
                        SND.km.setKit(p, Kit.EXPLOSIVE, SND.tm.getTeam(p));
                    } else {
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage(SND.TAG_RED + "You don't have permission for this kit!");
                    }
                } else if (name.equals("§bWizard Kit")&&m==Material.BLAZE_ROD) {
                    if (p.hasPermission("snd.donator")) {
                        p.getInventory().remove(new ItemStack(Material.NETHER_STAR, 1));
                        e.setCancelled(true);
                        p.closeInventory();
                        SND.km.setKit(p, Kit.WIZARD, SND.tm.getTeam(p));
                    } else {
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage(SND.TAG_RED + "You don't have permission for this kit!");
                    }
                } else if (name.equals("§aBanter Kit")&&m==Material.WOOD_SWORD) {
                    p.getInventory().remove(new ItemStack(Material.NETHER_STAR, 1));
                    e.setCancelled(true);
                    p.closeInventory();
                    SND.km.setKit(p, Kit.BANTER, SND.tm.getTeam(p));
                } else if (name.equals("§bAssassin Kit")&&m==Material.IRON_CHESTPLATE) {
                    if (p.hasPermission("snd.donator")) {
                        p.getInventory().remove(new ItemStack(Material.NETHER_STAR, 1));
                        e.setCancelled(true);
                        p.closeInventory();
                        SND.km.setKit(p, Kit.ASSASSIN, SND.tm.getTeam(p));
                    } else {
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage(SND.TAG_RED + "You don't have permission for this kit!");
                    }
                }
            } catch (NullPointerException npe) {}
            /**
             * Assault-
             Sniper-
             Banter
             Ghost-
             Scout-
             Juggernaut-
             Assassin
             Explosive-
             Wizard-
             */
        }
    }
}
