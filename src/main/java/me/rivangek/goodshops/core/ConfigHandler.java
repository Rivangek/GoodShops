package me.rivangek.goodshops.core;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ConfigHandler {
    private final JavaPlugin injectedPlugin;
    private final File injectedFile;

    private final Map<String, File> configurationFiles = new HashMap<>();

    public ConfigHandler(JavaPlugin pluginDependency, File fileDependency) {
        this.injectedPlugin = pluginDependency;
        this.injectedFile = fileDependency;

        // Handle initial files

        this.saveResourceFileTo("config.yml", "config.yml");
        this.saveResourceFileTo("messages.yml", "messages.yml");

        this.configurationFiles.put("config.yml", this.getFileFromDataFolder("config.yml"));
        this.configurationFiles.put("messages.yml", this.getFileFromDataFolder("messages.yml"));

        this.saveResourceFolderTo("categories", "categories");
    }

    public File getFileFromDataFolder(String filePath) {
        return new File(this.injectedPlugin.getDataFolder(), filePath);
    }

    public void saveResourceFileTo(String resourcePath, String destination) {
        InputStream streamResourceFile = this.injectedPlugin.getResource(resourcePath);
        File externalResourceFile = new File(this.injectedPlugin.getDataFolder(), destination);

        try {
            Files.copy(streamResourceFile, externalResourceFile.toPath());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void saveResourceFolderTo(String resourcePath, String destination) {
        try {
            ZipFile internalJAR = new ZipFile(this.injectedFile);
            Enumeration<? extends ZipEntry> internalJarEntries = internalJAR.entries();

            while (internalJarEntries.hasMoreElements()) {
                ZipEntry currentEntry = internalJarEntries.nextElement();

                if (currentEntry.getName().contains(resourcePath)) {
                    InputStream inputStream = internalJAR.getInputStream(currentEntry);
                    Files.copy(inputStream, this.getFileFromDataFolder(currentEntry.getName()).toPath());

                    inputStream.close(); // Probably redundant but whatever.
                }
            }

            internalJAR.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public YamlConfiguration getConfigurationFromFile(String fileName) {
        File file = this.configurationFiles.get(fileName);
        return YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getMenuConfiguration(String menuName) {
        File file = new File(this.injectedPlugin.getDataFolder(), "categories" + File.separator + menuName);
        return YamlConfiguration.loadConfiguration(file);
    }

    public String getMessageFormatted(String messageLocation) {
        String pluginPrefix = this.getConfigurationFromFile("messages.yml").getString("prefix");
        String targetMessage = this.getConfigurationFromFile("messages.yml").getString(messageLocation);

        return ChatColor.translateAlternateColorCodes(
                '&',
                targetMessage.replace("{prefix}", pluginPrefix)
        );
    }

    public String getMessageNoFormat(String messageLocation) {
        return this.getConfigurationFromFile("messages.yml").getString(messageLocation);
    }

    public Object getSectionFromConfig(String configLocation) {
        return this.getConfigurationFromFile("config.yml").getString(configLocation);
    }
}
