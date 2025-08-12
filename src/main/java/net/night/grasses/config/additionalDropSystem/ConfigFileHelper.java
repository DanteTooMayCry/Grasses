package net.night.grasses.config.additionalDropSystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;

//Copies the default config from the resources to the game's config folder, creates a new_version file if the config file exists but has a different version,
//and manages a hash file for change detection.
public class ConfigFileHelper {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String CONFIG_FILE_NAME = "additional_drops.toml";
    private static final String NEW_VERSION_SUFFIX = "_new_version.toml";
    private static final String RESOURCE_PATH = "/data/grasses/config/" + CONFIG_FILE_NAME;
    private static final String HASH_FILE_NAME = "additional_drops.hash";

    public static void updateConfigWithVersionCheck(Path configRootDir) {
        Path modConfigDir = configRootDir.resolve("grasses");
        Path configFile = modConfigDir.resolve(CONFIG_FILE_NAME);
        Path newVersionFile = modConfigDir.resolve("additional_drops" + NEW_VERSION_SUFFIX);
        Path hashFile = modConfigDir.resolve(HASH_FILE_NAME);

        try {
            if (!Files.exists(modConfigDir))
                Files.createDirectories(modConfigDir);

            String resourceHash = computeResourceHash();
            boolean configExists = Files.exists(configFile);
            boolean hashChanged = isHashChanged(hashFile, resourceHash);

            if (!configExists) {
                copyResourceToFile(configFile);
                Files.writeString(hashFile, resourceHash);
            } else if (hashChanged) {
                copyResourceToFile(newVersionFile);
                Files.writeString(hashFile, resourceHash);
                ModConfigStatus.setConfigUpdated(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyResourceToFile(Path targetFile) throws IOException {
        try (InputStream inputStream = ConfigFileHelper.class.getResourceAsStream(RESOURCE_PATH)) {
            if (inputStream == null)
                throw new IOException("Resource not found: " + RESOURCE_PATH);

            Files.copy(inputStream, targetFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static String computeResourceHash() {
        try (InputStream inputStream = ConfigFileHelper.class.getResourceAsStream(RESOURCE_PATH)) {
            if (inputStream == null)
                return "";

            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] buffer = new byte[8192];
            int read;

            while ((read = inputStream.read(buffer)) > 0)
                digest.update(buffer, 0, read);

            byte[] hashBytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes)
                sb.append(String.format("%02x", b));

            return sb.toString();
        } catch (Exception e) {
            LOGGER.error("Error while computing resource hash", e);
            return "";
        }
    }

    private static boolean isHashChanged(Path hashFile, String currentHash) {
        if (!Files.exists(hashFile)) return true;
        try {
            String storedHash = Files.readString(hashFile);
            return !storedHash.equals(currentHash);
        } catch (IOException e) {
            LOGGER.error("Failed to read stored hash from file: " + hashFile + ", assuming hash changed.", e);
            return true;
        }
    }
}
