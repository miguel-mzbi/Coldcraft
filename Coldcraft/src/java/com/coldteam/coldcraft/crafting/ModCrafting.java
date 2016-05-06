package com.coldteam.coldcraft.crafting;

import com.coldteam.coldcraft.blocks.ModBlocks;
import com.coldteam.coldcraft.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

//Create crafting recipes 
public class ModCrafting {
	public static void initCrafting(){
		GameRegistry.addRecipe(new ItemStack(ModBlocks.campFire), "IFI", "###", '#', Blocks.cobblestone, 'I', Items.stick, 'F', Items.flint_and_steel);//For the campfire
		GameRegistry.addRecipe(new ItemStack(ModItems.coatBoots), "I I", "# #", '#', Items.leather, 'I', Blocks.wool);//For the coat's boots
		GameRegistry.addRecipe(new ItemStack(ModItems.coatCoat), "# #", "III", "III", '#', Items.leather, 'I', Blocks.wool);//For the coat
		GameRegistry.addRecipe(new ItemStack(ModItems.winterCap), "III", "I I", 'I', Blocks.wool);//For the winter cap
		GameRegistry.addRecipe(new ItemStack(ModItems.thermometer), "#", "I", '#', Blocks.glass, 'I', Items.iron_ingot);//For the thermometer
	}
}

/*
 Camp fire --- I = Stick --- # = Cobblestone --- F = Flint and steel
	IFI
	###

 Coat's boots --- # = Leather --- I = Wool
 	I I
 	# #
 
 Coat --- # = Leather --- I = Wool
 	# #
 	III
 	III
 
 Winter cap --- I = Wool
 	III
 	I I
 	I I
 Thermometer --- # = Glass --- I = Iron ingot
 	#
 	I
*/
 