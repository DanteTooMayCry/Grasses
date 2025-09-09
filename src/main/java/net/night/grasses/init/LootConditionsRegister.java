package net.night.grasses.init;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.night.grasses.Grasses;
import net.night.grasses.recipe.recipeConditions.*;

public class LootConditionsRegister {

    public static final DeferredRegister<Codec<? extends ICondition>> CONDITIONALS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, Grasses.MOD_ID);

    public static final DeferredHolder<Codec<? extends ICondition>, Codec<BambooPaperRecipeCondition>> BAMBOO_PAPER_RECIPE_CONDITION = CONDITIONALS.register("bamboo_paper_enabled", () -> BambooPaperRecipeCondition.CODEC);
    public static final DeferredHolder<Codec<? extends ICondition>, Codec<DiamondPrunerRecipeCondition>> DIAMOND_PRUNER_RECIPE_CONDITION = CONDITIONALS.register("diamond_auto_pruner_enabled", () -> DiamondPrunerRecipeCondition.CODEC);
    public static final DeferredHolder<Codec<? extends ICondition>, Codec<DyeingStationRecipeCondition>> DYEING_STATION_RECIPE_CONDITION = CONDITIONALS.register("dyeing_station_enabled", () -> DyeingStationRecipeCondition.CODEC);
    public static final DeferredHolder<Codec<? extends ICondition>, Codec<DyeingToolRecipeCondition>> DYEING_TOOL_RECIPE_CONDITION = CONDITIONALS.register("dyeing_tool_enabled", () -> DyeingToolRecipeCondition.CODEC);
    public static final DeferredHolder<Codec<? extends ICondition>, Codec<EndPortalFrameRecipeCondition>> END_PORTAL_RECIPE_CONDITION = CONDITIONALS.register("end_portal_frame_enabled", () -> EndPortalFrameRecipeCondition.CODEC);
    public static final DeferredHolder<Codec<? extends ICondition>, Codec<EndStoneRecipeCondition>> END_STONE_RECIPE_CONDITION = CONDITIONALS.register("end_stone_enabled", () -> EndStoneRecipeCondition.CODEC);
    public static final DeferredHolder<Codec<? extends ICondition>, Codec<IceRecipeCondition>> ICE_RECIPE_CONDITION = CONDITIONALS.register("ice_enabled", () -> IceRecipeCondition.CODEC);
    public static final DeferredHolder<Codec<? extends ICondition>, Codec<QuartzRecipeCondition>> QUARTZ_RECIPE_CONDITION = CONDITIONALS.register("quartz_smelting_enabled", () -> QuartzRecipeCondition.CODEC);
    public static final DeferredHolder<Codec<? extends ICondition>, Codec<StringRecipeCondition>> STRING_RECIPE_CONDITION = CONDITIONALS.register("string_enabled", () -> StringRecipeCondition.CODEC);


    public static void register(IEventBus eventBus) {
        CONDITIONALS.register(eventBus);
    }
}
