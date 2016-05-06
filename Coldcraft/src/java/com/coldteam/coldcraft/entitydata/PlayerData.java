package com.coldteam.coldcraft.entitydata;

import com.coldteam.coldcraft.Main;
import com.coldteam.coldcraft.network.packets.PacketSyncArmorTemp;
import com.coldteam.coldcraft.network.packets.PacketSyncBiomeTemp;
import com.coldteam.coldcraft.network.packets.PacketSyncOnCampTemp;
import com.coldteam.coldcraft.network.packets.PacketSyncPlayerData;
import com.coldteam.coldcraft.network.packets.PacketSyncTemperature;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.IExtendedEntityProperties;

//Handler of entity player data. Every process is made on server-side
public class PlayerData implements IExtendedEntityProperties{

	private static final String identifier = "coldcraftTemperature";// ID of the data
	//Properties
	private final EntityPlayer player;//Player who holds the data
	private double temperature;//Temperature of the player
	private double generalTemp;//Temperature only considering biome
	private BlockPos pos;//Position of the player
	private double onCampTemp;//Temperature given by the camp
	private float tickChange = 0;//Change of the temperature per tick
	private boolean wasCampCalled;//Was camp called on tick?
	private BiomeGenBase biome;//Biome where player is located
	private float biomeTemp;//Temperature of the biome
	private int previousType;//Previous biome type
	private double armorTemp;//Temperature given by the coat
	private boolean wasArmorCalled;//Was the coat called on tick?
	
//Methods for attributes ---------------------------------------------------
	//Constructor
	public PlayerData(EntityPlayer player) {
		this.player = player;
		this.temperature = 37.0;
		this.generalTemp = 37.0;
		this.wasCampCalled = false;
		this.onCampTemp = 0.0;
		this.biomeTemp = 0;
		this.armorTemp = 0.0;
		this.wasArmorCalled = false;
	}
	
	//Get the player's data for the entity
	public static PlayerData get(EntityPlayer player) {
		return (PlayerData) player.getExtendedProperties(identifier);
	}
	//Register the player's data for the entity
	public static void register(EntityPlayer player) {
		player.registerExtendedProperties(identifier, new PlayerData(player));
	}
	//Check if the process is called from the server side
	public boolean isServerSide() {
		return this.player instanceof EntityPlayerMP;
	}

	//Save data when paused or exiting game
	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		nbt.setDouble("temperature", this.getTemperature());//Save temperature
		nbt.setDouble("generalTemp", this.getGeneralTemp());//Save temperature considering only biomes
		nbt.setDouble("onCampTemp", this.getOnCampTemp());//Save camp's temperature
		nbt.setFloat("biomeTemp", this.getBiomeTemp());//Save biome's temperature
		nbt.setDouble("armorTemp", this.getArmorTemp());//Save camp's temperature
	}
	//Don't save data when player dies (Restore to defaults)
	public void saveReviveRelevantNBTData(NBTTagCompound nbt, boolean wasDeath) {
		if (!wasDeath)
			this.saveNBTData(nbt);
	}
	//Load data if exists when game is loaded
	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		if (nbt.hasKey("temperature")){
			this.setTemperature(nbt.getDouble("temperature"));//Load temperature
		}
		if (nbt.hasKey("generalTemp")){
			this.setGeneralTemp(nbt.getDouble("generalTemp"));//Load temperature considering only biomes
		}
		if (nbt.hasKey("onCampTemp")){
			this.setOnCampTemp(nbt.getDouble("onCampTemp"));//Load camp's temperature
		}
		if (nbt.hasKey("biomeTemp")){
			this.setBiomeTemp(nbt.getFloat("biomeTemp"));//Load biome's temperature
		}
		if (nbt.hasKey("armorTemp")){
			this.setArmorTemp(nbt.getDouble("armorTemp"));//Load camp's temperature
		}
	}
	//Start the player's data
	@Override
	public void init(Entity entity, World world) {}

