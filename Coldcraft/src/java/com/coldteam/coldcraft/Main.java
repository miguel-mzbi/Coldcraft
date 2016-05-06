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

    public static final String MODID = "coldcraft"; //Id name of mod
    public static final String MODNAME = "ColdcraftSurvivalMod"; //Name of mod
    public static final String VERSION = "v.1.0.0-mc1.8"; //Version of mod + mc version

    @Instance
    public static Main instance = new Main(); //Creation of new loading order
    
    @SidedProxy(clientSide="com.coldteam.coldcraft.ClientProxy", serverSide="com.coldteam.coldcraft.ServerProxy")
    public static CommonProxy proxy; //Load common proxy always
    
    public static PacketHandler packetHandler; //Declare Packet Handlers existance

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    	proxy.preInit(e);
    	//Nothing here. Everything on proxies
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
    	proxy.init(e);
    	//Nothing here. Everything on proxies
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	proxy.postInit(e);    	
    	//Nothing here. Everything on proxies
    }
}