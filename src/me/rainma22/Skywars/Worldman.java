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
    private ArrayList<File> templates=new ArrayList<>(0);
    public Worldman(Main plugin){
        this.plugin=plugin;
        loadWorld();
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                File f=new File(plugin.getDataFolder(),"templates");
                f.mkdirs();
                if (!source.toLowerCase().endsWith(".zip"))source.concat(".zip");
                f=new File(f,source);
                if (!f.exists())f=new File(source);
                try {
                    ZipInputStream zipIn= new ZipInputStream(new FileInputStream(f));
                    File f2=new File(Bukkit.getWorldContainer(),name);
                    f2.mkdirs();
                    OutputStreamWriter out= new OutputStreamWriter(new FileOutputStream(f2));
                    int i=-1;
                    while ((i=zipIn.read())!=-1){
                        out.write(i);
                    }
                    saveWorld(source,name);
                } catch (IOException e) {
                }
            }
        }).start();
    }
    public void saveWorld(String source,String name){
        String configName="worlds.yml";
        config c=plugin.getCm().getConfig(configName);
        ArrayList<String> strings=new ArrayList<>(0);
        for (String s:c.load("worlds").split(",")){
            String[] s1=s.split(",");
            if (s1[0]==name){
                s1[1]=source;
                strings.add(s1[0]+" "+s1[1]);
            }else{
                strings.add(s);
            }
        }
        c.save("worlds",strings.get(0));
        for (int i = 1; i < strings.size(); i++) {
            c.save("worlds",c.load("worlds")+","+strings.get(i));
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin,this::loadWorld);
    }
    public void loadWorld(){
        String configName="worlds.yml";
        config c=plugin.getCm().getConfig(configName);
        for (String s:c.load("worlds").split(",")){
            String[] s1=s.split(" ");
            worlds.add(Bukkit.getWorld(s1[0]));
            templates.add(new File(s1[1]));
    }
    }
}
