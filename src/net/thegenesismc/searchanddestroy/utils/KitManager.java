package net.thegenesismc.searchanddestroy.utils;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ---- Kits --------------------------------------------------------------------------------------
 - Assault - Iron sword - x2 Golden apple(s) - Iron armour
 - Juggernaut - Stone sword - slowness (/speed 0.5) - Iron armour (Protection 4)
 - Spy - Gold sword (Fire aspect 2) - X3 EnderPearls - Invisible - ( No armour )
 - Sniper - Wood sword - Bow (Power 2, Knock back 3, infinity) - Chain armour
 - Scout - Diamond axe - Fishing Rod - Speed 2 - Gold armour (Unbreaking 3)

 > Explosive - Stone sword (Sharpness 1) - X6 Snowballs (Grenades) - x2 Stone Pressure plates - Chain armour
 > Wizard: Fire - Stone sword - Blaze rod (Fire | 2 tics) - Chain armour
 ---- Kits --------------------------------------------------------------------------------------
 Pressure plates = If an enemy team steps on one, they instantly die

 ALL Non-Donator kits expire within a month (This means they have to re-buy it every month)
 */

public class KitManager {

    private SND plugin;

    public KitManager(SND plugin) {
        this.plugin = plugin;
    }

    private Map<Player, Kit> kits = new HashMap<Player, Kit>();

