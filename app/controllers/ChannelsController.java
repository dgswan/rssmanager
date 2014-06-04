package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.syndication.io.FeedException;
import models.Channel;
import models.Item;
import models.User;
import play.Logger;
import play.mvc.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChannelsController extends Controller {

    public static final Gson gson;

    static {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Channel.class, new ChannelSerializer());
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    public static void channel(long channelId) {
        Channel channel = Channel.getChannel(channelId);
        Logger.info(channel.description);
        render(channelId, channel);
    }

    public static void channels(String q, int page, int length) {
       // List<Channel> channels = Channel.findAll();
        List<Channel> channels;
        if(q == null) {
            channels = Channel.findAll();
        } else {
            channels = Channel.getChannels(q, page, length);
        }
        Logger.info(q);
        String jsonChannels = gson.toJson(channels);
        render(jsonChannels, page, length);

    }

    public static void items(long channelId, int page, int length) {
        User user = User.getByCookie(request.cookies.get(User.AUTH_KEY).value);
        Channel channel = Channel.findById(channelId);
        List<Item> items;
        if(user.getChannel(channelId) == null) {
            items = channel.getItems(page, length);
        } else {
            items = channel.getItems(page, length); //TODO
        }


        String jsonItems = ItemsController.gson.toJson(items);
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