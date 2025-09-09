package net.night.grasses.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.Grasses;
import net.night.grasses.block.plants.bop.TintedHugeLilyPadBOP;
import net.night.grasses.enums.GrassesQuarterProperty;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.core.Direction.*;
import static net.minecraft.core.Direction.DOWN;
import static net.minecraft.core.Direction.EAST;
import static net.minecraft.core.Direction.NORTH;
import static net.minecraft.core.Direction.SOUTH;
import static net.minecraft.core.Direction.UP;
import static net.minecraft.core.Direction.WEST;
import static net.minecraft.world.level.block.DoublePlantBlock.HALF;
import static net.minecraft.world.level.block.EndPortalFrameBlock.HAS_EYE;
import static net.minecraft.world.level.block.FarmBlock.MOISTURE;
import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static net.minecraft.world.level.block.SlabBlock.TYPE;
import static net.minecraft.world.level.block.SnowyDirtBlock.SNOWY;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.UPPER;
import static net.minecraft.world.level.block.state.properties.SlabType.*;
import static net.minecraft.world.level.block.state.properties.SlabType.BOTTOM;
import static net.minecraft.world.level.block.state.properties.Tilt.*;
import static net.minecraft.world.level.block.state.properties.Tilt.UNSTABLE;
import static net.minecraftforge.client.model.generators.ModelBuilder.FaceRotation.*;
import static net.minecraftforge.client.model.generators.ModelBuilder.FaceRotation.CLOCKWISE_90;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;

public class ModBlockStateProvider extends BlockStateProvider {
    String blockPath = "minecraft:block/";
    String blockGrassesPath = "grasses:block/";
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Grasses.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        for (RegistryObject<Block> block : grassRegistryBlocksList) {
            blockWithItem(block, 1);
        }
        for(RegistryObject<Block> block : grassRegistrySlabBlocksList){
            blockWithItem(block, 2);
        }
        blockWithItem(DIRT_SLAB_BLOCK, 3);
        blockWithItem(DIRT_PATH_SLAB_BLOCK, 4);
        blockWithItem(FARMLAND_SLAB_BLOCK, 5);
        blockWithItem(COARSE_DIRT_SLAB_BLOCK, 6);
        blockWithItem(ROOTED_DIRT_SLAB_BLOCK, 7);
        blockWithItem(GROW_MYCELIUM_BLOCK, 8);
        blockWithItem(MYCELIUM_SLAB_BLOCK, 9);
        blockWithItem(GROW_PODZOL_BLOCK, 10);
        blockWithItem(PODZOL_SLAB_BLOCK, 11);

        blockWithItem(GROW_CRIMSON_NYLIUM_BLOCK, 12);
        blockWithItem(CRIMSON_NYLIUM_SLAB_BLOCK, 13);
        blockWithItem(GROW_WARPED_NYLIUM_BLOCK, 14);
        blockWithItem(WARPED_NYLIUM_SLAB_BLOCK, 15);
        blockWithItem(NETHERRACK_SLAB_BLOCK, 16);

        blockWithItem(SAND_SLAB_BLOCK, 17);
        blockWithItem(RED_SAND_SLAB_BLOCK, 18);
        blockWithItem(GRAVEL_SLAB_BLOCK, 19);
        blockWithItem(CLAY_SLAB_BLOCK, 20);

        blockWithItem(SOUL_SAND_SLAB_BLOCK, 21);
        blockWithItem(SOUL_SOIL_SLAB_BLOCK, 22);
        blockWithItem(MAGMA_SLAB_BLOCK, 23);

        blockWithItem(MOSS_SLAB_BLOCK, 24);
        blockWithItem(MUD_SLAB_BLOCK, 25);
        blockWithItem(PACKED_MUD_SLAB_BLOCK, 26);
        blockWithItem(MUDDY_ROOTS_SLAB_BLOCK, 27);

        blockWithItem(ICE_SLAB_BLOCK, 28);
        blockWithItem(PACKED_ICE_SLAB_BLOCK, 29);
        blockWithItem(BLUE_ICE_SLAB_BLOCK, 30);

        blockWithItem(DYEING_STATION, 31);

        for(RegistryObject<Block> leaves : grassesLeavesRegistryBlocksList){
            blockWithItem(leaves, 32);
        }

        blockWithItem(GRASS_TINTED, 33);
        blockWithItem(FERN_TINTED, 33);

        blockWithItem(GRASS_TALL_TINTED, 34);
        blockWithItem(FERN_TALL_TINTED, 34);

        blockWithItem(SEAGRASS_TINTED, 35);
        blockWithItem(SEAGRASS_TALL_TINTED, 36);

        blockWithItem(VINE_TINTED, 37);
        blockWithItem(WILLOW_VINE_TINTED, 37);
        blockWithItem(LILY_TINTED, 38);
        blockWithItem(SUGAR_CANE_TINTED, 39);
        blockWithItem(BAMBOO_TINTED, 40);
        blockWithItem(BAMBOO_SAPLING_TINTED, 41);
        blockWithItem(BIG_DRIP_LEAF_TINTED, 42);
        blockWithItem(BIG_DRIP_LEAF_STEM_TINTED, 43);
        blockWithItem(SMALL_DRIP_LEAF_TINTED, 44);
        blockWithItem(KELP_TINTED, 45);
        blockWithItem(KELP_PLANT_TINTED, 46);
        blockWithItem(CACTUS_TINTED, 47);

        blockWithItem(GRASS_POTTED, 48);
        blockWithItem(SEAGRASS_POTTED, 48);
        blockWithItem(SUGAR_CANE_POTTED, 48);
        blockWithItem(VINE_POTTED, 48);
        blockWithItem(BIG_DRIP_LEAF_POTTED, 52);
        blockWithItem(SMALL_DRIP_LEAF_POTTED, 52);
        blockWithItem(KELP_POTTED, 48);

        blockWithItem(GRASS_POTTED_TINTED, 49);
        blockWithItem(FERN_POTTED_TINTED, 49);
        blockWithItem(SEAGRASS_POTTED_TINTED, 49);
        blockWithItem(BAMBOO_POTTED_TINTED, 49);
        blockWithItem(SUGAR_CANE_POTTED_TINTED, 50);
        blockWithItem(VINE_POTTED_TINTED, 49);
        blockWithItem(BIG_DRIP_LEAF_POTTED_TINTED, 51);
        blockWithItem(SMALL_DRIP_LEAF_POTTED_TINTED, 51);
        blockWithItem(KELP_POTTED_TINTED, 49);
        blockWithItem(CACTUS_POTTED_TINTED, 49);

        for(RegistryObject<Block> leaves : pottedVanillaRegistryLeavesList){
            blockWithItem(leaves, 53);
        }

        for(RegistryObject<Block> leaves : pottedTintedRegistryLeavesList){
            blockWithItem(leaves, 54);
        }

        blockWithItem(END_PORTAL_FRAME_BLOCK, 55);
        blockWithItem(GRASS_IN_BARS, 56);
        blockWithItem(FERN_IN_BARS, 56);
        blockWithItem(VINE_IN_BARS, 57);
        blockWithItem(TINTED_GRASS_IN_BARS, 58);
        blockWithItem(TINTED_FERN_IN_BARS, 58);
        blockWithItem(TINTED_VINE_IN_BARS, 59);


        blockWithItem(BUSH_TINTED, 400);
        blockWithItem(SPROUT_TINTED, 400);
        blockWithItem(CLOVER_TINTED, 401);
        blockWithItem(HUGE_CLOVER_TINTED, 402);
        blockWithItem(HIGH_GRASS_TINTED, 403);
        blockWithItem(HIGH_GRASS_PLANT_TINTED, 404);
        blockWithItem(HUGE_LILY_PAD_TINTED, 405);
        blockWithItem(WATER_GRASS_TINTED, 406);
        blockWithItem(TINY_CACTUS_TINTED, 407);
        blockWithItem(WATERLILY_TINTED, 408);
        blockWithItem(LEAF_PILE_TINTED, 409);

