package com.example.eventplugin;

import com.example.eventplugin.commands.EventCommand;
import com.example.eventplugin.commands.TeamCommand;
import io.papermc.paper.command.brigadier.BasicCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class EventPlugin extends JavaPlugin {
    private static EventPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        EventCommand eventCmd = new EventCommand();
        registerCommand("event", eventCmd);

        TeamCommand teamCmd = new TeamCommand();
        registerCommand("blau", teamCmd);
        registerCommand("rot", teamCmd);
        registerCommand("blue", teamCmd);
        registerCommand("red", teamCmd);

        getLogger().info("EventTeamPlugin aktiviert!");
    }

    @Override
    public void onDisable() {
        getLogger().info("EventTeamPlugin deaktiviert!");
    }

    public static EventPlugin getInstance() {
        return instance;
    }
}
