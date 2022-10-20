package me.rivangek.goodshops.databases;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;

public class MySQL {
    public Database getDatabase(Map<String, Object> databaseConfig) {
        return new Database(databaseConfig);
    }

    private class Table {
        private final String tableName;
        private final Database tableDatabase;

        public Table(String name, Database db) {
            this.tableName = name;
            this.tableDatabase = db;
        }

        public Object getObject() {
            return null;
        }
    }

    private class Database {
        private HikariDataSource dataSource;
        private Connection dataConnection;

        public Database(Map<String, Object> databaseConfig) {
            HikariConfig config = new HikariConfig();
            config.addDataSourceProperty("serverName", databaseConfig.get("host"));
            config.addDataSourceProperty("port", databaseConfig.get("port"));
            config.addDataSourceProperty("databaseName", databaseConfig.get("database_name"));
            config.addDataSourceProperty("user", databaseConfig.get("user"));
            config.addDataSourceProperty("password", databaseConfig.get("password"));

            try {
                this.dataSource = new HikariDataSource(config);
                this.dataConnection = dataSource.getConnection();
            } catch (SQLException exception) {
                Bukkit.getServer().getLogger().log(Level.SEVERE, exception.getMessage());
            }
        }

        protected HikariDataSource getDataSource() {
            return this.dataSource;
        }

        protected Connection getDataConnection() {
            return this.dataConnection;
        }

        public void closeDataSource() {
            try {
                this.dataSource.close();
                this.dataConnection.close();
            } catch (SQLException exception) {
                Bukkit.getServer().getLogger().log(Level.SEVERE, exception.getMessage());
            }
        }

        public Table getTable() {
            return null;
        }
    }
}
