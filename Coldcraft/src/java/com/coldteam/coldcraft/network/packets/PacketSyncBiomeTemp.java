package com.coldteam.coldcraft.network.packets;

import com.coldteam.coldcraft.entitydata.PlayerData;

import io.netty.buffer.ByteBuf;
import minersbasic.api.network.MessageHandler;
import minersbasic.api.utils.ClientUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncBiomeTemp implements IMessage {

	private float biomeTemp;

    public PacketSyncBiomeTemp() {}

    public PacketSyncBiomeTemp(float biome) {
        this.biomeTemp = biome;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.biomeTemp = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(this.biomeTemp);
    }

    // ========================================================================

    public static class Handler extends MessageHandler.Client<PacketSyncBiomeTemp> {

        @Override
        public IMessage handleClientMessage(final EntityPlayer player, final PacketSyncBiomeTemp msg, MessageContext ctx) {
            ClientUtils.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    PlayerData.get(player).setBiomeTemp(msg.biomeTemp);
                }
            });
            return null;
        }
    }
}
