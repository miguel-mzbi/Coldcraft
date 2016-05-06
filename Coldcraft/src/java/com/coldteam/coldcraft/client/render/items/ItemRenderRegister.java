package com.coldteam.coldcraft.client.render.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

import com.coldteam.coldcraft.Main;
import com.coldteam.coldcraft.items.ItemModCoat;
import com.coldteam.coldcraft.items.ModItems;

public final class ItemRenderRegister {

	public static String modid = Main.MODID;
	
	public static void registerItemRenderer() {
	    reg(ModItems.termometer);
	    reg(ModItems.coatBoots);
	    reg(ModItems.winterCap);
	    reg(ModItems.coatCoat);

	}

	public static void reg(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
	
	public static void reg(ItemModCoat item){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}