package com.yuziak;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {
    public static void main(String[] args) throws Exception {
        final JDA bot = JDABuilder.createDefault("OTAwMDM5MTQ0OTQ2OTI5NzI0.YW7gxg.zjq_HHKPWszq7CJZWTVXb42p1pI")
                .build();

        bot.addEventListener(new BotEventListener());
    }
}
