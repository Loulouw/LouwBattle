package fr.loulouw.louwbattle.scoreboard;

import com.coloredcarrot.api.sidebar.Sidebar;
import com.coloredcarrot.api.sidebar.SidebarString;
import fr.loulouw.louwbattle.Battle;
import fr.loulouw.louwbattle.Main;
import fr.loulouw.louwbattle.PlayerBattle;
import javafx.collections.ListChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class ScoPreparationBattle {

    private static Sidebar sidebar;
    private static boolean enCours;

    public static void showSidebar() {
        if (sidebar == null) {
            create();
        }
        for (Player p : Bukkit.getOnlinePlayers()) sidebar.showTo(p);
        enCours = true;
    }

    public static void hideSidebar() {
        if (sidebar != null){
            enCours = false;
            for (Player p : Bukkit.getOnlinePlayers()) sidebar.hideFrom(p);
        }
    }

    public static boolean isEnCours(){
        return enCours;
    }

    public static void showSideBar(Player p){
        if(sidebar != null) {
            sidebar.showTo(p);
        }
    }

    private static void create() {
        sidebar = new Sidebar(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Battle", Main.javaplugin, 20);
        sidebar.addEntry(new SidebarString(""));
        sidebar.addEntry(new SidebarString("=-=-=-=-=-=-=-=-"));
        SidebarString s3 = new SidebarString("Joueurs : " + Battle.battleEnAttente.getListPlayer().size() + "/" + Battle.battleEnAttente.getPositionSpawn().size());
        sidebar.addEntry(s3);

        SidebarString s4 = new SidebarString(".");
        sidebar.addEntry(s4);
        SidebarString s5 = new SidebarString(". ");
        sidebar.addEntry(s5);

        Battle.battleEnAttente.getListPlayer().addListener((ListChangeListener<PlayerBattle>) c -> {
            ArrayList<String> noms = new ArrayList<>();
            for (PlayerBattle p : Battle.battleEnAttente.getListPlayer()){
                String nom = p.getPlayer().getName();
                if(nom.length() > 16){
                    nom = nom.substring(0,16);
                }

                noms.add(nom);

                if(!s4.getVariations().contains(nom)){
                    s4.addVariation(nom);
                    s5.addVariation("Cote : " + new DecimalFormat("##.##").format(p.getCote()));
                }
            }

            for(int i=0;i<s4.getVariations().size();i++){
                String va = s4.getVariations().get(i);
                if(!noms.contains(va)){
                    s4.removeVariation(va);
                    s5.removeVariation(s5.getVariations().get(i));
                }
            }

            if(s4.getVariations().contains(".")){
                s4.removeVariation(".");
                s5.removeVariation(". ");
            }

            if(s4.getVariations().size() == 0){
                s4.addVariation(".");
                s5.addVariation(". ");
            }

            s3.removeVariation(s3.getVariations().get(0));
            s3.addVariation("Joueurs : " + Battle.battleEnAttente.getListPlayer().size() + "/" + Battle.battleEnAttente.getPositionSpawn().size());
        });

        sidebar.addEntry(new SidebarString("-=-=-=-=-=-=-=-="));

        sidebar.addEntry(new SidebarString("Temps d'attente"));

        SidebarString s7 = new SidebarString();
        sidebar.addEntry(s7);
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        int taskId = scheduler.scheduleSyncRepeatingTask(Main.javaplugin, new Runnable() {
            String last = "";

            @Override
            public void run() {
                if (!last.equals("")) s7.removeVariation(last);

                int tempsAttente = Battle.battleEnAttente.getSecondeAvantBattle();
                int minute = (int) Math.ceil(tempsAttente / 60);
                int seconde = tempsAttente % 60;

                last = minute + "min " + seconde + "s";
                s7.addVariation(last);
            }
        }, 0L, 20L);


        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getScheduler().cancelTask(taskId);
            }
        }.runTaskLater(Main.javaplugin,Battle.battleEnAttente.getSecondeAvantBattle()*20);

    }

}
