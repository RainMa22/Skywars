package me.rainma22.Skywars;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ModeMan {
    private Main plugin;
    private ArrayList<mode> modes= new ArrayList<>(0);
    private ArrayList maps=new ArrayList(0);
    public ModeMan(Main p){
        plugin=p;
    }
    public void getModes(){
        if(plugin.getCm().getConfig("modes.yml")==null) plugin.getCm().addConfig("modes.yml");
        config c=plugin.getCm().getConfig("modes.yml");
        if(c.load("modes")!=null) {
            String[] s = c.load("modes").split(",");
            for (String ss:s) {
                modes.add(new mode(ss,c));
            }
        }
    }
}
class mode{
    String name;
    ArrayList<ItemStack> items=new ArrayList<>(0);
    ArrayList<Integer> percentDrop=new ArrayList<>(0);
    public mode(String s,config c){
        name=s;
    }
}