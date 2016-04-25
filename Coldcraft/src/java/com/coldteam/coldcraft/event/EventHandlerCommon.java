package com.coldteam.coldcraft.event;

import com.coldteam.coldcraft.entitydata.PlayerData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerCommon {
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing e) {
	    if (e.entity instanceof EntityPlayer) {
	        PlayerData.register((EntityPlayer) e.entity);
	    }
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e) {
	    if (e.entity instanceof EntityPlayer) {
	        PlayerData.get((EntityPlayer) e.entity).requestSyncAll();
	    }
	}
	
	@SubscribeEvent
	public void onPlayerCloned(PlayerEvent.Clone e) {
	    NBTTagCompound nbt = new NBTTagCompound();
	    PlayerData.get(e.original).saveReviveRelevantNBTData(nbt, e.wasDeath);
	    PlayerData.get(e.entityPlayer).loadNBTData(nbt);
	}
	
}
