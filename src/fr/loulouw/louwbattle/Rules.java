package fr.loulouw.louwbattle;


import fr.loulouw.louwbattle.inventaire.InventoryPerso;
import fr.loulouw.louwbattle.outils.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;

import java.util.Set;

public class Rules {

    public static boolean isKey(String key) { return Main.javaplugin.getConfig().isConfigurationSection(key);}

    public static int getInt(String key) {
        return Main.javaplugin.getConfig().getInt(key);
    }

    public static boolean getBoolean(String key) {
        return Main.javaplugin.getConfig().getBoolean(key);
    }

    public static String getString(String key) {
        return Main.javaplugin.getConfig().getString(key);
    }

    public static void set(String key, Object value) {
        Main.javaplugin.getConfig().set(key, value);
        Main.javaplugin.saveConfig();
    }

    public static Set<String> getChildren(String key){
        return Main.javaplugin.getConfig().getConfigurationSection(key).getKeys(false);
    }

    public static void prompteInt(Player p, String key, int borneMin, int borneSup, InventoryPerso inv) {
        new AnvilGUI(Main.javaplugin, p, "" + Rules.getInt(key), inv, (player, reply) -> {
            try {
                int res = Integer.parseInt(reply);
                if (res >= borneMin && res <= borneSup) {
                    Rules.set(key, res);
                } else {
                    return "" + Rules.getInt(key);
                }
            } catch (Exception e) {
                return "" + Rules.getInt(key);
            }
            return null;
        });
    }

    public static void changeBoolean(Player p, String key, InventoryPerso inv) {
        Rules.set(key, !Rules.getBoolean(key));
        inv.generateInventory();
        p.openInventory(inv.getInventory());
    }

    public static void changeString(Player p,String key,String value,InventoryPerso inv){
        Rules.set(key,value);
        inv.generateInventory();
        p.openInventory(inv.getInventory());
    }

}
