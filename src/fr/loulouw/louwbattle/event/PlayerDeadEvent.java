package fr.loulouw.louwbattle.event;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.loulouw.louwbattle.Main;
import fr.loulouw.louwbattle.PlayerBattle;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import static fr.loulouw.louwbattle.Battle.*;


public class PlayerDeadEvent implements Listener {

    private Location loc;

    public PlayerDeadEvent() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.javaplugin);

        RegionManager rm = Main.worldGuard.getRegionManager(world);
        ProtectedRegion pr = rm.getRegions().get(NAME_OF_ARENA_SPEC);
        BlockVector bvMin = pr.getMinimumPoint();
        BlockVector bvMax = pr.getMaximumPoint();

        double x = bvMax.getX() - ((bvMax.getX() - bvMin.getX()) / 2);
        double y = bvMax.getY() - ((bvMax.getY() - bvMin.getY()) / 2);
        double z = bvMax.getZ() - ((bvMax.getZ() - bvMin.getZ()) / 2);

        loc = new Location(world, x, y, z);
    }

    @EventHandler
    public void onPlayerDead(PlayerDeathEvent e) {
        PlayerBattle pb = PlayerBattle.listPlayer.get(e.getEntity());
        if (pb.isOnBattle()) {
            e.setKeepInventory(true);
            pb.setOnBattle(false);

            RegionManager rm = Main.worldGuard.getRegionManager(world);
            ProtectedRegion pr2 = rm.getRegions().get(NAME_OF_ARENA_SPEC);
            DefaultDomain dd2 = pr2.getOwners();
            dd2.addPlayer(pb.getPlayer().getUniqueId());

            pb.setOnSpecatate(true);
            pb.getPlayer().setGameMode(GameMode.SPECTATOR);

        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        PlayerBattle pb = PlayerBattle.listPlayer.get(e.getPlayer());
        if (pb.isOnSpecatate()) {
            pb.getPlayer().teleport(loc);
        }
    }

}
