package me.rainma22.Skywars;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class configMan {
    private Main plugin;
    ArrayList<config> configs=new ArrayList<>(0);
    public configMan(Main p){
        plugin=p;
    }
    public void addConfig(String s){
        configs.add(new config(new File(plugin.getDataFolder(),s)));
    }
    public config getConfig(String s){
        for(config c:configs){
            if(c.file.getName().equalsIgnoreCase(s)){
                return c;
            }
        }
        return null;
    }

}
class config{
    public YamlConfiguration yaml;
    public File file;
    public config(File f){
        try {
        file=f;
        file.getParentFile().mkdirs();
        file.createNewFile();
        yaml= new YamlConfiguration();
            yaml.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String load(String p){
        return yaml.getString(p);
    }
    public void save(String p,String c){
        try {
        yaml.set(p,c);
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
