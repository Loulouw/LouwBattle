package fr.loulouw.louwbattle.inventaire;


import fr.loulouw.louwbattle.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

abstract class InventoryBase implements Listener, InventoryPerso {

    Inventory inventory;
    HashMap<String, ItemStack> itemStackInventory;

    InventoryBase() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.javaplugin);
        itemStackInventory = new HashMap<>();
        generateInventory();
    }

    boolean inventoryClikedValid(Inventory i, ItemStack is) {
        boolean res = false;
        if (i != null && is != null && !is.getData().getItemType().equals(Material.AIR) && inventory.getName().equals(i.getName()))
            res = true;
        return res;
    }

    public Inventory getInventory() {
        return inventory;
    }

    String getNameItemStack(String cle) {
        return itemStackInventory.get(cle).getItemMeta().getDisplayName();
    }

}
