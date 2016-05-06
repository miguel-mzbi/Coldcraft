package com.coldteam.coldcraft;

import com.coldteam.coldcraft.client.render.blocks.BlockRenderRegister;
import com.coldteam.coldcraft.client.render.items.ItemRenderRegister;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
//Methods and constructors needed only by the client
public class ClientProxy extends CommonProxy {
	//Basic first things to load. 
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }
	//Other things to load
    @Override
    public void init(FMLInitializationEvent e) {
    	super.init(e);
        BlockRenderRegister.registerBlockRenderer();//Load block textures
        ItemRenderRegister.registerItemRenderer();//Load item textures
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

}
