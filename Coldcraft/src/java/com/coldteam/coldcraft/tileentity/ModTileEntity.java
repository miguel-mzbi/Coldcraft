package com.coldteam.coldcraft.tileentity;

import java.util.List;

import com.coldteam.coldcraft.entitydata.PlayerData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

//Functionality of tile entity
public class ModTileEntity extends TileEntity implements IUpdatePlayerListBox{
	//Method called on every client and server tick
	@Override
	public void update(){
		//Get coordinates of tiles
		int xCoord = this.getPos().getX();
		int yCoord = this.getPos().getY();
		int zCoord = this.getPos().getZ();
		//Create volume	to check on every tile
		AxisAlignedBB aabb = AxisAlignedBB.fromBounds(xCoord-4, yCoord-4, zCoord-4, xCoord+4, yCoord+4, zCoord+4);
		//Fill list with instances of EntityPlayer in the volume
		List<?> presentEnt = worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb);
		if(!presentEnt.isEmpty()){
			EntityPlayer player = (EntityPlayer) presentEnt.get(0);
			if (PlayerData.get(player).isServerSide()){//If player present on server side
				PlayerData.get(player).setCamp(true);//Set campfire calling true
			}
		}
	}
}
