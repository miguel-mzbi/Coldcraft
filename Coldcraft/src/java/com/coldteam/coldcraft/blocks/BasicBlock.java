package com.coldteam.coldcraft.blocks;

import com.coldteam.coldcraft.tileentity.ModTileEntity;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

//General constructor for basic blocks
public class BasicBlock extends BlockContainer {

    public BasicBlock(String name, Material material, float hardness, float resistance) {
        super(material);
        this.setCreativeTab(CreativeTabs.tabBlock); //Where it is displayed on the Creative Inventory
        this.setUnlocalizedName(name);//System name of the block
        this.setHardness(hardness);//Tool needed to break
        this.setResistance(resistance);//Resistance to explosions
        }
    
    //Create tiles from this blocks
    @Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new ModTileEntity();
	}
    //Rendering type statement
	@Override
	public int getRenderType() {
		return 3;
	}
}
