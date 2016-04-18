package com.coldteam.coldcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModBlocks {

    public static Block testBlock;

    public static void createBlocks() {
        GameRegistry.registerBlock(testBlock = new BasicBlock("test_block", Material.ground, 1.0F, 10F).setLightLevel(1.0f), "test_block");
    
	}
}
