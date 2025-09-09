package net.night.grasses.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.Grasses;
import net.night.grasses.item.AutomaticPrunerItem;
import net.night.grasses.item.DyeingBoneMealItem;
import net.night.grasses.item.DyeingItem;
import net.night.grasses.item.DyeingTool;

public class ItemsRegister {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Grasses.MOD_ID);


    public static final RegistryObject<Item> DIAMOND_AUTO_PRUNER = ITEMS.register("diamond_auto_pruner",
            () -> new AutomaticPrunerItem(new Item.Properties().durability(1024)));
    public static final RegistryObject<Item> NETHERITE_AUTO_PRUNER = ITEMS.register("netherite_auto_pruner",
            () -> new AutomaticPrunerItem(new Item.Properties().durability(2048)));

    public static final RegistryObject<Item> DYEING_TOOL = ITEMS.register("dyeing_tool",
            () -> new DyeingTool(new Item.Properties().durability(2048)));

    public static final RegistryObject<Item> DYEING_BONE_MEAL = ITEMS.register("dyeing_bone_meal",
            () -> new DyeingBoneMealItem(new Item.Properties()));

    public static final RegistryObject<Item> GRASSES_DYE = ITEMS.register("grasses_dye",
            () -> new DyeingItem(new Item.Properties()));

    public static final RegistryObject<Item> FERTILE_ICON = ITEMS.register("fertile_icon",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
