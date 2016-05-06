package com.coldteam.coldcraft.items;

import com.coldteam.coldcraft.entitydata.PlayerData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

//Functions of the custom armor
public class ItemModCoat extends ItemArmor{
	//Constructor
	public ItemModCoat(String unlocalizedName, ArmorMaterial material, int renderIndex, int armorType) {
        super(material, renderIndex, armorType);
        this.setUnlocalizedName(unlocalizedName);
    }
	//Event that gets called on every tick for the armor and the player
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {//If the three armor parts are being used
		if (player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem() == ModItems.coatBoots
		        && player.inventory.armorItemInSlot(2) != null && player.inventory.armorItemInSlot(2).getItem() == ModItems.coatCoat
		        && player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() == ModItems.winterCap) {
				//Set to true the armor usage on tick on PlayerData for the MP
		        if(PlayerData.get(player).isServerSide()){
		        	PlayerData.get(player).setArmor(true);
		        }
		}
	}  
}

