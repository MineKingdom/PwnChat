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
public class talk extends SubCommand {

    public talk(PwnChat instance) {
        super(instance, "talk", "pwnchat.talk");
        setUsage("talk <channel>");
        setDescription("Change your default channel.");
    }

	public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(PwnChat.PREFIX + " Only players can execute this command.");
            return;
        }
        Chatter chatter = ChatterManager.getInstance().getOrCreate((ProxiedPlayer) sender);

        if (args.length < 2) {
            sender.sendMessage(getUsage());
            return;
        }

        if (args[1].equalsIgnoreCase("local")) {
            chatter.setFocus(null);
            sender.sendMessage(PwnChat.PREFIX + " You are now talking in the local server channel.");
            return;
        }

        Channel channel = ChannelManager.getInstance().getChannel(args[1].toLowerCase());

        if (channel == null ) {
            sender.sendMessage(PwnChat.PREFIX + " Channel named: " + args[1] + " does not exist!");
            return;
        }

        if (channel.hasChatter(chatter)) {
            chatter.setFocus(channel);
            sender.sendMessage(PwnChat.PREFIX+" You are now talking in the "+ channel.getName() + " channel.");
        } else {
            sender.sendMessage(PwnChat.PREFIX + " You must be listening to the channel before you can talk in it!");
        }

    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return ChannelManager.getInstance().getCompletions(sender, args);
    }

}
