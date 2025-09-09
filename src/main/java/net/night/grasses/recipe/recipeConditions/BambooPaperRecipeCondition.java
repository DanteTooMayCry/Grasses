package net.night.grasses.recipe.recipeConditions;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.night.grasses.config.GrassesConfig;

public class BambooPaperRecipeCondition implements ICondition {
    public static final BambooPaperRecipeCondition INSTANCE = new BambooPaperRecipeCondition();
    public static final Codec<BambooPaperRecipeCondition> CODEC = Codec.unit(INSTANCE);

    private BambooPaperRecipeCondition() {

    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.COMMON_CONFIG.ALLOW_CRAFT_BAMBOO_PAPER.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.COMMON_CONFIG.ALLOW_CRAFT_BAMBOO_PAPER.get().toString();
    }
}
