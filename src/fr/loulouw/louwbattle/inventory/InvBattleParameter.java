package fr.loulouw.louwbattle.inventory;

import fr.loulouw.louwbattle.outils.Menu;
import org.bukkit.ChatColor;

/**
 * Created by Loulouw on 16/03/2017.
 */
public class InvBattleParameter {

    private static Menu menu;

    public static Menu getMenu() {
        if(menu == null) generate();
        return menu;
    }

    private static void generate(){
        menu = new Menu(3, ChatColor.BLUE + "" + ChatColor.BOLD + "Battle Paramètres");
    }

}
