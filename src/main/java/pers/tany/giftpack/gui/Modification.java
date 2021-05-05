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
import java.util.List;

public class Modification implements InventoryHolder {
    Inventory inventory;
    Player player;
    String name;

    public Modification(Player player, String name) {
        Inventory inventory = Bukkit.createInventory(this, 9, "§a请选择编辑§f" + Other.data.getString("GiftPackInfo." + name + ".Color") + name + "§a的类型");

        ItemStack color = BasicLibrary.stainedwool.get(1);
        ItemStack material = null;
        ItemStack content = null;
        ItemStack remove = null;
        ItemStack back = null;
        try {
            material = new ItemStack(Material.valueOf("LEATHER"));
            content = new ItemStack(Material.valueOf("STORAGE_MINECART"));
            remove = new ItemStack(Material.valueOf("LAVA_BUCKET"));
            back = new ItemStack(Material.valueOf("EYE_OF_ENDER"));
        } catch (Exception e) {
            material = new ItemStack(Material.valueOf("LEGACY_LEATHER"));
            content = new ItemStack(Material.valueOf("LEGACY_STORAGE_MINECART"));
            remove = new ItemStack(Material.valueOf("LEGACY_LAVA_BUCKET"));
            back = new ItemStack(Material.valueOf("LEGACY_EYE_OF_ENDER"));
        }
        ItemMeta colorMeta = color.getItemMeta();
        ItemMeta materialMeta = material.getItemMeta();
        ItemMeta contentMeta = content.getItemMeta();
        ItemMeta removeMeta = remove.getItemMeta();
        ItemMeta backMeta = back.getItemMeta();

        List<String> lore = new ArrayList<String>();

        colorMeta.setDisplayName("§6选择礼包名称颜色");
        materialMeta.setDisplayName("§e选择礼包显示的材质");
        contentMeta.setDisplayName("§2设置礼包的内容");
        removeMeta.setDisplayName("§c删除这个礼包");
        backMeta.setDisplayName("§5返回礼包列表界面");

        lore.add("§a点击后选择你礼包名称显示的颜色");
        colorMeta.setLore(lore);
        lore.clear();

        lore.add("§a点击后在空位置放入你想礼包在界面显示的物品材质的物品");
        lore.add("§7是不是有点绕，意思就是礼包想显示什么物品样子就把对应物品扔进去");
        materialMeta.setLore(lore);
        lore.clear();

        lore.add("§a重新编辑你的这个礼包内容！");
        contentMeta.setLore(lore);
        lore.clear();

        lore.add("§a删除你的这个礼包吧");
        removeMeta.setLore(lore);
        lore.clear();

        lore.add("§5返回到第一页");
        backMeta.setLore(lore);
        lore.clear();

        color.setItemMeta(colorMeta);
        material.setItemMeta(materialMeta);
        content.setItemMeta(contentMeta);
        remove.setItemMeta(removeMeta);
        back.setItemMeta(backMeta);

        inventory.setItem(0, color);
        inventory.setItem(2, material);
        inventory.setItem(4, content);
        inventory.setItem(6, remove);
        inventory.setItem(8, back);

        this.inventory = inventory;
        this.player = player;
        this.name = name;
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
