package net.thegenesismc.searchanddestroy.utils;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpectatorManager {

    private SND plugin;

    public SpectatorManager(SND plugin) {
        this.plugin = plugin;
    }

    private List<Player> spectators = new ArrayList<Player>();

    public List<Player> getSpectators() {
        return spectators;
    }

    public boolean isSpectator(Player p) {
        return spectators.contains(p);
    }

    public void setSpectator(Player p) {
        SND.sm.getSpectators().add(p);
        p.getInventory().clear();
        p.setCanPickupItems(false);
        p.setAllowFlight(true);
        p.setFlying(true);
        p.setFireTicks(0);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.hidePlayer(p);
        }
        p.teleport(SND.lh.getSpectatorSpawn());
    }

    public void removeSpectator(Player p) {
        SND.sm.getSpectators().remove(p);
        p.setCanPickupItems(true);
        p.setAllowFlight(false);
        p.setFlying(false);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.showPlayer(p);
        }
    }
}
