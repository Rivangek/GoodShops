package me.rivangek.goodshops.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ColoredLogger {
    public void outputMessage(String messageToOutput) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', messageToOutput));
    }
}
