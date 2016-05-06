package com.coldteam.coldcraft.network.packets;

import com.coldteam.coldcraft.entitydata.PlayerData;

import io.netty.buffer.ByteBuf;
import minersbasic.api.network.MessageHandler;
import minersbasic.api.utils.ClientUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Handle messages from server to client
public class PacketSyncTemperature implements IMessage {

	private double temperature;//Content of message: Temperature
	private double generalTemp;//Content of message: Temperature considering only biome

    public PacketSyncTemperature() {}
    //Setter
    public PacketSyncTemperature(double temp, double general) {
        this.temperature = temp;
        this.generalTemp = general;
    }
    //Load data
    @Override
    public void fromBytes(ByteBuf buf) {
        this.temperature = buf.readDouble();
        this.generalTemp = buf.readDouble();
    }
    //Save data
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.temperature);
        buf.writeDouble(this.generalTemp);
    }

//Handler ---------------------------------------------------------------------------------

    public static class Handler extends MessageHandler.Client<PacketSyncTemperature> {
    	//What do do when message is transmitted
        @Override
        public IMessage handleClientMessage(final EntityPlayer player, final PacketSyncTemperature msg, MessageContext ctx) {
            ClientUtils.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    PlayerData.get(player).setTemperature(msg.temperature);//Set on SP
                    PlayerData.get(player).setGeneralTemp(msg.generalTemp);//Set on SP
                }
            });
            return null;
        }
    }
}
