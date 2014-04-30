package controllers;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.Item;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ItemSerializer implements JsonSerializer<Item>{  //ADDCHANNELID TODO

    public JsonElement serialize(Item item, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", item.id);
        jsonObject.addProperty("title", item.title);
        jsonObject.addProperty("description", item.description);
        jsonObject.addProperty("url", item.url);
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy h:mm:ss a", Locale.ENGLISH);
        jsonObject.addProperty("pubDate", df.format(item.pubDate));
        jsonObject.addProperty("image", item.image);
        return jsonObject;
    }
}
