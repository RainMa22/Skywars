package me.rainma22.Skywars;


import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipInputStream;

public class Worldman {
    private Main plugin;
    private ArrayList<World> worlds=new ArrayList(0);
    private ArrayList<File> templates=new ArrayList<>(0)
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
    public void saveWorldFromTemplate(String source,String Modename,String name){

        for ( ModeMan.mode mode:plugin.getMm().getMode() ) {
            String configName="modes/"+mode.getName()+".yml";
            config c=plugin.getCm().getConfig(configName);
            ArrayList<String> map=new ArrayList<>(0);
            for (String s2:c.load("maps").split(",")){
                if (name!=s2){
                    map.add(s2);
                }
            }
            if (mode.getName()==Modename){

                c.save("maps",c.load("maps")+","+name);
                mode.loadmaps();
            }
        }
        String configName="worlds.yml";
        new Thread(new Runnable() {
            @Override
            public void run() {
                File f=new File(plugin.getDataFolder(),"templates");
                f.mkdirs();
                if (!source.toLowerCase().endsWith(".zip"))source.concat(".zip");
                f=new File(f,source);
                try {
                    ZipInputStream zipIn= new ZipInputStream(new FileInputStream(f));
                    File f2=new File(Bukkit.getWorldContainer(),"name");
                    f2.mkdirs();
                    OutputStreamWriter out= new OutputStreamWriter(new FileOutputStream(f2));
                    int i=-1;
                    while ((i=zipIn.read())!=-1){
                        out.write(i);
                    }
                } catch (IOException e) {
                }
            }
        }).start();
    }
}
