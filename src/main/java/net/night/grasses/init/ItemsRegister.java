package net.night.grasses.init;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.night.grasses.Grasses;
import net.night.grasses.item.AutomaticPrunerItem;
import net.night.grasses.item.DyeingBoneMealItem;
import net.night.grasses.item.DyeingItem;
import net.night.grasses.item.DyeingTool;

public class ItemsRegister {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Grasses.MOD_ID);


    public static final DeferredItem<Item> DIAMOND_AUTO_PRUNER = ITEMS.register("diamond_auto_pruner",
            () -> new AutomaticPrunerItem(new Item.Properties().durability(1024)));
    public static final DeferredItem<Item> NETHERITE_AUTO_PRUNER = ITEMS.register("netherite_auto_pruner",
            () -> new AutomaticPrunerItem(new Item.Properties().durability(2048)));

    public static final DeferredItem<Item> DYEING_TOOL = ITEMS.register("dyeing_tool",
            () -> new DyeingTool(new Item.Properties().durability(2048)));

    public static final DeferredItem<Item> DYEING_BONE_MEAL = ITEMS.register("dyeing_bone_meal",
            () -> new DyeingBoneMealItem(new Item.Properties()));

    public static final DeferredItem<Item> GRASSES_DYE = ITEMS.register("grasses_dye",
            () -> new DyeingItem(new Item.Properties()));

    public static final DeferredItem<Item> FERTILE_ICON = ITEMS.register("fertile_icon",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
