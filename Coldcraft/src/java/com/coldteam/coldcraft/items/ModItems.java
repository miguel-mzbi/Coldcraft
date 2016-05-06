package com.coldteam.coldcraft.items;

import com.coldteam.coldcraft.Main;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.common.util.EnumHelper;

//Creation of items
public final class ModItems {
	
	public static Item thermometer; //New item thermometer
	public static ArmorMaterial Coat = EnumHelper.addArmorMaterial("Coat", "coldcraft:coat", 6, new int[]{1, 3, 2, 1}, 0); //New armor coat
	public static ItemModCoat coatBoots;//New item of coat
	public static ItemModCoat coatCoat;//New item of coat
	public static ItemModCoat winterCap;//New item of coat

	public static void createItems() {
		//Open GUI
		GameRegistry.registerItem(thermometer = new Item() {//Register thermometer
		    @Override
		    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {//When player right click the item on hand
		        if (worldIn.isRemote) {//Create GUI
		            playerIn.openGui(Main.instance, 0, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
		        }
		        return itemStackIn;
		    }
		}.setUnlocalizedName("thermometer").setCreativeTab(CreativeTabs.tabMisc), "thermometer");
		
		GameRegistry.registerItem(winterCap = new ItemModCoat("winter_cap", Coat, 1, 0), "winter_cap");//Register
		GameRegistry.registerItem(coatCoat = new ItemModCoat("coat_coat", Coat, 1, 1), "coat_coat");//Register
		GameRegistry.registerItem(coatBoots = new ItemModCoat("coat_boots", Coat, 1, 3), "coat_boots");//Register
	}
}