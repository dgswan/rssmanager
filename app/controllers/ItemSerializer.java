package controllers;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.Item;

import java.lang.reflect.Type;

public class ItemSerializer implements JsonSerializer<Item>{

    public JsonElement serialize(Item item, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", item.id);
        jsonObject.addProperty("title", item.title);
        jsonObject.addProperty("description", item.description);
        jsonObject.addProperty("url", item.url);
        jsonObject.addProperty("pubDate", item.pubDate.toString());
        jsonObject.addProperty("image", item.image);
        return jsonObject;
    }
}
