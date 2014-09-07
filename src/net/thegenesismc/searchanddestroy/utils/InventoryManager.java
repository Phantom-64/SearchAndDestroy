package net.thegenesismc.searchanddestroy.utils;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * ASSAULT,
 JUGGERNAUT,
 SPY,
 SNIPER,
 SCOUT,
 EXPLOSIVE,
 WIZARD;

 - Assault - Iron sword - x2 Golden apple(s) - Iron armour
 - Juggernaut - Stone sword - slowness (/speed 0.5) - Iron armour (Protection 4)
 - Spy - Gold sword (Fire aspect 2) - X3 EnderPearls - Invisible - ( No armour )
 - Sniper - Wood sword - Bow (Power 2, Knock back 3, infinity) - Chain armour
 - Scout - Diamond axe - Fishing Rod - Speed 2 - Gold armour (Unbreaking 3)

 > Explosive - Stone sword (Sharpness 1) - X6 Snowballs (Grenades) - x2 Stone Pressure plates - Chain armour
 > Wizard: Fire - Stone sword - Blaze rod (Fire | 2 tics) - Chain armour
 */

public class InventoryManager {

    private SND plugin;

    public InventoryManager(SND plugin) {
        this.plugin = plugin;
    }

    public void openMainMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, "§cTheGenesis §4SND");
        ItemStack devs = new ItemStack(Material.EMPTY_MAP, 1);
        ItemMeta devmeta = devs.getItemMeta();
        devmeta.setDisplayName("§4Developers");
        devmeta.setLore(Arrays.asList("§5> §3Phantom_64", "§5> §cJack§bTheDev"));
        devs.setItemMeta(devmeta);
        ItemStack owners = new ItemStack(Material.EMPTY_MAP, 1);
        ItemMeta ownermeta = owners.getItemMeta();
        ownermeta.setDisplayName("§4Owners");
        ownermeta.setLore(Arrays.asList("§5> §aPrivateFearless", "§5> §6DISCOMONK", "§5> §bXtremeHD21"));
        owners.setItemMeta(ownermeta);
        ItemStack kits = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta kitmeta = kits.getItemMeta();
        kitmeta.setDisplayName("§aKits");
        kits.setItemMeta(kitmeta);
        inv.setItem(1, devs);
        inv.setItem(4, kits);
        inv.setItem(7, owners);
        p.openInventory(inv);
    }

    /**
     * Assault-
     Sniper-
     Banter-
     Ghost-
     Scout-
     Juggernaut-
     Assassin-
     Explosive-
     Wizard-
     */

    public void openKitSelector(Player p) {
        if(!p.hasPermission("snd.donator")) {
            Inventory inv = Bukkit.createInventory(null, 18, "§4SND §aKit Selector");
            ItemStack assault = new ItemStack(Material.IRON_SWORD, 1);
            ItemMeta assaultmeta = assault.getItemMeta();
            assaultmeta.setDisplayName("§aAssault Kit");
            assaultmeta.setLore(Arrays.asList("Free"));
            assault.setItemMeta(assaultmeta);
            ItemStack jugger = new ItemStack(Material.STONE_SWORD, 1);
            ItemMeta juggermeta = jugger.getItemMeta();
            juggermeta.setDisplayName("§bJuggernaut Kit");
            juggermeta.setLore(Arrays.asList("Cost: " + Kit.JUGGERNAUT.getCost()));
            jugger.setItemMeta(juggermeta);
            ItemStack ghost = new ItemStack(Material.ENDER_PEARL, 1);
            ItemMeta ghostmeta = ghost.getItemMeta();
            ghostmeta.setDisplayName("§aGhost Kit");
            ghostmeta.setLore(Arrays.asList("Cost: " + Kit.GHOST.getCost()));
            ghost.setItemMeta(ghostmeta);
            ItemStack sniper = new ItemStack(Material.BOW, 1);
            ItemMeta snipermeta = sniper.getItemMeta();
            snipermeta.setDisplayName("§aSniper Kit");
            snipermeta.setLore(Arrays.asList("Free"));
            sniper.setItemMeta(snipermeta);
            ItemStack scout = new ItemStack(Material.DIAMOND_AXE, 1);
            ItemMeta scoutmeta = scout.getItemMeta();
            scoutmeta.setDisplayName("§aScout Kit");
            scoutmeta.setLore(Arrays.asList("Cost: " + Kit.SCOUT.getCost()));
            scout.setItemMeta(scoutmeta);
            ItemStack explosive = new ItemStack(Material.TNT, 1);
            ItemMeta explosivemeta = explosive.getItemMeta();
            explosivemeta.setDisplayName("§bExplosive Kit");
            explosivemeta.setLore(Arrays.asList("Cost: " + Kit.EXPLOSIVE.getCost()));
            explosive.setItemMeta(explosivemeta);
            ItemStack wizard = new ItemStack(Material.BLAZE_ROD, 1);
            ItemMeta wizardmeta = wizard.getItemMeta();
            wizardmeta.setDisplayName("§bWizard Kit");
            wizardmeta.setLore(Arrays.asList("Cost: " + Kit.WIZARD.getCost()));
            wizard.setItemMeta(wizardmeta);
            ItemStack banter = new ItemStack(Material.WOOD_SWORD, 1);
            ItemMeta bantermeta = banter.getItemMeta();
            bantermeta.setDisplayName("§aBanter Kit");
            bantermeta.setLore(Arrays.asList("Cost: " + Kit.BANTER.getCost()));
            banter.setItemMeta(bantermeta);
            ItemStack ass = new ItemStack(Material.IRON_CHESTPLATE, 1);
            ItemMeta assmeta = ass.getItemMeta();
            assmeta.setDisplayName("§bAssassin Kit");
            assmeta.setLore(Arrays.asList("Cost: " + Kit.ASSASSIN.getCost()));
            ass.setItemMeta(assmeta);
            inv.setItem(0, assault);
            inv.setItem(1, sniper);
            inv.setItem(2, banter);
            inv.setItem(3, ghost);
            inv.setItem(4, scout);
            inv.setItem(9, jugger);
            inv.setItem(10, ass);
            inv.setItem(11, explosive);
            inv.setItem(12, wizard);
            p.openInventory(inv);
        } else {
            Inventory inv = Bukkit.createInventory(null, 18, "§4SND §aKit Selector");
            ItemStack assault = new ItemStack(Material.IRON_SWORD, 1);
            ItemMeta assaultmeta = assault.getItemMeta();
            assaultmeta.setDisplayName("§aAssault Kit");
            assault.setItemMeta(assaultmeta);
            ItemStack jugger = new ItemStack(Material.STONE_SWORD, 1);
            ItemMeta juggermeta = jugger.getItemMeta();
            juggermeta.setDisplayName("§bJuggernaut Kit");
            jugger.setItemMeta(juggermeta);
            ItemStack ghost = new ItemStack(Material.ENDER_PEARL, 1);
            ItemMeta ghostmeta = ghost.getItemMeta();
            ghostmeta.setDisplayName("§aGhost Kit");
            ghost.setItemMeta(ghostmeta);
            ItemStack sniper = new ItemStack(Material.BOW, 1);
            ItemMeta snipermeta = sniper.getItemMeta();
            snipermeta.setDisplayName("§aSniper Kit");
            sniper.setItemMeta(snipermeta);
            ItemStack scout = new ItemStack(Material.DIAMOND_AXE, 1);
            ItemMeta scoutmeta = scout.getItemMeta();
            scoutmeta.setDisplayName("§aScout Kit");
            scout.setItemMeta(scoutmeta);
            ItemStack explosive = new ItemStack(Material.TNT, 1);
            ItemMeta explosivemeta = explosive.getItemMeta();
            explosivemeta.setDisplayName("§bExplosive Kit");
            explosive.setItemMeta(explosivemeta);
            ItemStack wizard = new ItemStack(Material.BLAZE_ROD, 1);
            ItemMeta wizardmeta = wizard.getItemMeta();
            wizardmeta.setDisplayName("§bWizard Kit");
            wizard.setItemMeta(wizardmeta);
            ItemStack banter = new ItemStack(Material.WOOD_SWORD, 1);
            ItemMeta bantermeta = banter.getItemMeta();
            bantermeta.setDisplayName("§aBanter Kit");
            banter.setItemMeta(bantermeta);
            ItemStack ass = new ItemStack(Material.IRON_CHESTPLATE, 1);
            ItemMeta assmeta = ass.getItemMeta();
            assmeta.setDisplayName("§bAssassin Kit");
            ass.setItemMeta(assmeta);
            inv.setItem(0, assault);
            inv.setItem(1, sniper);
            inv.setItem(2, banter);
            inv.setItem(3, ghost);
            inv.setItem(4, scout);
            inv.setItem(9, jugger);
            inv.setItem(10, ass);
            inv.setItem(11, explosive);
            inv.setItem(12, wizard);
            p.openInventory(inv);
        }
    }

    public void giveKitSelector(Player p) {
        ItemStack kitselector = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta kitmeta = kitselector.getItemMeta();
        kitmeta.setDisplayName("§bKit Selector");
        kitmeta.setLore(Arrays.asList("Right click this to select", "your kit!"));
        kitselector.setItemMeta(kitmeta);
        p.getInventory().addItem(kitselector);
    }
}
