package com.yuziak;

import com.yuziak.listeners.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class App {
    private String testTime = "19:00:00";

    public void setTestTime(String s) {
        testTime = s;
    }

    public String getTestTime() {
        return testTime;
    }

    public static void main(String[] args) throws Exception {
        App newApp = new App();
        final String guildName = "ФПСеры";
        final String bossChanelName = "напоминалка";
        final String bossRoleName = "Босс";
        final String sonilRoleName = "Сонил";

        String token = "";

        final JDA bot = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS).build();
        bot.getPresence().setActivity(Activity.watching("Anime"));
        bot.addEventListener(new BotEventUpdateGear());
        bot.addEventListener(new BotEventDeleteGarbage());
        bot.addEventListener(new BotEventRoleProvider());
        bot.addEventListener(new BotEventNewUser());
        bot.addEventListener(new CommandListener(newApp));

        bot.awaitReady();
        TextChannel channelToRemind = bot.getTextChannelsByName(bossChanelName, true).get(0);
        Role boss = bot.getGuildsByName(guildName, false).get(0).getRolesByName(bossRoleName, false).get(0);
        Role sonil = bot.getGuildsByName(guildName, false).get(0).getRolesByName(sonilRoleName, false).get(0);
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);

                String timePattern = "HH:mm:ss";
                String time = LocalDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern(timePattern));
                if (time.equals("19:00:00") || time.equals("01:30:00") || time.equals(newApp.getTestTime())) {
                    channelToRemind.sendMessage("<@&" + sonil.getId() + "> Друг, забери своих сонилов").submit();
                }

                LocalDateTime curTime = LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusMinutes(15);
                String checkBefore15 = curTime.format(DateTimeFormatter.ofPattern("EEEE HH:mm:ss", Locale.ENGLISH));
                String check = curTime.minusMinutes(15).format(DateTimeFormatter.ofPattern("EEEE HH:mm:ss", Locale.ENGLISH));
                BossSchedule.schedule.forEach((k, v) -> {
                    for (String date : v) {
                        if (checkBefore15.equals(date)) {
                            channelToRemind.sendMessage("<@&" + boss.getId() + "> " + k + " рес через 15 минут").submit();
                        } else if (check.equals(date)) {
                            channelToRemind.sendMessage("<@&" + boss.getId() + "> " + k + " реснулся").submit();
                        }
                    }
                });


            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
        }
    }
}