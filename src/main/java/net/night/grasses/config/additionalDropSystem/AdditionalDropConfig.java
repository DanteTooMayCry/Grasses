package net.night.grasses.config.additionalDropSystem;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class AdditionalDropConfig {
    public record DropEntry(Item item, float[] fortuneChances) {}

    public static class DropGroup {
        public final List<DropEntry> all = new ArrayList<>();
        public final List<DropEntry> oneOf = new ArrayList<>();
    }

    public static final Map<BlockCondition, DropGroup> dropMap = new LinkedHashMap<>();

    public static Optional<DropGroup> getDropGroup(BlockState state) {
        for (Map.Entry<BlockCondition, DropGroup> entry : dropMap.entrySet()) {
            if (entry.getKey().matches(state))
                return Optional.of(entry.getValue());
        }
        return Optional.empty();
    }

    public static void clear() {
        dropMap.clear();
    }

    public static void setFromMap(Map<BlockCondition, DropGroup> map) {
        clear();
        dropMap.putAll(map);
    }
}
