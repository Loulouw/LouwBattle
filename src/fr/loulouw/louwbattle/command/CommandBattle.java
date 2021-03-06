package fr.loulouw.louwbattle.command;

import fr.loulouw.louwbattle.PlayerBattle;
import fr.loulouw.louwbattle.inventory.InvBattle;
import fr.loulouw.louwbattle.outils.Menu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandBattle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            PlayerBattle pb = PlayerBattle.listPlayer.get(commandSender);
            if (pb.isOnSpecatate()) {

            }else if(!pb.isOnBattle()){
                Menu m = InvBattle.getMenu();
                m.openInventory((Player)commandSender);
            }
        }
        return true;
    }

}
