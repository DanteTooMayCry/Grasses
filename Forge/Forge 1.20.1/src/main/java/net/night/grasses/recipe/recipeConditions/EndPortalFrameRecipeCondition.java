package net.night.grasses.recipe.recipeConditions;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.night.grasses.Grasses;
import net.night.grasses.config.GrassesConfig;

public class EndPortalFrameRecipeCondition implements ICondition {
    public static final EndPortalFrameRecipeCondition INSTANCE = new EndPortalFrameRecipeCondition();
    private static final ResourceLocation NAME = new ResourceLocation(Grasses.MOD_ID, "end_portal_frame_enabled");

    private EndPortalFrameRecipeCondition() {

    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_END_PORTAL_FRAME.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_END_PORTAL_FRAME.get().toString();
    }

    public static class Serializer implements IConditionSerializer<EndPortalFrameRecipeCondition> {

        @Override
        public void write(JsonObject json, EndPortalFrameRecipeCondition value) {}


        @Override
        public EndPortalFrameRecipeCondition read(JsonObject json) {
            return EndPortalFrameRecipeCondition.INSTANCE;
        }

        @Override
        public ResourceLocation getID() {
            return EndPortalFrameRecipeCondition.NAME;
        }
    }
}
