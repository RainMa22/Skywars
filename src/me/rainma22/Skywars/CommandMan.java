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
                case 2:
                case 1:
                case 0:
                    sender.sendMessage("Usage: /addSkywarsMap <mode> <mapname> <ZipFile>\nGenerate a world from a ZipFile as an Template");
                    break;
            }
        }
        return false;
    }
}
