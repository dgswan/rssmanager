package controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.Channel;

import java.lang.reflect.Type;


public class ChannelSerializer implements JsonSerializer<Channel> {

    public JsonElement serialize(Channel channel, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", channel.id);
        jsonObject.addProperty("title", channel.title);
        jsonObject.addProperty("description", channel.description);
        jsonObject.addProperty("url", channel.url);
        jsonObject.addProperty("pubDate", channel.pubDate.toString());
        jsonObject.addProperty("image", channel.image);
        return jsonObject;
    }
}
