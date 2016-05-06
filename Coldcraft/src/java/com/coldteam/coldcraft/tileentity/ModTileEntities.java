package com.coldteam.coldcraft.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;
//Register all tile entities
public final class ModTileEntities {
	//Register initialization
	public static void init() {
		GameRegistry.registerTileEntity(ModTileEntity.class, "camp_fire");
	}
}
