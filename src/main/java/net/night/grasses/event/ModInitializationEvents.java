package net.night.grasses.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import net.night.grasses.Grasses;
import net.night.grasses.config.*;
import net.night.grasses.config.additionalDropSystem.AdditionalDropConfig;
import net.night.grasses.config.additionalDropSystem.BlockCondition;
import net.night.grasses.config.additionalDropSystem.ConfigFileHelper;
import net.night.grasses.config.additionalDropSystem.TOMLParser;
import net.night.grasses.network.MessageRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Map;

import static net.minecraft.world.item.Items.ROTTEN_FLESH;
import static net.minecraft.world.level.block.Blocks.BAMBOO;
import static net.minecraft.world.level.block.ComposterBlock.COMPOSTABLES;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;

@Mod.EventBusSubscriber(modid = Grasses.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModInitializationEvents {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            MessageRegistry.register("color_type");
        });

        //=====================
        Path configRootDir = FMLPaths.CONFIGDIR.get();
        ConfigFileHelper.updateConfigWithVersionCheck(configRootDir);
        Path configPath = configRootDir.resolve("grasses/additional_drops.toml");

        try {
            Map<BlockCondition, AdditionalDropConfig.DropGroup> dropMap = TOMLParser.parseConfig(configPath);
            AdditionalDropConfig.setFromMap(dropMap);

            LOGGER.info("[Grasses Mod] Additional drop config loaded for {} block conditions.", dropMap.size());
        } catch (Exception e) {
            LOGGER.error("[Grasses Mod] Failed to load additional drop config from file: {}", configPath, e);
            AdditionalDropConfig.clear();
        }

        //=====================

        float f03 = 0.3F;
        float f05 = 0.5F;
        float f065 = 0.65F;
        float f085 = 0.85F;
        float f1 = 1.0F;

        COMPOSTABLES.put(ACACIA_LEAVES_BLOCK.get().asItem(), f03);
        COMPOSTABLES.put(AZALEA_LEAVES_BLOCK.get().asItem(), f03);
        COMPOSTABLES.put(BIRCH_LEAVES_BLOCK.get().asItem(), f03);
        COMPOSTABLES.put(CHERRY_LEAVES_BLOCK.get().asItem(), f03);
        COMPOSTABLES.put(DARK_OAK_LEAVES_BLOCK.get().asItem(), f03);
        COMPOSTABLES.put(FLOWERING_AZALEA_LEAVES_BLOCK.get().asItem(), f03);
        COMPOSTABLES.put(JUNGLE_LEAVES_BLOCK.get().asItem(), f03);
        COMPOSTABLES.put(MANGROVE_LEAVES_BLOCK.get().asItem(), f03);
        COMPOSTABLES.put(OAK_LEAVES_BLOCK.get().asItem(), f03);
        COMPOSTABLES.put(SPRUCE_LEAVES_BLOCK.get().asItem(), f03);
        COMPOSTABLES.put(SPRUCE_LEAVES_BLOCK.get().asItem(), f03);

        COMPOSTABLES.put(GRASS_TINTED.get().asItem(), f03);
        COMPOSTABLES.put(FERN_TINTED.get().asItem(), f065);
        COMPOSTABLES.put(SEAGRASS_TINTED.get().asItem(), f03);
        COMPOSTABLES.put(GRASS_TALL_TINTED.get().asItem(), f05);
        COMPOSTABLES.put(FERN_TALL_TINTED.get().asItem(), f065);
        COMPOSTABLES.put(SEAGRASS_TALL_TINTED.get().asItem(), f05);
        COMPOSTABLES.put(VINE_TINTED.get().asItem(), f05);
        COMPOSTABLES.put(LILY_TINTED.get().asItem(), f065);
        COMPOSTABLES.put(SUGAR_CANE_TINTED.get().asItem(), f05);

        COMPOSTABLES.put(BIG_DRIP_LEAF_TINTED.get().asItem(), f065);
        COMPOSTABLES.put(SMALL_DRIP_LEAF_TINTED.get().asItem(), f03);

        COMPOSTABLES.put(KELP_TINTED.get().asItem(), f05);
        COMPOSTABLES.put(CACTUS_TINTED.get().asItem(), f03);

        if (isBOPLoaded) {
            COMPOSTABLES.put(FIR_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(PINE_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(MAPLE_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(REDWOOD_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(MAHOGANY_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(JACARANDA_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(PALM_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(WILLOW_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(DEAD_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(MAGIC_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(UMBRAN_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(EMPYREAL_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(FLOWERING_OAK_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(ORIGIN_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(CYPRESS_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(HELLBARK_LEAVES_BLOCK.get().asItem(), f03);
            COMPOSTABLES.put(WILLOW_VINE_TINTED.get().asItem(), f03);


            COMPOSTABLES.put(BUSH_TINTED.get().asItem(), f05);
            COMPOSTABLES.put(SPROUT_TINTED.get().asItem(), f05);
            COMPOSTABLES.put(CLOVER_TINTED.get().asItem(), f05);
            COMPOSTABLES.put(HUGE_CLOVER_TINTED.get().asItem(), f05);
            COMPOSTABLES.put(HIGH_GRASS_TINTED.get().asItem(), f05);
            COMPOSTABLES.put(HIGH_GRASS_PLANT_TINTED.get().asItem(), f05);
            COMPOSTABLES.put(HUGE_LILY_PAD_TINTED.get().asItem(), f065);
            COMPOSTABLES.put(WATER_GRASS_TINTED.get().asItem(), f05);
            COMPOSTABLES.put(TINY_CACTUS_TINTED.get().asItem(), f05);
            COMPOSTABLES.put(WATERLILY_TINTED.get().asItem(), f065);
            COMPOSTABLES.put(WILLOW_VINE_TINTED.get().asItem(), f05);
        }

        if (GrassesConfig.CommonConfig.ALLOW_COMPOSTING_BAMBOO.get()) {
            COMPOSTABLES.put(BAMBOO.asItem(), f03);
            COMPOSTABLES.put(BAMBOO_TINTED.get().asItem(), f03);
        }
        if (GrassesConfig.CommonConfig.ALLOW_COMPOSTING_ROTTER_FLESH.get())
            COMPOSTABLES.put(ROTTEN_FLESH.asItem(), f03);
    }
}
