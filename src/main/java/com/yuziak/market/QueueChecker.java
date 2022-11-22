package com.yuziak.market;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class QueueChecker {

    private final String urlPath = "https://api.arsha.io/v2/ru/GetWorldMarketWaitList";
    private final String garmothAssetsUrl="assets.garmoth.com/items/";
    private List<QueueItem> currentRegistrationQueue = new ArrayList<>();

    final static String BS = "Blackstar ";
    final static String Godr_Ayed = "Godr-Ayed ";
    final static String[] AWAKENING = {"\"Greatsword", "Vediant", "Jordun", "Gardbrace", "Crimson Glaives", "Celestial Bo Staff", "Iron Buster", "Scythe",
            "Kerispear", "Lancia", "Kamasylven Sword", "Godr Sphera", "Crescent Blade", "Sura Katana", "Sting", "Aad Sphera", "Greatbow", "Sah Chakram",
            "Cestus", "Kibelius", "Patraca", "Dual Glaives"
    };
    final static String[] Main = {"Longsword", "Longbow", "Amulet", "Axe", "Shortsword", "Blade", "Staff", "Kriegsmesser", "Gauntlet", "Crescent Pendulum",
            "Crossbow", "Florang", "Battle Axe", "Shamshir", "Morning Star", "Kyve", "Serenaca", "Slayer"};


    public static void main(String[] args) {
//
//        QueueChecker queueChecker = new QueueChecker();
//
//        List<QueueItem> queueItem = queueChecker.mapToQueueItem(queueChecker.check());


        System.out.println(new Date().getTime());
    }


    public void start() throws InterruptedException {
        while (true) {
            TimeUnit.MINUTES.sleep(1);

            Long currentTime = new Date().getTime();
            currentRegistrationQueue.removeIf(queueItem -> queueItem.getLiveAt() < currentTime);

            String queueJSON = check();
            List<QueueItem> newQueueItems = mapToQueueItem(queueJSON);

            for (QueueItem newQueueItem : newQueueItems) {
                boolean isNew = true;
                for (QueueItem currentQueueItem : currentRegistrationQueue) {
                    if (!currentQueueItem.isAnother(newQueueItem)) {
                        isNew = false;
                    }
                }
                if (isNew) {
                    checkNeededItems(newQueueItem);
                    currentRegistrationQueue.add(newQueueItem);
                }
            }

        }
    }

    private String check() {
        try {
            Connection.Response response = Jsoup.connect(urlPath).ignoreContentType(true).method(Connection.Method.GET).execute();
            String json = response.body();
            if (!json.startsWith("[") && !json.endsWith("]")) {
                json = "[" + json + "]";
            }
            return json;

        } catch (IOException e) {
            if (!e.getMessage().contains("515")) {
                System.out.println(e.getMessage());
            }
            return null;
        }
    }

    private List<QueueItem> mapToQueueItem(String json) {
        List<QueueItem> queueItems = new ArrayList<>();
        if (Objects.isNull(json)) {
            return queueItems;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            queueItems = objectMapper.readValue(json, new TypeReference<List<QueueItem>>() {
            });

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Error with mapping. Json: " + json);
        }
        return queueItems;
    }

    private void checkNeededItems(QueueItem queueItem) {
        String[] bsMain = Arrays.stream(Main).map(name -> BS + name).toArray(String[]::new);
        if (isNeeded(queueItem,bsMain,20L)){
            //    send();
        }

        String[] geMain = Arrays.stream(Main).map(name -> Godr_Ayed + name).toArray(String[]::new);
        if (isNeeded(queueItem,bsMain,5L)){
            //    send();
        }

        String[] bsAwa = Arrays.stream(AWAKENING).map(name -> BS + name).toArray(String[]::new);
        if (isNeeded(queueItem,bsMain,20L)){
            //    send();
        }

        String[] geAwa = Arrays.stream(AWAKENING).map(name -> Godr_Ayed + name).toArray(String[]::new);
        if (isNeeded(queueItem,bsMain,5L)){
            //    send();
        }


    }

    private boolean isNeeded(QueueItem queueItem, String[] names, Long subId) {
        if (!queueItem.getSubId().equals(subId)) {
            return false;
        }
        for (String name : names) {
            if (queueItem.getName().contains(name)) {
                return true;
            }
        }
        return false;
    }

    private void send(TextChannel channel, Role auk, QueueItem queueItem) {

            channel.sendMessage("<@&" + auk.getId() + ">").submit();
     //       channel.sendMessageEmbeds().submit();

    }

    private MessageEmbed createMessage(QueueItem queueItem) {

    }

}
