package com.coldteam.coldcraft.network.packets;

import com.coldteam.coldcraft.entitydata.PlayerData;

import io.netty.buffer.ByteBuf;
import minersbasic.api.network.MessageHandler;
import minersbasic.api.utils.ClientUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Handle messages from server to client
public class PacketSyncBiomeTemp implements IMessage {

	private float biomeTemp;//Content of message: Biome Temperature

    public PacketSyncBiomeTemp() {}
    //Setter
    public PacketSyncBiomeTemp(float biome) {
        this.biomeTemp = biome;
    }
    //Load data
    @Override
    public void fromBytes(ByteBuf buf) {
        this.biomeTemp = buf.readFloat();
    }
    //Save data
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(this.biomeTemp);
    }

//Handler --------------------------------------------------------------------------

    public static class Handler extends MessageHandler.Client<PacketSyncBiomeTemp> {
    	//What do do when message is transmitted
        @Override
        public IMessage handleClientMessage(final EntityPlayer player, final PacketSyncBiomeTemp msg, MessageContext ctx) {
            ClientUtils.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    PlayerData.get(player).setBiomeTemp(msg.biomeTemp);//Set on SP
                }
            });
            return null;
        }
    }
}
