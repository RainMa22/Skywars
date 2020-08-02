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
        }else if (command.getName().equalsIgnoreCase("")){

        }
        return false;
    }
}
