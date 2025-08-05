package net.night.grasses.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.Grasses;
import net.night.grasses.colorManagers.ColorType;
import net.night.grasses.item.AutomaticPrunerItem;

import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.data.ModData.*;
import static net.night.grasses.data.ModMethods.setColorOnItemStack;
import static net.night.grasses.data.ModMethods.setEnchantmentBoolean;
import static net.night.grasses.init.BlocksRegister.JUNGLE_GRASS_BLOCK;
import static net.night.grasses.init.ItemsRegister.*;


public class CreativeModTabsRegister {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Grasses.MOD_ID);


    public static final RegistryObject<CreativeModeTab> GRASSES_TAB = CREATIVE_MODE_TABS.register("grasses_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(JUNGLE_GRASS_BLOCK.get()))
                    .title(Component.translatable("creativetab.grasses_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        for (RegistryObject<Item> item : ITEMS.getEntries()) {

                            if (!notForCreativeTab.contains(item.get().asItem())) {
                                ItemStack itemStack = item.get().getDefaultInstance();
                                if (modBlockItemsWithNBT.contains(item.get().asItem())) {
                                    if (tintedBOPRestrictedCreativeTab.contains(item.get().asItem()) && !isBOPLoaded)
                                        continue;

                                    setColorOnItemStack(itemStack, ColorType.PLAINS);
                                    output.accept(itemStack);
                                } else if (item.get().asItem() instanceof AutomaticPrunerItem) {
                                    setEnchantmentBoolean(itemStack);
                                    output.accept(itemStack);
                                } else {
                                    output.accept(itemStack);
                                }
                            }
                        }
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}