package net.thegenesismc.searchanddestroy.utils;

import net.thegenesismc.searchanddestroy.SND;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamManager {

    private SND plugin;

    public TeamManager(SND plugin) {
        this.plugin = plugin;
    }

    private List<Player> red = new ArrayList<Player>();
    private List<Player> blue = new ArrayList<Player>();

    private Map<Player, Team> teams = new HashMap<Player, Team>();

    public Map<Player, Team> getTeams() {
        return teams;
    }

    public List<Player> getRed() {
        return red;
    }

    public List<Player> getBlue() {
        return blue;
    }

    public Team getValidTeam() {
        if (getRed().size()>getBlue().size()) {
            return Team.BLUE;
        } else if (getBlue().size()>getRed().size()) {
            return Team.RED;
        }
        return Team.BLUE;
    }

    public void setTeam(Player p, Team team) {
        getTeams().put(p, team);
        if (team==Team.RED) {
            getTeamScoreboard().getTeam("red").addPlayer(p);
            getRed().add(p);
        }
        else if (team==Team.BLUE) {
            getTeamScoreboard().getTeam("blue").addPlayer(p);
            getBlue().add(p);
        }
    }

    public Team getTeam(Player p) {
        if (getTeams().containsKey(p)) {
            return getTeams().get(p);
        }
        return null;
    }

    public void removeFromTeam(Player p) {
        Team team = getTeams().get(p);
        if (team==Team.RED) {
            getTeamScoreboard().getTeam("red").removePlayer(p);
            getRed().remove(p);
        }
        else if (team==Team.BLUE) {
            getTeamScoreboard().getTeam("blue").removePlayer(p);
            getBlue().remove(p);
        }
        getTeams().remove(p);
    }

    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    private Scoreboard sb = manager.getNewScoreboard();

    public Scoreboard getTeamScoreboard() {
        return this.sb;
    }

    public void setupTeams() {
        org.bukkit.scoreboard.Team red = getTeamScoreboard().registerNewTeam("red");
        org.bukkit.scoreboard.Team blue = getTeamScoreboard().registerNewTeam("blue");
        red.setDisplayName("§cRed");
        red.setAllowFriendlyFire(false);
        red.setCanSeeFriendlyInvisibles(true);
        blue.setDisplayName("§9Blue");
        blue.setAllowFriendlyFire(false);
        blue.setCanSeeFriendlyInvisibles(true);
    }

    public String getPlayerNameInTeamColor(Player p) {
        if (getTeams().get(p)!=null) {
            if (getTeam(p)==Team.RED) return "§c" + p.getName();
            else if (getTeam(p)==Team.BLUE) return "§9" + p.getName();
        }
        return "§8" + p.getName();
    }
}
