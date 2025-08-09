package net.night.grasses.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import java.io.File;

public class AdditionalDropConfigGenerator {

    public static final float[] MAPLE_LEAVES_SAPLING_CHANCES = new float[]{0.05F / 3f, 0.0625F / 3f, 0.083333336F / 3f, 0.1F / 3f};
    public static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    public static final float[] HALF_LEAVES_SAPLING_CHANCES = new float[]{0.05F/2, 0.0625F/2, 0.083333336F/2, 0.1F/2};

    public static void main(String[] args) {
        Map<String, Object> config = new LinkedHashMap<>();

        config.put("grasses:fir_leaves_block", prepareDrops(List.of("biomesoplenty:fir_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:pine_leaves_block", prepareDrops(List.of("biomesoplenty:pine_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:maple_leaves_block", prepareDrops(Collections.emptyList(), null, List.of("biomesoplenty:red_maple_sapling", "biomesoplenty:orange_maple_sapling", "biomesoplenty:yellow_maple_sapling"), MAPLE_LEAVES_SAPLING_CHANCES));
        config.put("grasses:redwood_leaves_block", prepareDrops(List.of("biomesoplenty:redwood_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:mahogany_leaves_block", prepareDrops(List.of("biomesoplenty:mahogany_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:jacaranda_leaves_block", prepareDrops(List.of("biomesoplenty:jacaranda_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:palm_leaves_block", prepareDrops(List.of("biomesoplenty:palm_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:willow_leaves_block", prepareDrops(List.of("biomesoplenty:willow_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:dead_leaves_block", prepareDrops(List.of("biomesoplenty:dead_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:magic_leaves_block", prepareDrops(List.of("biomesoplenty:magic_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:umbran_leaves_block", prepareDrops(List.of("biomesoplenty:umbran_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:empyreal_leaves_block", prepareDrops(List.of("biomesoplenty:empyreal_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:birch_leaves_block", prepareDrops(List.of("biomesoplenty:empyreal_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:cherry_leaves_block", prepareDrops(List.of("biomesoplenty:snowblossom_sapling"), HALF_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:flowering_oak_leaves_block", prepareDrops(List.of("biomesoplenty:flowering_oak_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:origin_leaves_block", prepareDrops(List.of("biomesoplenty:origin_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:cypress_leaves_block", prepareDrops(List.of("biomesoplenty:cypress_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));
        config.put("grasses:hellbark_leaves_block", prepareDrops(List.of("biomesoplenty:hellbark_sapling"), NORMAL_LEAVES_SAPLING_CHANCES, Collections.emptyList(), null));


        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String outputPath = "run/config/grasses/additional_drops.json";

        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();

        if (!parentDir.exists()) {
            boolean success = parentDir.mkdirs();
            if (!success)
                return;
        }

        try (FileWriter writer = new FileWriter(outputFile)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> prepareDrops(List<String> allItems, float[] allChances, List<String> oneOfItems, float[] oneOfChances) {

        Map<String, Object> dropMap = new LinkedHashMap<>();

        List<Map<String, Object>> allDrops = new ArrayList<>();
        if (allItems != null && allChances != null) {
            for (String item : allItems) {
                allDrops.add(Map.of(
                        "item", item,
                        "fortune_chances", allChances
                ));
            }
        }
        dropMap.put("all", allDrops);

        List<Map<String, Object>> oneOfDrops = new ArrayList<>();
        if (oneOfItems != null && oneOfChances != null) {
            for (String item : oneOfItems) {
                oneOfDrops.add(Map.of(
                        "item", item,
                        "fortune_chances", oneOfChances
                ));
            }
        }
        dropMap.put("one_of", oneOfDrops);

        return dropMap;
    }
}