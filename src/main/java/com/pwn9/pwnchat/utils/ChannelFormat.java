package com.pwn9.pwnchat.utils;

import java.text.MessageFormat;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import com.pwn9.pwnchat.Channel;
import com.pwn9.pwnchat.PwnChat;

public class ChannelFormat {
    public static String getFormat(ProxiedPlayer p, Channel c, PwnChat plugin) {
    	Object[] format = { "", "", "", "", "", 
    			c.getPrefix()
    	};
        return c.getFormat().format(format);
    }
}
