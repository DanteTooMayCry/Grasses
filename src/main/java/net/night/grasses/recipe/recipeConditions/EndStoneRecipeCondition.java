package net.night.grasses.recipe.recipeConditions;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.night.grasses.config.GrassesConfig;

public class EndStoneRecipeCondition implements ICondition {
    public static final EndStoneRecipeCondition INSTANCE = new EndStoneRecipeCondition();
    public static final Codec<EndStoneRecipeCondition> CODEC = Codec.unit(INSTANCE);

    private EndStoneRecipeCondition() {

    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }


    @Override
    public boolean test(IContext context) {
        return GrassesConfig.COMMON_CONFIG.ALLOW_CRAFT_END_STONE.get();
    }

}
