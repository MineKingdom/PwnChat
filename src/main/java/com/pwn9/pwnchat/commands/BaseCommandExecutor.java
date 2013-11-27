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

/**
 * PwnRewards main command executor.  This executor is designed to handle the
 * /pwnrewards command.  It will provide basic help for players and full help
 * for admins.
 * User: ptoal
 * Date: 13-07-01
 * Time: 4:19 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import com.pwn9.pwnchat.PwnChat;

public class BaseCommandExecutor extends Command implements TabExecutor {
    protected final PwnChat plugin;
    private final Map<String,SubCommand> subCommands;

    public BaseCommandExecutor(PwnChat instance){
    	super("pchat", null, "pc");
        plugin = instance;
        subCommands = new HashMap<String, SubCommand>();
    }

    public void addSubCommand(SubCommand subCommand) {
        if (subCommand == null) {
            throw new IllegalArgumentException("SubCommand was null.");
        }
        subCommands.put(subCommand.getName(), subCommand);
    }

    @Override
    public void execute (final CommandSender sender, final String[] args) {
        if (args.length < 1) {
            sendHelpMsg(sender);
            return;
        }

        SubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand == null) {
            sendHelpMsg(sender);
        } else {
            if (subCommand.getPermission() == null || sender.hasPermission(subCommand.getPermission())) {
                subCommand.execute(sender, args);
            } else {
                sender.sendMessage(PwnChat.PREFIX+" You don't have permission to do that.");
            }
        }
    }

    public void sendHelpMsg(CommandSender sender) {

        ArrayList<SubCommand> availableCommands = new ArrayList<SubCommand>();

        for ( SubCommand subCommand : subCommands.values()) {
            if (subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission())) continue;
            if (!subCommand.getHelpMessage().isEmpty()) {
                availableCommands.add(subCommand);
            }
        }

        if (availableCommands.size() != 0 ) {
            sender.sendMessage("Available commands for pchat :");

            for ( SubCommand subCommand : availableCommands) {
                sender.sendMessage("/pchat " + subCommand.getHelpMessage());
            }
        }
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null to default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<String>();

        if (args.length == 1 ) {
            for ( SubCommand subCommand : subCommands.values() ) {
                if (!subCommand.getName().startsWith(args[0])) continue;

                if (subCommand.getPermission() == null ||
                        sender.hasPermission(subCommand.getPermission())) {
                    completions.add(subCommand.getName());
                }
            }
        } else if (args.length > 1) {
            SubCommand subCommand = subCommands.get(args[0].toLowerCase());
            if ( subCommand != null ) {
                return subCommand.tabComplete(sender, args);
            }
        }

        if (completions.isEmpty()) return null;

        return completions;
    }
}
