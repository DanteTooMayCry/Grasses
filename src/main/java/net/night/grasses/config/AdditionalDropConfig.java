package net.night.grasses.config;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class AdditionalDropConfig {

    public record DropEntry(Item item, float[] fortuneChances) {
    }

    public static class DropGroup {
        public final List<DropEntry> all = new ArrayList<>();
        public final List<DropEntry> oneOf = new ArrayList<>();
    }

    private static final Map<Block, DropGroup> dropMap = new HashMap<>();

    public void loadConfigFromFile(String filePath) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(filePath)) {
            Type rawType = new TypeToken<Map<String, JsonObject>>() {}.getType();
            Map<String, JsonObject> rawConfig = gson.fromJson(reader, rawType);

            for (Map.Entry<String, JsonObject> entry : rawConfig.entrySet()) {
                ResourceLocation blockId = new ResourceLocation(entry.getKey());
                Block block = ForgeRegistries.BLOCKS.getValue(blockId);
                if (block == null)
                    continue;

                JsonObject dropGroupsObj = entry.getValue();

                DropGroup group = new DropGroup();

                if (dropGroupsObj.has("all")) {
                    JsonArray allArray = dropGroupsObj.getAsJsonArray("all");
                    for (JsonElement elem : allArray) {
                        DropEntry dropEntry = parseDropEntry(elem.getAsJsonObject());
                        if (dropEntry != null) group.all.add(dropEntry);
                    }
                }

                if (dropGroupsObj.has("one_of")) {
                    JsonArray oneOfArray = dropGroupsObj.getAsJsonArray("one_of");
                    for (JsonElement elem : oneOfArray) {
                        DropEntry dropEntry = parseDropEntry(elem.getAsJsonObject());
                        if (dropEntry != null) group.oneOf.add(dropEntry);
                    }
                }

                dropMap.put(block, group);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DropEntry parseDropEntry(JsonObject obj) {
        if (!obj.has("item") || !obj.has("fortune_chances")) return null;

        String itemId = obj.get("item").getAsString();
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId));
        if (item == null)
            return null;

        JsonArray chancesArray = obj.getAsJsonArray("fortune_chances");
        float[] chances = new float[chancesArray.size()];
        for (int i = 0; i < chancesArray.size(); i++)
            chances[i] = chancesArray.get(i).getAsFloat();

        return new DropEntry(item, chances);
    }
    public static Optional<DropGroup> getDropGroup(Block block) {
        return Optional.ofNullable(dropMap.get(block));
    }
}
