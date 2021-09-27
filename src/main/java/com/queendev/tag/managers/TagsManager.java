package com.queendev.tag.managers;

import com.queendev.tag.Tag;
import com.queendev.tag.models.PlayerTagModel;
import com.queendev.tag.models.TagModel;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class TagsManager {

    private final FileConfiguration config;

    private HashMap<String, TagModel> tags;
    private HashMap<String, PlayerTagModel> users;

    public TagsManager(FileConfiguration config) {
        this.config = config;

        tags = new HashMap<>();
        users = new HashMap<>();

    }

    public PlayerTagModel getPlayerOrCreate(Player p) {
        String nick = p.getName().toLowerCase();
        if(users.containsKey(nick)) {
            return users.get(nick);
        }

        PlayerTagModel user = new PlayerTagModel(p.getName());
        return user;
    }

    public void update(Player p) {
        TagModel newTag = null;
        PlayerTagModel playerTag = getPlayerOrCreate(p);

        for(TagModel tag : tags.values()) {
            if(p.hasPermission(tag.getPermission())) {
                playerTag.setTag(tag);
                newTag = tag;
            }
        }
    }

    public void loadTags() {
        ConfigurationSection section = config.getConfigurationSection("Tags");
        if (section != null) {
            tags.clear();

            for (String id : section.getKeys(false)) {
                String path = "Tags." + id + ".";

                ConfigurationSection key = config.getConfigurationSection("Tags." + id);

                String prefix = key.getString("prefix").replace("&", "§");
                String permission = key.getString("permission");
                String position = key.getString("position");

                TagModel tag = new TagModel(id);
                tag.setPrefix(prefix);
                tag.setPermission(permission);
                tag.setPosition(position);
                tags.put(tag.getName().toLowerCase(), tag);
            }
        } else {
            Bukkit.getConsoleSender().sendMessage("§cConfig.yml está incompleta, delete e gere uma nova!!!!");
            Bukkit.getPluginManager().disablePlugin(Tag.getPlugin());
        }
    }

    public TagModel getTag(String name) {
        return tags.get(name.toLowerCase());
    }

    public boolean containsTag(String name) {
        return tags.containsKey(name.toLowerCase());
    }

    public HashMap<String, TagModel> getTags() {
        return tags;
    }
}
