package net.night.grasses.recipe.recipeConditions;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.night.grasses.config.GrassesConfig;

public class DyeingStationRecipeCondition implements ICondition {
    public static final DyeingStationRecipeCondition INSTANCE = new DyeingStationRecipeCondition();
    public static final Codec<DyeingStationRecipeCondition> CODEC = Codec.unit(INSTANCE);

    public DyeingStationRecipeCondition() {

    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.COMMON_CONFIG.ALLOW_CRAFT_DYEING_STATION.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.COMMON_CONFIG.ALLOW_CRAFT_DYEING_STATION.get().toString();
    }
}
