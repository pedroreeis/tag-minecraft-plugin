package com.queendev.tag.commands;

import com.queendev.tag.Tag;
import com.queendev.tag.models.TagModel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TagCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;

            if(cmd.getName().equalsIgnoreCase("tag")) {
                if(args.length == 0) {
                    HashMap<String, TagModel> tags = Tag.getPlugin().getManager().getTags();

                    for(TagModel tag : tags.values()) {
                        if(p.hasPermission(tag.getPermission())) {
                            p.sendMessage(tag.getPrefix());
                        }
                }
            }
            }
        }else {
            sender.sendMessage("§cEste comando é para apenas jogadores.");
            return false;
        }
        return true;
    }
}
