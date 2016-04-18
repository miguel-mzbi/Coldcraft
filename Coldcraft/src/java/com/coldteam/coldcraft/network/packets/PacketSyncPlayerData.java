package com.coldteam.coldcraft.network.packets;

import com.coldteam.coldcraft.entitydata.PlayerData;

import io.netty.buffer.ByteBuf;
import minersbasic.api.network.MessageHandler;
import minersbasic.api.utils.ClientUtils;
import minersbasic.api.utils.ServerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncPlayerData implements IMessage {

    private NBTTagCompound data;

    public PacketSyncPlayerData () {}

    public PacketSyncPlayerData(PlayerData playerData) {
        this.data = new NBTTagCompound();
        playerData.saveNBTData(this.data);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.data);
    }

    // ===================================Handling the Handlers

    public static class Handler extends MessageHandler.Bidirectional<PacketSyncPlayerData> {

        @Override
        public IMessage handleClientMessage(final EntityPlayer player, final PacketSyncPlayerData msg, MessageContext ctx) {
            ClientUtils.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    PlayerData.get(player).loadNBTData(msg.data);
                }
            });
            return null;
        }

        @Override
        public IMessage handleServerMessage(final EntityPlayer player, PacketSyncPlayerData msg, MessageContext ctx) {
            ServerUtils.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    PlayerData.get(player).syncAll();
                }
            });
            return null;
        }
    }
}
