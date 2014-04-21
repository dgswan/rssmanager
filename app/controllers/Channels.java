package controllers;

import com.google.gson.Gson;
import models.Channel;
import models.Item;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

public  class Channels extends Controller {

    public static final Gson gson = new Gson();

    public  static void channel(int channelId) {
        Channel channel = new Channel(); //TODO
        render(channelId, channel);
    }

    public  static void channels(int offset, int limit) {
        List<Channel> channels = new ArrayList<Channel>();
        channels.add(new Channel());
        channels.add(new Channel());
        String jsonChannels = gson.toJson(channels);
        render(jsonChannels, offset, limit);

    }

    public static void channels(String q, int offset, int limit) {
        List<Channel> channels = new ArrayList<Channel>();
        channels.add(new Channel());
        channels.add(new Channel());
        String jsonChannels = gson.toJson(channels);
        render(jsonChannels, offset, limit);

    }

    public  static void items(int channelId, int offset, int limit) {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item());
        items.add(new Item());

        String jsonItems = gson.toJson(items);
        render(jsonItems, offset, limit);
    }

    public static void create(String url) {
        Channel channel = new Channel();
        render(channel);

    }

    public static void subscribe(int channelId) {
        List<Channel> channels = new ArrayList<Channel>();
        channels.add(new Channel());

    }

    public static void unsubscribe(int channelId) {
    }



}