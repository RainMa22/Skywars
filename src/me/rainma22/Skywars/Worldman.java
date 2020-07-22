package me.rainma22.Skywars;


import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class Worldman {
    private Main plugin;
    public Worldman(Main plugin){
        this.plugin=plugin;
    }
    public void genNewWorld(String name){
        new Thread(new Runnable() {
            @Override
            public void run() {
                WorldCreator wc= new WorldCreator(name);
                wc.generateStructures(false);
                wc.type(WorldType.CUSTOMIZED);
                wc.generatorSettings("0:1");
                wc.createWorld();
            }
        }).start();

    }
}
