package com.coldteam.coldcraft;

import com.coldteam.coldcraft.blocks.ModBlocks;
import com.coldteam.coldcraft.crafting.ModCrafting;
import com.coldteam.coldcraft.tileentity.ModTileEntities;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e){
		
	    ModBlocks.createBlocks();
	    ModTileEntities.init();
	}
	public void init(FMLInitializationEvent e){
	    ModCrafting.initCrafting();
		
	}
	public void postInit(FMLPostInitializationEvent e){
		
	}
}
