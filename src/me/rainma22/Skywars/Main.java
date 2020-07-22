package me.rainma22.Skywars;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
    private configMan cm= new configMan(this);
    public configMan getCm(){
        return cm;
    }
    @Override
    public void onEnable() {


    }

    @Override
    public void onDisable() {
    }
}
