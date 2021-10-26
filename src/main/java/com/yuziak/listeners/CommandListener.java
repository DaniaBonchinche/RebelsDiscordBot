package com.yuziak.listeners;

import com.yuziak.App;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class CommandListener extends ListenerAdapter {

    public CommandListener(App app){
        this.app=app;
    }

    private final App app;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String channelName = "comands";
        if (event.getChannel().getName().equals(channelName)){
            if(event.getMessage().getContentDisplay().startsWith("!testSonil")){
                String timePattern = "HH:mm:ss";
                String time = LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusSeconds(3).format(DateTimeFormatter.ofPattern(timePattern));
                this.app.setTestTime(time);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.app.setTestTime("19:00:00");
            }

        }
    }
}
