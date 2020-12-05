package pers.tany.giftpack.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.scheduler.BukkitRunnable;
import pers.tany.giftpack.BasicLibrary;
import pers.tany.giftpack.CommonlyWay;
import pers.tany.giftpack.Main;
import pers.tany.giftpack.Other;

import java.util.ArrayList;
import java.util.List;

public class DrawList implements InventoryHolder {
    Inventory inventory;
    Player player;
    int page;

    public DrawList(Player player, int page) {
        if (page > 1) {
            if (Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).size() < (page - 1) * 45 + 1) {
                new DrawList(player, page - 1).openInventory();
                return;
            }
        }

        Inventory inventory = Bukkit.createInventory(this, 54, "§2请选择你要查看/领取的礼包");
        ItemStack frame = BasicLibrary.stainedglass.get(4);
        ItemStack last = BasicLibrary.stainedglass.get(11);
        ItemStack next = BasicLibrary.stainedglass.get(1);
        ItemMeta frameMeta = frame.getItemMeta();
        ItemMeta lastMeta = frame.getItemMeta();
        ItemMeta nextMeta = frame.getItemMeta();

        frameMeta.setDisplayName("§2介绍");
        lastMeta.setDisplayName("§3上一页");
        nextMeta.setDisplayName("§6下一页");

        List<String> lore = new ArrayList<String>();

        lore.add("§a左键查看礼包内容，右键领取礼包");
        frameMeta.setLore(lore);
        lore.clear();

        lore.add("§a翻到下一页");
        nextMeta.setLore(lore);
        lore.clear();

        lore.add("§a翻到上一页");
        lastMeta.setLore(lore);
        lore.clear();

        frame.setItemMeta(frameMeta);
        last.setItemMeta(lastMeta);
        next.setItemMeta(nextMeta);

        if (page > 1) {
            inventory.setItem(45, last);
        } else {
            inventory.setItem(45, frame);
        }
        for (int i = 46; i <= 52; i++) {
            inventory.setItem(i, frame);
        }
        if (Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).size() > 45 + (page - 1) * 45) {
            inventory.setItem(53, next);
        } else {
            inventory.setItem(53, frame);
        }
        ArrayList<String> list = new ArrayList<String>(Other.data.getConfigurationSection("GiftPackInfo").getKeys(false));

        int index = (page - 1) * 45;
        int location = 0;
        int size = list.size() - 1;
        while (index <= size && index <= 44 + (page - 1) * 45) {
            String name = list.get(index);
            if (Other.data.getString("GiftPackInfo." + name + ".Type").equalsIgnoreCase("normal")) {
                lore.add("§2刷新模式： §a常驻礼包");
            } else if (Other.data.getString("GiftPackInfo." + name + ".Type").equalsIgnoreCase("daily")) {
                lore.add("§2刷新模式： §b每日刷新礼包");
            } else if (Other.data.getString("GiftPackInfo." + name + ".Type").equalsIgnoreCase("weekly")) {
                lore.add("§2刷新模式： §6每周刷新礼包");
            } else if (Other.data.getString("GiftPackInfo." + name + ".Type").equalsIgnoreCase("monthly")) {
                lore.add("§2刷新模式： §5每月刷新礼包");
            }
            int number = Other.data.getInt("GiftPackInfo." + name + ".Number");
            for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
                if (permission.getPermission().startsWith("gp.number." + name + ".") || permission.getPermission().startsWith("gp.number.*.") && !permission.getPermission().equals("gp.number.*.*")) {
                    number = Integer.parseInt(permission.getPermission().replace("gp.number." + name + ".", "").replace("gp.number.*.", ""));
                }
            }
            if (player.hasPermission("gp.number.*.*")) {
                number = 99999999;
            }
            number -= Other.data.getInt("PlayerInfo." + player.getName() + "." + name + ".Number");
            number = Math.max(number, 0);
            lore.add("§2礼包剩余领取次数： §a" + number + " §2次");

            int t = Other.data.getInt("GiftPackInfo." + name + ".Cooling") - Other.data.getInt("PlayerInfo." + player.getName() + "." + name + ".Cooling");
            t = Math.max(t, 0);
            String time = CommonlyWay.getDay(Other.data.getInt("PlayerInfo." + player.getName() + "." + name + ".Cooling"));
            lore.add("§2礼包剩余领取冷却： §a" + time.split(":")[0] + "日" + time.split(":")[1] + "时" + time.split(":")[2] + "分");

            boolean available = false;
            if (number > 0 && t == Other.data.getInt("GiftPackInfo." + name + ".Cooling") && (player.hasPermission("gp.kit.*") || player.hasPermission("gp.kit." + name))) {
                available = true;
            }

            if (Other.data.getStringList("GiftPackInfo." + name + ".Lore").size() > 0) {
                lore.add("");
                lore.addAll(Other.data.getStringList("GiftPackInfo." + name + ".Lore"));
            }
            lore.add("");
            lore.add("§e左键查看礼包内容");
            if (available) {
                lore.add("§6右键领取礼包");
            }

            ItemStack itemStack;
            if (available) {
                if (!Other.data.getString("GiftPackInfo." + name + ".Icon").equalsIgnoreCase("null")) {
                    itemStack = CommonlyWay.getItemStack(Other.data.getString("GiftPackInfo." + name + ".Icon"));
                    for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
                        itemStack.removeEnchantment(enchantment);
                    }
                } else {
                    try {
                        itemStack = new ItemStack(Material.valueOf("STORAGE_MINECART"));
                    } catch (Exception e) {
                        itemStack = new ItemStack(Material.valueOf("LEGACY_STORAGE_MINECART"));
                    }
                }
            } else {
                if (!Other.data.getString("GiftPackInfo." + name + ".OtherIcon").equalsIgnoreCase("null")) {
                    itemStack = CommonlyWay.getItemStack(Other.data.getString("GiftPackInfo." + name + ".OtherIcon"));
                    for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
                        itemStack.removeEnchantment(enchantment);
                    }
                } else {
                    try {
                        itemStack = new ItemStack(Material.valueOf("MINECART"));
                    } catch (Exception e) {
                        itemStack = new ItemStack(Material.valueOf("LEGACY_MINECART"));
                    }
                }
            }
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Other.data.getString("GiftPackInfo." + name + ".Color") + name);

            itemMeta.setLore(lore);
            lore.clear();
            itemStack.setItemMeta(itemMeta);

            inventory.setItem(location, itemStack);
            location++;
            index++;
        }

        this.player = player;
        this.page = page;
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

    public int getPage() {
        return page;
    }
}
