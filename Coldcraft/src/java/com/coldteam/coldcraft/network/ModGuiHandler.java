package com.coldteam.coldcraft.network;

import com.coldteam.coldcraft.client.gui.GuiTempBar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

//Handler for the GUI
public class ModGuiHandler implements IGuiHandler {

	public static final int TEMPERATURE_GUI = 0;
	//For the server
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	//For the client
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == TEMPERATURE_GUI)
			return new GuiTempBar(player);
		return null;
	}

}
