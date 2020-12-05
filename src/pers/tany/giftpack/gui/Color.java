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
import pers.tany.giftpack.Main;
import pers.tany.giftpack.Other;

import java.util.ArrayList;

public class Color implements InventoryHolder {
    Inventory inventory;
    Player player;
    String name;

    public Color(Player player, String name) {
        Inventory inventory = Bukkit.createInventory(this, 27, "§2修改§a" + Other.data.getString("GiftPackInfo." + name + ".Color") + name + "§2礼包名称颜色");

        ItemStack red = BasicLibrary.stainedglass.get(14);
        ItemStack yellow = BasicLibrary.stainedglass.get(4);
        ItemStack blue = BasicLibrary.stainedglass.get(9);
        ItemStack green = BasicLibrary.stainedglass.get(5);
        ItemStack purple = BasicLibrary.stainedglass.get(10);
        ItemStack orange = BasicLibrary.stainedglass.get(1);
        ItemStack white = BasicLibrary.stainedglass.get(0);
        ItemStack lightGreen = BasicLibrary.stainedglass.get(13);
        ItemStack lightPurple = BasicLibrary.stainedglass.get(2);
        ItemStack lightRed = BasicLibrary.stainedglass.get(6);
        ItemStack gray = BasicLibrary.stainedglass.get(7);
        ItemStack lightBlue = BasicLibrary.stainedglass.get(3);
        ItemStack black = BasicLibrary.stainedglass.get(15);
        ItemStack drakBlue = BasicLibrary.stainedglass.get(11);
        ItemStack back;
        try {
            back = new ItemStack(Material.valueOf("EYE_OF_ENDER"));
        } catch (Exception a) {
            back = new ItemStack(Material.valueOf("ENDER_EYE"));
        }

        ItemMeta glassMeta = red.getItemMeta();
        ItemMeta backMeta = back.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();

        glassMeta.setDisplayName("§4红色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        red.setItemMeta(glassMeta);
        lore.clear();

        glassMeta.setDisplayName("§e黄色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        yellow.setItemMeta(glassMeta);
        lore.clear();

        glassMeta.setDisplayName("§6橙色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        orange.setItemMeta(glassMeta);
        lore.clear();

        glassMeta.setDisplayName("§3蓝色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        blue.setItemMeta(glassMeta);
        lore.clear();

        glassMeta.setDisplayName("§2绿色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        green.setItemMeta(glassMeta);
        lore.clear();

        glassMeta.setDisplayName("§5紫色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        purple.setItemMeta(glassMeta);
        lore.clear();

        glassMeta.setDisplayName("§f白色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        white.setItemMeta(glassMeta);
        lore.clear();

        glassMeta.setDisplayName("§a浅绿色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        lightGreen.setItemMeta(glassMeta);
        lore.clear();

        glassMeta.setDisplayName("§7灰色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        gray.setItemMeta(glassMeta);
        lore.clear();

        glassMeta.setDisplayName("§c粉红色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        lightRed.setItemMeta(glassMeta);
        lore.clear();

        glassMeta.setDisplayName("§d亮紫色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        lightPurple.setItemMeta(glassMeta);
        lore.clear();

        glassMeta.setDisplayName("§b天蓝色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        lightBlue.setItemMeta(glassMeta);
        lore.clear();

        glassMeta.setDisplayName("§1深蓝色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        drakBlue.setItemMeta(glassMeta);
        lore.clear();

        glassMeta.setDisplayName("§0黑色");
        lore.add("§a设置名字颜色");
        glassMeta.setLore(lore);
        black.setItemMeta(glassMeta);
        lore.clear();

        backMeta.setDisplayName("§5返回设置界面");
        lore.add("§7是不是感觉颜色设置很像某个插件，我就是抄袭它的233");
        backMeta.setLore(lore);
        back.setItemMeta(backMeta);
        lore.clear();

        inventory.setItem(1, blue);
        inventory.setItem(3, green);
        inventory.setItem(5, purple);
        inventory.setItem(7, orange);
        inventory.setItem(10, red);
        inventory.setItem(11, black);
        inventory.setItem(12, yellow);
        inventory.setItem(13, back);
        inventory.setItem(14, lightGreen);
        inventory.setItem(15, drakBlue);
        inventory.setItem(16, white);
        inventory.setItem(19, lightPurple);
        inventory.setItem(21, lightBlue);
        inventory.setItem(23, lightRed);
        inventory.setItem(25, gray);

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