    public void setKit(Player p, Kit kit, Team team) {
        kits.put(p, kit);
        PlayerInventory inv = p.getInventory();
        inv.setHelmet(new ItemStack(Material.AIR));
        inv.setChestplate(new ItemStack(Material.AIR));
        inv.setLeggings(new ItemStack(Material.AIR));
        inv.setBoots(new ItemStack(Material.AIR));
        inv.clear();
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        if (kit==Kit.ASSAULT) {
            inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
            inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
            inv.setBoots(new ItemStack(Material.IRON_BOOTS, 1));
            inv.addItem(new ItemStack(Material.IRON_SWORD, 1));
            inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
            ItemStack fuse = new ItemStack(Material.BLAZE_POWDER, 1);
            ItemMeta fusemeta = fuse.getItemMeta();
            fusemeta.setDisplayName("§5Fuse");
            fusemeta.setLore(Arrays.asList("Use this to light the", "other team's bomb!"));
            fuse.setItemMeta(fusemeta);
            inv.setItem(8, fuse);
        } else if (kit==Kit.JUGGERNAUT) {
            ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE, 1);
            chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
            leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);
            boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            inv.setChestplate(chest);
            inv.setLeggings(leggings);
            inv.setBoots(boots);
            inv.addItem(new ItemStack(Material.STONE_SWORD, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 1));
            ItemStack fuse = new ItemStack(Material.BLAZE_POWDER, 1);
            ItemMeta fusemeta = fuse.getItemMeta();
            fusemeta.setDisplayName("§5Fuse");
            fusemeta.setLore(Arrays.asList("Use this to light the", "other team's bomb!"));
            fuse.setItemMeta(fusemeta);
            inv.setItem(8, fuse);
        } else if (kit==Kit.SPY) {
            ItemStack sword = new ItemStack(Material.GOLD_SWORD, 1);
            sword.addEnchantment(Enchantment.FIRE_ASPECT, 2);
            sword.addEnchantment(Enchantment.DURABILITY, 3);
            inv.addItem(sword);
            inv.addItem(new ItemStack(Material.ENDER_PEARL, 3));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999999, 1));
            ItemStack fuse = new ItemStack(Material.BLAZE_POWDER, 1);
            ItemMeta fusemeta = fuse.getItemMeta();
            fusemeta.setDisplayName("§5Fuse");
            fusemeta.setLore(Arrays.asList("Use this to light the", "other team's bomb!"));
            fuse.setItemMeta(fusemeta);
            inv.setItem(8, fuse);
        } else if (kit==Kit.SNIPER) {
            ItemStack bow = new ItemStack(Material.BOW, 1);
            bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
            bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
            bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
            inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
            inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
            inv.setBoots(new ItemStack(Material.IRON_BOOTS, 1));
            inv.addItem(new ItemStack(Material.WOOD_SWORD, 1));
            inv.addItem(bow);
            inv.addItem(new ItemStack(Material.ARROW, 1));
            ItemStack fuse = new ItemStack(Material.BLAZE_POWDER, 1);
            ItemMeta fusemeta = fuse.getItemMeta();
            fusemeta.setDisplayName("§5Fuse");
            fusemeta.setLore(Arrays.asList("Use this to light the", "other team's bomb!"));
            fuse.setItemMeta(fusemeta);
            inv.setItem(8, fuse);
        } else if (kit==Kit.SCOUT) {
            ItemStack chest = new ItemStack(Material.GOLD_CHESTPLATE, 1);
            chest.addEnchantment(Enchantment.DURABILITY, 3);
            ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS, 1);
            leggings.addEnchantment(Enchantment.DURABILITY, 3);
            ItemStack boots = new ItemStack(Material.GOLD_BOOTS, 1);
            boots.addEnchantment(Enchantment.DURABILITY, 3);
            inv.setChestplate(chest);
            inv.setLeggings(leggings);
            inv.setBoots(boots);
            inv.addItem(new ItemStack(Material.DIAMOND_AXE, 1));
            inv.addItem(new ItemStack(Material.FISHING_ROD, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
            ItemStack fuse = new ItemStack(Material.BLAZE_POWDER, 1);
            ItemMeta fusemeta = fuse.getItemMeta();
            fusemeta.setDisplayName("§5Fuse");
            fusemeta.setLore(Arrays.asList("Use this to light the", "other team's bomb!"));
            fuse.setItemMeta(fusemeta);
            inv.setItem(8, fuse);
        } else if (kit==Kit.EXPLOSIVE) {
            ItemStack sword = new ItemStack(Material.STONE_SWORD, 1);
            sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
            ItemStack grenades = new ItemStack(Material.SNOW_BALL, 6);
            ItemMeta grenademeta = grenades.getItemMeta();
            grenademeta.setDisplayName("Grenade");
            grenades.setItemMeta(grenademeta);
            ItemStack plates = new ItemStack(Material.STONE_PLATE, 2);
            ItemMeta platemeta = plates.getItemMeta();
            platemeta.setDisplayName("Death plates");
            plates.setItemMeta(platemeta);
            ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
            chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
            leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
            boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            inv.setChestplate(chest);
            inv.setLeggings(leggings);
            inv.setBoots(boots);
            inv.addItem(sword);
            inv.addItem(grenades);
            inv.addItem(plates);
            ItemStack fuse = new ItemStack(Material.BLAZE_POWDER, 1);
            ItemMeta fusemeta = fuse.getItemMeta();
            fusemeta.setDisplayName("§5Fuse");
            fusemeta.setLore(Arrays.asList("Use this to light the", "other team's bomb!"));
            fuse.setItemMeta(fusemeta);
            inv.setItem(8, fuse);
        } else if (kit==Kit.WIZARD) {
            ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
            chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
            leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
            boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            ItemStack staff = new ItemStack(Material.BLAZE_ROD, 1);
            ItemMeta staffmeta = staff.getItemMeta();
            staffmeta.setDisplayName("Staff");
            staffmeta.setLore(Arrays.asList("Left click to shoot fireballs!"));
            staff.setItemMeta(staffmeta);
            ItemStack sword = new ItemStack(Material.STONE_SWORD, 1);
            sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
            inv.setChestplate(chest);
            inv.setLeggings(leggings);
            inv.setBoots(boots);
            inv.addItem(sword);
            inv.addItem(staff);
            ItemStack fuse = new ItemStack(Material.BLAZE_POWDER, 1);
            ItemMeta fusemeta = fuse.getItemMeta();
            fusemeta.setDisplayName("§5Fuse");
            fusemeta.setLore(Arrays.asList("Use this to light the", "other team's bomb!"));
            fuse.setItemMeta(fusemeta);
            inv.setItem(8, fuse);
        }

        if (kit!=Kit.SPY) {
            if (team==Team.RED) {
                ItemStack red = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
                inv.setHelmet(red);
            } else if (team==Team.BLUE) {
                ItemStack blue = new ItemStack(Material.WOOL, 1, DyeColor.BLUE.getData());
                inv.setHelmet(blue);
            }
        }
    }

    public void removeKit(Player p) {
        kits.remove(p);
    }

    public Kit getKit(Player p) {
        if (kits.containsKey(p)) {
            return kits.get(p);
        }
        return null;
    }

    public void clearInventory(Player p) {
        PlayerInventory inv = p.getInventory();
        inv.setHelmet(new ItemStack(Material.AIR));
        inv.setChestplate(new ItemStack(Material.AIR));
        inv.setLeggings(new ItemStack(Material.AIR));
        inv.setBoots(new ItemStack(Material.AIR));
        inv.clear();
    }
}
