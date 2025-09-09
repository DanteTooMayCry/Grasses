package net.night.grasses.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.night.grasses.Grasses;

public final class MessageRegistry
{

    public static SimpleChannel INSTANCE;

    public static void register(String name) {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Grasses.MOD_ID, name), //
                () -> "1.0", //
                s -> true, //
                s -> true);

        INSTANCE.messageBuilder(SyncColorPacket.class, nextid(), NetworkDirection.PLAY_TO_CLIENT) //
                .decoder(SyncColorPacket::new) //
                .encoder(SyncColorPacket::encode) //
                .consumerMainThread(SyncColorPacket::handle) //
                .add();
    }

    public static <MSG> void sendToServer(MSG message)
    {
        var minecraft = Minecraft.getInstance();
        if (minecraft.level != null && minecraft.level.isClientSide)
            INSTANCE.sendToServer(message);
    }

    private static int packetId = 0;

    private static int nextid()
    {
        return packetId++;
    }

    public static void sendToClient(ServerPlayer player, Object packet)
    {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendToClientsNear(BlockPos pos, ServerLevel level, Object packet)
    {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), packet);
    }

    public static void sendToAllClients(Object packet) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }
}
