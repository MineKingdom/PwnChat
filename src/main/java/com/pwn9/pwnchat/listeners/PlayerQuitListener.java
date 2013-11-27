/*
 * PwnChat -- A Bukkit/Spigot plugin for multi-channel cross-server (via bungeecord) chat.
 * Copyright (c) 2013 Pwn9.com. Sage905 <ptoal@takeflight.ca>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 */

package com.pwn9.pwnchat.listeners;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import com.pwn9.pwnchat.Chatter;
import com.pwn9.pwnchat.ChatterManager;
import com.pwn9.pwnchat.PwnChat;
import com.pwn9.pwnchat.utils.LogManager;

/**
 * Listen for Player join events and set up their default channels.
 * User: ptoal
 * Date: 13-07-15
 * Time: 10:14 PM
 */
public class PlayerQuitListener implements Listener {
    private final PwnChat plugin;

    public PlayerQuitListener(PwnChat instance) {
        plugin = instance;
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerDisconnectEvent event) {

        LogManager.getInstance().debugMedium("Removing Player: " + event.getPlayer().getName());
        Chatter chatter = ChatterManager.getInstance().getOrCreate(event.getPlayer());
        chatter.remove();
    }
}
