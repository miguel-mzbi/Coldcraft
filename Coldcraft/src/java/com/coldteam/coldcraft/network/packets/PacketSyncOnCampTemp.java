package com.coldteam.coldcraft.network.packets;

import com.coldteam.coldcraft.entitydata.PlayerData;

import io.netty.buffer.ByteBuf;
import minersbasic.api.network.MessageHandler;
import minersbasic.api.utils.ClientUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Handle messages from server to client
public class PacketSyncOnCampTemp implements IMessage {

	private double onCampTemp;//Content of message: Campfire Temperature

    public PacketSyncOnCampTemp() {}
    //Setter
    public PacketSyncOnCampTemp(double camp) {
        this.onCampTemp = camp;
    }
    //Load data
    @Override
    public void fromBytes(ByteBuf buf) {
        this.onCampTemp = buf.readDouble();
    }
    //Save data
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.onCampTemp);
    }

//Handler -------------------------------------------------------------------------------

    public static class Handler extends MessageHandler.Client<PacketSyncOnCampTemp> {
    	//What do do when message is transmitted
        @Override
        public IMessage handleClientMessage(final EntityPlayer player, final PacketSyncOnCampTemp msg, MessageContext ctx) {
            ClientUtils.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    PlayerData.get(player).setOnCampTemp(msg.onCampTemp);//Set on SP
                }
            });
            return null;
        }
    }
}
