package com.yuziak;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Reminder extends ListenerAdapter {
    public Reminder() {
        schedule.put("Кзарка", new String[]{"Monday 00:00:00", "Tuesday 00:00:00", "Tuesday 14:00:00",
                "Wednesday 18:00:00", "Thursday 18:00:00", "Friday 01:00:00", "Friday 18:00:00",
                "Saturday 00:00:00", "Saturday 08:00:00", "Sunday 01:00:00", "Sunday 18:00:00"});
        schedule.put("Каранда", new String[]{"Monday 00:00:00", "Monday 16:00:00", "Monday 23:00:00",
                "Tuesday 01:00:00", "Tuesday 12:00:00", "Tuesday 18:00:00", "Wednesday 14:00:00",
                "Thursday 00:00:00", "Friday 12:00:00", "Saturday 16:00:00", "Sunday 08:00:00"});
        schedule.put("Нубер", new String[]{"Monday 01:00:00", "Monday 14:00:00", "Monday 23:00:00",
                "Wednesday 01:00:00", "Wednesday 16:00:00", "Thursday 12:00:00", "Thursday 16:00:00",
                "Friday 00:00:00", "Friday 16:00:00", "Saturday 00:00:00", "Saturday 10:00:00",
                "Sunday 18:00:00"});
        schedule.put("Кутум", new String[]{"Monday 12:00:00", "Monday 18:00:00", "Tuesday 16:00:00",
                "Wednesday 00:00:00", "Thursday 01:00:00", "Thursday 22:00:00", "Friday 14:00:00",
                "Saturday 01:00:00", "Saturday 12:00:00", "Saturday 18:00:00", "Sunday 10:00:00",
                "Sunday 14:00:00"});
        schedule.put("Камос", new String[]{"Thursday 23:02:00", "Sunday 00:00:00", "Sunday 12:00:00"});
        schedule.put("Велл", new String[]{"Wednesday 23:00:00", "Sunday 16:00:00"});
        schedule.put("Биба и Боба", new String[]{"Tuesday 23:00:00", "Saturday 14:00:00"});
        schedule.put("Офин", new String[]{"Wednesday 18:00:00", "Friday 23:00:00", "Sunday 23:00:00"});
    }

    private HashMap<String, String[]> schedule = new HashMap<>();
    private TextChannel channel;
    private final boolean channelDet = false;

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        if (!channelDet) {
            for (TextChannel chan : event.getJDA().getTextChannels()) {
                if (chan.getName().equals("напоминалка")) {
                    this.channel = chan;
                    break;
                }
            }
        }
        ScheduledExecutorService
        try {
            TimeUnit.SECONDS.sleep(1);

            String timePattern = "HH:mm:ss";
            String time = LocalDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern(timePattern));
            if (time.equals("22:56:00") || time.equals("01:00:00")) {
                this.channel.sendMessage("Друг, забери своих сонилов").submit();
            }
            schedule.forEach((k, v) -> {
                for (String date : v) {
                    LocalDateTime curTime = LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusMinutes(15);
                    String check = curTime.format(DateTimeFormatter.ofPattern("EEEE HH:mm:ss", Locale.ENGLISH));
                    if (check.equals(date)) {
                        this.channel.sendMessage(k + " рес через 15 минут").submit();
                        this.channel.sendMessage(k + " реснулся").queueAfter(15, TimeUnit.MINUTES);
                    }
                }
            });


        } catch (InterruptedException e) {
            //kkk
        }

    }

}

