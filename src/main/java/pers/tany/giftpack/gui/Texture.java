package pers.tany.giftpack.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import pers.tany.giftpack.BasicLibrary;
import pers.tany.giftpack.CommonlyWay;
import pers.tany.giftpack.Main;
import pers.tany.giftpack.Other;

import java.util.ArrayList;
import java.util.List;

public class Texture implements InventoryHolder {
    Inventory inventory;
    Player player;
    String name;

    public Texture(Player player, String name) {
        Inventory inventory = Bukkit.createInventory(this, 45, "§2修改§a" + Other.data.getString("GiftPackInfo." + name + ".Color") + name + "§2礼包的材质");

        ItemStack frame = BasicLibrary.stainedglass.get(10);
        ItemStack back = null;
        try {
            back = new ItemStack(Material.valueOf("EYE_OF_ENDER"));
        } catch (Exception e) {
            back = new ItemStack(Material.valueOf("LEGACY_EYE_OF_ENDER"));
        }
        ItemMeta frameMeta = frame.getItemMeta();
        ItemMeta backMeta = back.getItemMeta();

        List<String> lore = new ArrayList<String>();

        frameMeta.setDisplayName("§5请在中间放入你要设置的物品材质的物品");
        lore.add("§a上面为可开启状态的材质");
        lore.add("§a下面为不可开启状态的材质");
        frameMeta.setLore(lore);
        lore.clear();
        frame.setItemMeta(frameMeta);

        backMeta.setDisplayName("§5返回设置界面");
        back.setItemMeta(backMeta);

        for (int i = 0; i <= 44; i++) {
            if (i != 22 && i != 31) {
                inventory.setItem(i, frame);
            }
        }
        inventory.setItem(40, back);

        if (!Other.data.getString("GiftPackInfo." + name + ".Icon").equalsIgnoreCase("null")) {
            inventory.setItem(22, CommonlyWay.getItemStack(Other.data.getString("GiftPackInfo." + name + ".Icon")));
        }
        if (!Other.data.getString("GiftPackInfo." + name + ".OtherIcon").equalsIgnoreCase("null")) {
            inventory.setItem(31, CommonlyWay.getItemStack(Other.data.getString("GiftPackInfo." + name + ".OtherIcon")));
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