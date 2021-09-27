package com.queendev.tag.models;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerTagModel {

    private String name;
    private TagModel tag;
    Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

    public PlayerTagModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagModel getTag() {
        return tag;
    }

    public void setTag(TagModel tag) {
        Player p = Bukkit.getPlayer(name);
        Team team = board.registerNewTeam(tag.getPosition());
        team.setPrefix(tag.getPrefix());
        team.addEntry(p.getName());
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        p.setDisplayName(tag.getPrefix() + p.getName());

        p.setScoreboard(board);
        this.tag = tag;
    }
}
