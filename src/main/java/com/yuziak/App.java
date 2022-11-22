package com.yuziak;

import com.yuziak.listeners.*;
import com.yuziak.market.QueueChecker;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;


public class App {

    public static void main(String[] args) throws Exception {

        String token = "OTAwMDM5MTQ0OTQ2OTI5NzI0.Gwh1JD.te_xgYSajIbZ_WA-Z-wsiPFQrmXprMnrD9OJQU";

        final JDA bot = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS).build();
        bot.getPresence().setActivity(Activity.watching("Anime"));

        bot.addEventListener(new BotEventNewUser());
        bot.addEventListener(new InRegQueueListener());


        bot.awaitReady();

       // BossSchedule.doSchedule(bot);
        QueueChecker queueChecker = new QueueChecker();
    }


}