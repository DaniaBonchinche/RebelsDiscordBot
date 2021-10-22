package com.yuziak;

import java.util.HashMap;

public class Schedule {
    public static HashMap<String, String[]> schedule = new HashMap<>();
    static {
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
        schedule.put("Камос", new String[]{"Thursday 23:00:00", "Sunday 00:00:00", "Sunday 12:00:00"});
        schedule.put("Велл", new String[]{"Wednesday 23:00:00", "Sunday 16:00:00"});
        schedule.put("Биба и Боба", new String[]{"Tuesday 23:00:00", "Saturday 14:00:00"});
        schedule.put("Офин", new String[]{"Wednesday 18:00:00", "Friday 15:19:00", "Sunday 23:00:00"});
    }
}
