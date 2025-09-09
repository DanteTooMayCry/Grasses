package net.night.grasses.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.night.grasses.Grasses;
import net.night.grasses.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.tags.BlockTags.*;
import static net.minecraft.world.level.block.Blocks.*;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Grasses.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        for(DeferredBlock<Block> grassesBlock : grassRegistryBlocksList){
            this.tag(ModTags.Blocks.ALL_MOD_GRASS_BLOCKS)
                    .add(grassesBlock.get());
        }

        for(DeferredBlock<Block> grassesBlock : grassRegistrySlabBlocksList){
            this.tag(ModTags.Blocks.ALL_MOD_GRASS_SLABS)
                    .add(grassesBlock.get());
        }

        this.tag(ModTags.Blocks.ALL_MOD_GRASS)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS_BLOCKS)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS_SLABS);

        this.tag(ModTags.Blocks.OTHER_GRASS_BLOCKS);
        
        for(DeferredBlock<Block> grassesBlock : grassesLeavesRegistryBlocksList){
            this.tag(ModTags.Blocks.ALL_MOD_LEAVES)
                    .add(grassesBlock.get());
            this.tag(LEAVES)
                    .add(grassesBlock.get());
        }

        for(DeferredBlock<Block> grassesBlock : plantInBarsRegistryBlockList){
            this.tag(DRAGON_IMMUNE)
                    .add(grassesBlock.get());
            this.tag(MINEABLE_WITH_PICKAXE)
                    .add(grassesBlock.get());
        }

        this.tag(ModTags.Blocks.LEAVES_LOGS_ADVANCEMENTS)
                .addTag(LOGS_THAT_BURN)
                .addTag(LEAVES);

        this.tag(ModTags.Blocks.BONE_MEAL_ABLE_BLOCKS)
                .addTag(ModTags.Blocks.PODZOL_BLOCKS)
                .addTag(ModTags.Blocks.MYCELIUM_BLOCKS)
                .addTag(ModTags.Blocks.SOUL_SAND_BLOCKS);

        this.tag(ModTags.Blocks.CACTUS_LIKE_PLANTS_1);
        this.tag(ModTags.Blocks.CACTUS_LIKE_PLANTS_2);
        this.tag(ModTags.Blocks.CACTUS_LIKE_PLANTS_3);


        this.tag(ModTags.Blocks.MYCELIUM_BLOCKS)
                .add(MYCELIUM)
                .add(MYCELIUM_SLAB_BLOCK.get())
                .add(GROW_MYCELIUM_BLOCK.get());

        this.tag(ModTags.Blocks.PODZOL_BLOCKS)
                .add(PODZOL)
                .add(GROW_PODZOL_BLOCK.get())
                .add(PODZOL_SLAB_BLOCK.get());

        this.tag(ModTags.Blocks.NYLIUM_SLAB_BLOCKS)
                .add(CRIMSON_NYLIUM_SLAB_BLOCK.get())
                .add(WARPED_NYLIUM_SLAB_BLOCK.get());

        this.tag(ModTags.Blocks.CRIMSON_NYLIUM_BLOCKS)
                .add(CRIMSON_NYLIUM_SLAB_BLOCK.get())
                .add(GROW_CRIMSON_NYLIUM_BLOCK.get());

        this.tag(ModTags.Blocks.WARPED_NYLIUM_BLOCKS)
                .add(WARPED_NYLIUM_SLAB_BLOCK.get())
                .add(GROW_WARPED_NYLIUM_BLOCK.get());

        this.tag((ModTags.Blocks.NYLIUM_BLOCKS))
                .addTag(ModTags.Blocks.CRIMSON_NYLIUM_BLOCKS)
                .addTag(ModTags.Blocks.WARPED_NYLIUM_BLOCKS);

        this.tag(ModTags.Blocks.STEMS)
                .addTag(CRIMSON_STEMS)
                .addTag(WARPED_STEMS);

        this.tag(ModTags.Blocks.BLOCKS_ON_CRIMSON_FUNGUS)
                .add(NETHER_WART_BLOCK)
                .add(WEEPING_VINES)
                .add(WEEPING_VINES_PLANT);

        this.tag(ModTags.Blocks.BLOCKS_ON_WARPED_FUNGUS)
                .add(WARPED_WART_BLOCK)
                .add(TWISTING_VINES)
                .add(TWISTING_VINES_PLANT);

        this.tag(ModTags.Blocks.NETHERRACK_BASE_BLOCKS)
                .addTag(NYLIUM)
                .add(NETHERRACK)
                .add(NETHERRACK_SLAB_BLOCK.get());

        this.tag(BASE_STONE_NETHER)
                .add(NETHERRACK_SLAB_BLOCK.get());

        this.tag(ModTags.Blocks.SOUL_SAND_BLOCKS)
                .add(SOUL_SAND)
                .add(SOUL_SAND_SLAB_BLOCK.get());

        this.tag(ModTags.Blocks.UNDERWATER_TINTED_BONEMEALS)
                .add(SEAGRASS_TINTED.get());

        this.tag(ModTags.Blocks.MOD_BIG_DRIPLEAF_PLACEABLE)
                .addTag(SMALL_DRIPLEAF_PLACEABLE)
                .addTag(BlockTags.DIRT)
                .add(FARMLAND)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS)
                .add(FARMLAND_SLAB_BLOCK.get());

        //Vanilla Tags:

        this.tag(BlockTags.ANIMALS_SPAWNABLE_ON)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS);

        this.tag(BlockTags.AXOLOTLS_SPAWNABLE_ON)
                .add(CLAY_SLAB_BLOCK.get());

        this.tag(BlockTags.AZALEA_GROWS_ON)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS);

        this.tag(BlockTags.AZALEA_ROOT_REPLACEABLE)
                .add(SAND_SLAB_BLOCK.get())
                .add(RED_SAND_SLAB_BLOCK.get())
                .add(CLAY_SLAB_BLOCK.get())
                .add(GRAVEL_SLAB_BLOCK.get());

        this.tag(BlockTags.BAMBOO_PLANTABLE_ON)
                .add(GRAVEL_SLAB_BLOCK.get())
                .add(BAMBOO_TINTED.get())
                .add(BAMBOO_SAPLING_TINTED.get());

        this.tag(BlockTags.BIG_DRIPLEAF_PLACEABLE)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS)
                .add(FARMLAND_SLAB_BLOCK.get());

        this.tag(BlockTags.CLIMBABLE)
                .add(VINE_TINTED.get());

        this.tag(ModTags.Blocks.CONVERTABLE_SLAB_TO_MUD)
                .add(DIRT_SLAB_BLOCK.get())
                .add(COARSE_DIRT_SLAB_BLOCK.get())
                .add(ROOTED_DIRT_SLAB_BLOCK.get());

        this.tag(BlockTags.DIRT)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS)
                .add(DIRT_SLAB_BLOCK.get())
                .add(COARSE_DIRT_SLAB_BLOCK.get())
                .add(ROOTED_DIRT_SLAB_BLOCK.get())
                .add(GROW_MYCELIUM_BLOCK.get())
                .add(MYCELIUM_SLAB_BLOCK.get())
                .add(GROW_PODZOL_BLOCK.get())
                .add(PODZOL_SLAB_BLOCK.get())
                .add(MOSS_SLAB_BLOCK.get())
                .add(MUD_SLAB_BLOCK.get())
                .add(MUDDY_ROOTS_SLAB_BLOCK.get());

        this.tag(DRAGON_IMMUNE)
                .add(END_PORTAL_FRAME_BLOCK.get());

        this.tag(BlockTags.ENDERMAN_HOLDABLE)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS)
                .add(GRAVEL_SLAB_BLOCK.get())
                .add(SAND_SLAB_BLOCK.get())
                .add(RED_SAND_SLAB_BLOCK.get())
                .add(CLAY_SLAB_BLOCK.get())
                .add(GROW_MYCELIUM_BLOCK.get())
                .add(GROW_PODZOL_BLOCK.get())
                .add(CACTUS_TINTED.get())
                .addTag(ModTags.Blocks.NYLIUM_BLOCKS);

        this.tag(FEATURES_CANNOT_REPLACE)
                .add(END_PORTAL_FRAME_BLOCK.get());

        this.tag(BlockTags.FLOWER_POTS)
                .add(GRASS_POTTED.get())
                .add(SEAGRASS_POTTED.get())
                .add(SUGAR_CANE_POTTED.get())
                .add(BIG_DRIP_LEAF_POTTED.get())
                .add(SMALL_DRIP_LEAF_POTTED.get())
                .add(KELP_POTTED.get())
                .add(GRASS_SHORT_POTTED_TINTED.get())
                .add(FERN_POTTED_TINTED.get())
                .add(SEAGRASS_POTTED_TINTED.get())
                .add(BAMBOO_POTTED_TINTED.get())
                .add(SUGAR_CANE_POTTED_TINTED.get())
                .add(BIG_DRIP_LEAF_POTTED_TINTED.get())
                .add(SMALL_DRIP_LEAF_POTTED_TINTED.get())
                .add(KELP_POTTED_TINTED.get())
                .add(CACTUS_POTTED_TINTED.get());

        this.tag(BlockTags.FOXES_SPAWNABLE_ON)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS)
                .add(COARSE_DIRT_SLAB_BLOCK.get())
                .add(PODZOL_SLAB_BLOCK.get())
                .add(GROW_PODZOL_BLOCK.get());

        this.tag(BlockTags.FROGS_SPAWNABLE_ON)
                .add(MUD_SLAB_BLOCK.get());

        this.tag(FROG_PREFER_JUMP_TO)
                .add(LILY_TINTED.get())
                .add(BIG_DRIP_LEAF_TINTED.get());

        this.tag(BlockTags.GOATS_SPAWNABLE_ON)
                .add(GRAVEL_SLAB_BLOCK.get())
                .add(PACKED_ICE_SLAB_BLOCK.get());

        this.tag(BlockTags.ICE)
                .add(ICE_SLAB_BLOCK.get())
                .add(PACKED_ICE_SLAB_BLOCK.get())
                .add(BLUE_ICE_SLAB_BLOCK.get());

        this.tag(BlockTags.INFINIBURN_OVERWORLD)
                .add(MAGMA_SLAB_BLOCK.get())
                .add(NETHERRACK_SLAB_BLOCK.get());

        this.tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS)
                .add(LILY_TINTED.get());

        this.tag(LAVA_POOL_STONE_CANNOT_REPLACE)
                .add(END_PORTAL_FRAME_BLOCK.get());

        this.tag(BlockTags.LUSH_GROUND_REPLACEABLE)
                .add(SAND_SLAB_BLOCK.get())
                .add(GRAVEL_SLAB_BLOCK.get())
                .add(CLAY_SLAB_BLOCK.get());

        this.tag(MANGROVE_ROOTS_CAN_GROW_THROUGH)
                .add(MUD_SLAB_BLOCK.get())
                .add(MUDDY_ROOTS_SLAB_BLOCK.get())
                .add(VINE_TINTED.get());

        this.tag(MANGROVE_LOGS_CAN_GROW_THROUGH)
                .add(MUD_SLAB_BLOCK.get())
                .add(MUDDY_ROOTS_SLAB_BLOCK.get())
                .add(MANGROVE_LEAVES_BLOCK.get())
                .add(VINE_TINTED.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(FERN_TINTED.get())
                .add(FERN_TALL_TINTED.get())
                .add(GRASS_SHORT_TINTED.get())
                .add(GRASS_TALL_TINTED.get())
                .add(LILY_TINTED.get())
                .add(VINE_TINTED.get())
                .add(BAMBOO_TINTED.get())
                .add(SUGAR_CANE_TINTED.get())
                .add(BIG_DRIP_LEAF_STEM_TINTED.get())
                .add(BIG_DRIP_LEAF_TINTED.get())
                .add(SMALL_DRIP_LEAF_TINTED.get());

        this.tag(BlockTags.MINEABLE_WITH_HOE)
                .addTag(ModTags.Blocks.ALL_MOD_LEAVES)
                .add(MOSS_SLAB_BLOCK.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(CRIMSON_NYLIUM_SLAB_BLOCK.get())
                .add(GROW_CRIMSON_NYLIUM_BLOCK.get())
                .add(WARPED_NYLIUM_SLAB_BLOCK.get())
                .add(GROW_WARPED_NYLIUM_BLOCK.get())
                .add(NETHERRACK_SLAB_BLOCK.get())
                .add(MAGMA_SLAB_BLOCK.get())
                .add(PACKED_MUD_SLAB_BLOCK.get())
                .add(ICE_SLAB_BLOCK.get())
                .add(PACKED_ICE_SLAB_BLOCK.get())
                .add(BLUE_ICE_SLAB_BLOCK.get())
                .add(DYEING_STATION.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS)
                .add(DIRT_SLAB_BLOCK.get())
                .add(DIRT_PATH_SLAB_BLOCK.get())
                .add(FARMLAND_SLAB_BLOCK.get())
                .add(COARSE_DIRT_SLAB_BLOCK.get())
                .add(ROOTED_DIRT_SLAB_BLOCK.get())
                .add(MYCELIUM_SLAB_BLOCK.get())
                .add(GROW_MYCELIUM_BLOCK.get())
                .add(PODZOL_SLAB_BLOCK.get())
                .add(GROW_PODZOL_BLOCK.get())
                .add(GRAVEL_SLAB_BLOCK.get())
                .add(SAND_SLAB_BLOCK.get())
                .add(RED_SAND_SLAB_BLOCK.get())
                .add(CLAY_SLAB_BLOCK.get())
                .add(SOUL_SAND_SLAB_BLOCK.get())
                .add(SOUL_SOIL_SLAB_BLOCK.get())
                .add(MUD_SLAB_BLOCK.get())
                .add(MUDDY_ROOTS_SLAB_BLOCK.get());


        this.tag(BlockTags.MOOSHROOMS_SPAWNABLE_ON)
                .add(MYCELIUM_SLAB_BLOCK.get())
                .add(GROW_MYCELIUM_BLOCK.get());

        this.tag(BlockTags.MOSS_REPLACEABLE)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS)
                .add(GROW_MYCELIUM_BLOCK.get())
                .add(GROW_PODZOL_BLOCK.get());

        this.tag(BlockTags.MUSHROOM_GROW_BLOCK)
                .add(PODZOL_SLAB_BLOCK.get())
                .add(GROW_PODZOL_BLOCK.get())
                .add(MYCELIUM_SLAB_BLOCK.get())
                .add(GROW_MYCELIUM_BLOCK.get())
                .addTag(ModTags.Blocks.NYLIUM_BLOCKS);

        this.tag(BlockTags.NETHER_CARVER_REPLACEABLES)
                .add(SOUL_SAND_SLAB_BLOCK.get())
                .add(SOUL_SOIL_SLAB_BLOCK.get());

        this.tag(BlockTags.NYLIUM)
                .addTag(ModTags.Blocks.NYLIUM_BLOCKS);

        this.tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS)
                .add(GRAVEL_SLAB_BLOCK.get())
                .add(PACKED_ICE_SLAB_BLOCK.get());

        this.tag(BlockTags.PARROTS_SPAWNABLE_ON)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS)
                .addTag(ModTags.Blocks.ALL_MOD_LEAVES);

        this.tag(BlockTags.POLAR_BEARS_SPAWNABLE_ON_ALTERNATE)
                .add(ICE_SLAB_BLOCK.get());

        this.tag(BlockTags.RABBITS_SPAWNABLE_ON)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS)
                .add(SAND_SLAB_BLOCK.get());

        this.tag(BlockTags.REPLACEABLE)
                .add(GRASS_SHORT_TINTED.get())
                .add(GRASS_TALL_TINTED.get())
                .add(FERN_TINTED.get())
                .add(FERN_TALL_TINTED.get())
                .add(SEAGRASS_TINTED.get())
                .add(SEAGRASS_TALL_TINTED.get())
                .add(VINE_TINTED.get());

        this.tag(BlockTags.REPLACEABLE_BY_TREES)
                .addTag(ModTags.Blocks.ALL_MOD_LEAVES)
                .add(GRASS_SHORT_TINTED.get())
                .add(GRASS_TALL_TINTED.get())
                .add(FERN_TINTED.get())
                .add(FERN_TALL_TINTED.get())
                .add(SEAGRASS_TINTED.get())
                .add(SEAGRASS_TALL_TINTED.get())
                .add(VINE_TINTED.get());

        this.tag(BlockTags.SAND)
                .add(SAND_SLAB_BLOCK.get())
                .add(RED_SAND_SLAB_BLOCK.get());

        this.tag(BlockTags.SCULK_REPLACEABLE)
                .add(SAND_SLAB_BLOCK.get())
                .add(RED_SAND_SLAB_BLOCK.get())
                .add(GRAVEL_SLAB_BLOCK.get())
                .add(SOUL_SAND_SLAB_BLOCK.get())
                .add(SOUL_SOIL_SLAB_BLOCK.get())
                .add(CLAY_SLAB_BLOCK.get());

        this.tag(BlockTags.SMALL_DRIPLEAF_PLACEABLE)
                .add(CLAY_SLAB_BLOCK.get())
                .add(MOSS_SLAB_BLOCK.get());

        this.tag(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)
                .add(ICE_SLAB_BLOCK.get())
                .add(PACKED_ICE_SLAB_BLOCK.get());

        this.tag(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON)
                .add(SOUL_SAND_SLAB_BLOCK.get())
                .add(MUD_SLAB_BLOCK.get());

        this.tag(BlockTags.SNIFFER_DIGGABLE_BLOCK)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS)
                .add(DIRT_SLAB_BLOCK.get())
                .add(COARSE_DIRT_SLAB_BLOCK.get())
                .add(ROOTED_DIRT_SLAB_BLOCK.get())
                .add(PODZOL_SLAB_BLOCK.get())
                .add(GROW_PODZOL_BLOCK.get())
                .add(MOSS_SLAB_BLOCK.get())
                .add(MUD_SLAB_BLOCK.get())
                .add(MUDDY_ROOTS_SLAB_BLOCK.get());

        this.tag(BlockTags.SNIFFER_EGG_HATCH_BOOST)
                .add(MOSS_SLAB_BLOCK.get());

        this.tag(BlockTags.SOUL_FIRE_BASE_BLOCKS)
                .add(SOUL_SAND_SLAB_BLOCK.get())
                .add(SOUL_SOIL_SLAB_BLOCK.get());

        this.tag(BlockTags.SOUL_SPEED_BLOCKS)
                .add(SOUL_SAND_SLAB_BLOCK.get())
                .add(SOUL_SOIL_SLAB_BLOCK.get());

        this.tag(SWORD_EFFICIENT)
                .addTag(ModTags.Blocks.ALL_MOD_LEAVES)
                .add(GRASS_SHORT_TINTED.get())
                .add(GRASS_TALL_TINTED.get())
                .add(FERN_TINTED.get())
                .add(FERN_TALL_TINTED.get())
                .add(VINE_TINTED.get())
                .add(LILY_TINTED.get())
                .add(BIG_DRIP_LEAF_TINTED.get())
                .add(BIG_DRIP_LEAF_STEM_TINTED.get())
                .add(SMALL_DRIP_LEAF_TINTED.get())
                .add(SUGAR_CANE_TINTED.get());


        this.tag(BlockTags.TRAIL_RUINS_REPLACEABLE)
                .add(GRAVEL_SLAB_BLOCK.get());

        this.tag(WITHER_IMMUNE)
                .add(END_PORTAL_FRAME_BLOCK.get());

        this.tag(BlockTags.WOLVES_SPAWNABLE_ON)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS);

        this.tag(BlockTags.VALID_SPAWN)
                .addTag(ModTags.Blocks.ALL_MOD_GRASS)
                .add(PODZOL_SLAB_BLOCK.get())
                .add(GROW_PODZOL_BLOCK.get());

        if (isBOPLoaded) {
            for(DeferredBlock<Block> grassesBlock : tintedBOPleavesRegistryBlocksList){
                this.tag(ModTags.Blocks.ALL_MOD_LEAVES)
                        .add(grassesBlock.get());
                this.tag(LEAVES)
                        .add(grassesBlock.get());
            }

            this.tag(BlockTags.CLIMBABLE)
                    .add(WILLOW_VINE_TINTED.get());

            this.tag(FROG_PREFER_JUMP_TO)
                    .add(HUGE_LILY_PAD_TINTED.get());

            this.tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS)
                    .add(HIGH_GRASS_TINTED.get())
                    .add(HIGH_GRASS_PLANT_TINTED.get())
                    .add(CLOVER_TINTED.get())
                    .add(HUGE_CLOVER_TINTED.get())
                    .add(HUGE_LILY_PAD_TINTED.get());

            this.tag(MANGROVE_LOGS_CAN_GROW_THROUGH)
                    .add(WILLOW_VINE_TINTED.get());

            this.tag(BlockTags.MINEABLE_WITH_AXE)
                    .add(WILLOW_VINE_TINTED.get())
                    .add(HUGE_LILY_PAD_TINTED.get());

            this.tag(BlockTags.REPLACEABLE)
                    .add(WILLOW_VINE_TINTED.get())
                    .add(SPROUT_TINTED.get())
                    .add(BUSH_TINTED.get())
                    .add(HIGH_GRASS_TINTED.get())
                    .add(HIGH_GRASS_PLANT_TINTED.get())
                    .add(CLOVER_TINTED.get());

            this.tag(BlockTags.REPLACEABLE_BY_TREES)
                    .addTag(ModTags.Blocks.ALL_MOD_LEAVES)
                    .add(WILLOW_VINE_TINTED.get())
                    .add(SPROUT_TINTED.get())
                    .add(BUSH_TINTED.get())
                    .add(HIGH_GRASS_TINTED.get())
                    .add(HIGH_GRASS_PLANT_TINTED.get())
                    .add(CLOVER_TINTED.get());

            this.tag(SWORD_EFFICIENT)
                    .addTag(ModTags.Blocks.ALL_MOD_LEAVES)
                    .add(WILLOW_VINE_TINTED.get())
                    .add(SPROUT_TINTED.get())
                    .add(BUSH_TINTED.get())
                    .add(HIGH_GRASS_TINTED.get())
                    .add(HIGH_GRASS_PLANT_TINTED.get())
                    .add(CLOVER_TINTED.get())
                    .add(HUGE_CLOVER_TINTED.get())
                    .add(HUGE_LILY_PAD_TINTED.get());

        }
    }
}
