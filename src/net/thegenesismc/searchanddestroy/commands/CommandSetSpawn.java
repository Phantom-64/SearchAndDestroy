package net.thegenesismc.searchanddestroy.commands;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.entity.Player;

public class CommandSetSpawn {

    public static void execute(Player p, String[] a) {

        if (!p.isOp()) {
            p.sendMessage(SND.TAG_RED + "You don't have permission to do this!");
            return;
        }

        if (a.length!=2) {
            p.sendMessage(SND.TAG_BLUE + "Usage: /snd setspawn <red/blue/exit/lobby/spectator>");
        } else {
            if (a[1].equalsIgnoreCase("red")) {
                SND.lh.setRedSpawn(p.getLocation());
                p.sendMessage(SND.TAG_GREEN + "§cRed spawn §aset!");
            } else if (a[1].equalsIgnoreCase("blue")) {
                SND.lh.setBlueSpawn(p.getLocation());
                p.sendMessage(SND.TAG_GREEN + "§9Blue spawn §aset!");
            } else if (a[1].equalsIgnoreCase("exit")) {
                SND.lh.setExitSpawn(p.getLocation());
                p.sendMessage(SND.TAG_GREEN + "Exit spawn set!");
            } else if (a[1].equalsIgnoreCase("lobby")) {
                SND.lh.setLobbySpawn(p.getLocation());
                p.sendMessage(SND.TAG_GREEN + "Lobby spawn set!");
            } else if (a[1].equalsIgnoreCase("spectator")) {
                SND.lh.setSpectatorSpawn(p.getLocation());
                p.sendMessage(SND.TAG_GREEN + "Spectator spawn set!");
            } else {
                p.sendMessage(SND.TAG_BLUE + "Invalid spawn type. Available spawn types: red, blue, exit, lobby, spectator");
            }
        }

    }

}
