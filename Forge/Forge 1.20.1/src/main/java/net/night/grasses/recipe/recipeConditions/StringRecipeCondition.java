package net.night.grasses.recipe.recipeConditions;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.night.grasses.Grasses;
import net.night.grasses.config.GrassesConfig;

public class StringRecipeCondition implements ICondition {
    public static final StringRecipeCondition INSTANCE = new StringRecipeCondition();
    private static final ResourceLocation NAME = new ResourceLocation(Grasses.MOD_ID, "string_enabled");

    private StringRecipeCondition() {

    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_STRING.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_STRING.get().toString();
    }

    public static class Serializer implements IConditionSerializer<StringRecipeCondition> {

        @Override
        public void write(JsonObject json, StringRecipeCondition value) {}


        @Override
        public StringRecipeCondition read(JsonObject json) {
            return StringRecipeCondition.INSTANCE;
        }

        @Override
        public ResourceLocation getID() {
            return StringRecipeCondition.NAME;
        }
    }
}
