package net.night.grasses.mixins;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.TridentChannelingEnchantment;
import net.night.grasses.item.AutomaticPrunerItem;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(TridentChannelingEnchantment.class)
public abstract class ChannelingEnchantmentMixin extends Enchantment {

    protected ChannelingEnchantmentMixin(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return itemStack.getItem() instanceof AutomaticPrunerItem || super.canEnchant(itemStack);
    }
}
