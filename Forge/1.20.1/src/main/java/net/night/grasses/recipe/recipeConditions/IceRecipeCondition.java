package net.night.grasses.recipe.recipeConditions;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.night.grasses.Grasses;
import net.night.grasses.config.GrassesConfig;

public class IceRecipeCondition implements ICondition {
    public static final IceRecipeCondition INSTANCE = new IceRecipeCondition();
    private static final ResourceLocation NAME = new ResourceLocation(Grasses.MOD_ID, "ice_enabled");

    private IceRecipeCondition() {

    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_ICE.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_ICE.get().toString();
    }

    public static class Serializer implements IConditionSerializer<IceRecipeCondition> {

        @Override
        public void write(JsonObject json, IceRecipeCondition value) {}


        @Override
        public IceRecipeCondition read(JsonObject json) {
            return IceRecipeCondition.INSTANCE;
        }

        @Override
        public ResourceLocation getID() {
            return IceRecipeCondition.NAME;
        }
    }
}
