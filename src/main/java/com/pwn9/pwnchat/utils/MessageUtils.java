package com.pwn9.pwnchat.utils;

import java.util.Collection;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MessageUtils {
	
	public static void sendToAll(String message) {
		
		Collection<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers();
		
		
		for (ProxiedPlayer player : players) {
			player.sendMessage(message);
		}
	}

}
