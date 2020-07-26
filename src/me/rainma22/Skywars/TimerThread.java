package me.rainma22.Skywars;

public class TimerThread implements Runnable{
    private Main plugin;
    public boolean running=true;
    private EnchantMan enchantMan;
    public TimerThread(Main plugin, EnchantMan em){
        this.plugin=plugin;
        enchantMan=em;
    }
    @Override
    public void run(){
        String configName="Skywars.yml";
        config c=plugin.getCm().getConfig(configName);
        int Countdown;
        if (c==null){
            plugin.getCm().addConfig(configName);
            c=plugin.getCm().getConfig(configName);
        }
        if (c.load("countdownTimeInSeconds")==null){
            c.save("countdownTimeInSeconds","150");
        }
        Countdown=Integer.parseInt(c.load("countdownTimeInSeconds"));
        while (running){
            try{
                Thread.sleep(Countdown);
                enchantMan.resetOpened();
            }catch (InterruptedException ie){}
        }
    }
}
