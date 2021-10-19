package com.yuziak;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class BotEventListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getChannel().getName().equals("основной")) {
            System.out.println(event.getAuthor().getName());
            System.out.println(event.getChannel().getName());

        }
    }
}
