package fr.loulouw.louwbattle;

import fr.loulouw.louwbattle.commandes.CommandBattle;
import fr.loulouw.louwbattle.outils.Outils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

    /**
     * Execute au lancement du plugin
     */
    public static JavaPlugin javaplugin;


    @Override
    public void onEnable(){
        Main.javaplugin = this;

        saveDefaultConfig();
        initialiseEnchantment();

        getLogger().info("[*] LouwBattle en cours de demarrage...");
        getLogger().info("[*] ====");

        getLogger().info("[*] Chargement des commandes... ");
        chargementCommande();
        getLogger().info("[*] Commandes chargees");
        getLogger().info("[*] ====");

        getLogger().info("[*] LouwBattle est demarre");
        getLogger().info(getConfig().getConfigurationSection("kit").getKeys(false).toString());
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

    private void initialiseEnchantment(){
        Outils.enchantement.put("AA", Enchantment.WATER_WORKER);
        Outils.enchantement.put("BA", Enchantment.DAMAGE_ARTHROPODS);
        Outils.enchantement.put("BP", Enchantment.PROTECTION_EXPLOSIONS);
        Outils.enchantement.put("DS", Enchantment.DEPTH_STRIDER);
        Outils.enchantement.put("E", Enchantment.DIG_SPEED);
        Outils.enchantement.put("F", Enchantment.ARROW_FIRE);
        Outils.enchantement.put("FA", Enchantment.FIRE_ASPECT);
        Outils.enchantement.put("FF", Enchantment.PROTECTION_FALL);
        Outils.enchantement.put("FO", Enchantment.LOOT_BONUS_BLOCKS);
        Outils.enchantement.put("FP", Enchantment.PROTECTION_FIRE);
        Outils.enchantement.put("FW", Enchantment.FROST_WALKER);
        Outils.enchantement.put("I", Enchantment.ARROW_INFINITE);
        Outils.enchantement.put("K", Enchantment.KNOCKBACK);
        Outils.enchantement.put("L", Enchantment.LOOT_BONUS_MOBS);
        Outils.enchantement.put("LS", Enchantment.LUCK);
        Outils.enchantement.put("LU", Enchantment.LURE);
        Outils.enchantement.put("M", Enchantment.MENDING);
        Outils.enchantement.put("P", Enchantment.PROTECTION_ENVIRONMENTAL);
        Outils.enchantement.put("PO", Enchantment.ARROW_DAMAGE);
        Outils.enchantement.put("PP", Enchantment.PROTECTION_PROJECTILE);
        Outils.enchantement.put("PU", Enchantment.ARROW_KNOCKBACK);
        Outils.enchantement.put("R", Enchantment.OXYGEN);
        Outils.enchantement.put("S", Enchantment.DAMAGE_ALL);
        Outils.enchantement.put("SE", Enchantment.SWEEPING_EDGE);
        Outils.enchantement.put("SM", Enchantment.DAMAGE_UNDEAD);
        Outils.enchantement.put("ST", Enchantment.SILK_TOUCH);
        Outils.enchantement.put("T", Enchantment.THORNS);
        Outils.enchantement.put("U", Enchantment.DURABILITY);
    }
}
