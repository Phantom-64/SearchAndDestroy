package net.thegenesismc.searchanddestroy.utils;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

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

    public GameManager(SND plugin) {
        this.plugin = plugin;
    }

    private List<Player> playing = new ArrayList<Player>();

    public List<Player> getPlaying() {
        return playing;
    }

    public boolean isPlaying(Player p) {
        return playing.contains(p);
    }

    public void addPlayerToGame(Player p, Team team, Kit kit) {
        getPlaying().add(p);
        SND.tm.setTeam(p, team);
        SND.km.setKit(p, kit, team);
        p.setGameMode(GameMode.ADVENTURE);
        SND.lh.teleportPlayerToGame(p, team);
    }

}
