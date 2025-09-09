package net.night.grasses.recipe.recipeConditions;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.night.grasses.Grasses;
import net.night.grasses.config.GrassesConfig;

public class DyeingStationRecipeCondition implements ICondition {
    public static final DyeingStationRecipeCondition INSTANCE = new DyeingStationRecipeCondition();
    private static final ResourceLocation NAME = new ResourceLocation(Grasses.MOD_ID, "dyeing_station_enabled");

    private DyeingStationRecipeCondition() {

    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_DYEING_STATION.get();
    }

    @Override
    public String toString()
    {
        return GrassesConfig.CommonConfig.ALLOW_CRAFT_DYEING_STATION.get().toString();
    }

    public static class Serializer implements IConditionSerializer<DyeingStationRecipeCondition> {

        @Override
        public void write(JsonObject json, DyeingStationRecipeCondition value) {}


        @Override
        public DyeingStationRecipeCondition read(JsonObject json) {
            return DyeingStationRecipeCondition.INSTANCE;
        }

        @Override
        public ResourceLocation getID() {
            return DyeingStationRecipeCondition.NAME;
        }
    }
}
