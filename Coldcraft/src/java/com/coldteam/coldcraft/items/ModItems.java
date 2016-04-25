package com.coldteam.coldcraft.items;

import com.coldteam.coldcraft.Main;
import com.coldteam.coldcraft.entitydata.PlayerData;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModItems {

	public static void createItems() {
		
		//Open GUI
		GameRegistry.registerItem(new Item() {
		    @Override
		    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		        if (worldIn.isRemote) {
		            playerIn.openGui(Main.instance, 0, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
		        }
		        return itemStackIn;
		    }
		}.setUnlocalizedName("gui_item").setCreativeTab(CreativeTabs.tabMisc), "packetitem");
		
		//See Temperature
		GameRegistry.registerItem(new Item() {
			@Override
			public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
				if (world.isRemote) {
					player.addChatMessage(new ChatComponentText("Temperature: " + String.format("%.2f", PlayerData.get(player).getTemperature())));
				}
				return stack;
			}
		}.setUnlocalizedName("output_item").setCreativeTab(CreativeTabs.tabMisc), "output_item");
		
		
		//Increase Temperature
		GameRegistry.registerItem(new Item() {
			@Override
			public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
				if (!world.isRemote) {
					PlayerData.get(player).setTemperature(PlayerData.get(player).getTemperature() + 1);
				}
				return stack;
			}
		}.setUnlocalizedName("increase_item").setCreativeTab(CreativeTabs.tabMisc), "increase_item");
		
		//Decrease Temperature
		GameRegistry.registerItem(new Item() {
			@Override
			public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
				if (!world.isRemote) {
					PlayerData.get(player).setTemperature(PlayerData.get(player).getTemperature() - 1);
				}
				return stack;
			}
		}.setUnlocalizedName("decrease_item").setCreativeTab(CreativeTabs.tabMisc), "decrease_item");
		
		
	}
}