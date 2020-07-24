package me.rainma22.Skywars;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ModeMan {
    private Main plugin;
    private ArrayList<mode> modes= new ArrayList<>(0);
    private ArrayList<map> maps=new ArrayList<>(0);
    public ModeMan(Main p){
        plugin=p;
        try {
            getModes();
            loadMaps();
        } catch (Exception e) {
            plugin.getLogger().severe(e.getMessage()+"\n"+e.getCause());
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
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
    public void loadMaps(){
        for(mode m:modes){
            for(String s:m.getMaps()){
                maps.add(new map(s,m));
            }
        }
    }
    public mode getMode(World w){
        String name=w.getName();
        for (map m:maps){
            if (m.getName()==name){
             return m.getMode();
            }
        }
        return null;
    }
class map{
    private String name;
    private mode mode;

    public String getName() {
        return name;
    }

    public mode getMode(){return mode;}
    public map(String s,mode m){
        name=s;
        mode=m;
    }
}
class mode{
    private String name,configName;
    private ArrayList<ItemStack> items=new ArrayList<>(0);
    private ArrayList<Integer> percentDrop=new ArrayList<>(0);
    private ArrayList<Integer> min=new ArrayList<>(0);
    private ArrayList<Integer> max=new ArrayList<>(0);
    private ArrayList<String> maps=new ArrayList(0);
    private Main plugin;
    private config c;

    public ArrayList<String> getMaps() {
        return maps;
    }

    public mode(Main p, String s) throws Exception {
        name=s;
        plugin=p;
        configName="modes/"+name+".yml";
        if(plugin.getCm().getConfig(configName)==null) plugin.getCm().addConfig(configName);
        c=plugin.getCm().getConfig(configName);
        if(c.load("items")!=null){
            String[] ss=c.load("items").toUpperCase().split(",");
            String[] s2=new String[0];
            String[] minMax=new String[0];
            if(c.load("chancedrops")!=null) s2=c.load("chancedrops").toUpperCase().split(",");
            if (c.load("amount")!=null) minMax=c.load("amounts").split(",");
            if((ss.length+s2.length+minMax.length)/3!=ss.length) throw new Exception("please have same amount of items and chancedrops!("+plugin.getDataFolder().toString()+","+configName+")");
            for(String s1:ss){
                items.add(new ItemStack(Material.getMaterial(s1)));
            }
            for(String s1:s2){
                percentDrop.add(Integer.parseInt(s1));
            }
            for (String s1:minMax) {
                min.add(Integer.parseInt(s1.split("-")[0]));
                max.add(Integer.parseInt(s1.split("-")[1]));
            }
        }
        loadmaps();
    }
    public String getName(){
        return name;
    }
    public void additem(ItemStack item,int chance,int min,int max){
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i)==item){
                percentDrop.set(i, chance);
                this.min.set(i,min);
                this.max.set(i,max);
            }else{
                items.add(item);
                percentDrop.add(chance);
                this.min.add(min);
                this.max.add(max);
            }
        }
        c.save("items","");
        c.save("chancedrops","");
        for (int i = 0; i < items.size(); i++) {
            if(i==0){
                c.save("items",items.get(i).getType().name());
                c.save("chancedrops",Integer.toString(percentDrop.get(i)));
                c.save("amount",(this.min.get(i)+"-"+this.max.get(i)));
            }else{
                c.save("items",c.load("items")+","+items.get(i).getType().name());
                c.save("chancedrops",c.load("chancedrops")+","+ percentDrop.get(i));
                c.save("amount",(this.min.get(i)+"-"+this.max.get(i)));
            }
        }
    }
    public void loadmaps(){
        String[] s=c.load("maps").split(",");
        for (String s1:s){
            maps.add(s1);
        }
    }
    public ItemStack[] getDrop(){
        ArrayList<ItemStack> Itemfiller=new ArrayList<>(0);
        int rand=(int)(Math.random()*100+1);
        for (int i = 0; i < items.size(); i++) {
            if (rand<=percentDrop.get(i)){
                Itemfiller.add(items.get(i));
            }
        }
        return (ItemStack[]) Itemfiller.toArray();
    }
}
}