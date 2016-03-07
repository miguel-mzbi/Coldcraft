package com.coldteam.coldcraft;

import com.coldteam.coldcraft.items.ModItems;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e){
		ModItems.createItems();
	}
	public void init(FMLInitializationEvent e){
		
	}
	public void postInit(FMLPostInitializationEvent e){
		
	}
}
