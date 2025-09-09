package net.night.grasses.init;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.night.grasses.block.blockEntity.DyeingStationBlockEntity;

public class CapabilityRegistration {

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BlockEntitiesRegister.DYEING_STATION_BE.get(),
                DyeingStationBlockEntity::getHandlerForSide
        );
    }
}
