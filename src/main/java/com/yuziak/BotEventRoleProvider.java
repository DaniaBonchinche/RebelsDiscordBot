package com.yuziak;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class BotEventRoleProvider extends ListenerAdapter {

    private final String bossChanelName = "сонилы-боссы";
    private final String bossRoleName = "Босс";
    private final String sonilChanelName = "Сонил";

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        Role boss = event.getGuild().getRolesByName(bossRoleName, false).get(0);
        Role sonil = event.getGuild().getRolesByName(sonilChanelName, false).get(0);
        if (event.getChannel().getName().equals(bossChanelName)) {
            if (event.getReactionEmote().getName().equals("\uD83E\uDD8E")) {
                event.getGuild().addRoleToMember(event.getUserId(), sonil).submit();
            } else if (event.getReactionEmote().getName().equals("\uD83D\uDC17")) {
                event.getGuild().addRoleToMember(event.getUserId(), boss).submit();
            }
        }

    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {
        Role boss = event.getGuild().getRolesByName(bossRoleName, false).get(0);
        Role sonil = event.getGuild().getRolesByName(sonilChanelName, false).get(0);
        if (event.getChannel().getName().equals(bossChanelName)) {
            if (event.getReactionEmote().getName().equals("\uD83E\uDD8E")) {
                event.getGuild().removeRoleFromMember(event.getUserId(), sonil).submit();
            } else if (event.getReactionEmote().getName().equals("\uD83D\uDC17")) {
                event.getGuild().removeRoleFromMember(event.getUserId(), boss).submit();
            }
        }
    }
}
