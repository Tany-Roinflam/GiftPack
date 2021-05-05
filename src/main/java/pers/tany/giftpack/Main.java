package pers.tany.giftpack;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pers.tany.giftpack.command.Commands;
import pers.tany.giftpack.listenevent.Events;
import pers.tany.giftpack.task.Cooling;
import pers.tany.giftpack.task.Type;

import java.io.File;

public class Main extends JavaPlugin {
    public static Plugin plugin = null;

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getConsoleSender().sendMessage("§f[§bGiftPack§f]§a插件已加载");
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveResource("config.yml", false);
        }
        if (!new File(getDataFolder(), "data.yml").exists()) {
            saveResource("data.yml", false);
        }
        if (!new File(getDataFolder(), "message.yml").exists()) {
            saveResource("message.yml", false);
        }
        if (!new File(getDataFolder(), "redemptionCode.yml").exists()) {
            saveResource("redemptionCode.yml", false);
        }
        Player a = null;
        getCommand("gp").setExecutor(new Commands());
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        new Cooling().runTaskTimerAsynchronously(this, 1190, 1200);
        new Type().runTaskTimerAsynchronously(this, 1200, 1200);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§f[§bGiftPack§f]§c插件已卸载");
    }
}
