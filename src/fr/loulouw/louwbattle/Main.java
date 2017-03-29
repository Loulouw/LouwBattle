package fr.loulouw.louwbattle;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.loulouw.louwbattle.command.CommandBattle;
import fr.loulouw.louwbattle.event.JoinQuitEvent;
import fr.loulouw.louwbattle.scoreboard.ScoPreparationBattle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

import static fr.loulouw.louwbattle.PlayerBattle.listPlayer;

public class Main extends JavaPlugin{

    /**
     * Execute au lancement du plugin
     */
    public static JavaPlugin javaplugin;
    public static WorldGuardPlugin worldGuard;

    @Override
    public void onEnable(){
        Main.javaplugin = this;
        saveDefaultConfig();

        getLogger().info("[*] LouwBattle en cours de demarrage...");
        getLogger().info("[*] ====");
        getLogger().info("[*] Chargement des dependances... ");
        worldGuard = (WorldGuardPlugin) getServer().getPluginManager().getPlugin("WorldGuard");
        getLogger().info("[*] dependances chargees");
        getLogger().info("[*] ====");
        getLogger().info("[*] Chargement des commandes... ");
        chargementCommande();
        getLogger().info("[*] Commandes chargees");
        getLogger().info("[*] ====");

        getLogger().info("[*] LouwBattle est demarre");

        new JoinQuitEvent();
        PlayerBattle.listPlayer = new HashMap<>();
        for(Player p : getServer().getOnlinePlayers()){
            listPlayer.put(p,new PlayerBattle(p));
        }

        Battle.battleEnAttente = new Battle();
        Battle.battleEnAttente.processStart();
    }

    /**
     * Execute a la fermuture du plugin (serveur)
     */
    @Override
    public void onDisable(){
        ScoPreparationBattle.hideSidebar();
    }

    /**
     * Méthode permettant de charger les commandes
     */
    private void chargementCommande(){
        getCommand("battle").setExecutor(new CommandBattle());
    }

}
