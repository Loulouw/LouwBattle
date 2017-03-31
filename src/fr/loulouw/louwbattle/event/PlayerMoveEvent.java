package fr.loulouw.louwbattle.event;

import fr.loulouw.louwbattle.Main;
import fr.loulouw.louwbattle.PlayerBattle;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class PlayerMoveEvent implements Listener {


    public PlayerMoveEvent() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.javaplugin);
    }

    @EventHandler
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent e) {
        PlayerBattle pb = PlayerBattle.listPlayer.get(e.getPlayer());
        if (pb.isOnWait()) {
            if (!(e.getFrom().getX() == e.getTo().getX() &&
                    e.getFrom().getY() == e.getTo().getY() &&
                    e.getFrom().getZ() == e.getTo().getZ())) {
                e.getPlayer().teleport(e.getFrom());
            }
        }
    }

}
