package me.rainma22.Skywars;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnchantMan implements Listener {
    private Main plugin;
    private ModeMan mode;
    private config c;
    public EnchantMan(Main plu,ModeMan mod,config cq){
        c=cq;
        plugin=plu;
        mode=mod;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler
    public void onChestOpen(PlayerInteractEvent e){
        ModeMan.mode m= mode.getMode(e.getClickedBlock().getWorld());
        if(m!=null){

        }
    }
}
