package me.rainma22.Skywars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
            }
        }
        return false;
    }
}
