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

//Methods and constructors needed by both the server and client
public class CommonProxy {
	
	//Basic first things to load. 
	public void preInit(FMLPreInitializationEvent e){
		ModItems.createItems(); //Items creation
	    ModBlocks.createBlocks(); //Blocks creation
	    ModTileEntities.init(); //Tile entities (of blocks) creation
	}
	
	//Other things to load
	public void init(FMLInitializationEvent e){
	    ModCrafting.initCrafting(); //Crafting recipes creation
	    NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, (IGuiHandler) new ModGuiHandler()); //GUI creation
	    //Packet handlers
	    Main.packetHandler = MinersbasicAPI.createPacketHandler(Main.MODID); //Load Miner's Basic Packet Handler utilities 
	    Main.packetHandler.registerBidiPacket(PacketSyncPlayerData.class, new PacketSyncPlayerData.Handler()); //Load all player data sync
		Main.packetHandler.registerPacket(PacketSyncTemperature.class, new PacketSyncTemperature.Handler(), Side.CLIENT); //Load temperature sync
		Main.packetHandler.registerPacket(PacketSyncBiomeTemp.class, new PacketSyncBiomeTemp.Handler(), Side.CLIENT); //Load biome temperature sync
		Main.packetHandler.registerPacket(PacketSyncOnCampTemp.class, new PacketSyncOnCampTemp.Handler(), Side.CLIENT); //Load campfire temperature sync
		Main.packetHandler.registerPacket(PacketSyncArmorTemp.class, new PacketSyncArmorTemp.Handler(), Side.CLIENT); //Load armor temperature sync
		//Event bus
		MinecraftForge.EVENT_BUS.register(new LivingUpdateEvent()); //Load update per tick event
		MinecraftForge.EVENT_BUS.register(new EventHandlerCommon()); //Load other events
	}
	
	//Relation between mods loading
	public void postInit(FMLPostInitializationEvent e){	
	}
}
