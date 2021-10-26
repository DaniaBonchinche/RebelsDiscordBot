package com.yuziak.listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class BotEventDeleteGarbage extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String bossChanelName = "сонилы-боссы";
        if(event.getMessage().getAuthor().isBot()&&event.getChannel().getName().equals(bossChanelName)){
            event.getMessage().delete().queueAfter(15, TimeUnit.MINUTES);
        }

        String botUpdateGearName = "боттест";
        if(event.getChannel().getName().equals(botUpdateGearName)){
            event.getMessage().delete().queueAfter(15, TimeUnit.MINUTES);
        }

    }
}
