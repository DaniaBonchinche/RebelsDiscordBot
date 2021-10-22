package com.yuziak;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
        final JDA bot = JDABuilder.createDefault("")
                .build();
        bot.getPresence().setActivity(Activity.watching("Anime"));
        bot.addEventListener(new BotEventUpdateGear());
        bot.addEventListener(new BotEventDeleteGarbage());
        bot.awaitReady();
        List<TextChannel> channels = bot.getTextChannelsByName("напоминалка",true);

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);

                String timePattern = "HH:mm:ss";
                String time = LocalDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern(timePattern));
                if (time.equals("19:00:00") || time.equals("01:00:00")) {
                    channels.get(0).sendMessage("@here Друг, забери своих сонилов").submit();
                }
                Schedule.schedule.forEach((k, v) -> {
                    for (String date : v) {
                        LocalDateTime curTime = LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusMinutes(15);
                        String check = curTime.format(DateTimeFormatter.ofPattern("EEEE HH:mm:ss", Locale.ENGLISH));
                        if (check.equals(date)) {
                            channels.get(0).sendMessage("@here "+k + " рес через 15 минут").submit();
                            channels.get(0).sendMessage("@here "+k + " реснулся").queueAfter(15, TimeUnit.MINUTES );
                        }
                    }
                });


            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
        }
    }
}