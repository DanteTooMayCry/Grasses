package net.night.grasses.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.night.grasses.Grasses;

import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;
import static net.night.grasses.init.ItemsRegister.*;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Grasses.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        simpleItem(DIAMOND_AUTO_PRUNER);
        simpleItem(NETHERITE_AUTO_PRUNER);
        simpleItemInstanceOfDyeingTool(DYEING_TOOL);
        simpleItemInstanceOfDyeingBoneMeal(DYEING_BONE_MEAL, "minecraft:item/bone_meal");
        simpleItemInstanceOfDyeingBoneMeal(GRASSES_DYE, "grasses:item/grasses_dye");

        simpleItemInstancePlant(GRASS_SHORT_TINTED, "minecraft:block/short_grass");
        simpleItemInstancePlant(GRASS_TALL_TINTED, "minecraft:block/tall_grass_top");
        simpleItemInstancePlant(FERN_TINTED, "minecraft:block/fern");
        simpleItemInstancePlant(FERN_TALL_TINTED, "minecraft:block/large_fern_top");
        simpleItemInstancePlant(SEAGRASS_TINTED, "grasses:block/seagrass_tinted");
        simpleItemInstancePlant(SEAGRASS_TALL_TINTED, "grasses:block/seagrass_tall_tinted_top");
        simpleItemInstancePlant(VINE_TINTED, "minecraft:block/vine");
        simpleItemInstancePlant(LILY_TINTED, "minecraft:block/lily_pad");
        simpleItemInstancePlant(SUGAR_CANE_TINTED, "grasses:item/sugar_cane");
        simpleItemInstancePlant(BAMBOO_TINTED, "grasses:item/bamboo");
        simpleItemWithOverlay(BAMBOO_SAPLING_TINTED, "grasses:block/bamboo_stage0", "grasses:block/bamboo_stage0_overlay");
        itemDripLeaf(BIG_DRIP_LEAF_TINTED, "grasses:item/tinted_big_dripleaf_model");
        simpleItemInstancePlant(BIG_DRIP_LEAF_STEM_TINTED, "grasses:block/big_dripleaf_stem");
        itemDripLeaf(SMALL_DRIP_LEAF_TINTED, "grasses:item/tinted_small_dripleaf_model");
        simpleItemInstancePlant(KELP_TINTED, "grasses:item/kelp");
        simpleItemInstancePlant(KELP_PLANT_TINTED, "grasses:block/kelp_plant_tinted");

        simpleItemWithOverlay(GRASS_IN_BARS, "minecraft:block/short_grass", "minecraft:block/iron_bars");
        simpleItemWithOverlay(FERN_IN_BARS, "minecraft:block/fern", "minecraft:block/iron_bars");
        simpleItemWithOverlay(VINE_IN_BARS, "minecraft:block/vine", "minecraft:block/iron_bars");
        simpleItemWithOverlay(TINTED_VINE_IN_BARS, "minecraft:block/vine", "minecraft:block/iron_bars");
        simpleItemWithOverlay(TINTED_GRASS_IN_BARS, "minecraft:block/short_grass", "minecraft:block/iron_bars");
        simpleItemWithOverlay(TINTED_FERN_IN_BARS, "minecraft:block/fern", "minecraft:block/iron_bars");

        if (isBOPLoaded) {
            simpleItemInstancePlant(WILLOW_VINE_TINTED, "grasses:block/bop/willow_vine");
            simpleItemWithOverlay(TINY_CACTUS_TINTED, "grasses:block/bop/tiny_cactus", "grasses:block/bop/tiny_cactus_overlay");
            simpleItemInstancePlant(SPROUT_TINTED, "grasses:block/bop/sprout");
            simpleItemInstancePlant(BUSH_TINTED, "grasses:block/bop/bush");
            simpleItemInstancePlant(CLOVER_TINTED, "grasses:item/bop/clover");
            simpleItemInstancePlant(HUGE_CLOVER_TINTED, "grasses:item/bop/huge_clover_petal");
            simpleItemInstancePlant(HIGH_GRASS_TINTED, "grasses:item/bop/high_grass");
            simpleItemInstancePlant(HIGH_GRASS_PLANT_TINTED, "grasses:block/bop/high_grass_plant");
            simpleItemInstancePlant(HUGE_LILY_PAD_TINTED, "grasses:item/bop/huge_lily_pad");
            simpleItemWithOverlay(WATER_GRASS_TINTED, "grasses:item/bop/watergrass", "grasses:item/bop/watergrass_overlay");
            simpleItemWithOverlay(WATERLILY_TINTED, "grasses:item/bop/waterlily_overlay", "grasses:item/bop/waterlily");
            simpleItemInstancePlant(LEAF_PILE_TINTED, "grasses:block/bop/leaf_pile");
        }

        simpleItem(FERTILE_ICON);

    }

    protected ItemModelBuilder simpleItem (DeferredItem<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(Grasses.MOD_ID, "item/" + item.getId().getPath()));
    }
    protected ItemModelBuilder simpleItemInstanceOfDyeingTool(DeferredItem<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(Grasses.MOD_ID, "item/" + item.getId().getPath()))
                    .texture("layer1", new ResourceLocation(Grasses.MOD_ID,"item/" + item.getId().getPath()) + "_overlay");
    }
    protected ItemModelBuilder simpleItemInstanceOfDyeingBoneMeal(DeferredItem<Item> item, String path) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(path));
    }
    protected ItemModelBuilder simpleItemInstancePlant(DeferredBlock<Block> item, String path) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(path));
    }
    protected ItemModelBuilder itemDripLeaf (DeferredBlock<Block> item, String name) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation(name));
    }

    protected ItemModelBuilder simpleItemWithOverlay (DeferredBlock<Block> item, String path, String pathOverlay) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(path))
                .texture("layer1", new ResourceLocation(pathOverlay));
    }
}
