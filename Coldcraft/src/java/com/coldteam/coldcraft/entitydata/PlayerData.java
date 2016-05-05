package com.coldteam.coldcraft.entitydata;

import com.coldteam.coldcraft.Main;
import com.coldteam.coldcraft.network.packets.PacketSyncOnCampTemp;
import com.coldteam.coldcraft.network.packets.PacketSyncPlayerData;
import com.coldteam.coldcraft.network.packets.PacketSyncTemperature;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerData implements IExtendedEntityProperties{

	private static final String identifier = "coldcraftTemperature";

	// PROPERTIES =============================================================

	private final EntityPlayer player;
	private double temperature;
	private double generalTemp;
	private BlockPos pos;
	private double onCampTemp;
	private float tickChange = 0;
	private boolean wasCampCalled;
	private BiomeGenBase biome;
	private float biomeTemp;
	private int previousType;
	

	// CONSTRUCTOR, GETTER, REGISTER ==========================================

	public PlayerData(EntityPlayer player) {
		this.player = player;
		this.temperature = 37.0;
		this.generalTemp = 37.0;
		this.wasCampCalled = false;
		this.onCampTemp = 0.0;
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

	// LOAD, SAVE Currently

	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		nbt.setDouble("temperature", this.getTemperature());
		nbt.setDouble("generalTemp", this.getGeneralTemp());
		nbt.setDouble("onCampTemp", this.getOnCampTemp());

	}

	public void saveReviveRelevantNBTData(NBTTagCompound nbt, boolean wasDeath) {
		if (!wasDeath)
			this.saveNBTData(nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		if (nbt.hasKey("temperature")){
			this.setTemperature(nbt.getDouble("temperature"));
		}
		if (nbt.hasKey("generalTemp")){
			this.setGeneralTemp(nbt.getDouble("generalTemp"));
		}
		if (nbt.hasKey("onCampTemp")){
			this.setOnCampTemp(nbt.getDouble("onCampTemp"));
		}
	}

	@Override
	public void init(Entity entity, World world) {}

	// Interactions =================================================

	//Biome Effect
	public void biomeTemperature(){
		if(!this.isServerSide()){
			this.tickChange = 0;
			double objectiveTemp;
			
			if(this.biomeTemp < 0.5){
				objectiveTemp = 29.0;
				if(this.generalTemp == objectiveTemp){
					System.out.println("Cold or Cooler, temperature reached");
				}
				else if(this.generalTemp > objectiveTemp){
					if(this.biomeTemp <=0){
						this.tickChange -= 0.005;
						System.out.println("Cold");
					}
					else{
						this.tickChange -= 0.001;
						System.out.println("Kinda Cold");
					}
				}
				else{
					System.out.println("Cold error");
					this.tickChange += 29.0 - this.generalTemp;
					this.generalTemp = 29.0;
					
				}
			}
			else if(0.5 <= this.biomeTemp && this.biomeTemp <= 1.2){
				objectiveTemp = 37.0;
				if(this.generalTemp < objectiveTemp){
					System.out.println("From cold");
					this.previousType = 0;
					if(previousType == 1){
						this.tickChange -= this.generalTemp - 37.0;
						this.generalTemp = 37.0;
						this.previousType = 3;
					}
					else{
						this.tickChange += 0.002;
					}
				}
				else if(this.generalTemp == objectiveTemp){
					System.out.println("Perfect");
				}
				else{
					System.out.println("From hot");
					previousType = 1;
					if(previousType == 0){
						this.tickChange -= 37.0 - this.generalTemp;
						this.previousType = 3;
					}
					else{
						this.tickChange -= 0.002;
					}
				}
			}
			else{
				objectiveTemp = 43.0;
				if(this.generalTemp == objectiveTemp){
					System.out.println("Hot, temperature reached");
				}
				else if(this.generalTemp < objectiveTemp){
					this.tickChange += 0.005;
					System.out.println("Hot");
				}
				else{
					this.tickChange -= this.generalTemp - 43.0;
					this.generalTemp = 43.0;
					System.out.println("Hot error");
				}
			}
			this.generalTemp += this.tickChange;
		}
	}
	
	//Camp Effect
	public void campStuff(){
		if(!this.isServerSide()){
			if(!this.wasCampCalled && this.onCampTemp > 0.0){
				System.out.println("The camp is being turned off");
				this.setOnCampTemp(this.onCampTemp - 0.0011);
				this.tickChange -= 0.0011;
			}
			else if (!this.wasCampCalled && this.onCampTemp <= 0){
				System.out.println("The camp isn't there");
				this.onCampTemp = 0.0;
			}
			else if(this.wasCampCalled && this.onCampTemp < 6.0){
				System.out.println("With the camp");
				this.setOnCampTemp(this.onCampTemp + 0.0011);
				this.tickChange += 0.0011;
			}
			else if(this.wasCampCalled && this.onCampTemp >= 6.0){
				System.out.println("The camp is full");
				this.onCampTemp = 6.0;
			}
			else{
				System.out.println("Error");
			}
		}
	}
	
	//Loop
	public void doStuff(){
		if(!this.isServerSide()){
			this.tickChange = 0;
			this.pos = this.player.getPosition();
			BiomeGenBase tempBiome = this.player.worldObj.getBiomeGenForCoords(this.pos);
			if(this.biome != tempBiome){
				this.biome = tempBiome;
				this.biomeTemp = this.biome.getFloatTemperature(this.pos);
				System.out.println("Biome Change");
			}
					
			this.biomeTemperature();
			this.campStuff();
			
			this.setTemperature(this.getTemperature()+this.tickChange);
			System.out.println("Temp: "+this.temperature+ "-----General: "+this.generalTemp);
			this.wasCampCalled = false;
		}
	}
	
//Getters, setters, sync
	public void setTemperature(double d) {
		this.temperature = d;
		this.syncTemperature();
		//this.syncAll();
	}
	
	public double getTemperature() {
		return this.temperature;
	}
	
	public void setGeneralTemp(double d) {
		this.generalTemp = d;
	}
	
	public double getGeneralTemp() {
		return this.generalTemp;
	}
	
	public void setCamp(boolean d){
		this.wasCampCalled = d;
	}
	
	public boolean getCamp(){
		return this.wasCampCalled;
	}
	
	public void setOnCampTemp(double d){
		this.onCampTemp = d;
		this.syncOnCampTemperature();
	}
	
	public double getOnCampTemp(){
		return this.onCampTemp;
	}

	public void syncTemperature() {
	    if (this.isServerSide()){
	    	Main.packetHandler.sendTo(new PacketSyncTemperature(this.getTemperature(), this.getGeneralTemp()), (EntityPlayerMP) this.player);
	    }
	}
	
	public void syncOnCampTemperature() {
	    if (this.isServerSide()){
	    	Main.packetHandler.sendTo(new PacketSyncOnCampTemp(this.getOnCampTemp()), (EntityPlayerMP) this.player);
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

