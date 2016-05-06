package com.coldteam.coldcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
//Block creation and constructor
public final class ModBlocks {

    public static Block campFire; //Campfire block creation

    //Method called on the proxies to create the blocks
    public static void createBlocks() {
        GameRegistry.registerBlock(campFire = new BasicBlock("camp_fire", Material.ground, 1.0F, 10F)
        		.setLightLevel(1.0f), "camp_fire");//Data is passed to the general constructor
    
	}
}
