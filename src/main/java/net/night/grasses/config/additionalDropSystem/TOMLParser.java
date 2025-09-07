package net.night.grasses.config.additionalDropSystem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.night.grasses.datagen.AdditionalDropDataProvider;
import net.night.grasses.datagen.loot.ModBlockLootTables;
import net.night.grasses.enums.DropType;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TOMLParser {

    private static final Map<String, float[]> CHANCES_MAP = Map.of(
            AdditionalDropDataProvider.NORMAL_LEAVES_SAPLING_CHANCES, ModBlockLootTables.NORMAL_LEAVES_SAPLING_CHANCES,
            AdditionalDropDataProvider.HALF_LEAVES_SAPLING_CHANCES, ModBlockLootTables.HALF_LEAVES_SAPLING_CHANCES,
            AdditionalDropDataProvider.A_THIRD_LEAVES_SAPLING_CHANCES, ModBlockLootTables.A_THIRD_LEAVES_SAPLING_CHANCES,
            AdditionalDropDataProvider.FULL_CHANCES, ModBlockLootTables.FULL_CHANCES
    );

    public static ConfigDrops parseConfig(Path tomlFile) throws IOException {

        Map<BlockCondition, AdditionalDropConfig.DropGroup> dropMap = new LinkedHashMap<>();
        Map<MobCondition, AdditionalDropConfig.DropGroup> mobDropMap = new LinkedHashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(tomlFile)) {
            String line;
            String currentSection = null;
            List<String> currentItems = null;
            Object currentChancesKeyOrList = null;
            Map<String, Object> currentConditions = null;
            DropType currentClearOriginalDropsMode = DropType.NONE;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#") || line.startsWith("//"))
                    continue;

                if (line.startsWith("[") && line.endsWith("]")) {
                    if (currentSection != null && currentItems != null && currentChancesKeyOrList != null) {
                        boolean isMobSection = currentSection.startsWith("all_mob.") || currentSection.startsWith("one_of_mob.");

                        saveDropGroupUnified(
                                isMobSection ? mobDropMap : dropMap,
                                currentSection,
                                currentItems,
                                currentChancesKeyOrList,
                                currentConditions,
                                currentClearOriginalDropsMode,
                                isMobSection);
                    }

                    currentSection = line.substring(1, line.length() - 1).trim();
                    currentItems = null;
                    currentChancesKeyOrList = null;
                    currentConditions = null;
                    currentClearOriginalDropsMode = DropType.NONE;
                } else if (line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    if (parts.length < 2)
                        continue;

                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    switch (key) {
                        case "items" -> currentItems = parseItemsList(value);
                        case "chances" -> currentChancesKeyOrList = parseChancesValue(value);
                        case "conditions" -> {
                            List<String> condList = parseConditionsAsListOfStrings(value);
                            if (currentConditions == null)
                                currentConditions = new HashMap<>();
                            currentConditions.put("conditions", condList);
                        }
                        case "clearOriginalDropsMode" -> currentClearOriginalDropsMode = parseDropClearMode(removeQuotes(value));

                        default -> {}
                    }
                }
            }

            if (currentSection != null && currentItems != null && currentChancesKeyOrList != null) {
                boolean isMobSection = currentSection.startsWith("all_mob.") || currentSection.startsWith("one_of_mob.");

                saveDropGroupUnified(
                        isMobSection ? mobDropMap : dropMap,
                        currentSection,
                        currentItems,
                        currentChancesKeyOrList,
                        currentConditions,
                        currentClearOriginalDropsMode,
                        isMobSection);
            }
        }

        return new ConfigDrops(dropMap, mobDropMap);
    }


    private static List<String> parseConditionsAsListOfStrings(String rawValue) {
        List<String> result = new ArrayList<>();
        rawValue = rawValue.trim();

        if (rawValue.startsWith("[") && rawValue.endsWith("]")) {
            String inner = rawValue.substring(1, rawValue.length() - 1).trim();

            if (!inner.isEmpty()) {
                String[] parts = inner.split(",(\\s)*");
                for (String part : parts) {
                    part = removeQuotes(part.trim());
                    if (!part.isEmpty())
                        result.add(part);
                }
            }
        }
        return result;
    }


    @SuppressWarnings("unchecked")
    private static void saveDropGroupUnified(Map<?, AdditionalDropConfig.DropGroup> map, String section, List<String> items,
                                             Object chancesKey, Map<String, Object> conditionsMap,
                                             DropType clearOriginalDropsMode, boolean isMob) {
        SectionParts parts = parseSection(section);
        if (parts == null)
            return;

        String type = parts.type;

        ResourceLocation resourceLocation = new ResourceLocation(parts.key);
        float[] chances = resolveChances(chancesKey);
        AdditionalDropConfig.DropGroup dropGroup;

        if (isMob) {
            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(resourceLocation);
            if (entityType == null)
                return;

            MobCondition mobCondition = new MobCondition(entityType, conditionsMap);
            dropGroup = ((Map<MobCondition, AdditionalDropConfig.DropGroup>) map).computeIfAbsent(mobCondition, mc -> new AdditionalDropConfig.ExtDropGroup());
        } else {
            Block block = ForgeRegistries.BLOCKS.getValue(resourceLocation);
            if (block == null)
                return;

            BlockCondition blockCondition = new BlockCondition(block, conditionsMap);
            dropGroup = ((Map<BlockCondition, AdditionalDropConfig.DropGroup>) map).computeIfAbsent(blockCondition, bc -> new AdditionalDropConfig.ExtDropGroup());
        }

        if (dropGroup instanceof AdditionalDropConfig.ExtDropGroup extDropGroup) {
            extDropGroup.setClearOriginalDropsMode(clearOriginalDropsMode);

            DropType addDropMode = DropType.BOTH;

            if (conditionsMap != null) {
                Object condObj = conditionsMap.get("conditions");

                if (condObj instanceof List<?> condList) {
                    for (Object condItem : condList) {
                        if (condItem instanceof String condStr) {
                            final String key = "addAdditionalDropMode=";
                            if (condStr.startsWith(key)) {
                                String setting = condStr.substring(key.length());
                                addDropMode = parseDropClearMode(setting);
                                break;
                            }
                        }
                    }
                }
            }
            extDropGroup.setAddAdditionalDropMode(addDropMode);
        }

        for (String itemId : items) {
            ResourceLocation itemResource = new ResourceLocation(itemId);
            Item item = ForgeRegistries.ITEMS.getValue(itemResource);
            if (item == null)
                continue;

            AdditionalDropConfig.DropEntry entry = new AdditionalDropConfig.DropEntry(item, chances);

            if (type.startsWith("all"))
                dropGroup.all.add(entry);
            else if (type.startsWith("one_of"))
                dropGroup.oneOf.add(entry);
        }
    }

    private static DropType parseDropClearMode(String clearMode) {
        if (clearMode == null)
            return DropType.NONE;
        try {
            return DropType.valueOf(clearMode.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            return DropType.NONE;
        }
    }

    private static SectionParts parseSection(String section) {
        String[] parts = section.split("\\.", 2);
        if (parts.length < 2)
            return null;

        String type = parts[0].trim();
        String key = parts[1].trim();

        if (key.startsWith("\"") && key.endsWith("\""))
            key = key.substring(1, key.length() - 1);

        int underscoreIndex = key.lastIndexOf('_');

        if (underscoreIndex > 0 && key.length() - underscoreIndex - 1 == 6)
            key = key.substring(0, underscoreIndex);

        return new SectionParts(type, key);
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

    private static String removeQuotes(String str) {
        str = str.trim();

        if ((str.startsWith("\"") && str.endsWith("\"")) || (str.startsWith("'") && str.endsWith("'")))
            return str.substring(1, str.length() - 1);

        return str;
    }

    private static float[] resolveChances(Object chancesObj) {
        if (chancesObj instanceof String chancesKey) {
            String keyLower = chancesKey.toLowerCase(Locale.ROOT);
            return CHANCES_MAP.getOrDefault(keyLower, ModBlockLootTables.FULL_CHANCES);
        } else if (chancesObj instanceof List<?> list) {
            float[] chancesArray = new float[list.size()];

            for (int i = 0; i < list.size(); i++) {
                Object val = list.get(i);
                if (val instanceof Number n)
                    chancesArray[i] = n.floatValue();
                else
                    try {
                        chancesArray[i] = Float.parseFloat(val.toString());
                    } catch (NumberFormatException e) {
                        chancesArray[i] = 0.0f;
                    }
            }
            return chancesArray;
        } else {
            return ModBlockLootTables.FULL_CHANCES;
        }
    }

    private static Object parseChancesValue(String rawValue) {
        rawValue = rawValue.trim();
        if (rawValue.startsWith("[") && rawValue.endsWith("]")) {
            String inner = rawValue.substring(1, rawValue.length() - 1).trim();
            if (inner.isEmpty())
                return List.of();

            String[] parts = inner.split(",\\s*");
            List<Float> floats = new ArrayList<>();
            for (String part : parts) {
                try {
                    floats.add(Float.parseFloat(part));
                } catch (NumberFormatException e) {
                    floats.add(0f);
                }
            }
            return floats;
        }
        return removeQuotes(rawValue);
    }


    private record SectionParts(String type, String key) {
    }

}
