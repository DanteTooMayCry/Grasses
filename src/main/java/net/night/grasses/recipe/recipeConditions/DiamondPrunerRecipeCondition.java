package net.night.grasses.recipe.recipeConditions;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.night.grasses.Grasses;
import net.night.grasses.config.GrassesConfig;

public class DiamondPrunerRecipeCondition implements ICondition {
    public static final DiamondPrunerRecipeCondition INSTANCE = new DiamondPrunerRecipeCondition();
    private static final ResourceLocation NAME = new ResourceLocation(Grasses.MOD_ID, "diamond_auto_pruner_enabled");

    private DiamondPrunerRecipeCondition() {

    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_AUTO_PRUNER.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_AUTO_PRUNER.get().toString();
    }

    public static class Serializer implements IConditionSerializer<DiamondPrunerRecipeCondition> {

        @Override
        public void write(JsonObject json, DiamondPrunerRecipeCondition value) {}


        @Override
        public DiamondPrunerRecipeCondition read(JsonObject json) {
            return DiamondPrunerRecipeCondition.INSTANCE;
        }

        @Override
        public ResourceLocation getID() {
            return DiamondPrunerRecipeCondition.NAME;
        }
    }
}
