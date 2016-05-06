package com.coldteam.coldcraft.client.gui;

import java.io.IOException;

import com.coldteam.coldcraft.entitydata.PlayerData;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

//Create GUI to for the thermometer item
public class GuiTempBar extends GuiScreen {

	private String temp; //Temperature string
	private GuiButton exit; //Exit button
	private EntityPlayer entityPlayer; //Player who open GUI (On SP)
	private String biomeTemp; //Biome Temperature string
	
	public GuiTempBar(EntityPlayer p) {
		this.entityPlayer = p; //Getter for the player who opens the GUI
	}
	
	//GUI initialization
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		this.buttonList.add(this.exit = new GuiButton(0, this.width / 2 - 100, this.height / 2, "Close"));//Location of the button on screen
		super.initGui();
	}
	
	//What to do when actions are done with buttons
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button == this.exit) { //If button that received action is the exit button
			this.mc.displayGuiScreen(null); //Close the screen
			if (this.mc.currentScreen == null)
                this.mc.setIngameFocus();
		}
	}
	
	//Method called every tick while screen is open
	@Override
	public void updateScreen() {
		temp = "Temperature: "+String.format("%.2f",PlayerData.get(this.entityPlayer).getTemperature());//Get value of temperature
		biomeTemp = "Current biome temperature: "+String.format("%.2f",PlayerData.get(this.entityPlayer).getBiomeTemp());//Get value of biome tempereture
		super.updateScreen();//Update
	}

	//Locations of strings in the screen
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();//Background
		this.fontRendererObj.drawString(temp, width / 2 - 90, 50, 0xFFFFFF);//Location temperature string
		this.fontRendererObj.drawString(biomeTemp, width / 2 - 90, 70, 0xFFFFFF);//Location biome temperature string
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	//GUI doesn't pause game when opened
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
