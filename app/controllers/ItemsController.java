package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Item;
import play.Logger;
import play.mvc.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsController extends Controller {
    public static final Gson gson;

    static {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Item.class, new ItemSerializer());
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    public static void items(int page, int lenght) {
        List<Item> items = Item.findAll();   // very tmp
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("items", items);
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("offset", page);
        metadata.put("limit", lenght);
        metadata.put("count", 42);
        response.put("metadata", metadata);
        String prettyJson = gson.toJson(response);
        renderJSON(prettyJson);
    }

    public static void item(int itemId) {
        Item item = new Item();
        String jsonItem = gson.toJson(item);
        render(jsonItem);
    }

    public static void action (int itemId, String typeOfAction) {
        Logger.info("%d", itemId);
    }
}
