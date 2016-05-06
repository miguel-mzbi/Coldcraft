package com.coldteam.coldcraft.items;

import net.minecraft.item.ItemArmor;

public class ItemModCoat extends ItemArmor{
	
	public ItemModCoat(String unlocalizedName, ArmorMaterial material, int renderIndex, int armorType) {
        super(material, renderIndex, armorType);

        this.setUnlocalizedName(unlocalizedName);
    }
}
