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
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CommandListener extends ListenerAdapter {

    public CommandListener(App app) {
        this.app = app;
    }

    private final App app;
    private int himkasLopps = 4;
    final String bossChanelName = "напоминалка";
    private TextChannel channelToRemind;
    private Role himka;
    private HimkaReminder himkaRem;
    private FutureTask task;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentDisplay().startsWith("!")) {
            String channelName0 = "comands";
            String channelName1 = "напоминалка";
            String himkalRoleName = "Химка";
            channelToRemind = event.getGuild().getTextChannelsByName(bossChanelName, true).get(0);
            himka = event.getGuild().getRolesByName(himkalRoleName, false).get(0);

            if (event.getChannel().getName().equals(channelName0)) {
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
            }
            if (event.getChannel().getName().equals(channelName1)) {
                if (event.getMessage().getContentDisplay().startsWith("!start")) {
                    this.himkasLopps = 4;
                    himkaRem = new HimkaReminder();
                    task = new FutureTask(himkaRem);
                    Thread t = new Thread(task);
                    t.start();
                }
                if (event.getMessage().getContentDisplay().startsWith("!stop")) {
                    this.himkasLopps = 0;
                }
                if (event.getMessage().getContentDisplay().startsWith("!death")) {
                    himkasLopps++;
                    task.cancel(true);
                    himkaRem = new HimkaReminder();
                    task = new FutureTask(himkaRem);
                    Thread t = new Thread(task);
                    t.start();
                }
            }
        }
    }

    public class HimkaReminder implements Callable {

        @Override
        public Object call() throws Exception {
            do {
                himkasLopps--;
                channelToRemind.sendMessage("<@&" + himka.getId() + "> ребафни химу").submit();
                TimeUnit.SECONDS.sleep(15);
            } while (himkasLopps > 0);
            return null;
        }
    }

}
