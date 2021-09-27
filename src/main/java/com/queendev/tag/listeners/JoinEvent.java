package com.queendev.tag.listeners;

import com.queendev.tag.Tag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    void playerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Tag.getPlugin().getManager().update(p);
    }
}
