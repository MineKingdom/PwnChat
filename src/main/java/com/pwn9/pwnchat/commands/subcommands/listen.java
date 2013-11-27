/*
 * PwnChat -- A Bukkit/Spigot plugin for multi-channel cross-server (via bungeecord) chat.
 * Copyright (c) 2013 Pwn9.com. Sage905 <ptoal@takeflight.ca>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 */

package com.pwn9.pwnchat.commands.subcommands;

import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import com.pwn9.pwnchat.Channel;
import com.pwn9.pwnchat.ChannelManager;
import com.pwn9.pwnchat.Chatter;
import com.pwn9.pwnchat.ChatterManager;
import com.pwn9.pwnchat.PwnChat;
import com.pwn9.pwnchat.commands.SubCommand;

/**
 * List Channels
 * User: ptoal
 * Date: 13-07-19
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class listen extends SubCommand {

    public listen(PwnChat instance) {
        super(instance, "listen", "pwnchat.listen");
        setUsage("listen <channel>");
        setDescription("Listen to a channel.");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(PwnChat.PREFIX + " Only players can execute this command.");
            return;
        }

        if (args.length < 2) {
            sender.sendMessage(getUsage());
            return;
        }

        if (args[1].equalsIgnoreCase("local")) {
            sender.sendMessage(PwnChat.PREFIX+" You always listen to the local server channel.");
        }

        Chatter chatter = ChatterManager.getInstance().getOrCreate((ProxiedPlayer) sender);
        Channel channel = ChannelManager.getInstance().getChannel(args[1].toLowerCase());

        if (channel == null ) {
            sender.sendMessage(PwnChat.PREFIX + " Channel named: " + args[1] + " does not exist!");
            return;
        }


        if (channel.hasChatter(chatter)) {
            sender.sendMessage(PwnChat.PREFIX + " You are already listening to that channel!");
            return;
        }

        if (chatter.addChannel(channel)) {
            sender.sendMessage(PwnChat.PREFIX + " You will now hear chat from the '" + channel.getName() + "' channel.");
        } else {
            sender.sendMessage(PwnChat.PREFIX + " You aren't allowed to listen to the '" + channel.getName() + "' channel!");
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return ChannelManager.getInstance().getCompletions(sender, args);
    }
}
