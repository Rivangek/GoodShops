package me.rivangek.goodshops.databases;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class SQLite {
    private Connection currentConnection;

    public SQLite(JavaPlugin injectedPlugin) {
        File databaseFile = new File(injectedPlugin.getDataFolder(), "database.db");

        if (!databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException exception) {
               injectedPlugin.getLogger().log(
                       Level.SEVERE,
                       "There was an error while creating database.db from GoodShops\n" + exception.getMessage()
               );
            }
        }

        try {
            this.currentConnection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getPath());
        } catch (SQLException exception) {
            injectedPlugin.getLogger().log(
                    Level.SEVERE,
                    "There was an error while establishing connection with SQLite (GoodShops)\n" + exception.getMessage()
            );
        }
    }

    public Connection getConnection() {
        return this.currentConnection;
    }

    public Table getTable() {
        return null;
    }

    // Internal classes

    private class Table {

    }
}
