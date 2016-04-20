package com.coldteam.coldcraft.client.gui;

import java.io.IOException;

import com.coldteam.coldcraft.entitydata.PlayerData;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class GuiTempBar extends GuiScreen {

	private String temp;
	private GuiButton a;
	private EntityPlayer entityPlayer;
	
	public GuiTempBar(EntityPlayer p) {
		this.entityPlayer = p;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		this.buttonList.add(this.a = new GuiButton(0, this.width / 2 - 100, this.height / 2 - 24, "This is button a"));
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
		temp = "Temperatura: "+String.format("%.2f",PlayerData.get(this.entityPlayer).getTemperature());
		super.updateScreen();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.fontRendererObj.drawString(temp, width / 2 - 27, 50, 0xFFFFFF);

		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}
	
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
