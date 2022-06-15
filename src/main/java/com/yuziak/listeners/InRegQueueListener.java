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
    final String[] excluded = {"\"PEN Blackstar Helmet\" RU", "\"PEN Blackstar Gloves\" RU", "\"PEN Blackstar Shoes\" RU", "\"PEN Blackstar Armor\" RU",};
    final static String[] AWAKENING_BS = {"\"PEN Blackstar Greatsword", "PEN Blackstar Vediant", "PEN Blackstar Jordun", "PEN Blackstar Gardbrace",
            "PEN Blackstar Crimson Glaives", "PEN Blackstar Celestial Bo Staff", "PEN Blackstar Iron Buster", "PEN Blackstar Scythe",
            "PEN Blackstar Kerispear", "PEN Blackstar Lancia", "PEN Blackstar Kamasylven Sword", "PEN Blackstar Godr Sphera",
            "PEN Blackstar Crescent Blade", "PEN Blackstar Sura Katana", "PEN Blackstar Sting", "PEN Blackstar Aad Sphera",
            "PEN Blackstar Greatbow", "PEN Blackstar Sah Chakram", "PEN Blackstar Cestus", "PEN Blackstar Kibelius", "PEN Blackstar Patraca",
            "PEN Blackstar Dual Glaives"
    };

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
                    for (String bsName : AWAKENING_BS) {
                        if (title.contains(bsName)) {
                            channelForBS.sendMessage("<@&" + auk.getId() + ">").submit();
                            channelForBS.sendMessageEmbeds(x).submit();
                        }
                    }
                }

            });

        }
    }

}
