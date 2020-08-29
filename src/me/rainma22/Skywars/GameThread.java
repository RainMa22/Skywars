package me.rainma22.Skywars;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class GameThread implements Runnable{
    private Main plugin;
    public ArrayList<Player> aliveGamers;
    private World world;
    public boolean avaliable;
    public boolean running=true;
    GameThread(Main plugin,World world){
        this.plugin=plugin;
        this.world=world;
        resetWorld();
        avaliable=true;
    }
    private boolean resetWorld(){
        if (plugin.getMm().getMode(world)!=null){
            ArrayList ar=plugin.getWm().worlds;
            for(int i=0;i< ar.size();i++){
                if (world==ar.get(i)){
                    File f=plugin.getWm().templates.get(i);
                    plugin.getWm().saveWorldFromTemplate(f.getName(),plugin.getMm().getMode(world).getName(),world.getName());
                }
            }
        }
        return false;
    }
    public boolean playerJoin(Player p){
        if (avaliable){
            aliveGamers.add(p);
            return true;
        }
        return false;
    }
    public void spectatorJoin(Player p,Player target){
        p.setGameMode(GameMode.SPECTATOR);
        p.teleport(target);
    }
    public void run(){
        while (running){
            resetWorld();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
