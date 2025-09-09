package net.night.grasses.datagen.loot;

import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootConditionHelpers {
    public static LootItemCondition.Builder modNotLoadedBuilder(String modID) {
        LootItemCondition.Builder modLoadedCondition = IsModLoaded.builder(modID);
        return InvertedLootItemCondition.invert(modLoadedCondition);
    }
}

