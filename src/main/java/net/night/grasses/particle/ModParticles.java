package net.night.grasses.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.night.grasses.Grasses;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Grasses.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> JACARANDA_LEAVES_PARTICLE =
            PARTICLE_TYPES.register("jacaranda_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOWBLOSSOM_LEAVES_PARTICLE =
            PARTICLE_TYPES.register("snowblossom_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MAPLE_LEAVES_PARTICLE =
            PARTICLE_TYPES.register("maple_leaves", () -> new SimpleParticleType(false));


    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
