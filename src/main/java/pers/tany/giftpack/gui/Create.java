package pers.tany.giftpack.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;
import pers.tany.giftpack.Main;

public class Create implements InventoryHolder {
    Inventory inventory;
    Player player;
    String title;

    public Create(Player player, String title) {
        this.player = player;
        this.title = title;
        this.inventory = Bukkit.createInventory(this, 54, "§2放入§a" + title + "§2礼包内容的物品");
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void openInventory() {
        new BukkitRunnable() {

            @Override
            public void run() {
                player.openInventory(inventory);
            }

        }.runTask(Main.plugin);
    }

    public String getTitle() {
        return title;
    }
}
