package net.night.grasses.config.additionalDropSystem;

import java.util.Map;

public record ConfigDrops(
        Map<BlockCondition, AdditionalDropConfig.DropGroup> blockDropMap,
        Map<MobCondition, AdditionalDropConfig.DropGroup> mobDropMap) {
}
