package net.thegenesismc.searchanddestroy.commands;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.entity.Player;

public class CommandSetBombSpawn {

    public static void execute(Player p, String[] a) {

        if (!p.isOp()) {
            p.sendMessage(SND.TAG_RED + "You don't have permission to do this!");
            return;
        }

        if (a.length!=2) {
            p.sendMessage(SND.TAG_BLUE + "Usage: /snd setbombspawn <red/blue>");
        } else {
            if (a[1].equalsIgnoreCase("red")) {
                SND.lh.setRedBombSpawn(p.getLocation());
                p.sendMessage(SND.TAG_GREEN + "§cRed bomb spawn §aset!");
            } else if (a[1].equalsIgnoreCase("blue")) {
                SND.lh.setBlueBombSpawn(p.getLocation());
                p.sendMessage(SND.TAG_GREEN + "§9Blue bomb spawn §aset!");
            } else {
                p.sendMessage(SND.TAG_BLUE + "Invalid bomb spawn type. Available bomb spawn types: red, blue");
            }
        }

    }

}
