package com.coldteam.coldcraft.network.packets;

import com.coldteam.coldcraft.entitydata.PlayerData;

import io.netty.buffer.ByteBuf;
import minersbasic.api.network.MessageHandler;
import minersbasic.api.utils.ClientUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncTemperature implements IMessage {

    private double temperature;
    private int entityID;

    public PacketSyncTemperature() {}

    public PacketSyncTemperature(EntityPlayer entity, double temperature) {
        this.entityID = entity.getEntityId();
        this.temperature = temperature;

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityID = buf.readInt();
        this.temperature = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeDouble(this.temperature);
    }

    // ========================================================================

    public static class Handler extends MessageHandler.Client<PacketSyncTemperature> {

        @Override
        public IMessage handleClientMessage(final EntityPlayer player, final PacketSyncTemperature msg, MessageContext ctx) {
            ClientUtils.addScheduledTask(new Runnable() {

				@Override
                public void run() {
                    Entity thePlayer = player.worldObj.getEntityByID(msg.entityID);
                    if (thePlayer != null && thePlayer instanceof EntityPlayer)
                        PlayerData.get((EntityPlayer) thePlayer).setTemperature(msg.temperature);
            	}
            });
            return null;
        }
    }
}
