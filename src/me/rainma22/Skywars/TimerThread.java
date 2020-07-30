package me.rainma22.Skywars;

public class TimerThread implements Runnable{
    private Main plugin;
    public boolean running=true;
    private EnchantMan enchantMan;
    public int Countdown;
    public TimerThread(Main plugin){
        this.plugin=plugin;
        enchantMan=plugin.getEm();
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
        for(ModeMan.mode mode:plugin.getMm().getMode())
            mode.remainingSec=Countdown;
    }
    public void resetCount(ModeMan.mode m){
        m.remainingSec=Countdown;
    }
    @Override
    public void run(){

        while (running){
            try{
                Thread.sleep(100);
                for(ModeMan.mode mode:plugin.getMm().getMode()){
                    mode.remainingSec--;
                    if (mode.remainingSec<=0){
                        enchantMan.resetOpened(mode.getName());
                    }
                }
            }catch (InterruptedException ie){}
        }
    }
}
