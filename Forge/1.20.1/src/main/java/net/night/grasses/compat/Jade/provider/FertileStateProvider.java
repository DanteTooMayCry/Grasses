package net.night.grasses.compat.Jade.provider;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.night.grasses.Grasses;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

import static net.night.grasses.init.BlocksRegister.FERTILE;
import static net.night.grasses.init.ItemsRegister.FERTILE_ICON;

public class FertileStateProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    public static final FertileStateProvider FERTILE_INSTANCE = new FertileStateProvider();
    private static final ResourceLocation UID = new ResourceLocation(Grasses.MOD_ID, "fertile_plugin");


    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {

        IElement icon = IElementHelper.get().item(new ItemStack(FERTILE_ICON.get()), 0.5f).size(new Vec2(10, 10)).translate(new Vec2(0, -1));
        icon.message(null);
        iTooltip.add(icon);
        if (blockAccessor.getBlockState().getValue(FERTILE))
            iTooltip.append(Component.translatable("grasses.fertile_state_true", blockAccessor.getBlockState().getValue(FERTILE))
                    .withStyle(ChatFormatting.GREEN));
        else
            iTooltip.append(Component.translatable("grasses.fertile_state_false", blockAccessor.getBlockState().getValue(FERTILE))
                    .withStyle(ChatFormatting.DARK_RED));

    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {

    }
}
