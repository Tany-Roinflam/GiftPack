package pers.tany.giftpack.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import pers.tany.giftpack.Main;

import java.util.ArrayList;
import java.util.List;

public class Select implements InventoryHolder {
    Inventory inventory;
    Player player;

    public Select(Player player) {
        Inventory inventory = Bukkit.createInventory(this, 9, "§b选择你要进行的操作");

        ItemStack anvil = null;
        ItemStack workBench = null;
        try {
            anvil = new ItemStack(Material.valueOf("ANVIL"));
            workBench = new ItemStack(Material.valueOf("WORKBENCH"));
        } catch (Exception e) {
            anvil = new ItemStack(Material.valueOf("LEGACY_ANVIL"));
            workBench = new ItemStack(Material.valueOf("LEGACY_WORKBENCH"));
        }
        ItemMeta anvil_itemMeta = anvil.getItemMeta();
        ItemMeta workBench_itemMeta = workBench.getItemMeta();

        List<String> lore = new ArrayList<String>();
        lore.add("§a选择后执行对应的操作");

        anvil_itemMeta.setDisplayName("§7编辑已有的礼包");
        anvil_itemMeta.setLore(lore);
        workBench_itemMeta.setDisplayName("§2新创建一个礼包");
        workBench_itemMeta.setLore(lore);

        anvil.setItemMeta(anvil_itemMeta);
        workBench.setItemMeta(workBench_itemMeta);

        inventory.setItem(2, anvil);
        inventory.setItem(6, workBench);

        this.player = player;
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
}
