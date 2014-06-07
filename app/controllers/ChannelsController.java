package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (q == null) {
            User user = User.getBySession(session);
            channels = Channel.getByUser(user, page, length);
            Logger.info("get channels %s %d", user.username, channels.size());
        } else {
            channels = Channel.getChannels(q, page, length);
        }
        Logger.info(q);
        String jsonChannels = gson.toJson(channels);
        Map<String, Object> response = new HashMap<String, Object>();
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("page", page);
        metadata.put("length", length);
        metadata.put("count", 42);
        response.put("channels", jsonChannels);
        response.put("metadata", metadata);
        renderJSON(response);
    }

    // create user's channel
    public static void create(String url) {
        Logger.info("url " + url);
        int code;
        Long channelId = -1L;
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        try {
            Channel channel = Channel.addChannel(url);
            if (channel != null) {
                channelId = channel.getId();
                code = Http.StatusCode.OK;
                User user = User.getBySession(session);
                if (user != null) {
                    user.subscribe(channel);
                    Logger.info(user.username);
                }
            } else {
                Logger.info("channel is null");
                code = Http.StatusCode.BAD_REQUEST;
            }

        } catch (Exception e) {
            Logger.info("exception : " + e.getMessage());
            code = Http.StatusCode.BAD_REQUEST;
        }
        jsonResponse.put("id", channelId);
        jsonResponse.put("code", code);
        renderJSON(jsonResponse);
    }

    public static void items(long channelId, int page, int length) {
        User user = User.getBySession(session);
        Channel channel = Channel.findById(channelId);
        List<Item> items;
        if (!user.isSubscribed(channelId)) {
            items = channel.getItems(page, length);
        } else {
            items = Item.getItems(user, channel, page, length);
        }

        String jsonItems = ItemsController.gson.toJson(items);
        render(jsonItems, page, length);
    }

    public static void subscribe(long channelId) {
        int code;
        User user = User.getBySession(session);
        Channel channel = Channel.getChannel(channelId);
        UserChannel userChannel = UserChannel.find("channel = ? and user = ?", channel, user).first();

        Logger.info(user.id.toString());
        Logger.info(channel.id.toString());

        if (userChannel == null) {
            user.subscribe(channel);
            code = Http.StatusCode.OK;
        } else {
            code = Http.StatusCode.BAD_REQUEST;
        }
        render(code);
    }

    public static void unsubscribe(int channelId) {
        User user = User.getBySession(session);
        int code;
        Channel channel = Channel.getChannel(channelId);
        UserChannel userChannel = UserChannel.find("user = ? and channel = ?", user, channel).first();
        if (userChannel != null) {
            userChannel.delete();
            List<UserItem> userItems = UserItem.getByChannelAndUser(channel, user);
            for (UserItem ui : userItems) {
                ui.delete();
            }
            code = Http.StatusCode.OK;
        } else {
            code = Http.StatusCode.BAD_REQUEST;
        }
        render(code);

    }


}