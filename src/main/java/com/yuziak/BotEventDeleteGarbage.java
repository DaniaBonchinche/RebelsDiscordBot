package com.yuziak;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class BotEventDeleteGarbage extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if(event.getMessage().getAuthor().isBot()&&event.getChannel().getName().equals("напоминалка")){
            event.getMessage().delete().queueAfter(15, TimeUnit.MINUTES);
        }

        if(event.getChannel().getName().equals("боттест")){
            event.getMessage().delete().queueAfter(15, TimeUnit.MINUTES);
        }
    }
}
