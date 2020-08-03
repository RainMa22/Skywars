package me.rainma22.Skywars;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class CommandMan implements CommandExecutor {
    private Main plugin;
    public CommandMan(Main plugin){
        this.plugin=plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("addSkywarsMap")){
            switch (args.length){
                case 3:
                    plugin.getWm().saveWorldFromTemplate(args[2],args[1],args[0]);
                    break;
                default:
                    sender.sendMessage("Usage: /addSkywarsMap <mapname> <mode> <ZipFile>\nGenerate a world from a ZipFile as an Template");
                    break;
            }
            return true;
        }else if (command.getName().equalsIgnoreCase("start")){
            switch (args.length) {
                case 1:
                    String worldname=args[0];
                    for (int i = 0; i < plugin.getWm().worlds.size(); i++) {
                        if (plugin.getWm().worlds.get(i).getName()==worldname){
                            plugin.getWm().isRunning.set(i,true);
                        }
                    }
                    break;
                default:
                    sender.sendMessage("Usage: /start <worldname>");
                    break;
            }
            return true;
        }else if (command.getName().equalsIgnoreCase("addSkywarsEnchantment")){
            switch (args.length){
                case 5:
                    try {
                        plugin.getEm().addEnchantment(new ItemStack(Material.getMaterial(args[0])), args[1],
                                Enchantment.getByName(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                    }catch (Exception e){
                        sender.sendMessage(ChatColor.DARK_RED+"Error!\n"+e.toString());
                    }
                    break;
                default:
                    sender.sendMessage("Usage: /addSkywarsEnchantment <itemname> <modename> <Enchantment Name> "+
                            "<Enchantment level> <chance>");
                    break;
            }
            return true;
        }else if (command.getName().equalsIgnoreCase("addMap")){
            switch (args.length){
                case 2:
                    if(!plugin.getMm().addmap(args[0],args[1])){
                        sender.sendMessage(ChatColor.DARK_RED+"Error!\n"+
                                "no such mode called "+args[0]+"!");
                    }else{
                        sender.sendMessage(ChatColor.GREEN+"Sucess!");
                    }
                    break;
                default:
                    sender.sendMessage("Usage: /addMap <Modename> <worldname>\n"+
                            ChatColor.DARK_RED+"Warning: Not case sensitive");
                    break;
            }
            return true;
        }else if (command.getName().equalsIgnoreCase("addMode")){
            switch (args.length){
                case 1:
                    try {
                        if (plugin.getMm().addMode(args[0])){
                            sender.sendMessage("Sucess!");
                        }else{
                            sender.sendMessage(ChatColor.DARK_RED+"Error!\n"+
                                    "Mode "+args[0]+" already exists!");
                        }
                    } catch (Exception e) {
                    }
            }
            return true;
        }
        return false;
    }
}
