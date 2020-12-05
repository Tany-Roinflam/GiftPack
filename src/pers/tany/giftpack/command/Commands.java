package pers.tany.giftpack.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import pers.tany.giftpack.CommonlyWay;
import pers.tany.giftpack.Main;
import pers.tany.giftpack.Other;
import pers.tany.giftpack.gui.DrawList;
import pers.tany.giftpack.gui.Select;
import pers.tany.giftpack.listenevent.Events;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限执行此命令");
                    return true;
                }
                File file = new File(Main.plugin.getDataFolder(), "config.yml");
                File file1 = new File(Main.plugin.getDataFolder(), "data.yml");
                File file2 = new File(Main.plugin.getDataFolder(), "message.yml");
                File file3 = new File(Main.plugin.getDataFolder(), "redemptionCode.yml");
                Other.config = YamlConfiguration.loadConfiguration(file);
                Other.data = YamlConfiguration.loadConfiguration(file1);
                Other.message = YamlConfiguration.loadConfiguration(file2);
                Other.redemptionCode = YamlConfiguration.loadConfiguration(file3);
                sender.sendMessage("§a重载成功");
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限执行此命令");
                    return true;
                }
                if (Other.redemptionCode.getConfigurationSection("Code").getKeys(false).size() <= 0) {
                    sender.sendMessage("§c当前没有任何兑换码");
                    return true;
                }
                for (String kit : Other.redemptionCode.getConfigurationSection("Code").getKeys(false)) {
                    sender.sendMessage("§a礼包 §e§l" + kit + " §a兑换码");
                    for (String code : Other.redemptionCode.getStringList("Code." + kit)) {
                        sender.sendMessage("§a - §2" + code);
                    }
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("set")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限执行此命令");
                    return true;
                }
                sender.sendMessage("§f/gp enable 礼包  §b开关这个礼包的领取公告");
                sender.sendMessage("§f/gp message 礼包 公告  §b为这个礼包自定义领取公告（'空格'会自动替换成空格）");
                sender.sendMessage("§f/gp number 礼包 数字  §b设置这个礼包最大的领取次数");
                sender.sendMessage("§f/gp cooling 礼包 数字  §b设置这个礼包领取的冷却时间（单位为分钟）");
                sender.sendMessage("§f/gp addlore 礼包 lore  §b为这个礼包添加lore（'空格'会自动替换成空格）");
                sender.sendMessage("§f/gp dellore 礼包  §b删除最后一行lore");
                sender.sendMessage("§f/gp code  §b查看兑换码指令");
                sender.sendMessage("§f/gp type 礼包 [normal/daily/weekly/monthly]  §b修改类型为常驻或每日/周/月");
                return true;
            }
            if (args[0].equalsIgnoreCase("code")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限执行此命令");
                    return true;
                }
                sender.sendMessage("§f/gp list  §b查询所有礼包的兑换码");
                sender.sendMessage("§f/gp create 礼包 数量  §b创建这个礼包的兑换码");
                sender.sendMessage("§f/gp remove 礼包  §b删除这个礼包的兑换码");
                sender.sendMessage("§f/gp convert 兑换码  §b使用这个兑换码兑换礼包");
                sender.sendMessage("§f礼包输入*代表操作所有礼包");
                return true;
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("remove")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限执行此命令");
                    return true;
                }
                if (Other.redemptionCode.getConfigurationSection("Code").getKeys(false).size() <= 0) {
                    sender.sendMessage("§c当前没有任何兑换码");
                    return true;
                }
                if (args[1].equals("*")) {
                    for (String kit : Other.redemptionCode.getConfigurationSection("Code").getKeys(false)) {
                        Other.redemptionCode.set("Code." + kit, null);
                    }
                } else {
                    if (!Other.redemptionCode.getConfigurationSection("Code").getKeys(false).contains(args[1])) {
                        sender.sendMessage("§c这个礼包没有兑换码");
                        return true;
                    }
                    Other.redemptionCode.set("Code." + args[1], null);
                }
                CommonlyWay.saveConfig(Main.plugin, Other.redemptionCode, "redemptionCode");
                sender.sendMessage("§a删除成功");
                return true;
            }
            if (args[0].equalsIgnoreCase("dellore")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限执行此命令");
                    return true;
                }
                if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[1])) {
                    sender.sendMessage("§c没有这个礼包！");
                    return true;
                }
                List<String> lore = new ArrayList<String>(Other.data.getStringList("GiftPackInfo." + args[1] + ".Lore"));
                if (lore.size() <= 0) {
                    sender.sendMessage("§c这个礼包没有lore！");
                    return true;
                }
                lore.remove(lore.size() - 1);
                Other.data.set("GiftPackInfo." + args[1] + ".Lore", lore);
                CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                sender.sendMessage("§a成功删除lore！");
                return true;
            }
            if (args[0].equalsIgnoreCase("enable")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限执行此命令");
                    return true;
                }
                if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[1])) {
                    sender.sendMessage("§c没有这个礼包！");
                    return true;
                }
                if (Other.data.getBoolean("GiftPackInfo." + args[1] + ".Enable")) {
                    sender.sendMessage("§a成功§c取消§f" + Other.data.getString("GiftPackInfo." + args[1] + ".Color") + args[1] + "§a的领取公告");
                    Other.data.set("GiftPackInfo." + args[1] + ".Enable", false);
                } else {
                    sender.sendMessage("§a成功开启§f" + Other.data.getString("GiftPackInfo." + args[1] + ".Color") + args[1] + "§a的领取公告");
                    Other.data.set("GiftPackInfo." + args[1] + ".Enable", true);
                }
                CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                return true;
            }
            if (args[0].equalsIgnoreCase("create")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限使用此指令");
                    return true;
                }
                if (Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).size() <= 0) {
                    sender.sendMessage("§c当前没有任何礼包");
                    return true;
                }
                if (args[1].equals("*")) {
                    for (String kit : Other.data.getConfigurationSection("GiftPackInfo").getKeys(false)) {
                        List<String> codeList = new ArrayList<>(Other.redemptionCode.getStringList("Code." + kit));
                        codeList.add(CommonlyWay.createRandomString(CommonlyWay.randomNumber(Other.config.getInt("MinLenght"), Other.config.getInt("MaxLenght"))));
                        Other.redemptionCode.set("Code." + kit, codeList);
                    }
                } else {
                    if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[1])) {
                        sender.sendMessage("§c没有这个礼包！");
                        return true;
                    }
                    List<String> codeList = new ArrayList<>(Other.redemptionCode.getStringList("Code." + args[1]));
                    codeList.add(CommonlyWay.createRandomString(CommonlyWay.randomNumber(Other.config.getInt("MinLenght"), Other.config.getInt("MaxLenght"))));
                    Other.redemptionCode.set("Code." + args[1], codeList);
                }
                CommonlyWay.saveConfig(Main.plugin, Other.redemptionCode, "redemptionCode");
                sender.sendMessage("§a创建成功！输入/gp list或者在redemptionCode.yml查看兑换码吧！");
                sender.sendMessage("§a发给玩家以让他自己兑换礼包！");
                return true;
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("create")) {
                new Thread(() -> {
                    if (CommonlyWay.opUseCommand(sender)) {
                        sender.sendMessage("§c你没有权限使用此指令");
                        return;
                    }
                    if (Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).size() <= 0) {
                        sender.sendMessage("§c当前没有任何礼包");
                        return;
                    }
                    int number = 0;
                    try {
                        number = Integer.parseInt(args[2]);
                        if (number <= 0) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        sender.sendMessage("§c请输入一个大于0的数字！");
                        return;
                    }
                    if (args[1].equals("*")) {
                        for (String kit : Other.data.getConfigurationSection("GiftPackInfo").getKeys(false)) {
                            List<String> codeList = new ArrayList<>(Other.redemptionCode.getStringList("Code." + kit));
                            int i = 0;
                            do {
                                boolean hasCode = false;
                                String newCode = CommonlyWay.createRandomString(CommonlyWay.randomNumber(Other.config.getInt("MinLenght"), Other.config.getInt("MaxLenght")));
                                for (String k : Other.data.getConfigurationSection("GiftPackInfo").getKeys(false)) {
                                    if (Other.redemptionCode.getStringList("Code." + k).contains(newCode)) {
                                        hasCode = true;
                                        break;
                                    }
                                }
                                if (!hasCode) {
                                    codeList.add(newCode);
                                    i++;
                                }
                            } while (i < number);
                            Other.redemptionCode.set("Code." + kit, codeList);
                        }
                    } else {
                        if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[1])) {
                            sender.sendMessage("§c没有这个礼包！");
                            return;
                        }
                        List<String> codeList = new ArrayList<>(Other.redemptionCode.getStringList("Code." + args[1]));
                        int i = 0;
                        do {
                            boolean hasCode = false;
                            String newCode = CommonlyWay.createRandomString(CommonlyWay.randomNumber(Other.config.getInt("MinLenght"), Other.config.getInt("MaxLenght")));
                            for (String k : Other.data.getConfigurationSection("GiftPackInfo").getKeys(false)) {
                                if (Other.redemptionCode.getStringList("Code." + k).contains(newCode)) {
                                    hasCode = true;
                                    break;
                                }
                            }
                            if (!hasCode) {
                                codeList.add(newCode);
                                i++;
                            }
                        } while (i < number);
                        Other.redemptionCode.set("Code." + args[1], codeList);
                    }
                    CommonlyWay.saveConfig(Main.plugin, Other.redemptionCode, "redemptionCode");
                    sender.sendMessage("§a创建成功！输入/gp list或者在redemptionCode.yml查看兑换码吧！");
                    sender.sendMessage("§a发给玩家以让他自己兑换礼包！");
                }).start();
                return true;
            }
            if (args[0].equalsIgnoreCase("type")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限使用此指令");
                    return true;
                }
                if (!args[2].equalsIgnoreCase("normal") && !args[2].equalsIgnoreCase("daily") && !args[2].equalsIgnoreCase("weekly") && !args[2].equalsIgnoreCase("monthly")) {
                    sender.sendMessage("§c没有这个类型");
                    return true;
                }
                if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[1])) {
                    sender.sendMessage("§c没有这个礼包！");
                    return true;
                }
                Other.data.set("GiftPackInfo." + args[1] + ".Type", args[2]);
                CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                sender.sendMessage("§a修改类型成功！");
                return true;
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限使用此指令");
                    return true;
                }
                if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[1])) {
                    sender.sendMessage("§c没有这个礼包！");
                    return true;
                }
                if (args[2].equals("*")) {
                    String name = args[1];
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (!player.hasPermission("gp.kit.*") && !player.hasPermission("gp.kit." + name)) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoPermission")));
                            continue;
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
                            continue;
                        }
                        if (t != Other.data.getInt("GiftPackInfo." + name + ".Cooling")) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("Cooling")));
                            continue;
                        }
                        Other.data.set("PlayerInfo." + player.getName() + "." + name + ".Number", Other.data.getInt("PlayerInfo." + player.getName() + "." + name + ".Number") + 1);
                        Other.data.set("PlayerInfo." + player.getName() + "." + name + ".Cooling", Other.data.getInt("GiftPackInfo." + name + ".Cooling"));
                        CommonlyWay.saveConfig(Main.plugin, Other.data, "data");

                        List<String> itemStackList = new ArrayList<String>(Other.data.getStringList("GiftPackInfo." + name + ".ItemList"));
                        for (String data : itemStackList) {
                            CommonlyWay.giveItem(player, CommonlyWay.getItemStack(data));
                        }
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("SuccessfullyReceived").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                        if (Other.data.getBoolean("GiftPackInfo." + name + ".Enable")) {
                            if (!Other.data.getString("GiftPackInfo." + name + ".Message").equalsIgnoreCase("null")) {
                                Bukkit.broadcastMessage(Other.data.getString("GiftPackInfo." + name + ".Message"));
                            } else {
                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("DefaultMessage").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                            }
                        }
                    }
                } else {
                    if (Bukkit.getPlayerExact(args[2]) == null) {
                        sender.sendMessage("§c这个玩家未在线");
                        return true;
                    }
                    String name = args[1];
                    Player player = Bukkit.getPlayerExact(args[2]);
                    if (!player.hasPermission("gp.kit.*") && !player.hasPermission("gp.kit." + name)) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoPermission")));
                        return true;
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
                        return true;
                    }
                    if (t != Other.data.getInt("GiftPackInfo." + name + ".Cooling")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("Cooling")));
                        return true;
                    }
                    Other.data.set("PlayerInfo." + player.getName() + "." + name + ".Number", Other.data.getInt("PlayerInfo." + player.getName() + "." + name + ".Number") + 1);
                    Other.data.set("PlayerInfo." + player.getName() + "." + name + ".Cooling", Other.data.getInt("GiftPackInfo." + name + ".Cooling"));
                    CommonlyWay.saveConfig(Main.plugin, Other.data, "data");

                    List<String> itemStackList = new ArrayList<String>(Other.data.getStringList("GiftPackInfo." + name + ".ItemList"));
                    for (String data : itemStackList) {
                        CommonlyWay.giveItem(player, CommonlyWay.getItemStack(data));
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("SuccessfullyReceived").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                    if (Other.data.getBoolean("GiftPackInfo." + name + ".Enable")) {
                        if (!Other.data.getString("GiftPackInfo." + name + ".Message").equalsIgnoreCase("null")) {
                            Bukkit.broadcastMessage(Other.data.getString("GiftPackInfo." + name + ".Message"));
                        } else {
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("DefaultMessage").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                        }
                    }
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("admingive")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限使用此指令");
                    return true;
                }
                if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[1])) {
                    sender.sendMessage("§c没有这个礼包！");
                    return true;
                }
                if (args[2].equals("*")) {
                    String name = args[1];
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        List<String> itemStackList = new ArrayList<String>(Other.data.getStringList("GiftPackInfo." + name + ".ItemList"));
                        for (String data : itemStackList) {
                            CommonlyWay.giveItem(player, CommonlyWay.getItemStack(data));
                        }
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("SuccessfullyReceived").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                        if (Other.data.getBoolean("GiftPackInfo." + name + ".Enable")) {
                            if (!Other.data.getString("GiftPackInfo." + name + ".Message").equalsIgnoreCase("null")) {
                                Bukkit.broadcastMessage(Other.data.getString("GiftPackInfo." + name + ".Message"));
                            } else {
                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("DefaultMessage").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                            }
                        }
                    }
                } else {
                    if (Bukkit.getPlayerExact(args[2]) == null) {
                        sender.sendMessage("§c这个玩家未在线");
                        return true;
                    }
                    String name = args[1];
                    Player player = Bukkit.getPlayerExact(args[2]);
                    List<String> itemStackList = new ArrayList<String>(Other.data.getStringList("GiftPackInfo." + name + ".ItemList"));
                    for (String data : itemStackList) {
                        CommonlyWay.giveItem(player, CommonlyWay.getItemStack(data));
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("SuccessfullyReceived").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                    if (Other.data.getBoolean("GiftPackInfo." + name + ".Enable")) {
                        if (!Other.data.getString("GiftPackInfo." + name + ".Message").equalsIgnoreCase("null")) {
                            Bukkit.broadcastMessage(Other.data.getString("GiftPackInfo." + name + ".Message"));
                        } else {
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("DefaultMessage").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                        }
                    }
                }

                return true;
            }
            if (args[0].equalsIgnoreCase("number")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限执行此命令");
                    return true;
                }
                if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[1])) {
                    sender.sendMessage("§c没有这个礼包！");
                    return true;
                }
                int number = 0;
                try {
                    number = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§c请输入数字！");
                    return true;
                }
                if (number < 0) {
                    sender.sendMessage("§c请输入个大于负数的整数");
                    return true;
                }
                Other.data.set("GiftPackInfo." + args[1] + ".Number", number);
                CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                sender.sendMessage("§a设置领取次数为§6" + number + "§a次成功");
                return true;
            }
            if (args[0].equalsIgnoreCase("cooling")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限执行此命令");
                    return true;
                }
                if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[1])) {
                    sender.sendMessage("§c没有这个礼包！");
                    return true;
                }
                int number = 0;
                try {
                    number = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§c请输入数字！");
                    return true;
                }
                if (number < 0) {
                    sender.sendMessage("§c请输入个大于负数的整数");
                    return true;
                }
                Other.data.set("GiftPackInfo." + args[1] + ".Cooling", number);
                CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                String time = CommonlyWay.getDay(number);
                sender.sendMessage("§a设置领取CD为§6" + time.split(":")[0] + "日" + time.split(":")[1] + "时" + time.split(":")[2] + "分§a成功");
                return true;
            }
            if (args[0].equalsIgnoreCase("addlore")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限执行此命令");
                    return true;
                }
                if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[1])) {
                    sender.sendMessage("§c没有这个礼包！");
                    return true;
                }
                List<String> lore = new ArrayList<String>(Other.data.getStringList("GiftPackInfo." + args[1] + ".Lore"));
                lore.add(ChatColor.translateAlternateColorCodes('&', args[2].replace("空格", " ")));
                Other.data.set("GiftPackInfo." + args[1] + ".Lore", lore);
                CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                sender.sendMessage("§a成功添加lore： §6" + ChatColor.translateAlternateColorCodes('&', args[2].replace("空格", " ")));
                return true;
            }
            if (args[0].equalsIgnoreCase("message")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限执行此命令");
                    return true;
                }
                if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[1])) {
                    sender.sendMessage("§c没有这个礼包！");
                    return true;
                }
                Other.data.set("GiftPackInfo." + args[1] + ".Message", ChatColor.translateAlternateColorCodes('&', args[2].replace("空格", " ")));
                CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                sender.sendMessage("§a成功设置领取公告： §6" + ChatColor.translateAlternateColorCodes('&', args[2].replace("空格", " ")));
                return true;
            }
            if (args[0].equalsIgnoreCase("clearnumber")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限执行此命令");
                    return true;
                }
                if (Other.data.getConfigurationSection("PlayerInfo").getKeys(false).size() <= 0) {
                    sender.sendMessage("§c当前没有任何玩家有礼包数据！");
                    return true;
                }
                if (args[1].equals("*")) {
                    if (args[2].equals("*")) {
                        for (String name : Other.data.getConfigurationSection("PlayerInfo").getKeys(false)) {
                            for (String kit : Other.data.getConfigurationSection("PlayerInfo." + name).getKeys(false)) {
                                Other.data.set("PlayerInfo." + name + "." + kit + ".Number", 0);
                            }
                        }
                    } else {
                        if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[2])) {
                            sender.sendMessage("§c没有这个礼包！");
                            return true;
                        }
                        for (String name : Other.data.getConfigurationSection("PlayerInfo").getKeys(false)) {
                            Other.data.set("PlayerInfo." + name + "." + args[2] + ".Number", 0);
                        }
                    }
                } else {
                    if (Other.data.getConfigurationSection("PlayerInfo." + args[1]).getKeys(false) == null) {
                        sender.sendMessage("§c没有这个玩家的记录！");
                        return true;
                    }
                    if (args[2].equals("*")) {
                        for (String kit : Other.data.getConfigurationSection("PlayerInfo." + args[1]).getKeys(false)) {
                            Other.data.set("PlayerInfo." + args[1] + "." + kit + ".Number", 0);
                        }
                    } else {
                        if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[2])) {
                            sender.sendMessage("§c没有这个礼包！");
                            return true;
                        }
                        Other.data.set("PlayerInfo." + args[1] + "." + args[2] + ".Number", 0);
                    }
                }
                CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                sender.sendMessage("§a重置成功！");
                return true;
            }
            if (args[0].equalsIgnoreCase("clearcooling")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    sender.sendMessage("§c你没有权限执行此命令");
                    return true;
                }
                if (Other.data.getConfigurationSection("PlayerInfo").getKeys(false).size() <= 0) {
                    sender.sendMessage("§c当前没有任何玩家有礼包数据！");
                    return true;
                }
                if (args[1].equals("*")) {
                    if (args[2].equals("*")) {
                        for (String name : Other.data.getConfigurationSection("PlayerInfo").getKeys(false)) {
                            for (String kit : Other.data.getConfigurationSection("PlayerInfo." + name).getKeys(false)) {
                                Other.data.set("PlayerInfo." + name + "." + kit + ".Cooling", 0);
                            }
                        }
                    } else {
                        if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[2])) {
                            sender.sendMessage("§c没有这个礼包！");
                            return true;
                        }
                        for (String name : Other.data.getConfigurationSection("PlayerInfo").getKeys(false)) {
                            Other.data.set("PlayerInfo." + name + "." + args[2] + ".Cooling", 0);
                        }
                    }
                } else {
                    if (Other.data.getConfigurationSection("PlayerInfo." + args[1]) == null) {
                        sender.sendMessage("§c没有这个玩家的记录！");
                        return true;
                    }
                    if (args[2].equals("*")) {
                        for (String kit : Other.data.getConfigurationSection("PlayerInfo." + args[1]).getKeys(false)) {
                            Other.data.set("PlayerInfo." + args[1] + "." + kit + ".Cooling", 0);
                        }
                    } else {
                        if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[2])) {
                            sender.sendMessage("§c没有这个礼包！");
                            return true;
                        }
                        Other.data.set("PlayerInfo." + args[1] + "." + args[2] + ".Cooling", 0);
                    }
                }
                CommonlyWay.saveConfig(Main.plugin, Other.data, "data");
                sender.sendMessage("§a重置成功！");
                return true;
            }
        }
        if (CommonlyWay.consoleUse(sender)) {
            sender.sendMessage("§f/gp give [礼包] [玩家]  §b领取礼包（计入次数CD）");
            sender.sendMessage("§f/gp admingive [礼包] [玩家]  §b领取礼包（管理员强制给予，不计入次数CD）");
            sender.sendMessage("§f/gp clearnumber 玩家 礼包  §b重置玩家的礼包的使用次数（填*代表所有）");
            sender.sendMessage("§f/gp clearcooling 玩家 礼包  §b重置玩家的礼包的冷却时间（填*代表所有）");
            sender.sendMessage("§f/gp set  §b查看编辑抽奖箱指令");
            sender.sendMessage("§f/gp code  §b查看兑换码指令");
            sender.sendMessage("§f/gp reload  §b重载");
            return true;
        }
        Player player = (Player) sender;
        if (Events.createList.contains(player.getName())) {
            player.sendMessage("§c请先完成当前会话！");
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("draw")) {
                if (!player.hasPermission("gp.draw")) {
                    player.sendMessage("§c你没有权限使用此指令");
                    return true;
                }
                new DrawList(player, 1).openInventory();
                return true;
            }
            if (args[0].equalsIgnoreCase("setting")) {
                if (CommonlyWay.opUseCommand(sender)) {
                    player.sendMessage("§c你没有权限使用此指令");
                    return true;
                }
                new Select(player).openInventory();
                return true;
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("convert")) {
                if (!player.hasPermission("gp.convert")) {
                    player.sendMessage("§c你没有权限使用此指令");
                    return true;
                }
                String code = args[1];
                for (String kit : Other.data.getConfigurationSection("GiftPackInfo").getKeys(false)) {
                    if (Other.redemptionCode.getStringList("Code." + kit).contains(code)) {
                        if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(kit)) {
                            player.sendMessage("§c这个礼包不存在/已删除");
                            return true;
                        }
                        List<String> codeList = new ArrayList<>(Other.redemptionCode.getStringList("Code." + kit));
                        codeList.remove(code);
                        Other.redemptionCode.set("Code." + kit, codeList);
                        CommonlyWay.saveConfig(Main.plugin, Other.redemptionCode, "redemptionCode");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ConvertKit").replace("[name]", kit)));
                        List<String> itemStackList = new ArrayList<String>(Other.data.getStringList("GiftPackInfo." + kit + ".ItemList"));
                        for (String data : itemStackList) {
                            CommonlyWay.giveItem(player, CommonlyWay.getItemStack(data));
                        }
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("SuccessfullyReceived").replace("[name]", Other.data.getString("GiftPackInfo." + kit + ".Color") + kit)));
                        if (Other.data.getBoolean("GiftPackInfo." + kit + ".Enable")) {
                            if (!Other.data.getString("GiftPackInfo." + kit + ".Message").equalsIgnoreCase("null")) {
                                Bukkit.broadcastMessage(Other.data.getString("GiftPackInfo." + kit + ".Message"));
                            } else {
                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("DefaultMessage").replace("[name]", Other.data.getString("GiftPackInfo." + kit + ".Color") + kit)));
                            }
                        }
                        return true;
                    }
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoConvertKit")));
                return true;
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (!player.hasPermission("gp.give")) {
                    player.sendMessage("§c你没有权限使用此指令");
                    return true;
                }
                if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[1])) {
                    player.sendMessage("§c没有这个礼包！");
                    return true;
                }
                String name = args[1];
                if (!player.hasPermission("gp.kit.*") && !player.hasPermission("gp.kit." + name)) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoPermission")));
                    return true;
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
                    return true;
                }
                if (t != Other.data.getInt("GiftPackInfo." + name + ".Cooling")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("Cooling")));
                    return true;
                }
                Other.data.set("PlayerInfo." + player.getName() + "." + name + ".Number", Other.data.getInt("PlayerInfo." + player.getName() + "." + name + ".Number") + 1);
                Other.data.set("PlayerInfo." + player.getName() + "." + name + ".Cooling", Other.data.getInt("GiftPackInfo." + name + ".Cooling"));
                CommonlyWay.saveConfig(Main.plugin, Other.data, "data");

                List<String> itemStackList = new ArrayList<String>(Other.data.getStringList("GiftPackInfo." + name + ".ItemList"));
                for (String data : itemStackList) {
                    CommonlyWay.giveItem(player, CommonlyWay.getItemStack(data));
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("SuccessfullyReceived").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                if (Other.data.getBoolean("GiftPackInfo." + name + ".Enable")) {
                    if (!Other.data.getString("GiftPackInfo." + name + ".Message").equalsIgnoreCase("null")) {
                        Bukkit.broadcastMessage(Other.data.getString("GiftPackInfo." + name + ".Message"));
                    } else {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("DefaultMessage").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                    }
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("admingive")) {
                if (CommonlyWay.opUseCommand(player)) {
                    player.sendMessage("§c你没有权限使用此指令");
                    return true;
                }
                if (!Other.data.getConfigurationSection("GiftPackInfo").getKeys(false).contains(args[1])) {
                    player.sendMessage("§c没有这个礼包！");
                    return true;
                }
                String name = args[1];
                List<String> itemStackList = new ArrayList<String>(Other.data.getStringList("GiftPackInfo." + name + ".ItemList"));
                for (String data : itemStackList) {
                    CommonlyWay.giveItem(player, CommonlyWay.getItemStack(data));
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("SuccessfullyReceived").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                if (Other.data.getBoolean("GiftPackInfo." + name + ".Enable")) {
                    if (!Other.data.getString("GiftPackInfo." + name + ".Message").equalsIgnoreCase("null")) {
                        Bukkit.broadcastMessage(Other.data.getString("GiftPackInfo." + name + ".Message"));
                    } else {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("DefaultMessage").replace("[name]", Other.data.getString("GiftPackInfo." + name + ".Color") + name)));
                    }
                }
                return true;
            }
        }
        if (player.isOp()) {
            player.sendMessage("§f/gp draw  §b打开显示/领取礼包界面");
            player.sendMessage("§f/gp setting  §b打开编辑礼包界面");
            player.sendMessage("§f/gp give [礼包] [玩家]  §b领取礼包（计入次数CD）");
            player.sendMessage("§f/gp admingive [礼包] [玩家]  §b领取礼包（管理员强制给予，不计入次数CD）");
            player.sendMessage("§f/gp clearnumber 玩家 礼包  §b重置玩家的礼包的使用次数（填*代表所有）");
            player.sendMessage("§f/gp clearcooling 玩家 礼包  §b重置玩家的礼包的冷却时间（填*代表所有）");
            player.sendMessage("§f/gp set  §b查看编辑抽奖箱指令");
            player.sendMessage("§f/gp code  §b查看兑换码指令");
            player.sendMessage("§f/gp reload  §b重载");
        } else {
            player.sendMessage("§f/gp draw  §b打开显示/领取礼包界面");
            player.sendMessage("§f/gp give 礼包  §b直接领取对应礼包");
            player.sendMessage("§f/gp convert 兑换码  §b使用这个兑换码兑换礼包");
        }
        return true;
    }
}
