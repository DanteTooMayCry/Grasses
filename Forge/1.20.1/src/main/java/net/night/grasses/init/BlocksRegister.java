package net.night.grasses.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.Grasses;
import net.night.grasses.block.bars.TintedVineInBars;
import net.night.grasses.block.bars.VineInBars;
import net.night.grasses.block.leaves.TintedJungleLeavesBlock;
import net.night.grasses.block.leaves.TintedLeavesBlock;
import net.night.grasses.block.other.ModEndPortalFrameBlock;
import net.night.grasses.block.blockItems.ModBlockItem;
import net.night.grasses.block.blockItems.ModPlaceOnWaterBlockItem;
import net.night.grasses.block.dirtLike.*;
import net.night.grasses.block.falling.FallingSlabBlock;
import net.night.grasses.block.netherrackLike.GrassesNyliumBlock;
import net.night.grasses.block.netherrackLike.NetherrackSlab;
import net.night.grasses.block.netherrackLike.NyliumSlabBlock;
import net.night.grasses.block.otherSlabs.*;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;
import net.night.grasses.block.plants.*;
import net.night.grasses.block.bars.PlantInBars;
import net.night.grasses.block.bars.TintedPlantInBars;
import net.night.grasses.block.plants.superclasses.ParentTintedDoublePlantBlock;
import net.night.grasses.block.potted.*;
import net.night.grasses.block.station.DyeingStationBlock;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.block.Blocks.*;
import static net.night.grasses.init.ItemsRegister.ITEMS;

