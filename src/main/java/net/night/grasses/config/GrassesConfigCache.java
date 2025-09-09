package net.night.grasses.config;

public class GrassesConfigCache {
    public static boolean disableVanillaPlantsOffset = false;

    public static void reload() {
        disableVanillaPlantsOffset = GrassesConfig.CLIENT_CONFIG.DISABLE_VANILLA_PLANTS_OFFSET.get();
    }
}
