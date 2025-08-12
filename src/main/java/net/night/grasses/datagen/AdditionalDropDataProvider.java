package net.night.grasses.datagen;

import biomesoplenty.api.item.BOPItems;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;

//Generates additional_drops.toml file in run-data/config/grasses/ when runData
//It is then copied to src/generated(...) when 'build' runs.
//And finally, when the game is launched, it copies this file to the player's config folder (via 'ConfigFileHelper'), where it is read by the game and can be changed.
public class AdditionalDropDataProvider implements DataProvider {

    private final PackOutput packOutput;

    public static final String NORMAL_LEAVES_SAPLING_CHANCES = "normal_leaves_sapling_chances";
    public static final String HALF_LEAVES_SAPLING_CHANCES = "half_leaves_sapling_chances";
    public static final String A_THIRD_LEAVES_SAPLING_CHANCES = "a_third_leaves_sapling_chances";
    public static final String FULL_CHANCES = "full_chances";

    public AdditionalDropDataProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
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
                            "# Drop type 'ALL' - each item has a declared chance of being dropped (can drop all at once)\n" +
                            "# Drop type 'ONE OF' - each item has a declared chance of being dropped (can drop randomly only one of them)\n" +
                            "# 'chances' - chance set name\n" +
                            "# 'conditions' - (optional) BlockState properties terms\n" +
                            "# Detailed information about the system on the wiki on gitHub:'\n" +
                            "# https://github.com/DanteTooMayCry/Grasses/wiki/Additional-Drop-System'\n" +
                            "#######################################################################");

            config.setComment("all", "Drop type 'ALL' - each item has a declared chance of being dropped (can drop all at once)");

            Map<String, Object> variantBOPLily = Map.of(VARIANT_LILY.getName(), 2);

            putDropEntries(config, "all", FIR_LEAVES_BLOCK.get(), List.of(BOPItems.FIR_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", PINE_LEAVES_BLOCK.get(), List.of(BOPItems.PINE_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", REDWOOD_LEAVES_BLOCK.get(), List.of(BOPItems.REDWOOD_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", MAHOGANY_LEAVES_BLOCK.get(), List.of(BOPItems.MAHOGANY_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", JACARANDA_LEAVES_BLOCK.get(), List.of(BOPItems.JACARANDA_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", PALM_LEAVES_BLOCK.get(), List.of(BOPItems.PALM_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", WILLOW_LEAVES_BLOCK.get(), List.of(BOPItems.WILLOW_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", DEAD_LEAVES_BLOCK.get(), List.of(BOPItems.DEAD_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", MAGIC_LEAVES_BLOCK.get(), List.of(BOPItems.MAGIC_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", UMBRAN_LEAVES_BLOCK.get(), List.of(BOPItems.UMBRAN_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", EMPYREAL_LEAVES_BLOCK.get(), List.of(BOPItems.EMPYREAL_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", CHERRY_LEAVES_BLOCK.get(), List.of(BOPItems.SNOWBLOSSOM_SAPLING), HALF_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", FLOWERING_OAK_LEAVES_BLOCK.get(), List.of(BOPItems.FLOWERING_OAK_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", ORIGIN_LEAVES_BLOCK.get(), List.of(BOPItems.ORIGIN_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", CYPRESS_LEAVES_BLOCK.get(), List.of(BOPItems.CYPRESS_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", HELLBARK_LEAVES_BLOCK.get(), List.of(BOPItems.HELLBARK_SAPLING), NORMAL_LEAVES_SAPLING_CHANCES, null);
            putDropEntries(config, "all", HUGE_LILY_PAD_TINTED.get(), List.of(BOPItems.WATERLILY), FULL_CHANCES, variantBOPLily);

            //if not have BLOCKS reference, can make by string:
            //putDropEntries(config, "all", "grasses:fir_leaves_block", List.of("biomesoplenty:fir_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, null);


            //======================================================================================================================================================
            config.setComment("one_of", "Drop type 'ONE OF' - each item has a declared chance of being dropped (can drop randomly only one of them)");

            putDropEntries(config, "one_of", MAPLE_LEAVES_BLOCK.get(),
                    List.of(BOPItems.RED_MAPLE_SAPLING, BOPItems.ORANGE_MAPLE_SAPLING, BOPItems.YELLOW_MAPLE_SAPLING), A_THIRD_LEAVES_SAPLING_CHANCES, null);

            //if not have BLOCKS reference, can make by string:
            //putDropEntries(config, "one_of", "grasses:maple_leaves_block",
            //        List.of("biomesoplenty:red_maple_sapling", "biomesoplenty:orange_maple_sapling", "biomesoplenty:yellow_maple_sapling"), A_THIRD_LEAVES_SAPLING_CHANCES, null);

            config.save();

        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public String getName() {
        return "Grasses Additional Drops Config Generator";
    }

    private static void putDropEntries(CommentedFileConfig config, String dropType, Object blockOrString, List<?> items, String chancesKey, Map<String, Object> conditions) {

        if (blockOrString == null || items == null || items.isEmpty())
            return;

        String blockKey;
        if (blockOrString instanceof Block block)
            blockKey = getRegistryName(block);
        else if (blockOrString instanceof String blockString)
            blockKey = blockString;
        else
            return;

        List<String> itemStrings = new ArrayList<>();
        if (!items.isEmpty()) {
            Object first = items.get(0);
            if (first instanceof Item) {
                for (Object itemObj : items) {
                    Item item = (Item) itemObj;
                    if (item != null)
                        itemStrings.add(getRegistryName(item));
                }
                if (itemStrings.isEmpty())
                    return;
            } else if (first instanceof String) {
                for (Object itemObj : items) {
                    if (!(itemObj instanceof String))
                        return;
                    itemStrings.add((String) itemObj);
                }
            } else
                return;
        } else
            return;

        String sectionKey = dropType + "." + blockKey;

        config.set(sectionKey + ".items", itemStrings);
        config.set(sectionKey + ".chances", chancesKey);

        if (conditions != null && !conditions.isEmpty())
            config.set(sectionKey + ".conditions", flattenConditions(conditions));
    }

    private static String getRegistryName(Block block) {
        if (block == null)
            return "null_block";

        ResourceLocation resourceLocation = ForgeRegistries.BLOCKS.getKey(block);
        if (resourceLocation == null)
            return "unknown_block";
        return resourceLocation.toString();
    }

    private static String getRegistryName(Item item) {
        if (item == null)
            return "null_item";

        ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(item);
        if (resourceLocation == null)
            return "unknown_item";

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
}