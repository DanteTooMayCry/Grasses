package net.night.grasses.config.additionalDropSystem;

import net.night.grasses.Grasses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;

//Copies the default config from the resources to the game's config folder, creates an old_version file if 1) the config file already exists, 2) mod in next version made changes
// in file and 3) player made own changes before update. Config use a hash file for change detection.
public class ConfigFileHelper {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String CONFIG_FILE_BASE = "additional_drops";
    private static final String CONFIG_FILE_EXT = ".toml";
    private static final String HASH_FILE_NAME = "additional_drops.hash";
    private static final String RESOURCE_PATH = "/data/grasses/config/" + CONFIG_FILE_BASE + CONFIG_FILE_EXT;
    private static final String BACKUP_FILE_PREFIX = "additional_drops_old_version_";

    public static void updateConfigWithVersionCheck(Path configRootDir) {
        try {
            Path modConfigDir = configRootDir.resolve(Grasses.MOD_ID);
            if (!Files.exists(modConfigDir))
                Files.createDirectories(modConfigDir);

            Path currentConfigFile = null;

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(modConfigDir, CONFIG_FILE_BASE + "_*" + CONFIG_FILE_EXT)) {
                for (Path file : stream) {
                    String name = file.getFileName().toString();
                    if (!name.contains("_old_version")) {
                        currentConfigFile = file;
                        break;
                    }
                }
            }

            if (currentConfigFile == null) {
                String currentConfigFileName = CONFIG_FILE_BASE + "_" + Grasses.MOD_VERSION + CONFIG_FILE_EXT;
                currentConfigFile = modConfigDir.resolve(currentConfigFileName);
            }

            Path hashFile = modConfigDir.resolve(HASH_FILE_NAME);

            String resourceHash = computeResourceHash();

            boolean configExists = Files.exists(currentConfigFile);
            boolean hashChanged = isHashChanged(hashFile, resourceHash);
            boolean playerFileChanged = false;

            if (configExists && Files.exists(hashFile)) {
                String currentPlayerFileHash = computeHash(currentConfigFile);
                String savedHash = Files.readString(hashFile);
                playerFileChanged = !currentPlayerFileHash.equals(savedHash);
            }

            if (!configExists) {
                copyResourceToFile(currentConfigFile);
                Files.writeString(hashFile, resourceHash);
                ModConfigStatus.setConfigUpdated(true);
                return;
            }

            if (hashChanged) {
                if (playerFileChanged) {
                    String oldVersion = extractVersionFromFileName(currentConfigFile.getFileName().toString());
                    if ("unknown_version".equals(oldVersion))
                        oldVersion = "unknown_version";

                    Path backupFile = modConfigDir.resolve(BACKUP_FILE_PREFIX + oldVersion + CONFIG_FILE_EXT);

                    int counter = 1;
                    while (Files.exists(backupFile)) {
                        backupFile = modConfigDir.resolve(BACKUP_FILE_PREFIX + oldVersion + "_" + counter + CONFIG_FILE_EXT);
                        counter++;
                    }


                    Files.move(currentConfigFile, backupFile, StandardCopyOption.REPLACE_EXISTING);

                    String newConfigFileName = CONFIG_FILE_BASE + "_" + Grasses.MOD_VERSION + CONFIG_FILE_EXT;
                    Path newConfigFile = modConfigDir.resolve(newConfigFileName);
                    copyResourceToFile(newConfigFile);

                    Files.writeString(hashFile, resourceHash);
                    ModConfigStatus.setConfigUpdated(true);
                } else {
                    copyResourceToFile(currentConfigFile);
                    Files.writeString(hashFile, resourceHash);
                    ModConfigStatus.setConfigUpdated(false);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to update config file", e);
        }
    }

    private static String extractVersionFromFileName(String fileName) {
        if (fileName == null)
            return "unknown_version";

        int baseLength = CONFIG_FILE_BASE.length();
        int extIndex = fileName.lastIndexOf(CONFIG_FILE_EXT);

        if (!fileName.startsWith(CONFIG_FILE_BASE + "_") || extIndex == -1)
            return "unknown_version";

        return fileName.substring(baseLength + 1, extIndex);
    }

    private static void copyResourceToFile(Path targetFile) throws IOException {
        try (InputStream inputStream = ConfigFileHelper.class.getResourceAsStream(RESOURCE_PATH)) {
            if (inputStream == null)
                throw new IOException("Resource not found: " + RESOURCE_PATH);

            Files.copy(inputStream, targetFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static String computeResourceHash() throws IOException {
        try (InputStream inputStream = ConfigFileHelper.class.getResourceAsStream(RESOURCE_PATH)) {
            if (inputStream == null)
                return "";

            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] buffer = new byte[8192];
            int read;
            while ((read = inputStream.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }

            byte[] hashBytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            LOGGER.error("Error while computing resource hash", e);
            return "";
        }
    }

    private static String computeHash(Path file) throws IOException {
        try (InputStream inputStream = Files.newInputStream(file)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] buffer = new byte[8192];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
            byte[] hashBytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            LOGGER.error("Failed to compute hash for file: {}", file, e);
            throw new IOException(e);
        }
    }

    private static boolean isHashChanged(Path hashFile, String currentHash) {
        if (!Files.exists(hashFile))
            return true;
        try {
            String storedHash = Files.readString(hashFile);
            return !storedHash.equals(currentHash);
        } catch (IOException e) {
            LOGGER.error("Failed to read stored hash from file: {}, assuming hash changed.", hashFile, e);
            return true;
        }
    }
}
