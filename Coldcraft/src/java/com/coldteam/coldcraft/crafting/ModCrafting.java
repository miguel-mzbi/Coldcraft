package com.coldteam.coldcraft.crafting;

import com.coldteam.coldcraft.blocks.ModBlocks;
import com.coldteam.coldcraft.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {
	public static void initCrafting(){
		GameRegistry.addRecipe(new ItemStack(ModBlocks.campFire), "IFI", "###", '#', Blocks.cobblestone, 'I', Items.stick, 'F', Items.flint_and_steel);
		GameRegistry.addRecipe(new ItemStack(ModItems.coatBoots), "I I", "# #", '#', Items.leather, 'I', Blocks.wool);
		GameRegistry.addRecipe(new ItemStack(ModItems.coatCoat), "# #", "III", "III", '#', Items.leather, 'I', Blocks.wool);
		GameRegistry.addRecipe(new ItemStack(ModItems.winterCap), "III", "I I", 'I', Blocks.wool);
		GameRegistry.addRecipe(new ItemStack(ModItems.thermometer), "#", "I", '#', Blocks.glass, 'I', Items.iron_ingot);
	}
}
