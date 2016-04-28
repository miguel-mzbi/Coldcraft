package com.coldteam.coldcraft.client.gui;

import java.io.IOException;

import com.coldteam.coldcraft.entitydata.PlayerData;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.player.EntityPlayerMP;


public class GuiTempBar extends GuiScreen {

	private String temp;
	private String camp;
	private GuiButton a;
	private EntityPlayer entityPlayer;
	private String biomeTemp;
	private String biome;
	//EntityPlayerMP entityPlayerMP;
	
	public GuiTempBar(EntityPlayer p) {
		this.entityPlayer = p;
		//this.entityPlayerMP = (EntityPlayerMP)this.entityPlayer;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		this.buttonList.add(this.a = new GuiButton(0, this.width / 2 - 100, this.height / 2, "Close"));
		//System.out.println(this.entityPlayerMP);
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button == this.a) {
			this.mc.displayGuiScreen(null);
			if (this.mc.currentScreen == null)
                this.mc.setIngameFocus();
		}
	}
	
	@Override
	public void updateScreen() {
		temp = "Temperature: "+String.format("%.2f",PlayerData.get(this.entityPlayer).getTemperature());
		camp = "Is campfire near: "+PlayerData.get(this.entityPlayer).getCamp();
		biome = "Current biome: "+PlayerData.get(this.entityPlayer).getBiome();
		biomeTemp = "Current biome temperature: "+PlayerData.get(this.entityPlayer).getBiomeTemp();
		super.updateScreen();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.fontRendererObj.drawString(temp, width / 2 - 50, 30, 0xFFFFFF);
		this.fontRendererObj.drawString(camp, width / 2 - 50, 50, 0xFFFFFF);
		this.fontRendererObj.drawString(biome, width / 2 - 50, 70, 0xFFFFFF);
		this.fontRendererObj.drawString(biomeTemp, width / 2 - 50, 90, 0xFFFFFF);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
