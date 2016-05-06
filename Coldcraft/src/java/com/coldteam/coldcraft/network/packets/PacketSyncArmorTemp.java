package com.coldteam.coldcraft.network.packets;

import com.coldteam.coldcraft.entitydata.PlayerData;

import io.netty.buffer.ByteBuf;
import minersbasic.api.network.MessageHandler;
import minersbasic.api.utils.ClientUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncArmorTemp implements IMessage {

	private double armorTemp;

    public PacketSyncArmorTemp() {}

    public PacketSyncArmorTemp(double camp) {
        this.armorTemp = camp;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.armorTemp = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.armorTemp);
    }

    // ========================================================================

    public static class Handler extends MessageHandler.Client<PacketSyncArmorTemp> {

        @Override
        public IMessage handleClientMessage(final EntityPlayer player, final PacketSyncArmorTemp msg, MessageContext ctx) {
            ClientUtils.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    PlayerData.get(player).setArmorTemp(msg.armorTemp);
                }
            });
            return null;
        }
    }
}

