package fr.loulouw.louwbattle;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.loulouw.louwbattle.command.CommandBattle;
import org.bukkit.plugin.java.JavaPlugin;

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

        new Battle().processStart();
    }

    /**
     * Execute a la fermuture du plugin (serveur)
     */
    @Override
    public void onDisable(){

    }

    /**
     * Méthode permettant de charger les commandes
     */
    private void chargementCommande(){
        getCommand("battle").setExecutor(new CommandBattle());
    }

}
