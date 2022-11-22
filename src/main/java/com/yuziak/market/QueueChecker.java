package com.yuziak.market;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class QueueChecker {

    private final String urlPath = "https://api.arsha.io/v2/ru/GetWorldMarketWaitList";
    private final String garmothAssetsUrl = "https://assets.garmoth.com/items/";
    private List<QueueItem> currentRegistrationQueue = new ArrayList<>();
    final String BLACKSTAR_MAIN_CHANEL = "бс-меин-аук";
    final String BLACKSTAR_AWA_CHANEL = "бс-пробуда-аук";
    final String CURRENT_QUEUE_CHANEL = "текущие-лот";
    final String guildName = "ФПСеры";
    final String aukRoleNameRoleName = "Аук";
    final static String BS = "Blackstar ";
    final static String Godr_Ayed = "Godr-Ayed ";
    final static String[] AWAKENING = {"\"Greatsword", "Vediant", "Jordun", "Gardbrace", "Crimson Glaives", "Celestial Bo Staff", "Iron Buster", "Scythe",
            "Kerispear", "Lancia", "Kamasylven Sword", "Godr Sphera", "Crescent Blade", "Sura Katana", "Sting", "Aad Sphera", "Greatbow", "Sah Chakram",
            "Cestus", "Kibelius", "Patraca", "Dual Glaives"
    };
    final static String[] Main = {"Longsword", "Longbow", "Amulet", "Axe", "Shortsword", "Blade", "Staff", "Kriegsmesser", "Gauntlet", "Crescent Pendulum",
            "Crossbow", "Florang", "Battle Axe", "Shamshir", "Morning Star", "Kyve", "Serenaca", "Slayer"};

    TextChannel channelCurQueue;
    TextChannel channelBsMain;
    TextChannel channelBsAwa;
    Role auk;

    public void start(JDA bot) throws InterruptedException {

        Guild guild = bot.getGuildsByName(guildName, false).get(0);
        channelBsMain = guild.getTextChannelsByName(BLACKSTAR_MAIN_CHANEL, true).get(0);
        channelBsAwa = guild.getTextChannelsByName(BLACKSTAR_AWA_CHANEL, true).get(0);
        channelCurQueue = guild.getTextChannelsByName(CURRENT_QUEUE_CHANEL, true).get(0);

        auk = guild.getRolesByName(aukRoleNameRoleName, false).get(0);

        while (true) {
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

            MessageHistory history = MessageHistory.getHistoryFromBeginning(channelCurQueue).complete();
            List<Message> mess = history.getRetrievedHistory();
            for (Message m : mess) {
                m.delete().queue();
            }
            for (QueueItem queueItem : currentRegistrationQueue) {
                MessageEmbed curItem = createMessage(queueItem);
                channelCurQueue.sendMessageEmbeds(curItem).submit();
            }

            TimeUnit.MINUTES.sleep(1);
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
        if (isNeeded(queueItem, bsMain, 20L)) {
            send(channelBsMain, queueItem);
        }

        String[] geMain = Arrays.stream(Main).map(name -> Godr_Ayed + name).toArray(String[]::new);
        if (isNeeded(queueItem, geMain, 5L)) {
            send(channelBsMain, queueItem);
        }

        String[] bsAwa = Arrays.stream(AWAKENING).map(name -> BS + name).toArray(String[]::new);
        if (isNeeded(queueItem, bsAwa, 20L)) {
            send(channelBsAwa, queueItem);
        }

        String[] geAwa = Arrays.stream(AWAKENING).map(name -> Godr_Ayed + name).toArray(String[]::new);
        if (isNeeded(queueItem, geAwa, 5L)) {
            send(channelBsAwa, queueItem);
        }

        //Test
        String[] test = {"Black Distortion Earring"};
        if (isNeeded(queueItem, test, 4L)) {
            send(channelBsAwa, queueItem);
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

    private void send(TextChannel channel, QueueItem queueItem) {
        channel.sendMessage("<@&" + auk.getId() + ">").submit();
        MessageEmbed itemMessage = createMessage(queueItem);
        channel.sendMessageEmbeds(itemMessage).submit();
    }

    private MessageEmbed createMessage(QueueItem queueItem) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(queueItem.getName(), null);

        eb.setColor(new Color(0x37FFFFFF, true));

        String price = queueItem.getPrice().toString();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        eb.setDescription("Price: "+ price +"\n"
                + "Live at: " + sdfDate.format(new Date(queueItem.getLiveAt())));

        eb.setAuthor("Daniil", null, "https://cdn.discordapp.com/avatars/900039144946929724/ed553654042e7caf124cf30933c9cef8.png");

        eb.setFooter("ФПСеры", "https://cdn.discordapp.com/avatars/900039144946929724/ed553654042e7caf124cf30933c9cef8.png");

        eb.setThumbnail(garmothAssetsUrl + queueItem.getId() + ".png");

        return eb.build();
    }

}
