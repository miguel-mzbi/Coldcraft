package com.coldteam.coldcraft.entitydata;

import com.coldteam.coldcraft.Main;
import com.coldteam.coldcraft.network.packets.PacketSyncPlayerData;
import com.coldteam.coldcraft.network.packets.PacketSyncTemperature;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerData implements IExtendedEntityProperties{

	private static final String identifier = "coldcraftPlayerData";

	// PROPERTIES =============================================================

	private final EntityPlayer player;
	private double temperature;

	// CONSTRUCTOR, GETTER, REGISTER ==========================================

	public PlayerData(EntityPlayer player) {
		this.player = player;
		this.temperature = 100;
	}

	public static PlayerData get(EntityPlayer player) {
		return (PlayerData) player.getExtendedProperties(identifier);
	}

	public static void register(EntityPlayer player) {
		player.registerExtendedProperties(identifier, new PlayerData(player));
	}

	public boolean isServerSide() {
		return this.player instanceof EntityPlayerMP;
	}

	// LOAD, SAVE =============================================================

	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		nbt.setDouble("temperature", this.getTemperature());
	}

	public void saveReviveRelevantNBTData(NBTTagCompound nbt, boolean wasDeath) {
		if (!wasDeath)
			this.saveNBTData(nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		if (nbt.hasKey("temeprature", 3))
			this.setTemperature(nbt.getDouble("temperature"));

	}

	@Override
	public void init(Entity entity, World world) {
	}

	// GETTER, SETTER, SYNCER =================================================

	public void setTemperature(double d) {
		this.temperature = d;
		this.syncTemperature();
	}
	
	public void campTemperature() {
		this.temperature += 0.005;
		this.syncTemperature();
	}
	
	public double getTemperature() {
		return this.temperature;
	}

	public void syncTemperature() {
	    if (this.isServerSide()){
	        Main.packetHandler.sendToAllAround(new PacketSyncTemperature(this.player, this.getTemperature()), this.player, 128);
	    }
	}

	public void syncAll() {
		if (this.isServerSide())
			Main.packetHandler.sendTo(new PacketSyncPlayerData(this), (EntityPlayerMP) this.player);
	}

	public void requestSyncAll() {
		if (!this.isServerSide())
			Main.packetHandler.sendToServer(new PacketSyncPlayerData());
	}
	
}
