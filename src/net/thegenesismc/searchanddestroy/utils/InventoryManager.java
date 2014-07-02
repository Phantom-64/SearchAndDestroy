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

    public void openKitSelector(Player p) {
        Inventory inv = Bukkit.createInventory(null, 18, "§4SND §aKit Selector");
        ItemStack assault = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta assaultmeta = assault.getItemMeta();
        assaultmeta.setDisplayName("§bAssault Kit");
        assaultmeta.setLore(Arrays.asList("§7Non Donator Kit"));
        assault.setItemMeta(assaultmeta);
        ItemStack jugger = new ItemStack(Material.STONE_SWORD, 1);
        ItemMeta juggermeta = jugger.getItemMeta();
        juggermeta.setDisplayName("§bJuggernaut Kit");
        juggermeta.setLore(Arrays.asList("§7Non Donator Kit"));
        jugger.setItemMeta(juggermeta);
        ItemStack spy = new ItemStack(Material.ENDER_PEARL, 1);
        ItemMeta spymeta = spy.getItemMeta();
        spymeta.setDisplayName("§bSpy Kit");
        spymeta.setLore(Arrays.asList("§7Non Donator Kit"));
        spy.setItemMeta(spymeta);
        ItemStack sniper = new ItemStack(Material.BOW, 1);
        ItemMeta snipermeta = sniper.getItemMeta();
        snipermeta.setDisplayName("§bSniper Kit");
        snipermeta.setLore(Arrays.asList("§7Non Donator Kit"));
        sniper.setItemMeta(snipermeta);
        ItemStack scout = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta scoutmeta = scout.getItemMeta();
        scoutmeta.setDisplayName("§bScout Kit");
        scoutmeta.setLore(Arrays.asList("§7Non Donator Kit"));
        scout.setItemMeta(scoutmeta);
        ItemStack explosive = new ItemStack(Material.TNT, 1);
        ItemMeta explosivemeta = explosive.getItemMeta();
        explosivemeta.setDisplayName("§aExplosive Kit");
        explosivemeta.setLore(Arrays.asList("§fDonator Kit"));
        explosive.setItemMeta(explosivemeta);
        ItemStack wizard = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta wizardmeta = wizard.getItemMeta();
        wizardmeta.setDisplayName("§aWizard Kit");
        wizardmeta.setLore(Arrays.asList("§fDonator Kit"));
        wizard.setItemMeta(wizardmeta);
        inv.setItem(0, assault);
        inv.setItem(1, jugger);
        inv.setItem(2, spy);
        inv.setItem(3, sniper);
        inv.setItem(4, scout);
        inv.setItem(9, explosive);
        inv.setItem(10, wizard);
        p.openInventory(inv);
    }
}
