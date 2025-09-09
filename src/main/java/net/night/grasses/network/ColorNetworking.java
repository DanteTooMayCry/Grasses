package net.night.grasses.network;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import static net.night.grasses.Grasses.MOD_ID;

public class ColorNetworking {
    private static final String PROTOCOL_VERSION = "1";

    public static void register(final RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(MOD_ID)
                .versioned(PROTOCOL_VERSION);

        registrar.play(SyncColorPayload.ID, SyncColorPayload::new, payload -> payload.client(SyncColorPayload::handle));
    }

    public static void sendToClientsNear(BlockPos blockPos, ResourceKey<Level> dimension) {
        PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 3, dimension).get()).send();
    }
}
