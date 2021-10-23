package com.yuziak;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {

        final String guildName = "ФПСеры";
        final String bossChanelName = "сонилы-боссы";
        final String bossRoleName = "Босс";
        final String sonilChanelName = "Сонил";

        String token = "";


        final JDA bot = JDABuilder.createDefault(token).build();
        bot.getPresence().setActivity(Activity.watching("Anime"));
        bot.addEventListener(new BotEventUpdateGear());
        bot.addEventListener(new BotEventDeleteGarbage());
        bot.addEventListener(new BotEventRoleProvider());

        bot.awaitReady();
        TextChannel channelToRemind = bot.getTextChannelsByName(bossChanelName, true).get(0);
        Role boss = bot.getGuildsByName(guildName, false).get(0).getRolesByName(bossRoleName, false).get(0);
        Role sonil = bot.getGuildsByName(guildName, false).get(0).getRolesByName(sonilChanelName, false).get(0);
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);

                String timePattern = "HH:mm:ss";
                String time = LocalDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern(timePattern));
                if (time.equals("19:00:00") || time.equals("01:30:00")) {
                    channelToRemind.sendMessage("<@&"+sonil.getId()+"> Друг, забери своих сонилов").submit();
                }

                LocalDateTime curTime = LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusMinutes(15);
                String checkBefore15 = curTime.format(DateTimeFormatter.ofPattern("EEEE HH:mm:ss", Locale.ENGLISH));
                String check = curTime.minusMinutes(15).format(DateTimeFormatter.ofPattern("EEEE HH:mm:ss", Locale.ENGLISH));
                Schedule.schedule.forEach((k, v) -> {
                    for (String date : v) {
                        if (checkBefore15.equals(date)) {
                            channelToRemind.sendMessage("<@&"+boss.getId()+"> " + k + " рес через 15 минут").mention(boss).submit();
                        } else if (check.equals(date)) {
                            channelToRemind.sendMessage("<@&"+boss.getId()+"> " + k + " реснулся").mention(boss).submit();
                        }
                    }
                });


            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
        }
    }
}