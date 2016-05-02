package com.coldteam.coldcraft.items;

import com.coldteam.coldcraft.Main;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		
	}
}