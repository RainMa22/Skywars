package me.rainma22.Skywars;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
    private configMan cm;
    private ModeMan mm;
    private EnchantMan em;
    public configMan getCm(){
        return cm;
    }
    public ModeMan getMm(){return mm;}
    public EnchantMan getEm(){return em;}
    @Override
    public void onEnable() {
        cm= new configMan(this);
        mm= new ModeMan(this);
        em= new EnchantMan(this,mm);
    }

    @Override
    public void onDisable() {
    }
}
