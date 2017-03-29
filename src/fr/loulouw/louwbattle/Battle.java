package fr.loulouw.louwbattle;


import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.loulouw.louwbattle.scoreboard.ScoPreparationBattle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Random;

public class Battle {

    public static Battle battleEnCours;
    public static Battle battleEnAttente;

    private String NAME_OF_ARENA = "";
    private World world;
    private ObservableList<PlayerBattle> listPlayer;
    private boolean enCours = false;
    private int tempsMax;
    private int tempsAttenteMin;
    private int tempsAttenteMax;
    private ArrayList<Location> positionSpawn;
    private int secondeAvantBattle;

    public Battle() {
        listPlayer = FXCollections.observableArrayList();
        positionSpawn = new ArrayList<>();

        NAME_OF_ARENA = Main.javaplugin.getConfig().getString("nameregion");
        tempsMax = Main.javaplugin.getConfig().getInt("maxtempbattle");
        world = Main.javaplugin.getServer().getWorld("world");
        tempsAttenteMin = Main.javaplugin.getConfig().getInt("tempsattentemin");
        tempsAttenteMax = Main.javaplugin.getConfig().getInt("tempsattentemax");

        if (world == null) {
            Bukkit.getConsoleSender().sendMessage("Erreur le monde n'existe pas");
        }

        getRegion();
    }

    private void getRegion() {
        RegionManager rm = Main.worldGuard.getRegionManager(world);
        ProtectedRegion pr = rm.getRegions().get(NAME_OF_ARENA);
        if (pr == null) {
            Bukkit.getConsoleSender().sendMessage("La région " + NAME_OF_ARENA + " n'existe pas");
            return;
        }

        BlockVector bvMin = pr.getMinimumPoint();
        BlockVector bvMax = pr.getMaximumPoint();
        Location min = new Location(world, Math.ceil(bvMin.getX()), Math.ceil(bvMin.getY()), Math.ceil(bvMin.getZ()));
        Location max = new Location(world, Math.ceil(bvMax.getX()), Math.ceil(bvMax.getY()), Math.ceil(bvMax.getZ()));

        for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    if (world.getBlockAt(new Location(world, x, y, z)).getType().equals(Material.BEDROCK)) {
                        positionSpawn.add(new Location(world, (x < 0) ? x + 1 : x, (y < 0) ? y + 1 : y, (z < 0) ? z + 1 : z));
                        Bukkit.broadcastMessage("Loc : " + ((x < 0) ? x + 1 : x) + " : " + ((y < 0) ? y + 1 : y) + " : " + ((z < 0) ? z + 1 : z));
                    }
                }
            }
        }

    }

    private Location getLocationOfBlock(BlockVector bv) {
        double v1 = Math.ceil(bv.getX());
        if (v1 < 0) v1 += 1;

        double v2 = Math.ceil(bv.getY());
        if (v2 < 0) v2 += 1;

        double v3 = Math.ceil(bv.getZ());
        if (v3 < 0) v3 += 1;

        return new Location(world, v1, v2, v3);
    }

    private void start() {
        enCours = true;
    }

    private void stop() {
        enCours = false;
    }

    public void processStart() {
        final int waitingTimeSecond = (new Random().nextInt(tempsAttenteMax - tempsAttenteMin) + tempsAttenteMin) * 60;
        final int messageChat = 5;
        final int scoreboard = 10;

        //15min : Message dans le chat : 900s
        //10min scoreboard : 600s

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        int taskID = scheduler.scheduleSyncRepeatingTask(Main.javaplugin, new Runnable() {
            int tick = 0;

            @Override
            public void run() {
                tick++;
                secondeAvantBattle = waitingTimeSecond - tick;
                if (tick == messageChat) {
                    String texte = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + "Battle dans " + ChatColor.GOLD + "" + ChatColor.BOLD + " 15 " + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + " min !";
                    String texte2 = ChatColor.WHITE + "Pour rejoindre : " + ChatColor.BOLD + "/battle";
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                       p.sendTitle(texte,texte2,10,80,10);
                    }
                } else if (tick == scoreboard) {
                    ScoPreparationBattle.showSidebar();
                }
            }
        }, 0L, 20L);

        new BukkitRunnable(){
            public void run(){
                Bukkit.getScheduler().cancelTask(taskID);
                ScoPreparationBattle.hideSidebar();
            }
        }.runTaskLater(Main.javaplugin,waitingTimeSecond * 20);
    }

    public String getNAME_OF_ARENA() {
        return NAME_OF_ARENA;
    }

    public World getWorld() {
        return world;
    }

    public ObservableList<PlayerBattle> getListPlayer() {
        return listPlayer;
    }

    public boolean isEnCours() {
        return enCours;
    }

    public int getTempsMax() {
        return tempsMax;
    }

    public int getTempsAttenteMin() {
        return tempsAttenteMin;
    }

    public int getTempsAttenteMax() {
        return tempsAttenteMax;
    }

    public ArrayList<Location> getPositionSpawn() {
        return positionSpawn;
    }

    public int getSecondeAvantBattle() {
        return secondeAvantBattle;
    }
}
