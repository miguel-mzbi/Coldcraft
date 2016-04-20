package com.coldteam.coldcraft.tileentity;

import java.util.List;

import com.coldteam.coldcraft.entitydata.PlayerData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class ModTileEntity extends TileEntity implements IUpdatePlayerListBox{
	
	
	@Override
	public void update() {
		int xCoord = this.getPos().getX();
		int yCoord = this.getPos().getY();
		int zCoord = this.getPos().getZ();
				
		AxisAlignedBB aabb = AxisAlignedBB.fromBounds(xCoord-4, yCoord-4, zCoord-4, xCoord+4, yCoord+4, zCoord+4);
		
		List<?> w = worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb);
		
		if (w.size() != 0){
			PlayerData.get((EntityPlayer) w.get(0)).campTemperature();

		}
		
	}
}
