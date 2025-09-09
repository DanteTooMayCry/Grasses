package net.night.grasses.recipe.recipeConditions;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.night.grasses.config.GrassesConfig;

public class IceRecipeCondition implements ICondition {
    public static final IceRecipeCondition INSTANCE = new IceRecipeCondition();
    public static final Codec<IceRecipeCondition> CODEC = Codec.unit(INSTANCE);
    //private static final ResourceLocation NAME = new ResourceLocation(Grasses.MOD_ID, "ice_enabled");

    private IceRecipeCondition() {

    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }


    @Override
    public boolean test(IContext context) {
        return GrassesConfig.COMMON_CONFIG.ALLOW_CRAFT_ICE.get();
    }
}
