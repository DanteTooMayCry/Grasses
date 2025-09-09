package net.night.grasses.colorManagers;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.night.grasses.enums.ColorType;

import java.util.ArrayList;
import java.util.List;

import static net.night.grasses.data.ModMethods.getColorTypeFromNBT;
import static net.night.grasses.data.ModMethods.hasBlockStateTag;
import static net.night.grasses.init.ItemsRegister.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DyeingItemColorManager extends ItemColors {
    public static final List<ItemColor> dyeingItemColorList = new ArrayList<>();

    private static ItemColor registerItemColor() {
        ItemColor toReturn;

        toReturn = (itemStack, layer) -> {

            if (!hasBlockStateTag(itemStack)) {
                return -1;
            }

            ColorType colorType = getColorTypeFromNBT(itemStack);
            return ColorsDefinition.takeColor(colorType, null, itemStack);
        };

        dyeingItemColorList.add(toReturn);
        return toReturn;
    }



    @SubscribeEvent
    public static ItemColors createDefault(RegisterColorHandlersEvent.Item event) {

        final ItemColors itemColors = event.getItemColors();

        ItemColor dyeingItemColor = registerItemColor();

        for(ItemColor itemColor : dyeingItemColorList){

            itemColors.register((itemStack, layer) -> {
                return layer > 0 ? -1 : itemColor.getColor(itemStack, layer);
            }, DYEING_TOOL.get(), DYEING_BONE_MEAL.get(), GRASSES_DYE.get());
        }

        return itemColors;
    }
}
