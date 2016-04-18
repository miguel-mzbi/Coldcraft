package com.coldteam.coldcraft;

import com.coldteam.coldcraft.blocks.ModBlocks;
import com.coldteam.coldcraft.crafting.ModCrafting;
import com.coldteam.coldcraft.event.EventHandlerCommon;
import com.coldteam.coldcraft.items.ModItems;
import com.coldteam.coldcraft.network.packets.PacketSyncPlayerData;
import com.coldteam.coldcraft.network.packets.PacketSyncTemperature;
import com.coldteam.coldcraft.tileentity.ModTileEntities;

import minersbasic.api.MinersbasicAPI;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e){
		ModItems.createItems();
	    ModBlocks.createBlocks();
	    ModTileEntities.init();
	}
	public void init(FMLInitializationEvent e){
	    ModCrafting.initCrafting();
	    Main.packetHandler = MinersbasicAPI.createPacketHandler(Main.MODID);
	    Main.packetHandler.registerBidiPacket(PacketSyncPlayerData.class, new PacketSyncPlayerData.Handler());
		Main.packetHandler.registerPacket(PacketSyncTemperature.class, new PacketSyncTemperature.Handler(), Side.CLIENT);
	
		MinecraftForge.EVENT_BUS.register(new EventHandlerCommon());
	
	}
	public void postInit(FMLPostInitializationEvent e){
		
	}
}
