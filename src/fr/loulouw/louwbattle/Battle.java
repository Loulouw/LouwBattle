package fr.loulouw.louwbattle;


import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.loulouw.louwbattle.scoreboard.ScoPreparationBattle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Battle {

    public static Battle battleEnCours;
    public static Battle battleEnAttente;

    public static String NAME_OF_ARENA = "";
    public static String NAME_OF_ARENA_SPEC = "";
    public static World world;
    public static int tempsMax;
    public static int tempsAttenteMin;
    public static int tempsAttenteMax;


    private ObservableList<PlayerBattle> listPlayer;
    private boolean enCours = false;

    private ArrayList<Location> positionSpawn;
    private int secondeAvantBattle;

    public Battle() {
        listPlayer = FXCollections.observableArrayList();
        positionSpawn = new ArrayList<>();

        getRegion();
    }

    public static void initialisation() {
        NAME_OF_ARENA = Main.javaplugin.getConfig().getString("nameregion");
        NAME_OF_ARENA_SPEC = Main.javaplugin.getConfig().getString("nameregionspec");
        tempsMax = Main.javaplugin.getConfig().getInt("maxtempbattle");
        world = Main.javaplugin.getServer().getWorld("world");
        tempsAttenteMin = Main.javaplugin.getConfig().getInt("tempsattentemin");
        tempsAttenteMax = Main.javaplugin.getConfig().getInt("tempsattentemax");

        if (world == null) {
            Bukkit.getConsoleSender().sendMessage("Erreur le monde n'existe pas");
        }
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
                        Location l = new Location(world, x+ 0.5, y+1, z+0.5);
                        positionSpawn.add(l);
                    }
                }
            }
        }

        Collections.shuffle(positionSpawn);

    }

    private void start() {
        if (listPlayer.size() > 0) {
            PotionEffect pe = new PotionEffect(PotionEffectType.BLINDNESS, 20 * 50, 50);
            enCours = true;
            RegionManager rm = Main.worldGuard.getRegionManager(world);
            ProtectedRegion pr = rm.getRegions().get(NAME_OF_ARENA);
            if (pr != null) {
                DefaultDomain dd = pr.getOwners();
                int ipos = 0;
                for (PlayerBattle pb : listPlayer) {
                    dd.addPlayer(pb.getPlayer().getUniqueId());
                    pb.setOnBattle(true);
                    pb.getPlayer().addPotionEffect(pe);
                    pb.getPlayer().teleport(positionSpawn.get(ipos));
                    ipos++;
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            pb.setOnWait(true);
                        }
                    }.runTaskLater(Main.javaplugin,4);
                }
            }

            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            int taskID = scheduler.scheduleSyncRepeatingTask(Main.javaplugin, new Runnable() {

                int second = 10;

                @Override
                public void run() {
                    if(second > 0){
                        for (PlayerBattle pb : listPlayer) {
                            pb.getPlayer().sendTitle(second + "", "", 2, 16, 2);
                        }
                    }
                    second--;
                }

            }, 0L, 20L);

            new BukkitRunnable() {

                @Override
                public void run() {
                    scheduler.cancelTask(taskID);
                    for (PlayerBattle pb : listPlayer) {
                        pb.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
                        pb.setOnWait(false);
                        pb.getPlayer().sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "START","",2,16,2);
                    }
                }

            }.runTaskLater(Main.javaplugin, 20 * 10);
        }
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
                        p.sendTitle(texte, texte2, 10, 80, 10);
                    }
                } else if (tick == scoreboard) {
                    ScoPreparationBattle.showSidebar();
                }
            }
        }, 0L, 20L);

        new BukkitRunnable() {
            public void run() {
                Bukkit.getScheduler().cancelTask(taskID);
                ScoPreparationBattle.hideSidebar();
                Battle.battleEnCours = Battle.this;
                Battle.battleEnAttente = new Battle();
                //Battle.battleEnAttente.processStart();
                start();
            }
        }.runTaskLater(Main.javaplugin, waitingTimeSecond * 20);
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
