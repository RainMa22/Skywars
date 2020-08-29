package me.rainma22.Skywars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SpawnMan {
    private Main p;
    public ArrayList<Location> playerSpawns=new ArrayList<>(0);
    public ArrayList<ArrayList<Location>> organizedSpawn=new ArrayList<>(0);
    public SpawnMan(Main m){
        p=m;
    }
    private void loadSpawn(){
        String configName="playerStart.yml";
        if(p.getCm().getConfig(configName)==null)p.getCm().addConfig(configName);
        config c=p.getCm().getConfig(configName);
        String s=c.load("locations").toLowerCase();
        String[] ss=s.split(",");
        for (String s1: ss){
            String[] ss1=s1.split(":");
            if (ss1.length==4) playerSpawns.add(new Location(Bukkit.getWorld(ss1[0]),Double.parseDouble(ss1[1]),Double.parseDouble(ss1[2]),Double.parseDouble(ss1[3])));
        }
    }
    private void organize(){
        ArrayList<String> worldnames=new ArrayList<>(0);
        for (int i = 0; i < playerSpawns.size(); i++) {
            if (worldnames.size()==0||!worldnames.contains(playerSpawns.get(i).getWorld().getName())){
                worldnames.add(playerSpawns.get(i).getWorld().getName());
            }
        }
        for(String s:worldnames){
            ArrayList<Location> temp2= (ArrayList<Location>) playerSpawns.clone();
            ArrayList<Location> temp=new ArrayList<>(0);
            for (int i = 0; i < temp2.size(); i++) {
                if (temp2.get(i).getWorld().getName()==s) temp.add(playerSpawns.get(i));
            }
            temp2.remove(temp);
            for (int i = 0; i < p.getWm().worlds.size(); i++) {
                if (organizedSpawn.size()<= i) organizedSpawn.add(null);
                if (p.getWm().worlds.get(i).getName()==s);
                organizedSpawn.add(i,temp);
            }
        }

    }
}
