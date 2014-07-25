package net.thegenesismc.searchanddestroy;

import net.thegenesismc.searchanddestroy.commands.CommandLeave;
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
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.Random;

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
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        registerListeners(this, new SignListener(), new PlayerJoin(), new InventoryListener(), new BlockListener(),
                new FoodLevelListener(), new NoDropListener());
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
    }

    @Override
    public void onDisable() {
        SND.gm.broadcastMessageInGame(SND.TAG_RED + "The game is ending due to a server restart!");
        SND.gm.endGame();
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
                } else if (args[0].equalsIgnoreCase("leave")) {
                    CommandLeave.execute(p, args);
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

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block b = e.getClickedBlock();
            if (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN) {
                Sign s = (Sign) b.getState();
                if (s.getLine(0).equalsIgnoreCase("§9[Join]") && s.getLine(1).equalsIgnoreCase("§aSND")) {
                    if (SND.gm.getGameState()==GameState.LOBBY) {
                        if (SND.lm.getLobby().size()>=SND.gm.getPlayerLimit()) {
                            p.sendMessage(SND.TAG_RED + "This game is full.");
                        } else if (SND.lm.getLobby().size()<SND.gm.getPlayerLimit()) {
                            SND.lm.addPlayerToLobby(p);
                            SND.gm.updateJoinSign();
                            SND.lm.broadcastMessageInLobby(SND.TAG_GREEN + p.getName() + " joined the lobby. §2(§a" + SND.lm.getLobby().size() + "§2/§a" + SND.gm.getPlayerLimit() + "§2)");
                            if (SND.lm.getLobby().size()>=SND.lm.getMinPlayersToStart()) {
                                getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                                    int num = 3;
                                    @Override
                                    public void run() {
                                        if (num!=-1) {
                                            if (num!=0) {
                                                for (Player pl : SND.lm.getLobby()) {
                                                    pl.setExp(0);
                                                    pl.setLevel(num);
                                                    pl.getWorld().playSound(p.getLocation(), Sound.CLICK, 1, 1);
                                                }
                                                num--;
                                            } else {
                                                for (Player pl : Bukkit.getOnlinePlayers()) {
                                                    if (SND.lm.getLobby().contains(pl)) {
                                                        pl.setExp(0);
                                                        pl.setLevel(0);
                                                        pl.getWorld().playSound(pl.getLocation(), Sound.BURP, 1, 1);
                                                        Team team = SND.tm.getValidTeam();
                                                        SND.lm.removePlayerFromLobby(pl);
                                                        SND.gm.addPlayerToGame(pl, team);
                                                        SND.im.openKitSelector(pl);
                                                    }
                                                }
                                                SND.lh.getRedBombSpawn().getBlock().setType(Material.TNT);
                                                SND.lh.getBlueBombSpawn().getBlock().setType(Material.TNT);
                                                SND.gm.setGameState(GameState.INGAME);
                                                SND.gm.updateJoinSign();
                                                num--;
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

    @EventHandler
    public void onBombFuse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        try {
            if (e.getAction()==Action.RIGHT_CLICK_BLOCK) {
                Block b = e.getClickedBlock();
                if (b.getType()==Material.TNT) {
                    if (e.getItem().getType()==Material.BLAZE_POWDER) {
                        if (SND.gm.isPlaying(p)) {
                            if (SND.tm.getTeam(p)==Team.RED&&b.getLocation()==SND.lh.getBlueBombSpawn().getBlock().getLocation()) {
                                for (Player pl : Bukkit.getOnlinePlayers()) {
                                    if (SND.gm.isPlaying(pl)||SND.sm.isSpectator(pl)) {
                                        pl.getWorld().playSound(pl.getLocation(), Sound.FUSE, 1, 1);
                                    }
                                }
                                SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "Blue team's bomb has been lit! They have 30 seconds to defuse it before it blows up!", true);
                                SND.bm.setBlueFused(true);
                                getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                                    @Override
                                    public void run() {
                                        if (SND.bm.isBlueFused()) {
                                            SND.bm.setBlueFused(false);
                                            Block b = SND.lh.getBlueBombSpawn().getBlock();
                                            b.getWorld().playSound(b.getLocation(), Sound.EXPLODE, 1, 1);
                                            SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "Blue team's bomb has blown up! §cRed team §9wins!", true);
                                            for (int i=1;i<4;i++) {
                                                shootFirework(SND.lh.getRedSpawn());
                                            }
                                            SND.gm.endGame();
                                        }
                                    }
                                }, 30 * 20);
                            } else if (SND.tm.getTeam(p)==Team.BLUE&&b.getLocation()==SND.lh.getRedBombSpawn().getBlock().getLocation()) {
                                for (Player pl : Bukkit.getOnlinePlayers()) {
                                    if (SND.gm.isPlaying(pl)||SND.sm.isSpectator(pl)) {
                                        pl.getWorld().playSound(pl.getLocation(), Sound.FUSE, 1, 1);
                                    }
                                }
                                SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "§cRed team§9's bomb has been lit! They have 30 seconds to defuse it before it blows up!", true);
                                SND.bm.setRedFused(true);
                                getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                                    @Override
                                    public void run() {
                                        if (SND.bm.isRedFused()) {
                                            SND.bm.setRedFused(false);
                                            Block b = SND.lh.getRedBombSpawn().getBlock();
                                            b.getWorld().playSound(b.getLocation(), Sound.EXPLODE, 1, 1);
                                            SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "§cRed team§9's bomb has blown up! Blue team wins!", true);
                                            for (int i=1;i<4;i++) {
                                                shootFirework(SND.lh.getBlueSpawn());
                                            }
                                            SND.gm.endGame();
                                        }
                                    }
                                }, 30 * 20);
                            } else if (SND.tm.getTeam(p)==Team.RED&&b.getLocation()==SND.lh.getRedBombSpawn().getBlock().getLocation()) {
                                if (SND.bm.isRedFused()) {
                                    SND.bm.setRedFused(false);
                                    SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "§cRed team §9defused their bomb in time!", true);
                                    SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "§cRed team §9wins!", true);
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
                            } else if (SND.tm.getTeam(p)==Team.BLUE&&b.getLocation()==SND.lh.getBlueBombSpawn().getBlock().getLocation()) {
                                if (SND.bm.isBlueFused()) {
                                    SND.bm.setBlueFused(false);
                                    SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "Blue team defused their bomb in time!", true);
                                    SND.gm.broadcastMessageInGame(SND.TAG_BLUE + "Blue team wins!", true);
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
                                p.sendMessage("lol");
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
                    p.setHealth(0.0);
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
                    }, 0L, 20L);
                }
            }
        }
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
                }
            }
        }
    }

    private void shootFirework(Location loc) {
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
}
