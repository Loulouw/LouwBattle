package fr.loulouw.louwbattle.outils;

import fr.loulouw.louwbattle.Main;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;

/**
 * Created by Loulouw on 15/02/2017.
 */
public class Outils {

    public static HashMap<String,Enchantment> enchantement = new HashMap<>();


    public static boolean isInteger(String texte){
        boolean res = false;
        try{
            Integer.parseInt(texte);
            res = true;
        }catch (Exception ignored){}
        return res;
    }

}
