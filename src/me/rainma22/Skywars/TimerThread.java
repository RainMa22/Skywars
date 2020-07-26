package me.rainma22.Skywars;

public class TimerThread implements Runnable{
    private Main plugin;
    public boolean running=true;
    private EnchantMan enchantMan;
    public int Countdown;
    public TimerThread(Main plugin, EnchantMan em){
        this.plugin=plugin;
        enchantMan=em;
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
    }
    @Override
    public void run(){

        while (running){
            try{
                Thread.sleep(100);
                enchantMan.RemainingSec--;
                if (enchantMan.RemainingSec<=0){
                    enchantMan.RemainingSec=Countdown;
                    enchantMan.resetOpened();
                }
            }catch (InterruptedException ie){}
        }
    }
}
