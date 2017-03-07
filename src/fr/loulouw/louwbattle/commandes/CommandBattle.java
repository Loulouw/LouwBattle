package fr.loulouw.louwbattle.commandes;

import fr.loulouw.louwbattle.inventaire.InventoryBattleMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Classe pour la commande CommandBattle
 */
public class CommandBattle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean res = true;
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.openInventory(new InventoryBattleMenu().getInventory());
        } else {
            res = false;
        }
        return res;
    }

    private void openInventoryBattle(){

    }

}
