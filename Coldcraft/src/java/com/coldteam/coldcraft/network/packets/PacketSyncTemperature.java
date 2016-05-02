package com.coldteam.coldcraft.network.packets;

import com.coldteam.coldcraft.entitydata.PlayerData;

import io.netty.buffer.ByteBuf;
import minersbasic.api.network.MessageHandler;
import minersbasic.api.utils.ClientUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncTemperature implements IMessage {

	private double temperature;
	private double generalTemp;
	/*private float biomeTemp;
	private boolean camping;*/

    public PacketSyncTemperature() {}

    public PacketSyncTemperature(double temp, double general/*, float biome, boolean camp*/) {
        this.temperature = temp;
        this.generalTemp = general;
        /*this.biomeTemp = biome;
        this.camping = camp;*/
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.temperature = buf.readDouble();
        this.generalTemp = buf.readDouble();/*
        this.biomeTemp = buf.readFloat();
        this.camping = buf.readBoolean();*/

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.temperature);
        buf.writeDouble(this.generalTemp);/*
        buf.writeFloat(this.biomeTemp);
        buf.writeBoolean(this.camping);*/
    }

    // ========================================================================

    public static class Handler extends MessageHandler.Client<PacketSyncTemperature> {

        @Override
        public IMessage handleClientMessage(final EntityPlayer player, final PacketSyncTemperature msg, MessageContext ctx) {
            ClientUtils.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    PlayerData.get(player).setTemperature(msg.temperature);
                    PlayerData.get(player).setGeneralTemp(msg.generalTemp);
                    /*PlayerData.get(player).setBiomeTemp(msg.biomeTemp);
                    PlayerData.get(player).setCamp(msg.camping);*/
                }
            });
            return null;
        }
    }
}
