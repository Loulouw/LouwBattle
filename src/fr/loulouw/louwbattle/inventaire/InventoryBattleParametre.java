package fr.loulouw.louwbattle.inventaire;

import fr.loulouw.louwbattle.Main;
import fr.loulouw.louwbattle.Rules;
import fr.loulouw.louwbattle.outils.ItemCreator;
import fr.loulouw.louwbattle.outils.anvilgui.AnvilGUI;
import org.apache.commons.codec.language.bm.Rule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class InventoryBattleParametre extends InventoryBase {

    @Override
    public void generateInventory() {
        Inventory invPerso = Bukkit.createInventory(null, 6 * 9, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Paramètres");

        createIntModifier(invPerso, new ItemStack(Material.PAPER, 1), 10, "Max Joueurs", "nbJoueurMax");

        createIntModifier(invPerso, new ItemStack(Material.PRISMARINE_CRYSTALS, 1), 11, "Gain Par Kill", "gainParKill");

        createIntModifier(invPerso, new ItemStack(Material.RED_MUSHROOM, 1), 12, "Nombre de Vies", "nbVie");

        createIntModifier(invPerso, new ItemStack(Material.INK_SACK, 1, (byte) 1), 13, "Nombre de Coeurs", "nbCoeur");

        createBooleanModifier(invPerso, new ItemStack(Material.DIAMOND_CHESTPLATE, 1), 14, "Perte du Stuff", "perteStuff");

        createBooleanModifier(invPerso, new ItemStack(Material.DAYLIGHT_DETECTOR, 1), 15, "Jour", "jour");

        ItemStack kitactif = ItemCreator.createItemStack(null, Material.ARMOR_STAND, 1, ChatColor.WHITE + "Kit de départ: " + ChatColor.GOLD + "" + ChatColor.BOLD + Rules.getString("kitactif"), null, false, true, true);
        invPerso.setItem(16, kitactif);

        ItemStack kitactifModify = ItemCreator.createItemStack(null, Material.NAME_TAG, 1, ChatColor.WHITE + "Modifier Kit de départ: ", null, false, false, true);
        itemStackInventory.put("kitactifModify", kitactifModify);
        invPerso.setItem(16 + 9, kitactifModify);

        ItemStack createKit = ItemCreator.createItemStack(null, Material.BOOK_AND_QUILL, 1, ChatColor.WHITE + "Créer un kit", null, false, false, true);
        itemStackInventory.put("createKit", createKit);
        invPerso.setItem(40, createKit);

        inventory = invPerso;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player playerClicked = (Player) event.getWhoClicked();
        ItemStack itemClicked = event.getCurrentItem();
        Inventory inventoryClicked = event.getInventory();

        if (inventoryClikedValid(inventoryClicked, itemClicked)) {
            String nameItemCliked = itemClicked.getItemMeta().getDisplayName();

            if (getNameItemStack("nbJoueurMaxModify").equals(nameItemCliked)) {
                Rules.prompteInt(playerClicked, "nbJoueurMax", 2, 1000, this);

            } else if (getNameItemStack("gainParKillModify").equals(nameItemCliked)) {
                Rules.prompteInt(playerClicked, "gainParKill", 0, 1000000, this);

            } else if (getNameItemStack("nbVieModify").equals(nameItemCliked)) {
                Rules.prompteInt(playerClicked, "nbVie", 1, 1000, this);

            } else if (getNameItemStack("nbCoeurModify").equals(nameItemCliked)) {
                Rules.prompteInt(playerClicked, "nbCoeur", 1, 40, this);

            } else if (getNameItemStack("perteStuffModify").equals(nameItemCliked)) {
                Rules.changeBoolean(playerClicked, "perteStuff", this);

            } else if (getNameItemStack("jourModify").equals(nameItemCliked)) {
                Rules.changeBoolean(playerClicked, "jour", this);

            } else if (getNameItemStack("kitactifModify").equals(nameItemCliked)) {
                playerClicked.openInventory(new InventoryBattleKit(this).getInventory());

            } else if (getNameItemStack("createKit").equals(nameItemCliked)) {
                playerClicked.openInventory(new InventoryBattleCreateKit().getInventory());

            }
            event.setCancelled(true);
        }
    }

    private void createIntModifier(Inventory i, ItemStack is, int posBase, String nom, String key) {
        ItemStack firstItemStack = ItemCreator.createItemStack(is, null, 0, ChatColor.WHITE + nom + " : " + ChatColor.GOLD + "" + ChatColor.BOLD + Rules.getInt(key), null, false, true, true);
        i.setItem(posBase, firstItemStack);

        ItemStack secondItemStack = ItemCreator.createItemStack(null, Material.NAME_TAG, 1, ChatColor.WHITE + "Modifier " + nom, null, false, false, true);
        itemStackInventory.put(key + "Modify", secondItemStack);
        i.setItem(posBase + 9, secondItemStack);
    }

    private void createBooleanModifier(Inventory i, ItemStack is, int posBase, String nom, String key) {
        ItemStack interrupteur = new ItemStack(Material.INK_SACK, 1, (byte) 8);
        String value = "Non";

        if (Rules.getBoolean(key)) {
            interrupteur = new ItemStack(Material.INK_SACK, 1, (byte) 10);
            value = "Oui";
        }


        ItemStack firstItemStack = ItemCreator.createItemStack(is, null, 0, ChatColor.WHITE + nom + " : " + ChatColor.GOLD + "" + ChatColor.BOLD + value, null, false, true, true);
        i.setItem(posBase, firstItemStack);

        ItemStack secondItemStack = ItemCreator.createItemStack(interrupteur, null, 0, ChatColor.WHITE + "Modifier " + nom, null, false, false, true);
        itemStackInventory.put(key + "Modify", secondItemStack);
        i.setItem(posBase + 9, secondItemStack);
    }


}
