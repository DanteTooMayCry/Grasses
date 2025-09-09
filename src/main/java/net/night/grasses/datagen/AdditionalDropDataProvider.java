package net.night.grasses.datagen;

import biomesoplenty.api.item.BOPItems;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.night.grasses.enums.DropType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static net.night.grasses.data.ModMethods.generateUniqueSuffix;
import static net.night.grasses.init.BlocksRegister.CHERRY_LEAVES_BLOCK;
import static net.night.grasses.init.BlocksRegisterBoP.*;

//Generates additional_drops.toml file in run-data/config/grasses/ when runData
//It is then copied to src/generated(...) when 'build' or runClient runs.
//And finally, when the game is launched, it copies this file to the player's config folder (via 'ConfigFileHelper'), where it is read by the game and can be changed.
public class AdditionalDropDataProvider implements DataProvider {

    private final PackOutput packOutput;

    public static final String NORMAL_LEAVES_SAPLING_CHANCES = "normal_leaves_sapling_chances";
    public static final String HALF_LEAVES_SAPLING_CHANCES = "half_leaves_sapling_chances";
    public static final String A_THIRD_LEAVES_SAPLING_CHANCES = "a_third_leaves_sapling_chances";
    public static final String FULL_CHANCES = "full_chances";
    private static final String addAdditionalDropMode = "addAdditionalDropMode";

    public AdditionalDropDataProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        String destroy = "DESTROY";
        String mine = "MINE";
        String both = "BOTH";
        String none = "NONE";

        Path projectRoot = Paths.get("").toAbsolutePath().normalize();
        Path outputPath = projectRoot.resolve("config/grasses/additional_drops.toml");

        try {
            Files.createDirectories(outputPath.getParent());
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directories for " + outputPath, e);
        }

