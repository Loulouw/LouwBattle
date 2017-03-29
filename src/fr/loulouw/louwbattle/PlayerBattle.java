package fr.loulouw.louwbattle;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerBattle implements Comparable<PlayerBattle> {

    private YamlConfiguration yc;
    private Player player;
    private HashMap<String,Integer> stats;

    public PlayerBattle(Player p){
        player = p;

        stats = new HashMap<>();
        stats.put("nbKill",0);
        stats.put("nbMort",0);
        stats.put("nbVictory",0);

        loadPlayer();
    }

    public void add(String title,int value){
        if(stats.containsKey(title)){
            stats.put(title,value);
        }
    }

    public int getValue(String title){
        int res = -1;
        if(stats.containsKey(title)){
            res = yc.getInt(title);
        }
        return res;
    }

    private void loadPlayer(){
        File file = new File("plugins/LouwBattle/StatsBattle/" + player.getUniqueId() + ".yml");

        if(!file.exists()){
           createFile(file);
        }else{
            yc = YamlConfiguration.loadConfiguration(file);
            HashMap<String,Integer> ancStats = (HashMap<String, Integer>) stats.clone();
            stats.clear();
            for (Map.Entry<String,Integer> entry : ancStats.entrySet()){
                stats.put(entry.getKey(),yc.getInt(entry.getKey()));
            }
        }

    }

    private void createFile(File file) {
        try {
            file.createNewFile();
            yc = YamlConfiguration.loadConfiguration(file);

            for(Map.Entry<String,Integer> entry : stats.entrySet()){
                if(!yc.contains(entry.getKey())){
                    yc.set(entry.getKey(),entry.getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(PlayerBattle o) {
        return player.getName().compareTo(o.player.getName());
    }

    @Override
    public boolean equals(Object o){
        boolean res = false;
        if(o instanceof PlayerBattle){
            if(((PlayerBattle) o).player.getUniqueId().equals(player.getUniqueId())){
                res = true;
            }
        }
        return res;
    }
}
