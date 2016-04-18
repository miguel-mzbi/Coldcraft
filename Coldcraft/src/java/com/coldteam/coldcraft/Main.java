package com.coldteam.coldcraft;

import minersbasic.api.network.PacketHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION)
public class Main {

    public static final String MODID = "coldcraft";
    public static final String MODNAME = "Coldcraft Survival Mod";
    public static final String VERSION = "0.1.0";

    @Instance
    public static Main instance = new Main();
    
    @SidedProxy(clientSide="com.coldteam.coldcraft.ClientProxy", serverSide="com.coldteam.coldcraft.ServerProxy")
    public static CommonProxy proxy;
    
    public static PacketHandler packetHandler;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    	proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
    	proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	proxy.postInit(e);
    }
}