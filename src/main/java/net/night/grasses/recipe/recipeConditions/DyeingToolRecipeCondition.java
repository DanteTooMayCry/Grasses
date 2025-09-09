package net.night.grasses.recipe.recipeConditions;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.night.grasses.config.GrassesConfig;

public class DyeingToolRecipeCondition implements ICondition {
    public static final DyeingToolRecipeCondition INSTANCE = new DyeingToolRecipeCondition();
    public static final Codec<DyeingToolRecipeCondition> CODEC = Codec.unit(INSTANCE);

    private DyeingToolRecipeCondition() {

    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.COMMON_CONFIG.ALLOW_CRAFT_DYEING_TOOL.get();
    }
}
