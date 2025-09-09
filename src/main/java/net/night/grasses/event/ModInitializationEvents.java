package net.night.grasses.event;

import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.night.grasses.Grasses;
import net.night.grasses.config.additionalDropSystem.*;

import java.nio.file.Path;

import static net.night.grasses.init.ItemsRegister.DIAMOND_AUTO_PRUNER;
import static net.night.grasses.init.ItemsRegister.NETHERITE_AUTO_PRUNER;

public class ModInitializationEvents {

    public static void commonSetup(FMLCommonSetupEvent event) {

        DispenserBlock.registerBehavior(DIAMOND_AUTO_PRUNER.get(), new ShearsDispenseItemBehavior());
        DispenserBlock.registerBehavior(NETHERITE_AUTO_PRUNER.get(), new ShearsDispenseItemBehavior());


        Path configRootDir = FMLPaths.CONFIGDIR.get();
        ConfigFileHelper.updateConfigWithVersionCheck(configRootDir);
        String configFileName = "additional_drops_" + Grasses.MOD_VERSION + ".toml";
        Path configPath = configRootDir.resolve("grasses").resolve(configFileName);

        try {
            ConfigDrops drops = TOMLParser.parseConfig(configPath);
            AdditionalDropConfig.setFromConfigDrops(drops);
        } catch (Exception e) {
            AdditionalDropConfig.clearBlockDropMap();
            AdditionalDropConfig.clearMobDropMap();
        }
    }
}
