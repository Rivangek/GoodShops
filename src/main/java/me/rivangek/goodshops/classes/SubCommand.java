package me.rivangek.goodshops.classes;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    public abstract String getCommandName();
    public abstract String getPermission();

    public abstract boolean executeCommand(CommandSender sender, String[] args);
    public abstract boolean tabCompleteCommand(CommandSender sender, String[] args);
}
