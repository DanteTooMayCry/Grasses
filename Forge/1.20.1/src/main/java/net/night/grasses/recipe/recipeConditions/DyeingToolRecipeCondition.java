package net.night.grasses.recipe.recipeConditions;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.night.grasses.Grasses;
import net.night.grasses.config.GrassesConfig;

public class DyeingToolRecipeCondition implements ICondition {
    public static final DyeingToolRecipeCondition INSTANCE = new DyeingToolRecipeCondition();
    private static final ResourceLocation NAME = new ResourceLocation(Grasses.MOD_ID, "dyeing_tool_enabled");

    private DyeingToolRecipeCondition() {

    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_DYEING_TOOL.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_DYEING_TOOL.get().toString();
    }

    public static class Serializer implements IConditionSerializer<DyeingToolRecipeCondition> {

        @Override
        public void write(JsonObject json, DyeingToolRecipeCondition value) {}


        @Override
        public DyeingToolRecipeCondition read(JsonObject json) {
            return DyeingToolRecipeCondition.INSTANCE;
        }

        @Override
        public ResourceLocation getID() {
            return DyeingToolRecipeCondition.NAME;
        }
    }
}
