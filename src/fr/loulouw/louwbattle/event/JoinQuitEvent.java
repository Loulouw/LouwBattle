package fr.loulouw.louwbattle.event;

import fr.loulouw.louwbattle.Battle;
import fr.loulouw.louwbattle.Main;
import fr.loulouw.louwbattle.PlayerBattle;
import fr.loulouw.louwbattle.scoreboard.ScoPreparationBattle;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static fr.loulouw.louwbattle.PlayerBattle.listPlayer;

/**
 * Created by Loulouw on 29/03/2017.
 */
public class JoinQuitEvent implements Listener{

    public JoinQuitEvent(){
        Bukkit.getServer().getPluginManager().registerEvents(this,Main.javaplugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        listPlayer.put(e.getPlayer(),new PlayerBattle(e.getPlayer()));
        if(ScoPreparationBattle.isEnCours()) ScoPreparationBattle.showSideBar(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        listPlayer.remove(e.getPlayer());
        PlayerBattle pb = PlayerBattle.listPlayer.get(e.getPlayer());
        if(Battle.battleEnAttente.getListPlayer().contains(pb)) Battle.battleEnAttente.getListPlayer().remove(pb);
    }
}
