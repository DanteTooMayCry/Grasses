package net.night.grasses.datagen.loot;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.Grasses;

public class LootItemConditions {

    public static final DeferredRegister<LootItemConditionType> CONDITIONS =
            DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, Grasses.MOD_ID);

    public static final RegistryObject<LootItemConditionType> IS_MOD_LOADED =
            CONDITIONS.register("is_mod_loaded", () -> new LootItemConditionType(new IsModLoaded.ConditionSerializer()));
}
