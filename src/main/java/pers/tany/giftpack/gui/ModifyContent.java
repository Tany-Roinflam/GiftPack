package pers.tany.giftpack.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;
import pers.tany.giftpack.CommonlyWay;
import pers.tany.giftpack.Main;
import pers.tany.giftpack.Other;

import java.util.ArrayList;
import java.util.List;

public class ModifyContent implements InventoryHolder {
    Inventory inventory;
    Player player;
    String name;

    public ModifyContent(Player player, String name) {
        Inventory inventory = Bukkit.createInventory(this, 54, "§2修改§a" + Other.data.getString("GiftPackInfo." + name + ".Color") + name + "§2礼包内容的物品");
        List<String> itemList = new ArrayList<String>(Other.data.getStringList("GiftPackInfo." + name + ".ItemList"));

        for (int i = 0; i <= 53; i++) {
            inventory.setItem(i, CommonlyWay.getItemStack(itemList.get(i)));
        }

        this.player = player;
        this.name = name;
        this.inventory = inventory;
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

    public String getName() {
        return name;
    }
}
