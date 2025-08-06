package net.night.grasses.compat.Jade.provider;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.night.grasses.Grasses;
import net.night.grasses.block.leaves.TintedJungleLeavesBlock;
import net.night.grasses.block.leaves.superclasses.ParentTintedLeavesBlock;
import net.night.grasses.enums.ColorType;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

import static net.minecraft.world.item.Items.YELLOW_DYE;
import static net.night.grasses.data.ModMethods.getColorType;
import static net.night.grasses.init.BlocksRegister.ALTER;
import static net.night.grasses.init.BlocksRegister.DOUBLE_ALTER;

public class ColorStateProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    public static final ColorStateProvider COLOR_INSTANCE = new ColorStateProvider();
    private static final ResourceLocation UID = new ResourceLocation(Grasses.MOD_ID, "color_plugin");

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {

        IElement icon = IElementHelper.get().item(new ItemStack(YELLOW_DYE), 0.5f).size(new Vec2(10, 10)).translate(new Vec2(0, -1));

        String pKey = "grasses.color_state_";
        String pKeyAlter = "grasses.description_of_alter";
        String pKeyLight = "grasses.description_of_light";

        BlockPos blockPos = blockAccessor.getPosition();
        BlockState blockState = blockAccessor.getBlockState();
        ColorType colorType = getColorType(blockPos);

        icon.message(null);
        if (blockState.getBlock() instanceof TintedJungleLeavesBlock && (blockState.getValue(DOUBLE_ALTER).equals(2) || blockState.getValue(DOUBLE_ALTER).equals(3))) {
            iTooltip.append(Component.translatable(pKeyAlter).withStyle(ChatFormatting.WHITE));
        }
        iTooltip.add(icon);
        if (colorType != null) {
            if (blockState.getBlock() instanceof ParentTintedLeavesBlock && !(blockState.getBlock() instanceof TintedJungleLeavesBlock) && blockState.getValue(ALTER).equals(Boolean.TRUE))
                iTooltip.append(Component.translatable(pKey.concat(colorType.toString()), colorType.toString()).append(Component.translatable(pKeyLight)).withStyle(ChatFormatting.BLUE));
            else if (blockState.getBlock() instanceof TintedJungleLeavesBlock && (blockState.getValue(DOUBLE_ALTER).equals(1) || blockState.getValue(DOUBLE_ALTER).equals(3)))
                iTooltip.append(Component.translatable(pKey.concat(colorType.toString()), colorType.toString()).append(Component.translatable(pKeyLight)).withStyle(ChatFormatting.BLUE));
            else
                iTooltip.append(Component.translatable(pKey.concat(colorType.toString()), colorType.toString()).withStyle(ChatFormatting.BLUE));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {

    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}
