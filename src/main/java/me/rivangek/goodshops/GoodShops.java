package me.rivangek.goodshops;

import me.rivangek.goodshops.core.ConfigHandler;
import me.rivangek.goodshops.core.EconomyHandler;
import me.rivangek.goodshops.databases.MySQL;
import me.rivangek.goodshops.databases.SQLite;
import org.bukkit.plugin.java.JavaPlugin;

import me.rivangek.goodshops.core.ColoredLogger;

import java.util.Map;

public final class GoodShops extends JavaPlugin {
    private static JavaPlugin currentPlugin;
    private static ColoredLogger coloredLogger;

    private static ConfigHandler configHandler; // Configuration handler.
    private static EconomyHandler economyHandler; // Economy wrapper for Vault//GoodCurrencies

    private static Object currentWorkingDatabase; // Current working database. Relies on the database implementation specified in config.yml
    public static String currentDataMethod; // Tells other components which method of data-saving it is using

    // Methods

    public void onEnable() {
        // Initial Welcome Message
        coloredLogger = new ColoredLogger();
        coloredLogger.outputMessage("&8(&aGoodShops&8)&r Initializing plugin, thanks for using!");

        currentPlugin = this;

        // Start Handler
        economyHandler = new EconomyHandler(); // Economy wrapper.
        configHandler = new ConfigHandler(this, this.getFile()); // Configuration API

        // Database Initialization
        String dataSavingMethod = (String) configHandler.getSectionFromConfig("data_saving");

        switch(dataSavingMethod) {
            case "MySQL":
                currentDataMethod = "MySQL";
                currentWorkingDatabase = new MySQL().getDatabase(
                        (Map<String, Object>) configHandler.getSectionFromConfig("sql_database")
                );

                break;
            case "SQLite":
                currentDataMethod = "SQLite";
                currentWorkingDatabase = new SQLite(currentPlugin);
        }
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    // Getters

    public static Object getCurrentWorkingDatabase() {
        return currentWorkingDatabase;
    }

    public static EconomyHandler getEconomyHandler() {
        return economyHandler;
    }

    public static ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public static ColoredLogger getColoredLogger() {
        return coloredLogger;
    }
}
