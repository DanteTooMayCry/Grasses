package net.night.grasses.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.night.grasses.config.GrassesConfig;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static net.night.grasses.data.MethodsLib.additionalHoverText;
import static net.night.grasses.data.MethodsLib.getColorTypeAndShowOnActionBar;

public class DyeingTool extends Item {
    public DyeingTool(Properties pProperties) {
        super(pProperties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, tooltip, tooltipFlag);
        additionalHoverText(itemStack, tooltip);
    }

    @Override
    public Component getName(ItemStack itemStack) {

        getColorTypeAndShowOnActionBar(itemStack);
        return super.getName(itemStack);
    }

    @Override
    public int getEnchantmentValue() {
        return 20;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant) {
        return enchant == Enchantments.UNBREAKING
                || enchant == Enchantments.CHANNELING
                || enchant == Enchantments.FIRE_ASPECT;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        AtomicBoolean allowEnchant = new AtomicBoolean();
        EnchantmentHelper.getEnchantments(book).forEach((enchantment, integer) -> {
            if (Objects.equals(Enchantments.CHANNELING, enchantment) ||
                    Objects.equals(Enchantments.UNBREAKING, enchantment) ||
                    Objects.equals(Enchantments.VANISHING_CURSE, enchantment) ||
                    Objects.equals(Enchantments.FIRE_ASPECT, enchantment) ||
                    (Objects.equals(Enchantments.INFINITY_ARROWS, enchantment) && GrassesConfig.CommonConfig.ALLOW_APPLY_INFINITY_ON_DYEING_TOOL.get())

            ) {
                allowEnchant.set(true);
            }
        });

        return allowEnchant.get();
    }
}
