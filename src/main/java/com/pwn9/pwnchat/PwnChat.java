/*
 * PwnChat -- A Bukkit/Spigot plugin for multi-channel cross-server (via bungeecord) chat.
 * Copyright (c) 2013 Pwn9.com. Sage905 <ptoal@takeflight.ca>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 */

package com.pwn9.pwnchat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.protocol.packet.Chat;

import com.pwn9.pwnchat.commands.pchat;
import com.pwn9.pwnchat.config.PwnChatConfig;
import com.pwn9.pwnchat.listeners.ChatListener;
import com.pwn9.pwnchat.listeners.PlayerJoinListener;
import com.pwn9.pwnchat.listeners.PlayerQuitListener;
import com.pwn9.pwnchat.utils.LogManager;

public class PwnChat extends Plugin {

	private Chat chat = null;
	private PwnChatConfig config;
    private LogManager logManager;

    public static final String PREFIX = ChatColor.YELLOW + "[PwnChat]";

    @Override
    public void onLoad() {
        LogManager.getInstance(getLogger(), getDataFolder());
    }

    public void reloadConfig() {
        getProxy().getPluginManager().unregisterListeners(this);
        ChannelManager.getInstance().setupChannels(this, config);
        setupLog();
        registerListeners();
    }
    
    public void onEnable() {

        try {
            config = new PwnChatConfig(this);
            config.init();
        } catch (Exception ex) {
            getLogger().severe("Failed to load configuration. " + ex.getMessage());
            return;
        }

        setupLog();

        ChannelManager.getInstance().setupChannels(this, config);

        getProxy().getPluginManager().registerCommand(this, new pchat(this));

        registerListeners();
	}

    public void setupPwnFilter() {
    if (getProxy().getPluginManager().getPlugin("PwnFilter") != null) {

    }
    getLogger().info("PwnFilter Dependency not found.  Disabling chat filtering.");
    }

    private void registerListeners() {
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);

        ChatListener cl = new ChatListener(this);
    }

    private void setupLog() {
        logManager = LogManager.getInstance();
        logManager.stop();
        LogManager.DebugModes dm;
        try {
            dm = LogManager.DebugModes.valueOf(config.Settings_debug);
        } catch (IllegalArgumentException ex ) {
            dm = LogManager.DebugModes.off;
        }
        logManager.setDebugMode(dm);
        logManager.start("pwnchat.log");
    }
}
