package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Channel;
import models.Item;
import play.Logger;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

public  class ChannelsController extends Controller {

    public static final Gson gson;

    static {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Channel.class, new ChannelSerializer());
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    public  static void channel(long channelId) {
        Channel channel = Channel.getChannel(channelId);
        Logger.info(channel.description);
        render(channelId, channel);
    }

    public  static void channels(int page, int length) {
       // User user = User.findById(1); //TODO
      //  List<Channel> channels = Channel.getChannels(user, page, length);
        List<Channel> channels = new ArrayList<Channel>();
       // channels.add(new Channel());
      //  channels.add(new Channel());
        channels = Channel.getChannels(null, 0, 0);
        String jsonChannels = gson.toJson(channels);
       render(jsonChannels, page, length);

    }

    public static void channels(String q, int page, int length) {
        List<Channel> channels = new ArrayList<Channel>();
        //channels.add(new Channel());
        //channels.add(new Channel());
        String jsonChannels = gson.toJson(channels);
        render(jsonChannels, page, length);

    }

    public  static void items(int channelId, int page, int length) {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item());
        items.add(new Item());

        String jsonItems = gson.toJson(items);
        render(jsonItems, page, length);
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