/*
 * PwnChat -- A Bukkit/Spigot plugin for multi-channel cross-server (via bungeecord) chat.
 * Copyright (c) 2013 Pwn9.com. Sage905 <ptoal@takeflight.ca>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 */

package com.pwn9.pwnchat.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Command;

import com.pwn9.pwnchat.Channel;
import com.pwn9.pwnchat.ChannelManager;
import com.pwn9.pwnchat.PwnChat;
import com.pwn9.pwnchat.commands.subcommands.dumpdata;
import com.pwn9.pwnchat.commands.subcommands.list;
import com.pwn9.pwnchat.commands.subcommands.listen;
import com.pwn9.pwnchat.commands.subcommands.reload;
import com.pwn9.pwnchat.commands.subcommands.silence;
import com.pwn9.pwnchat.commands.subcommands.talk;

/**
 * Main command handler for all /pr commands.
 */
public class pchat extends BaseCommandExecutor {

    public pchat(PwnChat instance) {
        super(instance);
        addSubCommand(new reload(instance));
        addSubCommand(new list(instance));
        addSubCommand(new listen(instance));
        addSubCommand(new silence(instance));
        addSubCommand(new talk(instance));
        addSubCommand(new dumpdata(instance));
    }

    @Override
    public void sendHelpMsg(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "PwnChat Commands:");
        super.sendHelpMsg(sender);
    }
}