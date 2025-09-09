package net.night.grasses.recipe.recipeConditions;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.night.grasses.config.GrassesConfig;

public class StringRecipeCondition implements ICondition {
    public static final StringRecipeCondition INSTANCE = new StringRecipeCondition();
    public static final Codec<StringRecipeCondition> CODEC = Codec.unit(INSTANCE);

    private StringRecipeCondition() {

    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }


    @Override
    public boolean test(IContext context) {
        return GrassesConfig.COMMON_CONFIG.ALLOW_CRAFT_STRING.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.COMMON_CONFIG.ALLOW_CRAFT_STRING.get().toString();
    }

}
