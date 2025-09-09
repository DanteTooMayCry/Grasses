package net.night.grasses.colorManagers;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.init.BlocksRegister;

import java.util.ArrayList;
import java.util.List;

import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegister.SUGAR_CANE_POTTED;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GrassBlockColorManager {

    public static final List<BlockColor> grassBlockColorList = new ArrayList<>();

    private static BlockColor registerBlockColor(String color) {
        BlockColor toReturn;
        toReturn = (blockState, BlockAndTintGetter, blockPos, i) -> Integer.decode(color);
        grassBlockColorList.add(toReturn);
        return toReturn;
    }

    @SubscribeEvent
    public static void onItemColorsInit(RegisterColorHandlersEvent.Item event) {
        final BlockColors blockColors = event.getBlockColors();
        final ItemColors itemColors = event.getItemColors();

        BlockColor BadlandsGrassBlock       = registerBlockColor("#90814d");
        BlockColor BirchGrassBlock          = registerBlockColor("#88bb67");
        BlockColor CherryGroveGrassBlock    = registerBlockColor("#b6db61");
        BlockColor DarkGrassBlock           = registerBlockColor("#507a32");
        BlockColor DesertGrassBlock         = registerBlockColor("#bfb755");
        BlockColor DripstoneCaveGrassBlock  = registerBlockColor("#8bd58a");
        BlockColor ForestGrassBlock         = registerBlockColor("#79c05a");
        BlockColor JungleGrassBlock         = registerBlockColor("#59c93c");
        BlockColor LushGrassBlock           = registerBlockColor("#b9b75b");
        BlockColor MangroveGrassBlock       = registerBlockColor("#4c763c");
        BlockColor MeadowGrassBlock         = registerBlockColor("#83bb6d");
        BlockColor MushroomGrassBlock       = registerBlockColor("#55c93f");
        BlockColor OceanGrassBlock          = registerBlockColor("#8eb971");
        BlockColor PlainsGrassBlock         = registerBlockColor("#91bd59");
        BlockColor PineTaigaGrassBlock      = registerBlockColor("#86b87f");
        BlockColor SnowyBeachGrassBlock     = registerBlockColor("#83b593");
        BlockColor SnowyPlainsGrassBlock    = registerBlockColor("#80b497");
        BlockColor SparseJungleGrassBlock   = registerBlockColor("#64c73f");
        BlockColor StonyPeaksGrassBlock     = registerBlockColor("#9abe4b");
        BlockColor SwampGrassBlock          = registerBlockColor("#6a7039");
        BlockColor ColdSwampGrassBlock      = registerBlockColor("#4c763c");
        BlockColor TaigaGrassBlock          = registerBlockColor("#86b783");
        BlockColor WindsweptGrassBlock      = registerBlockColor("#8ab689");
        BlockColor WhiteGrassBlock          = registerBlockColor("#F9FFFE");
        BlockColor RedGrassBlock            = registerBlockColor("#B02E26");
        BlockColor OrangeGrassBlock         = registerBlockColor("#F9801D");
        BlockColor PinkGrassBlock           = registerBlockColor("#F38BAA");
        BlockColor YellowGrassBlock         = registerBlockColor("#FED83D");
        BlockColor LimeGrassBlock           = registerBlockColor("#80C71F");
        BlockColor GreenGrassBlock          = registerBlockColor("#5E7C16");
        BlockColor LightBlueGrassBlock      = registerBlockColor("#3AB3DA");
        BlockColor CyanGrassBlock           = registerBlockColor("#169C9C");
        BlockColor BlueGrassBlock           = registerBlockColor("#3C44AA");
        BlockColor MagentaGrassBlock        = registerBlockColor("#C74EBD");
        BlockColor PurpleGrassBlock         = registerBlockColor("#8932B8");
        BlockColor BrownGrassBlock          = registerBlockColor("#835432");
        BlockColor GrayGrassBlock           = registerBlockColor("#474F52");
        BlockColor LightGrayGrassBlock      = registerBlockColor("#9D9D97");
        BlockColor BlackGrassBlock          = registerBlockColor("#1D1D21");
        BlockColor QuartzGrassBlock         = registerBlockColor("#E3D4D1");
        BlockColor CopperGrassBlock         = registerBlockColor("#B4684D");
        BlockColor IronGrassBlock           = registerBlockColor("#CECACA");
        BlockColor GoldGrassBlock           = registerBlockColor("#DEB12D");
        BlockColor DiamondGrassBlock        = registerBlockColor("#2CBAA8");
        BlockColor EmeraldGrassBlock        = registerBlockColor("#47A036");
        BlockColor NetheriteGrassBlock      = registerBlockColor("#443A3B");
        BlockColor RedstoneGrassBlock       = registerBlockColor("#971607");
        BlockColor AmethystGrassBlock       = registerBlockColor("#9A5CC6");
        BlockColor LapisGrassBlock          = registerBlockColor("#21497B");

        BlockColor LightLimeGrassBlock      = registerBlockColor("#00b300");
        BlockColor DarkGreenGrassBlock      = registerBlockColor("#013220");
        BlockColor XMasGrassBlock           = registerBlockColor("#006400");
        BlockColor MalachiteGrassBlock      = registerBlockColor("#006633");
        BlockColor GlassBottleGrassBlock    = registerBlockColor("#1f7e45");
        BlockColor LivingGreenGrassBlock    = registerBlockColor("#0d6245");
        BlockColor BetterCherryGrassBlock   = registerBlockColor("#5ccb7d");

        int i = 1; //skip grow_grass block
        for(BlockColor blockColor : grassBlockColorList){
            blockColors.register(blockColor, grassRegistryBlocksList.get(i).get(), grassRegistrySlabBlocksList.get(i).get());
            i++;
        }

        ItemColor itemBlockColourHandler = (itemStack, tintIndex) ->
        {
            BlockState blockState = ((BlockItem) itemStack.getItem()).getBlock().defaultBlockState();
            return tintIndex > 0 ? -1 : blockColors.getColor(blockState, null, null, tintIndex);
        };

        for(RegistryObject<Block> grassesBlock : grassRegistryBlocksList){
            itemColors.register(itemBlockColourHandler, grassesBlock.get());
        }
        for(RegistryObject<Block> grassesBlock : grassRegistrySlabBlocksList){
            itemColors.register(itemBlockColourHandler, grassesBlock.get());
        }

        itemColors.register(itemBlockColourHandler, GRASS_IN_BARS.get(), FERN_IN_BARS.get());
    }

    @SubscribeEvent
    public static void registerBlockColourHandlers(final RegisterColorHandlersEvent.Block event) {
        final BlockColor grassColourHandler = (state, blockAccess, pos, tintIndex) -> {
            if (blockAccess != null && pos != null) {
                return BiomeColors.getAverageGrassColor(blockAccess, pos);
            }

            return GrassColor.get(0.5d, 1.0d);
        };

        event.register(grassColourHandler, BlocksRegister.GROW_GRASS_BLOCK.get());
        event.register(grassColourHandler, BlocksRegister.GRASS_SLAB_BLOCK.get());
        event.register(grassColourHandler, GRASS_POTTED.get());
        event.register(grassColourHandler, SUGAR_CANE_POTTED.get());

        event.register(grassColourHandler, GRASS_IN_BARS.get());
        event.register(grassColourHandler, FERN_IN_BARS.get());
    }
}
