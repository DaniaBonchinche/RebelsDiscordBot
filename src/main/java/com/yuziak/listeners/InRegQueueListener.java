package com.yuziak.listeners;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class InRegQueueListener extends ListenerAdapter {

    final String GARMOTH_CHANELL = "все-лоты";
    final String BLACKSTAR_CHANELL = "бс-на-ауке";
    final String guildName = "ФПСеры";
    final String aukRoleNameRoleName = "Аук";

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        TextChannel channelForBS = event.getGuild().getTextChannelsByName(BLACKSTAR_CHANELL, true).get(0);
        Role auk = event.getJDA().getGuildsByName(guildName, false).get(0).getRolesByName(aukRoleNameRoleName, false).get(0);

        if (event.getChannel().getName().equals(GARMOTH_CHANELL)) {
            List<MessageEmbed> messagesEmbed = event.getMessage().getEmbeds();
            messagesEmbed.forEach(x -> {
                MessageEmbed.AuthorInfo info = x.getAuthor();
                if (info != null) {
                    String title = info.getName();
                    System.out.println(title);
                    if (title.contains("PEN Blackstar")) {
                        channelForBS.sendMessage("<@&" + auk.getId() + ">").submit();
                        channelForBS.sendMessageEmbeds(x).submit();
                    }
                }
            });

        }
    }


}
