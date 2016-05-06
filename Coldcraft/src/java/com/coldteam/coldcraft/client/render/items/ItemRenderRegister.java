package com.coldteam.coldcraft.client.render.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

import com.coldteam.coldcraft.Main;
import com.coldteam.coldcraft.items.ItemModCoat;
import com.coldteam.coldcraft.items.ModItems;

//Register items textures
public final class ItemRenderRegister {

	public static String modid = Main.MODID;
	
	public static void registerItemRenderer() {
	    reg(ModItems.thermometer);//Register thermometer textures
	    reg(ModItems.coatBoots);//Register coat boots textures
	    reg(ModItems.winterCap);//Register winter cap textures
	    reg(ModItems.coatCoat);//Register coat textures
	}

	//Register method for items
	public static void reg(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, 
				new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
	
	//Register method for coat's items
	public static void reg(ItemModCoat item){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, 
				new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}