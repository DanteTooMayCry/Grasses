package net.night.grasses.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.night.grasses.Grasses;
import net.night.grasses.recipe.DyeingStationRecipe;

public class RecipeRegister {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Grasses.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Grasses.MOD_ID);


    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DyeingStationRecipe>> DYEING_SERIALIZER =
            SERIALIZERS.register("dyeing", () -> DyeingStationRecipe.Serializer.INSTANCE);

    public static final DeferredHolder<RecipeType<?>, RecipeType<DyeingStationRecipe>> DYEING_TYPE =
            TYPES.register("dyeing", () -> new RecipeType<DyeingStationRecipe>() {
                @Override
                public String toString() {
                    return "dyeing";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
