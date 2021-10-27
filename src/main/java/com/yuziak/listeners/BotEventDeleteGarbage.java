package com.yuziak.listeners;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class BotEventDeleteGarbage extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        final String bossRoleName = "Босс";
        final String sonilRoleName = "Сонил";
        final String himkalRoleName = "Химка";

        String bossChanelName = "напоминалка";
        if (event.getMessage().getAuthor().isBot() && event.getChannel().getName().equals(bossChanelName)) {
            System.out.println(event.getMessage().getContentDisplay());
            if (event.getMessage().getContentDisplay().contains("рес" )) {
                event.getMessage().delete().queueAfter(15, TimeUnit.MINUTES);
            }else if (event.getMessage().getContentDisplay().contains("сонилов")) {
                event.getMessage().delete().queueAfter(60, TimeUnit.MINUTES);
            }else if (event.getMessage().getContentDisplay().contains("ребафни")) {
                event.getMessage().delete().queueAfter(1, TimeUnit.MINUTES);
            } else if (event.getMessage().getContentDisplay().contains("!")) {
                event.getMessage().delete().queueAfter(1, TimeUnit.MINUTES);
            } else{
                event.getMessage().delete().queueAfter(10, TimeUnit.SECONDS);
            }
        }

        String botUpdateGearName = "боттест";
        if (event.getChannel().getName().equals(botUpdateGearName)) {
            event.getMessage().delete().queueAfter(15, TimeUnit.MINUTES);
        }

    }
}
