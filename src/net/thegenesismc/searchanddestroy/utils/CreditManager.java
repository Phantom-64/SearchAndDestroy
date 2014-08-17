package net.thegenesismc.searchanddestroy.utils;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.entity.Player;

public class CreditManager {

    private SND plugin;

    public CreditManager(SND plugin) {
        this.plugin = plugin;
    }

    public void giveCredits(Player p, int credits) {
        if (plugin.getConfig().contains("Credits." + p.getName())) {
            plugin.getConfig().set("Credits." + p.getName(), plugin.getConfig().getInt("Credits." + p.getName()) + credits);
        } else {
            plugin.getConfig().set("Credits." + p.getName(), credits);
        }
    }

    public void setCredits(Player p, int credits) {
        plugin.getConfig().set("Credits." + p.getName(), credits);
    }

    public int getCredits(Player p) {
        if (plugin.getConfig().contains("Credits." + p.getName())) {
            return plugin.getConfig().getInt("Credits." + p.getName());
        }
        return 0;
    }
}
