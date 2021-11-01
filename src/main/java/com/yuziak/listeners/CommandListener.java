package com.yuziak.listeners;

import com.yuziak.App;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CommandListener extends ListenerAdapter {

    public CommandListener(App app) {
        this.app = app;
    }

    private final App app;

    final String bossChanelName = "напоминалка";

    private List<HimkaReminder> himkaPool = new LinkedList<>();

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentDisplay().startsWith("!")) {
            String channelName0 = "comands";
            String channelName1 = "напоминалка";
            String himkalRoleName = "Химка";


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
/*
                    HimkaReminder himkaReminder = new HimkaReminder(event.getAuthor());
                    himkaReminder.start();
                    himkaPool.add(himkaReminder);*/

                }
                if (event.getMessage().getContentDisplay().startsWith("!stop")) {
                    for (HimkaReminder h : himkaPool) {
                        if (h.getUser().equals(event.getAuthor())) {
                 //           h.stop();
                        }
                    }
                }
                if (event.getMessage().getContentDisplay().startsWith("!death")) {
                    for (HimkaReminder h : himkaPool) {
                        if (h.getUser().equals(event.getAuthor())) {
                  //          h.death();
                        }
                    }
                }
            }
        }
    }

    public class HimkaReminder implements Callable {
        public HimkaReminder(User userRemind) {
            this.userRemind = userRemind;
        }

        public User getUser() {
            return userRemind;
        }

        private final User userRemind;
        private int himkaLoops;
        FutureTask task;

        public void start() {
            himkaLoops = 4;
            task = new FutureTask(this);
            Thread t = new Thread(task);
            t.start();
        }

        public void stop() {
            //  System.out.println("stop");
            himkaLoops=0;
            deleteAll(userRemind);
        }

        public void death() {
            task.cancel(true);
            // System.out.println("death");
            himkaLoops++;
            task = new FutureTask(this);
            Thread t = new Thread(task);
            t.start();
        }

        @Override
        public Object call() throws Exception {
            do {
                himkaLoops--;
                sendMessage(userRemind, "Бафай химу");

                // System.out.println(userRemind.getAsTag());
                TimeUnit.SECONDS.sleep(870);
            } while (himkaLoops > 0);
            deleteAll(userRemind);
            return null;
        }
    }

    static void sendMessage(User user, String content) {
        user.openPrivateChannel().queue(channel -> {
            channel.sendMessage(content).queue();
            channel.deleteMessageById(channel.getLatestMessageId()).queueAfter(30, TimeUnit.SECONDS);

        });
    }

    static void deleteAll(User user) {
        user.openPrivateChannel().queue(channel -> {
            channel.getHistory().getRetrievedHistory().forEach(message ->
                    channel.deleteMessageById(message.getId()).submit()
            );
        });
    }
}
