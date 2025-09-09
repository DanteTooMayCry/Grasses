package net.night.grasses;

import net.neoforged.fml.ModList;
import net.night.grasses.block.blockEntity.screen.MenuTypesRegister;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.datagen.loot.LootItemConditions;
import net.night.grasses.event.ModInitializationEvents;
import net.night.grasses.init.*;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModLoadingContext;
import net.night.grasses.loot.ModLootModifiers;
import net.night.grasses.particle.ModParticles;

@Mod(Grasses.MOD_ID) // [1.20.4]
public class Grasses {
    public static final String MOD_ID = "grasses";
    public static final String MOD_VERSION = Version.MOD_VERSION;

    public static final boolean isBOPLoaded = ModList.get().isLoaded("biomesoplenty");
    public static final boolean isERLoaded = ModList.get().isLoaded("endrem");

    public Grasses(IEventBus modEventBus) {

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GrassesConfig.COMMON_CONFIG_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, GrassesConfig.CLIENT_CONFIG_SPEC);

        LootItemConditions.CONDITIONS.register(modEventBus);

        BlocksRegister.register(modEventBus);
        BlocksRegisterBoP.register(modEventBus);
        ItemsRegister.register(modEventBus);
        BlockEntitiesRegister.register(modEventBus);
        CreativeModTabsRegister.register(modEventBus);

        MenuTypesRegister.register(modEventBus);
        RecipeRegister.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModParticles.register(modEventBus);
        LootConditionsRegister.register(modEventBus);

        modEventBus.addListener(ModInitializationEvents::commonSetup);

        modEventBus.addListener(CapabilityRegistration::onRegisterCapabilities);
    }

}
