package net.night.grasses.recipe.recipeConditions;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.night.grasses.Grasses;
import net.night.grasses.config.GrassesConfig;

public class BambooPaperRecipeCondition implements ICondition {
    public static final BambooPaperRecipeCondition INSTANCE = new BambooPaperRecipeCondition();
    private static final ResourceLocation NAME = new ResourceLocation(Grasses.MOD_ID, "bamboo_paper_enabled");

    private BambooPaperRecipeCondition() {

    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_BAMBOO_PAPER.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_BAMBOO_PAPER.get().toString();
    }

    public static class Serializer implements IConditionSerializer<BambooPaperRecipeCondition> {

        @Override
        public void write(JsonObject json, BambooPaperRecipeCondition value) {}


        @Override
        public BambooPaperRecipeCondition read(JsonObject json) {
            return BambooPaperRecipeCondition.INSTANCE;
        }

        @Override
        public ResourceLocation getID() {
            return BambooPaperRecipeCondition.NAME;
        }
    }
}
