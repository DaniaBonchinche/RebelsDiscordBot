package com.yuziak;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class Bot {
    public static void main(String[] args) throws Exception {
        final JDA bot = JDABuilder.createDefault("")
                .build();
        bot.getPresence().setActivity(Activity.watching("Anime"));

        bot.addEventListener(new BotEventUpdateGear());
       // bot.addEventListener(new Reminder());

    }
}