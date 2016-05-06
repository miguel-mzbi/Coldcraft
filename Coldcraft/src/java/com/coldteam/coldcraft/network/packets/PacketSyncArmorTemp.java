package com.coldteam.coldcraft.network.packets;

import com.coldteam.coldcraft.entitydata.PlayerData;

import io.netty.buffer.ByteBuf;
import minersbasic.api.network.MessageHandler;
import minersbasic.api.utils.ClientUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Handle messages from server to client
public class PacketSyncArmorTemp implements IMessage {

	private double armorTemp;//Content of message: Armor Temperature

    public PacketSyncArmorTemp() {}
    //Setter
    public PacketSyncArmorTemp(double camp) {
        this.armorTemp = camp;
    }
    //Load data
    @Override
    public void fromBytes(ByteBuf buf) {
        this.armorTemp = buf.readDouble();
    }
    //Save data
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.armorTemp);
    }

//Handler -----------------------------------------------------

    public static class Handler extends MessageHandler.Client<PacketSyncArmorTemp> {
    	//What do do when message is transmitted
        @Override
        public IMessage handleClientMessage(final EntityPlayer player, final PacketSyncArmorTemp msg, MessageContext ctx) {
            ClientUtils.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    PlayerData.get(player).setArmorTemp(msg.armorTemp);//Set on SP
                }
            });
            return null;
        }
    }
}
