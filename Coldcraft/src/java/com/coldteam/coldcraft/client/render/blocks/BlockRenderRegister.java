package com.coldteam.coldcraft.client.render.blocks;

import com.coldteam.coldcraft.Main;
import com.coldteam.coldcraft.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

//Register blocks textures
public final class BlockRenderRegister {

	public static void registerBlockRenderer() {
		reg(ModBlocks.campFire); //Register campfire texture 
	}

	public static String modid = Main.MODID; //Get ModID to assign texture location

	public static void reg(Block block) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), //Get texture 
				0, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));//Get location
	}
}

