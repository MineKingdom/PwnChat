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

import net.md_5.bungee.api.ChatColor;
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
public class list extends SubCommand {

    public list(PwnChat instance) {
        super(instance, "list", "pwnchat.list");
        setUsage("list");
        setDescription("List available channels.");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        sender.sendMessage(ChatColor.GOLD + "Available Chat Channels "
                +ChatColor.RED + "[*Talk]"
                +ChatColor.GREEN+"[Listening]"
                +ChatColor.BLUE+"[Silenced]");
        sender.sendMessage(ChatColor.WHITE + "+-------------------------------------------+");

        Chatter chatter = null;

        if (sender instanceof ProxiedPlayer) {
            chatter = ChatterManager.getInstance().getOrCreate((ProxiedPlayer) sender);
        }

        String prefix;

        for (Channel channel : ChannelManager.getInstance().getChannelList()) {
            if (channel.hasPermission(chatter)) {
                prefix = ChatColor.BLUE + "[S] ";
                if (channel.hasChatter(chatter)) {
                    prefix = ChatColor.GREEN + "[L] ";
                    if (chatter != null && chatter.isFocused(channel)) {
                        prefix = ChatColor.RED + "[*] ";
                    }
                }
                prefix = prefix + "("+channel.getPrefix()+") ";
                sender.sendMessage(" " + prefix + channel.getName() + " - " + ChatColor.WHITE + channel.getDescription());
            }
        }
    }

}
