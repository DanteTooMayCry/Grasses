package net.night.grasses.recipe.recipeConditions;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.night.grasses.config.GrassesConfig;

public class QuartzRecipeCondition implements ICondition {
    public static final QuartzRecipeCondition INSTANCE = new QuartzRecipeCondition();
    public static final Codec<QuartzRecipeCondition> CODEC = Codec.unit(INSTANCE);

    private QuartzRecipeCondition() {

    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }


    @Override
    public boolean test(IContext context) {
        return GrassesConfig.COMMON_CONFIG.ALLOW_SMELT_QUARTZ_FROM_DIORITE.get();
    }

}
