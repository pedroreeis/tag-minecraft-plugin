package com.queendev.tag;

import com.queendev.tag.commands.TagCommand;
import com.queendev.tag.listeners.JoinEvent;
import com.queendev.tag.managers.ConfigurationManager;
import com.queendev.tag.managers.TagsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Tag extends JavaPlugin {

    public static Tag plugin;

    public static Tag getPlugin() {
        return plugin;
    }

    public ConfigurationManager config;
    public ConfigurationManager messages;
    public TagsManager manager;

    @Override
    public void onEnable() {
        plugin = this;

        config = new ConfigurationManager(null, "config.yml", false);
        messages = new ConfigurationManager(null,"messages.yml", false);

        manager = new TagsManager();

        getCommand("tag").setExecutor(new TagCommand());
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);

        getLogger().info("Plugin ativado com sucesso.");
    }

    @Override
    public void onDisable() {
        getLogger().severe("Plugin desativado com sucesso.");
    }
}
