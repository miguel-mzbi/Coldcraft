package com.coldteam.coldcraft.event;

import com.coldteam.coldcraft.entitydata.PlayerData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//Event that gets called both for the server and client entity on each tick
public class LivingUpdateEvent {
	@SubscribeEvent
	public void onEntityUpdate(LivingEvent.LivingUpdateEvent e) {//If the entity which called the update is a player
		if(e.entityLiving instanceof EntityPlayer){
			EntityPlayer entityPlayer = (EntityPlayer) e.entityLiving;
			if(PlayerData.get(entityPlayer).isServerSide()){//If the player is called from the server
				PlayerData.get(entityPlayer).doStuff();//Call for the general loop on Player Data
			}
		}
	}
}