        try (CommentedFileConfig config = CommentedFileConfig.builder(outputPath)
                .writingMode(WritingMode.REPLACE)
                .build()) {

            config.clear();

            config.set("header_marker", "");
            config.setComment("header_marker",
                    "#######################################################################\n" +
                            "# Grasses mod additional drops config\n" +
                            "# Drop Type 'ALL' - each item has a declared chance of being dropped (can drop all at once)\n" +
                            "# Drop Type 'ONE OF' - each item has a declared chance of being dropped (can drop randomly only one of them)\n" +
                            "# 'chances' - chance set name or values \n" +
                            "# 'conditions' - (optional) BlockState or mobs properties terms\n" +
                            "# Detailed information about the system on the wiki on gitHub:'\n" +
                            "# https://github.com/DanteTooMayCry/Grasses/wiki/Additional-Drop-System'\n" +
                            "#######################################################################");

            //======================================================================================================================================================
            config.setComment("all", "Drop Type 'ALL' - each item has a declared chance of being dropped (can drop all at once)");

            Map<String, Object> variantBOPLily = Map.of(VARIANT_LILY.getName(), 2, addAdditionalDropMode, both);

            putDropEntries(config, "all", FIR_LEAVES_BLOCK.get(), List.of(BOPItems.FIR_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", PINE_LEAVES_BLOCK.get(), List.of(BOPItems.PINE_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", REDWOOD_LEAVES_BLOCK.get(), List.of(BOPItems.REDWOOD_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", MAHOGANY_LEAVES_BLOCK.get(), List.of(BOPItems.MAHOGANY_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", JACARANDA_LEAVES_BLOCK.get(), List.of(BOPItems.JACARANDA_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", PALM_LEAVES_BLOCK.get(), List.of(BOPItems.PALM_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", WILLOW_LEAVES_BLOCK.get(), List.of(BOPItems.WILLOW_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", DEAD_LEAVES_BLOCK.get(), List.of(BOPItems.DEAD_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", MAGIC_LEAVES_BLOCK.get(), List.of(BOPItems.MAGIC_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", UMBRAN_LEAVES_BLOCK.get(), List.of(BOPItems.UMBRAN_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", EMPYREAL_LEAVES_BLOCK.get(), List.of(BOPItems.EMPYREAL_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", CHERRY_LEAVES_BLOCK.get(), List.of(BOPItems.SNOWBLOSSOM_SAPLING), HALF_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", FLOWERING_OAK_LEAVES_BLOCK.get(), List.of(BOPItems.FLOWERING_OAK_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", ORIGIN_LEAVES_BLOCK.get(), List.of(BOPItems.ORIGIN_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", CYPRESS_LEAVES_BLOCK.get(), List.of(BOPItems.CYPRESS_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", HELLBARK_LEAVES_BLOCK.get(), List.of(BOPItems.HELLBARK_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, addDropWhen(destroy), clearWhen(none));
            putDropEntries(config, "all", HUGE_LILY_PAD_TINTED.get(), List.of(BOPItems.WATERLILY), FULL_CHANCES, variantBOPLily, clearWhen(none));

            //if not have BLOCKS reference, can make by string:
            //putDropEntries(config, "all", "grasses:fir_leaves_block", List.of("biomesoplenty:fir_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, null, false);

            //======================================================================================================================================================
            config.setComment("one_of", "Drop Type 'ONE OF' - each item has a declared chance of being dropped (can drop randomly only one of them)");

            putDropEntries(config, "one_of", MAPLE_LEAVES_BLOCK.get(), List.of(BOPItems.RED_MAPLE_SAPLING, BOPItems.ORANGE_MAPLE_SAPLING, BOPItems.YELLOW_MAPLE_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null, clearWhen(none));

            //if not have BLOCKS reference, can make by string:
            //putDropEntries(config, "one_of", "grasses:maple_leaves_block", List.of("biomesoplenty:red_maple_sapling", "biomesoplenty:orange_maple_sapling", "biomesoplenty:yellow_maple_sapling"), A_THIRD_LEAVES_SAPLING_CHANCES, null, false);

            //same for mobs:
            //all_mob examples:
            //putDropEntries(config, "all_mob", "minecraft:zombie", List.of(Items.NETHER_STAR, Items.CAKE), FULL_CHANCES, addDropWhen(destroy, IS_BABY, true), clearWhen(destroy)); //Testowe
            //putDropEntries(config, "all_mob", "minecraft:zombie", List.of(Items.ICE, Items.SAND), FULL_CHANCES, Map.of(addAdditionalDropMode, destroy,"customName", "Boss"), clearWhen(none)); //Testowe
            //putDropEntries(config, "all_mob", EntityType.COW, List.of(Items.ROTTEN_FLESH, Items.IRON_INGOT), FULL_CHANCES, Map.of(addAdditionalDropMode, destroy, CUSTOM_NAME, "Boss"), clearWhen(none)); //Testowe

            //one_of_mob examples
            //putDropEntries(config, "one_of_mob", EntityType.PIG, List.of(Items.DIAMOND_PICKAXE, Items.GOLD_NUGGET), FULL_CHANCES, Map.of(addAdditionalDropMode, destroy, "customName", "Boss", IN_WATER, true), clearWhen(none)); //Testowe
            //or:
            //putDropEntries(config, "one_of_mob", EntityType.PIG, List.of(Items.DIAMOND_PICKAXE, Items.GOLD_NUGGET), FULL_CHANCES, addDropWhen(destroy, "customName", "Boss", IN_WATER, true), clearWhen(none)); //Testowe

            config.save();

        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public String getName() {
        return "Grasses Additional Drops Config Generator";
    }

    private static void putDropEntries(CommentedFileConfig config, String dropType, Object blockOrString, List<?> itemslist, String chancesKey, Map<String, Object> conditions, DropType clearMode) {
        if (blockOrString == null || itemslist == null || itemslist.isEmpty())
            return;

        String blockKey;
        if (blockOrString instanceof Block block) {
            blockKey = getRegistryName(block);
        } else if (blockOrString instanceof String blockString) {
            blockKey = blockString;
        } else if (blockOrString instanceof EntityType<?> entityType) {
            ResourceLocation resourceLocation = BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
            blockKey = resourceLocation.toString();
        } else {
            return;
        }

        List<String> itemStrings = new ArrayList<>();
        if (!itemslist.isEmpty()) {
            Object first = itemslist.get(0);
            if (first instanceof Item) {
                for (Object itemObj : itemslist) {
                    Item item = (Item) itemObj;
                    if (item != null)
                        itemStrings.add(getRegistryName(item));
                }
                if (itemStrings.isEmpty())
                    return;
            } else if (first instanceof String) {
                for (Object itemObj : itemslist) {
                    if (!(itemObj instanceof String))
                        return;
                    itemStrings.add((String) itemObj);
                }
            } else
                return;
        } else
            return;

        String baseSection = dropType + "." + blockKey;
        String uniqueSuffix = generateUniqueSuffix(itemStrings, conditions);
        String sectionKey = baseSection + "_" + uniqueSuffix;

        config.set(sectionKey + ".items", itemStrings);
        config.set(sectionKey + ".chances", chancesKey);

        if (conditions != null && !conditions.isEmpty())
            config.set(sectionKey + ".conditions", flattenConditions(conditions));

        if (clearMode != null && clearMode != DropType.NONE)
            config.set(sectionKey + ".clearOriginalDropsMode", clearMode.toString());
    }

    private static String getRegistryName(Block block) {
        if (block == null)
            return "null_block";

        ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourceLocation.toString();
    }

    private static String getRegistryName(Item item) {
        if (item == null)
            return "null_item";

        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item);
        return resourceLocation.toString();
    }

    private static List<String> flattenConditions(Map<String, Object> conditions) {
        List<String> result = new ArrayList<>();

        if (conditions == null)
            return result;

        for (Map.Entry<String, Object> entry : conditions.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String valStr = (value == null) ? "null" : value.toString();
            result.add(key + "=" + valStr);
        }
        return result;
    }


    //Some Predefined conditions:

    // Whether the mob is a young (baby) entity
    public static final String IS_BABY = "isBaby";

    // Custom name assigned to the mob
    public static final String CUSTOM_NAME = "customName";

    // Whether the mob has the "persistent" flag set (won't despawn)
    public static final String IS_PERSISTENT = "isPersistent";

    // Whether the mob is silent (does not make sounds)
    public static final String IS_SILENT = "isSilent";

    // Whether the mob is currently on fire
    public static final String IS_ON_FIRE = "isOnFire";

    // Whether the mob has AI disabled
    public static final String IS_NO_AI = "isNoAI";

    // The current health of the mob (float)
    public static final String HEALTH = "health";

    // The maximum health of the mob (float)
    public static final String MAX_HEALTH = "maxHealth";

    // Whether the mob is in water
    public static final String IN_WATER = "inWater";

    // Whether the mob is invisible
    public static final String IS_INVISIBLE = "isInvisible";

    // Whether the mob is leashed
    public static final String IS_LEASHED = "isLeashed";

    // Whether the mob is glowing (has glow effect)
    public static final String IS_GLOWING = "isGlowing";

    private static DropType clearWhen(String clearMode) {

        return switch (clearMode) {
            case "DESTROY" -> DropType.DESTROY;
            case "MINE" -> DropType.MINE;
            case "BOTH" -> DropType.BOTH;
            default -> DropType.NONE;
        };
    }

    private static Map<String, Object> addDropWhen(String addDropMode) {
        return Map.of(addAdditionalDropMode, addDropMode);
    }

    private static Map<String, Object> addDropWhen(String addDropMode, Object... additionalConditions) {
        Map<String, Object> map = new HashMap<>();
        map.put(addAdditionalDropMode, addDropMode);

        if (additionalConditions != null) {
            for (int i = 0; i < additionalConditions.length - 1; i += 2) {
                Object key = additionalConditions[i];
                Object value = additionalConditions[i + 1];
                if (key instanceof String) {
                    map.put((String) key, value);
                }
            }
        }

        return map;
    }
}