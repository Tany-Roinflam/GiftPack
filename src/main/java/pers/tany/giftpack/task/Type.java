package pers.tany.giftpack.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pers.tany.giftpack.CommonlyWay;
import pers.tany.giftpack.Main;
import pers.tany.giftpack.Other;

import java.util.Calendar;

public class Type extends BukkitRunnable {
    @Override
    public void run() {
        String time = CommonlyWay.getTime();
        int monthday = CommonlyWay.getMonthDay();
        int day = Integer.parseInt(time.split(":")[2]);
        int hour = Integer.parseInt(time.split(":")[3]);
        int minute = Integer.parseInt(time.split(":")[4]);
        for (String kit : Other.data.getConfigurationSection("GiftPackInfo").getKeys(false)) {
            String type = Other.data.getString("GiftPackInfo." + kit + ".Type");
            if (type.equalsIgnoreCase("daily")) {
                if (hour == 23 && minute == 50) {
                    for (String name : Other.data.getConfigurationSection("PlayerInfo").getKeys(false)) {
                        Other.data.set("PlayerInfo." + name + "." + kit + ".Number", 0);
                        Other.data.set("PlayerInfo." + name + "." + kit + ".Cooling", 0);
                    }
                }
            } else if (type.equalsIgnoreCase("weekly")) {
                int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
                if (week < 0 && hour == 23 && minute == 50) {
                    for (String name : Other.data.getConfigurationSection("PlayerInfo").getKeys(false)) {
                        Other.data.set("PlayerInfo." + name + "." + kit + ".Number", 0);
                        Other.data.set("PlayerInfo." + name + "." + kit + ".Cooling", 0);
                    }
                }
            } else if (type.equalsIgnoreCase("monthly")) {
                if (day == monthday && hour == 23 && minute == 50) {
                    for (String name : Other.data.getConfigurationSection("PlayerInfo").getKeys(false)) {
                        Other.data.set("PlayerInfo." + name + "." + kit + ".Number", 0);
                        Other.data.set("PlayerInfo." + name + "." + kit + ".Cooling", 0);
                    }
                }
            }
        }
        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
    }

}
