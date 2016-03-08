package com.coldteam.coldcraft.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModItems {

	public static Item testItem;

	public static void createItems() {
		GameRegistry.registerItem(testItem = new BasicItem("test_item"), "test_item");
	}

}
