package net.thegenesismc.searchanddestroy.utils;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.entity.Player;

public class CreditManager {

    private SND plugin;

    public CreditManager(SND plugin) {
        this.plugin = plugin;
    }

    public void giveCredits(Player p, int credits) {
        //Used to give credits
        p.sendMessage(SND.TAG_GREEN + "You earned §7[" + "§b" + credits + "§7] §acredits!");
    }

    public void setCredits(Player p, int credits) {
        //Mostly used to reset credits (set them to 0)
    }

    public int getCredits(Player p) {
        //Getter for credits
        return 0;
    }
}
