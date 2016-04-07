package com.coldteam.coldcraft.crafting;

import com.coldteam.coldcraft.blocks.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {
	public static void initCrafting(){
		GameRegistry.addRecipe(new ItemStack(ModBlocks.testBlock), "IFI", "###", '#', Blocks.cobblestone, 'I', Items.stick, 'F', Items.flint_and_steel);
	}
}