//Interactions ---------------------------------------------------------------------
	//Biome Effect
	public void biomeTemperature(){
		if(this.isServerSide()){
			double objectiveTemp;//The temperature objective of the biome
			//Cold biomes
			if(this.biomeTemp < 0.5){
				objectiveTemp = 29.0;
				if(this.generalTemp <= objectiveTemp){ //If temperature is already reached
					this.tickChange += 29.0 - this.generalTemp;
					this.generalTemp = 29.0;
					System.out.println("Cold or Cooler, temperature reached");
				}
				else if(this.generalTemp > objectiveTemp){
					//Coldest biomes
					if(this.biomeTemp <=0){
						this.tickChange -= 0.005;//Decrease faster
						System.out.println("Cold");
					}
					//Cold biome
					else{
						this.tickChange -= 0.001;//Decrease
						System.out.println("Kinda Cold");
					}
				}
				else{
					System.out.println("Cold error");
				}
			}
			//Warm biomes
			else if(0.5 <= this.biomeTemp && this.biomeTemp <= 1.2){
				objectiveTemp = 37.0;//The temperature objective of the biome
				if(this.generalTemp < objectiveTemp){//If the player comes from a cold biome
					System.out.println("From cold");
					this.previousType = 0;
					if(previousType == 1){
						this.tickChange -= this.generalTemp - 37.0;
						this.generalTemp = 37.0;
						this.previousType = 3;
					}
					else{
						this.tickChange += 0.002;//Increase to warm
					}
				}
				else if(this.generalTemp == objectiveTemp){
					System.out.println("Perfect");//Reached objective temperature
				}
				else{
					System.out.println("From hot");//If the player comes from a hot biome
					previousType = 1;
					if(previousType == 0){
						this.tickChange -= 37.0 - this.generalTemp;
						this.previousType = 3;
					}
					else{
						this.tickChange -= 0.002;//Decrease to warm
					}
				}
			}
			//Hot biomes
			else{
				objectiveTemp = 43.0;
				if(this.generalTemp >= objectiveTemp){
					this.tickChange -= this.generalTemp - 43.0;
					this.generalTemp = 43.0;
					System.out.println("Hot, temperature reached");
				}
				else if(this.generalTemp < objectiveTemp){
					this.tickChange += 0.005;//Increase
					System.out.println("Hot");
				}
				else{
					System.out.println("Hot error");
				}
			}
			this.generalTemp += this.tickChange;//Add the biome tick change to the temperature that considers only the biome temperature
		}
	}
	//Camp Effect
	public void campStuff(){
		if(this.isServerSide()){
			if(!this.wasCampCalled && this.onCampTemp > 0.0){//If camp wasn't called but sends temperature
				this.setOnCampTemp(this.onCampTemp - 0.0011);
				this.tickChange -= 0.0011;//Reduce temperature
			}
			else if (!this.wasCampCalled && this.onCampTemp <= 0){//If camp wasn't called and doesn't sends temperature
				this.setOnCampTemp(0.0);//Set 0
			}
			else if(this.wasCampCalled && this.onCampTemp < 3.0){//If camp was called but hasn't reached the max temperature
				this.setOnCampTemp(this.onCampTemp + 0.0011);
				this.tickChange += 0.0011;//Increase temperature
			}
			else if(this.wasCampCalled && this.onCampTemp >= 3.0){//If camp was called but has reached the max temperature
				this.setOnCampTemp(3.0);//Set 3
			}
			else{
				System.out.println("Camp error");
			}
		}
	}
	//Coat Effect
	public void coatStuff(){
		if(this.isServerSide()){
			if(this.armorTemp < 5.0 && this.wasArmorCalled){//If coat was called but hasn't reached the max temperature
				this.setArmorTemp(this.armorTemp + 0.0051);
				this.tickChange += 0.0051;//Increase temperature
			}
			else if(this.armorTemp >= 6.0 && this.wasArmorCalled){//If coat was called but has reached the max temperature
				this.setArmorTemp(5.0);//Set 5
			}
			else if(this.armorTemp > 0.0 && !this.wasArmorCalled){//If coat wasn't called but sends temperature
				this.setArmorTemp(this.armorTemp - 0.0051);
				this.tickChange -= 0.0051;//Reduce temperature
			}
			else if(this.armorTemp <= 0.0 && !this.wasArmorCalled){//If coat wasn't called and doesn't sends temperature
				this.setArmorTemp(0.0);//Set 0
			}
			else{
				System.out.println("Armor error");
			}
		}
	}
	//Loop that gets called every tick
	public void doStuff(){
		if(this.isServerSide()){
			//Setters
			this.tickChange = 0;//Set tick change to 0
			this.pos = this.player.getPosition();//get position
			BiomeGenBase tempBiome = this.player.worldObj.getBiomeGenForCoords(this.pos);
			if(this.biome != tempBiome){
				this.biome = tempBiome;
				this.setBiomeTemp(this.biome.getFloatTemperature(this.pos));//Set temperature of the biome
				System.out.println("Biome Change");
			}
			//Process
			this.biomeTemperature();//Call biome process
			this.coatStuff();//Call coat process
			this.campStuff();//Call camfire stuff
			//Setters 2
			this.setTemperature(this.getTemperature()+this.tickChange);//Set new temperature
			System.out.println("Temp: "+this.temperature+ "-----General: "+this.generalTemp);
			this.wasCampCalled = false;//Set to false the calling of the campfire
			this.wasArmorCalled = false;//Set to false the calling of the armor(coat)
			//Damage
			if(this.temperature <= 29.0 || this.temperature >= 43.0){//If temperature gets to some extremes deals generic damage
				this.player.attackEntityFrom(DamageSource.generic, 1);
			}
		}
	}
	
