package fr.loulouw.louwbattle.inventaire;


import fr.loulouw.louwbattle.Rules;
import fr.loulouw.louwbattle.outils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBattleCreateKit extends InventoryBase {

    private String nomBase = "";

    @Override
    public void generateInventory() {
        Inventory invPerso = Bukkit.createInventory(null, 5 * 9, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Creation Kit");

        ItemStack separation = ItemCreator.createItemStack(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7), null, 0, "", null, false, false, false);
        for (int i = 27; i <= 35; i++) {
            invPerso.setItem(i, separation);
        }

        ItemStack name = ItemCreator.createItemStack(null, Material.NAME_TAG, 1, ChatColor.WHITE + "Nom : " + ChatColor.GOLD + nomBase, null, false, false, false);
        invPerso.setItem(36,name);

        ItemStack annuler = ItemCreator.createItemStack(new ItemStack(Material.WOOL,1,(byte)14),null,0,ChatColor.WHITE + "Annuler la création de kit",null,false,false,false);
        itemStackInventory.put("annuler",annuler);
        invPerso.setItem(43,annuler);

        ItemStack creer = ItemCreator.createItemStack(new ItemStack(Material.WOOL,1,(byte)5),null,0,ChatColor.WHITE + "Créer un kit",null,false,false,false);
        itemStackInventory.put("creer",creer);
        invPerso.setItem(44,creer);

        inventory = invPerso;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player playerClicked = (Player) event.getWhoClicked();
        ItemStack itemClicked = event.getCurrentItem();
        Inventory inventoryClicked = event.getInventory();

        if (inventoryClikedValid(inventoryClicked, itemClicked)) {
            String nameItemCliked = itemClicked.getItemMeta().getDisplayName();
            if(getNameItemStack("annuler").equals(nameItemCliked)) {

            }else if(getNameItemStack("creer").equals(nameItemCliked)) {

            }
        }



        event.setCancelled(true);
    }


}
