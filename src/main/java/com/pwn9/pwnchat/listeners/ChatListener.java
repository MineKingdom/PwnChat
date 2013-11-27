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

import java.util.Set;

import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import com.pwn9.pwnchat.Channel;
import com.pwn9.pwnchat.ChannelManager;
import com.pwn9.pwnchat.Chatter;
import com.pwn9.pwnchat.ChatterManager;
import com.pwn9.pwnchat.PwnChat;
import com.pwn9.pwnchat.utils.ChannelFormat;

public class ChatListener implements Listener {
	
	private PwnChat plugin;
    private boolean active;
	
	public ChatListener(PwnChat plugin) {
		this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener(plugin, this);

    }

//    @Override
    public String getShortName() { return "PWNCHAT"; }

    @EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChat(ChatEvent event) {
    	Connection sender = event.getSender();
    	
        if (event.isCancelled() || !(sender instanceof ProxiedPlayer)) {
        	return;
        }
        
        
        
        ProxiedPlayer p = (ProxiedPlayer) sender;
        String message = event.getMessage();
        Chatter chatter = ChatterManager.getInstance().getOrCreate(p);

        
        Channel c;
        
        if (event.isCommand()) {
        	String[] split = message.split(" ", 2);
        	if (split.length < 2) {
        		return;
        	}
        	if (split[0].length() == 1) {
        		c = ChannelManager.getInstance().shortcutLookup(message);
        	} else {
        		c = ChannelManager.getInstance().getChannel(split[0]);
        	}
        	message = split[1];
        } else {
            c = chatter.getFocus();
        }

        // If no channel, then return, as we're just going to let the local chat
        // handle it. TODO: Modify this when we take over formatting.
        if (c == null || c.equals(ChannelManager.getInstance().getLocal())) return;

        if (!p.hasPermission(c.getPermission())) {
            // They don't have permission for this channel anymore.
            // Remove them from the channel, and dump them back in
            // Local chat
            chatter.removeChannel(c);
            if (c != chatter.getFocus()) {
            	chatter.setFocus(c);
            } else {
            	chatter.setFocus(ChannelManager.getInstance().getLocal());
            }
            event.setCancelled(true);
            return;
        }
        String format = ChannelFormat.getFormat(p, c, plugin);

        event.setCancelled(true);
        if (c.isPrivateChannel()) {
            c.sendMessage(plugin,p.getDisplayName(),format,message);
        } else {
            for (ProxiedPlayer target : c.getRecipients()) {
            	target.sendMessage(String.format(format, p, message));
            }
            
        }
	}
}
