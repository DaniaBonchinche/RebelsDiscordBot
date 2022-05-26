package com.yuziak.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class BotEventDeleteGarbage extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String bossChanelName = "напоминалка";

        if (event.getChannel().getName().equals(bossChanelName)) {
            event.getMessage().delete().queueAfter(15, TimeUnit.MINUTES);
        }

    }
}
