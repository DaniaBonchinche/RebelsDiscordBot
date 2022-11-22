package com.yuziak.listeners;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;


public class InRegQueueListener extends ListenerAdapter {

    final String GARMOTH_CHANELL = "все-лоты";
    final String BLACKSTAR_MAIN_CHANELL = "бс-меин-аук";
    final String BLACKSTAR_MAIN4_CHANELL = "глаз-родэ-4";
    final String BLACKSTAR_AWA_CHANELL = "бс-пробуда-аук";
    final String guildName = "ФПСеры";
    final String aukRoleNameRoleName = "Аук";
    final String[] excluded = {"\"PEN Blackstar Helmet\" RU", "\"PEN Blackstar Gloves\" RU", "\"PEN Blackstar Shoes\" RU", "\"PEN Blackstar Armor\" RU",};
    final static String BS = "Blackstar ";
    final static String Godr_Ayed = "Godr-Ayed ";
    final static String PLUS5 = "+5 ";
    final static String PLUS4 = "+4 ";
    final static String PEN = "PEN: ";

    final static String[] AWAKENING = {"\"Greatsword", "Vediant", "Jordun", "Gardbrace", "Crimson Glaives", "Celestial Bo Staff", "Iron Buster", "Scythe",
            "Kerispear", "Lancia", "Kamasylven Sword", "Godr Sphera", "Crescent Blade", "Sura Katana", "Sting", "Aad Sphera", "Greatbow", "Sah Chakram",
            "Cestus", "Kibelius", "Patraca", "Dual Glaives"
    };
    final static String[] Main = {"Longsword", "Longbow", "Amulet", "Axe", "Shortsword", "Blade", "Staff", "Kriegsmesser", "Gauntlet", "Crescent Pendulum",
            "Crossbow", "Florang", "Battle Axe", "Shamshir", "Morning Star", "Kyve", "Serenaca", "Slayer"};

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        TextChannel channelBsMain = event.getGuild().getTextChannelsByName(BLACKSTAR_MAIN_CHANELL, true).get(0);
        TextChannel channelBsMain4 = event.getGuild().getTextChannelsByName(BLACKSTAR_MAIN4_CHANELL, true).get(0);
        TextChannel channelBsAwa = event.getGuild().getTextChannelsByName(BLACKSTAR_AWA_CHANELL, true).get(0);

        Role auk = event.getJDA().getGuildsByName(guildName, false).get(0).getRolesByName(aukRoleNameRoleName, false).get(0);

        if (event.getChannel().getName().equals(GARMOTH_CHANELL)) {
            List<MessageEmbed> messagesEmbed = event.getMessage().getEmbeds();
            messagesEmbed.forEach(message -> {

                MessageEmbed.AuthorInfo info = message.getAuthor();
                if (info != null) {
                    String title = info.getName();

                    Arrays.stream(AWAKENING).map(name ->PEN + BS + name).forEach(name -> send(channelBsAwa, auk, message, title, name));
                    Arrays.stream(AWAKENING).map(name ->PLUS5 + Godr_Ayed + name).forEach(name -> send(channelBsAwa, auk, message, title, name));

                    Arrays.stream(Main).map(name ->PEN + BS + name).forEach(name -> send(channelBsMain, auk, message, title, name));
                    Arrays.stream(Main).map(name ->PLUS5 + Godr_Ayed + name).forEach(name -> send(channelBsMain, auk, message, title, name));

                    Arrays.stream(Main).map(name ->PLUS4 + Godr_Ayed + name).forEach(name -> send(channelBsMain4, auk, message, title, name));

                }
            });
        }
    }

    private void send(TextChannel channel, Role auk, MessageEmbed massage, String title, String itemName) {
        if (title.contains(itemName)) {
            channel.sendMessage("<@&" + auk.getId() + ">").submit();
            channel.sendMessageEmbeds(massage).submit();
        }
    }
}
