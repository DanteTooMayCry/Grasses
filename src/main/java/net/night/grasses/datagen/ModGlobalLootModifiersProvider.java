package net.night.grasses.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.night.grasses.Grasses;
import net.night.grasses.loot.AddItemModifier;

import static net.night.grasses.data.ModData.chestLootLocations;
import static net.night.grasses.init.BlocksRegister.DYEING_STATION;
import static net.night.grasses.init.ItemsRegister.*;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, Grasses.MOD_ID);
    }

    @Override
    protected void start() {

        for (String locationName : chestLootLocations) {
            itemIntoChest(DYEING_TOOL.get(), "chests/", locationName, 1, 1, 1f);
        }
        for (String locationName : chestLootLocations) {
            itemIntoChest(DYEING_BONE_MEAL.get(), "chests/", locationName, 5, 32, 1f);
        }

        for (String locationName : chestLootLocations) {
            itemIntoChest(GRASSES_DYE.get(), "chests/", locationName, 5, 32, 1f);
        }

        for (String locationName : chestLootLocations) {
            itemIntoChest(DIAMOND_AUTO_PRUNER.get(), "chests/", locationName, 1, 1, 0.05f);
        }

        for (String locationName : chestLootLocations) {
            itemIntoChest(DYEING_STATION.get().asItem(), "chests/", locationName, 1, 1, 0.05f);
        }


        itemIntoChest(NETHERITE_AUTO_PRUNER.get(), "chests/", "end_city_treasure" , 1, 1, 0.25f);
        itemIntoChest(NETHERITE_AUTO_PRUNER.get(), "chests/", "bastion_treasure" , 1, 1, 0.33f);


        itemIntoChest(DYEING_TOOL.get(), "chests/village/", "village_toolsmith" , 1, 1, 1f);
        itemIntoChest(DIAMOND_AUTO_PRUNER.get(), "chests/village/", "village_toolsmith" , 1, 1, 1f);

        itemIntoChest(DYEING_BONE_MEAL.get(), "chests/village/", "village_fisher" , 5, 32, 1f);
        itemIntoChest(DYEING_BONE_MEAL.get(), "chests/village/", "village_temple" , 5, 32, 1f);
        itemIntoChest(DYEING_BONE_MEAL.get(), "entities/", "tropical_fish" , 1, 1, 0.10f);
        itemIntoChest(DYEING_BONE_MEAL.get(), "gameplay/fishing/", "junk" , 1, 1, 0.10f);

        itemIntoChest(GRASSES_DYE.get(), "chests/village/", "village_fisher" , 5, 32, 1f);
        itemIntoChest(GRASSES_DYE.get(), "chests/village/", "village_temple" , 5, 32, 1f);
        itemIntoChest(GRASSES_DYE.get(), "entities/", "tropical_fish" , 1, 1, 0.10f);
        itemIntoChest(GRASSES_DYE.get(), "gameplay/fishing/", "junk" , 1, 1, 0.10f);

        itemIntoChest(DYEING_TOOL.get(), "gameplay/hero_of_the_village/", "toolsmith_gift" , 1, 1, 0.50f);
        itemIntoChest(DIAMOND_AUTO_PRUNER.get(), "gameplay/hero_of_the_village/", "toolsmith_gift" , 1, 1, 0.25f);
        itemIntoChest(DYEING_BONE_MEAL.get(), "gameplay/hero_of_the_village/", "fisherman_gift" , 10, 64, 1f);
        itemIntoChest(GRASSES_DYE.get(), "gameplay/hero_of_the_village/", "fisherman_gift" , 10, 64, 1f);



    }
    private void itemIntoChest(Item item, String locationPrefix, String location, int minItems, int maxItems, double probability) {
        add(getPath(item) + "_" + location, new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation(locationPrefix + location)).build()
        }, item, minItems, maxItems, probability));
    }

    public static String getPath(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }
}
