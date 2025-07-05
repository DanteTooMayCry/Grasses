package net.night.grasses.item;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static net.night.grasses.data.MethodsLib.setEnchantmentBoolean;

public class AutomaticPrunerItem extends ShearsItem {
    public AutomaticPrunerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack itemStack) {

        boolean hasSilk = EnchantmentHelper.hasSilkTouch(itemStack);
        boolean hasChanneling = EnchantmentHelper.hasChanneling(itemStack);

        MutableComponent enchantType = Component.translatable("grasses.silk_and_channeling");

        if (hasSilk && !hasChanneling)
            enchantType = Component.translatable("grasses.silk");
        else if (!hasSilk && hasChanneling)
            enchantType = Component.translatable("grasses.channeling");
        else if (!hasSilk && !hasChanneling)
            enchantType = Component.literal("");

        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.displayClientMessage(enchantType, true);

        return super.getName(itemStack);
    }

    @Override
    public void onInventoryTick(ItemStack itemStack, Level level, Player player, int slotIndex, int selectedIndex) {

        assert itemStack.getTag() != null;
        boolean flag = itemStack.getTag().getBoolean("Enchanted");
        boolean hasSilkTouch = EnchantmentHelper.hasSilkTouch(itemStack);

        if (flag != hasSilkTouch) {
            setEnchantmentBoolean(itemStack);
        }
        super.onInventoryTick(itemStack, level, player, slotIndex, selectedIndex);
    }

    @Override
    public int getEnchantmentValue() {
        return 20;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant) {
        return enchant == Enchantments.UNBREAKING
                || enchant == Enchantments.SILK_TOUCH
                || enchant == Enchantments.BLOCK_FORTUNE
                || enchant == Enchantments.CHANNELING
                || enchant == Enchantments.KNOCKBACK;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        AtomicBoolean allowEnchant = new AtomicBoolean();
        EnchantmentHelper.getEnchantments(book).forEach((enchantment, integer) -> {
            if (Objects.equals(Enchantments.UNBREAKING, enchantment) ||
                    Objects.equals(Enchantments.SILK_TOUCH, enchantment) ||
                    Objects.equals(Enchantments.BLOCK_FORTUNE, enchantment) ||
                    Objects.equals(Enchantments.CHANNELING, enchantment) ||
                    Objects.equals(Enchantments.MENDING, enchantment) ||
                    Objects.equals(Enchantments.VANISHING_CURSE, enchantment)

            ) {
                allowEnchant.set(true);
            }
        });

        return allowEnchant.get();
    }
}
