package net.thegenesismc.searchanddestroy.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SignListener implements Listener {

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction()==Action.RIGHT_CLICK_BLOCK) {
            Block b = e.getClickedBlock();
            if (b.getType()==Material.SIGN_POST||b.getType()==Material.WALL_SIGN) {
                Sign sign = (Sign) b.getState();
                if (sign.getLine(0).equalsIgnoreCase("join")) {
                    Inventory inv = Bukkit.createInventory(null, 9);
                    ItemStack devs = new ItemStack(Material.PAPER, 1);
                    ItemMeta devmeta = devs.getItemMeta();
                    devmeta.setDisplayName("§4Developers");
                    devmeta.setLore(Arrays.asList("§5> §3Phantom_64", "§5> §cJack§bTheDev"));
                    devs.setItemMeta(devmeta);
                    ItemStack kits = new ItemStack(Material.NETHER_STAR, 1);
                    ItemMeta kitmeta = kits.getItemMeta();
                    kitmeta.setDisplayName("§aKits");
                    kitmeta.setLore(Arrays.asList("Click here to choose", "a kit!"));
                    kits.setItemMeta(kitmeta);
                    ItemStack owners = new ItemStack(Material.PAPER, 1);
                    ItemMeta ownermeta = owners.getItemMeta();
                    ownermeta.setDisplayName("§4Owners");
                    ownermeta.setLore(Arrays.asList("§aPrivateFearless", "§6DISCOMONK", "§bXtremeHD21"));
                    owners.setItemMeta(ownermeta);
                    inv.setItem(1, devs);
                    inv.setItem(4, kits);
                    inv.setItem(7, owners);
                    p.openInventory(inv);
                }
            }
        }
    }

}
