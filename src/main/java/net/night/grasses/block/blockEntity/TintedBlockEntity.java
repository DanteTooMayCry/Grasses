package net.night.grasses.block.blockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.night.grasses.network.MessageRegistry;
import net.night.grasses.network.SyncColorPacket;
import net.night.grasses.enums.ColorType;
import org.jetbrains.annotations.Nullable;

import static net.night.grasses.init.BlockEntitiesRegister.TINTED_BE;

public class TintedBlockEntity extends BlockEntity {

    public static BlockEntityType<TintedBlockEntity> type;
    private ColorType colorType;
    private int color;
    private String counterpart;

    public TintedBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(TINTED_BE.get(), pPos, pBlockState);
    }

    public ColorType getColorType()
    {
        return colorType;
    }

    public int getColorInt()
    {
        return color;
    }

    public String getCounterpart() {
        return counterpart;
    }

    public void setColorType(ColorType colorType, int color, String counterpart) {
        this.colorType = colorType;
        this.color = color;
        this.counterpart = counterpart;
        sendUpdate();
    }

    public void setColorType(ColorType colorType, int color) {
        this.colorType = colorType;
        this.color = color;
        sendUpdate();
    }

    public void setColorType(int color)
    {
        this.color = color;
        sendUpdate();
    }

    public void refresh() {
        if (level != null)
        {
            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, 2);
            level.setBlocksDirty(worldPosition, state, state);

            if (!level.isClientSide)
            {
                MessageRegistry.sendToClientsNear(worldPosition, (ServerLevel) level, new SyncColorPacket(worldPosition, this.color));
            }
        }
    }

    private void sendUpdate() {
        setChanged();
        refresh();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {

        super.saveAdditional(nbt);
        if (this.colorType != null) {
            CompoundTag colorType = new CompoundTag();
            colorType.putString("color_type", this.colorType.toString());
            colorType.putInt("color_int", this.color);
            if (this.counterpart != null){
                colorType.putString("counterpart", this.counterpart);
            }
            nbt.put("BlockStateTag", colorType);
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        CompoundTag blockStateTag = nbt.getCompound("BlockStateTag");
        if (!(blockStateTag.getString("color_type").toUpperCase()).isBlank()) {
            this.colorType = ColorType.valueOf(blockStateTag.getString("color_type").toUpperCase());
            this.color = blockStateTag.getInt("color_int");
        }
        if (!(blockStateTag.getString("counterpart").toUpperCase()).isBlank()) {
            this.counterpart = blockStateTag.getString("counterpart");
        }
        refresh();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithFullMetadata();
    }
}
