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

    private TagModel defaultTag;
    private Scoreboard mainScore;

    public TagsManager(FileConfiguration config) {
        this.config = config;

        tags = new HashMap<>();
        users = new HashMap<>();

        mainScore = Bukkit.getScoreboardManager().getMainScoreboard();
    }

    public void runUpdateTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    update(player.getScoreboard(), player);
                }
            }
        }.runTaskTimerAsynchronously(Tag.getPlugin(), 0L, 20L * 60L);
    }

    public PlayerTagModel getPlayerOrCreate(Player p) {
        String nick = p.getName().toLowerCase();
        if(users.containsKey(nick)) {
            return users.get(nick);
        }

        PlayerTagModel user = new PlayerTagModel(p.getName());
        user.setTag(defaultTag);
        return user;
    }

    public void update(Scoreboard sb,Player p) {
        TagModel newTag = null;
        PlayerTagModel playerTag = getPlayerOrCreate(p);

        for(TagModel tag : tags.values()) {
            if(p.hasPermission(tag.getPermission())) {
                playerTag.setTag(tag);
                tag.getTeam().addPlayer(p);
                newTag = tag;
            }
        }

        if(newTag == null) playerTag.setTag(defaultTag);

        p.setDisplayName(playerTag.getTag().getPrefix() + p.getName());
    }

    public void loadTags() {
        ConfigurationSection section = config.getConfigurationSection("Tags");
        if (section != null) {
            tags.clear();

            for (String id : section.getKeys(false)) {
                String path = "Tags." + id + ".";
                id = id.toLowerCase();

                ConfigurationSection key = config.getConfigurationSection("Tags." + id);

                String prefix = key.getString("prefix").replace("&", "§");
                String permission = key.getString("permission");
                String position = key.getString("position");


                Team teamScore = mainScore.registerNewTeam(position);
                teamScore.setPrefix(prefix);
                teamScore.setNameTagVisibility(NameTagVisibility.ALWAYS);

                TagModel tag = new TagModel(id);
                tag.setPrefix(prefix);
                tag.setPermission(permission);
                tag.setPosition(position);
                tag.setTeam(teamScore);
                tags.put(tag.getName(), tag);
            }

            TagModel defaultTag = getTag(config.getString("defaultTag"));
            if (defaultTag != null) {
                this.defaultTag = defaultTag;
            } else {
                Bukkit.getConsoleSender().sendMessage("§cNão foi identificado nada em 'defaultTag' na config.yml, coloque o grupo padrão para solucionar este problema!");
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
