package me.rainma22.Skywars;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class EnchantMan implements Listener {
    private Main plugin;
    private ModeMan mode;
    private config c;
    private ArrayList<Location> openedChests=new ArrayList<>(0);
    private ArrayList<Enchantment> enchantments;
    private ArrayList<Integer> chance;
    public EnchantMan(Main plu,ModeMan mod){
        plugin=plu;
        mode=mod;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler
    public void onChestOpen(PlayerInteractEvent e){
        ModeMan.mode m= mode.getMode(e.getClickedBlock().getWorld());
        if(m!=null){
              if (e.getClickedBlock().getType()== Material.CHEST&&!openedChests.contains(e.getClickedBlock().getLocation())){
                  Chest c= (Chest) e.getClickedBlock().getState();

              }
        }
    }
}
