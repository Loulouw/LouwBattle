package fr.loulouw.louwbattle.inventaire;

import fr.loulouw.louwbattle.outils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBattleMenu extends InventoryBase {


    @Override
    public void generateInventory() {
        Inventory invPerso = Bukkit.createInventory(null, 3 * 9, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Battle");

        ItemStack parametre = ItemCreator.createItemStack(null, Material.REDSTONE_TORCH_ON, 1, ChatColor.DARK_GREEN + "Paramètres", null, false, false, true);
        itemStackInventory.put("parametre", parametre);
        invPerso.setItem(10, parametre);

        ItemStack whitelist = ItemCreator.createItemStack(null, Material.PAPER, 1, ChatColor.WHITE + "Liste des joueurs", null, false, false, true);
        itemStackInventory.put("whitelist", whitelist);
        invPerso.setItem(12, whitelist);

        ItemStack start = ItemCreator.createItemStack(new ItemStack(Material.WOOL, 1, (byte) 5), null, 0, ChatColor.GREEN + "START", null, false, false, true);
        itemStackInventory.put("start", start);
        invPerso.setItem(14, start);

        ItemStack stop = ItemCreator.createItemStack(new ItemStack(Material.WOOL, 1, (byte) 14), null, 0, ChatColor.RED + "STOP", null, false, false, true);
        itemStackInventory.put("stop", stop);
        invPerso.setItem(16, stop);

        inventory = invPerso;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player playerClicked = (Player) event.getWhoClicked();

        ItemStack itemClicked = event.getCurrentItem();

        Inventory inventoryClicked = event.getInventory();
        if (inventoryClikedValid(inventoryClicked, itemClicked)) {

            String nameItemCliked = itemClicked.getItemMeta().getDisplayName();

            if (getNameItemStack("parametre").equals(nameItemCliked)) {
                actionParametre(playerClicked);
            } else if (getNameItemStack("start").equals(nameItemCliked)) {

            } else if (getNameItemStack("stop").equals(nameItemCliked)) {

            } else if (getNameItemStack("whitelist").equals(nameItemCliked)) {

            }
            event.setCancelled(true);
        }
    }

    private void actionParametre(Player p) {
        p.openInventory(new InventoryBattleParametre().getInventory());
    }
}
