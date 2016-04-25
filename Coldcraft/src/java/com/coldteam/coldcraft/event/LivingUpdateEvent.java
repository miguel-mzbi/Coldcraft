package com.coldteam.coldcraft.event;

import com.coldteam.coldcraft.entitydata.PlayerData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LivingUpdateEvent {

	@SubscribeEvent
	public void onEntityUpdate(LivingEvent.LivingUpdateEvent e) {
		if(e.entityLiving instanceof EntityPlayer){
			EntityPlayer entityPlayer = (EntityPlayer) e.entityLiving;
			PlayerData.get(entityPlayer).doStuff();
		}
	}
}
