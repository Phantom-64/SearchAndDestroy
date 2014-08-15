package net.thegenesismc.searchanddestroy.listeners;

import net.thegenesismc.searchanddestroy.SND;
import net.thegenesismc.searchanddestroy.utils.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class PlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (SND.gm.isPlaying(p)) {
            e.setFormat("[In game] " + p.getName() + " > " + e.getMessage());
        } else if (SND.sm.isSpectator(p)) {
            e.setFormat("[Spec] " + p.getName() + " > " + e.getMessage());
        } else if (SND.lm.isInLobby(p)) {
            e.setFormat("[Lobby] " + p.getName() + " > " + e.getMessage());
        } else {
            e.setFormat(p.getName() + " > " + e.getMessage());
        }
    }

    @EventHandler
    public void onMOTD(ServerListPingEvent e) {
        if (SND.gm.getGameState()== GameState.LOBBY) {
            e.setMotd("§bSND " + SND.gm.getGameState().getName() + " §a" + SND.lm.getLobby().size() + "/" + SND.gm.getPlayerLimit());
        } else if (SND.gm.getGameState()==GameState.INGAME) {
            e.setMotd("§bSND " + SND.gm.getGameState().getName() + " §a" + SND.gm.getPlaying().size() + "/" + SND.gm.getPlayerLimit());
        }
    }
}
