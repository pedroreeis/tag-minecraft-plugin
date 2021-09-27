package com.queendev.tag.commands;

import com.queendev.tag.Tag;
import com.queendev.tag.managers.TagsManager;
import com.queendev.tag.models.PlayerTagModel;
import com.queendev.tag.models.TagModel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TagCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        FileConfiguration config = Tag.getPlugin().messages.getConfig();

        if(!(sender instanceof Player)) {
            sender.sendMessage(config.getString("Mensagens.errorConsole").replace("&", "§"));
            return false;
        }

        Player p = (Player)sender;

        if(cmd.getName().equalsIgnoreCase("tag")) {
            if(args.length == 0) {
                p.sendMessage(config.getString("Mensagens.noArgs").replace("&", "§"));
                return false;
            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("list")) {
                    HashMap<String, TagModel> tags = Tag.getPlugin().getManager().getTags();
                    p.sendMessage("§fSuas tags:");
                    for(TagModel tag : tags.values()) {
                        if(p.hasPermission(tag.getPermission())) {
                            p.sendMessage(tag.getPrefix());
                        }
                    }
                    return false;
                }

                HashMap<String, TagModel> tags = Tag.getPlugin().getManager().getTags();
                for(TagModel tag : tags.values()) {
                    if(args[0].equalsIgnoreCase(tag.getName())) {
                        if(p.hasPermission(tag.getPermission())) {
                            p.sendMessage(config.getString("Mensagens.sucessoChange").replace("&", "§").replace("%tag%", tag.getPrefix()));
                            Tag.getPlugin().manager.getPlayerOrCreate(p).setTag(tag);
                        }else {
                            p.sendMessage(config.getString("Mensagens.noPermission").replace("&", "§"));
                        }
                    }
                }
            }
        }
        return true;
    }
}
