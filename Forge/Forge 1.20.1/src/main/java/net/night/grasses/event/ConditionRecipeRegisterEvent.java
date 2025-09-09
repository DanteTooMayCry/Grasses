package net.night.grasses.event;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.night.grasses.recipe.recipeConditions.*;

public class ConditionRecipeRegisterEvent {
    @SubscribeEvent
    public void registerCondition(RegisterEvent event) {

        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS,
                helper -> CraftingHelper.register(new DiamondPrunerRecipeCondition.Serializer()));
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS,
                helper -> CraftingHelper.register(new DyeingToolRecipeCondition.Serializer()));
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS,
                helper -> CraftingHelper.register(new DyeingStationRecipeCondition.Serializer()));
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS,
                helper -> CraftingHelper.register(new BambooPaperRecipeCondition.Serializer()));
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS,
                helper -> CraftingHelper.register(new IceRecipeCondition.Serializer()));
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS,
                helper -> CraftingHelper.register(new StringRecipeCondition.Serializer()));
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS,
                helper -> CraftingHelper.register(new EndStoneRecipeCondition.Serializer()));
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS,
                helper -> CraftingHelper.register(new EndPortalFrameRecipeCondition.Serializer()));
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS,
                helper -> CraftingHelper.register(new QuartzRecipeCondition.Serializer()));
    }
}
