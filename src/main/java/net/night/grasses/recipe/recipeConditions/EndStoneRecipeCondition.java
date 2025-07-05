package net.night.grasses.recipe.recipeConditions;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.night.grasses.Grasses;
import net.night.grasses.config.GrassesConfig;

public class EndStoneRecipeCondition implements ICondition {
    public static final EndStoneRecipeCondition INSTANCE = new EndStoneRecipeCondition();
    private static final ResourceLocation NAME = new ResourceLocation(Grasses.MOD_ID, "end_stone_enabled");

    private EndStoneRecipeCondition() {

    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_END_STONE.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_END_STONE.get().toString();
    }

    public static class Serializer implements IConditionSerializer<EndStoneRecipeCondition> {

        @Override
        public void write(JsonObject json, EndStoneRecipeCondition value) {}


        @Override
        public EndStoneRecipeCondition read(JsonObject json) {
            return EndStoneRecipeCondition.INSTANCE;
        }

        @Override
        public ResourceLocation getID() {
            return EndStoneRecipeCondition.NAME;
        }
    }
}
