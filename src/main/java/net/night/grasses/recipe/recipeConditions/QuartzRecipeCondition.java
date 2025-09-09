package net.night.grasses.recipe.recipeConditions;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.night.grasses.Grasses;
import net.night.grasses.config.GrassesConfig;

public class QuartzRecipeCondition implements ICondition {
    public static final QuartzRecipeCondition INSTANCE = new QuartzRecipeCondition();
    private static final ResourceLocation NAME = new ResourceLocation(Grasses.MOD_ID, "quartz_smelting_enabled");

    private QuartzRecipeCondition() {

    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.CommonConfig.ALLOW_SMELT_QUARTZ_FROM_DIORITE.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.CommonConfig.ALLOW_SMELT_QUARTZ_FROM_DIORITE.get().toString();
    }

    public static class Serializer implements IConditionSerializer<QuartzRecipeCondition> {

        @Override
        public void write(JsonObject json, QuartzRecipeCondition value) {}


        @Override
        public QuartzRecipeCondition read(JsonObject json) {
            return QuartzRecipeCondition.INSTANCE;
        }

        @Override
        public ResourceLocation getID() {
            return QuartzRecipeCondition.NAME;
        }
    }
}
