package com.yuziak.listeners;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class BotEventRoleProvider extends ListenerAdapter {

    private final String bossChanelName = "напоминалка";
    private final String bossRoleName = "Босс";
    private final String sonilRoleName = "Сонил";

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {

        Role boss = event.getGuild().getRolesByName(bossRoleName, false).get(0);
        Role sonil = event.getGuild().getRolesByName(sonilRoleName, false).get(0);
        User user = event.getUser();

        if (event.getChannel().getName().equals(bossChanelName) && user != null) {
            if (event.getReactionEmote().getName().equals("\uD83E\uDD8E")) {
                event.getGuild().addRoleToMember(user, sonil).submit();
            } else if (event.getReactionEmote().getName().equals("\uD83D\uDC17")) {
                event.getGuild().addRoleToMember(user, boss).submit();
            }
        }

    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        Role boss = event.getGuild().getRolesByName(bossRoleName, false).get(0);
        Role sonil = event.getGuild().getRolesByName(sonilRoleName, false).get(0);
        User user = event.getUser();

        if (event.getChannel().getName().equals(bossChanelName) && user != null) {
            if (event.getReactionEmote().getName().equals("\uD83E\uDD8E")) {
                event.getGuild().removeRoleFromMember(user, sonil).submit();
            } else if (event.getReactionEmote().getName().equals("\uD83D\uDC17")) {
                event.getGuild().removeRoleFromMember(user, boss).submit();
            }
        }
    }
}
