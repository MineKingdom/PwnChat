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

import java.util.Collections;
import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import com.pwn9.pwnchat.PwnChat;

/**
 * SubCommand adds the plugin to the instance of Command.
 * User: ptoal
 * Date: 13-08-16
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */

public abstract class SubCommand extends Command {

    protected final PwnChat plugin;
	private String description = "";
	private String usage = "";

    public SubCommand(PwnChat plugin, String name, String permission) {
        super(name, permission);
        this.plugin = plugin;
    }

    public String getHelpMessage() {
        String message = "";
        if (!getUsage().isEmpty()) message = getUsage();
        if (!getDescription().isEmpty()) {
            if (!message.isEmpty()) {
                message = message + " -- " + getDescription();
            } else {
                message = getDescription();
            }
        }
        return message;
    }

	public String getDescription() {
		return "";
	}

	public String getUsage() {
		return "";
	}

	public void setDescription(String desc) {
		this.description = desc == null ? "" : desc;
	}

	public void setUsage(String usage) {
		this.usage = usage == null ? "" : usage;
	}

	public List<String> tabComplete(CommandSender sender, String[] args) {
		return Collections.emptyList();
	}
}
