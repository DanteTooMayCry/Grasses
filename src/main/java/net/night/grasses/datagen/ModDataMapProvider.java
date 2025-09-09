package net.night.grasses.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.night.grasses.config.GrassesConfig;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.item.Items.ROTTEN_FLESH;
import static net.minecraft.world.level.block.Blocks.BAMBOO;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;

public class ModDataMapProvider extends DataMapProvider {
    Builder<Compostable, Item> compostable = this.builder(NeoForgeDataMaps.COMPOSTABLES);
    private final GrassesConfig.CommonConfig config;


    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, GrassesConfig.CommonConfig config) {
        super(packOutput, lookupProvider);
        this.config = config;
    }

    @Override
    protected void gather() {

        float f03 = 0.3F;
        float f05 = 0.5F;
        float f065 = 0.65F;
        float f085 = 0.85F;
        float f1 = 1.0F;

        addCompostable(ACACIA_LEAVES_BLOCK, f03);
        addCompostable(AZALEA_LEAVES_BLOCK, f03);
        addCompostable(BIRCH_LEAVES_BLOCK, f03);
        addCompostable(CHERRY_LEAVES_BLOCK, f03);
        addCompostable(DARK_OAK_LEAVES_BLOCK, f03);
        addCompostable(FLOWERING_AZALEA_LEAVES_BLOCK, f03);
        addCompostable(JUNGLE_LEAVES_BLOCK, f03);
        addCompostable(MANGROVE_LEAVES_BLOCK, f03);
        addCompostable(OAK_LEAVES_BLOCK, f03);
        addCompostable(SPRUCE_LEAVES_BLOCK, f03);
        addCompostable(SPRUCE_LEAVES_BLOCK, f03);

        addCompostable(GRASS_SHORT_TINTED, f03);
        addCompostable(FERN_TINTED, f065);
        addCompostable(SEAGRASS_TINTED, f03);
        addCompostable(GRASS_TALL_TINTED, f05);
        addCompostable(FERN_TALL_TINTED, f065);
        addCompostable(SEAGRASS_TALL_TINTED, f05);
        addCompostable(VINE_TINTED, f05);
        addCompostable(LILY_TINTED, f065);
        addCompostable(SUGAR_CANE_TINTED, f05);

        addCompostable(BIG_DRIP_LEAF_TINTED, f065);
        addCompostable(SMALL_DRIP_LEAF_TINTED, f03);

        addCompostable(KELP_TINTED, f05);
        addCompostable(CACTUS_TINTED, f03);



        if (isBOPLoaded) {
            addCompostable(FIR_LEAVES_BLOCK, f03);
            addCompostable(PINE_LEAVES_BLOCK, f03);
            addCompostable(MAPLE_LEAVES_BLOCK, f03);
            addCompostable(REDWOOD_LEAVES_BLOCK, f03);
            addCompostable(MAHOGANY_LEAVES_BLOCK, f03);
            addCompostable(JACARANDA_LEAVES_BLOCK, f03);
            addCompostable(PALM_LEAVES_BLOCK, f03);
            addCompostable(WILLOW_LEAVES_BLOCK, f03);
            addCompostable(DEAD_LEAVES_BLOCK, f03);
            addCompostable(MAGIC_LEAVES_BLOCK, f03);
            addCompostable(UMBRAN_LEAVES_BLOCK, f03);
            addCompostable(EMPYREAL_LEAVES_BLOCK, f03);
            addCompostable(FLOWERING_OAK_LEAVES_BLOCK, f03);
            addCompostable(ORIGIN_LEAVES_BLOCK, f03);
            addCompostable(CYPRESS_LEAVES_BLOCK, f03);
            addCompostable(HELLBARK_LEAVES_BLOCK, f03);
            addCompostable(WILLOW_VINE_TINTED, f03);

            addCompostable(BUSH_TINTED, f05);
            addCompostable(SPROUT_TINTED, f05);
            addCompostable(CLOVER_TINTED, f05);
            addCompostable(HUGE_CLOVER_TINTED, f05);
            addCompostable(HIGH_GRASS_TINTED, f05);
            addCompostable(HIGH_GRASS_PLANT_TINTED, f05);
            addCompostable(HUGE_LILY_PAD_TINTED, f065);
            addCompostable(WATER_GRASS_TINTED, f05);
            addCompostable(TINY_CACTUS_TINTED, f05);
            addCompostable(WATERLILY_TINTED, f065);
            addCompostable(WILLOW_VINE_TINTED, f05);


        }

        if (config.ALLOW_COMPOSTING_BAMBOO.get()) {
            addCompostable(BAMBOO, f03);
            addCompostable(BAMBOO_TINTED, f03);
        }

        if (config.ALLOW_COMPOSTING_ROTTER_FLESH.get())
            addCompostable(ROTTEN_FLESH, f03);
    }

    private void addCompostable(ItemLike item, float chance) {
        Builder<Compostable, Item> builder = this.builder(NeoForgeDataMaps.COMPOSTABLES);
        builder.add(item.asItem().builtInRegistryHolder(), new Compostable(chance), false);
    }
}
