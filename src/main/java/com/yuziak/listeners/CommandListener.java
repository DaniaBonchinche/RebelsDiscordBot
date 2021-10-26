package com.yuziak.listeners;

import com.yuziak.App;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class CommandListener extends ListenerAdapter {

    public CommandListener(App app) {
        this.app = app;
    }

    private final App app;
    private int himkasLopps = 4;
    private boolean death = false;
    final String bossChanelName = "сонилы-боссы";
    private TextChannel channelToRemind;
    private Role himka;
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)  {
        if (event.getMessage().getContentDisplay().startsWith("!")) {
            String channelName = "comands";
            String himkalRoleName = "Химка";
            channelToRemind = event.getGuild().getTextChannelsByName(bossChanelName, true).get(0);
            himka = event.getGuild().getRolesByName(himkalRoleName, false).get(0);

            if (event.getChannel().getName().equals(channelName)) {
                if (event.getMessage().getContentDisplay().startsWith("!testSonil")) {
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
                if (event.getMessage().getContentDisplay().startsWith("!himka start")) {
                    this.himkasLopps = 4;
                    try {
                       // call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (event.getMessage().getContentDisplay().startsWith("!himka stop")) {
                    this.himkasLopps = 0;
                }
                if (event.getMessage().getContentDisplay().startsWith("!death")) {
                    death = true;
                    himkasLopps++;
                    try {
                        //call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }


    public void method() {
        do {
            himkasLopps--;
            channelToRemind.sendMessage("<@&" + himka.getId() + "> ребафни химу").submit();
            //TimeUnit.SECONDS.sleep(30);
            death = false;
        } while (himkasLopps > 0 && !death);
    }
}
