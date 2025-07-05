package net.night.grasses.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.Grasses;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Grasses.MOD_ID);

    public static final RegistryObject<SimpleParticleType> JACARANDA_LEAVES_PARTICLE =
            PARTICLE_TYPES.register("jacaranda_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SNOWBLOSSOM_LEAVES_PARTICLE =
            PARTICLE_TYPES.register("snowblossom_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> MAPLE_LEAVES_PARTICLE =
            PARTICLE_TYPES.register("maple_leaves", () -> new SimpleParticleType(false));


    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
