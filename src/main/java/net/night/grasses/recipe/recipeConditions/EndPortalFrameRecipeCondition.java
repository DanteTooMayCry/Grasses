package net.night.grasses.recipe.recipeConditions;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.night.grasses.config.GrassesConfig;

public class EndPortalFrameRecipeCondition implements ICondition {
    public static final EndPortalFrameRecipeCondition INSTANCE = new EndPortalFrameRecipeCondition();
    public static final Codec<EndPortalFrameRecipeCondition> CODEC = Codec.unit(INSTANCE);
    //private static final ResourceLocation NAME = new ResourceLocation(Grasses.MOD_ID, "end_portal_frame_enabled");

    private EndPortalFrameRecipeCondition() {

    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.COMMON_CONFIG.ALLOW_CRAFT_END_PORTAL_FRAME.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.COMMON_CONFIG.ALLOW_CRAFT_END_PORTAL_FRAME.get().toString();
    }
}
