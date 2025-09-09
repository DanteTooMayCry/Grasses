package net.night.grasses.datagen.loot;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.night.grasses.Grasses;

public class LootItemConditions {

    public static final DeferredRegister<LootItemConditionType> CONDITIONS =
            DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, Grasses.MOD_ID);

    public static final DeferredHolder<LootItemConditionType, LootItemConditionType> IS_MOD_LOADED =
            CONDITIONS.register("is_mod_loaded", () -> new LootItemConditionType(IsModLoaded.CODEC));

    public static final DeferredHolder<LootItemConditionType, LootItemConditionType> HAS_INSTANCE_OF_SHEARS =
            CONDITIONS.register("has_instance_of_shears", () -> new LootItemConditionType(HasInstanceOfShearsCondition.CODEC));
}