public class BlocksRegister {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Grasses.MOD_ID);

    public static final BooleanProperty FERTILE = BooleanProperty.create("fertile");
    public static final BooleanProperty BIOMES_COLOR_SOURCE = BooleanProperty.create("biomes_color_source");
    public static final BooleanProperty ALTER = BooleanProperty.create("alter");
    public static final IntegerProperty DOUBLE_ALTER = IntegerProperty.create("double_alter", 0, 3);
    public static final IntegerProperty CLICKED_SIDE = IntegerProperty.create("clicked_side", 0, 4);
    public static final IntegerProperty ROTATE_MUDDY = IntegerProperty.create("rotate_muddy", 0, 2);

    public static final List<RegistryObject<Block>> allLeavesRegistryBlocksList         = new ArrayList<>();
    public static final List<RegistryObject<Block>> grassesLeavesRegistryBlocksList     = new ArrayList<>();
    public static final List<RegistryObject<Block>> plantRegistryBlockList              = new ArrayList<>();
    public static final List<RegistryObject<Block>> plantInBarsTintedRegistryBlockList  = new ArrayList<>();
    public static final List<RegistryObject<Block>> plantInBarsRegistryBlockList        = new ArrayList<>();
    public static final List<RegistryObject<Block>> grassRegistryBlocksList             = new ArrayList<>();
    public static final List<RegistryObject<Block>> grassRegistrySlabBlocksList         = new ArrayList<>();
    public static final List<RegistryObject<Block>> pottedTintedRegistryPlantList       = new ArrayList<>();
    public static final List<RegistryObject<Block>> pottedVanillaRegistryLeavesList     = new ArrayList<>();
    public static final List<RegistryObject<Block>> pottedTintedRegistryLeavesList      = new ArrayList<>();

    public static final RegistryObject<Block> DYEING_STATION            = registerBlock("dyeing_station", 31);

    public static final RegistryObject<Block> GROW_GRASS_BLOCK          = registerBlock("grow", 1);
    public static final RegistryObject<Block> GRASS_SLAB_BLOCK          = registerBlock("grow", 2);
    public static final RegistryObject<Block> DIRT_SLAB_BLOCK           = registerBlock("dirt_slab", 3);
    public static final RegistryObject<Block> DIRT_PATH_SLAB_BLOCK      = registerBlock("dirt_path_slab", 4);
    public static final RegistryObject<Block> FARMLAND_SLAB_BLOCK       = registerBlock("farmland_slab", 5);
    public static final RegistryObject<Block> COARSE_DIRT_SLAB_BLOCK    = registerBlock("coarse_dirt_slab", 6);
    public static final RegistryObject<Block> ROOTED_DIRT_SLAB_BLOCK    = registerBlock("rooted_dirt_slab", 7);
    public static final RegistryObject<Block> GROW_MYCELIUM_BLOCK       = registerBlock("grow_mycelium", 8);
    public static final RegistryObject<Block> MYCELIUM_SLAB_BLOCK       = registerBlock("mycelium_slab", 9);
    public static final RegistryObject<Block> GROW_PODZOL_BLOCK         = registerBlock("grow_podzol", 10);
    public static final RegistryObject<Block> PODZOL_SLAB_BLOCK         = registerBlock("podzol_slab", 11);
    public static final RegistryObject<Block> GROW_CRIMSON_NYLIUM_BLOCK = registerBlock("grow_crimson_nylium", 12);
    public static final RegistryObject<Block> CRIMSON_NYLIUM_SLAB_BLOCK = registerBlock("crimson_nylium_slab", 13);
    public static final RegistryObject<Block> GROW_WARPED_NYLIUM_BLOCK  = registerBlock("grow_warped_nylium", 14);
    public static final RegistryObject<Block> WARPED_NYLIUM_SLAB_BLOCK  = registerBlock("warped_nylium_slab", 15);
    public static final RegistryObject<Block> NETHERRACK_SLAB_BLOCK     = registerBlock("netherrack_slab", 16);

    public static final RegistryObject<Block> SAND_SLAB_BLOCK           = registerBlock("sand_slab", 17);
    public static final RegistryObject<Block> RED_SAND_SLAB_BLOCK       = registerBlock("red_sand_slab", 18);
    public static final RegistryObject<Block> GRAVEL_SLAB_BLOCK         = registerBlock("gravel_slab", 19);
    public static final RegistryObject<Block> CLAY_SLAB_BLOCK           = registerBlock("clay_slab", 20);
    public static final RegistryObject<Block> SOUL_SAND_SLAB_BLOCK      = registerBlock("soul_sand_slab", 21);
    public static final RegistryObject<Block> SOUL_SOIL_SLAB_BLOCK      = registerBlock("soul_soil_slab", 22);
    public static final RegistryObject<Block> MAGMA_SLAB_BLOCK          = registerBlock("magma_slab", 23);

    public static final RegistryObject<Block> MOSS_SLAB_BLOCK           = registerBlock("moss_slab", 24);
    public static final RegistryObject<Block> MUD_SLAB_BLOCK            = registerBlock("mud_slab", 25);
    public static final RegistryObject<Block> PACKED_MUD_SLAB_BLOCK     = registerBlock("packet_mud_slab", 26);
    public static final RegistryObject<Block> MUDDY_ROOTS_SLAB_BLOCK    = registerBlock("muddy_roots_slab", 27);

    public static final RegistryObject<Block> ICE_SLAB_BLOCK            = registerBlock("ice_slab", 28);
    public static final RegistryObject<Block> PACKED_ICE_SLAB_BLOCK     = registerBlock("packet_ice_slab", 29);
    public static final RegistryObject<Block> BLUE_ICE_SLAB_BLOCK       = registerBlock("blue_ice_slab", 30);

    public static final RegistryObject<Block> BADLANDS_GRASS_BLOCK      = registerBlock("badlands", 1);
    public static final RegistryObject<Block> BADLANDS_SLAB_BLOCK       = registerBlock("badlands", 2);
    public static final RegistryObject<Block> BIRCH_GRASS_BLOCK         = registerBlock("birch", 1);
    public static final RegistryObject<Block> BIRCH_SLAB_BLOCK          = registerBlock("birch", 2);
    public static final RegistryObject<Block> CHERRY_GROVE_GRASS_BLOCK  = registerBlock("cherry", 1);
    public static final RegistryObject<Block> CHERRY_GROVE_SLAB_BLOCK   = registerBlock("cherry", 2);
    public static final RegistryObject<Block> DARK_FOREST_GRASS_BLOCK   = registerBlock("dark_forest", 1);
    public static final RegistryObject<Block> DARK_FOREST_SLAB_BLOCK    = registerBlock("dark_forest", 2);
    public static final RegistryObject<Block> DESERT_GRASS_BLOCK        = registerBlock("desert", 1);
    public static final RegistryObject<Block> DESERT_SLAB_BLOCK         = registerBlock("desert", 2);
    public static final RegistryObject<Block> DRIPSTONE_BE_GRASS_BLOCK  = registerBlock("dripstone_be", 1);
    public static final RegistryObject<Block> DRIPSTONE_BE_SLAB_BLOCK   = registerBlock("dripstone_be", 2);
    public static final RegistryObject<Block> FOREST_GRASS_BLOCK        = registerBlock("forest", 1);
    public static final RegistryObject<Block> FOREST_SLAB_BLOCK         = registerBlock("forest", 2);
    public static final RegistryObject<Block> JUNGLE_GRASS_BLOCK        = registerBlock("jungle", 1);
    public static final RegistryObject<Block> JUNGLE_SLAB_BLOCK         = registerBlock("jungle", 2);
    public static final RegistryObject<Block> LUSH_BE_GRASS_BLOCK       = registerBlock("lush_be", 1);
    public static final RegistryObject<Block> LUSH_BE_SLAB_BLOCK        = registerBlock("lush_be", 2);
    public static final RegistryObject<Block> MANGROVE_GRASS_BLOCK      = registerBlock("mangrove", 1);
    public static final RegistryObject<Block> MANGROVE_SLAB_BLOCK       = registerBlock("mangrove", 2);
    public static final RegistryObject<Block> MEADOW_GRASS_BLOCK        = registerBlock("meadow", 1);
    public static final RegistryObject<Block> MEADOW_SLAB_BLOCK         = registerBlock("meadow", 2);
    public static final RegistryObject<Block> MUSHROOM_GRASS_BLOCK      = registerBlock("mushroom", 1);
    public static final RegistryObject<Block> MUSHROOM_SLAB_BLOCK       = registerBlock("mushroom", 2);
    public static final RegistryObject<Block> OCEAN_GRASS_BLOCK         = registerBlock("ocean", 1);
    public static final RegistryObject<Block> OCEAN_SLAB_BLOCK          = registerBlock("ocean", 2);
    public static final RegistryObject<Block> PLAINS_GRASS_BLOCK        = registerBlock("plains", 1);
    public static final RegistryObject<Block> PLAINS_SLAB_BLOCK         = registerBlock("plains", 2);
    public static final RegistryObject<Block> PINE_TAIGA_GRASS_BLOCK    = registerBlock("pine_taiga", 1);
    public static final RegistryObject<Block> PINE_TAIGA_SLAB_BLOCK     = registerBlock("pine_taiga", 2);
    public static final RegistryObject<Block> SNOWY_BEACH_GRASS_BLOCK   = registerBlock("snowy_beach", 1);
    public static final RegistryObject<Block> SNOWY_BEACH_SLAB_BLOCK    = registerBlock("snowy_beach", 2);
    public static final RegistryObject<Block> SNOWY_PLAINS_GRASS_BLOCK  = registerBlock("snowy_plains", 1);
    public static final RegistryObject<Block> SNOWY_PLAINS_SLAB_BLOCK   = registerBlock("snowy_plains", 2);
    public static final RegistryObject<Block> SPARSE_JUNGLE_GRASS_BLOCK = registerBlock("sparse_jungle", 1);
    public static final RegistryObject<Block> SPARSE_JUNGLE_SLAB_BLOCK  = registerBlock("sparse_jungle", 2);
    public static final RegistryObject<Block> STONY_PEAKS_GRASS_BLOCK   = registerBlock("stony_peaks", 1);
    public static final RegistryObject<Block> STONY_PEAKS_SLAB_BLOCK    = registerBlock("stony_peaks", 2);
    public static final RegistryObject<Block> SWAMP_GRASS_BLOCK         = registerBlock("swamp", 1);
    public static final RegistryObject<Block> SWAMP_SLAB_BLOCK          = registerBlock("swamp", 2);
    public static final RegistryObject<Block> COLD_SWAMP_GRASS_BLOCK    = registerBlock("cold_swamp", 1);
    public static final RegistryObject<Block> COLD_SWAMP_SLAB_BLOCK     = registerBlock("cold_swamp", 2);
    public static final RegistryObject<Block> TAIGA_GRASS_BLOCK         = registerBlock("taiga", 1);
    public static final RegistryObject<Block> TAIGA_SLAB_BLOCK          = registerBlock("taiga", 2);
    public static final RegistryObject<Block> WINDSWEPT_GRASS_BLOCK     = registerBlock("windswept", 1);
    public static final RegistryObject<Block> WINDSWEPT_SLAB_BLOCK      = registerBlock("windswept", 2);
    public static final RegistryObject<Block> WHITE_GRASS_BLOCK         = registerBlock("white", 1);
    public static final RegistryObject<Block> WHITE_SLAB_BLOCK          = registerBlock("white", 2);
    public static final RegistryObject<Block> RED_GRASS_BLOCK           = registerBlock("red", 1);
    public static final RegistryObject<Block> RED_SLAB_BLOCK            = registerBlock("red", 2);
    public static final RegistryObject<Block> ORANGE_GRASS_BLOCK        = registerBlock("orange", 1);
    public static final RegistryObject<Block> ORANGE_SLAB_BLOCK         = registerBlock("orange", 2);
    public static final RegistryObject<Block> PINK_GRASS_BLOCK          = registerBlock("pink", 1);
    public static final RegistryObject<Block> PINK_SLAB_BLOCK           = registerBlock("pink", 2);
    public static final RegistryObject<Block> YELLOW_GRASS_BLOCK        = registerBlock("yellow", 1);
    public static final RegistryObject<Block> YELLOW_SLAB_BLOCK         = registerBlock("yellow", 2);
    public static final RegistryObject<Block> LIME_GRASS_BLOCK          = registerBlock("lime", 1);
    public static final RegistryObject<Block> LIME_SLAB_BLOCK           = registerBlock("lime", 2);
    public static final RegistryObject<Block> GREEN_GRASS_BLOCK         = registerBlock("green", 1);
    public static final RegistryObject<Block> GREEN_SLAB_BLOCK          = registerBlock("green", 2);
    public static final RegistryObject<Block> LIGHT_BLUE_GRASS_BLOCK    = registerBlock("light_blue", 1);
    public static final RegistryObject<Block> LIGHT_BLUE_SLAB_BLOCK     = registerBlock("light_blue", 2);
    public static final RegistryObject<Block> CYAN_GRASS_BLOCK          = registerBlock("cyan", 1);
    public static final RegistryObject<Block> CYAN_SLAB_BLOCK           = registerBlock("cyan", 2);
    public static final RegistryObject<Block> BLUE_GRASS_BLOCK          = registerBlock("blue", 1);
    public static final RegistryObject<Block> BLUE_SLAB_BLOCK           = registerBlock("blue", 2);
    public static final RegistryObject<Block> MAGENTA_GRASS_BLOCK       = registerBlock("magenta", 1);
    public static final RegistryObject<Block> MAGENTA_SLAB_BLOCK        = registerBlock("magenta", 2);
    public static final RegistryObject<Block> PURPLE_GRASS_BLOCK        = registerBlock("purple", 1);
    public static final RegistryObject<Block> PURPLE_SLAB_BLOCK         = registerBlock("purple", 2);
    public static final RegistryObject<Block> BROWN_GRASS_BLOCK         = registerBlock("brown", 1);
    public static final RegistryObject<Block> BROWN_SLAB_BLOCK          = registerBlock("brown", 2);
    public static final RegistryObject<Block> GRAY_GRASS_BLOCK          = registerBlock("gray", 1);
    public static final RegistryObject<Block> GRAY_SLAB_BLOCK           = registerBlock("gray", 2);
    public static final RegistryObject<Block> LIGHT_GRAY_GRASS_BLOCK    = registerBlock("light_gray", 1);
    public static final RegistryObject<Block> LIGHT_GRAY_SLAB_BLOCK     = registerBlock("light_gray", 2);
    public static final RegistryObject<Block> BLACK_GRASS_BLOCK         = registerBlock("black", 1);
    public static final RegistryObject<Block> BLACK_SLAB_BLOCK          = registerBlock("black", 2);
    public static final RegistryObject<Block> QUARTZ_GRASS_BLOCK        = registerBlock("quartz", 1);
    public static final RegistryObject<Block> QUARTZ_SLAB_BLOCK         = registerBlock("quartz", 2);
    public static final RegistryObject<Block> COPPER_GRASS_BLOCK        = registerBlock("copper", 1);
    public static final RegistryObject<Block> COPPER_SLAB_BLOCK         = registerBlock("copper", 2);
    public static final RegistryObject<Block> IRON_GRASS_BLOCK          = registerBlock("iron", 1);
    public static final RegistryObject<Block> IRON_SLAB_BLOCK           = registerBlock("iron", 2);
    public static final RegistryObject<Block> GOLD_GRASS_BLOCK          = registerBlock("gold", 1);
    public static final RegistryObject<Block> GOLD_SLAB_BLOCK           = registerBlock("gold", 2);
    public static final RegistryObject<Block> DIAMOND_GRASS_BLOCK       = registerBlock("diamond", 1);
    public static final RegistryObject<Block> DIAMOND_SLAB_BLOCK        = registerBlock("diamond", 2);
    public static final RegistryObject<Block> EMERALD_GRASS_BLOCK       = registerBlock("emerald", 1);
    public static final RegistryObject<Block> EMERALD_SLAB_BLOCK        = registerBlock("emerald", 2);
    public static final RegistryObject<Block> NETHERITE_GRASS_BLOCK     = registerBlock("netherite", 1);
    public static final RegistryObject<Block> NETHERITE_SLAB_BLOCK      = registerBlock("netherite", 2);
    public static final RegistryObject<Block> REDSTONE_GRASS_BLOCK      = registerBlock("redstone", 1);
    public static final RegistryObject<Block> REDSTONE_SLAB_BLOCK       = registerBlock("redstone", 2);
    public static final RegistryObject<Block> AMETHYST_GRASS_BLOCK      = registerBlock("amethyst", 1);
    public static final RegistryObject<Block> AMETHYST_SLAB_BLOCK       = registerBlock("amethyst", 2);
    public static final RegistryObject<Block> LAPIS_GRASS_BLOCK         = registerBlock("lapis", 1);
    public static final RegistryObject<Block> LAPIS_SLAB_BLOCK          = registerBlock("lapis", 2);

    public static final RegistryObject<Block> LIGHT_LIME_GRASS_BLOCK    = registerBlock("light_lime", 1);
    public static final RegistryObject<Block> LIGHT_LIME_SLAB_BLOCK     = registerBlock("light_lime", 2);
    public static final RegistryObject<Block> DARK_GREEN_GRASS_BLOCK    = registerBlock("dark_green", 1);
    public static final RegistryObject<Block> DARK_GREEN_SLAB_BLOCK     = registerBlock("dark_green", 2);
    public static final RegistryObject<Block> XMAS_GREEN_GRASS_BLOCK    = registerBlock("xmas_green", 1);
    public static final RegistryObject<Block> XMAS_GREEN_SLAB_BLOCK     = registerBlock("xmas_green", 2);
    public static final RegistryObject<Block> MALACHITE_GRASS_BLOCK     = registerBlock("malachite", 1);
    public static final RegistryObject<Block> MALACHITE_SLAB_BLOCK      = registerBlock("malachite", 2);
    public static final RegistryObject<Block> GLASS_BOTTLE_GRASS_BLOCK  = registerBlock("glass_bottle", 1);
    public static final RegistryObject<Block> GLASS_BOTTLE_SLAB_BLOCK   = registerBlock("glass_bottle", 2);
    public static final RegistryObject<Block> LIVING_GREEN_GRASS_BLOCK  = registerBlock("living_green", 1);
    public static final RegistryObject<Block> LIVING_GREEN_SLAB_BLOCK   = registerBlock("living_green", 2);
    public static final RegistryObject<Block> BETTER_CHERRY_GRASS_BLOCK = registerBlock("better_cherry", 1);
    public static final RegistryObject<Block> BETTER_CHERRY_SLAB_BLOCK  = registerBlock("better_cherry", 2);

    public static final RegistryObject<Block> ACACIA_LEAVES_BLOCK           = registerBlock("acacia", 32);
    public static final RegistryObject<Block> AZALEA_LEAVES_BLOCK           = registerBlock("azalea", 32);
    public static final RegistryObject<Block> BIRCH_LEAVES_BLOCK            = registerBlock("birch", 32);
    public static final RegistryObject<Block> CHERRY_LEAVES_BLOCK           = registerBlock("cherry", 32);
    public static final RegistryObject<Block> DARK_OAK_LEAVES_BLOCK         = registerBlock("dark_oak", 32);
    public static final RegistryObject<Block> FLOWERING_AZALEA_LEAVES_BLOCK = registerBlock("flowering_azalea", 32);
    public static final RegistryObject<Block> JUNGLE_LEAVES_BLOCK           = registerBlock("jungle", 321);
    public static final RegistryObject<Block> MANGROVE_LEAVES_BLOCK         = registerBlock("mangrove", 32);
    public static final RegistryObject<Block> OAK_LEAVES_BLOCK              = registerBlock("oak", 32);
    public static final RegistryObject<Block> SPRUCE_LEAVES_BLOCK           = registerBlock("spruce", 32);

    public static final RegistryObject<Block> GRASS_TINTED                  = registerBlock("grass_tinted", 33);
    public static final RegistryObject<Block> FERN_TINTED                   = registerBlock("fern_tinted", 33);

    public static final RegistryObject<Block> GRASS_TALL_TINTED             = registerBlock("grass_tall_tinted", 34);
    public static final RegistryObject<Block> FERN_TALL_TINTED              = registerBlock("fern_tall_tinted", 34);

    public static final RegistryObject<Block> SEAGRASS_TINTED               = registerBlock("seagrass_tinted", 35);
    public static final RegistryObject<Block> SEAGRASS_TALL_TINTED          = registerBlock("seagrass_tall_tinted", 36);

    public static final RegistryObject<Block> VINE_TINTED                   = registerBlock("vine_tinted", 37);

    public static final RegistryObject<Block> LILY_TINTED                   = registerBlock("lily_pad_tinted", 38);
    public static final RegistryObject<Block> SUGAR_CANE_TINTED             = registerBlock("sugar_cane_tinted", 39);
    public static final RegistryObject<Block> BAMBOO_TINTED                 = registerBlock("bamboo_tinted", 40);
    public static final RegistryObject<Block> BAMBOO_SAPLING_TINTED         = registerBlock("bamboo_sapling_tinted", 41);
    public static final RegistryObject<Block> BIG_DRIP_LEAF_TINTED          = registerBlock("big_drip_leaf_tinted", 42);
    public static final RegistryObject<Block> BIG_DRIP_LEAF_STEM_TINTED     = registerBlock("big_drip_leaf_stem_tinted", 43);
    public static final RegistryObject<Block> SMALL_DRIP_LEAF_TINTED        = registerBlock("small_drip_leaf_tinted", 44);
    public static final RegistryObject<Block> KELP_TINTED                   = registerBlock("kelp_tinted", 45);
    public static final RegistryObject<Block> KELP_PLANT_TINTED             = registerBlock("kelp_plant_tinted", 46);
    public static final RegistryObject<Block> CACTUS_TINTED                 = registerBlock("cactus_tinted", 47);

    public static final RegistryObject<Block> GRASS_POTTED                  = registerVanillaLikePotBlock("grass", GRASS, 48);
    public static final RegistryObject<Block> SEAGRASS_POTTED               = registerVanillaLikePotBlock("seagrass", SEAGRASS, 48);
    public static final RegistryObject<Block> SUGAR_CANE_POTTED             = registerVanillaLikePotBlock("sugar_cane", SUGAR_CANE, 48);
    public static final RegistryObject<Block> VINE_POTTED                   = registerVanillaLikePotBlock("vine", VINE, 48);
    public static final RegistryObject<Block> BIG_DRIP_LEAF_POTTED          = registerVanillaLikePotBlock("big_dripleaf", BIG_DRIPLEAF, 52);
    public static final RegistryObject<Block> SMALL_DRIP_LEAF_POTTED        = registerVanillaLikePotBlock("small_dripleaf",SMALL_DRIPLEAF, 52);
    public static final RegistryObject<Block> KELP_POTTED                   = registerVanillaLikePotBlock("kelp", Blocks.KELP, 48);

    public static final RegistryObject<Block> GRASS_POTTED_TINTED           = registerPotBlock("grass", GRASS_TINTED, 49);
    public static final RegistryObject<Block> FERN_POTTED_TINTED            = registerPotBlock("fern", FERN_TINTED, 49);
    public static final RegistryObject<Block> SEAGRASS_POTTED_TINTED        = registerPotBlock("seagrass", SEAGRASS_TINTED, 49);
    public static final RegistryObject<Block> BAMBOO_POTTED_TINTED          = registerPotBlock("bamboo", BAMBOO_TINTED, 49);
    public static final RegistryObject<Block> SUGAR_CANE_POTTED_TINTED      = registerPotBlock("sugar_cane", SUGAR_CANE_TINTED , 50);
    public static final RegistryObject<Block> VINE_POTTED_TINTED            = registerPotBlock("vine", VINE_TINTED, 49);
    public static final RegistryObject<Block> BIG_DRIP_LEAF_POTTED_TINTED   = registerPotBlock("big_dripleaf", BIG_DRIP_LEAF_TINTED , 51);
    public static final RegistryObject<Block> SMALL_DRIP_LEAF_POTTED_TINTED = registerPotBlock("small_dripleaf", SMALL_DRIP_LEAF_TINTED , 51);
    public static final RegistryObject<Block> KELP_POTTED_TINTED            = registerPotBlock("kelp", KELP_TINTED , 49);
    public static final RegistryObject<Block> CACTUS_POTTED_TINTED          = registerPotBlock("cactus", CACTUS_TINTED , 49);

    public static final RegistryObject<Block> ACACIA_LEAVES_POTTED          = registerVanillaLikePotBlock("acacia", ACACIA_LEAVES,53);
    public static final RegistryObject<Block> AZALEA_LEAVES_POTTED          = registerVanillaLikePotBlock("azalea", AZALEA_LEAVES,53);
    public static final RegistryObject<Block> BIRCH_LEAVES_POTTED           = registerVanillaLikePotBlock("birch", BIRCH_LEAVES,53);
    public static final RegistryObject<Block> CHERRY_LEAVES_POTTED          = registerVanillaLikePotBlock("cherry", CHERRY_LEAVES, 53);
    public static final RegistryObject<Block> DARK_OAK_LEAVES_POTTED        = registerVanillaLikePotBlock("dark_oak", DARK_OAK_LEAVES, 53);
    public static final RegistryObject<Block> FLOWERING_AZALEA_LEAVES_POTTED= registerVanillaLikePotBlock("flowering_azalea", FLOWERING_AZALEA_LEAVES, 53);
    public static final RegistryObject<Block> JUNGLE_LEAVES_POTTED          = registerVanillaLikePotBlock("jungle", JUNGLE_LEAVES, 53);
    public static final RegistryObject<Block> MANGROVE_LEAVES_POTTED        = registerVanillaLikePotBlock("mangrove", MANGROVE_LEAVES, 53);
    public static final RegistryObject<Block> OAK_LEAVES_POTTED             = registerVanillaLikePotBlock("oak", OAK_LEAVES, 53);
    public static final RegistryObject<Block> SPRUCE_LEAVES_POTTED          = registerVanillaLikePotBlock("spruce", SPRUCE_LEAVES, 53);

    public static final RegistryObject<Block> ACACIA_LEAVES_POTTED_TINTED   = registerPotBlock("acacia", ACACIA_LEAVES_BLOCK,54);
    public static final RegistryObject<Block> AZALEA_LEAVES_POTTED_TINTED   = registerPotBlock("azalea", AZALEA_LEAVES_BLOCK,54);
    public static final RegistryObject<Block> BIRCH_LEAVES_POTTED_TINTED    = registerPotBlock("birch", BIRCH_LEAVES_BLOCK,54);
    public static final RegistryObject<Block> CHERRY_LEAVES_POTTED_TINTED   = registerPotBlock("cherry", CHERRY_LEAVES_BLOCK, 54);
    public static final RegistryObject<Block> DARK_OAK_LEAVES_POTTED_TINTED = registerPotBlock("dark_oak", DARK_OAK_LEAVES_BLOCK, 54);
    public static final RegistryObject<Block> FLOWERING_AZALEA_LEAVES_POTTED_TINTED  = registerPotBlock("flowering_azalea", FLOWERING_AZALEA_LEAVES_BLOCK, 54);
    public static final RegistryObject<Block> JUNGLE_LEAVES_POTTED_TINTED   = registerPotBlock("jungle", JUNGLE_LEAVES_BLOCK, 54);
    public static final RegistryObject<Block> MANGROVE_LEAVES_POTTED_TINTED = registerPotBlock("mangrove", MANGROVE_LEAVES_BLOCK, 54);
    public static final RegistryObject<Block> OAK_LEAVES_POTTED_TINTED      = registerPotBlock("oak", OAK_LEAVES_BLOCK, 54);
    public static final RegistryObject<Block> SPRUCE_LEAVES_POTTED_TINTED   = registerPotBlock("spruce", SPRUCE_LEAVES_BLOCK, 54);

    public static final RegistryObject<Block> END_PORTAL_FRAME_BLOCK        = registerBlock("end_portal_frame_block", 55);
    public static final RegistryObject<Block> GRASS_IN_BARS                 = registerBlock("grass_in_bars", 56);
    public static final RegistryObject<Block> FERN_IN_BARS                  = registerBlock("fern_in_bars", 56);
    public static final RegistryObject<Block> VINE_IN_BARS                  = registerBlock("vine_in_bars", 57);
    public static final RegistryObject<Block> TINTED_GRASS_IN_BARS          = registerBlock("tinted_grass_in_bars", 58);
    public static final RegistryObject<Block> TINTED_FERN_IN_BARS           = registerBlock("tinted_fern_in_bars", 58);
    public static final RegistryObject<Block> TINTED_VINE_IN_BARS           = registerBlock("tinted_vine_in_bars", 59);


    private static RegistryObject<Block> registerBlock(String name, int type) {

        RegistryObject<Block> toReturn;

        if (type == 1) {
            name = name.concat("_grass_block");
            toReturn = BLOCKS.register(name, GrassesBlock::new);
            grassRegistryBlocksList.add(toReturn);
        } else if (type == 2) {
            name = name.concat("_grass_slab");
            toReturn = BLOCKS.register(name, GrassesSlabBlock::new);
            grassRegistrySlabBlocksList.add(toReturn);
        } else if (type == 3) {
            toReturn = BLOCKS.register(name, DirtSlabBlock::new);
        } else if (type == 4) {
            toReturn = BLOCKS.register(name, DirthPathSlabBlock::new);
        } else if (type == 5) {
            toReturn = BLOCKS.register(name, FarmlandSlabBlock::new);
        } else if (type == 6) {
            toReturn = BLOCKS.register(name, CoarseDirtSlabBlock::new);
        } else if (type == 7) {
            toReturn = BLOCKS.register(name, RootedDirtSlabBlock::new);
        } else if (type == 8) {
            toReturn = BLOCKS.register(name, GrassesMyceliumBlock::new);
        } else if (type == 9) {
            toReturn = BLOCKS.register(name, MyceliumSlabBlock::new);
        } else if (type == 10) {
            toReturn = BLOCKS.register(name, GrassesPodzolBlock::new);
        } else if (type == 11) {
            toReturn = BLOCKS.register(name, PodzolSlabBlock::new);
        } else if (type == 12) {
            toReturn = BLOCKS.register(name, () -> new GrassesNyliumBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_NYLIUM)));
        } else if (type == 13) {
            toReturn = BLOCKS.register(name, () -> new NyliumSlabBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_NYLIUM)));
        } else if (type == 14) {
            toReturn = BLOCKS.register(name, () -> new GrassesNyliumBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_NYLIUM)));
        } else if (type == 15) {
            toReturn = BLOCKS.register(name, () -> new NyliumSlabBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_NYLIUM)));
        } else if (type == 16) {
            toReturn = BLOCKS.register(name, NetherrackSlab::new);
        } else if (type == 17) {
            toReturn = BLOCKS.register(name, () -> new FallingSlabBlock(14406560, BlockBehaviour.Properties.copy(Blocks.SAND)));
        } else if (type == 18) {
            toReturn = BLOCKS.register(name, () -> new FallingSlabBlock(11098145, BlockBehaviour.Properties.copy(Blocks.RED_SAND)));
        } else if (type == 19) {
            toReturn = BLOCKS.register(name, () -> new FallingSlabBlock(-8356741, BlockBehaviour.Properties.copy(Blocks.GRAVEL)));
        } else if (type == 20) {
            toReturn = BLOCKS.register(name, () -> new ParentSlabBlock(BlockBehaviour.Properties.copy(Blocks.CLAY)));
        } else if (type == 21) {
            toReturn = BLOCKS.register(name, SoulSandSlabBlock::new);
        } else if (type == 22){
            toReturn = BLOCKS.register(name, () -> new ParentSlabBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL)));
        } else if (type == 23) {
            toReturn = BLOCKS.register(name, MagmaSlabBlock::new);
        } else if (type == 24) {
            toReturn = BLOCKS.register(name, MossSlabBlock::new);
        } else if (type == 25) {
            toReturn = BLOCKS.register(name, MudSlabBlock::new);
        } else if (type == 26) {
            toReturn = BLOCKS.register(name, () -> new ParentSlabBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_MUD)));
        } else if (type == 27) {
            toReturn = BLOCKS.register(name, MuddyMangroveRootsSlabBlock::new);
        } else if (type == 28) {
            toReturn = BLOCKS.register(name, IceSlabBlock::new);
        } else if (type == 29) {
            toReturn = BLOCKS.register(name, () -> new ParentSlabBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE)));
        } else if (type == 30) {
            toReturn = BLOCKS.register(name, () -> new ParentSlabBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_ICE)));
        } else if (type == 31) {
            toReturn = BLOCKS.register(name, DyeingStationBlock::new);
        } else if (type == 32) {
            name = name.concat("_leaves_block");
            toReturn = BLOCKS.register(name, TintedLeavesBlock::new);
            allLeavesRegistryBlocksList.add(toReturn);
            grassesLeavesRegistryBlocksList.add(toReturn);
        } else if (type == 321) { // 32.1
            name = name.concat("_leaves_block");
            toReturn = BLOCKS.register(name, TintedJungleLeavesBlock::new);
            allLeavesRegistryBlocksList.add(toReturn);
            grassesLeavesRegistryBlocksList.add(toReturn);
        }else if (type == 33) {
            toReturn = BLOCKS.register(name, TintedGrassPlant::new);
            plantRegistryBlockList.add(toReturn);
        } else if (type == 34) {
            toReturn = BLOCKS.register(name, () -> new ParentTintedDoublePlantBlock(BlockBehaviour.Properties.copy(TALL_GRASS)));
            plantRegistryBlockList.add(toReturn);
        } else if (type == 35) {
            toReturn = BLOCKS.register(name, TintedSeaGrass::new);
            plantRegistryBlockList.add(toReturn);
        } else if (type == 36) {
            toReturn = BLOCKS.register(name, TintedTallSeaGrass::new);
            plantRegistryBlockList.add(toReturn);
        } else if (type == 37) {
            toReturn = BLOCKS.register(name, TintedVine::new);
            plantRegistryBlockList.add(toReturn);
        } else if (type == 38) {
            toReturn = BLOCKS.register(name, TintedWaterlily::new);
            plantRegistryBlockList.add(toReturn);
        } else if (type == 39) {
            toReturn = BLOCKS.register(name, TintedSugarCane::new);
            plantRegistryBlockList.add(toReturn);
        } else if (type == 40) {
            toReturn = BLOCKS.register(name, TintedBamboo::new);
            plantRegistryBlockList.add(toReturn);
        } else if (type == 41) {
            toReturn = BLOCKS.register(name, TintedBambooSapling::new);
            plantRegistryBlockList.add(toReturn);
        } else if (type == 42) {
            toReturn = BLOCKS.register(name, TintedBigDripLeaf::new);
            plantRegistryBlockList.add(toReturn);
        } else if (type == 43) {
            toReturn = BLOCKS.register(name, TintedBigDripleafStem::new);
            plantRegistryBlockList.add(toReturn);
        } else if (type == 44) {
            toReturn = BLOCKS.register(name, TintedSmallDripLeaf::new);
            plantRegistryBlockList.add(toReturn);
        } else if (type == 45) {
            toReturn = BLOCKS.register(name, TintedKelp::new);
            plantRegistryBlockList.add(toReturn);
        }  else if (type == 46) {
            toReturn = BLOCKS.register(name, TintedKelpPlant::new);
            plantRegistryBlockList.add(toReturn);
        }  else if (type == 47) {
            toReturn = BLOCKS.register(name, TintedCactus::new);
            plantRegistryBlockList.add(toReturn);
        } else if (type == 55) {
            toReturn = BLOCKS.register(name, ModEndPortalFrameBlock::new);
        } else if (type == 56) {
            toReturn = BLOCKS.register(name, PlantInBars::new);
            plantInBarsRegistryBlockList.add(toReturn);
        } else if (type == 57) {
            toReturn = BLOCKS.register(name, VineInBars::new);
            plantInBarsRegistryBlockList.add(toReturn);
        } else if (type == 58) {
            toReturn = BLOCKS.register(name, TintedPlantInBars::new);
            plantInBarsTintedRegistryBlockList.add(toReturn);
            plantInBarsRegistryBlockList.add(toReturn);
        } else if (type == 59) {
            toReturn = BLOCKS.register(name, TintedVineInBars::new);
            plantInBarsTintedRegistryBlockList.add(toReturn);
            plantInBarsRegistryBlockList.add(toReturn);
        } else
            toReturn = null;

        if (type == 38)
            registerBlockItemLily(name, toReturn);
        else if ((type >= 32 && type <= 47) || type >=56)
            registerModBlockItem(name, toReturn);
        else
            registerBlockItem(name, toReturn);

        return toReturn;
    }

    private static RegistryObject<Block> registerPotBlock(String name, RegistryObject<Block> block, int type) {
        RegistryObject<Block> toReturn;

        name = name.concat("_potted_tinted");
        if (type == 50)
            toReturn = BLOCKS.register(name, () -> new TintedPottedSugarCaneBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, block));
        else if (type == 51)
            toReturn = BLOCKS.register(name, () -> new TintedPottedDripLeafBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, block));
        else if (type == 54) {
            toReturn = BLOCKS.register(name, () -> new TintedPottedPlantBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, block));
            pottedTintedRegistryLeavesList.add(toReturn);
        }
        else //type 49
            toReturn = BLOCKS.register(name, () -> new TintedPottedPlantBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, block));

        pottedTintedRegistryPlantList.add(toReturn);

        return toReturn;
    }
    private static RegistryObject<Block> registerVanillaLikePotBlock(String name, Block block, int type) {

        RegistryObject<Block> toReturn;

        name = name.concat("_potted");
        if (type == 52)
            toReturn = BLOCKS.register(name, () -> new PottedDripLeafBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> block));
        else if (type == 53) {
            toReturn = BLOCKS.register(name, () -> new PottedPlantBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> block));
            pottedVanillaRegistryLeavesList.add(toReturn);
        }
        else // type 48
            toReturn = BLOCKS.register(name, () -> new PottedPlantBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> block));

        return toReturn;
    }

    private static void registerBlockItem(String name, RegistryObject<Block> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    };

    static void registerModBlockItem(String name, RegistryObject<Block> block) {
        ITEMS.register(name, () -> new ModBlockItem(block.get(), new Item.Properties()));
    };

    static void registerBlockItemLily(String name, RegistryObject<Block> block) {
        ITEMS.register(name, () -> new ModPlaceOnWaterBlockItem(block.get(), new Item.Properties()));
    };

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
