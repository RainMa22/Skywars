package me.rainma22.Skywars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class EnchantMan implements Listener {
    private Main plugin;
    private ModeMan mode;
    private ArrayList<config> configs=new ArrayList<>(0);
    public TimerThread tt;
    public EnchantMan(Main plu,ModeMan mod){
        plugin=plu;
        for (ModeMan.mode mo:mode.getMode()){
            String configname="modes/"+mo.getName()+"/enchants.yml";
            if (plugin.getCm().getConfig(configname)==null)plugin.getCm().addConfig(configname);
        }
        mode=mod;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        tt=new TimerThread(plugin);
    }
    public void addEnchantment(ItemStack item,String modeName,Enchantment e,int level,int chance){
        ArrayList<ModeMan.mode> modes=mode.getMode();
        for (ModeMan.mode mode1:modes){
            if (mode1.getName()==modeName){
                String configname="modes/"+mode1.getName()+"/enchants.yml";
                config c=plugin.getCm().getConfig(configname);
                c.save(item.getType().toString(),c.load(item.getType().toString())+","+e.getName()+" "+level);
                c.save(item.getType().toString(),c.load(item.getType().toString()+","+chance));
            }
        }
    }
    @EventHandler
    public void onChestOpen(PlayerInteractEvent e){
        ModeMan.mode m= mode.getMode(e.getClickedBlock().getWorld());
        if(m!=null){
            config conf=plugin.getCm().getConfig("modes/"+m.getName()+"/enchants.yml");
              if (e.getClickedBlock().getType()== Material.CHEST&&!plugin.getWm().chestOpened.contains(e.getClickedBlock().getLocation())){
                  Chest c= (Chest) e.getClickedBlock().getState();
                  ItemStack[] iss= m.getDrop();
                  for (ItemStack is:iss){
                      String s=conf.load(is.getType().toString());
                      String s1=conf.load(is.getType().toString()+".chance");
                      String[] sa=s.toUpperCase().split(",");
                      String[] ca=s.split(",");
                      if (s!=null&&s1!=null&&sa.length==ca.length){
                          int rand=(int)(Math.random()*100+1);
                          for (int i = 0; i < sa.length; i++) {
                              String[] ssss=sa[i].split(" ");
                              short level=Short.parseShort(ssss[1]);
                              if (rand<=Byte.parseByte(ca[i])){
                                  is.addUnsafeEnchantment(Enchantment.getByName(sa[i]),level);
                              }
                          }
                      }
                      c.getBlockInventory().addItem(is);
                  }
                  c.update();
                  plugin.getWm().chestOpened.add(c.getLocation());
              }
        }
    }
}