//Getters, setters --------------------------------------------------------------
	public void setTemperature(double d) {
		this.temperature = d;
		this.syncTemperature();//Need for sync to player on SP
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
	
	public void setBiomeTemp(float d){
		this.biomeTemp = d;
		this.syncBiomeTemperature();//Need for sync to player on SP
	}
	
	public float getBiomeTemp(){
		return this.biomeTemp;
	}
	
	public void setOnCampTemp(double d){
		this.onCampTemp = d;
		this.syncOnCampTemperature();//Need for sync to player on SP
	}
	
	public double getOnCampTemp(){
		return this.onCampTemp;
	}
	
	public void setArmorTemp(double d){
		this.armorTemp = d;
		this.syncArmorTemperature();//Need for sync to player on SP
	}
	
	public double getArmorTemp(){
		return this.armorTemp;
	}	
	
	public void setArmor(boolean d){
		this.wasArmorCalled = d;
	}
	
	public boolean getArmor(){
		return this.wasArmorCalled;
	}	

//Synchronization between MP and SP
	//Sync temperature and general temperature
	public void syncTemperature() {
	    if (this.isServerSide()){
	    	Main.packetHandler.sendTo(new PacketSyncTemperature(this.getTemperature(), this.getGeneralTemp()), (EntityPlayerMP) this.player);
	    }
	}
	//Sync campfire temperature
	public void syncOnCampTemperature() {
	    if (this.isServerSide()){
	    	Main.packetHandler.sendTo(new PacketSyncOnCampTemp(this.getOnCampTemp()), (EntityPlayerMP) this.player);
	    }
	}
	//Sync armor temperature
	public void syncArmorTemperature() {
	    if (this.isServerSide()){
	    	Main.packetHandler.sendTo(new PacketSyncArmorTemp(this.getArmorTemp()), (EntityPlayerMP) this.player);
	    }
	}
	//Sync biome temperature
	public void syncBiomeTemperature() {
	    if (this.isServerSide()){
	    	Main.packetHandler.sendTo(new PacketSyncBiomeTemp(this.getBiomeTemp()), (EntityPlayerMP) this.player);
	    }
	}
	//Sync everything
	public void syncAll() {
		if (this.isServerSide())
			Main.packetHandler.sendTo(new PacketSyncPlayerData(this), (EntityPlayerMP) this.player);
	}
	//Request sync everything from the client side
	public void requestSyncAll() {
		if (!this.isServerSide())
			Main.packetHandler.sendToServer(new PacketSyncPlayerData());
	}
}