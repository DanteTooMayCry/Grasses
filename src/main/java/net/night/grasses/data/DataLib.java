package net.night.grasses.data;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.colorManagers.ColorType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static biomesoplenty.api.block.BOPBlocks.*;
import static biomesoplenty.init.ModTags.Blocks.*;
import static net.minecraft.tags.BlockTags.*;
import static net.minecraft.tags.BlockTags.SPRUCE_LOGS;
import static net.minecraft.world.level.block.Blocks.*;
import static net.minecraft.world.level.block.Blocks.TORCHFLOWER;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.colorManagers.ColorType.*;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;
import static net.night.grasses.init.ItemsRegister.*;

public class DataLib {

    public static final List<Item> ingredientsList = new ArrayList<>();
    public static final List<ColorType> colorTypeList = new ArrayList<>();

    public static final Map<String, Block> counterpartIDMap = new HashMap<>();

    public static final Map<Block, Block> matchingCounterpartsLeaves = new HashMap<>();
    public static final Map<Block, Block> matchingCounterpartsPlants = new HashMap<>();
    public static final Map<Block, Block> matchingCounterpartsVines = new HashMap<>();
    public static final Map<Block, Block> matchingCounterpartsVanillaPotted = new HashMap<>();
    public static final Map<Block, Block> matchingTintedPottedWithPlant = new HashMap<>();
    public static final Map<Block, Block> matchingNotTintedPottedWithPlant = new HashMap<>();
    public static final Map<Block, Block> matchingBarsWithPlant = new HashMap<>();
    public static final Map<Block, TagKey<Block>> matchingLogsWithLeaves = new HashMap<>();

    public static final List<TagKey<Block>> vanillaLogsTagsBlocksList = new ArrayList<>();
    public static final List<TagKey<Block>> BOPLogsTagsBlocksList = new ArrayList<>();

    public static final Map<Block, Integer> plantsChangedIntoTintedWithFlag = new HashMap<>();

    public static final List<Block> BOPPlantsBlocksList = new ArrayList<>();

    public static final List<Item> modBlockItemsWithNBT = new ArrayList<>();
    public static final List<Item> notForCreativeTab = new ArrayList<>();
    public static final List<Item> tintedBOPRestrictedCreativeTab = new ArrayList<>();

    public static final List<Block> tintedLeavesPottedBlockList = new ArrayList<>();

    public static final List<ItemLike> tintedPlantsThatCanBePotted = new ArrayList<>();
    public static final List<ItemLike> notTintedPlantsThatCanBePotted = new ArrayList<>();
    public static final List<Block> standardTintedPlantsList = new ArrayList<>();
    public static final List<Block> sugarCaneTintedPlantsList = new ArrayList<>();
    public static final List<Block> stagePlants = new ArrayList<>();

    public static final List<String> chestLootLocations = new ArrayList<>();


    static {

        ingredientsList.add(Items.RED_SAND);
        ingredientsList.add(Items.BIRCH_SAPLING);
        ingredientsList.add(Items.CHERRY_SAPLING);
        ingredientsList.add(Items.DARK_OAK_SAPLING);
        ingredientsList.add(Items.CACTUS);
        ingredientsList.add(Items.POINTED_DRIPSTONE);
        ingredientsList.add(Items.OAK_SAPLING);
        ingredientsList.add(Items.JUNGLE_SAPLING);
        ingredientsList.add(Items.GLOW_BERRIES);
        ingredientsList.add(Items.MANGROVE_PROPAGULE);
        ingredientsList.add(Items.ALLIUM);
        ingredientsList.add(Items.MYCELIUM);
        ingredientsList.add(Items.KELP);
        ingredientsList.add(Items.GRASS);
        ingredientsList.add(Items.FERN);
        ingredientsList.add(Items.ICE);
        ingredientsList.add(Items.SNOWBALL);
        ingredientsList.add(Items.VINE);
        ingredientsList.add(Items.EMERALD_ORE);
        ingredientsList.add(Items.BLUE_ORCHID);
        ingredientsList.add(Items.LILY_PAD);
        ingredientsList.add(Items.SPRUCE_SAPLING);
        ingredientsList.add(Items.GRASS_BLOCK);
        ingredientsList.add(Items.WHITE_DYE);
        ingredientsList.add(Items.RED_DYE);
        ingredientsList.add(Items.ORANGE_DYE);
        ingredientsList.add(Items.PINK_DYE);
        ingredientsList.add(Items.YELLOW_DYE);
        ingredientsList.add(Items.LIME_DYE);
        ingredientsList.add(Items.GREEN_DYE);
        ingredientsList.add(Items.LIGHT_BLUE_DYE);
        ingredientsList.add(Items.CYAN_DYE);
        ingredientsList.add(Items.BLUE_DYE);
        ingredientsList.add(Items.MAGENTA_DYE);
        ingredientsList.add(Items.PURPLE_DYE);
        ingredientsList.add(Items.BROWN_DYE);
        ingredientsList.add(Items.GRAY_DYE);
        ingredientsList.add(Items.LIGHT_GRAY_DYE);
        ingredientsList.add(Items.BLACK_DYE);
        ingredientsList.add(Items.QUARTZ);
        ingredientsList.add(Items.COPPER_INGOT);
        ingredientsList.add(Items.IRON_INGOT);
        ingredientsList.add(Items.GOLD_INGOT);
        ingredientsList.add(Items.DIAMOND);
        ingredientsList.add(Items.EMERALD);
        ingredientsList.add(Items.NETHERITE_SCRAP);
        ingredientsList.add(Items.REDSTONE);
        ingredientsList.add(Items.AMETHYST_SHARD);
        ingredientsList.add(Items.LAPIS_LAZULI);
        ingredientsList.add(Items.SCUTE);
        ingredientsList.add(Items.ENDER_PEARL);
        ingredientsList.add(Items.BAMBOO);
        ingredientsList.add(Items.WARPED_WART_BLOCK);
        ingredientsList.add(Items.ENDER_EYE);
        ingredientsList.add(Items.SCULK);
        ingredientsList.add(Items.PINK_PETALS);
    } // ingredientsList

