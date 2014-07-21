package net.thegenesismc.searchanddestroy.utils;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class LobbyManager {

    private SND plugin;

    public LobbyManager(SND plugin) {
        this.plugin = plugin;
    }

    public int getMinPlayersToStart() {
        return plugin.getConfig().getInt("MinPlayersToStart");
    }

    public void setMinPlayersToStart(int minPlayersToStart) {
        plugin.getConfig().set("MinPlayersToStart", minPlayersToStart);
    }

    private List<Player> lobby = new ArrayList<Player>();

    public List<Player> getLobby() {
        return lobby;
    }

    public boolean isInLobby(Player p) {
        return lobby.contains(p);
    }

    public void addPlayerToLobby(Player p) {
        lobby.add(p);
        p.getInventory().clear();
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        p.setGameMode(GameMode.ADVENTURE);
        p.setHealth(20.0);
        p.setFoodLevel(20);
        p.teleport(SND.lh.getLobbySpawn());
    }

    public void removePlayerFromLobby(Player p) {
        lobby.remove(p);
        p.getInventory().clear();
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        p.setGameMode(GameMode.ADVENTURE);
        p.setHealth(20.0);
        p.setFoodLevel(20);
    }

    public void broadcastMessageInLobby(String message) {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (SND.lm.getLobby().contains(pl)) {
                pl.sendMessage(message);
            }
        }
    }
}
