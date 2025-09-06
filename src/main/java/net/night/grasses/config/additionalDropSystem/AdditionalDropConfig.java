package net.night.grasses.config.additionalDropSystem;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.night.grasses.enums.DropType;

import java.util.*;

public class AdditionalDropConfig {
    public static final Map<BlockCondition, DropGroup> dropMap = new LinkedHashMap<>();
    public static final Map<MobCondition, DropGroup> mobDropMap = new LinkedHashMap<>();

    public record DropEntry(Item item, float[] fortuneChances) {
    }

    public static class DropGroup {
        public final List<DropEntry> all = new ArrayList<>();
        public final List<DropEntry> oneOf = new ArrayList<>();
    }

    public static Optional<DropGroup> getDropGroup(BlockState blockState) {
        for (Map.Entry<BlockCondition, DropGroup> blockCondition : dropMap.entrySet())
            if (blockCondition.getKey().matches(blockState))
                return Optional.of(blockCondition.getValue());

        return Optional.empty();
    }

    public static Optional<DropGroup> getMobDropGroup(LivingEntity entity) {
        for (Map.Entry<MobCondition, DropGroup> mobCondition : mobDropMap.entrySet())
            if (mobCondition.getKey().matches(entity))
                return Optional.of(mobCondition.getValue());

        return Optional.empty();
    }

    public static void clearBlockDropMap() {
        dropMap.clear();
    }

    public static void clearMobDropMap() {
        mobDropMap.clear();
    }

    public static void setFromConfigDrops(ConfigDrops configDrops) {
        clearBlockDropMap();
        dropMap.putAll(configDrops.blockDropMap());
        clearMobDropMap();
        mobDropMap.putAll(configDrops.mobDropMap());
    }

    public static class ExtDropGroup extends DropGroup {
        private DropType clearOriginalDropsMode = DropType.NONE;
        private DropType addAdditionalDropMode = DropType.BOTH;

        public void setClearOriginalDropsMode(DropType mode) {
            this.clearOriginalDropsMode = mode;
        }
        public DropType getClearOriginalDropsMode() {
            return clearOriginalDropsMode;
        }

        public void setAddAdditionalDropMode(DropType mode) {
            this.addAdditionalDropMode = mode;
        }
        public DropType getAddAdditionalDropMode() {
            return addAdditionalDropMode;
        }
    }

}


