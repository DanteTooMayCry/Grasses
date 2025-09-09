package net.night.grasses.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.night.grasses.Grasses;
import net.night.grasses.block.blockEntity.TintedBlockEntity;

public record SyncColorPayload(BlockPos pos, int color) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(Grasses.MOD_ID, "sync_color");

    public SyncColorPayload(FriendlyByteBuf byteBuf)
    {
        this(byteBuf.readBlockPos(), byteBuf.readInt());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }


    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(this.pos);
        friendlyByteBuf.writeInt(this.color);
    }

    public void handle(PlayPayloadContext context) {

        context.workHandler().execute(() -> {
            Minecraft mc = Minecraft.getInstance();
            ClientLevel level = mc.level;
            if (level != null)
            {
                BlockEntity be = level.getBlockEntity(this.pos);
                if (be instanceof TintedBlockEntity blockEntity)
                    blockEntity.setColorType(this.color); // Apply new color
            }
        });
    }
}
