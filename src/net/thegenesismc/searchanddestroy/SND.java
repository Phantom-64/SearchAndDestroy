package net.thegenesismc.searchanddestroy;

import me.confuser.barapi.BarAPI;
import net.thegenesismc.searchanddestroy.commands.CommandSetBombSpawn;
import net.thegenesismc.searchanddestroy.commands.CommandSetSpawn;
import net.thegenesismc.searchanddestroy.listeners.*;
import net.thegenesismc.searchanddestroy.utils.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.*;

/**
 * -----{ Search And Destroy }-----

 ---- Kits --------------------------------------------------------------------------------------
 - Assault - Iron sword - x2 Golden apple(s) - Iron armour
 - Juggernaut - Stone sword - slowness (/speed 0.5) - Iron armour (Protection 4)
 - Spy - Gold sword (Fire aspect 2) - X3 EnderPearls - Invisible - ( No armour )
 - Sniper - Wood sword - Bow (Power 2, Knock back 3, infinity) - Chain armour
 - Scout - Diamond axe - Fishing Rod - Speed 2 - Gold armour (Unbreaking 3)

 > Explosive - Stone sword (Sharpness 1) - X6 Snowballs (Grenades) - x2 Stone Pressure plates - Chain armour
 > Wizard: Fire - Stone sword - Blaze rod (Fire | 2 tics) - Chain armour
 ---- Kits --------------------------------------------------------------------------------------
 Pressure plates = If an enemy team steps on one, they instantly die


 ---- Kill Streaks ------------------------------------------------------------------------------
 3 Kills: Radar | Compass (Points to the nearest player)

 5 Kills: Dogs | Spawn egg (Spawns X2 dogs and attacks the other team. They dogs will follow the player)

 8 Kills: Nuke | TNT (Place it down and the whole enemy team dies) +You gain 100 Credits!
 ---- Kill Streaks ------------------------------------------------------------------------------


 • Teams:
 - Red | Red wool (On Head)
 - Blue | Blue wool (On Head)

 Shop:
 • Non-Donator •
 (Donators still have to buy these kits)
 - Assault = FREE
 - Spy = 400 Credits
 - Scout = 800 Credits
 - Sniper = 800 Credits
 - Juggernaut = 1200 Credits

 • Donator ONLY •
 (If donator, you get it the kit instantly. If not a donator, you have to buy it instead)
 - Explosive = 1200 Credits
 - Wizard: Fire = 3000 Credits

 Each time you get a kill, you earn 10 Credits. (You can use coins to buy different kits)

 When the players die, they spectate (They can fly around)
 Players have 1 life. There will be 1 round and both teams have to go to the other side of the map and light the bomb with a fuse (This will be a blaze powder)
 Whoever lights the bomb first. For example, the blue team. The red team then have to defuse the bomb. If they defuse, red team wins! If red team don't defuse in time, the blue team wins!
 Bomb timer = 30 seconds
 Make it so when the bomb has been lit, it stands out in chat. Also, make sure there is a sound so players know when it the bomb has been lit!
 You can also win by killing the whole entire team!
 Games last up to 10 minutes. Each time you get a kill, you earn 10 Credits. (You can use coins to buy different kits)
 ALL Non-Donator kits expire within a month (This means they have to re-buy it every month)
 This is good as it makes them want to play more! ;P

 Players will join via a sign on the wall, they will then get teleported in an arena where they can then select a kit (Via GUI)
 There has to be 8 players for the game to start. There will be a 1 minute countdown (This starts when there are 4+ players on the server)
 Make sure that the arena gets reset after every match and that the server restarts.
 If you can, make it so whenever the server restarts, a random arena is selected? (So we can have multiple arena's)

 You have until the 7th of July to get a substantial amount done! Hopefully that is enough time!
 The plugin has to be done by the 14th July!
 */


/**
 * TO-DO
 * - Fuse and defuse logic
 * - Spectator shiz
 *   - Let people join when game state is in game and set them to spectator
 *   - Block all interactions with the environment
 */
public class SND extends JavaPlugin implements Listener {

    public static String TAG_GREEN = "§5[§3SearchAndDestroy§5] §a";
    public static String TAG_BLUE = "§5[§3SearchAndDestroy§5] §9";
    public static String TAG_RED = "§5[§3SearchAndDestroy§5] §c";

