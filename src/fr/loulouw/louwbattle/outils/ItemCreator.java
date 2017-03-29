package fr.loulouw.louwbattle.outils;


import javafx.collections.FXCollections;
import net.minecraft.server.v1_11_R1.Tuple;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemCreator {

    public static ItemStack createItemStack(ItemStack itemStack, String nom, List<String> description, boolean indestructible, List<Tuple<Enchantment, Integer>> enchantements, boolean apparenceEnchantement, boolean hideEnchantementName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(nom);
        if (description != null) itemMeta.setLore(description);
        itemMeta.setUnbreakable(indestructible);
        if (enchantements != null) {
            for (Tuple<Enchantment, Integer> t : enchantements) {
                itemMeta.addEnchant(t.a(), t.b(), true);
            }
        }
        if (apparenceEnchantement && enchantements == null) {
            itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 0, true);
        }
        if (hideEnchantementName) itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStack(ItemStack itemStack, String nom, List<String> description, boolean indestructible, boolean apparenceEnchantement, boolean hideEnchantementName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(nom);
        if (description != null) itemMeta.setLore(description);
        itemMeta.setUnbreakable(indestructible);
        if (apparenceEnchantement) itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 0, true);
        if (hideEnchantementName) itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
