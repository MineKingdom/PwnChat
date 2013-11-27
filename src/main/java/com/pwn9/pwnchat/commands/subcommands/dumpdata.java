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

import java.util.logging.Logger;

import net.md_5.bungee.api.CommandSender;

import com.pwn9.pwnchat.Channel;
import com.pwn9.pwnchat.ChannelManager;
import com.pwn9.pwnchat.Chatter;
import com.pwn9.pwnchat.ChatterManager;
import com.pwn9.pwnchat.PwnChat;
import com.pwn9.pwnchat.commands.SubCommand;
import com.pwn9.pwnchat.utils.LogManager;

/**
 * Reload configs
 * User: ptoal
 * Date: 13-07-19
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class dumpdata extends SubCommand {

    public dumpdata(PwnChat instance) {
        super(instance, "dumpdata", "pwnchat.debug");
        setUsage("dumpdata");
        setDescription("Dump internal data to logfile.");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Logger l = LogManager.logger;
        for (Channel c : ChannelManager.getInstance().getChannelList()) {
            l.info("Channel: " + c.getName() + " Listeners: " + c.getRecipients().size());
        }
        for (Chatter chatter : ChatterManager.getInstance().getAll()) {
            StringBuilder sb = new StringBuilder();
            for (Channel chan : chatter.getChannels()) {
                sb.append(" " + chan.getName());
            }
            l.info("  Chatter: " + chatter.getPlayerName() + " Talking: " + chatter.getFocus().getName());
            l.info("   Other Channels:" + sb.toString());
        }

        sender.sendMessage(PwnChat.PREFIX + " Check pwnchat.log for data.");
    }

}
