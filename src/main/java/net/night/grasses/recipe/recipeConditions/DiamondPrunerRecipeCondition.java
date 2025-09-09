package net.night.grasses.recipe.recipeConditions;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.night.grasses.config.GrassesConfig;

public class DiamondPrunerRecipeCondition implements ICondition {
    public static final DiamondPrunerRecipeCondition INSTANCE = new DiamondPrunerRecipeCondition();
    public static final Codec<DiamondPrunerRecipeCondition> CODEC = Codec.unit(INSTANCE);

    private DiamondPrunerRecipeCondition() {

    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.COMMON_CONFIG.ALLOW_CRAFT_AUTO_PRUNER.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.COMMON_CONFIG.ALLOW_CRAFT_AUTO_PRUNER.get().toString();
    }
}
