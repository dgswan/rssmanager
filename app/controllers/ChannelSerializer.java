package controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.Channel;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class ChannelSerializer implements JsonSerializer<Channel> {

    public JsonElement serialize(Channel channel, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", channel.id);
        jsonObject.addProperty("title", channel.title);
        jsonObject.addProperty("description", channel.description);
        jsonObject.addProperty("url", channel.url);
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy h:mm:ss a", Locale.ENGLISH);
        if (channel.pubDate != null) {
            jsonObject.addProperty("pubDate", df.format(channel.pubDate));
        }
        jsonObject.addProperty("image", channel.image);
        return jsonObject;
    }
}
