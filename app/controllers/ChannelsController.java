package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Http;

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
        if (q == null) {
            User user = User.getBySession(session);
            channels = Channel.getByUser(user, page, length);
            Logger.info("get channels %s %d", user.username, channels.size());
        } else {
            channels = Channel.getChannels(q, page, length);
        }
        Logger.info(q);
        String jsonChannels = gson.toJson(channels);
        render(jsonChannels, page, length);

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

    public static void create(String url) {
        Channel channel = new Channel();
        render(channel);
    }

    public static void subscribe(long channelId) {
        int code;
        User user = User.getBySession(session);
        Channel channel = Channel.getChannel(channelId);
        UserChannel userChannel = UserChannel.find("channel = ? and user = ?", channel, user).first();

        Logger.info(user.id.toString());
        Logger.info(channel.id.toString());

        if (userChannel == null) {
            userChannel = new UserChannel(user, channel);
            userChannel.create();
            code = Http.StatusCode.OK;
            List<Item> items = channel.getItems(1, 10);
            for (Item item : items) {
                UserItem useritem = new UserItem(user, item);
                useritem.create();
            }
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