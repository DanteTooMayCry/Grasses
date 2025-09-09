package net.night.grasses.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.night.grasses.Grasses;

@Mod.EventBusSubscriber(modid = Grasses.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventHandler {

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
    }
}
