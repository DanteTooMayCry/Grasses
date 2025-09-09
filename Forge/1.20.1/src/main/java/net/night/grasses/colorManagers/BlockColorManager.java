package net.night.grasses.colorManagers;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.enums.ColorType;

import static net.night.grasses.data.ModMethods.getColorTypeForColorManager;
import static net.night.grasses.init.BlocksRegister.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockColorManager extends BlockColors {

    private static BlockColor registerBlockColor() {
        BlockColor toReturn;

        toReturn = (blockState, BlockAndTintGetter, blockPos, index) -> {

            int colorInt;
            if (blockPos == null)
                colorInt = 0;
            else
                colorInt = getColorTypeForColorManager(blockPos);

            return colorInt;
        };
        return toReturn;
    }

    @SubscribeEvent
    public static BlockColorManager createDefault(RegisterColorHandlersEvent.Item event) {
        final BlockColors blockColors = event.getBlockColors();
        final ItemColors itemColors = event.getItemColors();

        BlockColor blockColor = registerBlockColor();

        BlockColorManager blockColorManager = new BlockColorManager();

        for (RegistryObject<Block> blockRegistryObject : allLeavesRegistryBlocksList)
            blockColors.register(blockColor, blockRegistryObject.get());

        for (RegistryObject<Block> blockRegistryObject : plantRegistryBlockList)
            blockColors.register(blockColor, blockRegistryObject.get());

        for (RegistryObject<Block> blockRegistryObject : pottedTintedRegistryPlantList)

            blockColors.register(blockColor, blockRegistryObject.get());

        for (RegistryObject<Block> blockRegistryObject : plantInBarsTintedRegistryBlockList)
            blockColors.register(blockColor, blockRegistryObject.get());


        ItemColor itemBlockColourHandler = (itemStack, tintIndex) ->
        {
            String name = "PLAINS";

            CompoundTag compoundtag = itemStack.getTag();
            if (compoundtag != null) {
                name = compoundtag.getCompound("BlockStateTag").getString("color_type").toUpperCase();
                if (name.isBlank())
                    name = "PLAINS";
            }

            return tintIndex > 0 ? -1 : ColorsDefinition.takeColor(ColorType.valueOf(name), null, itemStack);
        };

        for(RegistryObject<Block> leavesBlock : allLeavesRegistryBlocksList)
            itemColors.register(itemBlockColourHandler, leavesBlock.get());

        for(RegistryObject<Block> plantBlock : plantRegistryBlockList)
            itemColors.register(itemBlockColourHandler, plantBlock.get());

        for(RegistryObject<Block> pottedPlantBlock : pottedTintedRegistryPlantList)
            itemColors.register(itemBlockColourHandler, pottedPlantBlock.get());

        for(RegistryObject<Block> plantInBarsBlock : plantInBarsTintedRegistryBlockList)
            itemColors.register(itemBlockColourHandler, plantInBarsBlock.get());

        itemColors.register(itemBlockColourHandler, VINE_IN_BARS.get());

        return blockColorManager;
    }


    @SubscribeEvent
    public static void registerBlockColourHandlers(final RegisterColorHandlersEvent.Block event) {

        final BlockColor grassColourHandler = (state, blockAccess, pos, tintIndex) -> {
            if (blockAccess != null && pos != null) {
                return BiomeColors.getAverageFoliageColor(blockAccess, pos);
            }

            return FoliageColor.get(0.5d, 1.0d);
        };

        event.register(grassColourHandler, OAK_LEAVES_POTTED.get());
        event.register(grassColourHandler, JUNGLE_LEAVES_POTTED.get());
        event.register(grassColourHandler, ACACIA_LEAVES_POTTED.get());
        event.register(grassColourHandler, DARK_OAK_LEAVES_POTTED.get());
        event.register(grassColourHandler, MANGROVE_LEAVES_POTTED.get());
        event.register(registerOriginBlockColor(Integer.decode("#80a755")), BIRCH_LEAVES_POTTED.get());
        event.register(registerOriginBlockColor(Integer.decode("#619961")), SPRUCE_LEAVES_POTTED.get());

        event.register(grassColourHandler, VINE_POTTED.get());
        event.register(grassColourHandler, VINE_IN_BARS.get());

    }

    private static BlockColor registerOriginBlockColor(int integer) {

        return (blockState, BlockAndTintGetter, blockPos, index) -> integer;
    }
}
