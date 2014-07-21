package net.thegenesismc.searchanddestroy.utils;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationHandler {

    private SND plugin;

    public LocationHandler(SND plugin) {
        this.plugin = plugin;
    }

    public Location getRedSpawn() {
        if (plugin.getConfig().contains("Spawns.red")) {
            return new Location(Bukkit.getWorld(plugin.getConfig().getString("Spawns.red.world")),
                    plugin.getConfig().getInt("Spawns.red.x"), plugin.getConfig().getInt("Spawns.red.y"), plugin.getConfig().getInt("Spawns.red.z"));
        }
        return null;
    }

    public void setRedSpawn(Location redSpawn) {
        plugin.getConfig().set("Spawns.red.world", redSpawn.getWorld().getName());
        plugin.getConfig().set("Spawns.red.x", redSpawn.getBlockX());
        plugin.getConfig().set("Spawns.red.y", redSpawn.getBlockY());
        plugin.getConfig().set("Spawns.red.z", redSpawn.getBlockZ());
        plugin.saveConfig();
    }

    public Location getBlueSpawn() {
        if (plugin.getConfig().contains("Spawns.blue")) {
            return new Location(Bukkit.getWorld(plugin.getConfig().getString("Spawns.blue.world")),
                    plugin.getConfig().getInt("Spawns.blue.x"), plugin.getConfig().getInt("Spawns.blue.y"), plugin.getConfig().getInt("Spawns.blue.z"));
        }
        return null;
    }

    public void setBlueSpawn(Location blueSpawn) {
        plugin.getConfig().set("Spawns.blue.world", blueSpawn.getWorld().getName());
        plugin.getConfig().set("Spawns.blue.x", blueSpawn.getBlockX());
        plugin.getConfig().set("Spawns.blue.y", blueSpawn.getBlockY());
        plugin.getConfig().set("Spawns.blue.z", blueSpawn.getBlockZ());
        plugin.saveConfig();
    }

    public Location getExitSpawn() {
        if (plugin.getConfig().contains("Spawns.exit")) {
            return new Location(Bukkit.getWorld(plugin.getConfig().getString("Spawns.exit.world")),
                    plugin.getConfig().getInt("Spawns.exit.x"), plugin.getConfig().getInt("Spawns.exit.y"), plugin.getConfig().getInt("Spawns.exit.z"));
        }
        return null;
    }

    public void setExitSpawn(Location exitSpawn) {
        plugin.getConfig().set("Spawns.exit.world", exitSpawn.getWorld().getName());
        plugin.getConfig().set("Spawns.exit.x", exitSpawn.getBlockX());
        plugin.getConfig().set("Spawns.exit.y", exitSpawn.getBlockY());
        plugin.getConfig().set("Spawns.exit.z", exitSpawn.getBlockZ());
        plugin.saveConfig();
    }

    public Location getRedBombSpawn() {
        if (plugin.getConfig().contains("BombSpawns.red")) {
            return new Location(Bukkit.getWorld(plugin.getConfig().getString("BombSpawns.red.world")),
                    plugin.getConfig().getInt("BombSpawns.red.x"), plugin.getConfig().getInt("BombSpawns.red.y"), plugin.getConfig().getInt("BombSpawns.red.z"));
        }
        return null;
    }

    public void setRedBombSpawn(Location redBombSpawn) {
        plugin.getConfig().set("BombSpawns.red.world", redBombSpawn.getWorld().getName());
        plugin.getConfig().set("BombSpawns.red.x", redBombSpawn.getBlockX());
        plugin.getConfig().set("BombSpawns.red.y", redBombSpawn.getBlockY());
        plugin.getConfig().set("BombSpawns.red.z", redBombSpawn.getBlockZ());
        plugin.saveConfig();
    }

    public Location getBlueBombSpawn() {
        if (plugin.getConfig().contains("BombSpawns.blue")) {
            return new Location(Bukkit.getWorld(plugin.getConfig().getString("BombSpawns.blue.world")),
                    plugin.getConfig().getInt("BombSpawns.blue.x"), plugin.getConfig().getInt("BombSpawns.blue.y"), plugin.getConfig().getInt("BombSpawns.blue.z"));
        }
        return null;
    }

    public void setBlueBombSpawn(Location blueBombSpawn) {
        plugin.getConfig().set("BombSpawns.blue.world", blueBombSpawn.getWorld().getName());
        plugin.getConfig().set("BombSpawns.blue.x", blueBombSpawn.getBlockX());
        plugin.getConfig().set("BombSpawns.blue.y", blueBombSpawn.getBlockY());
        plugin.getConfig().set("BombSpawns.blue.z", blueBombSpawn.getBlockZ());
        plugin.saveConfig();
    }

    public Location getLobbySpawn() {
        return new Location (Bukkit.getWorld(plugin.getConfig().getString("LobbySpawn.world")),
                plugin.getConfig().getInt("LobbySpawn.x"), plugin.getConfig().getInt("LobbySpawn.y"), plugin.getConfig().getInt("LobbySpawn.z"));
    }

    public void setLobbySpawn(Location lobbySpawn) {
        plugin.getConfig().set("LobbySpawn.world", lobbySpawn.getWorld().getName());
        plugin.getConfig().set("LobbySpawn.x", lobbySpawn.getBlockX());
        plugin.getConfig().set("LobbySpawn.y", lobbySpawn.getBlockY());
        plugin.getConfig().set("LobbySpawn.z", lobbySpawn.getBlockZ());
        plugin.saveConfig();
    }

    public Location getSpectatorSpawn() {
        return new Location (Bukkit.getWorld(plugin.getConfig().getString("SpectatorSpawn.world")),
                plugin.getConfig().getInt("SpectatorSpawn.x"), plugin.getConfig().getInt("SpectatorSpawn.y"), plugin.getConfig().getInt("SpectatorSpawn.z"));
    }

    public void setSpectatorSpawn(Location spectatorSpawn) {
        plugin.getConfig().set("SpectatorSpawn.world", spectatorSpawn.getWorld().getName());
        plugin.getConfig().set("SpectatorSpawn.x", spectatorSpawn.getBlockX());
        plugin.getConfig().set("SpectatorSpawn.y", spectatorSpawn.getBlockY());
        plugin.getConfig().set("SpectatorSpawn.z", spectatorSpawn.getBlockZ());
        plugin.saveConfig();
    }

    public void teleportPlayerToGame(Player p, Team team) {
        if (team==Team.RED) p.teleport(SND.lh.getRedSpawn());
        else if (team==Team.BLUE) p.teleport(SND.lh.getBlueSpawn());
    }

    public void teleportPlayerFromGame(Player p) {
        p.teleport(SND.lh.getExitSpawn());
    }
}
