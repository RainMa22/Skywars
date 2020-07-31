package me.rainma22.Skywars;

import org.bukkit.Location;

import java.util.ArrayList;

public class TimerThread implements Runnable{
    private Main plugin;
    public boolean running=true;
    private Worldman worldman;
    public int Countdown;
    public TimerThread(Main plugin){
        this.plugin=plugin;
        worldman=plugin.getWm();
        String configName="Skywars.yml";
        config c=plugin.getCm().getConfig(configName);
        if (c==null){
            plugin.getCm().addConfig(configName);
            c=plugin.getCm().getConfig(configName);
        }
        if (c.load("countdownTimeInSeconds")==null){
            c.save("countdownTimeInSeconds","150");
        }
        Countdown=Integer.parseInt(c.load("countdownTimeInSeconds"));
        for(Integer ints:worldman.refillTimes)
            ints--;
    }
    public void resetCount(String s){
        for (int i = 0; i < worldman.worlds.size(); i++) {
            if(worldman.worlds.get(i).getName().equalsIgnoreCase(s)){
                worldman.refillTimes.set(i,Countdown);
            }
        }
        ArrayList<Location> removedNum= new ArrayList<>(0);
        for (int i = 0; i < worldman.chestOpened.size(); i++) {
            Location l=worldman.chestOpened.get(i);
            if (!l.getWorld().getName().equalsIgnoreCase(s)) removedNum.add(l);
        }
        worldman.chestOpened=removedNum;
    }

    @Override
    public void run(){
        while (running){
            try{
                Thread.sleep(100);
                for (int i = 0; i < worldman.worlds.size(); i++) {
                    Integer integer=worldman.refillTimes.get(i);
                    integer--;
                    if (integer==0){
                        this.resetCount(worldman.worlds.get(i).getName());
                    }
                }
            }catch (InterruptedException ie){}
        }
    }
}
