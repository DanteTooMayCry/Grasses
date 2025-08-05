package net.night.grasses.block.blockItems;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import static net.night.grasses.data.ModMethods.getColorTypeAndShowOnActionBar;

public class ModBlockItem extends BlockItem {
    public ModBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public Component getName(ItemStack itemStack) {

        getColorTypeAndShowOnActionBar(itemStack);

        return super.getName(itemStack);
    }
}
