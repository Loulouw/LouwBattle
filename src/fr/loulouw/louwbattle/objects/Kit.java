package fr.loulouw.louwbattle.objects;

import fr.loulouw.louwbattle.Rules;
import fr.loulouw.louwbattle.outils.Outils;
import net.minecraft.server.v1_11_R1.Items;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Map;

public class Kit {

    private String nameKit;
    private String nom;
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;
    private ArrayList<ItemStack> items;

    public Kit(String key) {
        String chemin = "kit." + key;
        nameKit = key;
        nom = Rules.getString(chemin + ".nom");
        helmet = getItems(Rules.getString(chemin + ".helmet"));
        chestplate = getItems(Rules.getString(chemin + ".chestplate"));
        leggings = getItems(Rules.getString(chemin + ".leggings"));
        boots = getItems(Rules.getString(chemin + ".boots"));
        items = new ArrayList<>();
        for (String item : Rules.getString(chemin + ".items").split(" \\| ")) {
            items.add(getItems(item));
        }
    }


    private ItemStack getItems(String code) {
        int idEnCours = 0;
        int b = -1;
        ItemStack item;
        String contenance[] = code.split(" ");

        String nameItem = contenance[idEnCours];
        if(nameItem.contains("(") && nameItem.contains(")")){
            String byteString = nameItem.substring(nameItem.indexOf('(')+1,nameItem.indexOf(")")-1);
            b = Integer.parseInt(byteString);
            nameItem = nameItem.substring(0,nameItem.indexOf('(')-1);
        }
        idEnCours++;

        int number = 1;
        if (Outils.isInteger(contenance[idEnCours])) {
            number = Integer.parseInt(contenance[1]);
            idEnCours++;
        }

        if(b!=-1){
            item = new ItemStack(Material.matchMaterial(nameItem), number,(byte)b);
        }else{
            item = new ItemStack(Material.matchMaterial(nameItem), number);
        }
        ItemMeta itemMeta = item.getItemMeta();


        for (int i = idEnCours; i < contenance.length; i += 2) {
            itemMeta.addEnchant(Outils.enchantement.get(contenance[i]), Integer.parseInt(contenance[i + 1]), true);
        }
        item.setItemMeta(itemMeta);
        return item;
    }

    private String getInfosItemStack(ItemStack item) {
        String enchant = "";
        for (Map.Entry<Enchantment, Integer> entry : item.getItemMeta().getEnchants().entrySet()) {
            enchant += ChatColor.DARK_RED + "" + WordUtils.capitalizeFully(entry.getKey().getName()) + " " + ChatColor.LIGHT_PURPLE + "" + entry.getValue() + " ";
        }
        return ChatColor.GOLD + "  - " + WordUtils.capitalizeFully(item.getType().toString()) + " : " + enchant + "\n";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ItemStack getHelmet() {
        return helmet;
    }

    public void setHelmet(ItemStack helmet) {
        this.helmet = helmet;
    }

    public ItemStack getChestplate() {
        return chestplate;
    }

    public void setChestplate(ItemStack chestplate) {
        this.chestplate = chestplate;
    }

    public ItemStack getLeggings() {
        return leggings;
    }

    public void setLeggings(ItemStack leggings) {
        this.leggings = leggings;
    }

    public ItemStack getBoots() {
        return boots;
    }

    public void setBoots(ItemStack boots) {
        this.boots = boots;
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemStack> items) {
        this.items = items;
    }

    public String toString() {
        String res = nameKit + "\n" +
                getInfosItemStack(helmet) +
                getInfosItemStack(chestplate) +
                getInfosItemStack(leggings) +
                getInfosItemStack(boots);
        for (ItemStack i : items) {
            res += getInfosItemStack(i);
        }

        return res;
    }
}
