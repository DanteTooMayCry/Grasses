package net.night.grasses;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.night.grasses.block.blockEntity.screen.MenuTypesRegister;
import net.night.grasses.datagen.loot.LootItemConditions;
import net.night.grasses.event.ConditionRecipeRegisterEvent;
import net.night.grasses.init.*;
import net.night.grasses.loot.ModLootModifiers;
import net.night.grasses.particle.ModParticles;

import static net.night.grasses.config.GrassesConfig.CLIENT_CONFIG_SPEC;
import static net.night.grasses.config.GrassesConfig.COMMON_CONFIG_SPEC;

@Mod(Grasses.MOD_ID) // [1.20.1]
public class Grasses {
    public static final String MOD_ID = "grasses";
    public static final String MOD_VERSION = Version.MOD_VERSION;

    public static final boolean isBOPLoaded = ModList.get().isLoaded("biomesoplenty");
    public static final boolean isERLoaded = ModList.get().isLoaded("endrem");

    public Grasses() {
        
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG_SPEC);

        LootItemConditions.CONDITIONS.register(modEventBus);
        modEventBus.register(new ConditionRecipeRegisterEvent());

        BlocksRegister.register(modEventBus);
        BlocksRegisterBoP.register(modEventBus);
        ItemsRegister.register(modEventBus);
        BlockEntitiesRegister.register(modEventBus);
        CreativeModTabsRegister.register(modEventBus);

        MenuTypesRegister.register(modEventBus);
        RecipeRegister.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModParticles.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

    }


}
