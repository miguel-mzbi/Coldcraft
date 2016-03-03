package com.coldteam.coldcraft.item;

import com.coldteam.coldcraft.Main;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	public static Item testItem;

    public static final void init() {
        testItem = new Item().setUnlocalizedName("testItem").setCreativeTab(CreativeTabs.tabMisc).setTextureName(Main.MODID + ":testimg");
        GameRegistry.registerItem(testItem, "testItem");
    }
}
