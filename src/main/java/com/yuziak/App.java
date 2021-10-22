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
        final JDA bot = JDABuilder.createDefault("OTAwMDM5MTQ0OTQ2OTI5NzI0.YW7gxg.QzAfH5OMm8JkwBZ-FgNAgJ_VLCk")
                .build();
        bot.getPresence().setActivity(Activity.watching("Anime"));
        bot.addEventListener(new BotEventUpdateGear());
        bot.addEventListener(new BotEventDeleteGarbage());
        bot.awaitReady();
        TextChannel channelВoss = bot.getTextChannelsByName("боссы", true).get(0);
        TextChannel channelSonil = bot.getTextChannelsByName("сонилы", true).get(0);

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);

                String timePattern = "HH:mm:ss";
                String time = LocalDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern(timePattern));
                if (time.equals("19:00:00") || time.equals("01:00:00")) {
                    channelSonil.sendMessage("@here Друг, забери своих сонилов").submit();
                }
                LocalDateTime curTime = LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusMinutes(15);
                String check = curTime.format(DateTimeFormatter.ofPattern("EEEE HH:mm:ss", Locale.ENGLISH));
                Schedule.schedule.forEach((k, v) -> {
                    for (String date : v) {
                        if (check.equals(date)) {
                            channelВoss.sendMessage("@here " + k + " рес через 15 минут").submit();
                            channelВoss.sendMessage("@here " + k + " реснулся").queueAfter(15, TimeUnit.MINUTES);
                        }
                    }
                });


            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
        }
    }
}