    static {
        colorTypeList.add(BADLANDS);
        colorTypeList.add(BIRCH);
        colorTypeList.add(CHERRY);
        colorTypeList.add(DARK);
        colorTypeList.add(DESERT);
        colorTypeList.add(DRIPSTONEBE);
        colorTypeList.add(FOREST);
        colorTypeList.add(JUNGLE);
        colorTypeList.add(LUSHBE);
        colorTypeList.add(MANGROVE);
        colorTypeList.add(MEADOW);
        colorTypeList.add(MUSHROOM);
        colorTypeList.add(OCEAN);
        colorTypeList.add(PLAINS);
        colorTypeList.add(PINETAIGA);
        colorTypeList.add(SNOWYBEACH);
        colorTypeList.add(SNOWYPLAINS);
        colorTypeList.add(SPARSEJUNGLE);
        colorTypeList.add(STONYPEAKS);
        colorTypeList.add(SWAMP);
        colorTypeList.add(SWAMPCOLD);
        colorTypeList.add(TAIGA);
        colorTypeList.add(WINDSWEPT);
        colorTypeList.add(WHITE);
        colorTypeList.add(RED);
        colorTypeList.add(ORANGE);
        colorTypeList.add(PINK);
        colorTypeList.add(YELLOW);
        colorTypeList.add(LIME);
        colorTypeList.add(GREEN);
        colorTypeList.add(LIGHTBLUE);
        colorTypeList.add(CYAN);
        colorTypeList.add(BLUE);
        colorTypeList.add(MAGENTA);
        colorTypeList.add(PURPLE);
        colorTypeList.add(BROWN);
        colorTypeList.add(GRAY);
        colorTypeList.add(LIGHTGRAY);
        colorTypeList.add(BLACK);
        colorTypeList.add(QUARTZ);
        colorTypeList.add(COPPER);
        colorTypeList.add(IRON);
        colorTypeList.add(GOLD);
        colorTypeList.add(DIAMOND);
        colorTypeList.add(EMERALD);
        colorTypeList.add(NETHERITE);
        colorTypeList.add(REDSTONE);
        colorTypeList.add(AMETHYST);
        colorTypeList.add(LAPIS);
        colorTypeList.add(LIGHTLIME);
        colorTypeList.add(DARKGREEN);
        colorTypeList.add(XMAS);
        colorTypeList.add(MALACHITE);
        colorTypeList.add(GLASSBOTTLE);
        colorTypeList.add(LIVINGGREEN);
        colorTypeList.add(BETTERCHERRY);
    } // colorTypeList

    static {
        counterpartIDMap.put("acacia", ACACIA_LEAVES);
        counterpartIDMap.put("azalea", AZALEA_LEAVES);
        counterpartIDMap.put("birch", BIRCH_LEAVES);
        counterpartIDMap.put("cherry", CHERRY_LEAVES);
        counterpartIDMap.put("dark_oak", DARK_OAK_LEAVES);
        counterpartIDMap.put("flowering_azalea", FLOWERING_AZALEA);
        counterpartIDMap.put("jungle", JUNGLE_LEAVES);
        counterpartIDMap.put("mangrove", MANGROVE_LEAVES);
        counterpartIDMap.put("oak", OAK_LEAVES);
        counterpartIDMap.put("spruce", SPRUCE_LEAVES);

        counterpartIDMap.put("grass", GRASS);
        counterpartIDMap.put("tall_grass", TALL_GRASS);
        counterpartIDMap.put("fern", FERN);
        counterpartIDMap.put("large_fern", LARGE_FERN);
        counterpartIDMap.put("seagrass", SEAGRASS);
        counterpartIDMap.put("tall_seagrass", TALL_SEAGRASS);
        counterpartIDMap.put("vine", VINE);
        counterpartIDMap.put("lily_pad", LILY_PAD);
        counterpartIDMap.put("sugar_cane", SUGAR_CANE);
        counterpartIDMap.put("bamboo", BAMBOO);
        counterpartIDMap.put("bamboo_sapling", BAMBOO_SAPLING);
        counterpartIDMap.put("big_drip_leaf", BIG_DRIPLEAF);
        counterpartIDMap.put("big_drip_leaf_stem", BIG_DRIPLEAF_STEM);
        counterpartIDMap.put("small_drip_leaf", SMALL_DRIPLEAF);
        counterpartIDMap.put("kelp", KELP);
        counterpartIDMap.put("kep_plant", KELP_PLANT);
        counterpartIDMap.put("cactus", CACTUS);

        counterpartIDMap.put("bars_grass", GRASS_IN_BARS.get());
        counterpartIDMap.put("fern_grass", FERN_IN_BARS.get());
        counterpartIDMap.put("vine_grass", VINE_IN_BARS.get());

        counterpartIDMap.put("potted_grass", GRASS_POTTED.get());
        counterpartIDMap.put("potted_fern", POTTED_FERN);
        counterpartIDMap.put("seagrass_potted", SEAGRASS_POTTED.get());
        counterpartIDMap.put("bamboo_potted", POTTED_BAMBOO);
        counterpartIDMap.put("sugar_cane_potted", SUGAR_CANE_POTTED.get());
        counterpartIDMap.put("vine_potted", VINE_POTTED.get());
        counterpartIDMap.put("big_drip_leaf_potted", BIG_DRIP_LEAF_POTTED.get());
        counterpartIDMap.put("small_drip_leaf_potted", SMALL_DRIP_LEAF_POTTED.get());
        counterpartIDMap.put("kelp_potted", KELP_POTTED.get());
        counterpartIDMap.put("cactus_potted", POTTED_CACTUS);

        counterpartIDMap.put("acacia_leaves_potted", ACACIA_LEAVES_POTTED.get());
        counterpartIDMap.put("azalea_leaves_potted", AZALEA_LEAVES_POTTED.get());
        counterpartIDMap.put("birch_leaves_potted", BIRCH_LEAVES_POTTED.get());
        counterpartIDMap.put("cherry_leaves_potted", CHERRY_LEAVES_POTTED.get());
        counterpartIDMap.put("dark_oak_leaves_potted", DARK_OAK_LEAVES_POTTED.get());
        counterpartIDMap.put("flowering_leaves_potted", FLOWERING_AZALEA_LEAVES_POTTED.get());
        counterpartIDMap.put("jungle_leaves_potted", JUNGLE_LEAVES_POTTED.get());
        counterpartIDMap.put("mangrove_leaves_potted", MANGROVE_LEAVES_POTTED.get());
        counterpartIDMap.put("oak_leaves_potted", OAK_LEAVES_POTTED.get());
        counterpartIDMap.put("spruce_leaves_potted", SPRUCE_LEAVES_POTTED.get());

        if (isBOPLoaded) {
            counterpartIDMap.put("bop_fir", FIR_LEAVES);
            counterpartIDMap.put("bop_pine", PINE_LEAVES);
            counterpartIDMap.put("bop_red_maple", RED_MAPLE_LEAVES);
            counterpartIDMap.put("bop_orange_maple", ORANGE_MAPLE_LEAVES);
            counterpartIDMap.put("bop_yellow_maple", YELLOW_MAPLE_LEAVES);
            counterpartIDMap.put("bop_redwood", REDWOOD_LEAVES);
            counterpartIDMap.put("bop_mahogany", MAHOGANY_LEAVES);
            counterpartIDMap.put("bop_jacaranda", JACARANDA_LEAVES);
            counterpartIDMap.put("bop_palm", PALM_LEAVES);
            counterpartIDMap.put("bop_willow", WILLOW_LEAVES);
            counterpartIDMap.put("bop_dead", DEAD_LEAVES);
            counterpartIDMap.put("bop_magic", MAGIC_LEAVES);
            counterpartIDMap.put("bop_umbran", UMBRAN_LEAVES);
            counterpartIDMap.put("bop_empyreal", EMPYREAL_LEAVES);
            counterpartIDMap.put("bop_flowering_oak", FLOWERING_OAK_LEAVES);
            counterpartIDMap.put("bop_origin", ORIGIN_LEAVES);
            counterpartIDMap.put("bop_cypress", CYPRESS_LEAVES);
            counterpartIDMap.put("bop_hellbark", HELLBARK_LEAVES);
            counterpartIDMap.put("bop_rainbow_birch", RAINBOW_BIRCH_LEAVES);
            counterpartIDMap.put("bop_snowblossom", SNOWBLOSSOM_LEAVES);

            counterpartIDMap.put("bop_bush", BUSH);
            counterpartIDMap.put("bop_sprout", SPROUT);
            counterpartIDMap.put("bop_clover", CLOVER);
            counterpartIDMap.put("bop_huge_clover", HUGE_CLOVER_PETAL);
            counterpartIDMap.put("bop_high_grass", HIGH_GRASS);
            counterpartIDMap.put("bop_high_grass_plant", HIGH_GRASS_PLANT);
            counterpartIDMap.put("bop_huge_lily_pad", HUGE_LILY_PAD);
            counterpartIDMap.put("bop_water_grass", WATERGRASS);
            counterpartIDMap.put("bop_tiny_cactus", TINY_CACTUS);
            counterpartIDMap.put("bop_waterlily", WATERLILY);
            counterpartIDMap.put("bop_willow_vine", WILLOW_VINE);
            counterpartIDMap.put("bop_red_leaf_pile", RED_MAPLE_LEAF_PILE);
            counterpartIDMap.put("bop_orange_leaf_pile", ORANGE_MAPLE_LEAF_PILE);
            counterpartIDMap.put("bop_yellow_leaf_pile", YELLOW_MAPLE_LEAF_PILE);
        }
    } // counterpartIDMap

