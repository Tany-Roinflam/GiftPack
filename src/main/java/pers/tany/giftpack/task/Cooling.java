package pers.tany.giftpack.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pers.tany.giftpack.CommonlyWay;
import pers.tany.giftpack.Main;
import pers.tany.giftpack.Other;
import pers.tany.giftpack.gui.DrawList;

public class Cooling extends BukkitRunnable {
    @Override
    public void run() {
        for (String name : Other.data.getConfigurationSection("PlayerInfo").getKeys(false)) {
            for (String kit : Other.data.getConfigurationSection("PlayerInfo." + name).getKeys(false)) {
                int cooling = Other.data.getInt("PlayerInfo." + name + "." + kit + ".Cooling") - 1;
                cooling = Math.max(cooling, 0);
                Other.data.set("PlayerInfo." + name + "." + kit + ".Cooling", cooling);
            }
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getOpenInventory().getTopInventory().getHolder() instanceof DrawList) {
                DrawList drawList = (DrawList) player.getOpenInventory().getTopInventory().getHolder();
                new DrawList(player, drawList.getPage()).openInventory();
            }
        }
        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
    }

}