        if (isBOPLoaded) {
            prepareBlocksListFromRegistryLists();

            for (RegistryObject<Block> leaves : tintedBOPleavesRegistryBlocksList) {
                grassesBoPBlockWithItem(leaves, 32);
            }

            grassesBoPBlockWithItem(BUSH_TINTED, 400);
            grassesBoPBlockWithItem(SPROUT_TINTED, 400);
            grassesBoPBlockWithItem(CLOVER_TINTED, 401);
            grassesBoPBlockWithItem(HUGE_CLOVER_TINTED, 402);
            grassesBoPBlockWithItem(HIGH_GRASS_TINTED, 403);
            grassesBoPBlockWithItem(HIGH_GRASS_PLANT_TINTED, 404);
            grassesBoPBlockWithItem(HUGE_LILY_PAD_TINTED, 405);
            grassesBoPBlockWithItem(WATER_GRASS_TINTED, 406);
            grassesBoPBlockWithItem(TINY_CACTUS_TINTED, 407);
            grassesBoPBlockWithItem(WATERLILY_TINTED, 408);
            grassesBoPBlockWithItem(LEAF_PILE_TINTED, 409);
        }
    }

    private void prepareBlocksListFromRegistryLists() {
        for (RegistryObject<Block> leaves : tintedBOPleavesRegistryBlocksList) {
            tintedBOPleavesBlocksList.add(leaves.get());
        }
        for (RegistryObject<Block> plants : tintedBOPplantRegistryBlockList) {
            tintedBOPPlantBlockList.add(plants.get());
        }
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject, int typeBlock) {
        Block block = blockRegistryObject.get();
        String path = blockRegistryObject.getId().getPath();

        if (typeBlock == 1) {
            ModelFile grassesBlock      = makeTintedGrassBlock(0.0F, 16.0F, 16.0F, path);
            ConfiguredModel snowModel   = new ConfiguredModel(models().getExistingFile(mcLoc("block/grass_block_snow")));

            getSnowyVariant(block, grassesBlock, snowModel);

        } else if (typeBlock == 2) {

            ModelFile bottomModel           = makeTintedGrassBlock(0.0F, 8.0F, 8.0F, path);
            ModelFile topModel              = makeTintedGrassBlock(8.0F, 16.0F, 8.0F, path.concat("_top"));
            ModelFile doubleModel           = makeTintedGrassBlock(0.0F, 16.0F, 16.0F, path.concat("_block"));
            ConfiguredModel snowModelTop    = new ConfiguredModel(makeSlabTopSnow("grass_slab_snow", "dirt"));
            ConfiguredModel snowModelDouble = new ConfiguredModel(models().getExistingFile(mcLoc("block/grass_block_snow")));

            getSnowySlabVariant(block, bottomModel, topModel, doubleModel, snowModelTop, snowModelDouble);

        } else if (typeBlock == 3) {
            getSlabModel(block, path,4, false, false, "dirt", null, null);
        } else if (typeBlock == 4) {
            getSlabModel(block, path,5, true, false, "dirt", "dirt_path_side", "dirt_path_top");
        } else if (typeBlock == 5) {
            String parentBlock = "block";
            String textureBasic = "dirt";
            String textureSide = "dirt";
            String textureTop = "farmland";
            String textureTop2 = "farmland_moist";

            ConfiguredModel bottomModel     = new ConfiguredModel(makeSlabOrBlockDiffFaces(0.0F, 7.0F, 1, 8.0f, path, parentBlock, textureBasic, textureSide, textureTop));
            ConfiguredModel topModel        = new ConfiguredModel(makeSlabOrBlockDiffFaces(8.0F, 15.0F, 1, 8.0f, path.concat("_top"), parentBlock, textureBasic, textureSide, textureTop));
            ConfiguredModel doubleModel     = new ConfiguredModel(makeSlabOrBlockDiffFaces(0.0F, 15.0F, 1, 16.0f, path.concat("_block"), parentBlock, textureBasic, textureSide, textureTop));
            ConfiguredModel bottomMoistModel= new ConfiguredModel(makeSlabOrBlockDiffFaces(0.0F, 7.0F, 1, 8.0f, path.concat("_moist"), parentBlock, textureBasic, textureSide, textureTop2));
            ConfiguredModel topMoistModel   = new ConfiguredModel(makeSlabOrBlockDiffFaces(8.0F, 15.0F, 1, 8.0f, path.concat("_top_moist"), parentBlock, textureBasic, textureSide, textureTop2));
            ConfiguredModel doubleMoistModel= new ConfiguredModel(makeSlabOrBlockDiffFaces(0.0F, 15.0F, 1, 16.0f, path.concat("_block_moist"), parentBlock, textureBasic, textureSide, textureTop2));

            getSlabTypeMoist(block, bottomModel, topModel, doubleModel, bottomMoistModel, topMoistModel, doubleMoistModel);
        } else if (typeBlock == 6) {
            getSlabModel(block, path, typeBlock, false, true, "coarse_dirt", null, null);
        } else if (typeBlock == 7) {
            getSlabModel(block, path, typeBlock, false, false, "rooted_dirt", null, null);
        } else if (typeBlock == 8) {
            ModelFile myceliumBlock     = makeSlabOrBlockDiffFaces(0.0f, 16.0F, 0, 16.0F, path, "cube_bottom_top", "dirt", "mycelium_side", "mycelium_top");
            ConfiguredModel snowModel   = new ConfiguredModel(models().getExistingFile(mcLoc("block/grass_block_snow")));

            getSnowyVariant(block, myceliumBlock, snowModel);
        } else if (typeBlock == 9) {
            String textureBasic = "dirt";
            String textureSide = "mycelium_side";
            String textureTop = "mycelium_top";
            String parent = "cube_bottom_top";
            ModelFile bottomModel = makeSlabOrBlockDiffFaces(0.0f, 8.0F, 0, 8.0F, path, parent, textureBasic, textureSide, textureTop);
            ModelFile topModel = makeSlabOrBlockDiffFaces(8.0f, 16.0F, 0, 8.0F, path.concat("_top"), parent, textureBasic, textureSide, textureTop);
            ModelFile doubleModel = makeSlabOrBlockDiffFaces(0.0f, 16.0F, 0, 16.0F, path.concat("_block"), parent, textureBasic, textureSide, textureTop);

            ConfiguredModel snowModelTop = new ConfiguredModel(makeSlabTopSnow("grass_slab_snow", "mycelium_top"));
            ConfiguredModel snowModelDouble = new ConfiguredModel(models().getExistingFile(mcLoc("block/grass_block_snow")));

            getSnowySlabVariant(block, bottomModel, topModel, doubleModel, snowModelTop, snowModelDouble);
        } else if (typeBlock == 10) {
            ModelFile podzolBlock     = makeSlabOrBlockDiffFaces(0.0f, 16.0F, 0, 16.0F, path, "cube_bottom_top", "dirt", "podzol_side", "podzol_top");
            ConfiguredModel snowModel   = new ConfiguredModel(models().getExistingFile(mcLoc("block/grass_block_snow")));

            getSnowyVariant(block, podzolBlock, snowModel);
        } else if (typeBlock == 11) {
            String textureBasic = "dirt";
            String textureSide = "podzol_side";
            String textureTop = "podzol_top";
            String parent = "cube_bottom_top";
            ModelFile bottomModel = makeSlabOrBlockDiffFaces(0.0f, 8.0F, 0, 8.0F, path, parent, textureBasic, textureSide, textureTop);
            ModelFile topModel = makeSlabOrBlockDiffFaces(8.0f, 16.0F, 0, 8.0F, path.concat("_top"), parent, textureBasic, textureSide, textureTop);
            ModelFile doubleModel = makeSlabOrBlockDiffFaces(0.0f, 16.0F, 0, 16.0F, path.concat("_block"), parent, textureBasic, textureSide, textureTop);

            ConfiguredModel snowModelTop = new ConfiguredModel(makeSlabTopSnow("grass_slab_snow", "podzol_top"));
            ConfiguredModel snowModelDouble = new ConfiguredModel(models().getExistingFile(mcLoc("block/grass_block_snow")));

            getSnowySlabVariant(block, bottomModel, topModel, doubleModel, snowModelTop, snowModelDouble);
        } else if (typeBlock == 12) {
            ModelFile nylium  = makeSlabOrBlockDiffFaces(0.0f, 16.0F, 0, 16.0F, path, "cube_bottom_top","netherrack", "crimson_nylium_side","crimson_nylium");
            simpleBlock(block, nylium);
        } else if (typeBlock == 13) {
            getSlabModel(block, path, typeBlock, false, true, "netherrack", "crimson_nylium_side","crimson_nylium");
        } else if (typeBlock == 14) {
            ModelFile nylium  = makeSlabOrBlockDiffFaces(0.0f, 16.0F, 0, 16.0F, path, "cube_bottom_top","netherrack", "warped_nylium_side","warped_nylium");
            simpleBlock(block, nylium);
        } else if (typeBlock == 15) {
            getSlabModel(block, path, typeBlock, false, true, "netherrack", "warped_nylium_side","warped_nylium");
        } else if (typeBlock == 16) {
            getSlabModel(block, path, typeBlock, false, false, "netherrack", null, null);
        } else if (typeBlock == 17) {
            getSlabModel(block, path, typeBlock, false, false, "sand", null, null);
        } else if (typeBlock == 18) {
            getSlabModel(block, path, typeBlock, false, false, "red_sand", null, null);
        } else if (typeBlock == 19) {
            getSlabModel(block, path, typeBlock, false, true, "gravel", null, null);
        } else if (typeBlock == 20) {
            getSlabModel(block, path, typeBlock, false, true, "clay", null, null);
        } else if (typeBlock == 21) {
            getSlabModel(block, path, typeBlock, false, true, "soul_sand", null, null);
        } else if (typeBlock == 22) {
            getSlabModel(block, path, typeBlock, false, true, "soul_soil", null, null);
        } else if (typeBlock == 23) {
            getSlabModel(block, path, typeBlock, false, true, "magma", null, null);
        } else if (typeBlock == 24) {
            getSlabModel(block, path, typeBlock, false, true, "moss_block", null, null);
        } else if (typeBlock == 25) {
            getSlabModel(block, path, typeBlock, false, true, "mud", null, null);
        } else if (typeBlock == 26) {
            getSlabModel(block, path, typeBlock, false, true, "packed_mud", null, null);
        } else if (typeBlock == 27) {
            getSlabModelQuasiAxisRotate(block, path, "muddy_mangrove_roots_top", "muddy_mangrove_roots_side");
        } else if (typeBlock == 28) {
            getSlabModel(block, path, typeBlock, false, true, "ice", null, null);
        } else if (typeBlock == 29) {
            getSlabModel(block, path, typeBlock, false, true, "packed_ice", null, null);
        } else if (typeBlock == 30) {
            getSlabModel(block, path, typeBlock, false, true, "blue_ice", null, null);
        } else if (typeBlock == 31) {
            ModelFile stationBlock = new ModelFile.UncheckedModelFile(modLoc("block/dyeing_station"));

            getVariantBuilder(block)
                    .forAllStates(state ->
                            ConfiguredModel.builder()
                                    .modelFile(stationBlock)
                                    .rotationY((int) state.getValue(HORIZONTAL_FACING).toYRot())
                                    .build()
                    );
        } else if (typeBlock == 32) {
            getLeavesModel(block, path);
        } else if (typeBlock == 33) {

            ModelFile modelPlant = null;

            if (block.defaultBlockState() == GRASS_TINTED.get().defaultBlockState()) {
                modelPlant = makeTintedPlantBlock(path, "minecraft:block/grass");
            }

            else if (block.defaultBlockState() == FERN_TINTED.get().defaultBlockState()) {
                modelPlant = makeTintedPlantBlock(path, "minecraft:block/fern");
            }

            simpleBlock(block, modelPlant);
        } else if (typeBlock == 34) {

            if (block.defaultBlockState() == GRASS_TALL_TINTED.get().defaultBlockState()) {
                ConfiguredModel bottomGrassModel    = new ConfiguredModel(makeTintedPlantBlock(path.concat("_bottom"), "minecraft:block/tall_grass_bottom"));
                ConfiguredModel topGrassModel       = new ConfiguredModel(makeTintedPlantBlock(path.concat("_top"), "minecraft:block/tall_grass_top"));

                getVariantBuilder(block)
                        .partialState().with(HALF, LOWER).addModels(bottomGrassModel)
                        .partialState().with(HALF, UPPER).addModels(topGrassModel);

            } else if (block.defaultBlockState() == FERN_TALL_TINTED.get().defaultBlockState()) {
                ConfiguredModel bottomFernModel     = new ConfiguredModel(makeTintedPlantBlock(path.concat("_bottom"), "minecraft:block/large_fern_bottom"));
                ConfiguredModel topFernModel        = new ConfiguredModel(makeTintedPlantBlock(path.concat("_top"), "minecraft:block/large_fern_top"));

                getVariantBuilder(block)
                        .partialState().with(HALF, LOWER).addModels(bottomFernModel)
                        .partialState().with(HALF, UPPER).addModels(topFernModel);

            }
        } else if (typeBlock == 35) {
            ModelFile modelPlant = makeTintedSeaGrassBlock(path, "grasses:block/seagrass_tinted");
            simpleBlock(block, modelPlant);
        } else if (typeBlock == 36) {
            ConfiguredModel bottomSeaGrassModel    = new ConfiguredModel(makeTintedSeaGrassBlock(path.concat("_bottom"), "grasses:block/seagrass_tall_tinted_bottom"));
            ConfiguredModel topSeaGrassModel       = new ConfiguredModel(makeTintedSeaGrassBlock(path.concat("_top"), "grasses:block/seagrass_tall_tinted_top"));

            getVariantBuilder(block)
                    .partialState().with(HALF, LOWER).addModels(bottomSeaGrassModel)
                    .partialState().with(HALF, UPPER).addModels(topSeaGrassModel);
        } else if (typeBlock == 37) {

            String plant = "minecraft:block/vine";
            if (block.equals(WILLOW_VINE_TINTED.get()))
                plant = "grasses:block/bop/willow_vine";

            ModelFile modelVine = makeTintedVineBlock(path, plant);
            getMultipartVine(block, modelVine);
        } else if (typeBlock == 38) {
            ModelFile modelLily = makeTintedLilyBlock(path,"minecraft:block/lily_pad");
            getRotateVariant(block, modelLily);
        } else if (typeBlock == 39) {
            getSugarCaneVariant(block, path);
        } else if (typeBlock == 40) {

            getMultipartBamboo(block, path);


        } else if (typeBlock == 41) {
            ModelFile modelBambooSapling = makeTintedPlantBlockWithOverlay(path, "bamboo_stage0", "bamboo_stage0_overlay");
            simpleBlock(block, modelBambooSapling);
        } else if (typeBlock == 42) {
            getBigDripLeafVariant(block, path);
        } else if (typeBlock == 43) {
            getFacingRotateVariant(block, path);
        } else if (typeBlock == 44) {
            getSmallDripLeafVariant(block, path);
        } else if (typeBlock == 45) {
            ModelFile modelKelp = makeTintedPlantBlock(path, "grasses:block/kelp_tinted");
            simpleBlock(block, modelKelp);
        } else if (typeBlock == 46) {
            ModelFile modelKelpPlant = makeTintedPlantBlock(path, "grasses:block/kelp_plant_tinted");
            simpleBlock(block, modelKelpPlant);
        } else if (typeBlock == 47) {
            ModelFile modelCactus = makeTintedCactusBlock(path);
            simpleBlock(block, modelCactus);
        } else if (typeBlock == 48) {

            ModelFile modelPotted = null;

            if (block.defaultBlockState() == GRASS_POTTED.get().defaultBlockState())
                modelPotted = makeTintedPottedBlockSingle(path, Blocks.GRASS, "minecraft:block/dirt");
            else if (block.defaultBlockState() == SEAGRASS_POTTED.get().defaultBlockState())
                modelPotted = makeTintedPottedBlockSingle(path, Blocks.SEAGRASS, "minecraft:block/water_still");
            else if (block.defaultBlockState() == SUGAR_CANE_POTTED.get().defaultBlockState())
                modelPotted = makeTintedPottedBlock(path, "sugar_cane_potted_tinted_biomes", "minecraft:block/dirt");
            else if (block.defaultBlockState() == VINE_POTTED.get().defaultBlockState())
                modelPotted = makeTintedPottedBlockSingle(path, Blocks.VINE, "minecraft:block/jungle_log");
            else if (block.defaultBlockState() == KELP_POTTED.get().defaultBlockState())
                modelPotted = makePottedKelpBlock(path, "kelp_potted", "grasses:block/water_still");

            simpleBlock(block, modelPotted);
        } else if (typeBlock == 49) {

            ModelFile modelPotted = null;

            if (block.defaultBlockState() == GRASS_POTTED_TINTED.get().defaultBlockState())
                modelPotted = makeTintedPottedBlockSingle(path, Blocks.GRASS, "minecraft:block/dirt");
            else if (block.defaultBlockState() == FERN_POTTED_TINTED.get().defaultBlockState())
                modelPotted = makeTintedPottedBlockSingle(path, Blocks.FERN, "minecraft:block/dirt");
            else if (block.defaultBlockState() == SEAGRASS_POTTED_TINTED.get().defaultBlockState())
                modelPotted = makeTintedPottedBlock(path, "seagrass_tinted", "grasses:block/water_still");
            else if (block.defaultBlockState() == VINE_POTTED_TINTED.get().defaultBlockState())
                modelPotted = makeTintedPottedBlockSingle(path, Blocks.VINE, "minecraft:block/jungle_log");
            else if (block.defaultBlockState() == KELP_POTTED_TINTED.get().defaultBlockState())
                modelPotted = makeTintedPottedBlock(path, "kelp_potted_tinted", "grasses:block/water_still");
            else if (block.defaultBlockState() == BAMBOO_POTTED_TINTED.get().defaultBlockState())
                modelPotted = makeTintedPottedBambooBlock(path);
            else if (block.defaultBlockState() == CACTUS_POTTED_TINTED.get().defaultBlockState())
                modelPotted = makeTintedPottedCactusBlock(path);

            simpleBlock(block, modelPotted);
        } else if (typeBlock == 50) {
            getPottedSugarCaneVariant(block, path);
        } else if (typeBlock == 51 || typeBlock == 52) {
            getFacingRotateVariant(block, path);
        } else if (typeBlock == 53 || typeBlock == 54) {
            simpleBlock(block, getPottedLeavesModel(block, path));
        } else if (typeBlock == 55) {
            getEndFramePortalVariant(block, path);
        } else if (typeBlock == 56) {

            ModelFile plantInBars = null;
            if (block.defaultBlockState() == GRASS_IN_BARS.get().defaultBlockState())
                plantInBars = makeTintedPlantBlockForBars(path, "minecraft:block/grass", 0f, 16f);
            else if (block.defaultBlockState() == FERN_IN_BARS.get().defaultBlockState())
                plantInBars = makeTintedPlantBlockForBars(path, "minecraft:block/fern", -1f, 15f);

            getPlantInBarsVariant(block, path, plantInBars);

        } else if (typeBlock == 57) {
            getVineInBarsVariant(block, path, "minecraft:block/vine");
        } else if (typeBlock == 58) {
            ModelFile plantInBars = null;
            if (block.defaultBlockState() == TINTED_GRASS_IN_BARS.get().defaultBlockState())
                plantInBars = makeTintedPlantBlockForBars(path, "minecraft:block/grass", 0f, 16f);
            else if (block.defaultBlockState() == TINTED_FERN_IN_BARS.get().defaultBlockState())
                plantInBars = makeTintedPlantBlockForBars(path, "minecraft:block/fern", -1f, 15f);

            getPlantInBarsVariant(block, path, plantInBars);
        } else if  (typeBlock == 59) {
            getVineInBarsVariant(block, path, "minecraft:block/vine");
        }


        if ((typeBlock >= 33 && typeBlock <= 59 && typeBlock != 47 && typeBlock != 55) || typeBlock >= 400) {
            return;
        } else
            simpleBlockItem(block, models().getExistingFile(blockTexture(block)));

    }

    private void grassesBoPBlockWithItem(RegistryObject<Block> blockRegistryObject, int typeBlock) {
        Block block = blockRegistryObject.get();
        String path = blockRegistryObject.getId().getPath();

        if (typeBlock == 32) {
            getLeavesModel(block, path);
        }

        else if (typeBlock == 400) {
            ModelFile modelPlant = null;

            if (block.defaultBlockState() == BUSH_TINTED.get().defaultBlockState()) {
                modelPlant = makeTintedPlantBlock(path, "grasses:block/bop/bush");
            } else if (block.defaultBlockState() == SPROUT_TINTED.get().defaultBlockState()) {
                modelPlant = makeTintedPlantBlock(path, "grasses:block/bop/sprout");
            }

            simpleBlock(block, modelPlant);
        } else if (typeBlock == 401) {
            getMultiPartPetalLike(block, path);
        } else if (typeBlock == 402) {
            getFacingRotateVariant(block, path);
        } else if (typeBlock == 403) {
            ModelFile modelHighGrass = makeTintedPlantBlock(path, "grasses:block/bop/high_grass");
            simpleBlock(block, modelHighGrass);
        } else if (typeBlock == 404) {
            ModelFile modelHighGrassPlant = makeTintedPlantBlock(path, "grasses:block/bop/high_grass_plant");
            simpleBlock(block, modelHighGrassPlant);
        } else if (typeBlock == 405) {
            getHugeLilyVariant(block, path);
        } else if (typeBlock == 406) {
            getWatergrassVariant(block, path);
        } else if (typeBlock == 407) {
            ModelFile modelPlant = makeTintedPlantBlockWithOverlay(path, "bop/tiny_cactus", "bop/tiny_cactus_overlay");
            ModelFile modelPlantAlt = makeTintedPlantBlockWithOverlay(path.concat("_alt"), "bop/tiny_cactus_alt", "bop/tiny_cactus_alt_overlay");

            getVariantBuilder(block)
                    .partialState().modelForState().modelFile(modelPlant).nextModel().modelFile(modelPlantAlt).addModel();
        } else if (typeBlock == 408) {
            ModelFile modelWaterlily = makeTintedWaterlily(path);
            simpleBlock(block, modelWaterlily);
        } else if (typeBlock == 409) {
            ModelFile modelPlant = makeTintedLeafPile(path, "bop/leaf_pile");
            simpleBlock(block, modelPlant);
        }

        if (typeBlock == 32)
            simpleBlockItem(block, models().getExistingFile(blockTexture(block)));
    }


    //////////////////////////////////////////////////////////////////////

    private void getSlabModel(Block block, String path, int typeBlock, boolean loweredSlab, boolean staticTexture, String textureBasic, String textureSide, String textureTop) {
        String renderType = "cutout_mipped";
        String parentBlock = "block";
        float yToB, yToT, yToD, v1;
        ModelFile bottomSlab;
        ModelFile topSlab;
        ModelFile doubleSlab;

        if (typeBlock == 28)
            renderType = "translucent";

        yToB = 8.0f;
        yToT = 16.0f;
        yToD = 16.0f;
        v1 = 0;

        if (loweredSlab) {
            yToB = 7.0f;
            yToT = 15.0f;
            yToD = 15.0f;
            v1 = 1.0f;
        }

        if (textureTop == null) {
            bottomSlab  = makeSlabBlockSameFaces(0.0f, yToB, 8.0f, path, textureBasic, renderType);
            topSlab     = makeSlabBlockSameFaces(8.0f, yToT, 8.0f, path.concat("_top"), textureBasic, renderType);
            doubleSlab  = makeSlabBlockSameFaces(0.0f, yToD, 16.0f, path.concat("_block"), textureBasic, renderType);
        }
        else {
            bottomSlab  = makeSlabOrBlockDiffFaces(0.0f, yToB, v1, 8.0f, path, parentBlock, textureBasic, textureSide, textureTop);
            topSlab     = makeSlabOrBlockDiffFaces(8.0f, yToT, v1, 8.0f, path.concat("_top"), parentBlock, textureBasic, textureSide, textureTop);
            doubleSlab  = makeSlabOrBlockDiffFaces(0.0f, yToD, v1, 16.0f, path.concat("_block"), parentBlock, textureBasic, textureSide, textureTop);
        }

        if (staticTexture) {
            getSlabTypeStaticVariant(block, new ConfiguredModel(bottomSlab), new ConfiguredModel(topSlab), new ConfiguredModel(doubleSlab));
        } else {
            getSlabTypeRotateVariant(block, bottomSlab, topSlab, doubleSlab);
        }
    }


    //////////////////////////////////////////////////////////////////////

    private void getSnowyVariant(Block block, ModelFile grassesBlock, ConfiguredModel snowModel) {

        getVariantBuilder(block)
                .partialState().with(SNOWY, Boolean.FALSE)
                .modelForState()
                .modelFile(grassesBlock).nextModel()
                .rotationY(90).modelFile(grassesBlock).nextModel()
                .rotationY(180).modelFile(grassesBlock).nextModel()
                .rotationY(270).modelFile(grassesBlock)
                .addModel()
                .partialState().with(SNOWY, Boolean.TRUE).addModels(snowModel);
    }

    private void getSnowySlabVariant(Block block, ModelFile bottomModel, ModelFile topModel, ModelFile doubleModel, ConfiguredModel snowModelTop, ConfiguredModel snowModelDouble) {

        getVariantBuilder(block)
                .partialState().with(SNOWY, Boolean.FALSE).with(TYPE, BOTTOM)
                .modelForState()
                .modelFile(bottomModel).nextModel()
                .rotationY(90).modelFile(bottomModel).nextModel()
                .rotationY(180).modelFile(bottomModel).nextModel()
                .rotationY(270).modelFile(bottomModel)
                .addModel()
                .partialState().with(SNOWY, Boolean.FALSE).with(TYPE, TOP)
                .modelForState()
                .modelFile(topModel).nextModel()
                .rotationY(90).modelFile(topModel).nextModel()
                .rotationY(180).modelFile(topModel).nextModel()
                .rotationY(270).modelFile(topModel)
                .addModel()
                .partialState().with(SNOWY, Boolean.FALSE).with(TYPE, DOUBLE)
                .modelForState()
                .modelFile(doubleModel).nextModel()
                .rotationY(90).modelFile(doubleModel).nextModel()
                .rotationY(180).modelFile(doubleModel).nextModel()
                .rotationY(270).modelFile(doubleModel)
                .addModel()
                .partialState().with(SNOWY, Boolean.TRUE).with(TYPE, BOTTOM).addModels(snowModelTop)
                .partialState().with(SNOWY, Boolean.TRUE).with(TYPE, TOP).addModels(snowModelTop)
                .partialState().with(SNOWY, Boolean.TRUE).with(TYPE, DOUBLE).addModels(snowModelDouble);
    }

    private void getSlabTypeStaticVariant(Block block, ConfiguredModel bottomModel, ConfiguredModel topModel, ConfiguredModel doubleModel) {
        getVariantBuilder(block)
                .partialState().with(TYPE, BOTTOM).addModels(bottomModel)
                .partialState().with(TYPE, TOP).addModels(topModel)
                .partialState().with(TYPE, DOUBLE).addModels(doubleModel);
    }

    private void getSlabTypeRotateVariant(Block block, ModelFile bottomModel, ModelFile topModel, ModelFile doubleModel) {
        getVariantBuilder(block)
                .partialState().with(TYPE, BOTTOM)
                .modelForState()
                .modelFile(bottomModel).nextModel()
                .rotationY(90).modelFile(bottomModel).nextModel()
                .rotationY(180).modelFile(bottomModel).nextModel()
                .rotationY(270).modelFile(bottomModel)
                .addModel()
                .partialState().with(TYPE, TOP)
                .modelForState()
                .modelFile(topModel).nextModel()
                .rotationY(90).modelFile(topModel).nextModel()
                .rotationY(180).modelFile(topModel).nextModel()
                .rotationY(270).modelFile(topModel)
                .addModel()
                .partialState().with(TYPE, DOUBLE)
                .modelForState()
                .modelFile(doubleModel).nextModel()
                .rotationY(90).modelFile(doubleModel).nextModel()
                .rotationY(180).modelFile(doubleModel).nextModel()
                .rotationY(270).modelFile(doubleModel)
                .addModel();
    }

    private void getSlabModelQuasiAxisRotate(Block block, String path, String textureBasic, String textureSide) {

        List<Float> bottomEl = Arrays.asList(0.0f, 8.0f, 8.0f, 8.0f);
        List<Float> topEl = Arrays.asList(8.0f, 16.0f, 8.0f, 8.0f);
        List<Float> doubleEl  = Arrays.asList(0.0f, 16.0f, 16.0f, 0.0f);

        //rotate_muddy = 0
        ModelFile bottomSlabAxisY  = makeSlabOrBlockDiffFacesQuasiAxisY(bottomEl, path, textureBasic, textureSide);
        ModelFile topSlabAxisY     = makeSlabOrBlockDiffFacesQuasiAxisY(topEl, path.concat("_top"), textureBasic, textureSide);
        ModelFile doubleSlabAxisY  = makeSlabOrBlockDiffFacesQuasiAxisY(doubleEl, path.concat("_block"), textureBasic, textureSide);
        //rotate_muddy = 1
        ModelFile bottomSlabAxisX  = makeSlabOrBlockDiffFacesQuasiAxisX(bottomEl, path.concat("_x"), textureBasic, textureSide);
        ModelFile topSlabAxisX     = makeSlabOrBlockDiffFacesQuasiAxisX(topEl, path.concat("_top_x"), textureBasic, textureSide);
        ModelFile doubleSlabAxisX  = makeSlabOrBlockDiffFacesQuasiAxisX(doubleEl, path.concat("_block_x"),  textureBasic, textureSide);
        //rotate_muddy = 2
        ModelFile bottomSlabAxisZ  = makeSlabOrBlockDiffFacesQuasiAxisZ(bottomEl, path.concat("_z"), textureBasic, textureSide);
        ModelFile topSlabAxisZ     = makeSlabOrBlockDiffFacesQuasiAxisZ(topEl, path.concat("_top_z"), textureBasic, textureSide);
        ModelFile doubleSlabAxisZ  = makeSlabOrBlockDiffFacesQuasiAxisZ(doubleEl, path.concat("_block_z"), textureBasic, textureSide);

        getSlabTypeRotateVariantQuasiAxis(block, bottomSlabAxisY, topSlabAxisY, doubleSlabAxisY, bottomSlabAxisX, topSlabAxisX, doubleSlabAxisX, bottomSlabAxisZ, topSlabAxisZ, doubleSlabAxisZ);
    }

    private void getSlabTypeRotateVariantQuasiAxis(Block block, ModelFile bottomModelY, ModelFile topModelY, ModelFile doubleModelY, ModelFile bottomModelX, ModelFile topModelX, ModelFile doubleModelX, ModelFile bottomModelZ, ModelFile topModelZ, ModelFile doubleModelZ) {

        getVariantBuilder(block)
                .partialState().with(TYPE, BOTTOM).with(ROTATE_MUDDY, 0).modelForState().modelFile(bottomModelY).addModel()
                .partialState().with(TYPE, BOTTOM).with(ROTATE_MUDDY, 1).modelForState().modelFile(bottomModelX).addModel()
                .partialState().with(TYPE, BOTTOM).with(ROTATE_MUDDY, 2).modelForState().modelFile(bottomModelZ).addModel()
                .partialState().with(TYPE, TOP).with(ROTATE_MUDDY, 0).modelForState().modelFile(topModelY).addModel()
                .partialState().with(TYPE, TOP).with(ROTATE_MUDDY, 1).modelForState().modelFile(topModelX).addModel()
                .partialState().with(TYPE, TOP).with(ROTATE_MUDDY, 2).modelForState().modelFile(topModelZ).addModel()
                .partialState().with(TYPE, DOUBLE).with(ROTATE_MUDDY, 0).modelForState().modelFile(doubleModelY).addModel()
                .partialState().with(TYPE, DOUBLE).with(ROTATE_MUDDY, 1).modelForState().modelFile(doubleModelX).addModel()
                .partialState().with(TYPE, DOUBLE).with(ROTATE_MUDDY, 2).modelForState().modelFile(doubleModelZ).addModel();
    }

    private void getSlabTypeMoist(Block block, ConfiguredModel bottomModel, ConfiguredModel topModel, ConfiguredModel doubleModel, ConfiguredModel bottomMoistModel, ConfiguredModel topMoistModel, ConfiguredModel doubleMoistModel) {
        getVariantBuilder(block)
                .partialState().with(TYPE, BOTTOM).with(MOISTURE, 0).addModels(bottomModel)
                .partialState().with(TYPE, BOTTOM).with(MOISTURE, 1).addModels(bottomModel)
                .partialState().with(TYPE, BOTTOM).with(MOISTURE, 2).addModels(bottomModel)
                .partialState().with(TYPE, BOTTOM).with(MOISTURE, 3).addModels(bottomModel)
                .partialState().with(TYPE, BOTTOM).with(MOISTURE, 4).addModels(bottomModel)
                .partialState().with(TYPE, BOTTOM).with(MOISTURE, 5).addModels(bottomModel)
                .partialState().with(TYPE, BOTTOM).with(MOISTURE, 6).addModels(bottomModel)
                .partialState().with(TYPE, BOTTOM).with(MOISTURE, 7).addModels(bottomMoistModel)

                .partialState().with(TYPE, TOP).with(MOISTURE, 0).addModels(topModel)
                .partialState().with(TYPE, TOP).with(MOISTURE, 1).addModels(topModel)
                .partialState().with(TYPE, TOP).with(MOISTURE, 2).addModels(topModel)
                .partialState().with(TYPE, TOP).with(MOISTURE, 3).addModels(topModel)
                .partialState().with(TYPE, TOP).with(MOISTURE, 4).addModels(topModel)
                .partialState().with(TYPE, TOP).with(MOISTURE, 5).addModels(topModel)
                .partialState().with(TYPE, TOP).with(MOISTURE, 6).addModels(topModel)
                .partialState().with(TYPE, TOP).with(MOISTURE, 7).addModels(topMoistModel)

                .partialState().with(TYPE, DOUBLE).with(MOISTURE, 0).addModels(doubleModel)
                .partialState().with(TYPE, DOUBLE).with(MOISTURE, 1).addModels(doubleModel)
                .partialState().with(TYPE, DOUBLE).with(MOISTURE, 2).addModels(doubleModel)
                .partialState().with(TYPE, DOUBLE).with(MOISTURE, 3).addModels(doubleModel)
                .partialState().with(TYPE, DOUBLE).with(MOISTURE, 4).addModels(doubleModel)
                .partialState().with(TYPE, DOUBLE).with(MOISTURE, 5).addModels(doubleModel)
                .partialState().with(TYPE, DOUBLE).with(MOISTURE, 6).addModels(doubleModel)
                .partialState().with(TYPE, DOUBLE).with(MOISTURE, 7).addModels(doubleMoistModel);
    }

    private void getLeavesModel (Block block, String path) {


        if (block.defaultBlockState() == ACACIA_LEAVES_BLOCK.get().defaultBlockState())
            getLeavesVariantWithAlter(block, path, "minecraft:block/acacia_leaves", "grasses:block/vanilla_leaves/acacia_leaves_alter");
        else if (block.defaultBlockState() == AZALEA_LEAVES_BLOCK.get().defaultBlockState())
            getLeavesVariantWithAlter(block, path, "grasses:block/vanilla_leaves/azalea_leaves", "grasses:block/vanilla_leaves/azalea_leaves_alter");
        else if (block.defaultBlockState() == BIRCH_LEAVES_BLOCK.get().defaultBlockState())
            getLeavesVariantWithAlter(block, path, "minecraft:block/birch_leaves", "grasses:block/vanilla_leaves/birch_leaves_alter");
        else if (block.defaultBlockState() == CHERRY_LEAVES_BLOCK.get().defaultBlockState())
            getLeavesVariantWithOverlayAndAlter(block, path, "grasses:block/vanilla_leaves/cherry_leaves", "grasses:block/vanilla_leaves/cherry_leaves_alter", "grasses:block/vanilla_leaves/cherry_leaves_overlay");
        else if (block.defaultBlockState() == DARK_OAK_LEAVES_BLOCK.get().defaultBlockState())
            getLeavesVariantWithAlter(block, path, "minecraft:block/dark_oak_leaves", "grasses:block/vanilla_leaves/dark_oak_leaves_alter");
        else if (block.defaultBlockState() == FLOWERING_AZALEA_LEAVES_BLOCK.get().defaultBlockState())
            getLeavesVariantWithOverlayAndAlter(block, path, "grasses:block/vanilla_leaves/flowering_azalea_leaves", "grasses:block/vanilla_leaves/flowering_azalea_leaves_alter", "grasses:block/vanilla_leaves/flowering_azalea_leaves_overlay");
        else if (block.defaultBlockState() == JUNGLE_LEAVES_BLOCK.get().defaultBlockState())
            getDoubleLeavesVariantWithOverlayAndAlter(block, path, "minecraft:block/jungle_leaves", "grasses:block/vanilla_leaves/jungle_leaves_light", "grasses:block/vanilla_leaves/jungle_leaves_alt", "grasses:block/vanilla_leaves/jungle_leaves_alt_light", "grasses:block/vanilla_leaves/jungle_leaves_overlay");
        else if (block.defaultBlockState() == MANGROVE_LEAVES_BLOCK.get().defaultBlockState())
            getLeavesVariantWithAlter(block, path, "minecraft:block/mangrove_leaves", "grasses:block/vanilla_leaves/mangrove_leaves_alter");
        else if (block.defaultBlockState() == OAK_LEAVES_BLOCK.get().defaultBlockState())
            getLeavesVariantWithAlter(block, path, "minecraft:block/oak_leaves", "grasses:block/vanilla_leaves/oak_leaves_alter");
        else if (block.defaultBlockState() == SPRUCE_LEAVES_BLOCK.get().defaultBlockState())
            getLeavesVariantWithAlter(block, path, "minecraft:block/spruce_leaves", "grasses:block/vanilla_leaves/spruce_leaves_alter");
        else if (isBOPLoaded && tintedBOPleavesBlocksList.contains(block)) {
            if (block.defaultBlockState() == FIR_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithAlter(block, path, "grasses:block/bop/fir_leaves", "grasses:block/bop/fir_leaves_alter");
            else if (block.defaultBlockState() == PINE_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithOverlayAndAlter(block, path, "grasses:block/bop/pine_leaves", "grasses:block/bop/pine_leaves_alter", "grasses:block/bop/pine_leaves_overlay");
            else if (block.defaultBlockState() == MAPLE_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithAlter(block, path, "grasses:block/bop/maple_leaves", "grasses:block/bop/maple_leaves_alter");
            else if (block.defaultBlockState() == REDWOOD_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithAlter(block, path, "grasses:block/bop/redwood_leaves", "grasses:block/bop/redwood_leaves_alter");
            else if (block.defaultBlockState() == MAHOGANY_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithAlter(block, path, "grasses:block/bop/mahogany_leaves", "grasses:block/bop/mahogany_leaves_alter");
            else if (block.defaultBlockState() == JACARANDA_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithOverlayAndAlter(block, path, "grasses:block/bop/jacaranda_leaves", "grasses:block/bop/jacaranda_leaves_alter", "grasses:block/bop/jacaranda_leaves_overlay");
            else if (block.defaultBlockState() == PALM_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithAlter(block, path, "grasses:block/bop/palm_leaves", "grasses:block/bop/palm_leaves_alter");
            else if (block.defaultBlockState() == WILLOW_LEAVES_BLOCK.get().defaultBlockState())
                getWillowLeavesVariantWithAlter(block, path);
            else if (block.defaultBlockState() == DEAD_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithOverlayAndAlter(block, path, "grasses:block/bop/dead_leaves", "grasses:block/bop/dead_leaves_alter", "grasses:block/bop/dead_leaves_overlay");
            else if (block.defaultBlockState() == MAGIC_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithAlter(block, path, "grasses:block/bop/magic_leaves", "grasses:block/bop/magic_leaves_alter", "grasses:block/bop/magic_leaves_alt", "grasses:block/bop/magic_leaves_alt_alter");
            else if (block.defaultBlockState() == UMBRAN_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithAlter(block, path, "grasses:block/bop/umbran_leaves", "grasses:block/bop/umbran_leaves_alter");
            else if (block.defaultBlockState() == EMPYREAL_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithAlter(block, path, "grasses:block/bop/empyreal_leaves", "grasses:block/bop/empyreal_leaves_alter");
            else if (block.defaultBlockState() == FLOWERING_OAK_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithOverlayAndAlter(block, path, "grasses:block/bop/flowering_oak_leaves", "grasses:block/bop/flowering_oak_leaves_alter", "grasses:block/bop/flowering_oak_leaves_overlay");
            else if (block.defaultBlockState() == ORIGIN_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithAlter(block, path, "grasses:block/bop/origin_leaves", "grasses:block/bop/origin_leaves_alter");
            else if (block.defaultBlockState() == CYPRESS_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithAlter(block, path, "grasses:block/bop/cypress_leaves", "grasses:block/bop/cypress_leaves_alter");
            else if (block.defaultBlockState() == HELLBARK_LEAVES_BLOCK.get().defaultBlockState())
                getLeavesVariantWithAlter(block, path, "grasses:block/bop/hellbark_leaves", "grasses:block/bop/hellbark_leaves_alter");
        }
    }

    protected void getLeavesVariantWithAlter(Block block, String path, String leavesTexture, String alterTexture) {
        ModelFile modelLeavesStandard = makeTintedLeavesBlock(path, leavesTexture);
        ModelFile modelLeavesLight = makeTintedLeavesBlock(path.concat("_alter"), alterTexture);

        getVariantBuilder(block)
                .partialState().with(ALTER, Boolean.FALSE).modelForState().modelFile(modelLeavesStandard).addModel()
                .partialState().with(ALTER, Boolean.TRUE).modelForState().modelFile(modelLeavesLight).addModel();
    }

    protected void getLeavesVariantWithAlter(Block block, String path, String leavesTexture, String alterTexture, String leavesTextureAlt, String alterTextureAlt) {
        ModelFile modelLeavesStandard = makeTintedLeavesBlock(path, leavesTexture);
        ModelFile modelLeavesLight = makeTintedLeavesBlock(path.concat("_alter"), alterTexture);
        ModelFile modelLeavesStandardAlt = makeTintedLeavesBlock(path.concat("_alt"), leavesTextureAlt);
        ModelFile modelLeavesLightAlt = makeTintedLeavesBlock(path.concat("_alter_alt"), alterTextureAlt);

        getVariantBuilder(block)
                .partialState().with(ALTER, Boolean.FALSE).modelForState().modelFile(modelLeavesStandard).nextModel().modelFile(modelLeavesStandardAlt).addModel()
                .partialState().with(ALTER, Boolean.TRUE).modelForState().modelFile(modelLeavesLight).nextModel().modelFile(modelLeavesLightAlt).addModel();
    }

    protected void getLeavesVariantWithOverlayAndAlter(Block block, String path, String leavesTexture, String alterTexture, String overlay) {
        ModelFile modelLeavesStandard = makeTintedOverlayLeavesBlock(path, leavesTexture, overlay);
        ModelFile modelLeavesLight = makeTintedOverlayLeavesBlock(path.concat("_light"), alterTexture, overlay);

        getVariantBuilder(block)
                .partialState().with(ALTER, Boolean.FALSE).modelForState().modelFile(modelLeavesStandard).addModel()
                .partialState().with(ALTER, Boolean.TRUE).modelForState().modelFile(modelLeavesLight).addModel();
    }

    protected void getDoubleLeavesVariantWithOverlayAndAlter(Block block, String path, String leavesTexture, String lightTexture, String leavesTextureAlt, String lightTextureAlt, String overlay) {
        ModelFile modelLeavesStandard = makeTintedLeavesBlock(path, leavesTexture);
        ModelFile modelLeavesLight = makeTintedLeavesBlock(path.concat("_light"), lightTexture);

        ModelFile modelLeavesStandardAlt = makeTintedOverlayLeavesBlock(path.concat("alt"), leavesTextureAlt, overlay);
        ModelFile modelLeavesLightAlt = makeTintedOverlayLeavesBlock(path.concat("_light_alt"), lightTextureAlt, overlay);

        getVariantBuilder(block)
                .partialState().with(DOUBLE_ALTER, 0).modelForState().modelFile(modelLeavesStandard).addModel()
                .partialState().with(DOUBLE_ALTER, 1).modelForState().modelFile(modelLeavesLight).addModel()
                .partialState().with(DOUBLE_ALTER, 2).modelForState().modelFile(modelLeavesStandardAlt).addModel()
                .partialState().with(DOUBLE_ALTER, 3).modelForState().modelFile(modelLeavesLightAlt).addModel();
    }

    protected void getWillowLeavesVariantWithAlter(Block block, String path) {
        ModelFile modelNoMossyStandard = makeTintedLeavesBlock(path, "grasses:block/bop/willow_leaves");
        ModelFile modelNoMossyAlter = makeTintedLeavesBlock(path.concat("_light"), "grasses:block/bop/willow_leaves_alter");
        ModelFile modelMossyStandard = makeTintedOverlayLeavesBlock(path.concat("_mossy"), "grasses:block/bop/willow_leaves_mossy", "grasses:block/bop/willow_leaves_mossy_overlay");
        ModelFile modelMossyAlter = makeTintedOverlayLeavesBlock(path.concat("_light_mossy"), "grasses:block/bop/willow_leaves_mossy_alter", "grasses:block/bop/willow_leaves_mossy_overlay");

        getVariantBuilder(block)
                .partialState().with(MOSSY, Boolean.FALSE).with(ALTER, Boolean.FALSE).modelForState().modelFile(modelNoMossyStandard).addModel()
                .partialState().with(MOSSY, Boolean.FALSE).with(ALTER, Boolean.TRUE).modelForState().modelFile(modelNoMossyAlter).addModel()
                .partialState().with(MOSSY, Boolean.TRUE).with(ALTER, Boolean.FALSE).modelForState().modelFile(modelMossyStandard).addModel()
                .partialState().with(MOSSY, Boolean.TRUE).with(ALTER, Boolean.TRUE).modelForState().modelFile(modelMossyAlter).addModel();
    }

    private void getMultipartVine(Block block, ModelFile modelVine) {
        getMultipartBuilder(block)
                .part().modelFile(modelVine).addModel().condition(BlockStateProperties.NORTH, true).end()
                .part().modelFile(modelVine).addModel()
                .condition(BlockStateProperties.NORTH, false)
                .condition(BlockStateProperties.EAST, false)
                .condition(BlockStateProperties.SOUTH, false)
                .condition(BlockStateProperties.WEST, false)
                .condition(BlockStateProperties.UP, false)
                .end()
                .part().modelFile(modelVine).rotationY(90).uvLock(true).addModel().condition(BlockStateProperties.EAST, true).end()
                .part().modelFile(modelVine).rotationY(90).uvLock(true).addModel()
                .condition(BlockStateProperties.NORTH, false)
                .condition(BlockStateProperties.EAST, false)
                .condition(BlockStateProperties.SOUTH, false)
                .condition(BlockStateProperties.WEST, false)
                .condition(BlockStateProperties.UP, false)
                .end()
                .part().modelFile(modelVine).rotationY(180).uvLock(true).addModel().condition(BlockStateProperties.SOUTH, true).end()
                .part().modelFile(modelVine).rotationY(180).uvLock(true).addModel()
                .condition(BlockStateProperties.NORTH, false)
                .condition(BlockStateProperties.EAST, false)
                .condition(BlockStateProperties.SOUTH, false)
                .condition(BlockStateProperties.WEST, false)
                .condition(BlockStateProperties.UP, false)
                .end()
                .part().modelFile(modelVine).rotationY(270).uvLock(true).addModel().condition(BlockStateProperties.WEST, true).end()
                .part().modelFile(modelVine).rotationY(270).uvLock(true).addModel()
                .condition(BlockStateProperties.NORTH, false)
                .condition(BlockStateProperties.EAST, false)
                .condition(BlockStateProperties.SOUTH, false)
                .condition(BlockStateProperties.WEST, false)
                .condition(BlockStateProperties.UP, false)
                .end()
                .part().modelFile(modelVine).rotationX(270).uvLock(true).addModel().condition(BlockStateProperties.UP, true).end()
                .part().modelFile(modelVine).rotationX(270).uvLock(true).addModel()
                .condition(BlockStateProperties.NORTH, false)
                .condition(BlockStateProperties.EAST, false)
                .condition(BlockStateProperties.SOUTH, false)
                .condition(BlockStateProperties.WEST, false)
                .condition(BlockStateProperties.UP, false)
                .end();
    }

    private void getRotateVariant (Block block, ModelFile modelFile) {
        getVariantBuilder(block)
                .partialState()
                .modelForState()
                .modelFile(modelFile).nextModel()
                .rotationY(90).modelFile(modelFile).nextModel()
                .rotationY(180).modelFile(modelFile).nextModel()
                .rotationY(270).modelFile(modelFile)
                .addModel();
    }

    private void getSugarCaneVariant(Block block, String path) {

        ConfiguredModel modelSugarCane = new ConfiguredModel(makeTintedPlantBlock(path, "grasses:block/sugar_cane_tinted"));
        ConfiguredModel modelSugarCaneBiomes = new ConfiguredModel(makeTintedPlantBlock(path.concat("_biomes"), "grasses:block/sugar_cane_tinted_biomes"));

        getVariantBuilder(block)
                .partialState().with(BIOMES_COLOR_SOURCE, Boolean.FALSE).addModels(modelSugarCane)
                .partialState().with(BIOMES_COLOR_SOURCE, Boolean.TRUE).addModels(modelSugarCaneBiomes);
    }

    private void getMultipartBamboo(Block block, String path) {

        final List<Float> age0 = Arrays.asList(7f, 0f, 7f, 9f, 16f, 9f, 13f, 4f, 15f, 6f, 13f, 0f, 15f, 2f);
        final List<Float> age1 = Arrays.asList(6.5f, 0f, 6.5f, 9.5f, 16f, 9.5f, 13f, 4f, 16f, 7f, 13f, 0f, 16f, 3f);

        ModelFile modelBamboo1_age0 = makeTintedBambooAge(path.concat("1_age0"), age0, 0f, 2f, "bamboo1_age0");
        ModelFile modelBamboo2_age0 = makeTintedBambooAge(path.concat("2_age0"), age0, 3f, 5f, "bamboo2_age0");
        ModelFile modelBamboo3_age0 = makeTintedBambooAge(path.concat("3_age0"), age0, 6f, 8f, "bamboo3_age0");
        ModelFile modelBamboo4_age0 = makeTintedBambooAge(path.concat("4_age0"), age0, 9f, 11f, "bamboo4_age0");

        ModelFile modelBamboo1_age1 = makeTintedBambooAge(path.concat("1_age1"), age1, 0f, 3f, "bamboo1_age1");
        ModelFile modelBamboo2_age1 = makeTintedBambooAge(path.concat("2_age1"), age1, 0f, 6f, "bamboo2_age1");
        ModelFile modelBamboo3_age1 = makeTintedBambooAge(path.concat("3_age1"), age1, 6f, 9f, "bamboo3_age1");
        ModelFile modelBamboo4_age1 = makeTintedBambooAge(path.concat("4_age1"), age1, 9f, 12f, "bamboo4_age1");
        ModelFile modelBamboo_small = makeTintedBambooLeaves(path.concat("_small_leaves"), "grasses:block/bamboo_small_leaves", "bamboo_small_leaves");
        ModelFile modelBamboo_large = makeTintedBambooLeaves(path.concat("_large_leaves"), "grasses:block/bamboo_large_leaves", "bamboo_large_leaves");

        getMultipartBuilder(block)
                .part().modelFile(modelBamboo1_age0).addModel().condition(BlockStateProperties.AGE_1, 0).end()
                .part().modelFile(modelBamboo2_age0).addModel().condition(BlockStateProperties.AGE_1, 0).end()
                .part().modelFile(modelBamboo3_age0).addModel().condition(BlockStateProperties.AGE_1, 0).end()
                .part().modelFile(modelBamboo4_age0).addModel().condition(BlockStateProperties.AGE_1, 0).end()

                .part().modelFile(modelBamboo1_age1).addModel().condition(BlockStateProperties.AGE_1, 1).end()
                .part().modelFile(modelBamboo2_age1).addModel().condition(BlockStateProperties.AGE_1, 1).end()
                .part().modelFile(modelBamboo3_age1).addModel().condition(BlockStateProperties.AGE_1, 1).end()
                .part().modelFile(modelBamboo4_age1).addModel().condition(BlockStateProperties.AGE_1, 1).end()

                .part().modelFile(modelBamboo_small).addModel().condition(BlockStateProperties.BAMBOO_LEAVES, BambooLeaves.SMALL).end()
                .part().modelFile(modelBamboo_large).addModel().condition(BlockStateProperties.BAMBOO_LEAVES, BambooLeaves.LARGE).end();
    }

    private void getBigDripLeafVariant(Block block, String path) {
        ModelFile modelDripLeaf = makeTintedBigDripLeafBlock(path);
        ModelFile modelDripLeafPartialTilt = makeTintedBigDripLeafTiltBlock(path.concat("_partial_tilt"), -22.5f);
        ModelFile modelDripLeafFullTilt = makeTintedBigDripLeafTiltBlock(path.concat("_full_tilt"), -45f);

        getVariantBuilder(block)
                .partialState().with(FACING, EAST).with(TILT, FULL).modelForState().rotationY(90).modelFile(modelDripLeafFullTilt).addModel()
                .partialState().with(FACING, EAST).with(TILT, NONE).modelForState().rotationY(90).modelFile(modelDripLeaf).addModel()
                .partialState().with(FACING, EAST).with(TILT, PARTIAL).modelForState().rotationY(90).modelFile(modelDripLeafPartialTilt).addModel()
                .partialState().with(FACING, EAST).with(TILT, UNSTABLE).modelForState().rotationY(90).modelFile(modelDripLeaf).addModel()

                .partialState().with(FACING, NORTH).with(TILT, FULL).modelForState().modelFile(modelDripLeafFullTilt).addModel()
                .partialState().with(FACING, NORTH).with(TILT, NONE).modelForState().modelFile(modelDripLeaf).addModel()
                .partialState().with(FACING, NORTH).with(TILT, PARTIAL).modelForState().modelFile(modelDripLeafPartialTilt).addModel()
                .partialState().with(FACING, NORTH).with(TILT, UNSTABLE).modelForState().modelFile(modelDripLeaf).addModel()

                .partialState().with(FACING, SOUTH).with(TILT, FULL).modelForState().rotationY(180).modelFile(modelDripLeafFullTilt).addModel()
                .partialState().with(FACING, SOUTH).with(TILT, NONE).modelForState().rotationY(180).modelFile(modelDripLeaf).addModel()
                .partialState().with(FACING, SOUTH).with(TILT, PARTIAL).modelForState().rotationY(180).modelFile(modelDripLeafPartialTilt).addModel()
                .partialState().with(FACING, SOUTH).with(TILT, UNSTABLE).modelForState().rotationY(180).modelFile(modelDripLeaf).addModel()

                .partialState().with(FACING, WEST).with(TILT, FULL).modelForState().rotationY(270).modelFile(modelDripLeafFullTilt).addModel()
                .partialState().with(FACING, WEST).with(TILT, NONE).modelForState().rotationY(270).modelFile(modelDripLeaf).addModel()
                .partialState().with(FACING, WEST).with(TILT, PARTIAL).modelForState().rotationY(270).modelFile(modelDripLeafPartialTilt).addModel()
                .partialState().with(FACING, WEST).with(TILT, UNSTABLE).modelForState().rotationY(270).modelFile(modelDripLeaf).addModel();
    }

    private void getSmallDripLeafVariant(Block block, String path) {
        ModelFile modelDripLeafTop = makeTintedSmallDripLeafBlockTop(path.concat("_top"));
        ModelFile modelDripLeafBottom = makeTintedSmallDripLeafBlockBottom(path.concat("_bottom"));

        getVariantBuilder(block)
                .partialState().with(FACING, EAST).with(HALF, LOWER).modelForState().rotationY(90).modelFile(modelDripLeafBottom).addModel()
                .partialState().with(FACING, EAST).with(HALF, UPPER).modelForState().rotationY(90).modelFile(modelDripLeafTop).addModel()

                .partialState().with(FACING, NORTH).with(HALF, LOWER).modelForState().modelFile(modelDripLeafBottom).addModel()
                .partialState().with(FACING, NORTH).with(HALF, UPPER).modelForState().modelFile(modelDripLeafTop).addModel()

                .partialState().with(FACING, SOUTH).with(HALF, LOWER).modelForState().rotationY(180).modelFile(modelDripLeafBottom).addModel()
                .partialState().with(FACING, SOUTH).with(HALF, UPPER).modelForState().rotationY(180).modelFile(modelDripLeafTop).addModel()

                .partialState().with(FACING, WEST).with(HALF, LOWER).modelForState().rotationY(270).modelFile(modelDripLeafBottom).addModel()
                .partialState().with(FACING, WEST).with(HALF, UPPER).modelForState().rotationY(270).modelFile(modelDripLeafTop).addModel();

    }

    private void getPottedSugarCaneVariant(Block block, String path) {

        ConfiguredModel modelSugarCane = new ConfiguredModel(makeTintedPottedBlock(path, "sugar_cane_potted_tinted", "minecraft:block/dirt"));
        ConfiguredModel modelSugarCaneBiomes = new ConfiguredModel(makeTintedPottedBlock(path.concat("_biomes"), "sugar_cane_potted_tinted_biomes", "minecraft:block/dirt"));

        getVariantBuilder(block)
                .partialState().with(BIOMES_COLOR_SOURCE, Boolean.FALSE).addModels(modelSugarCane)
                .partialState().with(BIOMES_COLOR_SOURCE, Boolean.TRUE).addModels(modelSugarCaneBiomes);
    }

    protected void getFacingRotateVariant(Block block, String path) {
        ModelFile modelFacingRotate;

        if (isBOPLoaded && block == HUGE_CLOVER_TINTED.get())
            modelFacingRotate = makeTintedHugeClover(path);
        else if (block == BIG_DRIP_LEAF_POTTED_TINTED.get())
            modelFacingRotate = makeTintedPottedBigDripLeafBlock(path);
        else if (block == SMALL_DRIP_LEAF_POTTED_TINTED.get())
            modelFacingRotate = makeTintedPottedSmallDripLeafBlock(path);
        else if (block == BIG_DRIP_LEAF_POTTED.get())
            modelFacingRotate = makePottedBigDripLeafBlock(path);
        else if (block == SMALL_DRIP_LEAF_POTTED.get())
            modelFacingRotate = makePottedSmallDripLeafBlock(path);
        else
            modelFacingRotate = makeTintedBigDripLeafStemBlock(path);

        getVariantBuilder(block)
                .partialState().with(FACING, EAST).modelForState().rotationY(90).modelFile(modelFacingRotate).addModel()
                .partialState().with(FACING, NORTH).modelForState().modelFile(modelFacingRotate).addModel()
                .partialState().with(FACING, SOUTH).modelForState().rotationY(180).modelFile(modelFacingRotate).addModel()
                .partialState().with(FACING, WEST).modelForState().rotationY(270).modelFile(modelFacingRotate).addModel();
    }

    private void getEndFramePortalVariant(Block block, String path) {
        ModelFile modelEndFrame = makePortalFrameBlock(path);
        ModelFile modelFilledEndFrame = makeFilledPortalFrameBlock(path.concat("_filled"));

        getVariantBuilder(block)
                .partialState().with(FACING, EAST).with(HAS_EYE, Boolean.FALSE).modelForState().rotationY(270).modelFile(modelEndFrame).addModel()
                .partialState().with(FACING, NORTH).with(HAS_EYE, Boolean.FALSE).modelForState().rotationY(180).modelFile(modelEndFrame).addModel()
                .partialState().with(FACING, SOUTH).with(HAS_EYE, Boolean.FALSE).modelForState().modelFile(modelEndFrame).addModel()
                .partialState().with(FACING, WEST).with(HAS_EYE, Boolean.FALSE).modelForState().rotationY(90).modelFile(modelEndFrame).addModel()

                .partialState().with(FACING, EAST).with(HAS_EYE, Boolean.TRUE).modelForState().rotationY(270).modelFile(modelFilledEndFrame).addModel()
                .partialState().with(FACING, NORTH).with(HAS_EYE, Boolean.TRUE).modelForState().rotationY(180).modelFile(modelFilledEndFrame).addModel()
                .partialState().with(FACING, SOUTH).with(HAS_EYE, Boolean.TRUE).modelForState().modelFile(modelFilledEndFrame).addModel()
                .partialState().with(FACING, WEST).with(HAS_EYE, Boolean.TRUE).modelForState().rotationY(90).modelFile(modelFilledEndFrame).addModel();
    }

    private void getVineInBarsVariant (Block block, String path, String plant) {

        ModelFile inBarsCap      = makeInBarsCap(path.concat("_cap"));
        ModelFile inBarsCapAlt   = makeInBarsCapAlt(path.concat("_cap_alt"));
        ModelFile inBarsPost     = makeInBarsPost(path.concat("_post"));
        ModelFile inBarsPostEnds = makeInBarsPostEnds(path.concat("_post_ends"));
        ModelFile inBarsSide     = makeInBarsSide(path.concat("_side"));
        ModelFile inBarsSideAlt  = makeInBarsSideAlt(path.concat("_side_alt"));

        ModelFile vineInBarsStraight        = makeTintedVineBlockForBarsStraight(path.concat("_straight"), plant);
        ModelFile vineInBarsStraightDouble  = makeTintedVineBlockForBarsStraightDouble(path.concat("_straight_double"), plant);
        ModelFile vineInBarsInnerTurn       = makeTintedVineBlockForBarsInnerTurn(path.concat("_inner"), plant);
        ModelFile vineInBarsOuterTurn       = makeTintedVineBlockForBarsOuterTurn(path.concat("_outer"), plant);
        ModelFile vineInBarsOuterTurnDouble = makeTintedVineBlockForBarsOuterTurnDouble(path.concat("_outer_double"), plant);
        ModelFile vineInBarsInnerTurnDouble = makeTintedVineBlockForBarsInnerTurnDouble(path.concat("_inner_double"), plant);
        ModelFile vineInBarsInnerT          = makeTintedVineBlockForBarsInnerT(path.concat("_inner_t"), plant);
        ModelFile vineInBarsLeftNotFullT    = makeTintedVineBlockForBarsLeftNotFullT(path.concat("lnf_inner_t"), plant);
        ModelFile vineInBarsRightNotFullT   = makeTintedVineBlockForBarsRightNotFullT(path.concat("rnf_inner_t"), plant);
        ModelFile vineInBarsFullT           = makeTintedVineBlockForBarsFullT(path.concat("_full_t"), plant);
        ModelFile vineInBarsTripleInner     = makeTintedVineBlockForBarsTripleInner(path.concat("_triple_inner"), plant);
        ModelFile vineInBarsQuadrupleInner  = makeTintedVineBlockForBarsQuadrupleInner(path.concat("_quadruple_inner"), plant);
        ModelFile vineInBarsDiagonalInner   = makeTintedVineBlockForBarsDiagonalInner(path.concat("_diagonal_inner"), plant);

        BooleanProperty north = BlockStateProperties.NORTH;
        BooleanProperty east = BlockStateProperties.EAST;
        BooleanProperty south = BlockStateProperties.SOUTH;
        BooleanProperty west = BlockStateProperties.WEST;
        ModelFile modelFileStraight;
        ModelFile modelFileOuter;
        ModelFile modelFileInner;
        ModelFile modelFileX;

        for (int i = 0; i < 2; i++) {
            if (i==0) {
                modelFileStraight = vineInBarsStraight;
                modelFileOuter = vineInBarsOuterTurn;
                modelFileInner = vineInBarsInnerTurn;
                modelFileX = vineInBarsInnerTurn;

            }
            else {
                modelFileStraight = vineInBarsStraightDouble;
                modelFileOuter = vineInBarsOuterTurnDouble;
                modelFileInner = vineInBarsInnerTurnDouble;
                modelFileX = vineInBarsInnerT;
            }

            getMultiPartVineInBarsStraight(block, modelFileStraight, 0, NORTH, i, Arrays.asList(south, north, west, east));
            getMultiPartVineInBarsStraight(block, modelFileStraight, 90, EAST, i, Arrays.asList(west, east, north, south));
            getMultiPartVineInBarsStraight(block, modelFileStraight, 180, SOUTH, i, Arrays.asList(north, south, east, west));
            getMultiPartVineInBarsStraight(block, modelFileStraight, 270, WEST, i, Arrays.asList(east, west, north, south));

            getMultiPartVineInBarsInner(block, modelFileInner, 0, NORTH, EAST, i, Arrays.asList(south, west, north, east));
            getMultiPartVineInBarsInner(block, modelFileInner, 90, EAST, SOUTH, i, Arrays.asList(north, west, south, east));
            getMultiPartVineInBarsInner(block, modelFileInner, 180, SOUTH, WEST, i, Arrays.asList(east, north, west, south));
            getMultiPartVineInBarsInner(block, modelFileInner, 270, WEST, NORTH, i, Arrays.asList(south, east, north, west));

            getMultiPartVineInBarsOuter(block, modelFileOuter, 0, NORTH, WEST, i, Arrays.asList(north, west, south, east));
            getMultiPartVineInBarsOuter(block, modelFileOuter, 90, EAST, NORTH, i, Arrays.asList(north, east, south, west));
            getMultiPartVineInBarsOuter(block, modelFileOuter, 180, SOUTH, EAST, i , Arrays.asList(east, south, north, west));
            getMultiPartVineInBarsOuter(block, modelFileOuter, 270, WEST, SOUTH, i, Arrays.asList(west, south, north, east));

            getMultiPartVineInBarsInnerForX(block, modelFileX, 0, NORTH, i, Arrays.asList(south, east, north, west));
            getMultiPartVineInBarsInnerForX(block, modelFileX, 90, EAST, i, Arrays.asList(south, west, north, east));
            getMultiPartVineInBarsInnerForX(block, modelFileX, 180, SOUTH, i, Arrays.asList(west, north, east, south));
            getMultiPartVineInBarsInnerForX(block, modelFileX, 270, WEST, i, Arrays.asList(east, north, west, south));

            if (i == 0) {
                getMultiPartVineInBarsStraightT(block, vineInBarsStraight, 0, NORTH, i, Arrays.asList(north, west, east, south));
                getMultiPartVineInBarsStraightT(block, vineInBarsStraight, 90, EAST, i, Arrays.asList(north, east, south, west));
                getMultiPartVineInBarsStraightT(block, vineInBarsStraight, 180, SOUTH, i, Arrays.asList(east, south, west, north));
                getMultiPartVineInBarsStraightT(block, vineInBarsStraight, 270, WEST, i, Arrays.asList(north, south, west, east));

                getMultiPartVineInBarsInnerT(block, vineInBarsInnerTurn, 0, NORTH, EAST, i, Arrays.asList(south, west, north, east), Arrays.asList(east, north));
                getMultiPartVineInBarsInnerT(block, vineInBarsInnerTurn, 90, EAST, SOUTH, i, Arrays.asList(north, west, east, south), Arrays.asList(south,  east));
                getMultiPartVineInBarsInnerT(block, vineInBarsInnerTurn, 180, SOUTH, WEST, i, Arrays.asList(north, east, south, west), Arrays.asList(west, south));
                getMultiPartVineInBarsInnerT(block, vineInBarsInnerTurn, 270, WEST, NORTH, i, Arrays.asList(south, east, west, north), Arrays.asList(north, west));

                getMultiPartVineInBarsDoubleInnerT(block, vineInBarsInnerT, 0, NORTH, i, Arrays.asList(south, west, east, north));
                getMultiPartVineInBarsDoubleInnerT(block, vineInBarsInnerT, 90, EAST, i, Arrays.asList(north, west, south, east));
                getMultiPartVineInBarsDoubleInnerT(block, vineInBarsInnerT, 180, SOUTH, i, Arrays.asList(north, east, west, south));
                getMultiPartVineInBarsDoubleInnerT(block, vineInBarsInnerT, 270, WEST, i, Arrays.asList(south, east, north, west));
            }
            else {
                getMultiPartVineInBarsFullT(block, vineInBarsFullT, 0, NORTH, SOUTH, i, Arrays.asList(south, west, east, north));
                getMultiPartVineInBarsFullT(block, vineInBarsFullT, 90, EAST, WEST, i, Arrays.asList(north, west, south, east));
                getMultiPartVineInBarsFullT(block, vineInBarsFullT, 180, SOUTH, NORTH, i, Arrays.asList(north, east, west, south));
                getMultiPartVineInBarsFullT(block, vineInBarsFullT, 270, WEST, EAST, i, Arrays.asList(south, east, north, west));

                getMultiPartVineInBarsDoubleInnerT(block, vineInBarsLeftNotFullT, 0, EAST, i, Arrays.asList(south, west, east, north));
                getMultiPartVineInBarsDoubleInnerT(block, vineInBarsLeftNotFullT, 90, SOUTH, i, Arrays.asList(north, west, south, east));
                getMultiPartVineInBarsDoubleInnerT(block, vineInBarsLeftNotFullT, 180, WEST, i, Arrays.asList(north, east, west, south));
                getMultiPartVineInBarsDoubleInnerT(block, vineInBarsLeftNotFullT, 270, NORTH, i, Arrays.asList(south, east, north, west));

                getMultiPartVineInBarsDoubleInnerT(block, vineInBarsRightNotFullT, 0, WEST, i, Arrays.asList(south, west, east, north));
                getMultiPartVineInBarsDoubleInnerT(block, vineInBarsRightNotFullT, 90, NORTH, i, Arrays.asList(north, west, south, east));
                getMultiPartVineInBarsDoubleInnerT(block, vineInBarsRightNotFullT, 180, EAST, i, Arrays.asList(north, east, west, south));
                getMultiPartVineInBarsDoubleInnerT(block, vineInBarsRightNotFullT, 270, SOUTH, i, Arrays.asList(south, east, north, west));


            }
        }

        getMultiPartVineInBarsInnerForX(block, vineInBarsTripleInner, 0, NORTH, 2, Arrays.asList(south, east, north, west));
        getMultiPartVineInBarsInnerForX(block, vineInBarsTripleInner, 90, EAST, 2, Arrays.asList(south, west, north, east));
        getMultiPartVineInBarsInnerForX(block, vineInBarsTripleInner, 180, SOUTH, 2, Arrays.asList(west, north, east, south));
        getMultiPartVineInBarsInnerForX(block, vineInBarsTripleInner, 270, WEST, 2, Arrays.asList(east, north, west, south));

        getMultiPartVineInBarsInnerForX(block, vineInBarsQuadrupleInner, 0, NORTH, 3, Arrays.asList(south, east, north, west));
        getMultiPartVineInBarsInnerForX(block, vineInBarsQuadrupleInner, 90, EAST, 3, Arrays.asList(south, west, north, east));
        getMultiPartVineInBarsInnerForX(block, vineInBarsQuadrupleInner, 180, SOUTH, 3, Arrays.asList(west, north, east, south));
        getMultiPartVineInBarsInnerForX(block, vineInBarsQuadrupleInner, 270, WEST, 3, Arrays.asList(east, north, west, south));

        getMultiPartVineInBarsInnerForX(block, vineInBarsDiagonalInner, 0, NORTH, 4, Arrays.asList(south, east, north, west));
        getMultiPartVineInBarsInnerForX(block, vineInBarsDiagonalInner, 90, EAST, 4, Arrays.asList(south, west, north, east));
        getMultiPartVineInBarsInnerForX(block, vineInBarsDiagonalInner, 180, SOUTH, 4, Arrays.asList(west, north, east, south));
        getMultiPartVineInBarsInnerForX(block, vineInBarsDiagonalInner, 270, WEST, 4, Arrays.asList(east, north, west, south));


        getMultipartBuilder(block)
            //BARS
            .part().modelFile(inBarsPostEnds).addModel()
            .end()
            .part().modelFile(inBarsPost).addModel()
                .condition(BlockStateProperties.NORTH, false)
                .condition(BlockStateProperties.EAST, false)
                .condition(BlockStateProperties.SOUTH, false)
                .condition(BlockStateProperties.WEST, false)
            .end()
            .part().modelFile(inBarsCap).addModel()
                .condition(BlockStateProperties.NORTH, true)
                .condition(BlockStateProperties.EAST, false)
                .condition(BlockStateProperties.SOUTH, false)
                .condition(BlockStateProperties.WEST, false)
            .end()
            .part().modelFile(inBarsCap).rotationY(90).addModel()
                .condition(BlockStateProperties.NORTH, false)
                .condition(BlockStateProperties.EAST, true)
                .condition(BlockStateProperties.SOUTH, false)
                .condition(BlockStateProperties.WEST, false)
            .end()
            .part().modelFile(inBarsCapAlt).addModel()
                .condition(BlockStateProperties.NORTH, false)
                .condition(BlockStateProperties.EAST, false)
                .condition(BlockStateProperties.SOUTH, true)
                .condition(BlockStateProperties.WEST, false)
            .end()
            .part().modelFile(inBarsCapAlt).rotationY(90).addModel()
                .condition(BlockStateProperties.NORTH, false)
                .condition(BlockStateProperties.EAST, false)
                .condition(BlockStateProperties.SOUTH, false)
                .condition(BlockStateProperties.WEST, true)
            .end()
            .part().modelFile(inBarsSide).addModel()
                .condition(BlockStateProperties.NORTH, true)
            .end()
            .part().modelFile(inBarsSide).rotationY(90).addModel()
                .condition(BlockStateProperties.EAST, true)
            .end()
            .part().modelFile(inBarsSideAlt).addModel()
                .condition(BlockStateProperties.SOUTH, true)
            .end()
            .part().modelFile(inBarsSideAlt).rotationY(90).addModel()
                .condition(BlockStateProperties.WEST, true)
            .end();
    }

    private void getMultiPartVineInBarsStraight(Block block, ModelFile vineInBars, int rotation, Direction direction, int clickedSide, List<BooleanProperty> propertiesList) {

        getMultipartBuilder(block)
                .part().modelFile(vineInBars).rotationY(rotation).uvLock(true).addModel()
                    .nestedGroup()
                        .condition(FACING, direction)
                        .condition(CLICKED_SIDE, clickedSide)
                        .condition(propertiesList.get(0), false)
                    .end()
                    .nestedGroup()
                        .useOr()
                        .nestedGroup()
                            .condition(propertiesList.get(1), false)
                            .condition(propertiesList.get(2), false, true)
                            .condition(propertiesList.get(3), false)
                        .endNestedGroup()
                        .nestedGroup()
                            .condition(propertiesList.get(1), false)
                            .condition(propertiesList.get(2), false, true)
                            .condition(propertiesList.get(3), true)
                        .endNestedGroup()
                    .end()
                .end();
    }

    private void getMultiPartVineInBarsStraightT(Block block, ModelFile vineInBars, int rotation, Direction direction, int clickedSide, List<BooleanProperty> propertiesList) {

        getMultipartBuilder(block)
                .part().modelFile(vineInBars).rotationY(rotation).uvLock(true).addModel()
                .nestedGroup()
                .condition(FACING, direction)
                .condition(CLICKED_SIDE, clickedSide)
                .condition(propertiesList.get(0), true)
                .condition(propertiesList.get(1), true)
                .condition(propertiesList.get(2), true)
                .condition(propertiesList.get(3), false)
                .end()
                .end();
    }

    private void getMultiPartVineInBarsInner(Block block, ModelFile vineInBars, int rotation, Direction stDirection, Direction ndDirection, int clickedSide, List<BooleanProperty> propertiesList) {

        getMultipartBuilder(block)
                .part().modelFile(vineInBars).rotationY(rotation).uvLock(true).addModel()
                .nestedGroup()
                    .condition(CLICKED_SIDE, clickedSide)
                    .condition(propertiesList.get(0), true)
                    .condition(propertiesList.get(1), true)
                    .condition(propertiesList.get(2), false)
                    .condition(propertiesList.get(3), false)
                .end()
                .nestedGroup()
                .useOr()
                    .nestedGroup()
                        .condition(FACING, stDirection)
                    .endNestedGroup()
                    .nestedGroup()
                        .condition(FACING, ndDirection)
                    .endNestedGroup()
                    .end()
                .end();
    }

    private void getMultiPartVineInBarsInnerForX(Block block, ModelFile vineInBars, int rotation, Direction direction, int clickedSide, List<BooleanProperty> propertiesList) {

        getMultipartBuilder(block)
                .part().modelFile(vineInBars).rotationY(rotation).uvLock(true).addModel()
                .nestedGroup()
                .condition(CLICKED_SIDE, clickedSide)
                .condition(FACING, direction)
                .condition(propertiesList.get(0), true)
                .condition(propertiesList.get(1), true)
                .condition(propertiesList.get(2), true)
                .condition(propertiesList.get(3), true)
                .end()
                .end();
    }

    private void getMultiPartVineInBarsInnerT(Block block, ModelFile vineInBars, int rotation, Direction stDirection, Direction ndDirection, int clickedSide, List<BooleanProperty> stPropertiesList, List<BooleanProperty> ndPropertiesList) {

        getMultipartBuilder(block)
                .part().modelFile(vineInBars).rotationY(rotation).uvLock(true).addModel()
                    .nestedGroup()
                        .condition(CLICKED_SIDE, clickedSide)
                        .condition(stPropertiesList.get(0), true)
                        .condition(stPropertiesList.get(1), true)
                    .end()
                    .nestedGroup()
                        .useOr()
                        .nestedGroup()
                            .condition(FACING, stDirection)
                            .condition(stPropertiesList.get(2), true)
                            .condition(stPropertiesList.get(3), false)
                        .endNestedGroup()
                        .nestedGroup()
                            .condition(FACING, ndDirection)
                            .condition(ndPropertiesList.get(0), true)
                            .condition(ndPropertiesList.get(1), false)
                        .endNestedGroup()
                    .end()
                .end();
    }

    private void getMultiPartVineInBarsOuter(Block block, ModelFile vineInBars, int rotation, Direction stDirection, Direction ndDirection, int clickedSide, List<BooleanProperty> propertiesList) {

        getMultipartBuilder(block)
                .part().modelFile(vineInBars).rotationY(rotation).uvLock(true).addModel()
                .nestedGroup()
                .condition(CLICKED_SIDE, clickedSide)
                .condition(propertiesList.get(0), true)
                .condition(propertiesList.get(1), true)
                .condition(propertiesList.get(2), false)
                .condition(propertiesList.get(3), false)
                .end()
                .nestedGroup()
                .useOr()
                .nestedGroup()
                .condition(FACING, stDirection)
                .endNestedGroup()
                .nestedGroup()
                .condition(FACING, ndDirection)
                .endNestedGroup()
                .end()
                .end();
    }

    private void getMultiPartVineInBarsDoubleInnerT(Block block, ModelFile vineInBars, int rotation, Direction direction, int clickedSide, List<BooleanProperty> propertiesList) {

        getMultipartBuilder(block)
                .part().modelFile(vineInBars).rotationY(rotation).uvLock(true).addModel()
                .nestedGroup()
                .condition(CLICKED_SIDE, clickedSide)
                .condition(FACING, direction)
                .condition(propertiesList.get(0), true)
                .condition(propertiesList.get(1), true)
                .condition(propertiesList.get(2), true)
                .condition(propertiesList.get(3), false)
                .end()
                .end();
    }

    private void getMultiPartVineInBarsFullT(Block block, ModelFile vineInBars, int rotation, Direction stDirection, Direction ndDirection, int clickedSide, List<BooleanProperty> propertiesList) {

        getMultipartBuilder(block)
                .part().modelFile(vineInBars).rotationY(rotation).uvLock(true).addModel()
                    .nestedGroup()
                        .condition(CLICKED_SIDE, clickedSide)
                        .condition(propertiesList.get(0), true)
                        .condition(propertiesList.get(1), true)
                        .condition(propertiesList.get(2), true)
                        .condition(propertiesList.get(3), false)
                    .end()
                    .nestedGroup()
                        .useOr()
                        .nestedGroup()
                            .condition(FACING, stDirection)
                        .endNestedGroup()
                        .nestedGroup()
                            .condition(FACING, ndDirection)
                        .endNestedGroup()
                .end();
    }

    private void getPlantInBarsVariant (Block block, String path, ModelFile plantInBars) {

    ModelFile inBarsCap      = makeInBarsCap(path.concat("_cap"));
    ModelFile inBarsCapAlt   = makeInBarsCapAlt(path.concat("_cap_alt"));
    ModelFile inBarsPost     = makeInBarsPost(path.concat("_post"));
    ModelFile inBarsPostEnds = makeInBarsPostEnds(path.concat("_post_ends"));
    ModelFile inBarsSide     = makeInBarsSide(path.concat("_side"));
    ModelFile inBarsSideAlt  = makeInBarsSideAlt(path.concat("_side_alt"));

    getMultipartBuilder(block)
            .part().modelFile(plantInBars).addModel()
            .end()

            .part().modelFile(inBarsPostEnds).addModel()
            .end()
            .part().modelFile(inBarsPost).addModel()
            .condition(BlockStateProperties.NORTH, false)
            .condition(BlockStateProperties.EAST, false)
            .condition(BlockStateProperties.SOUTH, false)
            .condition(BlockStateProperties.WEST, false)
            .end()
            .part().modelFile(inBarsCap).addModel()
            .condition(BlockStateProperties.NORTH, true)
            .condition(BlockStateProperties.EAST, false)
            .condition(BlockStateProperties.SOUTH, false)
            .condition(BlockStateProperties.WEST, false)
            .end()
            .part().modelFile(inBarsCap).rotationY(90).addModel()
            .condition(BlockStateProperties.NORTH, false)
            .condition(BlockStateProperties.EAST, true)
            .condition(BlockStateProperties.SOUTH, false)
            .condition(BlockStateProperties.WEST, false)
            .end()
            .part().modelFile(inBarsCapAlt).addModel()
            .condition(BlockStateProperties.NORTH, false)
            .condition(BlockStateProperties.EAST, false)
            .condition(BlockStateProperties.SOUTH, true)
            .condition(BlockStateProperties.WEST, false)
            .end()
            .part().modelFile(inBarsCapAlt).rotationY(90).addModel()
            .condition(BlockStateProperties.NORTH, false)
            .condition(BlockStateProperties.EAST, false)
            .condition(BlockStateProperties.SOUTH, false)
            .condition(BlockStateProperties.WEST, true)
            .end()
            .part().modelFile(inBarsSide).addModel()
            .condition(BlockStateProperties.NORTH, true)
            .end()
            .part().modelFile(inBarsSide).rotationY(90).addModel()
            .condition(BlockStateProperties.EAST, true)
            .end()
            .part().modelFile(inBarsSideAlt).addModel()
            .condition(BlockStateProperties.SOUTH, true)
            .end()
            .part().modelFile(inBarsSideAlt).rotationY(90).addModel()
            .condition(BlockStateProperties.WEST, true)
            .end();
    }

    private ModelFile getPottedLeavesModel (Block block, String path) {
        ModelFile modelLeaves;

        if (block.defaultBlockState() == ACACIA_LEAVES_POTTED.get().defaultBlockState() || block.defaultBlockState() == ACACIA_LEAVES_POTTED_TINTED.get().defaultBlockState())
            modelLeaves = makePottedLeavesBlock(path, "minecraft:block/acacia_leaves");
        else if (block.defaultBlockState() == AZALEA_LEAVES_POTTED.get().defaultBlockState() || block.defaultBlockState() == AZALEA_LEAVES_POTTED_TINTED.get().defaultBlockState())
            modelLeaves = makePottedLeavesBlock(path, "minecraft:block/azalea_leaves");
        else if (block.defaultBlockState() == BIRCH_LEAVES_POTTED.get().defaultBlockState() || block.defaultBlockState() == BIRCH_LEAVES_POTTED_TINTED.get().defaultBlockState())
            modelLeaves = makePottedLeavesBlock(path, "minecraft:block/birch_leaves");
        else if (block.defaultBlockState() == CHERRY_LEAVES_POTTED.get().defaultBlockState())
            modelLeaves = makePottedLeavesBlock(path, "minecraft:block/cherry_leaves");
        else if (block.defaultBlockState() == CHERRY_LEAVES_POTTED_TINTED.get().defaultBlockState())
            modelLeaves = makeTintedOverlayPottedLeavesBlock(path, "grasses:block/vanilla_leaves/cherry_leaves", "grasses:block/vanilla_leaves/cherry_leaves_overlay");
        else if (block.defaultBlockState() == DARK_OAK_LEAVES_POTTED.get().defaultBlockState() || block.defaultBlockState() == DARK_OAK_LEAVES_POTTED_TINTED.get().defaultBlockState())
            modelLeaves = makePottedLeavesBlock(path, "minecraft:block/dark_oak_leaves");
        else if (block.defaultBlockState() == FLOWERING_AZALEA_LEAVES_POTTED.get().defaultBlockState())
            modelLeaves = makePottedLeavesBlock(path, "minecraft:block/flowering_azalea_leaves");
        else if (block.defaultBlockState() == FLOWERING_AZALEA_LEAVES_POTTED_TINTED.get().defaultBlockState())
            modelLeaves = makeTintedOverlayPottedLeavesBlock(path, "grasses:block/vanilla_leaves/flowering_azalea_leaves", "grasses:block/vanilla_leaves/flowering_azalea_leaves_overlay");
        else if (block.defaultBlockState() == JUNGLE_LEAVES_POTTED.get().defaultBlockState() || block.defaultBlockState() == JUNGLE_LEAVES_POTTED_TINTED.get().defaultBlockState())
            modelLeaves = makePottedLeavesBlock(path, "minecraft:block/jungle_leaves");
        else if (block.defaultBlockState() == MANGROVE_LEAVES_POTTED.get().defaultBlockState() || block.defaultBlockState() == MANGROVE_LEAVES_POTTED_TINTED.get().defaultBlockState())
            modelLeaves = makePottedLeavesBlock(path, "minecraft:block/mangrove_leaves");
        else if (block.defaultBlockState() == OAK_LEAVES_POTTED.get().defaultBlockState() || block.defaultBlockState() == OAK_LEAVES_POTTED_TINTED.get().defaultBlockState())
            modelLeaves = makePottedLeavesBlock(path, "minecraft:block/oak_leaves");
        else if (block.defaultBlockState() == SPRUCE_LEAVES_POTTED.get().defaultBlockState() || block.defaultBlockState() == SPRUCE_LEAVES_POTTED_TINTED.get().defaultBlockState())
            modelLeaves = makePottedLeavesBlock(path, "minecraft:block/spruce_leaves");
        else
            modelLeaves = null;

        return modelLeaves;
    }

    protected void getMultiPartPetalLike(Block block, String path) {

        String clover = "bop/clover";
        String stem = "bop/clover_stem";

        ModelFile modelFlower_1 = makeTintedFlowerbed1(path, clover, stem);
        ModelFile modelFlower_2 = makeTintedFlowerbed2(path.concat("_2"), clover, stem);
        ModelFile modelFlower_3 = makeTintedFlowerbed3(path.concat("_3"), clover, stem);
        ModelFile modelFlower_4 = makeTintedFlowerbed4(path.concat("_4"), clover, stem);

        getMultipartBuilder(block)
                .part().modelFile(modelFlower_1).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, NORTH)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).end()
                .part().modelFile(modelFlower_1).rotationY(90).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, EAST)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).end()
                .part().modelFile(modelFlower_1).rotationY(180).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, SOUTH)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).end()
                .part().modelFile(modelFlower_1).rotationY(270).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, WEST)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).end()
                .part().modelFile(modelFlower_2).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, NORTH)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).end()
                .part().modelFile(modelFlower_2).rotationY(90).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, EAST)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).end()
                .part().modelFile(modelFlower_2).rotationY(180).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, SOUTH)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).end()
                .part().modelFile(modelFlower_2).rotationY(270).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, WEST)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).end()
                .part().modelFile(modelFlower_3).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, NORTH)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 3, 4).end()
                .part().modelFile(modelFlower_3).rotationY(90).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, EAST)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 3, 4).end()
                .part().modelFile(modelFlower_3).rotationY(180).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, SOUTH)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 3, 4).end()
                .part().modelFile(modelFlower_3).rotationY(270).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, WEST)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 3, 4).end()
                .part().modelFile(modelFlower_4).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, NORTH)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 4).end()
                .part().modelFile(modelFlower_4).rotationY(90).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, EAST)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 4).end()
                .part().modelFile(modelFlower_4).rotationY(180).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, SOUTH)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 4).end()
                .part().modelFile(modelFlower_4).rotationY(270).addModel()
                .condition(BlockStateProperties.HORIZONTAL_FACING, WEST)
                .condition(BlockStateProperties.FLOWER_AMOUNT, 4).end();
    }

    protected void getHugeLilyVariant(Block block, String path) {

        ModelFile modelLily_NW = makeTintedHugeLily(path.concat("_north_west"), "bop/huge_lily_pad_north_west");
        ModelFile modelLily_NE = makeTintedHugeLily(path.concat("_north_east"), "bop/huge_lily_pad_north_east");
        ModelFile modelLily_SW = makeTintedHugeLily(path.concat("_south_west"), "bop/huge_lily_pad_south_west");
        ModelFile modelLily_SE = makeTintedHugeLily(path.concat("_south_east"), "bop/huge_lily_pad_south_east");
        ModelFile modelLilyFlower_SW_white = makeTintedHugeLilyWithFlower(path.concat("_south_west_flower_white"), "bop/huge_lily_pad_south_west", 2f, "bop/waterlily_inner", "bop/waterlily_outer");
        ModelFile modelLilyFlower_SW_origin = makeTintedHugeLilyWithFlower(path.concat("_south_west_flower_origin"), "bop/huge_lily_pad_south_west", 2f, "bop/waterlily_inner_origin", "bop/waterlily_outer_origin");


        getVariantBuilder(block)
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.NORTH_WEST).with(TintedHugeLilyPadBOP.FACING, EAST).modelForState().rotationY(90).modelFile(modelLily_NW).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.NORTH_EAST).with(TintedHugeLilyPadBOP.FACING, EAST).modelForState().rotationY(90).modelFile(modelLily_NE).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST).with(TintedHugeLilyPadBOP.FACING, EAST).with(VARIANT_LILY, 0).modelForState().rotationY(90).modelFile(modelLily_SW).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST).with(TintedHugeLilyPadBOP.FACING, EAST).with(VARIANT_LILY, 1).modelForState().rotationY(90).modelFile(modelLilyFlower_SW_white).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST).with(TintedHugeLilyPadBOP.FACING, EAST).with(VARIANT_LILY, 2).modelForState().rotationY(90).modelFile(modelLilyFlower_SW_origin).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_EAST).with(TintedHugeLilyPadBOP.FACING, EAST).modelForState().rotationY(90).modelFile(modelLily_SE).addModel()

                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.NORTH_WEST).with(TintedHugeLilyPadBOP.FACING, SOUTH).modelForState().rotationY(180).modelFile(modelLily_NW).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.NORTH_EAST).with(TintedHugeLilyPadBOP.FACING, SOUTH).modelForState().rotationY(180).modelFile(modelLily_NE).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST).with(TintedHugeLilyPadBOP.FACING, SOUTH).with(VARIANT_LILY, 0).modelForState().rotationY(180).modelFile(modelLily_SW).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST).with(TintedHugeLilyPadBOP.FACING, SOUTH).with(VARIANT_LILY, 1).modelForState().rotationY(180).modelFile(modelLilyFlower_SW_white).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST).with(TintedHugeLilyPadBOP.FACING, SOUTH).with(VARIANT_LILY, 2).modelForState().rotationY(180).modelFile(modelLilyFlower_SW_origin).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_EAST).with(TintedHugeLilyPadBOP.FACING, SOUTH).modelForState().rotationY(180).modelFile(modelLily_SE).addModel()

                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.NORTH_WEST).with(TintedHugeLilyPadBOP.FACING, WEST).modelForState().rotationY(270).modelFile(modelLily_NW).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.NORTH_EAST).with(TintedHugeLilyPadBOP.FACING, WEST).modelForState().rotationY(270).modelFile(modelLily_NE).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST).with(TintedHugeLilyPadBOP.FACING, WEST).with(VARIANT_LILY, 0).modelForState().rotationY(270).modelFile(modelLily_SW).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST).with(TintedHugeLilyPadBOP.FACING, WEST).with(VARIANT_LILY, 1).modelForState().rotationY(270).modelFile(modelLilyFlower_SW_white).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST).with(TintedHugeLilyPadBOP.FACING, WEST).with(VARIANT_LILY, 2).modelForState().rotationY(270).modelFile(modelLilyFlower_SW_origin).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_EAST).with(TintedHugeLilyPadBOP.FACING, WEST).modelForState().rotationY(270).modelFile(modelLily_SE).addModel()

                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.NORTH_WEST).with(TintedHugeLilyPadBOP.FACING, NORTH).modelForState().modelFile(modelLily_NW).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.NORTH_EAST).with(TintedHugeLilyPadBOP.FACING, NORTH).modelForState().modelFile(modelLily_NE).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST).with(TintedHugeLilyPadBOP.FACING, NORTH).with(VARIANT_LILY, 0).modelForState().modelFile(modelLily_SW).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST).with(TintedHugeLilyPadBOP.FACING, NORTH).with(VARIANT_LILY, 1).modelForState().modelFile(modelLilyFlower_SW_white).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST).with(TintedHugeLilyPadBOP.FACING, NORTH).with(VARIANT_LILY, 2).modelForState().modelFile(modelLilyFlower_SW_origin).addModel()
                .partialState().with(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_EAST).with(TintedHugeLilyPadBOP.FACING, NORTH).modelForState().modelFile(modelLily_SE).addModel();
    }

    protected void getWatergrassVariant(Block block, String path) {
        ConfiguredModel bottomGrassModel    = new ConfiguredModel(makeTintedPlantBlock(path.concat("_bottom"), "minecraft:block/tall_grass_bottom"));
        ConfiguredModel topGrassModel       = new ConfiguredModel(makeTintedPlantBlock(path.concat("_top"), "minecraft:block/tall_grass_top"));

        getVariantBuilder(block)
                .partialState().with(HALF, LOWER).addModels(bottomGrassModel)
                .partialState().with(HALF, UPPER).addModels(topGrassModel);
    }

    //////////////////////////////////////////////////////////////////////

    // Grass Block (Full And Slab) :
    protected ModelFile makeTintedGrassBlock(float yFrom, float yTo, float v2, String name) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("particle", "minecraft:block/dirt")
                .texture("bottom", "minecraft:block/dirt")
                .texture("top", "minecraft:block/grass_block_top")
                .texture("side", "minecraft:block/grass_block_side")
                .texture("overlay", "minecraft:block/grass_block_side_overlay")
                .element().from(0.0F, yFrom, 0.0F).to(16.0F, yTo, 16.0F)
                .face(DOWN).uvs(0, 0, 16, 16).texture("#bottom").cullface(DOWN).end()
                .face(UP).uvs(0, 0, 16, 16).texture("#top").cullface(UP).tintindex(0).end()
                .face(NORTH).uvs(0, 0, 16, v2).texture("#side").cullface(NORTH).end()
                .face(SOUTH).uvs(0, 0, 16, v2).texture("#side").cullface(SOUTH).end()
                .face(WEST).uvs(0, 0, 16, v2).texture("#side").cullface(WEST).end()
                .face(EAST).uvs(0, 0, 16, v2).texture("#side").cullface(EAST).end().end()
                .element().from(0.0F, yFrom, 0.0F).to(16.0F, yTo, 16.0F)
                .face(NORTH).uvs(0, 0, 16, v2).texture("#overlay").cullface(NORTH).tintindex(0).end()
                .face(SOUTH).uvs(0, 0, 16, v2).texture("#overlay").cullface(SOUTH).tintindex(0).end()
                .face(WEST).uvs(0, 0, 16, v2).texture("#overlay").cullface(WEST).tintindex(0).end()
                .face(EAST).uvs(0, 0, 16, v2).texture("#overlay").cullface(EAST).tintindex(0).end()
                .end();
    }

    protected ModelFile makeSlabTopSnow(String name, String topTexture) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("particle", blockPath.concat(topTexture))
                .texture("bottom", "minecraft:block/dirt")
                .texture("side", "minecraft:block/grass_block_snow")
                .texture("top", blockPath.concat(topTexture))
                .element().from(0.0F, 8.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(DOWN).uvs(0, 0, 16, 16).texture("#bottom").cullface(DOWN).end()
                .face(UP).uvs(0, 0, 16, 16).texture("#top").cullface(UP).end()
                .face(NORTH).uvs(0, 0, 16, 8).texture("#side").cullface(NORTH).end()
                .face(SOUTH).uvs(0, 0, 16, 8).texture("#side").cullface(SOUTH).end()
                .face(WEST).uvs(0, 0, 16, 8).texture("#side").cullface(WEST).end()
                .face(EAST).uvs(0, 0, 16, 8).texture("#side").cullface(EAST).end()
                .end();
    }

    protected ModelFile makeSlabBlockSameFaces(float yFrom, float yTo, float v2, String name, String texture, String renderType) {
        return models().withExistingParent(name, "minecraft:block/block").renderType(renderType)
                .texture("particle", "minecraft:block/".concat(texture))
                .texture("all", "minecraft:block/".concat(texture))
                .element().from(0.0F, yFrom, 0.0F).to(16.0F, yTo, 16.0F)
                .face(DOWN).uvs(0, 0, 16, 16).texture("#all").cullface(DOWN).end()
                .face(UP).uvs(0, 0, 16, 16).texture("#all").cullface(UP).end()
                .face(NORTH).uvs(0, 0, 16, v2).texture("#all").cullface(NORTH).end()
                .face(SOUTH).uvs(0, 0, 16, v2).texture("#all").cullface(SOUTH).end()
                .face(WEST).uvs(0, 0, 16, v2).texture("#all").cullface(WEST).end()
                .face(EAST).uvs(0, 0, 16, v2).texture("#all").cullface(EAST).end()
                .end();
    }

    protected ModelFile makeSlabOrBlockDiffFaces(float yFrom, float yTo, float v1, float v2, String name, String parent, String bottomTexture, String sideTexture, String topTexture) {
        return models().withExistingParent(name, blockPath.concat(parent)).renderType("cutout_mipped")
                .texture("particle", blockPath.concat(topTexture))
                .texture("top", blockPath.concat(topTexture))
                .texture("side", blockPath.concat(sideTexture))
                .texture("bottom", blockPath.concat(bottomTexture))
                .element().from(0.0F, yFrom, 0.0F).to(16.0F, yTo, 16.0F)
                .face(DOWN).uvs(0, 0, 16, 16).texture("#bottom").cullface(DOWN).end()
                .face(UP).uvs(0, 0, 16, 16).texture("#top").end()
                .face(NORTH).uvs(0, v1, 16, v2).texture("#side").cullface(NORTH).end()
                .face(SOUTH).uvs(0, v1, 16, v2).texture("#side").cullface(SOUTH).end()
                .face(WEST).uvs(0, v1, 16, v2).texture("#side").cullface(WEST).end()
                .face(EAST).uvs(0, v1, 16, v2).texture("#side").cullface(EAST).end()
                .end();
    }

    protected ModelFile makeSlabOrBlockDiffFacesQuasiAxisY(List<Float> listEl, String name, String topBottomTexture, String sideTexture) {
        return models().withExistingParent(name, blockPath.concat("block")).renderType("cutout_mipped")
                .texture("particle", blockPath.concat(topBottomTexture))
                .texture("topBottom", blockPath.concat(topBottomTexture))
                .texture("side", blockPath.concat(sideTexture))
                .element().from(0F, listEl.get(0), 0F).to(16F, listEl.get(1), 16F)
                .face(DOWN).uvs(0, 0, 16, 16).texture("#topBottom").cullface(DOWN).end()
                .face(UP).uvs(0, 0, 16, 16).texture("#topBottom").end()
                .face(NORTH).uvs(0, 0, 16, listEl.get(2)).texture("#side").cullface(NORTH).end()
                .face(SOUTH).uvs(0, 0, 16, listEl.get(2)).texture("#side").cullface(SOUTH).end()
                .face(WEST).uvs(0, 0, 16, listEl.get(2)).texture("#side").cullface(WEST).end()
                .face(EAST).uvs(0, 0, 16, listEl.get(2)).texture("#side").cullface(EAST).end()
                .end();
    }

    protected ModelFile makeSlabOrBlockDiffFacesQuasiAxisX(List<Float> listEl, String name, String topBottomTexture, String sideTexture) {
        return models().withExistingParent(name, blockPath.concat("block")).renderType("cutout_mipped")
                .texture("particle", blockPath.concat(topBottomTexture))
                .texture("topBottom", blockPath.concat(topBottomTexture))
                .texture("side", blockPath.concat(sideTexture))
                .element().from(0F, listEl.get(0), 0F).to(16F, listEl.get(1), 16F)
                .face(DOWN).uvs(0, 0, 16, 16).texture("#side").rotation(CLOCKWISE_90).cullface(DOWN).end()
                .face(UP).uvs(0, 0, 16, 16).texture("#side").rotation(CLOCKWISE_90).end()
                .face(NORTH).uvs(listEl.get(3), 0, 16, 16).texture("#side").rotation(COUNTERCLOCKWISE_90).cullface(NORTH).end()
                .face(SOUTH).uvs(0, 0, listEl.get(2), 16).texture("#side").rotation(CLOCKWISE_90).cullface(SOUTH).end()
                .face(WEST).uvs(0, 0, 16, listEl.get(2)).texture("#topBottom").rotation(ZERO).cullface(WEST).end()
                .face(EAST).uvs(0, listEl.get(3), 16, 16).texture("#topBottom").rotation(UPSIDE_DOWN).cullface(EAST).end() //
                .end();
    }


    protected ModelFile makeSlabOrBlockDiffFacesQuasiAxisZ(List<Float> listEl, String name, String topBottomTexture, String sideTexture) {
        return models().withExistingParent(name, blockPath.concat("block")).renderType("cutout_mipped")
                .texture("particle", blockPath.concat(topBottomTexture))
                .texture("topBottom", blockPath.concat(topBottomTexture))
                .texture("side", blockPath.concat(sideTexture))
                .element().from(0F, listEl.get(0), 0F).to(16F, listEl.get(1), 16F)
                .face(DOWN).uvs(0, 0, 16, 16).texture("#side").rotation(UPSIDE_DOWN).cullface(DOWN).end()
                .face(UP).uvs(0, 0, 16, 16).texture("#side").rotation(ZERO).end()
                .face(NORTH).uvs(0, 0, 16, listEl.get(2)).texture("#topBottom").rotation(UPSIDE_DOWN).cullface(NORTH).end()
                .face(SOUTH).uvs(0, 0, 16, listEl.get(2)).texture("#topBottom").rotation(ZERO).cullface(SOUTH).end()
                .face(WEST).uvs(listEl.get(3), 0, 16, 16).texture("#side").rotation(COUNTERCLOCKWISE_90).cullface(WEST).end()
                .face(EAST).uvs(0, 0, listEl.get(2), 16).texture("#side").rotation(CLOCKWISE_90).cullface(EAST).end()
                .end();
    }

    protected ModelFile makeTintedLeavesBlock(String name, String leaves) {
        return models().withExistingParent(name, "minecraft:block/leaves").renderType("cutout_mipped")
                .texture("particle", leaves)
                .texture("all", leaves)
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(DOWN).uvs(0, 0, 16, 16).texture("#all").cullface(DOWN).tintindex(0).end()
                .face(UP).uvs(0, 0, 16, 16).texture("#all").cullface(UP).tintindex(0).end()
                .face(NORTH).uvs(0, 0, 16, 16).texture("#all").cullface(NORTH).tintindex(0).end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#all").cullface(SOUTH).tintindex(0).end()
                .face(WEST).uvs(0, 0, 16, 16).texture("#all").cullface(WEST).tintindex(0).end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#all").cullface(EAST).tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedOverlayLeavesBlock(String name, String leaves, String overlay) {
        return models().withExistingParent(name, "minecraft:block/leaves").renderType("cutout_mipped")
                .texture("particle", leaves)
                .texture("all", leaves)
                .texture("overlay", overlay)
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(DOWN).uvs(0, 0, 16, 16).texture("#all").cullface(DOWN).tintindex(0).end()
                .face(UP).uvs(0, 0, 16, 16).texture("#all").cullface(UP).tintindex(0).end()
                .face(NORTH).uvs(0, 0, 16, 16).texture("#all").cullface(NORTH).tintindex(0).end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#all").cullface(SOUTH).tintindex(0).end()
                .face(WEST).uvs(0, 0, 16, 16).texture("#all").cullface(WEST).tintindex(0).end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#all").cullface(EAST).tintindex(0).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(DOWN).uvs(0, 0, 16, 16).texture("#overlay").cullface(DOWN).end()
                .face(UP).uvs(0, 0, 16, 16).texture("#overlay").cullface(UP).end()
                .face(NORTH).uvs(0, 0, 16, 16).texture("#overlay").cullface(NORTH).end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#overlay").cullface(SOUTH).end()
                .face(WEST).uvs(0, 0, 16, 16).texture("#overlay").cullface(WEST).end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#overlay").cullface(EAST).end()
                .end();
    }

    protected ModelFile makeTintedPlantBlock(String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/tinted_cross").renderType("cutout_mipped")
                .texture("cross", plant);
    }

    protected ModelFile makeTintedPlantBlockWithOverlay(String name, String plant, String overlay) {
        return models().withExistingParent(name, "minecraft:block/tinted_cross").renderType("cutout_mipped")
                .texture("cross", blockGrassesPath.concat(plant))
                .texture("overlay", blockGrassesPath.concat(overlay))
                .texture("particle", blockGrassesPath.concat(plant))
                .element().from(0.8f,0f,8f).to(15.2f, 16f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(0f, 0f, 16f, 16f).texture("#cross").tintindex(0).end()
                .face(SOUTH).uvs(0f, 0f, 16f, 16f).texture("#cross").tintindex(0).end()
                .end()

                .element().from(8f,0f,0.8f).to(8f, 16f, 15.2f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .shade(false)
                .face(WEST).uvs(0f, 0f, 16f, 16f).texture("#cross").tintindex(0).end()
                .face(EAST).uvs(0f, 0f, 16f, 16f).texture("#cross").tintindex(0).end()
                .end()

                .element().from(0.8f,0f,8f).to(15.2f, 16f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(0f, 0f, 16f, 16f).texture("#overlay").end()
                .face(SOUTH).uvs(0f, 0f, 16f, 16f).texture("#overlay").end()
                .end()

                .element().from(8f,0f,0.8f).to(8f, 16f, 15.2f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .shade(false)
                .face(WEST).uvs(0f, 0f, 16f, 16f).texture("#overlay").end()
                .face(EAST).uvs(0f, 0f, 16f, 16f).texture("#overlay").end()
                .end();
    }

    protected ModelFile makeTintedSeaGrassBlock (String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("texture", plant)
                .texture("particle", plant)
                .element().from(0.0F, 0.0F, 4.0F).to(16.0F, 16.0F, 4.0F)
                .shade(false)
                .face(NORTH).uvs(0, 0, 16, 16).texture("#texture").tintindex(0).end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#texture").tintindex(0).end()
                .end()

                .element().from(12.0F, 0.0F, 0.0F).to(12.0F, 16.0F, 16.0F)
                .shade(false)
                .face(WEST).uvs(0, 0, 16, 16).texture("#texture").tintindex(0).end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#texture").tintindex(0).end()
                .end()

                .element().from(4.0F, 0.0F, 0.0F).to(4.0F, 16.0F, 16.0F)
                .shade(false)
                .face(WEST).uvs(0, 0, 16, 16).texture("#texture").tintindex(0).end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#texture").tintindex(0).end()
                .end()

                .element().from(0.0F, 0.0F, 12.0F).to(16.0F, 16.0F, 12.0F)
                .shade(false)
                .face(NORTH).uvs(0, 0, 16, 16).texture("#texture").tintindex(0).end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#texture").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedVineBlock (String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)
                .element().from(0.0F, 0.0F, 0.8F).to(16.0F, 16.0F, 0.8F)
                .shade(false)
                .face(NORTH).uvs(16, 0, 0, 16).texture("#vine").tintindex(0).end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedLilyBlock (String name, String plant) {
        return models().withExistingParent (name, "minecraft:block/lily_pad").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("lily", plant)
                .element().from(0.0F, 0.25F, 0.0F).to(16.0F, 0.25F, 16.0F)
                .face(UP).uvs(16, 0, 0, 16).texture("#lily").tintindex(0).end()
                .face(DOWN).uvs(16, 16, 0, 0).texture("#lily").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedCactusBlock(String name) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("particle", "grasses:block/cactus_side")
                .texture("top", "grasses:block/cactus_top")
                .texture("topOverlay", "grasses:block/cactus_top_overlay")
                .texture("bottom", "grasses:block/cactus_bottom")
                .texture("bottomOverlay", "grasses:block/cactus_bottom_overlay")
                .texture("side", "grasses:block/cactus_side")
                .texture("sideOverlay", "grasses:block/cactus_side_overlay")
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(DOWN).uvs(0, 0, 16, 16).texture("#bottom").cullface(DOWN).tintindex(0).end()
                .face(UP).uvs(0, 0, 16, 16).texture("#top").cullface(UP).tintindex(0).end()
                .end()
                .element().from(0.0F, 0.0F, 1.0F).to(16.0F, 16.0F, 15.0F)
                .face(NORTH).uvs(0, 0, 16, 16).texture("#side").tintindex(0).end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#side").tintindex(0).end()
                .end()
                .element().from(1.0F, 0.0F, 0.0F).to(15.0F, 16.0F, 16.0F)
                .face(WEST).uvs(0, 0, 16, 16).texture("#side").tintindex(0).end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#side").tintindex(0).end()
                .end()

                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(DOWN).uvs(0, 0, 16, 16).texture("#bottomOverlay").cullface(DOWN).end()
                .face(UP).uvs(0, 0, 16, 16).texture("#topOverlay").cullface(UP).end()
                .end()
                .element().from(0.0F, 0.0F, 1.0F).to(16.0F, 16.0F, 15.0F)
                .face(NORTH).uvs(0, 0, 16, 16).texture("#sideOverlay").end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#sideOverlay").end()
                .end()
                .element().from(1.0F, 0.0F, 0.0F).to(15.0F, 16.0F, 16.0F)
                .face(WEST).uvs(0, 0, 16, 16).texture("#sideOverlay").end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#sideOverlay").end()
                .end();
    }

    protected ModelFile makeTintedBambooAge(String name, List<Float> age, float u1, float u2, String parent) {

        return models().withExistingParent(name, blockPath.concat(parent)).renderType("cutout_mipped").ao(false)
                .texture("all", "grasses:block/bamboo_stalk")
                .texture("particle", "grasses:block/bamboo_stalk")
                .element().from(age.get(0), age.get(1), age.get(2)).to(age.get(3), age.get(4), age.get(5))
                .face(DOWN).uvs(age.get(6), age.get(7), age.get(8), age.get(9)).texture("#all").cullface(DOWN).tintindex(0).end()
                .face(UP).uvs(age.get(9), age.get(10), age.get(11), age.get(12)).texture("#all").cullface(UP).tintindex(0).end()
                .face(NORTH).uvs(u1, 0, u2, 16).texture("#all").tintindex(0).end()
                .face(SOUTH).uvs(u1, 0, u2, 16).texture("#all").tintindex(0).end()
                .face(WEST).uvs(u1, 0, u2, 16).texture("#all").tintindex(0).end()
                .face(EAST).uvs(u1, 0, u2, 16).texture("#all").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedBambooLeaves(String name, String plant, String parent) {
        return models().withExistingParent(name, blockPath.concat(parent)).renderType("cutout_mipped")
                .texture("all", plant)
                .texture("particle", plant)
                .element().from(0.8f, 0f, 8f).to(15.2f, 16f, 8f)
                .shade(false)
                .face(NORTH).uvs(0f,0f,16f,16f).texture("#all").cullface(NORTH).tintindex(0).end()
                .face(SOUTH).uvs(0f,0f,16f,16f).texture("#all").cullface(SOUTH).tintindex(0).end().end()

                .element().from(8f, 0f, 0.8f).to(8f, 16f, 15.2f)
                .shade(false)
                .face(WEST).uvs(0f,0f,16f,16f).texture("#all").cullface(WEST).tintindex(0).end()
                .face(EAST).uvs(0f,0f,16f,16f).texture("#all").cullface(EAST).tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedBigDripLeafBlock(String name) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("top", "grasses:block/big_dripleaf_top")
                .texture("stem", "grasses:block/big_dripleaf_stem")
                .texture("side", "grasses:block/big_dripleaf_side")
                .texture("tip", "grasses:block/big_dripleaf_tip")
                .texture("particle", "grasses:block/big_dripleaf_top")
                .element().from(0f,15f,0f).to(16f, 15f, 16f)
                .shade(false)
                .face(DOWN).uvs(0f, 16f, 16f, 0f).texture("#top").tintindex(0).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#top").tintindex(0).end()
                .end()
                .element().from(0f,11f,0f).to(16f, 15f, 0.002f)
                .shade(false)
                .face(NORTH).uvs(0f, 0f, 16f, 4f).texture("#tip").cullface(NORTH).tintindex(0).end()
                .face(SOUTH).uvs(16f, 0f, 0f, 4f).texture("#tip").tintindex(0).end()
                .end()
                .element().from(0f,11f,0f).to(0.002f, 15f, 16f)
                .shade(false)
                .face(EAST).uvs(16f, 0f, 0f, 4f).texture("#side").tintindex(0).end()
                .face(WEST).uvs(0f, 0f, 16f, 4f).texture("#side").cullface(WEST).tintindex(0).end()
                .end()
                .element().from(15.998f,11f,0f).to(16f, 15f, 16f)
                .shade(false)
                .face(EAST).uvs(16f, 0f, 0f, 4f).texture("#side").cullface(EAST).tintindex(0).end()
                .face(WEST).uvs(0f, 0f, 16f, 4f).texture("#side").tintindex(0).end()
                .end()
                .element().from(5f,0f,12f).to(11f, 15f, 12f)
                .rotation().origin(8f, 8f, 12f).axis(Axis.Y).angle(45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .end()
                .element().from(5f,0f,12f).to(11f, 15f, 12f)
                .rotation().origin(8f, 8f, 12f).axis(Axis.Y).angle(-45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedBigDripLeafTiltBlock(String name, float angle) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("top", "grasses:block/big_dripleaf_top")
                .texture("stem", "grasses:block/big_dripleaf_stem")
                .texture("side", "grasses:block/big_dripleaf_side")
                .texture("tip", "grasses:block/big_dripleaf_tip")
                .texture("particle", "grasses:block/big_dripleaf_top")
                .element().from(0f,15f,0f).to(16f, 15f, 16f)
                .rotation().origin(8f, 15f, 16f).axis(Axis.X).angle(angle).end()
                .shade(false)
                .face(DOWN).uvs(16f, 16f, 0f, 0f).texture("#top").tintindex(0).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#top").tintindex(0).end()
                .end()

                .element().from(0f,11f,0f).to(16f, 15f, 0f)
                .rotation().origin(8f, 15f, 16f).axis(Axis.X).angle(angle).end()
                .shade(false)
                .face(NORTH).uvs(0f, 0f, 16f, 4f).texture("#tip").tintindex(0).end()
                .face(SOUTH).uvs(0f, 0f, 16f, 4f).texture("#tip").tintindex(0).end()
                .end()

                .element().from(0f,11f,0f).to(0.002f, 15f, 16f)
                .rotation().origin(8f, 15f, 16f).axis(Axis.X).angle(angle).end()
                .shade(false)
                .face(EAST).uvs(16f, 0f, 0f, 4f).texture("#side").tintindex(0).end()
                .face(WEST).uvs(0f, 0f, 16f, 4f).texture("#side").cullface(WEST).tintindex(0).end()
                .end()

                .element().from(15.998f,11f,0f).to(16f, 15f, 16f)
                .rotation().origin(8f, 15f, 16f).axis(Axis.X).angle(angle).end()
                .shade(false)
                .face(EAST).uvs(16f, 0f, 0f, 4f).texture("#side").cullface(EAST).tintindex(0).end()
                .face(WEST).uvs(0f, 0f, 16f, 4f).texture("#side").tintindex(0).end()
                .end()

                .element().from(5f,0f,12f).to(11f, 15f, 12f)
                .rotation().origin(8f, 8f, 12f).axis(Axis.Y).angle(45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(5f,0f,12f).to(11f, 15f, 12f)
                .rotation().origin(8f, 8f, 12f).axis(Axis.Y).angle(-45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedBigDripLeafStemBlock(String name) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("stem", "grasses:block/big_dripleaf_stem")
                .texture("particle", "grasses:block/big_dripleaf_stem")
                .element().from(5f,0f,12f).to(11f, 16f, 12f)
                .rotation().origin(8f, 8f, 12f).axis(Axis.Y).angle(45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(5f,0f,12f).to(11f, 16f, 12f)
                .rotation().origin(8f, 8f, 12f).axis(Axis.Y).angle(-45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedSmallDripLeafBlockTop(String name) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("top", blockGrassesPath.concat("small_dripleaf_top"))
                .texture("stem", blockGrassesPath.concat("small_dripleaf_stem_top"))
                .texture("side", blockGrassesPath.concat("small_dripleaf_side"))
                .texture("particle", blockGrassesPath.concat("small_dripleaf_top"))

                .element().from(8f,2.99f,8f).to(15f, 2.99f, 15f)
                .shade(false)
                .face(DOWN).uvs(8f, 0f, 0f, 8f).texture("#top").tintindex(0).end()
                .face(UP).uvs(8f, 8f, 0f, 0f).texture("#top").tintindex(0).end()
                .end()

                .element().from(1f,8f,1f).to(8f, 8f, 8f)
                .shade(false)
                .face(DOWN).uvs(0f, 8f, 8f, 0f).texture("#top").tintindex(0).end()
                .face(UP).uvs(0f, 0f, 8f, 8f).texture("#top").tintindex(0).end()
                .end()

                .element().from(1f,12f,8f).to(8f, 12f, 15f)
                .shade(false)
                .face(DOWN).uvs(8f, 0f, 0f, 8f).texture("#top").rotation(COUNTERCLOCKWISE_90).tintindex(0).end()
                .face(UP).uvs(0f, 0f, 8f, 8f).texture("#top").rotation(COUNTERCLOCKWISE_90).tintindex(0).end()
                .end()

                .element().from(8f,2f,8f).to(15f, 3f, 15f)
                .shade(false)
                .face(EAST).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(WEST).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(NORTH).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(SOUTH).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .end()

                .element().from(1f,7f,1.01f).to(8f, 8f, 8f)
                .shade(false)
                .face(EAST).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(WEST).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(NORTH).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(SOUTH).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .end()

                .element().from(1f,11f,8f).to(8f, 12f, 15f)
                .shade(false)
                .face(EAST).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(WEST).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(NORTH).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(SOUTH).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .end()

                .element().from(4.5f,0f,8f).to(11.5f, 14f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(false).end()
                .shade(false)
                .face(NORTH).uvs(4f, 0f, 12f, 14f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(4f, 0f, 12f, 14f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(4.5f,0f,8f).to(11.5f, 14f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(-45).rescale(false).end()
                .shade(false)
                .face(NORTH).uvs(4f, 0f, 12f, 14f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(4f, 0f, 12f, 14f).texture("#stem").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedSmallDripLeafBlockBottom(String name) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("stem", blockGrassesPath.concat("small_dripleaf_stem_bottom"))
                .texture("particle", blockGrassesPath.concat("big_dripleaf_stem"))
                .element().from(4.5f,0f,8f).to(11.5f, 16f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(false).end()
                .shade(false)
                .face(NORTH).uvs(3f, 0f, 14f, 16).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(4.5f,0f,8f).to(11.5f, 16f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(-45).rescale(false).end()
                .shade(false)
                .face(NORTH).uvs(3f, 0f, 14f, 16).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedPottedBlockSingle(String name, Block plant, String soil) {
        return models().singleTexture(name, new ResourceLocation("minecraft:block/tinted_flower_pot_cross"), "plant", blockTexture(plant)).renderType("cutout_mipped")
                .texture("dirt", soil);
    }

    protected ModelFile makeTintedPottedBlock (String name, String plant, String soil) {
        return models().withExistingParent(name, "minecraft:block/tinted_flower_pot_cross").renderType("cutout_mipped").ao(false)
                .texture("particle", blockPath.concat("flower_pot"))
                .texture("flowerpot", blockPath.concat("flower_pot"))
                .texture("soil", soil)
                .texture("plant", blockGrassesPath.concat(plant))

                .element().from(5f,0f,5f).to(6f, 6f, 11f)
                .face(DOWN) .uvs(5, 5, 6, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP)   .uvs(5, 5, 6, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(WEST) .uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST) .uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(10f,0f,5f).to(11f, 6f, 11f)
                .face(DOWN) .uvs(10, 5, 11, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP)   .uvs(10, 5, 11, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(WEST) .uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST) .uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,5f).to(10f, 6f, 6f)
                .face(DOWN).uvs(6, 10, 10, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 5, 10, 6).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,10f).to(10f, 6f, 11f)
                .face(DOWN).uvs(6, 5, 10, 6).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 10, 10, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,6f).to(10f, 4f, 10f)
                .face(DOWN).uvs(6, 12, 10, 16).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 6, 10, 10).texture("#soil").end()
                .end()

                .element().from(2.6f,4f,8f).to(13.4f, 16f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .face(NORTH).uvs(0, 0, 16, 16).texture("#plant").tintindex(0).end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#plant").tintindex(0).end()
                .end()

                .element().from(8f,4f,2.6f).to(8f, 16f, 13.4f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .face(WEST).uvs(0, 0, 16, 16).texture("#plant").tintindex(0).end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#plant").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedPottedBambooBlock (String name) {
        return models().withExistingParent(name, "minecraft:block/potted_bamboo").renderType("cutout_mipped").ao(false)
                .texture("particle", blockPath.concat("flower_pot"))
                .texture("flowerpot", blockPath.concat("flower_pot"))
                .texture("soil", blockPath.concat("dirt"))
                .texture("bamboo", blockGrassesPath.concat("bamboo_stalk"))
                .texture("leaf", blockGrassesPath.concat("bamboo_singleleaf"))

                .element().from(5f,0f,5f).to(6f, 6f, 11f)
                .face(DOWN).uvs(5, 5, 6, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(5, 5, 6, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(10f,0f,5f).to(11f, 6f, 11f)
                .face(DOWN).uvs(10, 5, 11, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(10, 5, 11, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,5f).to(10f, 6f, 6f)
                .face(DOWN).uvs(6, 10, 10, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 5, 10, 6).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,10f).to(10f, 6f, 11f)
                .face(DOWN).uvs(6, 5, 10, 6).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 10, 10, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,6f).to(10f, 4f, 10f)
                .face(DOWN).uvs(6, 12, 10, 16).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 6, 10, 10).texture("#soil").end()
                .end()

                .element().from(7f,0f,7f).to(9f, 16f, 9f)
                .face(UP).uvs(13, 0, 15, 2).texture("#bamboo").tintindex(0).cullface(UP).end()
                .face(NORTH).uvs(6, 0, 8, 16).texture("#bamboo").tintindex(0).end()
                .face(SOUTH).uvs(6, 0, 8, 16).texture("#bamboo").tintindex(0).end()
                .face(WEST).uvs(6, 0, 8, 16).texture("#bamboo").tintindex(0).end()
                .face(EAST).uvs(6, 0, 8, 16).texture("#bamboo").tintindex(0).end()
                .end()

                .element().from(0f,2f,8f).to(16f, 18f, 8f)
                .face(NORTH).uvs(0, 0, 16, 16).texture("#leaf").tintindex(0).end()
                .face(SOUTH).uvs(16, 0, 0, 16).texture("#leaf").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedPottedBigDripLeafBlock (String name) {
        return models().withExistingParent(name, "minecraft:block/tinted_flower_pot_cross").renderType("cutout_mipped").ao(false)
                .texture("particle", blockPath.concat("flower_pot"))
                .texture("flowerpot", blockPath.concat("flower_pot"))
                .texture("soil", blockGrassesPath.concat("water_still"))
                .texture("top", "grasses:block/big_dripleaf_top")
                .texture("stem", "grasses:block/big_dripleaf_stem")
                .texture("side", "grasses:block/big_dripleaf_side")
                .texture("tip", "grasses:block/big_dripleaf_tip")

                .element().from(5f,0f,9f).to(6f, 6f, 15f)
                .face(DOWN).uvs(5, 5, 6, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(5, 5, 6, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(10f,0f,9f).to(11f, 6f, 15f)
                .face(DOWN).uvs(10, 5, 11, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(10, 5, 11, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,9f).to(10f, 6f, 10f)
                .face(DOWN).uvs(6, 10, 10, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 5, 10, 6).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,14).to(10f, 6f, 15f)
                .face(DOWN).uvs(6, 5, 10, 6).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 10, 10, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,10f).to(10f, 4f, 14f)
                .face(DOWN).uvs(6, 12, 10, 16).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 6, 10, 10).texture("#soil").end()
                .end()

                .element().from(0f,16f,0f).to(16f, 16f, 16f)
                .shade(false)
                .face(DOWN).uvs(0f, 16f, 16f, 0f).texture("#top").tintindex(0).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#top").tintindex(0).end()
                .end()
                .element().from(0f,12f,0f).to(16f, 16f, 0.002f)
                .shade(false)
                .face(NORTH).uvs(0f, 0f, 16f, 4f).texture("#tip").cullface(NORTH).tintindex(0).end()
                .face(SOUTH).uvs(16f, 0f, 0f, 4f).texture("#tip").tintindex(0).end()
                .end()
                .element().from(0f,12f,0f).to(0.002f, 16f, 16f)
                .shade(false)
                .face(EAST).uvs(16f, 0f, 0f, 4f).texture("#side").tintindex(0).end()
                .face(WEST).uvs(0f, 0f, 16f, 4f).texture("#side").cullface(WEST).tintindex(0).end()
                .end()
                .element().from(15.998f,12f,0f).to(16f, 16f, 16f)
                .shade(false)
                .face(EAST).uvs(16f, 0f, 0f, 4f).texture("#side").cullface(EAST).tintindex(0).end()
                .face(WEST).uvs(0f, 0f, 16f, 4f).texture("#side").tintindex(0).end()
                .end()

                .element().from(5f,1f,12f).to(11f, 16f, 12f)
                .rotation().origin(8f, 8f, 12f).axis(Axis.Y).angle(45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(5f,1f,12f).to(11f, 16f, 12f)
                .rotation().origin(8f, 8f, 12f).axis(Axis.Y).angle(-45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(3f, 0f, 14f, 16f).texture("#stem").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedPottedSmallDripLeafBlock (String name) {
        return models().withExistingParent(name, "minecraft:block/tinted_flower_pot_cross").renderType("cutout_mipped").ao(false)
                .texture("particle", blockPath.concat("flower_pot"))
                .texture("flowerpot", blockPath.concat("flower_pot"))
                .texture("soil", blockGrassesPath.concat("water_still"))
                .texture("top", "grasses:block/small_dripleaf_top")
                .texture("stem", "grasses:block/small_dripleaf_stem_top")
                .texture("side", "grasses:block/small_dripleaf_side")

                .element().from(5f,0f,5f).to(6f, 6f, 11f)
                .face(DOWN).uvs(5, 5, 6, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(5, 5, 6, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(10f,0f,5f).to(11f, 6f, 11f)
                .face(DOWN).uvs(10, 5, 11, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(10, 5, 11, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,5f).to(10f, 6f, 6f)
                .face(DOWN).uvs(6, 10, 10, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 5, 10, 6).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,10f).to(10f, 6f, 11f)
                .face(DOWN).uvs(6, 5, 10, 6).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 10, 10, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,6f).to(10f, 4f, 10f)
                .face(DOWN).uvs(6, 12, 10, 16).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 6, 10, 10).texture("#soil").end()
                .end()

                .element().from(8f,6.99f,8f).to(15f, 6.99f, 15f)
                .shade(false)
                .face(DOWN).uvs(8f, 0f, 0f, 8f).texture("#top").tintindex(0).end()
                .face(UP).uvs(8f, 8f, 0f, 0f).texture("#top").tintindex(0).end()
                .end()

                .element().from(1f,12f,1f).to(8f, 12f, 8f)
                .shade(false)
                .face(DOWN).uvs(0f, 8f, 8f, 0f).texture("#top").tintindex(0).end()
                .face(UP).uvs(0f, 0f, 8f, 8f).texture("#top").tintindex(0).end()
                .end()

                .element().from(1f,16f,8f).to(8f, 16f, 15f)
                .shade(false)
                .face(DOWN).uvs(8f, 0f, 0f, 8f).texture("#top").rotation(COUNTERCLOCKWISE_90).tintindex(0).end()
                .face(UP).uvs(0f, 0f, 8f, 8f).texture("#top").rotation(COUNTERCLOCKWISE_90).tintindex(0).end()
                .end()

                .element().from(8f,6f,8f).to(15f, 7f, 15f)
                .shade(false)
                .face(EAST).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(WEST).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(NORTH).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(SOUTH).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .end()

                .element().from(1f,11f,1.01f).to(8f, 12f, 8f)
                .shade(false)
                .face(EAST).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(WEST).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(NORTH).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(SOUTH).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .end()

                .element().from(1f,15f,8f).to(8f, 16f, 15f)
                .shade(false)
                .face(EAST).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(WEST).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(NORTH).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .face(SOUTH).uvs(0f, 0f, 8f, 1f).texture("#side").tintindex(0).end()
                .end()

                .element().from(4.5f,4f,8f).to(11.5f, 18f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(false).end()
                .shade(false)
                .face(NORTH).uvs(4f, 0f, 12f, 14f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(4f, 0f, 12f, 14f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(4.5f,4f,8f).to(11.5f, 18f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(-45).rescale(false).end()
                .shade(false)
                .face(NORTH).uvs(4f, 0f, 12f, 14f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(4f, 0f, 12f, 14f).texture("#stem").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedPottedCactusBlock (String name) {
        return models().withExistingParent(name, "minecraft:block/potted_cactus").renderType("cutout_mipped").ao(false)
                .texture("particle", blockPath.concat("flower_pot"))
                .texture("flowerpot", blockPath.concat("flower_pot"))
                .texture("cactus", blockGrassesPath.concat("cactus_side"))
                .texture("cactus_overlay", blockGrassesPath.concat("cactus_side_overlay"))
                .texture("cactus_top", blockGrassesPath.concat("cactus_top"))
                .texture("cactus_top_overlay", blockGrassesPath.concat("cactus_top_overlay"))

                .element().from(5f,0f,5f).to(11f, 6f, 11f)
                .face(DOWN).uvs(5, 5, 6, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).texture("#flowerpot").end()
                .face(NORTH).texture("#flowerpot").end()
                .face(SOUTH).texture("#flowerpot").end()
                .face(WEST).texture("#flowerpot").end()
                .face(EAST).texture("#flowerpot").end()
                .end()

                .element().from(6f,5f,6f).to(10f, 16f, 10f)
                .face(UP).texture("#cactus_top").tintindex(0).cullface(UP).end()
                .face(NORTH).uvs(6, 0, 10, 11).texture("#cactus").tintindex(0).end()
                .face(SOUTH).uvs(6, 0, 10, 11).texture("#cactus").tintindex(0).end()
                .face(WEST).uvs(6, 0, 10, 11).texture("#cactus").tintindex(0).end()
                .face(EAST).uvs(6, 0, 10, 11).texture("#cactus").tintindex(0).end()
                .end()

                .element().from(6f,5f,6f).to(10f, 16f, 10f)
                .face(UP).texture("#cactus_top_overlay").cullface(UP).end()
                .face(NORTH).uvs(6, 0, 10, 11).texture("#cactus_overlay").end()
                .face(SOUTH).uvs(6, 0, 10, 11).texture("#cactus_overlay").end()
                .face(WEST).uvs(6, 0, 10, 11).texture("#cactus_overlay").end()
                .face(EAST).uvs(6, 0, 10, 11).texture("#cactus_overlay").end()
                .end();
    }

    protected ModelFile makePottedBigDripLeafBlock (String name) {
        return models().withExistingParent(name, "minecraft:block/tinted_flower_pot_cross").renderType("cutout_mipped").ao(false)
                .texture("particle", blockPath.concat("flower_pot"))
                .texture("flowerpot", blockPath.concat("flower_pot"))
                .texture("soil", blockGrassesPath.concat("water_still"))
                .texture("top", blockPath.concat("big_dripleaf_top"))
                .texture("stem", blockPath.concat("big_dripleaf_stem"))
                .texture("side", blockPath.concat("big_dripleaf_side"))
                .texture("tip", blockPath.concat("big_dripleaf_tip"))

                .element().from(5f,0f,9f).to(6f, 6f, 15f)
                .face(DOWN).uvs(5, 5, 6, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(5, 5, 6, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(10f,0f,9f).to(11f, 6f, 15f)
                .face(DOWN).uvs(10, 5, 11, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(10, 5, 11, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,9f).to(10f, 6f, 10f)
                .face(DOWN).uvs(6, 10, 10, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 5, 10, 6).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,14).to(10f, 6f, 15f)
                .face(DOWN).uvs(6, 5, 10, 6).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 10, 10, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,10f).to(10f, 4f, 14f)
                .face(DOWN).uvs(6, 12, 10, 16).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 6, 10, 10).texture("#soil").end()
                .end()

                .element().from(0f,16f,0f).to(16f, 16f, 16f)
                .shade(false)
                .face(DOWN).uvs(0f, 16f, 16f, 0f).texture("#top").end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#top").end()
                .end()
                .element().from(0f,12f,0f).to(16f, 16f, 0.002f)
                .shade(false)
                .face(NORTH).uvs(0f, 0f, 16f, 4f).texture("#tip").cullface(NORTH).end()
                .face(SOUTH).uvs(16f, 0f, 0f, 4f).texture("#tip").end()
                .end()
                .element().from(0f,12f,0f).to(0.002f, 16f, 16f)
                .shade(false)
                .face(EAST).uvs(16f, 0f, 0f, 4f).texture("#side").end()
                .face(WEST).uvs(0f, 0f, 16f, 4f).texture("#side").cullface(WEST).end()
                .end()
                .element().from(15.998f,12f,0f).to(16f, 16f, 16f)
                .shade(false)
                .face(EAST).uvs(16f, 0f, 0f, 4f).texture("#side").cullface(EAST).end()
                .face(WEST).uvs(0f, 0f, 16f, 4f).texture("#side").end()
                .end()

                .element().from(5f,1f,12f).to(11f, 16f, 12f)
                .rotation().origin(8f, 8f, 12f).axis(Axis.Y).angle(45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(3f, 0f, 14f, 16f).texture("#stem").end()
                .face(SOUTH).uvs(3f, 0f, 14f, 16f).texture("#stem").end()
                .end()

                .element().from(5f,1f,12f).to(11f, 16f, 12f)
                .rotation().origin(8f, 8f, 12f).axis(Axis.Y).angle(-45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(3f, 0f, 14f, 16f).texture("#stem").end()
                .face(SOUTH).uvs(3f, 0f, 14f, 16f).texture("#stem").end()
                .end();
    }

    protected ModelFile makePottedSmallDripLeafBlock (String name) {
        return models().withExistingParent(name, "minecraft:block/tinted_flower_pot_cross").renderType("cutout_mipped").ao(false)
                .texture("particle", blockPath.concat("flower_pot"))
                .texture("flowerpot", blockPath.concat("flower_pot"))
                .texture("soil", blockGrassesPath.concat("water_still"))
                .texture("top", blockPath.concat("small_dripleaf_top"))
                .texture("stem", blockPath.concat("small_dripleaf_stem_top"))
                .texture("side", blockPath.concat("small_dripleaf_side"))

                .element().from(5f,0f,5f).to(6f, 6f, 11f)
                .face(DOWN).uvs(5, 5, 6, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(5, 5, 6, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(10f,0f,5f).to(11f, 6f, 11f)
                .face(DOWN).uvs(10, 5, 11, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(10, 5, 11, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,5f).to(10f, 6f, 6f)
                .face(DOWN).uvs(6, 10, 10, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 5, 10, 6).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,10f).to(10f, 6f, 11f)
                .face(DOWN).uvs(6, 5, 10, 6).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 10, 10, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,6f).to(10f, 4f, 10f)
                .face(DOWN).uvs(6, 12, 10, 16).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 6, 10, 10).texture("#soil").end()
                .end()

                .element().from(8f,6.99f,8f).to(15f, 6.99f, 15f)
                .shade(false)
                .face(DOWN).uvs(8f, 0f, 0f, 8f).texture("#top").end()
                .face(UP).uvs(8f, 8f, 0f, 0f).texture("#top").end()
                .end()

                .element().from(1f,12f,1f).to(8f, 12f, 8f)
                .shade(false)
                .face(DOWN).uvs(0f, 8f, 8f, 0f).texture("#top").end()
                .face(UP).uvs(0f, 0f, 8f, 8f).texture("#top").end()
                .end()

                .element().from(1f,16f,8f).to(8f, 16f, 15f)
                .shade(false)
                .face(DOWN).uvs(8f, 0f, 0f, 8f).texture("#top").rotation(COUNTERCLOCKWISE_90).end()
                .face(UP).uvs(0f, 0f, 8f, 8f).texture("#top").rotation(COUNTERCLOCKWISE_90).end()
                .end()

                .element().from(8f,6f,8f).to(15f, 7f, 15f)
                .shade(false)
                .face(EAST).uvs(0f, 0f, 8f, 1f).texture("#side").end()
                .face(WEST).uvs(0f, 0f, 8f, 1f).texture("#side").end()
                .face(NORTH).uvs(0f, 0f, 8f, 1f).texture("#side").end()
                .face(SOUTH).uvs(0f, 0f, 8f, 1f).texture("#side").end()
                .end()

                .element().from(1f,11f,1.01f).to(8f, 12f, 8f)
                .shade(false)
                .face(EAST).uvs(0f, 0f, 8f, 1f).texture("#side").end()
                .face(WEST).uvs(0f, 0f, 8f, 1f).texture("#side").end()
                .face(NORTH).uvs(0f, 0f, 8f, 1f).texture("#side").end()
                .face(SOUTH).uvs(0f, 0f, 8f, 1f).texture("#side").end()
                .end()

                .element().from(1f,15f,8f).to(8f, 16f, 15f)
                .shade(false)
                .face(EAST).uvs(0f, 0f, 8f, 1f).texture("#side").end()
                .face(WEST).uvs(0f, 0f, 8f, 1f).texture("#side").end()
                .face(NORTH).uvs(0f, 0f, 8f, 1f).texture("#side").end()
                .face(SOUTH).uvs(0f, 0f, 8f, 1f).texture("#side").end()
                .end()

                .element().from(4.5f,4f,8f).to(11.5f, 18f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(false).end()
                .shade(false)
                .face(NORTH).uvs(4f, 0f, 12f, 14f).texture("#stem").end()
                .face(SOUTH).uvs(4f, 0f, 12f, 14f).texture("#stem").end()
                .end()

                .element().from(4.5f,4f,8f).to(11.5f, 18f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(-45).rescale(false).end()
                .shade(false)
                .face(NORTH).uvs(4f, 0f, 12f, 14f).texture("#stem").end()
                .face(SOUTH).uvs(4f, 0f, 12f, 14f).texture("#stem").end()
                .end();
    }

    protected ModelFile makePottedKelpBlock (String name, String plant, String soil) {
        return models().withExistingParent(name, "minecraft:block/tinted_flower_pot_cross").renderType("cutout_mipped").ao(false)
                .texture("particle", blockPath.concat("flower_pot"))
                .texture("flowerpot", blockPath.concat("flower_pot"))
                .texture("soil", soil)
                .texture("plant", blockGrassesPath.concat(plant))

                .element().from(5f,0f,5f).to(6f, 6f, 11f)
                .face(DOWN) .uvs(5, 5, 6, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP)   .uvs(5, 5, 6, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(WEST) .uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST) .uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(10f,0f,5f).to(11f, 6f, 11f)
                .face(DOWN) .uvs(10, 5, 11, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP)   .uvs(10, 5, 11, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(WEST) .uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST) .uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,5f).to(10f, 6f, 6f)
                .face(DOWN).uvs(6, 10, 10, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 5, 10, 6).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,10f).to(10f, 6f, 11f)
                .face(DOWN).uvs(6, 5, 10, 6).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 10, 10, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,6f).to(10f, 4f, 10f)
                .face(DOWN).uvs(6, 12, 10, 16).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 6, 10, 10).texture("#soil").end()
                .end()

                .element().from(2.6f,4f,8f).to(13.4f, 16f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .face(NORTH).uvs(0, 0, 16, 16).texture("#plant").tintindex(0).end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#plant").tintindex(0).end()
                .end()

                .element().from(8f,4f,2.6f).to(8f, 16f, 13.4f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .face(WEST).uvs(0, 0, 16, 16).texture("#plant").tintindex(0).end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#plant").tintindex(0).end()
                .end();
    }

    protected ModelFile makePottedLeavesBlock (String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/tinted_flower_pot_cross").renderType("cutout_mipped").ao(false)
                .texture("particle", blockPath.concat("flower_pot"))
                .texture("flowerpot", blockPath.concat("flower_pot"))
                .texture("leaves", plant)
                .texture("stem", blockPath.concat("dead_bush"))
                .texture("soil", blockPath.concat("dirt"))

                .element().from(5f,0f,5f).to(6f, 6f, 11f)
                .face(DOWN).uvs(5, 5, 6, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(5, 5, 6, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(10f,0f,5f).to(11f, 6f, 11f)
                .face(DOWN).uvs(10, 5, 11, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(10, 5, 11, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,5f).to(10f, 6f, 6f)
                .face(DOWN).uvs(6, 10, 10, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 5, 10, 6).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,10f).to(10f, 6f, 11f)
                .face(DOWN).uvs(6, 5, 10, 6).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 10, 10, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,6f).to(10f, 4f, 10f)
                .face(DOWN).uvs(6, 12, 10, 16).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 6, 10, 10).texture("#soil").end()
                .end()

                .element().from(0f,10f,0f).to(16f, 26f, 16f)
                .face(UP).uvs(0, 0, 16, 16).texture("#leaves").tintindex(0).cullface(UP).end()
                .face(DOWN).uvs(0, 0, 16, 16).texture("#leaves").tintindex(0).end()
                .face(NORTH).uvs(0, 0, 16, 16).texture("#leaves").tintindex(0).cullface(NORTH).end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#leaves").tintindex(0).cullface(SOUTH).end()
                .face(WEST).uvs(0, 0, 16, 16).texture("#leaves").tintindex(0).cullface(WEST).end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#leaves").tintindex(0).cullface(EAST).end()
                .end()

                .element().from(2.6f,4f,8f).to(13.4f, 16f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .face(NORTH).uvs(0, 0, 16, 16).texture("#stem").end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#stem").end()
                .end()

                .element().from(8f,4f,2.6f).to(8f, 16f, 13.4f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .face(WEST).uvs(0, 0, 16, 16).texture("#stem").end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#stem").end()
                .end();
    }

    protected ModelFile makeTintedOverlayPottedLeavesBlock (String name, String plant, String overlay) {
        return models().withExistingParent(name, "minecraft:block/tinted_flower_pot_cross").renderType("cutout_mipped").ao(false)
                .texture("particle", blockPath.concat("flower_pot"))
                .texture("flowerpot", blockPath.concat("flower_pot"))
                .texture("leaves", plant)
                .texture("stem", blockPath.concat("dead_bush"))
                .texture("soil", blockPath.concat("dirt"))
                .texture("overlay", overlay)

                .element().from(5f,0f,5f).to(6f, 6f, 11f)
                .face(DOWN).uvs(5, 5, 6, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(5, 5, 6, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(10f,0f,5f).to(11f, 6f, 11f)
                .face(DOWN).uvs(10, 5, 11, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(10, 5, 11, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(5, 10, 6, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(10, 10, 11, 16).texture("#flowerpot").end()
                .face(WEST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .face(EAST).uvs(5, 10, 11, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,5f).to(10f, 6f, 6f)
                .face(DOWN).uvs(6, 10, 10, 11).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 5, 10, 6).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,10f).to(10f, 6f, 11f)
                .face(DOWN).uvs(6, 5, 10, 6).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 10, 10, 11).texture("#flowerpot").end()
                .face(NORTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .face(SOUTH).uvs(6, 10, 10, 16).texture("#flowerpot").end()
                .end()

                .element().from(6f,0f,6f).to(10f, 4f, 10f)
                .face(DOWN).uvs(6, 12, 10, 16).texture("#flowerpot").cullface(DOWN).end()
                .face(UP).uvs(6, 6, 10, 10).texture("#soil").end()
                .end()

                .element().from(0f,10f,0f).to(16f, 26f, 16f)
                .face(UP).uvs(0, 0, 16, 16).texture("#leaves").tintindex(0).cullface(UP).end()
                .face(DOWN).uvs(0, 0, 16, 16).texture("#leaves").tintindex(0).end()
                .face(NORTH).uvs(0, 0, 16, 16).texture("#leaves").tintindex(0).cullface(NORTH).end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#leaves").tintindex(0).cullface(SOUTH).end()
                .face(WEST).uvs(0, 0, 16, 16).texture("#leaves").tintindex(0).cullface(WEST).end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#leaves").tintindex(0).cullface(EAST).end()
                .end()

                .element().from(2.6f,4f,8f).to(13.4f, 16f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .face(NORTH).uvs(0, 0, 16, 16).texture("#stem").end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#stem").end()
                .end()

                .element().from(8f,4f,2.6f).to(8f, 16f, 13.4f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .face(WEST).uvs(0, 0, 16, 16).texture("#stem").end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#stem").end()
                .end()

                .element().from(0.0F, 10.0F, 0.0F).to(16.0F, 26.0F, 16.0F)
                .face(DOWN).uvs(0, 0, 16, 16).texture("#overlay").end()
                .face(UP).uvs(0, 0, 16, 16).texture("#overlay").cullface(UP).end()
                .face(NORTH).uvs(0, 0, 16, 16).texture("#overlay").cullface(NORTH).end()
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#overlay").cullface(SOUTH).end()
                .face(WEST).uvs(0, 0, 16, 16).texture("#overlay").cullface(WEST).end()
                .face(EAST).uvs(0, 0, 16, 16).texture("#overlay").cullface(EAST).end()
                .end();

    }

    protected ModelFile makePortalFrameBlock(String name) {
        return models().withExistingParent(name, "minecraft:block/end_portal_frame").renderType("cutout_mipped");
    }

    protected ModelFile makeFilledPortalFrameBlock(String name) {
        return models().withExistingParent(name, "minecraft:block/end_portal_frame_filled").renderType("cutout_mipped");
    }

    protected ModelFile makeTintedHugeClover(String name) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("petal", "grasses:block/bop/huge_clover_petal")
                .texture("stem", "grasses:block/bop/huge_clover_stem")
                .texture("stem_back", "grasses:block/bop/huge_clover_stem_back")
                .texture("particle", "grasses:block/bop/huge_clover_petal")

                .element().from(0f,15f,0f).to(16f, 15f, 16f)
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#petal").tintindex(0).end()
                .face(DOWN).uvs(0f, 0f, 16f, 16f).texture("#petal").rotation(COUNTERCLOCKWISE_90).tintindex(0).end()
                .end()

                .element().from(0f,0f,8f).to(16f, 16f, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(-45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(0f, 0f, 16f, 16f).texture("#stem_back").tintindex(0).end()
                .face(SOUTH).uvs(0f, 0f, 16f, 16f).texture("#stem").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedFlowerbed1(String name, String flowerbed, String stem) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("flowerbed", blockGrassesPath.concat(flowerbed))
                .texture("stem", blockGrassesPath.concat(stem))
                .texture("particle", blockGrassesPath.concat(flowerbed))

                .element().from(0f, 2.99f, 0f).to(8f, 2.99f, 8f)
                .face(UP).uvs(0f, 0f, 8f, 8f).texture("#flowerbed").tintindex(0).end()
                .face(DOWN).uvs(0f, 8f, 8f, 0f).texture("#flowerbed").tintindex(0).end()
                .end()

                .element().from(5.3f, 0f, -0.8f).to(5.3f, 2.99f, 0.2f)
                .rotation().origin(0f, 0f, 0f).axis(Axis.Y).angle(-45).end()
                .face(EAST).uvs(0f, 4f, 1f, 7f).texture("#stem").tintindex(0).end()
                .face(WEST).uvs(0f, 4f, 1f, 7f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(4.8f, 0f, -0.3f).to(5.8f, 2.99f, -0.3f)
                .rotation().origin(0f, 0f, 0f).axis(Axis.Y).angle(-45).end()
                .face(NORTH).uvs(0f, 4f, 1f, 7f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(0f, 4f, 1f, 7f).texture("#stem").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedFlowerbed3(String name, String flowerbed, String stem) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("flowerbed", blockGrassesPath.concat(flowerbed))
                .texture("stem", blockGrassesPath.concat(stem))
                .texture("particle", blockGrassesPath.concat(flowerbed))

                .element().from(8f, 2f, 8f).to(16f, 2f, 16f)
                .face(UP).uvs(8f, 8f, 16f, 16f).texture("#flowerbed").tintindex(0).end()
                .face(DOWN).uvs(8f, 16f, 16f, 8f).texture("#flowerbed").tintindex(0).end()
                .end()

                .element().from(15.9f, 0f, 0.1f).to(16.9f, 2f, 0.1f)
                .rotation().origin(0.5f, 0f, 0.5f).axis(Axis.Y).angle(-45).end()
                .face(NORTH).uvs(0f, 5f, 1f, 7f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(0f, 5f, 1f, 7f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(16.4f, 0f, -0.4f).to(16.4f, 2f, 0.6f)
                .rotation().origin(0.5f, 0f, 0.5f).axis(Axis.Y).angle(-45).end()
                .face(EAST).uvs(0f, 5f, 1f, 7f).texture("#stem").tintindex(0).end()
                .face(WEST).uvs(0f, 5f, 1f, 7f).texture("#stem").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedFlowerbed2(String name, String flowerbed, String stem) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("flowerbed", blockGrassesPath.concat(flowerbed))
                .texture("stem", blockGrassesPath.concat(stem))
                .texture("particle", blockGrassesPath.concat(flowerbed))

                .element().from(0f, 1f, 8f).to(8f, 1f, 16f)
                .face(UP).uvs(0f, 8f, 8f, 16f).texture("#flowerbed").tintindex(0).end()
                .face(DOWN).uvs(0f, 16f, 8f, 8f).texture("#flowerbed").tintindex(0).end()
                .end()

                .element().from(0f, 1f, 8f).to(8f, 1f, 16f)
                .face(UP).uvs(0f, 8f, 8f, 16f).texture("#flowerbed").tintindex(0).end()
                .face(DOWN).uvs(0f, 16f, 8f, 8f).texture("#flowerbed").tintindex(0).end()
                .end()

                .element().from(9.9f, 0f, 4.1f).to(9.9f, 1f, 5.1f)
                .rotation().origin(0f, 0f, 1f).axis(Axis.Y).angle(-45).end()
                .face(NORTH).uvs(0f, 6f, 1f, 7f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(0f, 6f, 1f, 7f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(9.4f, 0f, 4.6f).to(10.4f, 1f, 4.6f)
                .rotation().origin(0f, 0f, 1f).axis(Axis.Y).angle(-45).end()
                .face(EAST).uvs(0f, 6f, 1f, 7f).texture("#stem").tintindex(0).end()
                .face(WEST).uvs(0f, 6f, 1f, 7f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(10.8f, 0f, 8.8f).to(11.8f, 1f, 8.8f)
                .rotation().origin(0f, 0f, 1f).axis(Axis.Y).angle(-45).end()
                .face(NORTH).uvs(0f, 6f, 1f, 7f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(0f, 6f, 1f, 7f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(11.3f, 0f, 8.3f).to(11.3f, 1f, 9.3f)
                .rotation().origin(0f, 0f, 1f).axis(Axis.Y).angle(-45).end()
                .face(EAST).uvs(0f, 6f, 1f, 7f).texture("#stem").tintindex(0).end()
                .face(WEST).uvs(0f, 6f, 1f, 7f).texture("#stem").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedFlowerbed4(String name, String flowerbed, String stem) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("flowerbed", blockGrassesPath.concat(flowerbed))
                .texture("stem", blockGrassesPath.concat(stem))
                .texture("particle", blockGrassesPath.concat(flowerbed))

                .element().from(8f, 2f, 0f).to(16f, 2f, 8f)
                .face(UP).uvs(8f, 0f, 16f, 8f).texture("#flowerbed").tintindex(0).end()
                .face(DOWN).uvs(8f, 8f, 16f, 0f).texture("#flowerbed").tintindex(0).end()
                .end()

                .element().from(13.2f, 0f, -9.9f).to(13.2f, 2f, -8.9f)
                .rotation().origin(-1f, 0f, -3f).axis(Axis.Y).angle(-45).end()
                .face(EAST).uvs(0f, 5f, 1f, 7f).texture("#stem").tintindex(0).end()
                .face(WEST).uvs(0f, 5f, 1f, 7f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(12.7f, 0f, -9.4f).to(13.7f, 2f, -9.4f)
                .rotation().origin(-1f, 0f, -3f).axis(Axis.Y).angle(-45).end()
                .face(NORTH).uvs(0f, 5f, 1f, 7f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(0f, 5f, 1f, 7f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(13.2f, 0f, -4.2f).to(13.2f, 2f, -3.2f)
                .rotation().origin(-1f, 0f, -3f).axis(Axis.Y).angle(-45).end()
                .face(EAST).uvs(0f, 5f, 1f, 7f).texture("#stem").tintindex(0).end()
                .face(WEST).uvs(0f, 5f, 1f, 7f).texture("#stem").tintindex(0).end()
                .end()

                .element().from(12.7f, 0f, -3.7f).to(13.7f, 2f, -3.7f)
                .rotation().origin(-1f, 0f, -3f).axis(Axis.Y).angle(-45).end()
                .face(NORTH).uvs(0f, 5f, 1f, 7f).texture("#stem").tintindex(0).end()
                .face(SOUTH).uvs(0f, 5f, 1f, 7f).texture("#stem").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedHugeLily(String name, String lily) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("lily", blockGrassesPath.concat(lily))
                .texture("particle", blockGrassesPath.concat(lily))

                .element().from(0f, 0.25f,  0f).to(16f, 0.25f, 16f)
                .face(DOWN).uvs(0f, 16f, 16f, 0f).texture("#lily").tintindex(0).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#lily").tintindex(0).end()
                .end();
    }

    protected ModelFile makeInBarsCap(String name) {
        return models().withExistingParent(name, "minecraft:block/iron_bars_cap").renderType("cutout")
                .texture("bars", blockPath.concat("iron_bars"))
                .texture("edge", blockPath.concat("iron_bars"))
                .texture("particle", blockPath.concat("iron_bars"));
    }
    protected ModelFile makeInBarsCapAlt(String name) {
        return models().withExistingParent(name, "minecraft:block/iron_bars_cap_alt").renderType("cutout")
                .texture("bars", blockPath.concat("iron_bars"))
                .texture("edge", blockPath.concat("iron_bars"))
                .texture("particle", blockPath.concat("iron_bars"));
    }
    protected ModelFile makeInBarsPost(String name) {
        return models().withExistingParent(name, "minecraft:block/iron_bars_post").renderType("cutout")
                .texture("bars", blockPath.concat("iron_bars"))
                .texture("edge", blockPath.concat("iron_bars"))
                .texture("particle", blockPath.concat("iron_bars"));
    }
    protected ModelFile makeInBarsPostEnds(String name) {
        return models().withExistingParent(name, "minecraft:block/iron_bars_post_ends").renderType("cutout")
                .texture("edge", blockPath.concat("iron_bars"))
                .texture("particle", blockPath.concat("iron_bars"));
    }
    protected ModelFile makeInBarsSide(String name) {
        return models().withExistingParent(name, "minecraft:block/iron_bars_side").renderType("cutout")
                .texture("bars", blockPath.concat("iron_bars"))
                .texture("edge", blockPath.concat("iron_bars"))
                .texture("particle", blockPath.concat("iron_bars"));
    }
    protected ModelFile makeInBarsSideAlt(String name) {
        return models().withExistingParent(name, "minecraft:block/iron_bars_side_alt").renderType("cutout")
                .texture("bars", blockPath.concat("iron_bars"))
                .texture("edge", blockPath.concat("iron_bars"))
                .texture("particle", blockPath.concat("iron_bars"));
    }
    protected ModelFile makeTintedPlantBlockForBars(String name, String plant, float yFrom, float yTo) {
        return models().withExistingParent(name, "minecraft:block/tinted_cross").renderType("cutout_mipped")
                .texture("cross", plant)

                .element().from(0.8f, yFrom,8f).to(15.2f, yTo, 8f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .shade(false)
                .face(NORTH).uvs(0f, 0f, 16f, 16f).texture("#cross").tintindex(0).end()
                .face(SOUTH).uvs(0f, 0f, 16f, 16f).texture("#cross").tintindex(0).end()
                .end()

                .element().from(8f,yFrom,0.8f).to(8f, yTo, 15.2f)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45).rescale(true).end()
                .shade(false)
                .face(WEST).uvs(0f, 0f, 16f, 16f).texture("#cross").tintindex(0).end()
                .face(EAST).uvs(0f, 0f, 16f, 16f).texture("#cross").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedVineBlockForBarsStraight (String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)
                .element().from(0f, 0f, 9f).to(16f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#vine").tintindex(0).end()
                .face(NORTH).uvs(16, 0, 0, 16).texture("#vine").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedVineBlockForBarsStraightDouble (String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)
                .element().from(0f, 0f, 7f).to(16f, 16f, 7f)
                .shade(false)
                .face(NORTH).uvs(0, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(0f, 0f, 9f).to(16f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedVineBlockForBarsInnerTurn(String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)
                .element().from(0f, 0f, 9f).to(8f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .face(NORTH).uvs(8, 0, 0, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(7f, 0f, 8f).to(7f, 16f, 16f)
                .shade(false)
                .face(WEST).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .face(EAST).uvs(16, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedVineBlockForBarsOuterTurn(String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)
                .element().from(0f, 0f, 9f).to(8f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .face(NORTH).uvs(8, 0, 0, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(9f, 0f, 0f).to(9f, 16f, 8f)
                .shade(false)
                .face(WEST).uvs(16, 0, 8, 16).texture("#vine").tintindex(0).end()
                .face(EAST).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedVineBlockForBarsOuterTurnDouble(String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)
                .element().from(0f, 0f, 9f).to(8f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(9f, 0f, 0f).to(9f, 16f, 8f)
                .shade(false)
                .face(EAST).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(0f, 0f, 7f).to(8f, 16f, 7f)
                .shade(false)
                .face(NORTH).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(7f, 0f, 0f).to(7f, 16f, 8f)
                .shade(false)
                .face(WEST).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedVineBlockForBarsInnerTurnDouble(String name, String plant) { //////////////////////////
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)

                .element().from(0f, 0f, 9f).to(8f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(7f, 0f, 8f).to(7f, 16f, 16f)
                .shade(false)
                .face(WEST).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(0f, 0f, 7f).to(8f, 16f, 7f)
                .shade(false)
                .face(NORTH).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(9f, 0f, 8f).to(9f, 16f, 16f)
                .shade(false)
                .face(EAST).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedVineBlockForBarsTripleInner(String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)

                .element().from(0f, 0f, 9f).to(8f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .face(NORTH).uvs(8, 0, 0, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(7f, 0f, 8f).to(7f, 16f, 16f)
                .shade(false)
                .face(WEST).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(9f, 0f, 8f).to(9f, 16f, 16f)
                .shade(false)
                .face(EAST).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(8f, 0f, 9f).to(16f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(8f, 0f, 7f).to(16f, 16f, 7f)
                .shade(false)
                .face(NORTH).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(9f, 0f, 0f).to(9f, 16f, 8f)
                .shade(false)
                .face(EAST).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .face(WEST).uvs(16, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedVineBlockForBarsQuadrupleInner(String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)

                .element().from(0f, 0f, 9f).to(8f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(7f, 0f, 8f).to(7f, 16f, 16f)
                .shade(false)
                .face(WEST).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(9f, 0f, 8f).to(9f, 16f, 16f)
                .shade(false)
                .face(EAST).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(8f, 0f, 9f).to(16f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(8f, 0f, 7f).to(16f, 16f, 7f)
                .shade(false)
                .face(NORTH).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(9f, 0f, 0f).to(9f, 16f, 8f)
                .shade(false)
                .face(EAST).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(7f, 0f, 0f).to(7f, 16f, 8f)
                .shade(false)
                .face(WEST).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(0f, 0f, 7f).to(8f, 16f, 7f)
                .shade(false)
                .face(NORTH).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedVineBlockForBarsDiagonalInner(String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)

                .element().from(0f, 0f, 9f).to(8f, 16f, 9f)
                .shade(false)
                .face(NORTH).uvs(8, 0, 0, 16).texture("#vine").tintindex(0).end()
                .face(SOUTH).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(7f, 0f, 8f).to(7f, 16f, 16f)
                .shade(false)
                .face(WEST).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .face(EAST).uvs(16, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(8f, 0f, 7f).to(16f, 16f, 7f)
                .shade(false)
                .face(NORTH).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .face(SOUTH).uvs(8, 0, 0, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(9f, 0f, 0f).to(9f, 16f, 8f)
                .shade(false)
                .face(EAST).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .face(WEST).uvs(16, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedVineBlockForBarsInnerT(String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)

                .element().from(0f, 0f, 9f).to(16f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#vine").tintindex(0).end()
                .face(NORTH).uvs(16, 0, 0, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(7f, 0f, 8).to(7f, 16f, 16f)
                .shade(false)
                .face(WEST).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(9f, 0f, 8f).to(9f, 16f, 16f)
                .shade(false)
                .face(EAST).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedVineBlockForBarsFullT(String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)

                .element().from(0f, 0f, 9f).to(16f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(0, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(0f, 0f, 7f).to(16f, 16f, 7f)
                .shade(false)
                .face(NORTH).uvs(0, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(7f, 0f, 8).to(7f, 16f, 16f)
                .shade(false)
                .face(WEST).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(9f, 0f, 8f).to(9f, 16f, 16f)
                .shade(false)
                .face(EAST).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedVineBlockForBarsLeftNotFullT(String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)

                .element().from(0f, 0f, 9f).to(8f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(0f, 0f, 7f).to(16f, 16f, 7f)
                .shade(false)
                .face(NORTH).uvs(0, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(8f, 0f, 7f).to(16f, 16f, 7f)
                .shade(false)
                .face(SOUTH).uvs(8, 0, 0, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(7f, 0f, 8).to(7f, 16f, 16f)
                .shade(false)
                .face(WEST).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .face(EAST).uvs(16, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end();
    }
    protected ModelFile makeTintedVineBlockForBarsRightNotFullT(String name, String plant) {
        return models().withExistingParent(name, "minecraft:block/vine").renderType("cutout_mipped")
                .texture("particle", plant)
                .texture("vine", plant)

                .element().from(8f, 0f, 9f).to(16f, 16f, 9f)
                .shade(false)
                .face(SOUTH).uvs(8, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(0f, 0f, 7f).to(16f, 16f, 7f)
                .shade(false)
                .face(NORTH).uvs(0, 0, 16, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(0f, 0f, 7f).to(8f, 16f, 7f)
                .shade(false)
                .face(SOUTH).uvs(16, 0, 8, 16).texture("#vine").tintindex(0).end()
                .end()

                .element().from(9f, 0f, 8f).to(9f, 16f, 16f)
                .shade(false)
                .face(EAST).uvs(0, 0, 8, 16).texture("#vine").tintindex(0).end()
                .face(WEST).uvs(8, 0, 0, 16).texture("#vine").tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedWaterlily(String name) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("outer", blockGrassesPath.concat("bop/waterlily_outer"))
                .texture("inner", blockGrassesPath.concat("bop/waterlily_inner"))
                .texture("stem", blockGrassesPath.concat("bop/waterlily_stem"))
                .texture("overlay", blockGrassesPath.concat("bop/waterlily_inner_overlay"))
                .texture("particle", blockGrassesPath.concat("bop/waterlily_outer"))


                .element().from(7.4f, -1.5f, 0f).to(23.4f, -1.5f, 16f)
                .shade(false)
                .rotation().origin(8f, 0f, 0f).axis(Axis.Z).angle(22.5f).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#outer").rotation(CLOCKWISE_90).tintindex(0).end()
                .face(DOWN).uvs(0f, 16f, 16f, 0f).texture("#outer").rotation(COUNTERCLOCKWISE_90).tintindex(0).end()
                .end()


                .element().from(-7.4f, -1.5f, 0f).to(8.6f, -1.5f, 16f)
                .shade(false)
                .rotation().origin(8f, 0f, 0f).axis(Axis.Z).angle(-22.5f).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#outer").rotation(COUNTERCLOCKWISE_90).tintindex(0).end()
                .face(DOWN).uvs(0f, 16f, 16f, 0f).texture("#outer").rotation(CLOCKWISE_90).tintindex(0).end()
                .end()

                .element().from(0f, -1.5f, 7.4f).to(16f, -1.5f, 23.4f)
                .shade(false)
                .rotation().origin(0f, 0f, 8f).axis(Axis.X).angle(-22.5f).end()
                .face(UP).uvs(16f, 16f, 0f, 0f).texture("#outer").tintindex(0).end()
                .face(DOWN).uvs(16f, 0f, 0f, 16f).texture("#outer").tintindex(0).end()
                .end()

                .element().from(0f, -1.5f, -7.4f).to(16f, -1.5f, 8.6f)
                .shade(false)
                .rotation().origin(0f, 0f, 8f).axis(Axis.X).angle(22.5f).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#outer").tintindex(0).end()
                .face(DOWN).uvs(0f, 16f, 16f, 0f).texture("#outer").tintindex(0).end()
                .end()

                .element().from(8.6f, -1.5f, 0f).to(8.6f, 14.5f, 16f)
                .shade(false)
                .rotation().origin(8f, 0f, 0f).axis(Axis.Z).angle(-22.5f).end()
                .face(EAST).uvs(0f, 16f, 16f, 0f).texture("#inner").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(0).end()
                .face(WEST).uvs(0f, 0f, 16f, 16f).texture("#inner").tintindex(0).end()
                .end()

                .element().from(7.4f, -1.5f, 0f).to(7.4f, 14.5f, 16f)
                .shade(false)
                .rotation().origin(8f, 0f, 0f).axis(Axis.Z).angle(22.5f).end()
                .face(EAST).uvs(0f, 0f, 16f, 16f).texture("#inner").tintindex(0).end()
                .face(WEST).uvs(0f, 16f, 16f, 0f).texture("#inner").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(0).end()
                .end()

                .element().from(0f, -1.5f, 8.6f).to(16f, 14.5f, 8.6f)
                .shade(false)
                .rotation().origin(0f, 0f, 8f).axis(Axis.X).angle(22.5f).end()
                .face(NORTH).uvs(16f, 16f,0f, 0f).texture("#inner").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(0).end()
                .face(SOUTH).uvs(16f, 0f, 0f, 16f).texture("#inner").tintindex(0).end()
                .end()

                .element().from(0f, -1.5f, 7.4f).to(16f, 14.5f, 7.4f)
                .shade(false)
                .rotation().origin(0f, 0f, 8f).axis(Axis.X).angle(-22.5f).end()
                .face(NORTH).uvs(0f, 16f,16f, 0f).texture("#inner").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(0).end()
                .face(SOUTH).uvs(0f, 0f, 16f, 16f).texture("#inner").tintindex(0).end()
                .end()

                .element().from(0.8f, -14.6f, 8f).to(15.2f, 1.4f, 8f)
                .shade(false)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45f).rescale(true).end()
                .face(NORTH).uvs(0f, 0f,16f, 16f).texture("#stem").end()
                .face(SOUTH).uvs(0f, 0f, 16f, 16f).texture("#stem").end()
                .end()

                .element().from(8f, -14.6f, 0.8f).to(8f, 1.4f, 15.2f)
                .shade(false)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45f).rescale(true).end()
                .face(EAST).uvs(0f, 0f,16f, 16f).texture("#stem").end()
                .face(WEST).uvs(0f, 0f, 16f, 16f).texture("#stem").end()
                .end()

                .element().from(8.6f, -1.5f, 0f).to(8.6f, 14.5f, 16f)
                .shade(false)
                .rotation().origin(8f, 0f, 0f).axis(Axis.Z).angle(-22.5f).end()
                .face(EAST).uvs(0f, 16f, 16f, 0f).texture("#overlay").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(-1).end()
                .face(WEST).uvs(0f, 0f, 16f, 16f).texture("#overlay").end()
                .end()

                .element().from(7.4f, -1.5f, 0f).to(7.4f, 14.5f, 16f)
                .shade(false)
                .rotation().origin(8f, 0f, 0f).axis(Axis.Z).angle(22.5f).end()
                .face(EAST).uvs(0f, 0f, 16f, 16f).texture("#overlay").end()
                .face(WEST).uvs(0f, 16f, 16f, 0f).texture("#overlay").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(-1).end()
                .end()

                .element().from(0f, -1.5f, 8.6f).to(16f, 14.5f, 8.6f)
                .shade(false)
                .rotation().origin(0f, 0f, 8f).axis(Axis.X).angle(22.5f).end()
                .face(NORTH).uvs(16f, 16f,0f, 0f).texture("#overlay").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(-1).end()
                .face(SOUTH).uvs(16f, 0f, 0f, 16f).texture("#overlay").end()
                .end()

                .element().from(0f, -1.5f, 7.4f).to(16f, 14.5f, 7.4f)
                .shade(false)
                .rotation().origin(0f, 0f, 8f).axis(Axis.X).angle(-22.5f).end()
                .face(NORTH).uvs(0f, 16f,16f, 0f).texture("#overlay").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(-1).end()
                .face(SOUTH).uvs(0f, 0f, 16f, 16f).texture("#overlay").end()
                .end();
    }

    protected ModelFile makeTintedLeafPile(String name, String pile) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("pile", blockGrassesPath.concat(pile))
                .texture("particle", blockGrassesPath.concat(pile))

                .element().from(0f, 0f, 4f).to(16f, 0f, 20f)
                .rotation().origin(8f, 0f, 16f).axis(Axis.X).angle(22.5f).rescale(true).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#pile").tintindex(0).end()
                .face(DOWN).uvs(0f, 0f, 16f, 16f).texture("#pile").tintindex(0).end()
                .end()

                .element().from(-4f, 0f, 0f).to(12f, 0f, 16f)
                .rotation().origin(0f, 0f, 8f).axis(Axis.Z).angle(22.5f).rescale(true).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#pile").rotation(CLOCKWISE_90).tintindex(0).end()
                .face(DOWN).uvs(0f, 0f, 16f, 16f).texture("#pile").rotation(CLOCKWISE_90).tintindex(0).end()
                .end()

                .element().from(0f, 0f, -4f).to(16f, 0f, 12f)
                .rotation().origin(8f, 0f, 0f).axis(Axis.X).angle(-22.5f).rescale(true).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#pile").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(0).end()
                .face(DOWN).uvs(0f, 0f, 16f, 16f).texture("#pile").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(0).end()
                .end()

                .element().from(4f, 0f, 0f).to(20f, 0f, 16f)
                .rotation().origin(16f, 0f, 8f).axis(Axis.Z).angle(-22.5f).rescale(true).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#pile").rotation(COUNTERCLOCKWISE_90).tintindex(0).end()
                .face(DOWN).uvs(0f, 0f, 16f, 16f).texture("#pile").rotation(COUNTERCLOCKWISE_90).tintindex(0).end()
                .end();
    }

    protected ModelFile makeTintedHugeLilyWithFlower(String name, String lily, float y, String inner, String outer) {
        return models().withExistingParent(name, "minecraft:block/block").renderType("cutout_mipped")
                .texture("lily", blockGrassesPath.concat(lily))
                .texture("particle", blockGrassesPath.concat(lily))
                .texture("outer", blockGrassesPath.concat(outer))
                .texture("inner", blockGrassesPath.concat(inner))
                .texture("stem", blockGrassesPath.concat("bop/waterlily_stem"))
                .texture("overlay", blockGrassesPath.concat("bop/waterlily_inner_overlay"))

                .element().from(0f, 0.25f,  0f).to(16f, 0.25f, 16f)
                .face(DOWN).uvs(0f, 16f, 16f, 0f).texture("#lily").tintindex(0).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#lily").tintindex(0).end()
                .end()

                .element().from(7.4f, -1.5f+y, 0f).to(23.4f, -1.5f+y, 16f)
                .shade(false)
                .rotation().origin(8f, 0f, 0f).axis(Axis.Z).angle(22.5f).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#outer").rotation(CLOCKWISE_90).tintindex(-1).end()
                .face(DOWN).uvs(0f, 16f, 16f, 0f).texture("#outer").rotation(COUNTERCLOCKWISE_90).tintindex(-1).end()
                .end()

                .element().from(-7.4f, -1.5f+y, 0f).to(8.6f, -1.5f+y, 16f)
                .shade(false)
                .rotation().origin(8f, 0f, 0f).axis(Axis.Z).angle(-22.5f).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#outer").rotation(COUNTERCLOCKWISE_90).tintindex(-1).end()
                .face(DOWN).uvs(0f, 16f, 16f, 0f).texture("#outer").rotation(CLOCKWISE_90).tintindex(-1).end()
                .end()

                .element().from(0f, -1.5f+y, 7.4f).to(16f, -1.5f+y, 23.4f)
                .shade(false)
                .rotation().origin(0f, 0f, 8f).axis(Axis.X).angle(-22.5f).end()
                .face(UP).uvs(16f, 16f, 0f, 0f).texture("#outer").tintindex(-1).end()
                .face(DOWN).uvs(16f, 0f, 0f, 16f).texture("#outer").tintindex(-1).end()
                .end()

                .element().from(0f, -1.5f+y, -7.4f).to(16f, -1.5f+y, 8.6f)
                .shade(false)
                .rotation().origin(0f, 0f, 8f).axis(Axis.X).angle(22.5f).end()
                .face(UP).uvs(0f, 0f, 16f, 16f).texture("#outer").tintindex(-1).end()
                .face(DOWN).uvs(0f, 16f, 16f, 0f).texture("#outer").tintindex(-1).end()
                .end()

                .element().from(8.6f, -1.5f+y, 0f).to(8.6f, 14.5f+y, 16f)
                .shade(false)
                .rotation().origin(8f, 0f, 0f).axis(Axis.Z).angle(-22.5f).end()
                .face(EAST).uvs(0f, 16f, 16f, 0f).texture("#inner").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(-1).end()
                .face(WEST).uvs(0f, 0f, 16f, 16f).texture("#inner").tintindex(-1).end()
                .end()

                .element().from(7.4f, -1.5f+y, 0f).to(7.4f, 14.5f+y, 16f)
                .shade(false)
                .rotation().origin(8f, 0f, 0f).axis(Axis.Z).angle(22.5f).end()
                .face(EAST).uvs(0f, 0f, 16f, 16f).texture("#inner").tintindex(-1).end()
                .face(WEST).uvs(0f, 16f, 16f, 0f).texture("#inner").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(-1).end()
                .end()

                .element().from(0f, -1.5f+y, 8.6f).to(16f, 14.5f+y, 8.6f)
                .shade(false)
                .rotation().origin(0f, 0f, 8f).axis(Axis.X).angle(22.5f).end()
                .face(NORTH).uvs(16f, 16f,0f, 0f).texture("#inner").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(-1).end()
                .face(SOUTH).uvs(16f, 0f, 0f, 16f).texture("#inner").tintindex(-1).end()
                .end()

                .element().from(0f, -1.5f+y, 7.4f).to(16f, 14.5f+y, 7.4f)
                .shade(false)
                .rotation().origin(0f, 0f, 8f).axis(Axis.X).angle(-22.5f).end()
                .face(NORTH).uvs(0f, 16f,16f, 0f).texture("#inner").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(-1).end()
                .face(SOUTH).uvs(0f, 0f, 16f, 16f).texture("#inner").tintindex(-1).end()
                .end()

                .element().from(0.8f, -14.6f+y, 8f).to(15.2f, 1.4f+y, 8f)
                .shade(false)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45f).rescale(true).end()
                .face(NORTH).uvs(0f, 0f,16f, 16f).texture("#stem").end()
                .face(SOUTH).uvs(0f, 0f, 16f, 16f).texture("#stem").end()
                .end()

                .element().from(8f, -14.6f+y, 0.8f).to(8f, 1.4f+y, 15.2f)
                .shade(false)
                .rotation().origin(8f, 8f, 8f).axis(Axis.Y).angle(45f).rescale(true).end()
                .face(EAST).uvs(0f, 0f,16f, 16f).texture("#stem").end()
                .face(WEST).uvs(0f, 0f, 16f, 16f).texture("#stem").end()
                .end()

                .element().from(8.6f, -1.5f+y, 0f).to(8.6f, 14.5f+y, 16f)
                .shade(false)
                .rotation().origin(8f, 0f, 0f).axis(Axis.Z).angle(-22.5f).end()
                .face(EAST).uvs(0f, 16f, 16f, 0f).texture("#overlay").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(-1).end()
                .face(WEST).uvs(0f, 0f, 16f, 16f).texture("#overlay").end()
                .end()

                .element().from(7.4f, -1.5f+y, 0f).to(7.4f, 14.5f+y, 16f)
                .shade(false)
                .rotation().origin(8f, 0f, 0f).axis(Axis.Z).angle(22.5f).end()
                .face(EAST).uvs(0f, 0f, 16f, 16f).texture("#overlay").end()
                .face(WEST).uvs(0f, 16f, 16f, 0f).texture("#overlay").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(-1).end()
                .end()

                .element().from(0f, -1.5f+y, 8.6f).to(16f, 14.5f+y, 8.6f)
                .shade(false)
                .rotation().origin(0f, 0f, 8f).axis(Axis.X).angle(22.5f).end()
                .face(NORTH).uvs(16f, 16f,0f, 0f).texture("#overlay").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(-1).end()
                .face(SOUTH).uvs(16f, 0f, 0f, 16f).texture("#overlay").end()
                .end()

                .element().from(0f, -1.5f+y, 7.4f).to(16f, 14.5f+y, 7.4f)
                .shade(false)
                .rotation().origin(0f, 0f, 8f).axis(Axis.X).angle(-22.5f).end()
                .face(NORTH).uvs(0f, 16f,16f, 0f).texture("#overlay").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).tintindex(-1).end()
                .face(SOUTH).uvs(0f, 0, 16f, 16f).texture("#overlay").end()
                .end();
    }
}