    private Map<Player, Boolean> canFireBall = new HashMap<Player, Boolean>();
    public static List<Block> pressurePlateList = new ArrayList<Block>();

    //Too many managers :O
    public static LocationHandler lh;
    public static TeamManager tm;
    public static KitManager km;
    public static GameManager gm;
    public static InventoryManager im;
    public static LobbyManager lm;
    public static SpectatorManager sm;
    public static BombManager bm;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        registerListeners(this, new SignListener(), new InventoryListener(), new BlockListener(),
                new FoodLevelListener(), new NoDropListener(), new PlayerQuit(), new PlayerChat());
        lh = new LocationHandler(this);
        tm = new TeamManager(this);
        km = new KitManager(this);
        gm = new GameManager(this);
        im = new InventoryManager(this);
        lm = new LobbyManager(this);
        sm = new SpectatorManager(this);
        bm = new BombManager(this);
        tm.setupTeams();
        gm.setGameState(GameState.LOBBY);
        SND.gm.updateJoinSign();
        SND.gm.setEnded(false);
    }

    @Override
    public void onDisable() {
        SND.gm.broadcastMessageInGame(SND.TAG_RED + "The game is ending due to a server restart!");
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (SND.gm.isPlaying(p)) {
                Team team = SND.tm.getTeam(p);
                SND.gm.removePlayerFromGame(p);
                p.teleport(SND.getTeamSpawn(team));
            } else if (SND.lm.isInLobby(p)) {
                SND.lm.removePlayerFromLobby(p);
                p.teleport(SND.lh.getExitSpawn());
            } else if (SND.sm.isSpectator(p)) {
                SND.sm.removeSpectator(p);
                p.teleport(SND.lh.getSpectatorSpawn());
            }
            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
            p.setFireTicks(0);
            p.setHealth(20.0);
            p.setExp(0);
            p.setLevel(0);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.kickPlayer("  " + SND.TAG_GREEN + "\n" + "§bThe game ended due to a server restart!");
            }
        }
        for (Block b : SND.pressurePlateList) {
            b.setType(Material.AIR);
        }
        SND.lh.getRedBombSpawn().getBlock().setType(Material.AIR);
        SND.lh.getBlueBombSpawn().getBlock().setType(Material.AIR);
        SND.gm.setGameState(GameState.LOBBY);
        SND.gm.updateJoinSign();
        SND.gm.setEnded(false);
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
            getLogger().info(listener.getClass().getSimpleName() + " registered.");
        }
        getLogger().info("All events registered.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("snd")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length==0) {
                    p.sendMessage(SND.TAG_BLUE + "Available arguments: leave, setspawn, setbombspawn");
                } else if (args[0].equalsIgnoreCase("setspawn")) {
                    CommandSetSpawn.execute(p, args);
                } else if (args[0].equalsIgnoreCase("setbombspawn")) {
                    CommandSetBombSpawn.execute(p, args);
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            }
        } else if (label.equalsIgnoreCase("p")) {
            Player p = (Player) sender;
            p.teleport(p.getLocation().add(0, 150, 0));
        }
        return true;
    }

    public static int timer = 500;

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block b = e.getClickedBlock();
            if (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN) {
                Sign s = (Sign) b.getState();
                if (s.getLine(0).equalsIgnoreCase("§9[Join]") && s.getLine(1).equalsIgnoreCase("§aSND")) {
                    Location loc = new Location(b.getWorld(), b.getX(), b.getY(), b.getZ());
                    if (loc.equals(SND.gm.getJoinSignLocation())) {
                        if (SND.gm.getGameState()==GameState.LOBBY) {
                            if (SND.lm.getLobby().size()>=SND.gm.getPlayerLimit()) {
                                p.sendMessage(SND.TAG_RED + "This game is full.");
                            } else if (SND.lm.getLobby().size()<SND.gm.getPlayerLimit()) {
                                SND.lm.addPlayerToLobby(p);
                                SND.gm.updateJoinSign();
                                SND.lm.broadcastMessageInLobby(SND.TAG_GREEN + p.getName() + " joined the lobby. §2(§a" + SND.lm.getLobby().size() + "§2/§a" + SND.gm.getPlayerLimit() + "§2)");
                                if (SND.lm.getLobby().size()==1) {
                                    timer = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                                        int num = 10;
                                        @Override
                                        public void run() {
                                            if (num!=-1) {
                                                if (num!=0) {
                                                    for (Player pl : SND.lm.getLobby()) {
                                                        pl.setExp(0);
                                                        pl.setLevel(num);
                                                    }
                                                    num--;
                                                } else {
                                                    if (SND.lm.getLobby().size()>=SND.lm.getMinPlayersToStart()) {
                                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                                            if (SND.lm.getLobby().contains(pl)) {
                                                                pl.setExp(0);
                                                                pl.setLevel(0);
                                                                Team team = SND.tm.getValidTeam();
                                                                SND.lm.removePlayerFromLobby(pl);
                                                                SND.gm.addPlayerToGame(pl, team);
                                                                pl.getWorld().playSound(pl.getLocation(), Sound.LEVEL_UP, 1, 1);
                                                                SND.im.giveKitSelector(pl);
                                                                if (SND.tm.getTeam(pl)==Team.RED) {
                                                                    SND.gm.broadcastMessageInGame(SND.TAG_BLUE + pl.getName() + " joined the §cRed team§9!");
                                                                } else if (SND.tm.getTeam(pl)==Team.BLUE) {
                                                                    SND.gm.broadcastMessageInGame(SND.TAG_BLUE + pl.getName() + " joined the Blue team!");
                                                                }
                                                            }
                                                        }
                                                        SND.lh.getRedBombSpawn().getBlock().setType(Material.TNT);
                                                        SND.lh.getBlueBombSpawn().getBlock().setType(Material.TNT);
                                                        SND.gm.setGameState(GameState.INGAME);
                                                        SND.gm.updateJoinSign();
                                                        num--;
                                                    } else if (SND.lm.getLobby().size()<SND.lm.getMinPlayersToStart()) {
                                                        SND.lm.broadcastMessageInLobby(SND.TAG_RED + "There weren't enough players to start. Restarting timer...");
                                                        num = 10;
                                                    } else if (SND.lm.getLobby().size()==0) {
                                                        num = 10;
                                                    }
                                                }
                                            }
                                        }
                                    }, 0L, 20L);
                                }
                            }
                        } else if (SND.gm.getGameState()==GameState.INGAME) {
                            if (SND.gm.getPlaying().size()<SND.gm.getPlayerLimit()) {
                                if (!SND.gm.isPlaying(p)) {
                                    SND.gm.removePlayerFromGame(p);
                                    SND.sm.setSpectator(p);
                                    SND.gm.broadcastMessageInGame(SND.TAG_GREEN + p.getName() + " is spectating the game.", true);
                                } else {
                                    p.sendMessage(SND.TAG_BLUE + "You are already in the game.");
                                }
                            } else {
                                p.sendMessage(SND.TAG_RED + "This game is full.");
                            }
                        } else {
                            p.sendMessage(SND.TAG_RED + "This game is not joinable right now.");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBombFuse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        try {
            if (e.getAction()==Action.RIGHT_CLICK_BLOCK) {
                Block b = e.getClickedBlock();
                Location loc = new Location(b.getWorld(), b.getX(), b.getY(), b.getZ());
                if (b.getType()==Material.TNT) {
                    if (e.getItem().getType()==Material.BLAZE_POWDER) {
                        if (SND.gm.isPlaying(p)) {
                            if (SND.tm.getTeam(p)==Team.RED&&loc.equals(SND.lh.getBlueBombSpawn())) {
                                if (!SND.bm.isBlueFused()) {
                                    if (!SND.bm.isRedFused()) {
                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                            if (SND.gm.isPlaying(pl)||SND.sm.isSpectator(pl)) {
                                                pl.getWorld().playSound(pl.getLocation(), Sound.FUSE, 1, 1);
                                            }
                                        }
                                        SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "Blue team§b's bomb has been lit! They have 15 seconds to defuse it before it blows up!", true);
                                        SND.bm.setBlueFused(true);
                                        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                                            int fuse = 15;
                                            @Override
                                            public void run() {
                                                if (fuse!=-1) {
                                                    if (fuse!=0) {
                                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                                            if (SND.gm.isPlaying(pl)||SND.sm.isSpectator(pl)) {
                                                                pl.getWorld().playSound(pl.getLocation(), Sound.FUSE, 1, 1);
                                                                BarAPI.setMessage(pl, "§9§l" + fuse + "§r§9...", 100);
                                                            }
                                                        }
                                                        fuse--;
                                                    } else {
                                                        if (SND.bm.isBlueFused()&&SND.gm.getPlaying().size()!=0) {
                                                            SND.bm.setBlueFused(false);
                                                            SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "Blue team's bomb has blown up! §cRed team §9wins!", true);
                                                            for (int i=1;i<4;i++) {
                                                                shootFirework(SND.lh.getRedSpawn());
                                                            }
                                                            SND.gm.endGame();
                                                            for (Player pl : Bukkit.getOnlinePlayers()) {
                                                                if (SND.gm.isPlaying(pl)||SND.sm.isSpectator(pl)) {
                                                                    BarAPI.removeBar(pl);
                                                                    pl.getWorld().playSound(pl.getLocation(), Sound.EXPLODE, 1, 1);
                                                                }
                                                            }
                                                        }
                                                        fuse--;
                                                    }
                                                }
                                            }
                                        }, 0L, 20L);
                                    } else {
                                        p.sendMessage(SND.TAG_BLUE + "Defuse your own bomb first!");
                                    }
                                } else {
                                    p.sendMessage(SND.TAG_BLUE + "This bomb is already fused.");
                                }
                            } else if (SND.tm.getTeam(p)==Team.BLUE&&loc.equals(SND.lh.getRedBombSpawn())) {
                                if (!SND.bm.isRedFused()) {
                                    if (!SND.bm.isBlueFused()) {
                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                            if (SND.gm.isPlaying(pl)||SND.sm.isSpectator(pl)) {
                                                pl.getWorld().playSound(pl.getLocation(), Sound.FUSE, 1, 1);
                                            }
                                        }
                                        SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "§cRed team§b's bomb has been lit! They have 15 seconds to defuse it before it blows up!", true);
                                        SND.bm.setRedFused(true);
                                        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                                            int fuse = 15;
                                            @Override
                                            public void run() {
                                                if (fuse!=-1) {
                                                    if(fuse!=0) {
                                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                                            if (SND.gm.isPlaying(pl)||SND.sm.isSpectator(pl)) {
                                                                pl.getWorld().playSound(pl.getLocation(), Sound.FUSE, 1, 1);
                                                                BarAPI.setMessage(pl, "§c§l" + fuse + "§r§c...", 100);
                                                            }
                                                        }
                                                        fuse--;
                                                    } else {
                                                        if (SND.bm.isRedFused()&&SND.gm.getPlaying().size()!=0) {
                                                            SND.bm.setRedFused(false);
                                                            SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "§cRed team§9's bomb has blown up! Blue team wins!", true);
                                                            for (int i=1;i<4;i++) {
                                                                shootFirework(SND.lh.getBlueSpawn());
                                                            }
                                                            SND.gm.endGame();
                                                            for (Player pl : Bukkit.getOnlinePlayers()) {
                                                                BarAPI.removeBar(pl);
                                                                pl.getWorld().playSound(pl.getLocation(), Sound.EXPLODE, 1, 1);
                                                            }
                                                        }
                                                        fuse--;
                                                    }
                                                }
                                            }
                                        }, 0L, 20L);
                                    } else {
                                        p.sendMessage(SND.TAG_BLUE + "Defuse your own bomb first!");
                                    }
                                } else {
                                    p.sendMessage(SND.TAG_BLUE + "This bomb is already fused.");
                                }
                            } else if (SND.tm.getTeam(p)==Team.RED&&loc.equals(SND.lh.getRedBombSpawn())) {
                                if (SND.bm.isRedFused()) {
                                    SND.bm.setRedFused(false);
                                    SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "§cRed team §9defused their bomb in time!", true);
                                    SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "§cRed team §9wins!", true);
                                    SND.gm.setEnded(true);
                                    getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                                        int num = 3;
                                        @Override
                                        public void run() {
                                            if (num!=-1) {
                                                if (num!=0) {
                                                    shootFirework(SND.lh.getRedSpawn());
                                                    num--;
                                                } else {
                                                    SND.gm.endGame();
                                                    num--;
                                                }
                                            }
                                        }
                                    }, 0L, 30L);
                                } else {
                                    p.sendMessage(SND.TAG_BLUE + "Your bomb isn't fused yet.");
                                }
                            } else if (SND.tm.getTeam(p)==Team.BLUE&&loc.equals(SND.lh.getBlueBombSpawn())) {
                                if (SND.bm.isBlueFused()) {
                                    SND.bm.setBlueFused(false);
                                    SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "Blue team defused their bomb in time!", true);
                                    SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "Blue team wins!", true);
                                    SND.gm.setEnded(true);
                                    getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                                        int num = 3;
                                        @Override
                                        public void run() {
                                            if (num!=-1) {
                                                if (num!=0) {
                                                    shootFirework(SND.lh.getBlueSpawn());
                                                    num--;
                                                } else {
                                                    SND.gm.endGame();
                                                    num--;
                                                }
                                            }
                                        }
                                    }, 0L, 30L);
                                } else {
                                    p.sendMessage(SND.TAG_BLUE + "Your bomb isn't fused yet.");
                                }
                            } else {
                                p.sendMessage(SND.TAG_RED + "This is not a bomb.");
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException npe) {}
    }

    @EventHandler
    public void onPressurePlate(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction()==Action.PHYSICAL) {
            Block b = e.getClickedBlock();
            if (b.getType()==Material.STONE_PLATE) {
                if (SND.gm.isPlaying(p)) {
                    Entity tnt = p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
                    ((TNTPrimed)tnt).setFuseTicks(0);
                    b.setType(Material.AIR);
                }
            }
        }
    }

    @EventHandler
    public void onGrenade(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Snowball) {
            final Snowball snowball = (Snowball) e.getEntity();
            if (snowball.getShooter() instanceof Player) {
                Player p = (Player) snowball.getShooter();
                if (SND.gm.isPlaying(p)) {
                    getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                        int num = 3;
                        @Override
                        public void run() {
                            if (num != -1) {
                                if (num != 0) {
                                    snowball.getWorld().playSound(snowball.getLocation(), Sound.CLICK, 1, 2);
                                    num--;
                                } else {
                                    Entity tnt = snowball.getWorld().spawn(snowball.getLocation().add(0, 2, 0), TNTPrimed.class);
                                    ((TNTPrimed) tnt).setFuseTicks(0);
                                    num--;
                                }
                            }
                        }
                    }, 0L, 10L);
                }
            }
        } else if (e.getEntity() instanceof Fireball) {
            Fireball fireball = (Fireball) e.getEntity();
            Entity tnt = fireball.getWorld().spawn(fireball.getLocation(), TNTPrimed.class);
            ((TNTPrimed)tnt).setFuseTicks(0);
        }
    }

    @EventHandler
    public void onBlazeRod(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        try {
            if (e.getAction()==Action.LEFT_CLICK_AIR||e.getAction()==Action.LEFT_CLICK_BLOCK) {
                if (e.getItem().getType()==Material.BLAZE_ROD) {
                    if (SND.gm.isPlaying(p)) {
                        if (canFireBall.containsKey(p)) {
                            if (canFireBall.get(p)==true) {
                                e.setCancelled(true);
                                Entity ball = p.getWorld().spawn(p.getLocation().add(0, 3, 0), Fireball.class);
                                ball.setVelocity(p.getLocation().getDirection().multiply(2));
                                canFireBall.put(p, false);
                                getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                                    @Override
                                    public void run() {
                                        canFireBall.put(p, true);
                                    }
                                }, 20 * 10);
                            } else {
                                p.sendMessage(SND.TAG_BLUE + "Please wait 10 seconds before using this again.");
                            }
                        } else {
                            e.setCancelled(true);
                            Entity ball = p.getWorld().spawn(p.getLocation().add(0, 3, 0), Fireball.class);
                            ball.setVelocity(p.getLocation().getDirection().multiply(2));
                            canFireBall.put(p, false);
                            getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                                @Override
                                public void run() {
                                    canFireBall.put(p, true);
                                }
                            }, 20 * 10);
                        }
                    }
                }
            }
        } catch (NullPointerException npe) {}
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        e.blockList().clear();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (SND.sm.isSpectator(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (SND.gm.isPlaying(p)) {
            e.getDrops().clear();
            if (p.getKiller() instanceof Player) {
                Player killer = p.getKiller();
                SND.gm.broadcastMessageInGame(SND.TAG_BLUE + SND.tm.getPlayerNameInTeamColor(p) + " §9was killed by " + SND.tm.getPlayerNameInTeamColor(killer) + "§9.", true);
            } else {
                SND.gm.broadcastMessageInGame(SND.TAG_BLUE + SND.tm.getPlayerNameInTeamColor(p) + " §9died.", true);
            }
            e.setDeathMessage("");
            p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 1, 1);
            p.setHealth(20.0);
            p.setFoodLevel(20);
            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
            SND.gm.removePlayerFromGame(p);
            SND.sm.setSpectator(p);
            SND.gm.updateJoinSign();
            if (SND.tm.getBlue().size()==0) {
                SND.gm.broadcastMessageInGame(SND.TAG_GREEN + "Everyone in §9blue team §ais dead!", true);
                SND.gm.broadcastMessageInGame(SND.TAG_GREEN + "§cRed team §awins!", true);
                SND.gm.setEnded(true);
                getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                    int num = 3;
                    @Override
                    public void run() {
                        if (num!=-1) {
                            if (num!=0) {
                                shootFirework(SND.lh.getRedSpawn());
                                num--;
                            } else {
                                SND.gm.endGame();
                                num--;
                            }
                        }
                    }
                }, 0L, 30L);
            } else if (SND.tm.getRed().size()==0) {
                SND.gm.broadcastMessageInGame(SND.TAG_GREEN + "Everyone in §cred team §ais dead!", true);
                SND.gm.broadcastMessageInGame(SND.TAG_GREEN + "§9Blue team §awins!", true);
                SND.gm.setEnded(true);
                getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                    int num = 3;
                    @Override
                    public void run() {
                        if (num!=-1) {
                            if (num!=0) {
                                shootFirework(SND.lh.getRedSpawn());
                                num--;
                            } else {
                                SND.gm.endGame();
                                num--;
                            }
                        }
                    }
                }, 0L, 30L);
            }
        }
    }

    @EventHandler
    public void onPVP(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.getDamager() instanceof Player) {
                Player damager = (Player) e.getDamager();
                if (SND.lm.isInLobby(p)&&SND.lm.isInLobby(damager)) {
                    e.setCancelled(true);
                } else if (SND.sm.isSpectator(damager)&&SND.gm.isPlaying(p)) {
                    e.setCancelled(true);
                } else if (SND.tm.getTeam(p)==SND.tm.getTeam(damager)) {
                    e.setCancelled(true);
                } else if (SND.gm.isPlaying(p)&&!SND.gm.isPlaying(damager)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    Player shooter = (Player) arrow.getShooter();
                    if (SND.gm.isPlaying(p)&&SND.gm.isPlaying(shooter)) {
                        if (SND.tm.getTeam(p)==SND.tm.getTeam(shooter)) {
                            e.setCancelled(true);
                        } else if (SND.sm.isSpectator(p)) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    public static void shootFirework(Location loc) {
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        Random r = new Random();

        int rt = r.nextInt(5) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (rt == 1) type = FireworkEffect.Type.BALL;
        if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
        if (rt == 3) type = FireworkEffect.Type.BURST;
        if (rt == 4) type = FireworkEffect.Type.CREEPER;
        if (rt == 5) type = FireworkEffect.Type.STAR;

        Color c1 = Color.AQUA;
        Color c2 = Color.LIME;

        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

        fwm.addEffect(effect);
        fwm.setPower(1);
        fw.setFireworkMeta(fwm);
    }

    @EventHandler
    public void onKitSelectorOpen(PlayerInteractEvent e) {
        try {
            Player p = e.getPlayer();
            if (e.getItem().getType()==Material.NETHER_STAR&&e.getItem().getItemMeta().getDisplayName().equals("§bKit Selector")) {
                if (SND.gm.isPlaying(p)) {
                    SND.im.openKitSelector(p);
                }
            }
        } catch (NullPointerException npe) {}
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        LocationHandler lh = SND.lh;
        if (p.isOp()) {
            if (lh.getRedBombSpawn()==null||lh.getBlueBombSpawn()==null||lh.getRedSpawn()==null||lh.getBlueSpawn()==null||lh.getExitSpawn()==null) {
                p.sendMessage(SND.TAG_BLUE + "All the spawns are not set. Please set them using /snd setspawn and /snd setbombspawn immediately or you will not be able to play.");
            }
        }
        SND.gm.removePlayerFromGame(e.getPlayer());
        SND.sm.removeSpectator(e.getPlayer());
        SND.lm.removePlayerFromLobby(e.getPlayer());
        e.setJoinMessage("");
        if (SND.gm.getGameState()==GameState.LOBBY) {
            if (SND.lm.getLobby().size()>=SND.gm.getPlayerLimit()) {
                p.sendMessage(SND.TAG_RED + "This game is full.");
            } else if (SND.lm.getLobby().size()<SND.gm.getPlayerLimit()) {
                SND.lm.addPlayerToLobby(p);
                SND.gm.updateJoinSign();
                SND.lm.broadcastMessageInLobby(SND.TAG_GREEN + p.getName() + " joined the lobby. §2(§a" + SND.lm.getLobby().size() + "§2/§a" + SND.gm.getPlayerLimit() + "§2)");
                if (SND.lm.getLobby().size()==1) {
                    timer = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                        int num = 10;
                        @Override
                        public void run() {
                            if (num!=-1) {
                                if (num!=0) {
                                    for (Player pl : SND.lm.getLobby()) {
                                        pl.setExp(0);
                                        pl.setLevel(num);
                                    }
                                    num--;
                                } else {
                                    if (SND.lm.getLobby().size()>=SND.lm.getMinPlayersToStart()) {
                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                            if (SND.lm.getLobby().contains(pl)) {
                                                pl.setExp(0);
                                                pl.setLevel(0);
                                                pl.getWorld().playSound(pl.getLocation(), Sound.BURP, 1, 1);
                                                Team team = SND.tm.getValidTeam();
                                                SND.lm.removePlayerFromLobby(pl);
                                                SND.gm.addPlayerToGame(pl, team);
                                                SND.im.giveKitSelector(pl);
                                            }
                                        }
                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                            if (SND.gm.getPlaying().contains(pl)) {
                                                if (SND.tm.getTeam(pl)==Team.RED) {
                                                    SND.gm.broadcastMessageInGame(SND.TAG_BLUE + pl.getName() + " joined the §cRed team§9!");
                                                } else if (SND.tm.getTeam(pl)==Team.BLUE) {
                                                    SND.gm.broadcastMessageInGame(SND.TAG_BLUE + pl.getName() + " joined the Blue team!");
                                                }
                                            }
                                        }
                                        SND.lh.getRedBombSpawn().getBlock().setType(Material.TNT);
                                        SND.lh.getBlueBombSpawn().getBlock().setType(Material.TNT);
                                        SND.gm.setGameState(GameState.INGAME);
                                        SND.gm.updateJoinSign();
                                        num--;
                                    } else if (SND.lm.getLobby().size()<SND.lm.getMinPlayersToStart()) {
                                        SND.lm.broadcastMessageInLobby(SND.TAG_RED + "There weren't enough players to start. Restarting timer...");
                                        num = 10;
                                    } else if (SND.lm.getLobby().size()==0) {
                                        num = 10;
                                    }
                                }
                            }
                        }
                    }, 0L, 20L);
                }
            }
        } else if (SND.gm.getGameState()==GameState.INGAME) {
            if (SND.gm.getPlaying().size()<SND.gm.getPlayerLimit()) {
                if (!SND.gm.isPlaying(p)) {
                    SND.gm.removePlayerFromGame(p);
                    SND.sm.setSpectator(p);
                    SND.gm.broadcastMessageInGame(SND.TAG_GREEN + p.getName() + " is spectating the game.", true);
                } else {
                    p.sendMessage(SND.TAG_BLUE + "You are already in the game.");
                }
            } else if (SND.gm.getPlaying().size()==SND.gm.getPlayerLimit()) {
                p.sendMessage(SND.TAG_RED + "This game is full.");
            }
        } else {
            p.sendMessage(SND.TAG_RED + "This game is not joinable right now.");
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if (Bukkit.getOnlinePlayers().length==SND.gm.getPlayerLimit()||SND.gm.getPlaying().size()==SND.gm.getPlayerLimit()) {
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            e.setKickMessage(" " + SND.TAG_GREEN + "\n" + "§cThe game is full!");
        }
    }

    public static Location getTeamSpawn(Player p) {
        Team team = SND.tm.getTeam(p);
        if (team!=null) {
            if (team==Team.RED) return SND.lh.getRedSpawn();
            else if (team==Team.BLUE) return SND.lh.getBlueSpawn();
        }
        return SND.lh.getExitSpawn();
    }

    public static Location getTeamSpawn(Team team) {
        if (team==Team.RED) return SND.lh.getRedSpawn();
        else if (team==Team.BLUE) return SND.lh.getBlueSpawn();
        return SND.lh.getExitSpawn();
    }
}
