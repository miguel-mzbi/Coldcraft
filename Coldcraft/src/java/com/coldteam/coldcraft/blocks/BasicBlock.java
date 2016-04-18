package com.coldteam.coldcraft.blocks;

import com.coldteam.coldcraft.tileentity.ModTileEntity;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public class BasicBlock extends BlockContainer {

    public BasicBlock(String name, Material material, float hardness, float resistance) {
        super(material);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setUnlocalizedName(name);
        this.setHardness(hardness);
        this.setResistance(resistance);
        }
    
    @Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new ModTileEntity();
	}

	@Override
	public int getRenderType() {
		return 3;
	}
}
