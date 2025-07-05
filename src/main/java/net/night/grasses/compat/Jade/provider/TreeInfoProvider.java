package net.night.grasses.compat.Jade.provider;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.night.grasses.Grasses;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.item.AutomaticPrunerItem;
import net.night.grasses.item.DyeingTool;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

import static net.minecraft.tags.BlockTags.*;
import static net.night.grasses.event.ModEvents.*;
import static net.night.grasses.util.ModTags.Blocks.STEMS;

public class TreeInfoProvider implements IBlockComponentProvider {

    public static final TreeInfoProvider LOG_INSTANCE = new TreeInfoProvider();
    private static final ResourceLocation UID = new ResourceLocation(Grasses.MOD_ID, "size_plugin");

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {

        ItemStack itemStack = blockAccessor.getPlayer().getMainHandItem();
        boolean hasSilkTouch = EnchantmentHelper.hasSilkTouch(itemStack);

        if (logInfoForWAILA != null && logInfoForWAILA.getValue() !=0 && (itemStack.getItem() instanceof AxeItem && hasSilkTouch || itemStack.getItem() instanceof AutomaticPrunerItem || itemStack.getItem() instanceof DyeingTool)) {
            BlockState blockStateTarget = logInfoForWAILA.getKey().defaultBlockState();
            int maxCount = 0;
            int maxSize = 0;

            if (blockStateTarget.is(STEMS)) {
                maxCount = GrassesConfig.CommonConfig.STEMS_MAX_AMOUNT_AT_ONCE.get();
                maxSize = GrassesConfig.CommonConfig.STEMS_MAX_AMOUNT_AT_ONCE.get() <= 30 ? 60 : GrassesConfig.CommonConfig.STEMS_MAX_AMOUNT_AT_ONCE.get()*2;
            }
            else if (blockStateTarget.is(LOGS_THAT_BURN)) {
                maxCount = GrassesConfig.CommonConfig.LOGS_MAX_AMOUNT_AT_ONCE.get();
                maxSize = GrassesConfig.CommonConfig.LOGS_MAX_AMOUNT_AT_ONCE.get() <= 150 ? 200 : GrassesConfig.CommonConfig.LOGS_MAX_AMOUNT_AT_ONCE.get();
            }

            IElement icon = IElementHelper.get().item(new ItemStack(blockAccessor.getBlock()), 0.5f).size(new Vec2(10, 10)).translate(new Vec2(0, -1));
            icon.message(null);
            iTooltip.add(icon);

            if (logInfoForWAILA.getValue() > maxSize)
                iTooltip.append(Component.translatable("grasses.log_info_plus", maxSize)
                        .withStyle(ChatFormatting.BLUE));
            else
                iTooltip.append(Component.translatable("grasses.log_info", logInfoForWAILA.getValue())
                        .withStyle(ChatFormatting.BLUE));

            if (logInfoForWAILA.getValue() > maxCount)
                iTooltip.append(Component.translatable("grasses.max_info", maxCount)
                        .withStyle(ChatFormatting.BLUE));

            if (crownInfoForWAILA.getValue() != 0) {
                icon = IElementHelper.get().item(new ItemStack(crownInfoForWAILA.getKey()), 0.5f).size(new Vec2(10, 10)).translate(new Vec2(0, -1));
                icon.message(null);
                iTooltip.add(icon);
                String info;
                info = crownInfoForWAILA.getKey().defaultBlockState().is(LEAVES) ? "grasses.leaves_info" : "grasses.wart_info";
                iTooltip.append(Component.translatable(info, crownInfoForWAILA.getValue())
                        .withStyle(ChatFormatting.BLUE));
            }
            if (fungusSLInfoForWAILA.getValue() != 0) {
                icon = IElementHelper.get().item(new ItemStack(fungusSLInfoForWAILA.getKey()), 0.5f).size(new Vec2(10, 10)).translate(new Vec2(0, -1));
                icon.message(null);
                iTooltip.add(icon);
                iTooltip.append(Component.translatable("grasses.shroomlight_info", fungusSLInfoForWAILA.getValue())
                        .withStyle(ChatFormatting.BLUE));
            }
            if (vineInfoForWAILA.getValue() != 0) {
                icon = IElementHelper.get().item(new ItemStack(vineInfoForWAILA.getKey()), 0.5f).size(new Vec2(10, 10)).translate(new Vec2(0, -1));
                icon.message(null);
                iTooltip.add(icon);
                iTooltip.append(Component.translatable("grasses.vine_info", vineInfoForWAILA.getValue())
                        .withStyle(ChatFormatting.BLUE));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}
