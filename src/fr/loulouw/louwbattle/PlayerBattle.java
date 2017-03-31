package fr.loulouw.louwbattle;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerBattle implements Comparable<PlayerBattle> {

    public static HashMap<Player, PlayerBattle> listPlayer;
    private YamlConfiguration yc;
    private Player player;
    private HashMap<String, Integer> stats;
    private boolean onBattle;
    private boolean onSpecatate;
    private boolean onWait;

    public PlayerBattle(Player p) {
        player = p;
        onBattle = false;
        onSpecatate = false;
        onWait = false;
        stats = new HashMap<>();
        stats.put("nbKill", 0);
        stats.put("nbMort", 0);
        stats.put("nbVictory", 0);

        loadPlayer();
    }

    public void add(String title, int value) {
        if (stats.containsKey(title)) {
            stats.put(title, value);
        }
    }

    public int getValue(String title) {
        int res = -1;
        if (stats.containsKey(title)) {
            res = yc.getInt(title);
        }
        return res;
    }

    private void loadPlayer() {
        final File file = new File("plugins" + File.separator + "LouwBattle" + File.separator + "Stats" + File.separator + player.getUniqueId() + ".yml");
        final File chemin = new File("plugins" + File.separator + "LouwBattle" + File.separator + "Stats");

        if (!chemin.exists()) chemin.mkdir();

        if (!file.exists()) {
            createFile(file);
        } else {
            loadFile(file);
        }

    }

    private void createFile(File file) {
        try {
            file.createNewFile();
            yc = YamlConfiguration.loadConfiguration(file);
            FileWriter fw = new FileWriter(file);
            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                fw.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFile(File file) {
        yc = YamlConfiguration.loadConfiguration(file);
        HashMap<String, Integer> ancStats = (HashMap<String, Integer>) stats.clone();
        stats.clear();

        for (Map.Entry<String, Integer> entry : ancStats.entrySet()) {
            stats.put(entry.getKey(), yc.getInt(entry.getKey()));
        }
    }

    @Override
    public int compareTo(PlayerBattle o) {
        return player.getName().compareTo(o.player.getName());
    }

    @Override
    public boolean equals(Object o) {
        boolean res = false;
        if (o instanceof PlayerBattle) {
            if (((PlayerBattle) o).player.getUniqueId().equals(player.getUniqueId())) {
                res = true;
            }
        } else if (o instanceof Player) {
            if (((Player) o).getUniqueId().equals(player.getUniqueId())) {
                res = true;
            }
        }
        return res;
    }

    public YamlConfiguration getYc() {
        return yc;
    }

    public Player getPlayer() {
        return player;
    }

    public HashMap<String, Integer> getStats() {
        return stats;
    }

    public double getCote() {
        double res = 1;
        if (stats.get("nbMort") != 0) {
            res = 1 + (stats.get("nbKill") / stats.get("nbMort"));
        }
        return res;
    }

    public boolean isOnBattle() {
        return onBattle;
    }

    public void setOnBattle(boolean onBattle) {
        this.onBattle = onBattle;
    }

    public boolean isOnSpecatate() {
        return onSpecatate;
    }

    public void setOnSpecatate(boolean onSpecatate) {
        this.onSpecatate = onSpecatate;
    }

    public boolean isOnWait() {
        return onWait;
    }

    public void setOnWait(boolean onWait) {
        this.onWait = onWait;
    }
}