    static {

        matchingCounterpartsLeaves.put(Blocks.ACACIA_LEAVES, ACACIA_LEAVES_BLOCK.get());
        matchingCounterpartsLeaves.put(Blocks.AZALEA_LEAVES, AZALEA_LEAVES_BLOCK.get());
        matchingCounterpartsLeaves.put(Blocks.BIRCH_LEAVES, BIRCH_LEAVES_BLOCK.get());
        matchingCounterpartsLeaves.put(Blocks.CHERRY_LEAVES, CHERRY_LEAVES_BLOCK.get());
        matchingCounterpartsLeaves.put(Blocks.DARK_OAK_LEAVES, DARK_OAK_LEAVES_BLOCK.get());
        matchingCounterpartsLeaves.put(Blocks.FLOWERING_AZALEA_LEAVES, FLOWERING_AZALEA_LEAVES_BLOCK.get());
        matchingCounterpartsLeaves.put(Blocks.JUNGLE_LEAVES, JUNGLE_LEAVES_BLOCK.get());
        matchingCounterpartsLeaves.put(Blocks.MANGROVE_LEAVES, MANGROVE_LEAVES_BLOCK.get());
        matchingCounterpartsLeaves.put(Blocks.OAK_LEAVES, OAK_LEAVES_BLOCK.get());
        matchingCounterpartsLeaves.put(Blocks.SPRUCE_LEAVES, SPRUCE_LEAVES_BLOCK.get());

        matchingCounterpartsPlants.put(GRASS, GRASS_TINTED.get());
        matchingCounterpartsPlants.put(TALL_GRASS, GRASS_TALL_TINTED.get());
        matchingCounterpartsPlants.put(FERN, FERN_TINTED.get());
        matchingCounterpartsPlants.put(LARGE_FERN, FERN_TALL_TINTED.get());
        matchingCounterpartsPlants.put(SEAGRASS, SEAGRASS_TINTED.get());
        matchingCounterpartsPlants.put(TALL_SEAGRASS, SEAGRASS_TALL_TINTED.get());
        matchingCounterpartsPlants.put(VINE, VINE_TINTED.get());
        matchingCounterpartsPlants.put(LILY_PAD, LILY_TINTED.get());
        matchingCounterpartsPlants.put(SUGAR_CANE, SUGAR_CANE_TINTED.get());
        matchingCounterpartsPlants.put(BAMBOO_SAPLING, BAMBOO_SAPLING_TINTED.get());
        matchingCounterpartsPlants.put(BAMBOO, BAMBOO_TINTED.get());
        matchingCounterpartsPlants.put(BIG_DRIPLEAF, BIG_DRIP_LEAF_TINTED.get());
        matchingCounterpartsPlants.put(BIG_DRIPLEAF_STEM, BIG_DRIP_LEAF_STEM_TINTED.get());
        matchingCounterpartsPlants.put(SMALL_DRIPLEAF, SMALL_DRIP_LEAF_TINTED.get());
        matchingCounterpartsPlants.put(KELP_PLANT, KELP_PLANT_TINTED.get());
        matchingCounterpartsPlants.put(KELP, KELP_TINTED.get());
        matchingCounterpartsPlants.put(CACTUS, CACTUS_TINTED.get());

        matchingCounterpartsPlants.put(GRASS_IN_BARS.get(), TINTED_GRASS_IN_BARS.get());
        matchingCounterpartsPlants.put(FERN_IN_BARS.get(), TINTED_FERN_IN_BARS.get());
        matchingCounterpartsPlants.put(VINE_IN_BARS.get(), TINTED_VINE_IN_BARS.get());

        //matchingCounterpartsPotted.put(GRASS_POTTED.get(), GRASS_TINTED.get());

        if (isBOPLoaded) {
            matchingCounterpartsLeaves.put(FIR_LEAVES, FIR_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(PINE_LEAVES, PINE_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(RED_MAPLE_LEAVES, MAPLE_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(ORANGE_MAPLE_LEAVES, MAPLE_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(YELLOW_MAPLE_LEAVES, MAPLE_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(REDWOOD_LEAVES, REDWOOD_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(MAHOGANY_LEAVES, MAHOGANY_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(JACARANDA_LEAVES, JACARANDA_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(PALM_LEAVES, PALM_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(WILLOW_LEAVES, WILLOW_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(DEAD_LEAVES, DEAD_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(MAGIC_LEAVES, MAGIC_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(UMBRAN_LEAVES, UMBRAN_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(EMPYREAL_LEAVES, EMPYREAL_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(RAINBOW_BIRCH_LEAVES, BIRCH_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(SNOWBLOSSOM_LEAVES, CHERRY_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(FLOWERING_OAK_LEAVES, FLOWERING_OAK_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(ORIGIN_LEAVES, ORIGIN_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(CYPRESS_LEAVES, CYPRESS_LEAVES_BLOCK.get());
            matchingCounterpartsLeaves.put(HELLBARK_LEAVES, HELLBARK_LEAVES_BLOCK.get());


            matchingCounterpartsPlants.put(BUSH, BUSH_TINTED.get());
            matchingCounterpartsPlants.put(SPROUT, SPROUT_TINTED.get());
            matchingCounterpartsPlants.put(CLOVER, CLOVER_TINTED.get());
            matchingCounterpartsPlants.put(HUGE_CLOVER_PETAL, HUGE_CLOVER_TINTED.get());
            matchingCounterpartsPlants.put(HIGH_GRASS, HIGH_GRASS_TINTED.get());
            matchingCounterpartsPlants.put(HIGH_GRASS_PLANT, HIGH_GRASS_PLANT_TINTED.get());
            matchingCounterpartsPlants.put(HUGE_LILY_PAD, HUGE_LILY_PAD_TINTED.get());
            matchingCounterpartsPlants.put(WATERGRASS, WATER_GRASS_TINTED.get());
            matchingCounterpartsPlants.put(TINY_CACTUS, TINY_CACTUS_TINTED.get());
            matchingCounterpartsPlants.put(WATERLILY, WATERLILY_TINTED.get());
            matchingCounterpartsPlants.put(WILLOW_VINE, WILLOW_VINE_TINTED.get());
            matchingCounterpartsPlants.put(YELLOW_MAPLE_LEAF_PILE, LEAF_PILE_TINTED.get());
            matchingCounterpartsPlants.put(ORANGE_MAPLE_LEAF_PILE, LEAF_PILE_TINTED.get());
            matchingCounterpartsPlants.put(RED_MAPLE_LEAF_PILE, LEAF_PILE_TINTED.get());
        }

    } // matchingCounterpartsLeaves, matchingCounterpartsPlants

    static {
        matchingCounterpartsVines.put(VINE, VINE_TINTED.get());
        if (isBOPLoaded) {
            matchingCounterpartsVines.put(WILLOW_VINE, WILLOW_VINE_TINTED.get());
        }
    } // matchingCounterpartsVines

    static {
        matchingLogsWithLeaves.put(ACACIA_LEAVES, ACACIA_LOGS);
        matchingLogsWithLeaves.put(ACACIA_LEAVES_BLOCK.get(), ACACIA_LOGS);

        matchingLogsWithLeaves.put(AZALEA_LEAVES, OAK_LOGS);
        matchingLogsWithLeaves.put(AZALEA_LEAVES_BLOCK.get(), OAK_LOGS);

        matchingLogsWithLeaves.put(BIRCH_LEAVES, BIRCH_LOGS);
        matchingLogsWithLeaves.put(BIRCH_LEAVES_BLOCK.get(), BIRCH_LOGS);

        matchingLogsWithLeaves.put(CHERRY_LEAVES, CHERRY_LOGS);
        matchingLogsWithLeaves.put(CHERRY_LEAVES_BLOCK.get(), CHERRY_LOGS);

        matchingLogsWithLeaves.put(DARK_OAK_LEAVES, DARK_OAK_LOGS);
        matchingLogsWithLeaves.put(DARK_OAK_LEAVES_BLOCK.get(), DARK_OAK_LOGS);

        matchingLogsWithLeaves.put(FLOWERING_AZALEA_LEAVES, OAK_LOGS);
        matchingLogsWithLeaves.put(FLOWERING_AZALEA_LEAVES_BLOCK.get(), OAK_LOGS);

        matchingLogsWithLeaves.put(JUNGLE_LEAVES, JUNGLE_LOGS);
        matchingLogsWithLeaves.put(JUNGLE_LEAVES_BLOCK.get(), JUNGLE_LOGS);

        matchingLogsWithLeaves.put(MANGROVE_LEAVES, MANGROVE_LOGS);
        matchingLogsWithLeaves.put(MANGROVE_LEAVES_BLOCK.get(), MANGROVE_LOGS);

        matchingLogsWithLeaves.put(OAK_LEAVES, OAK_LOGS);
        matchingLogsWithLeaves.put(OAK_LEAVES_BLOCK.get(), OAK_LOGS);

        matchingLogsWithLeaves.put(SPRUCE_LEAVES, SPRUCE_LOGS);
        matchingLogsWithLeaves.put(SPRUCE_LEAVES_BLOCK.get(), SPRUCE_LOGS);

        if (isBOPLoaded) {
            matchingLogsWithLeaves.put(FIR_LEAVES, FIR_LOGS);
            matchingLogsWithLeaves.put(FIR_LEAVES_BLOCK.get(), FIR_LOGS);

            matchingLogsWithLeaves.put(PINE_LEAVES, PINE_LOGS);
            matchingLogsWithLeaves.put(PINE_LEAVES_BLOCK.get(), PINE_LOGS);

            matchingLogsWithLeaves.put(ORANGE_MAPLE_LEAVES, MAPLE_LOGS);
            matchingLogsWithLeaves.put(MAPLE_LEAVES_BLOCK.get(), MAPLE_LOGS);

            matchingLogsWithLeaves.put(RED_MAPLE_LEAVES, MAPLE_LOGS);

            matchingLogsWithLeaves.put(YELLOW_MAPLE_LEAVES, MAPLE_LOGS);

            matchingLogsWithLeaves.put(REDWOOD_LEAVES, REDWOOD_LOGS);
            matchingLogsWithLeaves.put(REDWOOD_LEAVES_BLOCK.get(), REDWOOD_LOGS);

            matchingLogsWithLeaves.put(MAHOGANY_LEAVES, MAHOGANY_LOGS);
            matchingLogsWithLeaves.put(MAHOGANY_LEAVES_BLOCK.get(), MAHOGANY_LOGS);

            matchingLogsWithLeaves.put(JACARANDA_LEAVES, JACARANDA_LOGS);
            matchingLogsWithLeaves.put(JACARANDA_LEAVES_BLOCK.get(), JACARANDA_LOGS);

            matchingLogsWithLeaves.put(PALM_LEAVES, PALM_LOGS);
            matchingLogsWithLeaves.put(PALM_LEAVES_BLOCK.get(), PALM_LOGS);

            matchingLogsWithLeaves.put(WILLOW_LEAVES, WILLOW_LOGS);
            matchingLogsWithLeaves.put(WILLOW_LEAVES_BLOCK.get(), WILLOW_LOGS);

            matchingLogsWithLeaves.put(DEAD_LEAVES, DEAD_LOGS);
            matchingLogsWithLeaves.put(DEAD_LEAVES_BLOCK.get(), DEAD_LOGS);

            matchingLogsWithLeaves.put(MAGIC_LEAVES, MAGIC_LOGS);
            matchingLogsWithLeaves.put(MAGIC_LEAVES_BLOCK.get(), MAGIC_LOGS);

            matchingLogsWithLeaves.put(UMBRAN_LEAVES, UMBRAN_LOGS);
            matchingLogsWithLeaves.put(UMBRAN_LEAVES_BLOCK.get(), UMBRAN_LOGS);

            matchingLogsWithLeaves.put(EMPYREAL_LEAVES, EMPYREAL_LOGS);
            matchingLogsWithLeaves.put(EMPYREAL_LEAVES_BLOCK.get(), EMPYREAL_LOGS);

            matchingLogsWithLeaves.put(FLOWERING_OAK_LEAVES, OAK_LOGS);
            matchingLogsWithLeaves.put(FLOWERING_OAK_LEAVES_BLOCK.get(), OAK_LOGS);

            matchingLogsWithLeaves.put(ORIGIN_LEAVES, OAK_LOGS);
            matchingLogsWithLeaves.put(ORIGIN_LEAVES_BLOCK.get(), OAK_LOGS);

            matchingLogsWithLeaves.put(CYPRESS_LEAVES, SPRUCE_LOGS);
            matchingLogsWithLeaves.put(CYPRESS_LEAVES_BLOCK.get(), SPRUCE_LOGS);

            matchingLogsWithLeaves.put(HELLBARK_LEAVES, HELLBARK_LOGS);
            matchingLogsWithLeaves.put(HELLBARK_LEAVES_BLOCK.get(), HELLBARK_LOGS);

            matchingLogsWithLeaves.put(RAINBOW_BIRCH_LEAVES, BIRCH_LOGS);
            matchingLogsWithLeaves.put(SNOWBLOSSOM_LEAVES, CHERRY_LOGS);
        }

    } // matchingLogsWithLeaves

    static {
        vanillaLogsTagsBlocksList.add(ACACIA_LOGS);
        vanillaLogsTagsBlocksList.add(BIRCH_LOGS);
        vanillaLogsTagsBlocksList.add(CHERRY_LOGS);
        vanillaLogsTagsBlocksList.add(DARK_OAK_LOGS);
        vanillaLogsTagsBlocksList.add(JUNGLE_LOGS);
        vanillaLogsTagsBlocksList.add(MANGROVE_LOGS);
        vanillaLogsTagsBlocksList.add(OAK_LOGS);
        vanillaLogsTagsBlocksList.add(SPRUCE_LOGS);

        if (isBOPLoaded) {
            BOPLogsTagsBlocksList.add(FIR_LOGS);
            BOPLogsTagsBlocksList.add(PINE_LOGS);
            BOPLogsTagsBlocksList.add(MAPLE_LOGS);
            BOPLogsTagsBlocksList.add(REDWOOD_LOGS);
            BOPLogsTagsBlocksList.add(MAHOGANY_LOGS);
            BOPLogsTagsBlocksList.add(JACARANDA_LOGS);
            BOPLogsTagsBlocksList.add(PALM_LOGS);
            BOPLogsTagsBlocksList.add(WILLOW_LOGS);
            BOPLogsTagsBlocksList.add(DEAD_LOGS);
            BOPLogsTagsBlocksList.add(MAGIC_LOGS);
            BOPLogsTagsBlocksList.add(UMBRAN_LOGS);
            BOPLogsTagsBlocksList.add(EMPYREAL_LOGS);
            BOPLogsTagsBlocksList.add(HELLBARK_LOGS);
        }
    } // vanillaLogsTagsBlocksList && BOPLogsTagsBlocksList

    static {
        plantsChangedIntoTintedWithFlag.put(GRASS, 3);
        plantsChangedIntoTintedWithFlag.put(TALL_GRASS, 19);
        plantsChangedIntoTintedWithFlag.put(FERN, 3);
        plantsChangedIntoTintedWithFlag.put(LARGE_FERN, 19);
        plantsChangedIntoTintedWithFlag.put(SEAGRASS, 3);
        plantsChangedIntoTintedWithFlag.put(TALL_SEAGRASS, 19);
        plantsChangedIntoTintedWithFlag.put(VINE, 19);
        plantsChangedIntoTintedWithFlag.put(LILY_PAD, 3);
        plantsChangedIntoTintedWithFlag.put(SUGAR_CANE, 19);
        plantsChangedIntoTintedWithFlag.put(BAMBOO, 19);
        plantsChangedIntoTintedWithFlag.put(BAMBOO_SAPLING, 19);
        plantsChangedIntoTintedWithFlag.put(BIG_DRIPLEAF, 19);
        plantsChangedIntoTintedWithFlag.put(BIG_DRIPLEAF_STEM, 19);
        plantsChangedIntoTintedWithFlag.put(SMALL_DRIPLEAF, 19);
        plantsChangedIntoTintedWithFlag.put(KELP, 19);
        plantsChangedIntoTintedWithFlag.put(KELP_PLANT, 19);
        plantsChangedIntoTintedWithFlag.put(CACTUS, 19);
        plantsChangedIntoTintedWithFlag.put(GRASS_IN_BARS.get(), 3);
        plantsChangedIntoTintedWithFlag.put(FERN_IN_BARS.get(), 3);
        plantsChangedIntoTintedWithFlag.put(VINE_IN_BARS.get(), 3);

        if (isBOPLoaded){
            plantsChangedIntoTintedWithFlag.put(BUSH, 3);
            plantsChangedIntoTintedWithFlag.put(SPROUT, 3);
            plantsChangedIntoTintedWithFlag.put(CLOVER, 3);
            plantsChangedIntoTintedWithFlag.put(HUGE_CLOVER_PETAL, 19);
            plantsChangedIntoTintedWithFlag.put(HIGH_GRASS, 19);
            plantsChangedIntoTintedWithFlag.put(HIGH_GRASS_PLANT, 19);
            plantsChangedIntoTintedWithFlag.put(HUGE_LILY_PAD, 19);
            plantsChangedIntoTintedWithFlag.put(WATERGRASS, 19);
            plantsChangedIntoTintedWithFlag.put(TINY_CACTUS, 3);
            plantsChangedIntoTintedWithFlag.put(WATERLILY, 3);
            plantsChangedIntoTintedWithFlag.put(WILLOW_VINE, 19);
            plantsChangedIntoTintedWithFlag.put(YELLOW_MAPLE_LEAF_PILE, 3);
            plantsChangedIntoTintedWithFlag.put(ORANGE_MAPLE_LEAF_PILE, 3);
            plantsChangedIntoTintedWithFlag.put(RED_MAPLE_LEAF_PILE, 3);
        }

    } // plantsChangedIntoTintedWithFlag

    static {
        if (isBOPLoaded){
            BOPPlantsBlocksList.add(BUSH);
            BOPPlantsBlocksList.add(SPROUT);
            BOPPlantsBlocksList.add(CLOVER);
            BOPPlantsBlocksList.add(HUGE_CLOVER_PETAL);
            BOPPlantsBlocksList.add(HIGH_GRASS);
            BOPPlantsBlocksList.add(HIGH_GRASS_PLANT);
            BOPPlantsBlocksList.add(HUGE_LILY_PAD);
            BOPPlantsBlocksList.add(WATERGRASS);
            BOPPlantsBlocksList.add(TINY_CACTUS);
            BOPPlantsBlocksList.add(WATERLILY);
            BOPPlantsBlocksList.add(WILLOW_VINE);
            BOPPlantsBlocksList.add(YELLOW_MAPLE_LEAF_PILE);
            BOPPlantsBlocksList.add(ORANGE_MAPLE_LEAF_PILE);
            BOPPlantsBlocksList.add(RED_MAPLE_LEAF_PILE);
        }

    } //  BOPPlantsBlocksList

    static {

        for (RegistryObject<Block> block : allLeavesRegistryBlocksList) {
            modBlockItemsWithNBT.add(block.get().asItem());

        }
        for (RegistryObject<Block> block : plantRegistryBlockList) {
            modBlockItemsWithNBT.add(block.get().asItem());
        }

        for (RegistryObject<Block> block : plantInBarsTintedRegistryBlockList) {
            modBlockItemsWithNBT.add(block.get().asItem());
        }

        if (!isBOPLoaded) {
            for (RegistryObject<Block> block : tintedBOPleavesRegistryBlocksList) {
                modBlockItemsWithNBT.add(block.get().asItem());

            }
            for (RegistryObject<Block> block : tintedBOPplantRegistryBlockList) {
                modBlockItemsWithNBT.add(block.get().asItem());
            }
        }
        modBlockItemsWithNBT.add(DYEING_BONE_MEAL.get().asItem());
        modBlockItemsWithNBT.add(GRASSES_DYE.get().asItem());
    } //modBlockItemsWithNBT

    static {
        notForCreativeTab.add(BAMBOO_SAPLING_TINTED.get().asItem());
        notForCreativeTab.add(KELP_PLANT_TINTED.get().asItem());
        notForCreativeTab.add(BIG_DRIP_LEAF_STEM_TINTED.get().asItem());
        notForCreativeTab.add(FERTILE_ICON.get());

        for (RegistryObject<Block> block : plantInBarsRegistryBlockList) {
            notForCreativeTab.add(block.get().asItem());
        }

        notForCreativeTab.add(HIGH_GRASS_PLANT_TINTED.get().asItem());
    } // notForCreativeTab

    static {

        tintedBOPleavesBlocksList.clear();
        tintedBOPPlantBlockList.clear();
        for (RegistryObject<Block> leaves : tintedBOPleavesRegistryBlocksList) {
            tintedBOPRestrictedCreativeTab.add(leaves.get().asItem());
            tintedBOPleavesBlocksList.add(leaves.get());
        }
        for (RegistryObject<Block> plant : tintedBOPplantRegistryBlockList) {
            tintedBOPRestrictedCreativeTab.add(plant.get().asItem());
            tintedBOPPlantBlockList.add(plant.get());
        }

    } // tintedBOPRestrictedCreativeTab && tintedBOPPlantBlockList

    static {
        matchingTintedPottedWithPlant.put(GRASS_POTTED_TINTED.get(), GRASS_TINTED.get());
        matchingTintedPottedWithPlant.put(FERN_POTTED_TINTED.get(), FERN_TINTED.get());
        matchingTintedPottedWithPlant.put(SEAGRASS_POTTED_TINTED.get(), SEAGRASS_TINTED.get());
        matchingTintedPottedWithPlant.put(BAMBOO_POTTED_TINTED.get(), BAMBOO_TINTED.get());
        matchingTintedPottedWithPlant.put(SUGAR_CANE_POTTED_TINTED.get(), SUGAR_CANE_TINTED.get());
        matchingTintedPottedWithPlant.put(SUGAR_CANE_POTTED_TINTED.get(), SUGAR_CANE_TINTED.get());
        matchingTintedPottedWithPlant.put(VINE_POTTED_TINTED.get(), VINE_TINTED.get());
        matchingTintedPottedWithPlant.put(SMALL_DRIP_LEAF_POTTED_TINTED.get(), SMALL_DRIP_LEAF_TINTED.get());
        matchingTintedPottedWithPlant.put(BIG_DRIP_LEAF_POTTED_TINTED.get(), BIG_DRIP_LEAF_TINTED.get());
        matchingTintedPottedWithPlant.put(KELP_POTTED_TINTED.get(), KELP_TINTED.get());
        matchingTintedPottedWithPlant.put(CACTUS_POTTED_TINTED.get(), CACTUS_TINTED.get());

        matchingTintedPottedWithPlant.put(ACACIA_LEAVES_POTTED_TINTED.get(), ACACIA_LEAVES_BLOCK.get());
        matchingTintedPottedWithPlant.put(AZALEA_LEAVES_POTTED_TINTED.get(), AZALEA_LEAVES_BLOCK.get());
        matchingTintedPottedWithPlant.put(BIRCH_LEAVES_POTTED_TINTED.get(), BIRCH_LEAVES_BLOCK.get());
        matchingTintedPottedWithPlant.put(CHERRY_LEAVES_POTTED_TINTED.get(), CHERRY_LEAVES_BLOCK.get());
        matchingTintedPottedWithPlant.put(DARK_OAK_LEAVES_POTTED_TINTED.get(), DARK_OAK_LEAVES_BLOCK.get());
        matchingTintedPottedWithPlant.put(FLOWERING_AZALEA_LEAVES_POTTED_TINTED.get(), FLOWERING_AZALEA_LEAVES_BLOCK.get());
        matchingTintedPottedWithPlant.put(JUNGLE_LEAVES_POTTED_TINTED.get(), JUNGLE_LEAVES_BLOCK.get());
        matchingTintedPottedWithPlant.put(MANGROVE_LEAVES_POTTED_TINTED.get(), MANGROVE_LEAVES_BLOCK.get());
        matchingTintedPottedWithPlant.put(OAK_LEAVES_POTTED_TINTED.get(), OAK_LEAVES_BLOCK.get());
        matchingTintedPottedWithPlant.put(SPRUCE_LEAVES_POTTED_TINTED.get(), SPRUCE_LEAVES_BLOCK.get());

    } // matchingTintedPottedWithPlant

    static {
        matchingNotTintedPottedWithPlant.put(GRASS_POTTED.get(), GRASS);
        matchingNotTintedPottedWithPlant.put(SEAGRASS_POTTED.get(), SEAGRASS);
        matchingNotTintedPottedWithPlant.put(SUGAR_CANE_POTTED.get(), SUGAR_CANE);
        matchingNotTintedPottedWithPlant.put(VINE_POTTED.get(), VINE);
        matchingNotTintedPottedWithPlant.put(BIG_DRIP_LEAF_POTTED.get(), BIG_DRIPLEAF);
        matchingNotTintedPottedWithPlant.put(SMALL_DRIP_LEAF_POTTED.get(), SMALL_DRIPLEAF);
        matchingNotTintedPottedWithPlant.put(KELP_POTTED.get(), KELP);

        matchingNotTintedPottedWithPlant.put(ACACIA_LEAVES_POTTED.get(), ACACIA_LEAVES);
        matchingNotTintedPottedWithPlant.put(AZALEA_LEAVES_POTTED.get(), AZALEA_LEAVES);
        matchingNotTintedPottedWithPlant.put(BIRCH_LEAVES_POTTED.get(), BIRCH_LEAVES);
        matchingNotTintedPottedWithPlant.put(CHERRY_LEAVES_POTTED.get(), CHERRY_LEAVES);
        matchingNotTintedPottedWithPlant.put(DARK_OAK_LEAVES_POTTED.get(), DARK_OAK_LEAVES);
        matchingNotTintedPottedWithPlant.put(FLOWERING_AZALEA_LEAVES_POTTED.get(), FLOWERING_AZALEA_LEAVES);
        matchingNotTintedPottedWithPlant.put(JUNGLE_LEAVES_POTTED.get(), JUNGLE_LEAVES);
        matchingNotTintedPottedWithPlant.put(MANGROVE_LEAVES_POTTED.get(), MANGROVE_LEAVES);
        matchingNotTintedPottedWithPlant.put(OAK_LEAVES_POTTED.get(), OAK_LEAVES);
        matchingNotTintedPottedWithPlant.put(SPRUCE_LEAVES_POTTED.get(), SPRUCE_LEAVES);

        matchingNotTintedPottedWithPlant.put(POTTED_BLUE_ORCHID, BLUE_ORCHID);
        matchingNotTintedPottedWithPlant.put(POTTED_ALLIUM, ALLIUM);
        matchingNotTintedPottedWithPlant.put(POTTED_AZURE_BLUET, AZURE_BLUET);
        matchingNotTintedPottedWithPlant.put(POTTED_RED_TULIP, RED_TULIP);
        matchingNotTintedPottedWithPlant.put(POTTED_ORANGE_TULIP, ORANGE_TULIP);
        matchingNotTintedPottedWithPlant.put(POTTED_WHITE_TULIP, WHITE_TULIP);
        matchingNotTintedPottedWithPlant.put(POTTED_PINK_TULIP, PINK_TULIP);
        matchingNotTintedPottedWithPlant.put(POTTED_OXEYE_DAISY, OXEYE_DAISY);
        matchingNotTintedPottedWithPlant.put(POTTED_DANDELION, DANDELION);
        matchingNotTintedPottedWithPlant.put(POTTED_OAK_SAPLING, OAK_SAPLING);
        matchingNotTintedPottedWithPlant.put(POTTED_SPRUCE_SAPLING, SPRUCE_SAPLING);
        matchingNotTintedPottedWithPlant.put(POTTED_BIRCH_SAPLING, BIRCH_SAPLING);
        matchingNotTintedPottedWithPlant.put(POTTED_JUNGLE_SAPLING, JUNGLE_SAPLING);
        matchingNotTintedPottedWithPlant.put(POTTED_ACACIA_SAPLING, ACACIA_SAPLING);
        matchingNotTintedPottedWithPlant.put(POTTED_DARK_OAK_SAPLING, DARK_OAK_SAPLING);
        matchingNotTintedPottedWithPlant.put(POTTED_RED_MUSHROOM, RED_MUSHROOM);
        matchingNotTintedPottedWithPlant.put(POTTED_BROWN_MUSHROOM, BROWN_MUSHROOM);
        matchingNotTintedPottedWithPlant.put(POTTED_DEAD_BUSH, DEAD_BUSH);
        matchingNotTintedPottedWithPlant.put(POTTED_FERN, FERN);
        matchingNotTintedPottedWithPlant.put(POTTED_CACTUS, CACTUS);
        matchingNotTintedPottedWithPlant.put(POTTED_CORNFLOWER, CORNFLOWER);
        matchingNotTintedPottedWithPlant.put(POTTED_LILY_OF_THE_VALLEY, LILY_OF_THE_VALLEY);
        matchingNotTintedPottedWithPlant.put(POTTED_WITHER_ROSE, WITHER_ROSE);
        matchingNotTintedPottedWithPlant.put(POTTED_BAMBOO, BAMBOO);
        matchingNotTintedPottedWithPlant.put(POTTED_CRIMSON_FUNGUS, CRIMSON_FUNGUS);
        matchingNotTintedPottedWithPlant.put(POTTED_WARPED_FUNGUS, WARPED_FUNGUS);
        matchingNotTintedPottedWithPlant.put(POTTED_CRIMSON_ROOTS, CRIMSON_ROOTS);
        matchingNotTintedPottedWithPlant.put(POTTED_WARPED_ROOTS, WARPED_ROOTS);
        matchingNotTintedPottedWithPlant.put(POTTED_AZALEA, AZALEA);
        matchingNotTintedPottedWithPlant.put(POTTED_FLOWERING_AZALEA, FLOWERING_AZALEA);
        matchingNotTintedPottedWithPlant.put(POTTED_MANGROVE_PROPAGULE, MANGROVE_PROPAGULE);
        matchingNotTintedPottedWithPlant.put(POTTED_CHERRY_SAPLING, CHERRY_SAPLING);
        matchingNotTintedPottedWithPlant.put(POTTED_TORCHFLOWER, TORCHFLOWER);

        if (isBOPLoaded) {

        }
    } // matchingNotTintedPottedWithPlant

    static {
        for(RegistryObject<Block> leaves : pottedTintedRegistryLeavesList){
            tintedLeavesPottedBlockList.add(leaves.get());
        }
    } // vanillaLeavesPottedBlockList

    static {

        matchingBarsWithPlant.put(GRASS_IN_BARS.get(), GRASS);
        matchingBarsWithPlant.put(FERN_IN_BARS.get(), FERN);
        matchingBarsWithPlant.put(VINE_IN_BARS.get(), VINE);
        matchingBarsWithPlant.put(TINTED_GRASS_IN_BARS.get(), GRASS_TINTED.get());
        matchingBarsWithPlant.put(TINTED_FERN_IN_BARS.get(), FERN_TINTED.get());
        matchingBarsWithPlant.put(TINTED_VINE_IN_BARS.get(), VINE_TINTED.get());

    } // matchingBarsWithPlant

    static {
        matchingCounterpartsVanillaPotted.put(POTTED_FERN, FERN_POTTED_TINTED.get());
        matchingCounterpartsVanillaPotted.put(POTTED_BAMBOO, BAMBOO_POTTED_TINTED.get());
        matchingCounterpartsVanillaPotted.put(POTTED_CACTUS, CACTUS_POTTED_TINTED.get());
    } // matchingCounterpartsVanillaPotted

    static {
        tintedPlantsThatCanBePotted.add(GRASS_TINTED.get().asItem());
        tintedPlantsThatCanBePotted.add(FERN_TINTED.get().asItem());
        tintedPlantsThatCanBePotted.add(SEAGRASS_TINTED.get().asItem());
        tintedPlantsThatCanBePotted.add(BAMBOO_TINTED.get().asItem());
        tintedPlantsThatCanBePotted.add(SUGAR_CANE_TINTED.get().asItem());
        tintedPlantsThatCanBePotted.add(BIG_DRIP_LEAF_TINTED.get().asItem());
        tintedPlantsThatCanBePotted.add(SMALL_DRIP_LEAF_TINTED.get().asItem());
        tintedPlantsThatCanBePotted.add(KELP_TINTED.get().asItem());
        tintedPlantsThatCanBePotted.add(CACTUS_TINTED.get().asItem());
        tintedPlantsThatCanBePotted.add(VINE_TINTED.get().asItem());

        tintedPlantsThatCanBePotted.add(ACACIA_LEAVES_BLOCK.get().asItem());
        tintedPlantsThatCanBePotted.add(AZALEA_LEAVES_BLOCK.get().asItem());
        tintedPlantsThatCanBePotted.add(BIRCH_LEAVES_BLOCK.get().asItem());
        tintedPlantsThatCanBePotted.add(CHERRY_LEAVES_BLOCK.get().asItem());
        tintedPlantsThatCanBePotted.add(DARK_OAK_LEAVES_BLOCK.get().asItem());
        tintedPlantsThatCanBePotted.add(FLOWERING_AZALEA_LEAVES_BLOCK.get().asItem());
        tintedPlantsThatCanBePotted.add(JUNGLE_LEAVES_BLOCK.get().asItem());
        tintedPlantsThatCanBePotted.add(MANGROVE_LEAVES_BLOCK.get().asItem());
        tintedPlantsThatCanBePotted.add(OAK_LEAVES_BLOCK.get().asItem());
        tintedPlantsThatCanBePotted.add(SPRUCE_LEAVES_BLOCK.get().asItem());
    } // tintedPlantsThatCanBePotted

    static {
        notTintedPlantsThatCanBePotted.add(GRASS.asItem());
        notTintedPlantsThatCanBePotted.add(FERN.asItem());
        notTintedPlantsThatCanBePotted.add(SEAGRASS.asItem());
        notTintedPlantsThatCanBePotted.add(BAMBOO.asItem());
        notTintedPlantsThatCanBePotted.add(SUGAR_CANE.asItem());
        notTintedPlantsThatCanBePotted.add(BIG_DRIPLEAF.asItem());
        notTintedPlantsThatCanBePotted.add(SMALL_DRIPLEAF.asItem());
        notTintedPlantsThatCanBePotted.add(KELP.asItem());
        notTintedPlantsThatCanBePotted.add(CACTUS.asItem());
        notTintedPlantsThatCanBePotted.add(VINE.asItem());
        notTintedPlantsThatCanBePotted.add(ACACIA_LEAVES.asItem());
        notTintedPlantsThatCanBePotted.add(AZALEA_LEAVES.asItem());
        notTintedPlantsThatCanBePotted.add(BIRCH_LEAVES.asItem());
        notTintedPlantsThatCanBePotted.add(CHERRY_LEAVES.asItem());
        notTintedPlantsThatCanBePotted.add(DARK_OAK_LEAVES.asItem());
        notTintedPlantsThatCanBePotted.add(FLOWERING_AZALEA_LEAVES.asItem());
        notTintedPlantsThatCanBePotted.add(JUNGLE_LEAVES.asItem());
        notTintedPlantsThatCanBePotted.add(MANGROVE_LEAVES.asItem());
        notTintedPlantsThatCanBePotted.add(OAK_LEAVES.asItem());
        notTintedPlantsThatCanBePotted.add(SPRUCE_LEAVES.asItem());
        notTintedPlantsThatCanBePotted.add(POPPY.asItem());
        notTintedPlantsThatCanBePotted.add(BLUE_ORCHID.asItem());
        notTintedPlantsThatCanBePotted.add(ALLIUM.asItem());
        notTintedPlantsThatCanBePotted.add(AZURE_BLUET.asItem());
        notTintedPlantsThatCanBePotted.add(RED_TULIP.asItem());
        notTintedPlantsThatCanBePotted.add(ORANGE_TULIP.asItem());
        notTintedPlantsThatCanBePotted.add(WHITE_TULIP.asItem());
        notTintedPlantsThatCanBePotted.add(PINK_TULIP.asItem());
        notTintedPlantsThatCanBePotted.add(OXEYE_DAISY.asItem());
        notTintedPlantsThatCanBePotted.add(DANDELION.asItem());
        notTintedPlantsThatCanBePotted.add(OAK_SAPLING.asItem());
        notTintedPlantsThatCanBePotted.add(SPRUCE_SAPLING.asItem());
        notTintedPlantsThatCanBePotted.add(BIRCH_SAPLING.asItem());
        notTintedPlantsThatCanBePotted.add(JUNGLE_SAPLING.asItem());
        notTintedPlantsThatCanBePotted.add(ACACIA_SAPLING.asItem());
        notTintedPlantsThatCanBePotted.add(DARK_OAK_SAPLING.asItem());
        notTintedPlantsThatCanBePotted.add(RED_MUSHROOM.asItem());
        notTintedPlantsThatCanBePotted.add(BROWN_MUSHROOM.asItem());
        notTintedPlantsThatCanBePotted.add(DEAD_BUSH.asItem());
        notTintedPlantsThatCanBePotted.add(CORNFLOWER.asItem());
        notTintedPlantsThatCanBePotted.add(LILY_OF_THE_VALLEY.asItem());
        notTintedPlantsThatCanBePotted.add(WITHER_ROSE.asItem());
        notTintedPlantsThatCanBePotted.add(CRIMSON_FUNGUS.asItem());
        notTintedPlantsThatCanBePotted.add(WARPED_FUNGUS.asItem());
        notTintedPlantsThatCanBePotted.add(CRIMSON_ROOTS.asItem());
        notTintedPlantsThatCanBePotted.add(WARPED_ROOTS.asItem());
        notTintedPlantsThatCanBePotted.add(AZALEA.asItem());
        notTintedPlantsThatCanBePotted.add(FLOWERING_AZALEA.asItem());
        notTintedPlantsThatCanBePotted.add(MANGROVE_PROPAGULE.asItem());
        notTintedPlantsThatCanBePotted.add(CHERRY_SAPLING.asItem());
        notTintedPlantsThatCanBePotted.add(TORCHFLOWER.asItem());
    } // notTintedPlantsThatCanBePotted

    static {

        for (RegistryObject<Block> plant : plantRegistryBlockList)
            standardTintedPlantsList.add(plant.get());

        for (RegistryObject<Block> plant : pottedTintedRegistryPlantList)
            standardTintedPlantsList.add(plant.get());

        for (Map.Entry<Block, Block> plantInBars : matchingBarsWithPlant.entrySet())
            standardTintedPlantsList.add(plantInBars.getKey());

        if (isBOPLoaded){
            for (RegistryObject<Block> plant : tintedBOPplantRegistryBlockList) {
                standardTintedPlantsList.add(plant.get());
            }
        }


    } //standardTintedPlantsList

    static {
      sugarCaneTintedPlantsList.add(SUGAR_CANE_TINTED.get());
      sugarCaneTintedPlantsList.add(SUGAR_CANE_POTTED_TINTED.get());
    } //sugarCaneTintedPlantsList

    static {
        stagePlants.add(CACTUS);
        stagePlants.add(CACTUS_TINTED.get());
        stagePlants.add(SUGAR_CANE);
        stagePlants.add(SUGAR_CANE_TINTED.get());
    } // stagePlants

    static {
        chestLootLocations.add("abandoned_mineshaft");
        chestLootLocations.add("ancient_city");
        chestLootLocations.add("ancient_city_ice_box");
        chestLootLocations.add("bastion_bridge");
        chestLootLocations.add("bastion_hoglin_stable");
        chestLootLocations.add("bastion_other");
        chestLootLocations.add("bastion_treasure");
        chestLootLocations.add("buried_treasure");
        chestLootLocations.add("desert_pyramid");
        chestLootLocations.add("end_city_treasure");
        chestLootLocations.add("igloo_chest");
        chestLootLocations.add("jungle_temple");
        chestLootLocations.add("jungle_temple_dispenser");
        chestLootLocations.add("nether_bridge");
        chestLootLocations.add("pillager_outpost");
        chestLootLocations.add("ruined_portal");
        chestLootLocations.add("shipwreck_map");
        chestLootLocations.add("shipwreck_supply");
        chestLootLocations.add("shipwreck_treasure");
        chestLootLocations.add("simple_dungeon");
        chestLootLocations.add("spawn_bonus_chest");
        chestLootLocations.add("stronghold_corridor");
        chestLootLocations.add("stronghold_crossing");
        chestLootLocations.add("stronghold_library");
        chestLootLocations.add("underwater_ruin_big");
        chestLootLocations.add("underwater_ruin_small");
        chestLootLocations.add("woodland_mansion");


    } // chestLootLocations
}
