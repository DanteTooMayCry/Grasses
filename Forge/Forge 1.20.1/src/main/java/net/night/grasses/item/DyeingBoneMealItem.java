package net.night.grasses.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.night.grasses.data.ModMethods.additionalHoverText;
import static net.night.grasses.data.ModMethods.getColorTypeAndShowOnActionBar;

public class DyeingBoneMealItem extends BoneMealItem {

    public DyeingBoneMealItem(Properties pProperties) {
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
}
