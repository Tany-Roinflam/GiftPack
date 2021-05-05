package pers.tany.giftpack.listenevent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.scheduler.BukkitRunnable;
import pers.tany.giftpack.CommonlyWay;
import pers.tany.giftpack.Main;
import pers.tany.giftpack.Other;
import pers.tany.giftpack.gui.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Events implements Listener {
    public static Set<String> createList = new HashSet<String>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        if (!(evt.getWhoClicked() instanceof Player) || evt.getClickedInventory() == null) {
            return;
        }
        if (evt.getCurrentItem() == null || evt.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        if (evt.getInventory().getHolder() == null) {
            return;
        }
        Player player = (Player) evt.getWhoClicked();
        if (evt.getInventory().getHolder() instanceof Select) {
            Select select = (Select) evt.getInventory().getHolder();
            evt.setCancelled(true);
            if (evt.getClickedInventory().getHolder() instanceof Select) {
                if (evt.getRawSlot() == 2) {
                    new SettingList(player, 1).openInventory();
                    return;
                }
                if (evt.getRawSlot() == 6) {
                    player.closeInventory();
                    createList.add(player.getName());
                    player.sendMessage("§a输入新礼包的名称（不支持颜色代码）");
                    return;
                }
            }
            return;
        }
        if (evt.getInventory().getHolder() instanceof SettingList) {
            SettingList settingList = (SettingList) evt.getInventory().getHolder();
            int page = settingList.getPage();
            evt.setCancelled(true);
            if (evt.getClickedInventory().getHolder() instanceof SettingList) {
                if (evt.getCurrentItem().hasItemMeta() && evt.getCurrentItem().getItemMeta().hasDisplayName()) {
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§2介绍")) {
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a翻到上一页")) {
                        new SettingList(player, --page).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a翻到下一页")) {
                        new SettingList(player, ++page).openInventory();
                        return;
                    }
                    String name = ChatColor.stripColor(evt.getCurrentItem().getItemMeta().getDisplayName());
                    new Modification(player, name).openInventory();
                }
            }
            return;
        }
        if (evt.getInventory().getHolder() instanceof Modification) {
            Modification modification = (Modification) evt.getInventory().getHolder();
            String name = modification.getName();
            evt.setCancelled(true);
            if (evt.getClickedInventory().getHolder() instanceof Modification) {
                if (evt.getCurrentItem().hasItemMeta() && evt.getCurrentItem().getItemMeta().hasDisplayName()) {
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§6选择礼包名称颜色")) {
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§e选择礼包显示的材质")) {
                        new Texture(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§2设置礼包的内容")) {
                        new ModifyContent(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§c删除这个礼包")) {
                        player.sendMessage("§a成功删除礼包： " + Other.data.getString("GiftPackInfo." + name + ".Color") + name);
                        Other.data.set("GiftPackInfo." + name, null);
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        for (String n : Other.data.getConfigurationSection("PlayerInfo").getKeys(false)) {
                            Other.data.set("PlayerInfo." + n + "." + name, null);
                        }
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.getOpenInventory().getTopInventory().getType() != InventoryType.CRAFTING && p.getOpenInventory().getTopInventory().getHolder() != null) {
                                if (p.getOpenInventory().getTopInventory().getHolder() instanceof SettingList) {
                                    new SettingList(player, ((SettingList) p.getOpenInventory().getTopInventory().getHolder()).getPage()).openInventory();
                                } else if (p.getOpenInventory().getTopInventory().getHolder() instanceof Modification) {
                                    if (((Modification) p.getOpenInventory().getTopInventory().getHolder()).getName().equals(name)) {
                                        new SettingList(player, 1).openInventory();
                                    }
                                } else if (p.getOpenInventory().getTopInventory().getHolder() instanceof Color) {
                                    if (((Color) p.getOpenInventory().getTopInventory().getHolder()).getName().equals(name)) {
                                        new SettingList(player, 1).openInventory();
                                    }
                                } else if (p.getOpenInventory().getTopInventory().getHolder() instanceof Texture) {
                                    if (((Texture) p.getOpenInventory().getTopInventory().getHolder()).getName().equals(name)) {
                                        new SettingList(player, 1).openInventory();
                                    }
                                }
                            }
                        }
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§5返回礼包列表界面")) {
                        new SettingList(player, 1).openInventory();
                        return;
                    }
                }
            }
            return;
        }
        if (evt.getInventory().getHolder() instanceof Color) {
            Color color = (Color) evt.getInventory().getHolder();
            String name = color.getName();
            evt.setCancelled(true);
            if (evt.getClickedInventory().getHolder() instanceof Color) {
                if (evt.getCurrentItem().hasItemMeta() && evt.getCurrentItem().getItemMeta().hasDisplayName()) {
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§5返回设置界面")) {
                        new Modification(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§4红色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§4");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§e黄色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§e");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§3蓝色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§3");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§1深蓝色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§1");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§0黑色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§0");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§5紫色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§5");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§6橙色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§6");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§f白色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§f");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a浅绿色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§a");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§2绿色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§2");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§6橙色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§6");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§b天蓝色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§b");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§c粉红色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§c");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§d亮紫色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§d");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§7灰色")) {
                        Other.data.set("GiftPackInfo." + name + ".Color", "§7");
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                        player.sendMessage("§a修改成功！");
                        new Color(player, name).openInventory();
                        return;
                    }
                }
            }
            return;
        }
        if (evt.getInventory().getHolder() instanceof Texture) {
            Texture texture = (Texture) evt.getInventory().getHolder();
            if (evt.getCurrentItem().hasItemMeta() && evt.getCurrentItem().getItemMeta().hasDisplayName()) {
                if (evt.getClickedInventory().getHolder() instanceof Texture) {
                    if (evt.getRawSlot() != 22 && evt.getRawSlot() != 31) {
                        evt.setCancelled(true);
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§5返回设置界面")) {
                        new Modification(player, texture.getName()).openInventory();
                        return;
                    }
                }
            }
            return;
        }
        if (evt.getInventory().getHolder() instanceof DrawList) {
            DrawList drawList = (DrawList) evt.getInventory().getHolder();
            int page = drawList.getPage();
            evt.setCancelled(true);
            if (evt.getClickedInventory().getHolder() instanceof DrawList) {
                if (evt.getCurrentItem().hasItemMeta() && evt.getCurrentItem().getItemMeta().hasDisplayName()) {
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§2介绍")) {
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a翻到上一页")) {
                        new DrawList(player, --page).openInventory();
                        return;
                    }
                    if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a翻到下一页")) {
                        new DrawList(player, ++page).openInventory();
                        return;
                    }
                    String name = ChatColor.stripColor(evt.getCurrentItem().getItemMeta().getDisplayName());
                    if (evt.getClick() == ClickType.LEFT || evt.getClick() == ClickType.SHIFT_LEFT) {
                        new Show(player, name).openInventory();
                    }
                    if (evt.getClick() == ClickType.RIGHT || evt.getClick() == ClickType.SHIFT_RIGHT) {
                        if (!player.hasPermission("gp.kit.*") && !player.hasPermission("gp.kit." + name)) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoPermission")));
                            new DrawList(player, page).openInventory();
                            return;
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

                        int t = Other.data.getInt("GiftPackInfo." + name + ".Cooling") - Other.data.getInt("PlayerInfo." + player.getName() + "." + name + ".Cooling");
                        t = Math.max(t, 0);

                        if (number <= 0) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNumber")));
                            new DrawList(player, page).openInventory();
                            return;
                        }
                        if (t != Other.data.getInt("GiftPackInfo." + name + ".Cooling")) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("Cooling")));
                            new DrawList(player, page).openInventory();
                            return;
                        }
                        Other.data.set("PlayerInfo." + player.getName() + "." + name + ".Number", Other.data.getInt("PlayerInfo." + player.getName() + "." + name + ".Number") + 1);
                        Other.data.set("PlayerInfo." + player.getName() + "." + name + ".Cooling", Other.data.getInt("GiftPackInfo." + name + ".Cooling"));
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");

                        List<String> itemStackList = new ArrayList<String>(Other.data.getStringList("GiftPackInfo." + name + ".ItemList"));
                        for (String data : itemStackList) {
                            CommonlyWay.giveItem(player, CommonlyWay.getItemStack(data));
                        }
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("SuccessfullyReceived").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                        new DrawList(player, page).openInventory();
                        if (Other.data.getBoolean("GiftPackInfo." + name + ".Enable")) {
                            if (!Other.data.getString("GiftPackInfo." + name + ".Message").equalsIgnoreCase("null")) {
                                Bukkit.broadcastMessage(Other.data.getString("GiftPackInfo." + name + ".Message"));
                            } else {
                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("DefaultMessage").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                            }
                        }
                    }
                }
            }
            return;
        }
        if (evt.getInventory().getHolder() instanceof Show) {
            evt.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent evt) {
        Player player = evt.getPlayer();
        String message = evt.getMessage().replace(" ", "");
        if (message.equals("") || message.equals(".")) {
            player.sendMessage("§c名字不能为空！");
            return;
        }
        if (createList.contains(evt.getPlayer().getName())) {
            evt.setCancelled(true);
            if (Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(message)) {
                player.sendMessage("§c这个名字的礼包已经存在！");
            } else {
                new Create(player, message).openInventory();
                createList.remove(player.getName());
            }
            return;
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt) {
        if (evt.getInventory().getHolder() == null) {
            return;
        }
        Player player = (Player) evt.getPlayer();
        if (evt.getInventory().getHolder() instanceof Create) {
            Create create = (Create) evt.getInventory().getHolder();
            Inventory inventory = create.getInventory();
            String name = create.getTitle();
            List<String> itemList = new ArrayList<String>();
            for (int i = 0; i <= 53; i++) {
                itemList.add(CommonlyWay.getItemData(inventory.getItem(i)));
            }
            Other.data.set("GiftPackInfo." + name + ".Icon", "null");
            Other.data.set("GiftPackInfo." + name + ".OtherIcon", "null");
            Other.data.set("GiftPackInfo." + name + ".Type", "normal");
            Other.data.set("GiftPackInfo." + name + ".Color", "§f");
            Other.data.set("GiftPackInfo." + name + ".Enable", Other.config.getBoolean("EnableMessage"));
            Other.data.set("GiftPackInfo." + name + ".Message", "null");
            Other.data.set("GiftPackInfo." + name + ".Number", Other.config.getInt("DefaultNumber"));
            Other.data.set("GiftPackInfo." + name + ".Cooling", Other.config.getInt("DefaultCooling"));
            Other.data.set("GiftPackInfo." + name + ".Lore", new ArrayList<String>());
            Other.data.set("GiftPackInfo." + name + ".ItemList", itemList);
            CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
            player.sendMessage("§a创建成功");
            new BukkitRunnable() {

                @Override
                public void run() {
                    new Modification(player, name).openInventory();
                }

            }.runTaskLater(Main.plugin, 0);
            return;
        }
        if (evt.getInventory().getHolder() instanceof ModifyContent) {
            ModifyContent modifyContent = (ModifyContent) evt.getInventory().getHolder();
            Inventory inventory = modifyContent.getInventory();
            List<String> itemList = new ArrayList<String>();
            for (int i = 0; i <= 53; i++) {
                itemList.add(CommonlyWay.getItemData(inventory.getItem(i)));
            }
            Other.data.set("GiftPackInfo." + modifyContent.getName() + ".ItemList", itemList);
            CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
            player.sendMessage("§a修改成功");
            new BukkitRunnable() {

                @Override
                public void run() {
                    new Modification(player, modifyContent.getName()).openInventory();
                }

            }.runTaskLater(Main.plugin, 0);
            return;
        }
        if (evt.getInventory().getHolder() instanceof Texture) {
            Texture texture = (Texture) evt.getInventory().getHolder();
            Inventory inventory = texture.getInventory();
            String name = texture.getName();
            if (!(inventory.getItem(22) == null || inventory.getItem(22).getType() == Material.AIR)) {
                Other.data.set("GiftPackInfo." + name + ".Icon", CommonlyWay.getItemData(inventory.getItem(22)));
            } else {
                Other.data.set("GiftPackInfo." + name + ".Icon", "null");
            }
            if (!(inventory.getItem(31) == null || inventory.getItem(31).getType() == Material.AIR)) {
                Other.data.set("GiftPackInfo." + name + ".OtherIcon", CommonlyWay.getItemData(inventory.getItem(31)));
            } else {
                Other.data.set("GiftPackInfo." + name + ".OtherIcon", "null");
            }
            CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
            player.sendMessage("§a设置材质成功");
            return;
        }
        if (evt.getInventory().getHolder() instanceof Show) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    new DrawList(player, 1).openInventory();
                }

            }.runTaskLater(Main.plugin, 0);
            return;
        }
    }
}
