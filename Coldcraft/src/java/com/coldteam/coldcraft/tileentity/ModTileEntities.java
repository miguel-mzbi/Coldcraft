package com.coldteam.coldcraft.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModTileEntities {

	public static void init() {
		GameRegistry.registerTileEntity(ModTileEntity.class, "camp_fire");
	}

}
