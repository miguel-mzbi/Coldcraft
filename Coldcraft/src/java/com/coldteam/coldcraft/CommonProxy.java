package com.coldteam.coldcraft;

import com.coldteam.coldcraft.blocks.ModBlocks;
import com.coldteam.coldcraft.crafting.ModCrafting;
import com.coldteam.coldcraft.event.EventHandlerCommon;
import com.coldteam.coldcraft.event.LivingUpdateEvent;
import com.coldteam.coldcraft.items.ModItems;
import com.coldteam.coldcraft.network.ModGuiHandler;
import com.coldteam.coldcraft.network.packets.PacketSyncArmorTemp;
import com.coldteam.coldcraft.network.packets.PacketSyncBiomeTemp;
import com.coldteam.coldcraft.network.packets.PacketSyncOnCampTemp;
import com.coldteam.coldcraft.network.packets.PacketSyncPlayerData;
import com.coldteam.coldcraft.network.packets.PacketSyncTemperature;
import com.coldteam.coldcraft.tileentity.ModTileEntities;

import minersbasic.api.MinersbasicAPI;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
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
		Main.packetHandler.registerPacket(PacketSyncBiomeTemp.class, new PacketSyncBiomeTemp.Handler(), Side.CLIENT);
		Main.packetHandler.registerPacket(PacketSyncOnCampTemp.class, new PacketSyncOnCampTemp.Handler(), Side.CLIENT);
		Main.packetHandler.registerPacket(PacketSyncArmorTemp.class, new PacketSyncArmorTemp.Handler(), Side.CLIENT);

	
		MinecraftForge.EVENT_BUS.register(new LivingUpdateEvent());
		MinecraftForge.EVENT_BUS.register(new EventHandlerCommon());
	    NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, (IGuiHandler) new ModGuiHandler());
		
	}
	public void postInit(FMLPostInitializationEvent e){
		
	}
}
