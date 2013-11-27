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

import net.md_5.bungee.api.CommandSender;

import com.pwn9.pwnchat.PwnChat;
import com.pwn9.pwnchat.commands.SubCommand;

/**
 * Reload configs
 * User: ptoal
 * Date: 13-07-19
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class reload extends SubCommand {

    public reload(PwnChat instance) {
        super(instance, "reload", "pwnchat.reload");
        setUsage("reload");
        setDescription("Reload config.");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        plugin.reloadConfig();
        sender.sendMessage(PwnChat.PREFIX + " Reloaded configuration.");
    }

}
