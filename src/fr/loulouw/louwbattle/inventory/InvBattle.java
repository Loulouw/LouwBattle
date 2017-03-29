package fr.loulouw.louwbattle.inventory;

import fr.loulouw.louwbattle.Battle;
import fr.loulouw.louwbattle.PlayerBattle;
import fr.loulouw.louwbattle.outils.ItemCreator;
import fr.loulouw.louwbattle.outils.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InvBattle {

    private static Menu menu;

    public static Menu getMenu(){
        if(menu == null) generate();
        return menu;
    }

    private static void generate(){
        menu = new Menu(3, ChatColor.BLUE +"" + ChatColor.BOLD + "Battle");

        ItemStack rejoindre = ItemCreator.createItemStack(new ItemStack(Material.WOOL,1,(byte)5),ChatColor.GREEN + "Rejoindre",null,false,false,true );
        ItemStack quitter = ItemCreator.createItemStack(new ItemStack(Material.WOOL,1,(byte)14),ChatColor.RED + "Quitter",null,false,false,true );
        ItemStack listPlayer = ItemCreator.createItemStack(new ItemStack(Material.PAPER,1),ChatColor.WHITE + "Liste des joueurs",null,false,true,true);
        ItemStack parametre = ItemCreator.createItemStack(new ItemStack(Material.REDSTONE_TORCH_ON,1),ChatColor.WHITE + "Paramètres",null,false,true,true);

        menu.addItemAction(parametre, 2, 2, new Menu.onClick() {
            @Override
            public boolean click(ClickType clickType, Player player, Inventory inventory, int slot, ItemStack item) {
                Menu m = InvBattle.getMenu();
                m.openInventory(player);
                return true;
            }
        });

        menu.addItemAction(listPlayer, 2, 4, new Menu.onClick() {
            @Override
            public boolean click(ClickType clickType, Player player, Inventory inventory, int slot, ItemStack item) {
                return true;
            }
        });

        menu.addItemAction(quitter, 2, 6, new Menu.onClick() {
            @Override
            public boolean click(ClickType clickType, Player player, Inventory inventory, int slot, ItemStack item) {
                PlayerBattle pb = PlayerBattle.listPlayer.get(player);
                if(Battle.battleEnAttente.getListPlayer().contains(pb)){
                    Battle.battleEnAttente.getListPlayer().remove(pb);
                }else{
                    player.sendMessage(ChatColor.RED + "Vous n'êtes pas dans la partie");
                }
                return true;
            }
        });

        menu.addItemAction(rejoindre, 2, 8, new Menu.onClick() {
            @Override
            public boolean click(ClickType clickType, Player player, Inventory inventory, int slot, ItemStack item) {
                PlayerBattle pb = PlayerBattle.listPlayer.get(player);
                if(pb==null){
                    Bukkit.getServer().broadcastMessage("bite");
                }
                if(!Battle.battleEnAttente.getListPlayer().contains(pb)){
                    Battle.battleEnAttente.getListPlayer().add(pb);
                }else{
                    player.sendMessage(ChatColor.RED + "Vous êtes déjà dans la partie");
                }
                return true;
            }
        });
    }

}
