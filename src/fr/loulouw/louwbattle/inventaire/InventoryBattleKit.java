package fr.loulouw.louwbattle.inventaire;


import fr.loulouw.louwbattle.Rules;
import fr.loulouw.louwbattle.objects.Kit;
import fr.loulouw.louwbattle.outils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InventoryBattleKit extends InventoryBase {

    private HashMap<String, Kit> kits;
    private InventoryPerso inv;

    public InventoryBattleKit(InventoryPerso inv){
        this.inv = inv;
    }

    @Override
    public void generateInventory() {
        Inventory invPerso = Bukkit.createInventory(null, 4*9, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Kit Battle");
        kits = new HashMap<>();

        int i =0;
        for(String kitName : Rules.getChildren("kit")){
            Kit k = new Kit(kitName);
            kits.put(kitName, k);
            ArrayList<String> description = new ArrayList<>();
            Collections.addAll(description, k.toString().split("\\n"));
            ItemStack kit = ItemCreator.createItemStack(null, Material.ARMOR_STAND, 1, ChatColor.WHITE + k.getNom(), description, false, true, true);
            itemStackInventory.put(kitName,kit);
            invPerso.setItem(i, kit);
            i++;
        }

        inventory = invPerso;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player playerClicked = (Player) event.getWhoClicked();
        ItemStack itemClicked = event.getCurrentItem();
        Inventory inventoryClicked = event.getInventory();

        if (inventoryClikedValid(inventoryClicked, itemClicked)) {
            Rules.changeString(playerClicked,"kitactif",itemClicked.getItemMeta().getLore().get(0),inv);
        }
        event.setCancelled(true);
    }


}
