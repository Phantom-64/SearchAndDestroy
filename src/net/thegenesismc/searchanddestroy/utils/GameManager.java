package net.thegenesismc.searchanddestroy.utils;

import me.confuser.barapi.BarAPI;
import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * When the players die, they spectate (They can fly around)
 Players have 1 life. There will be 1 round and both teams have to go to the other side of the map and light the bomb with a fuse (This will be a blaze powder)
 Whoever lights the bomb first. For example, the blue team. The red team then have to defuse the bomb. If they defuse, red team wins! If red team don't defuse in time, the blue team wins!
 Bomb timer = 30 seconds
 Make it so when the bomb has been lit, it stands out in chat. Also, make sure there is a sound so players know when it the bomb has been lit!
 You can also win by killing the whole entire team!
 Games last up to 10 minutes. Each time you get a kill, you earn 10 Credits. (You can use coins to buy different kits)
 */

public class GameManager {

    private SND plugin;

    private int playerLimit;

    public int getPlayerLimit() {
        return plugin.getConfig().getInt("PlayerLimit");
    }

    public void setPlayerLimit(int playerLimit) {
        plugin.getConfig().set("PlayerLimit", playerLimit);
    }

    private Location joinSignLocation;

    public Location getJoinSignLocation() {
        if (plugin.getConfig().contains("JoinSign.world")) {
            return new Location(Bukkit.getWorld(plugin.getConfig().getString("JoinSign.world")),
                    plugin.getConfig().getInt("JoinSign.x"), plugin.getConfig().getInt("JoinSign.y"), plugin.getConfig().getInt("JoinSign.z"));
        }
        return null;
    }

    public void setJoinSignLocation(Location joinSignLocation) {
        plugin.getConfig().set("JoinSign.world", joinSignLocation.getWorld().getName());
        plugin.getConfig().set("JoinSign.x", joinSignLocation.getBlockX());
        plugin.getConfig().set("JoinSign.y", joinSignLocation.getBlockY());
        plugin.getConfig().set("JoinSign.z", joinSignLocation.getBlockZ());
        plugin.saveConfig();
    }

    public GameManager(SND plugin) {
        this.plugin = plugin;
    }

    private List<Player> playing = new ArrayList<Player>();

    private GameState gameState;

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;

    }

    public List<Player> getPlaying() {
        return playing;
    }

    public boolean isPlaying(Player p) {
        return playing.contains(p);
    }

    private boolean ended;

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public void addPlayerToGame(Player p, Team team, Kit kit) {
        getPlaying().add(p);
        SND.tm.setTeam(p, team);
        SND.km.setKit(p, kit, team);
        p.setGameMode(GameMode.ADVENTURE);
        p.setHealth(20.0);
        p.setFoodLevel(20);
        SND.lh.teleportPlayerToGame(p, team);
    }

    public void addPlayerToGame(Player p, Team team) {
        getPlaying().add(p);
        SND.tm.setTeam(p, team);
        p.setGameMode(GameMode.ADVENTURE);
        p.setHealth(20.0);
        p.setFoodLevel(20);
        SND.lh.teleportPlayerToGame(p, team);
    }

    public void removePlayerFromGame(Player p) {
        getPlaying().remove(p);
        SND.tm.removeFromTeam(p);
        SND.km.clearInventory(p);
        SND.km.removeKit(p);
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20.0);
        p.setFoodLevel(20);
        SND.lh.teleportPlayerFromGame(p);
    }

    public void broadcastMessageInGame(String message) {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (isPlaying(pl)) {
                pl.sendMessage(message);
            }
        }
    }

    public void broadcastMessageInGame(Team team, String message) {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (isPlaying(pl)) {
                if (SND.tm.getTeam(pl)==team) {
                    pl.sendMessage(message);
                }
            }
        }
    }

    public void broadcastMessageInGame(String message, boolean sendToSpectator) {
        if (sendToSpectator==true) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (SND.gm.isPlaying(pl)||SND.sm.isSpectator(pl)) {
                    pl.sendMessage(message);
                }
            }
        } else {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (SND.gm.isPlaying(pl)) {
                    pl.sendMessage(message);
                }
            }
        }
    }

    public void updateJoinSign() {
        Block b = SND.gm.getJoinSignLocation().getBlock();
        if (b.getState() instanceof Sign) {
            Sign s = (Sign) b.getState();
            if (SND.gm.getGameState()==GameState.LOBBY) {
                s.setLine(0, "§9[Join]");
                s.setLine(1, "§aSND");
                s.setLine(2, "§5§l● Lobby ●");
                s.setLine(3, "§a" + SND.lm.getLobby().size() + "/" + SND.gm.getPlayerLimit());
                s.update();
            } else if (SND.gm.getGameState()==GameState.INGAME) {
                s.setLine(0, "§9[Join]");
                s.setLine(1, "§aSND");
                s.setLine(2, "§8● In Game ●");
                s.setLine(3, "§a" + SND.gm.getPlaying().size() + "/" + SND.gm.getPlayerLimit());
                s.update();
            } else if (SND.gm.getGameState()==GameState.RESTARTING) {
                s.setLine(0, "§9[Join]");
                s.setLine(1, "§aSND");
                s.setLine(2, "§4§l● Restarting ●");
                s.setLine(3, "§a" + SND.gm.getPlaying().size() + "/" + SND.gm.getPlayerLimit());
                s.update();
            }
        }
    }

    public void endGame() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (SND.gm.isPlaying(p)) {
                Team team = SND.tm.getTeam(p);
                SND.gm.removePlayerFromGame(p);
                p.teleport(SND.getTeamSpawn(team));
                p.setHealth(20.0);
            } else if (SND.lm.isInLobby(p)) {
                SND.lm.removePlayerFromLobby(p);
                p.teleport(SND.lh.getExitSpawn());
                p.setHealth(20.0);
            } else if (SND.sm.isSpectator(p)) {
                SND.sm.removeSpectator(p);
                p.teleport(SND.lh.getSpectatorSpawn());
                p.setHealth(20.0);
            }
            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
            p.setFireTicks(0);
            p.setHealth(20.0);
            p.setExp(0);
            p.setLevel(0);
            BarAPI.removeBar(p);
            p.sendMessage(SND.TAG_GREEN + "You will be kicked from the server in 5 seconds!");
        }
        for (Block b : SND.pressurePlateList) {
            b.setType(Material.AIR);
        }
        SND.lh.getRedBombSpawn().getBlock().setType(Material.AIR);
        SND.lh.getBlueBombSpawn().getBlock().setType(Material.AIR);
        SND.gm.setGameState(GameState.LOBBY);
        SND.gm.updateJoinSign();
        SND.gm.setEnded(false);
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.kickPlayer("  " + SND.TAG_GREEN + "\n" + "§aThe game is over!");
                }
            }
        }, 5 * 20);
    }
}
