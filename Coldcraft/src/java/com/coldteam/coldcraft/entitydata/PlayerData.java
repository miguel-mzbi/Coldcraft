package com.coldteam.coldcraft.entitydata;

import com.coldteam.coldcraft.Main;
import com.coldteam.coldcraft.network.packets.PacketSyncPlayerData;
import com.coldteam.coldcraft.network.packets.PacketSyncTemperature;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerData implements IExtendedEntityProperties{

	private static final String identifier = "coldcraftPlayerData";

	// PROPERTIES =============================================================

	private final EntityPlayer player;
	private double temperature;
	private BlockPos pos;
	private double onCampTemp;
	private double tickChange = 0.0;
	private boolean wasCampCalled;
	

	// CONSTRUCTOR, GETTER, REGISTER ==========================================

	public PlayerData(EntityPlayer player) {
		this.player = player;
		this.temperature = 37.0;
		this.pos = this.player.getPosition();
		this.wasCampCalled = false;

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

	// LOAD, SAVE Currently not working properly

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
		if (nbt.hasKey("temperature", 3))
			this.setTemperature(nbt.getDouble("temperature"));

	}

	@Override
	public void init(Entity entity, World world) {
	}

	// Interactions =================================================

	//When near a camp fire
	public void campTemperature(){
		if(this.onCampTemp < 6.0){
			System.out.println("Camping");
			this.onCampTemp += 0.001;
			this.setTemperature(this.getTemperature()+0.001);
			this.wasCampCalled = true;
		}
	}
	
	//Biome Effect
	public void biomeTemperature(){
		float biomeTemp = this.player.worldObj.getBiomeGenForCoords(this.pos).getFloatTemperature(this.pos);
		double objectiveTemp, rate;
		
		if(biomeTemp < 0.5){
			objectiveTemp = 29.0;
			if(biomeTemp <=0){
				rate = 8000;
			}
			else{
				rate = 1600;
			}
		}
		else if(0.5 < biomeTemp && biomeTemp <= 1.2){
			objectiveTemp = 37.0;
			rate = 10000;
		}
		else{
			objectiveTemp = 43.0;
			rate = 3000;
		}
		this.tickChange += (objectiveTemp - this.temperature)/rate;
	}
	
	//Loop
	public void doStuff(){
		this.tickChange = 0;
		if(!this.wasCampCalled && this.onCampTemp > 0.0){
			this.onCampTemp -= 0.001;
			this.tickChange -= 0.001;
		}
		//this.biomeTemperature();
		this.setTemperature(this.getTemperature()+this.tickChange);
		this.wasCampCalled = false;
	}
	
//Getters, setters, sync
	public void setTemperature(double d) {
		this.temperature = d;
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









