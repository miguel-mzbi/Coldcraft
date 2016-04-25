package com.coldteam.coldcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModBlocks {

    public static Block campFire;

    public static void createBlocks() {
        GameRegistry.registerBlock(campFire = new BasicBlock("camp_fire", Material.ground, 1.0F, 10F).setLightLevel(1.0f), "camp_fire");
    
	}
}
