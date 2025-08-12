package net.night.grasses.config.additionalDropSystem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.night.grasses.datagen.AdditionalDropDataProvider;
import net.night.grasses.datagen.loot.ModBlockLootTables;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

//A TOML file parser that allows to define drops for blocks with optional conditions on the BlockState property (properties)
public class TOMLParser {

    private static final Map<String, float[]> CHANCES_MAP = Map.of(
            AdditionalDropDataProvider.NORMAL_LEAVES_SAPLING_CHANCES, ModBlockLootTables.NORMAL_LEAVES_SAPLING_CHANCES,
            AdditionalDropDataProvider.HALF_LEAVES_SAPLING_CHANCES, ModBlockLootTables.HALF_LEAVES_SAPLING_CHANCES,
            AdditionalDropDataProvider.A_THIRD_LEAVES_SAPLING_CHANCES, ModBlockLootTables.A_THIRD_LEAVES_SAPLING_CHANCES,
            AdditionalDropDataProvider.FULL_CHANCES, ModBlockLootTables.FULL_CHANCES
    );

    public static Map<BlockCondition, AdditionalDropConfig.DropGroup> parseConfig(Path tomlFile) throws IOException {

        Map<BlockCondition, AdditionalDropConfig.DropGroup> dropMap = new LinkedHashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(tomlFile)) {
            String line;
            String currentSection = null;
            List<String> currentItems = null;
            String currentChancesKey = null;
            Map<String, Object> currentConditions = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#") || line.startsWith("//"))
                    continue;

                if (line.startsWith("[") && line.endsWith("]")) {
                    if (currentSection != null && currentItems != null && currentChancesKey != null)
                        saveDropGroup(dropMap, currentSection, currentItems, currentChancesKey, currentConditions);

                    currentSection = line.substring(1, line.length() - 1).trim();
                    currentItems = null;
                    currentChancesKey = null;
                    currentConditions = null;
                } else if (line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    if (parts.length < 2)
                        continue;

                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    switch (key) {
                        case "items":
                            currentItems = parseItemsList(value);
                            break;
                        case "chances":
                            currentChancesKey = removeQuotes(value);
                            break;
                        case "conditions":
                            currentConditions = parseConditions(value);
                            break;
                        default:
                            break;
                    }
                }
            }
            if (currentSection != null && currentItems != null && currentChancesKey != null)
                saveDropGroup(dropMap, currentSection, currentItems, currentChancesKey, currentConditions);
        }

        return dropMap;
    }

    private static void saveDropGroup(Map<BlockCondition, AdditionalDropConfig.DropGroup> dropMap, String section, List<String> items, String chancesKey,
                                      Map<String, Object> conditionsMap) {

        String[] parts = section.split("\\.", 2);
        if (parts.length < 2)
            return;

        String type = parts[0];
        String blockKey = parts[1].trim();

        if (blockKey.startsWith("\"") && blockKey.endsWith("\""))
            blockKey = blockKey.substring(1, blockKey.length() - 1);

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockKey));
        if (block == null)
            return;

        float[] chances = CHANCES_MAP.getOrDefault(chancesKey, ModBlockLootTables.NORMAL_LEAVES_SAPLING_CHANCES);

        BlockCondition blockCondition = new BlockCondition(block, conditionsMap);

        AdditionalDropConfig.DropGroup dropGroup = dropMap.computeIfAbsent(blockCondition, bc -> new AdditionalDropConfig.DropGroup());

        for (String itemId : items) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId));
            if (item != null) {
                AdditionalDropConfig.DropEntry entry = new AdditionalDropConfig.DropEntry(item, chances);

                if ("all".equals(type))
                    dropGroup.all.add(entry);
                else if ("one_of".equals(type))
                    dropGroup.oneOf.add(entry);
            }
        }
    }

    private static List<String> parseItemsList(String value) {
        value = value.trim();
        if (value.startsWith("[") && value.endsWith("]"))
            value = value.substring(1, value.length() - 1);

        List<String> items = new ArrayList<>();

        for (String part : value.split(",")) {
            String item = removeQuotes(part.trim());
            if (!item.isEmpty())
                items.add(item);
        }
        return items;
    }

    private static Map<String, Object> parseConditions(String rawValue) {
        Map<String, Object> map = new HashMap<>();
        rawValue = rawValue.trim();

        if (rawValue.length() < 2)
            return map;

        if (rawValue.startsWith("{") && rawValue.endsWith("}")) {
            String content = trimBraces(rawValue);

            if (!content.isEmpty()) {
                String[] pairs = content.split(",(\\s)*");
                for (String pair : pairs) {
                    String[] kv = pair.split("=", 2);
                    if (kv.length != 2) continue;

                    String key = kv[0].trim();
                    String val = kv[1].trim();

                    if ((key.startsWith("\"") && key.endsWith("\"")) || (key.startsWith("'") && key.endsWith("'")))
                        key = key.substring(1, key.length() - 1);

                    Object valueParsed = parseTOMLValue(val);
                    map.put(key, valueParsed);
                }
            }
        } else if (rawValue.startsWith("[") && rawValue.endsWith("]")) {
            String listContent = trimBraces(rawValue);

            if (!listContent.isEmpty()) {
                String[] items = listContent.split(",(\\s)*");
                for (String item : items) {
                    item = removeQuotes(item.trim());
                    int eqIndex = item.indexOf('=');
                    if (eqIndex <= 0) continue;

                    String key = item.substring(0, eqIndex).trim();
                    String val = item.substring(eqIndex + 1).trim();

                    Object valueParsed = parseTOMLValue(val);
                    map.put(key, valueParsed);
                }
            }
        }

        return map;
    }

    private static String trimBraces(String rawValue) {
        if (rawValue.length() < 2)
            return "";

        return rawValue.substring(1, rawValue.length() - 1).trim();
    }

    private static Object parseTOMLValue(String str) {
        str = str.trim();

        // boolean
        if ("true".equalsIgnoreCase(str))
            return true;
        if ("false".equalsIgnoreCase(str))
            return false;

        // integer
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ignored) {
        }

        if ((str.startsWith("\"") && str.endsWith("\"")) || (str.startsWith("'") && str.endsWith("'")))
            return str.substring(1, str.length() - 1);

        return str;
    }

    private static String removeQuotes(String str) {
        str = str.trim();

        if ((str.startsWith("\"") && str.endsWith("\"")) || (str.startsWith("'") && str.endsWith("'")))
            return str.substring(1, str.length() - 1);

        return str;
    }
}
