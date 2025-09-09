package net.night.grasses.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.night.grasses.block.blockEntity.TintedBlockEntity;

import java.util.function.Supplier;

public class SyncColorPacket
{
    private final BlockPos pos;
    private final int color;

    public SyncColorPacket(BlockPos pos, int color)
    {
        this.pos = pos;
        this.color = color;
    }

    public SyncColorPacket(FriendlyByteBuf friendlyByteBuf)
    {
        this(friendlyByteBuf.readBlockPos(), friendlyByteBuf.readInt());
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(this.pos);
        buffer.writeInt(this.color);
    }

    public SyncColorPacket decode(FriendlyByteBuf buffer)
    {
        return new SyncColorPacket(buffer.readBlockPos(), buffer.readInt());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            ClientLevel level = mc.level;
            if (level != null)
            {
                BlockEntity be = level.getBlockEntity(this.pos);
                if (be instanceof TintedBlockEntity blockEntity)
                    blockEntity.setColorType(this.color); // Apply new color
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
