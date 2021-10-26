package com.yuziak.listeners;

import com.yuziak.SheetApi;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class BotEventUpdateGear extends ListenerAdapter {
    String CHANNEL_NAME = "боттест";

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        if (event.getChannel().getName().equals(CHANNEL_NAME) && !event.getMessage().getAuthor().isBot()) {
            String message = event.getMessage().getContentDisplay();
            if (message.length() > 9) {
                if (message.startsWith("!update") && message.split(" ").length == 9) {
                    String[] data = message.substring(8).split(", ");
                    for (int i = 0; i < data.length; i++) {
                        if (data[i].equals("")) {
                            data[i] = "1999999999";
                        }
                    }
                    try {
                        Integer.parseInt(data[2]);
                        Integer.parseInt(data[3]);
                        Integer.parseInt(data[4]);
                        Integer.parseInt(data[5]);
                        try {
                            answer(updateValidator(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3])
                                    , Integer.parseInt(data[4]), Integer.parseInt(data[5]), data[6], data[7]), event.getMessage());
                        } catch (IOException | GeneralSecurityException e) {
                            e.printStackTrace();
                        }
                    } catch (NumberFormatException e) {
                        event.getMessage().reply("Непонял (ты что-то с порядком напутал)").timeout(1, TimeUnit.SECONDS).submit();
                    }

                } else {
                    event.getMessage().reply("Непонял").timeout(1, TimeUnit.SECONDS).submit();
                }
            } else {
                event.getMessage().reply("Непонял").timeout(1, TimeUnit.SECONDS).submit();
            }
        }
    }

    public static void answer(String kek, Message message) {
        switch (kek) {
            case "User not found":
                message.reply("Дружек, такой фамилии нет в таблице :__~1:").timeout(1, TimeUnit.SECONDS).submit();
                break;
            case "ClassNull":
                message.reply("Не верно написан класс").timeout(1, TimeUnit.SECONDS).submit();
                break;
            case "ApNull":
                message.reply("Что-то не так с атакой").timeout(1, TimeUnit.SECONDS).submit();
                break;
            case "DefNull":
                message.reply("Что-то не так с дефом").timeout(1, TimeUnit.SECONDS).submit();
                break;
            case "AccuracyNull":
                message.reply("Что-то не так с меткой").timeout(1, TimeUnit.SECONDS).submit();
                break;
            case "HorseTypeNull":
                message.reply("Неправильная лошадь").timeout(1, TimeUnit.SECONDS).submit();
                break;
            case "CkrockTypeNull":
                message.reply("Не правильный крогдаллор").timeout(1, TimeUnit.SECONDS).submit();
                break;
            case "HorseDefNull":
                message.reply("Что-то не так с дефом лошадки").timeout(1, TimeUnit.SECONDS).submit();
                break;
            case "Successful update":
                message.reply(":thumbsup:").timeout(1, TimeUnit.SECONDS).submit();
                break;
        }

    }


    public static String updateValidator(String name, String gameClass, Integer ap, Integer def, Integer accuracy, Integer horseDef, String ckrockType, String horseType) throws IOException, GeneralSecurityException {
        String[] classesPool = {"Воин", "Валькирия", "Волшебник", "Волшебница", "Страйкер", "Фурия", "Лучница", "Лучник", "Моева", "Ронин","Лан",
                "Хассашин", "Тёмный рыцарь", "Сорка", "Куноичи", "Ниндзя", "Мистик", "Варвар", "Нова", "Страж", "Шай", "Мудрец", "Рыба", null};
        String[] horseTypePool = {"8", "Единорог", "Дум", "Пегас", "Грёз", null};
        String[] ckrockTypePool = {"Ветер", "Земля", "Море","-", null};

        if (gameClass.equals("1999999999")) {
            gameClass = null;
        }
        if (ap.equals(1999999999)) {
            ap = null;
        }
        if (def.equals(1999999999)) {
            def = null;
        }
        if (accuracy.equals(1999999999)) {
            accuracy = null;
        }
        if (horseDef.equals(1999999999)) {
            horseDef = null;
        }
        if (ckrockType.equals("1999999999")) {
            ckrockType = null;
        }
        if (horseType.equals("1999999999")) {
            horseType = null;
        }
        if (Arrays.asList(classesPool).contains(gameClass)) {
            if (Arrays.asList(horseTypePool).contains(horseType)) {
                if (Arrays.asList(ckrockTypePool).contains(ckrockType)) {
                    return SheetApi.updateMember(name, gameClass, ap, def, accuracy, horseDef, ckrockType, horseType);
                } else {
                    return "CkrockTypeNull";
                }
            } else {
                return "HorseTypeNull";
            }
        } else {
            return "ClassNull";
        }
    }
}
