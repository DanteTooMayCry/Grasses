package net.night.grasses.config.additionalDropSystem;

public class ModConfigStatus {
    private static boolean configUpdated = false;

    public static void setConfigUpdated(boolean updated) {
        configUpdated = updated;
    }

    public static boolean isConfigUpdated() {
        return configUpdated;
    }
}
