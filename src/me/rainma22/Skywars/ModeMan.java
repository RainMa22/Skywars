package me.rainma22.Skywars;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ModeMan {
    private Main plugin;
    private ArrayList<mode> modes= new ArrayList<>(0);
    private ArrayList maps=new ArrayList(0);
    public ModeMan(Main p){
        plugin=p;
    }
    public void getModes() throws Exception {
        if(plugin.getCm().getConfig("modes.yml")==null) plugin.getCm().addConfig("modes.yml");
        config c=plugin.getCm().getConfig("modes.yml");
        if(c.load("modes")!=null) {
            String[] s = c.load("modes").split(",");
            for (String ss:s) {
                modes.add(new mode(plugin,ss));
            }
        }
    }
}
class mode{
    private String name,configName;
    private ArrayList<ItemStack> items=new ArrayList<>(0);
    private ArrayList<Integer> percentDrop=new ArrayList<>(0);
    private Main plugin;
    private config c;
    public mode(Main p,String s) throws Exception {
        name=s;
        plugin=p;
        configName="modes/"+name+".yml";
        if(plugin.getCm().getConfig(configName)==null) plugin.getCm().addConfig(configName);
        c=plugin.getCm().getConfig(configName);
        if(c.load("items")!=null){
            String[] ss=c.load("items").toUpperCase().split(",");
            String[] s2=new String[0];
            if(c.load("chancedrops")!=null) s2=c.load("chancedrops").toUpperCase().split(",");
            if(ss.length!=s2.length) throw new Exception("please have same amount of items and chancedrops!("+plugin.getDataFolder().toString()+","+configName+")");
            for(String s1:ss){
                items.add(new ItemStack(Material.getMaterial(s1)));
            }
            for(String s1:s2){
                percentDrop.add(Integer.parseInt(s1));
            }
        }
    }
    public String getName(){
        return name;
    }
    public void additem(ItemStack item,int chance){
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i)==item){
                percentDrop.set(i, chance);
            }else{
                items.add(item);
                percentDrop.add(chance);
            }
        }
        c.save("items","");
        c.save("chancedrops","");
        for (int i = 0; i < items.size(); i++) {
            if(i==0){
                c.save("items",items.get(i).getType().name());
                c.save("chancedrops",Integer.toString(percentDrop.get(i)));
            }
        }
    }

}