package me.rainma22.Skywars;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class configMan {
    private Main plugin;
    public configMan(Main p){
        plugin=p;
    }
}
class config{
    public YamlConfiguration yaml;
    public File file;
    public config(File f){
        file=f;
        yaml= new YamlConfiguration();
        try {
            yaml.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
