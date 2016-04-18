package com.coldteam.coldcraft.items;

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
		
		GameRegistry.registerItem(new Item() {
			@Override
			public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
				if (!world.isRemote) {
					PlayerData.get(player).setTemperature(PlayerData.get(player).getTemperature() + 1);
				}
				return stack;
			}
		}.setUnlocalizedName("increase_item").setCreativeTab(CreativeTabs.tabMisc), "increase_item");
		
		GameRegistry.registerItem(new Item() {
			@Override
			public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
				if (!world.isRemote) {
					PlayerData.get(player).setTemperature(PlayerData.get(player).getTemperature() - 1);
				}
				return stack;
			}
		}.setUnlocalizedName("decrease_item").setCreativeTab(CreativeTabs.tabMisc), "decrease_item");
		
		GameRegistry.registerItem(new Item() {
			@Override
			public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
				if (world.isRemote) {
					player.addChatMessage(new ChatComponentText("Temperature: " + PlayerData.get(player).getTemperature()));
				}
				return stack;
			}
		}.setUnlocalizedName("output_item").setCreativeTab(CreativeTabs.tabMisc), "output_item");
	}
}